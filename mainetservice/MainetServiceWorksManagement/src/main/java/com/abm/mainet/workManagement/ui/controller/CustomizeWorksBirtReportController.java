package com.abm.mainet.workManagement.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.ui.model.CustomizeWorksBirtReportModel;

@Controller
@RequestMapping("/customizeWorksBirtReport.html")
public class CustomizeWorksBirtReportController extends AbstractFormController<CustomizeWorksBirtReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "getCustWorksBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}

		if (StringUtils.equals(ReportType, "A1")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_CompletionCertficateOfWorks.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_HindranceRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A3")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_LetterOfAcceptance_Above15CR.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "A4")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_LetterOfAcceptance_Below15CR.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "A5")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_WORKORDER.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A6")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_WORKORDERMaintainance.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A7")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_WORKORDERquotation.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A8")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_WorksAndBillSummary.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A9")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_WorksBillReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		return null;

	}
}
