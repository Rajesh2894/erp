package com.abm.mainet.mrm.ui.controller;

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
import com.abm.mainet.mrm.ui.model.MarriageDetailSummaryReportModel;

@Controller
@RequestMapping("/marriageDetailSummaryBirtReportList.html")
public class MarriageDetailSummaryReportController extends AbstractFormController<MarriageDetailSummaryReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "getMrmDetailSummaryReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewMrmBirtReport(@RequestParam("ReportType") String ReportType,

			final HttpServletRequest request) {

	int langId = UserSession.getCurrent().getLanguageId();
		/* for english mode */

		if (langId == 1) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=MarriageDetailReport_English.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=MarriageSummaryReport_English.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

			}
		}
		/* for regional mode */
		else if (langId == 2) {
			if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=MarriageDetailReport_Regional.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
			} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=MarriageSummaryReport_Regional.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";

			}
		}

		return null;
	}

}
