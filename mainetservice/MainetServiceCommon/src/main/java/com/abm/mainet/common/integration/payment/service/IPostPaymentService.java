/**
 * 
 */
package com.abm.mainet.common.integration.payment.service;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IPostPaymentService {

    /**
     * method use to do task after payment gets success
     * 
     * @param PostPaymentDTO
     */
    public void postPaymentSuccess(final CommonChallanDTO CommonChallanDTO , TbServiceReceiptMasEntity receiptMaster);

    /**
     * method use to do task after payment gets fail
     * 
     * @param PostPaymentDTO
     */
    public void postPaymentFailure(final CommonChallanDTO CommonChallanDTO);

}
