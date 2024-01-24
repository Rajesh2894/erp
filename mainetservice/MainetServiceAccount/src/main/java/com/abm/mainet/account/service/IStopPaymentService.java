/**
 * 
 */
package com.abm.mainet.account.service;

import java.util.Date;

import com.abm.mainet.account.dto.StopPaymemtReqDto;
import com.abm.mainet.account.dto.TbAcChequebookleafDetDto;

/**
 * @author Anwarul.Hassan
 * @since 12-Dec-2019
 */

public interface IStopPaymentService {
    TbAcChequebookleafDetDto getById(Long chequebookDetid);

    void updateStopPaymentEntry(Long chequeNo, Long cpdIdstatus, String stopPayRemark, Date stoppayDate, Long updatedBy,
            Date updatedDate,
            String lgIpMacUpd,
            Long orgId);

    // TbAcChequebookleafDetDto saveStopPaymentEntry(TbAcChequebookleafDetDto chequebookleafDetDto);

    StopPaymemtReqDto searchPaymentDetails(String paymentNo, Long instrumentNumber, Date paymentDate, Long orgId);
}
