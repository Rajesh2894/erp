package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.ui.model.AccountMasterCommonModel;
import com.abm.mainet.account.ui.model.response.AccountFundMasterResponse;
import com.abm.mainet.account.ui.validator.FundMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.dao.AccountFundMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFundDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountFundMaster.html")
public class AccountFundMasterController extends AbstractController {

    private static final String MAIN_LIST_NAME = MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST;

    private static final String JSP_FORM = "tbAcFundMaster/form";

    private static final String JSP_VIEW = "tbAcFundMaster/view";

    private static final String JSP_LIST = "tbAcFundMaster/list";

    @Resource
    private AccountFundMasterService tbAcFundMasterService;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private AccountFundMasterDao accountFundMasterDao;

    @Resource
    private TbOrganisationService tbOrganisationService;

    private String modeView = MainetConstants.BLANK;

    public AccountFundMasterController() {
        super(AccountFundMasterController.class, MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN);
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountFundMasterResponse gridData(final HttpServletRequest request, final Model model) {
        log("AccountFundMaster-'gridData'  'Get grid Data'");
        AccountFundMasterResponse AccountFundMasterResponse = null;

        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));

        // changes was doing on Default Org Flag Checking related.!
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        List<AccountFundMasterBean> list = null;
        if (isDafaultOrgExist && fieldDefaultFlag) {
            list = tbAcFundMasterService
                    .findAllParentFunds(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (fieldDefaultFlag == false)) {
            list = tbAcFundMasterService.findAllParentFunds(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = tbAcFundMasterService.findAllParentFunds(UserSession.getCurrent().getOrganisation().getOrgid());
        }

        final List<AccountFundMasterBean> listOfFundBean = new ArrayList<>();
        if ((list != null) && !list.isEmpty()) {
            for (final AccountFundMasterBean bean : list) {
                // changes was doing on Default Org Flag Checking related.!
                if (isDafaultOrgExist && fieldDefaultFlag) {
                    if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                            .getSuperUserOrganization().getOrgid())) {
                        bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                    }
                } else {
                    bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
                listOfFundBean.add(bean);
            }
        }
        AccountFundMasterResponse = new AccountFundMasterResponse();
        AccountFundMasterResponse.setRows(listOfFundBean);
        AccountFundMasterResponse.setTotal(list.size());
        AccountFundMasterResponse.setRecords(list.size());
        AccountFundMasterResponse.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, list);

        return AccountFundMasterResponse;
    }

    @RequestMapping(params = "populateParentCode", method = RequestMethod.POST)
    public @ResponseBody Set<?> getEnteredParentCode(@RequestParam("selectLevel") final String selectLevel) {
        return getModel().getMapWithDesc().get(selectLevel);
    }

    public AccountMasterCommonModel getModel() {
        return ApplicationContextProvider.getApplicationContext().getBean(AccountMasterCommonModel.class);
    }

    @RequestMapping(params = "storeChildDetails", method = RequestMethod.POST)
    public @ResponseBody String storeChildCode(@RequestParam("childLevel") final String childLevel,
            @RequestParam("childParentLevel") final String childParentLevel,
            @RequestParam("childCode") final String childCode, @RequestParam("childParentCode") final String childParentCode,
            @RequestParam("childDesc") final String childDesc) {

        String finalCode = MainetConstants.CommonConstant.BLANK;

        final AccountMasterCommonModel model = getModel();
        HashSet<String> hashSetWithDesc = new HashSet<>();
        HashSet<String> hashSet = new HashSet<>();
        if (model.getMap().containsKey(childLevel)) {
            hashSet = model.getMap().get(childLevel);
            hashSetWithDesc = model.getMapWithDesc().get(childLevel);
            if (childLevel.equals(MainetConstants.ONE)) {
                hashSet.clear();
                hashSetWithDesc.clear();
                hashSet.add(childCode);
                hashSetWithDesc.add(childCode);
            } else {
                hashSet.add(childParentCode + MainetConstants.HYPHEN + childCode);
                hashSetWithDesc.add(childParentCode + MainetConstants.HYPHEN + childCode
                        + MainetConstants.FUND_MASTER.OPEN_BRACKET + childDesc + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                finalCode = childParentCode + MainetConstants.HYPHEN + childCode;
            }
        } else {

            if (childLevel.equals(MainetConstants.ONE)) {
                hashSet.clear();
                hashSet.add(childCode);
                hashSetWithDesc.clear();
                hashSetWithDesc.add(childCode);
            } else {
                hashSet.add(childParentCode + MainetConstants.HYPHEN + childCode);
                hashSetWithDesc.add(childParentCode + MainetConstants.HYPHEN + childCode
                        + MainetConstants.FUND_MASTER.OPEN_BRACKET + childDesc + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                finalCode = childParentCode + MainetConstants.HYPHEN + childCode;
            }
        }
        model.getMap().put(childLevel, hashSet);
        model.setMap(model.getMap());
        model.getMapWithDesc().put(childLevel, hashSetWithDesc);
        model.setMapWithDesc(model.getMapWithDesc());

        return finalCode;

    }

    @RequestMapping(params = "getCodeDigits")
    public @ResponseBody List<Long> getSelectedCodeGigits(@RequestParam("selectValue") final int selectValue) {

        // changes was done by using the Default Org Flag is Checking or not.!
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        if (isDafaultOrgExist && fieldDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else if (isDafaultOrgExist && (fieldDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }
        final List<Long> detailLists = new ArrayList<>();
        Long statusViewFlag = 0l;
        final Long activeId = 0l;
        Long codeMaxDigit = 0L;

        for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
            if (selectValue == Integer.parseInt(entity.getCodLevel().toString())) {
                codeMaxDigit = entity.getCodDigits();
                detailLists.add(codeMaxDigit);
                break;
            }
        }
        if (configurationMasterEntity.getCodNoLevel() == selectValue) {
            statusViewFlag = 1l;
            detailLists.add(statusViewFlag);
            detailLists.add(getActiveStatusId());
        } else {
            detailLists.add(statusViewFlag);
            detailLists.add(activeId);
        }
        return detailLists;
    }

    @RequestMapping(params = "validateDuplicateCompositeCode")
    public @ResponseBody boolean validateDupilcateCode(@RequestParam("childFinalCode") final String childFinalCode,
            @RequestParam("addOrRemoveRow") final String addOrRemoveRow) {
        boolean isDuplicate = false;
        if (getModel().getMode().equals(MainetConstants.PRIMARYCODEMASTER.ADD)) {

            if (addOrRemoveRow.equals(MainetConstants.FIELD_MASTER.REMOVE_ROW)) {
                if (getModel().getStoreCompositeCodeList().contains(childFinalCode)) {
                    getModel().getStoreCompositeCodeList().remove(childFinalCode);
                }
            }
            if (addOrRemoveRow.equals(MainetConstants.FIELD_MASTER.ADD_ROW)) {
                if (getModel().getStoreCompositeCodeList().contains(childFinalCode)) {
                    isDuplicate = true;
                } else {
                    getModel().getStoreCompositeCodeList().add(childFinalCode);
                }
            }
        } else {

            final List<AccountFundDto> listDto = getModel().getFundMasterBeanForRemoveRecords().getListDto();
            for (final AccountFundDto dto : listDto) {
                if (dto.getChildFinalCode().equals(childFinalCode.trim())) {
                    isDuplicate = true;
                }
            }
        }

        return isDuplicate;
    }

    private void populateModel(final Model model, final AccountFundMasterBean accountFundMasterBean, final FormMode formMode) {
        log("AccountFundMaster-'populateModel' : populate model");

        // changes was doing on Default Org Flag Checking related.!
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fundDefaultFlag = false;

        if (isDafaultOrgExist) {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        if (isDafaultOrgExist && fundDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }

        final List<String> listOfLevels = new ArrayList<>();
        final List<Long> levels = new LinkedList<>();
        final Map<Long, String> lavelMap = new TreeMap<>();
        final Map<Long, String> lavelParentMap = new TreeMap<>();
        final Map<Long, Long> codingDigitMap = new TreeMap<>();
        if ((configurationMasterEntity.getTbAcCodingstructureDetEntity() != null)
                && !configurationMasterEntity.getTbAcCodingstructureDetEntity().isEmpty()) {
            model.addAttribute(MainetConstants.MAX_PARENT_LVL,
                    configurationMasterEntity.getTbAcCodingstructureDetEntity().get(MainetConstants.EMPTY_LIST).getCodDigits());
            for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
                if (entity.getCodLevel().equals(1L)) {
                    accountFundMasterBean.setParentLevel(entity.getCodDescription());
                    accountFundMasterBean.setParentLevelCode(entity.getCodLevel());
                    codingDigitMap.put(entity.getCodLevel(), entity.getCodDigits());

                } else {
                    lavelMap.put(entity.getCodLevel(), entity.getCodDescription());
                    levels.add(entity.getCodLevel());
                    codingDigitMap.put(entity.getCodLevel(), entity.getCodDigits());
                }
                lavelParentMap.put(entity.getCodLevel(), entity.getCodDescription());
            }
        } else {
            accountFundMasterBean.setParentLevel(null);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL, null);
        }

        accountFundMasterBean.setCodconfigId(configurationMasterEntity.getCodcofId());
        final List<LookUp> fundStatus = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.FUND_MASTER.FUND_STATUS, fundStatus);
        model.addAttribute(MainetConstants.FUND_MASTER.GET_LVLS, listOfLevels);
        model.addAttribute(MainetConstants.FUND_MASTER.CODING_DIGIT_MAP, codingDigitMap);
        model.addAttribute(MainetConstants.FUND_MASTER.LEVELS_MAP, lavelMap);
        model.addAttribute(MainetConstants.FUND_MASTER.LEVELS_PARENT_MAP, lavelParentMap);
        model.addAttribute(MainetConstants.FUND_MASTER.LEVELS, levels);
        model.addAttribute(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN, accountFundMasterBean);
    }

    @RequestMapping()
    public String list(final Model model) {
        log("AccountFundMasterController 'list'");

        // changes was doing on Default Org Flag Checking related.!
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fundDefaultFlag = false;
        if (isDafaultOrgExist) {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist && fundDefaultFlag) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }

        model.addAttribute(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN, new AccountFundMasterBean());
        List<AccountFundMasterBean> list = null;
        if (isDafaultOrgExist && fundDefaultFlag) {
            list = tbAcFundMasterService
                    .findAllParentFunds(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
            list = tbAcFundMasterService.findAllParentFunds(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = tbAcFundMasterService.findAllParentFunds(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        return JSP_LIST;
    }

    @RequestMapping(params = "formForUpdate")
    public String formForUpdate(@RequestParam("fundId") final Long fundId, final Model model,
            @RequestParam("MODE_DATA") final String viewmode)
            throws Exception {
        log("AccountFundMaster-'formForUpdate' : 'formForUpdate'");
        setModeView(viewmode);
        getModel().getMap().clear();
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
        }

        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
        model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
        final AccountFundMasterBean bean = new AccountFundMasterBean();
        final List<AccountFundMasterBean> list = new ArrayList<>();
        final List<AccountFundDto> dtoList = new ArrayList<>();
        final AccountFundDto dto = new AccountFundDto();
        dtoList.add(dto);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.PRIMARYCODEMASTER.ADD);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        getModel().setMode(MainetConstants.PRIMARYCODEMASTER.ADD);
        bean.setParentLevel(MainetConstants.FUND_MASTER.FUND_GROUP_CODE);
        bean.setListDto(dtoList);
        list.add(bean);
        model.addAttribute(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN, bean);
        model.addAttribute(MainetConstants.FUND_MASTER.MAIN_LIST_NAME, list);
        populateModel(model, bean, FormMode.UPDATE);
        return JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountFundMasterBean accountFundMasterBean, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFundMaster-'create' : 'create'");
        validateFundMaster(accountFundMasterBean, bindingResult);
        for (final AccountFundDto dto : accountFundMasterBean.getListDto()) {
            for (final AccountFundDto childDto : accountFundMasterBean.getListDto()) {
                if (dto.getChildFinalCode().equals(childDto.getChildParentCode())) {
                    childDto.setChildParentCode(dto.getChildCode());
                }
            }
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        accountFundMasterBean.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

        if (!bindingResult.hasErrors()) {

            final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                    PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long parentActiveStatus = lookUpFieldStatus.getLookUpId();
            accountFundMasterBean.setFundStatusCpdId(parentActiveStatus);

            final TbAcCodingstructureDetEntity det = new TbAcCodingstructureDetEntity();
            tbAcFundMasterService.create(accountFundMasterBean, det, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            return JSP_FORM;
        } else {
            accountFundMasterBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            final List<AccountFundDto> list = keepDataAfterValidationError(accountFundMasterBean, bindingResult);
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, accountFundMasterBean, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    private void validateFundMaster(final AccountFundMasterBean accountFundMasterBean, final BindingResult bindingResult) {
        final FundMasterValidator validator = new FundMasterValidator();
        validator.validate(accountFundMasterBean, bindingResult);
        if (accountFundMasterBean.getFundId() == null) {
            final String fundCode = accountFundMasterBean.getParentCode();
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (fundCode != null) {
                if (tbAcFundMasterService.isExist(fundCode, orgId)) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN,
                                    MainetConstants.FUND_MASTER.PARENT_CODE, null, false, new String[] { MainetConstants.ERRORS },
                                    null, ApplicationSession.getInstance().getMessage("fund.master.parentCodeExist")));
                }
            }
        }
    }

    private List<AccountFundDto> keepDataAfterValidationError(final AccountFundMasterBean tbAcFundMaster,
            final BindingResult bindingResult)
            throws Exception {
        log("AccountFundMaster-'keepDataAfterValidationError' : Validation error'");
        ArrayList<AccountFundDto> list = null;
        final ArrayList<AccountFundMasterBean> beanList = new ArrayList<>();
        if ((tbAcFundMaster.getListDto() == null) || (tbAcFundMaster.getListDto().size() == 0)) {
            list = new ArrayList<>();
            list.add(new AccountFundDto());
            tbAcFundMaster.setListDto(list);
        }
        beanList.add(tbAcFundMaster);
        return tbAcFundMaster.getListDto();
    }

    @RequestMapping(params = "saveEditedData", method = RequestMethod.POST)
    public String saveEditedData(final AccountFundMasterBean tbAcFundMaster, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        tbAcFundMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

        if (!tbAcFundMaster.isNewRecord()) {
            tbAcFundMaster.setParentCode(getModel().getFundMasterBean().getParentCode());
            if (getModel().getFundMasterBean().getParentDesc() == null) {
                getModel().getFundMasterBean().setParentDesc(MainetConstants.CommonConstant.BLANK);
            }

            if ((tbAcFundMaster.getEditedDataChildParentCode() != null)
                    && tbAcFundMaster.getEditedDataChildParentCode().equals(MainetConstants.CommonConstant.BLANK)) {
                tbAcFundMaster.setParentDesc(tbAcFundMaster.getEditedDataChildDesc());
                tbAcFundMaster.setParentLevel(getModel().getFundMasterBean().getParentLevel());
                tbAcFundMaster.setParentFinalCode(getModel().getFundMasterBean().getParentFinalCode());
            } else {
                tbAcFundMaster.setParentDesc(getModel().getFundMasterBean().getParentDesc());
                tbAcFundMaster.setParentLevel(getModel().getFundMasterBean().getParentLevel());
                tbAcFundMaster.setParentFinalCode(getModel().getFundMasterBean().getParentFinalCode());
            }
        }
        List<AccountFundDto> newAddedList = null;

        if (tbAcFundMaster.isNewRecord()) {
            newAddedList = tbAcFundMaster.getListDto();
            getModel().getFundMasterBean().getListDto().addAll(newAddedList);
        } else {
            tbAcFundMaster.setListDto(getModel().getFundMasterBean().getListDto());
        }

        if (!tbAcFundMaster.isNewRecord()) {
            for (final AccountFundDto dto : tbAcFundMaster.getListDto()) {
                if (dto.getChildFinalCode().equals(tbAcFundMaster.getEditedDataChildCompositeCode().trim())) {
                    dto.setChildDesc(tbAcFundMaster.getEditedDataChildDesc());
                    if (tbAcFundMaster.getEditedChildStatus() != null) {
                        dto.setChildFundStatus(tbAcFundMaster.getEditedChildStatus());
                    }
                }
            }
        }

        if (!bindingResult.hasErrors()) {
            tbAcFundMasterService.saveEditedData(tbAcFundMaster, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            return JSP_FORM;
        } else {

            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFundMaster.getListDto());
            populateModel(model, tbAcFundMaster, FormMode.UPDATE);
            return MainetConstants.FUNCTION_MASTER.JSP_FORM;
        }

    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountFundMasterBean tbAcFundMaster, final BindingResult bindingResult, final Model model,
            @RequestParam("fundId") final Long fundId, @RequestParam("MODE_DATA") final String viewmode,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFundMaster- 'update' : 'update'");

        getModel().setMode(MainetConstants.EDIT);
        final AccountMasterCommonModel sessionDto = getModel();
        if (!bindingResult.hasErrors()) {
            tbAcFundMaster = tbAcFundMasterService.getDetailsUsingFundId(tbAcFundMaster);
            sessionDto.getMap().clear();
            HashSet<String> set = null;
            set = new HashSet<>();
            set.add(tbAcFundMaster.getParentCode());
            HashSet<String> setWithDesc = new HashSet<>();
            sessionDto.setFundMasterBean(tbAcFundMaster);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            setWithDesc.add(tbAcFundMaster.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                    + tbAcFundMaster.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
            for (final AccountFundDto masterDto : tbAcFundMaster.getListDto()) {
                sessionDto.getStoreCompositeCodeList().add(masterDto.getChildFinalCode());
                set = new HashSet<>();
                setWithDesc = new HashSet<>();
                if (sessionDto.getMap().containsKey(masterDto.getChildLevelCode().toString())) {
                    set = sessionDto.getMap().get(masterDto.getChildLevelCode().toString());
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevelCode().toString(), set);

                    setWithDesc = sessionDto.getMapWithDesc().get(masterDto.getChildLevelCode().toString());
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevelCode().toString(), setWithDesc);
                } else {
                    set.clear();
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevelCode().toString(), set);

                    setWithDesc.clear();
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevelCode().toString(), setWithDesc);
                }

            }
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (MainetConstants.VIEW.equals(viewmode)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
                final Set<AccountFundMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFundMasterDao.getParentDetailsUsingFundId(tbAcFundMaster));
                model.addAttribute(MainetConstants.FUND_MASTER.SET_OF_NODE, setOfEntityWithChild);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
                sessionDto.getFundMasterBean().getListDto().clear();
                final AccountFundMasterBean bean = new AccountFundMasterBean();
                bean.setListDto(tbAcFundMaster.getListDto());
                sessionDto.setFundMasterBeanForRemoveRecords(tbAcFundMasterService.getDetailsUsingFundId(tbAcFundMaster));
            }

            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            tbAcFundMaster.setFundId(fundId);

            if ((tbAcFundMaster.getListDto() == null) || tbAcFundMaster.getListDto().isEmpty()) {
                keepDataAfterValidationError(tbAcFundMaster, bindingResult);
            }
            model.addAttribute(MainetConstants.FUND_MASTER.MAIN_LIST_NAME, tbAcFundMaster.getListDto());
            model.addAttribute(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN, tbAcFundMaster);

            populateModel(model, tbAcFundMaster, FormMode.UPDATE);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            return JSP_FORM;
        } else {
            log("AccountFundMaster 'update' : binding errors");
            populateModel(model, tbAcFundMaster, FormMode.UPDATE);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "view", method = RequestMethod.POST)
    public String view(AccountFundMasterBean tbAcFundMaster, final BindingResult bindingResult, final Model model,
            @RequestParam("fundId") final Long fundId, @RequestParam("MODE_DATA") final String viewmode,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFundMaster- 'view' : 'view'");

        getModel().setMode(MainetConstants.EDIT);
        final AccountMasterCommonModel sessionDto = getModel();
        if (!bindingResult.hasErrors()) {
            tbAcFundMaster = tbAcFundMasterService.getDetailsUsingFundId(tbAcFundMaster);
            sessionDto.getMap().clear();
            HashSet<String> set = null;
            set = new HashSet<>();
            set.add(tbAcFundMaster.getParentCode());
            HashSet<String> setWithDesc = new HashSet<>();
            sessionDto.setFundMasterBean(tbAcFundMaster);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            setWithDesc.add(tbAcFundMaster.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                    + tbAcFundMaster.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
            for (final AccountFundDto masterDto : tbAcFundMaster.getListDto()) {
                sessionDto.getStoreCompositeCodeList().add(masterDto.getChildFinalCode());
                set = new HashSet<>();
                setWithDesc = new HashSet<>();
                if (sessionDto.getMap().containsKey(masterDto.getChildLevelCode().toString())) {
                    set = sessionDto.getMap().get(masterDto.getChildLevelCode().toString());
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevelCode().toString(), set);

                    setWithDesc = sessionDto.getMapWithDesc().get(masterDto.getChildLevelCode().toString());
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevelCode().toString(), setWithDesc);
                } else {
                    set.clear();
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevelCode().toString(), set);

                    setWithDesc.clear();
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevelCode().toString(), setWithDesc);
                }

            }
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (MainetConstants.VIEW.equals(viewmode)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.SUCCESS);
                final Set<AccountFundMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFundMasterDao.getParentDetailsUsingFundId(tbAcFundMaster));
                model.addAttribute(MainetConstants.FUND_MASTER.SET_OF_NODE, setOfEntityWithChild);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
                sessionDto.getFundMasterBean().getListDto().clear();
                final AccountFundMasterBean bean = new AccountFundMasterBean();
                bean.setListDto(tbAcFundMaster.getListDto());
                sessionDto.setFundMasterBeanForRemoveRecords(tbAcFundMasterService.getDetailsUsingFundId(tbAcFundMaster));
            }

            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            tbAcFundMaster.setFundId(fundId);

            if ((tbAcFundMaster.getListDto() == null) || tbAcFundMaster.getListDto().isEmpty()) {
                keepDataAfterValidationError(tbAcFundMaster, bindingResult);
            }
            model.addAttribute(MainetConstants.FUND_MASTER.MAIN_LIST_NAME, tbAcFundMaster.getListDto());
            model.addAttribute(MainetConstants.FUND_MASTER.ACCOUNT_FUND_MASTER_BEAN, tbAcFundMaster);

            populateModel(model, tbAcFundMaster, FormMode.UPDATE);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
            model.addAttribute(MainetConstants.FUND_MASTER.MODE_VIEW, getModeView());
            return JSP_VIEW;
        } else {
            log("AccountFundMaster 'update' : binding errors");
            populateModel(model, tbAcFundMaster, FormMode.UPDATE);
            return JSP_VIEW;
        }
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }

    public Long getActiveStatusId() {
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }
}
