package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;

@Component
@Scope("session")
public class RtiStatusReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	
	private RtiApplicationFormDetailsReqDTO rtiFormDto = new RtiApplicationFormDetailsReqDTO();

	private List<RtiApplicationFormDetailsReqDTO> rtiFormDtoList = new ArrayList<>();
	
	
	private TbDepartment departmentDto = new TbDepartment();

	private List<TbDepartment> deparmnetDtoList = new ArrayList<>();

	
	private WorkflowRequest WorkflowRequestDto =new WorkflowRequest();
	
	private List<WorkflowRequest> WorkflowRequestDtoList= new ArrayList<>();
	
	
	private List<FinYearDTO> financialYearList = new ArrayList<>();
	
	public RtiApplicationFormDetailsReqDTO getRtiFormDto() {
		return rtiFormDto;
	}

	public void setRtiFormDto(RtiApplicationFormDetailsReqDTO rtiFormDto) {
		this.rtiFormDto = rtiFormDto;
	}

	public List<RtiApplicationFormDetailsReqDTO> getRtiFormDtoList() {
		return rtiFormDtoList;
	}

	public void setRtiFormDtoList(List<RtiApplicationFormDetailsReqDTO> rtiFormDtoList) {
		this.rtiFormDtoList = rtiFormDtoList;
	}

	
	public TbDepartment getDepartmentDto() {
		return departmentDto;
	}

	public void setDepartmentDto(TbDepartment departmentDto) {
		this.departmentDto = departmentDto;
	}

	public List<TbDepartment> getDeparmnetDtoList() {
		return deparmnetDtoList;
	}

	public void setDeparmnetDtoList(List<TbDepartment> deparmnetDtoList) {
		this.deparmnetDtoList = deparmnetDtoList;
	}

	public WorkflowRequest getWorkflowRequestDto() {
		return WorkflowRequestDto;
	}

	public void setWorkflowRequestDto(WorkflowRequest workflowRequestDto) {
		WorkflowRequestDto = workflowRequestDto;
	}

	public List<WorkflowRequest> getWorkflowRequestDtoList() {
		return WorkflowRequestDtoList;
	}

	public void setWorkflowRequestDtoList(List<WorkflowRequest> workflowRequestDtoList) {
		WorkflowRequestDtoList = workflowRequestDtoList;
	}

	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	
	
	

}
