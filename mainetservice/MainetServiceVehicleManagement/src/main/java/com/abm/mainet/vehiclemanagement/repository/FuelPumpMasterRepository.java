package com.abm.mainet.vehiclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.FuelPumpMaster;

/**
 * The Interface PumpMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@Repository
public interface FuelPumpMasterRepository extends JpaRepository<FuelPumpMaster, Long> {
    /**
     * find All By pump Type
     * @param puPutype
     * @param orgId
     * @return
     */
    List<FuelPumpMaster> findAllBypuPutype(Long puPutype, Long orgId);
    
    @Modifying
    @Query("Delete from FuelPumpMasterDetails fd where fd.pfuId in (:pfuId)")
    void removeItemsById(@Param("pfuId") List<Long> pfuId);

    @Query(value = "select m.FPM_ID, m.PU_PUMPNAME from tb_vm_pump_mast m join TB_VM_PUMP_FULDET d on m.FPM_ID=d.fpm_id "
    		+ "where d.pu_fuid = (select fuel_Type from TB_VEHICLE_MAST where VE_ID =:veId and orgid=:orgid) ", nativeQuery = true)
	List<Object[]> getPumpIdAndNameByVeId(@Param("veId") Long veId, @Param("orgid") Long orgid);   

    
}
