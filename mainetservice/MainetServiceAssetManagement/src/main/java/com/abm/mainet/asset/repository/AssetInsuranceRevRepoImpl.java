package com.abm.mainet.asset.repository;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetInsuranceDetailsRev;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

@Repository
public class AssetInsuranceRevRepoImpl extends AbstractDAO<Long> implements AssetInsuranceRevRepoCustom {

   

    @Override
    @Transactional
    public void insuranceInfo(final AssetInsuranceDetailsRev entity) {

        final Query query = createQuery(buildUpdateDataQuery(entity));
       
        query.setParameter("revGrpIdentity", "");
        query.setParameter("updatedby", entity.getUpdatedBy());
        query.setParameter("updateddate", entity.getUpdatedDate());
        query.setParameter("lgipmacupd", entity.getLgIpMacUpd());
        query.setParameter("assetinsuranceid", entity.getAssetInsuranceId());
        query.setParameter("revGrpId", entity.getRevGrpId());
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException(
                    "Asset Service insurance Details could not be updated");
        }
    }

    private String buildUpdateDataQuery(final AssetInsuranceDetailsRev entity) {

        final StringBuilder builder = new StringBuilder();
        builder.append(" update AssetInsuranceDetailsRev astind  set ");
        builder.append(" astind.revGrpIdentity =:revGrpIdentity ");
        builder.append(", astind.updatedBy =:updatedby ");
        builder.append(", astind.updatedDate =:updateddate ");
        builder.append(", astind.lgIpMacUpd =:lgipmacupd ");
        builder.append(" where astind.revGrpId =:revGrpId ");
        return builder.toString();

    }

}
