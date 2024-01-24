/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetPurchaseInformationRevRepoImpl implements AssetPurchaseInformationRevRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

  /*  @Override
    public void updateByAssetId(final Long assetPurchaserId, final AssetPurchaseInformation entity) {
        final Query query = entityManager.createQuery(buildUpdateDataQuery(assetPurchaserId, entity.getFromWhomAcquired(),
                entity.getManufacturer(), entity.getPurchaseOrderNo(), entity.getDateOfAcquisition(),
                entity.getCostOfAcquisition(), entity.getBookValue(), entity.getLifeInYears(), entity.getModeOfPayment(),
                entity.getCountryOfOrigin1(), entity.getUpdatedBy(), entity.getUpdatedDate(), entity.getLgIpMacUpd()));

        query.setParameter("fromWhomAcquired", entity.getFromWhomAcquired());

        if (entity.getManufacturer() != null && !entity.getManufacturer().isEmpty()) {
            query.setParameter("manufacturer", entity.getManufacturer());
        }

        query.setParameter("purchaseOrderNo", entity.getPurchaseOrderNo());
        if (entity.getDateOfAcquisition() != null) {
            query.setParameter("dateOfAcquisition", entity.getDateOfAcquisition());
        }
        if (entity.getCostOfAcquisition() != null) {
            query.setParameter("costOfAcquisition", entity.getCostOfAcquisition());
        }
        if (entity.getBookValue() != null) {
            query.setParameter("bookValue", entity.getBookValue());
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
        query.setParameter("lifeInYears", entity.getLifeInYears());
        query.setParameter("modeOfPayment", entity.getModeOfPayment());
        query.setParameter("countryOfOrigin1", entity.getCountryOfOrigin1());
        query.setParameter("assetPurchaserId", assetPurchaserId);
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Purchase Information could not be updated ");
        }
    }

    *//**
     * Method to create Dynamic Update Query depend upon parameters provided in the method
     * 
     * @return String of Dynamic query
     *//*
    private String buildUpdateDataQuery(Long assetPurchaserId, Long fromWhomAcquired, String manufacturer, Long purchaseOrderNo,
            Date dateOfAcquisition, BigDecimal costOfAcquisition, BigDecimal bookValue, BigDecimal lifeInYears,
            Long modeOfPayment, Long countryOfOrigin1, Long updatedBy, Date updatedDate, String lgIpMacUpd) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update AssetPurchaseInformation purch set");

        builder.append(" purch.fromWhomAcquired=:fromWhomAcquired");

        if (manufacturer != null && !manufacturer.isEmpty()) {
            builder.append(",purch.manufacturer=:manufacturer");
        }

        builder.append(",purch.purchaseOrderNo=:purchaseOrderNo");
        if (dateOfAcquisition != null) {
            builder.append(",purch.dateOfAcquisition=:dateOfAcquisition");
        }
        if (costOfAcquisition != null) {
            builder.append(",purch.costOfAcquisition=:costOfAcquisition");
        }
        if (bookValue != null) {
            builder.append(",purch.bookValue=:bookValue");
        }
        if (updatedBy != null && updatedBy != 0) {
            builder.append(",purch.updatedBy=:updatedBy");
        }
        if (updatedDate != null) {
            builder.append(",purch.updatedDate=:updatedDate");
        }
        if (lgIpMacUpd != null && !lgIpMacUpd.isEmpty()) {
            builder.append(",purch.lgIpMacUpd=:lgIpMacUpd");
        }
        builder.append(",purch.lifeInYears=:lifeInYears");
        builder.append(",purch.modeOfPayment=:modeOfPayment");
        builder.append(",purch.countryOfOrigin1=:countryOfOrigin1");
        builder.append(" where purch.assetPurchaserId=:assetPurchaserId");
        return builder.toString();
    }*/
}
