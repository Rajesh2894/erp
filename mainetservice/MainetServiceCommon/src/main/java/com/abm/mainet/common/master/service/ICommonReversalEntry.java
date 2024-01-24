package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.dto.TrasactionReversalDTO;

public interface ICommonReversalEntry 

{

	
	 List<TbServiceReceiptMasBean> getReceiptByDeptAndDate(TrasactionReversalDTO trasactionReversalDTO,Long orgId,Long deptId);
	 
	 int [] validateReceipt(TrasactionReversalDTO trasactionReversalDTO,Long orgId,Long deptId,long receiptId);
	 
	 int updateReceipt(TbServiceReceiptMasBean ReceiptMasBean,long  orgid,long userId);
	 
	 List<TbServiceReceiptMasBean> getReceiptByDeptAndDateAndTransactionId(TrasactionReversalDTO trasactionReversalDTO,
				Long orgId, Long deptId, String transactionId);
	
}
