package com.abm.mainet.water.rest.dto;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardBucketDTO {

		private String name;
		private Long value;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Long getValue() {
			return value;
		}
		public void setValue(Long value) {
			this.value = value;
		}
		@Override
		public String toString() {
			return "WaterDashboardBucketDTO [name=" + name + ", value=" + value + "]";
		}

	
	
}
