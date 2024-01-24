package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;

public interface WorksMeasurementSheetService {

    /**
     * save Update Estimate Measure Details
     * @param measureDetailsList
     * @param removeIds
     * @param approvalFlag
     */
    void saveUpdateEstimateMeasureDetails(List<WorkEstimateMeasureDetailsDto> measureDetailsList, List<Long> removeIds,
            String approvalFlag);

    /**
     * calculate Total Estimated Amount By WorkId
     * @param workEId
     * @return
     */
    BigDecimal calculateTotalEstimatedAmountByWorkId(Long workEId);

    /**
     * get Work Estimate Details By WorkE Id
     * @param WorkEId
     * @return
     */
    List<WorkEstimateMeasureDetailsDto> getWorkEstimateDetailsByWorkEId(Long WorkEId);

    /**
     * used to get audit data for change log
     * 
     * @param workEId
     * @return
     */
    List<WorkEstimateMeasureDetailsDto> getAuditMeasuremnetByWorkEId(Long WorkEId);

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
     * @return WorkEstimateMeasureDetailsDto
     */
    WorkEstimateMeasureDetailsDto findByMeasureDetailsId(Long meMentId);

}
