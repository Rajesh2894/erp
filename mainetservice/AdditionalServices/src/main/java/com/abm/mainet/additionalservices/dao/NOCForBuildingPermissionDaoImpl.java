package com.abm.mainet.additionalservices.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.abm.mainet.additionalservices.domain.NOCForBuildingPermissionEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;

@Repository
public class NOCForBuildingPermissionDaoImpl extends AbstractDAO<NOCForBuildingPermissionEntity> implements NOCForBuildingPermissionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<NOCForBuildingPermissionEntity> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate,
			Long orgId,String refNo) {
		final StringBuilder builder = new StringBuilder();

		builder.append("select br from NOCForBuildingPermissionEntity br where br.orgId=:orgId");

		if ((apmApplicationId != null) && (apmApplicationId !=0)) {
			builder.append(" and br.apmApplicationId =:apmApplicationId");
		}
		
		if ((refNo != null) && (!refNo.isEmpty())) {
			builder.append(" and br.refNo =:refNo");
		}

		if ((fromDate != null)) {
			builder.append(" and br.date >=:fromDate");
		}
		if ((toDate != null)) {
			builder.append(" and br.date <=:toDate");
		}
		final Query query = entityManager.createQuery(builder.toString());

		query.setParameter("orgId", orgId);

		if ((apmApplicationId != null) && (apmApplicationId !=0)) {
			query.setParameter("apmApplicationId", apmApplicationId);

		}
		if ((fromDate != null)) {
			query.setParameter("fromDate", fromDate);
		}
		if ((toDate != null)) {
			query.setParameter("toDate", toDate);
		}
		
		if ((refNo != null)&& (!refNo.isEmpty())) {
			query.setParameter("refNo", refNo);
		}
		
		
		List<NOCForBuildingPermissionEntity> birthRegDetail = null;

		try {
			birthRegDetail = query.getResultList();
		} catch (Exception e) {

		}
		return birthRegDetail;
	}

}
