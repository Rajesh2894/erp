package com.abm.mainet.account.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.ui.model.BankMasterResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.TbBankaccount;
import com.abm.mainet.common.dto.TbBankmaster;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbCustbanksService;
import com.abm.mainet.common.service.TbBankaccountService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbBankmaster' management.
 */
@Controller
@RequestMapping("BankMaster.html")
public class TbBankmasterController extends AbstractController {

    private static Logger logger = Logger.getLogger(TbBankmasterController.class);

    @Resource
    private TbBankmasterService tbBankmasterService;

    @Resource
    private TbCustbanksService tbCustbanksService;

    @Resource
    private TbBankaccountService tbBankaccountService;

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private SecondaryheadMasterService secondaryHeadMasterService;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    private static final String JSP_FORM = "tbBankmaster/form";

    private static final String JSP_LIST = "tbBankmaster/list";

    private static final String SAVE_ACTION_CREATE = "BankMaster.html?create";

    private static final String SAVE_ACTION_UPDATE = "BankMaster.html?update";

    private static final String ENTITY = "tbBankmaster";

    private static final String POPUP_ADD = "BankMaster?saveFundForm";

    private static final String POPUP_SAVE = "BankMaster?saveFundForm";

    private static final String AccountFundMapping = "AccountFundMapping";

    private static final String FUND_TYPE = "fundType";

    private static final String FUND_LIST = "fundList";

    private static final String COUNT = "count";

    private static final String POPUP_MODE = "popupMode";

    private final List<TbBankmaster> Banklist = new ArrayList<>();

    private List<AccountFundMasterBean> fundMasterList = null;

    private static Map<Integer, Map<Long, List<String>>> popupMapData = new HashMap<>();

    private Date lmodDate;

    public TbBankmasterController() {
        super(TbBankmasterController.class, ENTITY);
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        binder.registerCustomEditor(Date.class, MainetConstants.BANK_MASTER.LIST_BANK_ACCOUNT_CURRENT_DATE,
                new CustomDateEditor(dateFormat, true));
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     * @param model
     * @param tbBankmaster
     */
    private void populateModel(final Model model, final TbBankmaster tbBankmaster, final FormMode formMode) {

        model.addAttribute(ENTITY, tbBankmaster);
        final List<LookUp> bankType = CommonMasterUtility.getListLookup(PrefixConstants.BAT,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> accountType = CommonMasterUtility.getListLookup(PrefixConstants.ACT,
                UserSession.getCurrent().getOrganisation());
        final List<LookUp> accountStatus = CommonMasterUtility.getListLookup(PrefixConstants.BAS,
                UserSession.getCurrent().getOrganisation());
        @SuppressWarnings("deprecation")
        final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
                PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Map<Long, String> accountHead = secondaryHeadMasterService
                .findPrimarySecondaryHead(UserSession.getCurrent().getOrganisation().getOrgid());

        if (formMode == FormMode.CREATE) {
            popupMapData.clear();
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
        } else if (formMode == FormMode.UPDATE) {
            if (tbBankmaster.getAlreadyExist() == null) {
                popupMapData.clear();
            }

            final List<TbBankaccount> bankAcList = tbBankmaster.getListOfTbBankaccount();
            if ((bankAcList != null) && !bankAcList.isEmpty()) {
                Integer cnt = 0;
                List<String> fundData = null;
                for (final TbBankaccount account : bankAcList) {
                    fundData = new ArrayList<>();
                    final Map<Long, List<String>> mapdata = new HashMap<>();
                    if (account.getBaCurrentdate() != null) {
                        account.setTempdate(
                                new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT).format(account.getBaCurrentdate()));
                    }
                    if (account.getFunds() != null) {
                        for (final AccountFundMasterBean fund : account.getFunds()) {
                            fundData.add(String.valueOf(fund.getFundId()));
                        }
                        popupMapData.put(cnt, mapdata);
                    }
                    cnt++;
                }
            }

            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
        } else if (formMode == FormMode.VIEW) {
            if ((tbBankmaster.getListOfTbBankaccount() != null) && !tbBankmaster.getListOfTbBankaccount().isEmpty()) {
                for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
                    if (account.getBaCurrentdate() != null) {
                        account.setTempdate(
                                new SimpleDateFormat(MainetConstants.COMMON_DATE_FORMAT).format(account.getBaCurrentdate()));
                    }
                }
            }
            model.addAttribute(MODE, MODE_VIEW);
        }
        model.addAttribute(MainetConstants.BANK_MASTER.BANK_TYPE, bankType);
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_TYPE, accountType);
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_STATUS, accountStatus);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.FIELD_MASTER_ITEMS,
                tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid()));
        model.addAttribute(FUND_LIST, tbAcCodingstructureMasService.getFundMasterList(fundLookup.getLookUpId()));
        model.addAttribute(MainetConstants.BANK_MASTER.ACCOUNT_HEAD, accountHead);
    }

    /**
     * Shows a list with all the occurrences of TbBankmaster found in the database
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping()
    public String list(final Model model) {

        Banklist.clear();

        {
            final List<Object[]> mainList = tbBankmasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
            final List<TbBankmaster> list = new ArrayList<>();
            TbBankmaster bank = null;
            for (final Object[] obj : mainList) {
                bank = new TbBankmaster();
                bank.setBmBankid(Long.valueOf(obj[0].toString()));
                bank.setBmBankname(obj[1].toString());
                list.add(bank);
            }
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
            return JSP_LIST;
        }

    }

    /**
     * Shows a form page in order to create a new TbBankmaster
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        final TbBankmaster tbBankmaster = new TbBankmaster();
        populateModel(model, tbBankmaster, FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to update an existing TbBankmaster
     * @param model Spring MVC model
     * @param bmBankid primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model, @RequestParam("bmBankid") final Long bmBankid,
            @RequestParam("orgid") final Long orgid) {

        final TbBankmaster tbBankmaster = tbBankmasterService.findById(bmBankid, orgid);
        setLmodDate(tbBankmaster.getLmoddate());

        for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
            if ((account.getBaStatus() != null) && account.getBaStatus().equals(MainetConstants.BANK_MASTER.I)) {
                tbBankmaster.getListOfTbBankaccount().remove(account);
            }
        }
        populateModel(model, tbBankmaster, FormMode.UPDATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to view an existing TbBankmaster
     * @param model Spring MVC model
     * @param bmBankid primary key element
     * @param orgid primary key element
     * @return
     */
    @RequestMapping(params = "formForView")
    public String formForView(final Model model, @RequestParam("bmBankid") final Long bmBankid,
            @RequestParam("orgid") final Long orgid) {

        final TbBankmaster tbBankmaster = tbBankmasterService.findById(bmBankid, orgid);
        setLmodDate(tbBankmaster.getLmoddate());
        for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
            if ((account.getBaStatus() != null) && account.getBaStatus().equals(MainetConstants.BANK_MASTER.I)) {
                tbBankmaster.getListOfTbBankaccount().remove(account);
            }
        }
        populateModel(model, tbBankmaster, FormMode.VIEW);
        return JSP_FORM;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbBankmaster entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "create") // GET or POST
    public ModelAndView create(@Valid final TbBankmaster tbBankmaster, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws ParseException {

        if (!bindingResult.hasErrors()) {
            setCommonFields(tbBankmaster, httpServletRequest);
            if ((tbBankmaster.getListOfTbBankaccount() != null) && !tbBankmaster.getListOfTbBankaccount().isEmpty()) {
                for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
                    final boolean exist = tbBankaccountService.findByAccountCode(account.getBaAccountcode());
                    if (exist) {
                        tbBankmaster.setAlreadyExist(MainetConstants.MASTER.Y);
                        tbBankmaster.setSuccessFlag(MainetConstants.MASTER.N);
                        bindingResult.addError(new org.springframework.validation.FieldError(ENTITY, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                MainetConstants.CommonConstant.BLANK + account.getBaAccountcode() + " "
                                        + ApplicationSession.getInstance().getMessage("bank.account.exist")));
                        populateModel(model, tbBankmaster, FormMode.CREATE);
                        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
                        return new ModelAndView(JSP_FORM);
                    }
                    final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
                    if ((account.getTempdate() != null) && !account.getTempdate().equals(MainetConstants.CommonConstant.BLANK)) {
                        account.setBaCurrentdate(sdf.parse(account.getTempdate()));
                    }
                }
            }

            if (!popupMapData.isEmpty()) {
                TbBankaccount account = null;
                List<AccountFundMasterBean> fundList = null;
                List<String> fundData = null;
                AccountFundMasterBean fund = null;
                Map<Long, List<String>> data = null;
                for (Integer i = 0; i <= tbBankmaster.getListOfTbBankaccount().size(); i++) {
                    if (popupMapData.containsKey(i)) {
                        account = tbBankmaster.getListOfTbBankaccount().get(i);
                        fundList = new ArrayList<>();
                        data = popupMapData.get(i);
                        for (final Map.Entry<Long, List<String>> entry : data.entrySet()) {
                            fundData = entry.getValue();
                            for (final String fundId : fundData) {
                                fund = new AccountFundMasterBean();
                                fund.setFundId(Long.valueOf(fundId));
                                fund.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                                fund.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
                                fund.setLmoddate(new Date());
                                fund.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                                fundList.add(fund);
                            }
                            account.setFunds(fundList);
                        }

                    }
                }
            }
            final TbBankmaster tbBankmasterCreated = tbBankmasterService.create(tbBankmaster);
            tbBankmasterCreated.setSuccessFlag(MainetConstants.MASTER.Y);
            model.addAttribute(ENTITY, tbBankmasterCreated);
            return new ModelAndView(JSP_FORM);
        } else {
            tbBankmaster.setSuccessFlag(MainetConstants.MASTER.N);
            logger.error("error in create method for binding result");
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
    }

    /**
     * @param tbBankmaster
     */
    private void setCommonFields(final TbBankmaster tbBankmaster, final HttpServletRequest httpServletRequest) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long langId = (long) UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        tbBankmaster.setOrgid(orgId);
        tbBankmaster.setLangId(langId);
        tbBankmaster.setUserId(userId);
        tbBankmaster.setLmoddate(new Date());
        tbBankmaster.setBmStatus(MainetConstants.MASTER.A);
        tbBankmaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     * @param tbBankmaster entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "update") // GET or POST
    public String update(@Valid final TbBankmaster tbBankmaster, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws ParseException {

        if ((tbBankmaster.getListOfTbBankaccount() != null) && !tbBankmaster.getListOfTbBankaccount().isEmpty()) {
            for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
                if (account.getBaAccountid() == null) {
                    final boolean exist = tbBankaccountService.findByAccountCode(account.getBaAccountcode());
                    if (exist) {
                        tbBankmaster.setAlreadyExist(MainetConstants.MASTER.Y);
                        tbBankmaster.setSuccessFlag(MainetConstants.MASTER.N);
                        bindingResult.addError(new org.springframework.validation.FieldError(ENTITY, MainetConstants.BLANK, null,
                                false, new String[] { MainetConstants.ERRORS }, null,
                                MainetConstants.CommonConstant.BLANK + account.getBaAccountcode() + " "
                                        + ApplicationSession.getInstance().getMessage("bank.account.exist")));
                        populateModel(model, tbBankmaster, FormMode.UPDATE);
                        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
                        return JSP_FORM;

                    }
                }

            }
        }
        if (!bindingResult.hasErrors()) {
            if ((tbBankmaster.getListOfTbBankaccount() != null) && !tbBankmaster.getListOfTbBankaccount().isEmpty()) {
                final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.DATE_FORMATE);
                for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {
                    if ((account.getTempdate() != null) && !account.getTempdate().equals(MainetConstants.CommonConstant.BLANK)) {
                        account.setBaCurrentdate(sdf.parse(account.getTempdate()));
                    }
                }
            }
            setCommonFieldsUpdate(tbBankmaster, httpServletRequest);
            if (!popupMapData.isEmpty()) {
                for (int i = 0; i <= tbBankmaster.getListOfTbBankaccount().size(); i++) {
                    if (popupMapData.containsKey(i)) {
                        final TbBankaccount account = tbBankmaster.getListOfTbBankaccount().get(i);
                        final List<AccountFundMasterBean> fundList = new ArrayList<>();
                        final Map<Long, List<String>> data = popupMapData.get(i);
                        for (final Map.Entry<Long, List<String>> entry : data.entrySet()) {
                            final List<String> fundData = entry.getValue();
                            for (final String fundId : fundData) {
                                final AccountFundMasterBean fund = new AccountFundMasterBean();
                                fund.setFundId(Long.valueOf(fundId));
                                fund.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                                fund.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
                                fund.setLmoddate(new Date());
                                fund.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                                fundList.add(fund);
                            }
                            account.setFunds(fundList);
                        }
                    }
                }
            }
            final TbBankmaster tbBankmasterSaved = tbBankmasterService.update(tbBankmaster);
            tbBankmasterSaved.setSuccessFlag(MainetConstants.MASTER.Y);
            model.addAttribute(ENTITY, tbBankmasterSaved);
            return JSP_FORM;
        } else {
            tbBankmaster.setSuccessFlag(MainetConstants.MASTER.N);
            logger.error("error in update method for binding result");
            return MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW;
        }
    }

    /**
     * @param tbBankmaster
     */
    private void setCommonFieldsUpdate(final TbBankmaster tbBankmaster, final HttpServletRequest httpServletRequest) {
        final UserSession userSession = UserSession.getCurrent();
        tbBankmaster.setUpdatedBy(userSession.getEmployee().getEmpId());
        tbBankmaster.setUpdatedDate(new Date());
        tbBankmaster.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
        tbBankmaster.setOrgid(userSession.getOrganisation().getOrgid());
        tbBankmaster.setLangId((long) userSession.getLanguageId());
        tbBankmaster.setBmStatus(MainetConstants.MASTER.A);
        tbBankmaster.setLmoddate(getLmodDate());
    }

    @RequestMapping(params = "getGridData")
    public @ResponseBody BankMasterResponse gridData(final HttpServletRequest request, final Model model) {

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final BankMasterResponse response = new BankMasterResponse();
        response.setTotal(Banklist.size());
        response.setRecords(Banklist.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, Banklist);
        return response;
    }

    @RequestMapping(params = "searchBank")
    public @ResponseBody void getBankData(@RequestParam("bmBankId") final Long bankId, final HttpServletRequest request,
            final Model model) {

        Banklist.clear();
        final TbBankmaster bank = tbBankmasterService.findById(bankId, UserSession.getCurrent().getOrganisation().getOrgid());
        Banklist.add(bank);
    }

    @RequestMapping(params = "addFundForm")
    public String addFundForm(@RequestParam("count") final String count, final HttpServletRequest request, final Model model) {
        final List<LookUp> fundType = CommonMasterUtility.getListLookup(PrefixConstants.FTP,
                UserSession.getCurrent().getOrganisation());
        @SuppressWarnings("deprecation")
        final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
                PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        fundMasterList = tbAcCodingstructureMasService.getFundMasterList(fundLookup.getLookUpId());
        model.addAttribute(FUND_TYPE, fundType);
        model.addAttribute(FUND_LIST, fundMasterList);
        model.addAttribute(COUNT, count);
        model.addAttribute(MODE, MainetConstants.BANK_MASTER.CREATE_FUND);
        model.addAttribute(SAVE_ACTION, POPUP_ADD);
        final TbBankmaster tbBankmaster = new TbBankmaster();
        tbBankmaster.setRow(Long.valueOf(count));
        model.addAttribute(ENTITY, tbBankmaster);
        return AccountFundMapping;
    }

    @RequestMapping(params = "viewFundForm")
    public String viewFundForm(@RequestParam("count") final String count, @RequestParam("popupMode") final String popupMode,
            final HttpServletRequest request, final Model model) {
        final List<LookUp> fundType = CommonMasterUtility.getListLookup(PrefixConstants.FTP,
                UserSession.getCurrent().getOrganisation());
        @SuppressWarnings("deprecation")
        final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
                PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        fundMasterList = tbAcCodingstructureMasService.getFundMasterList(fundLookup.getLookUpId());
        model.addAttribute(FUND_TYPE, fundType);
        model.addAttribute(FUND_LIST, fundMasterList);
        model.addAttribute(COUNT, count);
        model.addAttribute(MODE, MainetConstants.BANK_MASTER.VIEW_FUND);
        model.addAttribute(SAVE_ACTION, POPUP_SAVE);
        final TbBankmaster tbBankmaster = new TbBankmaster();
        final Integer cnt = Integer.valueOf(count);
        tbBankmaster.setRow(Long.valueOf(count));
        final List<TbBankaccount> listAccounts = new ArrayList<>();
        if (!popupMapData.isEmpty()) {
            if (popupMapData.containsKey(cnt)) {
                final Map<Long, List<String>> data = popupMapData.get(cnt);
                final TbBankaccount account = new TbBankaccount();
                for (final Map.Entry<Long, List<String>> mapdata : data.entrySet()) {
                    final List<AccountFundMasterBean> listFund = new ArrayList<>();
                    final List<String> fundString = mapdata.getValue();
                    for (final String fund : fundString) {
                        final AccountFundMasterBean fundDTO = new AccountFundMasterBean();
                        fundDTO.setFundId(Long.valueOf(fund));
                        listFund.add(fundDTO);
                    }
                    account.setFunds(listFund);
                    listAccounts.add(account);
                }
            }
        }
        tbBankmaster.setListOfTbBankaccount(listAccounts);
        model.addAttribute(POPUP_MODE, popupMode);
        model.addAttribute(ENTITY, tbBankmaster);
        return AccountFundMapping;
    }

    @RequestMapping(params = "saveFundForm")
    public @ResponseBody void saveFundData(final HttpServletRequest request, final Model model, final TbBankmaster tbBankmaster) {
        final Map<Long, List<String>> data = new HashMap<>();
        final List<String> fundData = new ArrayList<>();
        for (final TbBankaccount account : tbBankmaster.getListOfTbBankaccount()) {

            if (account.getFunds() != null) {
                for (final AccountFundMasterBean fund : account.getFunds()) {
                    fundData.add(String.valueOf(fund.getFundId()));
                }
                popupMapData.put(tbBankmaster.getRow().intValue(), data);
            }
        }
    }

    @RequestMapping(params = "validateBankCode")
    public @ResponseBody int validateBankCode(final Model model, @RequestParam("bankCode") final String bankCode) {
        log("Action 'validate BankCode'");
        final int isBankCodeExist = tbBankmasterService.validateBankCode(bankCode,
                UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.CommonConstant.BLANK, MainetConstants.CommonConstant.BLANK);
        return isBankCodeExist;

    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the tbBankaccountService
     */
    public TbBankaccountService getTbBankaccountService() {
        return tbBankaccountService;
    }

    /**
     * @param tbBankaccountService the tbBankaccountService to set
     */
    public void setTbBankaccountService(final TbBankaccountService tbBankaccountService) {
        this.tbBankaccountService = tbBankaccountService;
    }

    /**
     * @return the popupMapData
     */
    public Map<Integer, Map<Long, List<String>>> getPopupMapData() {
        return popupMapData;
    }

    /**
     * @param popupMapData the popupMapData to set
     */
    public void setPopupMapData(final Map<Integer, Map<Long, List<String>>> popupMapData) {
        TbBankmasterController.popupMapData = popupMapData;
    }

    /**
     * @return the fundMasterList
     */
    public List<AccountFundMasterBean> getFundMasterList() {
        return fundMasterList;
    }

    /**
     * @param fundMasterList the fundMasterList to set
     */
    public void setFundMasterList(final List<AccountFundMasterBean> fundMasterList) {
        this.fundMasterList = fundMasterList;
    }

}
