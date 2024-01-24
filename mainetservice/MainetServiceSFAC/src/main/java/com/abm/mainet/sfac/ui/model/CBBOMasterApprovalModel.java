/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.service.CBBOMasterService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class CBBOMasterApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	
	@Autowired
	private CBBOMasterService cbboMasterService;
	

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	@Autowired
	private IEmployeeService empService;

	
	private static final long serialVersionUID = 3867293789465828603L;

	private CBBOMasterDto masterDto = new CBBOMasterDto();
	
	

	/**
	 * @return the masterDto
	 */
	public CBBOMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(CBBOMasterDto masterDto) {
		this.masterDto = masterDto;
	}
	
	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee = UserSession.getCurrent().getEmployee();
		Date newDate = new Date();

		CBBOMasterDto dto = getMasterDto();
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
		cbboMasterService.updateApprovalStatusAndRemark(dto, this);
		if (StringUtils.isNotEmpty(dto.getActiveInactiveStatus()) && dto.getActiveInactiveStatus().equals(MainetConstants.FlagI)) {
			empService.updateIsDeletedFlagByMasId(MainetConstants.ONE,dto.getCbboId(),employee.getEmpId(),dto.getCbboUniqueId(),dto.getOrgId());
			}
		if (dto.getAppStatus().equals(MainetConstants.FlagA))
		    this.setSuccessMessage((getAppSession().getMessage("sfac.cbbo.approval.save.msg")) + " " + dto.getApplicationId());
		else
			this.setSuccessMessage((getAppSession().getMessage("sfac.cbbo.rejected.save.msg")) + " " + dto.getApplicationId());
		return true;
	}


}
