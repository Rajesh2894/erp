package com.abm.mainet.securitymanagement.dao;

import java.util.List;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;

@Repository
public class ContractualStaffMasterDaoImpl extends AbstractDAO<ContractualStaffMaster> implements IContractualStaffMasterDao{

	@Override
	@Transactional(readOnly=true)
	public List<ContractualStaffMaster> findStaffDetails(String contStaffName, Long vendorId, Long locId,
			Long cpdShiftId,Long dayPrefixId, Long orgId) {
		
		Query query = this.createQuery(buildQuery(contStaffName, vendorId, locId, cpdShiftId,dayPrefixId, orgId));
		
        query.setParameter("orgid", orgId);

        if (StringUtils.isNotEmpty(contStaffName)) {
            query.setParameter("contStaffName", "%"+contStaffName+"%");
            
        }
        if (vendorId != null && vendorId!=0) {
            query.setParameter("vendorId", vendorId);
        }
        if (locId != null && locId != 0) { 
            query.setParameter("locId", locId);
        }
        if (cpdShiftId != null && cpdShiftId != 0) {  
            query.setParameter("cpdShiftId", cpdShiftId);
        }
        if (dayPrefixId != null && dayPrefixId!=0) {
        	 query.setParameter("dayPrefixId", dayPrefixId);
        }
        return query.getResultList();
		
	}

	private String buildQuery(String contStaffName, Long vendorId, Long locId,
			Long cpdShiftId,Long dayPrefixId ,Long orgId) {
		StringBuilder searchQuery = new StringBuilder(" select cr from ContractualStaffMaster cr where cr.orgid=:orgid ");
		
		if (StringUtils.isNotEmpty(contStaffName)) {
			searchQuery.append(" AND cr.contStaffName Like :contStaffName ");
		}
		if (vendorId != null && vendorId!=0) {
			searchQuery.append(" AND cr.vendorId = :vendorId ");
		}
		if (locId != null && locId!=0 ) {
            searchQuery.append(" AND cr.locId = :locId ");
        }
		if (cpdShiftId != null && cpdShiftId!=0) {
            searchQuery.append(" AND cr.cpdShiftId = :cpdShiftId ");
        }
		if (dayPrefixId != null && dayPrefixId!=0) {
            searchQuery.append(" AND cr.dayPrefixId = :dayPrefixId ");
        }
		searchQuery.append("ORDER BY cr.contStaffAppointDate DESC ");
		
		return searchQuery.toString();
	}



	}
	
	

