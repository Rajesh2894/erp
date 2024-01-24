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
import com.abm.mainet.common.ui.controller.AbstractFormController;

import com.abm.mainet.legal.ui.model.DivisionDepartmnetUlbWiseReportUADModel;

@Controller
@RequestMapping("/divisionDepartmentUad.html")

public class DivisionDepartmentUlbWiseReportUADController extends AbstractFormController<DivisionDepartmnetUlbWiseReportUADModel> {
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetDivisionUadReport", method = { RequestMethod.POST })
	public @ResponseBody String viewDivsionWiseReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DivisionWiseDepartmentWiseReport_Uad.rptdesign&__title=&false";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DivisionWiseUlbWiseReport_Uad.rptdesign&__title=&false";
		}

		else {
			return null;
		}

	}

}
