package com.abm.mainet.council.service;

import java.util.List;

import com.abm.mainet.council.domain.CouncilActionTakenEntity;
import com.abm.mainet.council.dto.CouncilActionTakenDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;

public interface ICouncilActionTakenService {

    public void saveAction(List<CouncilActionTakenEntity> councilActionTakenEntity);

    public List<CouncilActionTakenDto> getActionTakenDetailsByPropsalId(Long proposalId, Long orgId);

    public List<CouncilProposalMasterDto> fetchProposalsByAgendaId(Long orgId);

}
