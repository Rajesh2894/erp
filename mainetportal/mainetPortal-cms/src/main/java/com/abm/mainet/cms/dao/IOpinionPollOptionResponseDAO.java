package com.abm.mainet.cms.dao;

import java.util.List;

import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.common.domain.Organisation;

public interface IOpinionPollOptionResponseDAO {

    public abstract List<OpinionPollOptionResponse> getOpinionPollOptionResponses(Organisation organisation);


    public abstract List<OpinionPollOptionResponse> getOpinionPollOptionResponsesByDeptId(long deptId, Organisation organisation);

    public abstract boolean saveOrUpdate(OpinionPollOptionResponse OpinionPollOptionResponse);

    
    public abstract List<OpinionPollOptionResponse> getOpinionPollOptionResponsesByOpinioId(long pnId);

    List<OpinionPollOptionResponse> getAdminOpinionPollOptionResponses(Organisation organisation, String flag);


	public List<OpinionPollOptionResponse> getCitizenOpinionPollOptionResponsesByOpinionPollId(final Organisation organisation,final long opinionPollId);
	
	public List<OpinionPollDTO> getOpinionPolls(long pid);

}