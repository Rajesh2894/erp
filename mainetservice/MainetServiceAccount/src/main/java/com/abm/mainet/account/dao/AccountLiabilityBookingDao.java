package com.abm.mainet.account.dao;

/**
 * @author tejas.kotekar
 *
 */
public interface AccountLiabilityBookingDao {

    Boolean isLiabilityExists(Long tenderId, Long orgId);

}
