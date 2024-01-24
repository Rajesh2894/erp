package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.IndentProcessEntity;

@Repository
public class IndentProcessDaoImpl extends AbstractDAO<IndentProcessEntity> implements IndentProcessDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Object[]> getIndentSummaryList(Long storeid, String indentno, Long deptId, Long indenter, String status,
			Long orgid) {
		final StringBuilder builder = new StringBuilder();
		builder.append("select i.indentid, i.indentno, i.indentdate, i.indenter, i.storeid, i.status "
				+ " from IndentProcessEntity i where i.orgid=:orgid");
		if (storeid != null)
			builder.append(" and i.storeid=:storeid");
		if (indentno != null && !indentno.equals(""))
			builder.append(" and i.indentno=:indentno");
		if (deptId != null)
			builder.append(" and i.deptId=:deptId");
		if (indenter != null)
			builder.append(" and i.indenter=:indenter");
		if (status != null && !status.equals("0"))
			builder.append(" and i.status=:status");
		builder.append(" order by i.indentid desc");

		final Query query = createQuery(builder.toString());
		query.setParameter("orgid", orgid);
		if (storeid != null)
			query.setParameter("storeid", storeid);
		if (indentno != null && !indentno.equals(""))
			query.setParameter("indentno", indentno);
		if (deptId != null)
			query.setParameter("deptId", deptId);
		if (indenter != null)
			query.setParameter("indenter", indenter);
		if (status != null && !status.equals("0"))
			query.setParameter("status", status);

		return query.getResultList();
	}

}
