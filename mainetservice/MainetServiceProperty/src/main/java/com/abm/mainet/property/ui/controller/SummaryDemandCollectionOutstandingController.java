package com.abm.mainet.property.ui.controller;

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
import com.abm.mainet.property.ui.model.SummaryDemandCollectionOutstandingModel;

@Controller
@RequestMapping("/SummaryDemandCollectionOutstandingReport.html")
public class SummaryDemandCollectionOutstandingController
		extends AbstractFormController<SummaryDemandCollectionOutstandingModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		return index();
	}

	@RequestMapping(params = "GetSummaryReports", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewSummaryReportSheet(@RequestParam("zone") Long zone, @RequestParam("ward") Long ward,
			@RequestParam("block") Long block, @RequestParam("route") Long route,
			@RequestParam("subroute") Long subroute, final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
				+ "=Summary_Demand_Collection_Outstanding_Report.rptdesign&__title=&false&ORGID=" + currentOrgId
				+ "&ZONE=" + zone + "&WARD=" + ward + "&BLOCK=" + block + "&ROUTE=" + route + "&SUBROUTE=" + subroute;
	}

}