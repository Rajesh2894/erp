package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;

@Component
@Scope("session")
public class SummaryDemandCollectionOutstandingModel extends AbstractFormModel{
	
private static final long serialVersionUID = 1354115025982990215L;
private PropertyReportRequestDto propertyRequestDto=new PropertyReportRequestDto();
private List<PropertyReportRequestDto> propertyDto =new ArrayList<>();
public PropertyReportRequestDto getPropertyRequestDto() {
	return propertyRequestDto;
}
public void setPropertyRequestDto(PropertyReportRequestDto propertyRequestDto) {
	this.propertyRequestDto = propertyRequestDto;
}
public List<PropertyReportRequestDto> getPropertyDto() {
	return propertyDto;
}
public void setPropertyDto(List<PropertyReportRequestDto> propertyDto) {
	this.propertyDto = propertyDto;
}



}

 




	

