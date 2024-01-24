package com.abm.mainet.account.ui.controller;

import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.common.constant.MainetConstants.ReportType;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AccountGeneralLedgerReports.html")
public class AccountGeneralLedgerReportController {

    private static final Logger LOGGER = Logger.getLogger(AccountFinancialReportController.class);

    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Resource
    private AccountFinancialReportService accountFinancialReportService;

    @Resource
    private TbAcCodingstructureMasService acCodingstructureMasService;

    private static final String REPORT_TYPE_LOOKUPS = "reportTypeLookUps";
    private static final String REPORT_DTO = "reportDTO";
    private static final String NO_REPORT_NAME_FOUND = "No Report name found for reportType=";
    private static final String JSP_VIEW_ON_REPORT_TYPE_SELECT = "AccountGeneralLedgerReportHomeOnReportType";
    private static final String INDEX_PAGE = "AccountGeneralLedgerReportHome";

    /**
     * index page for Report
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping()
    public String index(final HttpServletRequest request, final ModelMap model) {
        String reportTypeCode = "GLR";
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        accountFinancialReportService.initializeOnReportTypeSelect(model, reportTypeCode, orgId, langId);
        model.addAttribute(REPORT_TYPE_LOOKUPS, CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                UserSession.getCurrent().getOrganisation()));
        model.addAttribute(REPORT_DTO, new AccountFinancialReportDTO());
        return JSP_VIEW_ON_REPORT_TYPE_SELECT;
    }

    /**
     * process request on select of Report Type and GUI will be change dynamically as per Report Type as well initialize data
     * required.
     * 
     * @param reportTypeCode
     * @param model
     * @return : same index page
     */
    @RequestMapping(method = RequestMethod.POST, params = "onReportType")
    public String homePageOnReportType(@RequestParam("reportTypeCode") final String reportTypeCode,
            final ModelMap model) {
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        accountFinancialReportService.initializeOnReportTypeSelect(model, reportTypeCode, orgId, langId);
        model.addAttribute(REPORT_TYPE_LOOKUPS, CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                UserSession.getCurrent().getOrganisation()));
        model.addAttribute(REPORT_DTO, new AccountFinancialReportDTO());
        return JSP_VIEW_ON_REPORT_TYPE_SELECT;
    }

    /**
     *
     * process request on click of view report button and fetch Report related data and initialize as well decide Report page what
     * need to be displayed
     * 
     * @param reportTypeCode
     * @param transactionDate
     * @param fromDate
     * @param toDate
     * @param accountHeadId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "report")
    public String createBillEntry(@Valid final AccountFinancialReportDTO dto,
            final BindingResult bindingResult, final ModelMap model, final HttpServletRequest httpServletRequest)
            throws ParseException {
        dto.setReportType("GLR");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        accountFinancialReportService.processGeneralLedgerReport(model, dto, orgId);
        return decideReportPage(dto.getReportType());
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
