package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class DetailCasePendencyReportModel extends AbstractFormModel{

private static final long serialVersionUID = 1L;

private legalReportRequestDTO legalReport = new legalReportRequestDTO();

private List<legalReportRequestDTO> legalRequestReport = new ArrayList<>();

private List<AdvocateMasterDTO> advocateName= new ArrayList<>();



private CourtMasterDTO  courtMasterDTO =new CourtMasterDTO();

private List<CourtMasterDTO> courtMasterDTOList =new ArrayList<>();

private CaseEntryDTO caseEntryDto =new CaseEntryDTO();

private List<CaseEntryDTO> caseEntryListDto =new ArrayList<>();



public legalReportRequestDTO getLegalReport() {
	return legalReport;
}

public void setLegalReport(legalReportRequestDTO legalReport) {
	this.legalReport = legalReport;
}

public List<legalReportRequestDTO> getLegalRequestReport() {
	return legalRequestReport;
}

public void setLegalRequestReport(List<legalReportRequestDTO> legalRequestReport) {
	this.legalRequestReport = legalRequestReport;
}

public List<AdvocateMasterDTO> getAdvocateName() {
	return advocateName;
}

public void setAdvocateName(List<AdvocateMasterDTO> advocateName) {
	this.advocateName = advocateName;
}

public CourtMasterDTO getCourtMasterDTO() {
	return courtMasterDTO;
}

public void setCourtMasterDTO(CourtMasterDTO courtMasterDTO) {
	this.courtMasterDTO = courtMasterDTO;
}

public List<CourtMasterDTO> getCourtMasterDTOList() {
	return courtMasterDTOList;
}

public void setCourtMasterDTOList(List<CourtMasterDTO> courtMasterDTOList) {
	this.courtMasterDTOList = courtMasterDTOList;
}

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



}
