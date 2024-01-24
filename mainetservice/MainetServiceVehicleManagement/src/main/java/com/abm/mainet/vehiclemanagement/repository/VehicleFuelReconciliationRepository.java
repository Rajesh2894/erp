package com.abm.mainet.vehiclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.vehiclemanagement.domain.VmVehicleFuelReconciation;

/**
 * The Interface VehicleFuelReconciationRepository.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
@Repository
public interface VehicleFuelReconciliationRepository extends JpaRepository<VmVehicleFuelReconciation, Long> {
    /**
     * get VendorId By PumpId
     * @param puId
     * @param orgid
     * @return
     */
    @Query("select  pm.vendorId from FuelPumpMaster pm where pm.puId=:puId and pm.orgid=:orgid")
    Long getVendorIdByPumpId(@Param("puId") Long puId, @Param("orgid") long orgid);

	long countByInrecdInvnoAndOrgid(Long invoiceNo, long orgid);
	
	@Query("SELECT count(d.vefId) FROM VehicleFuelReconciationDetails d WHERE d.vefId=:vefId and d.orgid=:orgId")
	Long checkIsFuellingReconciled(@Param("vefId") Long vefId, @Param("orgId") Long orgId);

}
