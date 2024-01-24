/**
 * 
 */
package com.abm.mainet.common.service;

/**
 * @author akshata.bhat
 *
 */
public interface CFCCommonService {

	public String getConnectionDetailsByAppIdAndOrgId(Long applicationId, long orgId, String serviceCode);

	String getConnectionDetailsByAppIdAndService(Long applicationId, long orgId, String serviceCode);
	
}
