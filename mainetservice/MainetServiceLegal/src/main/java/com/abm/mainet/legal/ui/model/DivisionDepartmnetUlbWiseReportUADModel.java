package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class DivisionDepartmnetUlbWiseReportUADModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;

	private legalReportRequestDTO legalReport = new legalReportRequestDTO();

	private List<legalReportRequestDTO> legalReportList = new ArrayList<>();

	public legalReportRequestDTO getLegalReport() {
		return legalReport;
	}

	public List<legalReportRequestDTO> getLegalReportList() {
		return legalReportList;
	}

	public void setLegalReport(legalReportRequestDTO legalReport) {
		this.legalReport = legalReport;
	}

	public void setLegalReportList(List<legalReportRequestDTO> legalReportList) {
		this.legalReportList = legalReportList;
	}
}
