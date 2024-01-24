package com.abm.mainet.swm.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.swm.dto.BeatMasterDTO;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class RouteModel extends AbstractFormModel implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	BeatMasterDTO routemstr = new BeatMasterDTO();
	private List<LocationDTO> location = new ArrayList<>();	

	public List<LocationDTO> getLocation() {
		return location;
	}

	public void setLocation(List<LocationDTO> location) {
		this.location = location;
	}

	public BeatMasterDTO getRoutemstr() {
		return routemstr;
	}

	public void setRoutemstr(BeatMasterDTO routemstr) {
		this.routemstr = routemstr;
	}

}
