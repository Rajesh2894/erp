package com.abm.mainet.account.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.account.domain.AccountBankDepositeSlipMasterEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Repository
public class ContraVoucherEntryDaoImpl extends AbstractDAO<AccountBankDepositeSlipMasterEntity> implements ContraVoucherEntryDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.ContraVoucherEntryDao#getContraEntryDetails(java.lang.Long, java.lang.String,
     * java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Object[]> getContraEntryDetails(Long orgId, String fromDate, String toDate, Long transactionNo,
            Character transactionType) {

        StringBuilder builderOne = new StringBuilder();
        StringBuilder builderTwo = new StringBuilder();
        StringBuilder builderThree = new StringBuilder();
        List<Object[]> finalList = new ArrayList<>();
        Set<Object[]> finalSet = new LinkedHashSet<>();

        if (transactionType.equals('T')) {

            String queryStrOne = "select a.depositeSlipId, a.depositeSlipNumber, a.depositeSlipDate, a.depositeAmount, a.coTypeFlag from AccountBankDepositeSlipMasterEntity a, "
                    + "TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                    + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid "
                    + "and a.depositeSlipId = c.depositeSlipId and a.depositTypeFlag = 'C' and a.coTypeFlag in ('T') "
                    + "and a.orgid=c.orgId  and b.orgId=c.orgId and b.orgId=d.orgId and a.orgid =:orgId ";

            builderOne.append(queryStrOne);

            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                builderOne.append("and a.depositeSlipDate between :fromDate and :toDate ");
            }
            if (transactionType != null) {
                builderOne.append(" and a.coTypeFlag =:transactionType ");
            }
            if (transactionNo != null) {
                builderOne.append(" and a.depositeSlipNumber =:transactionNo");
            }
            builderOne.append(" order by a.depositeSlipDate desc");

            Query queryOne = this.entityManager.createQuery(builderOne.toString());

            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                queryOne.setParameter("fromDate", UtilityService.convertStringDateToDateFormat(fromDate));
                queryOne.setParameter("toDate", UtilityService.convertStringDateToDateFormat(toDate));
            }
            if (transactionType != null) {
                queryOne.setParameter("transactionType", transactionType);
            }
            if (transactionNo != null) {
                queryOne.setParameter("transactionNo", transactionNo);
            }
            queryOne.setParameter("orgId", orgId);
            List<Object[]> resultListOne = queryOne.getResultList();
            finalList.addAll(resultListOne);
            finalSet.addAll(finalList);
        }

        if (transactionType.equals('W')) {

            String queryStrTwo = "select b.paymentId, b.paymentNo, b.paymentDate, b.paymentAmount, b.paymentTypeFlag "
                    + "from AccountPaymentMasterEntity b where b.paymentTypeFlag = '2' and b.orgId =:orgId ";

            /* ############### Query two ############### */
            builderTwo.append(queryStrTwo);

            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                builderTwo.append("and b.paymentDate between :fromDate and :toDate ");
            }
            if (transactionNo != null) {
                builderTwo.append(" and b.paymentNo =:paymentNo ");
            }
            builderTwo.append(" order by b.paymentDate desc");

            Query queryTwo = this.entityManager.createQuery(builderTwo.toString());
            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                queryTwo.setParameter("fromDate", UtilityService.convertStringDateToDateFormat(fromDate));
                queryTwo.setParameter("toDate", UtilityService.convertStringDateToDateFormat(toDate));
            }
            if (transactionNo != null) {
                String paymentNo = transactionNo.toString();
                queryTwo.setParameter("paymentNo", paymentNo);
            }
            queryTwo.setParameter("orgId", orgId);
            List<Object[]> resultListTwo = queryTwo.getResultList();
            finalList.addAll(resultListTwo);
            finalSet.addAll(finalList);
        }

        if (transactionType.equals('D')) {

            String queryStrThree = "select a.depositeSlipId, a.depositeSlipNumber, a.depositeSlipDate, a.depositeAmount, a.coTypeFlag from AccountBankDepositeSlipMasterEntity a "
                    + "where a.depositTypeFlag = 'C' and a.coTypeFlag in ('D') "
                    + "and a.orgid =:orgId ";

            /* ############### Query two ############### */

            builderThree.append(queryStrThree);

            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                builderThree.append("and a.depositeSlipDate between :fromDate and :toDate ");
            }
            if (transactionType != null) {
                builderThree.append(" and a.coTypeFlag =:transactionType ");
            }
            if (transactionNo != null) {
                builderThree.append(" and a.depositeSlipNumber =:transactionNo");
            }
            builderThree.append(" order by a.depositeSlipDate desc");

            Query queryThree = this.entityManager.createQuery(builderThree.toString());

            if (fromDate != null && fromDate != MainetConstants.BLANK || toDate != null && toDate != MainetConstants.BLANK) {
                queryThree.setParameter("fromDate", UtilityService.convertStringDateToDateFormat(fromDate));
                queryThree.setParameter("toDate", UtilityService.convertStringDateToDateFormat(toDate));
            }
            if (transactionType != null) {
                queryThree.setParameter("transactionType", transactionType);
            }
            if (transactionNo != null) {
                queryThree.setParameter("transactionNo", transactionNo);
            }
            queryThree.setParameter("orgId", orgId);
            List<Object[]> resultListThree = queryThree.getResultList();
            finalList.addAll(resultListThree);
            finalSet.addAll(finalList);
        }
        return finalSet;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.ContraVoucherEntryDao#getAllContraEntryData(java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Object[]> getAllContraEntryData(Long orgId) {

        StringBuilder builderOne = new StringBuilder();
        StringBuilder builderTwo = new StringBuilder();
        List<Object[]> finalList = new ArrayList<>();
        Set<Object[]> finalSet = new LinkedHashSet<>();

        String queryStringOne = "select a.depositeSlipId, a.depositeSlipNumber, a.depositeSlipDate, a.depositeAmount, a.coTypeFlag from AccountBankDepositeSlipMasterEntity a, "
                + "TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid "
                + "and a.depositeSlipId = c.depositeSlipId and a.depositTypeFlag = 'C' and a.coTypeFlag in ('T', 'D') "
                + "and a.orgid=c.orgId  and b.orgId=c.orgId   and b.orgId=d.orgId and a.orgid =:orgId ";

        String queryStirngTwo = "select a1.depositeSlipId, a1.depositeSlipNumber, a1.depositeSlipDate, a1.depositeAmount, a1.coTypeFlag  "
                + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                + "and a1.depositTypeFlag = 'C' and a1.coTypeFlag in ('W') and a1.orgid=b1.orgId and a1.orgid =:orgId ";

        builderOne.append(queryStringOne);
        Query queryOne = this.entityManager.createQuery(builderOne.toString());
        queryOne.setParameter("orgId", orgId);
        List<Object[]> resultListOne = queryOne.getResultList();

        builderTwo.append(queryStirngTwo);
        Query queryTwo = this.entityManager.createQuery(builderTwo.toString());
        queryTwo.setParameter("orgId", orgId);
        List<Object[]> resultListTwo = queryTwo.getResultList();

        finalList.addAll(resultListOne);
        finalList.addAll(resultListTwo);

        finalSet.addAll(finalList);

        return finalSet;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.account.dao.ContraVoucherEntryDao#getContraEntryDataById(java.lang.Long, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object[] getContraEntryDataById(Long transactionId, Long orgId) {

        String queryToGetContraType = "select a.coTypeFlag from AccountBankDepositeSlipMasterEntity a where a.depositeSlipId =:transactionId and a.orgid =:orgId";
        StringBuilder builderOne = new StringBuilder();
        builderOne.append(queryToGetContraType);
        Query coQueryOne = this.entityManager.createQuery(builderOne.toString());
        coQueryOne.setParameter("transactionId", transactionId);
        coQueryOne.setParameter("orgId", orgId);
        Character contraType = (Character) coQueryOne.getSingleResult();

        Object[] objArr = new Object[2];
        if (contraType.equals('T')) {

            // Receipt bank account side details
            String transferQueryOne = "select a.depositeSlipNumber,a.depositeSlipDate,d.cpdFeemode,a.depositeAmount,a.depositeBAAccountId,b.rmReceivedfrom, a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a, TbServiceReceiptMasEntity  b, TbSrcptFeesDetEntity c, TbSrcptModesDetEntity d "
                    + "where b.rmRcptid = c.rmRcptid.rmRcptid and b.rmRcptid = d.rmRcptid.rmRcptid  "
                    + "and a.depositeSlipId = c.depositeSlipId and a.depositeSlipId =:transactionId "
                    + "and a.orgid=c.orgId  and b.orgId=c.orgId and b.orgId=d.orgId and a.orgid =:orgId ";

            // payment bank account side details
            String transferQueryTwo = "select b1.narration, b1.baBankAccountId.baAccountId, b1.instrumentNumber, b1.instrumentDate "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.orgid=b1.orgId and a1.orgid =:orgId and a1.depositeSlipId =:transactionId";

            StringBuilder transferBuilderOne = new StringBuilder();
            StringBuilder transferBuilderTwo = new StringBuilder();

            transferBuilderOne.append(transferQueryOne);
            Query queryOne = this.entityManager.createQuery(transferBuilderOne.toString());
            queryOne.setParameter("orgId", orgId);
            queryOne.setParameter("transactionId", transactionId);
            List<Object[]> resultListOne = queryOne.getResultList();

            transferBuilderTwo.append(transferQueryTwo);
            Query queryTwo = this.entityManager.createQuery(transferBuilderTwo.toString());
            queryTwo.setParameter("orgId", orgId);
            queryTwo.setParameter("transactionId", transactionId);
            List<Object[]> resultListTwo = queryTwo.getResultList();

            objArr[0] = resultListOne;
            objArr[1] = resultListTwo;
        }
        if (contraType.equals('W')) {

            // get data from bank deposit slip mas, payment mas,
            String withdrawQueryOne = "select a.depositeSlipNumber,a.depositeSlipDate,a.depositeAmount,a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a where a.depositeSlipId =:transactionId and a.orgid =:orgId ";

            String withdrawQueryTwo = "select b1.narration, b1.baBankAccountId.baAccountId, b1.instrumentNumber, b1.instrumentDate, b1.paymentModeId.cpdId "
                    + "from AccountBankDepositeSlipMasterEntity a1, AccountPaymentMasterEntity b1 where a1.depositeSlipId = b1.depositeSlipId.depositeSlipId "
                    + "and a1.orgid=b1.orgId and a1.orgid =:orgId and a1.depositeSlipId =:transactionId";

            StringBuilder withdrawBuilderOne = new StringBuilder();
            StringBuilder withdrawBuilderTwo = new StringBuilder();

            withdrawBuilderOne.append(withdrawQueryOne);
            Query queryOne = this.entityManager.createQuery(withdrawBuilderOne.toString());
            queryOne.setParameter("orgId", orgId);
            queryOne.setParameter("transactionId", transactionId);
            List<Object[]> resultListOne = queryOne.getResultList();

            withdrawBuilderTwo.append(withdrawQueryTwo);
            Query queryTwo = this.entityManager.createQuery(withdrawBuilderTwo.toString());
            queryTwo.setParameter("orgId", orgId);
            queryTwo.setParameter("transactionId", transactionId);
            List<Object[]> resultListTwo = queryTwo.getResultList();
            objArr[0] = resultListOne;
            objArr[1] = resultListTwo;

        }
        if (contraType.equals('D')) {
            String depositQueryOne = "select a.depositeSlipNumber,a.depositeSlipDate,a.depositeAmount, a.depositeRemark, a.depositeBAAccountId, a.coTypeFlag "
                    + "from AccountBankDepositeSlipMasterEntity a where a.depositeSlipId =:transactionId and a.orgid =:orgId ";
            String depositQueryTwo = "select d.cpdDenomId, d.denominationCount from AccountBankDepositeSlipDenomEntity d where d.depositeSlipId.depositeSlipId =:transactionId and d.orgid =:orgId";
            StringBuilder depositBuilderOne = new StringBuilder();
            StringBuilder depositBuilderTwo = new StringBuilder();
            depositBuilderOne.append(depositQueryOne);
            Query queryOne = this.entityManager.createQuery(depositBuilderOne.toString());
            queryOne.setParameter("orgId", orgId);
            queryOne.setParameter("transactionId", transactionId);
            List<Object[]> resultListOne = queryOne.getResultList();
            depositBuilderTwo.append(depositQueryTwo);
            Query queryTwo = this.entityManager.createQuery(depositBuilderTwo.toString());
            queryTwo.setParameter("orgId", orgId);
            queryTwo.setParameter("transactionId", transactionId);
            List<Object[]> resultListTwo = queryTwo.getResultList();
            objArr[0] = resultListOne;
            objArr[1] = resultListTwo;

        }

        return objArr;

    }

}
