package com.abm.mainet.workManagement.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.workManagement.domain.MeasurementBookDetails;

public interface MeasurementBookDetailsRepository extends CrudRepository<MeasurementBookDetails, Long> {

    @Query("select Sum(m.workActualAmt) from MeasurementBookDetails m where m.mbDetPId=:mbDetPId")
    BigDecimal getAllRateByMbId(@Param("mbDetPId") Long mbDetPId);

    @Modifying
    @Query("UPDATE MeasurementBookDetails wd set wd.workActualAmt=:amt where wd.mbdId=:mbdId")
    void updateAmount(@Param("mbdId") Long mbdId, @Param("amt") BigDecimal amt);

    @Query("select Sum(m.workActualAmt) from MeasurementBookDetails m where m.mbMaster.workMbId=:mbId and m.workActualAmt IS NOT NULL and  m.mbDetPId IS NULL")
    BigDecimal getAllRateAmount(@Param("mbId") Long mbId);

    @Query("select mbd from MeasurementBookDetails mbd where mbd.mbMaster.workMbId=:mbId and mbd.workEstimateMaster.workEstemateId in (:estimatedId)")
    List<MeasurementBookDetails> getPreviousMbForCummulativeUpdate(@Param("estimatedId") List<Long> estimatedId,
            @Param("mbId") Long workMbId);

}
