/**
 * 
 */
package com.abm.mainet.sfac.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.sfac.dto.FpoAssKeyParameterDto;
import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.service.FPOAssessmentService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class FPOAssApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3907602597123117212L;

	@Autowired
	private FPOAssessmentService fppoAssService;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	private FpoAssessmentMasterDto assementMasterDto = new FpoAssessmentMasterDto();

	private List<FpoAssKeyParameterDto> keyParamDtoList = new ArrayList<>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private String assYear;

	/**
	 * @return the assementMasterDto
	 */
	public FpoAssessmentMasterDto getAssementMasterDto() {
		return assementMasterDto;
	}

	/**
	 * @param assementMasterDto the assementMasterDto to set
	 */
	public void setAssementMasterDto(FpoAssessmentMasterDto assementMasterDto) {
		this.assementMasterDto = assementMasterDto;
	}

	/**
	 * @return the keyParamDtoList
	 */
	public List<FpoAssKeyParameterDto> getKeyParamDtoList() {
		return keyParamDtoList;
	}

	/**
	 * @param keyParamDtoList the keyParamDtoList to set
	 */
	public void setKeyParamDtoList(List<FpoAssKeyParameterDto> keyParamDtoList) {
		this.keyParamDtoList = keyParamDtoList;
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
	 * @return the assYear
	 */
	public String getAssYear() {
		return assYear;
	}

	/**
	 * @param assYear the assYear to set
	 */
	public void setAssYear(String assYear) {
		this.assYear = assYear;
	}

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee = UserSession.getCurrent().getEmployee();
		Date newDate = new Date();

		FpoAssessmentMasterDto dto = getAssementMasterDto();
		getWorkflowActionDto().setApplicationId(Long.valueOf(getWorkflowActionDto().getReferenceId()));
		getWorkflowActionDto().setDecision(dto.getAssStatus());
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
		taskAction.setDecision(dto.getAssStatus());
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
		fppoAssService.updateApprovalStatusAndRemark(dto, this);

		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.fpo.ass.approved.msg")) + " " + dto.getApplicationId());
		return true;
	}
}
