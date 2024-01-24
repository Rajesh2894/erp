package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IRecordManagementShcedular {
	
	public void recordManagementUpdate(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
	
	/*public void recordManagementForRetention(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);*/

}
