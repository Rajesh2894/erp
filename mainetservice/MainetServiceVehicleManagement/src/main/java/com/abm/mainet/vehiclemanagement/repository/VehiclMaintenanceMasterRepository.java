package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VehicleMaintenanceMast;

/**
 * Vehicle Maintenance Master Repository
 * @author Ajay.Kumar
 *
 */
@Repository
public interface VehiclMaintenanceMasterRepository extends JpaRepository<VehicleMaintenanceMast, Long> {

    /**
     * find All By VeVetype
     * @param vehicleType
     * @param orgId
     * @return
     */
    List<VehicleMaintenanceMast> findAllByVeVetype(Long vehicleType, Long orgId);

	@Query("Select mm from VehicleMaintenanceMast mm where mm.veMeActive=:maintActive and mm.orgid=:orgid")
	List<VehicleMaintenanceMast> getActiveVehicleMaintenanceMas(@Param("maintActive") String maintActive, @Param("orgid") Long orgid);

}
