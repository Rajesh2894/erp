package com.abm.mainet.swm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.VehicleFuelReconciation;

/**
 * The Interface VehicleFuelReconciationRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
@Repository
public interface VehicleFuelReconciationRepository extends JpaRepository<VehicleFuelReconciation, Long> {
    /**
     * get VendorId By PumpId
     * @param puId
     * @param orgid
     * @return
     */
    @Query("select  pm.vendorId from PumpMaster pm where pm.puId=:puId and pm.orgid=:orgid")
    Long getVendorIdByPumpId(@Param("puId") Long puId, @Param("orgid") long orgid);

}
