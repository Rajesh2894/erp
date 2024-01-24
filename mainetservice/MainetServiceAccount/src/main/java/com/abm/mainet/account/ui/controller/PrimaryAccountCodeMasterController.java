package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.ui.model.AccountMasterCommonModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterDTO;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
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
@RequestMapping("/PrimaryAccountCodeMaster.html")
public class PrimaryAccountCodeMasterController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "accountHeadPrimaryAccountCodeMasterBean";

    private static final String JSP_FORM = "primaryCodeMaster/form";
    private static final String JSP_LIST = "primaryCodeMaster/list";

    List<String> storeCompositeCodeList = new ArrayList<>();

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private AccountHeadPrimaryAccountCodeMasterService accountHeadPrimaryAccountCodeMasterService;
    @Resource
    private TbOrganisationService tbOrganisationService;

    public PrimaryAccountCodeMasterController() {
        super(PrimaryAccountCodeMasterController.class, MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST);
        log("TbAcFunctionMasterController created.");
    }

    private void populateModel(final Model model,
            final AccountHeadPrimaryAccountCodeMasterBean accountHeadPrimaryAccountCodeMasterBean,
            final BindingResult bindingResult) {
        final int languageId = UserSession.getCurrent().getLanguageId();

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.AHP, PrefixConstants.CMD,
                languageId, UserSession.getCurrent().getOrganisation());
        getStoreCompositeCodeList().clear();

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }

        List<TbAcCodingstructureDetEntity> acCodingstructureDetEntitys = null;
        List<String> listOfLevels = null;
        if (configurationMasterEntity != null) {
            acCodingstructureDetEntitys = configurationMasterEntity.getTbAcCodingstructureDetEntity();
            accountHeadPrimaryAccountCodeMasterBean.setCodconfigId(configurationMasterEntity.getCodcofId());
        }

        if ((acCodingstructureDetEntitys != null) && !acCodingstructureDetEntitys.isEmpty()) {
            listOfLevels = new LinkedList<>();
            for (final TbAcCodingstructureDetEntity entity : acCodingstructureDetEntitys) {

                listOfLevels.add(entity.getCodDescription());

            }

            Set<AccountHeadPrimaryAccountCodeMasterEntity> setOfParentWithChild = new HashSet<>();
            if (isDafaultOrgExist && primaryDefaultFlag) {
                setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                        .getParentMenuList(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                        .getParentMenuList(UserSession.getCurrent().getOrganisation().getOrgid());
            } else {
                setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                        .getParentMenuList(UserSession.getCurrent().getOrganisation().getOrgid());
            }

            List<AccountHeadPrimaryAccountCodeMasterEntity> list = null;
            if (isDafaultOrgExist && primaryDefaultFlag) {
                list = accountHeadPrimaryAccountCodeMasterService
                        .findAllComppositeCodeOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
            } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
                list = accountHeadPrimaryAccountCodeMasterService
                        .findAllComppositeCodeOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            } else {
                list = accountHeadPrimaryAccountCodeMasterService
                        .findAllComppositeCodeOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }
            for (final AccountHeadPrimaryAccountCodeMasterEntity det : list) {
                getStoreCompositeCodeList().add(det.getPrimaryAcHeadCompcode());
            }
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.NODE, setOfParentWithChild);
           

            accountHeadPrimaryAccountCodeMasterBean.setParentLevel(listOfLevels.get(MainetConstants.EMPTY_LIST));
            final List<LookUp> primaryCodeStatus = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                    UserSession.getCurrent().getOrganisation());

            List<LookUp> budgetCheckFlagLookUp = new ArrayList<>();
            LookUp lookUp = new LookUp();
            lookUp.setLookUpCode(MainetConstants.Y_FLAG);
            lookUp.setDescLangFirst(MainetConstants.Y_FLAG);
            LookUp lookUp1 = new LookUp();
            lookUp1.setLookUpCode(MainetConstants.N_FLAG);
            lookUp1.setDescLangFirst(MainetConstants.N_FLAG);
            budgetCheckFlagLookUp.add(lookUp);
            budgetCheckFlagLookUp.add(lookUp1);

            final List<LookUp> headType = CommonMasterUtility.getLookUps(PrefixConstants.COA,
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.HEAD_TYPE, headType);
            model.addAttribute(MainetConstants.FUNCTION_MASTER.LVL_SIZE, listOfLevels.size());
            model.addAttribute(MainetConstants.FUNCTION_MASTER.GET_LVL, listOfLevels);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.PRIMARY_CODE_STATUS, primaryCodeStatus);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.BUDGET_CHECK_FLAG_LIST, budgetCheckFlagLookUp);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL,
                    acCodingstructureDetEntitys.get(MainetConstants.EMPTY_LIST).getCodDigits());
            model.addAttribute(MainetConstants.CHILD_CODE_DIGIT,
                    acCodingstructureDetEntitys.get(MainetConstants.NUMBER_ONE).getCodDigits());
        } else {
            if (bindingResult != null) {
                bindingResult
                        .addError(new org.springframework.validation.FieldError(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN,
                                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                MainetConstants.FUNCTION_MASTER.CONFIG_MISSING));
            }
            accountHeadPrimaryAccountCodeMasterBean.setParentLevel(null);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL, null);
        }
        model.addAttribute(MAIN_ENTITY_NAME, accountHeadPrimaryAccountCodeMasterBean);
    }

    @RequestMapping()
    public String list(final Model model) {
        String result = StringUtils.EMPTY;
        log("Account Primary Code Master Controller 'list'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;
        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        List<AccountHeadPrimaryAccountCodeMasterBean> list = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            list = accountHeadPrimaryAccountCodeMasterService
                    .findAllWithOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            list = accountHeadPrimaryAccountCodeMasterService
                    .findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = accountHeadPrimaryAccountCodeMasterService
                    .findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        }

        if (isDafaultOrgExist && primaryDefaultFlag) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }

        model.addAttribute(MAIN_ENTITY_NAME, new AccountHeadPrimaryAccountCodeMasterBean());
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);

        Set<AccountHeadPrimaryAccountCodeMasterEntity> setOfParentWithChild = new HashSet<>();
        if (isDafaultOrgExist && primaryDefaultFlag) {
            setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                    .getParentMenuList(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                    .getParentMenuList(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            setOfParentWithChild = accountHeadPrimaryAccountCodeMasterService
                    .getParentMenuList(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.NODE, setOfParentWithChild);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.MODE_FLAG, MainetConstants.EDIT);
        result = JSP_LIST;
        return result;
    }

    public AccountMasterCommonModel getModel() {
        return ApplicationContextProvider.getApplicationContext().getBean(AccountMasterCommonModel.class);
    }

    @RequestMapping(params = "getCodeDigits")
    public @ResponseBody List<Long> getSelectedCodeGigits(@RequestParam("selectValue") final int selectValue,
            @RequestParam("compositeCode") final String compositeCode) {
        final

        boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.AHP, PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        }
        if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }
        final List<Long> detailLists = new ArrayList<>();
        Long codeMaxDigit = 0L;
        Long compositeCodeFlag = 0l;

        for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
            if (selectValue == Integer.parseInt(entity.getCodLevel().toString())) {
                codeMaxDigit = entity.getCodDigits();
                detailLists.add(codeMaxDigit);
                break;
            }

        }

        if (getStoreCompositeCodeList().contains(compositeCode)) {
            compositeCodeFlag = 1l;                           // composite code already present
            detailLists.add(compositeCodeFlag);
        } else {
            detailLists.add(compositeCodeFlag);
        }

        return detailLists;
    }

    @RequestMapping(params = "showLeveledform", method = RequestMethod.POST)
    public String formForUpdate(final Model model) {
        getModel().getMap().clear();
        getModel().getMapWithDesc().clear();
        getModel().getStoreCompositeCodeList().clear();
        if (getModel().getPrimaryCodeMasterBeanForRemoveRecords().getListDto() != null) {
            getModel().getPrimaryCodeMasterBeanForRemoveRecords().getListDto().clear();
        }
        final AccountHeadPrimaryAccountCodeMasterBean bean = new AccountHeadPrimaryAccountCodeMasterBean();
        final AccountHeadPrimaryAccountCodeMasterDTO dto = new AccountHeadPrimaryAccountCodeMasterDTO();
        final List<AccountHeadPrimaryAccountCodeMasterBean> beanList = new ArrayList<>();
        final List<AccountHeadPrimaryAccountCodeMasterDTO> dtoList = new ArrayList<>();
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.PRIMARYCODEMASTER.ADD);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        dtoList.add(dto);
        getModel().setMode(MainetConstants.PRIMARYCODEMASTER.ADD);
        bean.setListDto(dtoList);
        beanList.add(bean);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, beanList);
        populateModel(model, bean, null);
        getModel().getPrimaryCodeMasterBean().setListDto(dtoList);

        return JSP_FORM;
    }

    @RequestMapping(params = "showLeveledformForEdit", method = RequestMethod.POST)
    public String formForEdit(final Model model) {
        getModel().getMap().clear();
        getModel().getMapWithDesc().clear();
        getModel().getStoreCompositeCodeList().clear();
        if (getModel().getPrimaryCodeMasterBeanForRemoveRecords().getListDto() != null) {
            getModel().getPrimaryCodeMasterBeanForRemoveRecords().getListDto().clear();
        }
        final AccountHeadPrimaryAccountCodeMasterBean bean = new AccountHeadPrimaryAccountCodeMasterBean();
        final AccountHeadPrimaryAccountCodeMasterDTO dto = new AccountHeadPrimaryAccountCodeMasterDTO();
        final List<AccountHeadPrimaryAccountCodeMasterBean> beanList = new ArrayList<>();
        final List<AccountHeadPrimaryAccountCodeMasterDTO> dtoList = new ArrayList<>();
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.EDIT);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        dtoList.add(dto);
        getModel().setMode(MainetConstants.EDIT);
        bean.setListDto(dtoList);
        beanList.add(bean);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, beanList);
        populateModel(model, bean, null);

        return JSP_FORM;
    }

    @RequestMapping(params = "createParent", method = RequestMethod.POST)
    public String createParent(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        String result = StringUtils.EMPTY;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        validateUserInputForParent(primaryCodeMasterBean, orgId, bindingResult);
        if (!bindingResult.hasErrors()) {

            primaryCodeMasterBean.setPacStatusCpdId(getActiveStatusId());
            primaryCodeMasterBean.setIpMacAddress(Utility.getClientIpAddress(httpServletRequest));
            accountHeadPrimaryAccountCodeMasterService.createParent(primaryCodeMasterBean, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, primaryCodeMasterBean.getListDto());
            primaryCodeMasterBean.setHasError(MainetConstants.MASTER.Y);
            populateModel(model, primaryCodeMasterBean, bindingResult);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.PRIMARYCODEMASTER.ADD);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (primaryCodeMasterBean.getListDto().size() == MainetConstants.EMPTY_LIST) {
                keepDataAfterValidationError(primaryCodeMasterBean, bindingResult);
            }
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "validateParentCode", method = RequestMethod.POST)
    public @ResponseBody boolean validateParentCode(@RequestParam("parentCode") final String parentCode) {
        log("Action 'validate parent code'");
        boolean isDuplicateCode = false;
        if (getStoreCompositeCodeList().contains(parentCode)) {
            isDuplicateCode = true;
        }
        return isDuplicateCode;
    }

    @RequestMapping(params = "createChild", method = RequestMethod.POST)
    public String createChild(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
        String result = StringUtils.EMPTY;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        primaryCodeMasterBean.setIpMacAddress(Utility.getClientIpAddress(httpServletRequest));
        if (primaryCodeMasterBean.isNewRecord()) {
            validateUserInputForChild(primaryCodeMasterBean, bindingResult);
        }

        final Long functionId = primaryCodeMasterBean.getHiddenParentId();
        primaryCodeMasterBean.setPrimaryAcHeadId(functionId);

        if (!primaryCodeMasterBean.isNewRecord()) {
            AccountHeadPrimaryAccountCodeMasterBean existingBean;
            existingBean = accountHeadPrimaryAccountCodeMasterService.getDetailsUsingFunctionId(primaryCodeMasterBean);
            primaryCodeMasterBean.setListDto(existingBean.getListDto());

            if (primaryCodeMasterBean.getHiddenCodeLevel() == 1) {
                primaryCodeMasterBean.setParentDesc(primaryCodeMasterBean.getEditedDataChildDesc());
                primaryCodeMasterBean.setChildDescReg(primaryCodeMasterBean.getChildDescReg());
                primaryCodeMasterBean.setParentLevel(existingBean.getParentLevel());
                primaryCodeMasterBean.setParentFinalCode(existingBean.getParentFinalCode());
                primaryCodeMasterBean.setBudgetCheckFlag(primaryCodeMasterBean.getBudgetCheckFlag());

            } else {
                primaryCodeMasterBean.setParentDesc(existingBean.getParentDesc());
                primaryCodeMasterBean.setParentLevel(existingBean.getParentLevel());
                primaryCodeMasterBean.setParentFinalCode(existingBean.getParentFinalCode());
            }
        }

        if (!primaryCodeMasterBean.isNewRecord()) {
            for (final AccountHeadPrimaryAccountCodeMasterDTO dto : primaryCodeMasterBean.getListDto()) {
                if (dto.getChildFinalCode().equals(primaryCodeMasterBean.getEditedDataChildCompositeCode().trim())) {
                    dto.setChildDesc(primaryCodeMasterBean.getEditedDataChildDesc());
                    dto.setChildDescReg(primaryCodeMasterBean.getChildDescReg());
                    if (primaryCodeMasterBean.getHiddenCodeLevel().equals(primaryCodeMasterBean.getNoOfLevel())) {
                        dto.setChildPrimaryStatus(primaryCodeMasterBean.getEditedChildStatus());
                        if (primaryCodeMasterBean.getBudgetCheckFlag() != null
                                && !primaryCodeMasterBean.getBudgetCheckFlag().isEmpty()) {
                            dto.setBudgetCheckFlag(primaryCodeMasterBean.getBudgetCheckFlag());
                        }
                    }
                }
            }
        }

        if (!bindingResult.hasErrors()) {

            primaryCodeMasterBean.setPacStatusCpdId(getActiveStatusId());

            accountHeadPrimaryAccountCodeMasterService.createChild(primaryCodeMasterBean, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, primaryCodeMasterBean.getListDto());
            primaryCodeMasterBean.setHasError(MainetConstants.MASTER.Y);
            populateModel(model, primaryCodeMasterBean, bindingResult);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.PRIMARYCODEMASTER.ADD);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (primaryCodeMasterBean.getListDto().size() == MainetConstants.EMPTY_LIST) {
                keepDataAfterValidationError(primaryCodeMasterBean, bindingResult);
            }
            result = JSP_FORM;
        }
        return result;
    }

    private void validateUserInputForParent(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean, final Long orgId,
            final BindingResult bindingResult) {
        final List<Integer> listOfParentCode = accountHeadPrimaryAccountCodeMasterService.getAllParentLevelCodes(orgId);
        listOfParentCode.size();
        final Boolean isMasterExist = true;
        final Iterator<Integer> it = listOfParentCode.iterator();
        primaryCodeValidationForParent(primaryCodeMasterBean, bindingResult, isMasterExist, it);
    }

    public void primaryCodeValidationForParent(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult,
            Boolean isMasterExist, final Iterator<Integer> it) {
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentCode())) {
            bindingResult
                    .addError(new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage("account.master.sel.primAcCode")));
        }
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentDesc())) {
            bindingResult
                    .addError(new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage("account.master.sel.primAcDesc")));
        }
        if (MainetConstants.BLANK.equals(primaryCodeMasterBean.getParentFinalCode())) {
            bindingResult
                    .addError(new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            ApplicationSession.getInstance().getMessage("account.master.sel.primAcFinalCode")));
        }

        if (primaryCodeMasterBean.getPrimaryAcHeadId() == null) {
            while (it.hasNext()) {
                if (primaryCodeMasterBean.getParentCode().equals(it.next())) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    ApplicationSession.getInstance().getMessage("function.master.parentCodeExist")));
                    isMasterExist = true;
                    break;
                }

            }
        }

    }

    private void validateUserInputForChild(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult) {
        primaryCodeValidationForChild(primaryCodeMasterBean, bindingResult);
    }

    public void primaryCodeValidationForChild(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult) {
        for (final AccountHeadPrimaryAccountCodeMasterDTO dto : primaryCodeMasterBean.getListDto()) {

            if (MainetConstants.ZERO.equals(dto.getChildLevel()) || MainetConstants.BLANK.equals(dto.getChildCode()) ||
                    dto.getChildDesc().equals(MainetConstants.BLANK)) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                        MainetConstants.FUNCTION_MASTER.PARENT_LVL, null, false, new String[] { MainetConstants.ERRORS }, null,
                        ApplicationSession.getInstance().getMessage("account.master.sel.reqField")));
            }

            if (MainetConstants.ZERO.equals(dto.getChildLevel())) {
                bindingResult.addError(
                        new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                ApplicationSession.getInstance().getMessage("account.master.selectChild")));
            }
            if (MainetConstants.BLANK.equals(dto.getChildCode())) {
                bindingResult.addError(
                        new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                ApplicationSession.getInstance().getMessage("account.master.emptyChildCode")));
            }
            if (dto.getChildDesc().equals(MainetConstants.BLANK)) {
                bindingResult.addError(
                        new org.springframework.validation.FieldError(MainetConstants.PRIMARYCODEMASTER.PRIMARYCODE_BEAN,
                                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                ApplicationSession.getInstance().getMessage("account.master.duplicateChildCode")));
            }

        }

    }

    private List<AccountHeadPrimaryAccountCodeMasterDTO> keepDataAfterValidationError(
            final AccountHeadPrimaryAccountCodeMasterBean primaryCodeBean, final BindingResult bindingResult) {
        ArrayList<AccountHeadPrimaryAccountCodeMasterDTO> list = null;
        if (primaryCodeBean.getListDto().size() == MainetConstants.EMPTY_LIST) {
            list = new ArrayList<>();
            list.add(new AccountHeadPrimaryAccountCodeMasterDTO());
        }
        primaryCodeBean.setListDto(list);
        return list;

    }

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ModelAndView myError(final Exception exception) {
        log("----Caught" + exception.getClass().getSimpleName() + "FoundException----");
        final ModelAndView mav = new ModelAndView("error/generic_errorView");
        mav.addObject(MainetConstants.AccountDepositeAndAdvnHeadsMapping.NAME, exception.getClass().getSimpleName());
        mav.addObject(MainetConstants.AccountDepositeAndAdvnHeadsMapping.MESSAGE, exception.getMessage());
        return mav;
    }

    public Long getActiveStatusId() {
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }

    public Long getOtherLedgerId() {
        final LookUp otherledgerLookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.PRIMARYCODEMASTER.OT,
                PrefixConstants.FTY, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long otherId = otherledgerLookUp.getLookUpId();
        return otherId;
    }

    /**
     * @return the storeCompositeCodeList
     */
    public List<String> getStoreCompositeCodeList() {
        return storeCompositeCodeList;
    }

    /**
     * @param storeCompositeCodeList the storeCompositeCodeList to set
     */
    public void setStoreCompositeCodeList(final List<String> storeCompositeCodeList) {
        this.storeCompositeCodeList = storeCompositeCodeList;
    }

    @RequestMapping(params = "getLevel")
    public @ResponseBody List<Long> getSelectedLevel(@RequestParam("selectValue") final int selectValue,
            @RequestParam("compositeCode") final String compositeCode) {
        final

        boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean primaryDefaultFlag = false;

        if (isDafaultOrgExist) {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            primaryDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.AHP,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }

        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.AHP, PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        if (isDafaultOrgExist && primaryDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        }
        if (isDafaultOrgExist && (primaryDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }

        final List<Long> detailLists = new ArrayList<>();
        Long codeMaxDigit = 0L;
        Long compositeCodeFlag = 0l;

        for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
            if (selectValue == Integer.parseInt(entity.getCodLevel().toString())) {
                codeMaxDigit = entity.getCodDigits();
                detailLists.add(codeMaxDigit);
                break;
            }

        }

        if (getStoreCompositeCodeList().contains(compositeCode)) {
            compositeCodeFlag = 1l;                           // composite code already present
            detailLists.add(compositeCodeFlag);
        } else {
            detailLists.add(compositeCodeFlag);
        }

        return detailLists;
    }
    @RequestMapping(params = "getHeadCodeDescReg")
    @ResponseBody
    public String getHeadCodeDescRegByHeadId(@RequestParam("headId") Long headId) {
    	
    	String headCodeDesReg = accountHeadPrimaryAccountCodeMasterService.getHeadCodeDesReg(headId);
    	if(StringUtils.isNotBlank(headCodeDesReg)) {
    		return headCodeDesReg;
    	}else {
    		return "";
   	}
    	
    }
}
