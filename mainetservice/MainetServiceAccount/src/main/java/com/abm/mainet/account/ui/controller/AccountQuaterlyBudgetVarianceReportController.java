package com.abm.mainet.account.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.ui.model.AccountQuaterlyBudgetVarianceReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/QuaterlyBudgetVarianceReport.html")
public class AccountQuaterlyBudgetVarianceReportController
        extends AbstractFormController<AccountQuaterlyBudgetVarianceReportModel> {
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Resource
    private AccountFinancialReportService accountFinancialReportService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "quaterlybudgetreport", method = RequestMethod.POST)
    public ModelAndView AccountQuaterlyBudgetVarianceReport(final HttpServletRequest request,
            @RequestParam("financialYearId") Long financialYearId, @RequestParam("financialYear") String financialYear) {
        sessionCleanup(request);
        this.getModel().setAccountFinancialReportDTO(accountFinancialReportService
                .getQuarterlyBudgetVarianceReportData(financialYearId, UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().getAccountFinancialReportDTO().setFinancialYear(financialYear);
        return new ModelAndView("quaterlybudgetreport/form", MainetConstants.FORM_NAME, this.getModel());
    }
}
