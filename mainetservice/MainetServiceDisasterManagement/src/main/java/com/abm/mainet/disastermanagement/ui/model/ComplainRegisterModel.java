package com.abm.mainet.disastermanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.disastermanagement.constant.DisasterConstant;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;
import com.abm.mainet.disastermanagement.service.IComplainRegisterService;
import com.abm.mainet.disastermanagement.ui.validator.ComplainRegisterValidator;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ComplainRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 3108541764227266478L;

	@Autowired
	private IComplainRegisterService iComplainRegisterService;
	
	@Autowired
	private IEmployeeService iEmployeeService;
	
	@Autowired
	private IDepartmentDAO iDepartmentDAO;
	
	private ComplainRegisterDTO entity = new ComplainRegisterDTO();
	private List<ComplainRegisterDTO> entityList;
	
	private List<ComplainRegisterDTO> complainRegisterDTOList;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	List<Employee> employeList = new ArrayList<>();
	
	private Long DeptId; //TODO ::
	//List<Department> DeptId = new ArrayList<>();

	private String saveMode;

	/**
	 * @return the entity
	 */
	public ComplainRegisterDTO getEntity() {
		return entity;
	}

	/**
	 * @param entity the entity to set
	 */
	public void setEntity(ComplainRegisterDTO entity) {
		this.entity = entity;
	}

	/**
	 * @return the saveMode
	 */
	public String getSaveMode() {
		return saveMode;
	}

	/**
	 * @param saveMode the saveMode to set
	 */
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
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


	@Override
	public boolean saveForm() {
		validateBean(entity, ComplainRegisterValidator.class);
		if (this.hasValidationErrors()) {
			return false;
		}

		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		long userid = UserSession.getCurrent().getEmployee().getUserId();
		//Long Dept = iDepartmentDAO.getDepartment("DM", "A").getDpDeptid();
		String formStatusStr = DisasterConstant.OPEN;
		
		if (entity.getComplainId() == null) {

			entity.setOrgid(orgId);
			entity.setCreatedBy(createdBy);
			entity.setCreatedDate(createdDate);
			entity.setLgIpMac(lgIpMac);
			entity.setUserid(userid);
			entity.setComplaintStatus(formStatusStr);
			//entity.setDepartmentId(Dept);

			// initiate
			ServiceMaster service = ApplicationContextProvider.getApplicationContext()
					.getBean(ServiceMasterService.class).getServiceMasterByShortCode("CR", orgId);

			/*WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null,
							null);*/

			
			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(service.getTbDepartment().getDpDeptid());
			Integer currentYear = Utility.getYearByDate(new Date());
			
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo("DM", "TB_DM_COMPLAIN_REGISTER",
							"COMPLAIN_NO", orgId, MainetConstants.FlagY, null);
			String complainNo = deptCode + MainetConstants.WINDOWS_SLASH + currentYear.toString() + MainetConstants.WINDOWS_SLASH
					+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);
			entity.setComplainNo(complainNo);
						
			iComplainRegisterService.saveComplainRegistration(entity);
			//iComplainRegisterService.save(entity);

			/*ApplicationContextProvider.getApplicationContext().getBean(IComplainRegisterService.class)
					.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
							"ComplainRegisterApproval.html", MainetConstants.FlagA);*/

			setSuccessMessage(getAppSession().getMessage("ComplainRegisterDTO.form.save"
					+ "", new Object[] { complainNo }));

			// String flag = MainetConstants.FlagY;
		} else {
			entity.setUpdatedBy(createdBy);
			entity.setUpdatedDate(createdDate);
			entity.setLgIpMacUpd(lgIpMac);
			entity.setUserid(userid);
			setSuccessMessage(getAppSession().getMessage("ComplainRegisterDTO.form.save"
					+ "", new Object[] { entity.getComplainNo() }));

			iComplainRegisterService.saveComplainRegistration(entity);
			//complainRegisterService.save(entity);
		}
		
		prepareContractDocumentsData(entity);
		return true;
	}

	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(entity.getComplainNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
		return taskAction;
	}

	public void prepareContractDocumentsData(ComplainRegisterDTO entity) {
		RequestDTO requestDTO = new RequestDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		requestDTO.setOrgId(orgId);
		requestDTO.setStatus(MainetConstants.FlagA);

		requestDTO.setDepartmentName("DM");

		requestDTO.setIdfId(DisasterConstant.COMPLAIN_REG + MainetConstants.WINDOWS_SLASH +entity.getComplainNo()); //"CALL_REGISTER" + MainetConstants.WINDOWS_SLASH +
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());

		setCommonFileAttachment(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.doMasterFileUpload(getCommonFileAttachment(), requestDTO);
	}
	
	
	
	
	public List<Employee> getEmployeList() {
		return employeList;
	}

	public void setEmployeList(List<Employee> employeList) {
		this.employeList = employeList;
	}
	
	public Long getDeptId() {
		return DeptId;
	}
	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}
	
	public List<ComplainRegisterDTO> getComplainRegisterDTOList() {
		return complainRegisterDTOList;
	}

	public void setComplainRegisterDTOList(List<ComplainRegisterDTO> complainRegisterDTOList) {
		this.complainRegisterDTOList = complainRegisterDTOList;
	}
	
	public List<ComplainRegisterDTO> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<ComplainRegisterDTO> entityList) {
		this.entityList = entityList;
	}
	
//	public List<Department> getDeptId() {
//		return DeptId;
//	}
//	public void setDeptId(List<Department> deptId) {
//		DeptId = deptId;
//	}

	
	
}
