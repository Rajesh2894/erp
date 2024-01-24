package com.abm.mainet.lqp.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.lqp.dto.DocumentDto;
import com.abm.mainet.lqp.dto.QueryAnswerMasterDto;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;
import com.abm.mainet.lqp.service.ILegislativeWorkflowService;
import com.abm.mainet.lqp.service.IQueryRegistrationService;
import com.abm.mainet.lqp.ui.validator.QueryRegistrationValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class QueryRegistrationModel extends AbstractFormModel {

    private static final long serialVersionUID = 6280700536766194238L;
    private String saveMode;
    private String approvalViewFlag;
    private QueryRegistrationMasterDto queryRegMasrDto = new QueryRegistrationMasterDto();
    private List<QueryRegistrationMasterDto> queryRegMasrDtoList = new ArrayList<>();
    private List<EmployeeBean> employeeList = new ArrayList<>();
    private List<TbDepartment> departmentList = new ArrayList<>();
    private QueryAnswerMasterDto queryAnsrMstDto = new QueryAnswerMasterDto();
    private List<DocumentDto> documentDtos = new ArrayList<>();
    
    private List<DocumentDetailsVO> attachments = new ArrayList<>();
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    private Long deleteFileId;

    @Autowired
    IQueryRegistrationService registrationService;

    @Autowired
    private SeqGenFunctionUtility seqGenFunctionUtility;

    @Autowired
    ILegislativeWorkflowService legislativeWorkflowService;
    
    @Autowired
	private ISMSAndEmailService sMSAndEmailService;

    public QueryRegistrationMasterDto getQueryRegMasrDto() {
        return queryRegMasrDto;
    }

    public void setQueryRegMasrDto(QueryRegistrationMasterDto queryRegMasrDto) {
        this.queryRegMasrDto = queryRegMasrDto;
    }

    public List<TbDepartment> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<TbDepartment> departmentList) {
        this.departmentList = departmentList;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public String getApprovalViewFlag() {
        return approvalViewFlag;
    }

    public void setApprovalViewFlag(String approvalViewFlag) {
        this.approvalViewFlag = approvalViewFlag;
    }

    public List<QueryRegistrationMasterDto> getQueryRegMasrDtoList() {
        return queryRegMasrDtoList;
    }

    public void setQueryRegMasrDtoList(List<QueryRegistrationMasterDto> queryRegMasrDtoList) {
        this.queryRegMasrDtoList = queryRegMasrDtoList;
    }

    public List<EmployeeBean> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeBean> employeeList) {
        this.employeeList = employeeList;
    }

    public QueryAnswerMasterDto getQueryAnsrMstDto() {
        return queryAnsrMstDto;
    }

    public void setQueryAnsrMstDto(QueryAnswerMasterDto queryAnsrMstDto) {
        this.queryAnsrMstDto = queryAnsrMstDto;
    }

    public List<DocumentDto> getDocumentDtos() {
        return documentDtos;
    }

    public void setDocumentDtos(List<DocumentDto> documentDtos) {
        this.documentDtos = documentDtos;
    }
    

    public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	
	public Long getDeleteFileId() {
		return deleteFileId;
	}

	public void setDeleteFileId(Long deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

	@Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // validate server side
        if (queryRegMasrDto.getQuestionDate() == null) {
        	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
				queryRegMasrDto.setQuestionDate(formatter.parse(formatter.format(new Date())));
			} catch (ParseException e) {
				throw new FrameworkException("Exception occur while parsing date: ", e);
			}
        }
        validateBean(queryRegMasrDto, QueryRegistrationValidator.class);

        if (queryRegMasrDto.getQuestionRegId() == null) {
            queryRegMasrDto.setOrgId(orgId);
            queryRegMasrDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            queryRegMasrDto.setCreatedDate(new Date());
            queryRegMasrDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            // set DEPT object
            Department dept = new Department();
            dept.setDpDeptid(queryRegMasrDto.getDeptId());
            queryRegMasrDto.setDepartment(dept);
            Employee emp = new Employee();
            emp.setEmpId(queryRegMasrDto.getEmpId());
            queryRegMasrDto.setEmployee(emp);
        } else {
            queryRegMasrDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            queryRegMasrDto.setUpdatedDate(new Date());
            queryRegMasrDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
        }

        // if (!StringUtils.isEmpty(queryRegMasrDto.getReopenReason())) {
        if (queryRegMasrDto.getStatus() != null && queryRegMasrDto.getStatus().equals(MainetConstants.LQP.STATUS.CONCLUDED)) {
            queryRegMasrDto.setStatus(MainetConstants.LQP.STATUS.REOPEN);
            queryRegMasrDto.setReopenDate(new Date());
        } else {
            // open the status of question
            queryRegMasrDto.setStatus(MainetConstants.LQP.STATUS.OPEN);
        }

        TbDepartment deptObj = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                .findDeptByCode(orgId, MainetConstants.FlagA,
                        MainetConstants.LQP.LQP_DEPT_CODE);
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode(MainetConstants.LQP.SERVICE_CODE.LQE, orgId);

        // DEPT wise workflow exist or not check using deptId from UI selection
        WorkflowMas workFlowMas = legislativeWorkflowService.resolveServiceWorkflowType(orgId, deptObj.getDpDeptid(),
                sm.getSmServiceId(), null, queryRegMasrDto.getDeptId(), null, null, null, null, null);
        if (workFlowMas == null) {
            addValidationError(getAppSession().getMessage("workflow not define for this department"));
        }

        // generate sequence no using sequence CONFIG only when 1st time punch
        if (queryRegMasrDto.getQuestionRegId() == null) {
            SequenceConfigMasterDTO configMasterDTO = null;
            configMasterDTO = seqGenFunctionUtility.loadSequenceData(orgId, deptObj.getDpDeptid(),
                    MainetConstants.LQP.TB_LQP_QUERY_REGISTRATION, MainetConstants.LQP.QUESTION_ID);
            if (configMasterDTO.getSeqConfigId() == null) {
                addValidationError(getAppSession().getMessage("first configure the sequence master for question no generate"));
            }
            CommonSequenceConfigDto commonSequenceConfigDto = new CommonSequenceConfigDto();
            String sequenceNo = seqGenFunctionUtility.generateNewSequenceNo(configMasterDTO, commonSequenceConfigDto);
            if (sequenceNo == null) {
                addValidationError(getAppSession().getMessage("Question No. Not generated"));
            }
            queryRegMasrDto.setQuestionId(sequenceNo);
            queryRegMasrDto.setQuestionIdWF(sequenceNo);
        } else {
            final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
                    .generateSequenceNo(
                            MainetConstants.LQP.LQP_DEPT_CODE,
                            "TB_LQP_QUERY_REGISTRATION_HIST", "QUESTION_REG_ID_H", orgId, null, null);
            String questionIdWF = MainetConstants.LQP.LQP_DEPT_CODE + "/" + sm.getSmShortdesc() + "/"
                    + queryRegMasrDto.getQuestionRegId() + "/" + sequence;
            // queryRegMasrDto.setQuestionId(sequenceNo);
            queryRegMasrDto.setQuestionIdWF(questionIdWF);
        }

        if (hasValidationErrors()) {
            return false;
        }

        // set all relevant Work flow Task Action Data For initiating Work Flow -initial request
        getWorkflowActionDto().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        getWorkflowActionDto().setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        getWorkflowActionDto().setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        getWorkflowActionDto().setDateOfAction(new Date());
        getWorkflowActionDto().setCreatedDate(new Date());
        getWorkflowActionDto().setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        getWorkflowActionDto().setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
        getWorkflowActionDto().setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
        getWorkflowActionDto().setPaymentMode(MainetConstants.FlagF);
        //
        setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
                .setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
        boolean success = registrationService.queryRegistrationAndInitiateWorkflow(queryRegMasrDto, getWorkflowActionDto(),
                MainetConstants.FlagA, orgId, workFlowMas,getAttachments(),deleteFileId);
        if (success) {
            setSuccessMessage(getAppSession().getMessage("lqp.question.success",
                    new Object[] { queryRegMasrDto.getQuestionIdWF() }));
            //send sms & email after successful initiation of workflow
            sendLQPSmsAndEmail(queryRegMasrDto);
        } else {
            setSuccessMessage(ApplicationSession.getInstance().getMessage("lqp.validation.workflowfailedmsg"));
        }
        return true;

    }
    
    private void sendLQPSmsAndEmail(QueryRegistrationMasterDto queryRegMasrDto) {
    	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
    	List<Employee> empList = registrationService.fetchEmpDetailList(queryRegMasrDto.getQuestionId(),orgId);
    	String menuUrl = MainetConstants.LQP.LQE_URL; 
        SMSAndEmailDTO smsAndEmailDTO = new SMSAndEmailDTO();
        //setting parameter for sending sms and email
        smsAndEmailDTO.setOrgId(orgId);
        smsAndEmailDTO.setLangId(UserSession.getCurrent().getLanguageId());
        smsAndEmailDTO.setAppName("Notification alert");
        smsAndEmailDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        //setting data for using in sms&email body template
        smsAndEmailDTO.setDueDt(Utility.dateToString(queryRegMasrDto.getDeadlineDate()));
        smsAndEmailDTO.setFrmDt(Utility.dateToString(queryRegMasrDto.getQuestionDate()));
        smsAndEmailDTO.setReferenceNo(queryRegMasrDto.getQuestionId());
        
        if (!CollectionUtils.isEmpty(empList)) {
			empList.forEach(emp -> {
				smsAndEmailDTO.setUserId(emp.getEmpId());
				if (!StringUtils.isEmpty(emp.getEmpmobno()))
					smsAndEmailDTO.setMobnumber(emp.getEmpmobno());
				if (!StringUtils.isEmpty(emp.getEmpemail()))
					smsAndEmailDTO.setEmail(emp.getEmpemail());
				sMSAndEmailService.sendEmailSMS(MainetConstants.LQP.LQP_DEPT_CODE, menuUrl,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, smsAndEmailDTO, UserSession.getCurrent().getOrganisation(),
						UserSession.getCurrent().getLanguageId());
			});
		}

    }

}
