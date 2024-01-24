package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.account.dto.AccountCollectionSummaryDTO;
import com.abm.mainet.account.service.AccountCollectionSummaryReportService;
import com.abm.mainet.account.ui.model.AccountCollectionSummaryReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.ReportType;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AccountCollectionSummaryReport.html")
public class AccountCollectionSummaryReportController extends AbstractFormController<AccountCollectionSummaryReportModel> {

    private static final Logger LOGGER = Logger.getLogger(AccountCollectionSummaryReportController.class);

    @Resource
    AccountCollectionSummaryReportService accountCollectionSummaryReportService;

    private static final String INDEX_PAGE = "accountFinancialCollectionReportHome/list";
    private static final String REPORT_TYPE_LOOKUPS = "reportTypeLookUps";
    private static final String COLLECTION_REPORT_DTO = "collectionReportDto";
    private static final String NO_REPORT_NAME_FOUND = "No Report name found for reportType=";
    private static final String REPORT_DATA = "reportData";

    @RequestMapping()
    public String index(final HttpServletRequest request, final ModelMap model) {

        List<LookUp> lookUpListFiltered = new ArrayList<LookUp>();
        final List<LookUp> lookupList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                UserSession.getCurrent().getOrganisation());

        for (final LookUp lookUp : lookupList) {
            if (lookUp.getOtherField() != null && !lookUp.getOtherField().isEmpty()) {
                if (lookUp.getOtherField().equals(MainetConstants.FlagC)) {
                    LookUp lookUpValue = new LookUp();
                    lookUpValue.setLookUpId(lookUp.getLookUpId());
                    lookUpValue.setLookUpCode(lookUp.getLookUpCode());
                    lookUpValue.setLookUpDesc(lookUp.getLookUpDesc());
                    lookUpValue.setLookUpType(lookUp.getLookUpType());
                    lookUpValue.setDescLangFirst(lookUp.getDescLangFirst());
                    lookUpListFiltered.add(lookUpValue);
                }
            }
        }

        model.addAttribute(REPORT_TYPE_LOOKUPS,
                lookUpListFiltered);
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
        model.addAttribute(COLLECTION_REPORT_DTO, new AccountCollectionSummaryDTO());
        return INDEX_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST, params = "report")
    public String findReport(@RequestParam("reportTypeCode") final String reportTypeCode,
            @RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate,
            final HttpServletRequest request, final ModelMap model) {
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        AccountCollectionSummaryDTO accountCollectionSummaryDTO = accountCollectionSummaryReportService.processReport(
                reportTypeCode, fromDate, toDate,
                orgId, superOrgId);
        model.addAttribute(REPORT_DATA, accountCollectionSummaryDTO);
        return decideReportPage(reportTypeCode);
    }

    private String decideReportPage(final String reportTypeCode) {
        final String reportName = ReportType.reportName(reportTypeCode);
        if (reportName.isEmpty()) {
            LOGGER.error(NO_REPORT_NAME_FOUND + reportTypeCode);
            throw new NullPointerException(NO_REPORT_NAME_FOUND + reportTypeCode);
        }
        return reportName;

    }
}