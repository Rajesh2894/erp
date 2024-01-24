package com.abm.mainet.asset.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.abm.mainet.asset.domain.AssetRealEstate;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class AssetRealEstateRepoImpl extends AbstractDAO<Long> implements AssetRealEstateRepoCustom {

    @Override
    public void updateByAssetId(final Long assetRealEstateId, final AssetRealEstate entity) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<AssetRealEstate> update = criteriaBuilder.createCriteriaUpdate(AssetRealEstate.class);
        Root<AssetRealEstate> realEstate = update.from(AssetRealEstate.class);

        if (entity.getAssessmentNo() != null && !entity.getAssessmentNo().isEmpty())
            update.set("assessmentNo", entity.getAssessmentNo());

        if (entity.getMuncipalityName() != null && !entity.getMuncipalityName().isEmpty())
            update.set("muncipalityName", entity.getMuncipalityName());

        if (entity.getPropertyRegistrationNo() != null && !entity.getPropertyRegistrationNo().isEmpty())
            update.set("propertyRegistrationNo", entity.getPropertyRegistrationNo());

        if (entity.getRealEstateAmount() != null)
            update.set("realEstateAmount", entity.getRealEstateAmount());
        if (entity.getTaxCode() != null && !entity.getTaxCode().isEmpty())
            update.set("taxCode", entity.getTaxCode());
        if (entity.getTaxZoneLocation() != null && !entity.getTaxZoneLocation().isEmpty())
            // update.set("taxZoneLocation", entity.getTaxCode());
            update.set("taxZoneLocation", entity.getTaxZoneLocation());

        if (entity.getUpdatedDate() != null) {
            update.set("updatedDate", entity.getUpdatedDate());
        }
        if (entity.getLgIpMacUpd() != null && !entity.getLgIpMacUpd().isEmpty()) {
            update.set("lgIpMacUpd", entity.getLgIpMacUpd());
        }

        update.where(criteriaBuilder.equal(realEstate.get("assetRealStdId"), assetRealEstateId));
        int result = entityManager.createQuery(update).executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Real Estate Information could not be updated");
        }

    }

}
