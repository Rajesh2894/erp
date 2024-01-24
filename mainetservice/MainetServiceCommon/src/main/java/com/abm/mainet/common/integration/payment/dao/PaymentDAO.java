/**
 *
 */
package com.abm.mainet.common.integration.payment.dao;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface PaymentDAO {

    /**
     * @return Long
     */
    Long genrateTrackId();

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
     * @return PaymentTransactionMas
     */
    PaymentTransactionMas getOnlineTransactionMasByTrackId(Long txnid);

    /**
     * @param paymentTransactionMas
     */
    PaymentTransactionMas updateOnlineTransactionMas(PaymentTransactionMas paymentTransactionMas);

    /**
     * @param orgid
     * @return Map<Long, String>
     */
    Map<Long, String> getAllPgBank(Long orgid);

    /**
     * @param smServiceId
     * @param orgid
     * @return String
     */
    String getServiceShortName(Long smServiceId, Long orgid);

    /**
     * @param serviceShortName
     * @param orgId
     * @return ServiceMaster
     */
    ServiceMaster getServiceName(String serviceShortName, Long orgId);

    
    /**
     * @param cbBankName
     * @param orgId
     * @return ViewBankMaster
     */
    PGBankDetail getBankDetailByBankName(final String cbBankName, final Long orgId) ;
    
    List<PaymentTransactionMas> getPaymentMasterListById(Long tranCmId);
    
    PaymentTransactionMas getTransMasByMihpayId(String mihpayid);
    
    public long getCountByTransactionId(String recvMihpayid, Long orgId);
    
    public void saveTranscationBeforeResponse(PaymentTransactionMas paymentTransactionMas);

	PaymentTransactionMas getStatusByReferenceId(String referenceId);
	
	public PGBankDetail getBankDetailByMerchantId(String merchantId, Long orgId);
	
	PGBankDetail getBankDetailByBankName(final String cbBankName, final Long orgId, String pgStatus) ;

	Object[] checkPaymentStatus(String referenceId);
	
	Map<Long, String> getAllPgBankByDeptCode(Long orgid, String deptCode);

	PGBankDetail getBankDetailByBankNameAndDeptCode(final String cbBankName, final Long orgId, final String deptCode);
    
}
