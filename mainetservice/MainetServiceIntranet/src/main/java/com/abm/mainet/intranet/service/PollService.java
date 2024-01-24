package com.abm.mainet.intranet.service;


import java.util.List;

import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollDetails;
import com.abm.mainet.intranet.dto.report.PollRequest;

public interface PollService {
	
	public Poll createPoll(PollRequest pollRequest);
	 
	public PollDetails createPollDetails(PollRequest pollRequest);
	 
	public Poll getPollById(Long pollId);

	public void updatePollStatus(String pollStatus, Long pollId, Long orgid);

	public List<Poll> getPollData(Long orgId);
	
	 
}
