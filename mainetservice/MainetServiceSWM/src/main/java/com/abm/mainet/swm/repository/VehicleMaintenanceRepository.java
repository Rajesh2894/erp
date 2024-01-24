package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleMaintenance;

/**
 * The Interface VehicleMaintenanceRepository.
 * @author Lalit.Prusti Created Date : 22-May-2018
 */
@Repository
public interface VehicleMaintenanceRepository extends JpaRepository<VehicleMaintenance, Long> {

    /**
     * Find all vehicle by vehicle type.
     *
     * @param vehicleType the vehicle type
     * @param orgId the org id
     * @return the list
     */
    List<VehicleMaintenance> findAllByVeVetypeAndOrgid(Long vehicleType, Long orgId);

    /**
     * find Last Meter Reading
     * @param orgId
     * @param veheicleId
     * @return
     */
    @Query(" SELECT MAX(f.vemReading) FROM VehicleMaintenance f WHERE f.orgid=:orgId AND f.veId=:veheicleId ")
    Long findLastMeterReading(@Param("orgId") Long orgId, @Param("veheicleId") Long veheicleId);
}
