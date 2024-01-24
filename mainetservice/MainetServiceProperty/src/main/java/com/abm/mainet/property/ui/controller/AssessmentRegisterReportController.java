package com.abm.mainet.property.ui.controller;

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

import com.abm.mainet.property.ui.model.AssessmentRegisterReportModel;

@Controller
@RequestMapping("/AssessmentRegister.html")
public class AssessmentRegisterReportController extends AbstractFormController<AssessmentRegisterReportModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		// AssessmentRegisterReportModel model = this.getModel();
		// Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return index();
	}

	@RequestMapping(params = "GetAssessmentRegister", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewAssementSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			@RequestParam("proertyNo") String proertyNo, @RequestParam("oldPid") String oldPid,
			final HttpServletRequest request) {
		sessionCleanup(request);

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
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=AssessmentRegister.rptdesign&OrgId=" + currentOrgId
					+ "&WZoneB1=" + wardZone1 + "&WZoneB2=" + wardZone2 + "&WZoneB3=" + wardZone3 + "&WZoneB4="
					+ wardZone4 + "&WZoneB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt
					+ "&PropertyNumber=" + proertyNo+"&OldPropertNo="+oldPid;
		}
		return error;
	}
}
