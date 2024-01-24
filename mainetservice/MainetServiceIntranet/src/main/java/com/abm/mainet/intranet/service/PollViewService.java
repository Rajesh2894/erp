package com.abm.mainet.intranet.service;


import java.util.List;

import com.abm.mainet.intranet.domain.PollView;
import com.abm.mainet.intranet.dto.report.IntranetPollDTO;
import com.abm.mainet.intranet.dto.report.PollRequest;

public interface PollViewService {
	 
	public PollView createPollViewDetails(PollRequest pollRequest);

	public List<PollView> getPollViewData(Long orgId);

	public List<Object> getPollViewCount(Long orgId);
	
	public List<Object> getPollViewCountQue(Long orgId);
	
	public List<IntranetPollDTO> getPollViewCountDto(Long orgId);

	public List<PollView> getPollViewDet(Long pollid, Long userId);

	
}
