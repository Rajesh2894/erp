package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBillRegisterBean;
import com.abm.mainet.account.service.AccountBillRegisterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/BillRegister.html")
public class AccountBillRegistrationController extends AbstractController {
	private static final String MAIN_ENTITY_NAME = "aBillRegisterBean";
	private static final String Bill_Register = "billRegisterReport";
	protected static final String MODE = "mode";
	private static final String Bill_Rreport = "billReportdata";
	protected static final String MODE_CREATE = "create";
	protected static final String MODE_UPDATE = "update";
	protected static final String MODE_EDIT = "edit";
	protected static final String MODE_VIEW = "view";
	protected static final String JSP_BILL_RESULT = "aBillRegister";
	private final static String BILL_TYPE_LIST = "billTypeList";

	@Resource
	private AccountBillRegisterService billRegisterService;

	@Autowired
	private IEmployeeService employeeService;

	public AccountBillRegistrationController() {
		super(AccountBillRegistrationController.class, Bill_Register);
		log("AccountBillRegistrationController created.");
	}

	@RequestMapping()
	public String getList(final Model model) {

		final AccountBillRegisterBean AccountBillRegisterBean = new AccountBillRegisterBean();

		populateAction(model, FormMode.CREATE);
		List<LookUp> billTypeLookupList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.ABT.toString(),
				UserSession.getCurrent().getOrganisation());
		model.addAttribute(BILL_TYPE_LIST, billTypeLookupList);
		
		model.addAttribute(MAIN_ENTITY_NAME, AccountBillRegisterBean);
		return Bill_Register;
	}

	@RequestMapping(params = "PrintBillRegisterReport", method = RequestMethod.POST)
	public String getBillRegisterReport(final AccountBillEntryMasterBean AccountBillEntryMasterBean,
			final HttpServletRequest request, final Model model, @RequestParam("Fromdate") final String Fromdate,
			@RequestParam("Todate") final String Todate,@RequestParam("billTyp") final Long billTyp) {
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int count = 0;
		final List<Object[]> BillRegisterBean = billRegisterService.getBillRegisterDetails(orgId, Fromdate, Todate,billTyp);
		AccountBillEntryMasterBean dto = null;
		List<AccountBillEntryMasterBean> list = new ArrayList<>();
		final AccountBillEntryMasterBean BillRegister = new AccountBillEntryMasterBean();
		BigDecimal totalBillamt = BigDecimal.ZERO;
		BigDecimal totalActualamt = BigDecimal.ZERO;
		BigDecimal totalpayee = BigDecimal.ZERO;
		BigDecimal totalBillamtstr = BigDecimal.ZERO;
		BigDecimal totalBalance = BigDecimal.ZERO;
		BigDecimal sumOfdeductionpaidAmt = BigDecimal.ZERO;
		BigDecimal totaldeductionAmt = BigDecimal.ZERO;

		// *******************************************************New
		// Changes**************************************//
		if (BillRegisterBean != null && !BillRegisterBean.isEmpty()) {
			for (Object[] Entity : BillRegisterBean) {
				count = count + 1;
				BigDecimal totaloutstadingblnc = BigDecimal.ZERO;
				dto = new AccountBillEntryMasterBean();
				dto.setCount(count);
				// 1column
				if (Entity[0] != null) {
					dto.setBillNo((String) Entity[0]);
				} else {
					dto.setBillNo(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				// 1column
				if (Entity[1] != null) {
					String ds2 = null;
					String biilEntrydate = Entity[1].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
					try {
						ds2 = sdf2.format(sdf1.parse(biilEntrydate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					dto.setBillEntryDate(ds2);
				}

				// 2column
				if (Entity[2] != null) {
					dto.setVendorName((String) Entity[2]);
				}

				// 3column voucher No
				if (Entity[10] != null) {
					dto.setInvoiceNumber((String) Entity[10]);
				}

				// 4column bill amount
				if (Entity[5] != null) {
					dto.setInvoiceValueStr(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[5].toString())));
					totalBillamt = totalBillamt.add(new BigDecimal(Entity[5].toString()));
				} else {
					dto.setInvoiceValueStr(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				// 5column particular
				if (Entity[4] != null) {
					dto.setNarration((String) Entity[4]);
				}

				// sanction Amount changed
				if (Entity[7] != null) {
					dto.setBilltoatalAmt(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[7].toString())));
					totalActualamt = totalActualamt.add(new BigDecimal(Entity[7].toString()));
				} else {
					dto.setBilltoatalAmt(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}
				// sanction Date changed
				if (Entity[9] != null && !Entity[9].toString().equals("null")) {
					String ds2 = null;
					String checkerDate = Entity[9].toString();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yy-MM-dd");
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
					try {
						ds2 = sdf2.format(sdf1.parse(checkerDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					dto.setCheckerDate(ds2);
				}

				// Paid Amount changed
				if (Entity[11] != null && !Entity[11].toString().equals("null")) {
					dto.setPayee(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[11].toString())));
					totalpayee = totalpayee.add(new BigDecimal(Entity[11].toString()));
				} else {
					dto.setPayee(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				// disallowed amount changed
				if (Entity[6] != null && !Entity[6].toString().equals("null")) {
					dto.setTotalDisallowedAmountStr(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[6].toString())));
					totalBillamtstr = totalBillamtstr.add(new BigDecimal(Entity[6].toString()));
				} else {
					dto.setTotalDisallowedAmountStr(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				// deduction Amount changed
				if (Entity[8] != null && !Entity[8].toString().equals("null")) {
					dto.setDedcutionAmountStr(
							CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[8].toString())));
					totaldeductionAmt = totaldeductionAmt.add(new BigDecimal(Entity[8].toString()));
				} else {
					dto.setDedcutionAmountStr(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
				}

				// remarks changed
				if (Entity[12] != null && !Entity[12].toString().equals("null")) {
					dto.setDisallowedRemark(Entity[12].toString());

				}

				String empName = "";
				final EmployeeBean bean = employeeService.findById(Long.valueOf(Entity[13].toString()));
				if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
					empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
				} else {
					if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
						empName = bean.getEmpname() + " " + bean.getEmplname();
					} else {
						empName = bean.getEmpname();
					}
				}
				if (empName != null) {
					dto.setEmpName(empName);
				}

				// outstanding balance
				if (Entity[7] != null && Entity[11] != null && Entity[8] != null) {
					totalActualamt = totalActualamt.subtract(sumOfdeductionpaidAmt);
					dto.setTotalBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(
							new BigDecimal(Entity[7].toString()).subtract((new BigDecimal(Entity[11].toString()))
									.add(new BigDecimal(Entity[8].toString())))));
					dto.setOutStandingBalance(new BigDecimal(Entity[7].toString()).subtract(
							(new BigDecimal(Entity[11].toString())).add(new BigDecimal(Entity[8].toString()))));

				} else if (Entity[7] != null && Entity[11] != null) {
					totalActualamt = totalActualamt.subtract(sumOfdeductionpaidAmt);
					dto.setTotalBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(
							new BigDecimal(Entity[7].toString()).subtract(new BigDecimal(Entity[11].toString()))));
					dto.setOutStandingBalance(
							new BigDecimal(Entity[7].toString()).subtract(new BigDecimal(Entity[11].toString())));

				} else if (Entity[7] != null && Entity[8] != null) {
					totalActualamt = totalActualamt.subtract(sumOfdeductionpaidAmt);
					dto.setTotalBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(
							new BigDecimal(Entity[7].toString()).subtract(new BigDecimal(Entity[8].toString()))));
					dto.setOutStandingBalance(
							new BigDecimal(Entity[7].toString()).subtract(new BigDecimal(Entity[8].toString())));
				} else if (Entity[7] != null) {
					dto.setTotalBillAmountStr(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[7].toString())));
					dto.setOutStandingBalance(new BigDecimal(Entity[7].toString()));
				}
				if (dto.getOutStandingBalance() != null) {
					totalBalance = totalBalance.add(new BigDecimal(dto.getOutStandingBalance().toString()));
				}

				list.add(dto);
			}
		}
		BillRegister.setListOfBillRegister(list);

		BillRegister.setBilltoatalAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalBillamt.toString())));
		BillRegister.setTotalSactAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalActualamt.toString())));

		BillRegister.setTotalDedcutionsAmountStr(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalBillamtstr.toString())));

		BillRegister
				.setTotalPayments(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalpayee.toString())));

		BillRegister.setTotalBalance(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totalBalance.toString())));

		BillRegister.setSumDeductionAmt(
				CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(totaldeductionAmt.toString())));

		BillRegister.setFromDate(Fromdate);
		BillRegister.setToDate(Todate);
		model.addAttribute(Bill_Rreport, BillRegister);
		return JSP_BILL_RESULT;

	}

	private void populateAction(final Model model, final FormMode formMode) {
		if (formMode == FormMode.CREATE) {

			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.CREATE);

		} else if (formMode == FormMode.UPDATE) {

			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.UPDATE);
		}
	}

}
