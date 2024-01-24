package com.abm.mainet.lqp.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class BirtReportListModel extends AbstractFormModel {

	private static final long serialVersionUID = 861664615460439255L;
	
	private String reportType;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	

}
