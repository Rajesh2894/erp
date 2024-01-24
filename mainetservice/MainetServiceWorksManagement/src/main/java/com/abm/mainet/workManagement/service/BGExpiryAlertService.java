package com.abm.mainet.workManagement.service;
import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author sadik.shaikh
 *
 */
public interface BGExpiryAlertService {
	void alertForBGExpiry(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
