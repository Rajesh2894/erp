package com.abm.mainet.common.leavemanagement.dao;

import java.math.BigDecimal;

import com.abm.mainet.common.leavemanagement.domain.TbLeaveGrantMaster;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproveDTO;

public interface LeaveManagementDao {
	
	public void updateLeave(BigDecimal leavedays,TbLeaveGrantMaster leaveMasterEntity,LeaveManagementApproveDTO approveDto,String leaveType);

}
