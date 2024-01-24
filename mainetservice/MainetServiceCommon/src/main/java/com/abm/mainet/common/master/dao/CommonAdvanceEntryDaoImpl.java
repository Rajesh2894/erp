package com.abm.mainet.common.master.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.domain.AdvanceEntryEntity;
import com.abm.mainet.common.integration.acccount.domain.AdvanceRequisition;
import com.abm.mainet.common.integration.dao.RestDaoImpl;

@Repository
public class CommonAdvanceEntryDaoImpl extends RestDaoImpl<AdvanceEntryEntity> implements CommonAdvanceEntryDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<AdvanceRequisition> getFilterRequisition(Date advanceEntryDate, Long vendorId, Long deptId,
			Long orgId) {

		List<AdvanceRequisition> entity = new ArrayList<>();
		StringBuilder hql = new StringBuilder("SELECT ar FROM AdvanceRequisition ar  where ar.orgid = :orgId ");

		if (advanceEntryDate != null) {
			hql.append("and ar.entryDate = :entryDate ");
		}
		if (vendorId != null && vendorId != 0) {
			hql.append("and ar.venderId = :venderId ");
		}
		if (deptId != null && deptId != 0) {
			hql.append("and ar.deptId = :deptId ");
		}

		final Query query = createQuery(hql.toString());
		query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);

		if (advanceEntryDate != null) {
			query.setParameter("entryDate", advanceEntryDate);
		}
		if (vendorId != null && vendorId != 0) {
			query.setParameter("venderId", vendorId);
		}
		if (deptId != null && deptId != 0) {
			query.setParameter("deptId", deptId);
		}
		entity = (List<AdvanceRequisition>) query.getResultList();

		return entity;
	}
}
