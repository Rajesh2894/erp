/**
 * 
 */
package com.abm.mainet.swm.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.swm.datamodel.SWMRateMaster;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author sarojkumar.yadav
 *
 */
@WebService(endpointInterface = "com.abm.mainet.swm.service.IBRMSSWMService")
@Api(value = "/brmsswmservice")
@Path("/brmsswmservice")
@Service
public class BRMSSWMServiceImpl implements IBRMSSWMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRMSSWMServiceImpl.class);
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

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBRMSSWMService#getApplicableTaxes(com.abm.mainet. common.integration.dto.WSRequestDTO)
     */
    @POST
    @Path("/dependentparams")
    @Transactional(readOnly = true)
    @ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableTaxes(
            @ApiParam(value = "get dependent params", required = true) WSRequestDTO requestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();
        LOGGER.info("brms SWM getApplicableTaxes execution start..");
        try {
            if (requestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                SWMRateMaster swmRateMaster = (SWMRateMaster) CommonMasterUtility.castRequestToDataModel(requestDTO,
                        SWMRateMaster.class);
                validateDataModel(swmRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(swmRateMaster, responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        LOGGER.info("brms SWM getApplicableTaxes execution end..");
        return responseDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.swm.service.IBRMSSWMService#getApplicableCharges(com.abm. mainet.common.integration.dto.WSRequestDTO)
     */
    @POST
    @Path("/servicecharge")
    @Transactional(readOnly = true)
    @ApiOperation(value = "get SWM service charge", notes = "get SWM service charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableCharges(
            @ApiParam(value = "get SWM service charge", required = true) WSRequestDTO wsRequestDTO) {
        LOGGER.info("brms SWM getApplicableCharges execution start..");
        WSResponseDTO responseDTO = null;
        try {
            LOGGER.info("brms SWM request DTO is :" + wsRequestDTO.toString());
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.SWM_WASTE_COLLECTOR);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                responseDTO = setServiceChargeDTO(responseDTO);
            } else {
        	return responseDTO;
            }
        } catch (Exception ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        LOGGER.info("brms SWM getApplicableCharges execution End..");
        return responseDTO;
    }

    /**
     * validating WaterRateMaster model
     * 
     * @param waterRateMaster
     * @param responseDTO
     * @return
     */
    private WSResponseDTO validateDataModel(SWMRateMaster swmRateMaster, WSResponseDTO responseDTO) {
        LOGGER.info("validateDataModel execution start..");
        StringBuilder builder = new StringBuilder();
        if (swmRateMaster.getServiceCode() == null || swmRateMaster.getServiceCode().isEmpty()) {
            builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
        }
        if (swmRateMaster.getOrgId() == 0l) {
            builder.append(ORG_ID_CANT_BE_ZERO).append(",");
        }
        if (swmRateMaster.getChargeApplicableAt() == null || swmRateMaster.getChargeApplicableAt().isEmpty()) {
            builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
        } else if (!StringUtils.isNumeric(swmRateMaster.getChargeApplicableAt())) {
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

    public WSResponseDTO populateOtherFieldsForServiceCharge(SWMRateMaster swmRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {
        LOGGER.info("populateOtherFieldsForServiceCharge execution start..");
        List<SWMRateMaster> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(swmRateMaster.getServiceCode(),
                swmRateMaster.getOrgId());
        if (serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES)) {
            List<TbTaxMasEntity> applicableCharges = taxMasService.fetchAllApplicableServiceCharge(
                    serviceMas.getSmServiceId(), swmRateMaster.getOrgId(),
                    Long.parseLong(swmRateMaster.getChargeApplicableAt()));
            Organisation organisation = new Organisation();
            organisation.setOrgid(swmRateMaster.getOrgId());
            listOfCharges = settingAllFields(applicableCharges, swmRateMaster, organisation);
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
    private List<SWMRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, SWMRateMaster rateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        LOGGER.info("settingAllFields execution start..");
        List<SWMRateMaster> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
            SWMRateMaster swmRateMaster = (SWMRateMaster) rateMaster.clone();
            // SLD for dependsOnFactor
            String taxType = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.FSD,
                    rateMaster.getOrgId(), Long.parseLong(entity.getTaxMethod()));
            String chargeApplicableAt = CommonMasterUtility.findLookUpDesc(MainetConstants.CommonMasterUi.CAA,
                    entity.getOrgid(), entity.getTaxApplicable());
            swmRateMaster.setTaxType(taxType);
            swmRateMaster.setTaxCode(entity.getTaxCode());
            swmRateMaster.setChargeApplicableAt(chargeApplicableAt);
            swmRateMaster.setChargeDescEng(entity.getTaxDesc());
            swmRateMaster.setTaxId(entity.getTaxId());
            swmRateMaster.setTaxCategory(getCategoryDesc(entity.getTaxCategory1(), rateMaster.getOrgId()));
            swmRateMaster.setTaxSubCategory(getCategoryDesc(entity.getTaxCategory2(), rateMaster.getOrgId()));
            list.add(swmRateMaster);
        }
        LOGGER.info("settingAllFields execution end..");
        return list;
    }

    private String getCategoryDesc(final Long lookUpId, final Long org) {
        String subCategoryDesc = "";
        final LookUp lookUp = CommonMasterUtility.getHierarchicalLookUp(lookUpId, org);
        LOGGER.info("Look up values are " + lookUp.toString());
        subCategoryDesc = lookUp.getDescLangFirst();
        return subCategoryDesc;
    }

    private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
        LOGGER.info("setServiceChargeDTO execution start..");
        ChargeDetailDTO chargedto = null;
        final List<?> charges = RestClient.castResponse(responseDTO, SWMRateMaster.class);
        final List<SWMRateMaster> finalRateMaster = new ArrayList<>();
        for (final Object rate : charges) {
            final SWMRateMaster masterRate = (SWMRateMaster) rate;
            finalRateMaster.add(masterRate);
        }
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final SWMRateMaster rateCharge : finalRateMaster) {
            chargedto = new ChargeDetailDTO();
            chargedto.setChargeCode(rateCharge.getTaxId());
            chargedto.setChargeAmount(rateCharge.getFlatRate());
            chargedto.setChargeDescEng(rateCharge.getTaxSubCategory());
            chargedto.setChargeDescReg(rateCharge.getTaxSubCategory());
            detailDTOs.add(chargedto);
        }
        responseDTO.setResponseObj(detailDTOs);
        LOGGER.info("setServiceChargeDTO execution end..");
        return responseDTO;
    }
}
