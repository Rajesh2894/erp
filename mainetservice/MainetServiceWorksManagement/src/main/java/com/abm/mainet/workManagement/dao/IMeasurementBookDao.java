/**
 * 
 */
package com.abm.mainet.workManagement.dao;

import java.util.List;

import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface IMeasurementBookDao {
	List<MeasurementBookMaster> filterMeasurementBookData(Long workOrderId, String flag,Long orgId);

	List<MeasurementBookMaster> getAllMbDeatilsBySearch(Long workOrderId, String flag, String mbNo, Long workId,
			Long VendorId, Long orgId);
}
