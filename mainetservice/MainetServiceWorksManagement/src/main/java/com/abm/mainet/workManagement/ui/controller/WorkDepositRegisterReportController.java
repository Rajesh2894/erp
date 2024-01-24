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
import com.abm.mainet.workManagement.service.WorksDepositRegisterReportService;
import com.abm.mainet.workManagement.ui.model.WorkDepositRegisterReportModel;

@Controller
@RequestMapping("/DepositRefundLibilityRegisterReport.html")
public class WorkDepositRegisterReportController extends AbstractFormController<WorkDepositRegisterReportModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().bind(httpServletRequest);
        this.getModel().setCommonHelpDocs("DepositRefundLibilityRegisterReport.html");

        return new ModelAndView(MainetConstants.WorksManagement.DEPOSIT_REGISTER, MainetConstants.FORM_NAME,
                getModel());

    }

    @RequestMapping(params = MainetConstants.WorksManagement.VIEW_DEPOSIT_REGISTER_REPORT, method = {
            RequestMethod.POST })
    public ModelAndView viewDepositReport(@RequestParam(MainetConstants.WorksManagement.FROM_DATE) String fromDate,
            @RequestParam(MainetConstants.WorksManagement.TO_DATE) String toDate, final HttpServletRequest request) {
        bindModel(request);
        WorkDepositRegisterReportModel model = this.getModel();
        model.setFromDate(fromDate);
        model.setToDate(toDate);
        Date frmDate = Utility.stringToDate(fromDate);
        Date tDate = Utility.stringToDate(toDate);
        this.getModel()
                .setWorksDepositRegisterReportDto(
                        ApplicationContextProvider.getApplicationContext().getBean(WorksDepositRegisterReportService.class)
                                .getWorksDepositRegisterReport(UserSession.getCurrent().getOrganisation().getOrgid(), frmDate,
                                        tDate));
        if(!this.getModel().getWorksDepositRegisterReportDto().isEmpty())
        	this.getModel().setTotalValue(this.getModel().getWorksDepositRegisterReportDto().get(0).getTotalSum());
        return new ModelAndView(MainetConstants.WorksManagement.DEPOSIT_REGISTER_REPORT, MainetConstants.FORM_NAME,
                getModel());

    }
}