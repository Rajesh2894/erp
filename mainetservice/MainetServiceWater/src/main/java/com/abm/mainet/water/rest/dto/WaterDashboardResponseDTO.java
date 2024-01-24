package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public class WaterDashboardResponseDTO implements Serializable {

    private static final long serialVersionUID = 363628731673659628L;
    
	private String date;
	
    private String module;

	private String ward;
	
	private String ulb;
	
	private String region;

	private String state;
	
    private WaterDashboardMetricsDTO metrics;


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

	public WaterDashboardMetricsDTO getMetrics() {
		return metrics;
	}

	public void setMetrics(WaterDashboardMetricsDTO metrics) {
		this.metrics = metrics;
	}
    
   
}
