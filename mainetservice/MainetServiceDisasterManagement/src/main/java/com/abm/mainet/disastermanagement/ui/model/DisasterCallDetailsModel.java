package com.abm.mainet.disastermanagement.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.disastermanagement.dto.DisasterCallDetailsDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DisasterCallDetailsModel extends AbstractFormModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764359859549607009L;

	DisasterCallDetailsDTO disasterCallDetailsDTO=new DisasterCallDetailsDTO();

	public DisasterCallDetailsDTO getDisasterCallDetailsDTO() {
		return disasterCallDetailsDTO;
	}

	public void setDisasterCallDetailsDTO(DisasterCallDetailsDTO disasterCallDetailsDTO) {
		this.disasterCallDetailsDTO = disasterCallDetailsDTO;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
