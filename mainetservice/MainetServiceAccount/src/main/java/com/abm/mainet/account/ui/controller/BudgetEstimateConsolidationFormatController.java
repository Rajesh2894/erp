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
import com.abm.mainet.account.ui.model.BudgetEstimateConsolidationFormatModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller       
@RequestMapping("/budgetEstimateConsolidationFormat.html")
public class BudgetEstimateConsolidationFormatController extends AbstractFormController<BudgetEstimateConsolidationFormatModel> {
    
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
    @RequestMapping(params = "report", method = RequestMethod.POST)
    public ModelAndView BudgetEstimateConsolidationFormatReport(final HttpServletRequest request,
            @RequestParam("financialYearId") Long financialYearId,
            @RequestParam("financialYear") String financialYear,
            @RequestParam("reportType") String reportType) {
        sessionCleanup(request);
        Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
        AccountFinancialReportDTO reportData = accountFinancialReportService.getbudgetEstimationConsolidationFormat(financialYearId, null, reportType, orgId);
        this.getModel().getAccountFinancialReportDTO().setFinancialYear(financialYear);
        reportData.setReportType(reportType);
        reportData.setFinancialYear(financialYear);
        this.getModel().setAccountFinancialReportDTO(reportData);
        return new ModelAndView("budgetEstimateConsolidation/form", MainetConstants.FORM_NAME, this.getModel());
    }
}
