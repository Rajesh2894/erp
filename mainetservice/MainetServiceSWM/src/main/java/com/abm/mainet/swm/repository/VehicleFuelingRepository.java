package com.abm.mainet.swm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleFuelling;

/**
 * The Interface VehicleFuellingRepository.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Repository
public interface VehicleFuelingRepository extends JpaRepository<VehicleFuelling, Long> {

    /**
     * find Last Meter Reading
     * @param orgId
     * @param veheicleId
     * @return
     */
    @Query(" SELECT MAX(f.vefReading) FROM VehicleFuelling f WHERE f.orgid=:orgId AND f.veId=:veheicleId ")
    Long findLastMeterReading(@Param("orgId") Long orgId, @Param("veheicleId") Long veheicleId);
}
