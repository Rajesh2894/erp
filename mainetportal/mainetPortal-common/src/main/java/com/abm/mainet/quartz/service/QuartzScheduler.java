package com.abm.mainet.quartz.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public class QuartzScheduler implements IQuartzScheduler {

    @Override
    @Transactional
    public void invokeQuartzSample(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

        /* This method is to intilize the quartzScheduler */
    }

}
