package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetails;
import com.abm.mainet.workManagement.domain.WorkEstimateMeasureDetailsHistory;

@Repository
public interface WorksMeasurementSheetRepository extends CrudRepository<WorkEstimateMeasureDetails, Long> {

    @Modifying
    @Query("update WorkEstimateMeasureDetails set meMentActive = 'N'  where meMentId in :removeIds")
    void updateDeletedFlag(@Param("removeIds") List<Long> removeIds);

    @Query("select Sum(we.meMentToltal) from WorkEstimateMeasureDetails we where meMentActive = 'Y'  and workEstemateId =:workEId")
    BigDecimal calculateTotalEstimatedAmountByWorkId(@Param("workEId") Long workEId);

    @Query("SELECT pm FROM WorkEstimateMeasureDetails pm  where pm.workEstemateId = :workEId and meMentActive= 'Y' ")
    List<WorkEstimateMeasureDetails> getWorkEstimateDetailsByWorkEId(@Param("workEId") Long workEId);

    @Query("SELECT we FROM WorkEstimateMeasureDetailsHistory we  where we.workEstemateId = :workEId ")
    List<WorkEstimateMeasureDetailsHistory> getAuditMeasuremnetByWorkEId(@Param("workEId") Long workEId);

    @Modifying
    @Query("update WorkEstimateMeasureDetails set meNoUtl = :nosUtilize  where meMentId = :meMentId")
    void updateUtilizationNoByMeId(@Param("nosUtilize") Long nosUtilize, @Param("meMentId") Long meMentId);

    @Query("SELECT pm FROM WorkEstimateMeasureDetails pm  where meMentId = :meMentId and meMentActive= 'Y' ")
    WorkEstimateMeasureDetails findMeasureDetailsById(@Param("meMentId") Long meMentId);
    
    @Query("SELECT pm FROM WorkEstimateMeasureDetails pm  where pm.workEstemateId = :workEId")
    List<WorkEstimateMeasureDetails> getWorkEstimateByWorkEId(@Param("workEId") Long workEId);


}
