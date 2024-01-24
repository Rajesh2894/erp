package com.abm.mainet.care.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.ui.model.CareReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/CareReportForm.html")
public class CareReportController extends AbstractFormController<CareReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetCareReports", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		int langid = UserSession.getCurrent().getLanguageId();

		/* for English */

		if (langid == 1) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=DepartmentWiseBirtReport.rptdesign";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=Summary_Rp_Care.rptdesign";
			}
		}

		/* for Regional */
		else if (langid == 2) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=DepartmentWiseBirtReport_Regional.rptdesign";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=Summary_Rp_Care_Regional.rptdesign";
			}
		}
		return null;

	}

	/* LIS Disputed Land Survey report specific to dehardun environment */

	@RequestMapping(params = "getLISDisputedLandSurveyReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getLISDisputedLandSurveyReport(final HttpServletRequest httpServletRequest,
			final Model models) {
		sessionCleanup(httpServletRequest);

		return customResult("LISDisputedLandSurveyReport");
	}

	@RequestMapping(params = "getLandSurveyReport", method = { RequestMethod.POST })
	public @ResponseBody String viewLandSurveyReport(@RequestParam("ReportTypes") String ReportTypes,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportTypes, MainetConstants.FlagA)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=LandAndInpectionSurveyInformationReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportTypes, MainetConstants.FlagB)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=LandAndInpectionApplicantInformationReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportTypes, MainetConstants.FlagC)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=CaseDetailInformationReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		return null;

	}

}
