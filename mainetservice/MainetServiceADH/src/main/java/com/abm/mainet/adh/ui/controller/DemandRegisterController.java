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

import com.abm.mainet.adh.ui.model.DemandRegisterModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/DemandRegister.html")
public class DemandRegisterController extends AbstractFormController<DemandRegisterModel> {

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {

		this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
				UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));

		return index();
	}

	@RequestMapping(params = "GetAdhDemand", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDemandForm(@RequestParam("hoardingZone1") Long hoardingZone1,
			@RequestParam("hoardingZone2") Long hoardingZone2, @RequestParam("hoardingZone3") Long hoardingZone3,
			@RequestParam("hoardingZone4") Long hoardingZone4, @RequestParam("hoardingZone5") Long hoardingZone5,
			@RequestParam("finalcialYear") Long finalcialYear,

			final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=DemandRegister.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&Adhwz1=" + hoardingZone1 + "&Adhwz2=" + hoardingZone2 + "&Adhwz3=" + hoardingZone3
				+ "&Adhwz4=" + hoardingZone4 + "&Adhwz5=" + hoardingZone5 + "&FinYearId=" + finalcialYear;

	}
}
