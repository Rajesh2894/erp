package com.abm.mainet.common.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

import com.abm.mainet.common.domain.ServiceMaster;

/**
 * @author Vivek.Kumar
 * @since 21 March 2016
 */
@WebService
public interface ServiceMasterService {

	String isChargeApplicable(long serviceId, long orgId);

	/**
	 * use this method to get service action url params
	 * 
	 * @param smServiceId
	 * @param orgId
	 * @return list of params Ex: Abc.html,a,null
	 */
	List<String> getServiceActionUrlParams(long smServiceId, long orgId);

	/**
	 * @param string
	 * @param orgid
	 * @return ServiceMaster
	 */
	ServiceMaster getServiceByShortName(String shortName, long orgid);

	public List<Object[]> findAllActiveServicesByDepartment(Long orgId, Long depId, final Long activeStatusId);

	String fetchServiceShortCode(Long serviceId, Long orgId);

	ServiceMaster getServiceMaster(Long serviceId, Long orgId);

	public ServiceMaster getServiceMasterByShortCode(String shortCode, Long orgId);

	public List<Object[]> findAllActiveServicesForDepartment(final long orgId, final long depId);

	public String getServiceNameByServiceId(final long smServiceId);

	boolean isServiceRTS(Long smServiceId, Long orgId);

	String getProcessName(final Long smServiceId, final Long orgId);

	public List<Object[]> findAllActiveServicesWhichIsNotActual(Long orgId, Long depId, final Long activeStatusId,
			final String notActualFlag);

	/**
	 * Get Dept Id By Service Short Code
	 * 
	 * @param orgId
	 * @param serviceShortCode
	 * @return
	 */
	Long getDeptIdByServiceShortName(Long orgId, String serviceShortCode);

	/**
	 * Get ServiceId Id By Service Short Code
	 * 
	 * @param orgId
	 * @param serviceShortCode
	 * @return
	 */

	Long getServiceIdByShortName(Long orgId, String serviceCode);
	
	Map<String, String> getServiceExternalFlag(Long smServiceId, Long orgId);

	Map<String, String> getDeptIdAndShortCode(Long orgId, String serviceCode);

	
	
}
