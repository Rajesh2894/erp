
package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.AccountVoucherReversalService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BankAccountMasterEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.dto.TbCustbanks;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.mapper.TbDepartmentServiceMapper;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.mchange.v1.util.StringTokenizerUtils;

/**
 * @author dharmendra.chouhan
 *
 */
@Controller
@RequestMapping("/AccountVoucherReversal.html")
public class AccountVoucherReversalController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherReversalController.class);

    private static final String MAIN_ENTITY_NAME = "tbServiceReceiptMas";
    private static final String DEPARTMENT_LIST = "departmentlist";
    private static final String BANKACCOUNT_LIST = "bankaccountlist";
    private static final String PAYMENTMODE = "paymentMode";
    private static final String CUSTOMERBANKLIST = "customerBankList";
    private static final String JSP_FORM = "accountReceiptReversal/form";
    private static final String JSP_LIST = "accountReceiptReversal/list";
    private static final String SAVE_ACTION_CREATE = "AccountVoucherReversal.html?create";
    List<TbServiceReceiptMasBean> listOfTaxBean;
    private static final String VIEW = "View";
    private static final String EDIT = "Edit";
    private static final String REVERSAL_GRID_DATA = "reversalGridData";
    private static final String REVERSAL_SESSION_DTO = "reversalSessionDTO";
    private static final String REVERSAL_DTO = "reversalDTO";
    private static final String TRANSACTION_TYPE_LOOKUPS = "transactionTypeLookUps";
    private static final String APPROVED_BY = "approvedBy";
    private static final String REVERSAL_ITEMS = "reversalItems";
    private static final String ACTION_URL = "actionURL";
    private static final String TRANSACTION_TYPE = "transactionType";
    private static final String VALIDATION_MSG = "validationMsg";

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
    private AccountVoucherReversalService accountVoucherReversalService;

    @Resource
    private DepartmentService departmentService;

    @Resource
    private ILocationMasService locMasService;

    @Resource
    private TbDepartmentServiceMapper tbDepartmentServiceMapper;
    
    @Resource
	private IReceiptEntryService iReceiptEntryService;

    List<TbDepartment> deptList = new ArrayList<>();

    public AccountVoucherReversalController() {
        super(AccountVoucherReversalController.class, MAIN_ENTITY_NAME);
        log("AccountVoucherReversalController created.");
    }

    private void populateModel(final Model model, final FormMode formMode) {
        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<TbAcVendormaster> list = tbAcVendormasterService
                .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.VENDOR_MASTER.MAIN_LIST_NAME, list);

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
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(PrefixConstants.AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode1 = new ArrayList<>();
        for (final LookUp lookUp : paymentModeList) {
            if (isCashOrCheque(lookUp)
                    || isWebOrRTGS(lookUp)
                    || isFDROrNEFT(lookUp)
                    || isBankOrDemandDraft(lookUp)) {
                paymentMode1.add(lookUp);
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
    public String list(final Model model, final HttpServletRequest request) {

        request.getSession().setAttribute(REVERSAL_GRID_DATA, null);
        request.getSession().setAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, null);
        request.getSession().setAttribute(REVERSAL_SESSION_DTO, null);
        request.getSession().setAttribute(REVERSAL_GRID_DATA, null);

        model.addAttribute(TRANSACTION_TYPE_LOOKUPS, CommonMasterUtility
                .getListLookup(PrefixConstants.AccountPrefix.TOS.toString(), UserSession.getCurrent().getOrganisation()));
        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        final TbDepartment dept = departmentService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.CommonConstants.ACTIVE, MainetConstants.AccountConstants.AC.getValue());
        model.addAttribute(APPROVED_BY, accountVoucherReversalService.findApprovalAuthority(dept.getDpDeptid(),
                UserSession.getCurrent().getOrganisation().getOrgid()));
        VoucherReversalDTO dto = new VoucherReversalDTO();
        dto.setDpDeptid(dept.getDpDeptid());
        model.addAttribute(REVERSAL_DTO, dto);
        return JSP_LIST;
    }

    /**
     * back on the main page and will maintain/remember the view data in grid
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(params = "back", method = RequestMethod.GET)
    public String onBack(final Model model, final HttpServletRequest request) {
        model.addAttribute(REVERSAL_DTO, request.getSession().getAttribute(REVERSAL_SESSION_DTO));
        model.addAttribute(TRANSACTION_TYPE_LOOKUPS, CommonMasterUtility
                .getListLookup(PrefixConstants.AccountPrefix.TOS.toString(), UserSession.getCurrent().getOrganisation()));
        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        final TbDepartment dept = departmentService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.CommonConstants.ACTIVE, MainetConstants.AccountConstants.AC.getValue());
        model.addAttribute(APPROVED_BY, accountVoucherReversalService.findApprovalAuthority(dept.getDpDeptid(),
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return JSP_LIST;
    }

    /**
     * clear grid records on change of Transaction Type
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(params = "clear", method = RequestMethod.POST)
    public @ResponseBody String clearOnChangeOfTransactionType(final Model model, final HttpServletRequest request) {
        model.addAttribute(REVERSAL_DTO, null);
        request.getSession().removeAttribute(REVERSAL_GRID_DATA);
        model.addAttribute(TRANSACTION_TYPE_LOOKUPS, CommonMasterUtility
                .getListLookup(PrefixConstants.AccountPrefix.TOS.toString(), UserSession.getCurrent().getOrganisation()));
        final TbDepartment dept = departmentService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                MainetConstants.CommonConstants.ACTIVE, MainetConstants.AccountConstants.AC.getValue());
        model.addAttribute(APPROVED_BY, accountVoucherReversalService.findApprovalAuthority(dept.getDpDeptid(),
                UserSession.getCurrent().getOrganisation().getOrgid()));
        return StringUtils.EMPTY;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String formForCreate(final Model model) {

        log("Action 'formForCreate'");
        final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
        populateModel(model, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, tbServiceReceiptMasBean);
        return JSP_FORM;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "searchForReversal", method = RequestMethod.GET)
    public @ResponseBody String searchRecords(@Valid final VoucherReversalDTO dto,
            final BindingResult bindingResult,
            final HttpServletRequest request, final ModelMap model) {

        String resultMsg;
        final String transactionType = CommonMasterUtility.findLookUpCode(PrefixConstants.AccountPrefix.TOS.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(),
                dto.getTransactionTypeId());
        String tranNo = dto.getTransactionNo();
		if (StringUtils.isNotEmpty(tranNo)) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				if(transactionType.equalsIgnoreCase("R")) 
				dto.setTransactionNo(Utility.getReceiptIdFromCustomRcptNO(tranNo).toString());
			}
		}
		
        if (tranNo == null || tranNo.isEmpty()) {
            model.addAttribute(VALIDATION_MSG, "R");
            resultMsg = "R";
            model.addAttribute(REVERSAL_ITEMS, null);
            request.getSession().setAttribute(REVERSAL_GRID_DATA, null);
            LOGGER.error("Problem while search :" + "Transaction number is invalid for reversal process.");
        } else {
            request.getSession().setAttribute(TRANSACTION_TYPE, transactionType);
            request.getSession().setAttribute(REVERSAL_SESSION_DTO, dto);
            dto.setTransactionType(transactionType);

            final ResponseEntity<?> response = accountVoucherReversalService.findRecordsForReversal(dto,
                    UserSession.getCurrent().getOrganisation().getOrgid(),
                    dto.getDpDeptid());
            if (response.getStatusCode() == HttpStatus.OK) {
                final Object[] responseArray = (Object[]) response.getBody();
                request.getSession().setAttribute(ACTION_URL, responseArray[0]);
                
                if(StringUtils.equals("RP", transactionType) || StringUtils.equals("BP", transactionType)) {
                	 List<VoucherReversalDTO> reversalDtoList = (List<VoucherReversalDTO>) responseArray[1];
                	 if(StringUtils.equals("RP", transactionType)) {
                		 if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
                			 if(CollectionUtils.isNotEmpty(reversalDtoList))
                			 reversalDtoList.get(0).setTransactionNo(iReceiptEntryService.getCustomReceiptNo(
                					 dto.getDpDeptid(), Long.valueOf(reversalDtoList.get(0).getTransactionNo())));
             			}
                	 }
                     boolean checkBillReversalApplicable = accountVoucherReversalService.checkBillReversalApplicable(reversalDtoList.get(0), transactionType, UserSession.getCurrent().getOrganisation().getOrgid());
                     if(checkBillReversalApplicable) {
                     	resultMsg = "NA";
                     	return resultMsg;
                     }
                }
                if (MainetConstants.AccountConstants.N.getValue().equals(responseArray[0])) {
                    model.addAttribute(REVERSAL_ITEMS, null);
                    request.getSession().setAttribute(REVERSAL_GRID_DATA, null);
                    LOGGER.error("No Records Found For provided input[" + dto);
                } else {
                    model.addAttribute(REVERSAL_ITEMS, responseArray[1]);
                    request.getSession().setAttribute(REVERSAL_GRID_DATA, responseArray[1]);
                }
                resultMsg = (String) responseArray[0];
            } else {
                model.addAttribute(VALIDATION_MSG, response.getBody());
                resultMsg = (String) response.getBody();
                model.addAttribute(REVERSAL_ITEMS, null);
                request.getSession().setAttribute(REVERSAL_GRID_DATA, null);
                if(StringUtils.equals("RP", transactionType) ) {
                	Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
                 TbServiceReceiptMasBean receipt = accountReceiptEntryService.findReceiptData(orgId, Long.valueOf(dto.getTransactionNo()),
                		Utility.stringToDate(dto.getTransactionDate()), dto.getDpDeptid());
                	if(receipt!=null) {
						if (MainetConstants.TRUE.equalsIgnoreCase(receipt.getFlag())) {
							resultMsg = "DSP";// Despoite slip reversal pending
						} else if (MainetConstants.Y_FLAG.equalsIgnoreCase(receipt.getReceiptDelFlag())) {
							resultMsg = "REV";// Receipt already Reverse
						}
                	}
                }
                LOGGER.error("Problem while search :" + response.getBody());
            }
        }
        model.addAttribute(MainetConstants.CommonConstants.COMMAND, dto);
        return resultMsg;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", produces = "application/json", method = RequestMethod.GET)
    public @ResponseBody JQGridResponse geGridResults(final HttpServletRequest request, @RequestParam final String rows) {
        final JQGridResponse response = new JQGridResponse<>();
        List<VoucherReversalDTO> reversalRecords = new ArrayList<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        if (request.getSession().getAttribute(REVERSAL_GRID_DATA) != null) {
            reversalRecords = (List<VoucherReversalDTO>) request.getSession().getAttribute(REVERSAL_GRID_DATA);
        }
        response.setRows(reversalRecords);
        response.setTotal(reversalRecords.size());
        response.setRecords(reversalRecords.size());
        response.setPage(page);

        return response;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "reverse", method = RequestMethod.POST)
    public @ResponseBody String saveReversal(@Valid final VoucherReversalDTO dto, final HttpServletRequest request) {

        final List<VoucherReversalDTO> sessionData = (List<VoucherReversalDTO>) request.getSession()
                .getAttribute(REVERSAL_GRID_DATA);
        String result = StringUtils.EMPTY;
        final ResponseEntity<?> response = accountVoucherReversalService.validateDataForReversal(sessionData, dto,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (response.getStatusCode() == HttpStatus.OK) {
            dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            // check field id is not setting properly
            long fieldId = 0;
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                final TbLocationMas locMas = locMasService
                        .findById(UserSession.getCurrent().getLoggedLocId());
                if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                    fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                }
            }
            if (fieldId == 0) {
                throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
            }
            accountVoucherReversalService.saveOrUpdateVoucherReversal((List<String>) response.getBody(), dto,
                    fieldId,
                    UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId(),
                    Utility.getClientIpAddress(request));
            result = ApplicationSession.getInstance().getMessage("account.depositslip.result.success");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            result = (String) response.getBody();
        }

        return result;

    }

    @RequestMapping(params = "checkDepositSlip", method = RequestMethod.GET)
    public @ResponseBody String doCheckDepositSlipAgainstReceipt(@Valid final Long rowId, final HttpServletRequest request) {
        String result = MainetConstants.AccountConstants.N.getValue();
        if (accountVoucherReversalService.countDepositSlipAlreadyGenerated(rowId,
                UserSession.getCurrent().getOrganisation().getOrgid())) {
            result = ApplicationSession.getInstance().getMessage("account.voucher.reversal.deposite");
        }
        return result;
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
        model.addAttribute(MODE, EDIT);
        populateModel(model, FormMode.EDIT);

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
        model.addAttribute(MODE, VIEW);
        populateModel(model, FormMode.VIEW);

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
        final String coaPrefix = PrefixConstants.AccountPrefix.COA.toString();
        final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.LookUp.INDIVIDUAL, coaPrefix,
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

    private boolean isCashOrCheque(final LookUp lookUp) {
        return MainetConstants.AccountConstants.CASH.getValue().equals(lookUp.getLookUpCode())
                || MainetConstants.AccountConstants.CHEQUE.getValue().equals(lookUp.getLookUpCode());
    }

    private boolean isWebOrRTGS(final LookUp lookUp) {
        return MainetConstants.AccountConstants.W.getValue().equals(lookUp.getLookUpCode())
                || MainetConstants.AccountConstants.RTGS.getValue().equals(lookUp.getLookUpCode());
    }

    private boolean isFDROrNEFT(final LookUp lookUp) {
        return MainetConstants.AccountConstants.FDR.getValue().equals(lookUp.getLookUpCode())
                || MainetConstants.AccountConstants.NEFT.getValue().equals(lookUp.getLookUpCode());
    }

    private boolean isBankOrDemandDraft(final LookUp lookUp) {
        return MainetConstants.AccountConstants.BANK.getValue().equals(lookUp.getLookUpCode())
                || MainetConstants.AccountConstants.DRAFT.getValue().equals(lookUp.getLookUpCode());
    }

}
