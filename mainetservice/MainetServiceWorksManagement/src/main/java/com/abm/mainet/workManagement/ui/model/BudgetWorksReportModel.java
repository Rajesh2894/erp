package com.abm.mainet.workManagement.ui.model;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.WorksBudgetReportDto;


@Component
@Scope("session")
public class BudgetWorksReportModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<WorksBudgetReportDto> worksBudgetReportDto;
	private String fromDate;
	private String toDate;

	public List<WorksBudgetReportDto> getWorksBudgetReportDto() {
		return worksBudgetReportDto;
	}

	public void setWorksBudgetReportDto(List<WorksBudgetReportDto> worksBudgetReportDto) {
		this.worksBudgetReportDto = worksBudgetReportDto;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}
