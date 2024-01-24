package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class DetailCollectionRegisterModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;

	private PropertyReportRequestDto propertydto = new PropertyReportRequestDto();

	private List<PropertyReportRequestDto> propertyRequestdto = new ArrayList<>();

	private List<LookUp> taxWisweList = new ArrayList<LookUp>();

	private List<FinYearDTO> financialYearList = new ArrayList<>();

	private ProperySearchDto propSearchdto = new ProperySearchDto();

	private List<ProperySearchDto> propertySearchDto = new ArrayList<>();

	private Date maxFineDate;

	private Date minFineDate;

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

	public List<LookUp> getTaxWisweList() {
		return taxWisweList;
	}

	public void setTaxWisweList(List<LookUp> taxWisweList) {
		this.taxWisweList = taxWisweList;
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

	public Date getMaxFineDate() {
		return maxFineDate;
	}

	public Date getMinFineDate() {
		return minFineDate;
	}

	public void setMaxFineDate(Date maxFineDate) {
		this.maxFineDate = maxFineDate;
	}

	public void setMinFineDate(Date minFineDate) {
		this.minFineDate = minFineDate;
	}

}
