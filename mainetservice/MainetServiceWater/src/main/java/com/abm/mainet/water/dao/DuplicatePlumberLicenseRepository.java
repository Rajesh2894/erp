/**
 * 
 */
package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.water.domain.PlumberMaster;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface DuplicatePlumberLicenseRepository {
	//TbDuplicatePlumberLicenseEntity saveDuplicatePlumberLicenseDetails(TbDuplicatePlumberLicenseEntity entity);

	public Long findDuplicatePlumberIdByApplicationNumber(final Long applicationNumber, final Long orgId);

	public List<PlumberMaster> getPlumberDetailsByPlumId(Long plumId, Long orgid);

	public void updateApprovalStatus(Long applicationNumber, String appovalStatus, Date date);

	//public TbDuplicatePlumberLicenseEntity findLatestDuplicateLicenseByPlumId(Long plumId, Long orgid);

	/* public List<PlumberMaster> getPlumberDetailsByPlumId(Long plumId); */

	public List<PlumberMaster> getPlumberDetailsByApplicationId(final Long applicationNumber, final Long orgId);

	Object getchecklistStatusInApplicationMaster(Long apmApplicationId, Long orgid);
}
