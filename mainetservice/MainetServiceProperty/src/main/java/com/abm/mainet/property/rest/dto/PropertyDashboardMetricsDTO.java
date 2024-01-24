package com.abm.mainet.property.rest.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Mithila.Jondhale
 * @since 03-08-2023
 */

public class PropertyDashboardMetricsDTO implements Serializable{
	
	private static final long serialVersionUID = -6302709822967685417L;
	
	@JsonProperty("StipulatedDays")
	private Long stipulatedDays;
	private Long assessments;
	private Long noOfPropertiesPaidToday;
	private Long todaysTotalApplications;
	private Long todaysClosedApplications;
	private Long todaysApprovedApplications;
	private Long todaysApprovedApplicationsWithinSLA;
	private Long avgDaysForApplicationApproval;
	
	private List<PropertyDashboardTodaysCollectionDTO> todaysCollection;
    private List<PropertyDashboardTodaysMovedApplDTO> todaysMovedApplications;
    private List<PropertyDashboardPropertiesRegisteredDTO> propertiesRegistered;
    private List<PropertyDashboardAssessedPropertiesDTO> assessedProperties;
    private List<PropertyDashboardTransactionsDTO> transactions;
    private List<PropertyDashboardPropertyTaxDTO> propertyTax;
    private List<PropertyDashboardCessDTO> cess;
    private List<PropertyDashboardPenaltyDTO> penalty;
    private List<PropertyDashboardInterestDTO> interest;
    private List<PropertyDashboardRebateDTO> rebate;
    

	public Long getAssessments() {
		return assessments;
	}
	public void setAssessments(Long assessments) {
		this.assessments = assessments;
	}
	public Long getNoOfPropertiesPaidToday() {
		return noOfPropertiesPaidToday;
	}
	public void setNoOfPropertiesPaidToday(Long noOfPropertiesPaidToday) {
		this.noOfPropertiesPaidToday = noOfPropertiesPaidToday;
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
	public Long getTodaysApprovedApplications() {
		return todaysApprovedApplications;
	}
	public void setTodaysApprovedApplications(Long todaysApprovedApplications) {
		this.todaysApprovedApplications = todaysApprovedApplications;
	}
	public Long getTodaysApprovedApplicationsWithinSLA() {
		return todaysApprovedApplicationsWithinSLA;
	}
	public void setTodaysApprovedApplicationsWithinSLA(Long todaysApprovedApplicationsWithinSLA) {
		this.todaysApprovedApplicationsWithinSLA = todaysApprovedApplicationsWithinSLA;
	}
	public Long getAvgDaysForApplicationApproval() {
		return avgDaysForApplicationApproval;
	}
	public void setAvgDaysForApplicationApproval(Long avgDaysForApplicationApproval) {
		this.avgDaysForApplicationApproval = avgDaysForApplicationApproval;
	}
	public List<PropertyDashboardTodaysCollectionDTO> getTodaysCollection() {
		return todaysCollection;
	}
	public void setTodaysCollection(List<PropertyDashboardTodaysCollectionDTO> todaysCollection) {
		this.todaysCollection = todaysCollection;
	}
	public List<PropertyDashboardTodaysMovedApplDTO> getTodaysMovedApplications() {
		return todaysMovedApplications;
	}
	public void setTodaysMovedApplications(List<PropertyDashboardTodaysMovedApplDTO> todaysMovedApplications) {
		this.todaysMovedApplications = todaysMovedApplications;
	}
	public List<PropertyDashboardPropertiesRegisteredDTO> getPropertiesRegistered() {
		return propertiesRegistered;
	}
	public void setPropertiesRegistered(List<PropertyDashboardPropertiesRegisteredDTO> propertiesRegistered) {
		this.propertiesRegistered = propertiesRegistered;
	}
	public List<PropertyDashboardAssessedPropertiesDTO> getAssessedProperties() {
		return assessedProperties;
	}
	public void setAssessedProperties(List<PropertyDashboardAssessedPropertiesDTO> assessedProperties) {
		this.assessedProperties = assessedProperties;
	}
	public List<PropertyDashboardTransactionsDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<PropertyDashboardTransactionsDTO> transactions) {
		this.transactions = transactions;
	}
	public List<PropertyDashboardPropertyTaxDTO> getPropertyTax() {
		return propertyTax;
	}
	public void setPropertyTax(List<PropertyDashboardPropertyTaxDTO> propertyTax) {
		this.propertyTax = propertyTax;
	}
	public List<PropertyDashboardCessDTO> getCess() {
		return cess;
	}
	public void setCess(List<PropertyDashboardCessDTO> cess) {
		this.cess = cess;
	}
	public List<PropertyDashboardPenaltyDTO> getPenalty() {
		return penalty;
	}
	public void setPenalty(List<PropertyDashboardPenaltyDTO> penalty) {
		this.penalty = penalty;
	}
	public List<PropertyDashboardInterestDTO> getInterest() {
		return interest;
	}
	public void setInterest(List<PropertyDashboardInterestDTO> interest) {
		this.interest = interest;
	}
	public List<PropertyDashboardRebateDTO> getRebate() {
		return rebate;
	}
	public void setRebate(List<PropertyDashboardRebateDTO> rebate) {
		this.rebate = rebate;
	}
	public Long getStipulatedDays() {
		return stipulatedDays;
	}
	public void setStipulatedDays(Long stipulatedDays) {
		this.stipulatedDays = stipulatedDays;
	}
    
	
    
}
