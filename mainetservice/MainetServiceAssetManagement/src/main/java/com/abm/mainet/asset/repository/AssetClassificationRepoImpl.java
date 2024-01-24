/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetClassification;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetClassificationRepoImpl extends AbstractDAO<Long> implements AssetClassificationRepoCustom {

    @Override
    public void updateByAssetId(final Long assetClassificationId, final AssetClassification entity) {
        final Query query = createQuery(buildUpdateDataQuery(assetClassificationId,
                entity.getFunctionalLocationCode(), entity.getGisId(), entity.getLatitude(), entity.getLongitude(),
                entity.getCostCenter(), entity.getLocation(), entity.getSurveyNo(), entity.getDepartment(), entity.getUpdatedBy(),
                entity.getUpdatedDate(), entity.getLgIpMacUpd(),entity.getAddress()));

        query.setParameter("functionalLocationCode", entity.getFunctionalLocationCode());
        query.setParameter("gisId", entity.getGisId());
        query.setParameter("latitude", entity.getLatitude());
        query.setParameter("longitude", entity.getLongitude());
        query.setParameter("costCenter", entity.getCostCenter());
        query.setParameter("location", entity.getLocation());
        query.setParameter("surveyNo", entity.getSurveyNo());
        query.setParameter("address", entity.getAddress());

        if (entity.getDepartment() != null && entity.getDepartment() != 0) {
            query.setParameter("department", entity.getDepartment());
        }
        
        if (entity.getAddress() != null ) {
            query.setParameter("address", entity.getAddress());
        }
        

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

    /**
     * Method to create Dynamic Update Query depend upon parameters provided in the method
    
     * 
     * @return String of Dynamic query
     */
    private String buildUpdateDataQuery(Long assetClassificationId, Long functionalLocationCode, BigDecimal gisId,
            BigDecimal latitude, BigDecimal longitude, String costCenter, Long location, String surveyNo, Long department,
            Long updatedBy,
            Date updatedDate, String lgIpMacUpd, String address) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update AssetClassification ac set");

        builder.append(" ac.functionalLocationCode=:functionalLocationCode");
        builder.append(",ac.gisId=:gisId");
        builder.append(",ac.latitude=:latitude");
        builder.append(",ac.longitude=:longitude");
        builder.append(",ac.costCenter=:costCenter");
        builder.append(",ac.location=:location");
        builder.append(",ac.department=:department");
        builder.append(",ac.surveyNo=:surveyNo");
        builder.append(",ac.address=:address");

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
