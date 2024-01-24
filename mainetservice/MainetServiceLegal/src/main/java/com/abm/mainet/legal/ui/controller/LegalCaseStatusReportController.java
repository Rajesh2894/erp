package com.abm.mainet.legal.ui.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.ui.model.LegalCaseStatusReportModel;

@Controller
@RequestMapping("/LegalCaseReport.html")
public class LegalCaseStatusReportController extends AbstractFormController<LegalCaseStatusReportModel> {

	@Autowired
	private ICaseEntryService caseEntryService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		LegalCaseStatusReportModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));
		return index();
	}

	@RequestMapping(params = "GetCaseStatusReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportform(@RequestParam("caseStatus") Long caseStatus,
			@RequestParam("caseType") Long caseType, @RequestParam("csefrmDate") Date csefrmDate,
			@RequestParam("csetoDate") Date csetoDate, final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String fromdate = Utility.dateToString(csefrmDate, "yyyy-MM-dd");
		String todate = Utility.dateToString(csetoDate, "yyyy-MM-dd");

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=LegalCaseStatusReport.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&CaseType=" + caseType + "&CaseStatus=" + caseStatus + "&ActiveFromDate=" + fromdate
				+ "&ActiveToDate=" + todate;

	}

	/***************** Cases Dairy Report *********************/
	@RequestMapping(method = { RequestMethod.POST }, params = "getListCasesReport")
	public ModelAndView getListCasesReport(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		LegalCaseStatusReportModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));
		return customResult("ListOfCasesReport");
	}

	@RequestMapping(params = "GetListOfCases", method = { RequestMethod.POST })
	public @ResponseBody String viewListCases(@RequestParam("CasedSuitNo") String CasedSuitNo,
			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=CasesDairyReport.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&CaseNo=" + CasedSuitNo;

	}
}