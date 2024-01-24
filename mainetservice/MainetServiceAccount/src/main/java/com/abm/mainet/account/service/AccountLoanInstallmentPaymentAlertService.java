package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface AccountLoanInstallmentPaymentAlertService {
	
	public void sendLoanInstallmentPaymentAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
