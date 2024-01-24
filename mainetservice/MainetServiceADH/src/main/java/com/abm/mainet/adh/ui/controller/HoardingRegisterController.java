/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.ui.model.HoardingMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/HoardingRegister.html")
public class HoardingRegisterController extends AbstractFormController<HoardingMasterModel> {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {

		return index();
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(params = "hoarding", method = { RequestMethod.POST }) public
	 * ModelAndView HordingReport(final HttpServletRequest httpServletResquest) {
	 * return new
	 * ModelAndView("HoardingRegister_report",MainetConstants.FORM_NAME,this.
	 * getModel()); }
	 */

	@RequestMapping(params = "Gethoardingform", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewHoardingForm(@RequestParam("hoardingZone1") Long hoardingZone1,
			@RequestParam("hoardingZone2") Long hoardingZone2, @RequestParam("hoardingZone3") Long hoardingZone3,
			@RequestParam("hoardingZone4") Long hoardingZone4, @RequestParam("hoardingZone5") Long hoardingZone5,
			@RequestParam("hoardingType") Long hoardingType, @RequestParam("subhoardingType") Long subhoardingType,
			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=HoardingRegister.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&Adz1=" + hoardingZone1 + "&Adz2=" + hoardingZone2 + "&Adz3=" + hoardingZone3
				+ "&Adz4=" + hoardingZone4 + "&Adz5=" + hoardingZone5 + "&Hoardtype=" + hoardingType + "&HoardSubtype="
				+ subhoardingType;

	}

	/* ADH BIRT UAD Report */

	@RequestMapping(params = "getAdhReports", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getAdhReports(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("AdhBirtReportForm");

	}

	@RequestMapping(params = "GetReportform", method = { RequestMethod.POST })
	public @ResponseBody String viewReportSheet(@RequestParam("ReportType") String ReportType,

			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvertisementRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";

		} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvertisementPermitRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";

		} /*
			 * else if (StringUtils.equals(ReportType, MainetConstants.FlagC)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DcbRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";
			 * 
			 * } else if (StringUtils.equals(ReportType, MainetConstants.FlagD)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DemandRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";
			 * 
			 * } else if (StringUtils.equals(ReportType, MainetConstants.FlagE)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DemandNoticeRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";
			 * 
			 * }
			 */ 
		else if (StringUtils.equals(ReportType, MainetConstants.FlagF)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=HoardingRegister_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";

		} else if (StringUtils.equals(ReportType, MainetConstants.FlagL)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ListOfDefaulters_Report.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true";

		}

		return null;

	}

	/* ADH BIRT ULB Report */

	@RequestMapping(params = "getUlbAdhReports", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getUlbAdhReports(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("AdhUlbBirtReportForm");

	}

	@RequestMapping(params = "GetUlbReportform", method = { RequestMethod.POST })
	public @ResponseBody String viewReportUlbSheet(@RequestParam("ReportUlbType") String ReportUlbType,

			final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}

		if (StringUtils.equals(ReportUlbType, MainetConstants.FlagA)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvertisementRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
					+ currentOrgId;

		} else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagB)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvertisementPermitRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
					+ currentOrgId;

		} /*
			 * else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagC)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DcbRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
			 * + currentOrgId;
			 * 
			 * } else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagD)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DemandRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
			 * + currentOrgId;
			 * 
			 * } else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagE)) { return
			 * ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=DemandNoticeRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
			 * + currentOrgId;
			 * 
			 * }
			 */ 
		
		else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagF)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=HoardingRegister_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
					+ currentOrgId;

		} else if (StringUtils.equals(ReportUlbType, MainetConstants.FlagL)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ListOfDefaulters_UlbReport.rptdesign&__ExcelEmitter.DisableGrouping=true&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&Orgid="
					+ currentOrgId;

		}

		return null;

	}

}
