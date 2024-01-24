package com.abm.mainet.intranet.dao;

import java.util.List;

import com.abm.mainet.intranet.domain.PollDetails;

public interface PollDetailDao {

	List<PollDetails> getPollDetailData(Long orgId);
 
	List<PollDetails> getPollByIdFromPollDet(Long orgId);
	

}

