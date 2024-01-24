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
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.DetailOutstandingRegisterModel;

@Controller
@RequestMapping("/legacyPropertyBirtReport.html")
public class LegacyPropertyReportController extends AbstractFormController<DetailOutstandingRegisterModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "GetLegacyPropertyReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			@RequestParam("ReportSubType") String ReportSubType, @RequestParam("ReportSubType2") String ReportSubType2,
			@RequestParam("ReportSubType3") String ReportSubType3,
			@RequestParam("ReportSubType4") String ReportSubType4, final HttpServletRequest request) {
		if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvancePaymentRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "ADR")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdjustmentTransactionRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagB)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AssesmentRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "IPA")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=IndividualPropertyAssesmentRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagC)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=CollectionRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "IPC")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=IndividualPropertyCollectionRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DemandRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "IPD")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=IndividualPropertyDemandRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "DEF")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DefaulterListReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "CHE")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ChequeRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "DCH")) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DishonourChequeRegisterReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagE)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=DemandAndCollectionOfLandBuilding.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagF)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NewArrearsYearBreakUpReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagI)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=TaxWiseDemandForCurrentFinancialYear.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagM)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=TransferCaseDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagN)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=WardWiseYearWiseDemandRecovery.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "VDR")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=VoucherDetailAgainstAdvancePayment.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagP)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=PropertyNameAndAddressSummaryReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "CON")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ConcessionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "PLR")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=PropertyLedgerReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "ACR")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=AdvanceCollectionRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "TCW")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=TaxCollectorWiseCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "YTC")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=YearWiseTaxWiseCollectionSummaryReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		/* Status of new Property reports */

		else if (StringUtils.equals(ReportType, "H") && StringUtils.equals(ReportSubType, "NPR")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NewPropertiesReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "H") && StringUtils.equals(ReportSubType, "EPR")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ExtendedPropertiesReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "H") && StringUtils.equals(ReportSubType, "RAP")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RevisedAssessmentPropertiesReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "H") && StringUtils.equals(ReportSubType, "NPPD")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NewPropertiesPropertyWiseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "H") && StringUtils.equals(ReportSubType, "EPPD")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ExtendedPropertiesPropertyWiseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		/* Status of new Property reports */

		/* Objection register */
		else if (StringUtils.equals(ReportType, "O") && StringUtils.equals(ReportSubType2, "obj1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ObjectionAndHearingReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "O") && StringUtils.equals(ReportSubType2, "obj2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=ObjectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		/* NMAM Demand ,collection and balance reports */
		else if (StringUtils.equals(ReportType, "NMAM") && StringUtils.equals(ReportSubType3, "NMMD")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NMAMRegisterReport_Demand.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "NMAM") && StringUtils.equals(ReportSubType3, "NMMC")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NMAMRegisterReport_Collection.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "NMAM") && StringUtils.equals(ReportSubType3, "NMMB")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NMAMRegisterReport_Balance.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		/*
		 * Register Of Notice Warrant Other Fees Penalty Charge detail and summary
		 * Report
		 */
		else if (StringUtils.equals(ReportType, "Note") && StringUtils.equals(ReportSubType4, "Note1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RegisterOfNoticeWarrantOtherFeesPenaltyChargeReport_PT.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "Note") && StringUtils.equals(ReportSubType4, "Note2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RegisterOfNoticeFeeSummaryReport_PT.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		return null;
	}

	/******** DCB REPORTS ********************/

	@RequestMapping(params = "getDCBPropertyReport", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getDCBPropertyReport(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		return customResult("DCBBirtReportForm");

	}

	@RequestMapping(params = "getDCBBirtReport", method = { RequestMethod.POST })
	public @ResponseBody String viewDcbReport(@RequestParam("ReportDcbType") String ReportDcbType,
			final HttpServletRequest request) {

		if (StringUtils.equals(ReportDcbType, MainetConstants.FlagB)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=WardZoneWiseDetailCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		if (StringUtils.equals(ReportDcbType, MainetConstants.FlagC)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=WardZoneWiseCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		if (StringUtils.equals(ReportDcbType, MainetConstants.FlagD)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=WardZoneTaxWiseDemandReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportDcbType, MainetConstants.FlagE)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=TaxWiseDCBSummaryReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		if (StringUtils.equals(ReportDcbType, MainetConstants.FlagA)) {
			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_UsageTypeWiseDCBReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		return null;

	}

}
