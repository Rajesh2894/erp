package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.rnl.dto.EstatePropGrid;
import com.abm.mainet.rnl.ui.model.ReportModel;

public interface IReportService {

    void fetchRevenueReport(ReportModel model, String occupancyType, String fromDate, String toDate, Long serviceId,
            Long orgId);

    void fetchOutstandingReport(ReportModel model, String date, Long orgId);

    void fetchDemandRegisterReport(ReportModel model, String financialYear, Long orgId);

	void fetchDemandRegisterReportUsingProp(ReportModel model, Long estateId, Long propId, String financialYear, Long orgId);

	void fetchDemandRegisterReportAllProp(ReportModel model, Long estateId, List<EstatePropGrid> list,
			String financialYear, Long orgId);
}
