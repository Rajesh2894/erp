package com.abm.mainet.rts.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.rts.dto.FirstAppealDto;

@WebService
public interface IFirstAppealService {

    List<CitizenDashBoardResDTO> fetchFirstAppealData(CitizenDashBoardReqDTO request);

    // save first appeal
    ObjectionDetailsDto saveFirstAppealInObjection(ObjectionDetailsDto objDto);

    FirstAppealDto getApplicantData(RequestDTO requestDTO);

}
