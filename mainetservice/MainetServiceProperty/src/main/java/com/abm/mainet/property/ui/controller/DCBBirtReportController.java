package com.abm.mainet.property.ui.controller;

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
import com.abm.mainet.property.ui.model.ProvisionalDetailDemandRegisterModel;

@Controller
@RequestMapping("/dcbBirtReport.html")
public class DCBBirtReportController extends AbstractFormController<ProvisionalDetailDemandRegisterModel> {

	@RequestMapping(method = { RequestMethod.POST }, params = "getZoneWiseDcb")
	public ModelAndView getZoneWiseDcb(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("ZoneWiseUsageDcbReport");

	}

	@RequestMapping(params = "GetZoneWiseReport", method = { RequestMethod.POST })
	public @ResponseBody String viewZoneWiseReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ZonewiseUsagewiseDCB.rptdesign&__title=&false&ULB="
					+ getOrgid();
		} else {
			return null;
		}

	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getPropertyUsageDcb")
	public ModelAndView getPropertyUsageDcb(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("PropertyUsageTypeDcbReport");

	}

	@RequestMapping(params = "GetPropertyReport", method = { RequestMethod.POST })
	public @ResponseBody String viewPropertyReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=PropertyUsagetype_wise_DCB.rptdesign&__title=&false&ULB=" + getOrgid();
		} else {
			return null;
		}

	}

	private Long getOrgid() {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		return currentOrgId;

	}
}
