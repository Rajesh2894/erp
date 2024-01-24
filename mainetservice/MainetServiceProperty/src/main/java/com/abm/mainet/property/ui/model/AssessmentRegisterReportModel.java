package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class AssessmentRegisterReportModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	private ProperySearchDto propSearchDto = new ProperySearchDto();

	private List<ProperySearchDto> propSearchDtoList = new ArrayList<>();

	private PropertyReportRequestDto propertyDto = new PropertyReportRequestDto();
	
	private List<PropertyReportRequestDto> propertyDtoList = new ArrayList<>();

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ProperySearchDto getPropSearchDto() {
		return propSearchDto;
	}

	public List<ProperySearchDto> getPropSearchDtoList() {
		return propSearchDtoList;
	}

	public PropertyReportRequestDto getPropertyDto() {
		return propertyDto;
	}

	public List<PropertyReportRequestDto> getPropertyDtoList() {
		return propertyDtoList;
	}

	public void setPropSearchDto(ProperySearchDto propSearchDto) {
		this.propSearchDto = propSearchDto;
	}

	public void setPropSearchDtoList(List<ProperySearchDto> propSearchDtoList) {
		this.propSearchDtoList = propSearchDtoList;
	}

	public void setPropertyDto(PropertyReportRequestDto propertyDto) {
		this.propertyDto = propertyDto;
	}

	public void setPropertyDtoList(List<PropertyReportRequestDto> propertyDtoList) {
		this.propertyDtoList = propertyDtoList;
	}

	

	
}
