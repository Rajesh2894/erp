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
import com.abm.mainet.property.ui.model.DetailCollectionRegisterModel;

@Controller
@RequestMapping("/DefaulterReport.html")
public class DefaulterRegisterReportController extends AbstractFormController<DetailCollectionRegisterModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		return index();
	}

	@RequestMapping(params = "GetDefaulterReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnAmount") Long mnAmount, @RequestParam("mntoAmount") Long mntoAmount,
			@RequestParam("mnDefaulterCount") Long mnDefaulterCount, final HttpServletRequest request) {
		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL + "=DefaulterRegisterReport.rptdesign&orgId=" + currentOrgId
				+ "&Zone=" + wardZone1 + "&Ward=" + wardZone2 + "&block=" + wardZone3 + "&route=" + wardZone4
				+ "&subroute=" + wardZone5 + "&FromAmount=" + mnAmount + "&ToAmount=" + mntoAmount + "&TopN="
				+ mnDefaulterCount;
	}

}
