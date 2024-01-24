package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.BankReconciliationDTO;
import com.abm.mainet.account.dto.BankReconclitionUploadDto;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.AccountChequeDishonourService;
import com.abm.mainet.account.service.AccountChequeOrCashDepositeService;
import com.abm.mainet.account.service.AccountContraVoucherEntryService;
import com.abm.mainet.account.service.BankReconciliationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author prasad.kancharla
 *
 */
@Controller
@RequestMapping("/BankReconciliation.html")
public class BankReconciliationController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(BankReconciliationController.class);
    private static final String MAIN_ENTITY_NAME = "tbBankReconciliation";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbBankReconciliation/form";
    private static final String JSP_LIST = "tbBankReconciliation/list";
    private static final String JSP_SUMMARY = "bankReconciliation/summary";
    private static final String JSP_SUMM = "bankRecon/summary";
    private String modeView = MainetConstants.CommonConstant.BLANK;
    @Autowired
    private IFileUploadService fileUpload;
    @Resource
    private BankReconciliationService bankReconciliationService;
    @Resource
    private AccountChequeDishonourService accountChequeDishonourService;
    @Resource
    private AccountChequeOrCashDepositeService chequeOrCashService;
    @Resource
    private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private TbBankmasterService bankAccountService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    AccountContraVoucherEntryService accountContraVoucherEntryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(768);
    }
    public BankReconciliationController() {
        super(BankReconciliationController.class, MAIN_ENTITY_NAME);
        log("BankReconciliationController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("BankReconciliationController-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        List<BankReconciliationDTO> chList = new ArrayList<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridReceiptPaymentBothSearch", method = RequestMethod.POST)
    public ModelAndView getLedgerDetails(final BankReconciliationDTO bean, final BindingResult bindingResult,
            final Model model) {
        log("BankReconciliationController-'getjqGridsearch' : 'get jqGrid search data'");
        String result = MainetConstants.CommonConstant.BLANK;
        List<BankReconciliationDTO> chList = new ArrayList<>();
        BigDecimal bankBalance = new BigDecimal(0.00);
        BigDecimal totalBankBalance = new BigDecimal(0.00);
        BigDecimal totalPayment = new BigDecimal(0.00);
        BigDecimal totalreceipt = new BigDecimal(0.00);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        LOGGER.info("BankReconciliationController-'getjqGridsearch' :'For Orgid >>>'"+orgId);
        Long bankAccountId = Long.valueOf(bean.getBankAccount());
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        String collect = bankAccountlist.entrySet().stream().filter(map -> map.getKey().equals(bankAccountId))
                .map(map -> map.getValue()).collect(Collectors.joining());

        Date fromDte = null;
        Date toDte = null;
        if ((bean.getFormDate() != null) && !bean.getFormDate().isEmpty()) {
            fromDte = Utility.stringToDate(bean.getFormDate());
        }
        if ((bean.getToDate() != null) && !bean.getToDate().isEmpty()) {
            toDte = Utility.stringToDate(bean.getToDate());
        }
        String categoryId = "";
        String categoryCode = "";
        LookUp categoryLookUp = null;
        if (bean.getSerchType().equals(MainetConstants.MENU.R)) {
            categoryId = bean.getCategoryId();
        } else if (bean.getSerchType().equals(MainetConstants.MENU.P)) {
            categoryId = bean.getPaymentCategoryId();
        } else {
            categoryId = null;
        }
        if (categoryId != null) {
            categoryLookUp = CommonMasterUtility.getValueFromPrefixLookUp(categoryId, PrefixConstants.LookUpPrefix.CLR,
                    UserSession.getCurrent().getOrganisation());
            categoryCode = categoryLookUp.getLookUpCode();

            if (bean.getSerchType().equals(MainetConstants.MENU.R)) {
                String categoryOtherValue = categoryLookUp.getOtherField();
                if (categoryOtherValue != null && !categoryOtherValue.isEmpty()) {
                    chList = bankReconciliationService.findByAllGridReceiptSearchData(categoryOtherValue,
                            Long.valueOf(bean.getBankAccount()), bean.getTransactionMode(), fromDte, toDte, orgId,categoryLookUp.getLookUpId());

                }
            } else if (bean.getSerchType().equals(MainetConstants.MENU.P)) {
                Long categoryLookUpId = categoryLookUp.getLookUpId();
                if (categoryLookUpId != null) {
                    chList = bankReconciliationService.findByAllGridPaymentEntrySearchData(categoryLookUpId,
                            Long.valueOf(bean.getBankAccount()), bean.getTransactionMode(), fromDte, toDte, orgId);
                }
            }
        } else if (bean.getSerchType().equals(MainetConstants.MENU.A)) {
            chList = bankReconciliationService.findByAllGridReceiptAndPaymentEntrySearchData(Long.valueOf(bean.getBankAccount()),
                    fromDte, toDte, orgId);
            // closing balanace
            bankBalance = accountContraVoucherEntryService.getBankBalance(Long.valueOf(bean.getBankAccount()), toDte,
                    UserSession.getCurrent().getOrganisation().getOrgid());

            if ((!chList.isEmpty() && chList != null) && bankBalance != null) {

                if (chList.get(chList.size() - 1).getTotalPaymentAmount() != null) {
                    if (chList.get(chList.size() - 1).getTotalPaymentAmount() != null) {
                        totalPayment = chList.get(chList.size() - 1).getTotalPaymentAmount();
                    }
                    if (chList.get(chList.size() - 1).getTotalReceiptAmount() != null) {
                        totalreceipt = chList.get(chList.size() - 1).getTotalReceiptAmount();
                    }

                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);

                } else if (chList.get(chList.size() - 1).getTotalReceiptAmount() != null) {

                    if (chList.get(chList.size() - 1).getTotalPaymentAmount() != null) {
                        totalPayment = chList.get(chList.size() - 1).getTotalPaymentAmount();
                    }
                    if (chList.get(chList.size() - 1).getTotalReceiptAmount() != null) {
                        totalreceipt = chList.get(chList.size() - 1).getTotalReceiptAmount();
                    }
                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);

                } else if ((chList.get(chList.size() - 1).getTotalReceiptAmount() == null)
                        && (chList.get(chList.size() - 1).getTotalPaymentAmount() == null)) {
                    totalBankBalance = bankBalance.add(totalPayment).subtract(totalreceipt);
                }

            }
        }

        final BankReconciliationDTO dto = new BankReconciliationDTO();
        if (chList == null || chList.isEmpty()) {
            dto.setSuccessfulFlag(MainetConstants.MASTER.Y);
            result = JSP_LIST;
        } else {
            if (categoryCode != null && !categoryCode.isEmpty()) {
                if (bean.getSerchType().equals(MainetConstants.MENU.R) && !categoryCode.equals("DNC")) {
                    model.addAttribute("categoryType", MainetConstants.Y_FLAG);
                } else if (bean.getSerchType().equals(MainetConstants.MENU.P) && !categoryCode.equals("ISD")) {
                    model.addAttribute("categoryType", MainetConstants.Y_FLAG);
                } else if (bean.getSerchType().equals(MainetConstants.MENU.A)) {
                    model.addAttribute("categoryType", MainetConstants.Y_FLAG);
                } else {
                    model.addAttribute("categoryType", MainetConstants.N_FLAG);
                }
            }
            if (categoryCode != null && !categoryCode.isEmpty()) {
                String categoryTypeDesc = categoryLookUp.getLookUpDesc();
                model.addAttribute("categoryTypeDesc", categoryTypeDesc);
                model.addAttribute("bankTypeDesc", collect);
            } else {
                String categoryTypeDesc = "ALL";
                model.addAttribute("categoryTypeDesc", categoryTypeDesc);
                model.addAttribute("categoryType", MainetConstants.N_FLAG);
                model.addAttribute("bankTypeDesc", collect);
            }
            dto.setSerchType(bean.getSerchType());
            dto.setBankReconciliationDTO(chList);
            if(CollectionUtils.isNotEmpty(chList))
            LOGGER.info("BankReconciliationController-'getjqGridsearch' :'For Orgid >>>'"+orgId +"Record Fetched >>> "+chList.size());
            result = JSP_FORM;
        }
        if (bankBalance != null) {
            dto.setBalanceAsUlb(CommonMasterUtility.getAmountInIndianCurrency(bankBalance));
            dto.setBankBalanceAsperStatement(CommonMasterUtility.getAmountInIndianCurrency(totalBankBalance));
        } else {
            dto.setBalanceAsUlb("0.00");
            dto.setBankBalanceAsperStatement("0.00");
        }
        // dto.setBankReconIds(collect);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        return new ModelAndView(result, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("BankReconciliationController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        final BankReconciliationDTO dto = new BankReconciliationDTO();
        List<BankReconciliationDTO> chList = new ArrayList<>();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
       // chList = bankReconciliationService.getAllSummaryData(null, null, null, orgId);
        List<BankReconciliationDTO> listofStatusId = bankReconciliationService
                .getAllStatusId(UserSession.getCurrent().getOrganisation().getOrgid());
        dto.setBankReconciliationDTO(chList);
        dto.setListofStatusId(listofStatusId);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMMARY;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final HttpServletRequest request, final Model model) throws Exception {
        log("BankReconciliationController-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        @SuppressWarnings("unchecked")
        final List<BankReconciliationDTO> chList = (List<BankReconciliationDTO>) request.getSession()
                .getAttribute(MainetConstants.BankReconciliation.CH_LIST);
        final BankReconciliationDTO bean = new BankReconciliationDTO();
        bean.setBankReconciliationDTO(chList);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final BankReconciliationDTO bean, final FormMode formMode) {
        log("BankReconciliationController-'populateModel' : populate model");
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(),
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> paymentMode = new ArrayList<>();
        for (final LookUp payList : paymentModeList) {
            if (payList.getLookUpCode().equals(AccountConstants.CHEQUE.getValue())
                    || payList.getLookUpCode().equals(AccountConstants.CONTRA_DEPOSIT.getValue())) {
                paymentMode.add(payList);
            }
        }
        model.addAttribute(MainetConstants.TABLE_COLUMN.PAYMENT_MODE, paymentMode);

        model.addAttribute(MAIN_ENTITY_NAME, bean);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE, MainetConstants.Actions.CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE, MainetConstants.Actions.UPDATE);
        }
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@ModelAttribute final BankReconciliationDTO tbBankReconciliation,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("BankReconciliationDTO-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbBankReconciliation.setHasError(MainetConstants.MENU.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            tbBankReconciliation.setOrgid(userSession.getOrganisation().getOrgid());
            tbBankReconciliation.setLangId(userSession.getLanguageId());
            tbBankReconciliation.setUserId(userSession.getEmployee().getEmpId());
            tbBankReconciliation.setLmoddate(new Date());
            tbBankReconciliation.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            loadExcelData(tbBankReconciliation);
            populateModel(model, tbBankReconciliation, FormMode.CREATE);
            BankReconciliationDTO tbBankReconciliationCreated = bankReconciliationService
                    .saveBankReconciliationFormData(tbBankReconciliation);
            if (tbBankReconciliationCreated == null) {
                tbBankReconciliationCreated = new BankReconciliationDTO();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbBankReconciliationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            fileUpload.sessionCleanUpForFileUpload();
            result = JSP_FORM;
        } else {
            tbBankReconciliation.setHasError(MainetConstants.MENU.TRUE);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND, bindingResult);
            populateModel(model, tbBankReconciliation, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

	

	private void loadExcelData(BankReconciliationDTO tbBankReconciliation) {
		try {
			final String filePath = getUploadedFinePath();
			ReadExcelData<BankReconclitionUploadDto> data = new ReadExcelData<>(filePath,
					BankReconclitionUploadDto.class);
			data.parseExcelList();
			List<String> errlist = data.getErrorList();
			if (!errlist.isEmpty()) {
				LOGGER.error("Error at the time of read excel data  "+errlist);
			} else {
				final List<BankReconclitionUploadDto> accountDepositUploadDtos = data.getParseData();
				try {
				for (BankReconciliationDTO dto : tbBankReconciliation.getBankReconciliationDTO()) {
					for (BankReconclitionUploadDto dtos : accountDepositUploadDtos) {
						if (StringUtils.isBlank(dto.getDate())&&(dto.getTransactionDate() != null && dtos.getRealizationDate() != null)) {
							Date transDate=null,xlTrnsDate=null,xlPayDate=null,xlInstrmentDt=null,chequeDt =null;
							if(StringUtils.isNotEmpty(dto.getTransactionDate()))
							 transDate = Utility.stringToDate(dto.getTransactionDate());
							if(StringUtils.isNotEmpty(dtos.getTransactionDate()))
							 xlTrnsDate=Utility.stringToDate(dtos.getTransactionDate(),MainetConstants.DATE_FRMAT);
							if(StringUtils.isNotEmpty(dtos.getRealizationDate()))
							 xlPayDate=Utility.stringToDate(dtos.getRealizationDate(), MainetConstants.DATE_FRMAT);
							if(StringUtils.isNotEmpty(dtos.getInstrumentDate()))
							 xlInstrmentDt=Utility.stringToDate(dtos.getInstrumentDate(), MainetConstants.DATE_FRMAT);
							if(StringUtils.isNotEmpty(dto.getChequedddate()))
								chequeDt=Utility.stringToDate(dto.getChequedddate());
							String amt = "";
							if(xlPayDate!=null&&xlTrnsDate!=null) {
							boolean isEqlAftrDate=false;
								if((StringUtils.equalsIgnoreCase(dtos.getTransactionMode(), "Bank")||(xlInstrmentDt!=null&&Utility.compareDates(xlPayDate,xlInstrmentDt)>=0))&&Utility.compareDates(xlPayDate,xlTrnsDate)>=0) {
									isEqlAftrDate=true;
								}
								String xlChequeno=MainetConstants.BLANK;
								if(StringUtils.isNotBlank(dtos.getChequeNo())) {
									try {
										DecimalFormat f=new DecimalFormat("0");
										xlChequeno=f.format(Double.valueOf(dtos.getChequeNo()));
									} catch (Exception e) {
					
									}
								}
								if(StringUtils.isBlank(xlChequeno)&&StringUtils.isNotBlank(dtos.getChequeNo())) {
									xlChequeno=dtos.getChequeNo().replace(".0", "");
								}
								
							if (dtos.getAmount() != null) {
								amt = CommonMasterUtility.getAmountInIndianCurrency(dtos.getAmount());
							}if((StringUtils.equalsIgnoreCase(dtos.getTransactionMode(), "Bank")&&amt.equals(dto.getAmount()))&&(Utility.comapreDates(transDate, xlTrnsDate)&&isEqlAftrDate)) {
								dto.setDate(Utility.dateToString(xlPayDate));	
							}
								else if (((StringUtils.equalsIgnoreCase(dto.getChequeddno(), xlChequeno) || StringUtils
										.equalsIgnoreCase(dto.getChequeddno(), xlChequeno.replaceFirst("^0+", "")))
										&& amt.equals(dto.getAmount()))
										&& (Utility.comapreDates(chequeDt, xlInstrmentDt) && isEqlAftrDate)) {
									dto.setDate(Utility.dateToString(xlPayDate));
								}
						}}
					}
				}
			}catch (Exception e) {
				LOGGER.error("Error at the time of iterate with excel data  "+e);
			}}
		
		} catch (Exception e) {
			LOGGER.error("Error at the time of load excel data  "+e);
		}
		
	}
	@RequestMapping(params = "AddForm", method = RequestMethod.POST)
    public String AddForm(final BankReconciliationDTO bean1, final BindingResult bindingResult, final Model model) {
        log("BankReconciliationController-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        List<BankReconciliationDTO> chList = new ArrayList<>();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        final BankReconciliationDTO bean = new BankReconciliationDTO();
        bean.setSerchType(MainetConstants.MENU.R);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        final List<LookUp> clearLookUpList = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.CLR,
                UserSession.getCurrent().getOrganisation());
        List<LookUp> newReceiptClearanceList = new ArrayList<>();

        newReceiptClearanceList = clearLookUpList.stream()
                .filter(look -> look.getLookUpCode() != null && (look.getLookUpCode().equals("CLD")
                        || look.getLookUpCode().equals("DNC") || look.getLookUpCode().equals("DSH")))
                .collect(Collectors.toList());
        model.addAttribute(MainetConstants.BankReconciliation.CATEGORY_LIST, newReceiptClearanceList);

        List<LookUp> newPaymentClearanceList = new ArrayList<>();
        newPaymentClearanceList = clearLookUpList.stream()
                .filter(look -> look.getLookUpCode() != null && (look.getLookUpCode().equals("CLD")
                        || look.getLookUpCode().equals("CND") || look.getLookUpCode().equals("ISD")
                        || look.getLookUpCode().equals("STP") || look.getLookUpCode().equals("STL")||look.getLookUpCode().equals("RFI")))
                .collect(Collectors.toList());
        model.addAttribute("paymentClearLookUpList", newPaymentClearanceList);
        model.addAttribute("AllClearLookUpList", clearLookUpList);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "searchBankReconcilationData", method = RequestMethod.POST)
    public String createPopulationForm(final HttpServletRequest request, final Model model,
            @RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
            @RequestParam(required = false) Long bankAccount) {
        log("BankReconciliationController-'list' :'list'");
        fileUpload.sessionCleanUpForFileUpload();
        String result = MainetConstants.CommonConstant.BLANK;
        final BankReconciliationDTO dto = new BankReconciliationDTO();
        List<BankReconciliationDTO> chList = new ArrayList<>();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<Long, String> bankAccountlist = new LinkedHashMap<>();
        chList = bankReconciliationService.getAllSummaryData(bankAccount, Utility.stringToDate(fromDate),
                Utility.stringToDate(toDate), orgId);
        bankAccountlist = accountChequeDishonourService.getBankAccountData(orgId);
        List<BankReconciliationDTO> listofStatusId = bankReconciliationService
                .getAllStatusId(UserSession.getCurrent().getOrganisation().getOrgid());
        dto.setListofStatusId(listofStatusId);
        dto.setFormDate(fromDate);
        dto.setToDate(toDate);
        dto.setBankAccount(bankAccount.toString());
        dto.setBankReconciliationDTO(chList);
        model.addAttribute(MainetConstants.BankReconciliation.BANK_LIST, bankAccountlist);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_SUMM;
        return result;
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "searchStatusId")
    public Map<Long, String> serchStatusId(@RequestParam("bankAccountId") Long bankAccountId,
            final HttpServletRequest httpServletRequest) {
        List<BankReconciliationDTO> result = bankReconciliationService
                .getAllStatusId(UserSession.getCurrent().getOrganisation().getOrgid());
        Map<Long, String> data = new HashMap<>();
        if (result != null && !result.isEmpty()) {
            result.forEach(vdata -> {
                data.put(vdata.getId(), vdata.getBankReconIds());
            });
        }
        return data;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
    @RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
    public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
            @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
    public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
            final HttpServletRequest httpServletRequest, @RequestParam final String browserType,
            @RequestParam(name = "uniqueId", required = false) final Long uniqueId) {
        UserSession.getCurrent().setBrowserType(browserType);
        JsonViewObject jsonViewObject = JsonViewObject.successResult();
        jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
        return jsonViewObject;
    }
    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
                browserType);
        return jsonViewObject;
    }
    private String getUploadedFinePath() {
        String filePath = null;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            Set<File> list = entry.getValue();
            for (final File file : list) {
                filePath = file.toString();
                break;
            }
        }
        return filePath;
    }
    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountDepositExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<BankReconclitionUploadDto> data = new WriteExcelData<>(
                    "ReconclitionEntry" + MainetConstants.XLSX_EXT, request, response);

            data.getExpotedExcelSheet(new ArrayList<BankReconclitionUploadDto>(), BankReconclitionUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }
}