package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

@Component
@Scope("session")
public class DetailDemandRegisterModel extends AbstractFormModel {
	private static final long serialVersionUID = 1L;

	private PropertyReportRequestDto propertydto = new PropertyReportRequestDto();

	private List<PropertyReportRequestDto> propertyReportdto = new ArrayList<>();

	private ProperySearchDto propSearchdto = new ProperySearchDto();

	private List<ProperySearchDto> propertySearchDto = new ArrayList<>();

	
	  private Date maxFinDate;
	  private Date minFinDate;
	  
	  private ObjectionDetailsDto objectionDto =new ObjectionDetailsDto();
	
	public PropertyReportRequestDto getPropertydto() {
		return propertydto;
	}

	public void setPropertydto(PropertyReportRequestDto propertydto) {
		this.propertydto = propertydto;
	}

	public List<PropertyReportRequestDto> getPropertyReportdto() {
		return propertyReportdto;
	}

	public void setPropertyReportdto(List<PropertyReportRequestDto> propertyReportdto) {
		this.propertyReportdto = propertyReportdto;
	}

	public ProperySearchDto getPropSearchdto() {
		return propSearchdto;
	}

	public void setPropSearchdto(ProperySearchDto propSearchdto) {
		this.propSearchdto = propSearchdto;
	}

	public List<ProperySearchDto> getPropertySearchDto() {
		return propertySearchDto;
	}

	public void setPropertySearchDto(List<ProperySearchDto> propertySearchDto) {
		this.propertySearchDto = propertySearchDto;
	}

	public Date getMaxFinDate() {
		return maxFinDate;
	}

	public void setMaxFinDate(Date maxFinDate) {
		this.maxFinDate = maxFinDate;
	}

	public Date getMinFinDate() {
		return minFinDate;
	}

	public void setMinFinDate(Date minFinDate) {
		this.minFinDate = minFinDate;
	}

	public ObjectionDetailsDto getObjectionDto() {
		return objectionDto;
	}

	public void setObjectionDto(ObjectionDetailsDto objectionDto) {
		this.objectionDto = objectionDto;
	}
	
}
