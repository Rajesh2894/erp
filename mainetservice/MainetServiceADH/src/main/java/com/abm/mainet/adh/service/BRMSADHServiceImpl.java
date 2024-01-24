package com.abm.mainet.adh.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxDetMasEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author vishwajeet.kumar
 * @since 24 October 2019
 */
@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.adh.service.IBRMSADHService")
@Api(value = "/brmsAdhService")
@Path("/brmsAdhService")
@Service
public class BRMSADHServiceImpl implements IBRMSADHService {

    @Autowired
    private ServiceMasterService serviceMasterService;
    private static Map<Long, String> dependsOnFactorMap = null;

    private static final Logger LOGGER = Logger.getLogger(BRMSADHServiceImpl.class);
    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";

    @POST
    @Path("/getPenaltyTaxes")
    @ApiOperation(value = "get penalty tax", notes = "get penalty tax", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getPenaltyTaxes(WSRequestDTO wsRequestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms Advertising And Hoarding getPenaltyTaxes execution start..");

        try {
            if (wsRequestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                ADHRateMaster adhRateMaster = (ADHRateMaster) CommonMasterUtility.castRequestToDataModel(wsRequestDTO,
                        ADHRateMaster.class);
                validateDataModel(adhRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(adhRateMaster, responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info(" brms Advertising And Hoarding getPenaltyTaxes execution end..");
        return responseDTO;
    }

    @POST
    @Path("/dependentparams")
    @ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableTaxes(WSRequestDTO wsRequestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms Advertising And Hoarding getApplicableTaxes execution start..");

        try {
            if (wsRequestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                ADHRateMaster adhRateMaster = (ADHRateMaster) CommonMasterUtility.castRequestToDataModel(wsRequestDTO,
                        ADHRateMaster.class);
                validateDataModel(adhRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(adhRateMaster, responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info(" brms Advertising And Hoarding getApplicableTaxes execution end..");
        return responseDTO;
    }

    // preparing applicable tax details from tax master

    private WSResponseDTO populateOtherFieldsForServiceCharge(ADHRateMaster adhRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {

        LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
        List<ADHRateMaster> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(adhRateMaster.getServiceCode(),
                adhRateMaster.getOrgId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(adhRateMaster.getOrgId());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(adhRateMaster.getChargeApplicableAt()), organisation);
        if (serviceMas.getSmFeesSchedule().equals(1l)
                && (((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
                        || ((serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES)) && lookUp
                                .getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY)))) {
            List<TbTaxMasEntity> applicableCharges = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(serviceMas.getSmServiceId(),
                            adhRateMaster.getOrgId(), Long.parseLong(adhRateMaster.getChargeApplicableAt()));
            listOfCharges = settingAllFields(applicableCharges, adhRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setFree(true);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }
        LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
        return responseDTO;
    }
    // preparing applicable tax details from tax master

    @SuppressWarnings("unused")
    private WSResponseDTO populateOtherFieldsForPenaltyCharge(ADHRateMaster adhRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {

        LOGGER.info("populateOtherFieldsForPenaltyCharge execution start..");
        List<ADHRateMaster> listOfCharges;
        Organisation organisation = new Organisation();
        organisation.setOrgid(adhRateMaster.getOrgId());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(adhRateMaster.getChargeApplicableAt()), organisation);

        // List<TbTaxMasEntity> applicableCharges = ApplicationContextProvider.getApplicationContext()
        // .getBean(TbTaxMasService.class).findAllActiveTaxList(adhRateMaster.getOrgId(), adhRateMaster.getDeptCode(),
        // Long.parseLong(adhRateMaster.getChargeApplicableAt()));
        // listOfCharges = settingAllFields(applicableCharges, adhRateMaster, organisation);
        // responseDTO.setResponseObj(listOfCharges);
        responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);

        LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
        return responseDTO;
    }

    /**
     * 
     * @param applicableCharges
     * @param adhRateMaster
     * @return
     * @throws CloneNotSupportedException
     */

    private List<ADHRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, ADHRateMaster adhRateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        LOGGER.info("setting All Fields execution start..");
        List<ADHRateMaster> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
            ADHRateMaster rateMasterAdh = (ADHRateMaster) adhRateMaster.clone();
            // ADF for dependsOnFactor
            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(entity.getTaxMethod()),
                    MainetConstants.FlagE, adhRateMaster.getOrgId());
            String chargeApplicableAt = CommonMasterUtility.getCPDDescription(entity.getTaxApplicable(),
                    MainetConstants.FlagE, entity.getOrgid());
            rateMasterAdh.setTaxType(taxType);
            rateMasterAdh.setTaxCode(entity.getTaxCode());
            rateMasterAdh.setChargeApplicableAt(chargeApplicableAt);
            rateMasterAdh.setChargeDescEng(entity.getTaxDesc());
            rateMasterAdh.setChargeDescReg(entity.getTaxDesc());
            settingTaxCategories(rateMasterAdh, entity, organisation);
            rateMasterAdh.setDependsOnFactorList(settingDependsOnFactor(entity.getListOfTbTaxDetMas(), organisation));
            rateMasterAdh.setTaxId(entity.getTaxId());
            list.add(rateMasterAdh);
        }
        LOGGER.info("settingAllFields execution end..");
        return list;
    }

    /**
     * 
     * @param rateMasterAdh
     * @param enity
     * @param organisation
     * @return
     */

    private ADHRateMaster settingTaxCategories(ADHRateMaster rateMasterAdh, TbTaxMasEntity entity,
            Organisation organisation) {
        if (entity.getTaxCategory1() != null) {
            rateMasterAdh.setTaxCategory(CommonMasterUtility
                    .getHierarchicalLookUp(entity.getTaxCategory1(), organisation).getDescLangFirst());
        }
        if (entity.getTaxCategory2() != null) {
            rateMasterAdh.setTaxSubCategory(CommonMasterUtility
                    .getHierarchicalLookUp(entity.getTaxCategory2(), organisation).getDescLangFirst());
        }
        return rateMasterAdh;

    }

    private List<String> settingDependsOnFactor(List<TbTaxDetMasEntity> listOfTbTaxDetMas, Organisation organisation) {
        if (dependsOnFactorMap == null) {
            cacheDependsOnFactors(organisation);
        }
        List<String> dependsOnFactorList = new ArrayList<>();
        if (listOfTbTaxDetMas != null) {
            for (TbTaxDetMasEntity entity : listOfTbTaxDetMas) {
                if (StringUtils.equalsIgnoreCase(entity.getStatus(), "A"))
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
    private void cacheDependsOnFactors(Organisation organisation) {
        List<LookUp> lookUps = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.ADF, organisation);
        dependsOnFactorMap = new HashMap<>();
        for (LookUp lookUp : lookUps) {
            dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
        }
    }

    private WSResponseDTO validateDataModel(ADHRateMaster adhRateMaster, WSResponseDTO responseDTO) {

        LOGGER.info("validateDataModel execution start..");
        StringBuilder builder = new StringBuilder();
        if (adhRateMaster.getServiceCode() == null || adhRateMaster.getServiceCode().isEmpty()) {
            builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
        }
        if (adhRateMaster.getOrgId() == 0l) {
            builder.append(ORG_ID_CANT_BE_ZERO).append(",");
        }
        if (adhRateMaster.getChargeApplicableAt() == null || adhRateMaster.getChargeApplicableAt().isEmpty()) {
            builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
        } else if (!StringUtils.isNumeric(adhRateMaster.getChargeApplicableAt())) {
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

    @POST
    @Path("/adhServicecharge")
    @ApiOperation(value = "get Advertising and Hoarding service charge", notes = "get Advertising and Hoarding service charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableCharges(
            @ApiParam(value = "get Advertising and Hoarding service charge", required = true) WSRequestDTO wsRequestDTO) {
        LOGGER.info("brms Advertiser And Hoarding getApplicableCharges execution start..");
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.ADH_SERVICE_CHARGE_URI);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                responseDTO = setServiceChargeDTO(responseDTO);
            } else {
                return responseDTO;
            }
        } catch (Exception ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        LOGGER.info("brms Advertiser And Hoarding getApplicableCharges execution End..");
        return responseDTO;
    }

    // set Service Charge DTO details based on rule response
    private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
        LOGGER.info("setServiceChargeDTO execution start..");
        final List<?> charges = RestClient.castResponse(responseDTO, ADHRateMaster.class);
        final List<ADHRateMaster> finalRateMaster = new ArrayList<>();
        for (final Object rate : charges) {
            final ADHRateMaster masterRate = (ADHRateMaster) rate;
            finalRateMaster.add(masterRate);
        }
        final ChargeDetailDTO chargedto = new ChargeDetailDTO();
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final ADHRateMaster rateCharge : finalRateMaster) {
            chargedto.setChargeCode(rateCharge.getTaxId());
            chargedto.setChargeAmount(rateCharge.getFlatRate());
            //Defect #124591 length and height set
            chargedto.setHeight(rateCharge.getHeight());
            chargedto.setLength(rateCharge.getLength());
            chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
            chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
            detailDTOs.add(chargedto);
        }
        responseDTO.setResponseObj(detailDTOs);
        LOGGER.info("setServiceChargeDTO execution end..");
        return responseDTO;
    }

    @Override
    public List<ADHRateMaster> getLoiChargesForADH(WSRequestDTO requestDTO, Long orgId, String serviceShortCode) {
        requestDTO.setModelName("ADHRateMaster");
        List<ADHRateMaster> adhLoiCharges = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        WSResponseDTO initializeADHModelResponse = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(initializeADHModelResponse.getWsStatus())) {
            List<Object> ADHRateMasterList = RestClient.castResponse(initializeADHModelResponse, ADHRateMaster.class);
            ADHRateMaster ADHRateMaster = (ADHRateMaster) ADHRateMasterList.get(0);
            ADHRateMaster.setOrgId(orgId);
            ADHRateMaster.setServiceCode(serviceShortCode);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.SCRUTINY,
                    PrefixConstants.LookUp.CHARGE_MASTER_CAA, organisation);
            ADHRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            
            
            requestDTO.setDataModel(ADHRateMaster);
            WSResponseDTO adhRateFieldsResponseDto = getApplicableTaxes(requestDTO);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateFieldsResponseDto.getWsStatus())) {
                List<?> rates = castResponse(adhRateFieldsResponseDto, ADHRateMaster.class);
                for (Object rate : rates) {
                    adhLoiCharges.add((ADHRateMaster) rate);
                }
            } else {
                LOGGER.error("Error in Initializing other fields for taxes");
            }
        } else {
            LOGGER.error("Error in Initializing model");
        }
        return adhLoiCharges;
    }

    public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {
        Object dataModel = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for (final Object object : list) {
                    ADHRateMaster responseMap = (ADHRateMaster) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
            }
        } catch (final IOException e) {
            LOGGER.error("Error Occurred during cast response object while BRMS call is success!", e);
        }
        return dataModelList;

    }
    
    
    @Override
    public List<ADHRateMaster> getLoiChargesForADHData(WSRequestDTO requestDTO, AdvertiserMasterDto masterDto, String serviceShortCode) {
        requestDTO.setModelName("ADHRateMaster");
        List<ADHRateMaster> adhLoiCharges = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(masterDto.getOrgId());
        WSResponseDTO initializeADHModelResponse = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(initializeADHModelResponse.getWsStatus())) {
            List<Object> ADHRateMasterList = RestClient.castResponse(initializeADHModelResponse, ADHRateMaster.class);
            ADHRateMaster ADHRateMaster = (ADHRateMaster) ADHRateMasterList.get(0);
            ADHRateMaster.setOrgId(masterDto.getOrgId());
            ADHRateMaster.setServiceCode(serviceShortCode);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                    PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.SCRUTINY,
                    PrefixConstants.LookUp.CHARGE_MASTER_CAA, organisation);
            ADHRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix( masterDto.getAgencyCategory(), "AGC", masterDto.getOrgId());
            
            ADHRateMaster.setAdvertiserCategory(lookUp.getLookUpDesc());
            
            requestDTO.setDataModel(ADHRateMaster);
            WSResponseDTO adhRateFieldsResponseDto = getApplicableTaxes(requestDTO);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateFieldsResponseDto.getWsStatus())) {
                List<?> rates = castResponse(adhRateFieldsResponseDto, ADHRateMaster.class);
                for (Object rate : rates) {
                    adhLoiCharges.add((ADHRateMaster) rate);
                }
            } else {
                LOGGER.error("Error in Initializing other fields for taxes");
            }
        } else {
            LOGGER.error("Error in Initializing model");
        }
        return adhLoiCharges;
    }
}
