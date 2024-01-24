package com.abm.mainet.mobile.service;


import java.util.List;

import com.abm.mainet.cms.domain.EipUserContactUs;
import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionDTO;
import com.abm.mainet.cms.dto.OpinionPollDTO;



public interface IOpinionPollService {
	public List<OpinionPollDTO> getOpinionPollResponse(long pnid);
	public OpinionDTO getCitizenOpinion(long orgid);
	public boolean saveOpinionPoll(long orgid,OpinionPollOptionResponse opinionPollOptionResponse);
	public boolean saveInnovativeIdea(long orgid,Feedback feedback);
}
