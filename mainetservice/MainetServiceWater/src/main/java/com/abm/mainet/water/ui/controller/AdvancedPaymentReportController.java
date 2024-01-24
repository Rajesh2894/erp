package com.abm.mainet.water.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.AdvancedPaymentReportModel;

@Controller
@RequestMapping("/advancePaymentReport.html")
public class AdvancedPaymentReportController extends AbstractFormController<AdvancedPaymentReportModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		AdvancedPaymentReportModel model = this.getModel();
		return index();
	}

	@RequestMapping(params = "GetAdvancedReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewClosedConnectionReportSheet(@RequestParam("csFromdt") Date csFromdt,
			@RequestParam("csTodt") Date csTodt, @RequestParam("ConnNoFrom") String ConnNoFrom,
			@RequestParam("ConnNoTo") String ConnNoTo, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(csFromdt);

		String error = null;
		int comparision1 = csFromdt.compareTo((Date) ob[1]);
		int comparision2 = csTodt.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}
		String fromdt = Utility.dateToString(csFromdt, "yyyy-MM-dd");
		String todt = Utility.dateToString(csTodt, "yyyy-MM-dd");

		if (error == null) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=AdvancedPaymentReport.rptdesign&__title=&false&OrgId="
					+ currentOrgId + "&FromDate=" + fromdt + "&ToDate=" + todt + "&ConnectionNoFrom=" + ConnNoFrom
					+ "&ConnectionNoTo=" + ConnNoTo;
		}
		return error;
	}

}
