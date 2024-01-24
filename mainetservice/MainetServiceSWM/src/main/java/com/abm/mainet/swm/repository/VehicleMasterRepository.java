package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleMaster;

/**
 * The Interface VehicleMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 * 
 */
@Repository
public interface VehicleMasterRepository extends JpaRepository<VehicleMaster, Long> {

    /**
     * get All Vehicle ScheduleList
     * @param orgid
     * @return
     */
    @Query("select vm from VehicleMaster vm where vm.veId IN (select vs.veId from VehicleSchedule as vs) AND orgid =:orgid")
    List<VehicleMaster> getAllVehicleScheduleList(@Param("orgid") Long orgid);
}
