package com.abm.mainet.care.service;

import java.util.List;

import com.abm.mainet.care.dto.report.ComplaintReportDTO;
import com.abm.mainet.care.dto.report.ComplaintReportRequestDTO;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;

public interface ICareReportService {

	/**
	 * 
	 * @param complaintReportRequest
	 * @return
	 */
	ComplaintReportDTO getDepartmentWiseComplaintReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getDepartmentAndStatusWiseComplaintReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getSlaWiseComplaintReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getComplaintFeedbackReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getComplaintDetailedAgeingReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getComplaintSummaryAgeingReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getComplaintSummaryDetailedReport(ComplaintReportRequestDTO complaintReportRequest);

	ComplaintReportDTO getDepartmentWiseGradingReport(ComplaintReportRequestDTO complaintReportRequest);

	List<SmsEmailTransactionDTO> getSmsEmailHistoryByDeptIAndDate(ComplaintReportRequestDTO compRequestDto);

	ComplaintReportDTO getUserWiseComplaintSummaryReport(ComplaintReportRequestDTO complaintReportRequest);

}
