package com.abm.mainet.disastermanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.disastermanagement.domain.ComplainRegister;
import com.abm.mainet.disastermanagement.domain.ComplainScrutiny;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;

public interface IComplainRegisterDAO {

	ComplainRegister getComplainRegData(String complainNo, Long orgId,String complainStatus);

	List<Object[]> findByCompNoFrmDtToDate(String complainNo, Date frmDate, Date toDate, Long orgid, String formStatusStr, Long userId, Long deptId);

	void updateComplainRegData(String complainNo, String status, String remark, Long orgid,Long empId,ComplainRegisterDTO complainRegisterDTO);

	List<Object[]> getcomplaintData(Long orgId, Long userId, Long deptId, String formStatusStr);

	List<ComplainRegister> getComplainData(Long orgId, String complainStatus);
	
	List<ComplainScrutiny> getComplainScrutinyData(Long orgId, String complainNo);

	List<ComplainRegister> searchInjuryDetails(Long complaintType1, Long complaintType2, Long location, String complainNo, Long orgid,String complainStatus);

	List<Object[]> getComplaintClosureSummaryList(Long complaintType1, Long complaintType2, Long location,
			String complainNo, Long orgId, Long scrutinyUser, String scrutinyStatus);
	
}
