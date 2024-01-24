package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class CaseImplementaionDetailsReportModel  extends AbstractFormModel{

	private static final long serialVersionUID = 1L;

	private CaseEntryDTO caseEntryDto = new CaseEntryDTO();

	private List<CaseEntryDTO> caseEntryListDto = new ArrayList<>();
	
	private legalReportRequestDTO legalReport = new legalReportRequestDTO();

	private List<legalReportRequestDTO> legalReportDto = new ArrayList<>();
	
	
	public CaseEntryDTO getCaseEntryDto() {
		return caseEntryDto;
	}

	public void setCaseEntryDto(CaseEntryDTO caseEntryDto) {
		this.caseEntryDto = caseEntryDto;
	}

	public List<CaseEntryDTO> getCaseEntryListDto() {
		return caseEntryListDto;
	}

	public void setCaseEntryListDto(List<CaseEntryDTO> caseEntryListDto) {
		this.caseEntryListDto = caseEntryListDto;
	}

	public legalReportRequestDTO getLegalReport() {
		return legalReport;
	}

	public void setLegalReport(legalReportRequestDTO legalReport) {
		this.legalReport = legalReport;
	}

	public List<legalReportRequestDTO> getLegalReportDto() {
		return legalReportDto;
	}

	public void setLegalReportDto(List<legalReportRequestDTO> legalReportDto) {
		this.legalReportDto = legalReportDto;
	}
	
	
	
}
