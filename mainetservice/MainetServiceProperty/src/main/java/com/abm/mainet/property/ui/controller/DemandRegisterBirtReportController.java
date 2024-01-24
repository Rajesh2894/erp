package com.abm.mainet.property.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.ui.model.ProvisionalDetailDemandRegisterModel;

@Controller
@RequestMapping("/DemandBirtReport.html")
public class DemandRegisterBirtReportController extends AbstractFormController<ProvisionalDetailDemandRegisterModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	/* Provisional Demand Register Birt Report */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetDemandBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ProvisionalDemandRegisterBirtReport.rptdesign";
		} else {
			return null;
		}

	}

	/* Prefix Birt Report */
	@RequestMapping(params = "getPrefixReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getPrefixReport(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("PrefixBirtReportForm");

	}

	@RequestMapping(params = "getBirtPrefixform", method = { RequestMethod.POST })
	public @ResponseBody String viewReport(@RequestParam("prefixReport ") String prefixReport,
			final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}

		if (StringUtils.equals(prefixReport, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=PrefixBirtReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		return null;

	}

	/* Property Water Mismatch Report */

	@RequestMapping(params = "getMismatchReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getMismatchReport(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("PropertyWaterMismatchBirtForm");

	}

	@RequestMapping(params = "getBirtMimatchform", method = { RequestMethod.POST })
	public @ResponseBody String viewReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(mnFromdt);

		String error = null;
		int comparision1 = mnFromdt.compareTo((Date) ob[1]);
		int comparision2 = mnTodt.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}
		String fromdt = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		String todt = Utility.dateToString(mnTodt, "yyyy-MM-dd");

		if (error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=PropertyWaterMismatchReport.rptdesign&__ExcelEmitter.DisableGrouping=true&orgid="
					+ +currentOrgId + "&zone=" + wardZone1 + "&ward=" + wardZone2 + "&block=" + wardZone3 + "&route="
					+ +wardZone4 + "&subroute=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt;

		}

		return error;

	}
}
