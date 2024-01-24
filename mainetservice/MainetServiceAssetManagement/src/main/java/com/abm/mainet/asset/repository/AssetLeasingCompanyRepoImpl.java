/**
 * 
 */
package com.abm.mainet.asset.repository;

import java.util.Date;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetLeasingCompany;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetLeasingCompanyRepoImpl extends AbstractDAO<Long> implements AssetLeasingCompanyCustomRepo {

    

    @Override
    @Transactional
    public void updateLeasingComp(final AssetLeasingCompany entity) {
        final Query query = createQuery(buildUpdateDataQuery());
        query.setParameter("contractagreementno", entity.getContractAgreementNo());
        query.setParameter("agreementdate", entity.getAgreementDate());
        query.setParameter("noticedate", entity.getNoticeDate());
        query.setParameter("leasestartdate", entity.getLeaseStartDate());
        query.setParameter("leaseenddate", entity.getLeaseEndDate());
        query.setParameter("leasetype", entity.getLeaseType());
        query.setParameter("purchaseprice", entity.getPurchasePrice());
        query.setParameter("noofinstallment", entity.getNoOfInstallment());
        query.setParameter("paymentfrequency", entity.getPaymentFrequency());
        query.setParameter("advancedpayment", entity.getAdvancedPayment());
        query.setParameter("updatedby", entity.getCreatedBy());
        query.setParameter("updateddate", new Date());
        query.setParameter("lgipmacupd", entity.getLgIpMac());
        query.setParameter("assetleasingid", entity.getAssetLeasingID());
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Asset Service leasing Details could not be updated");
        }
    }

    private String buildUpdateDataQuery() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" update AssetLeasingCompany alc  set alc.contractAgreementNo =:contractagreementno ");
        builder.append(", alc.agreementDate =:agreementdate ");
        builder.append(", alc.noticeDate =:noticedate ");
        builder.append(", alc.leaseStartDate =:leasestartdate ");
        builder.append(", alc.leaseEndDate =:leaseenddate ");
        builder.append(", alc.leaseType =:leasetype ");
        builder.append(", alc.purchasePrice =:purchaseprice ");
        builder.append(", alc.noOfInstallment =:noofinstallment ");
        builder.append(", alc.paymentFrequency =:paymentfrequency ");
        builder.append(", alc.advancedPayment =:advancedpayment ");
        builder.append(", alc.updatedBy =:updatedby ");
        builder.append(", alc.updatedDate =:updateddate ");
        builder.append(", alc.lgIpMacUpd =:lgipmacupd ");
        builder.append(" where  alc.assetLeasingID =:assetleasingid");
        return builder.toString();
    }

}
