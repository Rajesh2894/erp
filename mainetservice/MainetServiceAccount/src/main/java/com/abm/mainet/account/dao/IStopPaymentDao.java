/**
 * 
 */
package com.abm.mainet.account.dao;

import java.util.Date;

import com.abm.mainet.account.domain.AccountPaymentMasterEntity;

/**
 * @author Anwarul.Hassan
 * @since 12-Dec-2019
 */
public interface IStopPaymentDao {
    AccountPaymentMasterEntity searchPaymentDetails(String paymentNo, Long instrumentNumber, Date paymentDate, Long orgId);
}
