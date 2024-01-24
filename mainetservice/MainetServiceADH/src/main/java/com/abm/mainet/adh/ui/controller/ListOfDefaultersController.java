/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.ui.model.ListOfDefaultersModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/ListOfDefaulters.html")
public class ListOfDefaultersController extends AbstractFormController<ListOfDefaultersModel> {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);

		return index();
	}

	@RequestMapping(params = "GetDefaulter", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDeafulterform(@RequestParam("hoardingZone1") Long hoardingZone1,
			@RequestParam("hoardingZone2") Long hoardingZone2, @RequestParam("hoardingZone3") Long hoardingZone3,
			@RequestParam("hoardingZone4") Long hoardingZone4, @RequestParam("hoardingZone5") Long hoardingZone5,
			@RequestParam("hoardingType") Long hoardingType, @RequestParam("subhoardingType") Long subhoardingType,
			@RequestParam("tillDueDate") Date tillDueDate, final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String duedate = Utility.dateToString(tillDueDate, "yyyy-MM-dd");

		return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=ListOfDefaulters.rptdesign&__title=&false&OrgId="
				+ currentOrgId + "&Adz1=" + hoardingZone1 + "&Adz2=" + hoardingZone2 + "&Adz3=" + hoardingZone3
				+ "&Adz4=" + hoardingZone4 + "&Adz5=" + hoardingZone5 + "&Hoardtype=" + hoardingType + "&HoardSubtype="
				+ subhoardingType + "&TillDueDate=" + duedate;

	}

}
