package com.abm.mainet.disastermanagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.disastermanagement.dto.AllAttendedDisasterDTO;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class AllAttendedDisasterModel extends AbstractFormModel{
	
	private static final long serialVersionUID = 7122043210507575324L;
	
	private AllAttendedDisasterDTO allAttendedDisasterDTO;

	public AllAttendedDisasterDTO getAllAttendedDisasterDTO() {
		return allAttendedDisasterDTO;
	}

	public void setAllAttendedDisasterDTO(AllAttendedDisasterDTO allAttendedDisasterDTO) {
		this.allAttendedDisasterDTO = allAttendedDisasterDTO;
	}

	
	
	
	
}
