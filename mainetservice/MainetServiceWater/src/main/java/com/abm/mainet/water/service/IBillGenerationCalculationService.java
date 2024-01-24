package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author manish.chaurasiya
 *
 */

public interface IBillGenerationCalculationService
{
	
	public void calculateMonthly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
