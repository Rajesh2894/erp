package com.abm.mainet.property.ui.controller;

import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.ui.model.DetailOutstandingRegisterModel;

@Controller
@RequestMapping("/DetailOutstandingRegister.html")
public class DetailOutstandingRegisterController extends AbstractFormController<DetailOutstandingRegisterModel> {

	@Autowired
	private TbFinancialyearService tbFinancialYearService;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		/*
		 * FinancialYear fineYear = iFinancialYearService.getFinanciaYearByDate(new
		 * Date()); if (fineYear != null) {
		 * getModel().setMaxFinDate(fineYear.getFaFromDate()); }
		 */
		return index();
	}

	@RequestMapping(params = "GetDetailOutstanding", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDetailOutstandingReportSheet(@RequestParam("wardZone1") Long wardZone1,
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
					+ "=DetailOutstandingRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt + "&PropertyNo=" + proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=SummaryOutstandingRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt;
		}
		return error;

	}

	/* for XLSX */
	@RequestMapping(params = "GetOutstandingXLSX", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewOutstandingXlsxReportSheet(@RequestParam("wardZone1") Long wardZone1,
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
					+ "=DetailOutstandingRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt + "&PropertyNo="
					+ proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=SummaryOutstandingRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FromDate=" + fromdt + "&ToDate=" + todt;
		}
		return error;

	}

	/************************** Specific to skdcl *********************************/

	@RequestMapping(params = "getOutstandingPropertyReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getOutstandingPropertyReport(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		DetailOutstandingRegisterModel model = this.getModel();
		model.setListOfinalcialyear(tbFinancialYearService.getAllFinincialYear());

		return customResult("OutstandingPropertyReport");

	}

	@RequestMapping(params = "getOutstandingReport", method = { RequestMethod.POST })
	public @ResponseBody String viewDcbReport(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("proertyNo") String proertyNo, @RequestParam("finyear") Long finyear,
			@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=Outstanding_DetailReport.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FinancialYear=" + finyear + "&PropertyNo=" + proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=Outstanding_SummaryReport.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId
					+ "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4=" + wardZone4
					+ "&WZB5=" + wardZone5 + "&FinancialYear=" + finyear;
		}

		return null;
	}

	/* for XLSX */
	@RequestMapping(params = "getXLSXReportOutstanding", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewOutstandingXlsxReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("proertyNo") String proertyNo, @RequestParam("finyear") Long finyear,
			@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {
		sessionCleanup(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=Outstanding_DetailReport_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FinancialYear=" + finyear + "&PropertyNo=" + proertyNo;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagS)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=Outstanding_SummaryReport_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId="
					+ currentOrgId + "&WZB1=" + wardZone1 + "&WZB2=" + wardZone2 + "&WZB3=" + wardZone3 + "&WZB4="
					+ wardZone4 + "&WZB5=" + wardZone5 + "&FinancialYear=" + finyear;
		}

		return null;

	}
}
