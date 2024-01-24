package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetailsHistory;

public interface WorksMeasurementSheetDao {

	/**
	 * used to save and update Works Measure Details List
	 * @param measureDetailsList
	 * @param removeIds
	 */
	WorkEstimateMeasureDetails saveUpdateEstimateMeasureDetails(WorkEstimateMeasureDetails entity);

	/**
	 * used to delete (in active)Works Measure Details List
	 * @param measureDetailsList
	 * @param removeIds
	 */
	void updateDeletedFlag(List<Long> removeIds);

	/**
	 * used to calculate Total Estimated Amount By Work Id
	 * @param measureDetailsList
	 * @param removeIds
	 */
	BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEId);

	/**
	 * used to get Work Estimate Details By Work Estimated Id
	 * @param measureDetailsList
	 * @param removeIds
	 */
	List<WorkEstimateMeasureDetails> getWorkEstimateDetailsByWorkEId(Long workEId);

	/**
	 * used to get audit data for change log
	 * @param workEId
	 * @return
	 */
	List<WorkEstimateMeasureDetailsHistory> getAuditMeasuremnetByWorkEId(Long workEId);

	/**
	 * used to update Utilization No while filing MB
	 * 
	 * @param nosUtilize
	 * @param meMentId
	 */
	void updateUtilizationNoByMeId(Long nosUtilize, Long meMentId);

	/**
	 * used to get Measure Details By Id
	 * @param meMentId
	 * @return WorkEstimateMeasureDetails
	 */
	WorkEstimateMeasureDetails findMeasureDetailsById(Long meMentId);
}
