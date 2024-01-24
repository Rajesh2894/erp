package com.abm.mainet.water.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public interface WaterDashboardSchedularService {

	  public void waterDashboardSchedular(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
