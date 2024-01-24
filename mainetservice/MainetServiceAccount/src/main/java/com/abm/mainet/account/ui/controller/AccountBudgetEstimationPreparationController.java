package com.abm.mainet.account.ui.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.service.AccountBudgetEstimationPreparationService;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
                 
@Controller
@RequestMapping("/AccountBudgetEstimationPreparation.html")
public class AccountBudgetEstimationPreparationController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetEstimationPreparation";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetEstimationPreparation/form";
    private static final String JSP_VIEW = "tbAcBudgetEstimationPreparation/view";
    private static final String JSP_LIST = "tbAcBudgetEstimationPreparation/list";
    private static final String JSP_BULK_EDIT_SEARCH = "tbAcBudgetEstimationPreparation/bulk_edit_search";
    private static final String JSP_BULK_EDIT_SAVE = "tbAcBudgetEstimationPreparation/bulk_edit_save";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;

    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private AccountBudgetEstimationPreparationService accountBudgetEstimationPreparationService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    
    private List<AccountBudgetEstimationPreparationBean> chList = null;

    public AccountBudgetEstimationPreparationController() {
        super(AccountBudgetEstimationPreparationController.class, MAIN_ENTITY_NAME);
        log("AccountBudgetEstimationPreparationController created.");
    }
    
    @RequestMapping(params = "bulk_edit_search")
    public String bulkEditSearch(final Model model) {
        log("AccountBudgetEstimationPreparation-'bulkEditSearch' : 'bulkEditSearch'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetEstimationPreparationBean bean = new AccountBudgetEstimationPreparationBean();
        bean.setTempDate(Utility.dateToString(new Date()));
        //populateModel(model, bean, FormMode.BULKEDITSEARCH);
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_BULK_EDIT_SEARCH;
        return result;
    }
    
    @RequestMapping(params = "getjqGridBulkEditsearch", method = RequestMethod.POST)
    public String getCheqDataForBulkEdit(AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            @RequestParam("faYearid") final Long faYearid,
            @RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("fieldId") Long fieldId, @RequestParam("cpdBugsubtypeId") final Long cpdBugsubtypeId,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest
            )  throws ParseException {
        log("AccountBudgetEstimationPreparation-'getjqGridBulkEditsearch' : 'get jqGrid search data bulk edit'");
        chList = new ArrayList<>();
        chList.clear();
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<String, String> primaryIdAllData = new LinkedHashMap<>();
        Map<String, String> findAccountBudgetSummaryBasedOnNFY=null;
        
        Long prBudgetCodeid = null;
        
        
        primaryIdAllData = accountBudgetEstimationPreparationService.findByBudgetIdsBulkEdit(faYearid, cpdBugtypeId, 
        		 dpDeptid, cpdBugsubtypeId, fieldId, orgId);
        
        int size = primaryIdAllData.size();
        if(size>0) {
        	
        	final List<Department> deptList = departmentService.getDepartments(MainetConstants.STATUS.ACTIVE);
            for (final String key : primaryIdAllData.keySet()) {
                prBudgetCodeid = Long.valueOf(key);
                final List<AccountBudgetEstimationPreparationBean> chListA = accountBudgetEstimationPreparationService
                        .findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid, orgId);
                chList.addAll(chListA);
                String amount = null;
                String tansAmount = null;
                for (final AccountBudgetEstimationPreparationBean bean : chList) {
                    final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                    final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
                    bean.setPrBudgetCode(budgetCode);

                    if (bean.getEstimateForNextyear() != null) {
                        tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getEstimateForNextyear());
                        bean.setEstimateForNextyearDup(tansAmount);
                    }
                    if (bean.getApprBugStandCom() != null) {
                        amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getApprBugStandCom());
                        bean.setApprBugStandComDup(amount);
                    }
                    
                    if(bean.getFinalizedBugGenBody()!=null) {
                    	bean.setFinalizedBugGenBodyDup(CommonMasterUtility.getAmountInIndianCurrency(bean.getFinalizedBugGenBody()));
                    }
                    
    				if (bean.getDpDeptid() != null) {
    					for (Department d : deptList) {
    						if (d.getDpDeptid() == bean.getDpDeptid()) {
    							bean.setDepartmentDesc(d.getDpDeptdesc());
    							break;
    						}
    					}
    				}
                }
            }
            if(CollectionUtils.isNotEmpty(chList)) {
    			if (getSelfDeparmentConfiguration() != null
    					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
    				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
    				String deptCode = departmentService.getDeptCode(deptId);
    				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
    					chList = chList.stream().filter(p -> p.getDpDeptid() == deptId)
    							.collect(Collectors.toList());
    				}
    			}
            } 
            
            String budgetCodeIds="";
            for (AccountBudgetEstimationPreparationBean accountBudgetEstimationPreparationBean : chList) {
            	budgetCodeIds=budgetCodeIds.concat(accountBudgetEstimationPreparationBean.getBugestId()+",");
    		}
            budgetCodeIds=budgetCodeIds.substring(0, budgetCodeIds.length()-1);
            String viewmode="EDIT";
            
            final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                    PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

            if (!bindingResult.hasErrors()) {
                if (viewmode.equals(MainetConstants.EDIT)) {
                    model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
                } else {
                    model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
                }
                setModeView(viewmode);
                //tbAcBudgetEstimationPreparation.setBugestId(bugestId);
                final UserSession userSession = UserSession.getCurrent();
                final int languageId = userSession.getLanguageId();
                //final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
                final Organisation org = UserSession.getCurrent().getOrganisation();
                
                //final Long faYearid = tbAcBudgetEstimationPreparation.getFaYearid();
                tbAcBudgetEstimationPreparation = accountBudgetEstimationPreparationService
                        .getDetailsUsingEstimationPreparationIdBulkEdit(tbAcBudgetEstimationPreparation, languageId, org, orgId,budgetCodeIds);
                if (tbAcBudgetEstimationPreparation.getFaYearid() != null) {
                    final Long cpdBugtypeid = tbAcBudgetEstimationPreparation.getCpdBugtypeId();
                    //final Long dpDeptid = tbAcBudgetEstimationPreparation.getDpDeptid();
                    if (dpDeptid != null && cpdBugtypeid != null) {
                        if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                            Map<Long, String> budgetCode = new LinkedHashMap<>();
                            budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_REV_PERCENT, revenueLookup.getOtherField());
                            model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                        }
                        if (expLookup.getLookUpId() == cpdBugtypeid) {
                            Map<Long, String> budgetCode = new LinkedHashMap<>();
                            budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGET_EXP_PERCENT, expLookup.getOtherField());
                            model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                        }
                    }
                }
                if (tbAcBudgetEstimationPreparation.getNextFaYearid() != null) {
                    tbAcBudgetEstimationPreparation.setNextFinancialYearDesc(
                            tbFinancialyearService.findFinancialYearDesc(tbAcBudgetEstimationPreparation.getNextFaYearid()));
                }
                if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                        && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
                    final List<AccountBudgetProjectedRevenueEntryBean> listOfAllFinacialYearDates = accountBudgetProjectedRevenueEntryService
                            .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                    model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
                }
                if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                        && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
                    final List<AccountBudgetProjectedExpenditureBean> listOfAllFinacialYearDates = accountBudgetProjectedExpenditureService
                            .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                    model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
                }
                
                findAccountBudgetSummaryBasedOnNFY = accountBudgetEstimationPreparationService.findAccountBudgetSummaryBasedOnNFY(tbAcBudgetEstimationPreparation.getNextFaYearid());
                //model.addAttribute("BUDGET_SUMMARY", findAccountBudgetSummaryBasedOnNFY);
                if(!findAccountBudgetSummaryBasedOnNFY.isEmpty()) {
                tbAcBudgetEstimationPreparation.setEstimateForNextyearRevAvg(findAccountBudgetSummaryBasedOnNFY.get("estimateForNextyearRevAvg"));
                tbAcBudgetEstimationPreparation.setApprBugStandComRevAvg(findAccountBudgetSummaryBasedOnNFY.get("apprBugStandComRevAvg"));
                tbAcBudgetEstimationPreparation.setFinalizedBugGenBodRevAvg(findAccountBudgetSummaryBasedOnNFY.get("finalizedBugGenBodRevAvg"));
                tbAcBudgetEstimationPreparation.setEstimateForNextyearExpAvg(findAccountBudgetSummaryBasedOnNFY.get("estimateForNextyearExpAvg"));
                tbAcBudgetEstimationPreparation.setApprBugStandComExpAvg(findAccountBudgetSummaryBasedOnNFY.get("apprBugStandComExpAvg"));
                tbAcBudgetEstimationPreparation.setFinalizedBugGenBodExpAvg(findAccountBudgetSummaryBasedOnNFY.get("finalizedBugGenBodExpAvg"));
                }
                model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetEstimationPreparation);
                populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
                messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
                log("tbAcBudgetEstimationPreparation 'formforupdate' : update done - redirect");
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
                log("tbAcBudgetEstimationPreparation 'formforupdate' : update done - redirect");
                
                result = JSP_BULK_EDIT_SAVE;
            } else {
                log("tbAcBudgetEstimationPreparation 'formforupdate' : binding errors");
                populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
                result = JSP_BULK_EDIT_SEARCH;
            }
        	
        }
        else {
        	bindingResult.addError(new ObjectError("Prefix", "No records found"));
            tbAcBudgetEstimationPreparation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.CREATE);
            result = JSP_BULK_EDIT_SEARCH;
        }
        
        
        return result;

    }
    
    @RequestMapping(params = "bulkEditSave", method = RequestMethod.POST)
    public String bulkEditSave(final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetEstimationPreparationBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            result = JSP_BULK_EDIT_SAVE;
        } else {
            result = JSP_BULK_EDIT_SAVE;
        }
        return result;
    }


    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountBudgetEstimationPreparation-'gridData' : 'Get grid Data'");
        final JQGridResponse response = new JQGridResponse<>();
//        Enumeration<String> eparam = request.getParameterNames();
//        while(eparam.hasMoreElements()){
//        	String reqp= (String)eparam.nextElement();
//       	    log("gridData parameter: "+reqp);
//        }
        final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
        response.setRows(chList);
        response.setTotal(chList.size());
        response.setRecords(chList.size());
        response.setPage(page);
        model.addAttribute(MAIN_LIST_NAME, chList);
        return response;
    }

    @RequestMapping(params = "getOrgAmountByLastYearCount", method = RequestMethod.POST)
    public @ResponseBody Map<String, BigDecimal> findBudgetEstimationPreparationLastYearCountAmountData(
            final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cnt") final int cnt)
            throws Exception {
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<BigDecimal> listOfLastYearCountAllDetails = accountBudgetEstimationPreparationService
                .findBudgetEstimationPreparationLastYearCountAmountData(faYearid, Long.valueOf(budgCodeid), orgId);
        final Map<String, BigDecimal> lastYearOrgBalMap = new HashMap<>();
        if ((listOfLastYearCountAllDetails != null) && !listOfLastYearCountAllDetails.isEmpty()) {
            final int size = listOfLastYearCountAllDetails.size();
            for (int i = 0; i < size; i++) {
                lastYearOrgBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_REV + i,
                        listOfLastYearCountAllDetails.get(i));
            }
        }
        return lastYearOrgBalMap;
    }

    @RequestMapping(params = "getOrgAmountByLastYearCountExp", method = RequestMethod.POST)
    public @ResponseBody Map<String, BigDecimal> findBudgetEstimationPreparationLastYearCountAmountDataExp(
            final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont)
            throws Exception {
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final List<BigDecimal> listOfLastYearCountAllDetails = accountBudgetEstimationPreparationService
                .findBudgetEstimationPreparationLastYearCountAmountDataExp(faYearid, Long.valueOf(budgCodeid), orgId);
        final Map<String, BigDecimal> lastYearOrgBalMap = new HashMap<>();
        if ((listOfLastYearCountAllDetails != null) && !listOfLastYearCountAllDetails.isEmpty()) {
            final int size = listOfLastYearCountAllDetails.size();
            for (int i = 0; i < size; i++) {
                lastYearOrgBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_EXP + i,
                        listOfLastYearCountAllDetails.get(i));
            }
        }
        return lastYearOrgBalMap;
    }
    
    @RequestMapping(params = "getActualofOrgAmountExp", method = RequestMethod.POST)
    public @ResponseBody BigDecimal findBudgetEstimationPreparationActualAmountDataExp(
            final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont)
            throws Exception {
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        
        List<BigDecimal> lastYearOrgBalMap = accountBudgetEstimationPreparationService.findBudgetEstimationPreparationLastYearExpActualCountAmountDataExp(faYearid, Long.valueOf(budgCodeid), orgId, bean.getDpDeptid());
       
        return lastYearOrgBalMap.get(0);
    }
    
    @RequestMapping(params = "getActualofOrgAmountRev", method = RequestMethod.POST)
    public @ResponseBody BigDecimal findBudgetEstimationPreparationRevenueActualAmountDataExp(
            final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont)
            throws Exception {
        final Long faYearid = bean.getFaYearid();
        
       
        final String budgCodeid =  bean.getBugprojRevBeanList().get(cont).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        
        List<BigDecimal> lastYearOrgBalMap = accountBudgetEstimationPreparationService.findBudgetEstimationPreparationLastYearActualCountAmountDataRev(faYearid, Long.valueOf(budgCodeid), orgId, bean.getDpDeptid());
       
        return lastYearOrgBalMap.get(0);
    }


	@RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> findBudgetAdditionalSupplementalAmountData(
			final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
			@RequestParam("cnt") final int cnt) {

		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final Long faYearid = bean.getFaYearid();
		final String budgCodeid = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode()
				.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
		final Map<String, String> opBalMap = new HashMap<>();
		final List<Object[]> orgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
				Long.valueOf(budgCodeid), orgId);
		if (!orgEsmtAmount.isEmpty()) {
			for (final Object[] objects : orgEsmtAmount) {
				if (objects[4] == bean.getDpDeptid()) {
					if (objects[0] == null) {
						BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
						originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
						opBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_REV,
								originalEstAmount.toString());
					}
					if (objects[0] != null) {
						BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
						revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
						opBalMap.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_REV,
								revisedEsmtAmount.toString());
					}
				}
			}
		}
		return opBalMap;
	}

    @RequestMapping(params = "getOrgBalGridloadExp", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetEstimationPreparationAmountDataExp(
            final AccountBudgetEstimationPreparationBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont) {
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<Object[]> list = null;
        final Map<String, String> opBalMapExp = new HashMap<>();
        list = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        if (!list.isEmpty()) {
            for (final Object[] amount : list) {
              if(bean.getDpDeptid()==amount[4]) {	
                if (amount[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(amount[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_EXP,
                            originalEstAmount.toString());
                }
                if (amount[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(amount[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opBalMapExp.put(MainetConstants.BUDGET_ESTIMATION_PREPARATION.ORIGINAL_ESMT_AMT_EXP,
                            revisedEsmtAmount.toString());
                }
            }
          }
        }     
        return opBalMapExp;
    }

    @RequestMapping(params = "getOrgBalRevDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationRevDuplicateCombination(final AccountBudgetEstimationPreparationBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final Long deptId=bean.getDpDeptid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList().get(cnt).getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetEstimationPreparationService.isBudgetEstimationPreparationEntryExists(faYearid,
                Long.valueOf(prRevBudgetCode), orgId,deptId)) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_ESTIMATION_PREPARATION.BUDGET_ESTIMATION_PREPARATION_MASTER,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getOrgBalExpDuplicateGridloadData", method = RequestMethod.POST)
    public @ResponseBody boolean findBudgetAllocationExpDuplicateCombination(final AccountBudgetEstimationPreparationBean bean,
            final HttpServletRequest request, final Model model, final BindingResult bindingResult,
            @RequestParam("cont") final int cont) {
        boolean isValidationError = false;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final Long deptId=bean.getDpDeptid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList().get(cont).getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        if (accountBudgetEstimationPreparationService.isBudgetEstimationPreparationEntryExists(faYearid,
                Long.valueOf(prExpBudgetCode), orgId,deptId)) {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_ESTIMATION_PREPARATION.BUDGET_ESTIMATION_PREPARATION_MASTER,
                    MainetConstants.CommonConstant.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                    ApplicationSession.getInstance().getMessage(MainetConstants.CommonConstant.BLANK)));
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "getjqGridsearch")
    public @ResponseBody List<?> getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("faYearid") final Long faYearid,
            @RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("prBudgetCodeid") Long prBudgetCodeid, @RequestParam("fundId") final Long fundId,
            @RequestParam("functionId") final Long functionId) {
        log("AccountBudgetEstimationPreparation-'getjqGridsearch' : 'get jqGrid search data'");
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Map<String, String> primaryIdAllData = new LinkedHashMap<>();
        primaryIdAllData = accountBudgetEstimationPreparationService.findByBudgetIds(faYearid, fundId, functionId, cpdBugtypeId,
                prBudgetCodeid, dpDeptid, orgId);
        final List<Department> deptList = departmentService.getDepartments(MainetConstants.STATUS.ACTIVE);
        for (final String key : primaryIdAllData.keySet()) {
            prBudgetCodeid = Long.valueOf(key);
            final List<AccountBudgetEstimationPreparationBean> chListA = accountBudgetEstimationPreparationService
                    .findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid, orgId);
            chList.addAll(chListA);
            String amount = null;
            String tansAmount = null;
            for (final AccountBudgetEstimationPreparationBean bean : chList) {
                final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
                bean.setPrBudgetCode(budgetCode);

                if (bean.getEstimateForNextyear() != null) {
                    tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getEstimateForNextyear());
                    bean.setEstimateForNextyearDup(tansAmount);
                }
                if (bean.getApprBugStandCom() != null) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getApprBugStandCom());
                    bean.setApprBugStandComDup(amount);
                }
                
                if(bean.getFinalizedBugGenBody()!=null) {
                	bean.setFinalizedBugGenBodyDup(CommonMasterUtility.getAmountInIndianCurrency(bean.getFinalizedBugGenBody()));
                }
                
				if (bean.getDpDeptid() != null) {
					for (Department d : deptList) {
						if (d.getDpDeptid() == bean.getDpDeptid()) {
							bean.setDepartmentDesc(d.getDpDeptdesc());
							break;
						}
					}
				}
            }
        }
        if(CollectionUtils.isNotEmpty(chList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = departmentService.getDeptCode(deptId);
				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
					chList = chList.stream().filter(p -> p.getDpDeptid() == deptId)
							.collect(Collectors.toList());
				}
			}
        } 
        
        
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetEstimationPreparationData(final AccountBudgetEstimationPreparationBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult)
            throws Exception {
        log("AccountBudgetEstimationPreparation-'getjqGridload' : 'get getjqGridload data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        final int langId = UserSession.getCurrent().getLanguageId();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final Long faYearid = bean.getFaYearid();
        if ((bean.getFaYearid() != null && bean.getCpdBugtypeId() != null)
                && bean.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            final List<AccountBudgetProjectedRevenueEntryBean> listOfAllFinacialYearDates = accountBudgetProjectedRevenueEntryService
                    .getListOfAllFinacialYearDates(orgId, faYearid);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_REV_PERCENT, revenueLookup.getOtherField());
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
        }
        if ((bean.getFaYearid() != null && bean.getCpdBugtypeId() != null)
                && bean.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            final List<AccountBudgetProjectedExpenditureBean> listOfAllFinacialYearDates = accountBudgetProjectedExpenditureService
                    .getListOfAllFinacialYearDates(orgId, faYearid);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGET_EXP_PERCENT, expLookup.getOtherField());
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
        }
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getDpDeptid();
            if (dpDeptid != null && cpdBugtypeid != null) {
            	  if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                      Map<Long, String> budgetCode = new LinkedHashMap<>();
                      budgetCode = accountBudgetCodeService.findByAllRevBudgetHeads(null,null,null,null,organisation,langId);
                      model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                  }
                  if (expLookup.getLookUpId() == cpdBugtypeid) {
                      Map<Long, String> budgetCode = new LinkedHashMap<>();
                      budgetCode = accountBudgetCodeService.findByAllExpBudgetHeads(null,null,null,null,organisation,langId);
                      model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                  }
            }
        }
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping()
    public String getList(final Model model, final HttpServletRequest request) {
        log("AccountBudgetEstimationPreparation-'list' :'list'");
//        Enumeration<String> eparams = request.getParameterNames();
//        while(eparams.hasMoreElements()){
//        	//System.out.println("RequestMapping parameter: "+eparams.nextElement().toString());
//        	String reqp= (String)eparams.nextElement();
//        	 log("RequestMapping parameter: "+reqp);
//        }
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String faYearid = UserSession.getCurrent().getFinYearId();
        final Map<Long, String> fundMap = new LinkedHashMap<>();
        final Map<Long, String> functionMap = new LinkedHashMap<>();
        final List<Department> deptList = departmentService.getDepartments(MainetConstants.STATUS.ACTIVE);
        if ((faYearid != null) && !faYearid.isEmpty()) {
            chList = accountBudgetEstimationPreparationService.findBudgetEstimationPreparationByFinId(orgId);
            String amount = null;
            String tansAmount = null;
            for (final AccountBudgetEstimationPreparationBean bean : chList) {
                final Long prBudgetCodeId = bean.getPrBudgetCodeid();
                final List<Object[]> fundCode = accountBudgetCodeService.getFundCode(prBudgetCodeId, orgId);
                if (fundCode != null) {
                    for (final Object[] objects : fundCode) {
                        if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                            fundMap.put((Long) objects[0],
                                    objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                        }
                    }
                }
                final List<Object[]> functionCode = accountBudgetCodeService.getFunctionCode(prBudgetCodeId, orgId);
                for (final Object[] objects : functionCode) {
                    if ((objects[0] != null) && (objects[1] != null) && (objects[2] != null)) {
                        functionMap.put((Long) objects[0],
                                objects[1].toString() + MainetConstants.SEPARATOR + objects[2].toString());
                    }
                }
                final Long prBudgetCodeid = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
                bean.setPrBudgetCode(budgetCode);

                if (bean.getEstimateForNextyear() != null) {
                    tansAmount = CommonMasterUtility.getAmountInIndianCurrency(bean.getEstimateForNextyear());
                    bean.setEstimateForNextyearDup(tansAmount);
                }
                if (bean.getApprBugStandCom() != null) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getApprBugStandCom());
                    bean.setApprBugStandComDup(amount);
                }
                
                if(bean.getFinalizedBugGenBody()!=null) {
                	bean.setFinalizedBugGenBodyDup(CommonMasterUtility.getAmountInIndianCurrency(bean.getFinalizedBugGenBody()));
                }
				if (bean.getDpDeptid() != null) {
					for (Department d : deptList) {
						if (d.getDpDeptid() == bean.getDpDeptid()) {
							bean.setDepartmentDesc(d.getDpDeptdesc());
							break;
						}
					}
				}
            }
        }
        if(CollectionUtils.isNotEmpty(chList)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
				String deptCode = departmentService.getDeptCode(deptId);
				if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue())) {
					chList = chList.stream().filter(p -> p.getDpDeptid() == deptId)
							.collect(Collectors.toList());
				}
			}
        } 
        final AccountBudgetEstimationPreparationBean bean = new AccountBudgetEstimationPreparationBean();
        populateModel(model, bean, FormMode.CREATE);
        String emplDesignation = ApplicationSession.getInstance().getMessage("employee.desi");
        Designation designation = UserSession.getCurrent().getEmployee().getDesignation();
        //only allow edit the entry for Accountant Designation
        if(designation.getDsgshortname().equalsIgnoreCase(emplDesignation))
        model.addAttribute("designation",MainetConstants.Y_FLAG);	
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MainetConstants.BUDGET_ESTIMATION_PREPARATION.FUND_MAP, fundMap);
        model.addAttribute(MainetConstants.BUDGET_ESTIMATION_PREPARATION.FUNCTION_MAP, functionMap);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
        log("AccountBudgetEstimationPreparation-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetEstimationPreparationBean bean = new AccountBudgetEstimationPreparationBean();
        bean.setTempDate(Utility.dateToString(new Date()));
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetEstimationPreparationBean bean, final FormMode formMode) {
        log("AccountBudgetEstimationPreparation-'populateModel' : populate model");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
        final Organisation organisation = UserSession.getCurrent().getOrganisation();
        String defaultFieldFlag = MainetConstants.CommonConstant.BLANK;
        String defaultFunctionFlag = MainetConstants.CommonConstant.BLANK;
        final Organisation superUserOrganization = ApplicationSession.getInstance().getSuperUserOrganization();
        // final int langId = UserSession.getCurrent().getLanguageId();
        final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
         Long defaultOrgId = null;
        if (isDafaultOrgExist) {
            defaultOrgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
        } else {
            defaultOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        }
        final List<TbAcCodingstructureMas> tbCodingList = tbAcCodingstructureMasService.findAllWithOrgId(defaultOrgId);

        
        final List<LookUp> bugTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (bugTypelevelMap != null) {
            for (final LookUp lookUp : bugTypelevelMap) {
                if (lookUp != null) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.REV_CPD_VALUE)
                            || lookUp.getLookUpCode().equals(PrefixConstants.EXP_CPD_VALUE)) {
                        model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.LEVELS_MAP, bugTypelevelMap);
                        model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_TYPE_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }

        final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.BUG_SUB_PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (bugSubTypelevelMap != null) {
            for (final LookUp lookUp : bugSubTypelevelMap) {
                if (lookUp != null) {
                    if (lookUp.getLookUpCode().equals(PrefixConstants.REV_SUB_CPD_VALUE)
                            || lookUp.getLookUpCode().equals(PrefixConstants.EXP_SUB_CPD_VALUE)) {
                        model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUG_SUB_TYPE_LEVEL_MAP,
                                bugSubTypelevelMap);
                        model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.BUDGET_SUBTYPE_STATUS,
                                MainetConstants.MASTER.Y);
                    }
                }
            }
        }

        final Map<Long, String> deptMap = new HashMap<>(0);
        List<Object[]> department = null;
        department = departmentService.getAllDeptTypeNames();
        
        if(CollectionUtils.isNotEmpty(department)) {
			if (getSelfDeparmentConfiguration() != null
					&& MainetConstants.Y_FLAG.equalsIgnoreCase(getSelfDeparmentConfiguration())) {
				department = populateDepartmentBasedOnConfiguration(department);
			}
         }
        
        for (final Object[] dep : department) {
            if (dep[0] != null) {
                deptMap.put((Long) (dep[0]), (String) dep[1]);
            }
        }
        
        model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.DEPT_MAP, deptMap);

        final Map<Long, String> employeeMap = new HashMap<>(0);
        List<Object[]> employee = null;
        employee = employeeService.getAllEmployeeNames(orgId);
        for (final Object[] emp : employee) {
            if (emp[0] != null) {
                employeeMap.put((Long) (emp[0]), (String) emp[1]);
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.EMPLOYEE_MAP, employeeMap);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));

        if (formMode == FormMode.CREATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
            model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.FINANCE_MAP, financeMap);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
            model.addAttribute(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
        
        if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {
            final LookUp cmdFieldPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.FIELD_CPD_VALUE, PrefixConstants.CMD,
                    UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            if ((tbCodingList != null) && !tbCodingList.isEmpty()) {
                for (final TbAcCodingstructureMas master : tbCodingList) {
                    if (cmdFieldPrefix.getLookUpId() == master.getComCpdId()) {
                        defaultFieldFlag = master.getDefineOnflag();
                        if (defaultFieldFlag.equals(MainetConstants.MASTER.Y) && isDafaultOrgExist) {
                            model.addAttribute(
                                    MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                    tbAcFieldMasterService.getFieldMasterStatusLastLevels(defaultOrgId,
                                            superUserOrganization, langId));
                        } else {
                            model.addAttribute(
                                    MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                    tbAcFieldMasterService.getFieldMasterStatusLastLevels(orgId, organisation,
                                            langId));
                        }
                    }
                }
            }
        }
        
        
        if (formMode.equals(FormMode.CREATE) || formMode.equals(FormMode.UPDATE)) {
        	boolean fundDefaultFlag = false;
    		if (isDafaultOrgExist) {
    			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
    					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
    		} else {
    			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
    					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
    		}
            final LookUp cmdFieldPrefix = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    PrefixConstants.FUND_CPD_VALUE, PrefixConstants.CMD,
                    UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
            Boolean isAplicable=false;
            if(cmdFieldPrefix!=null) {
            Organisation defultorg = null;
    		Long defultorgId = null;
    		if (isDafaultOrgExist && fundDefaultFlag) {
    			defultorg = ApplicationSession.getInstance().getSuperUserOrganization();
    			defultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
    		} else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
    			defultorg = UserSession.getCurrent().getOrganisation();
    			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
    		} else {
    			defultorg = UserSession.getCurrent().getOrganisation();
    			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
    		}
            List<AccountFundMasterBean> fundList = tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
            		cmdFieldPrefix.getLookUpId(), UserSession.getCurrent().getLanguageId());  
            isAplicable=true;
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FUND_LISI, fundList);
            model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.IS_FUND_APPLICABLE,isAplicable);
        }
        }
    }

    private List<Object[]> populateDepartmentBasedOnConfiguration(List<Object[]> department){
 		Long deptId = UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid();
 		String deptCode = departmentService.getDeptCode(deptId);
 		//other than Account and Audit Department Employee should not have the filter
 		if (!deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AC.getValue()) && !deptCode.equalsIgnoreCase(MainetConstants.AccountConstants.AD.getValue()))
 			department = department.stream().filter(obj -> obj[0] == deptId).collect(Collectors.toList());
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
    
    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetEstimationPreparationBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetEstimationPreparation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetEstimationPreparation.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetEstimationPreparation.setLangId(userSession.getLanguageId());
            tbAcBudgetEstimationPreparation.setCreatedBy(userSession.getEmployee().getEmpId());
            tbAcBudgetEstimationPreparation.setCreatedDate(new Date());
            tbAcBudgetEstimationPreparation.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.CREATE);
            AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparationCreated =new AccountBudgetEstimationPreparationBean();
            try {
            	tbAcBudgetEstimationPreparationCreated = accountBudgetEstimationPreparationService
                         .saveBudgetEstimationPreparationFormData(tbAcBudgetEstimationPreparation, languageId, org);
            }catch (FrameworkException e) {
            	
            	bindingResult.addError(new ObjectError("error", "Next Finanacial Year is Not Exist."));
            	tbAcBudgetEstimationPreparationCreated.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
                model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
				 result = JSP_FORM;
			}
       
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetEstimationPreparationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetEstimationPreparation.getBugestId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                		 ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            }
            if (tbAcBudgetEstimationPreparation.getBugestId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                        ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetEstimationPreparation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }
    @RequestMapping(params = "createForBulkEdit", method = RequestMethod.POST)
    public String createForBulkEdit(final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws Exception {
        log("AccountBudgetEstimationPreparationBean-'create' : 'create'");
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetEstimationPreparation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            tbAcBudgetEstimationPreparation.setOrgid(userSession.getOrganisation().getOrgid());
            tbAcBudgetEstimationPreparation.setLangId(userSession.getLanguageId());
            tbAcBudgetEstimationPreparation.setCreatedBy(userSession.getEmployee().getEmpId());
            tbAcBudgetEstimationPreparation.setCreatedDate(new Date());
            tbAcBudgetEstimationPreparation.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.CREATE);
            AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparationCreated = accountBudgetEstimationPreparationService
                    .saveBudgetEstimationPreparationFormDataBulkEdit(tbAcBudgetEstimationPreparation, languageId, org);
            if (tbAcBudgetEstimationPreparationCreated == null) {
                tbAcBudgetEstimationPreparationCreated = new AccountBudgetEstimationPreparationBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetEstimationPreparationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (tbAcBudgetEstimationPreparation.getBugestId() == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                		ApplicationSession.getInstance().getMessage("accounts.fieldmaster.success"));
            }
            if (tbAcBudgetEstimationPreparation.getBugestId() != null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                		 ApplicationSession.getInstance().getMessage("accounts.fieldmaster.update"));
            }
            result = JSP_FORM;
        } else {
            tbAcBudgetEstimationPreparation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }


    @RequestMapping(params = "formforupdate", method = RequestMethod.POST)
    public String formforupdate(AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            @RequestParam("bugestId") final Long bugestId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws ParseException {
        log("tbAcBudgetEstimationPreparation-'gridData' : 'formforupdate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());

        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.EDIT)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            tbAcBudgetEstimationPreparation.setBugestId(bugestId);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            
            //final Long faYearid = tbAcBudgetEstimationPreparation.getFaYearid();
            tbAcBudgetEstimationPreparation = accountBudgetEstimationPreparationService
                    .getDetailsUsingEstimationPreparationId(tbAcBudgetEstimationPreparation, languageId, org, orgId);
            if (tbAcBudgetEstimationPreparation.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetEstimationPreparation.getCpdBugtypeId();
                final Long dpDeptid = tbAcBudgetEstimationPreparation.getDpDeptid();
                if (dpDeptid != null && cpdBugtypeid != null) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.BUDGET_REV_PERCENT, revenueLookup.getOtherField());
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.BUDGET_EXP_PERCENT, expLookup.getOtherField());
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            if (tbAcBudgetEstimationPreparation.getNextFaYearid() != null) {
                tbAcBudgetEstimationPreparation.setNextFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetEstimationPreparation.getNextFaYearid()));
            }
            if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                    && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
                final List<AccountBudgetProjectedRevenueEntryBean> listOfAllFinacialYearDates = accountBudgetProjectedRevenueEntryService
                        .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
            }
            if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                    && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
                final List<AccountBudgetProjectedExpenditureBean> listOfAllFinacialYearDates = accountBudgetProjectedExpenditureService
                        .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetEstimationPreparation);
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetEstimationPreparation 'formforupdate' : update done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetEstimationPreparation 'formforupdate' : update done - redirect");
            result = JSP_FORM;
        } else {
            log("tbAcBudgetEstimationPreparation 'formforupdate' : binding errors");
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
            @RequestParam("bugestId") final Long bugestId,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws ParseException {
        log("tbAcBudgetEstimationPreparation-'gridData' : 'view'");
        String result = MainetConstants.CommonConstant.BLANK;
        
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        
        if (!bindingResult.hasErrors()) {
            if (viewmode.equals(MainetConstants.VIEW)) {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
            } else {
                model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
            }
            setModeView(viewmode);
            final UserSession userSession = UserSession.getCurrent();
            final int languageId = userSession.getLanguageId();
            final Organisation org = UserSession.getCurrent().getOrganisation();
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            tbAcBudgetEstimationPreparation.setBugestId(bugestId);
            tbAcBudgetEstimationPreparation = accountBudgetEstimationPreparationService
                    .getDetailsUsingEstimationPreparationId(tbAcBudgetEstimationPreparation, languageId, org, orgId);
            if (tbAcBudgetEstimationPreparation.getFaYearid() != null) {
                tbAcBudgetEstimationPreparation.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetEstimationPreparation.getFaYearid()));
            }
            if (tbAcBudgetEstimationPreparation.getNextFaYearid() != null) {
                tbAcBudgetEstimationPreparation.setNextFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetEstimationPreparation.getNextFaYearid()));
            }
            if (tbAcBudgetEstimationPreparation.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetEstimationPreparation.getCpdBugtypeId();
                final Long dpDeptid = tbAcBudgetEstimationPreparation.getDpDeptid();
                if (dpDeptid != null && cpdBugtypeid != null) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                    }
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCode);
                    }
                }
            }
            if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                    && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
                final List<AccountBudgetProjectedRevenueEntryBean> listOfAllFinacialYearDates = accountBudgetProjectedRevenueEntryService
                        .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
            }
            if ((tbAcBudgetEstimationPreparation.getFaYearid() != null && tbAcBudgetEstimationPreparation.getCpdBugtypeId() != null)
                    && tbAcBudgetEstimationPreparation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
                final List<AccountBudgetProjectedExpenditureBean> listOfAllFinacialYearDates = accountBudgetProjectedExpenditureService
                        .getListOfAllFinacialYearDates(orgId, tbAcBudgetEstimationPreparation.getFaYearid());
                model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_PAC_SAC_HEAD_LIST, listOfAllFinacialYearDates);
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetEstimationPreparation);
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetEstimationPreparation 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetEstimationPreparation 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("tbAcBudgetEstimationPreparation 'view' : binding errors");
            populateModel(model, tbAcBudgetEstimationPreparation, FormMode.UPDATE);
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
