/**
 * 
 */
package com.abm.mainet.common.integration.payment.service;

import java.util.List;

import com.abm.mainet.common.integration.payment.entity.PGBankDetail;
import com.abm.mainet.common.integration.payment.entity.PGBankParameter;

/**
 * @author cherupelli.srikanth
 *@since 28 september 2020
 */
public interface PgBankParameterService {

	void savePgBankParameterDetails(PGBankDetail pgBankMas);
}
