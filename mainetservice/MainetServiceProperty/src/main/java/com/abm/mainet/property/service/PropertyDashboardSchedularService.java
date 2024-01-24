package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author Mithila.Jondhale
 * @since 28-June-2023
 */

public interface PropertyDashboardSchedularService {

	  public void propertyDashboardSchedular(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}