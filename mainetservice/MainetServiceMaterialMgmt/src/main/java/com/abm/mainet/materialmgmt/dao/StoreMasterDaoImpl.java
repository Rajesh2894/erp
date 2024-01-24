package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.StoreMaster;

@Repository
public class StoreMasterDaoImpl extends AbstractDAO<StoreMaster> implements StoreMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<StoreMaster> searhStoreMasterData(Long locationId, String storeName, Long orgId) {
		// TODO Auto-generated method stub
		Query query = this.createQuery(buildQuery(locationId, storeName, orgId));

		query.setParameter("orgId", orgId);

		if (null != locationId) {
			query.setParameter("locationId", locationId);
		}

		if (StringUtils.isNotEmpty(storeName)) {
			query.setParameter("storeName", storeName);
		}
		return query.getResultList();
	}

	private String buildQuery(Long locationId, String storeName, Long orgId) {

		StringBuilder purchaseRequisitionSearchQuery = new StringBuilder(
				" SELECT st FROM StoreMaster st WHERE st.orgId = :orgId ");

		if (null != locationId) {
			purchaseRequisitionSearchQuery.append(" AND st.location = :locationId");
		}

		if (StringUtils.isNotEmpty(storeName)) {
			purchaseRequisitionSearchQuery.append(" AND st.storeName = :storeName ");
		}
		purchaseRequisitionSearchQuery.append(" ORDER BY st.storeId DESC ");
		return purchaseRequisitionSearchQuery.toString();

	}

}
