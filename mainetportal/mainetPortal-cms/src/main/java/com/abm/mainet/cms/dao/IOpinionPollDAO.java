package com.abm.mainet.cms.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IOpinionPollDAO {

    public abstract List<OpinionPoll> getOpinionPolls(Organisation organisation);

    public abstract List<OpinionPollHistory> getGuestOpinionPolls(Organisation organisation);


    public abstract boolean saveOrUpdate(OpinionPoll opinionPoll);

    public abstract OpinionPoll getOpinionPollById(long pnId);

    List<OpinionPoll> getAdminOpinionPolls(Organisation organisation, String flag);


	public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag);
	
	public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag,Date validityDate,Date issueDate);
}