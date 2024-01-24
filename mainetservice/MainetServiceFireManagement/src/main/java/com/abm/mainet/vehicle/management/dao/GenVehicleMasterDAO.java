/*package com.abm.mainet.vehicle.management.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehicle.management.domain.FmVehicleMaster;

*//**
 * @author Ajay.Kumar
 *
 *//*
@Repository
public class GenVehicleMasterDAO extends AbstractDAO<FmVehicleMaster> implements IVehicleMasterDAO {

    
	
	 * @Query("select e from Employee e  where e.organisation.orgid=:orgid and e.tbDepartment.dpDeptid=:deptId "
	 * + "  and e.tbLocationMas.locId =:locId and e.isDeleted='0' ") List<Employee>
	 * findEmployeeDataByLocID(@Param("deptId") Long deptId,
	 * 
	 * @Param("locId") Long locId, @Param("orgid") Long orgid);
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.abm.mainet.swm.dao.VehicleMasterDAO#
	 * serchVehicleByVehicleTypeAndVehicleRegNo(java.lang.Long, java.lang.String,
	 * java.lang.Long)
	 
     
    @SuppressWarnings("unchecked")
    @Override
    public List<FmVehicleMaster> searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Long vehicleType, String status,
            String vehicleRegNo,Long deptId,Long location,Long orgId) {
        Query query = this.createQuery(buildQuery(veId, vehicleType, status, vehicleRegNo, deptId, location));

        query.setParameter("orgid", orgId);

        if (veId != null) {
            query.setParameter("veId", veId);
        }

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }
        
        if (deptId != null) {
            query.setParameter("deptId", deptId);
        }
        if (location != null) {
            query.setParameter("location", location);
        }

        if (StringUtils.isNotEmpty(status)) {
            query.setParameter("status", status);
        }

        if (StringUtils.isNotEmpty(vehicleRegNo)) {
            query.setParameter("vehicleRegNo", vehicleRegNo);
        }

        return query.getResultList();
    }

    *//**
     * Builds the query.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @return the string
     *//*
    private String buildQuery(Long veId, Long vehicleType, String status, String vehicleRegNo,Long deptId,Long location) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT vm FROM FmVehicleMaster vm WHERE vm.orgid = :orgid ");

        if (veId != null) {
            disposalSiteSearchQuery.append(" AND vm.veId != :veId ");
        }

        if (vehicleType != null) {
            disposalSiteSearchQuery.append(" AND vm.veVetype = :vehicleType ");
        }
        
        if (deptId != null) {
            disposalSiteSearchQuery.append(" AND vm.deptId = :deptId ");
        }
        
        if (location != null) {
            disposalSiteSearchQuery.append(" AND vm.locId = :location ");
        }
        
        
        if (StringUtils.isNotEmpty(status)) {
            disposalSiteSearchQuery.append(" AND vm.veActive like :status ");
        }

        if (StringUtils.isNotEmpty(vehicleRegNo)) {
            disposalSiteSearchQuery.append(" AND vm.veNo = :vehicleRegNo ");
        }

        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");

        return disposalSiteSearchQuery.toString();
    }

	@Override
	public List<FmVehicleMaster> fetchVeNoByVehicleTypeId(Long vehicleType, Long deptId, Long orgId) {
		Query query = this.createQuery(buildQuery(vehicleType, deptId, orgId));
        query.setParameter("orgid", orgId);

        if (vehicleType != null) {
            query.setParameter("vehicleType", vehicleType);
        }
        
        if (deptId != null) {
            query.setParameter("deptId", deptId);
        }

        return query.getResultList();
	}

	private String buildQuery(Long vehicleType, Long deptId, Long orgId) {
		StringBuilder listOfVehNo = new StringBuilder(
                " SELECT vm FROM FmVehicleMaster vm WHERE vm.orgid = :orgid ");
      
        if (vehicleType != null) {
        	listOfVehNo.append(" AND vm.veVetype = :vehicleType ");
        }
        
        if (deptId != null) {
        	listOfVehNo.append(" AND vm.deptId = :deptId ");
        }
        
        listOfVehNo.append(" ORDER BY vm.veId DESC ");
        return listOfVehNo.toString();
	}

}
*/