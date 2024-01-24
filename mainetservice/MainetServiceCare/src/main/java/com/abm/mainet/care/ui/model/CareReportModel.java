package com.abm.mainet.care.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class CareReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	ComplaintReportRequestDTO careReportRequest = new ComplaintReportRequestDTO();
	
	List<ComplaintReportRequestDTO> careReportRequestList = new ArrayList<ComplaintReportRequestDTO>();
	
	private String reportTypes;

	public ComplaintReportRequestDTO getCareReportRequest() {
		return careReportRequest;
	}

	public void setCareReportRequest(ComplaintReportRequestDTO careReportRequest) {
		this.careReportRequest = careReportRequest;
	}

	public List<ComplaintReportRequestDTO> getCareReportRequestList() {
		return careReportRequestList;
	}

	public void setCareReportRequestList(List<ComplaintReportRequestDTO> careReportRequestList) {
		this.careReportRequestList = careReportRequestList;
	}

	public String getReportTypes() {
		return reportTypes;
	}

	public void setReportTypes(String reportTypes) {
		this.reportTypes = reportTypes;
	}
	
	

}
