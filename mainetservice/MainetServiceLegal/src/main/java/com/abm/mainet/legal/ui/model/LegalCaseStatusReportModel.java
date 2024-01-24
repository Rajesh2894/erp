package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class LegalCaseStatusReportModel extends AbstractFormModel {

private static final long serialVersionUID = 1L;

private legalReportRequestDTO legalReportDto=new legalReportRequestDTO();

private List<legalReportRequestDTO> legalReportDtoList =new ArrayList<>();

private List<FinYearDTO> financialYearList = new ArrayList<>();

private CaseEntryDTO caseEntryDto =new CaseEntryDTO();

private List<CaseEntryDTO> caseEntryListDto =new ArrayList<>();

public legalReportRequestDTO getLegalReportDto() {
	return legalReportDto;
}

public void setLegalReportDto(legalReportRequestDTO legalReportDto) {
	this.legalReportDto = legalReportDto;
}

public List<legalReportRequestDTO> getLegalReportDtoList() {
	return legalReportDtoList;
}

public void setLegalReportDtoList(List<legalReportRequestDTO> legalReportDtoList) {
	this.legalReportDtoList = legalReportDtoList;
}

public List<FinYearDTO> getFinancialYearList() {
	return financialYearList;
}

public void setFinancialYearList(List<FinYearDTO> financialYearList) {
	this.financialYearList = financialYearList;
}

public CaseEntryDTO getCaseEntryDto() {
	return caseEntryDto;
}

public List<CaseEntryDTO> getCaseEntryListDto() {
	return caseEntryListDto;
}

public void setCaseEntryDto(CaseEntryDTO caseEntryDto) {
	this.caseEntryDto = caseEntryDto;
}

public void setCaseEntryListDto(List<CaseEntryDTO> caseEntryListDto) {
	this.caseEntryListDto = caseEntryListDto;
}


}
