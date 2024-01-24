package com.abm.mainet.legal.ui.controller;

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
import com.abm.mainet.legal.ui.model.DivisionDepartmnetUlbWiseReportUADModel;

@Controller
@RequestMapping("/UlbDivisionDepartment.html")

public class DivisionDepartmentUlbWiseReportULBController
		extends AbstractFormController<DivisionDepartmnetUlbWiseReportUADModel> {
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetDivisionULBReport", method = { RequestMethod.POST })
	public @ResponseBody String viewDivsionWiseReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DivisionWiseDepartmentWiseReport_Ulb.rptdesign&__title=&false&Orgid=" + currentOrgId;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DivisionWiseUlbWiseReport_Ulb.rptdesign&__title=&false&Orgid=" + currentOrgId;
		}

		else {
			return null;
		}

	}

}
