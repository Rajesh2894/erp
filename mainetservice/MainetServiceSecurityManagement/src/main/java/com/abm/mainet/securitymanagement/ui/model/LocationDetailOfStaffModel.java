package com.abm.mainet.securitymanagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.securitymanagement.dto.LocationDetailsOfStaffDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class LocationDetailOfStaffModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocationDetailsOfStaffDTO locationDetailsOfStaffDTO=new LocationDetailsOfStaffDTO();
	
	public LocationDetailsOfStaffDTO getLocationDetailsOfStaffDTO() {
		return locationDetailsOfStaffDTO;
	}
	public void setLocationDetailsOfStaffDTO(LocationDetailsOfStaffDTO locationDetailsOfStaffDTO) {
		this.locationDetailsOfStaffDTO = locationDetailsOfStaffDTO;
	}
}
