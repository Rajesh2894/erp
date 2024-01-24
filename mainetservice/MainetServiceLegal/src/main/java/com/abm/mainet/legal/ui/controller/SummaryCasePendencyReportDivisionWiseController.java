package com.abm.mainet.legal.ui.controller;

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
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.ui.model.SummaryCasePendencyReportDivisionWiseModel;

@Controller
@RequestMapping("/SummaryCasePendencyReport.html")
public class SummaryCasePendencyReportDivisionWiseController
		extends AbstractFormController<SummaryCasePendencyReportDivisionWiseModel> {

	@Autowired
	private ICourtMasterService courtMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		SummaryCasePendencyReportDivisionWiseModel model = this.getModel();

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		model.setCourtMasterDTOList(courtMasterService.getAllActiveCourtMaster(currentOrgId));
		return index();
	}

	@RequestMapping(params = "GetSummaryCaseReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewSummaryReportSheet(@RequestParam("cseDivisionType") Long cseDivisionType,
			@RequestParam("cseCourtId") Long cseCourtId, final HttpServletRequest request) {
		SummaryCasePendencyReportDivisionWiseModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		/*
		 * model.setCourtMasterDTOList(courtMasterService.getAllActiveCourtMaster(
		 * currentOrgId));
		 */

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=SummaryCasePendencyReportDivisionWise.rptdesign&OrgId="
				+ currentOrgId + "&DivisionWise=" + cseDivisionType + "&CourtWise=" + cseCourtId;

	}

	/* Gender Wise Advocate List report for deharadun*/
	@RequestMapping(method = { RequestMethod.POST }, params = "getGenderWiseAdvReport")
	public ModelAndView getGenderWiseAdvReport(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		SummaryCasePendencyReportDivisionWiseModel model = this.getModel();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setCourtMasterDTOList(courtMasterService.getAllActiveCourtMaster(currentOrgId));

		return customResult("GenderWiseAdvocateList");
	}

	@RequestMapping(params = "getGenderAdvList", method = { RequestMethod.POST })
	public @ResponseBody String viewReportList(@RequestParam("advGen") Long advGen,
			@RequestParam("courtIde") Long courtIde, final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		/* int langID=UserSession.getCurrent().getLanguageId(); */

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL
				+ "=GenderWiseAdvocateListReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&OrgId="
				+ currentOrgId + "&GenderId=" + advGen + "&CourtId=" + courtIde; /* +"&LangIde="+langID */

	}

}
