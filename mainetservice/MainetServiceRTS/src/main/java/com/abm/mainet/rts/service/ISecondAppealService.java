package com.abm.mainet.rts.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;

@WebService
public interface ISecondAppealService {
    List<CitizenDashBoardResDTO> fetchSecondAppealData(CitizenDashBoardReqDTO request);

    // save second appeal
    ObjectionDetailsDto saveSecondAppealInObjection(ObjectionDetailsDto objDto);

}
