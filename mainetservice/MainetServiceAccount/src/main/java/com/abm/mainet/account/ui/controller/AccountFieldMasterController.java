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
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.ui.model.AccountMasterCommonModel;
import com.abm.mainet.account.ui.model.response.AccountFieldMasterResponse;
import com.abm.mainet.account.ui.validator.FieldMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.acccount.dao.AccountFieldMasterDao;
import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureDetEntity;
import com.abm.mainet.common.integration.acccount.domain.TbAcCodingstructureMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldDto;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
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
@RequestMapping("/AccountFieldMaster.html")
public class AccountFieldMasterController extends AbstractController {

    private final String JSP_FORM = "AccountFieldMaster/form";

    private final String JSP_LIST = "AccountFieldMaster/list";

    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;

    @Resource
    private AccountFieldMasterDao accountFieldMasterDao;

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private TbOrganisationService tbOrganisationService;

    public AccountFieldMasterController() {
        super(AccountFieldMasterController.class, MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME);
        log("TbAcFieldMasterController created.");
    }

    private void populateModel(final Model model, final AccountFieldMasterBean tbAcFieldMaster) {
        log("Action 'Populate Model' ");
        final

        boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;

        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FIELD_CPD_VALUE,
                PrefixConstants.CMD,
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

        final List<LookUp> fieldStatus = CommonMasterUtility.getLookUps(PrefixConstants.ACN,
                UserSession.getCurrent().getOrganisation());
        final List<Long> levels = new LinkedList<>();
        final Map<Long, String> lavelMap = new TreeMap<>();
        final Map<Long, String> lavelParentMap = new TreeMap<>();
        final Map<Long, Long> codingDigitMap = new TreeMap<>();
        if ((configurationMasterEntity.getTbAcCodingstructureDetEntity() != null)
                && !configurationMasterEntity.getTbAcCodingstructureDetEntity().isEmpty()) {
            for (final TbAcCodingstructureDetEntity entity : configurationMasterEntity.getTbAcCodingstructureDetEntity()) {
                if (entity.getCodLevel().equals(1L)) {
                    tbAcFieldMaster.setParentLevel(entity.getCodDescription());
                    tbAcFieldMaster.setParentLevelCode(entity.getCodLevel());
                    codingDigitMap.put(entity.getCodLevel(), entity.getCodDigits());
                } else {
                    entity.getCodLevel();
                    entity.getCodcofdetId();
                    lavelMap.put(entity.getCodLevel(), entity.getCodDescription());
                    levels.add(entity.getCodLevel());
                    codingDigitMap.put(entity.getCodLevel(), entity.getCodDigits());
                }
                lavelParentMap.put(entity.getCodLevel(), entity.getCodDescription());
            }
            model.addAttribute(MainetConstants.MAX_PARENT_LVL,
                    configurationMasterEntity.getTbAcCodingstructureDetEntity().get(MainetConstants.EMPTY_LIST).getCodDigits());
        }
        tbAcFieldMaster.setCodconfigId(configurationMasterEntity.getCodcofId());
        model.addAttribute(MainetConstants.FIELD_MASTER.CODING_DIGIT_MAP, codingDigitMap);
        model.addAttribute(MainetConstants.FIELD_MASTER.LEVELS_MAP, lavelMap);
        model.addAttribute(MainetConstants.FIELD_MASTER.LEVELS_PARENT_MAP, lavelParentMap);
        model.addAttribute(MainetConstants.FIELD_MASTER.LEVELS, levels);
        model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME, tbAcFieldMaster);
        model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, fieldStatus);
    }

    @RequestMapping()
    public String populateGridList(final Model model) {
        log("Action 'list'");

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        if (isDafaultOrgExist && fieldDefaultFlag) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.N);
            }
        } else if (isDafaultOrgExist && (fieldDefaultFlag == false)) {
            if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                    .getSuperUserOrganization().getOrgid())) {
                model.addAttribute(MainetConstants.FIELD_MASTER.PARENT_FLAG_STATUS, MainetConstants.MASTER.Y);
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }
        } else {
            model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
        }

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

        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        TbAcCodingstructureMasEntity configurationMasterEntity = null;
        final LookUp lkp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FIELD_CPD_VALUE,
                PrefixConstants.CMD,
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
        new ArrayList<String>();
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

            final List<AccountFieldDto> listDto = getModel().getFieldMasterBeanForRemoveRecords().getListDto();
            for (final AccountFieldDto dto : listDto) {
                if (dto.getChildFinalCode().equals(childFinalCode.trim())) {
                    isDuplicate = true;
                }
            }
        }

        return isDuplicate;
    }

    @RequestMapping(params = "showLeveledform", method = RequestMethod.POST)
    public String formForUpdate(final Model model,
            @RequestParam(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID) final Long fieldId,
            @RequestParam(MainetConstants.FIELD_MASTER.MODE) final String viewmode) {

        log("Action 'Show JSP form'");
        getModel().getMap().clear();
        final AccountFieldMasterBean tbAcFieldMaster = new AccountFieldMasterBean();
        populateModel(model, tbAcFieldMaster);
        final List<AccountFieldMasterBean> list = new ArrayList<>();
        final List<AccountFieldDto> dtoList = new ArrayList<>();
        final AccountFieldDto dto = new AccountFieldDto();
        dto.setChildFieldStatus(getActiveStatusId());
        dtoList.add(dto);
        tbAcFieldMaster.setListDto(dtoList);
        list.add(tbAcFieldMaster);
        model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME, tbAcFieldMaster);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, MainetConstants.PRIMARYCODEMASTER.ADD);
        model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
        getModel().setMode(MainetConstants.PRIMARYCODEMASTER.ADD);
        model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
        return JSP_FORM;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid final AccountFieldMasterBean accountFieldMasterBean, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("Action 'create'");
        final FieldMasterValidator validator = new FieldMasterValidator();
        validator.validate(accountFieldMasterBean, bindingResult);
        // check if parent code is not duplicate
        String parentCode = null;
        validateFieldMasterInput(accountFieldMasterBean, bindingResult);
        for (final AccountFieldDto dto : accountFieldMasterBean.getListDto()) {
            for (final AccountFieldDto childDto : accountFieldMasterBean.getListDto()) {
                if (dto.getChildFinalCode().equals(childDto.getChildParentCode())) {
                    childDto.setChildParentCode(dto.getChildCode());
                }
            }
        }
        if (!bindingResult.hasErrors()) {

            final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                    PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final Long parentActiveStatus = lookUpFieldStatus.getLookUpId();
            accountFieldMasterBean.setFieldStatusCpdId(parentActiveStatus);

            // For managing the composite code
            accountFieldMasterBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);

            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final int langId = UserSession.getCurrent().getLanguageId();
            final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
            accountFieldMasterBean.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            final TbAcCodingstructureDetEntity det = new TbAcCodingstructureDetEntity();
            tbAcFieldMasterService.create(accountFieldMasterBean, det, orgId, langId, empId);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            return JSP_FORM;
        } else {

            accountFieldMasterBean
                    .setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final List<AccountFieldDto> list = keepDataAfterValidationError(accountFieldMasterBean,
                    bindingResult);
            parentCode = accountFieldMasterBean.getParentCode();
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                    parentCode);
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
                    bindingResult);
            populateModel(model, accountFieldMasterBean);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "saveEditedData", method = RequestMethod.POST)
    public String saveEditedData(final AccountFieldMasterBean tbAcFieldMaster, final BindingResult bindingResult,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();
        tbAcFieldMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));

        if (!tbAcFieldMaster.isNewRecord()) {
            tbAcFieldMaster.setParentCode(getModel().getFieldMasterBean().getParentCode());
            if (getModel().getFieldMasterBean().getParentDesc() == null) {
                getModel().getFieldMasterBean().setParentDesc(MainetConstants.CommonConstant.BLANK);
            }

            if (tbAcFieldMaster.getEditedDataChildParentCode().equals(MainetConstants.CommonConstant.BLANK)) {
                tbAcFieldMaster.setParentDesc(tbAcFieldMaster.getEditedDataChildDesc());
                tbAcFieldMaster.setParentLevel(getModel().getFieldMasterBean().getParentLevel());
                tbAcFieldMaster.setParentFinalCode(getModel().getFieldMasterBean().getParentFinalCode());
            } else {
                tbAcFieldMaster.setParentDesc(getModel().getFieldMasterBean().getParentDesc());
                tbAcFieldMaster.setParentLevel(getModel().getFieldMasterBean().getParentLevel());
                tbAcFieldMaster.setParentFinalCode(getModel().getFieldMasterBean().getParentFinalCode());
            }
        }
        List<AccountFieldDto> newAddedList = null;

        if (tbAcFieldMaster.isNewRecord()) {
            newAddedList = tbAcFieldMaster.getListDto();
            getModel().getFieldMasterBean().getListDto().addAll(newAddedList);
        } else {
            tbAcFieldMaster.setListDto(getModel().getFieldMasterBean().getListDto());
        }

        if (!tbAcFieldMaster.isNewRecord()) {
            for (final AccountFieldDto dto : tbAcFieldMaster.getListDto()) {
                if (dto.getChildFinalCode().equals(tbAcFieldMaster.getEditedDataChildCompositeCode().trim())) {
                    dto.setChildDesc(tbAcFieldMaster.getEditedDataChildDesc());
                    dto.setChildFieldStatus(tbAcFieldMaster.getEditedChildStatus());
                }
            }
        }
        if (!bindingResult.hasErrors()) {
            AccountFieldMasterEntity finalEditedEntity = tbAcFieldMasterService.saveEditedData(tbAcFieldMaster, orgId, langId,
                    empId);
            tbAcFieldMasterService.insertEditedFieldMasterDataIntoPropertyTaxTableByUsingSoapWS(finalEditedEntity);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.COMMON_STATUS.SAVE_OK));
            model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                    ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            return JSP_FORM;
        } else {

            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, tbAcFieldMaster.getListDto());
            populateModel(model, tbAcFieldMaster);
            return JSP_FORM;
        }

    }

    private void validateFieldMasterInput(final AccountFieldMasterBean accountFieldMasterBean,
            final BindingResult bindingResult) {
        if (accountFieldMasterBean.getFieldId() == null) {
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final String fieldCode = accountFieldMasterBean.getParentCode();
            if (fieldCode != null) {
                if (tbAcFieldMasterService.isParentExists(fieldCode, orgId)) {
                    bindingResult
                            .addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                                    MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.PARENTCODE, null, false,
                                    new String[] { MainetConstants.ERRORS }, null,
                                    ApplicationSession.getInstance().getMessage("field.master.parentCodeExist")));
                }
            }
        }

        if (MainetConstants.BLANK.equals(accountFieldMasterBean.getParentCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage("account.master.sel.primAcCode")));
        }
        if (MainetConstants.BLANK.equals(accountFieldMasterBean.getParentDesc())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage("account.master.sel.primAcDesc")));
        }
        if (MainetConstants.BLANK.equals(accountFieldMasterBean.getParentFinalCode())) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage("account.master.sel.primAcFinalCode")));
        }

        for (final AccountFieldDto dto : accountFieldMasterBean.getListDto()) {

            if (accountFieldMasterBean.getListDto().size() == 1) {
                if (MainetConstants.ZERO.equals(dto.getChildLevelCode())
                        && MainetConstants.ZERO.equals(dto.getChildParentLevelCode())
                        && MainetConstants.BLANK.equals(dto.getChildCode())
                        && MainetConstants.BLANK.equals(dto.getChildParentCode())
                        && MainetConstants.BLANK.equals(dto.getChildDesc())) {
                    break;
                }
            }

            if (MainetConstants.ZERO.equals(dto.getChildLevelCode()) || MainetConstants.ZERO.equals(dto.getChildParentLevelCode())
                    || MainetConstants.BLANK.equals(dto.getChildCode()) || MainetConstants.BLANK.equals(dto.getChildParentCode())
                    || dto.getChildDesc().equals(MainetConstants.BLANK)) {
                bindingResult
                        .addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                                MainetConstants.FUNCTION_MASTER.PARENT_LVL, null, false, new String[] { MainetConstants.ERRORS },
                                null, ApplicationSession.getInstance().getMessage("account.master.sel.reqField")));
            }

        }

    }

    private List<AccountFieldDto> keepDataAfterValidationError(
            final AccountFieldMasterBean tbAcFieldMaster, final BindingResult bindingResult) {
        log("Validation error'");
        ArrayList<AccountFieldDto> list = null;
        final ArrayList<AccountFieldMasterBean> beanList = new ArrayList<>();
        if ((tbAcFieldMaster.getListDto() == null) || (tbAcFieldMaster.getListDto().size() == 0)) {
            list = new ArrayList<>();
            list.add(new AccountFieldDto());
            tbAcFieldMaster.setListDto(list);
        }
        beanList.add(tbAcFieldMaster);
        return tbAcFieldMaster.getListDto();
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(@Valid AccountFieldMasterBean accountFieldMasterBean, final BindingResult bindingResult,
            final Model model,
            @RequestParam(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID) final Long fieldId,
            @RequestParam(MainetConstants.FIELD_MASTER.MODE) final String viewmode) throws Exception {
        log("Action 'update'");
        getModel().setMode(MainetConstants.EDIT);
        if (!bindingResult.hasErrors()) {

            final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
            boolean fieldDefaultFlag = false;
            if (isDafaultOrgExist) {
                fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            if (isDafaultOrgExist && fieldDefaultFlag) {
                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
                }
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }

            accountFieldMasterBean.setFieldId(fieldId);
            accountFieldMasterBean = tbAcFieldMasterService.getDetailsUsingFieldId(accountFieldMasterBean);
            final AccountMasterCommonModel sessionDto = getModel();
            sessionDto.setFieldMasterBean(accountFieldMasterBean);
            sessionDto.getMap().clear();
            getModel().getMapWithDesc().clear();
            HashSet<String> set = new HashSet<>();
            HashSet<String> setWithDesc = new HashSet<>();
            set.add(accountFieldMasterBean.getParentCode());
            setWithDesc.add(accountFieldMasterBean.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                    + accountFieldMasterBean.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
            for (final AccountFieldDto masterDto : accountFieldMasterBean.getListDto()) {
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
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            log("Action 'update' : update done - redirect");
            populateModel(model, accountFieldMasterBean);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (MainetConstants.VIEW.equals(viewmode)) {
                model.addAttribute(MainetConstants.VIEW_MODE, true);
                final Set<AccountFieldMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFieldMasterDao.getParentDetailsUsingFieldId(accountFieldMasterBean));
                model.addAttribute(MainetConstants.FIELD_MASTER.NODE, setOfEntityWithChild);
            } else {

                sessionDto.getFieldMasterBean().getListDto().clear();
                final AccountFieldMasterBean bean = new AccountFieldMasterBean();
                bean.setListDto(accountFieldMasterBean.getListDto());
                sessionDto.setFieldMasterBeanForRemoveRecords(
                        tbAcFieldMasterService.getDetailsUsingFieldId(accountFieldMasterBean));
            }
            if ((accountFieldMasterBean.getListDto() == null) || accountFieldMasterBean.getListDto().isEmpty()) {
                keepDataAfterValidationError(accountFieldMasterBean, bindingResult);
            }
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, accountFieldMasterBean.getListDto());
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME, accountFieldMasterBean);
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, viewmode);
            return JSP_FORM;
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, accountFieldMasterBean);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "view", method = RequestMethod.POST)
    public String view(@Valid AccountFieldMasterBean accountFieldMasterBean, final BindingResult bindingResult, final Model model,
            @RequestParam(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.FIELD_ID) final Long fieldId,
            @RequestParam(MainetConstants.FIELD_MASTER.MODE) final String viewmode) throws Exception {
        log("Action 'update'");
        getModel().setMode(MainetConstants.CommonConstants.EDIT);
        if (!bindingResult.hasErrors()) {

            final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
            boolean fieldDefaultFlag = false;
            if (isDafaultOrgExist) {
                fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                        ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
            } else {
                fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                        UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
            }
            if (isDafaultOrgExist && fieldDefaultFlag) {
                if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                        .getSuperUserOrganization().getOrgid())) {
                    model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
                }
            } else {
                model.addAttribute(MainetConstants.FIELD_MASTER.DEFAULT_FLAG_STATUS, MainetConstants.MASTER.Y);
            }

            accountFieldMasterBean.setFieldId(fieldId);
            accountFieldMasterBean = tbAcFieldMasterService.getDetailsUsingFieldId(accountFieldMasterBean);
            final AccountMasterCommonModel sessionDto = getModel();
            sessionDto.setFieldMasterBean(accountFieldMasterBean);
            sessionDto.getMap().clear();
            getModel().getMapWithDesc().clear();
            HashSet<String> set = new HashSet<>();
            HashSet<String> setWithDesc = new HashSet<>();
            set.add(accountFieldMasterBean.getParentCode());
            setWithDesc.add(accountFieldMasterBean.getParentCode() + MainetConstants.FUND_MASTER.OPEN_BRACKET
                    + accountFieldMasterBean.getParentDesc() + MainetConstants.FUND_MASTER.CLOSE_BRACKET);
            sessionDto.getMap().put(MainetConstants.ONE, set);
            sessionDto.getMapWithDesc().put(MainetConstants.ONE, setWithDesc);
            for (final AccountFieldDto masterDto : accountFieldMasterBean.getListDto()) {
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
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.VIEW_MODE, viewmode);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, true);
            model.addAttribute(MainetConstants.FUNCTION_MASTER.VIEW_MODE_LEVEL, MainetConstants.MASTER.Y);
            log("Action 'update' : update done - redirect");
            populateModel(model, accountFieldMasterBean);
            model.addAttribute(MainetConstants.PRIMARYCODEMASTER.COUNT, MainetConstants.ZERO);
            if (MainetConstants.VIEW.equals(viewmode)) {
                model.addAttribute(MainetConstants.VIEW_MODE, true);
                final Set<AccountFieldMasterEntity> setOfEntityWithChild = new HashSet<>();
                setOfEntityWithChild.add(accountFieldMasterDao.getParentDetailsUsingFieldId(accountFieldMasterBean));
                model.addAttribute(MainetConstants.FIELD_MASTER.NODE, setOfEntityWithChild);
            } else {

                sessionDto.getFieldMasterBean().getListDto().clear();
                final AccountFieldMasterBean bean = new AccountFieldMasterBean();
                bean.setListDto(accountFieldMasterBean.getListDto());
                sessionDto.setFieldMasterBeanForRemoveRecords(
                        tbAcFieldMasterService.getDetailsUsingFieldId(accountFieldMasterBean));
            }
            if ((accountFieldMasterBean.getListDto() == null) || accountFieldMasterBean.getListDto().isEmpty()) {
                keepDataAfterValidationError(accountFieldMasterBean, bindingResult);
            }
            model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, accountFieldMasterBean.getListDto());
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME, accountFieldMasterBean);
            model.addAttribute(MainetConstants.FIELD_MASTER.MODE_DATA, viewmode);
            return JSP_FORM;
        } else {
            log("Action 'update' : binding errors");
            populateModel(model, accountFieldMasterBean);
            return JSP_FORM;
        }
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountFieldMasterResponse gridData(final HttpServletRequest request, final Model model) {
        log("Action 'Get grid Data'");
        AccountFieldMasterResponse fieldResponse = null;
        List<AccountFieldMasterBean> list = null;
        final int page = Integer.parseInt(request
                .getParameter(MainetConstants.CommonConstants.PAGE));
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
        boolean fieldDefaultFlag = false;
        if (isDafaultOrgExist) {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
        } else {
            fieldDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FIELD_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
        }
        UserSession.getCurrent().getEmployee().getEmpId();

        if (isDafaultOrgExist && fieldDefaultFlag) {
            list = tbAcFieldMasterService.findAll(ApplicationSession.getInstance().getSuperUserOrganization().getOrgid());
        } else if (isDafaultOrgExist && (fieldDefaultFlag == false)) {
            list = tbAcFieldMasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        } else {
            list = tbAcFieldMasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
        }
        final List<AccountFieldMasterBean> listOfFieldBean = new ArrayList<>();
        if ((list != null) && !list.isEmpty()) {
            for (final AccountFieldMasterBean bean : list) {

                if (isDafaultOrgExist && fieldDefaultFlag) {
                    if ((UserSession.getCurrent().getOrganisation().getOrgid()) == (ApplicationSession.getInstance()
                            .getSuperUserOrganization().getOrgid())) {
                        bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                    }
                } else {
                    bean.setDefaultOrgFlag(MainetConstants.MASTER.Y);
                }
                listOfFieldBean.add(bean);
            }
        }
        fieldResponse = new AccountFieldMasterResponse();
        fieldResponse.setRows(listOfFieldBean);
        fieldResponse.setTotal(list.size());
        fieldResponse.setRecords(list.size());
        fieldResponse.setPage(page);
        model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
        return fieldResponse;
    }

    public Long getActiveStatusId() {
        final LookUp lookUpFieldStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
                PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long activeStatusId = lookUpFieldStatus.getLookUpId();
        return activeStatusId;
    }

    @RequestMapping(params = "checkChildFieldCompositeCodeExists", method = RequestMethod.POST)
    public @ResponseBody boolean checkChildFieldCompositeCodeExists(@RequestParam("compositeCode") final String compositeCode,
            final BindingResult bindingResult, final HttpServletRequest request, final Model model) {

        boolean isValidationError = false;
        final Long defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

        if (tbAcFieldMasterService.isChildFieldCompositeCodeExists(compositeCode, defaultOrgId)) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] {
                            MainetConstants.ERRORS },
                    null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "checkFieldParentCodeExist", method = RequestMethod.POST)
    public @ResponseBody String findBudgetOpenBalDuplicateCombination(final AccountFieldMasterBean accountFieldMasterBean,
            final HttpServletRequest request,
            final Model model,
            final BindingResult bindingResult) {
        final String childParentCode = accountFieldMasterBean.getParentCode();

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if ((childParentCode != null) && !childParentCode.isEmpty()) {
            if (tbAcFieldMasterService.isParentExists(childParentCode, orgId)) {
                bindingResult
                        .addError(new org.springframework.validation.FieldError(MainetConstants.FIELD_MASTER.MAIN_ENTITY_NAME,
                                MainetConstants.CommonConstant.BLANK, null, false, new String[] {
                                        MainetConstants.ERRORS },
                                null, ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            }
        }
        return MainetConstants.AuthStatus.ONHOLD;
    }

}
