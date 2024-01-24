package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.MeterReadingReportModel;

@Controller
@RequestMapping("/meterReading.html")
public class MeterReadingReportController extends AbstractFormController<MeterReadingReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		return index();
	}

	@RequestMapping(params = "GetMeterReadingReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewClosedConnectionReportSheet(@RequestParam("ConnNoFrom") String ConnNoFrom,
			@RequestParam("ConnNoTo") String ConnNoTo, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=MeterReadingReport.rptdesign&OrgId=" + currentOrgId
				+ "&ConnectionNoFrom=" + ConnNoFrom + "&ConnectionNoTo=" + ConnNoTo;
	}
}
