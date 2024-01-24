package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.vehiclemanagement.domain.VeVehicleMaster;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;

/**
 * The Interface VeVehicleMasterDAO.
 *
 * @author Lalit.Prusti
 */
public interface IVehicleMasterDAO {

    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     */

    List<VeVehicleMaster> searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Long vehicleType, String status, String vehicleRegNo, String veChasisSrno, Long deptId, Long location, Long orgId);
	
    List<VeVehicleMaster> fetchVeNoByVehicleTypeId(Long vehicleType, Long deptId, Long orgId);

	List<VeVehicleMaster> searchVehicleByVehTypeAndVehRegNo(Long veId, Long vehicleType, String status,
			String vehicleRegNo, Long veDriverName ,String toDateFlag,Long orgId);

	List<VeVehicleMaster> searchFuelTypeByVehId(Long vehicleType, String status,String veChasisSrno ,Long orgId);

	List<VeVehicleMaster> searchVehicleTypeByDeptId(Long deptId, String status, Long orgId);

	List<VeVehicleMaster> getVehicleByNumberVe(Long veid, Long orgid);

	List<VeVehicleMaster> searchVehData(String veEngSrno, String veChasisSrno, String veRegNo, Long orgid,String veFlag,Long veId);

	List<VeVehicleMaster> fetchVeNoByDeptId(Long department, long orgid);

	List<VeVehicleMaster> getVehDet(String veId, Date vesFromdt, Date vesTodt);
	
    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     */


}
