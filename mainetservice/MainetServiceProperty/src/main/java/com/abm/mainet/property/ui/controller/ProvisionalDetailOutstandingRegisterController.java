package com.abm.mainet.property.ui.controller;

import java.util.Date;

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
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.ui.model.ProvisionalDetailOutstandingRegisterModel;

@Controller
@RequestMapping("/ProvisionalDetailOutstanding.html")
public class ProvisionalDetailOutstandingRegisterController
		extends AbstractFormController<ProvisionalDetailOutstandingRegisterModel> {



	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		return index();
	}

	@RequestMapping(params = "GetProvisionalOutstanding", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String vieWOutstandingReportSheet(@RequestParam("zoneLevel") Long zoneLevel,
			@RequestParam("wardLevel") Long wardLevel, @RequestParam("mnFromdt") Date mnFromdt,
			@RequestParam("mnTodt") Date mnTodt, final HttpServletRequest request) {
		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String fromdatet = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		String todate = Utility.dateToString(mnTodt, "yyyy-MM-dd");

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=ProvisionalDetailOutstandingRegister.rptdesign&OrgId="
				+ currentOrgId + "&Zone=" + zoneLevel + "&Ward=" + wardLevel + "&FromDate=" + fromdatet + "&ToDate="
				+ todate;

	}
}
