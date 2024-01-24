package com.abm.mainet.care.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.dto.report.ComplaintDTO;
import com.abm.mainet.care.dto.report.ComplaintGradeSummary;
import com.abm.mainet.care.dto.report.SummaryField;

public interface IComplaintDAO {

    Set<ComplaintDTO> getComplaints(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate, String status,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, long langId, String referenceMode, String empId);

    Set<ComplaintDTO> getComplaints(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
            List<String> status,
            List<String> worflowStatus,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, long langId);

    Set<ComplaintDTO> getComplaintFeedbacks(Long orgId, Date fromDate, Date toDate, Integer feedbackRating, long langId);

    Map<SummaryField, Long> getComlaintSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
            String status,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5,
            long langId);

    List<Object[]> searchComplaints(ComplaintSearchDTO filter);

    Set<ComplaintGradeSummary> getComplaintSummaryDepartmentWise(Long orgId, Long deptId, Long compalintTypeId, Date fromDate,
            Date toDate,
            Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
            Long codIdOperLevel5, long langId);

    List<Object[]> searchComplaintsDetail(ComplaintSearchDTO filter);

    List<Object[]> searchComplaintsDetailForCareOperatorRole(ComplaintSearchDTO filter);

	List<ComplaintDTO> getComplaintsSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId, String referenceMode, String empId);

	List<ComplaintDTO> getComplaintsSummaryDeptStatWise(Long orgId, Long deptId, Long compalintTypeId, Date fromDate,
			Date toDate, String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId, String referenceMode, String empId);

	
	List<ComplaintDTO> getComplaintsSummarySlaWise(Long orgId, Long department, Long complaintType, Date fromDate,
			Date toDate, List<String> statuses, String closed, Long codIdOperLevel1, Long codIdOperLevel2,
			Long codIdOperLevel3, Long codIdOperLevel4, Long codIdOperLevel5, long langId);

	Set<ComplaintDTO> getComplaintFeedbacksSummary(Long orgId, Date fromDate, Date toDate, Integer feedbackRating,
			long langId);

	List<ComplaintDTO> getUserWiseComplaintSummary(Long orgId, Long deptId, Long compalintTypeId, Date fromDate,
			Date toDate, String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3,
			Long codIdOperLevel4, Long codIdOperLevel5, long langId, String referenceMode, String empId);

	
	Set<ComplaintDTO> getComplaintsWithPendingWith(Long orgId, Long deptId, Long compalintTypeId, Date fromDate, Date toDate,
			String status, Long codIdOperLevel1, Long codIdOperLevel2, Long codIdOperLevel3, Long codIdOperLevel4,
			Long codIdOperLevel5, long langId,String referenceMode,String empId);

}
