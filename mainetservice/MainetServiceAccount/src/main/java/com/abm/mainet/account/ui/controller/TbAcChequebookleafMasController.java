package com.abm.mainet.account.ui.controller;

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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.account.domain.TbAcChequebookleafDetEntity;
import com.abm.mainet.account.dto.TbAcChequebookleafMas;
import com.abm.mainet.account.service.TbAcChequebookleafMasService;
import com.abm.mainet.account.ui.model.response.TbAcChequebookleafMasResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * Spring MVC controller for 'TbAcChequebookleafMas' management.
 */

@Controller
@RequestMapping("/Chequebookleafmaster.html")
public class TbAcChequebookleafMasController extends
        AbstractController {

    private static final String TB_AC_CHEQUEBOOKLEAF_MAS = "tbAcChequebookleafMas";
    private static final String MAIN_ENTITY_NAME = TB_AC_CHEQUEBOOKLEAF_MAS;
    private static final String MAIN_LIST_NAME = "list";

    private static final String JSP_FORM = "tbAcChequebookleafMas/form";
    private static final String JSP_LIST = "tbAcChequebookleafMas/list";

    private static final String SAVE_ACTION_CREATE = "Chequebookleafmaster.html?create";
    private static final String SAVE_ACTION_UPDATE = "Chequebookleafmaster.html?update";

    private static final String CHEQUEBOOK_MAP = "chequeBookMap";

    @Resource
    private TbAcChequebookleafMasService acChequebookleafMasService;

    @Resource
    private TbAcChequebookleafMasService tbAcChequebookleafMasService;

    @Resource
    private TbBankmasterService banksMasterService;

    @Resource
    private IEmployeeService employeeService;

    private static Logger logger = Logger.getLogger(TbBankmasterController.class);
    private List<TbAcChequebookleafMas> chList = new ArrayList<>();
    private static final String View = "View";
    private String modeView = MainetConstants.BLANK;

    public TbAcChequebookleafMasController() {
        super(TbAcChequebookleafMasController.class,
                MAIN_ENTITY_NAME);
        log("accounts.chequebookleaf.tbacchequebookleafmascontroller");
    }

    @InitBinder
    public void initBinder(final WebDataBinder binder) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                MainetConstants.DATE_FORMAT);
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,
                MainetConstants.TbAcChequebookleafMas.CHQ_BOOK_RETURN_DATE, new CustomDateEditor(
                        dateFormat, true));
        binder.registerCustomEditor(Date.class, MainetConstants.TbAcChequebookleafMas.L_MOD_DATE,
                new CustomDateEditor(dateFormat, true));
    }

    /**
     * Populates the Spring MVC model with the given entity and eventually other useful data
     *
     * @param model
     * @param tbAcChequebookleafMas
     */
    private void populateModel(final Model model,
            final TbAcChequebookleafMas tbAcChequebookleafMas,
            final FormMode formMode) {
        // --- Main entity
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> bankAccountList;
        final Map<Long, String> bankAccountMap = new HashMap<>();
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(PrefixConstants.PaymentMode.USER_ADUSTMENT,
                PrefixConstants.BAS, orgId);
        bankAccountList = banksMasterService.getActiveBankAccountList(orgId, statusId);
        for (final Object[] bankAc : bankAccountList) {
            bankAccountMap.put((Long) bankAc[0],
                    bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
        }

        final List<Employee> emplist = employeeService.findEmpList(orgId);
        model.addAttribute(MAIN_ENTITY_NAME,
                tbAcChequebookleafMas);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE); // The form is in "create"
            model.addAttribute(SAVE_ACTION,
                    SAVE_ACTION_CREATE);

        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE); // The form is in "update"
            model.addAttribute(SAVE_ACTION,
                    SAVE_ACTION_UPDATE);
        }
        model.addAttribute(MainetConstants.TbAcChequebookleafMas.BANK_MAP, bankAccountMap);
        model.addAttribute(MainetConstants.TbAcChequebookleafMas.EMP_LIST, emplist);
    }

    /**
     * Shows a list with all the occurrences of TbAcChequebookleafMas found in the database
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String list(final Model model) {
        log("Action 'list'");
        chList = new ArrayList<>();
        chList.clear();
        final TbAcChequebookleafMas tbAcChequebookleafMas = new TbAcChequebookleafMas();
        model.addAttribute(MAIN_ENTITY_NAME,
                tbAcChequebookleafMas);
        populateModel(model, tbAcChequebookleafMas,
                FormMode.CREATE);
        return JSP_LIST;
    }

    /**
     * Shows a form page in order to create a new TbAcChequebookleafMas
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("Action 'formForCreate'");
        // --- Populates the model with a new instance
        final TbAcChequebookleafMas tbAcChequebookleafMas = new TbAcChequebookleafMas();
        populateModel(model, tbAcChequebookleafMas,
                FormMode.CREATE);
        return JSP_FORM;
    }

    /**
     * Shows a form page in order to check a TbAcChequebookleafMas Data entry duplicate or not
     *
     * @param model Spring MVC model
     * @return
     */
    @RequestMapping(params = "getChequeBookDuplicateData", method = RequestMethod.POST)
    public @ResponseBody boolean findChequeBookDuplicateCombination(final TbAcChequebookleafMas tbAcChequebookleafMas,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult) {
        boolean isValidationError = false;
        if (acChequebookleafMasService.getChequeBookCount(
                tbAcChequebookleafMas.getBmBankid(),
                UserSession.getCurrent().getOrganisation()
                        .getOrgid(),
                tbAcChequebookleafMas.getFromChequeNo(),
                tbAcChequebookleafMas.getToChequeNo()) > 0) {
            bindingResult
                    .addError(new org.springframework.validation.FieldError(
                            TB_AC_CHEQUEBOOKLEAF_MAS,
                            MainetConstants.TbAcChequebookleafMas.FROM_CHQ_NO,
                            null,
                            false,
                            new String[] { MainetConstants.ERRORS },
                            null,
                            ApplicationSession
                                    .getInstance()
                                    .getMessage(
                                            "accounts.chequebookleaf.val_dupchequ")));
            isValidationError = true;
        }
        return isValidationError;
    }

    /**
     * Shows a form page in order to update an existing TbAcChequebookleafMas
     *
     * @param model Spring MVC model
     * @param chequebookId primary key element
     * @return
     */
    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(final Model model,
            @RequestParam("chequebookId") final Long chequebookId) {
        log("Action 'formForUpdate'");
        // --- Search the entity by its primary key and stores it in the model
        final TbAcChequebookleafMas tbAcChequebookleafMas = tbAcChequebookleafMasService
                .findById(chequebookId);

        tbAcChequebookleafMas.setBmBankid(tbAcChequebookleafMas
                .getBaAccountid());
        final String chkbookRtnDate = Utility
                .dateToString(tbAcChequebookleafMas
                        .getChkbookRtnDate());
        tbAcChequebookleafMas
                .setChkbookRtnDatetemp(chkbookRtnDate);
        final String rcptChqbookDate = Utility
                .dateToString(tbAcChequebookleafMas
                        .getRcptChqbookDate());
        tbAcChequebookleafMas
                .setRcptChqbookDatetemp(rcptChqbookDate);
        final Long bmBankidtemp1 = tbAcChequebookleafMas
                .getBmBankid();
        tbAcChequebookleafMas
                .setBmBankidTemp(bmBankidtemp1);
        final Long baAccountid1 = tbAcChequebookleafMas
                .getBaAccountid();
        tbAcChequebookleafMas
                .setBaAccountidTemp(baAccountid1);

        final String dateOfIssue = Utility.dateToString(tbAcChequebookleafMas.getChequeIssueDate());
        tbAcChequebookleafMas.setChequeIssueDateTemp(dateOfIssue);

        populateModel(model, tbAcChequebookleafMas,
                FormMode.UPDATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "viewMode")
    public String update(
            @Valid final TbAcChequebookleafMas tbAcChequebookleafMas,
            final BindingResult bindingResult, final Model model,
            final HttpServletRequest httpServletRequest,
            @RequestParam("MODE1") final String viewmode) {
        log("Action 'update'");
        String viewReturned = MainetConstants.CommonConstant.BLANK;

        if (tbAcChequebookleafMas.getChequebookId() != null) {
            if (viewmode.endsWith(View)) {
                model.addAttribute(MODE, View);
                final TbAcChequebookleafMas tbAcChequebookleafMas1 = tbAcChequebookleafMasService
                        .findById(tbAcChequebookleafMas
                                .getChequebookId());
                log("Action 'update' : update done - redirect");

                tbAcChequebookleafMas1.setBmBankid(tbAcChequebookleafMas1
                        .getBaAccountid());
                final Date rcptchkbookDate = tbAcChequebookleafMas1
                        .getRcptChqbookDate();
                tbAcChequebookleafMas1
                        .setRcptChqbookDatetemp(Utility
                                .dateToString(rcptchkbookDate));

                final String chkbookRtnDate = Utility
                        .dateToString(tbAcChequebookleafMas1
                                .getChkbookRtnDate());
                tbAcChequebookleafMas1
                        .setChkbookRtnDatetemp(chkbookRtnDate);

                final Long baAccountid1 = tbAcChequebookleafMas1
                        .getBaAccountid();
                tbAcChequebookleafMas1
                        .setBaAccountidTemp(baAccountid1);

                final String dateOfIssue = Utility.dateToString(tbAcChequebookleafMas1.getChequeIssueDate());
                tbAcChequebookleafMas1.setChequeIssueDateTemp(dateOfIssue);

                populateModel(model,
                        tbAcChequebookleafMas1,
                        FormMode.UPDATE);

                model.addAttribute(MODE, viewmode);
                viewReturned = JSP_FORM;
            } else {
                model.addAttribute(MODE, MODE_CREATE);
            }
        }
        return viewReturned;
    }

    /**
     * 'CREATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     *
     * @param tbAcChequebookleafMas entity to be created
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "create", method = RequestMethod.POST)
    // GET or POST
    public String create(final TbAcChequebookleafMas tbAcChequebookleafMas, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        String result = MainetConstants.CommonConstant.BLANK;
        final ApplicationSession session = ApplicationSession.getInstance();

        log("Action 'create'");

        if (!bindingResult.hasErrors()) {

            final Date curDate = new Date();
            tbAcChequebookleafMas.setLmoddate(curDate);
            tbAcChequebookleafMas.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcChequebookleafMas.setEmpid(UserSession.getCurrent().getEmployee().getEmpId());
            tbAcChequebookleafMas.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
            tbAcChequebookleafMas.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            tbAcChequebookleafMas.setLangId((long) UserSession.getCurrent().getLanguageId());
            populateModel(model, tbAcChequebookleafMas, FormMode.CREATE);

            tbAcChequebookleafMas.setBaAccountid(tbAcChequebookleafMas.getBmBankid());

            TbAcChequebookleafMas tbAcChequebookleafMasCreated = tbAcChequebookleafMasService
                    .create(tbAcChequebookleafMas);
            if (tbAcChequebookleafMasCreated == null) {
                tbAcChequebookleafMasCreated = new TbAcChequebookleafMas();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcChequebookleafMasCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcChequebookleafMas.getChequebookId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.fieldmaster.success"));
            }
            if (tbAcChequebookleafMas.getChequebookId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.fieldmaster.update"));
            }
            result = JSP_FORM;
        } else {
            tbAcChequebookleafMas.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            final List<BankAccountMasterDto> tbBankaccount = getBankaccountData(httpServletRequest, model,
                    tbAcChequebookleafMas.getBmBankid());
            model.addAttribute(MainetConstants.TbAcChequebookleafMas.LIST_TO_SHOW, tbBankaccount);
            populateModel(model, tbAcChequebookleafMas, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    /**
     * 'UPDATE' action processing. <br>
     * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
     *
     * @param tbAcChequebookleafMas entity to be updated
     * @param bindingResult Spring MVC binding result
     * @param model Spring MVC model
     * @param redirectAttributes Spring MVC redirect attributes
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(params = "update")
    // GET or POST
    public ModelAndView update(
            final TbAcChequebookleafMas tbAcChequebookleafMas,
            final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
        log("Action 'update'");

        if (!bindingResult.hasErrors()) {

            tbAcChequebookleafMas
                    .setUpdatedBy(UserSession
                            .getCurrent().getEmployee()
                            .getEmpId());
            tbAcChequebookleafMas
                    .setUpdatedDate(new Date());
            tbAcChequebookleafMas.setLgIpMac(Utility
                    .getMacAddress());
            tbAcChequebookleafMas.setLgIpMacUpd(Utility
                    .getMacAddress());

            final Date rcptchkbookDate = Utility
                    .stringToDate(tbAcChequebookleafMas
                            .getRcptChqbookDatetemp());
            tbAcChequebookleafMas
                    .setRcptChqbookDate(rcptchkbookDate);

            final Long bmBankid1 = tbAcChequebookleafMas
                    .getBmBankidTemp();
            tbAcChequebookleafMas
                    .setBmBankid(bmBankid1);

            final Long baAccountid1 = tbAcChequebookleafMas
                    .getBaAccountidTemp();
            tbAcChequebookleafMas
                    .setBaAccountid(baAccountid1);

            final Date dateOfIssue = Utility.stringToDate(tbAcChequebookleafMas.getChequeIssueDateTemp());
            tbAcChequebookleafMas.setChequeIssueDate(dateOfIssue);

            final String chequeBookReturnFlag = tbAcChequebookleafMas.getCheckBookReturn();
            if (chequeBookReturnFlag != null) {
                if (chequeBookReturnFlag.equals(MainetConstants.MENU.Y)) {
                    final Date chkbookRtnDate1 = Utility.stringToDate(tbAcChequebookleafMas.getChkbookRtnDatetemp());
                    tbAcChequebookleafMas.setChkbookRtnDate(chkbookRtnDate1);
                }
            }
            final TbAcChequebookleafMas tbAcChequebookleafMasSaved = tbAcChequebookleafMasService
                    .update(tbAcChequebookleafMas);
            model.addAttribute(MAIN_ENTITY_NAME,
                    tbAcChequebookleafMasSaved);
            return new ModelAndView(
                    new MappingJackson2JsonView(),
                    MainetConstants.FORM_NAME,
                    MainetConstants.PAYMENT_STATUS.SUCCESS);
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcChequebookleafMas,
                    FormMode.UPDATE);
            return new ModelAndView(JSP_FORM);
        }
    }

    @RequestMapping(params = "bankaccountList", method = {
            RequestMethod.POST }, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody List<BankAccountMasterDto> getBankaccountData(
            final HttpServletRequest request, final Model model,
            @RequestParam("bmBankid") final Long bmBankid) {

        List<BankAccountMasterDto> tbBankaccount = null;
        // --- Populates the model with a new instance
        tbBankaccount = banksMasterService.findByUlbBankId(bmBankid,
                UserSession.getCurrent().getOrganisation().getOrgid());
        return tbBankaccount;

    }

    @RequestMapping(params = "getLoadGridData")
    public @ResponseBody TbAcChequebookleafMasResponse getLoadGridData(
            final HttpServletRequest request, final ModelMap model) {
        log("Action 'Get grid Data'");
        final TbAcChequebookleafMasResponse tbAcChequebookleafMasResponse = new TbAcChequebookleafMasResponse();
        final int page = Integer.parseInt(request
                .getParameter(MainetConstants.CommonConstants.PAGE));
        tbAcChequebookleafMasResponse.setRows(chList);
        tbAcChequebookleafMasResponse.setTotal(chList
                .size());
        tbAcChequebookleafMasResponse.setRecords(chList
                .size());
        tbAcChequebookleafMasResponse.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);

        return tbAcChequebookleafMasResponse;
    }

    @RequestMapping(params = "getChequeData")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("bmBankid") final Long bmBankid) {
        log("Action-'getChequeData' : 'get getChequeData search data'");

        chList = new ArrayList<>();
        chList.clear();
        chList = acChequebookleafMasService.getChequeData(bmBankid, UserSession.getCurrent().getOrganisation().getOrgid());
        return chList;
    }

    @RequestMapping(params = "getIssuedChequeNumbers")
    public @ResponseBody Map<Long, String> getChequeNumbers(final Model model, @RequestParam("bankAcId") final Long bankAcId) {

        Map<Long, String> chequeBookMap = null;
        final LookUp lkpStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ISSUED.getValue(),
                AccountPrefix.CLR.toString(),
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long statusId = lkpStatus.getLookUpId();
        final List<TbAcChequebookleafDetEntity> chequeList = acChequebookleafMasService.getIssuedChequeNumbers(bankAcId,
                statusId);
        chequeBookMap = new HashMap<>();
        if ((chequeList != null) && !chequeList.isEmpty()) {
            for (final TbAcChequebookleafDetEntity en : chequeList) {
                chequeBookMap.put(en.getChequebookDetid(), en.getChequeNo());

            }
        }
        model.addAttribute(CHEQUEBOOK_MAP, chequeBookMap);
        return chequeBookMap;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }

}
