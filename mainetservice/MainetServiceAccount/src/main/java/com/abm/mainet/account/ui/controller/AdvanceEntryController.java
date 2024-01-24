package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.account.service.AdvanceEntryService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.service.AccountHeadPrimaryAccountCodeMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author prasad.kancharla
 *
 */
@Controller
@RequestMapping("/AdvanceEntry.html")
public class AdvanceEntryController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcAdvanceEntry";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcAdvanceEntry/form";
    private static final String JSP_VIEW = "tbAcAdvanceEntry/view";
    private static final String JSP_LIST = "tbAcAdvanceEntry/list";
    private String modeView = MainetConstants.BLANK;

    @Resource
    private AccountHeadPrimaryAccountCodeMasterService accountHeadPrimaryAccountCodeMasterService;
    @Resource
    private TbAcVendormasterService tbAcVendormasterService;
    @Resource
    private AdvanceEntryService advanceEntryService;
    @Autowired
    private DepartmentService departmentService;


    private List<AdvanceEntryDTO> chList = null;

    public AdvanceEntryController() {
        super(AdvanceEntryController.class, MAIN_ENTITY_NAME);
        log("AdvanceEntryController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AdvanceEntryDTO-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("advanceNumber") final Long advanceNumber, @RequestParam("advanceDate") final String advanceDate,
            @RequestParam("name") final String name,
            @RequestParam("advanceAmount") final String advanceAmount, @RequestParam("advanceType") final Long advanceType,
            @RequestParam("cpdIdStatus") final String cpdIdStatus) throws ParseException {
        log("AdvanceEntryDTO-'getjqGridsearch' : 'get jqGrid search data'");

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = new ArrayList<>();
        chList.clear();
        Date advDate = null;
        if ((advanceDate != null) && !advanceDate.isEmpty()) {
            final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
            advDate = sdf.parse(advanceDate);
        }
        BigDecimal advAmount = null;
        if ((advanceAmount != null) && !advanceAmount.isEmpty()) {
            advAmount = new BigDecimal(advanceAmount);
        }
        Long deptId=null;
        if(getSelfDeparmentConfiguration()!=null &&  MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
        	deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
        	String deptCode = departmentService.getDeptCode(deptId);
    		if (deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()))
    			deptId=null;
         }
        chList = advanceEntryService.findByAllGridSearchData(advanceNumber, advDate, name, advAmount, advanceType, cpdIdStatus,
                orgId,deptId);
        for (final AdvanceEntryDTO dto : chList) {
            final String cpdIdStats = dto.getCpdIdStatus();
            if ((cpdIdStats != null) && !cpdIdStats.isEmpty()) {
                if (cpdIdStats.equals(MainetConstants.MENU.A)) {
                    dto.setCpdIdStatusDup(MainetConstants.AdvanceEntry.OPEN);
                }
                if (cpdIdStats.equals(MainetConstants.STATUS.INACTIVE)) {
                    dto.setCpdIdStatusDup(MainetConstants.DASHBOARD.CLOSE);
                }
            }
            if ((dto.getAdvanceAmount() != null) && !dto.getAdvanceAmount().isEmpty()) {
                final String displayAmount = CommonMasterUtility
                        .getAmountInIndianCurrency(new BigDecimal(dto.getAdvanceAmount()));
                dto.setAdvanceAmount(displayAmount);
            }
            if ((dto.getBalanceAmount() != null) && !dto.getBalanceAmount().isEmpty()) {
                final String displayAmount1 = CommonMasterUtility
                        .getAmountInIndianCurrency(new BigDecimal(dto.getBalanceAmount()));
                dto.setBalanceAmount(displayAmount1);
                if (dto.getBalanceAmount().equals(MainetConstants.ZERO)
                        || dto.getBalanceAmount().equals(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)) {
                    dto.setCpdIdStatusDup(MainetConstants.DASHBOARD.CLOSE);
                }
            }
            if (dto.getPrAdvEntryDate() != null) {
                dto.setAdvanceDate(UtilityService.convertDateToDDMMYYYY(dto.getPrAdvEntryDate()));
            }
            final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
                    UserSession.getCurrent().getOrganisation());
            final String date = lookUp.getOtherField();
            final Date sliDate = Utility.stringToDate(date);
            if (dto.getPrAdvEntryDate().after(sliDate) || dto.getPrAdvEntryDate().equals(sliDate)) {
                dto.setSliStatusFlag(MainetConstants.Y_FLAG);
            } else {
                dto.setSliStatusFlag(MainetConstants.N_FLAG);
            }

            if (dto.getPrAdvEntryNo() != null) {
                dto.setAdvanceNumber(dto.getPrAdvEntryNo());
            }
            final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
                    MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
            for (LookUp lookUps : recieptVouType) {
                if (lookUps.getLookUpCode().equals("A")) {
                    dto.setCategoryTypeId(lookUps.getLookUpId());
                }
            }

            if (dto.getVendorId() != null) {
                final TbAcVendormaster vendorList = tbAcVendormasterService.findById(dto.getVendorId(), orgId);
                if ((vendorList != null) && ((vendorList.getVmVendorname() != null) && !vendorList.getVmVendorname().isEmpty())) {
                    dto.setVendorName(vendorList.getVmVendorname());
                }
            }
        }
        return chList;
    }

    @RequestMapping()
    public String getList(final Model model) throws Exception {
        log("AdvanceEntryDTO-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final AdvanceEntryDTO dto = new AdvanceEntryDTO();
        populateModel(model, dto, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) throws Exception {
        log("AdvanceEntryDTO-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AdvanceEntryDTO dto = new AdvanceEntryDTO();
        populateModel(model, dto, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AdvanceEntryDTO dto, final FormMode formMode) throws Exception {
        log("AdvanceEntryDTO-'populateModel' : populate model");
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();

        final List<LookUp> AdvanceType = CommonMasterUtility.getListLookup(MainetConstants.AdvanceEntry.ATY,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.AdvanceEntry.ADVANCE_TYPE, AdvanceType);

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(AccountConstants.AC.getValue(),
                PrefixConstants.VSS, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> list = tbAcVendormasterService.getActiveVendors(orgId, vendorStatus);
        model.addAttribute(MainetConstants.AdvanceEntry.VENDOR_LIST, list);

        final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(MainetConstants.BUDGET_CODE.STATUS_FLAG,
                UserSession.getCurrent().getOrganisation());
        model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);

        final LookUp liveDateLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, langId, organisation);
        dto.setLiveModeDate(liveDateLookup.getOtherField());

        model.addAttribute(MAIN_ENTITY_NAME, dto);
        if (formMode == FormMode.CREATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
        
         List<DepartmentLookUp> deptList =null; /*CommonMasterUtility
				.getDepartmentForWS(UserSession.getCurrent().getOrganisation());*/
         List<Department> department = departmentService.getDepartments("A"); 
		 deptList =department.stream().map(m->{
			 DepartmentLookUp lookup=new DepartmentLookUp();
			 lookup.setLookUpId(m.getDpDeptid());
			 lookup.setLookUpCode(m.getDpDeptcode());
			 lookup.setDefaultVal(m.getDpDeptdesc());
			 return lookup; 
		 }).collect(Collectors.toList());
         if(CollectionUtils.isNotEmpty(deptList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				deptList = populateDepartmentBasedOnConfiguration(deptList);
			}
         }
		model.addAttribute(MainetConstants.AccountBillEntry.DEPT_LIST, deptList);

    }

    private List<DepartmentLookUp> populateDepartmentBasedOnConfiguration(List<DepartmentLookUp> department){
		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
		String deptCode = departmentService.getDeptCode(deptId);
		if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()))
			department = department.stream().filter(obj -> obj.getLookUpId() == deptId).collect(Collectors.toList());
    	return department;
    }
    
    public String getSelfDeparmentConfiguration() {
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
    
    @RequestMapping(params = "getBudgetHeadCodeDesc", method = RequestMethod.POST)
    public String getBudgetHeadCodeDesc(final AdvanceEntryDTO dto, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest request, final BindingResult bindingResult) throws Exception {
        log("StandardAccountHeadMappingController-'getPrimaryHeadCodeDesc' : 'get PrimaryHeadCodeDesc'");
        String result = MainetConstants.CommonConstant.BLANK;

        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long acountSubType = dto.getAdvanceTypeId();
        Map<Long, String> budgetHead = new LinkedHashMap<>();
        budgetHead = advanceEntryService.getBudgetHeadAllData(acountSubType, organisation, langId);
        model.addAttribute(MainetConstants.AdvanceEntry.ADVANCE_HEAD, budgetHead);
        populateModel(model, dto, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, dto);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AdvanceEntryDTO tbAcAdvanceEntry, final BindingResult bindingResult, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
        log("AdvanceEntryDTO-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcAdvanceEntry.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final Organisation organisation = UserSession.getCurrent().getOrganisation();
            final UserSession userSession = UserSession.getCurrent();
            tbAcAdvanceEntry.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcAdvanceEntry.setLangId(userSession.getLanguageId());
            tbAcAdvanceEntry.setCreatedBy(userSession.getEmployee().getEmpId());
            tbAcAdvanceEntry.setCreatedDate(new Date());
            tbAcAdvanceEntry.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcAdvanceEntry, FormMode.CREATE);
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            final LookUp statusLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MENU.A,
                    MainetConstants.BUDGET_CODE.STATUS_FLAG, langId, organisation);

            tbAcAdvanceEntry.setCpdIdStatus(statusLookup.getLookUpCode());
            AdvanceEntryDTO tbAcAdvanceEntryCreated = advanceEntryService.saveAdvanceEntryFormData(tbAcAdvanceEntry, orgid,
                    langId);
            if (tbAcAdvanceEntryCreated == null) {
                tbAcAdvanceEntryCreated = new AdvanceEntryDTO();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcAdvanceEntryCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcAdvanceEntry.getPrAdvEntryId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("advance.entry.advanceno")
                                + tbAcAdvanceEntryCreated.getPrAdvEntryNo() + " "
                                + ApplicationSession.getInstance().getMessage("advance.entry.success"));
            }
            if (tbAcAdvanceEntry.getPrAdvEntryId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("advance.entry.advanceno")
                                + tbAcAdvanceEntryCreated.getPrAdvEntryNo() + " "
                                + ApplicationSession.getInstance().getMessage("advance.entry.update"));
            }
            tbAcAdvanceEntry.setSuccessFlag(MainetConstants.FlagY);
            result = JSP_FORM;
        } else {
            tbAcAdvanceEntry.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcAdvanceEntry, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForUpdate", method = RequestMethod.POST)
    public String formForUpdate(AdvanceEntryDTO tbAcAdvanceEntry, @RequestParam("prAdvEntryId") final Long prAdvEntryId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcAdvanceEntry-'gridData' : 'formForUpdate'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            tbAcAdvanceEntry = advanceEntryService.getDetailsByUsingPrAdvEntryId(tbAcAdvanceEntry, orgid, langId);
            final Long acountSubType = tbAcAdvanceEntry.getAdvanceTypeId();
            Map<Long, String> budgetHead = new LinkedHashMap<>();
            budgetHead = advanceEntryService.getBudgetHeadAllData(acountSubType, orgid, langId);
            model.addAttribute(MainetConstants.AdvanceEntry.ADVANCE_HEAD, budgetHead);
            model.addAttribute(MAIN_ENTITY_NAME, tbAcAdvanceEntry);
            populateModel(model, tbAcAdvanceEntry, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("AdvanceEntryDTO 'formForUpdate' : formForUpdate done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("AdvanceEntryDTO 'formForUpdate' : formForUpdate done - redirect");
            tbAcAdvanceEntry.setSuccessFlag(MainetConstants.FlagY);
            result = JSP_FORM;
        } else {
            log("AdvanceEntryDTO 'formForUpdate' : binding errors");
            populateModel(model, tbAcAdvanceEntry, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AdvanceEntryDTO tbAcAdvanceEntry, @RequestParam("prAdvEntryId") final Long prAdvEntryId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("tbAcAdvanceEntry-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final Organisation orgid = UserSession.getCurrent().getOrganisation();
            final int langId = UserSession.getCurrent().getLanguageId();
            tbAcAdvanceEntry = advanceEntryService.getDetailsByUsingPrAdvEntryId(tbAcAdvanceEntry, orgid, langId);
            final Long acountSubType = tbAcAdvanceEntry.getAdvanceTypeId();
            Map<Long, String> budgetHead = new LinkedHashMap<>();
            budgetHead = advanceEntryService.getBudgetHeadAllData(acountSubType, orgid, langId);
            model.addAttribute(MainetConstants.AdvanceEntry.ADVANCE_HEAD, budgetHead);
            model.addAttribute(MAIN_ENTITY_NAME, tbAcAdvanceEntry);
            populateModel(model, tbAcAdvanceEntry, FormMode.VIEW);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("AdvanceEntryDTO 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("AdvanceEntryDTO 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("AdvanceEntryDTO 'view' : binding errors");
            populateModel(model, tbAcAdvanceEntry, FormMode.VIEW);
            result = JSP_VIEW;
        }
        return result;
    }

    public String getModeView() {
        return modeView;
    }

    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }
}