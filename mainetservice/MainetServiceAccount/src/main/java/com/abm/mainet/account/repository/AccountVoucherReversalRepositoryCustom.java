package com.abm.mainet.account.repository;

import java.util.List;

import com.abm.mainet.account.dto.VoucherReversalDTO;

/**
 *
 * @author Vivek.Kumar
 * @since 19 May 2017
 */
public interface AccountVoucherReversalRepositoryCustom {

    List<Object[]> findRerodsForVoucherReversal(VoucherReversalDTO dto, long deptId, long orgId);

    void saveOrUpdateReceipt(Long transactionId, VoucherReversalDTO dto, long orgId, String ipMacAddress);

}
