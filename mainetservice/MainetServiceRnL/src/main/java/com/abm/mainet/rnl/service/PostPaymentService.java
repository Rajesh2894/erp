/**
 * 
 */
package com.abm.mainet.rnl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.payment.service.IPostPaymentService;
import com.abm.mainet.rnl.repository.EstateBookingRepository;

/**
 * @author sarojkumar.yadav
 *
 */
@Service(value = "RnLPostPayment")
public class PostPaymentService implements IPostPaymentService {

    @Autowired
    private EstateBookingRepository estateBookingRepository;

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.integration.payment.service.IPostPaymentService#postPaymentSuccess()
     */
    @Override
    @Transactional
    public void postPaymentSuccess(final CommonChallanDTO CommonChallanDTO, TbServiceReceiptMasEntity receiptMaster) {
        estateBookingRepository.updateBookingStatus(CommonChallanDTO.getApplNo(), MainetConstants.FlagY, true);
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.integration.payment.service.IPostPaymentService#postPaymentFailure()
     */
    @Override
    @Transactional
    public void postPaymentFailure(final CommonChallanDTO CommonChallanDTO) {
        estateBookingRepository.updateBookingStatus(CommonChallanDTO.getApplNo(), MainetConstants.FlagN, false);
    }

}
