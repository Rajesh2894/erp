package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface AccountBillPaymentReminderAlertService {
	public void sendBillPaymentReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
