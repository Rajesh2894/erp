
package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountDepositEntity;
import com.abm.mainet.account.dto.AccountDepositBean;
import com.abm.mainet.account.dto.AccountDepositDto;
import com.abm.mainet.account.dto.AccountDepositUploadDto;
import com.abm.mainet.account.dto.ReadExcelData;
import com.abm.mainet.account.service.AccountDepositService;
import com.abm.mainet.account.ui.validator.AccountDepositExcelValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
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

@Controller
@RequestMapping("/AccountDeposit.html")
public class AccountDepositController extends AbstractController {

    public AccountDepositController() {
        super(AccountDepositController.class, MainetConstants.CommonConstant.BLANK);

    }

    private static final String MAIN_ENTITY_NAME = "accountDepositBean";
    private final String JSP_FORM = "AccountDeposit/form";

    private final String JSP_LIST = "AccountDeposit/list";

    private static final String JSP_EXCELUPLOAD = "AccountDeposit/ExcelUpload";

    private final String SAVE_ACTION_CREATE = "/AccountDeposit/create";

    private final String SAVE_ACTION_UPDATE = "/AccountDeposit/update";

    @Autowired
    private DepartmentService departmentService;

    @Resource
    private AccountDepositService accountDepositService;

    @Resource
    private TbAcVendormasterService VendormService;

    @Resource
    private SecondaryheadMasterService secondaryheadmasterservice;
    @Autowired
    private IFileUploadService fileUpload;
    private final String DEPOSIT_TYPE = "depositType";

    private final String DEPOSIT_SOURCE = "sourceType";

    private final String DEPOSIT_STATUS = "depositstatus";

    private final String VENDOR_MAP = "vendorMap";

    private List<AccountDepositBean> chList = null;

    @RequestMapping()
    public String populateGridList(final Model model) throws Exception {
        log("Action 'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
         Map<Long, String> accountHeadsMap = new LinkedHashMap<>();
		List<AccountDepositBean> depBeanList = new ArrayList<>();
				/*
												 * accountDepositService
												 * .findAll(UserSession.getCurrent().getOrganisation().getOrgid());
												 */
      //  if (depBeanList != null) {
          //  for (final AccountDepositBean accountDepositBean : depBeanList) {
               // final Long sacHeadId = accountDepositBean.getSacHeadId();
               // if (sacHeadId != null) {
                	accountHeadsMap = secondaryheadmasterservice.findStatusPrimarySecondaryHeadDataDeposit(orgId,organisation,langId);
                   // accountHeadsMap.put(accountDepositBean.getSacHeadId(), accountHead);
               // }
           // }
       // }
        if(CollectionUtils.isNotEmpty(depBeanList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = departmentService.getDeptCode(deptId);
				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
					depBeanList = depBeanList.stream().filter(p -> p.getDpDeptid() == deptId)
							.collect(Collectors.toList());
				}
			}
        } 
        chList.addAll(depBeanList);
        final AccountDepositBean bean = new AccountDepositBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MainetConstants.AccountDeposit.ACCOUNT_HEAD_MAP, accountHeadsMap);
        model.addAttribute(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST, chList);
        result = JSP_LIST;
        return result;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountDepositEntry-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MainetConstants.TENDER_ENTRY.MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("depNo") final String depNo,
            @RequestParam("vmVendorid") final Long vmVendorid, @RequestParam("cpdDepositType") final Long cpdDepositType,
            @RequestParam("sacHeadId") final Long sacHeadId, @RequestParam("date") final String date,
            @RequestParam("depAmount") final String depAmount) {
        log("AccountDepositEntry-'getjqGridsearch' : 'get jqGrid search data'");
        Date depDate = null;
        if ((date != null) && !date.isEmpty()) {
            depDate = Utility.stringToDate(date);
        }
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long deptId =null;
        chList = new ArrayList<>();
        chList.clear();
        if(getSelfDeparmentConfiguration()!=null &&  MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
        	deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
        	String deptCode = departmentService.getDeptCode(deptId);
    		if (deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()))
    			deptId=null;
         }
        chList = accountDepositService.findByAllGridSearchData(depNo, vmVendorid, cpdDepositType, sacHeadId, depDate, depAmount,
                orgId,deptId);
        return chList;
    }

    @RequestMapping(params = "form", method = RequestMethod.POST)
    public String formForCreate(final Model model) throws Exception {
        log("Action 'formForCreate'");
        final AccountDepositBean accountDepositBean = new AccountDepositBean();
        populateModel(model, accountDepositBean, FormMode.CREATE);
        final List<AccountDepositBean> list = new ArrayList<>();
        final List<AccountDepositDto> dtolist = new ArrayList<>();
        accountDepositBean.setAccountDepositDto(dtolist);
        list.add(accountDepositBean);
        model.addAttribute(MainetConstants.TAX_HEAD_MAPPING_MASTER.MAIN_LIST_NAME, list);
        return JSP_FORM;

    }

    @RequestMapping(params = "getDepositTypeWiseAccountHeads", method = RequestMethod.POST)
    public String findDepositTypeWiseAccountHeads(final AccountDepositBean bean, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log("AccountDepositBean-'findDepositTypeWiseAccountHeads' : 'findDepositTypeWiseAccountHeads'");
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Map<Long, String> deptTyepsMap = new LinkedHashMap<>();
        final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DP,
                AccountPrefix.SAM.toString(), orgId);
        final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.MENU.A,
                AccountPrefix.ACN.toString(), orgId);
        deptTyepsMap
                .putAll(secondaryheadmasterservice.findBudgetHeadDepTypes(depTypeId, bean.getCpdDepositType(), statusId, orgId));
        model.addAttribute(MainetConstants.AccountDeposit.DEPT_TYPE_MAP, deptTyepsMap);
        // final AccountDepositBean accountDepositBean = new AccountDepositBean();
        if (bean.getDepId() == null) {
            populateModel(model, bean, FormMode.CREATE);
        } else {
            populateModel(model, bean, FormMode.UPDATE);
        }
        model.addAttribute(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(@Valid AccountDepositBean depositBean,
            final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpRequestMapping) throws Exception {
        log("Action,'create'");
        if (!bindingResult.hasErrors()) {
            depositBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);

            depositBean.setOrgid(UserSession.getCurrent()
                    .getOrganisation().getOrgid());
            depositBean.setLangId((long) UserSession.getCurrent()
                    .getLanguageId());
            depositBean.setCreatedBy(UserSession.getCurrent()
                    .getEmployee().getEmpId());
            depositBean.setCreatedDate((new Date()));
            depositBean.setLgIpMac(Utility.getClientIpAddress(httpRequestMapping));
            
             if (depositBean.getCpdStatus() == null) {
                Long cpdId =CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DO,
                         PrefixConstants.NewWaterServiceConstants.RDC, UserSession.getCurrent()
                                 .getOrganisation().getOrgid());
                
                if(cpdId!=null && cpdId>0) {
                 depositBean.setCpdStatus(cpdId);
                 }else {
                 	 bindingResult.addError(new ObjectError("Prefix", "Depoiste open status not found under RDC prefix"));
                 	 depositBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
                      final List<AccountDepositDto> list = keepDataAfterValidationError(depositBean, bindingResult);
                      model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
                      model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
                      populateModel(model, depositBean, FormMode.CREATE);
                      return JSP_FORM;	
                 }
             }
            depositBean = accountDepositService.create(depositBean);
            model.addAttribute(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT, depositBean);
            messageHelper.addMessage(redirectAttributes, new Message(
                    MessageType.SUCCESS, MainetConstants.SAVE));
            if (depositBean.getDepId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("account.deposit.no")
                                + depositBean.getDepNo() + " "
                                + ApplicationSession.getInstance().getMessage("advance.entry.success"));
            }
            if (depositBean.getDepId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("account.deposit.no")
                                + depositBean.getDepNo() + " "
                                + ApplicationSession.getInstance().getMessage("advance.entry.update"));
            }
            return JSP_FORM;
        } else {
            depositBean.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final List<AccountDepositDto> list = keepDataAfterValidationError(depositBean, bindingResult);
            model.addAttribute(MainetConstants.FIELD_MASTER.MAIN_LIST_NAME, list);
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, depositBean, FormMode.CREATE);
            return JSP_FORM;
        }
    }

    private List<AccountDepositDto> keepDataAfterValidationError(final AccountDepositBean depositBean,
            final BindingResult bindingResult) {
        List<AccountDepositDto> list = null;
        list = depositBean.getAccountDepositDto();

        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.isEmpty()) {
            list.add(new AccountDepositDto());
            depositBean.setAccountDepositDto(list);
        }
        return list;
    }

    @RequestMapping(params = "update")
    public String formForUpdate(final Model model, final AccountDepositBean bean, final HttpServletRequest request,
            @RequestParam(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT_ID) final Long depId,
            @RequestParam(MainetConstants.TAX_HEAD_MAPPING_MASTER.MODE_DATA) final String viewmode) throws Exception {

        log("Action 'formForUpdate'");
        if (viewmode.equals(MainetConstants.VIEW)) {
            model.addAttribute(MainetConstants.VIEW_MODE,
                    MainetConstants.SUCCESS);
        } else {
            model.addAttribute(MainetConstants.VIEW_MODE,
                    MainetConstants.FAILED);
        }

        final AccountDepositEntity depositList = accountDepositService.fidbyId(depId);
        bean.setCpdDepositType(depositList.getTbComparamDet().getCpdId());
        populateModel(model, bean, FormMode.UPDATE);
        bean.setDepId(depositList.getDepId());
        bean.setDepNo(depositList.getDepNo());
        // bean.setCpdDepositType(depositList.getTbComparamDet().getCpdId());
        bean.setCpdStatus(depositList.getTbComparamDet3().getCpdId());
        bean.setDepReceiptno(depositList.getDepReferenceNo());
        bean.setDepAmount(depositList.getDepAmount());
        bean.setDepRefundBal(depositList.getDepRefundBal());
        bean.setDpDeptid(depositList.getTbDepartment().getDpDeptid());
        bean.setSacHeadId(depositList.getSacHeadId());
        bean.setVmVendorid(depositList.getTbVendormaster().getVmVendorid());
        bean.setDepNarration(depositList.getDepNarration());
        bean.setDepEntryDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(depositList.getDepReceiptdt()));
        bean.setDefectLiablityDate(Utility.dateToString(depositList.getDefectLiabilityDate()));
        return JSP_FORM;
    }

    private void populateModel(final Model model, final AccountDepositBean accountDepositBean, final FormMode formMode) {
        model.addAttribute(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT, accountDepositBean);
      
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MODE, MODE_CREATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE);
            populaleListOfDepositType(model);
            populateListOfDepositSource(model);
            populateAddVendorList(model);
            populateStatus(model, accountDepositBean);

            final Map<Long, String> deptMap = new LinkedHashMap<Long, String>();
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            if(getSelfDeparmentConfiguration()!=null &&  MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
            	department=populateDepartmentBasedOnConfiguration(department);
            }
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
            model.addAttribute(
                    MainetConstants.TENDER_ENTRY.DEPT_MAP,
                    deptMap);
            final LookUp liveDateLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.FlagL,
                    PrefixConstants.SLI,
                    UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            accountDepositBean.setLiveModeDate(liveDateLookup.getOtherField());
        }
        if (formMode == FormMode.UPDATE) {
            model.addAttribute(MODE, MODE_UPDATE);
            model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE);
            populaleListOfDepositType(model);
            populateListOfDepositSource(model);
            populateVendorList(model);
            populateStatus(model, accountDepositBean);
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final Map<Long, String> deptTyepsMap = new LinkedHashMap<>();
            final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DP,
                    AccountPrefix.SAM.toString(), orgId);
            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.MENU.A,
                    AccountPrefix.ACN.toString(), orgId);
            deptTyepsMap.putAll(secondaryheadmasterservice.findBudgetHeadDepTypes(depTypeId,
                    accountDepositBean.getCpdDepositType(), statusId, orgId));
            model.addAttribute(MainetConstants.AccountDeposit.DEPT_TYPE_MAP, deptTyepsMap);
            final Map<Long, String> deptMap = new HashMap<>(0);
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            
            if(getSelfDeparmentConfiguration()!=null &&  MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
            	department=populateDepartmentBasedOnConfiguration(department);
            }
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
            model.addAttribute(
                    MainetConstants.TENDER_ENTRY.DEPT_MAP,
                    deptMap);
            final LookUp liveDateLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.FlagL,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, UserSession.getCurrent().getLanguageId(),
                    UserSession.getCurrent().getOrganisation());
            accountDepositBean.setLiveModeDate(liveDateLookup.getOtherField());
        }
    }

    private List<Object[]> populateDepartmentBasedOnConfiguration(List<Object[]> department){
		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String deptCode = departmentService.getDeptCode(deptId);
		if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()))
			department = department.stream().filter(obj -> obj[0] == deptId).collect(Collectors.toList());
    	return department;
    }
    
    private String getSelfDeparmentConfiguration() {
    	LookUp lookup=null;
    	Organisation org = UserSession.getCurrent().getOrganisation();
    	int langId = UserSession.getCurrent().getLanguageId();
        try {
        	 lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.ASD.getValue(),
                    AccountPrefix.AIC.toString(),langId,org);
        }catch(Exception e) {
        	return null;
        }
        return lookup.getOtherField();
    }
    
    private void populateStatus(final Model model, final AccountDepositBean accountDepositBean) {

        final List<LookUp> depositStatuslookUp = CommonMasterUtility.getListLookup(

                AccountPrefix.RDC.toString(), UserSession.getCurrent()
                        .getOrganisation());

        accountDepositBean.setCpdStatus(CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DO,
                PrefixConstants.NewWaterServiceConstants.RDC, UserSession.getCurrent()
                        .getOrganisation().getOrgid()));
        model.addAttribute(DEPOSIT_STATUS, depositStatuslookUp);

    }

    private void populateAddVendorList(final Model model) {
        final Map<Long, String> vendorMap = new HashMap<>();
        String vendorName;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                AccountConstants.AC.getValue(), PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = VendormService.getActiveVendors(orgId, vendorStatus);
        // final List<TbAcVendormaster> vendorList = VendormService.findAll(orgId);
        if ((vendorList != null) && !vendorList.isEmpty()) {
            for (final TbAcVendormaster vendor : vendorList) {

                vendorName = vendor.getVmVendorcode()
                        + MainetConstants.SEPARATOR
                        + vendor.getVmVendorname();
                vendorMap.put(vendor.getVmVendorid(), vendorName);
            }
        }
        model.addAttribute(VENDOR_MAP, vendorMap);
    }

    private void populateVendorList(final Model model) {
        final Map<Long, String> vendorMap = new HashMap<>();
        String vendorName;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

        final List<TbAcVendormaster> vendorList = VendormService.findAll(orgId);
        if ((vendorList != null) && !vendorList.isEmpty()) {
            for (final TbAcVendormaster vendor : vendorList) {

                vendorName = vendor.getVmVendorcode()
                        + MainetConstants.SEPARATOR
                        + vendor.getVmVendorname();
                vendorMap.put(vendor.getVmVendorid(), vendorName);
            }
        }
        model.addAttribute(VENDOR_MAP, vendorMap);
    }

    private void populateListOfDepositSource(final Model model) {
        final List<LookUp> depositSourcelookUp = CommonMasterUtility.getListLookup(
                AccountPrefix.TOS.toString(), UserSession.getCurrent()
                        .getOrganisation());
        model.addAttribute(DEPOSIT_SOURCE, depositSourcelookUp);
    }

    private void populaleListOfDepositType(final Model model) {

        final List<LookUp> depositTypelookUp = CommonMasterUtility.getListLookup(
                AccountPrefix.DTY.toString(), UserSession.getCurrent()
                        .getOrganisation());
        model.addAttribute(DEPOSIT_TYPE, depositTypelookUp);
    }

    @RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
    public String exportImportExcelTemplate(final Model model) throws Exception {
        log("AccountDepositBean-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountDepositBean bean = new AccountDepositBean();
        populateModel(model, bean, FormMode.CREATE);
        fileUpload.sessionCleanUpForFileUpload();
        result = JSP_EXCELUPLOAD;
        return result;
    }

    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountDepositExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {

        try {
            WriteExcelData<AccountDepositUploadDto> data = new WriteExcelData<>(
                    MainetConstants.DEPOSITENTRYUPLOADDTO + MainetConstants.XLSX_EXT, request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountDepositUploadDto>(), AccountDepositUploadDto.class);
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(AccountDepositBean bean, final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {

        log("Action 'loadExcelData'");

        final ApplicationSession session = ApplicationSession.getInstance();
        final UserSession userSession = UserSession.getCurrent();
        final Long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountDepositUploadDto> data = new ReadExcelData<>(filePath,
                AccountDepositUploadDto.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT, MainetConstants.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.empty.excel")));
        } else {
            final List<AccountDepositUploadDto> accountDepositUploadDtos = data.getParseData();

            final List<LookUp> depositTypelookUp = CommonMasterUtility.getListLookup(
                    AccountPrefix.DTY.toString(), UserSession.getCurrent()
                            .getOrganisation());
            Long lookUpId = null;
            int rowNo = 1;
            int count = 0;
            Long cpdDepositType = null;
            for (AccountDepositUploadDto uploadDto : accountDepositUploadDtos) {
                rowNo++;
                int depositType = 0;
                if (uploadDto.getTypeOfDeposit() == null) {
                    depositType++;
                }
                if (depositType != 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositType") + rowNo));
                    break;
                }
                int depositTypeExist = 0;
                for (LookUp list : depositTypelookUp) {
                    String accDepositType = uploadDto.getTypeOfDeposit().trim();
                    if (accDepositType.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        cpdDepositType = list.getLookUpId();
                        depositTypeExist++;
                    }

                }
                if (depositTypeExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("account.deposit.excel.depositTypeExist")));
                    break;
                }
            }
            final Map<Long, String> vendorMap = new HashMap<>();
            String vendorName;
            final List<TbAcVendormaster> vendorList = VendormService.findAll(orgId);
            if ((vendorList != null) && !vendorList.isEmpty()) {
                for (final TbAcVendormaster vendor : vendorList) {

                    vendorName = vendor.getVmVendorcode()
                            + MainetConstants.SEPARATOR
                            + vendor.getVmVendorname();
                    vendorMap.put(vendor.getVmVendorid(), vendorName);
                }
            }
            final Map<Long, String> deptMap = new HashMap<>(0);
            List<Object[]> department = null;
            department = departmentService.getAllDeptTypeNames();
            for (final Object[] dep : department) {
                if (dep[0] != null) {
                    deptMap.put((Long) (dep[0]), (String) dep[1]);
                }
            }
            final Map<Long, String> deptTyepsMap = new LinkedHashMap<>();
            final Long depTypeId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DP,
                    AccountPrefix.SAM.toString(), orgId);

            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.MENU.A,
                    AccountPrefix.ACN.toString(), orgId);
           long openStatus=CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(MainetConstants.AccountDeposit.DO,
                    PrefixConstants.NewWaterServiceConstants.RDC, UserSession.getCurrent()
                            .getOrganisation().getOrgid());

            deptTyepsMap.putAll(secondaryheadmasterservice.findBudgetHeadDepTypes(depTypeId,
                    cpdDepositType, statusId, orgId));
            AccountDepositExcelValidator validator = new AccountDepositExcelValidator();
            List<AccountDepositUploadDto> accountDepositUploadDtosUploadList = null;
            if (count == 0) {
                accountDepositUploadDtosUploadList = validator
                        .excelValidation(accountDepositUploadDtos, bindingResult, depositTypelookUp, vendorMap, deptMap,
                                deptTyepsMap);
            }
            if (!bindingResult.hasErrors()) {
                for (AccountDepositUploadDto accountDepositUploadDto : accountDepositUploadDtosUploadList) {

                    accountDepositUploadDto
                            .setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    accountDepositUploadDto.setLangId(Long.valueOf(langId));
                    accountDepositUploadDto.setUserId(userId);
                    accountDepositUploadDto.setLmoddate(new Date());
                    accountDepositUploadDto
                            .setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    accountDepositService.saveAccountDepositExcelData(
                            accountDepositUploadDto, orgId, langId, openStatus);
                }

                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        session.getMessage("accounts.success.excel"));
            }
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));

        return JSP_EXCELUPLOAD;
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

    @RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
    public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
            final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
        UserSession.getCurrent().setBrowserType(browserType);
        final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
        final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
                browserType);
        return jsonViewObject;
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
}
