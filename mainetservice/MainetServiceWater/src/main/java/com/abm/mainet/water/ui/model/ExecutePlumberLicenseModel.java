package com.abm.mainet.water.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICFCApplicationAddressService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.water.domain.PlumberMaster;
import com.abm.mainet.water.dto.PlumLicenseRenewalSchDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author Arun.Chavda
 *
 */
@Component
@Scope("session")
public class ExecutePlumberLicenseModel extends AbstractFormModel {

    private static final long serialVersionUID = -7615250223127870139L;

    @Autowired
    private PlumberLicenseService plumberLicenseService;
    @Resource
    private TbApprejMasService tbApprejMasService;
    @Autowired
    private ICFCApplicationAddressService iCFCApplicationAddressService;
    @Autowired
    private ICFCApplicationMasterService icfcApplicationMasterService;

    private List<TbApprejMas> tbApprejMas = new ArrayList<>();
    private String serviceName;
    private Long applicationId;
    private String applicantFullName;
    private Date applicationDate;
    private Date plmLicIssueDate;
    private String approvedBy;
    private Date approvalDate;
    private String executePlmLic;
    private Long empId;
    private Long orgId;
    private Long serviceId;
    private String fileDownLoadPath;
    private String address;
    private String plumLicNo;
    private String plumLicFrmDate;
    private String plumLicToDate;
    private String plumberFullName;
    private String plumAddress;
    private TbCfcApplicationMstEntity cfcEntity;
    private ApplicantDetailDTO applicantDetailDto;
    private String deptName;
    @Override
    public boolean saveForm() {

        final boolean result = validateFormData(getPlmLicIssueDate());
        if (result) {
            return false;
        }
        ApplicantDetailDTO applicantdto = getApplicantDetailDto();
        final PlumberMaster plumberMaster = plumberLicenseService.getPlumberDetailsByAppId(applicationId, orgId);
        cfcEntity = icfcApplicationMasterService.getCFCApplicationByApplicationId(applicationId, orgId);
        initializeApplicantAddressDetail(icfcApplicationMasterService.getApplicantsDetails(applicationId));
        if (null != plumberMaster) {
            final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER + MainetConstants.FILE_PATH_SEPARATOR
                    + "SHOW_DOCS";
            setFileDownLoadPath(Utility.downloadedFileUrl(
                    plumberMaster.getPlumImagePath() + MainetConstants.FILE_PATH_SEPARATOR + plumberMaster.getPlumImage(),
                    outputPath, getFileNetClient()));
        }
        plumberMaster.setServiceId(getServiceId());
        plumberMaster.setPlumLicIssueFlag(executePlmLic);
        plumberMaster.setPlumLicIssueDate(new SimpleDateFormat(MainetConstants.DATE_AND_TIME_FORMAT).format(getPlmLicIssueDate()));//(Utility.dateToString(getPlmLicIssueDate()));
        plumberMaster.setUpdatedBy(getEmpId());
        plumberMaster.setUpdatedDate(new Date());
        plumberMaster.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        final WorkflowProcessParameter taskDefDto = new WorkflowProcessParameter();
        /*
         * taskDefDto.setApplicationId(getApplicationId()); taskDefDto.setEmployeeId(getEmpId()); taskDefDto.setOrgId(getOrgId());
         * taskDefDto.setServiceId(getServiceId()); taskDefDto.setClose(true);
         */
        final PlumLicenseRenewalSchDTO dto = plumberLicenseService.updatedUserTaskAndPlumberLicenseExecutionDetails(plumberMaster,
                taskDefDto);
        setPlumLicNo(dto.getLicenseNo());
        setPlumLicFrmDate(dto.getLicenseFromDate());
        setPlumLicToDate(dto.getLicenseToDate());
        
        
        String userName = (cfcEntity.getApmFname() == null ? MainetConstants.BLANK
                : cfcEntity.getApmFname() + MainetConstants.WHITE_SPACE);
        userName += cfcEntity.getApmMname() == null ? MainetConstants.BLANK
                : cfcEntity.getApmMname() + MainetConstants.WHITE_SPACE;
        userName += cfcEntity.getApmLname() == null ? MainetConstants.BLANK : cfcEntity.getApmLname();
		setPlumberFullName(userName);
		
		setPlumAddress(plumberMaster.getPlumAddress());
        WorkflowTaskAction taskAction = new WorkflowTaskAction();
	    taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
	    taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	    taskAction.setDateOfAction(new Date());
	    taskAction.setCreatedDate(new Date());
	    taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
	    taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
	    taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
	    taskAction.setApplicationId(plumberMaster.getApmApplicationId());
	    taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
	    taskAction.setTaskId(getTaskId());

	    WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
	    workflowProcessParameter.setProcessName("scrutiny");
	    workflowProcessParameter.setWorkflowTaskAction(taskAction);
        
	    try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
				.updateWorkflow(workflowProcessParameter);
	    } catch (Exception exception) {
		throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
	    }
        
        final long lookupId = CommonMasterUtility
                .getValueFromPrefixLookUp(PrefixConstants.WATERMODULEPREFIX.PLR, PrefixConstants.WATERMODULEPREFIX.REM)
                .getLookUpId();
        final List<TbApprejMas> apprejMas = tbApprejMasService.findByServiceId(lookupId, getServiceId());
        setTbApprejMas(apprejMas);
        final CFCApplicationAddressEntity master = iCFCApplicationAddressService.getApplicationAddressByAppId(applicationId,
                orgId);
        final String address = concanateApplicationAddress(master);
        setAddress(address);
        
        return true;
    }

    private String concanateApplicationAddress(
            final CFCApplicationAddressEntity master) {

        final StringBuilder builder = new StringBuilder();
        if ((null != master.getApaBldgnm()) && !master.getApaBldgnm().isEmpty()) {
            builder.append(master.getApaBldgnm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != master.getApaFlatBuildingNo()) && !master.getApaFlatBuildingNo().isEmpty()) {
            builder.append(master.getApaFlatBuildingNo());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != master.getApaRoadnm()) && !master.getApaRoadnm().isEmpty()) {
            builder.append(master.getApaRoadnm());
            builder.append(MainetConstants.BLANK);
        }
        if ((null != master.getApaAreanm()) && !master.getApaAreanm().isEmpty()) {
            builder.append(master.getApaAreanm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != master.getApaCityName()) && !master.getApaCityName().isEmpty()) {
            builder.append(master.getApaCityName());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if (null != master.getApaPincode()) {
            builder.append(master.getApaPincode());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        return builder.toString();
    }

    private boolean validateFormData(final Date reconnExecuteDate) {
        boolean status = false;
        if ((reconnExecuteDate == null) || reconnExecuteDate.equals(MainetConstants.BLANK)) {
            addValidationError(ApplicationSession.getInstance().getMessage("water.plumberLicense.valMsg.plmLicIssueDt"));
            status = true;
        }
        return status;
    }

    /**
     * @return the serviceName
     */
    @Override
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    @Override
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }
    /**
     * @param deptName the deptName to set
     */
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

    

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the applicantFullName
     */
    public String getApplicantFullName() {
        return applicantFullName;
    }

    /**
     * @param applicantFullName the applicantFullName to set
     */
    public void setApplicantFullName(final String applicantFullName) {
        this.applicantFullName = applicantFullName;
    }

    /**
     * @return the applicationDate
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * @return the plmLicIssueDate
     */
    public Date getPlmLicIssueDate() {
        return plmLicIssueDate;
    }

    /**
     * @param plmLicIssueDate the plmLicIssueDate to set
     */
    public void setPlmLicIssueDate(final Date plmLicIssueDate) {
        this.plmLicIssueDate = plmLicIssueDate;
    }

    /**
     * @return the approvedBy
     */
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * @param approvedBy the approvedBy to set
     */
    public void setApprovedBy(final String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * @return the approvalDate
     */
    public Date getApprovalDate() {
        return approvalDate;
    }

    /**
     * @param approvalDate the approvalDate to set
     */
    public void setApprovalDate(final Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    /**
     * @return the executePlmLic
     */
    public String getExecutePlmLic() {
        return executePlmLic;
    }

    /**
     * @param executePlmLic the executePlmLic to set
     */
    public void setExecutePlmLic(final String executePlmLic) {
        this.executePlmLic = executePlmLic;
    }

    /**
     * @return the empId
     */
    public Long getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final Long empId) {
        this.empId = empId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the serviceId
     */
    @Override
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    @Override
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getFileDownLoadPath() {
        return fileDownLoadPath;
    }

    public void setFileDownLoadPath(final String fileDownLoadPath) {
        this.fileDownLoadPath = fileDownLoadPath;
    }

    /**
     * @return the tbApprejMas
     */
    public List<TbApprejMas> getTbApprejMas() {
        return tbApprejMas;
    }

    /**
     * @param tbApprejMas the tbApprejMas to set
     */
    public void setTbApprejMas(final List<TbApprejMas> tbApprejMas) {
        this.tbApprejMas = tbApprejMas;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return the plumLicNo
     */
    public String getPlumLicNo() {
        return plumLicNo;
    }

    /**
     * @param plumLicNo the plumLicNo to set
     */
    public void setPlumLicNo(final String plumLicNo) {
        this.plumLicNo = plumLicNo;
    }

    public String getPlumLicFrmDate() {
        return plumLicFrmDate;
    }

    public void setPlumLicFrmDate(final String plumLicFrmDate) {
        this.plumLicFrmDate = plumLicFrmDate;
    }

    public String getPlumLicToDate() {
        return plumLicToDate;
    }

    public void setPlumLicToDate(final String plumLicToDate) {
        this.plumLicToDate = plumLicToDate;
    }

	public String getPlumberFullName() {
		return plumberFullName;
	}

	public void setPlumberFullName(String plumberFullName) {
		this.plumberFullName = plumberFullName;
	}

	public String getPlumAddress() {
		return plumAddress;
	}

	public void setPlumAddress(String plumAddress) {
		this.plumAddress = plumAddress;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	private void initializeApplicantAddressDetail( final CFCApplicationAddressEntity addressEntity) {
        final StringBuilder builder = new StringBuilder();
        
        if ((null != addressEntity.getApaBldgnm()) && !addressEntity.getApaBldgnm().isEmpty()) {
            builder.append(addressEntity.getApaBldgnm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaFlatBuildingNo()) && !addressEntity.getApaFlatBuildingNo().isEmpty()) {
            builder.append(addressEntity.getApaFlatBuildingNo());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaRoadnm()) && !addressEntity.getApaRoadnm().isEmpty()) {
            builder.append(addressEntity.getApaRoadnm());
            builder.append(MainetConstants.BLANK);
        }
        if ((null != addressEntity.getApaAreanm()) && !addressEntity.getApaAreanm().isEmpty()) {
            builder.append(addressEntity.getApaAreanm());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if ((null != addressEntity.getApaCityName()) && !addressEntity.getApaCityName().isEmpty()) {
            builder.append(addressEntity.getApaCityName());
            builder.append(MainetConstants.WHITE_SPACE);
        }
        if (null != addressEntity.getApaPincode()) {
            builder.append(addressEntity.getApaPincode());
            builder.append(MainetConstants.WHITE_SPACE);
        }
       setPlumAddress(builder.toString());
    }



}
