package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountBillRegisterBean;
import com.abm.mainet.account.service.AccountBillRegisterService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/OutstandingBillRegister.html")
public class OutstandingBillRegisterController extends AbstractController {
    private static final String OUTSTANDING_BILL_REGISTER = "OutStdngBillRegList";
    private static final String MAIN_ENTITY_NAME = "OutStdngBillRegisterBean";
    protected static final String JSP_OUTSTDBILL_RESULT = "OutStdngBillRegReport";
    private static final String OUTSTANDING_BILL_DATA = "OutStandingBilldata";
    private static final String ACCOUNT_HEAD="accountHead";

    @Resource
    private AccountBillRegisterService billRegisterService;
    @Resource
    private DepartmentService departmentService;
    @Resource
	private SecondaryheadMasterService secondaryheadMasterService;

    public OutstandingBillRegisterController() {
        super(OutstandingBillRegisterController.class, OUTSTANDING_BILL_REGISTER);
        // TODO Auto-generated constructor stub
    }

    @RequestMapping()
    public String getList(final Model model) {
        final AccountBillRegisterBean AccountBillRegisterBean = new AccountBillRegisterBean();
        populateAction(model, FormMode.CREATE);
        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }

        }
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        model.addAttribute("pacMap", secondaryheadMasterService
				.findExpenditureHeadMapAccountTypeIsOthers(UserSession.getCurrent().getOrganisation().getOrgid()));
        model.addAttribute(MAIN_ENTITY_NAME, AccountBillRegisterBean);
        return OUTSTANDING_BILL_REGISTER;
    }

    private void populateAction(final Model model, final FormMode formMode) {
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.CREATE);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, MainetConstants.Actions.UPDATE);
        }
    }

    @RequestMapping(params = "OutStdBillRegisterReport", method = RequestMethod.POST)
    public String getBillRegisterReport(final AccountBillEntryMasterBean AccountBillEntryMasterBean,
            final HttpServletRequest request, final Model model, @RequestParam("Fromdate") final String Fromdate,
            @RequestParam("dpDeptid") final Long dpDeptid, @RequestParam("deptName") final String deptName,
            @RequestParam("accountHeadId") final Long accountHeadId,@RequestParam("allAccountHeads") final String allAccountHeads) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        BigDecimal SumOfBillAmount = new BigDecimal(0.00);
        final List<Object[]> outStandingBillRegister = billRegisterService.getOutStandingBillRegisterDetails(orgId, Fromdate,
                dpDeptid,accountHeadId,allAccountHeads);
        AccountBillEntryMasterBean outStandingBillDto = null;
        List<AccountBillEntryMasterBean> outStandingBilllist = new ArrayList<>();
        final AccountBillEntryMasterBean outStandingBill = new AccountBillEntryMasterBean();
        if (outStandingBillRegister != null && !outStandingBillRegister.isEmpty()) {
            for (Object[] Entity : outStandingBillRegister) {
                outStandingBillDto = new AccountBillEntryMasterBean();

                if (Entity[0] != null) {
                    outStandingBillDto.setBillNo(Entity[0].toString());
                }

                if (Entity[1] != null) {
                    outStandingBillDto.setVendorName(Entity[1].toString());
                }

                if (Entity[2] != null) {
                    outStandingBillDto.setNarration(Entity[2].toString());
                }

                if (Entity[3] != null) {
                    outStandingBillDto.setAccountHead(Entity[3].toString());
                }

                if (Entity[4] != null) {
                    outStandingBillDto.setBillEntryDate(Utility.dateToString((Date) Entity[4]));
                }

                if (Entity[5] != null) {
                    outStandingBillDto.setBilltoatalAmt(
                            CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(Entity[5].toString())));
                    SumOfBillAmount = SumOfBillAmount.add(new BigDecimal(Entity[5].toString()));
                }

                if (Entity[6] != null) {
                    outStandingBillDto.setGrantFund(String.valueOf(Entity[6]));
                }
                
                outStandingBilllist.add(outStandingBillDto);
            }
        }

        final String amountInWords = Utility.convertBigNumberToWord(SumOfBillAmount);
        outStandingBill.setAmountInWords(amountInWords);
        outStandingBill
                .setActualAmountStr(CommonMasterUtility.getAmountInIndianCurrency(new BigDecimal(SumOfBillAmount.toString())));
        outStandingBill.setFromDate(Fromdate);
        outStandingBill.setDepartment(deptName);
        outStandingBill.setListOfBillRegister(outStandingBilllist);
        model.addAttribute(OUTSTANDING_BILL_DATA, outStandingBill);
        model.addAttribute(ACCOUNT_HEAD, allAccountHeads);
        return JSP_OUTSTDBILL_RESULT;
    }

}
