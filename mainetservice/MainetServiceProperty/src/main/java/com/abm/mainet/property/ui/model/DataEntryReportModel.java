package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;

@Component
@Scope("session")
public class DataEntryReportModel extends AbstractFormModel{

	private static final long serialVersionUID = 1L;
	
	 private PropertyReportRequestDto propertyDto = new PropertyReportRequestDto();

	    private List<PropertyReportRequestDto> propertyDtoList = new ArrayList<>();

		public PropertyReportRequestDto getPropertyDto() {
			return propertyDto;
		}

		public void setPropertyDto(PropertyReportRequestDto propertyDto) {
			this.propertyDto = propertyDto;
		}

		public List<PropertyReportRequestDto> getPropertyDtoList() {
			return propertyDtoList;
		}

		public void setPropertyDtoList(List<PropertyReportRequestDto> propertyDtoList) {
			this.propertyDtoList = propertyDtoList;
		}
	    
	  }
