package com.abm.mainet.workManagement.ui.model;

import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.workManagement.dto.WorksDeductionReportDto;

@Component
@Scope("session")
public class WorkDeductionReportModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 
	private List<WorksDeductionReportDto> worksDeductionReportDto;
	private String fromDate;
	private String toDate;

	public List<WorksDeductionReportDto> getWorksDeductionReportDto() {
		return worksDeductionReportDto;
	}

	public void setWorksDeductionReportDto(List<WorksDeductionReportDto> worksDeductionReportDto) {
		this.worksDeductionReportDto = worksDeductionReportDto;
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