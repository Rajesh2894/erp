package com.abm.mainet.intranet.service;


import java.util.List;

import com.abm.mainet.intranet.domain.PollDetails;

public interface PollDetService {
	
	public PollDetails getPollById(Long pollId);
	
	public List<PollDetails> getPollByIdFromPollDet(Long orgId);

	public List<PollDetails> getPollDet(Long orgId);

	 
}
