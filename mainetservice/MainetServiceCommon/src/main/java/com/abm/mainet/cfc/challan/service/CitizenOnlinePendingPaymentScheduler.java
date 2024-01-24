/**
 * 
 */
package com.abm.mainet.cfc.challan.service;

import com.abm.mainet.cfc.challan.ui.model.CitizenOnlinePendingPayment;

/**
 * @author cherupelli.srikanth
 *
 */
public interface CitizenOnlinePendingPaymentScheduler {

	void runOnlinePendingPaymentDate(CitizenOnlinePendingPayment model);
}
