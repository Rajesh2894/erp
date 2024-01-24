package com.abm.mainet.common.master.ui.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.dto.ChallanReportDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbCfcApplicationMstService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.master.ui.model.DepositReceiptEntryModel;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ReceiptDetailService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Saiprasad.Vengurlekar
 * @modified By Vishwajeet.kumar
 */
@Controller
@RequestMapping("/DepositReceiptEntry.html")
public class DepositReceiptEntryController extends AbstractFormController<DepositReceiptEntryModel> {

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    private TbBankmasterService banksMasterService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private BankMasterService bankMasterService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private IReceiptEntryService receiptEntryService;

    @Resource
    private TbTaxMasService tbTaxMasService;

    
    @Autowired
    private TbCfcApplicationMstService tbCfcservice;
    
    @Autowired
    private  IEmployeeService employeeServcie;
    
    @Autowired
    private ReceiptDetailService receiptDetailService;
    
    @Resource
	private TbServicesMstService tbServicesMstService;

    private static final Logger LOGGER = Logger.getLogger(DepositReceiptEntryController.class);

    /**
     * Used to default Deposit Receipt Entry Summary page
     * 
     * @param httpServletRequest
     * @return defaultResult
     * @throws Exception
     */

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest, final Model model) {
        sessionCleanup(httpServletRequest);
        final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
        List<TbServiceReceiptMasBean> receiptMasBeanList = receiptEntryService
                .getAllReceiptsByOrgId(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid(),
                        UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.ReceiptForm.RECEIPT_MASBEANLIST, receiptMasBeanList);
        model.addAttribute(MainetConstants.ReceiptForm.SERVICE_RECEIPTMASTER, tbServiceReceiptMasBean);
        populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
        //#151356
        if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL))
        	this.getModel().setEnvFlag(MainetConstants.FlagY);
        else
        	this.getModel().setEnvFlag(MainetConstants.FlagN);
        return defaultResult();
    }

    @RequestMapping(params = MainetConstants.Common_Constant.FORM, method = RequestMethod.POST)
    public ModelAndView formForCreate(final Model model) {
        final TbServiceReceiptMasBean tbServiceReceiptMasBean = new TbServiceReceiptMasBean();
        TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
        List<TbSrcptFeesDetBean> beanList = new ArrayList<TbSrcptFeesDetBean>();
        beanList.add(bean);
        tbServiceReceiptMasBean.setReceiptFeeDetail(beanList);
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        tbServiceReceiptMasBean.setDpDeptId(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
        tbServiceReceiptMasBean.setDeptName(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptdesc());
        }
        populateModel(model, tbServiceReceiptMasBean, FormMode.CREATE);
        model.addAttribute(MainetConstants.ReceiptForm.SERVICE_RECEIPTMAS, tbServiceReceiptMasBean);
        return new ModelAndView(MainetConstants.ReceiptForm.RECEIPT_ENTRY_FORM, MainetConstants.FORM_NAME, model);
    }

    private void populateModel(final Model model, final TbServiceReceiptMasBean tbServiceReceiptMasBean,
            final FormMode formMode) {

        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(orgid, vendorStatus);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);

        List<TbDepartment> departmentlist = tbDepartmentService.findAllMappedDepartments(orgid);
        // tbServiceReceiptMasBean.setTemplateExistsFlag(checkTemplate());
        model.addAttribute("departmentlist", departmentlist);
        final List<TbServicesMst> serviceMstList = tbServicesMstService.findActiveServiceByDeptIdAndNotActualSer(tbServiceReceiptMasBean.getDpDeptId(),orgid);
        model.addAttribute("serviceMasList", serviceMstList);
        List<Object[]> bankAccountList;
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility
                .lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, orgid);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgid, statusId);
        for (final Object[] bankAc : bankAccountList) {
            bankAccountMap.put((Long) bankAc[0],
                    bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
        }
        model.addAttribute(MainetConstants.ReceiptForm.BANK_ACCOUNT_MAP, bankAccountMap);
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode1 = new ArrayList<>();
        for (final LookUp looUp : paymentModeList) {
            if (looUp.getOtherField() != null) {
                if (MainetConstants.MENU.Y.equals(looUp.getOtherField())) {
                    paymentMode1.add(looUp);
                }
            }
        }
        final List<String> payeeList = receiptEntryService.getPayeeNames(orgid,
                UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
        model.addAttribute(MainetConstants.ReceiptForm.PAYEE_LIST, payeeList);
        model.addAttribute(MainetConstants.ReceiptForm.PAY_MODE, paymentMode1);
        final List<BankMasterEntity> customerBankList = bankMasterService.getBankList();
        model.addAttribute(MainetConstants.ReceiptForm.CUSTOMER_BANK_LIST, customerBankList);

        final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
                MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
        List<LookUp> newReciptVouTypeList = new ArrayList<LookUp>();
        for (LookUp lookUp : recieptVouType) {
            LookUp newReceiptCategory = new LookUp();
            if (lookUp.getLookUpCode().equals(MainetConstants.FlagM) || lookUp.getLookUpCode().equals(MainetConstants.FlagA)
                    || lookUp.getLookUpCode().equals(MainetConstants.FlagP)) {
                newReceiptCategory.setLookUpId(lookUp.getLookUpId());
                newReceiptCategory.setLookUpCode(lookUp.getLookUpCode());
                newReceiptCategory.setLookUpDesc(lookUp.getLookUpDesc());
                newReceiptCategory.setDescLangFirst(lookUp.getDescLangFirst());

                if (formMode == FormMode.CREATE) {
                    if (lookUp.getDefaultVal() != null && !lookUp.getDefaultVal().isEmpty()) {
                        if (lookUp.getDefaultVal().equals(MainetConstants.FlagY)) {
                            if (tbServiceReceiptMasBean.getRecCategoryType() == null ||
                                    tbServiceReceiptMasBean.getRecCategoryType().isEmpty()) {
                                tbServiceReceiptMasBean.setRecCategoryTypeId(lookUp.getLookUpId());
                                tbServiceReceiptMasBean.setRecCategoryType(MainetConstants.FlagM);
                            } else if (tbServiceReceiptMasBean.getRecCategoryType().equals(MainetConstants.FlagM)) {
                                tbServiceReceiptMasBean.setRecCategoryTypeId(lookUp.getLookUpId());
                                tbServiceReceiptMasBean.setRecCategoryType(MainetConstants.FlagM);
                            }
                        }
                    }
                }
                newReciptVouTypeList.add(newReceiptCategory);
            }
        }
        model.addAttribute(MainetConstants.ReceiptForm.RECEIPT_VOUTYPE, newReciptVouTypeList);

        if (formMode.equals(FormMode.CREATE)) {
            model.addAttribute(MainetConstants.ReceiptForm.FORM_MODE, MainetConstants.Actions.CREATE);
            if (tbServiceReceiptMasBean.getRecCategoryType() == null
                    || tbServiceReceiptMasBean.getRecCategoryType().isEmpty()) {
                populateBudgetCodes(model, tbServiceReceiptMasBean);
            } else if (tbServiceReceiptMasBean.getRecCategoryType().equals(MainetConstants.FlagM)) {
                populateBudgetCodes(model, tbServiceReceiptMasBean);
            }
            populateListOfTbAcFieldMasterItems(model, orgid);
        } /*
           * else if (formMode.equals(FormMode.EDIT)) { model.addAttribute("form_mode", "create"); populateViewBudgetCodes(model,
           * tbServiceReceiptMasBean); populateListOfTbAcFieldMasterItems(model, orgid); } else { model.addAttribute("form_mode",
           * "view"); populateViewBudgetCodes(model, tbServiceReceiptMasBean); populateListOfTbAcFieldMasterItems(model, orgid); }
           */
    }

    /*
     * private void populateViewBudgetCodes(Model model, TbServiceReceiptMasBean tbServiceReceiptMasBean) { final Organisation org
     * = UserSession.getCurrent().getOrganisation(); Map<Long, String> budgetAcHeadMap = new HashMap<Long, String>(); final LookUp
     * chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp( PrefixConstants.LookUpPrefix.RCPT,
     * PrefixConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation()); final List<TbTaxMas> taxList =
     * tbTaxMasService.findAllTaxesForBillGeneration(org.getOrgid(), tbServiceReceiptMasBean.getDpDeptId(),
     * chargeApplicableAt.getLookUpId(), null); if (!taxList.isEmpty()) { for (TbTaxMas tbTaxMas : taxList) {
     * budgetAcHeadMap.put(tbTaxMas.getTaxId(), tbTaxMas.getTaxDesc()); } }
     * model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap); }
     */

    private void populateBudgetCodes(final Model model, TbServiceReceiptMasBean tbServiceReceiptMasBean) {

        final Organisation org = UserSession.getCurrent().getOrganisation();
        Map<Long, String> budgetAcHeadMap = new HashMap<Long, String>();
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.LookUpPrefix.RCPT, PrefixConstants.NewWaterServiceConstants.CAA,
                UserSession.getCurrent().getOrganisation());

        List<LookUp> receiptHeadLookup = CommonMasterUtility.getNextLevelData(MainetConstants.ReceiptForm.TAC, 1,
                UserSession.getCurrent().getOrganisation().getOrgid());
        Long lookUpId = null;
        if (tbServiceReceiptMasBean.getRecCategoryType().equals(MainetConstants.FlagM)) {
            List<LookUp> misc = receiptHeadLookup.stream().filter(p -> p.getLookUpCode().equals(MainetConstants.ReceiptForm.SC))
                    .collect(Collectors.toList());
            if (!misc.isEmpty()) {
                lookUpId = misc.get(0).getLookUpId();
            }
        }
        final List<TbTaxMasEntity> taxMasList = tbTaxMasService.getTaxMasterByTaxCategoryId(tbServiceReceiptMasBean.getDpDeptId(),
                lookUpId, org.getOrgid(), chargeApplicableAt.getLookUpId());
        if (!taxMasList.isEmpty()) {
            taxMasList.forEach(tbTaxMas -> {
                TbTaxMas taxMas = new TbTaxMas();
                BeanUtils.copyProperties(tbTaxMas, taxMas);
                budgetAcHeadMap.put(taxMas.getTaxId(), taxMas.getTaxDesc());
            });
        }
        model.addAttribute(MainetConstants.AccountReceiptEntry.HEAD_CODE_MAP, budgetAcHeadMap);
    }

    private void populateListOfTbAcFieldMasterItems(final Model model, final Long orgId) {
        model.addAttribute(MainetConstants.ACCOUNT_RECEIPT_ENTRY_MASTER.FIELD_MASTER_ITEMS,
                tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
    }

    @RequestMapping(params = "SLIDate", method = RequestMethod.GET)
    public @ResponseBody Object[] findSLIDate(final HttpServletRequest request, final ModelMap model) {
        final Object[] dateArray = new Object[3];
        try {
            final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                    UserSession.getCurrent().getOrganisation());
            Objects.requireNonNull(lookUp, ApplicationSession.getInstance().getMessage("account.receipt.sli"));
            final String date = lookUp.getOtherField();
            Objects.requireNonNull(date, ApplicationSession.getInstance().getMessage("account.depositslip.livedate"));
            final Date sliDate = Utility.stringToDate(date);
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(sliDate);
            dateArray[0] = calendar.get(Calendar.YEAR);
            dateArray[1] = calendar.get(Calendar.MONTH);
            dateArray[2] = calendar.get(Calendar.DATE);

        } catch (final Exception ex) {
            LOGGER.error("Error while finding SLI Date from SLI Prefix:", ex);
        }
        return dateArray;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.ReceiptForm.GETRECEIPT_ACCOUNTHEAD_DATA, method = RequestMethod.POST)
    public Map<Long, String> getReceiptAccountHeadAllData(final TbServiceReceiptMasBean tbServiceReceiptMasBean,
            final Model model, final HttpServletRequest request) {

        Organisation org = UserSession.getCurrent().getOrganisation();
        Map<Long, String> budgetAcHeadMap = new HashMap<Long, String>();
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.LookUpPrefix.RCPT, PrefixConstants.NewWaterServiceConstants.CAA,
                org);
        String recCategoryType = CommonMasterUtility.findLookUpCode(MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV,
                UserSession.getCurrent().getOrganisation().getOrgid(), tbServiceReceiptMasBean.getRecCategoryTypeId());
        List<LookUp> receiptHeadLookup = CommonMasterUtility.getNextLevelData(MainetConstants.ReceiptForm.TAC, 1,
                org.getOrgid());
        Long lookUpId = null;
        if (!receiptHeadLookup.isEmpty()) {
            if (recCategoryType.equals(MainetConstants.FlagM)) {
                List<LookUp> misc = receiptHeadLookup.stream()
                        .filter(p -> p.getLookUpCode().equals(MainetConstants.ReceiptForm.SC))
                        .collect(Collectors.toList());
                if (!misc.isEmpty()) {
                    lookUpId = misc.get(0).getLookUpId();
                }
            }
            if (recCategoryType.equals(MainetConstants.FlagP)) {
                List<LookUp> deposit = receiptHeadLookup.stream()
                        .filter(p -> p.getLookUpCode().equals(MainetConstants.ReceiptForm.DPT))
                        .collect(Collectors.toList());
                if (!deposit.isEmpty()) {
                    lookUpId = deposit.get(0).getLookUpId();
                }
            }
            if (recCategoryType.equals(MainetConstants.FlagA)) {
                List<LookUp> advance = receiptHeadLookup.stream().filter(p -> p.getLookUpCode().equals(MainetConstants.FlagA))
                        .collect(Collectors.toList());
                if (!advance.isEmpty()) {
                    lookUpId = advance.get(0).getLookUpId();
                }
            }
        }
        final List<TbTaxMasEntity> taxMasList = tbTaxMasService.getTaxMasterByTaxCategoryId(tbServiceReceiptMasBean.getDpDeptId(),
                lookUpId, org.getOrgid(), chargeApplicableAt.getLookUpId());
        if (!taxMasList.isEmpty()) {
            taxMasList.forEach(tbTaxMas -> {
                TbTaxMas taxMas = new TbTaxMas();
                BeanUtils.copyProperties(tbTaxMas, taxMas);
                budgetAcHeadMap.put(taxMas.getTaxId(), taxMas.getTaxDesc());
            });
        }
        return budgetAcHeadMap;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.ReceiptForm.GETALL_RECEIPTDATA, method = RequestMethod.POST)
    public List<TbServiceReceiptMasBean> physicalMilestoneList(@RequestParam("rmAmount") String rmAmount,
            @RequestParam("rmRcptno") String rmRcptno, @RequestParam("rm_Receivedfrom") String rm_Receivedfrom,
            @RequestParam("rmDate") final String rmDate) {
        List<TbServiceReceiptMasBean> tbServiceReceiptMasBean = new ArrayList<>();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        Date date = null;
        if ((rmDate != null) && !rmDate.isEmpty()) {
            date = Utility.stringToDate(rmDate);
        }

        Long receiptNo = null;
        if ((rmRcptno == null) || rmRcptno.isEmpty()) {
            receiptNo = 0L;
        } else if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) && (rmRcptno != null)) {
        	String[] bits = rmRcptno.split("/");
        	String lastWord = bits[bits.length - 2];
           receiptNo = Long.parseLong(lastWord);
        }else {
            receiptNo = Utility.getReceiptIdFromCustomRcptNO(rmRcptno);
        }
        BigDecimal rmReptAmount;
        if ((rmAmount == null) || rmAmount.isEmpty()) {
            rmReptAmount = BigDecimal.ZERO;
        } else {
            rmReptAmount = new BigDecimal(rmAmount);
        }
        if (rm_Receivedfrom == null) {
            rm_Receivedfrom = MainetConstants.CommonConstant.BLANK;
        }
        if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
        tbServiceReceiptMasBean = receiptEntryService.findAll(orgId, rmReptAmount, receiptNo, rm_Receivedfrom, date,
                UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid());
        }
        else {
        	tbServiceReceiptMasBean = receiptEntryService.findAll(orgId, rmReptAmount, receiptNo, rm_Receivedfrom, date,
                    null);
        }
        return tbServiceReceiptMasBean;
    }

    @RequestMapping(params = MainetConstants.ReceiptForm.CHECK_BUDGECODE_FEEMODE)
    public @ResponseBody String checkBudgetCodeIdForFeeMode(final Model model,
            @RequestParam("cpdFeemode") final Long cpdFeemode) {

        final String budgetCodeStatus = MainetConstants.MENU.Y;
        return budgetCodeStatus;
    }

    @RequestMapping(params = MainetConstants.Actions.CREATE, method = RequestMethod.POST)
    public @ResponseBody TbServiceReceiptMasBean createReceipt(final TbServiceReceiptMasBean tbServiceReceiptMasBean,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest request,
            final BindingResult bindingResult) {
        TbServiceReceiptMasBean receiptMasBean = this.getModel().prepareAndSaveReceiptEntity(tbServiceReceiptMasBean);
        return receiptMasBean;
    }

    @RequestMapping(params = MainetConstants.ReceiptForm.ACTION_VIEW)
    public ModelAndView formForView(final Model model, @RequestParam("rmRcptid") final Long rmRcptid,
            @RequestParam("saveMode") final String saveMode) {

        Organisation organisation = UserSession.getCurrent().getOrganisation();
        final String feeAmountStr = null;
        TbServiceReceiptMasBean tbServiceReceiptMasBean = null;
        tbServiceReceiptMasBean = receiptEntryService.findReceiptById(rmRcptid,
                organisation.getOrgid());
        tbServiceReceiptMasBean.setRecCategoryTypeId(tbServiceReceiptMasBean.getRmReceiptcategoryId());
        Map<Long, String> budgetAcHeadMap = new HashMap<Long, String>();
        final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.LookUpPrefix.RCPT, PrefixConstants.NewWaterServiceConstants.CAA,
                organisation);
        List<LookUp> receiptHeadLookup = CommonMasterUtility.getNextLevelData(MainetConstants.ReceiptForm.TAC, 1,
                organisation.getOrgid());
        Long lookUpId = null;

        if (tbServiceReceiptMasBean.getReceiptTypeFlag().equals(MainetConstants.FlagM)) {
            for (LookUp up : receiptHeadLookup) {
                if (up.getLookUpCode().equals(MainetConstants.ReceiptForm.SC)) {
                    lookUpId = up.getLookUpId();
                }
            }
        }
        final List<TbTaxMasEntity> taxMasList = tbTaxMasService.getTaxMasterByTaxCategoryId(tbServiceReceiptMasBean.getDpDeptId(),
                lookUpId, organisation.getOrgid(), chargeApplicableAt.getLookUpId());
        if (!taxMasList.isEmpty()) {
            for (TbTaxMasEntity tbTaxMas : taxMasList) {
                TbTaxMas taxMas = new TbTaxMas();
                BeanUtils.copyProperties(tbTaxMas, taxMas);
                budgetAcHeadMap.put(taxMas.getTaxId(), taxMas.getTaxDesc());
            }
        }

        List<TbSrcptFeesDetBean> recDetDetails1 = new ArrayList<>();
        if (!tbServiceReceiptMasBean.getReceiptFeeDetail().isEmpty()) {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				for (TbSrcptFeesDetBean tbBean : tbServiceReceiptMasBean.getReceiptFeeDetail()) {
					TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
					String desc=tbTaxMasService.getAcHeadFromTaxMaster(tbBean.getTaxId(),tbServiceReceiptMasBean.getSmServiceId(),
			                  UserSession.getCurrent().getOrganisation().getOrgid());
					bean.setAcHeadCode(desc);
					bean.setRfFeeamount(tbBean.getRfFeeamount());
					recDetDetails1.add(bean);
				}
				tbServiceReceiptMasBean.setRmReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(tbServiceReceiptMasBean.getFieldId(),tbServiceReceiptMasBean.getSmServiceId(),Long.valueOf(tbServiceReceiptMasBean.getRmRcptno()),
						tbServiceReceiptMasBean.getRmDate(),UserSession.getCurrent().getOrganisation().getOrgid()));
        	 }else {
        		 TbTaxMas taxMas = tbTaxMasService.findById(tbServiceReceiptMasBean.getReceiptFeeDetail().get(0).getTaxId(),
                         organisation.getOrgid());
                 TbSrcptFeesDetBean bean = new TbSrcptFeesDetBean();
                 bean.setAcHeadCode(taxMas.getTaxDesc());
                 bean.setRfFeeamount(tbServiceReceiptMasBean.getReceiptFeeDetail().get(0).getRfFeeamount());
                 recDetDetails1.add(bean);
        	 }
            
            /*
             * for (TbSrcptFeesDetBean bean : tbServiceReceiptMasBean.getReceiptFeeDetail()) { if (!taxMasList.isEmpty()) { for
             * (TbTaxMasEntity tbTaxMas : taxMasList) { TbTaxMas taxMas = new TbTaxMas(); BeanUtils.copyProperties(tbTaxMas,
             * taxMas); bean.setAcHeadCode(taxMas.getTaxDesc()); bean.setRfFeeamount(bean.getRfFeeamount());
             * recDetDetails1.add(bean); } }
             */
        }
        tbServiceReceiptMasBean.setReceiptFeeDetail(recDetDetails1);

        tbServiceReceiptMasBean.setFeeAmountStr(feeAmountStr);

        TbAcVendormaster tbAcVendormaster = null;
        if (tbServiceReceiptMasBean.getVmVendorId() == null) {
            tbAcVendormaster = new TbAcVendormaster();
        } else {
            tbAcVendormaster = tbAcVendormasterService.findById(tbServiceReceiptMasBean.getVmVendorId(),
                    UserSession.getCurrent().getOrganisation().getOrgid());
            tbServiceReceiptMasBean.setVmVendorIdDesc(tbAcVendormaster.getVmVendorname());
        }
        if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode() != null && !tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode().equals(MainetConstants.BLANK)) {
            LookUp lookUp = new LookUp();

            organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            lookUp = CommonMasterUtility
                    .getNonHierarchicalLookUpObject(tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemode(), organisation);

            tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeDesc(lookUp.getLookUpDesc());
            tbServiceReceiptMasBean.getReceiptModeDetailList().setCpdFeemodeCode(lookUp.getLookUpCode());
        }

        if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() != null &&
        		tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.AccountReceiptEntry.RT)
        		||tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() !=null 
        		&& tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N)
        		||tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() != null
                && tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
                        .equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE)) {
            tbServiceReceiptMasBean.getReceiptModeDetailList()
                    .setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefNumber());
        } else {
            if (tbServiceReceiptMasBean.getReceiptModeDetailList() != null
                    && (tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno() != null)) {
                tbServiceReceiptMasBean.getReceiptModeDetailList()
                        .setTranRefNumber(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequeddno().toString());
            }
        }

        if (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() !=null &&
        		tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.AccountReceiptEntry.RT)
                ||( tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() != null && 
                tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode().equals(MainetConstants.MENU.N))
                || (tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode() != null &&
                 tbServiceReceiptMasBean.getReceiptModeDetailList().getCpdFeemodeCode()
                        .equals(MainetConstants.NewWaterServiceConstants.BILL_SCHEDULE_DATE))) {
            final String tranRefDate = Utility
                    .dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getTranRefDate());
            tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(tranRefDate);
        } else {
            final String chkDate = Utility
                    .dateToString(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddate());
            tbServiceReceiptMasBean.getReceiptModeDetailList().setRdChequedddatetemp(chkDate);
        }

        if (saveMode.equals(MainetConstants.MODE_EDIT)) {
            model.addAttribute(MainetConstants.ReceiptForm.FORM_MODE, MainetConstants.Actions.CREATE);
            populateModel(model, tbServiceReceiptMasBean, FormMode.EDIT);
        } else {
            model.addAttribute(MainetConstants.ReceiptForm.FORM_MODE, MainetConstants.ReceiptForm.VIEW);
            populateModel(model, tbServiceReceiptMasBean, FormMode.VIEW);
        }
        model.addAttribute("tbServiceReceiptMas", tbServiceReceiptMasBean);
        return new ModelAndView(MainetConstants.ReceiptForm.RECEIPT_ENTRY_FORM, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = MainetConstants.ReceiptForm.VALIDATE_CHEQUE_DATA, method = RequestMethod.POST)
    public @ResponseBody boolean validateChequeDate(TbServiceReceiptMasBean tbServiceReceiptMasBean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {

        boolean isValidationError = false;
        if (!MainetConstants.CommonConstant.BLANK
                .equals(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp())) {
            final Date date = Utility.stringToDate(tbServiceReceiptMasBean.getTransactionDate());
            final Date rdchequedddate = Utility
                    .stringToDate(tbServiceReceiptMasBean.getReceiptModeDetailList().getRdChequedddatetemp());
            if (rdchequedddate.compareTo(date) > 0) {
                bindingResult.addError(new org.springframework.validation.FieldError("tbServiceReceiptMasBean",
                        MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS },
                        null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
                isValidationError = true;
            }
            final Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, -1 * MainetConstants.CHEQUEDATEVALIDATION_MONTHS);
            if (rdchequedddate.before(c.getTime())) {
                bindingResult.addError(new org.springframework.validation.FieldError("tbServiceReceiptMasBean",
                        MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS },
                        null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
                isValidationError = true;
            }
        }
        return isValidationError;
    }

    @RequestMapping(params = MainetConstants.ReceiptForm.GET_VENDOR_PHONENO_EMAILID, method = RequestMethod.POST)
    public @ResponseBody List<String> getVendorPhoneNoAndEmailId(final TbServiceReceiptMasBean bean,
            final HttpServletRequest request, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long vendorId = bean.getVmVendorId();
        final List<String> vendrList = new ArrayList<>();
        final List<Object[]> vendorlist = tbAcVendormasterService.getVendorPhoneNoAndEmailId(vendorId, orgId);
        if (!vendorlist.isEmpty()) {
            for (final Object[] objects : vendorlist) {
                if (objects[0] != null) {
                    vendrList.add(objects[0].toString());
                } else {
                    vendrList.add(MainetConstants.CommonConstant.BLANK);
                }
                if (objects[1] != null) {
                    vendrList.add(objects[1].toString());
                } else {
                    vendrList.add(MainetConstants.CommonConstant.BLANK);
                }
            }
        }
        return vendrList;
    }
    //#134426
    @ResponseBody
	@RequestMapping(params = "printReceipt")
	public  ModelAndView printReceiptData(final HttpServletRequest request, @RequestParam("rmRcptid") final Long rmRcptid) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		CFCSchedulingCounterDet counterDet = null;
		ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		try {
		   printDTO = receiptDetailService.setValuesAndPrintReport(rmRcptid, orgId, langId);
			LookUp lookUp = null;
			try {
				lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
						UserSession.getCurrent().getOrganisation());
			} catch (Exception e) {
				LOGGER.info("CST Prefix not found" + e);
			}
		   counterDet = null;
			if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				counterDet = tbCfcservice.getCounterDetByEmpId(printDTO.getRecptCreatedBy(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null && counterDet.getEmpId() != null) {
				Employee empDto = employeeServcie.findEmployeeById(counterDet.getEmpId());
				if (empDto != null) {
					String empName = empDto.getEmpname().concat(MainetConstants.WHITE_SPACE).concat(empDto.getEmpmname()).concat(MainetConstants.WHITE_SPACE)
							.concat(empDto.getEmplname());
					printDTO.setUserName(empName);
				}
			}
				//D#147490
				final SimpleDateFormat sd = new SimpleDateFormat("dd-MMM-yyyy");
				String cfcDate = sd.format(printDTO.getRecptCreatedDate());
				printDTO.setCfcDate(cfcDate);
				final SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
				final String time = localDateFormat.format(printDTO.getRecptCreatedDate());
				printDTO.setReceiptTime(time);
			}
			//#145267
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				printDTO.setSubject(printDTO.getNarration());
				if(counterDet!=null) {
					tbCfcservice.setRecieptCfcAndCounterCount(counterDet);
				}
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				if(printDTO.getPaymentList()!=null) {
					for (ChallanReportDTO rebateReceipt :printDTO.getPaymentList()) {
						String desc=tbTaxMasService.getAcHeadFromTaxMaster(rebateReceipt.getTaxId(),printDTO.getServiceId(),
				                  UserSession.getCurrent().getOrganisation().getOrgid());
						rebateReceipt.setDetails(desc);
					}
				}
				String financialYear=null;
				if (printDTO.getRmDate() != null) {
					 try {
						financialYear = Utility.getFinancialYearFromDate(printDTO.getRmDate());
						printDTO.setFinYear(financialYear);
					} catch (Exception e) {
						LOGGER.info("Exception occur-------------------------->"+e);
					}
				}
				printDTO.setReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(printDTO.getFieldId(),printDTO.getServiceId(),Long.valueOf(printDTO.getReceiptNo()),printDTO.getRmDate(),orgId));
				printDTO.setPaymentText(ApplicationSession.getInstance().getMessage("onlineoffline.label.offlinePay"));
				printDTO.setSubject(printDTO.getSubject() + " - " + printDTO.getNarration());
				printDTO.setReceiverName(UserSession.getCurrent().getEmployee().getFullName());
			}	
			
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details in printReceiptData:" + e);
		}
		this.getModel().setCfcSchedulingCounterDet(counterDet);
		this.getModel().setReceiptDTO(printDTO);
		return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
	}
    
    @ResponseBody
    @RequestMapping(params = "reciptPrintForm", method = RequestMethod.POST)
	public ModelAndView ReceiptPrintForm(final HttpServletRequest request, @RequestParam("rmRcptno") final Long rmRcptno,@RequestParam("dpDeptId") final Long dpDeptId,@RequestParam("receiptdate") final Date receiptdate) {
    	bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		CFCSchedulingCounterDet counterDet = null;
		ChallanReceiptPrintDTO printDTO = new ChallanReceiptPrintDTO();
		try {
			TbServiceReceiptMasEntity entity = receiptEntryService.getReceiptDetailByRcptNoAndDeptIdAndRmDate(rmRcptno,orgId,dpDeptId,receiptdate);
		      printDTO = receiptDetailService.setValuesAndPrintReport(entity.getRmRcptid(), orgId, langId);
		      LookUp lookUp=null;
		      try {
		    	 lookUp = CommonMasterUtility.getValueFromPrefixLookUp("CST", MainetConstants.ENV,
							UserSession.getCurrent().getOrganisation());
		      }
		      catch (Exception e) {
		    	  LOGGER.info("CST Prefix not found" + e);
				}
			
			if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.FlagY)) {
				counterDet = tbCfcservice.getCounterDetByEmpId(UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				if (counterDet != null && counterDet.getEmpId() != null) {
				Employee empDto = employeeServcie.findEmployeeById(counterDet.getEmpId());
				if (empDto != null) {
					String empName = empDto.getEmpname().concat(MainetConstants.WHITE_SPACE).concat(empDto.getEmpmname()).concat(MainetConstants.WHITE_SPACE)
							.concat(empDto.getEmplname());
					printDTO.setUserName(empName);
				}
			}
				
			}
          //D#145307
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				printDTO.setSubject(entity.getRmNarration());
				if(counterDet!=null) {
					tbCfcservice.setRecieptCfcAndCounterCount(counterDet);
				}
				if (entity.getSmServiceId() != null)
					printDTO.setServiceCodeflag(MainetConstants.FlagN);
				else 
					printDTO.setServiceCodeflag(MainetConstants.FlagY);
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				if(printDTO.getPaymentList()!=null) {
					for (ChallanReportDTO rebateReceipt :printDTO.getPaymentList()) {
						String desc=tbTaxMasService.getAcHeadFromTaxMaster(rebateReceipt.getTaxId(),entity.getSmServiceId(),
				                  UserSession.getCurrent().getOrganisation().getOrgid());
						rebateReceipt.setDetails(desc);
					}
				}
				String financialYear=null;
				if (printDTO.getRmDate() != null) {
					 try {
						financialYear = Utility.getFinancialYearFromDate(printDTO.getRmDate());
						printDTO.setFinYear(financialYear);
					} catch (Exception e) {
						LOGGER.info("Exception occur-------------------------->"+e);
					}
				}
				printDTO.setReceiptNo(receiptEntryService.getTSCLCustomReceiptNo(printDTO.getFieldId(),printDTO.getServiceId(),Long.valueOf(printDTO.getReceiptNo()),printDTO.getRmDate(),orgId));
				printDTO.setPaymentText(ApplicationSession.getInstance().getMessage("onlineoffline.label.offlinePay"));
				printDTO.setSubject(printDTO.getSubject() + " - " + printDTO.getNarration());
				printDTO.setReceiverName(UserSession.getCurrent().getEmployee().getFullName());
			}
		} catch (Exception e) {
			LOGGER.info("Exception occure while Fetching receipt details in ReceiptPrintForm:" + e);
		}
		 
		this.getModel().setCfcSchedulingCounterDet(counterDet);
		this.getModel().setReceiptDTO(printDTO);
		return new ModelAndView(MainetConstants.CHALLAN_AT_ULB_RECEIPT_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
    }
    
    @RequestMapping(params = "refreshServiceData")
	public @ResponseBody List<TbServicesMst> refreshServiceData(final Model model,
			@RequestParam("deptId") final Long deptId) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final List<TbServicesMst> serviceMstList = tbServicesMstService.findActiveServiceByDeptIdAndNotActualSer(deptId, org.getOrgid());
		return serviceMstList;
	}
    
    @RequestMapping(params = "getBudgetHead")
	public @ResponseBody Map<Long, String> getBudgetHead(final Model model,
			@RequestParam("serviceId") final Long serviceId) {
    	 final Organisation org = UserSession.getCurrent().getOrganisation();
         Map<Long, String> budgetAcHeadMap = new HashMap<Long, String>();
         List<LookUp> receiptHeadLookup = CommonMasterUtility.getNextLevelData(MainetConstants.ReceiptForm.TAC, 1,
                 UserSession.getCurrent().getOrganisation().getOrgid());
         Long lookUpId = null;
             List<LookUp> misc = receiptHeadLookup.stream().filter(p -> p.getLookUpCode().equals(MainetConstants.ReceiptForm.SC))
                     .collect(Collectors.toList());
             if (!misc.isEmpty()) {
                 lookUpId = misc.get(0).getLookUpId();
         }
         budgetAcHeadMap = tbTaxMasService.getTaxMasterByTaxCategoryIdByServiceId(serviceId,
                 lookUpId, org.getOrgid());
		return budgetAcHeadMap;
	}


}
