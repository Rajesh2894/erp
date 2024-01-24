package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CriteriaDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4500287837072085467L;
	
	List<Long> factrors = new ArrayList<>();
	List<Long> criterias = new ArrayList<>();
	Long serviceId;
	Long orgId;
	public List<Long> getFactrors() {
		return factrors;
	}
	public void setFactrors(List<Long> factrors) {
		this.factrors = factrors;
	}
	public List<Long> getCriterias() {
		return criterias;
	}
	public void setCriterias(List<Long> criterias) {
		this.criterias = criterias;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	
	
	
	

}
