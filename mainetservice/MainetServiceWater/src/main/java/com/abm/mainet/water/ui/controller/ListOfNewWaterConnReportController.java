package com.abm.mainet.water.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.abm.mainet.water.ui.model.ListOfNewWaterConnReportModel;

@Controller
@RequestMapping("/ListOfNewWaterConnReport.html")

public class ListOfNewWaterConnReportController extends AbstractFormController<ListOfNewWaterConnReportModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		ListOfNewWaterConnReportModel model = this.getModel();
		return index();
	}

	@RequestMapping(params = "GetNewConnection", method = { RequestMethod.POST, RequestMethod.GET })
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
			@RequestParam("csFromdt") Date csFromdt, @RequestParam("csTodt") Date csTodt,
			@RequestParam("ConnType") Long ConnType, @RequestParam("MeteredType") Long MeteredType,
			@RequestParam("CcnSize") Long CcnSize, final HttpServletRequest request) {
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
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=ListOfNewWaterConnection.rptdesign&OrgId=" + currentOrgId
					+ "&WardZoneLevel1=" + WardZoneLevel1 + "&WardZoneLevel2=" + WardZoneLevel2 + "&WardZoneLevel3="
					+ WardZoneLevel3 + "&WardZoneLevel4=" + WardZoneLevel4 + "&WardZoneLevel5=" + WardZoneLevel5
					+ "&FromDate=" + fromdt + "&ToDate=" + todt + "&TariffCategory=" + tarrifType + "&TariffCategory2="
					+ tarrifType2 + "&TariffCategory3=" + tarrifType3 + "&TariffCategory4=" + tarrifType4
					+ "&TariffCategory5=" + tarrifType5 + "&FromDate=" + fromdt + "&ToDate=" + todt + "&ConnectionType="
					+ ConnType + "&MeterNonMeterType=" + MeteredType + "&ConnectionSize=" + CcnSize;

		}
		return error;
	}
}