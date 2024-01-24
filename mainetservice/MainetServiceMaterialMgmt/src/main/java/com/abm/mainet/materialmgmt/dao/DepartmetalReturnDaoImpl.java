package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;
import com.abm.mainet.materialmgmt.domain.GoodsReceivedNotesEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseOrderEntity;
import com.abm.mainet.materialmgmt.domain.PurchaseRequistionEntity;

@Repository
public class DepartmetalReturnDaoImpl extends AbstractDAO<DeptReturnEntity> implements DepartmentalReturnDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptReturnEntity> searchindentReturnData(String dreturnno, Long indentid, Date fromDate, Date toDate,
			Long storeid, Character status,Long orgid) {

		Query query = this.createQuery(buildQuery( dreturnno, indentid, fromDate,  toDate,
				storeid,status, orgid));

		query.setParameter("orgid", orgid);
		if (null != dreturnno && !dreturnno.isEmpty() ) {
			query.setParameter("dreturnno", dreturnno);
		}
		if (indentid != null) {
			query.setParameter("indentid", indentid);
		}
		
		
		if (null != fromDate) {
			query.setParameter("fromDate", fromDate);
		}
		if (null != toDate) {
			query.setParameter("toDate", toDate);
		}
		
		if (null != storeid) {
			query.setParameter("storeid", storeid);
		}
		
		if (null != status && status != '0') {
			query.setParameter("status", status);
		}
		
		return query.getResultList();
	}

	private String buildQuery(String dreturnno,Long indentid, Date fromDate, Date toDate,
			Long storeid, Character status,Long orgid) {

		StringBuilder DepartmentalReturnSearchQuery = new StringBuilder(
				"SELECT po FROM DeptReturnEntity po WHERE po.orgid = :orgid and po.status != 'Y'");

		if (null != storeid) {
			DepartmentalReturnSearchQuery.append(" AND po.storeid = :storeid");
		}
		
		if (indentid != null) {
			DepartmentalReturnSearchQuery.append(" and po.indentid =:indentid");
		}
		
		if (dreturnno != null && !dreturnno.isEmpty()) {
			DepartmentalReturnSearchQuery.append(" and po.dreturnno =:dreturnno");
		}
		
		if (null != fromDate) {
			DepartmentalReturnSearchQuery.append(" AND po.lmoddate >= :fromDate");
		}
		if (null != toDate) {
			DepartmentalReturnSearchQuery.append(" AND po.lmoddate <= :toDate");
		}
		if (null != status && status != '0') {
			DepartmentalReturnSearchQuery.append(" AND po.status = :status");
		}
		//goodsReceivedNotesSearchQuery.append(" ORDER BY po.grnid DESC ");
		return DepartmentalReturnSearchQuery.toString();
	}

}
