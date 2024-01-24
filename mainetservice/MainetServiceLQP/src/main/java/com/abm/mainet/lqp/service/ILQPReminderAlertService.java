package com.abm.mainet.lqp.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface ILQPReminderAlertService {

	public void sendReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
