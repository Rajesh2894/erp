
package com.abm.mainet.account.dao;

import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author dharmendra.chouhan
 *
 */
public interface AccountReceiptEntryDao {

    public Iterable<TbServiceReceiptMasEntity> getReceiptDetail(Long orgId, BigDecimal rmAmount, Long rmRcptno,
            String rmReceivedfrom, Date rmDate);

}
