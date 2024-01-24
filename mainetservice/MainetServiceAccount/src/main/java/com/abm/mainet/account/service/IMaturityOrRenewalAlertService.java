package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IMaturityOrRenewalAlertService {
	
	public void sendMaturityOrRenewalAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
