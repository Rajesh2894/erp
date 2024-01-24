package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.materialmgmt.domain.ExpiryItemsEntity;

@Repository
public class ExpiredItemDaoImpl extends AbstractDAO<ExpiryItemsEntity> implements ExpiredItemDao {

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ExpiryItemsEntity> searchExpiredDataByAll(Long storeId, String movementNo, String fromDate, String toDate,
			Long orgId) {
		Query query = this.createQuery(buildQuery(storeId, movementNo, fromDate, toDate, orgId));

		query.setParameter("orgId", orgId);

		if (null != storeId) {
			query.setParameter("storeId", storeId);
		}

		if (StringUtils.isNotEmpty(movementNo)) {
			query.setParameter("movementNo", movementNo);
		}

		if (((fromDate != null) && !fromDate.equals(MainetConstants.BLANK))
                || ((toDate != null) && !fromDate.equals(MainetConstants.BLANK))) {

            query.setParameter(MainetConstants.FROM_DATE, UtilityService.convertStringDateToDateFormat(fromDate));
            query.setParameter(MainetConstants.TO_DATE, UtilityService.convertStringDateToDateFormat(toDate));

        }
		return query.getResultList();

	}

	private String buildQuery(Long storeId, String movementNo, String fromDate, String toDate, Long orgId) {

		StringBuilder expiryItemSearchQuery = new StringBuilder(
				" SELECT exp FROM ExpiryItemsEntity exp WHERE exp.orgId = :orgId ");
		if (null != storeId) {
			expiryItemSearchQuery.append(" AND exp.storeId = :storeId");
		}
		if (StringUtils.isNotEmpty(movementNo)) {
			expiryItemSearchQuery.append(" AND exp.movementNo = :movementNo ");
		}
		if (((fromDate != null) && !fromDate.equals(MainetConstants.BLANK))
                || ((toDate != null) && !fromDate.equals(MainetConstants.BLANK))) {

			expiryItemSearchQuery.append(" and exp.lmodDate between :fromDate and :toDate");

        }
		expiryItemSearchQuery.append(" ORDER BY exp.expiryId DESC ");
		return expiryItemSearchQuery.toString();

	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ExpiryItemsEntity> searchExpiredSummaryData(Long storeId, String movementNo, Date fromDate, Date toDate,
			Long orgId) {
		Query query = this.createQuery(buildSummaryQuery(storeId, movementNo, fromDate, toDate, orgId));

		query.setParameter("orgId", orgId);

		if (null != storeId) {
			query.setParameter("storeId", storeId);
		}

		if (StringUtils.isNotEmpty(movementNo)) {
			query.setParameter("movementNo", movementNo);
		}

		if (null != fromDate) {
			query.setParameter("fromDate", fromDate);
		}

		if (null != toDate) {
			query.setParameter("toDate", toDate);
		}
		return query.getResultList();
	}

	private String buildSummaryQuery(Long storeId, String movementNo, Date fromDate, Date toDate, Long orgId) {
		StringBuilder expiryItemSearchQuery = new StringBuilder(
				" SELECT exp FROM ExpiryItemsEntity exp WHERE exp.orgId = :orgId ");
		if (null != storeId) {
			expiryItemSearchQuery.append(" AND exp.storeId = :storeId");
		}
		if (StringUtils.isNotEmpty(movementNo)) {
			expiryItemSearchQuery.append(" AND exp.movementNo = :movementNo ");
		}
		if (null != fromDate) {
			expiryItemSearchQuery.append(" AND exp.movementDate >= :fromDate ");
		}
		if (null != toDate) {
			expiryItemSearchQuery.append(" AND exp.movementDate <= :toDate ");
		}
		
		expiryItemSearchQuery.append(" ORDER BY exp.expiryId DESC ");
		return expiryItemSearchQuery.toString();
	}
}
