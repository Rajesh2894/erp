package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.vehiclemanagement.domain.OEMWarranty;


@Repository
public class OEMWarrantyServiceDAO extends AbstractDAO<OEMWarranty> implements IOEMWarrantyServiceDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<OEMWarranty> searchOemWarrantyDetails(Long department, Long vehicleType, Long veNo, Long orgid) {
 		Query query = this.createQuery(buildQuery(department, vehicleType, veNo, orgid));
		query.setParameter("orgid", orgid);

	  if (department != null){
            query.setParameter("department", department);
        }
		  if (vehicleType != null){
	            query.setParameter("vehicleType", vehicleType);
	        }
		  if (veNo != null && veNo != 0) {
	            query.setParameter("veNo", veNo);
	        }
		
		return query.getResultList();
	}

	private String buildQuery(Long department, Long vehicleType, Long veNo, Long orgid) {
	          StringBuilder searchQuery = new StringBuilder(
				" SELECT fcr FROM OEMWarranty fcr WHERE fcr.orgid = :orgid ");

	  if (null != department) {
		  searchQuery.append("AND fcr.department = :department");
		}
		  
		  if (null != vehicleType) {
			  searchQuery.append(" AND fcr.vehicleType = :vehicleType ");
	        }
		  
		  if (null != veNo && veNo != 0) {
			  searchQuery.append(" AND fcr.veId = :veNo ");
	        }
		  

		searchQuery.append(" ORDER BY fcr.orgid DESC");

		return searchQuery.toString();

}
}

