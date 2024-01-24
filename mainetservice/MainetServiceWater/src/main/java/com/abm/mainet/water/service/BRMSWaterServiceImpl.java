package com.abm.mainet.water.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.datamodel.WaterRateMaster;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author hiren.poriya
 * @Since 07-Jun-2018
 */
@WebService(endpointInterface = "com.abm.mainet.water.service.BRMSWaterService")
@Api(value = "/brmswaterservice")
@Path("/brmswaterservice")
@Service
public class BRMSWaterServiceImpl implements BRMSWaterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRMSWaterServiceImpl.class);
    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";

    @Resource
    private ServiceMasterService serviceMasterService;
    @Resource
    private TbTaxMasService taxMasService;
    private static Map<Long, String> dependsOnFactorMap = null;

    @Autowired
    IOrganisationService organisationService;
    

    @POST
    @Path("/dependentparams")
    @ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
    @Override
    @Transactional(readOnly = true)
    public WSResponseDTO getApplicableTaxes(
	    @ApiParam(value = "get dependent params", required = true) WSRequestDTO requestDTO) {
	WSResponseDTO responseDTO = new WSResponseDTO();
	LOGGER.info("brms water getApplicableTaxes execution start..");
	try {
	    if (requestDTO.getDataModel() == null) {
		responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
		responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
	    } else {
		WaterRateMaster waterRateMaster = (WaterRateMaster) CommonMasterUtility
			.castRequestToDataModel(requestDTO, WaterRateMaster.class);
		validateDataModel(waterRateMaster, responseDTO);
		if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
		    responseDTO = populateOtherFieldsForServiceCharge(waterRateMaster, responseDTO);
		}
	    }
	} catch (CloneNotSupportedException | FrameworkException ex) {
	    responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
	    responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
	    throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
	}
	LOGGER.info("brms water getApplicableTaxes execution end..");
	return responseDTO;
    }

    @POST
    @Path("/servicecharge")
    @ApiOperation(value = "get water service charge", notes = "get water service charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableCharges(
	    @ApiParam(value = "get water service charge", required = true) WSRequestDTO requestDTO) {
	LOGGER.info("brms water getApplicableCharges execution start..");
	WSResponseDTO responseDTO = null;
	try {
	    responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.WATER_SERVICE_CHARGE_URL);
	    if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
		responseDTO = setServiceChargeDTO(responseDTO);
	    } else {
		return responseDTO;
	    }
	} catch (Exception ex) {
	    throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
	}
	LOGGER.info("brms water getApplicableCharges execution End..");
	return responseDTO;
    }

    // set Service Charge DTO details based on rule response
    private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
	LOGGER.info("setServiceChargeDTO execution start..");
	final List<?> charges = RestClient.castResponse(responseDTO, WaterRateMaster.class);
	final List<WaterRateMaster> finalRateMaster = new ArrayList<>();
	for (final Object rate : charges) {
	    final WaterRateMaster masterRate = (WaterRateMaster) rate;
	    finalRateMaster.add(masterRate);
	}
	final ChargeDetailDTO chargedto = new ChargeDetailDTO();
	final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
	for (final WaterRateMaster rateCharge : finalRateMaster) {
	    chargedto.setChargeCode(rateCharge.getTaxId());
	    chargedto.setChargeAmount(rateCharge.getFlatRate());
	    chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
	    chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
	    detailDTOs.add(chargedto);
	}
	responseDTO.setResponseObj(detailDTOs);
	LOGGER.info("setServiceChargeDTO execution end..");
	return responseDTO;
    }

    /**
     * validating WaterRateMaster model
     * 
     * @param waterRateMaster
     * @param responseDTO
     * @return
     */
    private WSResponseDTO validateDataModel(WaterRateMaster waterRateMaster, WSResponseDTO responseDTO) {
	LOGGER.info("validateDataModel execution start..");
	StringBuilder builder = new StringBuilder();
	if (waterRateMaster.getServiceCode() == null || waterRateMaster.getServiceCode().isEmpty()) {
	    builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
	}
	if (waterRateMaster.getOrgId() == 0l) {
	    builder.append(ORG_ID_CANT_BE_ZERO).append(",");
	}
	if (waterRateMaster.getChargeApplicableAt() == null || waterRateMaster.getChargeApplicableAt().isEmpty()) {
	    builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
	} else if (!StringUtils.isNumeric(waterRateMaster.getChargeApplicableAt())) {
	    builder.append(CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC);
	}
	if (builder.toString().isEmpty()) {
	    responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
	} else {
	    responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
	    responseDTO.setErrorMessage(builder.toString());
	}

	return responseDTO;
    }

    // preparing applicable tax details from tax master
    public WSResponseDTO populateOtherFieldsForServiceCharge(WaterRateMaster waterRateMaster, WSResponseDTO responseDTO)
	    throws CloneNotSupportedException {
	LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
	List<WaterRateMaster> listOfCharges;
	ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(waterRateMaster.getServiceCode(),
		waterRateMaster.getOrgId());
	LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
		Long.valueOf(waterRateMaster.getChargeApplicableAt()),
		organisationService.getOrganisationById(waterRateMaster.getOrgId()));
	if (serviceMas.getSmFeesSchedule().equals(1l)
		&& ((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
			&& lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
		|| ((serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES))
			&& lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY))) {
	    List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
		    serviceMas.getSmServiceId(), waterRateMaster.getOrgId(),
		    Long.parseLong(waterRateMaster.getChargeApplicableAt()));
	    Organisation organisation = new Organisation();
	    organisation.setOrgid(waterRateMaster.getOrgId());
	    listOfCharges = settingAllFields(applicableCharges, waterRateMaster, organisation);
	    responseDTO.setResponseObj(listOfCharges);
	    responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
	} else {
	    responseDTO.setFree(true);
	    responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
	}
	LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
	return responseDTO;
    }

    /**
     * 
     * @param applicableCharges
     * @param rateMaster
     * @return
     * @throws CloneNotSupportedException
     */
    private List<WaterRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, WaterRateMaster rateMaster,
	    Organisation organisation) throws CloneNotSupportedException {
	LOGGER.info("settingAllFields execution start..");
	List<WaterRateMaster> list = new ArrayList<>();
	for (TbTaxMasEntity entity : applicableCharges) {
	    WaterRateMaster waterRateMaster = (WaterRateMaster) rateMaster.clone();
	    // SLD for dependsOnFactor
	    String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(entity.getTaxMethod()),
		    MainetConstants.FlagE, rateMaster.getOrgId());
	    String chargeApplicableAt = CommonMasterUtility.getCPDDescription(entity.getTaxApplicable(),
		    MainetConstants.FlagE, entity.getOrgid());
	    waterRateMaster.setTaxType(taxType);
	    waterRateMaster.setTaxCode(entity.getTaxCode());
	    waterRateMaster.setChargeApplicableAt(chargeApplicableAt);
	    waterRateMaster.setChargeDescEng(entity.getTaxDesc());
	    waterRateMaster.setChargeDescReg(entity.getTaxDesc());
	    settingTaxCategories(waterRateMaster, entity, organisation);
	    waterRateMaster.setDependsOnFactorList(settingDependsOnFactor(entity.getListOfTbTaxDetMas(), organisation));
	    waterRateMaster.setTaxId(entity.getTaxId());
	    list.add(waterRateMaster);
	}
	LOGGER.info("settingAllFields execution end..");
	return list;
    }

    /**
     * 
     * @param waterRateMaster
     * @param enity
     * @param organisation
     * @return
     */
    private WaterRateMaster settingTaxCategories(WaterRateMaster waterRateMaster, TbTaxMasEntity enity,
	    Organisation organisation) {

	if (enity.getTaxCategory1() != null) {
	    waterRateMaster.setTaxCategory(CommonMasterUtility
		    .getHierarchicalLookUp(enity.getTaxCategory1(), organisation).getDescLangFirst());
	}
	if (enity.getTaxCategory2() != null) {
	    waterRateMaster.setTaxSubCategory(CommonMasterUtility
		    .getHierarchicalLookUp(enity.getTaxCategory2(), organisation).getDescLangFirst());
	}
	return waterRateMaster;

    }

    private List<String> settingDependsOnFactor(List<TbTaxDetMasEntity> taxDetList, Organisation orgId) {

	if (dependsOnFactorMap == null) {
	    cacheDependsOnFactors(orgId);
	}
	List<String> dependsOnFactorList = new ArrayList<>();

	if (taxDetList != null) {
	    for (TbTaxDetMasEntity entity : taxDetList) {
		if(StringUtils.equalsIgnoreCase(entity.getStatus(), "A"))
		dependsOnFactorList.add(dependsOnFactorMap.get(entity.getTdDependFact()));
	    }
	}

	return dependsOnFactorList;
    }

    /**
     * cache Depends on factor only once
     * 
     * @param orgId
     */
    private static void cacheDependsOnFactors(Organisation orgId) {
	List<LookUp> lookUps = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.SLD, orgId);
	dependsOnFactorMap = new HashMap<>();
	for (LookUp lookUp : lookUps) {
	    dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
	}

    }

	@Override
	@Transactional
	public TbSrcptModesDetBean getDishonorCharges(Long deptId, Organisation org, TbSrcptModesDetBean chequeModeDto) {
		LOGGER.info(".......fetchDishonorCharge method start for getting dishonor charge....");
		WSRequestDTO dto = new WSRequestDTO();
		dto.setModelName("WaterRateMaster");
		WSResponseDTO response = RestClient.callBRMS(dto, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
		if (response != null && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			Map<Date, List<WaterRateMaster>> schWiseMap = new TreeMap<>();
			LookUp penaltyLookup = CommonMasterUtility.getHieLookupByLookupCode(
					MainetConstants.Property.CHEQUE_DISHONR_CHARGES, PrefixConstants.LookUpPrefix.TAC, 2,
					org.getOrgid());
			final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
					MainetConstants.Property.propPref.BILL, MainetConstants.Property.propPref.CAA, org);
			if(penaltyLookup!=null) {
				List<TbTaxMasEntity> taxList = taxMasService.findAllTaxesByChargeAppAtAndTaxSubCat(org.getOrgid(), deptId,
						taxAppAtBill.getLookUpId(), penaltyLookup.getLookUpId());
				if (CollectionUtils.isNotEmpty(taxList)) {
					List<Object> waterRateMasterList = RestClient.castResponse(response, WaterRateMaster.class, 0);
					WSRequestDTO request = new WSRequestDTO();
					WaterRateMaster waterRateModel = (WaterRateMaster) waterRateMasterList.get(0);
					List<WaterRateMaster> rateMasterList = new ArrayList<>();
					taxList.forEach(tax -> {
						setRateModelForDishonorcharge(org, taxAppAtBill, waterRateModel, rateMasterList, tax);
					});

					// schWiseMap.put(new Date(), rateMasterList);
					request.setDataModel(waterRateModel);
					// call rate master brms
					WaterRateMaster rateMaster = callWaterRateMasterBRMS(request);
					for (TbTaxMasEntity entity : taxList) {
						if (entity != null && rateMaster != null && entity.getTaxCode().equals(rateMaster.getTaxCode())) {
							if (entity != null && rateMaster.getFlatRate() > 0) {
								chequeModeDto.setRdSrChkDisChg(rateMaster.getFlatRate());
							} else {
								chequeModeDto.setRdSrChkDisChg(0d);
							}
						}
					}
				}
			}
			
		}
		LOGGER.info(".......fetchDishonorCharge method End for getting dishonor charge....");
		return chequeModeDto;

	}
	private void setRateModelForDishonorcharge(Organisation org, LookUp taxAppAt, WaterRateMaster waterRateModel,
			List<WaterRateMaster> rateMasterList, TbTaxMasEntity tax) {
		
		waterRateModel.setOrgId(org.getOrgid());
		waterRateModel.setRateStartDate(new Date().getTime());
		waterRateModel.setTaxCategory(
				CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory1(), org).getDescLangFirst());
		if (tax.getTaxCategory2() != null) {
			waterRateModel.setTaxSubCategory(
					CommonMasterUtility.getHierarchicalLookUp(tax.getTaxCategory2(), org).getDescLangFirst());
		}
		waterRateModel.setTaxCode(tax.getTaxCode());
		String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(tax.getTaxMethod()),
                MainetConstants.FlagE, waterRateModel.getOrgId());
		waterRateModel.setTaxType(taxType);
		waterRateModel.setChargeApplicableAt(taxAppAt.getDescLangFirst());
		waterRateModel.setDeptCode(MainetConstants.WATER_DEPARTMENT_CODE);
		waterRateModel.setFinancialYear(Utility.getCurrentFinancialYear());
		waterRateModel.setTaxId(tax.getTaxId());
		rateMasterList.add(waterRateModel);
	}

private WaterRateMaster callWaterRateMasterBRMS(WSRequestDTO request) {
	WaterRateMaster WaterRateMaster = null;
	WSResponseDTO dishonorCharges = RestClient.callBRMS(request,
			ServiceEndpoints.BRMSMappingURL.WATER_RATE_URL);
	if (dishonorCharges != null
			&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(dishonorCharges.getWsStatus())) {
		 try {
	            final List<Object> rate = castResponseToRate(dishonorCharges);
	            WaterRateMaster = (WaterRateMaster) rate.get(0);
	        } catch (final JsonParseException e) {
	            LOGGER.error("error in water/getWaterRate" + e);
	        } catch (final JsonMappingException e) {
	            LOGGER.error("error in water/getWaterRate" + e);
	        } catch (final IOException e) {
	            LOGGER.error("error in water/getWaterRate" + e);
	        }
		 
	}
    return WaterRateMaster;
}



@SuppressWarnings("unchecked")
private List<Object> castResponseToRate(final WSResponseDTO result)
        throws IOException, JsonParseException, JsonMappingException {
    Object dataModel = null;
    LinkedHashMap<Long, Object> responseMap = null;
    final List<Object> dataModelList = new ArrayList<>();
    responseMap = (LinkedHashMap<Long, Object>) result.getResponseObj();
    final String jsonString = new JSONObject(responseMap).toString();
    dataModel = new ObjectMapper().readValue(jsonString, WaterRateMaster.class);
    dataModelList.add(dataModel);
    return dataModelList;
}
}