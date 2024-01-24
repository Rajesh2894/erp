package com.abm.mainet.tradeLicense.dao;

import java.util.Date;
import java.util.List;
import com.abm.mainet.tradeLicense.domain.TbMlTradeMast;

public interface ITradeLicenseApplicationDao {

	/**
	 * used to Filter New Trade Licence List
	 * 
	 * @param oldLicenseNo
	 * @param businessName
	 * @param ward1
	 * @param ward2
	 * @param ward3
	 * @param ward4
	 * @param ward5
	 * @param orgId
	 * @return
	 */
	List<Object[]> getFilteredNewTradeLicenceList(Long licenseType, String oldLicenseNo, String newLicenseNo,
			Long ward1, Long ward2, Long ward3, Long ward4, Long ward5, Long orgId ,String busName, String ownerName);

	/**
	 * used to get Payment mode from loino
	 * 
	 * @param orgId
	 * @param loiNo
	 * @return
	 */

	List<Object[]> getpaymentMode(Long orgId, String loiNo);

	List<Object[]> getpaymentModeByRcptId(Long orgId, long loiNo);


	List<TbMlTradeMast> getLicenseDetailbyLicAndMobile(String trdLicno,String mobileNo, Long orgId);

	List<Object[]> getpaymentModeByAppId(Long orgId, Long applicationId);

	List<Object> getEmployeeIdList(Long orgId, Long ward1, Long ward2, Long ward3, Long ward4, Long ward5);
	
	List<TbMlTradeMast> fetchLicenseDetails(Long orgId, Long licType, String licNo, Date currDate, Long long1, Long long2, Long long3, Long long4, Long long5);
}
