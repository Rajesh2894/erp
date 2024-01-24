package com.abm.mainet.common.integration.dms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public class RecordManagementSchedularImpl implements IRecordManagementShcedular{

	@Autowired
	IDmsManagementService dmsManagementService;
	
	@Override
	public void recordManagementUpdate(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		Long orgId = runtimeBean.getOrgId().getOrgid();
		Organisation org = new Organisation();
		org.setOrgid(orgId);
		dmsManagementService.updateManagementRecord(orgId);
	}
}
