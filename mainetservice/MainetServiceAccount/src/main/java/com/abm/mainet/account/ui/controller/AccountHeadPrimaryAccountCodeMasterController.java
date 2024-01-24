package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.abm.mainet.account.ui.model.response.AccountPrimaryCodeMasterResponse;
import com.abm.mainet.account.ui.validator.AccountPrimaryCodeValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.dao.AccountHeadPrimaryAccountCodeMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterBean;
import com.abm.mainet.common.integration.acccount.dto.AccountHeadPrimaryAccountCodeMasterDTO;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/AccountHeadPrimaryAccountCodeMaster.html")
public class AccountHeadPrimaryAccountCodeMasterController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "accountHeadPrimaryAccountCodeMasterBean";

    private static final String JSP_FORM = "acHeadPrimaryCodeMaster/form";
    private static final String JSP_LIST = "acHeadPrimaryCodeMaster/list";

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterDao accountHeadPrimaryAccountCodeMasterDao;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService accountHeadPrimaryAccountCodeMasterService;

    public AccountHeadPrimaryAccountCodeMasterController() {
        super(AccountHeadPrimaryAccountCodeMasterController.class, MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST);
        log("TbAcFunctionMasterController created.");
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountPrimaryCodeMasterResponse gridData(final HttpServletRequest request) {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<AccountHeadPrimaryAccountCodeMasterBean> list = accountHeadPrimaryAccountCodeMasterService.findAllWithOrgId(orgId);
        final AccountPrimaryCodeMasterResponse primaryAccCodeMasterResponse = new AccountPrimaryCodeMasterResponse();
        if (list == null) {
            list = new ArrayList<>();
        }
        primaryAccCodeMasterResponse.setTotal(list.size());
        primaryAccCodeMasterResponse.setRecords(list.size());
        primaryAccCodeMasterResponse.setRows(list);
        primaryAccCodeMasterResponse.setPage(page);
        return primaryAccCodeMasterResponse;
    }

    private void populateModel(final Model model,
            final AccountHeadPrimaryAccountCodeMasterBean accountHeadPrimaryAccountCodeMasterBean,
            final BindingResult bindingResult) {
        final int languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.AHP, PrefixConstants.CMD,
                languageId, UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        final TbAcCodingstructureMasEntity configurationMasterEntity = tbAcCodingstructureMasService
                .findConfigurationMasterEntiry(
                        lkp.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);

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

            accountHeadPrimaryAccountCodeMasterBean.setParentLevel(listOfLevels.get(MainetConstants.EMPTY_LIST));
            final List<LookUp> primaryCodeStatus = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                    UserSession.getCurrent().getOrganisation());
            model.addAttribute(MainetConstants.FUNCTION_MASTER.LVL_SIZE, listOfLevels.size());
            model.addAttribute(MainetConstants.FUNCTION_MASTER.GET_LVL, listOfLevels);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.PRIMARY_CODE_STATUS, primaryCodeStatus);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL,
                    acCodingstructureDetEntitys.get(MainetConstants.EMPTY_LIST).getCodDigits());
            model.addAttribute(MainetConstants.CHILD_CODE_DIGIT,
                    acCodingstructureDetEntitys.get(MainetConstants.NUMBER_ONE).getCodDigits());
        } else {
            if (bindingResult != null) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null, MainetConstants.FUNCTION_MASTER.CONFIG_MISSING));
            }
            accountHeadPrimaryAccountCodeMasterBean.setParentLevel(null);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL, null);
        }
        model.addAttribute(MAIN_ENTITY_NAME, accountHeadPrimaryAccountCodeMasterBean);
    }

    @RequestMapping()
    public String list(final Model model) {
        log("Account Primary Code Master Controller 'list'");
        model.addAttribute(MAIN_ENTITY_NAME, new AccountHeadPrimaryAccountCodeMasterBean());
        final List<AccountHeadPrimaryAccountCodeMasterBean> list = accountHeadPrimaryAccountCodeMasterService
                .findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        return JSP_LIST;
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
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.AHP, PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        final TbAcCodingstructureMasEntity configurationMasterEntity = tbAcCodingstructureMasService
                .findConfigurationMasterEntiry(
                        lkp.getLookUpId(), UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        final List<Long> detailLists = new ArrayList<>();
        Long codeMaxDigit = 0L;
        Long statusViewFlag = 0l;
        final Long activeId = 0l;

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
        if (getModel().getMode().equals(MainetConstants.CommonConstants.ADD)) {

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

            final List<AccountHeadPrimaryAccountCodeMasterDTO> listDto = getModel().getPrimaryCodeMasterBeanForRemoveRecords()
                    .getListDto();
            for (final AccountHeadPrimaryAccountCodeMasterDTO dto : listDto) {
                if (dto.getChildFinalCode().equals(childFinalCode.trim())) {
                    isDuplicate = true;
                }
            }
        }

        return isDuplicate;
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
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.CommonConstants.ADD);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        dtoList.add(dto);
        getModel().setMode(MainetConstants.CommonConstants.ADD);
        bean.setListDto(dtoList);
        beanList.add(bean);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, beanList);
        populateModel(model, bean, null);
        return JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        for (final AccountHeadPrimaryAccountCodeMasterDTO dto : primaryCodeMasterBean.getListDto()) {
            for (final AccountHeadPrimaryAccountCodeMasterDTO childDto : primaryCodeMasterBean.getListDto()) {
                if (dto.getChildFinalCode().equals(childDto.getChildParentCode())) {
                    childDto.setChildParentCode(dto.getChildCode());
                }
            }
        }
        validateUserInput(primaryCodeMasterBean, orgId, bindingResult);

        if (!bindingResult.hasErrors()) {
            final TbAcCodingstructureDetEntity det = new TbAcCodingstructureDetEntity();
            accountHeadPrimaryAccountCodeMasterService.create(primaryCodeMasterBean, det, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, primaryCodeMasterBean.getListDto());
            populateModel(model, primaryCodeMasterBean, bindingResult);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.CommonConstants.ADD);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (primaryCodeMasterBean.getListDto().size() == MainetConstants.EMPTY_LIST) {
                keepDataAfterValidationError(primaryCodeMasterBean, bindingResult);
            }
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "saveEditedData", method = RequestMethod.POST)
    public String saveEditedData(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes) throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        if (!primaryCodeMasterBean.isNewRecord()) {
            primaryCodeMasterBean.setParentCode(getModel().getPrimaryCodeMasterBean().getParentCode());
            if (getModel().getPrimaryCodeMasterBean().getParentDesc() == null) {
                getModel().getPrimaryCodeMasterBean().setParentDesc(MainetConstants.CommonConstant.BLANK);
            }

            if (primaryCodeMasterBean.getEditedDataChildParentCode().equals(MainetConstants.CommonConstant.BLANK)) {
                primaryCodeMasterBean.setParentDesc(primaryCodeMasterBean.getEditedDataChildDesc());
                primaryCodeMasterBean.setParentLevel(getModel().getPrimaryCodeMasterBean().getParentLevel());
                primaryCodeMasterBean.setParentFinalCode(getModel().getPrimaryCodeMasterBean().getParentFinalCode());
            } else {
                primaryCodeMasterBean.setParentDesc(getModel().getPrimaryCodeMasterBean().getParentDesc());
                primaryCodeMasterBean.setParentLevel(getModel().getPrimaryCodeMasterBean().getParentLevel());
                primaryCodeMasterBean.setParentFinalCode(getModel().getPrimaryCodeMasterBean().getParentFinalCode());
            }
        }
        List<AccountHeadPrimaryAccountCodeMasterDTO> newAddedList = null;

        if (primaryCodeMasterBean.isNewRecord()) {
            newAddedList = primaryCodeMasterBean.getListDto();
            getModel().getPrimaryCodeMasterBean().getListDto().addAll(newAddedList);
        } else {
            primaryCodeMasterBean.setListDto(getModel().getPrimaryCodeMasterBean().getListDto());
        }

        if (!primaryCodeMasterBean.isNewRecord()) {
            for (final AccountHeadPrimaryAccountCodeMasterDTO dto : primaryCodeMasterBean.getListDto()) {
                if (dto.getChildFinalCode().equals(primaryCodeMasterBean.getEditedDataChildCompositeCode().trim())) {
                    dto.setChildDesc(primaryCodeMasterBean.getEditedDataChildDesc());
                    if (primaryCodeMasterBean.getEditedChildStatus() != null) {
                        dto.setChildPrimaryStatus(primaryCodeMasterBean.getEditedChildStatus());
                    }
                }
            }
        }
        if (!bindingResult.hasErrors()) {
            accountHeadPrimaryAccountCodeMasterService.saveEditedData(primaryCodeMasterBean, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {

            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, primaryCodeMasterBean.getListDto());
            populateModel(model, primaryCodeMasterBean, bindingResult);
            return JSP_FORM;
        }

    }

    private void validateUserInput(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean, final Long orgId,
            final BindingResult bindingResult) {
        final List<Integer> listOfParentCode = accountHeadPrimaryAccountCodeMasterService.getAllParentLevelCodes(orgId);
        listOfParentCode.size();
        final Boolean isMasterExist = true;
        final Iterator<Integer> it = listOfParentCode.iterator();
        new AccountPrimaryCodeValidator().primaryCodeValidation(primaryCodeMasterBean, bindingResult, isMasterExist, it);
    }

    @RequestMapping(params = "test", method = RequestMethod.POST)
    public void test(final AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean, final Model model) {
        model.addAttribute(MainetConstants.VIEW_MODE, true);
        final Set<AccountHeadPrimaryAccountCodeMasterEntity> setOfEntityWithChild = new HashSet<>();
        setOfEntityWithChild.add(
                accountHeadPrimaryAccountCodeMasterDao.getParentDetailsUsingPrimaryHeadId(getModel().getPrimaryCodeMasterBean()));
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.NODE, setOfEntityWithChild);
    }

    @RequestMapping(params = "editGridData", method = RequestMethod.POST)
    public String update(@Valid AccountHeadPrimaryAccountCodeMasterBean primaryCodeMasterBean, final BindingResult bindingResult,
            final Model model, @RequestParam(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID) final Long functionId,
            @RequestParam(MainetConstants.MODE) final String viewmode, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFunctionMasterController 'update'");
        getModel().setMode(MainetConstants.EDIT);
        if (!bindingResult.hasErrors()) {
            primaryCodeMasterBean.setPrimaryAcHeadId(functionId);
            primaryCodeMasterBean = accountHeadPrimaryAccountCodeMasterService.getDetailsUsingFunctionId(primaryCodeMasterBean);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
            final AccountMasterCommonModel sessionDto = getModel();
            sessionDto.setPrimaryCodeMasterBean(primaryCodeMasterBean);
            sessionDto.getMap().clear();
            getModel().getMapWithDesc().clear();
            HashSet<String> set = new HashSet<>();
            HashSet<String> setWithDesc = new HashSet<>();
            set.add(primaryCodeMasterBean.getParentCode());
            setWithDesc.add(primaryCodeMasterBean.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                    + primaryCodeMasterBean.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
            for (final AccountHeadPrimaryAccountCodeMasterDTO masterDto : primaryCodeMasterBean.getListDto()) {
                sessionDto.getStoreCompositeCodeList().add(masterDto.getChildFinalCode());
                set = new HashSet<>();
                setWithDesc = new HashSet<>();
                if (sessionDto.getMap().containsKey(masterDto.getChildLevel())) {
                    set = sessionDto.getMap().get(masterDto.getChildLevel());
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevel(), set);

                    setWithDesc = sessionDto.getMapWithDesc().get(masterDto.getChildLevel());
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevel(), setWithDesc);
                } else {
                    set.clear();
                    set.add(masterDto.getChildFinalCode());
                    sessionDto.getMap().put(masterDto.getChildLevel(), set);

                    setWithDesc.clear();
                    setWithDesc.add(masterDto.getChildFinalCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                            + masterDto.getChildDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
                    sessionDto.getMapWithDesc().put(masterDto.getChildLevel(), setWithDesc);
                }

            }

            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            Collections.sort(primaryCodeMasterBean.getListDto(), AccountHeadPrimaryAccountCodeMasterDTO.primaryAccountHead);
            if (primaryCodeMasterBean.getListDto().size() == MainetConstants.EMPTY_LIST) {
                keepDataAfterValidationError(primaryCodeMasterBean, bindingResult);
            }

            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, primaryCodeMasterBean.getListDto());
            model.addAttribute(MAIN_ENTITY_NAME, primaryCodeMasterBean);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            populateModel(model, primaryCodeMasterBean, bindingResult);
            if (MainetConstants.VIEW.equals(viewmode)) {
                model.addAttribute(MainetConstants.VIEW_MODE, true);
                final Set<AccountHeadPrimaryAccountCodeMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild
                        .add(accountHeadPrimaryAccountCodeMasterDao.getParentDetailsUsingPrimaryHeadId(primaryCodeMasterBean));
                model.addAttribute(MainetConstants.PRIMARYCODEMASTER.NODE, setOfEntityWithChild);
            } else {

                sessionDto.getPrimaryCodeMasterBean().getListDto().clear();
                final AccountHeadPrimaryAccountCodeMasterBean bean = new AccountHeadPrimaryAccountCodeMasterBean();
                bean.setListDto(primaryCodeMasterBean.getListDto());
                sessionDto.setPrimaryCodeMasterBeanForRemoveRecords(
                        accountHeadPrimaryAccountCodeMasterService.getDetailsUsingFunctionId(primaryCodeMasterBean));
            }
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, viewmode);
            return JSP_FORM;
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, primaryCodeMasterBean, bindingResult);
            return JSP_FORM;
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
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }
}
