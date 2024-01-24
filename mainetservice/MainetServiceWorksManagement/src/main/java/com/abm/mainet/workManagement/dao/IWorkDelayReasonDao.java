package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.WorkDelayReasonEntity;

public interface IWorkDelayReasonDao {
	
	List<WorkDelayReasonEntity> getAllData(Long orgId, Long projId, Long workId,Date date,String status);
	
	WorkDelayReasonEntity getDelayResById(Long orgId,Long delResId);

}
