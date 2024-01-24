package com.abm.mainet.securitymanagement.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.securitymanagement.domain.EmployeeScheduling;

@Repository
public class EmployeeSchedulingDaoImpl extends AbstractDAO<EmployeeScheduling> implements IEmployeeSchedulingDao{

	@Override
	public List<EmployeeScheduling> search(String empType, Long vendorId, Long locId, Long cpdShiftId,
			Date contStaffSchFrom, Date contStaffSchTo, String contStaffName, String contStaffIdNo, Long emplScdlId,
			Long orgid) {
		StringBuilder builder=null;
		if(empType!=null && !empType.equals(MainetConstants.BLANK)) {
			builder = new StringBuilder(" select distinct cr from EmployeeScheduling cr,ContractualStaffMaster cm where  cr.contStaffIdNo=cm.contStaffIdNo and cm.empType=:empType and cr.orgid=:orgid ");
		}else {
			 builder = new StringBuilder("select distinct cr from EmployeeScheduling cr where cr.orgid=:orgid ");
		}	
		List<String> ids=null;
		List<Long> vendorIds=null;
		if (vendorId != null && vendorId!=0) {
			builder.append(" AND cr.vendorId = :vendorId ");
		}
		if(StringUtils.isNotEmpty(contStaffName)) {
			String queryBuilder="select cm.contStaffIdNo from ContractualStaffMaster cm where cm.contStaffName like :contStaffName";
			Query newQuery=entityManager.createQuery(queryBuilder.toString());
			newQuery.setParameter("contStaffName", "%"+contStaffName+"%");
			List<String> details=newQuery.getResultList();
			
			List<Long> detailNew=new ArrayList<>();
			for(int i=0;i<details.size();i++) {
				List<Long> detail=new ArrayList<>();
				String staffId=details.get(i);
				StringBuilder queryBuilderNew=new StringBuilder("select cm.vendorId from ContractualStaffMaster cm where cm.contStaffName like :contStaffName"); //cm.contStaffIdNo=:staffId and
				
				if(staffId!=null) {
					queryBuilderNew.append(" AND cm.contStaffIdNo = :staffId ");
				}
				
				Query buildQuery=entityManager.createQuery(queryBuilderNew.toString());
				buildQuery.setParameter("contStaffName", "%"+contStaffName+"%");
				
				if (staffId != null) {
					buildQuery.setParameter("staffId", staffId);
				}
				
				detail=buildQuery.getResultList();
				detailNew.addAll(detail);
			}
			
			if(details !=null && !details.isEmpty() && detailNew !=null && !detailNew.isEmpty()) {
				ids=details;
				vendorIds=detailNew;
				builder.append(" AND cr.contStaffIdNo in (:ids) and cr.vendorId in (:vendorIds) ");
			}
		}
		if (locId != null && locId!=0 ) {
			builder.append(" AND cr.locId = :locId ");
        }
		if (cpdShiftId != null && cpdShiftId!=0) {
			builder.append(" AND cr.cpdShiftId = :cpdShiftId ");
        }
		if (contStaffSchFrom != null && contStaffSchTo != null) {
			builder.append(" AND   cr.contStaffSchTo >= :contStaffSchFrom ");
		}
		if (contStaffSchTo != null && contStaffSchFrom != null) {
			builder.append(" AND cr.contStaffSchFrom <= :contStaffSchTo ");
		}
		if (StringUtils.isNotEmpty(contStaffIdNo)) {
			builder.append(" AND cr.contStaffIdNo = :contStaffIdNo ");
		}
		if (emplScdlId != null && emplScdlId!=0) {
			builder.append(" AND cr.emplScdlId = :emplScdlId ");
		}
		
		final Query query = entityManager.createQuery(builder.toString());
		query.setParameter("orgid", orgid);
		if(empType!=null && !empType.equals(MainetConstants.BLANK)) {
			query.setParameter("empType", empType);
		}
		if (vendorId != null && vendorId!=0) {
			query.setParameter("vendorId", vendorId);
		}
		if (locId != null && locId!=0 ) {
			query.setParameter("locId", locId);
		}
		if (cpdShiftId != null && cpdShiftId!=0) {
			query.setParameter("cpdShiftId", cpdShiftId);
		}
		if (contStaffSchFrom != null) {
			query.setParameter("contStaffSchFrom", contStaffSchFrom);
		}
		if (contStaffSchTo != null) {
			query.setParameter("contStaffSchTo", contStaffSchTo);
		}
		if (StringUtils.isNotEmpty(contStaffIdNo)) {
			query.setParameter("contStaffIdNo", contStaffIdNo);
		}
		if (ids !=null  && !ids.isEmpty()) {
			query.setParameter("ids", ids);
		}
		if (vendorIds !=null  && !vendorIds.isEmpty()) {
			query.setParameter("vendorIds", vendorIds);
		}
		if (emplScdlId != null && emplScdlId!=0) {
			query.setParameter("emplScdlId", emplScdlId);
		}
		return query.getResultList();
		
	}

}
