/**
 * 
 */
package com.abm.mainet.common.service;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.TbReceiptDuplicateDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IDuplicateReceiptService {

    public void save(final TbReceiptDuplicateDTO dto);

    void saveDuplicateReceipt(ChallanReceiptPrintDTO receiptDto, TbServiceReceiptMasEntity receiptMaster,
            CommonChallanDTO offline);

	public ChallanReceiptPrintDTO getRevenueReceiptDetails(Long reciptId, Long receiptNo, String assNo,Long orgId, int langId);

	public ChallanReceiptPrintDTO getReceiptDetails(Long receiptId, Long receiptNo, Long applicationId);
}
