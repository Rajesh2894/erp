package com.abm.mainet.common.formbuilder.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.dto.ViewCFCScrutinyLabelValue;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ScrutinyServiceDto;
import com.abm.mainet.common.formbuilder.dto.FormBuilderValueDTO;
import com.abm.mainet.common.formbuilder.service.IFormBuilderUtilityService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class FormBuilderUtilityModel extends AbstractFormModel {

    private static final long serialVersionUID = -2765855207220786044L;

    private static final Logger logger = Logger.getLogger(FormBuilderUtilityModel.class);

    @Resource
    private IFormBuilderUtilityService utilityService;

    @Resource
    private IWorkflowExecutionService workflowExecutionService;

    @Resource
    private ServiceMasterService serviceMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private IAttachDocsService attachDocsService;

    private List<CFCAttachment> scrutinyDocs = new ArrayList<>(3);
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private List<AttachDocs> geoTagDocsList = new ArrayList<>();

    private List<DocumentDetailsVO> geoTagDoc = new ArrayList<>();
    private List<ScrutinyServiceDto> scrutinyServiceList = new ArrayList<>();
    private List<FormBuilderValueDTO> scrutinyValueList = new ArrayList<>();

    private ScrutinyLabelDTO scrutinyLabelDTO;

    private String queryString;

    private String errorMsg;

    private boolean flag;

    private String mode;

    private String removeCommonFileById;
    private String uploadFileName;
    private String serviceCode;

    public void populateScrutinyViewData(final Long applicationId, final String serviceCode, final UserSession userSession) {

        scrutinyDocs.clear();
        final Long gmId = UserSession.getCurrent().getEmployee().getGmid();
        scrutinyLabelDTO = utilityService.populateFormBuilderLabelData(applicationId,
                UserSession.getCurrent().getEmployee().getEmpId(), gmId, UserSession.getCurrent().getOrganisation().getOrgid(),
                serviceCode, UserSession.getCurrent().getLanguageId());
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.FINYEAR,
                getUserSession().getFinancialPeriodShortForm());
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.LANGID,
                getUserSession().getLanguageId() + MainetConstants.BLANK);
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.USERID,
                getUserSession().getEmployee().getEmpId() + MainetConstants.BLANK);
        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.ORGID,
                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.BLANK);

    }

    private Long getLegalScrDesg() {
        final String gmId = scrutinyLabelDTO.getRoleId();

        final Long dsgId = Long.valueOf(gmId);

        if (scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().containsKey(dsgId)) {
            return dsgId;
        }
        return 0L;
    }

    public boolean saveScrutinyLabels() {
        final boolean saveflag = saveScrutinyLabelValue(false, null);

        if (!saveflag) {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("builder.value.save"));
            return true;
        } else {
            setSuccessMessage(
                    ApplicationSession.getInstance().getMessage("builder.value.save.fail"));
            return false;
        }

    }

    private boolean saveScrutinyLabelValue(final boolean updateFlag, List<Long> attachmentId) {

        boolean saveFlag = false;
        try {
            final Long desgId = getLegalScrDesg();

            final List<ViewCFCScrutinyLabelValue> list = scrutinyLabelDTO.getDesgWiseScrutinyLabelMap().get(desgId);

            if ((list != null) && (!list.isEmpty())) {

                saveFlag = utilityService.saveCompleteFormBuilderLabel(list, getUserSession(), scrutinyLabelDTO, updateFlag,
                        this.getTaskId(), attachmentId);

            }

            prepareDocumentsData(scrutinyLabelDTO);
        } catch (final Exception exception) {

            logger.error("Exception Occur in saveScrutinyLabelValue()", exception);

        }
        return saveFlag;
    }

    private void prepareDocumentsData(ScrutinyLabelDTO scrutinyLabelDTO) {
        RequestDTO requestDTO = new RequestDTO();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        requestDTO.setOrgId(orgId);
        requestDTO.setStatus(MainetConstants.FlagA);

        requestDTO.setDepartmentName("SWM");
        requestDTO.setIdfId(MainetConstants.SolidWasteManagement.SURVEY_DOC + MainetConstants.DOUBLE_BACK_SLACE
                + getAppId(scrutinyLabelDTO));
        requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        List<DocumentDetailsVO> dto = getCommonFileAttachment();

        setCommonFileAttachment(fileUpload.prepareFileUpload(dto));

        fileUpload.doMasterFileUpload(getCommonFileAttachment(), requestDTO);

        List<DocumentDetailsVO> list = new ArrayList<>();
        requestDTO.setIdfId(MainetConstants.SolidWasteManagement.GEO_TAG_DOC + MainetConstants.DOUBLE_BACK_SLACE
                + getAppId(scrutinyLabelDTO));
       
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            if (entry.getKey() == 100L) {
                Base64 base64 = null;
                List<File> fileList = null;
                fileList = new ArrayList<>(entry.getValue());
                for (final File file : fileList) {
                    try {
                        base64 = new Base64();
                        final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));

                        DocumentDetailsVO d = new DocumentDetailsVO();
                        d.setDocumentName(file.getName());
                        d.setDocumentByteCode(bytestring);
                        list.add(d);

                        if(this.getMode().equalsIgnoreCase(MainetConstants.MODE_EDIT)) {
                        	attachDocsService.updateMasterDocumentStatus(MainetConstants.SolidWasteManagement.GEO_TAG_DOC + MainetConstants.DOUBLE_BACK_SLACE
                                + getAppId(scrutinyLabelDTO), MainetConstants.FlagD);
                        }
                    } catch (final IOException e) {
                        logger.error("Exception has been occurred in file byte to string conversions", e);
                    }
                }
            }
        }

        fileUpload.doMasterFileUpload(list, requestDTO);

        List<Long> enclosureRemoveById = null;
        String fileId = getRemoveCommonFileById();
        if (fileId != null && !fileId.isEmpty()) {
            enclosureRemoveById = new ArrayList<>();
            String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
            for (String fields : fileArray) {
                enclosureRemoveById.add(Long.valueOf(fields));
            }
        }
        if (enclosureRemoveById != null && !enclosureRemoveById.isEmpty())
            attachDocsService.deleteDocFileById(enclosureRemoveById,
                    UserSession.getCurrent().getEmployee().getEmpId());
    }

    private String getAppId(ScrutinyLabelDTO scrutinyLabelDTO) {
        Long appId = Long.valueOf(scrutinyLabelDTO.getApplicationId());
        return (appId != null && appId != 0) ? scrutinyLabelDTO.getApplicationId()
                : getUserSession().getCurrentAppid().toString();
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(final String queryString) {
        this.queryString = queryString;
    }

    public ScrutinyLabelDTO getScrutinyLabelDTO() {
        return scrutinyLabelDTO;
    }

    public void setScrutinyLabelDTO(final ScrutinyLabelDTO scrutinyLabelDTO) {
        this.scrutinyLabelDTO = scrutinyLabelDTO;
    }

    public List<CFCAttachment> getScrutinyDocs() {
        return scrutinyDocs;
    }

    public void setScrutinyDocs(final List<CFCAttachment> scrutinyDocs) {
        this.scrutinyDocs = scrutinyDocs;
    }

    /**
     * @return the flag
     */
    public boolean isFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(final boolean flag) {
        this.flag = flag;
    }

    /**
     * @return the errorMsg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<ScrutinyServiceDto> getScrutinyServiceList() {
        return scrutinyServiceList;
    }

    public void setScrutinyServiceList(List<ScrutinyServiceDto> scrutinyServiceList) {
        this.scrutinyServiceList = scrutinyServiceList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRemoveCommonFileById() {
        return removeCommonFileById;
    }

    public void setRemoveCommonFileById(String removeCommonFileById) {
        this.removeCommonFileById = removeCommonFileById;
    }

    public List<AttachDocs> getAttachDocsList() {
        return attachDocsList;
    }

    public void setAttachDocsList(List<AttachDocs> attachDocsList) {
        this.attachDocsList = attachDocsList;
    }

    public List<DocumentDetailsVO> getGeoTagDoc() {
        return geoTagDoc;
    }

    public void setGeoTagDoc(List<DocumentDetailsVO> geoTagDoc) {
        this.geoTagDoc = geoTagDoc;
    }

    public List<FormBuilderValueDTO> getScrutinyValueList() {
        return scrutinyValueList;
    }

    public void setScrutinyValueList(List<FormBuilderValueDTO> scrutinyValueList) {
        this.scrutinyValueList = scrutinyValueList;
    }

	public List<AttachDocs> getGeoTagDocsList() {
		return geoTagDocsList;
	}

	public void setGeoTagDocsList(List<AttachDocs> geoTagDocsList) {
		this.geoTagDocsList = geoTagDocsList;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
    

}
