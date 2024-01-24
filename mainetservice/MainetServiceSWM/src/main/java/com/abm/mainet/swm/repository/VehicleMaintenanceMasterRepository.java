package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleMaintenanceMaster;

/**
 * Vehicle Maintenance Master Repository
 * @author Ajay.Kumar
 *
 */
@Repository
public interface VehicleMaintenanceMasterRepository extends JpaRepository<VehicleMaintenanceMaster, Long> {

    /**
     * find All By VeVetype
     * @param vehicleType
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMaster> findAllByVeVetype(Long vehicleType, Long orgId);
}
