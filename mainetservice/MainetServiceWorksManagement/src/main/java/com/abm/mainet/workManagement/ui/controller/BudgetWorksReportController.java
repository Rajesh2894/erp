package com.abm.mainet.workManagement.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.service.WorksBudgetReportService;
import com.abm.mainet.workManagement.ui.model.BudgetWorksReportModel;

@Controller
@RequestMapping("/BudgetWorksReport.html")
public class BudgetWorksReportController extends AbstractFormController<BudgetWorksReportModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);

        return new ModelAndView(MainetConstants.WorksManagement.BUDGET_WORKS, MainetConstants.FORM_NAME, getModel());

    }

    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_BUDGET_REPORT, method = { RequestMethod.POST })
    public ModelAndView viewDeductionReport(@RequestParam(MainetConstants.WorksManagement.FROM_DATE) String fromDate,
            @RequestParam(MainetConstants.WorksManagement.TO_DATE) String toDate, final HttpServletRequest request) {
        bindModel(request);
        BudgetWorksReportModel model = this.getModel();
        this.getModel().setCommonHelpDocs("BudgetWorksReport.html");
        model.setFromDate(fromDate);
        model.setToDate(toDate);
        Date frmDate = Utility.stringToDate(fromDate);
        Date tDate = Utility.stringToDate(toDate);
        this.getModel().setWorksBudgetReportDto(
                ApplicationContextProvider.getApplicationContext().getBean(WorksBudgetReportService.class)
                        .getBudgetVsProjExpensesReport(frmDate, tDate,
                                UserSession.getCurrent().getOrganisation().getOrgid()));
        return new ModelAndView(MainetConstants.WorksManagement.BUDGET_WORKS_REPORT, MainetConstants.FORM_NAME,
                getModel());

    }

}