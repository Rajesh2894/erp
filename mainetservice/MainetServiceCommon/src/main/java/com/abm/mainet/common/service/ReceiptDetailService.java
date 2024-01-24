package com.abm.mainet.common.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

@WebService
public interface ReceiptDetailService {

	List<TbServiceReceiptMasBean> findReceiptDet(TbServiceReceiptMasBean receiptMasBean);

	TbServiceReceiptMasBean findReceiptById(Long rmRcptid, Long orgId,int langId);

	ChallanReceiptPrintDTO setValuesAndPrintReport(Long rmRcptid, Long orgId, int langId);

}
