/**
 * 
 */
package com.abm.mainet.adh.ui.controller;

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

import com.abm.mainet.adh.ui.model.AdvertisementsRegisterModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 19-Sep-2019
 */
@Controller
@RequestMapping("/AdvertisementsRegister.html")
public class AdvertisementsRegisterController extends AbstractFormController<AdvertisementsRegisterModel> {
	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);

		return index();
	}

	@RequestMapping(params = "GetAdvReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewReportSheet(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, @RequestParam("licstatus") String licstatus,
			@RequestParam("lictype") Long lictype, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(fromDate);
		String error = null;
		int comparision1 = fromDate.compareTo((Date) ob[1]);
		int comparision2 = toDate.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}
		String advfromdt = Utility.dateToString(fromDate, "yyyy-MM-dd");
		String advtodt = Utility.dateToString(toDate, "yyyy-MM-dd");

		if (error == null) {
			return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=AdvertisementRegister.rptdesign&OrgId="
					+ currentOrgId + "&FromDate=" + advfromdt + "&ToDate=" + advtodt + "&LicenseStatus=" + licstatus
					+ "&LicenseType=" + lictype;

		}
		return error;
	}
}
