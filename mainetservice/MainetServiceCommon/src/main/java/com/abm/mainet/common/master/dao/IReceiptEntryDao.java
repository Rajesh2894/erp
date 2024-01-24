package com.abm.mainet.common.master.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

public interface IReceiptEntryDao {
    public Iterable<TbServiceReceiptMasEntity> getReceiptDetail(Long orgId, BigDecimal rmAmount, Long rmRcptno,
            String rmReceivedfrom, Date rmDate, Long deptId);

	public Iterable<TbServiceReceiptMasEntity> getReceiptDet(TbServiceReceiptMasBean receiptMasBean);
   //Defect #145245
	public TbServiceReceiptMasEntity getReceiptDetailByIds(Long rcptNo, Long orgId, Long deptId, String loiNo,Date rmDate,String rmAmount,Long appNo,Long refNo,String rmReceivedfrom, String date);

	int getRecieptENTRYcount(Long orgId, Long userId, Long wardId);
}
