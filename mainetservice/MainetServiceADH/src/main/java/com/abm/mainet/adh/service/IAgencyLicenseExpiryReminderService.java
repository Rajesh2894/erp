package com.abm.mainet.adh.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IAgencyLicenseExpiryReminderService {

	public void sendReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
