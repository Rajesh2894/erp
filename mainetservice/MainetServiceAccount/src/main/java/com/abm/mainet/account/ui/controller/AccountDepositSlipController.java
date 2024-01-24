package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.account.dto.AccountCashDepositeBean;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositSlipDTO;
import com.abm.mainet.account.dto.AccountChequeOrCashDepositeBean;
import com.abm.mainet.account.dto.AccountLedgerMasBean;
import com.abm.mainet.account.dto.DraweeBankDetailsBean;
import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.service.AccountChequeOrCashDepositeService;
import com.abm.mainet.account.service.AccountVoucherReversalService;
import com.abm.mainet.account.ui.model.AccountChequeAndDepositeModel;
import com.abm.mainet.account.ui.model.response.AccountChequeAndCashDepositeSlipResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountDepositSlip.html")
public class AccountDepositSlipController extends AbstractController {

    private static final String MAIN_LIST_NAME = "accountChequeOrCashDepositeBean";

    private static final String JSP_LIST = "tbAcChequeOrCashDeposite/formLayout";

    private static final String JSP_RECEIPT_LIST = "tbAcChequeOrCashDeposite_receipt/formLayout";

    private static final String JSP_FORM_GRID = "tbAcChequeOrCashDeposite/form_grid/base";

    private static final String JSP_LIST_TABLE = "tbAcChequeOrCashDeposite/receiptDetails/form";
    private static final String JSP_LIST_LEGERTABLE = "tbAcChequeOrCashDeposite/ledgerDetails/form";
    private static final String JSP_LIST_RECEIPT_LEGERTABLE = "tbAcChequeOrCashDeposite/ledgerReceiptDetails/form";
    private static final String JSP_LIST_CHEQUEDDPOTABLE = "tbAcChequeOrCashDeposite/chequeddpo/form";
    private static final String JSP_DEPOSIT_RECIEPT_VIEW = "tbAcChequeOrCashDeposite/PrintDepositSlip";
    private static final String DEPOSIT_SLIP_VIEW = "accountChequeOrCashDepositDTO";
    @Resource
    private AccountChequeOrCashDepositeService chequeOrCashService;

    @Resource
    private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;
    @Resource
    private TbBankmasterService bankAccountService;
    @Resource
    private AccountVoucherReversalService accountVoucherReversalService;
    @Resource
    private ILocationMasService locMasService;
	
    @Resource
	private IReceiptEntryService iReceiptEntryService;
    
    @Resource
	private DepartmentService departmentService;
	
    private static Logger LOGGER = Logger.getLogger(AccountDepositSlipController.class);

    List<AccountChequeOrCashDepositeBean> listOfReceiptDetails = new ArrayList<>();

    public AccountDepositSlipController() {
        super(AccountDepositSlipController.class, MAIN_LIST_NAME);
    }

    public AccountChequeAndDepositeModel getModel() {
        return ApplicationContextProvider.getApplicationContext().getBean(AccountChequeAndDepositeModel.class);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
    	binder.setAutoGrowCollectionLimit(5000);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        binder.registerCustomEditor(Date.class, MainetConstants.AccountDepositSlip.CLEARING_DATE,
                new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Date.class, MainetConstants.AccountDepositSlip.PAY_ORDER_DATE,
                new CustomDateEditor(dateFormat, true));
        
    }

    @RequestMapping()
    public String list(final Model model) {
        log("AccountChequeOrCashDepositeController 'form'");
        final UserSession userSession = UserSession.getCurrent();
        final AccountChequeOrCashDepositeBean bean = new AccountChequeOrCashDepositeBean();
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(MainetConstants.CHEQUE_DISHONOUR.PAY,
                userSession.getOrganisation());
        final List<LookUp> payList = new ArrayList<>();
        for (final LookUp looUp : paymentModeList) {
            if (looUp.getOtherField() != null) {
                if (MainetConstants.MENU.Y.equals(looUp.getOtherField())) {
                    payList.add(looUp);
                }
            }
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.PERMANENT_PAY_LIST, payList);
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS,
                userSession.getOrganisation().getOrgid());
        final List<Object[]> accountsList = bankAccountService
                .getActiveBankAccountList(userSession.getOrganisation().getOrgid(), statusId);
        final List<BankAccountMasterDto> accountList = new ArrayList<>();
        BankAccountMasterDto account = null;
        for (final Object[] obj : accountsList) {
            account = new BankAccountMasterDto();
            account.setBaAccountId((Long) obj[0]);
            account.setBaAccountName(obj[2].toString());
            account.setBankName(obj[3].toString());
            account.setFundId((Long) obj[4]);
            account.setBaAccountNo(obj[1].toString());
            accountList.add(account);
        }

        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                accountFieldMasterService.getFieldMasterStatusLastLevels(userSession.getOrganisation().getOrgid(),
                        userSession.getOrganisation(), userSession.getLanguageId()));
       
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.BANK_LIST, accountList);
        // model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.PERMANENT_PAY_LIST,
        // payList);
        model.addAttribute(MAIN_LIST_NAME, bean);
        listOfReceiptDetails.clear();
        final List<TbServiceReceiptMasBean> listOfReceiptDetails = new ArrayList<>();
        bean.setListOfReceiptDetails(listOfReceiptDetails);
        return JSP_FORM_GRID;
    }

    // grid
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountChequeAndCashDepositeSlipResponse gridData(final HttpServletRequest request,
            final Model model) throws Exception {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final AccountChequeAndCashDepositeSlipResponse response = new AccountChequeAndCashDepositeSlipResponse();
        response.setRows(listOfReceiptDetails);
        response.setTotal(listOfReceiptDetails.size());
        response.setRecords(listOfReceiptDetails.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, listOfReceiptDetails);
        return response;
    }

    @RequestMapping(params = "createDataForm", method = RequestMethod.POST)
    public String createDataForm(final Model model, final AccountChequeOrCashDepositeBean bean) throws Exception {
        log("AccountChequeOrCashDepositeController 'create Data form'");
        getModel().setViewMode(false);
        final List<TbServiceReceiptMasBean> listOfReceiptDetails = new ArrayList<>();
        bean.setListOfReceiptDetails(listOfReceiptDetails);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, listOfReceiptDetails);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        populateDenominations(model, bean);
        final List<LookUp> listOfPaymentMode = CommonMasterUtility.getLookUps(MainetConstants.CHEQUE_DISHONOUR.PAY,
                UserSession.getCurrent().getOrganisation());
        Long depositeMode = null;
        for (final LookUp lkp : listOfPaymentMode) {
            if (lkp.getLookUpCode().equals(bean)) {
                depositeMode = lkp.getLookUpId();
            }
        }
        model.addAttribute(MainetConstants.AccountDepositSlip.Receipt_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.LEDGER_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.D_RAW_E_BANK, false);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, false);
        populateDepartments(model);
        /*final List<DepartmentLookUp> deptList = CommonMasterUtility
                .getDepartmentForWS(UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);*/
        final List<LookUp> listOfDepSlipTypes = CommonMasterUtility
                .getLookUps(PrefixConstants.AccountPrefix.CFD.toString(), UserSession.getCurrent().getOrganisation());
        for (LookUp lookUp : listOfDepSlipTypes) {
            if (lookUp.getDefaultVal().equals(MainetConstants.Y_FLAG)) {
                bean.setDepositSlipType(lookUp.getLookUpId());
            }
        }
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_DEP_SLIP_TYPES, listOfDepSlipTypes);
        try {
			model.addAttribute("functionMastLastlvl",
					tbAcFunctionMasterService.getFunctionMasterStatusLastLevels(UserSession.getCurrent().getOrganisation().getOrgid(),
							UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId()));
		} catch (Exception e) {
		}

        getModel().setDepositeType(depositeMode);
        bean.setFromDate(getModel().getFromDate());
        bean.setToDate(getModel().getToDate());
        model.addAttribute(MAIN_LIST_NAME, bean);
        return JSP_LIST;
    }

    @RequestMapping(params = "displayDepositeSlipDetails", method = RequestMethod.POST)
    public String displayDepositeSlipData(@RequestParam("depositeSlipId") final Long depositeSlipId,
            @RequestParam(MainetConstants.MODE) final String viewmode, final Model model) {
        log("AccountChequeOrCashDepositeController 'create Data form'");
        getModel().setViewMode(true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        String page = null;

        final AccountChequeOrCashDepositeBean chequeOrCashBean = chequeOrCashService
                .getSlipDetailsUsingDepSlipId(depositeSlipId);
        String newDepositeType = chequeOrCashBean.getDepositeType();
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_CASH, chequeOrCashBean.getDepositeType());
        if (MainetConstants.MODE_CREATE.equals(chequeOrCashBean.getDepositeType())) {
            final List<AccountLedgerMasBean> listOfLedgerMasBean = chequeOrCashService
                    .LedgerDetailsView(chequeOrCashBean);
            chequeOrCashBean.setListOfLedgerDetails(listOfLedgerMasBean);
            model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, listOfLedgerMasBean);
            model.addAttribute(MainetConstants.AccountDepositSlip.LEDGER_DETAIL, true);
            model.addAttribute(MainetConstants.AccountDepositSlip.D_RAW_E_BANK, false);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, false);
        } else {
             List<TbServiceReceiptMasBean> listOfDraweeBank = chequeOrCashService.getDraweeBankDetailsView(
                    chequeOrCashBean.getDepositeSlipId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    chequeOrCashBean.getCoTypeFlag(), chequeOrCashBean.getDepositeType());
              if(CollectionUtils.isNotEmpty(listOfDraweeBank)) {
            	  if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
            		  listOfDraweeBank= listOfDraweeBank.stream().map(dto->{
            			   dto.setRmReceiptNo(dto.getDeptName().concat(dto.getRmRcptno().toString()));
            			   return dto;
            		  }).collect(Collectors.toList());
                  }
            	  else {
            		  listOfDraweeBank= listOfDraweeBank.stream().map(dto->{
           			   dto.setRmReceiptNo(dto.getRmRcptno().toString());
           			   return dto;
           		  }).collect(Collectors.toList());
            	  }
              }
            chequeOrCashBean.setListOfChequeDDPoDetails(listOfDraweeBank);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, true);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, listOfDraweeBank);
        }
        if (chequeOrCashBean.getSfeeMode() != null) {
            chequeOrCashBean.setDepositeType(String.valueOf(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(chequeOrCashBean.getSfeeMode())).getLookUpDesc()));
        }
        populateDenominations(model, chequeOrCashBean);
//D#162867
        chequeOrCashBean.setRmDate(chequeOrCashBean.getDepositeSlipDate());        model.addAttribute(MAIN_LIST_NAME, chequeOrCashBean);
        if ((chequeOrCashBean.getDepositTypeFlag().equals("R"))
                && (MainetConstants.MODE_CREATE.equals(newDepositeType))) {
            page = JSP_RECEIPT_LIST;
        } else {
            page = JSP_LIST;
        }
        return page;
    }

    @RequestMapping(params = "searchSavedReceiptDetails", method = RequestMethod.POST)
    public @ResponseBody int searchSavedReceiptDetails(@RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, @RequestParam("feeMode") final String feeMode,
            @RequestParam("account") final String account, @RequestParam("slipNumber") final String slipNumber)
            throws ParseException {
        listOfReceiptDetails = chequeOrCashService.getSavedReceiptDetails(fromDate, toDate, feeMode,
                UserSession.getCurrent().getOrganisation().getOrgid(), account, slipNumber);
        return listOfReceiptDetails.size();
    }

    @RequestMapping(params = "getReceiptDetails", method = RequestMethod.POST)
    public String getReceiptDetails(final AccountChequeOrCashDepositeBean bean, final BindingResult bindingResult,
            final Model model) throws ParseException {

        model.addAttribute(MainetConstants.AccountDepositSlip.Receipt_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.LEDGER_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.D_RAW_E_BANK, false);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, false);
        String returnPage = null;

        if (((bean.getSfromDate() != null) && !bean.getSfromDate().isEmpty())
                && ((bean.getStoDate() != null) && !bean.getStoDate().isEmpty())) {
            final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
            final Date frmDate = formatter.parse(bean.getSfromDate());
            final Date tDate = formatter.parse(bean.getStoDate());
            bean.setFromDate(frmDate);
            bean.setToDate(tDate);
        }
        bean.setFieldId(bean.getHiddenfieldId());
        bean.setFundId(bean.getHiddenfundId());
        bean.setDepositeType(
                bean.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
        final List<TbServiceReceiptMasBean> listOfReceiptMasBean = chequeOrCashService.getReceiptDetails(bean,
                UserSession.getCurrent().getOrganisation());
        bean.setListOfReceiptDetails(listOfReceiptMasBean);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, listOfReceiptMasBean);
        bean.setListOfLedgerDetails(null);
        bean.setListOfDraweeBank(null);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, null);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_DRAW_E_BANK, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        bean.setFromDate(getModel().getFromDate());
        bean.setToDate(getModel().getToDate());
        model.addAttribute(MAIN_LIST_NAME, bean);
        populateDenominations(model, bean);
        returnPage = JSP_LIST_TABLE;
        return returnPage;
    }

    @RequestMapping(params = "getLedgerDetails", method = RequestMethod.POST)
    public String getLedgerDetails(final AccountChequeOrCashDepositeBean bean, final BindingResult bindingResult,
            final Model model) throws ParseException {
        model.addAttribute(MainetConstants.AccountDepositSlip.Receipt_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.LEDGER_DETAIL, true);
        model.addAttribute(MainetConstants.AccountDepositSlip.D_RAW_E_BANK, false);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, false);
        String returnPage = null;
        Date frmDate = null;
        Date tDate = null;
        if (((bean.getSfromDate() != null) && !bean.getSfromDate().isEmpty())
                && ((bean.getStoDate() != null) && !bean.getStoDate().isEmpty())) {
            final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
            frmDate = formatter.parse(bean.getSfromDate());
            tDate = formatter.parse(bean.getStoDate());
        } else {
            frmDate = new Date();
            tDate = new Date();
            model.addAttribute(MainetConstants.AccountDepositSlip.SEARCH_TYPE,
                    MainetConstants.AccountDepositSlip.NORMAL);
        }
        if (StringUtils.isNotBlank(bean.getRmDate())) {
			final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
			frmDate = formatter.parse(bean.getRmDate());
			tDate = formatter.parse(bean.getRmDate());
		}
        String depSlipType = CommonMasterUtility.findLookUpCode(PrefixConstants.AccountPrefix.CFD.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(), bean.getDepositSlipType());
        List<AccountLedgerMasBean> listOfLedgerMasBean=null;
        if (MainetConstants.AccountDepositSlip.NORMAL.equalsIgnoreCase(bean.getSearchType())) {
            if (depSlipType.equals("LWD")) {
                listOfLedgerMasBean = chequeOrCashService.LedgerDetails(frmDate, tDate,
                        Long.valueOf(bean.getSfeeMode().replace(MainetConstants.operator.COMMA,
                                MainetConstants.CommonConstant.BLANK)),
                        null, bean.getFieldId(), bean.getDepartment(), UserSession.getCurrent().getOrganisation().getOrgid(), depSlipType,bean.getFuncId());
            } else if (depSlipType.equals("RWD")) {
            	listOfLedgerMasBean = chequeOrCashService.LedgerDetails(frmDate, tDate,
                        Long.valueOf(bean.getSfeeMode().replace(MainetConstants.operator.COMMA,
                                MainetConstants.CommonConstant.BLANK)),
                        null, bean.getFieldId(),bean.getDepartment(), UserSession.getCurrent().getOrganisation().getOrgid(),
                        depSlipType,bean.getFuncId());
            }
        } else {
            if (depSlipType.equals("LWD")) {
                listOfLedgerMasBean = chequeOrCashService.LedgerDetails(frmDate, tDate,
                        Long.valueOf(bean.getSfeeMode().replace(MainetConstants.operator.COMMA,
                                MainetConstants.CommonConstant.BLANK)),
                        null, bean.getFieldId(), bean.getDepartment(), UserSession.getCurrent().getOrganisation().getOrgid(),
                        depSlipType,bean.getFuncId());
            } else if (depSlipType.equals("RWD")) {
                listOfLedgerMasBean = chequeOrCashService.LedgerDetails(frmDate, tDate,
                        Long.valueOf(bean.getSfeeMode().replace(MainetConstants.operator.COMMA,
                                MainetConstants.CommonConstant.BLANK)),
                        null, bean.getFieldId(), bean.getDepartment(), UserSession.getCurrent().getOrganisation().getOrgid(),
                        depSlipType,bean.getFuncId());
            } else {
                listOfLedgerMasBean = null;
            }
        }
        
        List<Department> department = departmentService.getDepartments("A"); 
        if(CollectionUtils.isNotEmpty(listOfLedgerMasBean)) {
      	  if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
      		listOfLedgerMasBean= listOfLedgerMasBean.stream().map(dto->{
      			  for(Department dept:department) {
      				  if(dto.getDeptId()!=null && dept.getDpDeptid()==dto.getDeptId()) {
      					dto.setRmReceiptNo(dept.getDpDeptcode().concat(dto.getRmRcptno().toString()));
      				  }
      			  }
      			   return dto;
      		  }).collect(Collectors.toList());
            }
        }
        
        
        bean.setListOfLedgerDetails(listOfLedgerMasBean);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, listOfLedgerMasBean);
        bean.setListOfReceiptDetails(null);
        bean.setListOfDraweeBank(null);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_DRAW_E_BANK, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        bean.setFromDate(getModel().getFromDate());
        bean.setToDate(getModel().getToDate());
        if (listOfLedgerMasBean != null) {
            if (listOfLedgerMasBean.size() == 0) {
                bean.setIsEmpty(MainetConstants.MENU.Y);
            } else {
                bean.setIsEmpty(MainetConstants.MENU.N);
            }
        }
        model.addAttribute(MAIN_LIST_NAME, bean);
        populateDenominations(model, bean);
        if (MainetConstants.AccountDepositSlip.NORMAL.equalsIgnoreCase(bean.getSearchType())) {
            returnPage =JSP_LIST_RECEIPT_LEGERTABLE; //JSP_LIST_LEGERTABLE; #128607
        } else if (depSlipType.equals("RWD")) {
            returnPage = JSP_LIST_RECEIPT_LEGERTABLE;
        } else {
            returnPage = JSP_LIST_LEGERTABLE;
        }
        return returnPage;
    }

    @RequestMapping(params = "draweeBankDetails", method = RequestMethod.POST)
    public String getDraweeBankDetails(final AccountChequeOrCashDepositeBean bean, final BindingResult bindingResult,
            final Model model) throws ParseException {
        String returnPage = null;

        Date frmDate = null;
        Date tDate = null;

        if (((bean.getSfromDate() != null) && !bean.getSfromDate().isEmpty())
                && ((bean.getStoDate() != null) && !bean.getStoDate().isEmpty())) {
            final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
            frmDate = formatter.parse(bean.getSfromDate());
            tDate = formatter.parse(bean.getStoDate());
            bean.setFromDate(frmDate);
            bean.setToDate(tDate);
        }
        final List<DraweeBankDetailsBean> listOfDraweeBank = chequeOrCashService.getDraweeBankDetails(frmDate, tDate,
                UserSession.getCurrent().getOrganisation().getOrgid(), bean.getFundId(), bean.getFieldId());
        bean.setListOfDraweeBank(listOfDraweeBank);
        bean.setListOfLedgerDetails(null);
        bean.setListOfReceiptDetails(null);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_DRAW_E_BANK, listOfDraweeBank);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        model.addAttribute(MAIN_LIST_NAME, bean);
        populateDenominations(model, bean);
        returnPage = JSP_LIST_CHEQUEDDPOTABLE;
        return returnPage;
    }

    @RequestMapping(params = "chequeOrDDDetails", method = RequestMethod.POST)
    public String chequeOrDDDetails(final AccountChequeOrCashDepositeBean bean, final BindingResult bindingResult,
            final Model model) throws ParseException {
        String returnPage = null;        
        Date frmDate = null;
        Date tDate = null;
        Date rmDate=null;

        final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);

        if (((bean.getSfromDate() != null) && !bean.getSfromDate().isEmpty())
                && ((bean.getStoDate() != null) && !bean.getStoDate().isEmpty())) {
            frmDate = formatter.parse(bean.getSfromDate());
            tDate = formatter.parse(bean.getStoDate());
        }
        if(StringUtils.isNotBlank(bean.getRmDate())) {
        	rmDate = formatter.parse(bean.getRmDate());
        }

        final List<LookUp> listOfPaymentMode = CommonMasterUtility.getLookUps(MainetConstants.CHEQUE_DISHONOUR.PAY,
                UserSession.getCurrent().getOrganisation());
        List<TbServiceReceiptMasBean> listOfReceiptMasBean = null;
        for (final LookUp lkp : listOfPaymentMode) {
            if (lkp.getLookUpCode().equals(bean.getDepositeType())) {
                getModel().setDepTypeCheque(lkp.getLookUpId());

            }
        }

        listOfReceiptMasBean = chequeOrCashService.getChequeOrDDDetails(frmDate, tDate, getModel().getDepTypeCheque(),
                bean.getFieldId(), UserSession.getCurrent().getOrganisation().getOrgid(),rmDate,bean.getDepartment(),bean.getFuncId());

		if (CollectionUtils.isNotEmpty(listOfReceiptMasBean)) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				listOfReceiptMasBean = listOfReceiptMasBean.stream().map(m -> {
					m.setRmReceiptNo(m.getDeptName().concat(m.getRmRcptno().toString()));
					return m;
				}).collect(Collectors.toList());
			}
		}
        bean.setListOfChequeDDPoDetails(listOfReceiptMasBean);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_CHQ_DETAIL, listOfReceiptMasBean);
        if (listOfReceiptMasBean.size() == 0) {
            bean.setIsEmpty(MainetConstants.MENU.Y);
        } else {
            bean.setIsEmpty(MainetConstants.MENU.N);
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        bean.setFromDate(getModel().getFromDate());
        bean.setToDate(getModel().getToDate());
        model.addAttribute(MAIN_LIST_NAME, bean);
        bean.setListOfLedgerDetails(null);
        bean.setListOfReceiptDetails(null);
        model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, null);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, null);
        populateDenominations(model, bean);
        returnPage = JSP_LIST_CHEQUEDDPOTABLE;
        return returnPage;
    }

    public void populateDenominations(final Model model, final AccountChequeOrCashDepositeBean chequeOrCashDepBean) {
        final UserSession userSession = UserSession.getCurrent();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS,
                userSession.getOrganisation().getOrgid());
        final List<Object[]> accountsList = bankAccountService
                .getActiveBankAccountList(userSession.getOrganisation().getOrgid(), statusId);
        final List<BankAccountMasterDto> accountList = new ArrayList<>();
        BankAccountMasterDto account = null;
        for (final Object[] obj : accountsList) {
            account = new BankAccountMasterDto();
            account.setBaAccountId((Long) obj[0]);
            account.setBaAccountName(obj[2].toString());
            account.setBankName(obj[3].toString());
            account.setFundId((Long) obj[4]);
            account.setBaAccountNo(obj[1].toString());
            accountList.add(account);
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.BANK_LIST, accountList);
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if ((null != payList.getOtherField() && (MainetConstants.FlagY).equals(payList.getOtherField())) ) { //#156418
                paymentMode.add(payList);
            }
        }
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.PERMANENT_PAY_LIST, paymentMode);

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp statusDeptLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ChequeDishonour.DWR, PrefixConstants.ChequeDishonour.CFD, langId, organisation);
        if (statusDeptLookup != null) {
        	populateDepartments(model);
            /*final List<DepartmentLookUp> deptList = CommonMasterUtility
                    .getDepartmentForWS(UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);*/
            model.addAttribute(MainetConstants.AccountBudgetCode.DEPT_STATUS, MainetConstants.MENU.Y);
        }

        final LookUp statusFieldLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                PrefixConstants.ChequeDishonour.FLR, PrefixConstants.ChequeDishonour.CFD, langId, organisation);
        if (statusFieldLookup != null) {
            model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                    accountFieldMasterService.getFieldMasterStatusLastLevels(
                            UserSession.getCurrent().getOrganisation().getOrgid(),
                            UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId()));
            model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MENU.Y);
        }

        if (!getModel().isViewMode()) {
            final List<LookUp> denLookupList = CommonMasterUtility.getLookUps(AccountPrefix.DEN.toString(),
                    userSession.getOrganisation());
            model.addAttribute(MainetConstants.CONTRA_VOUCHER_ENTRY.DEN_LOOKUP_LIST, denLookupList);
            final Iterator<LookUp> denListItr = denLookupList.iterator();
            Iterator<AccountCashDepositeBean> CashDep = null;
            if (chequeOrCashDepBean.getCashDep() != null) {
                CashDep = chequeOrCashDepBean.getCashDep().iterator();
            }
            final List<AccountCashDepositeBean> listOfBean = new ArrayList<>();
            AccountCashDepositeBean bean = null;
            if ((denListItr != null) && (CashDep != null)) {
                while (denListItr.hasNext()) {
                    while (CashDep.hasNext()) {
                        final AccountCashDepositeBean CashDep2 = CashDep.next();
                        final LookUp lkp = denListItr.next();
                        bean = new AccountCashDepositeBean();
                        bean.setDenomination(CashDep2.getDenomination());
                        bean.setTbComparamDet(lkp.getLookUpId());
                        bean.setDenomDesc(lkp.getLookUpCode());
                        bean.setDenCode(lkp.getLookUpDesc());
                        listOfBean.add(bean);
                    }
                }
            } else {
                if (denListItr != null) {
                    while (denListItr.hasNext()) {
                        final LookUp lkp = denListItr.next();
                        bean = new AccountCashDepositeBean();
                        bean.setTbComparamDet(lkp.getLookUpId());
                        bean.setDenomDesc(lkp.getLookUpCode());
                        bean.setDenCode(lkp.getLookUpDesc());
                        listOfBean.add(bean);
                    }
                }
            }
            chequeOrCashDepBean.setCashDep(listOfBean);
        }

        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                accountFieldMasterService.getFieldMasterStatusLastLevels(userSession.getOrganisation().getOrgid(),
                        userSession.getOrganisation(), userSession.getLanguageId()));

    }

    @RequestMapping(params = "save", method = RequestMethod.POST)
    public String searchSavedReceiptDetails(AccountChequeOrCashDepositeBean bean, final BindingResult bindingResult,
            final Model model, final HttpServletRequest httpServletRequest) throws Exception {
        BigDecimal recpttotalamount = BigDecimal.ZERO;
        bean.setDepositeType(bean.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.WHITE_SPACE));
        final String feemode = bean.getSfeeMode();
        String returnPage = null;

        final UserSession userSession = UserSession.getCurrent();
        final Long orgId = userSession.getOrganisation().getOrgid();
        if (bean.getDepositSlipTypeId() == null) {
            Long depSlipTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RWD",
                    PrefixConstants.AccountPrefix.CFD.toString(), orgId);
            bean.setDepositSlipType(depSlipTypeId);
        } else {
            bean.setDepositSlipType(bean.getDepositSlipTypeId());
        }
        if ((feemode != null) && (feemode.equalsIgnoreCase(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_P)
                || feemode.equalsIgnoreCase(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q)
                || feemode.equalsIgnoreCase(MainetConstants.MENU.D)|| feemode.equalsIgnoreCase(MainetConstants.MENU.PC))) {
            if ((bean.getDepositDateCheque() != null) && !bean.getDepositDateCheque().isEmpty()) {
                bean.setDepositeSlipDate(bean.getDepositDateCheque());
            }
        } else {
            if ((bean.getDepositDateLedger() != null) && !bean.getDepositDateLedger().isEmpty()) {
                bean.setDepositeSlipDate(bean.getDepositDateLedger());
            }
        }

        if ((feemode != null) && feemode.equalsIgnoreCase(MainetConstants.MODE_CREATE)) {
            if ((bean.getCashdepositeType() != null)
                    && bean.getCashdepositeType().equalsIgnoreCase(MainetConstants.MENU.R)) {
                for (final TbServiceReceiptMasBean receiptBean : bean.getListOfReceiptDetails()) {
                    recpttotalamount = recpttotalamount.add(new BigDecimal(receiptBean.getRmAmount()));
                }
                bean.setListOfChequeDDPoDetails(null);
                bean.setListOfLedgerDetails(null);
                bean.setListOfDraweeBank(null);
                if (bean.getTotal().compareTo(recpttotalamount) < 0) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.CommonConstant.BLANK, MainetConstants.BankParam.AMT, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage("accounts.depositslip.total.amount")));
                }
            }
        }
        if ((feemode != null) && (feemode.equalsIgnoreCase(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_P)
                || feemode.equalsIgnoreCase(PrefixConstants.CHEQUE_DISHONOUR.PREFIX_CODE_Q)
                || feemode.equalsIgnoreCase(MainetConstants.MENU.D)|| feemode.equalsIgnoreCase(MainetConstants.MENU.PC))) {
            bean.setListOfReceiptDetails(null);
            bean.setListOfLedgerDetails(null);
        }
        final VoucherPostDTO postDTO = new VoucherPostDTO();
        postDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        postDTO.setVoucherSubTypeId(
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
                        PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
        postDTO.setDepartmentId(bean.getDepartment());

        if (!bindingResult.hasErrors()) {

            Date frmDate = null;
            Date tDate = null;
            if (((bean.getSfromDate() != null) && !bean.getSfromDate().isEmpty())
                    && ((bean.getStoDate() != null) && !bean.getStoDate().isEmpty())) {
                final SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
                frmDate = formatter.parse(bean.getSfromDate());
                tDate = formatter.parse(bean.getStoDate());
            }
            final int langId = userSession.getLanguageId();
            final Long empId = userSession.getEmployee().getEmpId();
            long fieldId = 0;
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
                if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                    fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                }
            }
            if (fieldId == 0) {
                throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
            }
            bean.setFieldId(fieldId);
            bean.setFromDate(frmDate);
            bean.setToDate(tDate);
            if (bean.getSfromDate() == null || bean.getSfromDate().isEmpty()) {
                bean.setFromDate(new Date());
            }
            if (bean.getStoDate() == null || bean.getSfromDate().isEmpty()) {
                bean.setToDate(new Date());
            }
            bean.setClearingDate(new Date());
            bean.setDepositeType(
                    bean.getSfeeMode().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK));
            bean = chequeOrCashService.saveRecords(bean, orgId, langId, empId,
                    Utility.getClientIpAddress(httpServletRequest));
            bean.setTemplateExist(MainetConstants.MENU.Y);
            model.addAttribute(MainetConstants.CommonConstants.SUCCESS_URL,
                    MainetConstants.AccountDepositSlip.ACC_DEPOSIT_URL);
            model.addAttribute(MAIN_LIST_NAME, bean);
            httpServletRequest.getSession().setAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST, bean);
            returnPage = JSP_LIST;
        } else {
            model.addAttribute(MainetConstants.CONTRA_VOUCHER_ENTRY.DEN_LOOKUP_LIST, bean.getCashDep());
            populateDenominations(model, bean);
            returnPage = JSP_LIST;
        }

        return returnPage;
    }

    @RequestMapping(params = "checkTemplate")
    public @ResponseBody String checkTemplate(@RequestParam("depMode") final Long depMode,
            @RequestParam("deptId") final Long deptId) {

        final VoucherPostDTO postDTO = new VoucherPostDTO();
        String templateExistFlag = null;
        postDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        postDTO.setVoucherSubTypeId(
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ChequeDishonour.DS,
                        PrefixConstants.REV_TYPE_CPD_VALUE, UserSession.getCurrent().getOrganisation().getOrgid()));
        // postDTO.setDepartmentId(deptId);
        final boolean existTempalte = chequeOrCashService.checkTemplateType(postDTO,
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.PN,
                        PrefixConstants.ContraVoucherEntry.MTP, UserSession.getCurrent().getOrganisation().getOrgid()),
                CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.ContraVoucherEntry.CV,
                        MainetConstants.AccountBillEntry.VOT, UserSession.getCurrent().getOrganisation().getOrgid()));

        if (!existTempalte) {
            templateExistFlag = MainetConstants.MENU.N;
        } else {
            templateExistFlag = MainetConstants.MENU.Y;
        }
        return templateExistFlag;
    }

    /**
     * this method being used from Voucher Reversal form
     * 
     * @param gridId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(params = "viewDepositSlipDetail")
    public String openDepositSlipViewForm(@RequestParam("gridId") final long gridId, final HttpServletRequest request,
            final Model model) {
        log("AccountChequeOrCashDepositeController 'create Data form'");
        getModel().setViewMode(true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, true);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.IS_VIEW_MODE, getModel().isViewMode());
        String page = null;

        final AccountChequeOrCashDepositeBean chequeOrCashBean = chequeOrCashService
                .getSlipDetailsUsingDepSlipId(gridId);
        model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_CASH, chequeOrCashBean.getDepositeType());
        if (MainetConstants.MODE_CREATE.equals(chequeOrCashBean.getDepositeType())) {

            final List<AccountLedgerMasBean> listOfLedgerMasBean = chequeOrCashService
                    .LedgerDetailsView(chequeOrCashBean);
            chequeOrCashBean.setListOfLedgerDetails(listOfLedgerMasBean);
            model.addAttribute(MainetConstants.AccountDepositSlip.LIST_LEDGER_DETAIL, listOfLedgerMasBean);
            model.addAttribute(MainetConstants.AccountDepositSlip.LEDGER_DETAIL, true);
            model.addAttribute(MainetConstants.AccountDepositSlip.D_RAW_E_BANK, false);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, false);
        } else {
            final List<TbServiceReceiptMasBean> listOfDraweeBank = chequeOrCashService.getDraweeBankDetailsView(
                    chequeOrCashBean.getDepositeSlipId(), UserSession.getCurrent().getOrganisation().getOrgid(),
                    chequeOrCashBean.getCoTypeFlag(), chequeOrCashBean.getDepositeType());
            chequeOrCashBean.setListOfChequeDDPoDetails(listOfDraweeBank);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.CHEQUE_OR_DD, true);
            model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.RECEIPT_DETAIL, listOfDraweeBank);
        }
        if (chequeOrCashBean.getSfeeMode() != null) {
            chequeOrCashBean.setDepositeType(String.valueOf(CommonMasterUtility
                    .getNonHierarchicalLookUpObject(Long.valueOf(chequeOrCashBean.getSfeeMode())).getLookUpDesc()));
        }
        populateDenominations(model, chequeOrCashBean);
        model.addAttribute(MAIN_LIST_NAME, chequeOrCashBean);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.MODE_FLAG, MainetConstants.RnLCommon.MODE_VIEW);
        request.getSession().setAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, MainetConstants.RnLCommon.MODE_VIEW);
        page = JSP_LIST;
        return page;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(params = "reverse", method = RequestMethod.POST)
    public @ResponseBody String saveReversal(@Valid final VoucherReversalDTO dto, final HttpServletRequest request) {

        final List<VoucherReversalDTO> sessionData = (List<VoucherReversalDTO>) request.getSession()
                .getAttribute(MainetConstants.AccountBillEntry.REVERSAL_GRID_DATA);
        String result = StringUtils.EMPTY;
        final ResponseEntity<?> response = chequeOrCashService.validateDataForDepositSlipReversal(sessionData, dto,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (response.getStatusCode() == HttpStatus.OK) {
            dto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            dto.setLangId(UserSession.getCurrent().getLanguageId());
            long fieldId = 0;
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
                if ((locMas.getLocRevenueWZMappingDto() != null) && !locMas.getLocRevenueWZMappingDto().isEmpty()) {
                    fieldId = locMas.getLocRevenueWZMappingDto().get(0).getCodIdRevLevel1();
                }
            }
            if (fieldId == 0) {
                throw new NullPointerException("fieldId is not linked with Location Master for[locId="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocId() + ",locName="
                        + UserSession.getCurrent().getEmployee().getTbLocationMas().getLocNameEng() + "]");
            }
            chequeOrCashService.reverseDepositSlip((List<String[]>) response.getBody(), dto, fieldId,
                    UserSession.getCurrent().getOrganisation().getOrgid(), Utility.getClientIpAddress(request));
            result = ApplicationSession.getInstance().getMessage("account.depositslip.result.success");
        } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            result = (String) response.getBody();
        }

        return result;

    }

    @RequestMapping(params = "onTransactionDate", method = RequestMethod.GET)
    public @ResponseBody String validateTransactionDate(@RequestParam("transactionDate") final String transactionDate,
            @RequestParam(MainetConstants.AccountDepositSlip.SEARCH_TYPE) final String searchType,
            @RequestParam("fromDate") final String fromDateStr, @RequestParam("toDate") final String toDateStr,
            final ModelMap model) {
        String response;
        try {
            final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                    UserSession.getCurrent().getOrganisation());
            final String date = lookUp.getOtherField();
            Objects.requireNonNull(date, ApplicationSession.getInstance().getMessage("account.depositslip.livedate"));
            final Date sliDate = Utility.stringToDate(date);
            final Date transDate = Utility.stringToDate(transactionDate);
            if (MainetConstants.AccountDepositSlip.NORMAL.equalsIgnoreCase(searchType)) {
                if ((transDate.getTime() >= sliDate.getTime()) && (transDate.getTime() <= new Date().getTime())) {
                    response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.ok");
                } else {
                    response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.receiptdate")
                            + date
                            + ApplicationSession.getInstance().getMessage("accounts.depositslip.response.currentdate");
                }
            } else {
                final Date fromDate = Utility.stringToDate(fromDateStr);
                final Date toDate = Utility.stringToDate(toDateStr);
                if ((fromDate.getTime() < transDate.getTime()) && (transDate.getTime() >= toDate.getTime())) {
                    response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.ok");
                } else {
                    response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.receiptdate")
                            + date
                            + ApplicationSession.getInstance().getMessage("accounts.depositslip.response.currentdate");
                }
            }

        } catch (final Exception ex) {
            response = ApplicationSession.getInstance().getMessage("account.depositslip.response.servererror");
            LOGGER.error("Error while validating Receipt date from SLI Prefix", ex);
        }
        return response;
    }

    @RequestMapping(params = "printDepositReciept", method = RequestMethod.POST)
    public String printDepositReciepts(final Model model, final HttpServletRequest request) {
        BigDecimal picescount = new BigDecimal(0.00);
        final UserSession userSession = UserSession.getCurrent();
        final List<AccountChequeOrCashDepositSlipDTO> listOfBean = new ArrayList<>();
        final AccountChequeOrCashDepositSlipDTO depositSlipViewbeans = new AccountChequeOrCashDepositSlipDTO();

        final AccountChequeOrCashDepositeBean depositSipviewbean = (AccountChequeOrCashDepositeBean) request
                .getSession().getAttribute(MainetConstants.AccountReceiptEntry.RECEIPT_LIST);

        BigDecimal sumOfAmount = new BigDecimal(0.00);
        BigDecimal sumOfDecimalAmount = new BigDecimal(0.00);
        if (depositSipviewbean.getDepositDateLedger() != null) {
            depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositDateLedger());
        }
        Long bankAccountId = depositSipviewbean.getBaAccountid();
        List<Object[]> beanlist = bankAccountService.getBankBranchOrg(bankAccountId,
                userSession.getOrganisation().getOrgid());
        if (!beanlist.isEmpty()) {

            for (final Object[] obj : beanlist) {
                depositSlipViewbeans.setBank((String) obj[0]);
                depositSlipViewbeans.setBranch((String) obj[1]);
                depositSlipViewbeans.setBankaccountNo((String) obj[2]);
                depositSlipViewbeans.setBankAccountTypeCode((Long) obj[3]);
                depositSlipViewbeans.setBankAccountName((String) obj[4]);
            }

        }
        depositSlipViewbeans.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        final String totalAmountInWords = Utility.convertBigNumberToWord(depositSipviewbean.getTotal());

        depositSlipViewbeans.setTotalAmountInWords(totalAmountInWords);
        depositSlipViewbeans.setTotal(depositSipviewbean.getTotal());
        depositSlipViewbeans.setSlipNumber(depositSipviewbean.getDepositeSlipNo());
        depositSlipViewbeans.setOrgId(userSession.getOrganisation().getOrgid());

        if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.C))

        {

            final List<LookUp> denLookupList = CommonMasterUtility.getLookUps(AccountPrefix.DEN.toString(),
                    userSession.getOrganisation());

            final Iterator<LookUp> denListItr = denLookupList.iterator();
            Iterator<AccountCashDepositeBean> CashDep = null;
            if (depositSipviewbean.getCashDep() != null) {
                CashDep = depositSipviewbean.getCashDep().iterator();
            }

            AccountChequeOrCashDepositSlipDTO bean = null;
            if ((denListItr != null) && (CashDep != null)) {
                while (denListItr.hasNext()) {
                    while (CashDep.hasNext()) {
                        final AccountCashDepositeBean CashDep2 = CashDep.next();
                        final LookUp lkp = denListItr.next();
                        bean = new AccountChequeOrCashDepositSlipDTO();
                        if (CashDep2.getDenomination() != null && lkp.getLookUpCode() != null
                                && lkp.getLookUpCode() != "") {
                            bean.setNumberOfRupes(CashDep2.getDenomination());
                            bean.setRupesTpye(lkp.getLookUpDesc());
                            if (lkp.getLookUpCode().startsWith(MainetConstants.ZERO)&&!lkp.getLookUpCode().startsWith(MainetConstants.ZERO_ZERO)) {
                                BigDecimal multiplynumber = new BigDecimal(lkp.getLookUpCode());
                                BigDecimal numberOfCount = BigDecimal.valueOf(CashDep2.getDenomination());
                                BigDecimal amount = multiplynumber.multiply(numberOfCount);
                                bean.setIndianCurrencyDecimalAmount((CommonMasterUtility
                                        .getAmountInIndianCurrency(amount.setScale(2, RoundingMode.HALF_DOWN))));
                                picescount = numberOfCount.add(picescount);
                                sumOfDecimalAmount = sumOfDecimalAmount.add(amount);
                                listOfBean.add(bean);

                            } else {
                                BigDecimal multiplynumber = new BigDecimal(lkp.getLookUpCode());
                                BigDecimal numberOfCount = BigDecimal.valueOf(CashDep2.getDenomination());
                                BigDecimal amount = multiplynumber.multiply(numberOfCount);
                                bean.setIndainCurrencyAmount(CommonMasterUtility
                                        .getAmountInIndianCurrency(amount.setScale(2, RoundingMode.HALF_DOWN)));
                                picescount = numberOfCount.add(picescount);
                                sumOfAmount = sumOfAmount.add(amount);
                                listOfBean.add(bean);
                            }
                        }

                    }
                }
            }
            // depositSlipViewbeans.setAmount(sumOfAmount);
            if (sumOfAmount == null || sumOfAmount.equals(new BigDecimal(0.00))) {
                sumOfAmount = depositSipviewbean.getTotal();
            }
            depositSlipViewbeans.setIndainCurrencyAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfAmount));
            depositSlipViewbeans.setIndianCurrencyDecimalAmount(CommonMasterUtility
                    .getAmountInIndianCurrency(sumOfDecimalAmount.setScale(2, RoundingMode.HALF_EVEN)));
            depositSlipViewbeans.setTotalAmount(CommonMasterUtility
                    .getAmountInIndianCurrency(depositSipviewbean.getTotal()));
        } else if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.B)||depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.Q)
                || depositSipviewbean.getDepositeType().equals(MainetConstants.WORKFLOWTYPE.Flag_D) || depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.P) || depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.PC)||depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.POS)||depositSipviewbean.getDepositeType().equals(MainetConstants.PAYMODE.WEB)) {

            Iterator<TbServiceReceiptMasBean> chequeDDetails = null;
            if(depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.Q)) {
            	List<TbServiceReceiptMasBean>  beanList=	combineDuplicateChequeDetails(depositSipviewbean.getListOfChequeDDPoDetails());
            	depositSipviewbean.setListOfChequeDDPoDetails(beanList);
            }
            if (depositSipviewbean.getListOfChequeDDPoDetails() != null) {
                chequeDDetails = depositSipviewbean.getListOfChequeDDPoDetails().iterator();

                AccountChequeOrCashDepositSlipDTO bean = null;
                if (chequeDDetails != null) {
                    while (chequeDDetails.hasNext()) {
                        final TbServiceReceiptMasBean chequeDeposit = chequeDDetails.next();

                        if (chequeDeposit.getSelectDs() != null) {
                            bean = new AccountChequeOrCashDepositSlipDTO();
                            bean.setPayOrderNo(chequeDeposit.getPayOrderNo());
                            bean.setIndainCurrencyAmount(CommonMasterUtility.getAmountInIndianCurrency(
                                    new BigDecimal(chequeDeposit.getRmAmount()).setScale(2, RoundingMode.HALF_EVEN)));

                            bean.setDepositBankName(chequeDeposit.getDrawnOnBank());
                            bean.setBranch(chequeDeposit.getBranch());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
                            String cheqDate = dateFormat.format(chequeDeposit.getPayOrderDt());
                            bean.setChequeDate(cheqDate);
                            bean.setModeId(chequeDeposit.getModeId());
                            listOfBean.add(bean);
                        }
                    }
                }
            }

            if (depositSipviewbean.getDepositDateCheque() != null) {
                depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositDateCheque());
            }

            depositSlipViewbeans.setIndainCurrencyAmount(
                    CommonMasterUtility.getAmountInIndianCurrency(depositSipviewbean.getTotal()));

        }
        if (picescount.signum() != 0) {
            depositSlipViewbeans.setPicesCount(picescount.longValue());
        }
        String accountType = "";
        accountType = CommonMasterUtility.findLookUpDesc(PrefixConstants.ACT.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(), depositSlipViewbeans.getBankAccountTypeCode());
        if (accountType != null) {
            depositSlipViewbeans.setBankAccountType(accountType);
        }
        if(depositSipviewbean.getDepositeType()!= null){
        depositSlipViewbeans.setDepositeTypeCode(depositSipviewbean.getDepositeType());
        }
        if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.C)) {
        	 depositSlipViewbeans.setDenomination(listOfBean);
        }else {        
        depositSlipViewbeans.setDenomination(listOfBean.stream().filter(r->r!=null && r.getDepositBankName()!=null).sorted(Comparator.comparing(AccountChequeOrCashDepositSlipDTO::getDepositBankName))
        		.collect(Collectors.toList()));
        }
        depositSlipViewbeans.setUserName(UserSession.getCurrent().getEmployee().getFullName());
        model.addAttribute(DEPOSIT_SLIP_VIEW, depositSlipViewbeans);
        return JSP_DEPOSIT_RECIEPT_VIEW;

    }

    private List<TbServiceReceiptMasBean> combineDuplicateChequeDetails(
			List<TbServiceReceiptMasBean> listOfChequeDDPoDetails) {
    	List<TbServiceReceiptMasBean> beanList=new LinkedList<>();
    	Map<Object, List<TbServiceReceiptMasBean>> grouped =
    			listOfChequeDDPoDetails.stream().collect(Collectors.groupingBy(o->Arrays.asList(o.getPayOrderNo(),o.getPayOrderDt(),o.getDrawnOnBank())));
    	for (Entry<Object, List<TbServiceReceiptMasBean>> entry : grouped.entrySet()) {
    		if(CollectionUtils.isNotEmpty(entry.getValue())) {
    			List<TbServiceReceiptMasBean> listBean=entry.getValue();
    			Double  d=listBean.stream().mapToDouble(o->Double.valueOf(o.getRmAmount())).sum();
    			listBean.get(0).setRmAmount(d.toString());
    			beanList.add(listBean.get(0));
    		}
        }
    	return beanList;
	}

	@RequestMapping(params = "printReportDepositeSlipDetails", method = RequestMethod.POST)
    public String printReportDepositeSlip(@RequestParam("depositeSlipId") final Long depositeSlipId, final Model model,
            final HttpServletRequest request) {
        BigDecimal picescount = new BigDecimal(0.00);
        final UserSession userSession = UserSession.getCurrent();
        final List<AccountChequeOrCashDepositSlipDTO> listOfBean = new ArrayList<>();
        final AccountChequeOrCashDepositSlipDTO depositSlipViewbeans = new AccountChequeOrCashDepositSlipDTO();
        final AccountChequeOrCashDepositeBean depositSipviewbean = chequeOrCashService
                .getSlipDetailsUsingDepSlipId(depositeSlipId);

        BigDecimal sumOfAmount = new BigDecimal(0.00);
        BigDecimal sumOfDecimalAmount = new BigDecimal(0.00);
        if (depositSipviewbean.getDepositDateLedger() != null) {
            depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositDateLedger());
        }
        else {
        	depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositeSlipDate());
        }
        Long bankAccountId = depositSipviewbean.getBaAccountid();
        List<Object[]> beanlist = bankAccountService.getBankBranchOrg(bankAccountId,
                userSession.getOrganisation().getOrgid());
        if (!beanlist.isEmpty()) {

            for (final Object[] obj : beanlist) {
                depositSlipViewbeans.setBank((String) obj[0]);
                depositSlipViewbeans.setBranch((String) obj[1]);
                depositSlipViewbeans.setBankaccountNo((String) obj[2]);
                depositSlipViewbeans.setBankAccountTypeCode((Long) obj[3]);
                depositSlipViewbeans.setBankAccountName((String) obj[4]);
            }

        }
        depositSlipViewbeans.setOrganisationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
        String totalAmountInWords = null;
        if (depositSipviewbean.getDepositeAmount() != null) {
            totalAmountInWords = Utility.convertBigNumberToWord(depositSipviewbean.getDepositeAmount());
        }
        depositSlipViewbeans.setTotalAmountInWords(totalAmountInWords);
        depositSlipViewbeans.setTotal(depositSipviewbean.getTotal());
        depositSlipViewbeans.setSlipNumber(depositSipviewbean.getDepositeSlipNo());
        depositSlipViewbeans.setOrgId(userSession.getOrganisation().getOrgid());
        if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.C)) {
            List<AccountChequeOrCashDepositSlipDTO> denominationCashDetail = chequeOrCashService
                    .getDenominationCashDetails(depositeSlipId, userSession.getOrganisation().getOrgid());
            for (AccountChequeOrCashDepositSlipDTO accountChequeOrCashDepositSlipDTO : denominationCashDetail) {

                AccountChequeOrCashDepositSlipDTO bean = new AccountChequeOrCashDepositSlipDTO();
                if (accountChequeOrCashDepositSlipDTO.getNumberOfRupes() != null
                        && accountChequeOrCashDepositSlipDTO.getRupesTpye() != null
                        && accountChequeOrCashDepositSlipDTO.getRupesTpye() != "") {
                    bean.setNumberOfRupes(accountChequeOrCashDepositSlipDTO.getNumberOfRupes());
                    bean.setRupesTpye(accountChequeOrCashDepositSlipDTO.getRupesTpye());
                    if (accountChequeOrCashDepositSlipDTO.getRupesTpye().startsWith(MainetConstants.ZERO)) {
                        BigDecimal multiplynumber = new BigDecimal(accountChequeOrCashDepositSlipDTO.getRupesTpye());
                        BigDecimal numberOfCount = BigDecimal
                                .valueOf(accountChequeOrCashDepositSlipDTO.getNumberOfRupes());
                        BigDecimal amount = multiplynumber.multiply(numberOfCount);
                        bean.setIndianCurrencyDecimalAmount((CommonMasterUtility
                                .getAmountInIndianCurrency(amount.setScale(2, RoundingMode.HALF_DOWN))));
                        picescount = numberOfCount.add(picescount);
                        sumOfDecimalAmount = sumOfDecimalAmount.add(amount);
                        listOfBean.add(bean);
                    } else {
                        BigDecimal multiplynumber = new BigDecimal(accountChequeOrCashDepositSlipDTO.getRupesTpye());
                        BigDecimal numberOfCount = BigDecimal
                                .valueOf(accountChequeOrCashDepositSlipDTO.getNumberOfRupes());
                        BigDecimal amount = multiplynumber.multiply(numberOfCount);
                        bean.setIndainCurrencyAmount(CommonMasterUtility
                                .getAmountInIndianCurrency(amount.setScale(2, RoundingMode.HALF_DOWN)));
                        picescount = numberOfCount.add(picescount);
                        sumOfAmount = sumOfAmount.add(amount);
                        listOfBean.add(bean);
                    }
                }
            }

            if (depositSipviewbean.getDepositeSlipDate() != null) {
                depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositeSlipDate());
            }
            if (sumOfAmount == null || sumOfAmount.equals(new BigDecimal(0.00))) {
                sumOfAmount = depositSipviewbean.getDepositeAmount();
            }
            depositSlipViewbeans.setIndainCurrencyAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfAmount));
            depositSlipViewbeans.setIndianCurrencyDecimalAmount(CommonMasterUtility
                    .getAmountInIndianCurrency(sumOfDecimalAmount.setScale(2, RoundingMode.HALF_EVEN)));
            depositSlipViewbeans.setTotalAmount(CommonMasterUtility
                    .getAmountInIndianCurrency(depositSipviewbean.getDepositeAmount()));
        } else if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.B)||depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.Q)
                || depositSipviewbean.getDepositeType().equals(MainetConstants.WORKFLOWTYPE.Flag_D) ||depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.PC) ||depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.P)||depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.POS)||depositSipviewbean.getDepositeType().equals(MainetConstants.PAYMODE.WEB)) {
        	 List<AccountChequeOrCashDepositSlipDTO> bankDetails = chequeOrCashService.getBankDetails(depositeSlipId,
                    userSession.getOrganisation().getOrgid());
        	 if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.Q)) {
        		 bankDetails= combineChequeDetails(bankDetails);
        	 }
            if (bankDetails != null && !bankDetails.isEmpty()) {
                AccountChequeOrCashDepositSlipDTO bean = null;
                for (AccountChequeOrCashDepositSlipDTO beanlists : bankDetails) {

                    if (beanlists.getBranch() != null) {
                        bean = new AccountChequeOrCashDepositSlipDTO();
                        bean.setDepositBankName(beanlists.getBank());
                        bean.setBranch(beanlists.getBranch());
                        if(beanlists.getChequeNo()!=null)
                        bean.setPayOrderNo(beanlists.getChequeNo().toString());
                        bean.setChequeDate(beanlists.getChequeDate());
                        bean.setModeId(beanlists.getModeId());
                        bean.setIndainCurrencyAmount(
                                CommonMasterUtility.getAmountInIndianCurrency(beanlists.getAmount()));
                        listOfBean.add(bean);
                    }
                }
            }
            if (depositSipviewbean.getDepositeSlipDate() != null) {
                depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositeSlipDate());
            }

            if (depositSipviewbean.getDepositeAmount() != null) {
                depositSlipViewbeans.setIndainCurrencyAmount(
                        CommonMasterUtility.getAmountInIndianCurrency(depositSipviewbean.getDepositeAmount()));
            }

        } else if (depositSipviewbean.getDepositeType().equals(MainetConstants.CommonConstants.B)
                || depositSipviewbean.getDepositeType().equals(PrefixConstants.WATERMODULEPREFIX.RT)
                || depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.N)) {

            List<AccountChequeOrCashDepositSlipDTO> bankDetails = chequeOrCashService
                    .getBankAccountDetails(depositeSlipId, userSession.getOrganisation().getOrgid());

            if (bankDetails != null && !bankDetails.isEmpty()) {
                AccountChequeOrCashDepositSlipDTO bean = null;
                for (AccountChequeOrCashDepositSlipDTO beanlists : bankDetails) {

                    if (beanlists.getBranch() != null) {
                        bean = new AccountChequeOrCashDepositSlipDTO();
                        bean.setDepositBankName(beanlists.getBank());
                        bean.setBranch(beanlists.getBranch());
                        bean.setPayOrderNo(beanlists.getChequeNo().toString());
                        bean.setChequeDate(beanlists.getChequeDate());
                        bean.setModeId(beanlists.getModeId());
                        bean.setIndainCurrencyAmount(
                                CommonMasterUtility.getAmountInIndianCurrency(beanlists.getAmount()));
                        listOfBean.add(bean);
                    }
                }
            }
            if (depositSipviewbean.getDepositeSlipDate() != null) {
                depositSlipViewbeans.setRemittanceDate(depositSipviewbean.getDepositeSlipDate());
            }

            if (depositSipviewbean.getDepositeAmount() != null) {
                depositSlipViewbeans.setIndainCurrencyAmount(
                        CommonMasterUtility.getAmountInIndianCurrency(depositSipviewbean.getDepositeAmount()));
            }
        }
        if (depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.POS) || depositSipviewbean.getDepositeType().equals(MainetConstants.MENU.WB)){
        	if (depositSipviewbean.getDepositeAmount() != null) {
        		depositSlipViewbeans.setTotalAmount(CommonMasterUtility
                        .getAmountInIndianCurrency(depositSipviewbean.getDepositeAmount()));
            }
        }
        if (picescount.signum() != 0) {
            depositSlipViewbeans.setPicesCount(picescount.longValue());
        }
        String accountType = "";
        accountType = CommonMasterUtility.findLookUpDesc(PrefixConstants.ACT.toString(),
                UserSession.getCurrent().getOrganisation().getOrgid(), depositSlipViewbeans.getBankAccountTypeCode());
        if (accountType != null) {
            depositSlipViewbeans.setBankAccountType(accountType);
        }
        depositSlipViewbeans.setDenomination(listOfBean);
        depositSlipViewbeans.setDepositeTypeCode(depositSipviewbean.getDepositeType());
        model.addAttribute(DEPOSIT_SLIP_VIEW, depositSlipViewbeans);
        return JSP_DEPOSIT_RECIEPT_VIEW;

    }

    private List<AccountChequeOrCashDepositSlipDTO> combineChequeDetails(
			List<AccountChequeOrCashDepositSlipDTO> bankDetails) {
    	List<AccountChequeOrCashDepositSlipDTO> beanList=new LinkedList<>();
    	Map<Object, List<AccountChequeOrCashDepositSlipDTO>> grouped =
    			bankDetails.stream().collect(Collectors.groupingBy(o->Arrays.asList(o.getChequeDate(),o.getChequeNo(),o.getBank())));
    	for (Entry<Object, List<AccountChequeOrCashDepositSlipDTO>> entry : grouped.entrySet()) {
    		if(CollectionUtils.isNotEmpty(entry.getValue())) {
    			List<AccountChequeOrCashDepositSlipDTO> listBean=entry.getValue();
    			BigDecimal sum = listBean.stream().map(AccountChequeOrCashDepositSlipDTO::getAmount)
	                     .reduce(BigDecimal.ZERO, BigDecimal::add);
    			listBean.get(0).setAmount(sum);
    			beanList.add(listBean.get(0));
    		}
        }
    	return beanList;
	}

	@RequestMapping(params = "checkDepositSlipAmtExist", method = RequestMethod.POST)
    public @ResponseBody String checkDepositSlipAmountExists(final AccountChequeOrCashDepositeBean depositSipviewbean,
            final Model model, final HttpServletRequest request) {

        String date;
        if (depositSipviewbean.getSfromDate() != null && !depositSipviewbean.getSfromDate().isEmpty()) {
            date = depositSipviewbean.getSfromDate();
        } else if(depositSipviewbean.getRmDate() != null && !depositSipviewbean.getRmDate().isEmpty()) {
        	date	=depositSipviewbean.getRmDate();
        }
        else {
            Date newDate = new Date();
            date = Utility.dateToString(newDate);
        }
        BigDecimal depositFinalAmt = chequeOrCashService.getCheckDepositSlipAmountExists(Utility.stringToDate(date),
                UserSession.getCurrent().getOrganisation().getOrgid());
        return depositFinalAmt.toString();

    }

    private void populateDepartments(final Model model) {
	     List<DepartmentLookUp> deptList =null; /*CommonMasterUtility
				.getDepartmentForWS(UserSession.getCurrent().getOrganisation());*/
		 List<Department> department = departmentService.getDepartments("A"); 
		 deptList =department.stream().map(m->{
			 DepartmentLookUp lookup=new DepartmentLookUp();
			 lookup.setLookUpId(m.getDpDeptid());
			 lookup.setLookUpCode(m.getDpDeptcode());
			 lookup.setDefaultVal(m.getDpDeptdesc());
			 return lookup; 
		 }).collect(Collectors.toList());
		model.addAttribute(MainetConstants.CHEQUE_DISHONOUR.DEPT_LIST, deptList);
	}
   
}