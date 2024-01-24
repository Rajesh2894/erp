package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class SummaryCasePendencyReportDivisionWiseModel extends AbstractFormModel{

	private static final long serialVersionUID = 1L;
	
	private legalReportRequestDTO  legalReport = new  legalReportRequestDTO();
	
	private List<legalReportRequestDTO> legalRequestReport = new ArrayList<>();
	
	private CaseEntryDTO caseEntryDTO =new  CaseEntryDTO();
	
	private List<CaseEntryDTO> caseEntryDTOList =new ArrayList<>();
	
	
	private CourtMasterDTO courtMasterDTO =new CourtMasterDTO();
	
	private List<CourtMasterDTO> courtMasterDTOList =new ArrayList<>();
	
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
	public CaseEntryDTO getCaseEntryDTO() {
		return caseEntryDTO;
	}
	public void setCaseEntryDTO(CaseEntryDTO caseEntryDTO) {
		this.caseEntryDTO = caseEntryDTO;
	}
	public List<CaseEntryDTO> getCaseEntryDTOList() {
		return caseEntryDTOList;
	}
	public void setCaseEntryDTOList(List<CaseEntryDTO> caseEntryDTOList) {
		this.caseEntryDTOList = caseEntryDTOList;
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
	
	
	

}
