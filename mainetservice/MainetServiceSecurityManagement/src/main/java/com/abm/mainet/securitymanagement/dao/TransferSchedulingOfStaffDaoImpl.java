package com.abm.mainet.securitymanagement.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.securitymanagement.domain.ContractualStaffMaster;
import com.abm.mainet.securitymanagement.domain.TransferAndDutySchedulingOfStaff;

@Repository
public class TransferSchedulingOfStaffDaoImpl extends AbstractDAO<TransferAndDutySchedulingOfStaff> implements ITransferSchedulingOfStaffDao {

	@Override
	public List<ContractualStaffMaster> findStaffDetails(Long empTypeId,String empCode, Long vendorId, Long cpdShiftId,
			Long locId, Long orgId) {
		
		Query query = this.createQuery(buildQuery(empTypeId,empCode, vendorId, cpdShiftId, locId, orgId));
		query.setParameter("orgid", orgId);
		
		if (empCode != null && !empCode.equals(MainetConstants.BLANK)) {
			query.setParameter("empCode", empCode);
		}
		if (vendorId != null && vendorId != 0) {
			query.setParameter("vendorId", vendorId);
		}
		if (locId != null && locId != 0) {
			query.setParameter("locId", locId);
		}
		if (cpdShiftId != null && cpdShiftId != 0) {
			query.setParameter("cpdShiftId", cpdShiftId);
		}

		return query.getResultList();
	
	}

	private String buildQuery(Long empTypeId,String empCode, Long vendorId, Long cpdShiftId, Long locId, Long orgId) {
		StringBuilder searchQuery = new StringBuilder(
				"select tr from ContractualStaffMaster tr where tr.orgid=:orgid");

		if (empCode != null && !empCode.equals(MainetConstants.BLANK)) {
			searchQuery.append(" AND tr.empType=:empCode");
		}
		if (vendorId != null ) {
			searchQuery.append(" AND tr.vendorId=:vendorId");
		}
		if (cpdShiftId != null && cpdShiftId != 0) {
			searchQuery.append(" AND tr.cpdShiftId=:cpdShiftId");
		}
		if (locId != null) {
			searchQuery.append(" AND tr.locId=:locId");
		}
		searchQuery.append(" AND tr.status is 'A' order by tr.contStsffId desc ");
		return searchQuery.toString();
	}

}
