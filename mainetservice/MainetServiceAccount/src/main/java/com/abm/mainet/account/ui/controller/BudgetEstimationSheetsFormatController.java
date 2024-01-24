package com.abm.mainet.account.ui.controller;

import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;

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
import com.abm.mainet.account.ui.model.BudgetEstimationSheetsFormatModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;

import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/budgetEstimationSheetsFormat.html")
public class BudgetEstimationSheetsFormatController extends AbstractFormController<BudgetEstimationSheetsFormatModel> {
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private AccountFunctionMasterService tbAcFunctionMasterService;

	@Resource
	private TbOrganisationService tbOrganisationService;
	
	@Resource
	private AccountFinancialReportService accountFinancialReportService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
		final int langId = UserSession.getCurrent().getLanguageId();
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		Long defaultOrgId = null;
		if (isDafaultOrgExist) {
			defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else {
			defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
				UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		this.getModel().setDepList(deptMap);
		try {
			this.getModel().setFunctionList(tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(defaultOrgId,
					superUserOrganization, langId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return index();
	}

	/**
	 * Generate Budget Estimation Sheets Report By
	 * 
	 * @param request
	 * @param financialYearId
	 * @param financialYear
	 * @param deptName
	 * @param deptId
	 * @param functionName
	 * @param functionId
	 * @param reportType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = "report", method = RequestMethod.POST)
	public ModelAndView budgetEstimationSheetsFormatReport(final HttpServletRequest request,
			@RequestParam("financialYearId") Long financialYearId, @RequestParam("financialYear") String financialYear,
			@RequestParam("deptName") String deptName, @RequestParam("deptId") Long deptId,
			@RequestParam("functionName") String functionName, @RequestParam("functionId") Long functionId,
			@RequestParam("reportType") String reportType) {
		sessionCleanup(request);
		Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
		AccountFinancialReportDTO AccountFinancialReportDTO = accountFinancialReportService
				.getbudgetEstimationSheetsReport(financialYearId, deptId, functionId, reportType,orgId);
		if (AccountFinancialReportDTO != null) {
			this.getModel().setAccountFinancialReportDTO(AccountFinancialReportDTO);
		}
		this.getModel().getAccountFinancialReportDTO().setFinancialYear(financialYear);
		this.getModel().getAccountFinancialReportDTO().setDeptName(deptName);
		this.getModel().getAccountFinancialReportDTO().setFunctionName(functionName);
		this.getModel().getAccountFinancialReportDTO().setReportType(reportType);
		return new ModelAndView("budgetEstimationSheets/form", MainetConstants.FORM_NAME, this.getModel());
	}
}
