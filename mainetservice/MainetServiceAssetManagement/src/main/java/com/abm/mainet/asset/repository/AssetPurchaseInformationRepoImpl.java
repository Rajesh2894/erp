/**
 * 
 */
package com.abm.mainet.asset.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetPurchaseInformation;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author sarojkumar.yadav
 *
 */
@Repository
public class AssetPurchaseInformationRepoImpl extends AbstractDAO<Long> implements AssetPurchaseInformationRepoCustom {

    @Override
    public void updateByAssetId(final Long assetPurchaserId, final AssetPurchaseInformation entity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<AssetPurchaseInformation> update = criteriaBuilder.createCriteriaUpdate(AssetPurchaseInformation.class);
        Root<AssetPurchaseInformation> astpur = update.from(AssetPurchaseInformation.class);
        update.set("fromWhomAcquired", entity.getFromWhomAcquired());
        if (entity.getManufacturer() != null && !entity.getManufacturer().isEmpty()) {
            update.set("manufacturer", entity.getManufacturer());
        }
        update.set("purchaseOrderNo", entity.getPurchaseOrderNo());
        if (entity.getUpdatedBy() != null && entity.getUpdatedBy() != 0) {
            update.set("updatedBy", entity.getUpdatedBy());
        }
        if (entity.getUpdatedDate() != null) {
            update.set("updatedDate", entity.getUpdatedDate());
        }
        if (entity.getLgIpMacUpd() != null && !entity.getLgIpMacUpd().isEmpty()) {
            update.set("lgIpMacUpd", entity.getLgIpMacUpd());
        }
        update.set("modeOfPayment", entity.getModeOfPayment());
        update.set("countryOfOrigin1", entity.getCountryOfOrigin1());
        update.set("astCreationDate", entity.getAstCreationDate());
        if (entity.getDateOfAcquisition() != null) {
            update.set("dateOfAcquisition", entity.getDateOfAcquisition());
        }

        if (entity.getCostOfAcquisition() != null) {
            update.set("costOfAcquisition", entity.getCostOfAcquisition());
        }

        if (StringUtils.isNotEmpty(entity.getLicenseNo())) {
            update.set("licenseNo", entity.getLicenseNo());
        }

        if (StringUtils.isNotEmpty(entity.getLicenseNo())) {
            update.set("licenseDate", entity.getLicenseDate());
        }

        // Task #5318
        /* if (entity.getBookValue() != null) { */
        update.set("bookValue", entity.getBookValue());
        // }
        /* if (entity.getInitialBookDate() != null) { */
        update.set("initialBookDate", entity.getInitialBookDate());
        /* } */
        update.set("purchaseOrderDate", entity.getPurchaseOrderDate());
        update.set("warrantyTillDate", entity.getWarrantyTillDate());
        update.where(criteriaBuilder.equal(astpur.get("assetPurchaserId"), assetPurchaserId));
        int result = entityManager.createQuery(update).executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Purchase Information could not be updated");
        }

    }

}
