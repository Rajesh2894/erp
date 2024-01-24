package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.model.response.AccountReceiptEntryResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.dto.TbCustbanks;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.mapper.TbDepartmentServiceMapper;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author dharmendra.chouhan
 *
 */

@Controller
@RequestMapping("/AccountReceiptReversal.html")
public class AccountReceiptReversalController extends AbstractController {

    private static Logger logger = Logger
            .getLogger(AccountReceiptEntryController.class);

    static String MAIN_ENTITY_NAME = "tbServiceReceiptMas";
    static String MAIN_LIST_NAME = "list";
    static String DEPARTMENT_LIST = "departmentlist";
    static String BANKACCOUNT_LIST = "bankaccountlist";
    static String PAYMENTMODE = "paymentMode";
    static String CUSTOMERBANKLIST = "customerBankList";
    private static final String JSP_FORM = "accountReceiptReversal/form";
    private static final String JSP_LIST = "accountReceiptReversal/list";
    private static final String SAVE_ACTION_CREATE = "AccountReceiptReversal.html?create";
    List<TbServiceReceiptMasBean> listOfTaxBean;
    private static final String View = "View";
    private static final String Edit = "Edit";

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    private AccountReceiptEntryService accountReceiptEntryService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private BudgetCodeService budgetCodeService;

    @Resource
    private TbCustbanksService tbCustbanksService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private TbDepartmentServiceMapper tbDepartmentServiceMapper;

    List<TbDepartment> deptList = new ArrayList<>();

    public AccountReceiptReversalController() {
        super(AccountReceiptReversalController.class, MAIN_ENTITY_NAME);
        log("AccountReceiptReversalController created.");
    }

    private void populateModel(final Model model,
            final TbServiceReceiptMasBean tbServiceReceiptMasBean, final FormMode formMode) {
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbAcVendormaster> list = tbAcVendormasterService
                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(
                MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME,
                list);

        Department entities = departmentService.getDepartment(AccountConstants.AC.getValue(),
                MainetConstants.CommonConstants.ACTIVE);

        List<TbDepartment> departmentlist = null;
        if (entities != null) {
            departmentlist = new ArrayList<>();
            departmentlist.add(tbDepartmentServiceMapper.mapTbDepartmentEntityToTbDepartment(entities));
        }

        model.addAttribute(DEPARTMENT_LIST, departmentlist);

        final List<BankAccountMasterEntity> blist = accountReceiptEntryService
                .findBankacListReceipt(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(BANKACCOUNT_LIST, blist);

        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(
                AccountPrefix.PAY.name(), UserSession.getCurrent()
                        .getOrganisation());
        final List<LookUp> paymentMode1 = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(MainetConstants.Complaint.MODE_CREATE)
                    || payList.getLookUpCode().equals(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q)
                    || payList.getLookUpCode().equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)
                    || payList.getLookUpCode().equals(MainetConstants.PAYMODE.WEB)
                    || payList.getLookUpCode().equals(MainetConstants.AccountReceiptEntry.RT)
                    || payList.getLookUpCode().equals(MainetConstants.MENU.N)
                    || payList.getLookUpCode().equals(MainetConstants.MENU.D)
                    || payList.getLookUpCode().equals(MainetConstants.MENU.F)) {
                paymentMode1.add(payList);
            }
        }

        model.addAttribute(PAYMENTMODE, paymentMode1);

        final List<TbCustbanks> customerBankList = tbCustbanksService.findAll();
        model.addAttribute(CUSTOMERBANKLIST, customerBankList);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populateListOfTbAcFieldMasterItems(model, orgId);
        }

        if (formMode == FormMode.VIEW) {
            model.addAttribute(MODE, MODE_VIEW);
            populateBudgetCodes(model);
            populateListOfTbAcFieldMasterItems(model, orgId);
        }

        if (formMode == FormMode.EDIT) {
            model.addAttribute(MODE, MODE_EDIT);
            populateBudgetCodes(model);
            populateListOfTbAcFieldMasterItems(model, orgId);
        }

    }

    @RequestMapping()
    public String list(final Model model) {
        log("Action 'list'");
        listOfTaxBean = new ArrayList<>();
        return JSP_LIST;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String formForCreate(final Model model) {

        log("Action 'formForCreate'");
        final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
        populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
        return JSP_FORM;
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody AccountReceiptEntryResponse gridData(
            final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");

        final int page = Integer
                .parseInt(request
                        .getParameter(MainetConstants.CommonConstants.PAGE));

        final AccountReceiptEntryResponse accountReceiptEntryResponse = new AccountReceiptEntryResponse();
        accountReceiptEntryResponse.setRows(listOfTaxBean);
        accountReceiptEntryResponse.setTotal(listOfTaxBean.size());
        accountReceiptEntryResponse.setRecords(listOfTaxBean.size());
        accountReceiptEntryResponse.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, model);
        return accountReceiptEntryResponse;
    }

    @SuppressWarnings("unused")
    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody String gridData(final HttpServletRequest request, final Model model,
            @RequestParam("rmAmount") String rmAmount,
            @RequestParam("rmRcptno") String rmRcptno,
            @RequestParam("rm_Receivedfrom") String rm_Receivedfrom,
            @RequestParam("rmDate") final String rmDate

    ) throws ParseException {
        log("Action getjqGridsearch");
        List<TbServiceReceiptMasBean> list = null;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String message = MainetConstants.COMMON_STATUS.SUCCESS;
        BigDecimal bd = null;

        Date date = null;
        if ((rmDate != null) && !rmDate.isEmpty()) {
            date = Utility.stringToDate(rmDate);
        }

        if (rmRcptno.isEmpty() || (rmRcptno == null) || (rmRcptno == MainetConstants.CommonConstant.BLANK)) {
            rmRcptno = MainetConstants.ZERO;
        }

        BigDecimal rmReptAmount;
        if (rmAmount.isEmpty() || (rmAmount == null) || (rmAmount == MainetConstants.CommonConstant.BLANK)) {
            rmReptAmount = BigDecimal.ZERO;
        } else {
            rmReptAmount = new BigDecimal(rmAmount);
        }

        if (rm_Receivedfrom.isEmpty() && (rm_Receivedfrom == null)) {
            rm_Receivedfrom = MainetConstants.CommonConstant.BLANK;
        }
        Long receiptNo = null;
        if ((rmRcptno == null) || rmRcptno.isEmpty()) {
            receiptNo = 0L;
        } else {
            receiptNo = Long.valueOf(rmRcptno);
        }

        list = accountReceiptEntryService.findAll(orgId, rmReptAmount, receiptNo, rm_Receivedfrom, date);

        listOfTaxBean = new ArrayList<>();
        if ((list != null) && !list.isEmpty()) {
            for (final TbServiceReceiptMasBean bean : list) {

                bd = new BigDecimal(bean.getRmAmount());
                final String amount = CommonMasterUtility
                        .getAmountInIndianCurrency(bd);
                bean.setFormattedCurrency(amount);
                listOfTaxBean.add(bean);
            }
        }

        return message;
    }

    @RequestMapping(params = "Update")
    public ModelAndView formForUpdate(@RequestParam("rmRcptid") final Long rmRcptid,
            @RequestParam("receiptDelRemark") final String receiptDelRemark,
            @RequestParam("receiptDelDatetemp") final String receiptDelDatetemp, final Model model,
            final HttpServletRequest httpServletRequest) throws ParseException {
        log("Action 'Update'");

        final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();

        Date date = null;
        if ((receiptDelDatetemp != null) && !receiptDelDatetemp.isEmpty()) {

            final SimpleDateFormat sourceFormat = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            final String dateAsString = receiptDelDatetemp;
            date = sourceFormat.parse(dateAsString);

        }

        if (rmRcptid != null) {

            final String lgIpMacUpd = Utility.getClientIpAddress(httpServletRequest);
            final Long updatedBy = UserSession.getCurrent().getEmployee().getEmpId();

            accountReceiptEntryService
                    .findByIdEdit(rmRcptid, UserSession
                            .getCurrent().getOrganisation()
                            .getOrgid(), receiptDelRemark, date, lgIpMacUpd, updatedBy);
        }

        tbServiceReceiptMasBean.setSuccessFlag(MainetConstants.MENU.Y);
        model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
        return new ModelAndView(JSP_FORM);
    }

    @RequestMapping(params = "formForUpdate")
    public String formForEdit(final Model model,
            @RequestParam("rmRcptid") final Long rmRcptid) {

        TbServiceReceiptMasBean tbServiceReceiptMasBean = null;
        tbServiceReceiptMasBean = accountReceiptEntryService.findById(rmRcptid,
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MODE, Edit);
        populateModel(model, tbServiceReceiptMasBean, FormMode.EDIT);

        final String chkDate = Utility.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
        tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(chkDate);

        final String tranRefDate = Utility.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
        tbServiceReceiptMasBean.getReceiptModeDetailList().setTranRefDatetemp(tranRefDate);

        model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
        return JSP_FORM;
    }

    @RequestMapping(params = "viewMode")
    public String formForView(final Model model,
            @RequestParam("rmRcptid") final Long rmRcptid) {

        TbServiceReceiptMasBean tbServiceReceiptMasBean = null;
        tbServiceReceiptMasBean = accountReceiptEntryService.findById(rmRcptid,
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MODE, View);
        populateModel(model, tbServiceReceiptMasBean, FormMode.VIEW);

        final String chkDate = Utility.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
        tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(chkDate);

        final String tranRefDate = Utility.dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
        tbServiceReceiptMasBean.getReceiptModeDetailList().setTranRefDatetemp(tranRefDate);

        model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
        return JSP_FORM;

    }

    private void populateListOfTbAcFieldMasterItems(final Model model, final Long orgId) {

        model.addAttribute(
                MainetConstants.ACCOUNT_RECEIPT_ENTRY_MASTER.FIELD_MASTER_ITEMS,
                tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
    }

    private void populateBudgetCodes(final Model model) {

        final Long superOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final String coaPrefix = AccountPrefix.COA.toString();
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.IsLookUp.INACTIVE, coaPrefix,
                langId,
                org);
        final Long cpdIdHeadType = lookUp.getLookUpId();
        final List<Object[]> budgetHeadList = budgetCodeService.getBudgetHeads(superOrgId, org.getOrgid(), cpdIdHeadType);
        final Map<Long, String> headCodeMap = new HashMap<>();
        if ((budgetHeadList != null) && !budgetHeadList.isEmpty()) {
            for (final Object[] budgetArray : budgetHeadList) {
                headCodeMap.put((Long) budgetArray[0],
                        budgetArray[1].toString() + MainetConstants.SEPARATOR_AC_HEAD + budgetArray[2].toString()
                                + MainetConstants.HYPHEN + budgetArray[3].toString() + MainetConstants.HYPHEN
                                + budgetArray[4].toString());
            }
        }

        model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, headCodeMap);
    }

}
