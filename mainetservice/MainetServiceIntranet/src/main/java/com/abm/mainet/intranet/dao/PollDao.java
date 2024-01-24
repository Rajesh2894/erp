package com.abm.mainet.intranet.dao;

import java.util.List;

import com.abm.mainet.intranet.domain.Poll;

public interface PollDao {

	List<Poll> getPollData1(Long orgId, Long objPollId);
	List<Poll> getPollData(Long orgId);
	List<Poll> getByPollDetId(Long orgId, Long objPollId);
	List<Poll> getPollDataByStatus(Long orgId);
	List<Poll> getPollIdByPollDetId(Long pDetId, Long orgId); 
		
		
}
