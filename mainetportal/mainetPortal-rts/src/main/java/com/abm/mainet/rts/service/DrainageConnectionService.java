package com.abm.mainet.rts.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.rts.ui.model.RtsServiceFormModel;

public interface DrainageConnectionService {
	public DrainageConnectionDto saveDrainageConnection(DrainageConnectionModel model);

	public Map<Long, String> getDept(Long orgId);

	public Map<Long, String> getService(Long orgId, Long deptId, String activeStatus);

	// public Map<Long , String> getWardZones(Long orgId);

	public DrainageConnectionDto fetchDrainageConnectionInfo(Long appId, Long orgId);

	public List<DocumentDetailsVO> fetchDrainageConnectionDocsByAppNo(Long appId, Long orgId);

}
