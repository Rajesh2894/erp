package com.abm.mainet.securitymanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.securitymanagement.constant.SecurityConstant;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.dto.DeploymentOfStaffDTO;
import com.abm.mainet.securitymanagement.service.IDeploymentOfStaffService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DeploymentOfStaffModel extends AbstractFormModel {

	/**
	 * 
	 */
	private List<LookUp> lookup= new ArrayList<LookUp>();
	@Autowired
	IDeploymentOfStaffService staffService;

	private static final long serialVersionUID = 1L;

	private String saveMode;

	DeploymentOfStaffDTO deploymentOfStaffDTO = new DeploymentOfStaffDTO();

	private List<DeploymentOfStaffDTO> employeeList = new ArrayList<>();
	//private List<EmployeeSchedulingDetDTO> emplDetDtoList;
	private List<TbLocationMas> location=new ArrayList<TbLocationMas>();
	private List<ContractualStaffMasterDTO> empNameList=new ArrayList<ContractualStaffMasterDTO>();
	private List<TbAcVendormaster> vendorList=new ArrayList<TbAcVendormaster>();

	@Override
	public boolean saveForm() {

		boolean status = false;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee = UserSession.getCurrent().getEmployee();

		// code to generate seq no. start
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(SecurityConstant.SECURITY_SERVICE_CODE, orgId);

		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());

		String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDeptCode(service.getTbDepartment().getDpDeptid());

		final Long sequence = ApplicationContextProvider.getApplicationContext().getBean(SeqGenFunctionUtility.class)
				.generateSequenceNo(SecurityConstant.SECURITY_DEPT_CODE, SecurityConstant.SECURITY_TABLE,
						SecurityConstant.SECURITY_TABLE_COLUMN, orgId, MainetConstants.FlagC, financiaYear.getFaYear());

		String deplSeqNo = SecurityConstant.SECURITY_EMER_CODE + MainetConstants.WINDOWS_SLASH + deptCode
				+ MainetConstants.WINDOWS_SLASH
				+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);

		deploymentOfStaffDTO.setDeplSeq(deplSeqNo);
		deploymentOfStaffDTO.setWfStatus(SecurityConstant.WF_STATUS_DEFAULT);
		// code to generate seq no. end

		deploymentOfStaffDTO.setOrgid(orgId);
		deploymentOfStaffDTO.setCreatedBy(employee.getEmpId());
		deploymentOfStaffDTO.setCreatedDate(new Date());
		deploymentOfStaffDTO.setLgIpMac(employee.getEmppiservername());
		staffService.saveOrUpdate(deploymentOfStaffDTO);
		if (deploymentOfStaffDTO.getCount() == 0 && deploymentOfStaffDTO.getMessageDate() != "true") {
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage(
					"DeploymentOfStaffDTO.Validation.save" + "", new Object[] { deploymentOfStaffDTO.getDeplSeq() }));
			status = true;
		} else if (deploymentOfStaffDTO.getMessageDate() == "true") {
			addValidationError(getAppSession().getMessage("DeploymentOfStaffDTO.Validation.fromToAndAppointmentDate"));
			status = false;
		} else {
			addValidationError(getAppSession().getMessage("DeploymentOfStaffDTO.Validation.presentEmployee"));
			status = false;
		}
		if (hasValidationErrors()) {
			return false;
		}

		// initiate work flow start
		if (deploymentOfStaffDTO.getCount() == 0) {
			getWorkflowActionDto().setApplicationId(deploymentOfStaffDTO.getDeplId());
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());

			ServiceMaster service1 = ApplicationContextProvider.getApplicationContext()
					.getBean(ServiceMasterService.class).getServiceMasterByShortCode("DOS", orgId);

			WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
							service1.getTbDepartment().getDpDeptid(), service1.getSmServiceId(), null, null, null, null,
							null);

			ApplicationContextProvider.getApplicationContext().getBean(IDeploymentOfStaffService.class)
					.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
							"DeploymentOfStaff.html", MainetConstants.FlagA);
		}
		// initiate work flow end
		return status;
	}

	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		// taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		// taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(deploymentOfStaffDTO.getDeplSeq());
		// taskAction.setApplicationId(deploymentOfStaffDTO.getDeplId());
		taskAction.setComments(deploymentOfStaffDTO.getRemarkApproval());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
		return taskAction;
	}

	public List<TbLocationMas> getLocation() {
		return location;
	}

	public void setLocation(List<TbLocationMas> location) {
		this.location = location;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	public List<DeploymentOfStaffDTO> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<DeploymentOfStaffDTO> employeeList) {
		this.employeeList = employeeList;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public DeploymentOfStaffDTO getDeploymentOfStaffDTO() {
		return deploymentOfStaffDTO;
	}

	public void setDeploymentOfStaffDTO(DeploymentOfStaffDTO deploymentOfStaffDTO) {
		this.deploymentOfStaffDTO = deploymentOfStaffDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ContractualStaffMasterDTO> getEmpNameList() {
		return empNameList;
	}

	public void setEmpNameList(List<ContractualStaffMasterDTO> empNameList) {
		this.empNameList = empNameList;
	}

	public List<LookUp> getLookup() {
		return lookup;
	}

	public void setLookup(List<LookUp> lookup) {
		this.lookup = lookup;
	}

}
