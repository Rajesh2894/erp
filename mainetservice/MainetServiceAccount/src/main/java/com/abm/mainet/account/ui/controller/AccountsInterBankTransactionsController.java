package com.abm.mainet.account.ui.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.ui.model.AccountsInterBankTransactionsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/interBankTransactionsReport.html")
public class AccountsInterBankTransactionsController extends AbstractFormController<AccountsInterBankTransactionsModel> {

    @Autowired
    AccountFinancialReportService accountFinancialReportService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.getModel().setCommonHelpDocs("interBankTransactionsReport.html");
        sessionCleanup(httpServletRequest);
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "viewreport", method = RequestMethod.POST)
    public ModelAndView IncomeAndExpenditureReportForm(final HttpServletRequest request,
            @RequestParam("fromDateId") String fromDateId, @RequestParam("toDateId") String toDateId) {
        sessionCleanup(request);
        AccountFinancialReportDTO accountFinancialReportDTO = accountFinancialReportService
                .getInterBankTransactionsReportData(fromDateId, toDateId, UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setAccountFinancialReportDTO(accountFinancialReportDTO);
        this.getModel().getAccountFinancialReportDTO().setFromDate(fromDateId);
        this.getModel().getAccountFinancialReportDTO().setToDate(toDateId);
        return new ModelAndView("InterBankTransactions/Report", MainetConstants.FORM_NAME, this.getModel());
    }
}
