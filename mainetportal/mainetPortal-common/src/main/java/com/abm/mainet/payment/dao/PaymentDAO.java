package com.abm.mainet.payment.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.payment.domain.PGBankDetail;
import com.abm.mainet.payment.domain.PGBankParameter;
import com.abm.mainet.payment.domain.PaymentTransactionMas;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface PaymentDAO {

    /**
     * @return Long
     */
    Long invokeSequence();

    /**
     * @param serviceId
     * @return String
     */
    String findServiceNameForServiceId(Long serviceId);

    /**
     * @param paymentTransactionMas
     */
    void savePaymentTransaction(PaymentTransactionMas paymentTransactionMas);

    /**
     * @param cbBankid
     * @param orgId
     * @return List<PGBankParameter>
     */
    List<PGBankParameter> getMerchantMasterParamByBankId(Long cbBankid, long orgId);

    /**
     * @param cbBankid
     * @param orgId
     * @return ViewBankMaster
     */
    PGBankDetail getBankDetailByBankId(Long cbBankid, Long orgId);

    /**
     * @param txnid
     * @return
     */
    PaymentTransactionMas getOnlineTransactionMasByTrackId(Long txnid);

    /**
     * @param paymentTransactionMas
     */
    void updateOnlineTransactionMas(PaymentTransactionMas paymentTransactionMas);

    /**
     * @param orgid
     * @return
     */
    Map<Long, String> getAllPgBank(Long orgid);

    /**
     * @param smServiceId
     * @param orgid
     * @return
     */
    String getServiceShortName(Long smServiceId, Long orgid);

    List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId);

}
