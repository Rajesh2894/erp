package com.abm.mainet.common.checklist.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.checklist.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.checklist.dto.ChecklistServiceDTO;
import com.abm.mainet.common.checklist.dto.DocumentResubmissionRequestDTO;
import com.abm.mainet.common.checklist.dto.DocumentResubmissionResponseDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.star.uno.RuntimeException;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ChecklistResubmissionModel extends AbstractFormModel {

	
	@Resource
	IFileUploadService fileUpload;
    
    @Value("${upload.physicalPath}")
    private String filenetPath;
    
    private static final long serialVersionUID = -1707159826199117144L;

    private Long applicationId;
    private Date applicationDate;
    private ChecklistServiceDTO applicationDetails = new ChecklistServiceDTO();
    private List<CFCAttachmentsDTO> attachmentList = new ArrayList<>(0);
    private List<DocumentDetailsVO> checkList = new LinkedList<>();
    private boolean resubmitedApplication;
    private CFCAttachment attachment;

    public List<CFCAttachmentsDTO> querySearchResults() {

        attachmentList.clear();
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        if (applicationId != null) {
            final DocumentResubmissionRequestDTO request = new DocumentResubmissionRequestDTO();
            request.setApplicationId(applicationId);
            request.setApplicationStatus(MainetConstants.AuthStatus.ONHOLD);
            request.setOrgId(orgId);

            @SuppressWarnings("unchecked")
            final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(request,
                    "/DocumentResubmission/searchApplicantDetails");

            final String response = new JSONObject(responseVo).toString();

            DocumentResubmissionResponseDTO responseDTO = null;
            DocumentDetailsVO attachments = new DocumentDetailsVO();
            try {
                responseDTO = new ObjectMapper().readValue(response, DocumentResubmissionResponseDTO.class);
            } catch (final IOException e) {
                throw new RuntimeException(MainetConstants.ERROR_OCCURED, e);
            }

            applicationDetails = responseDTO.getChecklistDetail();
            if (null != applicationDetails.getApmChklstVrfyFlag()) {
                if (applicationDetails.getApmChklstVrfyFlag().equals(MainetConstants.AuthStatus.ONHOLD)) {

                    attachmentList = responseDTO.getAttachmentList();
                    attachmentList.forEach(doc -> {
                        attachments.setDocumentName(doc.getAttFname());
                		attachments.setDocumentByteCode(doc.getDocument());
                		String existingPath=null;
                        if (MainetConstants.FILE_PATH_SEPARATOR.equals("\\")) {
                            existingPath = doc.getAttPath().replace('/', '\\');
                        } else {
                            existingPath = doc.getAttPath().replace('\\', '/');     
                        }
                		final boolean fileSavedSucessfully = fileUpload.convertAndSaveFile(attachments, filenetPath,
                				existingPath,
                				doc.getAttFname());
                        });
                } else {
                    addValidationError(getAppSession().getMessage("cfc.docresubmit.error", new Object[] {
                            applicationId.toString(), getApprovalStatus(applicationDetails.getApmChklstVrfyFlag()) }));
                }

            } else {

                if (attachmentList.isEmpty()) {

                    addValidationError(getAppSession().getMessage("challan.noRecord"));
                }
            }
        } else {

            addValidationError(getAppSession().getMessage("cfc.applicationid.valid"));
        }

        return attachmentList;
    }

    @Override
    public boolean saveForm() {

        FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());

        if (hasValidationErrors()) {
            return false;
        }

        final DocumentResubmissionRequestDTO requestDto = new DocumentResubmissionRequestDTO();
        requestDto.setApplicationId(applicationId);
        requestDto.setApplicationStatus(MainetConstants.AuthStatus.REJECTED);
        requestDto.setServiceId(attachmentList.get(0).getServiceId());
        requestDto.setDeptId(attachmentList.get(0).getDept());
        requestDto.setUserId(attachmentList.get(0).getUserId());
           // for setting orgid from which organisation application initiate due to issue
     		// in Doon For orgId dependancy D#132847
     		if (applicationDetails != null && applicationDetails.getOrganisationId() != null) {
     			requestDto.setOrgId(applicationDetails.getOrganisationId());
     		} else {
     			requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
     		}
        requestDto.setLangId((long) UserSession.getCurrent().getLanguageId());
        setCheckList(prepareCheckList(attachmentList));
        requestDto.setDocumentList(getFileUploadListForResubmission(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));

        @SuppressWarnings("unchecked")
        final LinkedHashMap<Long, Object> responseVo = (LinkedHashMap<Long, Object>) JersyCall.callRestTemplateClient(requestDto,
                "/DocumentResubmission/saveDocumentDetails");
        final String response = new JSONObject(responseVo).toString();
        DocumentResubmissionResponseDTO responseDTO = null;
        try {
            responseDTO = new ObjectMapper().readValue(response, DocumentResubmissionResponseDTO.class);
        } catch (final IOException e) {
            throw new RuntimeException("Error Occured during saveForm ", e);
        }

        if (responseDTO.getStatus().equalsIgnoreCase(MainetConstants.Advertise.SUCCESS)) {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("cfc.doc.resubmit"));
            return true;

        } else {

            return false;
        }

    }

    public List<LookUp> getDocumentsList() {
        final List<LookUp> documentDetailsList = new ArrayList<>(0);

        LookUp lookUp = null;
        for (final CFCAttachmentsDTO temp : attachmentList) {
            lookUp = new LookUp(temp.getAttId(), temp.getAttPath());

            lookUp.setOtherField(temp.getMandatory());
            lookUp.setDescLangFirst(temp.getClmDescEngl());
            lookUp.setDescLangSecond(temp.getClmDesc());
            lookUp.setLookUpId(temp.getClmId());
            lookUp.setLookUpCode(temp.getAttFname());
            lookUp.setLookUpType(temp.getClmStatus());
            lookUp.setLookUpParentId(temp.getAttId());
            lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
            lookUp.setDescLangSecond(temp.getAttPath());
            lookUp.setExtraStringField1(temp.getClmRemark());
            lookUp.setDocDescription(temp.getDocDescription());
            documentDetailsList.add(lookUp);
        }
        return documentDetailsList;
    }

    private String getApprovalStatus(final String statusFlag) {
        String status = null;
        switch (statusFlag) {
        case MainetConstants.AUTH:
            status = getAppSession().getMessage("cfc.status.approve");
            break;
        case MainetConstants.UNAUTH:
            status = getAppSession().getMessage("cfc.status.reject");
            break;
        case MainetConstants.AuthStatus.ONHOLD:
            status = getAppSession().getMessage("cfc.status.hold");
            break;
        case MainetConstants.AuthStatus.REJECTED:
            status = getAppSession().getMessage("cfc.status.resubmit");
            break;
        default:
            status = getAppSession().getMessage("cfc.status.pending");
            break;
        }
        return status;
    }

    public String getApplicationService() {
        final int langId = UserSession.getCurrent().getLanguageId();
        String service = MainetConstants.BLANK;
        service = applicationDetails.getServiceName();
        if (langId == MainetConstants.MARATHI) {
            if (null != applicationDetails.getServiceNameMar()) {
                service = applicationDetails.getServiceNameMar();
            }
        }

        return service;

    }

    private List<DocumentDetailsVO> prepareCheckList(final List<CFCAttachmentsDTO> checkList) {

        List<DocumentDetailsVO> finalCheckList = null;
        DocumentDetailsVO doc = null;
        if ((checkList != null) && !checkList.isEmpty()) {
            finalCheckList = new ArrayList<>();
            for (final CFCAttachmentsDTO checklistData : checkList) {
                doc = new DocumentDetailsVO();
                doc.setAttachmentId(checklistData.getAttId());
                doc.setDocumentName(checklistData.getAttFname());
                doc.setDocumentId(checklistData.getClmId());
                doc.setDocumentSerialNo(checklistData.getClmSrNo());
                doc.setDoc_DESC_ENGL(checklistData.getClmDescEngl());
                if (checklistData.getClmDesc() != null) {
                    doc.setDoc_DESC_Mar(checklistData.getClmDesc());
                }
                doc.setCheckkMANDATORY(checklistData.getMandatory());
                doc.setDocDescription(checklistData.getDocDescription());

                finalCheckList.add(doc);
            }
        }

        return finalCheckList;
    }

    private List<DocumentDetailsVO> getFileUploadListForResubmission(final List<DocumentDetailsVO> checkList,
            final Map<Long, Set<File>> fileMap) {

        final Map<Long, String> listString = new HashMap<>();
        final Map<Long, String> fileName = new HashMap<>();

        try {
            final List<DocumentDetailsVO> docs = checkList;
            if ((fileMap != null) && !fileMap.isEmpty()) {
                for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

                    final List<File> list = new ArrayList<>(entry.getValue());
                    for (final File file : list) {
                        try {
                            final Base64 base64 = new Base64();

                            final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
                            fileName.put(entry.getKey(), file.getName());
                            listString.put(entry.getKey(), bytestring);

                        } catch (final IOException e) {
                            logger.error("Exception getFileUploadList  during file I/O :", e);
                            throw new FrameworkException(e);
                        }
                    }
                }
            }
            if (docs != null) {
                if (!docs.isEmpty() && !listString.isEmpty()) {

                    for (final Map.Entry<Long, String> entry : listString.entrySet()) {
                        docs.get(entry.getKey().intValue()).setDocumentByteCode(entry.getValue());
                        docs.get(entry.getKey().intValue()).setDocumentName(fileName.get(entry.getKey()));
                        if ((docs.get(entry.getKey().intValue()).getDoc_DESC_Mar() != null)
                                && !docs.get(entry.getKey().intValue()).getDoc_DESC_Mar().isEmpty()) {

                            /*docs.get(entry.getKey().intValue())
                                    .setDoc_DESC_Mar(Utility.encodeField(docs.get(entry.getKey().intValue()).getDoc_DESC_Mar()));*/
                        	docs.get(entry.getKey().intValue()).setDoc_DESC_Mar(docs.get(entry.getKey().intValue()).getDoc_DESC_Mar());
                        }
                        if ((docs.get(entry.getKey().intValue()).getDoc_DESC_ENGL() == null)
                                || docs.get(entry.getKey().intValue()).getDoc_DESC_ENGL().equals(MainetConstants.BLANK)) {
                            docs.get(entry.getKey().intValue())
                                    .setDoc_DESC_ENGL(docs.get(entry.getKey().intValue()).getDocumentSerialNo().toString());
                        }
                    }

                    /*
                     * for (final DocumentDetailsVO d : docs) { final long count = d.getDocumentSerialNo() - 1; if
                     * (listString.containsKey(count) && fileName.containsKey(count)) {
                     * d.setDocumentByteCode(listString.get(count)); d.setDocumentName(fileName.get(count)); if
                     * ((d.getDoc_DESC_Mar() != null) && !d.getDoc_DESC_Mar().isEmpty()) {
                     * d.setDoc_DESC_Mar(Utility.encodeField(d.getDoc_DESC_Mar())); } } if ((d.getDoc_DESC_ENGL() == null) ||
                     * d.getDoc_DESC_ENGL().equals(MainetConstants.BLANK)) {
                     * d.setDoc_DESC_ENGL(d.getDocumentSerialNo().toString()); } }
                     */

                }
            }
            return docs;
        } catch (final Exception e) {
            throw new FrameworkException("FileUploading Exception occur in getFileUploadList", e);
        }
    }

    public CFCAttachment getAttachment() {
        return attachment;
    }

    public void setAttachment(CFCAttachment attachment) {
        this.attachment = attachment;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(final List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public List<CFCAttachmentsDTO> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(final List<CFCAttachmentsDTO> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public ChecklistServiceDTO getApplicationDetails() {
        return applicationDetails;
    }

    public void setApplicationDetails(final ChecklistServiceDTO applicationDetails) {
        this.applicationDetails = applicationDetails;
    }

    public boolean isResubmitedApplication() {
        return resubmitedApplication;
    }

    public void setResubmitedApplication(final boolean resubmitedApplication) {
        this.resubmitedApplication = resubmitedApplication;
    }
}
