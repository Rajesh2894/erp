package com.abm.mainet.council.service;

import java.util.List;

import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;

public interface ICouncilAgendaMasterService {

    public boolean saveCouncilAgenda(CouncilAgendaMasterDto model);

    public List<CouncilAgendaMasterDto> searchCouncilAgendaMasterData(Long committeeTypeId, String agendaNo,
            String fromDate, String toDate, Long orgid);

    CouncilAgendaMasterDto getCouncilAgendaMasterByAgendaId(Long agendaId);

	void updateSubjectNo(List<CouncilProposalMasterDto> dto);

}
