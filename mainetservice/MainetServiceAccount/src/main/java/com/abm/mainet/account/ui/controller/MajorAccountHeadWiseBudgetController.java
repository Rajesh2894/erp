package com.abm.mainet.account.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.ui.model.MajorAccountHeadWiseBudgetModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("majorAccountHeadWiseBudget.html")
public class MajorAccountHeadWiseBudgetController extends AbstractFormController<MajorAccountHeadWiseBudgetModel> {
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView majorAccountHeadWiseBudgetReport(final HttpServletRequest request,
            @RequestParam("financialYearId") Long financialYearId,
            @RequestParam("financialYear") String financialYear) {
        sessionCleanup(request);
        this.getModel().getAccountFinancialReportDTO().setFinancialYear(financialYear);
        return new ModelAndView("majorAccountHeadWiseBudget/form", MainetConstants.FORM_NAME, this.getModel());
    }

}
