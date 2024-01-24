package com.abm.mainet.council.dao;

import java.util.List;

import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;

public interface ICouncilAgendaMasterDao {

    List<CouncilAgendaMasterEntity> searchCouncilAgendaMasterData(Long committeeTypeId, String agendaNo, String fromDate,
            String toDate, Long orgid);

}
