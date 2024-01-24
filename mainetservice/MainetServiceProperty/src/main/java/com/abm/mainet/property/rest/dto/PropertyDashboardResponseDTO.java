package com.abm.mainet.property.rest.dto;

import java.io.Serializable;

import com.abm.mainet.property.rest.dto.PropertyDashboardMetricsDTO;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardResponseDTO implements Serializable {

	  private static final long serialVersionUID = 363628731673659628L;
	    
		private String date;
		
	    private String module;

		private String ward;
		
		private String ulb;
		
		private String region;

		private String state;
		
	    private PropertyDashboardMetricsDTO metrics;


		public String getModule() {
			return module;
		}

		public void setModule(String module) {
			this.module = module;
		}

		public String getWard() {
			return ward;
		}

		public void setWard(String ward) {
			this.ward = ward;
		}

		public String getUlb() {
			return ulb;
		}

		public void setUlb(String ulb) {
			this.ulb = ulb;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public PropertyDashboardMetricsDTO getMetrics() {
			return metrics;
		}

		public void setMetrics(PropertyDashboardMetricsDTO metrics) {
			this.metrics = metrics;
		}
	    
}
