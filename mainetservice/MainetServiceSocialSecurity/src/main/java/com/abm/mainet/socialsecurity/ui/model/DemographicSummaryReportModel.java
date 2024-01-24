package com.abm.mainet.socialsecurity.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.socialsecurity.ui.dto.PensionSchemeMasterDto;
import com.abm.mainet.socialsecurity.ui.dto.ViewDtoList;

@Component
@Scope("session")
public class DemographicSummaryReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private PensionSchemeMasterDto pensionSchemeDto = new PensionSchemeMasterDto();

	private List<PensionSchemeMasterDto> pensionSchemList = new ArrayList<>();
	private List<Object[]> serviceList = new ArrayList<>();
	private List<ViewDtoList> viewList = new ArrayList<>();
	
	private List<FinYearDTO> financialYearList = new ArrayList<>();
	
	public PensionSchemeMasterDto getPensionSchemeDto() {
		return pensionSchemeDto;
	}
	public void setPensionSchemeDto(PensionSchemeMasterDto pensionSchemeDto) {
		this.pensionSchemeDto = pensionSchemeDto;
	}
	public List<PensionSchemeMasterDto> getPensionSchemList() {
		return pensionSchemList;
	}
	public void setPensionSchemList(List<PensionSchemeMasterDto> pensionSchemList) {
		this.pensionSchemList = pensionSchemList;
	}
	public List<Object[]> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<Object[]> serviceList) {
		this.serviceList = serviceList;
	}
	public List<ViewDtoList> getViewList() {
		return viewList;
	}
	public void setViewList(List<ViewDtoList> viewList) {
		this.viewList = viewList;
	}
	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}
	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}
	

	

}
