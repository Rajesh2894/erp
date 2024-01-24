package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;

@Repository
public class GrnInspectionDaoImpl extends AbstractDAO<GoodsReceivedNotesEntity> implements GrnInspectionDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> searchGRNInspectionData(Long storeId, Long grnid, Date fromDate, Date toDate, Long poid,
			Long orgId) {
		Query query = this.createQuery(buildQuery(storeId, grnid, fromDate, toDate, poid, orgId));
		query.setParameter("orgId", orgId);
		if (null != storeId && storeId != 0)
			query.setParameter("storeId", storeId);
		if (null != grnid && grnid != 0)
			query.setParameter("grnid", grnid);
		if (null != fromDate)
			query.setParameter("fromDate", fromDate);
		if (null != toDate)
			query.setParameter("toDate", toDate);
		if (null != poid && poid != 0)
			query.setParameter("poid", poid);
		return query.getResultList();
	}

	private String buildQuery(Long storeId, Long grnid, Date fromDate, Date toDate, Long poid, Long orgId) {
		StringBuilder query = new StringBuilder(
				"Select grn.grnid, grn.grnno, grn.inspectiondate, grn.poid, grn.storeid, "
						+ " grn.Status from GoodsReceivedNotesEntity grn where grn.orgId=:orgId ");

		if (null != storeId && storeId != 0)
			query.append(" AND grn.storeid=:storeId");
		if (null != grnid && grnid != 0)
			query.append(" AND grn.grnid=:grnid");
		if (null != fromDate)
			query.append(" AND grn.inspectiondate >= :fromDate ");
		if (null != toDate)
			query.append(" AND grn.inspectiondate <= :toDate ");
		if (null != poid && poid != 0)
			query.append(" AND grn.poid=:poid");
		query.append(" AND grn.Status in ('D', 'I')");
		query.append(" ORDER BY grn.grnid DESC ");
		return query.toString();
	}

}
