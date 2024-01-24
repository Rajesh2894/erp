package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IAppreciationInContractAggrementService {
	
	public void appreciationInRent(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
