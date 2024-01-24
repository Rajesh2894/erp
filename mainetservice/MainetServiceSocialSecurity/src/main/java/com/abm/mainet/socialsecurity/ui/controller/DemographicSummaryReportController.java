package com.abm.mainet.socialsecurity.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.LookUp;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.socialsecurity.service.IPensionSchemeMasterService;
import com.abm.mainet.socialsecurity.ui.model.DemographicSummaryReportModel;

@Controller
@RequestMapping("/demographicsReport.html")
public class DemographicSummaryReportController extends AbstractFormController<DemographicSummaryReportModel> {

	@Autowired
	private ServiceMasterService iServiceMasterService;

	@Autowired
	TbDepartmentService tbDepartmentService;

	@Autowired
	private IPensionSchemeMasterService iPensionSchemeMasterService;

	@Autowired
	private IFinancialYearService iFinancialYearService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		DemographicSummaryReportModel model = this.getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long DepartId = tbDepartmentService
				.getDepartmentIdByDeptCode(MainetConstants.SocialSecurity.DEPARTMENT_SORT_CODE);
		int langId = UserSession.getCurrent().getLanguageId();

		final com.abm.mainet.common.utility.LookUp lookUpFieldstatus = CommonMasterUtility
				.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A, PrefixConstants.ACN, langId, org);
		final Long activeStatusId = lookUpFieldstatus.getLookUpId();
		model.setViewList(iPensionSchemeMasterService.getAllData(currentOrgId, DepartId, activeStatusId));
		model.setServiceList(iServiceMasterService.findAllActiveServicesWhichIsNotActual(currentOrgId, DepartId,
				activeStatusId, "N"));

		return index();
	}

	@RequestMapping(params = "GetDemographicReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDemographicForm(@RequestParam("pSchedule") Long pSchedule,
			@RequestParam("pSchemeName") Long pSchemeName, @RequestParam("pFrmDate") Date pFrmDate,
			@RequestParam("pToDate") Date pToDate, final HttpServletRequest request) {
		sessionCleanup(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		/*
		 * Object[] ob = null; ob =
		 * iFinancialYearService.getFinacialYearByDate(pFrmDate);
		 * 
		 * String error = null; int comparision1 = pFrmDate.compareTo((Date) ob[1]); int
		 * comparision2 = pToDate.compareTo((Date) ob[2]); if (comparision1 == -1 ||
		 * comparision2 == 1) { error = "f"; }
		 */
		String fromdt = Utility.dateToString(pFrmDate, "yyyy-MM-dd");
		String todt = Utility.dateToString(pToDate, "yyyy-MM-dd");

		/* for english and regional passing language id */

		if (langId == 1) {
			return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=DemographicSummaryReport.rptdesign&OrgId="
					+ currentOrgId + "&PenSchedule=" + pSchedule + "&PenSchemeName=" + pSchemeName + "&FromDate="
					+ fromdt + "&ToDate=" + todt;
		} else {
			return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL + "=DemographicSummaryReport_Regional.rptdesign&OrgId="
					+ currentOrgId + "&PenSchedule=" + pSchedule + "&PenSchemeName=" + pSchemeName + "&FromDate="
					+ fromdt + "&ToDate=" + todt;
		}
	}
}
