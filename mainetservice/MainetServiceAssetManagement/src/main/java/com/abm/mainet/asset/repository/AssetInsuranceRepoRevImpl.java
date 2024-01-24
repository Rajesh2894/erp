package com.abm.mainet.asset.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInsuranceDetails;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class AssetInsuranceRepoRevImpl implements AssetInsuranceRevCustomRepo {

    @PersistenceContext
    private EntityManager entityManager;
/*
    @Override
    @Transactional
    public void updateInsurancesInfo(final AssetInsuranceDetails entity) {

        final Query query = entityManager.createQuery(buildUpdateDataQuery(entity));
        query.setParameter("insuranceno", entity.getInsuranceNo());
        query.setParameter("insuranceprovider", entity.getInsuranceProvider());
        query.setParameter("typeofinsurance", entity.getTypeOfInsurance());
        query.setParameter("insurancerate", entity.getInsuranceRate());
        query.setParameter("insuranceamount", entity.getInsuranceAmount());
        query.setParameter("premiumfrequency", entity.getPremiumFrequency());
        query.setParameter("premiumvalue", entity.getPremiumValue());
        query.setParameter("status", entity.getStatus());
        query.setParameter("insurancestartdate", entity.getInsuranceStartDate());
        query.setParameter("insuranceenddate", entity.getInsuranceEndDate());
        query.setParameter("costcenter", entity.getCostCenter());
        query.setParameter("remark", entity.getRemark());
        query.setParameter("updatedby", entity.getUpdatedBy());
        query.setParameter("updateddate", entity.getUpdatedDate());
        query.setParameter("lgipmacupd", entity.getLgIpMacUpd());
        query.setParameter("assetinsuranceid", entity.getAssetInsuranceId());
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException(
                    "Asset Service insurance Details could not be updated");
        }
    }

    private String buildUpdateDataQuery(final AssetInsuranceDetails entity) {

        final StringBuilder builder = new StringBuilder();
        builder.append(" update AssetInsuranceDetails astind  set  astind.insuranceNo=:insuranceno ");
        builder.append(", astind.insuranceProvider =:insuranceprovider ");
        builder.append(", astind.typeOfInsurance =:typeofinsurance ");
        builder.append(", astind.insuranceRate=:insurancerate ");
        builder.append(", astind.insuranceAmount =:insuranceamount ");
        builder.append(", astind.premiumFrequency =:premiumfrequency ");
        builder.append(", astind.premiumValue =:premiumvalue ");
        builder.append(", astind.status =:status ");
        builder.append(", astind.insuranceStartDate =:insurancestartdate ");
        builder.append(", astind.insuranceEndDate =:insuranceenddate ");
        builder.append(", astind.costCenter =:costcenter ");
        builder.append(", astind.remark =:remark ");
        builder.append(", astind.updatedBy =:updatedby ");
        builder.append(", astind.updatedDate =:updateddate ");
        builder.append(", astind.lgIpMacUpd =:lgipmacupd ");
        builder.append(" where astind.assetInsuranceId =:assetinsuranceid ");
        return builder.toString();

    }*/

}
