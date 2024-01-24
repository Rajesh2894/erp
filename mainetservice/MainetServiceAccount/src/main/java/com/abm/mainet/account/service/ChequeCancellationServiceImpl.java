package com.abm.mainet.account.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.ChequeCancellationDto;
import com.abm.mainet.account.repository.TbAcChequebookleafMasJpaRepository;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author tejas.kotekar
 *
 */
@Service
public class ChequeCancellationServiceImpl implements ChequeCancellationService {

    @Resource
    private TbAcChequebookleafMasJpaRepository chequebookRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void chequeCancellation(final ChequeCancellationDto cancellationDto) {

        final Organisation org = new Organisation();
        org.setOrgid(cancellationDto.getOrgId());

        /*
         * long chequebookdetid=cancellationDto.getNewChequeBookDetId(); long
         * paymentid=chequebookRepository.getpaymentId(chequebookdetid);
         */

        final LookUp cancelledStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.CANCELLED.getValue(),
                AccountPrefix.CLR.toString(),
                cancellationDto.getLanguageId().intValue(), org);
        final Long cancellationStatus = cancelledStatus.getLookUpId();

        chequebookRepository.updateChequeForCancellation(cancellationDto.getOldChequeBookDetId(),
                UtilityService.convertStringDateToDateFormat(cancellationDto.getCancellationDate()),
                cancellationStatus, cancellationDto.getCancellationReason(), cancellationDto.getNewChequeBookDetId(),
                cancellationDto.getOrgId());

        final LookUp status = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                AccountPrefix.CLR.toString(),
                cancellationDto.getLanguageId().intValue(), org);
        final Long cpdIdStatus = status.getLookUpId();

        Long paymentId = null;
        String paymentType = "";
        List<Object[]> listOfPaymentDetails = chequebookRepository
                .getpaymentIdAndTypeByChequeBookDetId(cancellationDto.getOldChequeBookDetId(), cancellationDto.getOrgId());
        for (Object[] objects : listOfPaymentDetails) {
            if (objects[0] != null && objects[1] != null) {
                paymentId = (Long) objects[0];
                paymentType = (String) objects[1];
            }
        }
        chequebookRepository.updateChequeDetailsForPayments(cpdIdStatus, cancellationDto.getNewChequeBookDetId(),
                cancellationDto.getOrgId(),
                paymentId,
                UtilityService.convertStringDateToDateFormat(cancellationDto.getCancellationDate()), paymentType);
        
      //update the new instrument number with Existing instrument number Defect No: 31225 by srikanth
        chequebookRepository.updateInstrumentnumberByNewInstrumentNumber(cancellationDto.getOrgId(), cancellationDto.getNewChequeBookDetId(), paymentId);
    }

    @Override
    @Transactional(readOnly = true)
    public String getDateByInstrumentNo(Long issuedChequeNo, Long orgId) {
        // TODO Auto-generated method stub
        String newDate = "";
        Date date = chequebookRepository.getDateByInstrumentNo(issuedChequeNo, orgId);
        if (date != null) {
            newDate = Utility.dateToString(date);
        }
        return newDate;
    }

}
