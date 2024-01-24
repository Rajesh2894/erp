package com.abm.mainet.property.service;

import java.math.BigInteger;
import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;

public interface BillDeletionService {

    BigInteger[] validateBillDeletion(String propNo, Long orgId, List<TbBillMas> tbBillMasList);

    void externalVoucherPostingInAccount(Long orgId, VoucherPostExternalDTO dto);

}
