package com.abm.mainet.rti.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
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
import com.abm.mainet.rti.ui.model.RtiRegisterBirtReportModel;

@Controller
@RequestMapping("/rtiRegisterBirtReport.html")
public class RtiRegisterBirtReportController extends AbstractFormController<RtiRegisterBirtReportModel> {
	/*
	 * Rti Register ULb Report FlagD->RtiRegisterUlbReport
	 * FlagP->RtiPendingUlbReport
	 * 
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = "getRtiUlbRegister")
	public ModelAndView getRtiUlbRegister(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("RtiRegisterUlbReport");

	}

	@RequestMapping(params = "GetRTIRegsiterUlbReport", method = { RequestMethod.POST })
	public @ResponseBody String viewRegisterUlbReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RtiRegisterUlbReport.rptdesign&__title=&false&ULB="
					+ getOrgid();
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagP)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RtiPendingUlbReport.rptdesign&__title=&false&ULB="
					+ getOrgid();
		}
		return null;
	}

	private Long getOrgid() {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		return currentOrgId;

	}

	/*
	 * Rti Register UAD reports FlagD->RtiRegisterUadReport
	 * FlagP->RtiPendingUadReport FlagS->RtiSummaryReport_UAD
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = "getRtiUadReport")
	public ModelAndView getRtiUadRegister(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("RtiRegisterUadReport");

	}

	@RequestMapping(params = "GetRTIRegsiterUadReport", method = { RequestMethod.POST })
	public @ResponseBody String viewRegisterUadReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RtiRegisterUadReport.rptdesign&__title=&false";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagP)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RtiPendingUadReport.rptdesign&__title=&false";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=RtiSummaryReport_UAD.rptdesign&__title=&false";
		}
		return null;

	}

}
