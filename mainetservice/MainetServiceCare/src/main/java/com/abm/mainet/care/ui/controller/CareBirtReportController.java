package com.abm.mainet.care.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.ui.model.CareReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/CareReportBirtForm.html")
public class CareBirtReportController extends AbstractFormController<CareReportModel> {

	/* FORMAT_1 Department Wise Complaint Report */
	@RequestMapping(method = { RequestMethod.POST }, params = "getDeptStaticsWise")
	public ModelAndView getDeptStaticsWise(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("DepartmentWiseStatisticReport");

	}

	@RequestMapping(params = "getDeptCareReports", method = { RequestMethod.POST })
	public @ResponseBody String viewDeptReport(@RequestParam("reportDeptName") String reportDeptName,
			@RequestParam("reportServiceDept") String reportServiceDept, final HttpServletRequest request) {

		/* for Detail Type */

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}

		if (StringUtils.equals(reportDeptName, MainetConstants.FlagD)) {
			if (StringUtils.equals(reportServiceDept, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentWiseComplaintDetailReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}
			if (StringUtils.equals(reportServiceDept, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentWiseComplaintDetailReport_ComplaintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		/* for summary Type */
		else if (StringUtils.equals(reportDeptName, MainetConstants.FlagS)) {

			if (StringUtils.equals(reportServiceDept, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentWiseComplaintSummaryReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}

			if (StringUtils.equals(reportServiceDept, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentWiseComplaintSummaryReport_ComplaintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		return null;

	}

	/* FORMAT_2 Ward Karalayalaya wise report */
	@RequestMapping(method = { RequestMethod.POST }, params = "getOrganisationStaticsWise")
	public ModelAndView getOrganisationStaticsWise(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("OrganisationWardKarayalaReport");

	}

	@RequestMapping(params = "getUlbCareReports", method = { RequestMethod.POST })
	public @ResponseBody String viewUlbReport(@RequestParam("reportUlbName") String reportUlbName,
			@RequestParam("reportServiceUlb") String reportServiceUlb, final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		/* for Detail Type */

		if (StringUtils.equals(reportUlbName, MainetConstants.FlagD)) {
			if (StringUtils.equals(reportServiceUlb, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=WardKaralayalayaWiseDetailReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}
			if (StringUtils.equals(reportServiceUlb, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=WardKaralayalayaWiseDetailReports_ComplaintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		/* for summary Type */
		else if (StringUtils.equals(reportUlbName, MainetConstants.FlagS)) {

			if (StringUtils.equals(reportServiceUlb, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=WardKaryalayaWiseSummaryReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}

			if (StringUtils.equals(reportServiceUlb, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=WardKaryalayaWiseSummaryReport_ComplaintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		return null;

	}

	/* FORMAT_3 District Wise Organisation Statistics Report */
	@RequestMapping(method = { RequestMethod.POST }, params = "getDistrictStaticsWise")
	public ModelAndView getDistrictStaticsWise(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("DistrictStatisticReport");

	}

	@RequestMapping(params = "getDistCareReports", method = { RequestMethod.POST })
	public @ResponseBody String viewDistrictReport(@RequestParam("reportDisName") String reportDisName,
			@RequestParam("reportServiceDist") String reportServiceDist, final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		/* for Detail Type */

		if (StringUtils.equals(reportDisName, MainetConstants.FlagD)) {
			if (StringUtils.equals(reportServiceDist, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DistrictWiseStatisticDetailReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}
			if (StringUtils.equals(reportServiceDist, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DistrictWiseStatisticDetailReport_ComplaintRequest.rptdesign&Orgid=" + currentOrgId;
			}

		}
		/* for summary Type */
		else if (StringUtils.equals(reportDisName, MainetConstants.FlagS)) {

			if (StringUtils.equals(reportServiceDist, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DistrictWiseStatisticSummaryReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}

			if (StringUtils.equals(reportServiceDist, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DistrictWiseStatisticSummaryReport_ComplaintRequest.rptdesign&Orgid=" + currentOrgId;
			}

		}
		return null;

	}

	/*
	 * FORMAT_4 Department Compliant Type report
	 */

	@RequestMapping(method = { RequestMethod.POST }, params = "getDeptWiseComplaintWise")

	public ModelAndView getDeptWiseComplaintWise(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("DepartmentWiseComplaintWiseReport");

	}

	@RequestMapping(params = "getComplaintForm", method = { RequestMethod.POST })
	public @ResponseBody String viewComplaintForm(@RequestParam("deptComplaintName") String deptComplaintName,
			@RequestParam("reportServiceComp") String reportServiceComp, final HttpServletRequest request) {

		/* for Detail Type */

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		if (StringUtils.equals(deptComplaintName, MainetConstants.FlagD)) {
			if (StringUtils.equals(reportServiceComp, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentComplaintWiseDetailReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}
			if (StringUtils.equals(reportServiceComp, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentComplaintWiseDetailReport_ComplaintRequest.rptdesign&Orgid=" + currentOrgId;
			}

		}
		/* for summary Type */
		else if (StringUtils.equals(deptComplaintName, MainetConstants.FlagS)) {

			if (StringUtils.equals(reportServiceComp, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentComplaintWiseSummaryReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}

			if (StringUtils.equals(reportServiceComp, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=DepartmentComplaintWiseSummaryReport_ComplaintRequest.rptdesign&Orgid=" + currentOrgId;
			}

		}
		return null;

	}

	/* FORMAT_5 Corporation Wise Ward Karyalaya Wise Report */

	@RequestMapping(method = { RequestMethod.POST }, params = "getCorporationWardWise")
	public ModelAndView getCorporationWardWise(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("CorporationWardWiseReport");

	}

	@RequestMapping(params = "getCorporationReports", method = { RequestMethod.POST })
	public @ResponseBody String viewCorporationReport(@RequestParam("corporationName") String corporationName,
			@RequestParam("corporationServiceUlb") String corporationServiceUlb, final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		/* for Detail Type */

		if (StringUtils.equals(corporationName, MainetConstants.FlagD)) {
			if (StringUtils.equals(corporationServiceUlb, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=CorporationWiseWardKaralayaDetailReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}
			if (StringUtils.equals(corporationServiceUlb, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=CorporationWiseWardKaralayaDetailReport_CompalintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		/* for summary Type */
		else if (StringUtils.equals(corporationName, MainetConstants.FlagS)) {

			if (StringUtils.equals(corporationServiceUlb, MainetConstants.FlagR)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=CorporationWiseWardKaralayaSummaryReport_ServiceRequest.rptdesign&Orgid=" + currentOrgId;
			}

			if (StringUtils.equals(corporationServiceUlb, MainetConstants.FlagA)) {
				return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
						+ "=CorporationWiseWardKaralayaSummaryReport_CompalintWise.rptdesign&Orgid=" + currentOrgId;
			}

		}
		return null;

	}

}
