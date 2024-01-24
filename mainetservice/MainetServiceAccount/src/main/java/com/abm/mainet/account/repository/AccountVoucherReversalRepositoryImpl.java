package com.abm.mainet.account.repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountPaymentDetEntity;
import com.abm.mainet.account.domain.AccountPaymentMasterEntity;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

/**
 *
 * @author Vivek.Kumar
 * @since 19 May 2017
 */
@Repository
public class AccountVoucherReversalRepositoryImpl extends AbstractDAO<T> implements AccountVoucherReversalRepositoryCustom {

    // @PersistenceContext
    // private EntityManager entityManager;
    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;
    @Resource
    private AccountBankDepositeSlipMasterJpaRepository depositSlipRepository;
    @Resource
    private AccountPaymentMasterJpaRepository paymentEntryRepository;

    @Override
    public List<Object[]> findRerodsForVoucherReversal(final VoucherReversalDTO dto, final long deptId, final long orgId) {

        List<Object[]> list = recordsForVoucherReversal(dto, deptId, orgId);
        boolean dateFlagExist = false;
        if (Objects.nonNull(list) && !list.isEmpty()) {
            for (Object[] objects : list) {
                switch (dto.getTransactionType()) {
                case "RP":
                    dateFlagExist = queryForReceiptCodeExist((long) objects[0], orgId);
                    break;
                case "BP":
                    dateFlagExist = queryForBillCodeExist((long) objects[0], orgId);
                    break;
                case "DSE":
                    dateFlagExist = queryForDepositCodeExist((long) objects[0], orgId);
                    break;
                case "BPE":
                case "DPE":
                    dateFlagExist = queryForPaymentEntryCodeExist((long) objects[0], orgId);
                    break;
                }
            }
        }

        if (dateFlagExist) {
            list = null;
        }

        return list;
    }

    private boolean queryForReceiptCodeExist(Long recieptid, Long orgId) {

        boolean depositSlipIdExist = false;
        TbServiceReceiptMasEntity entity = accountReceiptEntryJpaRepository.findAllByReceiptId(recieptid, orgId);
        if (entity != null) {
            List<TbSrcptFeesDetEntity> detEntity = entity.getReceiptFeeDetail();
            for (TbSrcptFeesDetEntity detDTO : detEntity) {
                if (detDTO.getDepositeSlipId() != null) {
                    depositSlipIdExist = true;
                }
            }
        }
        return depositSlipIdExist;
    }

    private boolean queryForDepositCodeExist(Long depositeSlipId, Long orgId) {

        boolean depositSlipIdExist = false;
        List<Object[]> recModeDateList = depositSlipRepository.findReceiptEntryDetailsByDepositSlipId(depositeSlipId,
                orgId);
        int count = 0;
        if (recModeDateList == null || recModeDateList.isEmpty()) {
            depositSlipIdExist = false;
        } else {
            for (Object objects : recModeDateList) {
                if (objects != null) {
                    count++;
                }
            }
        }
        if (count == 0) {
            depositSlipIdExist = false;
        } else {
            depositSlipIdExist = true;
        }
        return depositSlipIdExist;
    }

    private boolean queryForBillCodeExist(Long billId, Long orgId) {

        boolean depositSlipIdExist = false;
        AccountPaymentDetEntity payDetEntity = paymentEntryRepository.findBillEntryDetailsByBillId(billId, orgId);
        if (payDetEntity != null) {
            depositSlipIdExist = true;
        }
        return depositSlipIdExist;
    }

    private boolean queryForPaymentEntryCodeExist(Long id, Long orgId) {

        boolean depositSlipIdExist = false;
        AccountPaymentMasterEntity payEntity = paymentEntryRepository.findPaymentEntryDataById(id, orgId);
        if (payEntity != null) {
            if (payEntity.getChequeClearanceDate() != null) {
                depositSlipIdExist = true;
            }
        }
        return depositSlipIdExist;
    }

    @SuppressWarnings("unchecked")
    private List<Object[]> recordsForVoucherReversal(final VoucherReversalDTO dto, final long deptId, final long orgId) {

        final Date date = Utility.stringToDate(dto.getTransactionDate());
        Query query = entityManager.createQuery(buildDynamicQuery(dto, date));
        if (Objects.nonNull(dto.getTransactionNo())) {
            if ("BP".equals(dto.getTransactionType())
                    || "BPE".equals(dto.getTransactionType()) || "DPE".equals(dto.getTransactionType()) 
                    || "DSE".equals(dto.getTransactionType())) {
                query.setParameter("transactionNo", dto.getTransactionNo().toString());
            } else {
            	query.setParameter("transactionNo", (Long.valueOf(dto.getTransactionNo())));

            }
        }
        if (Objects.nonNull(date)) {
            query.setParameter("transactionDate", date);
        }
        if (Objects.nonNull(dto.getTransactionAmount())) {
            if ("BP".equals(dto.getTransactionType())
                    || "BPE".equals(dto.getTransactionType())) {
                query.setParameter("transactionAmount", dto.getTransactionAmount().replace(",", ""));
            } else {
                query.setParameter("transactionAmount", Double.valueOf(dto.getTransactionAmount().replace(",", "")));
            }
        }
        query.setParameter("orgId", orgId);
        if (!"BPE".equals(dto.getTransactionType())
                && !"DPE".equals(dto.getTransactionType())) {
            query.setParameter("dpDeptId", deptId);
        }
        if ("BPE".equals(dto.getTransactionType())) {
            query.setParameter("paymentTypeFlag", 0l);
            query = bindOtherParams(query, "PV", "PVE", orgId);
        }
        if ("DPE".equals(dto.getTransactionType())) {
            query.setParameter("paymentTypeFlag", 1l);
            query = bindOtherParams(query, "PV", "PVE", orgId);
        }
        if ("DSE".equals(dto.getTransactionType())) {
            query = bindOtherParams(query, "CV", "DS", orgId);
        }
        if ("RP".equals(dto.getTransactionType())) {
            query = bindReceiptOtherParams(query, "RV", "RV", orgId);
        }
        if ("BP".equals(dto.getTransactionType())) {
            query = bindOtherParamsForBP(query, "JV", "BI", orgId);
        }

        return query.getResultList();
    }

	private Query bindOtherParams(final Query query, final String voucherType, final String voucherSubType,
			final Long orgId) {

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
				AccountPrefix.VOT.toString(), orgId);
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherSubType,
				AccountPrefix.TDP.toString(), orgId);
		query.setParameter("authoFlg", "Y");
		query.setParameter("vouTypeCpdId", voucherTypeId);
		query.setParameter("vouSubtypeCpdId", voucherSubTypeId);

		return query;
	}
	
	private Query bindOtherParamsForBP(final Query query, final String voucherType, final String voucherSubType,
			final Long orgId) {

		final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType,
				AccountPrefix.VOT.toString(), orgId);
		final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherSubType,
				AccountPrefix.TDP.toString(), orgId);
		query.setParameter("authoFlg", 'Y');
		// query.setParameter("vouTypeCpdId", voucherTypeId);
		// query.setParameter("vouSubtypeCpdId", voucherSubTypeId);

		return query;
	}

    private Query bindReceiptOtherParams(final Query query, final String voucherType, final String voucherSubType,
            final Long orgId) {

        final Long voucherTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherType, AccountPrefix.VOT.toString(),
                orgId);
        final Long voucherSubTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(voucherSubType,
                AccountPrefix.TDP.toString(),
                orgId);
        query.setParameter("authoFlg", "Y");
        query.setParameter("vouTypeCpdId", voucherTypeId);
        return query;
    }

    private String buildDynamicQuery(final VoucherReversalDTO dto, final Date date) {

        String query = StringUtils.EMPTY;
        switch (dto.getTransactionType()) {
        case "RP":
            query = queryForReceipt(dto, date);
            break;
        case "BP":
            query = queryForBillInvoiceEntry(dto, date);
            break;
        case "DSE":
            query = queryForDepositSlip(dto, date);
            break;
        case "BPE":
        case "DPE":
            query = queryForBillPaymentEntry(dto, date);
            break;
        default:
            throw new IllegalArgumentException("No Case matched for provided transactionType=" + dto.getTransactionType());
        }

        return query;
    }

    private String queryForReceipt(final VoucherReversalDTO dto, final Date date) {

        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT DISTINCT rm.rmRcptid, rm.rmRcptno, "
                + "rm.rmDate, rm.rmAmount, rm.rmNarration,"
                + " rm.receiptDelFlag "
                + " FROM TbServiceReceiptMasEntity rm, AccountVoucherEntryEntity vo WHERE rm.orgId=vo.org and ");

        if (Objects.nonNull(dto.getTransactionNo())) {
            builder.append(" rm.rmRcptno=:transactionNo AND");
        }
        if (Objects.nonNull(date)) {
            builder.append(" rm.rmDate=:transactionDate AND");
        }
        if (Objects.nonNull(dto.getTransactionAmount())) {
            builder.append(" rm.rmAmount=:transactionAmount AND");
        }
        builder.append(" rm.orgId=:orgId AND")
                .append(" rm.rmDate=vo.vouDate AND ")
                .append(" rm.dpDeptId=:dpDeptId AND ")
                .append(" rm.rmRcptno=vo.vouReferenceNo AND ")
                .append(" rm.receiptDelFlag IS NULL AND ")
                .append(" vo.authoFlg=:authoFlg AND ")
                .append(" vo.vouTypeCpdId=:vouTypeCpdId ")
                /* .append(" vo.vouSubtypeCpdId=:vouSubtypeCpdId ") */
                .append(" ORDER BY rm.rmRcptno DESC");

        return builder.toString();
    }

    private String queryForDepositSlip(final VoucherReversalDTO dto, final Date date) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT dsm.depositeSlipId, dsm.depositeSlipNumber, "
                + "dsm.depositeSlipDate, dsm.depositeAmount, dsm.depositeRemark,"
                + " dsm.dps_del_flag, dsm.depositeTypeFlag "
                + " FROM AccountBankDepositeSlipMasterEntity dsm , AccountVoucherEntryEntity vo WHERE dsm.orgid=vo.org and ");

        if (Objects.nonNull(dto.getTransactionNo())) {
            builder.append(" dsm.depositeSlipNumber=:transactionNo AND");
        }
        if (Objects.nonNull(date)) {
            builder.append(" dsm.depositeSlipDate=:transactionDate AND");
        }
        if (Objects.nonNull(dto.getTransactionAmount())) {
            builder.append(" dsm.depositeAmount=:transactionAmount AND");
        }
        builder.append(" dsm.orgid=:orgId AND ")
                .append(" dsm.depositeSlipDate=vo.vouDate AND ")
                .append(" dsm.depositeSlipNumber=vo.vouReferenceNo AND ")
                .append("dsm.deptId.dpDeptid=:dpDeptId AND ")
                .append("dsm.dps_del_flag IS NULL AND ")
                /* .append("dsm.depositTypeFlag ='D' AND ") */
                .append("vo.authoFlg=:authoFlg AND ")
                .append("vo.vouTypeCpdId=:vouTypeCpdId AND ")
                .append("vo.vouSubtypeCpdId=:vouSubtypeCpdId ")
                .append("ORDER BY dsm.depositeSlipNumber DESC");
        return builder.toString();
    }

    private String queryForBillInvoiceEntry(final VoucherReversalDTO dto, final Date date) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT bm.id, bm.billNo, "
                + "bm.billEntryDate, bm.billTotalAmount, bm.narration,"
                + " bm.billDeletionFlag "
                + "  FROM AccountBillEntryMasterEnitity bm WHERE");

        if (Objects.nonNull(dto.getTransactionNo())) {
            builder.append(" bm.billNo=:transactionNo AND");
        }
        if (Objects.nonNull(date)) {
            builder.append(" bm.billEntryDate=:transactionDate AND");
        }
        if (Objects.nonNull(dto.getTransactionAmount())) {
            builder.append(" bm.billTotalAmount=:transactionAmount AND");
        }
        builder.append(" bm.orgId=:orgId AND")
               // .append(" bm.checkerDate=vo.vouPostingDate AND ")
                .append(" bm.departmentId.dpDeptid=:dpDeptId AND")
              //  .append(" bm.billNo=vo.vouReferenceNo AND ")
                .append(" bm.billDeletionFlag IS NULL AND ")
                .append(" bm.checkerAuthorization=:authoFlg ")
               // .append(" vo.vouTypeCpdId=:vouTypeCpdId AND ")
                //.append(" vo.vouSubtypeCpdId=:vouSubtypeCpdId ")
                .append(" ORDER BY bm.billNo DESC");
        return builder.toString();
    }

    private String queryForBillPaymentEntry(final VoucherReversalDTO dto, final Date date) {
        final StringBuilder builder = new StringBuilder();
        builder.append("SELECT pm.paymentId, pm.paymentNo, "
                + "pm.paymentDate, pm.paymentAmount, pm.narration,"
                + " pm.paymentDeletionFlag "
                + " FROM AccountPaymentMasterEntity pm, AccountVoucherEntryEntity vo  WHERE pm.orgId=vo.org and ");

        if (Objects.nonNull(dto.getTransactionNo())) {
            builder.append(" pm.paymentNo=:transactionNo AND");
        }
        if (Objects.nonNull(date)) {
            builder.append(" pm.paymentDate=:transactionDate AND");
        }
        if (Objects.nonNull(dto.getTransactionAmount())) {
            builder.append(" pm.paymentAmount=:transactionAmount AND");
        }
        builder.append(" pm.paymentTypeFlag=:paymentTypeFlag AND");
        builder.append(" pm.orgId=:orgId AND ")
                .append(" pm.paymentDate=vo.vouDate AND ")
                .append(" pm.paymentNo=vo.vouReferenceNo AND ")
                .append(" pm.paymentDeletionDate IS NULL AND ")
                .append(" vo.authoFlg=:authoFlg AND ")
                .append(" vo.vouTypeCpdId=:vouTypeCpdId AND ")
                .append(" vo.vouSubtypeCpdId=:vouSubtypeCpdId ")
                .append(" ORDER BY pm.paymentNo DESC ");
        return builder.toString();
    }

    @Override
    public void saveOrUpdateReceipt(final Long transactionId, final VoucherReversalDTO dto, final long orgId,
            final String ipMacAddress) {

        final Query query = entityManager.createQuery("UPDATE TbServiceReceiptMasEntity rm SET "
                + "rm.receiptDelFlag=:receiptDelFlag, rm.receipt_del_order_no=:receipt_del_order_no , "
                + "rm.receipt_del_auth_by=:receipt_del_auth_by"
                + ",rm.receiptDelDate=:receiptDelDate, rm.receiptDelPostingDate=:receiptDelPostingDate, rm.receiptDelRemark=:receiptDelRemark,"
                + "rm.updatedBy=:updatedBy,rm.updatedDate=:updatedDate"
                + ", rm.lgIpMacUpd=:lgIpMacUpd WHERE rm.rmRcptid=:rmRcptid AND rm.orgId=:orgId");
        query.setParameter("receiptDelFlag", "Y");
        query.setParameter("receipt_del_order_no", dto.getApprovalOrderNo());
        query.setParameter("receipt_del_auth_by", dto.getApprovedBy());
        query.setParameter("receiptDelDate", new Date());
        query.setParameter("receiptDelPostingDate", new Date());
        query.setParameter("receiptDelRemark", dto.getNarration());
        query.setParameter("updatedBy", dto.getUpdatedBy());
        query.setParameter("updatedDate", new Date());
        query.setParameter("lgIpMacUpd", ipMacAddress);
        query.setParameter("rmRcptid", transactionId);
        query.setParameter("orgId", orgId);

        query.executeUpdate();

    }

}
