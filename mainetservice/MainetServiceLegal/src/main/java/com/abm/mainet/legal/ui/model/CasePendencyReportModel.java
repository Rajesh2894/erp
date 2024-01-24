package com.abm.mainet.legal.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.legalReportRequestDTO;

@Component
@Scope("session")
public class CasePendencyReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private List<TbDepartment> departmentList =new ArrayList<>();

	private legalReportRequestDTO legalReport = new legalReportRequestDTO();

	private List<legalReportRequestDTO> legalReportDto = new ArrayList<>();
	
	private List<AdvocateMasterDTO> advocateName= new ArrayList<>();
	
	private List<FinYearDTO> financialYearList = new ArrayList<>();
	
	/*
	 * private CourtMasterDTO courtMasterDTO =new CourtMasterDTO();
	 * 
	 * private List<CourtMasterDTO> courtMasterListDTO =new ArrayList<>();
	 */

	
	private CaseEntryDTO caseEntryDto =new CaseEntryDTO();

	private List<CaseEntryDTO> caseEntryListDto =new ArrayList<>();
	
	
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

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public List<AdvocateMasterDTO> getAdvocateName() {
		return advocateName;
	}

	public void setAdvocateName(List<AdvocateMasterDTO> advocateName) {
		this.advocateName = advocateName;
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

	/*
	 * public CourtMasterDTO getCourtMasterDTO() { return courtMasterDTO; }
	 * 
	 * public void setCourtMasterDTO(CourtMasterDTO courtMasterDTO) {
	 * this.courtMasterDTO = courtMasterDTO; }
	 * 
	 * public List<CourtMasterDTO> getCourtMasterListDTO() { return
	 * courtMasterListDTO; }
	 * 
	 * public void setCourtMasterListDTO(List<CourtMasterDTO> courtMasterListDTO) {
	 * this.courtMasterListDTO = courtMasterListDTO; }
	 */

	
	

}
