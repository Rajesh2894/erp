package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.AccountCollectionSummaryDTO;

public interface AccountCollectionSummaryReportService {

    AccountCollectionSummaryDTO processReport(String reportTypeCode, String fromDate, String toDate, long orgId, Long superOrgId);

    AccountCollectionSummaryDTO processCollectionSummaryReport(long orgId, String fromDate, String toDate, String reportTypeCode);

    List<Object[]> findCollectionSummaryReportByTodateAndFromDateAndOrgId(String toDate, String fromDate, long orgId);

    List<Object[]> findCollectionDetailReportbyByTodateAndFromDateAndOrgIdAndCreatedById(String toDate, String fromDate,
            Long orgId, Long ctreated);

    List<Object[]> findBankDetailByReceiptHeadIdAndOrgId(Long receiptId, Long OrgId);
}
