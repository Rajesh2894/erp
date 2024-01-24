package com.abm.mainet.common.master.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.domain.TbDepositEntity;

public interface TbDepositReceiptRepositoryCustom {
	
	 List<TbDepositEntity> getDepositRefundList(Long depNo, Long vmVendorid, Long cpdDepositType, Long sacHeadId,
	            Date date, BigDecimal depositAmount, Long orgId);

}
