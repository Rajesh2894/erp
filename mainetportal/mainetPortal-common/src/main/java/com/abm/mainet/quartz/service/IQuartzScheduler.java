package com.abm.mainet.quartz.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface IQuartzScheduler {

    public void invokeQuartzSample(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
