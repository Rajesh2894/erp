package com.abm.mainet.common.leavemanagement.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.leavemanagement.domain.TbLeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<TbLeaveRequest, Long> {

	
	@Query("select l from TbLeaveRequest l where l.approverId=:approverId and l.orgId=:orgId")
	public List<TbLeaveRequest> fetchApproverDataByApproverId(@Param("approverId")Long approverId, @Param("orgId") Long orgId);

	@Query("select l from TbLeaveRequest l where l.leaveReqId=:leaveReqId and l.orgId=:orgId")
	public TbLeaveRequest fetchDataByLeaveReqId(@Param("leaveReqId") Long leaveReqId,@Param("orgId") Long orgId);
	
	
	@Modifying
	@Query("update TbLeaveRequest l set l.approveFlag=:approveFlag, l.approveDate=:approveDate, l.approverRemarks=:approverRemarks,l.lgIpMacUpd=:lgIpMacUpd,l.updatedBy=:updatedBy,l.updatedDate=:updatedDate where l.leaveReqId=:leaveReqId and l.orgId=:orgId")
	void updateTbLeaveRequest(@Param("approveFlag") String approveFlag,@Param("approveDate") Date approveDate,@Param("approverRemarks") String approverRemarks,@Param("lgIpMacUpd") String lgIpMacUpd,@Param("updatedBy") Long updatedBy,@Param("updatedDate") Date updatedDate,@Param("leaveReqId") Long leaveReqId,@Param("orgId") Long orgId);

}
