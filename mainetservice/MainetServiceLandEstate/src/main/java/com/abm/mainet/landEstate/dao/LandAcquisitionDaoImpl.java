package com.abm.mainet.landEstate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.landEstate.domain.LandAcquisition;

@Repository
public class LandAcquisitionDaoImpl extends AbstractDAO<Long> implements ILandAcquisitionDao {
    @SuppressWarnings("unchecked")
    @Override
    public List<LandAcquisition> searchLandAcquisitionData(String proposalNo, String payTo, String acqStatus, Long locId,
            Long orgid) {
        List<LandAcquisition> landAcquisitionEntity = new ArrayList<LandAcquisition>();
        try {
            StringBuilder jpaQuery = new StringBuilder(
                    "SELECT la FROM LandAcquisition la  where la.orgId = :orgid ");

            if (StringUtils.isNotEmpty(proposalNo)) {
                jpaQuery.append(" and la.apmApplicationId = :proposalNo ");
            }

            if (StringUtils.isNotEmpty(payTo)) {
                jpaQuery.append(" and la.payTo = :payTo ");
            }

            if (StringUtils.isNotEmpty(acqStatus)) {
                jpaQuery.append(" and la.acqStatus = :acqStatus ");
            }

            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                jpaQuery.append(" and la.locId = :locId ");
            }

            final Query hqlQuery = createQuery(jpaQuery.toString());

            hqlQuery.setParameter("orgid", orgid);

            if (StringUtils.isNotEmpty(proposalNo)) {
                hqlQuery.setParameter("proposalNo", Long.parseLong(proposalNo));// Convert string to long beacause
                                                                                // apmApplicationId data type is long
            }

            if (StringUtils.isNotEmpty(payTo)) {
                hqlQuery.setParameter("payTo", payTo);
            }

            if (StringUtils.isNotEmpty(acqStatus)) {
                hqlQuery.setParameter("acqStatus", acqStatus);
            }

            if (Optional.ofNullable(locId).orElse(0L) != 0) {
                hqlQuery.setParameter("locId", locId);
            }

            landAcquisitionEntity = hqlQuery.getResultList();

        } catch (Exception exception) {
            throw new FrameworkException("Exception occured to Search Record", exception);
        }

        return landAcquisitionEntity;
    }

    @Transactional
    public void updateLandValuationData(Long apmApplicationId, BigDecimal acqCost, Long vendorId, String billNo, Long assetId,
            String transferStatus, Date acqDate, Long orgId) {
        // before below line execute make sure apmApplicationId should not be null
        // update data based on condition
        Long updatedBy = UserSession.getCurrent().getEmployee().getEmpId();
        Date updatedDate = new Date();
        String lgIpMacUpd = UserSession.getCurrent().getEmployee().getEmppiservername();
        Query query = createQuery(
                buildQuery(apmApplicationId, acqCost, vendorId, billNo, assetId, transferStatus, acqDate, orgId));

        if (updatedBy != null) {
            query.setParameter("updatedBy", updatedBy);
        }
        if (updatedDate != null) {
            query.setParameter("updatedDate", updatedDate);
        }
        if (lgIpMacUpd != null) {
            query.setParameter("lgIpMacUpd", lgIpMacUpd);
        }
        if (acqCost != null) {
            query.setParameter("acqCost", acqCost);
        }
        if (acqDate != null) {
            query.setParameter("acqDate", acqDate);
        }
       
        if (vendorId != null) {
            query.setParameter("vendorId", vendorId);
        }

        if (billNo != null) {
            query.setParameter("billNo", billNo);
        }
        if (assetId != null) {
            query.setParameter("assetId", assetId);
        }
        if (transferStatus != null) {
            query.setParameter("transferStatus", transferStatus);
        }

        query.setParameter(MainetConstants.Common_Constant.ORGID, orgId);
        query.setParameter("apmApplicationId", apmApplicationId);

        query.executeUpdate();

    }

    // make dynamic query
    private String buildQuery(Long apmApplicationId, BigDecimal acqCost, Long vendorId, String billNo, Long assetId,
            String transferStatus, Date acqDate, Long orgId) {

        final StringBuilder builder = new StringBuilder();
        builder.append("update LandAcquisition set updatedBy=:updatedBy");
        builder.append(",updatedDate=:updatedDate ");
        builder.append(",lgIpMacUpd=:lgIpMacUpd ");

        if (acqCost != null) {
            builder.append(",acqCost=:acqCost");
        }
        if (vendorId != null) {
            builder.append(",vendorId=:vendorId");
        }
        if (billNo != null) {
            builder.append(",lnBillNo=:billNo");
        }
        if (assetId != null) {
            builder.append(",assetId=:assetId");
        }
        if (acqDate != null) {
            builder.append(",acqDate=:acqDate");
        }
        if (transferStatus != null) {
            builder.append(",transferStatus=:transferStatus");
        }
        if (orgId != null) {
            builder.append(" where orgId=:orgId AND");
        }
        if (apmApplicationId != null) {
            builder.append(" apmApplicationId=:apmApplicationId");
        }
        return builder.toString();
    }

    @Override
    public TbLoiMasEntity getLoiMasDataByApplicationId(Long applicationId) {
        TbLoiMasEntity loiMas = null;
        String hql = "from TbLoiMasEntity WHERE loiApplicationId=:applicationId";
        final Query query = createQuery(hql.toString());
        query.setParameter("applicationId", applicationId);
        @SuppressWarnings("unchecked")
        List<TbLoiMasEntity> loiMast = query.getResultList();
        if (!loiMast.isEmpty()) {
            loiMas = loiMast.get(0);
        }
        return loiMas;
    }

}
