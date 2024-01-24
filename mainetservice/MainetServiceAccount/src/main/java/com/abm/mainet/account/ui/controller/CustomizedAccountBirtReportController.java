package com.abm.mainet.account.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.ui.model.CustomizedAccountBirtReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/customizedAccountBirtReport.html")
public class CustomizedAccountBirtReportController extends AbstractFormController<CustomizedAccountBirtReportModel> {

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		CustomizedAccountBirtReportModel model = this.getModel();
		return index();
	}

	@RequestMapping(params = "GetBirtReport", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewBirtReport(@RequestParam("ReportType") String ReportType,
			@RequestParam("ReportSubType") String ReportSubType, final HttpServletRequest request) {

		Long currentOrgId = 0L;
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		if (organisation != null && !StringUtils.equals(organisation.getDefaultStatus(), MainetConstants.FlagY)) {
			currentOrgId = organisation.getOrgid();
		}
		if (StringUtils.equals(ReportType, MainetConstants.FlagA)) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_AdvancePaymentInterestCalculations.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "A1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_AgeingAnalysisForOutstandingInvoices.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_ChequeBookPrint.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A3")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_DepositForfeitureRegister.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "A4")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_MISReportPayments.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A5")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_MISReportReceipts.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportType, "A6")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_MonthlyAccountsSummaryPayments.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportType, "A7")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_MonthlyAccountsSummaryReceipt.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "BN")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=NMAM_BLSH.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}

		else if (StringUtils.equals(ReportSubType, "B1")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B1.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B2")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B2.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B3")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B3.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B4")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B4.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B5")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B5.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B6")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B6.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B7")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B7.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B8")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B8.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B9")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B9.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B10")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B10.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B11")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B11.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B12")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B12.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B13")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B13.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B14")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B14.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}else if (StringUtils.equals(ReportSubType, "B15")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B15.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} 
		else if (StringUtils.equals(ReportSubType, "B16")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B16.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} /*
			 * else if (StringUtils.equals(ReportSubType, "B16")) {
			 * 
			 * return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL +
			 * "=RP_BalanceSheet_B16.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
			 * + currentOrgId; }
			 */else if (StringUtils.equals(ReportSubType, "B17")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B17.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B18")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B18.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}  else if (StringUtils.equals(ReportSubType, "B18A")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B18_A.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}else if (StringUtils.equals(ReportSubType, "B19")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B19.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		} else if (StringUtils.equals(ReportSubType, "B20")) {

			return ServiceEndpoints.PROPERTY_BIRT_REPORT_URL
					+ "=RP_BalanceSheet_B20.rptdesign&__ExcelEmitter.SingleSheet=true&__ExcelEmitter.SingleSheetWithPageBreaks=true&__ExcelEmitter.DisableGrouping=true&Orgid="
					+ currentOrgId;
		}
		return ReportSubType;

	}
}
