package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.ReportType;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 *
 * @author Vivek.Kumar
 * @since 09-Aug-2017
 */
@Controller
// @RequestMapping("/AccountFinancialReport.html")
@RequestMapping(value = { "/AccountFinancialReport.html", "AccountCollectionReports.html", "AccountDailyReports.html",
        "AccountBudgetReports.html", "AccountOtherReports.html", "AccountExpensesReports.html" })
public class AccountFinancialReportController {

    private static final Logger LOGGER = Logger.getLogger(AccountFinancialReportController.class);

    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Resource
    private AccountFinancialReportService accountFinancialReportService;

    @Resource
    private TbAcCodingstructureMasService acCodingstructureMasService;
    
    @Resource
    private AccountFieldMasterService accountFieldMasterService;

    private static final String REPORT_TYPE_LOOKUPS = "reportTypeLookUps";
    private static final String REGISTER_DEPOSIT_LOOKUPS = "registerDepositList";
    private static final String REPORT_DTO = "reportDTO";
    private static final String NO_REPORT_NAME_FOUND = "No Report name found for reportType=";
    private static final String JSP_VIEW_ON_REPORT_TYPE_SELECT = "AccountFinancialReportHomeOnReportType";
    private static final String INDEX_PAGE = "AccountFinancialReportHome";
    private static final String PAYMENT_MODE = "paymentMode";

    /**
     * index page for Report
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping()
    public String index(final HttpServletRequest request, final ModelMap model) {

        if (request.getRequestURI().contains("Budget")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.FlagB))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            model.addAttribute("testPage", ApplicationSession.getInstance().getMessage("summary.budget.report.account.budget"));
            model.addAttribute("resetPage", "AccountBudgetReports.html");
        } else if (request.getRequestURI().contains("Collection")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.FlagC))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            model.addAttribute("testPage", ApplicationSession.getInstance().getMessage("account.Account.Collection.Reports"));
            model.addAttribute("resetPage", "AccountCollectionReports.html");
        } else if (request.getRequestURI().contains("Daily")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.FlagD)
                            && !look.getLookUpCode().equals("GLR"))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            List<LookUp> registerDepositList = CommonMasterUtility.getLookUps(AccountPrefix.DTY.toString(),
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(REGISTER_DEPOSIT_LOOKUPS, registerDepositList);
            model.addAttribute("testPage",ApplicationSession.getInstance().getMessage("account.daily.reports.msg"));
            model.addAttribute("resetPage", "AccountDailyReports.html");
        } else if (request.getRequestURI().contains("Other")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null
                            && look.getOtherField().equals(PrefixConstants.D2KFUNCTION.CPD_OTHERVALUE))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            model.addAttribute("testPage", ApplicationSession.getInstance().getMessage("account.financial.otherReports"));
            model.addAttribute("resetPage", "AccountOtherReports.html");
        } else if (request.getRequestURI().contains("Expenses")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.MENU.E))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            model.addAttribute("testPage", ApplicationSession.getInstance().getMessage("Account.Expenses.Reports"));
            model.addAttribute("resetPage", "AccountExpensesReports.html");
        } else if (request.getRequestURI().contains("Financial")) {
            List<LookUp> newFinBudgetReportList = new ArrayList<>();
            List<LookUp> finBudgetReportList = CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation());
            newFinBudgetReportList = finBudgetReportList.stream()
                    .filter(look -> look.getOtherField() != null && look.getOtherField().equals(MainetConstants.FlagF))
                    .collect(Collectors.toList());
            model.addAttribute(REPORT_TYPE_LOOKUPS, newFinBudgetReportList);
            model.addAttribute("testPage", ApplicationSession.getInstance().getMessage("account.Account.Financial.Reports"));
            model.addAttribute("resetPage", "AccountFinancialReport.html");
        } else {
            model.addAttribute(REPORT_TYPE_LOOKUPS, CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                    UserSession.getCurrent().getOrganisation()));
        }
        model.addAttribute(MainetConstants.VOUCHER_TEMPLATE_ENTRY.COMMAND, model);
        model.addAttribute(REPORT_DTO, new AccountFinancialReportDTO());
        return INDEX_PAGE;
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
        Map<Long, String> fieldMaster = accountFieldMasterService.getFieldMasterStatusLastLevels(UserSession.getCurrent().getOrganisation().getOrgid(),
        		UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
        fieldMaster.put(-1L, "ALL");
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,fieldMaster);
        int langId = UserSession.getCurrent().getLanguageId();
        accountFinancialReportService.initializeOnReportTypeSelect(model, reportTypeCode, orgId, langId);
        model.addAttribute(REPORT_TYPE_LOOKUPS, CommonMasterUtility.getLookUps(AccountPrefix.FRT.toString(),
                UserSession.getCurrent().getOrganisation()));
        List<LookUp> registerDepositList = CommonMasterUtility.getLookUps(AccountPrefix.DTY.toString(),
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(REGISTER_DEPOSIT_LOOKUPS, registerDepositList);
        final List<LookUp> clearLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.CLR,
                UserSession.getCurrent().getOrganisation());
        List<LookUp> newCategoryList = new ArrayList<>();
        if(reportTypeCode!=null && reportTypeCode.equals("CRR")) {
        	 newCategoryList = clearLookUpList.stream()
                     .filter(look -> look.getOtherField() != null && !look.getLookUpCode().equals("STL") && !look.getLookUpCode().equals("STP") && !look.getLookUpCode().equals("ISD"))
                     .collect(Collectors.toList());
        }else {
        	 newCategoryList = clearLookUpList.stream()
                     .filter(look -> look.getOtherField() != null)
                     .collect(Collectors.toList());
        }
        model.addAttribute(MainetConstants.BankReconciliation.CATEGORY_LIST, newCategoryList);
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(AccountConstants.CASH.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.PCA.getValue())) {
                paymentMode.add(payList);
            }
        }
        model.addAttribute(PAYMENT_MODE, paymentMode);

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
    //
    @RequestMapping(method = RequestMethod.POST, params = "report")
    public String findReport(@RequestParam("reportTypeCode") final String reportTypeCode,
            @RequestParam("transactionDate") final String transactionDate,
            @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
            @RequestParam("accountHeadId") final Long accountHeadId,
            @RequestParam("financialId") final Long financialId,
            @RequestParam("transactionTypeCode") final String transactionTypeCode,
            @RequestParam("registerDepTypeId") final Long registerDepTypeId,
            @RequestParam("categoryId") final String categoryId,
            @RequestParam("paymodeId") final Long paymodeId,
            @RequestParam("vendorId") final Long vendorId,
            @RequestParam("fieldId") final Long fieldId,
            final HttpServletRequest request,
            final ModelMap model) {

        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        accountFinancialReportService.processReport(model, reportTypeCode, transactionDate, fromDate, toDate,
                accountHeadId, orgId, superOrgId, langId, financialId, transactionTypeCode, registerDepTypeId,
                categoryId, paymodeId, vendorId,fieldId);
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
    @RequestMapping(params = "GetBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		if (StringUtils.equals(ReportType, MainetConstants.AccountBudgetCode.NAB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NotesToAccountsAsPerNMAM_Version2.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, MainetConstants.AccountBudgetCode.CFB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=CashFlowReport_Version2.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} 
		else if (StringUtils.equals(ReportType, MainetConstants.AccountBudgetCode.BSB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=BalanceSheetScheduleReport_Version2.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}
		
		else if (StringUtils.equals(ReportType, MainetConstants.AccountBudgetCode.NMB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NMAM_BLSH.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}
		else if (StringUtils.equals(ReportType, MainetConstants.AccountBudgetCode.IEB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ScheduleReport_IncomeExpenditureVersion2.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}
		return ReportType;
		

	}
}
