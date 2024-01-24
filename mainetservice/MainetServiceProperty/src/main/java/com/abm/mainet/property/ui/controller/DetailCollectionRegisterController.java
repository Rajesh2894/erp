package com.abm.mainet.property.ui.controller;

import java.util.Date;
import java.util.List;
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
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.service.DataEntrySuiteService;
import com.abm.mainet.property.ui.model.DetailCollectionRegisterModel;

@Controller
@RequestMapping("/DetailCollectionRegister.html")
public class DetailCollectionRegisterController extends AbstractFormController<DetailCollectionRegisterModel> {
	@Autowired
	private DataEntrySuiteService dataEntrySuiteService;
	
	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {

		Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.PROPERTY);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<LookUp> taxList = dataEntrySuiteService.getTaxCollectorList(deptId, currentOrgId);
		getModel().setTaxWisweList(taxList);

		return index();
	}

	@RequestMapping(params = "GetDetailCollection", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDetailCollectionReportSheet(@RequestParam("wardZone1") Long wardZone1,
			@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
			@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
			@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
			@RequestParam("taxCollectorId") Long taxCollectorId, @RequestParam("propNo") String propNo,
			@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {
		sessionCleanup(request);

		/*
		 * DetailCollectionRegisterModel model=this.getModel();
		 */
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
					+ "=DetailCollectionRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId + "&Zone=" + wardZone1
					+ "&Ward=" + wardZone2 + "&Block=" + wardZone3 + "&Route=" + wardZone4 + "&SubRoute=" + wardZone5
					+ "&FromDate=" + fromdt + "&ToDate=" + todt + "&TaxCollectorWise=" + taxCollectorId + "&PropertyNo="
					+ propNo;
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=SummaryCollectionRegister.rptdesign&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId + "&Zone=" + wardZone1
					+ "&Ward=" + wardZone2 + "&Block=" + wardZone3 + "&Route=" + wardZone4 + "&SubRoute=" + wardZone5
					+ "&FromDate=" + fromdt + "&ToDate=" + todt + "&TaxCollectorWise=" + taxCollectorId;
		} else {
			return error;
		}

	}




/*FOR XLSX*/

@RequestMapping(params = "GetCollectionXLSX", method = { RequestMethod.POST, RequestMethod.GET })
public @ResponseBody String viewDetailXLSXReportSheet(@RequestParam("wardZone1") Long wardZone1,
		@RequestParam("wardZone2") Long wardZone2, @RequestParam("wardZone3") Long wardZone3,
		@RequestParam("wardZone4") Long wardZone4, @RequestParam("wardZone5") Long wardZone5,
		@RequestParam("mnFromdt") Date mnFromdt, @RequestParam("mnTodt") Date mnTodt,
		@RequestParam("taxCollectorId") Long taxCollectorId, @RequestParam("propNo") String propNo,
		@RequestParam("ReportType") String ReportType, final HttpServletRequest request) {
	sessionCleanup(request);

	/*
	 * DetailCollectionRegisterModel model=this.getModel();
	 */
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
				+ "=DetailCollectionRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId + "&Zone=" + wardZone1
				+ "&Ward=" + wardZone2 + "&Block=" + wardZone3 + "&Route=" + wardZone4 + "&SubRoute=" + wardZone5
				+ "&FromDate=" + fromdt + "&ToDate=" + todt + "&TaxCollectorWise=" + taxCollectorId + "&PropertyNo="
				+ propNo;
	}
	if (StringUtils.equals(ReportType, MainetConstants.FlagS) && error == null) {
		return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
				+ "=SummaryCollectionRegister_PT_XLSX.rptdesign&__format=xlsx&__ExcelEmitter.DisableGrouping=true&OrgId=" + currentOrgId + "&Zone=" + wardZone1
				+ "&Ward=" + wardZone2 + "&Block=" + wardZone3 + "&Route=" + wardZone4 + "&SubRoute=" + wardZone5
				+ "&FromDate=" + fromdt + "&ToDate=" + todt + "&TaxCollectorWise=" + taxCollectorId;
	} else {
		return error;
	}

}
}