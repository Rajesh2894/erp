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
import com.abm.mainet.water.ui.model.ConnectionWiseDefaulterReportModel;

@Controller
@RequestMapping("/defaulterReport.html")
public class ConnectionWiseDefaulterReportController
		extends AbstractFormController<ConnectionWiseDefaulterReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		ConnectionWiseDefaulterReportModel model = this.getModel();
	return index();
	}

	@RequestMapping(params = "GetdefaulterReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(
			@RequestParam(value = "WardZoneLevel1", required = false) Long WardZoneLevel1,
			@RequestParam(value = "WardZoneLevel2", required = false) Long WardZoneLevel2,
			@RequestParam(value = "WardZoneLevel3", required = false) Long WardZoneLevel3,
			@RequestParam(value = "WardZoneLevel4", required = false) Long WardZoneLevel4,
			@RequestParam(value = "WardZoneLevel5", required = false) Long WardZoneLevel5,
			@RequestParam(value = "tarrifType", required = false) Long tarrifType,
			@RequestParam(value = "tarrifType2", required = false) Long tarrifType2,
			@RequestParam(value = "tarrifType3", required = false) Long tarrifType3,
			@RequestParam(value = "tarrifType4", required = false) Long tarrifType4,
			@RequestParam(value = "tarrifType5", required = false) Long tarrifType5,
			@RequestParam("csFromAmnt") String csFromAmnt, @RequestParam("csToAmnt") String csToAmnt,
			@RequestParam("ConnNoFrom") String ConnNoFrom, @RequestParam("ConnNoTo") String ConnNoTo,
			@RequestParam("defaulterCount") Long defaulterCount, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=DefaulterReportConnectionWise.rptdesign&OrgId="
				+ currentOrgId + "&WardZoneLevel1=" + WardZoneLevel1 + "&WardZoneLevel2=" + WardZoneLevel2
				+ "&WardZoneLevel3=" + WardZoneLevel3 + "&WardZoneLevel4=" + WardZoneLevel4 + "&WardZoneLevel5="
				+ WardZoneLevel5 + "&FromAmount=" + csFromAmnt + "&ToAmount=" + csToAmnt + "&TariffCategory="
				+ tarrifType + "&TariffCategory2=" + tarrifType2 + "&TariffCategory3=" + tarrifType3
				+ "&TariffCategory4=" + tarrifType4 + "&TariffCategory5=" + tarrifType5 + "&ConnectionNoFrom="
				+ ConnNoFrom + "&ConnectionNoTo=" + ConnNoTo + "&DefaulterCounter=" + defaulterCount;

	}

}
