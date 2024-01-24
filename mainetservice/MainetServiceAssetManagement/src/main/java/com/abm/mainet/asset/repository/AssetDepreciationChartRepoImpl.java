/**
 * 
 */
package com.abm.mainet.asset.repository;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetDepreciationChart;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetDepreciationChartRepoImpl extends AbstractDAO<Long> implements AssetDepreciationChartCustomRepo {

	
	@Override
	@Transactional
	public void updateDepreciationChart(final AssetDepreciationChart entity) {

		final Query query = createQuery(buildUpdateDataQuery(entity));

		if (entity.getChartOfDepretn() != null) {
			query.setParameter("chartofdepretn", entity.getChartOfDepretn());
		}

		if (entity.getDeprApplicable() != null) {
			query.setParameter("deprApplicable", entity.getDeprApplicable());
		}
		query.setParameter("salvageValue", entity.getSalvageValue());
		query.setParameter("accumulDeprDate", entity.getAccumulDeprDate());
		query.setParameter("accumDepreAmount", entity.getAccumDepreAmount());
		query.setParameter("accumuDepAc", entity.getAccumuDepAc());
		query.setParameter("originalUsefulLife", entity.getOriginalUsefulLife());
		query.setParameter("remark", entity.getRemark());
		query.setParameter("updatedby", entity.getUpdatedBy());
		query.setParameter("updateddate", entity.getUpdatedDate());
		query.setParameter("lgipmacupd", entity.getLgIpMacUpd());
		query.setParameter("assetdepretnid", entity.getAssetDepretnId());
		int result = query.executeUpdate();
		if (!(result > 0)) {
			throw new FrameworkException("Asset Service depreciation Details could not be updated");
		}
	}

	private String buildUpdateDataQuery(final AssetDepreciationChart entity) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" update AssetDepreciationChart adc set ");

		if (entity.getChartOfDepretn() != null) {
			builder.append(" adc.chartOfDepretn=:chartofdepretn ");
		}
		builder.append(",adc.salvageValue=:salvageValue ");
		builder.append(",adc.accumulDeprDate=:accumulDeprDate ");
		builder.append(",adc.accumDepreAmount=:accumDepreAmount ");
		builder.append(",adc.accumuDepAc=:accumuDepAc ");
		builder.append(",adc.originalUsefulLife=:originalUsefulLife ");
		builder.append(",adc.remark=:remark ");
		builder.append(",adc.updatedBy=:updatedby ");
		builder.append(",adc.updatedDate=:updateddate ");
		builder.append(",adc.lgIpMacUpd=:lgipmacupd ");
		builder.append(",adc.deprApplicable=:deprApplicable ");
		builder.append("WHERE adc.assetDepretnId=:assetdepretnid");
		return builder.toString();

	}

}
