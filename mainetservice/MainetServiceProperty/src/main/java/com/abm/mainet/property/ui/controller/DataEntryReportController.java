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
import com.abm.mainet.property.ui.model.DataEntryReportModel;

@Controller
@RequestMapping("/PropDataEntryVerification.html")
public class DataEntryReportController extends AbstractFormController<DataEntryReportModel> {
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetEntryReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDataEntrySheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			final HttpServletRequest request) {
		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		/*
		 * return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
		 * "=Data_Entry_Verification_report.rptdesign&ExcelEmitter.SingleSheet&__ExcelEmitter.SingleSheetWithPageBreaks=true&ULBName="
		 * + currentOrgId + "&Ward-Zone-Level-1=" + wardZone1 + "&Ward-Zone-Level-2=" +
		 * wardZone2 + "&Ward-Zone-Level-3=" + wardZone3 + "&Ward-Zone-Level-4=" +
		 * wardZone4 + "&Ward-Zone-Level-5=" + wardZone5;
		 */

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
				+ "=Data_Entry_Verification_report.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&ULBName="
				+ currentOrgId + "&Ward-Zone-Level-1=" + wardZone1 + "&Ward-Zone-Level-2=" + wardZone2
				+ "&Ward-Zone-Level-3=" + wardZone3 + "&Ward-Zone-Level-4=" + wardZone4 + "&Ward-Zone-Level-5="
				+ wardZone5;

	}

}
