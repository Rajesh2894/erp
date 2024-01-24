/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.FPOMasterService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class FpoMasterApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1240903660642153734L;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	@Autowired
	private FPOMasterService fpoMaseterService;

	FPOMasterDto dto = new FPOMasterDto();

	List<LookUp> allocationCatgList = new ArrayList<>();
	List<LookUp> allocationSubCatgList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();
	
	@Autowired
	private IEmployeeService empService;
	
	private String showUdyogDet;

	/**
	 * @return the dto
	 */
	public FPOMasterDto getDto() {
		return dto;
	}

	/**
	 * @param dto the dto to set
	 */
	public void setDto(FPOMasterDto dto) {
		this.dto = dto;
	}

	/**
	 * @return the allocationCatgList
	 */
	public List<LookUp> getAllocationCatgList() {
		return allocationCatgList;
	}

	/**
	 * @param allocationCatgList the allocationCatgList to set
	 */
	public void setAllocationCatgList(List<LookUp> allocationCatgList) {
		this.allocationCatgList = allocationCatgList;
	}

	/**
	 * @return the allocationSubCatgList
	 */
	public List<LookUp> getAllocationSubCatgList() {
		return allocationSubCatgList;
	}

	/**
	 * @param allocationSubCatgList the allocationSubCatgList to set
	 */
	public void setAllocationSubCatgList(List<LookUp> allocationSubCatgList) {
		this.allocationSubCatgList = allocationSubCatgList;
	}

	/**
	 * @return the faYears
	 */
	public List<TbFinancialyear> getFaYears() {
		return faYears;
	}

	/**
	 * @param faYears the faYears to set
	 */
	public void setFaYears(List<TbFinancialyear> faYears) {
		this.faYears = faYears;
	}
	
	

	/**
	 * @return the showUdyogDet
	 */
	public String getShowUdyogDet() {
		return showUdyogDet;
	}

	/**
	 * @param showUdyogDet the showUdyogDet to set
	 */
	public void setShowUdyogDet(String showUdyogDet) {
		this.showUdyogDet = showUdyogDet;
	}

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee = UserSession.getCurrent().getEmployee();
		Date newDate = new Date();

		FPOMasterDto dto = getDto();
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		getWorkflowActionDto().setDecision(dto.getAppStatus());

		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(orgId);
		taskAction.setEmpId(employee.getEmpId());
		taskAction.setEmpType(employee.getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(newDate);
		taskAction.setCreatedBy(employee.getEmpId());
		taskAction.setEmpName(employee.getEmplname());
		taskAction.setEmpEmail(employee.getEmpemail());
		taskAction.setTaskId(getWorkflowActionDto().getTaskId());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setIsFinalApproval(true);
		taskAction.setComments(dto.getRemark());
		taskAction.setDecision(dto.getAppStatus());
		taskAction.setApplicationId(dto.getApplicationId());
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(taskAction);
		try {
			iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods in Assesment approval",
					exception);
		}
		fpoMaseterService.updateApprovalStatusAndRemark(dto, this);
		//to update is deleted flag in employee table based on active inactive status
		if (StringUtils.isNotEmpty(dto.getActiveInactiveStatus()) && dto.getActiveInactiveStatus().equals(MainetConstants.FlagI)) {
			empService.updateIsDeletedFlagByMasId(MainetConstants.ONE,dto.getFpoId(),employee.getEmpId(),dto.getFpoRegNo(),dto.getOrgId());
			}
		if (dto.getAppStatus().equals(MainetConstants.FlagA))
		    this.setSuccessMessage((getAppSession().getMessage("sfac.fpo.approval.save.msg")) + " " + dto.getApplicationId());
		else
			this.setSuccessMessage((getAppSession().getMessage("sfac.fpo.rejected.save.msg")) + " " + dto.getApplicationId());
		return true;
	}
}
