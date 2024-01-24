/**
 * 
 */
package com.abm.mainet.additionalservices.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.domain.EChallanMasterEntity;

/**
 * @author divya.marshettiwar
 *
 */
@SuppressWarnings("unused")
public interface EChallanEntryDao {

	public List<EChallanMasterEntity> searchData(String challanNo, String raidNo, String offenderName,
			String offenderMobNo, String challanType, Long orgid);
	
	public List<EChallanMasterEntity> challanDetailsFromDates(Date challanFromDate, Date challanToDate, String challanType);
	
	public List<EChallanMasterEntity> searchRaidData(String raidNo, String offenderName,
			String offenderMobNo, String challanType, Long orgid);
	
	public List<EChallanMasterEntity> raidDetailsFromDates(Date challanFromDate, Date challanToDate, String challanType);
}
