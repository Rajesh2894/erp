
package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;

public class ViewDtoList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3495005225336356872L;
	Long id;
	Long srNumber;
	String schemeCode;
	String schemeName;
	Long orgId;
	Long serviceId;
	/**
	 * @return the srNumber
	 */
	public Long getSrNumber() {
		return srNumber;
	}
	/**
	 * @param srNumber the srNumber to set
	 */
	public void setSrNumber(Long srNumber) {
		this.srNumber = srNumber;
	}
	/**
	 * @return the schemeCode
	 */
	public String getSchemeCode() {
		return schemeCode;
	}
	/**
	 * @param schemeCode the schemeCode to set
	 */
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	/**
	 * @return the schemeName
	 */
	public String getSchemeName() {
		return schemeName;
	}
	/**
	 * @param schemeName the schemeName to set
	 */
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	
	
	
	

}
