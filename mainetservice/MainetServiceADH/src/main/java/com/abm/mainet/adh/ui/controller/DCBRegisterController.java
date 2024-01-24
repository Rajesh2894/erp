/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.service.ADHReportService;
import com.abm.mainet.adh.ui.model.DCBRegisterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/DCBRegister.html")
public class DCBRegisterController extends AbstractFormController<DCBRegisterModel> {
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	/*
	 * @Resource private ADHReportService adhReportService;
	 */

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {

		this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
				UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
		return index();
	}

	/*
	 * @RequestMapping(params = "report", method = { RequestMethod.GET,
	 * RequestMethod.POST }) public ModelAndView dcbReport(final HttpServletRequest
	 * httpServeltRequest) { return new
	 * ModelAndView("dcbRegister_report",MainetConstants.FORM_NAME,this.getModel());
	 * }
	 */

	@RequestMapping(params = "GetDcbform", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDCBReportForm(@RequestParam("hoardingZone1") Long hoardingZone1,
			@RequestParam("hoardingZone2") Long hoardingZone2, @RequestParam("hoardingZone3") Long hoardingZone3,
			@RequestParam("hoardingZone4") Long hoardingZone4, @RequestParam("hoardingZone5") Long hoardingZone5,
			@RequestParam("finalcialYear") Long finalcialYear,

			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=DcbRegisterReport.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&Adz1=" + hoardingZone1 + "&Adz2=" + hoardingZone2 + "&Adz3=" + hoardingZone3
				+ "&Adz4=" + hoardingZone4 + "&Adz5=" + hoardingZone5 + "&FinYearId=" + finalcialYear;

	}
}
