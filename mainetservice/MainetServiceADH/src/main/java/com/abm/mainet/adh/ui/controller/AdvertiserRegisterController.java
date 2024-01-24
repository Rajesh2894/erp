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

import com.abm.mainet.adh.ui.model.AdvertiserMasterModel;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Anwarul.Hassan
 * @since 18-Sep-2019
 */
@Controller
@RequestMapping("/AdvertiserRegister.html")
public class AdvertiserRegisterController extends AbstractFormController<AdvertiserMasterModel> {
	/*
	 * @Resource private ADHReportService adhReportService;
	 */
	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		// load the sli date.
		// bind it to the dto.

		return index();
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(params = "report", method = { RequestMethod.POST }) public
	 * ModelAndView report(final HttpServletRequest httpServletRequest,@RequestParam
	 * String fromDate,@RequestParam String toDate) {
	 * 
	 * AdvertiserMasterDto advertiserMasterDto; advertiserMasterDto =
	 * adhReportService.findAdvertiserRegister(fromDate, toDate,
	 * UserSession.getCurrent().getOrganisation().getOrgid());
	 * advertiserMasterDto.setAgencyLicenseFromDate(fromDate);
	 * advertiserMasterDto.setAgencyLicenseToDate(toDate);this.getModel().
	 * setFromDate(Utility.stringToDate(fromDate, "dd/MM/yyyy"));
	 * this.getModel().setToDate(Utility.stringToDate(toDate,"dd/MM/yyyy"));
	 * this.getModel().setAdvertiserMasterDtoList(advertiserMasterDto.
	 * getAdvertiserMasterDtoList()); return new
	 * ModelAndView("advertiserRegisterReport",MainetConstants.FORM_NAME,this.
	 * getModel()); }
	 */

	@RequestMapping(params = "GetAdvertiserReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewAdveritserReportform(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, final HttpServletRequest request) {
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
		String fromdt = Utility.dateToString(fromDate, "yyyy-MM-dd");
		String todt = Utility.dateToString(toDate, "yyyy-MM-dd");

		if (error == null) {
			return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL
					+ "=AdvertisementPermitRegister.rptdesign&__title=&false&OrgId=" + currentOrgId + "&FromDate="
					+ fromdt + "&ToDate=" + todt;

		}
		return error;
	}

}
