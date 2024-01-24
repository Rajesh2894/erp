package com.abm.mainet.common.integration.brms.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.cfc.checklist.domain.TbDocumentGroupEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.repository.TbDocumentGroupRepository;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author hiren.poriya
 * @Since 02-Jun-2018
 */
@WebService(endpointInterface = "com.abm.mainet.common.integration.brms.service.BRMSCommonService")
@Api(value = "/brmscommonservice")
@Path("/brmscommonservice")
@Service
public class BRMSCommonServiceImpl implements BRMSCommonService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BRMSCommonService.class);
    private static final String INITIALIZE_MODEL_EXCEPTION = "Exception while initilize model";
    private static final String GET_CHECKLIST_EXCEPTION = "Exception while fatching checklist";
    private static final String SERVICE_CODE_CANT_BE_NULL = "service code can not be null or empty";
    private static final String ORG_ID_CANT_BE_ZERO = "orgId can not be zero(0)";
    private static final String DATA_MODEL_CANT_BE_NULL = "WsRequestDTO field named dataModel cannot be null.";
    private static final String LOOKUP_NOT_FOUND_FOR_APL = "LookUps not found for Prefix APL for orgId=";

    @Autowired
    private TbDocumentGroupRepository documentGroupRepository;

    @Resource
    private ServiceMasterRepository serviceMasterRespository;

    @Autowired
    private TbOrganisationService organisationService;

    /**
     * this service is used for initialize model for particular model.
     */

    @POST
    @Path("/initializeModel")
    @Override
    @ApiOperation(value = "Initialize model with default values", notes = "initialize model with default value", response = WSResponseDTO.class)
    public WSResponseDTO initializeModel(
            @ApiParam(value = "Initialize model", required = true) WSRequestDTO requestDTO) {
        LOGGER.info("brms common service initialize model execution start..");
        WSResponseDTO responseDTO = null;
        try {
            responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
        } catch (Exception ex) {
            throw new FrameworkException(INITIALIZE_MODEL_EXCEPTION, ex);
        }
        LOGGER.info("brms common service initialize model execution End..");
        return responseDTO;
    }

    @POST
    @Path("/checkList")
    @Override
    @ApiOperation(value = "fetching checklist ", notes = "fetching checklist ", response = WSResponseDTO.class)
    public WSResponseDTO getChecklist(
            @ApiParam(value = "fetching checklist ", required = true) WSRequestDTO requestDTO) {
        LOGGER.info("brms common service get checklist execution start..");
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            if (isChecklistApplicableForRequestOrg(requestDTO, responseDTO)) {

                // if checklist define at UDHD then set super organization OrgId as told by
                // rajesh sir
                CheckListModel checkList = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
                        CheckListModel.class);
                Organisation org = new Organisation();
                org.setOrgid(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
                LookUp lookup = CommonMasterUtility.getDefaultValue(PrefixConstants.LookUpPrefix.CHK, org);
                if (lookup.getLookUpCode() != null && lookup.getLookUpCode().equals(MainetConstants.FlagY)) {
                    LOGGER.info("Checklist is define at UDHD level");
                    checkList.setOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
                    requestDTO.setDataModel(checkList);
                } else {
                    LOGGER.info("Checklist is define at organization level");
                }
                responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
                    if (StringUtils.isNotBlank(checkList.getFactor3()) && StringUtils.equals(checkList.getFactor3(), "Y")) {
                        StringBuilder documentGroupFactor = new StringBuilder();
                        documentGroupFactor.append("D");
                        documentGroupFactor.append(responseDTO.getDocumentGroup());
                        responseDTO.setDocumentGroup(documentGroupFactor.toString());
                    }
                    CheckListModel checkListModel = (CheckListModel) CommonMasterUtility
                            .castRequestToDataModel(requestDTO, CheckListModel.class);
                    preparedDocuments(responseDTO, checkListModel);
                } else if (MainetConstants.WebServiceStatus.FAIL.equalsIgnoreCase(responseDTO.getWsStatus())) {
                    throw new FrameworkException(
                            "Exception while fetch checklist from BRMS : " + responseDTO.getErrorMessage());
                }
            } else {
                responseDTO.setWsStatus(MainetConstants.CommonConstants.NA);
                LOGGER.info("Checklist is not applicable..!");
            }
        } catch (Exception ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(GET_CHECKLIST_EXCEPTION);
            throw new FrameworkException(GET_CHECKLIST_EXCEPTION, ex);
        }
        LOGGER.info("brms common service get checklist execution End..");
        return responseDTO;
    }

    // for MRM mobile
    @POST
    @Path("/checkListMRG")
    @Override
    @ApiOperation(value = "fetching checklist ", notes = "fetching checklist ", response = WSResponseDTO.class)
    public WSResponseDTO getChecklistMRG(
            @ApiParam(value = "fetching checklist ", required = true) WSRequestDTO requestDTO) {
        LOGGER.info("brms common service get checklist execution start..");
        WSResponseDTO responseDTO = new WSResponseDTO();
        try {
            if (isChecklistApplicableForRequestOrg(requestDTO, responseDTO)) {
                List<DocumentDetailsVO> finalCheckList = new ArrayList<>();
                // if checklist define at UDHD then set super organization OrgId as told by
                // rajesh sir
                CheckListModel checkList = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
                        CheckListModel.class);
                Organisation org = new Organisation();
                org.setOrgid(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
                LookUp lookup = CommonMasterUtility.getDefaultValue(PrefixConstants.LookUpPrefix.CHK, org);
                String usageSubType1 = checkList.getUsageSubtype1();
                String usageSubType2 = checkList.getUsageSubtype2();
                checkList.setUsageSubtype1("U");
                checkList.setUsageSubtype2("NA");
                requestDTO.setDataModel(checkList);
                if (lookup.getLookUpCode() != null && lookup.getLookUpCode().equals(MainetConstants.FlagY)) {
                    LOGGER.info("Checklist is define at UDHD level");
                    checkList.setOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
                    requestDTO.setDataModel(checkList);
                } else {
                    LOGGER.info("Checklist is define at organization level");
                }
                responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL);
                if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
                    if (StringUtils.isNotBlank(checkList.getFactor3()) && StringUtils.equals(checkList.getFactor3(), "Y")) {
                        StringBuilder documentGroupFactor = new StringBuilder();
                        documentGroupFactor.append("D");
                        documentGroupFactor.append(responseDTO.getDocumentGroup());
                        responseDTO.setDocumentGroup(documentGroupFactor.toString());
                    }
                    CheckListModel checkListModel = (CheckListModel) CommonMasterUtility
                            .castRequestToDataModel(requestDTO, CheckListModel.class);
                    preparedDocuments(responseDTO, checkListModel);
                    finalCheckList = (List<DocumentDetailsVO>) responseDTO.getResponseObj();

                    // hit again checklist for conditional wise
                    if (StringUtils.isNotEmpty(usageSubType1) && !MainetConstants.FlagU.equals(usageSubType1)) {
                            checkList.setUsageSubtype1(usageSubType1);
                            checkList.setUsageSubtype2("NA");
                            requestDTO.setDataModel(checkList);
                            responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL);
                            // call to setChecklistData
                            finalCheckList = setChecklistData(responseDTO, checkListModel, requestDTO, finalCheckList);
                    }
                    if (StringUtils.isNotEmpty(usageSubType2) && !MainetConstants.FlagU.equals(usageSubType2)) {
                        checkList.setUsageSubtype1(usageSubType2);
                        checkList.setUsageSubtype2("NA");
                        requestDTO.setDataModel(checkList);
                        responseDTO = RestClient.callBRMS(requestDTO, ServiceEndpoints.BRMSMappingURL.CHECKLIST_URL);
                        // call to setChecklistData
                        finalCheckList = setChecklistData(responseDTO, checkListModel, requestDTO, finalCheckList);

                    }
                    responseDTO.setResponseObj(finalCheckList);

                } else if (MainetConstants.WebServiceStatus.FAIL.equalsIgnoreCase(responseDTO.getWsStatus())) {
                    throw new FrameworkException(
                            "Exception while fetch checklist from BRMS : " + responseDTO.getErrorMessage());
                }
            } else {
                responseDTO.setWsStatus(MainetConstants.CommonConstants.NA);
                LOGGER.info("Checklist is not applicable..!");
            }
        } catch (Exception ex) {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(GET_CHECKLIST_EXCEPTION);
            throw new FrameworkException(GET_CHECKLIST_EXCEPTION, ex);
        }
        LOGGER.info("brms common service get checklist execution End..");
        return responseDTO;
    }

    List<DocumentDetailsVO> setChecklistData(WSResponseDTO responseDTO, CheckListModel checkList, WSRequestDTO requestDTO,
            List<DocumentDetailsVO> finalCheckList) {
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
            if (StringUtils.isNotBlank(checkList.getFactor3()) && StringUtils.equals(checkList.getFactor3(), "Y")) {
                StringBuilder documentGroupFactor = new StringBuilder();
                documentGroupFactor.append("D");
                documentGroupFactor.append(responseDTO.getDocumentGroup());
                responseDTO.setDocumentGroup(documentGroupFactor.toString());
            }
            CheckListModel checkListModel = (CheckListModel) CommonMasterUtility
                    .castRequestToDataModel(requestDTO, CheckListModel.class);
            preparedDocuments(responseDTO, checkListModel);
            finalCheckList.addAll((Collection<? extends DocumentDetailsVO>) responseDTO.getResponseObj());

        } else if (MainetConstants.WebServiceStatus.FAIL.equalsIgnoreCase(responseDTO.getWsStatus())) {
            throw new FrameworkException(
                    "Exception while fetch checklist from BRMS : " + responseDTO.getErrorMessage());
        }
        return finalCheckList;
    }

    // check is checklist is applicable or not for particular service in service
    // master
    private boolean isChecklistApplicableForRequestOrg(WSRequestDTO requestDTO, WSResponseDTO responseDTO) {
        boolean isApplicable = false;
        if (requestDTO.getDataModel() != null) {
            CheckListModel checkListModel = (CheckListModel) CommonMasterUtility.castRequestToDataModel(requestDTO,
                    CheckListModel.class);
            String errorMsg = validateChecklistModel(checkListModel);
            if (errorMsg.isEmpty()) {
                isApplicable = isChecklistApplicable(checkListModel.getServiceCode(), checkListModel.getOrgId());
            } else {
                responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMessage(errorMsg);
                throw new FrameworkException("Invalid isChecklistApplicable " + errorMsg);
            }
        } else {
            responseDTO.setWsStatus(MainetConstants.WebServiceStatus.FAIL);
            responseDTO.setErrorMessage(DATA_MODEL_CANT_BE_NULL);
            throw new NullPointerException(DATA_MODEL_CANT_BE_NULL);
        }
        return isApplicable;
    }

    // validate checklist model data
    private String validateChecklistModel(CheckListModel checkListModel) {
        String errorMsg = StringUtils.EMPTY;
        if (checkListModel.getServiceCode() == null || checkListModel.getServiceCode().isEmpty()) {
            errorMsg = SERVICE_CODE_CANT_BE_NULL + ",";
        }
        if (checkListModel.getOrgId() == 0l) {
            errorMsg = ORG_ID_CANT_BE_ZERO;
        }
        return errorMsg;

    }

    // get documents details by document group
    private WSResponseDTO preparedDocuments(WSResponseDTO response, CheckListModel checkListModel) {
        LOGGER.info("preparing document from document group master execution start..");
        if (checkListModel != null) {
            List<String> docGroupList = Pattern.compile(MainetConstants.operator.COMMA)
                    .splitAsStream(response.getDocumentGroup()).collect(Collectors.toList());
            List<TbDocumentGroupEntity> docList = documentGroupRepository.fetchCheckListByDocumentGroup(docGroupList,
                    checkListModel.getOrgId());
            if (docList == null || docList.isEmpty()) {
                throw new FrameworkException("No Document found for serviceCode= " + checkListModel.getServiceCode()
                        + " and orgId= " + checkListModel.getOrgId() + " and documntGroup=" + docGroupList);
            } else {
                response.setResponseObj(prepareCheckList(docList, checkListModel.getOrgId()));
            }
        } else {
            throw new FrameworkException("Exception while casting checklist model after checklist response");
        }

        return response;
    }

    // prepare document details
    private List<DocumentDetailsVO> prepareCheckList(final List<TbDocumentGroupEntity> checkList, long orgId) {
        LOGGER.info("preparing checklist details execution start..");
        Organisation org = new Organisation();
        org.setOrgid(orgId);
        List<DocumentDetailsVO> finalCheckList = null;
        DocumentDetailsVO doc = null;
        if (checkList != null && !checkList.isEmpty()) {
            finalCheckList = new ArrayList<>();
            for (final TbDocumentGroupEntity checklistData : checkList) {
                doc = new DocumentDetailsVO();
                doc.setDocumentId(checklistData.getDgId());
                doc.setDocumentSerialNo(checklistData.getDocSrNo());
                doc.setDoc_DESC_ENGL(checklistData.getDocName());
                // Defect #129055
                doc.setDoc_DESC_Mar(checklistData.getDocNameReg());
                if (checklistData.getCcmValueset() != null) {
                    doc.setCheckkMANDATORY(CommonMasterUtility
                            .getNonHierarchicalLookUpObject(checklistData.getCcmValueset(), org).getLookUpCode());
                }
                // D#123049
                if (checklistData.getDocSize() != null) {
                    // convert in bytes currently in document master size store in MB
                    doc.setDocSize((checklistData.getDocSize() * 1024 * 1024));// in bytes

                }
                if (StringUtils.isNotEmpty(checklistData.getPrefixName())) {
                    doc.setDocDes(checklistData.getPrefixName());
                }

                finalCheckList.add(doc);
            }
        }
        return finalCheckList;
    }

    public boolean isChecklistApplicable(String serviceShortCode, long orgId) {
        Long smCheckListVerifyId = serviceMasterRespository.isCheckListApplicable(serviceShortCode, orgId);
        if (smCheckListVerifyId == null || smCheckListVerifyId == 0) {
            throw new NullPointerException("No record found from TB_SERVICES_MST for serviceShortCode="
                    + serviceShortCode + "and orgId=" + orgId + "OR sm_chklst_verify column found null");
        }
        String flag = StringUtils.EMPTY;

        // get APL prefix from default organization
        Organisation defaultOrg = organisationService.findDefaultOrganisation();
        List<LookUp> lookUps = CommonMasterUtility.getLookUps("APL", defaultOrg);
        if (lookUps == null || lookUps.isEmpty()) {
            throw new NullPointerException(
                    LOOKUP_NOT_FOUND_FOR_APL + defaultOrg.getONlsOrgname() + ", orgId:" + defaultOrg.getOrgid());
        }
        for (LookUp lookUp : lookUps) {
            if (lookUp.getLookUpId() == smCheckListVerifyId) {
                flag = lookUp.getLookUpCode();
                break;
            }
        }
        if (flag.isEmpty()) {
            throw new IllegalArgumentException(
                    "conflicts! Prefix APL ids does'nt match to id found from TB_SERVICES_MST");
        }

        return MainetConstants.APPLICABLE.equalsIgnoreCase(flag) ? true : false;
    }

}
