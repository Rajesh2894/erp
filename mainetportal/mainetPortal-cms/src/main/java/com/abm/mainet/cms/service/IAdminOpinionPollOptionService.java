/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.domain.OpinionPollHistory;
import com.abm.mainet.cms.domain.OpinionPollOption;
import com.abm.mainet.cms.domain.OpinionPollOptionHistory;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 *
 */
public interface IAdminOpinionPollOptionService {
    public List<OpinionPollOption> getAllOpinionPollOptions(Organisation organisation, String flag);

    public List<OpinionPollOption> getCitizenOpinionPollOptions(Organisation organisation);

    public List<OpinionPollOptionHistory> getGuestCitizenOpinionPollOptions(Organisation organisation);

    public OpinionPollOption getOpinionPollOption(long pOptionId);

    public boolean delete(long pOptionId);
    
    public boolean deleteAll(final long pnId,final long pOptionId);

    public boolean saveOrUpdate(OpinionPollOption opinionPollOption);

    public List<OpinionPollOption> getOpinionPollOptionsByDeptId(long deptId, Organisation organisation);
    
    public List<OpinionPollOption> getOpinionPollOptionsByOpinionPolltId(long pnId);

	boolean checkSequence(long sequenceNo, Long orgId);

}
