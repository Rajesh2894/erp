package com.abm.mainet.tradeLicense.ui.controller;

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
import com.abm.mainet.tradeLicense.ui.model.TradeLicenseRegisterModel;

@Controller
@RequestMapping("/legacyLicenseBirtReport.html")
public class LegacyLicenseReportController extends AbstractFormController<TradeLicenseRegisterModel> {
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetLegacyLicenseReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=LicenseRegister_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=ListOfDefaulter_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagC)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=LicenseRenewalNotice_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=LicenseMIS_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		if (StringUtils.equals(ReportType, MainetConstants.FlagE)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=LicenseCancellation_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagF)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=MarketLicenseRenewal_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagH)) {
			return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL
					+ "=SummaryLicenseRegister_LegacyReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		return null;

	}
}
