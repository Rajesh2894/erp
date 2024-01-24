package com.abm.mainet.intranet.dao;

import java.util.List;

import com.abm.mainet.intranet.domain.PollView;

public interface PollViewDao {

	List<PollView> getPollViewData(Long orgId);

	List<PollView> getPollViewCount(Long orgId);

	List<PollView> getPollViewDet(Long pollid, Long userId);
	
		
		
}
