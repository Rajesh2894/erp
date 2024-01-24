/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 *
 */
public interface IAdminOpinionPollService {
    public List<OpinionPoll> getAllOpinionPolls(Organisation organisation, String flag);

    public List<OpinionPoll> getCitizenOpinionPolls(Organisation organisation);

    public List<OpinionPollHistory> getGuestCitizenOpinionPolls(Organisation organisation);

    public OpinionPoll getOpinionPoll(long pnId);

    public boolean delete(long pnId);

    public boolean saveOrUpdate(OpinionPoll opinionPoll);


	
	public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag);
	public List<OpinionPoll> getAdminOpinionPollsByValidityAndIssueDate(final Organisation organisation, String flag,Date validityDate,Date issueDate);
}
