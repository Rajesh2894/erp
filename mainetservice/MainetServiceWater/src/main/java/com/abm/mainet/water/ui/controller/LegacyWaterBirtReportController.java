package com.abm.mainet.water.ui.controller;

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

import com.abm.mainet.water.ui.model.ListOfClosingConnectionModel;

@Controller
@RequestMapping("/legacyWaterBirtReports.html")
public class LegacyWaterBirtReportController extends AbstractFormController<ListOfClosingConnectionModel> {
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		return index();
	}

	@RequestMapping(params = "getWaterBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReportSheet(@RequestParam("ReportType") String ReportType,
			@RequestParam("ReportSubType1") String ReportSubType1,
			@RequestParam("ReportSubType2") String ReportSubType2,
			@RequestParam("ReportSubType3") String ReportSubType3,
			@RequestParam("ReportSubType4") String ReportSubType4,
			@RequestParam("ReportSubType5") String ReportSubType5,
			@RequestParam("ReportSubType6") String ReportSubType6, final HttpServletRequest request) {

		if (StringUtils.equals(ReportType, "CDC")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=CfcConnectionDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagA) && StringUtils.equals(ReportSubType1, "A1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardWiseDemandRecoveryOfArrearCurrent_BillPeriod.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagA) && StringUtils.equals(ReportSubType1, "A2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardWiseDemandRecoveryOfArrearCurrent_BillDate.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagB) && StringUtils.equals(ReportSubType2, "B1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ConnectionAdjustmentDetail_NormalizeReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagB) && StringUtils.equals(ReportSubType2, "B2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ConnectionAdjustmentDetail_CCNNoWiseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagC) && StringUtils.equals(ReportSubType3, "C1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ClosedConnectionListReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagC) && StringUtils.equals(ReportSubType3, "C2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ClosedConnectionListReport_ArrearAmount.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagD) && StringUtils.equals(ReportSubType4, "D1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ListOfWaterDefaulterDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagD) && StringUtils.equals(ReportSubType4, "D2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ListOfWaterDefaulterSummaryReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagE) && StringUtils.equals(ReportSubType5, "E1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ConsumptionDemandOfBillReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, MainetConstants.FlagE) && StringUtils.equals(ReportSubType5, "E2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ListOfNotGeneratedConnectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagF) && StringUtils.equals(ReportSubType6, "F1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WaterBillReceiptDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, MainetConstants.FlagF) && StringUtils.equals(ReportSubType6, "F2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardWiseAndBillReceiptDetailsOfConusmer.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, "LCR")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ListOfCcnWhichHasNoReadingBeforeBillGenReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "LCW")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=ListOfConsumerWardGroupWiseReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, "W1")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardZoneWiseListOfPendingBillsReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "W2")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardConnectionNoWiseLastReadingDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "W3")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardCategoryConnectionSizeWiseTotalConnectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "W4")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WardZoneConsumerWiseArrearCurrentBillAmountReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, "W5")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WaterWardZoneWiseCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, "DRAC")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=DemandRecoveryOfArrearCurrentConnectionWise.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "WMD")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=WaterMeterDetailReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}

		else if (StringUtils.equals(ReportType, "RNF")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=RegisterOfNoticeWarrantOtherFeesPenaltyChargeReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "SMR")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=SummaryOfMeterReadingDataEntryReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		} else if (StringUtils.equals(ReportType, "SSY")) {
			return ServiceEndpoints.WATER_BIRT_REPORT_URL
					+ "=SummaryStatementYearHeadWiseCollectionReport.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true";
		}
		return null;

	}

}