package com.abm.mainet.common.leavemanagement.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApplyRequestDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproveDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementApproverDetailDTO;
import com.abm.mainet.common.leavemanagement.dto.LeaveManagementResponseDTO;

public interface LeaveManagementService {
	
	public Map<String,Object> getLeaveBalanceBasedOnType(Long orgId,Long empId);
	
	
    public List<String> applyLeave(LeaveManagementApplyRequestDTO leaveReqDTO);
    
    public List<LeaveManagementApproverDetailDTO> fetchApproverData(Long approverId, Long orgId);

	public LeaveManagementResponseDTO updateLeaveApprovalData(LeaveManagementApproveDTO approveDto);
   
  
    
}
