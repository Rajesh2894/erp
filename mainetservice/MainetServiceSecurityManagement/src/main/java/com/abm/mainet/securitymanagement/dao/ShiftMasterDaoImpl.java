package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;

@Repository
public class ShiftMasterDaoImpl extends AbstractDAO<ShiftMaster> implements IShiftMasterDao {

	@Override
	public List<ShiftMaster> searchShift(Long shiftId, Long orgid) {

		StringBuilder buildQuery = new StringBuilder(" select sm from ShiftMaster sm where sm.orgid=:orgid");

		if (shiftId != null && shiftId != 0) {
			buildQuery.append(" AND sm.shiftId=:shiftId ");
		}
	
		final Query query = entityManager.createQuery(buildQuery.toString());
		query.setParameter("orgid", orgid);
		
		if (shiftId != null && shiftId != 0) {
			query.setParameter("shiftId", shiftId);
		}
		List<ShiftMaster> list = query.getResultList();
		return list;
	}

}
