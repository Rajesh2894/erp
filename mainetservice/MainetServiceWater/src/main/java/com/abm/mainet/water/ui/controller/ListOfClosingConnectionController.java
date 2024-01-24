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
import com.abm.mainet.water.ui.model.ListOfClosingConnectionModel;

@Controller
@RequestMapping("/ListOfClosedConnection.html")
public class ListOfClosingConnectionController extends AbstractFormController<ListOfClosingConnectionModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);

		ListOfClosingConnectionModel model = this.getModel();
		return index();
	}

	@RequestMapping(params = "GetClosedConnectionDetails", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewClosedConnectionReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam(value = "trmgrp", required = false) Long trmgrp,
			@RequestParam(value = "trmgrp2", required = false) Long trmgrp2,
			@RequestParam(value = "trmgrp3", required = false) Long trmgrp3,
			@RequestParam(value = "trmgrp4", required = false) Long trmgrp4,
			@RequestParam(value = "trmgrp5", required = false) Long trmgrp5, @RequestParam("Disctype") Long Disctype,
			@RequestParam("csFromdt") Date csFromdt, @RequestParam("csTodt") Date csTodt,
			final HttpServletRequest request) {
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
			return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=ListOfClosedConnection.rptdesign&__title=&false&OrgId="
					+ currentOrgId + "&WWZ1=" + wardZone1 + "&WWZ2=" + wardZone2 + "&WWZ3=" + wardZone3 + "&WWZ4="
					+ wardZone4 + "&WWZ5=" + wardZone5 + "&Tarrif_Type=" + trmgrp + "&Tarrif_Type2=" + trmgrp2
					+ "&Tarrif_Type3=" + trmgrp3 + "&Tarrif_Type4=" + trmgrp4 + "&Tarrif_Type5=" + trmgrp5
					+ "&disconnectionType=" + Disctype + "&FromDate=" + fromdt + "&ToDate=" + todt;

		}
		return error;
	}

}
