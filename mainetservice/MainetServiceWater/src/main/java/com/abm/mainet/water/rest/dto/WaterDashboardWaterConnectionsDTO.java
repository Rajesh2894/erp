package com.abm.mainet.water.rest.dto;

import java.util.List;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardWaterConnectionsDTO {

	private String groupBy;

	private List<WaterDashboardBucketDTO> buckets;

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public List<WaterDashboardBucketDTO> getBuckets() {
		return buckets;
	}

	public void setBuckets(List<WaterDashboardBucketDTO> buckets) {
		this.buckets = buckets;
	}
	
	@Override
	public String toString() {
		return "WaterDashboardWaterConnectionsDTO [groupBy=" + groupBy + ", buckets=" + buckets + "]";
	}
	
}
