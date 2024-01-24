package com.abm.mainet.tradeLicense.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IReminderAlertService {

	public void sendLicenseRenewalReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
