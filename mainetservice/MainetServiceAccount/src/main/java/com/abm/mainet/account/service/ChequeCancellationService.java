package com.abm.mainet.account.service;

import com.abm.mainet.account.dto.ChequeCancellationDto;

/**
 * @author tejas.kotekar
 *
 */
public interface ChequeCancellationService {

    /**
     * @param cancellationDto
     */
    void chequeCancellation(ChequeCancellationDto cancellationDto);

    String getDateByInstrumentNo(Long issuedChequeNo, Long orgId);

}
