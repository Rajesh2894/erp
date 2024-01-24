package com.abm.mainet.property.rest.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardTransactionsDTO implements Serializable{
	
	private static final long serialVersionUID = -6302709822967685417L;

	private String groupBy;

	private List<PropertyDashboardBucketDTO> buckets;

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public List<PropertyDashboardBucketDTO> getBuckets() {
		return buckets;
	}

	public void setBuckets(List<PropertyDashboardBucketDTO> buckets) {
		this.buckets = buckets;
	}

}