package com.abm.mainet.workManagement.ui.controller;

import java.text.ParseException;
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
import com.abm.mainet.workManagement.service.WorksDeductionReportService;
import com.abm.mainet.workManagement.ui.model.WorkDeductionReportModel;

@Controller
@RequestMapping("/DeductionReport.html")
public class WorkDeductionReportController extends AbstractFormController<WorkDeductionReportModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);

        return new ModelAndView(MainetConstants.WorksManagement.DEDUCTION_SUMMARY, MainetConstants.FORM_NAME,
                getModel());

    }

    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_DEDUCTION_REPORT, method = { RequestMethod.POST })
    public ModelAndView viewDeductionReport(@RequestParam(MainetConstants.WorksManagement.FROM_DATE) String fromDate,
            @RequestParam(MainetConstants.WorksManagement.TO_DATE) String toDate, final HttpServletRequest request)
            throws ParseException {
        bindModel(request);
        WorkDeductionReportModel model = this.getModel();

        model.setFromDate(fromDate);
        model.setToDate(toDate);
        Date frmDate = Utility.stringToDate(fromDate);
        Date tDate = Utility.stringToDate(toDate);
        this.getModel().setWorksDeductionReportDto(
                ApplicationContextProvider.getApplicationContext().getBean(WorksDeductionReportService.class)
                        .getWorksDeductionReport(UserSession.getCurrent().getOrganisation().getOrgid(), frmDate, tDate));
        return new ModelAndView(MainetConstants.WorksManagement.DEDUCTION_REPORT, MainetConstants.FORM_NAME,
                getModel());

    }
}