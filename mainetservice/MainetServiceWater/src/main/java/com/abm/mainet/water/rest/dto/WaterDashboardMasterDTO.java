package com.abm.mainet.water.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardMasterDTO {
	
		@JsonProperty("RequestInfo")
		private WaterDashboardRequestInfoDTO RequestInfo;
		
		@JsonProperty("DATA")
		private List<WaterDashboardResponseDTO> DATA;

		public WaterDashboardRequestInfoDTO getRequestInfo() {
			return RequestInfo;
		}

		public void setRequestInfo(WaterDashboardRequestInfoDTO requestInfo) {
			RequestInfo = requestInfo;
		}

		public List<WaterDashboardResponseDTO> getDATA() {
			return DATA;
		}

		public void setDATA(List<WaterDashboardResponseDTO> dATA) {
			DATA = dATA;
		}

		

		

}
