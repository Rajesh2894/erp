package com.abm.mainet.account.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.ui.model.AccountBalanceSheetNmamReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;

@Controller
@RequestMapping("/balanceSheetNmamReport.html")
public class AccountBalanceSheetNmamReportController extends AbstractFormController<AccountBalanceSheetNmamReportModel> {
    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        return index();
    }

}
