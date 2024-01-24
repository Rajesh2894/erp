package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.dto.RTGSPaymentEntryDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author Vivek.Kumar
 * @since 05-Sep-2017
 */
@Repository
public class AccountPaymentMasterJpaRepositoryImpl extends AbstractDAO<AccountPaymentMasterEntity>
        implements AccountPaymentEntryRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> queryRecordsForPaymentEntry(final PaymentEntryDto dto, final Long orgId) {
        final Query query = entityManager.createQuery(buildDynamicQuery(dto));
        dto.setOrgId(orgId);
        dto.setPaymentTypeFlag(0L); // payment entry type flag is :0L only
        return bindInParams(query, dto).getResultList();
    }

    private String buildDynamicQuery(final PaymentEntryDto dto) {

        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT pm.paymentId,pm.paymentNo,pm.paymentDate,vm.vmVendorname,pm.paymentAmount ")
                .append(" FROM AccountPaymentMasterEntity pm, TbAcVendormasterEntity vm, AccountBillEntryMasterEnitity bm,")
                .append(" AccountBillEntryExpenditureDetEntity bd, AccountPaymentDetEntity pd")
                .append(" WHERE pm.paymentTypeFlag =:paymentTypeFlag and pm.paymentDeletionFlag IS NULL and pm.paymentId=pd.paymentMasterId.paymentId")
                .append(" AND pm.orgId=:orgId ")
                .append(" AND pm.vmVendorId=vm.vmVendorid")
                .append(" AND bm.id=bd.billMasterId.id")
                .append(" AND bd.id=pd.bchIdExpenditure")
                .append(" AND vm.vmVendorid=bm.vendorId.vmVendorid")
                .append(" AND pm.vmVendorId=bm.vendorId.vmVendorid");

        if ((dto.getSacHeadId() != null) && (dto.getSacHeadId().longValue() != 0l)) {
            builder.append(" AND bd.sacHeadId=:sacHeadId");
        }
        if ((dto.getTransactionDate() != null) && !dto.getTransactionDate().isEmpty()) {
            builder.append(" AND pm.paymentDate BETWEEN :fromDate AND :toDate");
        }
        if (dto.getPaymentAmount() != null) {
            builder.append(" AND pm.paymentAmount=:paymentAmount");
        }
        if ((dto.getVendorId() != null) && (dto.getVendorId().longValue() != 0l)) {
            builder.append(" AND pm.vmVendorId.vmVendorid=:vmVendorId");
        }
        if ((dto.getPaymentNo() != null) && !dto.getPaymentNo().isEmpty()) {
            builder.append(" AND pm.paymentNo=:paymentNo");
        }
        if ((dto.getBankAcId() != null) && (dto.getBankAcId().longValue() != 0l)) {
            builder.append(" AND pm.baBankAccountId.baAccountId=:baAccountId");
        }
        builder.append(" ORDER BY pm.paymentNo DESC");
        return builder.toString();
    }

    @SuppressWarnings("deprecation")
    private Query bindInParams(final Query query, final PaymentEntryDto dto) {

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, dto.getOrgId());
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PAY_TYPE_FLAG, dto.getPaymentTypeFlag());
        if ((dto.getSacHeadId() != null) && (dto.getSacHeadId().longValue() != 0l)) {
            query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.SAC_HEAD_ID, dto.getSacHeadId());
        }
        if ((dto.getTransactionDate() != null) && !dto.getTransactionDate().isEmpty()) {
            final Date date1 = Utility.stringToDate(dto.getTransactionDate());
            final Date date2 = Utility.stringToDate(dto.getTransactionDate());
            date2.setHours(23);
            date2.setMinutes(59);
            date2.setSeconds(59);
            query.setParameter(MainetConstants.FROM_DATE, date1);
            query.setParameter(MainetConstants.TO_DATE, date2);

        }
        if (dto.getPaymentAmount() != null) {
            query.setParameter("paymentAmount", dto.getPaymentAmount());
        }
        if ((dto.getVendorId() != null) && (dto.getVendorId().longValue() != 0l)) {
            query.setParameter("vmVendorId", dto.getVendorId());
        }
        if ((dto.getPaymentNo() != null) && !dto.getPaymentNo().isEmpty()) {
            query.setParameter("paymentNo", dto.getPaymentNo());
        }
        if ((dto.getBankAcId() != null) && (dto.getBankAcId().longValue() != 0l)) {
            query.setParameter("baAccountId", dto.getBankAcId());
        }

        return query;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> queryRecordsForRTGSPaymentEntry(RTGSPaymentEntryDTO dto, Long orgId) {
        final Query query = entityManager.createQuery(buildRTGSDynamicQuery(dto));
        dto.setOrgId(orgId);
        dto.setPaymentTypeFlag(3L); // RTGS payment entry type flag is :3L only
        return bindInRTGSParams(query, dto).getResultList();
    }

    @SuppressWarnings("deprecation")
    private Query bindInRTGSParams(final Query query, final RTGSPaymentEntryDTO dto) {

        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.ORGID, dto.getOrgId());
        query.setParameter(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PAY_TYPE_FLAG, dto.getPaymentTypeFlag());
        if ((dto.getTransactionDate() != null) && !dto.getTransactionDate().isEmpty()) {
            final Date date1 = Utility.stringToDate(dto.getTransactionDate());
            final Date date2 = Utility.stringToDate(dto.getTransactionDate());
            date2.setHours(23);
            date2.setMinutes(59);
            date2.setSeconds(59);
            query.setParameter(MainetConstants.FROM_DATE, date1);
            query.setParameter(MainetConstants.TO_DATE, date2);

        }
        if (dto.getPaymentAmount() != null) {
            query.setParameter("paymentAmount", dto.getPaymentAmount());
        }

        if ((dto.getPaymentNo() != null) && !dto.getPaymentNo().isEmpty()) {
            query.setParameter("paymentNo", dto.getPaymentNo());
        }
        if ((dto.getBankAcId() != null) && (dto.getBankAcId().longValue() != 0l)) {
            query.setParameter("baAccountId", dto.getBankAcId());
        }
        return query;
    }

    private String buildRTGSDynamicQuery(final RTGSPaymentEntryDTO dto) {

        final StringBuilder builder = new StringBuilder();
        builder.append(
                "SELECT DISTINCT pm.paymentId,pm.paymentNo,pm.paymentDate,pm.baBankAccountId.baAccountNo,pm.baBankAccountId.baAccountName,pm.paymentAmount ")
                .append(" FROM AccountPaymentMasterEntity pm, AccountBillEntryMasterEnitity bm,")
                .append(" AccountBillEntryExpenditureDetEntity bd, AccountPaymentDetEntity pd")
                .append(" WHERE pm.paymentTypeFlag =:paymentTypeFlag and pm.paymentDeletionFlag IS NULL and pm.paymentId=pd.paymentMasterId.paymentId")
                .append(" AND pm.orgId=:orgId ")
                .append(" AND bm.id=bd.billMasterId.id")
                .append(" AND bd.id=pd.bchIdExpenditure");

        if ((dto.getTransactionDate() != null) && !dto.getTransactionDate().isEmpty()) {
            builder.append(" AND pm.paymentDate BETWEEN :fromDate AND :toDate");
        }
        if (dto.getPaymentAmount() != null) {
            builder.append(" AND pm.paymentAmount=:paymentAmount");
        }

        if ((dto.getPaymentNo() != null) && !dto.getPaymentNo().isEmpty()) {
            builder.append(" AND pm.paymentNo=:paymentNo");
        }
        if ((dto.getBankAcId() != null) && (dto.getBankAcId().longValue() != 0l)) {
            builder.append(" AND pm.baBankAccountId.baAccountId=:baAccountId");
        }
        builder.append(" ORDER BY pm.paymentNo DESC");
        return builder.toString();
    }

}
