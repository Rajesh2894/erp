package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.domain.VeVehicleMaster;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;

/**
 * @author Niraj.Kumar
 *
 */
@Repository
public class GeVehicleMasterDAO extends AbstractDAO<VeVehicleMaster> implements IVehicleMasterDAO {

    
	/*
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
	 */
     
    @SuppressWarnings("unchecked")
    @Override
    public List<VeVehicleMaster> searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Long vehicleType, String status,
            String vehicleRegNo,String veChasisSrno, Long deptId,Long location,Long orgId) {
        Query query = this.createQuery(buildQuery(veId, vehicleType, status, vehicleRegNo,veChasisSrno, deptId, location));

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
        
        if (StringUtils.isNotEmpty(veChasisSrno)) {
            query.setParameter("veChasisSrno", veChasisSrno);
        }

        return query.getResultList();
    }

    /**
     * Builds the query.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @return the string
     */
    private String buildQuery(Long veId, Long vehicleType, String status, String vehicleRegNo,String veChasisSrno, Long deptId,Long location) {
        StringBuilder disposalSiteSearchQuery = new StringBuilder(
                " SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");

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
        
        if (StringUtils.isNotEmpty(veChasisSrno)) {
            disposalSiteSearchQuery.append(" AND vm.veChasisSrno = :veChasisSrno ");
        }
        
        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");

        return disposalSiteSearchQuery.toString();
    }

	@Override
	public List<VeVehicleMaster> fetchVeNoByVehicleTypeId(Long vehicleType, Long deptId, Long orgId) {
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
                " SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");
      
        if (vehicleType != null) {
        	listOfVehNo.append(" AND vm.veVetype = :vehicleType ");
        }
        
        if (deptId != null) {
        	listOfVehNo.append(" AND vm.deptId = :deptId ");
        }
        
        listOfVehNo.append(" ORDER BY vm.veId DESC ");
        return listOfVehNo.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VeVehicleMaster> searchVehicleByVehTypeAndVehRegNo(Long veId, Long vehicleType, String status,
			String vehicleRegNo,Long driverName,String toDateFlag, Long orgId) {
		
		
		  Query query = this.createQuery(buildQuery(veId, vehicleType, status, vehicleRegNo,driverName,toDateFlag));

	        query.setParameter("orgid", orgId);

	        if (veId != null) {
	            query.setParameter("veId", veId);
	        }

	        if (vehicleType != null) {
	            query.setParameter("vehicleType", vehicleType);
	        }

	        if (StringUtils.isNotEmpty(status)) {
	            query.setParameter("status", status);
	        }

	        if (StringUtils.isNotEmpty(vehicleRegNo)) {
	            query.setParameter("vehicleRegNo", vehicleRegNo);
	        }
	        if (driverName != null) {
	            query.setParameter("driverName", driverName);
	        }
			/*
			 * if (StringUtils.isNotEmpty(toDateFlag)) { query.setParameter("veRentTodate",
			 * toDateFlag); }
			 */
	       return query.getResultList();
	    }

	    /**
	     * Builds the query.
	     *
	     * @param vehicleType the vehicle type
	     * @param vehicleRegNo the vehicle reg no
	     * @return the string
	     */
	    private String buildQuery(Long veId, Long vehicleType, String status, String vehicleRegNo,Long driverName,String toDateFlag) {
	        StringBuilder disposalSiteSearchQuery = new StringBuilder(
	                " SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");

	        if (veId != null) {
	            disposalSiteSearchQuery.append(" AND vm.veId = :veId ");
	        }

	        if (vehicleType != null) {
	            disposalSiteSearchQuery.append(" AND vm.veVetype = :vehicleType ");
	        }

	        if (StringUtils.isNotEmpty(status)) {
	            disposalSiteSearchQuery.append(" AND vm.veActive like :status ");
	        }

	        if (StringUtils.isNotEmpty(vehicleRegNo)) {
	            disposalSiteSearchQuery.append(" AND vm.veNo = :vehicleRegNo ");
	        }
	        if (driverName != null) {
	            disposalSiteSearchQuery.append(" AND vm.driverName = :driverName ");
	        }
	        if (StringUtils.isNotEmpty(toDateFlag) && toDateFlag.equals("Y") ) {
	        	 disposalSiteSearchQuery.append(" AND	(vm.veFlag= 'Y' or (vm.veRentTodate >= current_date() and vm.veFlag = 'N' ) )"); 
	           // disposalSiteSearchQuery.append(" AND vm.VE_RENT_TODATE = :toDateFlag ");
	        }

	        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");

	        return disposalSiteSearchQuery.toString();
	    }


	    
	    @SuppressWarnings("unchecked")
		@Override
		public List<VeVehicleMaster> searchFuelTypeByVehId(Long vehicleType, String status,String veChasisSrno, Long orgId) {
			  Query query = this.createQuery(buildQuery1(vehicleType, status,veChasisSrno));
		      	query.setParameter("orgid", orgId);

		        if (vehicleType != null && vehicleType!=0 ) {
		            query.setParameter("vehicleType", vehicleType);
		        }
		        
		        if (StringUtils.isNotEmpty(status)) {
		            query.setParameter("status", status);
		        }
		        if (StringUtils.isNotEmpty(veChasisSrno)) {
		            query.setParameter("veChasisSrno", veChasisSrno);
		        }
		      return query.getResultList();
		    }

		    
		    private String buildQuery1(Long vehicleType, String status,String veChasisSrno) {
		        StringBuilder disposalSiteSearchQuery = new StringBuilder(" SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");

		        if (vehicleType != null && vehicleType!=0) {
		            disposalSiteSearchQuery.append(" AND vm.veId = :vehicleType ");
		        }
		        
		        if (StringUtils.isNotEmpty(status)) {
		            disposalSiteSearchQuery.append(" AND vm.veActive like :status ");
		        }
		        if (StringUtils.isNotEmpty(veChasisSrno)) {
		            disposalSiteSearchQuery.append(" AND vm.veChasisSrno = :veChasisSrno ");
		        }
		        
		        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");
		        return disposalSiteSearchQuery.toString();
		    }

		    @SuppressWarnings("unchecked")
			@Override
			public List<VeVehicleMaster> searchVehicleTypeByDeptId(Long deptId, String status, Long orgId) {
				  Query query = this.createQuery(buildQuery2(deptId, status));
			      	query.setParameter("orgid", orgId);

			        if (deptId != null) {
			            query.setParameter("deptId", deptId);
			        }
			        
			        if (StringUtils.isNotEmpty(status)) {
			            query.setParameter("status", status);
			        }
			      return query.getResultList();
			    }

			    
			    private String buildQuery2(Long deptId, String status) {
			        StringBuilder disposalSiteSearchQuery = new StringBuilder(" SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");

			        if (deptId != null) {
			            disposalSiteSearchQuery.append(" AND vm.deptId = :deptId ");
			        }
			        
			        if (StringUtils.isNotEmpty(status)) {
			            disposalSiteSearchQuery.append(" AND vm.veActive like :status ");
			        }
			        
			        disposalSiteSearchQuery.append(" ORDER BY vm.veId DESC ");
			        return disposalSiteSearchQuery.toString();
			    }

	@Override
	public List<VeVehicleMaster> getVehicleByNumberVe(Long veid, Long orgid) {
		Query query = this.createQuery(buildQuery11(veid, orgid));
        query.setParameter("orgid", orgid);
        if (veid != null) {
            query.setParameter("veId", veid);
        }
        return query.getResultList();
	}
	
	private String buildQuery11(Long veid, Long orgid) {
        StringBuilder searchQuery = new StringBuilder(" SELECT vm.veId, vm.veNo FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");
        if (veid != null) {
        	searchQuery.append(" AND vm.veId = :veid ");
        }
        return searchQuery.toString();
    }

	@Override
	public List<VeVehicleMaster> searchVehData(String veEngSrno, String veChasisSrno, String veRegNo, Long orgid,String veActive,Long veId) {
		Query query = this.createQuery(buildQuery111(veEngSrno, veChasisSrno, veRegNo, orgid,veActive,veId));
        query.setParameter("orgid", orgid);
        if (StringUtils.isNotEmpty(veEngSrno)) {
            query.setParameter("veEngSrno", veEngSrno);
        }
        if (StringUtils.isNotEmpty(veChasisSrno)) {
            query.setParameter("veChasisSrno", veChasisSrno);
        }
        if (StringUtils.isNotEmpty(veRegNo)) {
            query.setParameter("veRegNo", veRegNo);
        }
        if (veActive != null) {
            query.setParameter("veActive", veActive);
        }
        if (veId != null && veId!=0) {
            query.setParameter("veId", veId);
        }
        return query.getResultList();
	}

	private String buildQuery111(String veEngSrno, String veChasisSrno, String veRegNo, Long orgid,String veFlag,Long veId) {
		 StringBuilder searchQuery = new StringBuilder(" SELECT vm.veEngSrno, vm.veChasisSrno, vm.veActive, vm.veFlag FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");
		 if (StringUtils.isNotEmpty(veChasisSrno)) {
			 searchQuery.append(" AND vm.veChasisSrno = :veChasisSrno ");
	     }
	     if (StringUtils.isNotEmpty(veEngSrno)) {
	    	 searchQuery.append(" AND vm.veEngSrno = :veEngSrno ");
	     }
	     if (StringUtils.isNotEmpty(veRegNo)) {
	         searchQuery.append(" AND vm.veNo = :veRegNo ");
	     }
	     if (veFlag != null) {
	         searchQuery.append(" AND vm.veActive = :veActive ");
	     }
	     if (veId != null && veId!=0) {
	    	 searchQuery.append(" AND vm.veId <>  :veId ");
	        }
	        return searchQuery.toString();
	}

	@Override
	public List<VeVehicleMaster> fetchVeNoByDeptId(Long department, long orgid) {
		Query query = this.createQuery(buildQuery222(department, orgid));
      	query.setParameter("orgid", orgid);

        if (department != null) {
            query.setParameter("deptId", department);
        }
        return query.getResultList();
	}

	private String buildQuery222(Long department, long orgid) {
		StringBuilder disposalSiteSearchQuery = new StringBuilder(" SELECT vm FROM VeVehicleMaster vm WHERE vm.orgid = :orgid ");
        if (department != null) {
            disposalSiteSearchQuery.append(" AND vm.deptId = :deptId ");
        }
        return disposalSiteSearchQuery.toString();
	}
	
	
	@Override
	public List<VeVehicleMaster> getVehDet(String veId, Date vesFromdt, Date vesTodt) {
		Query query = this.createQuery(buildQuery11(veId, vesFromdt, vesTodt));
		query.setParameter("orgid", UserSession.getCurrent().getOrganisation().getOrgid());
		
		if ((vesFromdt != null) && (vesTodt != null)) {
	        query.setParameter("veRentFromdate", vesFromdt);
	        query.setParameter("veRentTodate", vesTodt);
	    }
		
		if (StringUtils.isNotEmpty(veId)) {
			query.setParameter("veId", Long.valueOf(veId));
		}
		return query.getResultList();
	}
	
	private String buildQuery11(String veId, Date vesFromdt, Date vesTodt) {
		StringBuilder searchQuery = new StringBuilder(" SELECT fcr FROM VeVehicleMaster fcr WHERE fcr.orgid = :orgid ");
		if (null != vesFromdt && null != vesTodt) {
			searchQuery.append("AND ((fcr.veRentFromdate between :veRentFromdate and :veRentTodate) or (fcr.veRentTodate between :veRentFromdate and :veRentTodate))"); 
		}
		if (StringUtils.isNotEmpty(veId)) {
			searchQuery.append(" AND fcr.veId = :veId ");
		}
	return searchQuery.toString();
	
	}

 
	
	
	
	    
}



