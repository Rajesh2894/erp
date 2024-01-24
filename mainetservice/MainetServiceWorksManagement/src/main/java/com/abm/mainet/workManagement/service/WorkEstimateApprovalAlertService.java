package com.abm.mainet.workManagement.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface WorkEstimateApprovalAlertService {
	void alertForWorkEstimateApproval(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
