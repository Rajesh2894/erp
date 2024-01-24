package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.ui.model.CustomizedBirtReportModel;

@Controller
@RequestMapping("/customizedPropertyBirtReports.html")
public class CustomizedBirtReportController extends AbstractFormController<CustomizedBirtReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetCustPropBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}

		if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_CFCEmployeeCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_FinalAssessmentRegisterZoneWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "C1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_DayBookCollectionRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "C2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_DailyCollectionReport_ADV_TEST.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "C3")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DemandAndDetailCollectionRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "C4")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DemandAndDetailCollectionRegisterEmpWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "C5")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_DemandAndCollectionpPropertyCountSummaryWardWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "C6")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_DemandAndCollectionpPropertyCountSummaryZoneWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_PropertyTaxHistoryForAudit.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "D1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_SummaryCollectionRegisterWardWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "D2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_SummaryCollectionRegisterZoneWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "E1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_CollectionSummaryWithDemandAndCollectionPropertyCountWardWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "E2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_CollectionSummaryWithDemandAndCollectionPropertyCountZoneWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		return ReportType;

	}
}
