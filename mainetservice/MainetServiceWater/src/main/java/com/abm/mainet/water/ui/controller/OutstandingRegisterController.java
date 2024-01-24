package com.abm.mainet.water.ui.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.water.ui.model.OutstandingRegisterModel;

/**
 * @author aarti.paan
 * @since 25/02/2019
 */
@Controller
@RequestMapping("/waterOutstandingRegister.html")
public class OutstandingRegisterController extends AbstractFormController<OutstandingRegisterModel> {

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		OutstandingRegisterModel model = this.getModel();

		return index();
	}

	@RequestMapping(params = "GetOutstandingDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewOutstandingRegister(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("trmgrp") Long trmgrp, @RequestParam("trmgrp2") Long trmgrp2,
			@RequestParam("trmgrp3") Long trmgrp3, @RequestParam("trmgrp4") Long trmgrp4,
			@RequestParam("trmgrp5") Long trmgrp5, @RequestParam("CcnSize") Long CcnSize,
			@RequestParam("csMeteredccn") Long csMeteredccn, @RequestParam("csCcn") String csCcn,
			@RequestParam("csFromdt") Date csFromdt, @RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		String fromdt = Utility.dateToString(csFromdt, "yyyy-MM-dd");

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Detail_Outstanding_Register_between.rptdesign&__title=&false&OrgId=" + currentOrgId + "&WWZ1="
					+ wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4=" + wardZone4 + "&WWZ5="
					+ wardZone5 + "&Tariff_Type=" + trmgrp + "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3
					+ "&Tariff_Type4=" + trmgrp4 + "&Tariff_Type5=" + trmgrp5 + "&Connection_Size=" + CcnSize
					+ "&MeterTypes=" + csMeteredccn + "&ConnectionNo=" + csCcn + "&Fromdate=" + fromdt;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=Summary_Outstanding_Register.rptdesign&__title=&false&OrgId=" + currentOrgId + "&WWZ1="
					+ wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4=" + wardZone4 + "&WWZ5="
					+ wardZone5 + "&Tariff_Type=" + trmgrp + "&Tariff_Type2=" + trmgrp2 + "&Tariff_Type3=" + trmgrp3
					+ "&Tariff_Type4=" + trmgrp4 + "&Tariff_Type5=" + trmgrp5 + "&Connection_Size=" + CcnSize
					+ "&MeterTypes=" + csMeteredccn + "&Fromdate=" + fromdt;
		}
		return null;
	}

	/* Specific to skdcl */

	@RequestMapping(params = "getOutstandingDetailSummary", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getOutstandingDetailSummary(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("OutstandingDetailSummaryWaterReport");

	}

	@RequestMapping(params = "getOutstandingWaterReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewOutstandingReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("trmgrp") Long trmgrp, @RequestParam("trmgrp2") Long trmgrp2,
			@RequestParam("trmgrp3") Long trmgrp3, @RequestParam("trmgrp4") Long trmgrp4,
			@RequestParam("trmgrp5") Long trmgrp5, @RequestParam("CcnSize") Long CcnSize,
			@RequestParam("csMeteredccn") Long csMeteredccn, @RequestParam("ReportType") String ReportType,
			@RequestParam("csCcn") String csCcn, final HttpServletRequest request) {
		// sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=Detail_Outstanding_Register_between.rptdesign&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Tariff_Type=" + trmgrp + "&Tariff_Type2=" + trmgrp2
					+ "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4 + "&Tariff_Type5=" + trmgrp5
					+ "&Connection_Size=" + CcnSize + "&MeterTypes=" + csMeteredccn;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=Summary_Outstanding_Register.rptdesign&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Tariff_Type=" + trmgrp + "&Tariff_Type2=" + trmgrp2
					+ "&Tariff_Type3=" + trmgrp3 + "&Tariff_Type4=" + trmgrp4 + "&Tariff_Type5=" + trmgrp5
					+ "&Connection_Size=" + CcnSize + "&MeterTypes=" + csMeteredccn;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagC)) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=ConnectionNoWise_Outstanding_Register.rptdesign&OrgId="
					+ currentOrgId + "&ConnectionNo=" + csCcn;
		}

		return null;

	}
}
