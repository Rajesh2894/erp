package com.abm.mainet.rnl.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.rnl.dto.DemandNoticeDTO;
import com.abm.mainet.rnl.dto.ReportDTO;

public interface IDemandNoticeService {

    List<ReportDTO> fetchDemandNoticeReportByCondition(Date date, Long vendorId, Long contractId, Long orgId,
            boolean noticePrintDetails);

    List<DemandNoticeDTO> fetchDemandNoticeBillData(Long contractId, Date previousFinancialEndDate, Long orgId);

}
