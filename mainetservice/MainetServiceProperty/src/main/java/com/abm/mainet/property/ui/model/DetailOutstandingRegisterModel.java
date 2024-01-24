package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class DetailOutstandingRegisterModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private PropertyReportRequestDto propertydto = new PropertyReportRequestDto();

	private List<PropertyReportRequestDto> propertyRequestdto = new ArrayList<>();

	private List<FinYearDTO> financialYearList = new ArrayList<>();

	private ProperySearchDto propSearchdto = new ProperySearchDto();

	private List<ProperySearchDto> propertySearchDto = new ArrayList<>();

	private Map<Long, String> listOfinalcialyear = null;

	public Map<Long, String> getListOfinalcialyear() {
		return listOfinalcialyear;
	}

	public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
		this.listOfinalcialyear = listOfinalcialyear;
	}

	private Date maxFinDate;

	private Date minFinDate;

	private String reportType;

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public PropertyReportRequestDto getPropertydto() {
		return propertydto;
	}

	public void setPropertydto(PropertyReportRequestDto propertydto) {
		this.propertydto = propertydto;
	}

	public List<PropertyReportRequestDto> getPropertyRequestdto() {
		return propertyRequestdto;
	}

	public void setPropertyRequestdto(List<PropertyReportRequestDto> propertyRequestdto) {
		this.propertyRequestdto = propertyRequestdto;
	}

	public List<FinYearDTO> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<FinYearDTO> financialYearList) {
		this.financialYearList = financialYearList;
	}

	public ProperySearchDto getPropSearchdto() {
		return propSearchdto;
	}

	public List<ProperySearchDto> getPropertySearchDto() {
		return propertySearchDto;
	}

	public void setPropSearchdto(ProperySearchDto propSearchdto) {
		this.propSearchdto = propSearchdto;
	}

	public void setPropertySearchDto(List<ProperySearchDto> propertySearchDto) {
		this.propertySearchDto = propertySearchDto;
	}

	public Date getMaxFinDate() {
		return maxFinDate;
	}

	public Date getMinFinDate() {
		return minFinDate;
	}

	public void setMaxFinDate(Date maxFinDate) {
		this.maxFinDate = maxFinDate;
	}

	public void setMinFinDate(Date minFinDate) {
		this.minFinDate = minFinDate;
	}

}
