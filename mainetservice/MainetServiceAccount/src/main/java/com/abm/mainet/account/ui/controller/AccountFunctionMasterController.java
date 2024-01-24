package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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
import com.abm.mainet.account.ui.model.response.AccountFunctionalMasterResponse;
import com.abm.mainet.account.ui.validator.AccountFunctionMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.dao.AccountFunctionMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFunctionMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
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
@RequestMapping("/AccountFunctionMaster.html")
public class AccountFunctionMasterController extends AbstractController {

    @Resource
    private AccountFunctionMasterService tbAcFunctionMasterService;

    @Resource
    private AccountFunctionMasterDao accountFunctionMasterDao;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private TbOrganisationService tbOrganisationService;

    List<String> storeCompositeCodes = new ArrayList<>();

    public AccountFunctionMasterController() {
        super(AccountFunctionMasterController.class, MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN);
        log("TbAcFunctionMasterController created.");
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountFunctionalMasterResponse gridData(final HttpServletRequest request, final Model model) {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean functionDefaultFlag = false;
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        List<AccountFunctionMasterBean> list = null;
        if (isDafaultOrgExist && functionDefaultFlag) {
            list = tbAcFunctionMasterService
                    .findAllWithOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
            list = tbAcFunctionMasterService.findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = tbAcFunctionMasterService.findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        }

        final List<AccountFunctionMasterBean> listOfFunctionBean = new ArrayList<>();
        if ((list != null) && !list.isEmpty()) {
            for (final AccountFunctionMasterBean bean : list) {

                if (isDafaultOrgExist && functionDefaultFlag) {
                    if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                            .getSuperUserOrganization().getOrgid())) {
                        bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                    }
                } else {
                    bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
                listOfFunctionBean.add(bean);
            }
        }

        final AccountFunctionalMasterResponse waterConnSizeResponse = new AccountFunctionalMasterResponse();
        waterConnSizeResponse.setRows(listOfFunctionBean);
        waterConnSizeResponse.setTotal(list.size());
        waterConnSizeResponse.setRecords(list.size());
        waterConnSizeResponse.setPage(page);

        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);

        return waterConnSizeResponse;
    }

    private void populateModel(final Model model, final AccountFunctionMasterBean accountFunctionMasterBean,
            final BindingResult bindingResult) {
        UserSession.getCurrent().getOrganisation().getOrgid();
        getStoreCompositeCodes().clear();
        final

        boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean functionDefaultFlag = false;

        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUNCTION_CPD_VALUE,
                PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        if (isDafaultOrgExist && functionDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }

        final List<String> listOfLevels = new ArrayList<>();
        List<TbAcCodingstructureDetEntity> detEntityList = null;
        if (configurationMasterEntity != null) {
            detEntityList = configurationMasterEntity.getTbAcCodingstructureDetEntity();
            accountFunctionMasterBean.setCodconfigId(configurationMasterEntity.getCodcofId());

        }
        if ((detEntityList != null) && !detEntityList.isEmpty()) {
            Collections.sort(detEntityList, TbAcCodingstructureDetEntity.codingStructureLevelComparator);
            for (final TbAcCodingstructureDetEntity entity : detEntityList) {

                listOfLevels.add(entity.getCodDescription());
            }

            List<String> compositeCodeList = null;
            if (isDafaultOrgExist && functionDefaultFlag) {
                compositeCodeList = tbAcFunctionMasterService
                        .getAllCompositeCodeByOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
            } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
                compositeCodeList = tbAcFunctionMasterService
                        .getAllCompositeCodeByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            } else {
                compositeCodeList = tbAcFunctionMasterService
                        .getAllCompositeCodeByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            }
            getStoreCompositeCodes().addAll(compositeCodeList);
            accountFunctionMasterBean.setParentLevel(listOfLevels.get(MainetConstants.EMPTY_LIST));
            model.addAttribute(MainetConstants.MAX_PARENT_LVL, detEntityList.get(MainetConstants.EMPTY_LIST).getCodDigits());
            model.addAttribute(MainetConstants.CHILD_CODE_DIGIT, detEntityList.get(MainetConstants.NUMBER_ONE).getCodDigits());
        } else {
            if (bindingResult != null) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null, MainetConstants.FUNCTION_MASTER.CONFIG_MISSING));
            }
            accountFunctionMasterBean.setParentLevel(null);
            model.addAttribute(MainetConstants.MAX_PARENT_LVL, null);
        }

        final List<LookUp> functionStatus = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.FUNCTION_MASTER.FUNCTION_STATUS, functionStatus);
        model.addAttribute(MainetConstants.FUNCTION_MASTER.LVL_SIZE, listOfLevels.size());
        model.addAttribute(MainetConstants.FUNCTION_MASTER.GET_LVL, listOfLevels);
        model.addAttribute(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, accountFunctionMasterBean);
    }

    @RequestMapping()
    public String list(final Model model) {
        log("AccountFunctionMasterController 'list'");
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean functionDefaultFlag = false;
        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist && functionDefaultFlag) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }
        model.addAttribute(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, new AccountFunctionMasterBean());

        List<AccountFunctionMasterBean> list = null;
        if (isDafaultOrgExist && functionDefaultFlag) {
            list = tbAcFunctionMasterService
                    .findAllWithOrgId(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else {
            list = tbAcFunctionMasterService.findAllWithOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        return MainetConstants.FUNCTION_MASTER.JSP_LIST;
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

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean functionDefaultFlag = false;

        if (isDafaultOrgExist) {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            functionDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUNCTION_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUNCTION_CPD_VALUE,
                PrefixConstants.CMD,
                UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final String activeStatusCode = lookUpFieldStatus.getLookUpCode();

        if (isDafaultOrgExist && functionDefaultFlag) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else if (isDafaultOrgExist && (functionDefaultFlag == false)) {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), activeStatusCode);
        } else {
            configurationMasterEntity = tbAcCodingstructureMasService.findConfigurationMasterEntiry(lkp.getLookUpId(),
                    UserSession.getCurrent().getOrganisation().getOrgid(), activeStatusCode);
        }

        final List<Long> detailLists = new ArrayList<>();
        Long codeMaxDigit = 0L;

        for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
            if (selectValue == Integer.parseInt(entity.getCodLevel().toString())) {
                codeMaxDigit = entity.getCodDigits();
                detailLists.add(codeMaxDigit);
                break;
            }
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

            final List<AccountFunctionDto> listDto = getModel().getFunctionMasterBeanForRemoveRecords().getListDto();
            for (final AccountFunctionDto dto : listDto) {
                if (dto.getChildFinalCode().equals(childFinalCode.trim())) {
                    isDuplicate = true;
                }
            }
        }

        return isDuplicate;
    }

    @RequestMapping(params = "showLeveledform", method = RequestMethod.POST)
    public String formForUpdate(final Model model,
            @RequestParam(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FUNCTION_ID) final Long functionId,
            @RequestParam(MainetConstants.MODE) final String viewmode) {
        log("AccountFunctionMasterController 'formForUpdate'");
        getModel().getMap().clear();
        final AccountFunctionMasterBean bean = new AccountFunctionMasterBean();
        final List<AccountFunctionMasterBean> list = new ArrayList<>();
        final List<AccountFunctionDto> dtoList = new ArrayList<>();
        final AccountFunctionDto dto = new AccountFunctionDto();
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.CommonConstants.ADD);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        getModel().setMode(MainetConstants.CommonConstants.ADD);
        dtoList.add(dto);
        bean.setListDto(dtoList);
        list.add(bean);
        model.addAttribute(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, bean);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
        populateModel(model, bean, null);
        return MainetConstants.FUNCTION_MASTER.JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFunctionMasterController 'create'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<Integer> listOfParentCode = tbAcFunctionMasterService.getAllParentLevelCodes(orgId);
        listOfParentCode.size();
        final Iterator<Integer> it = listOfParentCode.iterator();
        for (final AccountFunctionDto dto : tbAcFunctionMaster.getListDto()) {
            for (final AccountFunctionDto childDto : tbAcFunctionMaster.getListDto()) {
                if (dto.getChildFinalCode().equals(childDto.getChildParentCode())) {
                    childDto.setChildParentCode(dto.getChildCode());
                }
            }
        }
        new AccountFunctionMasterValidator().functionMasterValidation(tbAcFunctionMaster, bindingResult, it);

        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        tbAcFunctionMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

        if (!bindingResult.hasErrors()) {

            final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                    PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long parentActiveStatus = lookUpFieldStatus.getLookUpId();
            tbAcFunctionMaster.setFunctionStatusCpdId(parentActiveStatus);

            final TbAcCodingstructureDetEntity det = new TbAcCodingstructureDetEntity();
            tbAcFunctionMasterService.create(tbAcFunctionMaster, det, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            tbAcFunctionMaster.setHasError(MainetConstants.MASTER.Y);
            final List<AccountFunctionDto> list = keepDataAfterValidationError(tbAcFunctionMaster, bindingResult);
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, list);
            populateModel(model, tbAcFunctionMaster, bindingResult);
            return MainetConstants.FUNCTION_MASTER.JSP_FORM;
        }
    }

    @RequestMapping(params = "saveEditedData", method = RequestMethod.POST)
    public String saveEditedData(final AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {

        if (!bindingResult.hasErrors()) {

            UserSession.getCurrent().getOrganisation().getOrgid();
            final Long empId = UserSession.getCurrent().getEmployee().getEmpId();

            if (!tbAcFunctionMaster.isNewRecord()) {
                final Long editedFunctionId = tbAcFunctionMaster.getEdittedFunctionId();
                final AccountFunctionMasterEntity function = tbAcFunctionMasterService.getFunctionById(editedFunctionId);
                function.setFunctionDesc(tbAcFunctionMaster.getEditedDataChildDesc());
                function.setUpdatedBy(empId);
                function.setUpdatedDate(new Date());
                function.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
                if (tbAcFunctionMaster.getEditedChildStatus() != null) {
                    function.setFunctionStatusCpdId(tbAcFunctionMaster.getEditedChildStatus());
                } else {
                    function.setFunctionStatusCpdId(function.getFunctionStatusCpdId());
                }

                tbAcFunctionMasterService.saveUpdateFunction(function);
            }
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            tbAcFunctionMaster.setHasError(MainetConstants.MASTER.Y);
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFunctionMaster.getListDto());
            populateModel(model, tbAcFunctionMaster, bindingResult);
            return MainetConstants.FUNCTION_MASTER.JSP_FORM;
        }
    }

    @RequestMapping(params = "saveAddChildData", method = RequestMethod.POST)
    public String saveAddChildData(final AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        tbAcFunctionMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

        List<AccountFunctionDto> newAddedList = null;

        if (tbAcFunctionMaster.isNewRecord()) {
            newAddedList = tbAcFunctionMaster.getListDto();
            getModel().getFunctionMasterBean().getListDto().addAll(newAddedList);
        } else {
            tbAcFunctionMaster.setListDto(getModel().getFunctionMasterBean().getListDto());
        }

        if (!bindingResult.hasErrors()) {
            AccountFunctionMasterEntity finalEntity = tbAcFunctionMasterService.saveEditedData(tbAcFunctionMaster, orgId, langId,
                    empId);
            tbAcFunctionMasterService.insertEditedFunctionMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEntity);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            return MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            tbAcFunctionMaster.setHasError(MainetConstants.MASTER.Y);
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFunctionMaster.getListDto());
            populateModel(model, tbAcFunctionMaster, bindingResult);
            return MainetConstants.FUNCTION_MASTER.JSP_FORM;
        }
    }

    @RequestMapping(params = "editGridData", method = RequestMethod.POST)
    public String update(@Valid AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult,
            final Model model,
            @RequestParam("functionId") final Long functionId, @RequestParam(MainetConstants.MODE) final String viewmode,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFunctionMasterController 'update'");
        getModel().setMode(MainetConstants.EDIT);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<Integer> listOfParentCode = tbAcFunctionMasterService.getAllParentLevelCodes(orgId);
        listOfParentCode.size();
        final Iterator<Integer> it = listOfParentCode.iterator();

        tbAcFunctionMaster.setFunctionId(functionId);
        tbAcFunctionMaster = tbAcFunctionMasterService.getDetailsUsingFunctionId(tbAcFunctionMaster);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
        final AccountMasterCommonModel sessionDto = getModel();
        sessionDto.setFunctionMasterBean(tbAcFunctionMaster);
        sessionDto.getMap().clear();
        getModel().getMapWithDesc().clear();
        HashSet<String> set = new HashSet<>();
        HashSet<String> setWithDesc = new HashSet<>();
        set.add(tbAcFunctionMaster.getParentCode());
        setWithDesc.add(tbAcFunctionMaster.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                + tbAcFunctionMaster.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
        sessionDto.getMap().put(MainetConstants.ONE, set);
        sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
        for (final AccountFunctionDto masterDto : tbAcFunctionMaster.getListDto()) {
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

        new AccountFunctionMasterValidator().functionMasterValidation(tbAcFunctionMaster, bindingResult, it);
        if (!bindingResult.hasErrors()) {

            if (tbAcFunctionMaster.getListDto().isEmpty()) {
                keepDataAfterValidationError(tbAcFunctionMaster, bindingResult);
            }
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFunctionMaster.getListDto());
            model.addAttribute(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, tbAcFunctionMaster);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            populateModel(model, tbAcFunctionMaster, bindingResult);
            if (MainetConstants.VIEW.equals(viewmode)) {

                model.addAttribute(MainetConstants.VIEW_MODE, true);
                final Set<AccountFunctionMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFunctionMasterDao.getParentDetailsUsingFunctionId(tbAcFunctionMaster));
                model.addAttribute(MainetConstants.FUNCTION_MASTER.SETOF_NODE_FUNCTION, setOfEntityWithChild);
            } else {
                sessionDto.getFunctionMasterBean().getListDto().clear();
                final AccountFunctionMasterBean bean = new AccountFunctionMasterBean();
                bean.setListDto(tbAcFunctionMaster.getListDto());
                sessionDto.setFunctionMasterBeanForRemoveRecords(
                        tbAcFunctionMasterService.getDetailsUsingFunctionId(tbAcFunctionMaster));
            }
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, viewmode);
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcFunctionMaster, bindingResult);
        }
        return MainetConstants.FUNCTION_MASTER.JSP_FORM;
    }

    @RequestMapping(params = "viewGridData", method = RequestMethod.POST)
    public String view(@Valid AccountFunctionMasterBean tbAcFunctionMaster, final BindingResult bindingResult, final Model model,
            @RequestParam("functionId") final Long functionId, @RequestParam(MainetConstants.MODE) final String viewmode,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("AccountFunctionMasterController 'update'");
        getModel().setMode(MainetConstants.EDIT);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<Integer> listOfParentCode = tbAcFunctionMasterService.getAllParentLevelCodes(orgId);
        listOfParentCode.size();
        final Iterator<Integer> it = listOfParentCode.iterator();

        tbAcFunctionMaster.setFunctionId(functionId);
        tbAcFunctionMaster = tbAcFunctionMasterService.getDetailsUsingFunctionId(tbAcFunctionMaster);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
        model.addAttribute(MainetConstants.FUNCTION_MASTER.VIEW_MODE_LEVEL, MainetConstants.MASTER.Y);
        final AccountMasterCommonModel sessionDto = getModel();
        sessionDto.setFunctionMasterBean(tbAcFunctionMaster);
        sessionDto.getMap().clear();
        getModel().getMapWithDesc().clear();
        HashSet<String> set = new HashSet<>();
        HashSet<String> setWithDesc = new HashSet<>();
        set.add(tbAcFunctionMaster.getParentCode());
        setWithDesc.add(tbAcFunctionMaster.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                + tbAcFunctionMaster.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
        sessionDto.getMap().put(MainetConstants.ONE, set);
        sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
        for (final AccountFunctionDto masterDto : tbAcFunctionMaster.getListDto()) {
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

        new AccountFunctionMasterValidator().functionMasterValidation(tbAcFunctionMaster, bindingResult, it);
        if (!bindingResult.hasErrors()) {

            if (tbAcFunctionMaster.getListDto().isEmpty()) {
                keepDataAfterValidationError(tbAcFunctionMaster, bindingResult);
            }
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFunctionMaster.getListDto());
            model.addAttribute(MainetConstants.FUNCTION_MASTER.FUN_MASTER_BEAN, tbAcFunctionMaster);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            populateModel(model, tbAcFunctionMaster, bindingResult);
            if (MainetConstants.VIEW.equals(viewmode)) {

                model.addAttribute(MainetConstants.VIEW_MODE, true);
                final Set<AccountFunctionMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFunctionMasterDao.getParentDetailsUsingFunctionId(tbAcFunctionMaster));
                model.addAttribute(MainetConstants.FUNCTION_MASTER.SETOF_NODE_FUNCTION, setOfEntityWithChild);
            } else {
                sessionDto.getFunctionMasterBean().getListDto().clear();
                final AccountFunctionMasterBean bean = new AccountFunctionMasterBean();
                bean.setListDto(tbAcFunctionMaster.getListDto());
                sessionDto.setFunctionMasterBeanForRemoveRecords(
                        tbAcFunctionMasterService.getDetailsUsingFunctionId(tbAcFunctionMaster));
            }
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, viewmode);
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, tbAcFunctionMaster, bindingResult);
        }
        return MainetConstants.FUNCTION_MASTER.JSP_FORM;
    }

    private List<AccountFunctionDto> keepDataAfterValidationError(final AccountFunctionMasterBean acFunctionMaster,
            final BindingResult bindingResult) {
        ArrayList<AccountFunctionDto> list = null;
        final ArrayList<AccountFunctionMasterBean> beanList = new ArrayList<>();
        if (acFunctionMaster.getListDto().size() == 0) {
            list = new ArrayList<>();
            list.add(new AccountFunctionDto());
            acFunctionMaster.setListDto(list);
        }
        beanList.add(acFunctionMaster);

        return acFunctionMaster.getListDto();

    }

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ModelAndView myError(final Exception exception) {
        log("----Caught" + exception.getMessage() + "Exception ----");
        final ModelAndView mav = new ModelAndView(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.EXCEPTION_VIEW);
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

    @RequestMapping(params = "validateDuplicateCode", method = RequestMethod.POST)
    public @ResponseBody boolean validateDuplicateCode(@RequestParam("compositeCode") final String compositeCode) {
        log("Action 'validate composite code'");
        boolean isDuplicate = false;
        if (getStoreCompositeCodes().contains(compositeCode)) {
            isDuplicate = true;
        }
        return isDuplicate;

    }

    public List<String> getStoreCompositeCodes() {
        return storeCompositeCodes;
    }

    public void setStoreCompositeCodes(final List<String> storeCompositeCodes) {
        this.storeCompositeCodes = storeCompositeCodes;
    }

}
