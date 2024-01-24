/**
 * 
 */
package com.abm.mainet.validitymaster.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.validitymaster.domain.LicenseValidityMasterEntity;
import com.abm.mainet.validitymaster.repository.LicenseValidityMasterRepository;

/**
 * @author cherupelli.srikanth
 *
 */
@Repository
public class LicenseValidityMasterDaoImpl extends AbstractDAO<LicenseValidityMasterRepository>
		implements ILicenseValidityMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<LicenseValidityMasterEntity> searchLicenseValidtyData(Long orgId, Long deptId, Long serviceId,
			Long triCod1, Long licType) {
		List<LicenseValidityMasterEntity> entity = null;
		StringBuilder hqlQuery = new StringBuilder(
				"SELECT lm FROM LicenseValidityMasterEntity lm WHERE lm.orgId=:orgId");

		if (deptId > 0) {
			hqlQuery.append(" and lm.deptId=:deptId");
		}
		if (serviceId > 0) {
			hqlQuery.append(" and lm.serviceId=:serviceId");
		}
		if (triCod1 > 0) {
			hqlQuery.append(" and lm.triCod1=:triCod1");
		}
		if (licType > 0) {
			hqlQuery.append(" and lm.licType=:licType");
		}
		

		final Query query = this.createQuery(hqlQuery.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
		if (deptId > 0) {
			query.setParameter(MainetConstants.Common_Constant.DEPTID, deptId);
		}
		if (serviceId > 0) {
			query.setParameter(MainetConstants.Common_Constant.SERVICEID, serviceId);
		}
		// added for The system should have provision to define Item Category and
		// Sub-category in the license validity master User Story #113614
		if (triCod1 > 0) {
			query.setParameter(MainetConstants.Common_Constant.TRI_COD1, triCod1);
		}
		if (licType > 0) {
			query.setParameter("licType", licType);
		}
		entity = (List<LicenseValidityMasterEntity>) query.getResultList();
		return entity;
	}

}
