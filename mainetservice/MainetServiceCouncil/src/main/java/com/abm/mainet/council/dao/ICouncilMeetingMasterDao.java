package com.abm.mainet.council.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;

public interface ICouncilMeetingMasterDao {

    List<CouncilMeetingMasterEntity> searchCouncilMeetingMasterData(Long meetingTypeId, String meetingNo, Date fromDate,
            Date toDate, Long orgid, String orderBy);

    Long getMemberCountByCondition(Long meetingId, Date fromDate, Date toDate, Long orgId, String condition);

    CouncilMeetingMasterEntity getMeetingDetailsByCondition(Long meetingId, Long meetingTypeId, String meetingNo, Long orgId);

}
