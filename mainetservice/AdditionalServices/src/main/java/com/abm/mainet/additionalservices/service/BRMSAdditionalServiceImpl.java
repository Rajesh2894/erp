/**
 * 
 */
package com.abm.mainet.additionalservices.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.additionalservices.datamodel.AdditionalServicesModel;
import com.abm.mainet.common.constant.MainetConstants;
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

/**
 * @author cherupelli.srikanth
 *
 */
@Service
public class BRMSAdditionalServiceImpl implements BRMSAdditionalService{

	@Autowired
    private ServiceMasterService serviceMasterService;
	private static Map<Long, String> dependsOnFactorMap = null;
	
	private static final Logger LOGGER = Logger.getLogger(BRMSAdditionalServiceImpl.class);
	private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";
	
	@Override
	public WSResponseDTO getApplicableTaxes(WSRequestDTO wsRequestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms Advertising And Hoarding getApplicableTaxes execution start..");

        try {
            if (wsRequestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
            	AdditionalServicesModel adhRateMaster = (AdditionalServicesModel) CommonMasterUtility.castRequestToDataModel(wsRequestDTO,
                		AdditionalServicesModel.class);
                    responseDTO = populateOtherFieldsForServiceCharge(adhRateMaster, responseDTO);
                
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info(" brms Advertising And Hoarding getApplicableTaxes execution end..");
        return responseDTO;
    }

	@Override
	public WSResponseDTO getApplicableCharges(WSRequestDTO wsRequestDTO) {
        LOGGER.info("brms additional services getApplicableCharges execution start..");
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.ADDITIONAL_SERVICE_CHARGE_URI);
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
        final List<?> charges = RestClient.castResponse(responseDTO, AdditionalServicesModel.class);
        final List<AdditionalServicesModel> finalRateMaster = new ArrayList<>();
        for (final Object rate : charges) {
            final AdditionalServicesModel masterRate = (AdditionalServicesModel) rate;
            finalRateMaster.add(masterRate);
        }
        final ChargeDetailDTO chargedto = new ChargeDetailDTO();
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final AdditionalServicesModel rateCharge : finalRateMaster) {
			
			 // chargedto.setChargeCode(rateCharge.getTaxId());
			  chargedto.setChargeAmount(rateCharge.getFlatRate()); //Defect #124591 length
			  //and height set chargedto.setHeight(rateCharge.getHeight());
			  //chargedto.setLength(rateCharge.getLength());
			  //chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
			  //chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
			 
            detailDTOs.add(chargedto);
        }
        responseDTO.setResponseObj(detailDTOs);
        LOGGER.info("setServiceChargeDTO execution end..");
        return responseDTO;
    }
    
    // preparing applicable tax details from tax master

    private WSResponseDTO populateOtherFieldsForServiceCharge(AdditionalServicesModel additionalServiceRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {

        LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
        List<AdditionalServicesModel> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(additionalServiceRateMaster.getServiceCode(),
        		additionalServiceRateMaster.getOrgId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(additionalServiceRateMaster.getOrgId());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(additionalServiceRateMaster.getChargeApplicableAt()), organisation);
        if (serviceMas.getSmFeesSchedule().equals(1l)
                && (((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
                        || ((serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES)) && lookUp
                                .getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY)))) {
            List<TbTaxMasEntity> applicableCharges = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(serviceMas.getSmServiceId(),
                    		additionalServiceRateMaster.getOrgId(), Long.parseLong(additionalServiceRateMaster.getChargeApplicableAt()));
            listOfCharges = settingAllFields(applicableCharges, additionalServiceRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setFree(true);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }
        LOGGER.info("populateOtherFieldsForServiceCharge execution end..");
        return responseDTO;
    }
    
    private List<AdditionalServicesModel> settingAllFields(List<TbTaxMasEntity> applicableCharges, AdditionalServicesModel adhRateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        LOGGER.info("setting All Fields execution start..");
        List<AdditionalServicesModel> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
        	AdditionalServicesModel rateMasterAdh = (AdditionalServicesModel) adhRateMaster.clone();
            // ADF for dependsOnFactor
            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(entity.getTaxMethod()),
                    MainetConstants.FlagE, adhRateMaster.getOrgId());
            String chargeApplicableAt = CommonMasterUtility.getCPDDescription(entity.getTaxApplicable(),
                    MainetConstants.FlagE, entity.getOrgid());
            rateMasterAdh.setTaxType(taxType);
            rateMasterAdh.setTaxCode(entity.getTaxCode());
            rateMasterAdh.setChargeApplicableAt(chargeApplicableAt);
			/*
			 * rateMasterAdh.setChargeDescEng(entity.getTaxDesc());
			 * rateMasterAdh.setChargeDescReg(entity.getTaxDesc());
			 */
            settingTaxCategories(rateMasterAdh, entity, organisation);
			/*
			 * rateMasterAdh.setDependsOnFactorList(settingDependsOnFactor(entity.
			 * getListOfTbTaxDetMas(), organisation));
			 * rateMasterAdh.setTaxId(entity.getTaxId());
			 */
            list.add(rateMasterAdh);
        }
        LOGGER.info("settingAllFields execution end..");
        return list;
    }
    
    private AdditionalServicesModel settingTaxCategories(AdditionalServicesModel rateMasterAdh, TbTaxMasEntity entity,
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
    private void cacheDependsOnFactors(Organisation organisation) {
        List<LookUp> lookUps = CommonMasterUtility.getListLookup(MainetConstants.CommonMasterUi.ADF, organisation);
        dependsOnFactorMap = new HashMap<>();
        for (LookUp lookUp : lookUps) {
            dependsOnFactorMap.put(lookUp.getLookUpId(), lookUp.getLookUpCode());
        }
    }
}
