/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetClassificationRev;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetClassificationRevRepoImpl extends AbstractDAO<Long> implements AssetClassificationRevRepoCustom {


	// Revised method
	@Override
	public void updateByAssetIdRev(final Long assetClassificationId, final AssetClassificationRev entity) {

		Query queryTst = 
				createNamedQuery("select COUNT(a) from AssetClassificationRev a where a.assetId=:assetId ");

		queryTst.setParameter("assetId", assetClassificationId);

		int count = queryTst.getMaxResults();

		if (count > 0) {
			entityManager.persist(entity);
		} else {
			final Query query = createQuery(buildUpdateDataQueryRev(assetClassificationId,
					entity.getFunctionalLocationCode(), entity.getGisId(), entity.getLatitude(), entity.getLongitude(),
					entity.getUpdatedBy(), entity.getUpdatedDate(), entity.getLgIpMacUpd()));

			query.setParameter("functionalLocationCode", entity.getFunctionalLocationCode());
			query.setParameter("gisId", entity.getGisId());
			query.setParameter("latitude", entity.getLatitude());
			query.setParameter("longitude", entity.getLongitude());

			if (entity.getUpdatedBy() != null && entity.getUpdatedBy() != 0) {
				query.setParameter("updatedBy", entity.getUpdatedBy());
			}
			if (entity.getUpdatedDate() != null) {
				query.setParameter("updatedDate", entity.getUpdatedDate());
			}
			if (entity.getLgIpMacUpd() != null && !entity.getLgIpMacUpd().isEmpty()) {
				query.setParameter("lgIpMacUpd", entity.getLgIpMacUpd());
			}
			query.setParameter("assetClassificationId", assetClassificationId);
			int result = query.executeUpdate();
			if (!(result > 0)) {
				throw new FrameworkException("asset classification could not be updated");
			}
		}
	}

	/**
	 * Method to create Dynamic Update Query depend upon parameters provided in the
	 * method
	 * 
	 * @return String of Dynamic query
	 */
	private String buildUpdateDataQueryRev(Long assetClassificationId, Long functionalLocationCode, BigDecimal gisId,
			BigDecimal latitude, BigDecimal longitude, Long updatedBy, Date updatedDate, String lgIpMacUpd) {

		final StringBuilder builder = new StringBuilder();
		builder.append("update AssetClassificationRev ac set");

		builder.append(" ac.functionalLocationCode=:functionalLocationCode");
		builder.append(",ac.gisId=:gisId");
		builder.append(",ac.latitude=:latitude");
		builder.append(",ac.longitude=:longitude");

		if (updatedBy != null && updatedBy != 0) {
			builder.append(",ac.updatedBy=:updatedBy");
		}
		if (updatedDate != null) {
			builder.append(",ac.updatedDate=:updatedDate");
		}
		if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
			builder.append(",ac.lgIpMacUpd=:lgIpMacUpd");
		}
		builder.append(" where ac.assetClassificationId=:assetClassificationId");
		return builder.toString();
	}
}
