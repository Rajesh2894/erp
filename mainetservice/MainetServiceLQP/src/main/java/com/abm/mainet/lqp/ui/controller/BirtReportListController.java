package com.abm.mainet.lqp.ui.controller;

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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.lqp.ui.model.BirtReportListModel;

@Controller
@RequestMapping("/lqpBirtReportList.html")
public class BirtReportListController extends AbstractFormController<BirtReportListModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "getLqpReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,

			final HttpServletRequest request) {

		int langIde = UserSession.getCurrent().getLanguageId();

		/* for English */
		if (langIde == 1) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=StatusOfRecievedQuestion.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

			} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=SummaryReportDepartmentWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagC)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=QuestionHouseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=QuestionAndAnswerWiseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagE)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=AnswerAndResponse.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			}

		}
		/* for regional */

		else if (langIde == 2) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=StatusOfRecievedQuestion_Regional.rptdesign&__locale=hi_IN&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

			} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=SummaryReportDepartmentWise_Regional.rptdesign&__locale=hi_IN&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

			} else if (StringUtils.equals(ReportType, MainetConstants.FlagC)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=QuestionHouseReport_Regional.rptdesign&__locale=hi_IN&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=QuestionAndAnswerWiseReport_Regional.rptdesign&__locale=hi_IN&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagE)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=AnswerAndResponse_Regional.rptdesign&__locale=hi_IN&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			}
		}

		return null;
	}

}