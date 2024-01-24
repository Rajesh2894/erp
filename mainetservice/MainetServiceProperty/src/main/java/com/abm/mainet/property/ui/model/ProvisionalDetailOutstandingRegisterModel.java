package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;

@Component
@Scope("session")

public class ProvisionalDetailOutstandingRegisterModel extends AbstractFormModel{

private static final long serialVersionUID = 1L;

private PropertyReportRequestDto propertydto =new PropertyReportRequestDto();
private List<PropertyReportRequestDto> propertyListdto = new ArrayList<>();

public PropertyReportRequestDto getPropertydto() {
	return propertydto;
}

public void setPropertydto(PropertyReportRequestDto propertydto) {
	this.propertydto = propertydto;
}

public List<PropertyReportRequestDto> getPropertyListdto() {
	return propertyListdto;
}

public void setPropertyListdto(List<PropertyReportRequestDto> propertyListdto) {
	this.propertyListdto = propertyListdto;
}

}
