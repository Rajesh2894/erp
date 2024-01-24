package com.abm.mainet.mrm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
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
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.mrm.dto.MRMRateMaster;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Produces("application/json")
@WebService(endpointInterface = "com.abm.mainet.mrm.service.IBRMSMRMService")
@Api(value = "/brmsMrmService")
@Path("/brmsMrmService")
@Service

public class BRMSMRMServiceImpl implements IBRMSMRMService {

    private static final Logger LOGGER = Logger.getLogger(BRMSMRMServiceImpl.class);
    private static final String DATAMODEL_FIELD_CANT_BE_NULL = "dataModel field within WSRequestDTO dto cannot be null";
    private static final String SERVICE_ID_CANT_BE_ZERO = "ServiceCode cannot be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId cannot be zero(0)";
    private static final String CHARGE_APPLICABLE_AT_CANT_BE_ZERO = "chargeApplicableAt cannot be empty or zero(0)";
    private static final String CHARGE_APPLICABLE_AT_MUST_BE_NUMERIC = "chargeApplicableAt must be numeric";
    private static final String UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE = "Unable to process request for serrvice charge!";
    private static final String UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS = "Unable to process request to initialize other fields of dataModel";

    @Autowired
    private ServiceMasterService serviceMasterService;

    @POST
    @Path("/dependentparams")
    @ApiOperation(value = "get dependent paramaters", notes = "get dependent paramaters", response = WSResponseDTO.class)
    public WSResponseDTO getApplicableTaxes(WSRequestDTO wsRequestDTO) {
        WSResponseDTO responseDTO = new WSResponseDTO();

        try {
            if (wsRequestDTO.getDataModel() == null) {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(DATAMODEL_FIELD_CANT_BE_NULL);
            } else {
                MRMRateMaster mrmRateMaster = (MRMRateMaster) CommonMasterUtility.castRequestToDataModel(wsRequestDTO,
                        MRMRateMaster.class);
                validateDataModel(mrmRateMaster, responseDTO);
                if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                    responseDTO = populateOtherFieldsForServiceCharge(mrmRateMaster, responseDTO);
                }
            }
        } catch (CloneNotSupportedException | FrameworkException ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS);
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_TO_DEPEND_PARAMS, ex);
        }
        return responseDTO;
    }

    // preparing applicable tax details from tax master

    private WSResponseDTO populateOtherFieldsForServiceCharge(MRMRateMaster mrmRateMaster, WSResponseDTO responseDTO)
            throws CloneNotSupportedException {

        List<MRMRateMaster> listOfCharges;
        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(mrmRateMaster.getServiceCode(),
                mrmRateMaster.getOrgId());
        Organisation organisation = new Organisation();
        organisation.setOrgid(mrmRateMaster.getOrgId());
        LookUp lookUp = CommonMasterUtility
                .getNonHierarchicalLookUpObject(Long.valueOf(mrmRateMaster.getChargeApplicableAt()), organisation);
        if (serviceMas.getSmFeesSchedule().equals(1l)
                && (((serviceMas.getSmAppliChargeFlag().equals(MainetConstants.Common_Constant.YES))
                        && lookUp.getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.APPLICATION))
                        || ((serviceMas.getSmScrutinyChargeFlag().equals(MainetConstants.Common_Constant.YES)) && lookUp
                                .getLookUpCode().equalsIgnoreCase(MainetConstants.ChargeApplicableAt.SCRUTINY)))) {
            List<TbTaxMasEntity> applicableCharges = ApplicationContextProvider.getApplicationContext()
                    .getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(serviceMas.getSmServiceId(),
                            mrmRateMaster.getOrgId(), Long.parseLong(mrmRateMaster.getChargeApplicableAt()));
            listOfCharges = settingAllFields(applicableCharges, mrmRateMaster, organisation);
            responseDTO.setResponseObj(listOfCharges);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        } else {
            responseDTO.setFree(true);
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.SUCCESS);
        }
        return responseDTO;
    }

    private List<MRMRateMaster> settingAllFields(List<TbTaxMasEntity> applicableCharges, MRMRateMaster mrmRateMaster,
            Organisation organisation) throws CloneNotSupportedException {
        List<MRMRateMaster> list = new ArrayList<>();
        for (TbTaxMasEntity entity : applicableCharges) {
            MRMRateMaster rateMasterMrm = (MRMRateMaster) mrmRateMaster.clone();
            String taxType = CommonMasterUtility.getCPDDescription(Long.parseLong(entity.getTaxMethod()),
                    MainetConstants.FlagE, mrmRateMaster.getOrgId());
            String chargeApplicableAt = CommonMasterUtility.getCPDDescription(entity.getTaxApplicable(),
                    MainetConstants.FlagE, entity.getOrgid());
            rateMasterMrm.setTaxType(taxType);
            rateMasterMrm.setTaxCode(entity.getTaxCode());
            rateMasterMrm.setRateStartDate(new Date().getTime());
            rateMasterMrm.setChargeApplicableAt(chargeApplicableAt);
            rateMasterMrm.setChargeDescEng(entity.getTaxDesc());
            rateMasterMrm.setChargeDescReg(entity.getTaxDesc());
            settingTaxCategories(rateMasterMrm, entity, organisation);
            rateMasterMrm.setTaxId(entity.getTaxId());
            list.add(rateMasterMrm);
        }
        return list;
    }

    private MRMRateMaster settingTaxCategories(MRMRateMaster rateMasterMRM, TbTaxMasEntity entity,
            Organisation organisation) {
        if (entity.getTaxCategory1() != null) {
            rateMasterMRM.setTaxCategory(CommonMasterUtility
                    .getHierarchicalLookUp(entity.getTaxCategory1(), organisation).getDescLangFirst());
        }
        if (entity.getTaxCategory2() != null) {
            rateMasterMRM.setTaxSubCategory(CommonMasterUtility
                    .getHierarchicalLookUp(entity.getTaxCategory2(), organisation).getDescLangFirst());
        }
        return rateMasterMRM;

    }

    private WSResponseDTO validateDataModel(MRMRateMaster mrmRateMaster, WSResponseDTO responseDTO) {

        StringBuilder builder = new StringBuilder();
        if (mrmRateMaster.getServiceCode() == null || mrmRateMaster.getServiceCode().isEmpty()) {
            builder.append(SERVICE_ID_CANT_BE_ZERO).append(",");
        }
        if (mrmRateMaster.getOrgId() == 0l) {
            builder.append(ORG_ID_CANT_BE_ZERO).append(",");
        }
        if (mrmRateMaster.getChargeApplicableAt() == null || mrmRateMaster.getChargeApplicableAt().isEmpty()) {
            builder.append(CHARGE_APPLICABLE_AT_CANT_BE_ZERO).append(",");
        } else if (!StringUtils.isNumeric(mrmRateMaster.getChargeApplicableAt())) {
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
    @Path("/mrmServicecharge")
    @ApiOperation(value = "get Marriage service charge", notes = "get Marriage service charge", response = WSResponseDTO.class)
    @Override
    public WSResponseDTO getApplicableCharges(WSRequestDTO wsRequestDTO) {
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(wsRequestDTO, ServiceEndpoints.BRMSMappingURL.MRM_SERVICE_CHARGE_URI);
            if (responseDTO.getWsStatus().equals(MainetConstants.WebServiceStatus.SUCCESS)) {
                responseDTO = setServiceChargeDTO(responseDTO);
            } else {
                return responseDTO;
            }
        } catch (Exception ex) {
            throw new FrameworkException(UNABLE_TO_PROCESS_REQUEST_FOR_SERVICE_CHARGE, ex);
        }
        return responseDTO;
    }

    // set Service Charge DTO details based on rule response
    private WSResponseDTO setServiceChargeDTO(WSResponseDTO responseDTO) {
        final List<?> charges = RestClient.castResponse(responseDTO, MRMRateMaster.class);
        final List<MRMRateMaster> finalRateMaster = new ArrayList<>();
        for (final Object rate : charges) {
            final MRMRateMaster masterRate = (MRMRateMaster) rate;
            finalRateMaster.add(masterRate);
        }
        final ChargeDetailDTO chargedto = new ChargeDetailDTO();
        final List<ChargeDetailDTO> detailDTOs = new ArrayList<>();
        for (final MRMRateMaster rateCharge : finalRateMaster) {
            chargedto.setChargeCode(rateCharge.getTaxId());
            chargedto.setChargeAmount(rateCharge.getSlabRate1());
            chargedto.setChargeDescEng(rateCharge.getChargeDescEng());
            chargedto.setChargeDescReg(rateCharge.getChargeDescReg());
            detailDTOs.add(chargedto);
        }
        responseDTO.setResponseObj(detailDTOs);
        return responseDTO;
    }

    public static List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz) {
        Object dataModel = null;
        final List<Object> dataModelList = new ArrayList<>();
        try {
            if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
                final List<?> list = (List<?>) response.getResponseObj();
                for (final Object object : list) {
                    MRMRateMaster responseMap = (MRMRateMaster) object;
                    final String jsonString = new JSONObject(responseMap).toString();
                    dataModel = new ObjectMapper().readValue(jsonString, clazz);
                    dataModelList.add(dataModel);
                }
            }
        } catch (final IOException e) {
        }
        return dataModelList;

    }

    @Override
    public List<MRMRateMaster> getLoiChargesForMRM(WSRequestDTO requestDTO, Long orgId, String serviceShortCode) {
        requestDTO.setModelName("MRMRateMaster");
        List<MRMRateMaster> mrmLoiCharges = new ArrayList<>();
        Organisation organisation = new Organisation();
        organisation.setOrgid(orgId);
        WSResponseDTO initializeMRMModelResponse = RestClient.callBRMS(requestDTO,
                ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(initializeMRMModelResponse.getWsStatus())) {
            List<Object> MRMRateMasterList = RestClient.castResponse(initializeMRMModelResponse, MRMRateMaster.class);
            MRMRateMaster MRMRateMaster = (MRMRateMaster) MRMRateMasterList.get(0);
            MRMRateMaster.setOrgId(orgId);
            MRMRateMaster.setServiceCode(serviceShortCode);
            LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp("SCL",
                    PrefixConstants.LookUp.CHARGE_MASTER_CAA, organisation);
            MRMRateMaster.setChargeApplicableAt(Long.toString(chargeApplicableAt.getLookUpId()));
            requestDTO.setDataModel(MRMRateMaster);
            WSResponseDTO mrmRateFieldsResponseDto = getApplicableTaxes(requestDTO);
            if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(mrmRateFieldsResponseDto.getWsStatus())) {
                List<?> rates = castResponse(mrmRateFieldsResponseDto, MRMRateMaster.class);
                for (Object rate : rates) {
                    mrmLoiCharges.add((MRMRateMaster) rate);
                }
            } else {
                LOGGER.error("Error in Initializing other fields for taxes");
            }
        } else {
            LOGGER.error("Error in Initializing model");
        }
        return mrmLoiCharges;
    }

}
