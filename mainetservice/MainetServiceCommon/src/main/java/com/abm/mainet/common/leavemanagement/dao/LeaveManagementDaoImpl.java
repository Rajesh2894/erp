package com.abm.mainet.common.leavemanagement.dao;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.leavemanagement.domain.TbLeaveGrantMaster;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproveDTO;


@Repository
public class LeaveManagementDaoImpl extends AbstractDAO<TbLeaveGrantMaster> implements LeaveManagementDao {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public void updateLeave(BigDecimal leavedays, TbLeaveGrantMaster leaveMasterEntity,
			LeaveManagementApproveDTO approveDto,String leaveType) {
		final StringBuilder builder = new StringBuilder();
		builder.append("UPDATE TbLeaveGrantMaster t set t.noOfLeavesGranted=:noOfLeavesGranted , t.updatedBy=:updatedBy , t.updatedDate=:updatedDate, t.lgIpMacUpd=:lgIpMacUpd , ");
			switch (leaveType) {
            case MainetConstants.LEAVEMANAGEMENT.ML:
            	builder.append(" t.mlLeaveBalance=:leaveBalance ");
            	break;
            case MainetConstants.LEAVEMANAGEMENT.CL:
            	builder.append(" t.clLeaveBalance=:leaveBalance ");
                break;
            case MainetConstants.LEAVEMANAGEMENT.PL:
            	builder.append(" t.plLeaveBalance=:leaveBalance ");
                break;
            case MainetConstants.LEAVEMANAGEMENT.PIL:
            	builder.append(" t.ptLeaveBalance=:leaveBalance ");
            	break;
            case MainetConstants.LEAVEMANAGEMENT.SL:
            	builder.append(" t.slLeaveBalance=:leaveBalance ");
            	break;
            }
		
			builder.append("  WHERE t.leaveGrantId=:leaveGrantId and t.orgId=:orgId");
		
		try {
			final Query query = entityManager.createQuery(builder.toString());
			query.setParameter("noOfLeavesGranted", leaveMasterEntity.getNoOfLeavesGranted());
			query.setParameter("orgId", approveDto.getOrgId());
			query.setParameter("leaveGrantId", leaveMasterEntity.getLeaveGrantId());
			query.setParameter("leaveBalance", leavedays);
			query.setParameter("updatedBy", leaveMasterEntity.getUpdatedBy());
			query.setParameter("updatedDate", leaveMasterEntity.getUpdatedDate());
			query.setParameter("lgIpMacUpd", leaveMasterEntity.getLgIpMacUpd());
			query.executeUpdate();
		} catch (final Exception e) {
			
		}
	
	}
}
