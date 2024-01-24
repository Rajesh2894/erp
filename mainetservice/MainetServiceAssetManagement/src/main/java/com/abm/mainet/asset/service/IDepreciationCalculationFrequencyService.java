/**
 * 
 */
package com.abm.mainet.asset.service;

import java.util.List;

import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;

/**
 * @author sarojkumar.yadav
 *
 */
public interface IDepreciationCalculationFrequencyService {

	/**
	 * Used to calculate depreciation on daily basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateDaily(QuartzSchedulerMaster runtimeBean,List<Object> parameterList);

	/**
	 * Used to calculate depreciation on Weekly basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateWeekly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	/**
	 * Used to calculate depreciation on Monthly basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateMonthly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	/**
	 * Used to calculate depreciation on Quarterly basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateQuarterly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	/**
	 * Used to calculate depreciation on Half Yearly basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateHalfYearly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

	/**
	 * Used to calculate depreciation on Yearly basis
	 * 
	 * @param orgId
	 * @param frequency
	 */
	public void calculateYearly(QuartzSchedulerMaster runtimeBean, List<Object> parameterList);

}
