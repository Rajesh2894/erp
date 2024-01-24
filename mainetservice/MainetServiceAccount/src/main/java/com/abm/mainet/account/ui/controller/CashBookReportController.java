package com.abm.mainet.account.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.ui.model.CashBookReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/cashBookReport.html")
public class CashBookReportController extends AbstractFormController<CashBookReportModel> {

    @Resource
    private AccountFinancialReportService accountFinancialReportService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "cashBookReport", method = RequestMethod.POST)
    public ModelAndView cashBookReport(final HttpServletRequest request, @RequestParam("fromDateId") String fromDateId, @RequestParam("toDateId") String toDateId) {
        sessionCleanup(request);
        AccountFinancialReportDTO accountFinancialReportDTO = accountFinancialReportService.getPaymentAndReceiptReportData(
                fromDateId,toDateId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (accountFinancialReportDTO != null) {
            this.getModel().setAccountFinancialReportDTO(accountFinancialReportDTO);
        }
        this.getModel().getAccountFinancialReportDTO().setFromDate(fromDateId);
        this.getModel().getAccountFinancialReportDTO().setToDate(toDateId);
        return new ModelAndView("CashBookReport/Form", MainetConstants.FORM_NAME, this.getModel());
    }

}
