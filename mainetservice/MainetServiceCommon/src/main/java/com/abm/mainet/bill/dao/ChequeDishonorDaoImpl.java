package com.abm.mainet.bill.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptModesDetEntity;

/**
 * @author Rahul.Yadav
 *
 */
@Repository
public class ChequeDishonorDaoImpl extends AbstractDAO<Long> implements ChequeDishonorDao {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.ChequeDishonorDao#fetchChequePaymentData(java.lang.Long, long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<TbServiceReceiptMasEntity> fetchChequePaymentData(final List<Long> chequeId,
            final long orgid, final Long deptId, final Date fromDate, final Date toDate, final Long receiptNo,
            final Long chequeNo, final Long bankId) {

        final StringBuilder qlString1 = new StringBuilder(
                "select s from  TbServiceReceiptMasEntity s where  s.dpDeptId=:dpDeptId and s.orgId=:orgId"
                        + " and s.rmRcptid in (select mode.rmRcptid.rmRcptid from TbSrcptModesDetEntity mode where mode.orgId=:orgId and  mode.cpdFeemode"
                        + " in (:cpdFeemode) and mode.rmRcptid.rmRcptid in (select d.rmRcptid.rmRcptid from TbSrcptFeesDetEntity d "
                        + " where d.orgId=:orgId  ) ");

        if (chequeNo != null) {
            qlString1.append(" and mode.rdChequeddno=:rdChequeddno ");
        }else {
        	qlString1.append(" and mode.rdSrChkDis is null ");
        }
        qlString1.append(")");

        if ((fromDate != null) && (toDate != null)) {
            qlString1.append(" and s.rmDate between :fromDate and :todate ");
        }

        if (receiptNo != null) {
            qlString1.append(" and s.rmRcptno=:rmRcptno ");
        }
        qlString1.append(" order by s.rmRcptno desc ");

        final Query query = entityManager.createQuery(qlString1.toString());
        query.setParameter("cpdFeemode", chequeId);
        query.setParameter("orgId", orgid);
        query.setParameter("dpDeptId", deptId);
        if ((fromDate != null) && (toDate != null)) {
            query.setParameter("fromDate", fromDate);
            query.setParameter("todate", toDate);
        }
        if (receiptNo != null) {
            query.setParameter("rmRcptno", receiptNo);
        }
        if (chequeNo != null) {
            query.setParameter("rdChequeddno", chequeNo);
        }
        final List<TbServiceReceiptMasEntity> result = query.getResultList();
        return result;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.ChequeDishonorDao#updateFeeDet(com.abm.mainetservice.core.entity.
     * TbSrcptModesDetEntity)
     */
    @Override
    public void updateFeeDet(final TbSrcptModesDetEntity feeDet) {

        entityManager.merge(feeDet);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.cfc.challan.dao.ChequeDishonorDao#fetchReceiptFeeDetails(java.lang.Long)
     */
    @Override
    public TbSrcptModesDetEntity fetchReceiptFeeDetails(final Long rdModesid, final Long orgId) {
        final Query query = entityManager
                .createQuery("select m from TbSrcptModesDetEntity m where m.rdModesid=:rdModesid and m.orgId=:orgId ");
        query.setParameter("rdModesid", rdModesid);
        query.setParameter("orgId", orgId);
        final TbSrcptModesDetEntity result = (TbSrcptModesDetEntity) query.getSingleResult();
        return result;
    }

    @Override
    public void updateLoiNotPaid(final String rmLoiNo, final Long orgid) {
        final Query query = entityManager.createQuery(
                "update TbLoiMasEntity m set m.loiPaid='N' where m.loiNo=:loiNo and m.compositePrimaryKey.orgid=:orgId ");
        query.setParameter("loiNo", rmLoiNo);
        query.setParameter("orgId", orgid);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TbServiceReceiptMasEntity> fetchChequePaymentDataWithAccount(final List<Long> chequeId, final long orgid,
            final Long deptId, final Date fromDate, final Date toDate, final Long receiptNo, final Long chequeNo,
            final Long bankId) {
        final StringBuilder qlString1 = new StringBuilder(
                "select s from  TbServiceReceiptMasEntity s where  s.dpDeptId=:dpDeptId and s.orgId=:orgId"
                        + " and s.rmRcptid in (select mode.rmRcptid.rmRcptid from TbSrcptModesDetEntity mode where mode.orgId=:orgId and  mode.cpdFeemode"
                        + " in (:cpdFeemode) and mode.rmRcptid.rmRcptid in (select d.rmRcptid.rmRcptid from TbSrcptFeesDetEntity d "
                        + " where d.orgId=:orgId and d.depositeSlipId is not null and d.depositeSlipId in ( select a.depositeSlipId from "
                        + " AccountBankDepositeSlipMasterEntity a where a.depositeBAAccountId=:cbBankid and a.orgid=:orgId ) ) ");

        if (chequeNo != null) {
            qlString1.append(" and mode.rdChequeddno=:rdChequeddno ");
         }else {
        	qlString1.append(" and mode.rdSrChkDis is null ");
        }
        qlString1.append(")");

        if ((fromDate != null) && (toDate != null)) {
            qlString1.append(" and s.rmDate between :fromDate and :todate ");
        }

        if (receiptNo != null) {
            qlString1.append(" and s.rmRcptno=:rmRcptno ");
        }
        qlString1.append(" order by s.rmRcptno desc ");

        final Query query = entityManager.createQuery(qlString1.toString());
        query.setParameter("cpdFeemode", chequeId);
        query.setParameter("orgId", orgid);
        query.setParameter("dpDeptId", deptId);
        if ((fromDate != null) && (toDate != null)) {
            query.setParameter("fromDate", fromDate);
            query.setParameter("todate", toDate);
        }
        if (receiptNo != null) {
            query.setParameter("rmRcptno", receiptNo);
        }
        if (chequeNo != null) {
            query.setParameter("rdChequeddno", chequeNo);
        }
        query.setParameter("cbBankid", bankId);
        final List<TbServiceReceiptMasEntity> result = query.getResultList();
        return result;
    }
    
    
    @Override
    public List<TbSrcptModesDetEntity> fetchReceiptFeeDetails(List<Long> rdModesId, Long orgId) {
        final Query query = entityManager
                .createQuery("select m from TbSrcptModesDetEntity m where m.rdModesid in (:rdModesid) and m.orgId=:orgId ");
        query.setParameter("rdModesid", rdModesId);
        query.setParameter("orgId", orgId);        
        return  (List<TbSrcptModesDetEntity>) query.getResultList();
    }
    
    public  List<Object[]> fetchPaymentMode(Long orgId, String date1) {
    	final StringBuilder queryString = new StringBuilder();
    	queryString.append("select (select cpd_desc from tb_comparam_det where cpd_id=CPD_FEEMODE) modes,sum(rd_amount) -- ,count(1) \r\n" + 
            		"from tb_receipt_mode a,tb_receipt_mas b where a.rm_rcptid=b.rm_rcptid and dp_deptid=10 and a.orgid=:orgId and CPD_FEEMODE!=123\r\n" + 
            		"and date(rm_date)=:dateN \r\n" + 
            		"group by CPD_FEEMODE,b.orgid");
    	Query query = this.createNativeQuery(queryString.toString());
	    query.setParameter("orgId", orgId);	
	    query.setParameter("dateN", date1);
   		List<Object[]> entityList=query.getResultList();
   	return entityList;
        
    }
}
