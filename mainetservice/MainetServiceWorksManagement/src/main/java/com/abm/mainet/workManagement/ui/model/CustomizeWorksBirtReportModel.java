package com.abm.mainet.workManagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class CustomizeWorksBirtReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 4413812487107514739L;
	private String reportType;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

}
