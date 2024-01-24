package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.abm.mainet.account.dto.AccountDepositeAndAdvnHeadsMappingEntryMasterBean;
import com.abm.mainet.account.dto.DeasMasterEntryDto;
import com.abm.mainet.account.service.AccountAdvnAndDepositeHeadMapEntryMasterService;
import com.abm.mainet.account.ui.model.response.AccountAdvanceAndDepositeMapEntryMasterResponse;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFunctionMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountFundMasterService;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/AccountDepositeAndAdvnHeadsMappingEntryMaster.html")
public class AccountDepositeAndAdvnHeadsMappingEntryMasterController extends AbstractController {

    private static final String MAIN_LIST_NAME = "list";

    private static final String JSP_FORM = "accountDepositeAndAdvnHeadsMappingEntryMaster/form";

    private static final String JSP_LIST = "accountDepositeAndAdvnHeadsMappingEntryMaster/list";

    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @Resource
    private AccountFunctionMasterService accountFunctionMasterService;
    @Resource
    private AccountFundMasterService accountFundMasterService;
    @Resource
    private AccountFieldMasterService accountFieldMasterService;
    @Resource
    private AccountHeadPrimaryAccountCodeMasterService accountHeadPrimaryAccountCodeMasterService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;

    @Resource
    private TbDepartmentService tbDepartmentService;

    @Resource
    private AccountAdvnAndDepositeHeadMapEntryMasterService accountAdvnAndDepositeHeadMapEntryMasterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDepositeAndAdvnHeadsMappingEntryMasterController.class);

    private List<DeasMasterEntryDto> deaseEntryList = new ArrayList<>();

    public AccountDepositeAndAdvnHeadsMappingEntryMasterController() {
        super(AccountDepositeAndAdvnHeadsMappingEntryMasterController.class, MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN);
        log("AccountDepositeAndAdvnHeadsMappingEntryMasterController created.");
    }

    @RequestMapping()
    public String list(final Model model) throws Exception {
        log("Account Deposite And Advnc Controller 'list'");
        deaseEntryList.clear();
        final AccountDepositeAndAdvnHeadsMappingEntryMasterBean bean = new AccountDepositeAndAdvnHeadsMappingEntryMasterBean();
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN, bean);
        populateModel(model, bean);
        populateGridViewData(model);

        return JSP_LIST;
    }

    public void populateGridViewData(final Model model) throws Exception {
        final Organisation orginsation = UserSession.getCurrent().getOrganisation();
        List<LookUp> lookupList = null;
        List<LookUp> lookupListForDepositeType = null;
        List<LookUp> lookupListForAdvancedType = null;

        lookupList = CommonMasterUtility.getLookUps(PrefixConstants.HDM, orginsation);
        lookupListForDepositeType = CommonMasterUtility.getLookUps(AccountPrefix.DTY.toString(), orginsation);
        lookupListForAdvancedType = CommonMasterUtility.getLookUps(AccountPrefix.ATY.toString(), orginsation);

        model.addAttribute(MainetConstants.FUNCTION_MASTER.GET_LVL, lookupList);
        model.addAttribute(MainetConstants.AccountDepositeAndAdvnHeadsMapping.DEPOSIT_TYPE_LEVEL, lookupListForDepositeType);
        model.addAttribute(MainetConstants.AccountDepositeAndAdvnHeadsMapping.ADVANCE_TYPE_LEVEL, lookupListForAdvancedType);
    }

    @RequestMapping(params = "showLeveledform", method = RequestMethod.POST)
    public String formForUpdate(final AccountDepositeAndAdvnHeadsMappingEntryMasterBean bean, final BindingResult bindingResult,
            final Model model,
            @RequestParam(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_ID) final Long mappingId,
            @RequestParam(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPOS_ID) final Long depositeId,
            @RequestParam(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.ADVN_ID) final Long advncId,
            @RequestParam(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_ID) final Long deptId) throws Exception {

        bean.setMappingType(mappingId);
        bean.setDepositeType(depositeId);
        bean.setAdvancedType(advncId);
        bean.setDeptId(deptId);

        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN, bean);

        populateModel(model, bean);

        return JSP_FORM;
    }

    private void populateModel(final Model model,
            final AccountDepositeAndAdvnHeadsMappingEntryMasterBean AccountHeadPrimaryAccountCodeMasterBean) throws Exception {
        final Organisation orginsation = UserSession.getCurrent().getOrganisation();
        final Long defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        final Integer langId = UserSession.getCurrent().getLanguageId();
        final Long orgId = orginsation.getOrgid();
        final List<TbDepartment> departmentlist = tbDepartmentService.findByOrgId(orgId, Long.valueOf(langId.toString()));

        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FUNC_MAS_LAST_LVL,
                accountFunctionMasterService.getFunctionMasterLastLevels(defaultOrgId));
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FUND_MAS_LAST_LVL,
                accountFundMasterService.getFundMasterLastLevels(defaultOrgId));
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.FIELD_MAS_LAST_LVL,
                accountFieldMasterService.getFieldMasterLastLevels(orgId));
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.PRIMARY_MAS_LAST_LVL,
                accountHeadPrimaryAccountCodeMasterService.getPrimaryHeadCodeLastLevels(defaultOrgId));
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.SEC_MAS_LAST_LVL,
                secondaryheadMasterService.getSecondaryHeadCodeLastLevels(orgId));
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_LIST, departmentlist);
        model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN, AccountHeadPrimaryAccountCodeMasterBean);
    }

    @RequestMapping(params = "saveForm", method = RequestMethod.POST)
    public String create(final AccountDepositeAndAdvnHeadsMappingEntryMasterBean depositeAndAdvnMapBean,
            final BindingResult bindingResult,
            final Model model, final HttpServletRequest httpServletRequest) {

        log("Save Method of Advn and Deposite Head Mapping Entry'");
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long mappingType = depositeAndAdvnMapBean.getMappingType();
        final Long depId = depositeAndAdvnMapBean.getDeptId();
        Long advnceOrDepType = null;
        final Integer langId = UserSession.getCurrent().getLanguageId();
        final Long empId = UserSession.getCurrent().getEmployee().getEmpId();

        if ((depositeAndAdvnMapBean.getDepositeType() != null) && !depositeAndAdvnMapBean.getDepositeType().equals(0L)) {
            advnceOrDepType = depositeAndAdvnMapBean.getDepositeType();
        } else {
            advnceOrDepType = depositeAndAdvnMapBean.getAdvancedType();
        }
        String returnString = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            final List<DeasMasterEntryDto> list = accountAdvnAndDepositeHeadMapEntryMasterService.searchRecordUsingRequestParam(
                    orgId,
                    mappingType, advnceOrDepType, depId);
            final List<DeasMasterEntryDto> deasMasterEntryDtoList = depositeAndAdvnMapBean.getListOfDto();

            if ((deasMasterEntryDtoList != null) && !deasMasterEntryDtoList.isEmpty()) {
                for (final DeasMasterEntryDto dto : deasMasterEntryDtoList) {
                    if ((list != null) && !list.isEmpty()) {
                        for (final DeasMasterEntryDto innerDto : list) {
                            if ((dto.getEntityId() != null) && (dto.getEntityId().equals(innerDto.getEntityId()))) {
                                dto.setFundId(innerDto.getFundId());
                                dto.setFieldId(innerDto.getFieldId());
                                dto.setFunctionId(innerDto.getFunctionId());
                                dto.setPrimaryCodeId(innerDto.getPrimaryCodeId());
                                dto.setSecondaryCodeId(innerDto.getSecondaryCodeId());
                            }
                        }
                    }
                }
            }
            accountAdvnAndDepositeHeadMapEntryMasterService.save(depositeAndAdvnMapBean, orgId, langId, empId, mappingType,
                    Utility.getClientIpAddress(httpServletRequest));
            returnString = MainetConstants.CommonConstants.SUCCESS_PAGE;
        } else {
            returnString = JSP_FORM;
        }
        return returnString;
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody AccountAdvanceAndDepositeMapEntryMasterResponse gridData(final HttpServletRequest request,
            final Model model)
            throws Exception {
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        UserSession.getCurrent().getOrganisation().getOrgid();

        final List<DeasMasterEntryDto> list = deaseEntryList;
        final AccountAdvanceAndDepositeMapEntryMasterResponse response = new AccountAdvanceAndDepositeMapEntryMasterResponse();
        response.setRows(list);
        response.setTotal(list.size());
        response.setRecords(list.size());
        response.setPage(page);

        model.addAttribute(MAIN_LIST_NAME, list);

        return response;
    }

    @RequestMapping(params = "editData", method = RequestMethod.POST)
    public String editData(
            final AccountDepositeAndAdvnHeadsMappingEntryMasterBean accountDepositeAndAdvnHeadsMappingEntryMasterBean,
            final BindingResult bindingResult, final Model model,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_ID) final Long mappingType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPOS_ID) final Long depositeType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.ADVN_ID) final Long advnType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_ID) final Long deptId) throws Exception {

        final Long advnceOrDepType = (depositeType != 0L) ? depositeType : advnType;
        List<DeasMasterEntryDto> deaseEntryList = null;

        deaseEntryList = accountAdvnAndDepositeHeadMapEntryMasterService.searchRecordUsingRequestParam(
                UserSession.getCurrent().getOrganisation().getOrgid(), mappingType, advnceOrDepType, deptId);

        if (deaseEntryList.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN,
                    MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_TYPE, null, false, new String[] { MainetConstants.ERRORS },
                    null, "No Old Record Found Please Add New"));
        }
        if (!bindingResult.hasErrors()) {
            accountDepositeAndAdvnHeadsMappingEntryMasterBean.setMappingType(mappingType);
            accountDepositeAndAdvnHeadsMappingEntryMasterBean.setDepositeType(depositeType);
            accountDepositeAndAdvnHeadsMappingEntryMasterBean.setAdvancedType(advnType);
            accountDepositeAndAdvnHeadsMappingEntryMasterBean.setDeptId(deptId);
            accountDepositeAndAdvnHeadsMappingEntryMasterBean.setListOfDto(deaseEntryList);
            populateModel(model, accountDepositeAndAdvnHeadsMappingEntryMasterBean);
            model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_ID, mappingType);
            model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPOS_ID, depositeType);
            model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.ADVN_ID, advnType);
            model.addAttribute(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_ID, deptId);

            return JSP_FORM;
        } else {
            populateGridViewData(model);
            return MainetConstants.AccountDepositeAndAdvnHeadsMapping.ACC_DEPOSIT_ADVN_HEAD;
        }

    }

    @RequestMapping(params = "searchData", method = RequestMethod.POST)
    public @ResponseBody void searchData(
            final AccountDepositeAndAdvnHeadsMappingEntryMasterBean accountDepositeAndAdvnHeadsMappingEntryMasterBean,
            final BindingResult bindingResult, final Model model,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_ID) final Long mappingType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPOS_ID) final Long depositeType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.ADVN_ID) final Long advnType,
            @RequestParam(value = MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.DEPT_ID) final Long deptId) throws Exception {

        final Long advnceOrDepType = (depositeType != 0L) ? depositeType : advnType;

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        deaseEntryList = accountAdvnAndDepositeHeadMapEntryMasterService.searchRecordUsingRequestParam(orgId, mappingType,
                advnceOrDepType, deptId);

        if (deaseEntryList.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.BEAN,
                    MainetConstants.ADVNC_DEPOSIT_MAPP_ENTRY.MAPP_TYPE, null, false, new String[] { MainetConstants.ERRORS },
                    null, "No Old Record Found Please Add New"));
        }

    }

    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ModelAndView myError(final Exception exception) {
        log("----Caught" + exception.getClass().getSimpleName() + "FoundException----");
        final ModelAndView mav = new ModelAndView(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.EXCEPTION_VIEW);
        mav.addObject(MainetConstants.AccountDepositeAndAdvnHeadsMapping.NAME, exception.getClass().getSimpleName());
        mav.addObject(MainetConstants.AccountDepositeAndAdvnHeadsMapping.MESSAGE, exception.getMessage());
        return mav;
    }
}