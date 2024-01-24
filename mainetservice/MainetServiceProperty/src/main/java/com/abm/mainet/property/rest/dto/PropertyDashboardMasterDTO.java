package com.abm.mainet.property.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.property.rest.dto.PropertyDashboardRequestInfoDTO;
import com.abm.mainet.property.rest.dto.PropertyDashboardResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardMasterDTO implements Serializable{
	
	private static final long serialVersionUID = -6302709822967685417L;
	
	@JsonProperty("RequestInfo")
	private PropertyDashboardRequestInfoDTO RequestInfo;
	
	@JsonProperty("DATA")
	private List<PropertyDashboardResponseDTO> DATA;

	public PropertyDashboardRequestInfoDTO getRequestInfo() {
		return RequestInfo;
	}

	public void setRequestInfo(PropertyDashboardRequestInfoDTO requestInfo) {
		RequestInfo = requestInfo;
	}

	public List<PropertyDashboardResponseDTO> getDATA() {
		return DATA;
	}

	public void setDATA(List<PropertyDashboardResponseDTO> dATA) {
		DATA = dATA;
	}

	
}
