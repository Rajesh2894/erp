package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleFuellingData;

/**
 * The Interface VehicleFuellingRepository.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
@Repository
public interface VehicleFuelRepository extends JpaRepository<VehicleFuellingData, Long> {

    /**
     * find Last Meter Reading
     * @param orgId
     * @param veheicleId
     * @return
     */
    @Query(" SELECT MAX(f.vefReading) FROM VehicleFuellingData f WHERE f.orgid=:orgId AND f.veId=:veheicleId ")
    Long findLastMeterReading(@Param("orgId") Long orgId, @Param("veheicleId") Long veheicleId);


    @Query("Select fd from VehicleFuellingData fd where fd.vefId in (:vefIdList) and fd.puId=:puId and fd.orgid=:orgId")    
    List<VehicleFuellingData> getVehicleFuellingByVefIdsAndPumpId(@Param("vefIdList") List<Long> vefIdList, @Param("puId") Long puId, @Param("orgId") Long orgId);


}
