package com.abm.mainet.account.quartz.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

public interface AccountVoucherPostingForQuartz {

    public void invokeQtzForPosting(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

    public void propertyTexReceiptPosting(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

    public void SalaryBillUpload(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);
}
