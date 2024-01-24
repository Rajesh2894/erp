/**
 * 
 */
package com.abm.mainet.asset.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetDepreciationChartRevRepoImpl implements AssetDepreciationChartRevCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;

  /*  @Override
    @Transactional
    public void updateDepreciationChart(final AssetDepreciationChart entity) {

        final Query query = entityManager.createQuery(buildUpdateDataQuery(entity));
        if (entity.getSalvageValue() != null) {
            query.setParameter("salvagevalue", entity.getSalvageValue());
        }
        if (entity.getChartOfDepretn() != null) {
            query.setParameter("chartofdepretn", entity.getChartOfDepretn());
        }
        query.setParameter("originalusefullife", entity.getOriginalUsefulLife());
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
        if (entity.getSalvageValue() != null) {
            builder.append("adc.salvageValue=:salvagevalue ,");
        }
        if (entity.getChartOfDepretn() != null) {
            builder.append(" adc.chartOfDepretn=:chartofdepretn ");
        }
        builder.append(",adc.originalUsefulLife=:originalusefullife ");
        builder.append(",adc.remark=:remark ");
        builder.append(",adc.updatedBy=:updatedby ");
        builder.append(",adc.updatedDate=:updateddate ");
        builder.append(",adc.lgIpMacUpd=:lgipmacupd ");
        builder.append("WHERE adc.assetDepretnId=:assetdepretnid");
        return builder.toString();

    }
*/
}
