package com.abm.mainet.property.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
import com.abm.mainet.property.ui.model.DetailDemandRegisterModel;

@Controller
@RequestMapping("/DetailDemandRegister.html")
public class DetailDemandRegisterController extends AbstractFormController<DetailDemandRegisterModel> {

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		DetailDemandRegisterModel model = this.getModel();
		/*
		 * FinancialYear finyear = iFinancialYearService.getFinanciaYearByDate(new
		 * Date()); if (finyear != null) { model.setMaxFinDate(finyear.getFaFromDate());
		 * }
		 */
		return index();
	}

	@RequestMapping(params = "GetDetailDemand", method = { RequestMethod.POST })
	public @ResponseBody String viewDetailDemandReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			@RequestParam("proertyNo") String proertyNo, @RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(mnFromdt);

		String error = null;
		int comparision1 = mnFromdt.compareTo((Date) ob[1]);
		int comparision2 = mnTodt.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}
		String fromdt = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		String todt = Utility.dateToString(mnTodt, "yyyy-MM-dd");

		if (StringUtils.equals(ReportType, MainetConstants.FlagD) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DetailDemandRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt + "&PropertyNo=" + proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=SummaryDemandRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt;
		}

		return error;
	}

	/* FOR XLSX */

	@RequestMapping(params = "GetDemandXLSX", method = { RequestMethod.POST })
	public @ResponseBody String viewDemandXLSXSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			@RequestParam("proertyNo") String proertyNo, @RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		Object[] ob = null;
		ob = iFinancialYearService.getFinacialYearByDate(mnFromdt);

		String error = null;
		int comparision1 = mnFromdt.compareTo((Date) ob[1]);
		int comparision2 = mnTodt.compareTo((Date) ob[2]);
		if (comparision1 == -1 || comparision2 == 1) {
			error = "f";
		}
		String fromdt = Utility.dateToString(mnFromdt, "yyyy-MM-dd");
		String todt = Utility.dateToString(mnTodt, "yyyy-MM-dd");

		if (StringUtils.equals(ReportType, MainetConstants.FlagD) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DetailDemandRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt + "&PropertyNo="
					+ proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=SummaryDemandRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt;
		}

		return error;
	}
}
