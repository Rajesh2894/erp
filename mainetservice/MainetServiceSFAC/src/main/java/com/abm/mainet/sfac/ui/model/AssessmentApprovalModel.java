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
import com.abm.mainet.sfac.dto.AssessmentKeyParameterDto;
import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.service.CBBOAssesementEntryService;

/**
 * @author pooja.maske
 *
 */
@Component
@Scope("session")
public class AssessmentApprovalModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6624283211747083870L;

	@Autowired
	private CBBOAssesementEntryService assEntryService;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	private AssessmentMasterDto assementMasterDto = new AssessmentMasterDto();

	private List<AssessmentKeyParameterDto> assementKeyParamDtoList = new ArrayList<>();

	List<CBBOMasterDto> cbboMasterDtoList = new ArrayList<CBBOMasterDto>();

	private List<TbFinancialyear> faYears = new ArrayList<>();

	private String assYear;

	private String cbboAprRemark;

	/**
	 * @return the assementMasterDto
	 */
	public AssessmentMasterDto getAssementMasterDto() {
		return assementMasterDto;
	}

	/**
	 * @param assementMasterDto the assementMasterDto to set
	 */
	public void setAssementMasterDto(AssessmentMasterDto assementMasterDto) {
		this.assementMasterDto = assementMasterDto;
	}

	/**
	 * @return the assementKeyParamDtoList
	 */
	public List<AssessmentKeyParameterDto> getAssementKeyParamDtoList() {
		return assementKeyParamDtoList;
	}

	/**
	 * @param assementKeyParamDtoList the assementKeyParamDtoList to set
	 */
	public void setAssementKeyParamDtoList(List<AssessmentKeyParameterDto> assementKeyParamDtoList) {
		this.assementKeyParamDtoList = assementKeyParamDtoList;
	}

	/**
	 * @return the cbboMasterDtoList
	 */
	public List<CBBOMasterDto> getCbboMasterDtoList() {
		return cbboMasterDtoList;
	}

	/**
	 * @param cbboMasterDtoList the cbboMasterDtoList to set
	 */
	public void setCbboMasterDtoList(List<CBBOMasterDto> cbboMasterDtoList) {
		this.cbboMasterDtoList = cbboMasterDtoList;
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

	/**
	 * @return the cbboAprRemark
	 */
	public String getCbboAprRemark() {
		return cbboAprRemark;
	}

	/**
	 * @param cbboAprRemark the cbboAprRemark to set
	 */
	public void setCbboAprRemark(String cbboAprRemark) {
		this.cbboAprRemark = cbboAprRemark;
	}

	@Override
	public boolean saveForm() {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee = UserSession.getCurrent().getEmployee();
		Date newDate = new Date();

		AssessmentMasterDto dto = getAssementMasterDto();
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
		assEntryService.updateApprovalStatusAndRemark(dto, this);
		this.setSuccessMessage(
				(getAppSession().getMessage("sfac.assessment.approved.msg")) + " " + dto.getApplicationId());
		return true;
	}

}
