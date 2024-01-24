package com.abm.mainet.payment.service;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;

/**
 *
 * @author kavali.kiran
 *
 */
@Transactional(readOnly = true)
public interface IChallanService {
    public ApplicationFormChallanDTO getChallanData(CommonChallanDTO challanDTO, Organisation organisation);

    public CommonChallanDTO generateChallanNumber(CommonChallanDTO offline);
}
