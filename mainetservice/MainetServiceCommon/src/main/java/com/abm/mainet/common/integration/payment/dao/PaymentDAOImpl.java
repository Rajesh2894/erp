/**
 *
 */
package com.abm.mainet.common.integration.payment.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dao.RestDaoImpl;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class PaymentDAOImpl extends RestDaoImpl<PaymentTransactionMas> implements PaymentDAO {

    private static Logger LOG = Logger.getLogger(PaymentDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#invokeSequence()
     */
    @Override
    public Long genrateTrackId() {

        final Query query = entityManager.createNativeQuery("select TransId.nextval from dual");
        final BigDecimal result = (BigDecimal) query.getSingleResult();
        return result.longValue();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#savePaymentTransaction(com.abm.mainetsource.payment.entity.
     * PaymentTransactionMas)
     */
    @Override
    public void savePaymentTransaction(final PaymentTransactionMas paymentTransactionMas) {
        LOG.info("Start The savePaymentTransaction()");
        try {

            // PaymentTransactionMas entity =entityManager.merge(paymentTransactionMas) ;
            entityManager.persist(paymentTransactionMas);
            
        } catch (final Exception e) {

            LOG.error("Exception occur in savePaymentTransaction()", e);
            throw new FrameworkException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getMerchantMasterParamByBankId(long, long)
     */
    @Override
    public List<PGBankParameter> getMerchantMasterParamByBankId(final Long cbBankid, final long orgId) {

        final Query query = entityManager.createQuery("Select pg  from PGBankParameter pg where pg.pgId = ?1");
        query.setParameter(1, cbBankid);
        final List<PGBankParameter> bankParamDets = query.getResultList();
        return bankParamDets;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getBankDetailByBankId(long, long)
     */
    @Override
    public PGBankDetail getBankDetailByBankId(final Long cbBankid, final Long orgId) {
        final Query query = entityManager.createQuery("select p from PGBankDetail p where p.pgId =?1 and p.orgId =?2");
        query.setParameter(1, cbBankid);
        query.setParameter(2, orgId);
        final PGBankDetail bankMaster = (PGBankDetail) query.getSingleResult();

        return bankMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getOnlineTransactionMasByTrackId(java.lang.Long)
     */
    @Override
    public PaymentTransactionMas getOnlineTransactionMasByTrackId(final Long txnid) {

        final Query query = entityManager.createQuery("Select o from PaymentTransactionMas o where o.tranCmId = ?1 ");
        query.setParameter(1, txnid);
        final PaymentTransactionMas onlineTransactionMas = (PaymentTransactionMas) query.getSingleResult();
        return onlineTransactionMas;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#updateOnlineTransactionMas(com.abm.mainetsource.payment.entity.
     * PaymentTransactionMas)
     */
    @Override
    public PaymentTransactionMas updateOnlineTransactionMas(final PaymentTransactionMas paymentTransactionMas) {

        LOG.info("Start The updateOnlineTransactionMas()");
        PaymentTransactionMas applicationMaster = null;
        try {
           applicationMaster = entityManager.merge(paymentTransactionMas);
            entityManager.persist(applicationMaster);

        } catch (final Exception e) {

            LOG.error("Exception occur in updateOnlineTransactionMas()", e);
            throw new FrameworkException(e.getMessage());
        }
		return applicationMaster;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getAllPgBank(long)
     */
    @Override
    public Map<Long, String> getAllPgBank(final Long orgid) {

        final Query query = entityManager
                .createQuery("select bd from PGBankDetail  bd  where bd.orgId = ?1 and bd.pgStatus='A' ");
        query.setParameter(1, orgid);
        final List<PGBankDetail> bankDetails = query.getResultList();

        final Map<Long, String> bankMap = new LinkedHashMap<>(0);
        for (final PGBankDetail pgBankDetail : bankDetails) {

            bankMap.put(pgBankDetail.getPgId(), pgBankDetail.getPgName());
        }

        return bankMap;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getServiceShortName(java.lang.Long, java.lang.Long)
     */
    @Override
    public String getServiceShortName(final Long smServiceId, final Long orgid) {

        final Query query = entityManager
                .createNamedQuery("select sm from ServiceMaster sm where sm.smServiceId =:smServiceId and sm.orgid =:orgId");
        query.setParameter("smServiceId", smServiceId);
        query.setParameter("orgId", orgid);
        return (String) query.getSingleResult();
    }

    @Override
    public ServiceMaster getServiceName(final String serviceShortName, final Long orgId) {

        LOG.info("Start the getServiceName()");
        ServiceMaster service = null;
        try {

            final Query query = entityManager
                    .createQuery("select sm from ServiceMaster sm where sm.smShortdesc =:shortName and sm.orgid =:orgId ");
            query.setParameter("shortName", serviceShortName);
            query.setParameter("orgId", orgId);
            service = (ServiceMaster) query.getSingleResult();
        } catch (final Exception e) {
            LOG.error("Exception occur in getServiceName() ", e);
            return service;
        }
        return service;
    }

    /**
     * @param cbBankName
     * @param orgId
     * @return List<PGBankParameter>
     */
    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getBankDetailByBankId(long, long)
     */
    @Override
    public PGBankDetail getBankDetailByBankName(final String cbBankName, final Long orgId) {
        final Query query = entityManager.createQuery("select p from PGBankDetail p where p.pgName =?1 and p.orgId =?2 AND pgStatus='A' ");
        query.setParameter(1, cbBankName);
        query.setParameter(2, orgId);
        final PGBankDetail bankMaster = (PGBankDetail) query.getSingleResult();

        return bankMaster;
    }

	@Override
	public List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId) {
		 final Query query = entityManager.createQuery("Select o from PaymentTransactionMas o where o.tranCmId = ?1 and o.recvStatus in ('Aborted','failure','success','Cancel')");
	     query.setParameter(1, tranCmId);
	     final List<PaymentTransactionMas> masterList = query.getResultList();
		return masterList;
	}

	@Override
	public PaymentTransactionMas getTransMasByMihpayId(String recvMihpayid) {
		final Query query = createQuery("Select tr from PaymentTransactionMas tr where tr.recvMihpayid =:recvMihpayid");
	    query.setParameter("recvMihpayid", recvMihpayid);
	    
	    List<PaymentTransactionMas> paymentTransList = new ArrayList<>();
	    paymentTransList=query.getResultList();
	    if(CollectionUtils.isNotEmpty(paymentTransList)){
	    	return paymentTransList.get(0);
	    }
	    	return null;
	}

	@Override
	public long getCountByTransactionId(String recvMihpayid, Long orgId) {
		final Query query = createQuery(
                "select count(*) from PaymentTransactionMas tr where tr.orgId=:orgId and tr.recvMihpayid=:recvMihpayid");
        query.setParameter("recvMihpayid", recvMihpayid);
        query.setParameter("orgId", orgId);
        Long result = (Long) query.getSingleResult();
        return result;
	}

	@Override
	public void saveTranscationBeforeResponse(PaymentTransactionMas paymentTransactionMas) {
		Session sess=(Session)entityManager.getDelegate();
		Session newSession = sess.sessionWithOptions().openSession();
		Transaction tx = newSession.beginTransaction();
		newSession.saveOrUpdate(paymentTransactionMas);
		tx.commit();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentTransactionMas getStatusByReferenceId(String referenceId) {
		final Query query = createQuery(
	                "from PaymentTransactionMas tr where tr.tranCmId in ("
	                        + "select max(p.tranCmId) from PaymentTransactionMas p  WHERE  p.referenceId=:referenceId and p.recvStatus='Pending' and p.pgSource is not null)");
		query.setParameter("referenceId", referenceId);
		List<PaymentTransactionMas> paymentTransList = new ArrayList<>();
	    paymentTransList=query.getResultList();
	    if(CollectionUtils.isNotEmpty(paymentTransList)){
	    	return paymentTransList.get(0);
	    }
	    	return null;
	}

	@Override
	public PGBankDetail getBankDetailByMerchantId(String merchantId, Long orgId) {
		final Query query = createQuery("Select pg from PGBankDetail pg where pg.merchantId =:merchantId and pg.orgId =:orgId");
	    query.setParameter("merchantId", merchantId);
	    query.setParameter("orgId", orgId);
	    
	    List<PGBankDetail> pgList = new ArrayList<>();
	    pgList = query.getResultList();
	    if(CollectionUtils.isNotEmpty(pgList)){
	    	return pgList.get(0);
	    }
	    	return null;
	}

	@Override
	public PGBankDetail getBankDetailByBankName(String cbBankName, Long orgId, String pgStatus) {
        final StringBuilder builder = new StringBuilder();
        builder.append("select p from PGBankDetail p where p.pgName =?1 and p.orgId =?2");
        if((pgStatus != null) && !(MainetConstants.BLANK.equals(pgStatus))) {
			builder.append(" AND pgStatus=?3");
		}
        final Query query = createQuery(builder.toString());
        query.setParameter(1, cbBankName);
        query.setParameter(2, orgId);
        if((pgStatus != null) && !(MainetConstants.BLANK.equals(pgStatus))) {
        	query.setParameter(3, pgStatus);
		}
        final PGBankDetail bankMaster = (PGBankDetail) query.getSingleResult();

        return bankMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] checkPaymentStatus(String referenceId) {
		final Query query = createQuery(
				"select tr.referenceId, tr.referenceDate, tr.sendProductinfo,tr.sendFirstname,tr.sendPhone,tr.sendEmail,tr.recvStatus,tr.sendAmount,tr.recvMihpayid from PaymentTransactionMas tr where tr.tranCmId in ("
						+ "select max(p.tranCmId) from PaymentTransactionMas p  WHERE  p.referenceId=:referenceId  and p.pgSource is not null)");
		query.setParameter("referenceId", referenceId);
		List<Object[]> paymentTransList = new ArrayList<>();
		paymentTransList = query.getResultList();
		if (CollectionUtils.isNotEmpty(paymentTransList)) {
			return paymentTransList.get(0);
		}
		return null;
	}
	
	@Override
    public Map<Long, String> getAllPgBankByDeptCode(final Long orgid, final String deptCode) {
 
        final Query query = entityManager
                .createQuery("select bd from PGBankDetail  bd  where bd.orgId = ?1 and bd.deptCode = ?2 and bd.pgStatus='A' ");
        query.setParameter(1, orgid);
        query.setParameter(2, deptCode);
        final List<PGBankDetail> bankDetails = query.getResultList();
 
        final Map<Long, String> bankMap = new LinkedHashMap<>(0);
        for (final PGBankDetail pgBankDetail : bankDetails) {
 
            bankMap.put(pgBankDetail.getPgId(), pgBankDetail.getPgName());
        }
 
        return bankMap;
    }
    @Override
    public PGBankDetail getBankDetailByBankNameAndDeptCode(final String cbBankName, final Long orgId, final String deptCode) {
        final Query query = entityManager.createQuery("select p from PGBankDetail p where p.pgName =?1 and p.orgId =?2 and p.deptCode=?3 AND pgStatus='A' ");
        query.setParameter(1, cbBankName);
        query.setParameter(2, orgId);
        query.setParameter(3, deptCode);
        final PGBankDetail bankMaster = (PGBankDetail) query.getSingleResult();
 
        return bankMaster;
    }

}
