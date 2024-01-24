package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.securitymanagement.domain.DeploymentOfStaff;

@Repository
public class DeploymentOfStaffDaoImpl extends AbstractDAO<DeploymentOfStaff> implements IDeploymentOfStaffDao {

	public List<DeploymentOfStaff> buildQuery(Long empTypeId, Long vendorId, Long cpdShiftId, Long locId, Long orgid) {
		
		StringBuilder stringQuery =  new StringBuilder(" select ds from DeploymentOfStaff ds where ds.orgid=:orgid ");

		if (cpdShiftId != null && cpdShiftId != 0) {
			stringQuery.append(" AND ds.cpdShiftId =:cpdShiftId ");
		}
		if (locId != null && locId != 0) {
			stringQuery.append(" AND ds.locId =:locId ");
		}
		if (vendorId != null && vendorId != 0) {
			stringQuery.append(" AND ds.vendorId=:vendorId ");
		}
		if (empTypeId != null && empTypeId != 0) {
			stringQuery.append(" AND ds.empTypeId =:empTypeId ");
		}
		stringQuery.append(" order by ds.deplId desc");

		final Query query = entityManager.createQuery(stringQuery.toString());
		query.setParameter("orgid", orgid);

		if (empTypeId != null && empTypeId != 0) {
			query.setParameter("empTypeId", empTypeId);
		}
		if (cpdShiftId != null && cpdShiftId != 0) {
			query.setParameter("cpdShiftId", cpdShiftId);
		}

		if (locId != null && locId != 0) {
			query.setParameter("locId", locId);
		}
		if (vendorId != null && vendorId != 0) {
			query.setParameter("vendorId", vendorId);
		}
		List<DeploymentOfStaff> detail = query.getResultList();
		return detail;
	}

}
