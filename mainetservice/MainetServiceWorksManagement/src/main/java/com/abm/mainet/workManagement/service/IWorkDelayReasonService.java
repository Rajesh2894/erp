package com.abm.mainet.workManagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.WorkDelayReasonEntity;
import com.abm.mainet.workManagement.dto.WorkDelayReasonDto;

public interface IWorkDelayReasonService {

	boolean saveWorkDelayReason(WorkDelayReasonDto delayReasonDto);

	List<WorkDelayReasonDto> getAllData(Long orgId, Long projId, Long workId,Date date,String status);
	
	WorkDelayReasonDto getDelayReasonById(Long orgId,Long delresId);
}
