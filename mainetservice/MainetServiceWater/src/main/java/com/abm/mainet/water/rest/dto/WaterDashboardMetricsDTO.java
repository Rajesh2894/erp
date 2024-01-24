package com.abm.mainet.water.rest.dto;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */


public class WaterDashboardMetricsDTO {
	@JsonProperty("StipulatedDays")
	private Long stipulatedDays;
	private Long transactions;
	private List<WaterDashboardConnectionsCreatedDTO> connectionsCreated;
	private List<WaterDashboardTodaysCollectionDTO> todaysCollection;
	private List<WaterDashboardWaterConnectionsDTO> waterConnections;
	private List<WaterDashboardWaterSewerageConnectionsDTO> sewerageConnections;
	private List<WaterDashboardPendingConnectionsDTO> pendingConnections;
	private Long slaCompliance;
	private Long todaysTotalApplications;
	private Long todaysClosedApplications;
	private Long todaysCompletedApplicationsWithinSLA;
	private Long avgDaysForApplicationApproval;
	public Long getTransactions() {
		return transactions;
	}
	public void setTransactions(Long transactions) {
		this.transactions = transactions;
	}
	public List<WaterDashboardConnectionsCreatedDTO> getConnectionsCreated() {
		return connectionsCreated;
	}
	public void setConnectionsCreated(List<WaterDashboardConnectionsCreatedDTO> connectionsCreated) {
		this.connectionsCreated = connectionsCreated;
	}
	public List<WaterDashboardTodaysCollectionDTO> getTodaysCollection() {
		return todaysCollection;
	}
	public void setTodaysCollection(List<WaterDashboardTodaysCollectionDTO> todaysCollection) {
		this.todaysCollection = todaysCollection;
	}

	/*
	 * public List<WaterDashboardWaterConnectionsDTO> getWaterConnections() { return
	 * waterConnections; } public void
	 * setWaterConnections(List<WaterDashboardWaterConnectionsDTO> waterConnections)
	 * { this.waterConnections = waterConnections; }
	 */
	public List<WaterDashboardPendingConnectionsDTO> getPendingConnections() {
		return pendingConnections;
	}
	public void setPendingConnections(List<WaterDashboardPendingConnectionsDTO> pendingConnections) {
		this.pendingConnections = pendingConnections;
	}
	public Long getSlaCompliance() {
		return slaCompliance;
	}
	public void setSlaCompliance(Long slaCompliance) {
		this.slaCompliance = slaCompliance;
	}
	public Long getTodaysTotalApplications() {
		return todaysTotalApplications;
	}
	public void setTodaysTotalApplications(Long todaysTotalApplications) {
		this.todaysTotalApplications = todaysTotalApplications;
	}
	public Long getTodaysClosedApplications() {
		return todaysClosedApplications;
	}
	public void setTodaysClosedApplications(Long todaysClosedApplications) {
		this.todaysClosedApplications = todaysClosedApplications;
	}
	public Long getTodaysCompletedApplicationsWithinSLA() {
		return todaysCompletedApplicationsWithinSLA;
	}
	public void setTodaysCompletedApplicationsWithinSLA(Long todaysCompletedApplicationsWithinSLA) {
		this.todaysCompletedApplicationsWithinSLA = todaysCompletedApplicationsWithinSLA;
	}
	@Override
	public String toString() {
		return "WaterDashboardMetricsDTO [transactions=" + transactions + ", connectionsCreated=" + connectionsCreated
				+ ", todaysCollection=" + todaysCollection + ", pendingConnections=" + pendingConnections
				+ ", slaCompliance=" + slaCompliance + ", todaysTotalApplications=" + todaysTotalApplications
				+ ", todaysClosedApplications=" + todaysClosedApplications + ", todaysCompletedApplicationsWithinSLA="
				+ todaysCompletedApplicationsWithinSLA + "]";
	}
	public List<WaterDashboardWaterConnectionsDTO> getWaterConnections() {
		return waterConnections;
	}
	public void setWaterConnections(List<WaterDashboardWaterConnectionsDTO> waterConnections) {
		this.waterConnections = waterConnections;
	}
	public List<WaterDashboardWaterSewerageConnectionsDTO> getSewerageConnections() {
		return sewerageConnections;
	}
	public void setSewerageConnections(List<WaterDashboardWaterSewerageConnectionsDTO> sewerageConnections) {
		this.sewerageConnections = sewerageConnections;
	}
	public Long getAvgDaysForApplicationApproval() {
		return avgDaysForApplicationApproval;
	}
	public void setAvgDaysForApplicationApproval(Long avgDaysForApplicationApproval) {
		this.avgDaysForApplicationApproval = avgDaysForApplicationApproval;
	}
	public Long getStipulatedDays() {
		return stipulatedDays;
	}
	public void setStipulatedDays(Long stipulatedDays) {
		this.stipulatedDays = stipulatedDays;
	}
	
	
	
}