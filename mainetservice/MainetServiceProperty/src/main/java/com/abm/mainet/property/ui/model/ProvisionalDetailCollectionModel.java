package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.PropertyReportRequestDto;

@Component
@Scope("session")
public class ProvisionalDetailCollectionModel extends AbstractFormModel{

private static final long serialVersionUID = 1L;

private  PropertyReportRequestDto propertyReportDto =new PropertyReportRequestDto();

private List<PropertyReportRequestDto> propertyReportDtoList =new ArrayList<>();

private List<LookUp> taxWiseList =new ArrayList<LookUp>();

public PropertyReportRequestDto getPropertyReportDto() {
	return propertyReportDto;
}

public void setPropertyReportDto(PropertyReportRequestDto propertyReportDto) {
	this.propertyReportDto = propertyReportDto;
}

public List<PropertyReportRequestDto> getPropertyReportDtoList() {
	return propertyReportDtoList;
}

public void setPropertyReportDtoList(List<PropertyReportRequestDto> propertyReportDtoList) {
	this.propertyReportDtoList = propertyReportDtoList;
}

public List<LookUp> getTaxWiseList() {
	return taxWiseList;
}

public void setTaxWiseList(List<LookUp> taxWiseList) {
	this.taxWiseList = taxWiseList;
}


}

