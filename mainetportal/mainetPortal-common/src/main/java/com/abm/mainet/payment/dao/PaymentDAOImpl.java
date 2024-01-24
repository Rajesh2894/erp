package com.abm.mainet.payment.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.payment.domain.PGBankDetail;
import com.abm.mainet.payment.domain.PGBankParameter;
import com.abm.mainet.payment.domain.PaymentTransactionMas;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public class PaymentDAOImpl extends AbstractDAO<PaymentTransactionMas> implements PaymentDAO {

    private static final String EXCEPTION_OCCUR_IN_SAVE = "Exception occur in save()";
    private static Logger LOG = Logger.getLogger(PaymentDAOImpl.class);

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#invokeSequence()
     */
    @Override
    public Long invokeSequence() {

        final Query query = entityManager.createNativeQuery("select TransId.nextval from dual");
        final BigDecimal result = (BigDecimal) query.getSingleResult();
        return result.longValue();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#findServiceNameForServiceId(long)
     */
    @Override
    public String findServiceNameForServiceId(final Long serviceId) {

        final Query query = entityManager.createQuery("select am from PortalService am  WHERE am.serviceId = :serviceId");
        query.setParameter("serviceId", serviceId);
        final PortalService portalService = (PortalService) query.getSingleResult();
        return portalService.getServiceName();
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#savePaymentTransaction(com.abm.mainetsource.payment.entity.
     * PaymentTransactionMas)
     */
    @Override
    public void savePaymentTransaction(final PaymentTransactionMas paymentTransactionMas) {
        try {
            entityManager.persist(paymentTransactionMas);

        } catch (final Exception e) {

            throw new FrameworkException(EXCEPTION_OCCUR_IN_SAVE, e);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getMerchantMasterParamByBankId(long, long)
     */
    @Override
    public List<PGBankParameter> getMerchantMasterParamByBankId(final Long cbBankid, final long orgId) {

        final Query query = entityManager.createQuery(
                "Select pg from PGBankParameter pg, PGBankDetail pd  where pd.pgId= pg.pgId and pd.bankId = ?1 and pd.orgId =?2");
        query.setParameter(1, cbBankid);
        query.setParameter(2, orgId);
        @SuppressWarnings("unchecked")
        final List<PGBankParameter> bankParamDets = query.getResultList();
        return bankParamDets;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getBankDetailByBankId(long, long)
     */
    @Override
    public PGBankDetail getBankDetailByBankId(final Long cbBankid, final Long orgId) {
        final Query query = entityManager.createQuery("select p from PGBankDetail p where p.bankId =?1 and p.orgId =?2");
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
    public void updateOnlineTransactionMas(final PaymentTransactionMas paymentTransactionMas) {

        try {
            final PaymentTransactionMas applicationMaster = entityManager.merge(paymentTransactionMas);
            entityManager.persist(applicationMaster);

        } catch (final Exception e) {

            throw new FrameworkException(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getAllPgBank(long)
     */
    @Override
    public Map<Long, String> getAllPgBank(final Long orgid) {

        final Query query = entityManager.createQuery("select bd from PGBankDetail  bd  where bd.orgId = ?1 ");
        query.setParameter(1, orgid);
        @SuppressWarnings("unchecked")
        final List<PGBankDetail> bankDetails = query.getResultList();

        final Map<Long, String> bankMap = new LinkedHashMap<>(0);
        for (final PGBankDetail pgBankDetail : bankDetails) {

            bankMap.put(pgBankDetail.getBankId(), pgBankDetail.getPgName());
        }

        return bankMap;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetsource.payment.dao.PaymentDAO#getServiceShortName(java.lang.Long, java.lang.Long)
     */
    @Override
    public String getServiceShortName(final Long smServiceId, final Long orgid) {

        final Query query = entityManager.createNamedQuery(MainetConstants.NAMED_QUERY.SELECT_SERVICE_SHORT_NAME);
        query.setParameter(MainetConstants.Common.SMS_SERVICE, smServiceId);
        query.setParameter(MainetConstants.Common.ORGID, orgid);
        return (String) query.getSingleResult();
    }

	@Override
	public List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId) {
		 final Query query = entityManager.createQuery("Select o from PaymentTransactionMas o where o.tranCmId = ?1 and o.recvStatus in ('Aborted','failure','success','Cancel')");
		 query.setParameter(1, tranCmId);
	     final List<PaymentTransactionMas> masterList = query.getResultList();
		return masterList;
	}

    /*
     * @Override public List<PGPortalBankDetail> getPGPortalBankList(final String bankId) { final Query query =
     * createQuery("select p from PGPortalBankDetail p where p.isdeleted =?1 and p.pgBankId =?2 "); query.setParameter(1,
     * Long.valueOf(1)); query.setParameter(2, Long.valueOf(bankId));
     * @SuppressWarnings("unchecked") final List<PGPortalBankDetail> bankDetail = query.getResultList(); return bankDetail; }
     */

}
