package com.abm.mainet.council.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.council.domain.CouncilProposalMasterEntity;

public interface ICouncilProposalMasterDao {

    // update proposal master based on proposal ids
    void updateTheAgendaIdByProposalIds(List<Long> proposalIds, Long agendaId);

    void updateTheAgendaIdOfProposalHistoryByProposalIds(List<Long> proposalIds, Long agendaId);
    
    void updateNullOfAgendaIdInProposalByAgendaId(Long agendaId);
    
    void updateNullOfAgendaIdInProposalHistoryByAgendaId(Long agendaId);

    List<CouncilProposalMasterEntity> fetchProposalListByStatus(String proposalStatus, Long orgId);

    List<CouncilProposalMasterEntity> searchCouncilProposalData(Long proposalDepId, String proposalNo, Date fromDate, Date toDate,
            String proposalStatus, Long orgid, List<Long> proposalIds,String type);

}
