/**
 *
 */
package com.abm.mainet.cms.service;

import java.util.List;

import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author swapnil.shirke
 *
 */
public interface IAdminOpinionPollOptionResponseService {

    public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponses(Organisation organisation);
    
    public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponsesByOpinionPollId(Organisation organisation,long opinionPollId);

    public boolean saveOrUpdate(OpinionPollOptionResponse opinionPollOptionResponse);
    
    public List<OpinionPollDTO> getOpinionPolls(long pid);

}
