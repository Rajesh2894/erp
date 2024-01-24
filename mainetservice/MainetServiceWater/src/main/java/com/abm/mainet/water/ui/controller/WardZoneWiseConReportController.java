package com.abm.mainet.water.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.WardZoneWiseConReportModel;

/**
 * @author aarti.paan
 * @since 30/01/2019
 */
@Controller
@RequestMapping("/WardZoneWiseConnection.html")
public class WardZoneWiseConReportController extends AbstractFormController<WardZoneWiseConReportModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		WardZoneWiseConReportModel model = this.getModel();

		return index();
	}

	@RequestMapping(params = "GetWardZoneWiseDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewWardZonereportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("trmgrp") Long trmgrp, @RequestParam("trmgrp2") Long trmgrp2,
			@RequestParam("trmgrp3") Long trmgrp3, @RequestParam("trmgrp4") Long trmgrp4,
			@RequestParam("trmgrp4") Long trmgrp5,

			@RequestParam("CcnSize") Long CcnSize, @RequestParam("csMeteredccn") Long csMeteredccn,
			@RequestParam("csCcnfrom") Long csCcnfrom, @RequestParam("csCcnto") Long csCcnto,
			@RequestParam("csFromdt") Date csFromdt, @RequestParam("csTodt") Date csTodt,
			@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {
		// sessionCleanup(request);

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

		if (StringUtils.equals(ReportType, MainetConstants.FlagD) && error == null) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Detail_Demand_Register_between.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Fromdate=" + fromdt + "&Todate=" + todt + "&Tariff_Type="
					+ trmgrp + "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4
					+ "&Tariff_Type5=" + trmgrp5 + "&Connection_Size=" + CcnSize + "&MeterType=" + csMeteredccn
					+ "&ConnectionNoFrom=" + csCcnfrom + "&ConnectionNoTo=" + csCcnto;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Summary_Demand_Register.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4=" + wardZone4
					+ "&WWZ5=" + wardZone5 + "&Fromdate=" + fromdt + "&Todate=" + todt + "&Tariff_Type=" + trmgrp
					+ "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4
					+ "&Tariff_Type5=" + trmgrp5 + "&MeterType=" + csMeteredccn + "&Connection_Size=" + CcnSize;
		} else {
			return error;
		}
	}

	/* FOR XLSX */

	@RequestMapping(params = "GetWardZoneXLSX", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewXlsxDemandSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("trmgrp") Long trmgrp, @RequestParam("trmgrp2") Long trmgrp2,
			@RequestParam("trmgrp3") Long trmgrp3, @RequestParam("trmgrp4") Long trmgrp4,
			@RequestParam("trmgrp4") Long trmgrp5, @RequestParam("CcnSize") Long CcnSize,
			@RequestParam("csMeteredccn") Long csMeteredccn, @RequestParam("csCcnfrom") Long csCcnfrom,
			@RequestParam("csCcnto") Long csCcnto, @RequestParam("csFromdt") Date csFromdt,
			@RequestParam("csTodt") Date csTodt, @RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {
		// sessionCleanup(request);

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

		if (StringUtils.equals(ReportType, MainetConstants.FlagD) && error == null) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Detail_Demand_Register_between_WT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Fromdate=" + fromdt + "&Todate=" + todt + "&Tariff_Type="
					+ trmgrp + "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4
					+ "&Tariff_Type5=" + trmgrp5 + "&Connection_Size=" + CcnSize + "&MeterType=" + csMeteredccn
					+ "&ConnectionNoFrom=" + csCcnfrom + "&ConnectionNoTo=" + csCcnto;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Summary_Demand_Register_WT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Fromdate=" + fromdt + "&Todate=" + todt + "&Tariff_Type="
					+ trmgrp + "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4
					+ "&Tariff_Type5=" + trmgrp5 + "&MeterType=" + csMeteredccn + "&Connection_Size=" + CcnSize;
		} else {
			return error;
		}
	}

}
