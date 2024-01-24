/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.service.RenewalFormService;
import com.abm.mainet.socialsecurity.ui.dto.RenewalFormDto;
import com.abm.mainet.socialsecurity.ui.validator.RenewalFormValidator;

/**
 * @author priti.singh
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class RenewalFormModel extends AbstractFormModel {

    private static final Logger LOGGER = Logger.getLogger(RenewalFormModel.class);

    private static final long serialVersionUID = 1387449222271618470L;

    private RenewalFormDto renewalFormDto = new RenewalFormDto();

    private List<RenewalFormDto> renewalFormDtoList = new ArrayList<>();

    private List<Object[]> serviceList = new ArrayList<>();

    private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();

    private List<CFCAttachment> cfcAttachment;

    private ServiceMaster serviceMaster = new ServiceMaster();

    @Autowired
    private IPensionSchemeMasterService pensionSchemeMasterService;

    @Autowired
    private IFileUploadService fileUpload;

    @Autowired
    private TbDepartmentService tbDepartmentService;

    @Autowired
    private RenewalFormService renewalFormService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Autowired
    private IFileUploadService fileUploadService;

    private List<CFCAttachment> documentList = null;

    /**
     * @return the renewalFormDto
     */
    public RenewalFormDto getRenewalFormDto() {
        return renewalFormDto;
    }

    /**
     * @param renewalFormDto the renewalFormDto to set
     */
    public void setRenewalFormDto(RenewalFormDto renewalFormDto) {
        this.renewalFormDto = renewalFormDto;
    }

    /**
     * @return the serviceList
     */
    public List<Object[]> getServiceList() {
        return serviceList;
    }

    /**
     * @param serviceList the serviceList to set
     */
    public void setServiceList(List<Object[]> serviceList) {
        this.serviceList = serviceList;
    }

    /**
     * @return the pensionSchemeMasterService
     */
    public IPensionSchemeMasterService getPensionSchemeMasterService() {
        return pensionSchemeMasterService;
    }

    /**
     * @param pensionSchemeMasterService the pensionSchemeMasterService to set
     */
    public void setPensionSchemeMasterService(IPensionSchemeMasterService pensionSchemeMasterService) {
        this.pensionSchemeMasterService = pensionSchemeMasterService;
    }

    /**
     * @return the fileUpload
     */
    public IFileUploadService getFileUpload() {
        return fileUpload;
    }

    /**
     * @param fileUpload the fileUpload to set
     */
    public void setFileUpload(IFileUploadService fileUpload) {
        this.fileUpload = fileUpload;
    }

    /**
     * @return the tbDepartmentService
     */
    public TbDepartmentService getTbDepartmentService() {
        return tbDepartmentService;
    }

    /**
     * @return the renewalFormService
     */
    public RenewalFormService getRenewalFormService() {
        return renewalFormService;
    }

    /**
     * @param renewalFormService the renewalFormService to set
     */
    public void setRenewalFormService(RenewalFormService renewalFormService) {
        this.renewalFormService = renewalFormService;
    }

    /**
     * @param tbDepartmentService the tbDepartmentService to set
     */
    public void setTbDepartmentService(TbDepartmentService tbDepartmentService) {
        this.tbDepartmentService = tbDepartmentService;
    }

    /**
     * @return the uploadFileList
     */
    public List<DocumentDetailsVO> getUploadFileList() {
        return uploadFileList;
    }

    /**
     * @param uploadFileList the uploadFileList to set
     */
    public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
        this.uploadFileList = uploadFileList;
    }

    /**
     * @return the cfcAttachment
     */
    public List<CFCAttachment> getCfcAttachment() {
        return cfcAttachment;
    }

    /**
     * @param cfcAttachment the cfcAttachment to set
     */
    public void setCfcAttachment(List<CFCAttachment> cfcAttachment) {
        this.cfcAttachment = cfcAttachment;
    }

    /**
     * @return the serviceMaster
     */
    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    /**
     * @param serviceMaster the serviceMaster to set
     */
    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    /**
     * @return the ismsAndEmailService
     */
    public ISMSAndEmailService getIsmsAndEmailService() {
        return ismsAndEmailService;
    }

    /**
     * @param ismsAndEmailService the ismsAndEmailService to set
     */
    public void setIsmsAndEmailService(ISMSAndEmailService ismsAndEmailService) {
        this.ismsAndEmailService = ismsAndEmailService;
    }

    /**
     * @return the serviceMasterService
     */
    public ServiceMasterService getServiceMasterService() {
        return serviceMasterService;
    }

    /**
     * @param serviceMasterService the serviceMasterService to set
     */
    public void setServiceMasterService(ServiceMasterService serviceMasterService) {
        this.serviceMasterService = serviceMasterService;
    }

    /**
     * @return the documentList
     */
    public List<CFCAttachment> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CFCAttachment> documentList) {
        this.documentList = documentList;
    }

    /**
     * @return the renewalFormDtoList
     */
    public List<RenewalFormDto> getRenewalFormDtoList() {
        return renewalFormDtoList;
    }

    /**
     * @param renewalFormDtoList the renewalFormDtoList to set
     */
    public void setRenewalFormDtoList(List<RenewalFormDto> renewalFormDtoList) {
        this.renewalFormDtoList = renewalFormDtoList;
    }

    @Override
    public boolean saveForm() {
        RenewalFormDto statusDto = this.getRenewalFormDto();
        try {

            
            statusDto = renewalFormService.saveRenewalDetails(getRenewalFormDto());

            setSuccessMessage(getAppSession().getMessage("social.success.msg") + statusDto.getMasterAppId());

            // sms and email (renewal form submit)

            final SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
            Organisation org = UserSession.getCurrent().getOrganisation();
            smsDto.setMobnumber(getRenewalFormDto().getMobNum().toString());
            if (getRenewalFormDto().getMasterAppId() != null) {
                smsDto.setAppNo(getRenewalFormDto().getMasterAppId().toString());
            }
            ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                    .getServiceMasterByShortCode(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE,
                            UserSession.getCurrent().getOrganisation().getOrgid());
            setServiceMaster(sm);
            smsDto.setServName(sm.getSmServiceName());
            String url = "RenewalForm.html";
            org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            int langId = UserSession.getCurrent().getLanguageId();
            ismsAndEmailService.sendEmailSMS("SWD", url,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.GENERAL_MSG, smsDto, org, langId);
        } catch (FrameworkException exp) {
            statusDto.setStatusFlag(false);
            throw new FrameworkException(exp);
        }
        return true;

    }

    public boolean validateInputs() {
        validateBean(this, RenewalFormValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
        return true;
    }

    public boolean callWorkFlow() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        WorkflowTaskAction workFlowActionDto = getWorkflowActionDto();
        Employee emp = UserSession.getCurrent().getEmployee();
        workFlowActionDto.setOrgId(orgId);
        workFlowActionDto.setEmpId(emp.getEmpId());
        workFlowActionDto.setDecision(getWorkflowActionDto().getDecision());
        
        
        List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
                .getBean(IChecklistVerificationService.class).fetchAttachmentIdByAppid(getWorkflowActionDto().getApplicationId(),
                        orgId);
        workFlowActionDto.setAttachementId(attacheMentIds);
         

        ServiceMaster sm = serviceMasterService
                .getServiceMasterByShortCode(MainetConstants.SocialSecurity.RENEWAL_OF_LIFE_CERTIFICATE_SERVICE_CODE, orgId);
        RequestDTO requestDTO = new RequestDTO();
        requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
        requestDTO.setOrgId(orgId);
        requestDTO.setDepartmentName(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
        requestDTO.setServiceId(sm.getSmServiceId());
        requestDTO.setUserId(emp.getEmpId());
        requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());
        requestDTO.setDeptId(sm.getTbDepartment().getDpDeptid());

        List<DocumentDetailsVO> docs = new ArrayList<>();
        DocumentDetailsVO document = new DocumentDetailsVO();
        document.setDocumentSerialNo(1L);
        docs.add(document);
        docs = fileUpload.prepareFileUpload(docs);
        // getApplicationformdto().setDocumentList(docs);
        getRenewalFormDto().setDocumentList(docs);

        // this code is for document upload end
        return renewalFormService.saveDecision(getRenewalFormDto(), orgId, emp, workFlowActionDto, requestDTO);
    }

}
