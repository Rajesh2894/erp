package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IOpinionPollOptionDAO {

    public abstract List<OpinionPollOption> getOpinionPollOptions(Organisation organisation);

    public abstract List<OpinionPollOptionHistory> getGuestOpinionPollOptions(Organisation organisation);

    public abstract List<OpinionPollOption> getOpinionPollOptionsByDeptId(long deptId, Organisation organisation);

    public abstract boolean saveOrUpdate(OpinionPollOption opinionPollOption);
    
    public abstract boolean deleteAll(long pnId,long pOptionId);

    public abstract OpinionPollOption getOpinionPollOptionById(long pnId);

    List<OpinionPollOption> getAdminOpinionPollOptions(Organisation organisation, String flag);

	boolean checkSequence(long sequenceNo, Long orgId);

	public abstract List<OpinionPollOption> getOpinionPollOptionsByOpinionPollId(long deptId);


}