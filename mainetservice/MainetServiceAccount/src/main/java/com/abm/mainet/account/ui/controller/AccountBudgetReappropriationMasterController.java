
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetReappropriationMasterBean;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.AccountBudgetReappropriationMasterService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ILocationMasService;
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
@RequestMapping("/AccountBudgetReappropriation.html")
public class AccountBudgetReappropriationMasterController extends AbstractController {

    private static final String MAIN_ENTITY_NAME = "tbAcBudgetReappropriation";
    private static final String MAIN_LIST_NAME = "list";
    private static final String JSP_FORM = "tbAcBudgetReappropriation/form";
    private static final String JSP_VIEW = "tbAcBudgetReappropriation/view";
    private static final String JSP_LIST = "tbAcBudgetReappropriation/list";
    private static final String AC_Financialyr_LOOKUPS = "aFinancialYr";
    private String modeView = MainetConstants.BLANK;
    @Resource
    private AccountBudgetReappropriationMasterService accountBudgetReappropriationMasterService;
    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;
    @Resource
    private AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private TbFinancialyearService tbFinancialyearService;
    @Autowired
    private DepartmentService departmentService;
    @Resource
    private BudgetCodeService accountBudgetCodeService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private ILocationMasService locMasService;
    
    @Resource
    private TbAcCodingstructureMasService tbAcCodingstructureMasService;
    @Resource
    private TbOrganisationService tbOrganisationService;
    @Resource
    private AccountFieldMasterService tbAcFieldMasterService;
    
    @Autowired
	private IFileUploadService fileUpload;
    @Resource
	private IAttachDocsService attachDocsService;
    @Autowired
	private IFileUploadService accountFileUpload;

    private List<AccountBudgetReappropriationMasterBean> chList = null;

    public AccountBudgetReappropriationMasterController() {
        super(AccountBudgetReappropriationMasterController.class, MAIN_ENTITY_NAME);
        log("AccountReappropriationMasterController created.");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
            final Model model) {
        log("AccountReappropriationMaster-'gridData' : 'Get grid Data'");
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
    public @ResponseBody List<?> getCheqData(final HttpServletRequest request, final Model model,
            @RequestParam("faYearid") final Long faYearid,
            @RequestParam("cpdBugtypeId") final Long cpdBugtypeId, @RequestParam("dpDeptid") final Long dpDeptid,
            @RequestParam("prBudgetCodeid") final Long prBudgetCodeid,@RequestParam("fieldId") final Long fieldId) {
        log("AccountReappropriationMaster-'getjqGridsearch' : 'get jqGrid search data'");
        chList = new ArrayList<>();
        chList.clear();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String budgIdentifyFlag = MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG;
        chList = accountBudgetReappropriationMasterService.findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid,
                budgIdentifyFlag,fieldId, orgId);
        String amount = null;
        String amount1 = null;
        String amount2 = null;
        for (final AccountBudgetReappropriationMasterBean bean : chList) {
            final Long prBudgetCodeId = bean.getPrBudgetCodeid();
            final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeId, orgId);
            bean.setPrBudgetCode(budgetCode);

            if (bean.getOrgRevBalamt() != null) {
                amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getOrgRevBalamt());
                bean.setFormattedCurrency(amount);
            }
            if (bean.getTransferAmount() != null) {
                amount1 = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
                bean.setFormattedCurrency1(amount1);
            }
            if (bean.getNewOrgRevAmount() != null) {
                amount2 = CommonMasterUtility.getAmountInIndianCurrency(bean.getNewOrgRevAmount());
                bean.setFormattedCurrency2(amount2);
            }
            if ((bean.getAuthFlag() != null) && !bean.getAuthFlag().isEmpty()) {
                final String status1 = bean.getAuthFlag();
                if (status1.equals(MainetConstants.MENU.Y)) {
                    bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
                }
                if (status1.equals(MainetConstants.MENU.N)) {
                    bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
                }
            }
        }
        return chList;
    }

    @RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
    public String loadBudgetReappropriationData(final AccountBudgetReappropriationMasterBean bean, final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult) {
        log("AccountReappropriationMaster-'getjqGridsearch' : 'get jqGridload data'");
        String result = MainetConstants.CommonConstant.BLANK;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        LookUp lookupl =null;
        try {
          lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,PrefixConstants.BCE, UserSession.getCurrent().getOrganisation());
         if(lookupl.getOtherField().equals(MainetConstants.Y_FLAG)) 
        	 model.addAttribute("isCrossReapropriation",MainetConstants.Y_FLAG);
         else
        	 model.addAttribute("isCrossReapropriation",MainetConstants.N_FLAG);
        }catch(Exception e) {
        	lookupl=null;
        	 model.addAttribute("isCrossReapropriation",MainetConstants.N_FLAG);
        }
        final Long cpdBugtypeId = bean.getCpdBugtypeId();
        bean.getCpdBugSubTypeId();

        final Map<Long, String> deptMap = new LinkedHashMap<>(0);
        if (cpdBugtypeId != null) {

            if (revenueLookup.getLookUpId() == cpdBugtypeId) {
                List<Object[]> department = null;
                department = accountBudgetProjectedRevenueEntryService.getAllDepartmentIdsData(orgId);
                for (final Object[] dep : department) {
                    if (dep[0] != null) {
                        deptMap.put((Long) (dep[0]), (String) dep[1]);
                    }
                }
            }
            if (expLookup.getLookUpId() == cpdBugtypeId) {
                List<Object[]> department = null;
                department = accountBudgetProjectedExpenditureService.getBudgetProjExpDeptIds(orgId);
                for (final Object[] dep : department) {
                    if (dep[0] != null) {
                        deptMap.put((Long) (dep[0]), (String) dep[1]);
                    }
                }
            }
        }
        model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.DEPT_MAP, deptMap);
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        result = JSP_FORM;
        return result;
    }

    @RequestMapping(params = "getAllBudgetHeadDesc", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getAllBudgetHeadDesc(final AccountBudgetReappropriationMasterBean bean,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult,
            @RequestParam("cnt") final int cnt) {
        log("AccountReappropriationMaster-'getAllBudgetHeadDesc' : 'get getAllBudgetHeadDesc data'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        LookUp lookupl=null;
        try {
         lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,
        		PrefixConstants.BCE, UserSession.getCurrent().getOrganisation());
        }catch(Exception e) {
        	lookupl=null;
        }
        Long budgetCodeId=null;
        if(StringUtils.isNotEmpty(bean.getBugprojRevBeanList1().get(0).getPrRevBudgetCode()))
         budgetCodeId = Long.valueOf(bean.getBugprojRevBeanList1().get(0).getPrRevBudgetCode());
    	     	 
        Map<Long, String> budgetHeadMap = new LinkedHashMap<>();
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getBugprojRevBeanList1().get(cnt).getDpDeptid();
            final Long fieldId = bean.getFieldId();
            if (dpDeptid != null) {
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                	if(bean.getBugprojRevBeanList1().size()>1) {
                	 if(lookupl!=null && (MainetConstants.Y_FLAG.equals(lookupl.getOtherField()))) 
                          budgetHeadMap = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsWithFieldId(orgId,budgetCodeId,fieldId);
                	  else
                          budgetHeadMap = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsFieldId(orgId,fieldId);
                	}else {
                        budgetHeadMap = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsFieldId(orgId,fieldId);
                	}
                }
            }
        }
        return budgetHeadMap;
    }

    @RequestMapping(params = "getAllBudgetHeadExpDesc", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getAllBudgetHeadExpDesc(final AccountBudgetReappropriationMasterBean bean,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult,
            @RequestParam("cont") final int cont) {
        log("AccountReappropriationMaster-'getAllBudgetHeadExpDesc' : 'get getAllBudgetHeadExpDesc data'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
        LookUp lookupl =null;
        try {
          lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,PrefixConstants.BCE, UserSession.getCurrent().getOrganisation());
        }catch(Exception e) {
        	lookupl=null;
        }
        Long budgetCodeId=null;
        if(StringUtils.isNotEmpty(bean.getBugprojExpBeanList1().get(0).getPrExpBudgetCode()))
         budgetCodeId = Long.valueOf(bean.getBugprojExpBeanList1().get(0).getPrExpBudgetCode().replace(",",""));
        
        Map<Long, String> budgetHeadExpMap = new LinkedHashMap<>();
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getBugprojExpBeanList1().get(cont).getDpDeptid();
            final Long fieldId = bean.getFieldId();
            if (dpDeptid != null) {
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                	if(bean.getBugprojExpBeanList1().size()>1) {
                	 if(lookupl!=null && (MainetConstants.Y_FLAG.equals(lookupl.getOtherField())))
                	  budgetHeadExpMap = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsByBudgetCodeIDFieldId(orgId, budgetCodeId,fieldId);
                	 else 
                      budgetHeadExpMap = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsFieldId(orgId,fieldId);
                	}else {
                        budgetHeadExpMap = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsFieldId(orgId,fieldId);
                	}
                }
            }
        }
        return budgetHeadExpMap;
    }

    @RequestMapping(params = "getAllBudgetHeadDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getAllBudgetHeadDetails(final AccountBudgetReappropriationMasterBean bean,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult) {
        log("AccountReappropriationMaster-'getAllBudgetHeadDetails' : 'get getAllBudgetHead Details'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
    	LookUp lookupl =null;
         try {
           lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,PrefixConstants.BCE, UserSession.getCurrent().getOrganisation()); 
         }catch(Exception e) {
        	 lookupl=null;
         }
        Map<Long, String> budgetHeadDescMap = new LinkedHashMap<>();
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getBugprojRevBeanList().get(0).getDpDeptid();
            final Long fieldId = bean.getFieldId();
            if (dpDeptid != null) {
                if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                  if(lookupl!=null && (MainetConstants.Y_FLAG.equals(lookupl.getOtherField()))) 
                     budgetHeadDescMap = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsWithFieldId(orgId,Long.valueOf(bean.getBugprojRevBeanList1().get(0).getPrRevBudgetCode()),fieldId);
                	else 
                	budgetHeadDescMap = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsFieldId(orgId,fieldId);
                }
            }
        }
        return budgetHeadDescMap;
    }

    @RequestMapping(params = "getAllBudgetHeadExpDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getAllBudgetHeadExpDetails(final AccountBudgetReappropriationMasterBean bean,
            final Model model,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request, final BindingResult bindingResult) {
        log("AccountReappropriationMaster-'getAllBudgetHeadExpDetails' : 'get getAllBudgetHeadExp Details'");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
    	LookUp lookupl =null;
        try {
        	lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,PrefixConstants.BCE, UserSession.getCurrent().getOrganisation());
        }catch(Exception e) {
        	lookupl=null;
        }
        Map<Long, String> budgetHeadExpDescMap = new LinkedHashMap<>();
        if (bean.getFaYearid() != null) {
            final Long cpdBugtypeid = bean.getCpdBugtypeId();
            final Long dpDeptid = bean.getBugprojExpBeanList().get(0).getDpDeptid();
            final Long fieldId = bean.getFieldId();
            if (dpDeptid != null) {
                if (expLookup.getLookUpId() == cpdBugtypeid) {
                	if(lookupl!=null && (MainetConstants.Y_FLAG.equals(lookupl.getOtherField())))
                		budgetHeadExpDescMap=accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsByBudgetCodeIDFieldId(orgId,Long.valueOf(bean.getBugprojExpBeanList1().get(0).getPrExpBudgetCode().replace(",","")),fieldId);
                	else	
                        budgetHeadExpDescMap = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsFieldId(orgId,fieldId);
                }
            }
        }
        return budgetHeadExpDescMap;
    }

    @RequestMapping(params = "getOrgBalGridload", method = RequestMethod.POST)
    public @ResponseBody List<String> findBudgetReappropriationAmountData(final AccountBudgetReappropriationMasterBean bean,
            final HttpServletRequest request, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid().longValue();
        final String budgCodeid = bean.getBugprojRevBeanList().get(0).getPrRevBudgetCode();
        final Long deptId = bean.getBugprojRevBeanList().get(0).getDpDeptid();
        final List<String> arrRevList = new ArrayList<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        String collectedAmount = null;
        //collectedAmount = accountBudgetProjectedRevenueEntryService.getAllCollectedAmount(faYearid, Long.valueOf(budgCodeid),
         //       orgId);
        collectedAmount = accountBudgetProjectedRevenueEntryService.getAllCollectedAmountByBasedOnDept(faYearid, Long.valueOf(budgCodeid),
                orgId,deptId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
             if(objects[4]==deptId) {	
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    arrRevList.add(originalEstAmount.toString());
                    if (collectedAmount == null || collectedAmount.isEmpty()) {
                        arrRevList.add(originalEstAmount.toString());
                    } else {
                        BigDecimal revAmt = new BigDecimal(collectedAmount.toString());
                        revAmt = revAmt.setScale(2, RoundingMode.CEILING);
                        arrRevList.add(originalEstAmount.subtract(revAmt).toString());
                    }
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    arrRevList.add(revisedEsmtAmount.toString());
                    if (collectedAmount == null || collectedAmount.isEmpty()) {
                        arrRevList.add(revisedEsmtAmount.toString());
                    } else {
                        BigDecimal revAmt = new BigDecimal(collectedAmount.toString());
                        revAmt = revAmt.setScale(2, RoundingMode.CEILING);
                        arrRevList.add(revisedEsmtAmount.subtract(revAmt).toString());
                    }
                }
            }
        }
      }      
        return arrRevList;
    }

    @RequestMapping(params = "getOrgBalGridload1", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetReappropriationAmountData1(
            final AccountBudgetReappropriationMasterBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cnt") final int cnt) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid().longValue();
        final String budgCodeid = bean.getBugprojRevBeanList1().get(cnt).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long deptId = bean.getBugprojRevBeanList1().get(cnt).getDpDeptid();
        final Map<String, String> opBalMap1 = new HashMap<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        String collectedAmount = null;
        //collectedAmount = accountBudgetProjectedRevenueEntryService.getAllCollectedAmount(faYearid, Long.valueOf(budgCodeid),
         //       orgId);
        collectedAmount = accountBudgetProjectedRevenueEntryService.getAllCollectedAmountByBasedOnDept(faYearid, Long.valueOf(budgCodeid),
                orgId,deptId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
              if(objects[4]==deptId) {	
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_ORIGINAL_ESTIMATE,
                            originalEstAmount.toString());
                    if (collectedAmount == null || collectedAmount.isEmpty()) {
                        opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
                                originalEstAmount.toString());
                    } else {
                        BigDecimal revAmt = new BigDecimal(collectedAmount.toString());
                        revAmt = revAmt.setScale(2, RoundingMode.CEILING);
                        opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
                                originalEstAmount.subtract(revAmt).toString());
                    }
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_ORIGINAL_ESTIMATE,
                            revisedEsmtAmount.toString());
                    if (collectedAmount == null || collectedAmount.isEmpty()) {
                        opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
                                revisedEsmtAmount.toString());
                    } else {
                        BigDecimal revAmt = new BigDecimal(collectedAmount.toString());
                        revAmt = revAmt.setScale(2, RoundingMode.CEILING);
                        opBalMap1.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_REV_BALANCE_PROVISION,
                                revisedEsmtAmount.subtract(revAmt).toString());
                    }
                }
            }
        }
      }     
        return opBalMap1;
    }

    @RequestMapping(params = "getOrgBalExpGridload", method = RequestMethod.POST)
    public @ResponseBody List<String> findBudgetReappropriationExpAmountData(final AccountBudgetReappropriationMasterBean bean,
            final HttpServletRequest request, final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String budgCodeid = bean.getBugprojExpBeanList().get(0).getPrExpBudgetCode();
        final Long deptId = bean.getBugprojExpBeanList().get(0).getDpDeptid();
        final List<String> arrExpList = new ArrayList<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        String expenditureAmount = null;
       //expenditureAmount = accountBudgetProjectedExpenditureService
       //.getAllExpenditureAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        expenditureAmount = accountBudgetProjectedExpenditureService
                .getAllExpenditureAmountByDeptId(faYearid, Long.valueOf(budgCodeid), orgId,deptId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
              if(objects[4]==deptId) {	
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    arrExpList.add(originalEstAmount.toString());
                    if (expenditureAmount == null || expenditureAmount.isEmpty()) {
                        arrExpList.add(originalEstAmount.toString());
                    } else {
                        BigDecimal expAmt = new BigDecimal(expenditureAmount.toString());
                        expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                        arrExpList.add(originalEstAmount.subtract(expAmt).toString());
                    }
                } else {
                    BigDecimal revEsmtAmt = new BigDecimal(objects[0].toString());
                    revEsmtAmt = revEsmtAmt.setScale(2, RoundingMode.CEILING);
                    arrExpList.add(revEsmtAmt.toString());
                    if (expenditureAmount == null || expenditureAmount.isEmpty()) {
                        arrExpList.add(revEsmtAmt.toString());
                    } else {
                        BigDecimal expAmt = new BigDecimal(expenditureAmount.toString());
                        expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                        arrExpList.add(revEsmtAmt.subtract(expAmt).toString());
                    }
                }
            }
        }
        }      
        return arrExpList;
    }

    @RequestMapping(params = "getOrgBalExpGridload1", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> findBudgetReappropriationExpAmountData1(
            final AccountBudgetReappropriationMasterBean bean,
            final HttpServletRequest request, final Model model, @RequestParam("cont") final int cont) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid().longValue();
        final String budgCodeid = bean.getBugprojExpBeanList1().get(cont).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long deptId = bean.getBugprojExpBeanList1().get(cont).getDpDeptid();
        final Map<String, String> opBalMap3 = new HashMap<>();
        final List<Object[]> OrgEsmtAmount = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(budgCodeid), orgId);
        String expenditureAmount = null;
        //expenditureAmount = accountBudgetProjectedExpenditureService
          //      .getAllExpenditureAmount(faYearid, Long.valueOf(budgCodeid), orgId);
        expenditureAmount = accountBudgetProjectedExpenditureService
                .getAllExpenditureAmountByDeptId(faYearid, Long.valueOf(budgCodeid), orgId,deptId);
        if (!OrgEsmtAmount.isEmpty()) {
            for (final Object[] objects : OrgEsmtAmount) {
              if(objects[4]==deptId) {	
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
                            originalEstAmount.toString());
                    if (expenditureAmount == null || expenditureAmount.isEmpty()) {
                        opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                                originalEstAmount.toString());
                    } else {
                        BigDecimal expAmt = new BigDecimal(expenditureAmount.toString());
                        expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                        opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                                originalEstAmount.subtract(expAmt).toString());
                    }
                } else {
                    BigDecimal revEsmtAmt = new BigDecimal(objects[0].toString());
                    revEsmtAmt = revEsmtAmt.setScale(2, RoundingMode.CEILING);
                    opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_ORIGINAL_ESTIMATE,
                            revEsmtAmt.toString());
                    if (expenditureAmount == null || expenditureAmount.isEmpty()) {
                        opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                                revEsmtAmt.toString());
                    } else {
                        BigDecimal expAmt = new BigDecimal(expenditureAmount.toString());
                        expAmt = expAmt.setScale(2, RoundingMode.CEILING);
                        opBalMap3.put(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_EXP_BALANCE_PROVISION,
                                revEsmtAmt.subtract(expAmt).toString());
                    }
                }
            }
        }
      }      
        return opBalMap3;
    }

    @RequestMapping(params = "getReappRevPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappRevenueAllocationPrimaryIdData(
            final AccountBudgetReappropriationMasterBean bean, final HttpServletRequest request, final Model model) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList().get(0).getPrRevBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(prRevBudgetCode), orgId);
        final Map<Long, String> revBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null) {
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    revBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return revBalMap;
    }

    @RequestMapping(params = "getReappDynamicRevPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappDynamicRevenueAllocationPrimaryIdData(
            final AccountBudgetReappropriationMasterBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cnt") final int cnt) {

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prRevBudgetCode = bean.getBugprojRevBeanList1().get(cnt).getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedRevenueEntryService.findByRenueOrgAmount(faYearid,
                Long.valueOf(prRevBudgetCode), orgId);
        final Map<Long, String> revBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null) {
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    revBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return revBalMap;
    }

    @RequestMapping(params = "getReappExpPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappExpenditureAllocationPrimaryIdData(
            final AccountBudgetReappropriationMasterBean bean, final HttpServletRequest request, final Model model) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList().get(0).getPrExpBudgetCode().replace(
                MainetConstants.operator.COMMA,
                MainetConstants.CommonConstant.BLANK);
        final Long deptId = bean.getBugprojExpBeanList().get(0).getDpDeptid();
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(prExpBudgetCode), orgId);
        final Map<Long, String> expBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null && deptId==objects[4]) {
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    expBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return expBalMap;
    }

    @RequestMapping(params = "getReappDynamicExpPrimarykeyIdDetails", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> findBudgetReappDynamicExpenditureAllocationPrimaryIdData(
            final AccountBudgetReappropriationMasterBean bean, final HttpServletRequest request, final Model model,
            @RequestParam("cont") final int cont) {
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final Long faYearid = bean.getFaYearid();
        final String prExpBudgetCode = bean.getBugprojExpBeanList1().get(cont).getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstant.BLANK);
        final List<Object[]> primarykeyIdDetails = accountBudgetProjectedExpenditureService.findByExpOrgAmount(faYearid,
                Long.valueOf(prExpBudgetCode), orgId);
        final Map<Long, String> expBalMap = new HashMap<>();
        if (!primarykeyIdDetails.isEmpty()) {
            for (final Object[] objects : primarykeyIdDetails) {
                if (objects[3] != null) {
                    final Long PrimaryKeyId = Long.valueOf(objects[3].toString());
                    expBalMap.put(PrimaryKeyId, objects[3].toString());
                }
            }
        }
        return expBalMap;
    }

    @RequestMapping()
    public String getList(final Model model) {
        log("AccountReappropriationMaster-'list' :'list'");
        String result = MainetConstants.CommonConstant.BLANK;
        chList = new ArrayList<>();
        chList.clear();
        final String faYearid = UserSession.getCurrent().getFinYearId();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        final String budgIdentifyFlag = MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BUDGET_IDENTIFY_FLAG;
        if ((faYearid != null) && !faYearid.isEmpty()) {
            chList = accountBudgetReappropriationMasterService.findByFinancialId(Long.valueOf(faYearid), budgIdentifyFlag, orgId);
            String amount = null;
            String amount1 = null;
            String amount2 = null;
            for (final AccountBudgetReappropriationMasterBean bean : chList) {
                final Long prBudgetCodeid = bean.getPrBudgetCodeid();
                final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
                bean.setPrBudgetCode(budgetCode);

                if (bean.getOrgRevBalamt() != null) {
                    amount = CommonMasterUtility.getAmountInIndianCurrency(bean.getOrgRevBalamt());
                    bean.setFormattedCurrency(amount);
                }
                if (bean.getTransferAmount() != null) {
                    amount1 = CommonMasterUtility.getAmountInIndianCurrency(bean.getTransferAmount());
                    bean.setFormattedCurrency1(amount1);
                }
                if (bean.getNewOrgRevAmount() != null) {
                    amount2 = CommonMasterUtility.getAmountInIndianCurrency(bean.getNewOrgRevAmount());
                    bean.setFormattedCurrency2(amount2);
                }
                if ((bean.getAuthFlag() != null) && !bean.getAuthFlag().isEmpty()) {
                    final String status1 = bean.getAuthFlag();
                    if (status1.equals(MainetConstants.MENU.Y)) {
                        bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.APPROVED);
                    }
                    if (status1.equals(MainetConstants.MENU.N)) {
                        bean.setStatus(MainetConstants.AccountBudgetAdditionalSupplemental.UNAPPROVED);
                    }
                }
            }
        }
        final Map<Long, String> acReappMap = new HashMap<>();
        final List<AccountBudgetReappropriationMasterBean> list = accountBudgetReappropriationMasterService
                .findBudgetReappropriationMastersByOrgId(
                        orgId,
                        budgIdentifyFlag);
        for (final AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean : list) {
            final Long prBudgetCodeid = accountBudgetReappropriationMasterBean.getPrBudgetCodeid();
            final String budgetCode = accountBudgetCodeService.getBudgetCode(prBudgetCodeid, orgId);
            acReappMap.put(prBudgetCodeid, budgetCode);
        }
        model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.AC_REAPPROPRIATE_MAP, acReappMap);
        final AccountBudgetReappropriationMasterBean bean = new AccountBudgetReappropriationMasterBean();
        populateModel(model, bean, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, bean);
        model.addAttribute(MAIN_LIST_NAME, chList);
        result = JSP_LIST;
        return result;
    }

    @RequestMapping(params = "form")
    public String formForCreate(final Model model) {
    	fileUpload.sessionCleanUpForFileUpload();
        log("AccountReappropriationMaster-'formForCreate' : 'formForCreate'");
        String result = MainetConstants.CommonConstant.BLANK;
        final AccountBudgetReappropriationMasterBean bean = new AccountBudgetReappropriationMasterBean();
        bean.setTempDate(Utility.dateToString(new Date()));
        populateModel(model, bean, FormMode.CREATE);
        result = JSP_FORM;
        return result;
    }

    private void populateModel(final Model model, final AccountBudgetReappropriationMasterBean bean, final FormMode formMode) {
        log("AccountReappropriationMaster-'populateModel' : populate model");
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        int langId = UserSession.getCurrent().getLanguageId();
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
        
     // final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
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

        final List<LookUp> fundTypeLevel = CommonMasterUtility.getListLookup(
                MainetConstants.BUDGET_CODE.FUND_FIELD_DEPT_STATUS_PREFIX,
                UserSession.getCurrent().getOrganisation());
        for (final LookUp lookUp : fundTypeLevel) {
            if (MainetConstants.BUDGET_CODE.FIELD_WISE_STATUS.equalsIgnoreCase(lookUp.getLookUpCode())) {
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
                if (formMode.equals(FormMode.VIEW)) {
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
                                            tbAcFieldMasterService.getFieldMasterLastLevels(defaultOrgId));
                                } else {
                                    model.addAttribute(
                                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS,
                                            tbAcFieldMasterService.getFieldMasterLastLevels(orgId));
                                }
                            }
                        }
                    }
                }
                model.addAttribute(MainetConstants.FIELD_MASTER.FIELD_STATUS, MainetConstants.MASTER.Y);
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
        model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.DEPT_MAP_DATA, deptMap);
        model.addAttribute(MAIN_ENTITY_NAME, bean);

        model.addAttribute(AC_Financialyr_LOOKUPS, secondaryheadMasterService.getAllFinincialYear(orgId, langId));

        if (formMode == FormMode.CREATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYearByStatusWise(orgId);
            model.addAttribute(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
            model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
        } else if (formMode == FormMode.UPDATE) {

            final Map<Long, String> financeMap = tbFinancialyearService.getAllFinincialYear();
            model.addAttribute(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.FINANCE_MAP, financeMap);

            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
        }
    }

    @RequestMapping(params = "create", method = RequestMethod.POST)
    public String create(final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
            throws ParseException {
        log("AccountBudgetReappropriationMasterBean-'create' : 'create'");
        final Long projId=tbAcBudgetReappropriation.getPaAdjid();
        String result = MainetConstants.CommonConstant.BLANK;
        if (!bindingResult.hasErrors()) {
            tbAcBudgetReappropriation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.FALSE);
            final UserSession userSession = UserSession.getCurrent();
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetReappropriation.setOrgShortName(Organisation.getOrgShortNm());
            Long workFlowLevel1 = null;
            Long workFlowLevel2 = null;
            Long workFlowLevel3 = null;
            Long workFlowLevel4 = null;
            Long workFlowLevel5 = null;
            if (UserSession.getCurrent().getLoggedLocId() != null) {
                final TbLocationMas locMas = locMasService.findById(UserSession.getCurrent().getLoggedLocId());
                // In workflow, events wise Location Operation WZMapping is required.
                if ((locMas.getLocOperationWZMappingDto() != null) && !locMas.getLocOperationWZMappingDto().isEmpty()) {
                    workFlowLevel1 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel1();
                    workFlowLevel2 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel2();
                    workFlowLevel3 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel3();
                    workFlowLevel4 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel4();
                    workFlowLevel5 = locMas.getLocOperationWZMappingDto().get(0).getCodIdOperLevel5();
                }
            }
            tbAcBudgetReappropriation.setWorkFlowLevel1(workFlowLevel1);
            tbAcBudgetReappropriation.setWorkFlowLevel2(workFlowLevel2);
            tbAcBudgetReappropriation.setWorkFlowLevel3(workFlowLevel3);
            tbAcBudgetReappropriation.setWorkFlowLevel4(workFlowLevel4);
            tbAcBudgetReappropriation.setWorkFlowLevel5(workFlowLevel5);

            if (tbAcBudgetReappropriation.getPaAdjid() == null) {
                tbAcBudgetReappropriation.setLangId((long) userSession.getLanguageId());
                tbAcBudgetReappropriation.setUserId(userSession.getEmployee().getEmpId());
                tbAcBudgetReappropriation.setLmoddate(new Date());
                tbAcBudgetReappropriation.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
            } else {
                tbAcBudgetReappropriation.setUpdatedBy(userSession.getEmployee().getEmpId());
                tbAcBudgetReappropriation.setApprovedBy(userSession.getEmployee().getEmpId());
                tbAcBudgetReappropriation.setUpdatedDate(new Date());
                tbAcBudgetReappropriation.setLgIpMacUpd(Utility.getClientIpAddress(httpServletRequest));
                if (tbAcBudgetReappropriation.getCreatedDate() != null && !tbAcBudgetReappropriation.getCreatedDate().isEmpty()) {
                    tbAcBudgetReappropriation.setLmoddate(Utility.stringToDate(tbAcBudgetReappropriation.getCreatedDate()));
                }
            }

            tbAcBudgetReappropriation.setOrgid(userSession.getOrganisation().getOrgid());
            populateModel(model, tbAcBudgetReappropriation, FormMode.CREATE);
            AccountBudgetReappropriationMasterBean tbAcBudgetReappropriationCreated = accountBudgetReappropriationMasterService
                    .saveBudgetReappropriationFormData(tbAcBudgetReappropriation, LanguageId, Organisation);
            if (tbAcBudgetReappropriationCreated == null) {
                tbAcBudgetReappropriationCreated = new AccountBudgetReappropriationMasterBean();
            }
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappropriationCreated);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            if (projId == null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                		ApplicationSession.getInstance().getMessage("account.save.msg.transaction.no")+tbAcBudgetReappropriationCreated.getBudgetTranRefNo());
            }
            if (tbAcBudgetReappropriation.getUpdatedBy()!= null) {
                model.addAttribute(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                		ApplicationSession.getInstance().getMessage("account.update.msg.transaction.no")+tbAcBudgetReappropriationCreated.getBudgetTranRefNo());
            }
            
            //file upload start
            if (tbAcBudgetReappropriation.getAttachments() != null && tbAcBudgetReappropriation.getAttachments().size() > 0) {
				prepareFileUpload(tbAcBudgetReappropriation);
				
				String documentName = tbAcBudgetReappropriation.getAttachments().get(0).getDocumentName();
				if (documentName != null && !documentName.isEmpty()) {

					FileUploadDTO fileUploadDTO = new FileUploadDTO();
					if (tbAcBudgetReappropriation.getOrgid() != null) {
						fileUploadDTO.setOrgId(tbAcBudgetReappropriation.getOrgid());
					}
					if (tbAcBudgetReappropriation.getUserId() != null) {
						fileUploadDTO.setUserId(tbAcBudgetReappropriation.getUserId());
					}
					fileUploadDTO.setStatus(MainetConstants.FlagA);
					fileUploadDTO.setDepartmentName(MainetConstants.RECEIPT_MASTER.Module);
					final String accountIds = MainetConstants.RECEIPT_MASTER.Module
							+ MainetConstants.operator.FORWARD_SLACE + tbAcBudgetReappropriationCreated.getPaAdjid();
					fileUploadDTO.setIdfId(accountIds);
					boolean fileuploadStatus = accountFileUpload.doMasterFileUpload(tbAcBudgetReappropriation.getAttachments(),
							fileUploadDTO);
					if (!fileuploadStatus) {
						throw new FrameworkException("Invoice upload is failed, do to upload file into filenet path");
					}
				}
			}
            
            List<Long> removeFileById = null;
			String fileId = tbAcBudgetReappropriation.getRemoveFileById();
			if (fileId != null && !fileId.isEmpty()) {
				removeFileById = new ArrayList<>();
				String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
				for (String fields : fileArray) {
					removeFileById.add(Long.valueOf(fields));
				}
			}
			if (removeFileById != null && !removeFileById.isEmpty()) {
				accountBudgetReappropriationMasterService.updateUploadReappropriationDeletedRecords(removeFileById,tbAcBudgetReappropriation.getUpdatedBy());
			}
            //file upload end
            result = JSP_FORM;
        } else {
            tbAcBudgetReappropriation.setHasError(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.TRUE);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
            populateModel(model, tbAcBudgetReappropriation, FormMode.CREATE);
            result = JSP_FORM;
        }
        return result;
    }

    public void prepareFileUpload(AccountBudgetReappropriationMasterBean accountBudgetReappropriationMasterBean) {
		List<DocumentDetailsVO> documentDetailsVOList = accountBudgetReappropriationMasterBean.getAttachments();
		accountBudgetReappropriationMasterBean.setAttachments(accountFileUpload.prepareFileUpload(documentDetailsVOList));
	}
    
    @RequestMapping(params = "update", method = RequestMethod.POST)
    public String update(AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            @RequestParam("paAdjid") final Long paAdjid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) {
    	fileUpload.sessionCleanUpForFileUpload();
        log("tbAcBudgetReappropriation-'gridData' : 'update'");
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
            final UserSession userSession = UserSession.getCurrent();
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetReappropriation.setPaAdjid(paAdjid);
            tbAcBudgetReappropriation = accountBudgetReappropriationMasterService
                    .getDetailsUsingBudgetReappropriationId(tbAcBudgetReappropriation, LanguageId, Organisation);
            if (tbAcBudgetReappropriation.getFaYearid() != null) {
                tbAcBudgetReappropriation.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappropriation.getFaYearid()));
            }
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetReappropriation.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetReappropriation.getCpdBugtypeId();

                final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappropriation
                        .getBugprojRevBeanList1();
                for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
                    accountBudgetProjectedRevenueEntryBean.getDpDeptid();
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsFieldId(orgId,accountBudgetProjectedRevenueEntryBean.getFieldId());
                        accountBudgetProjectedRevenueEntryBean.setBudgetMapDynamic(budgetCode);
                    }
                }
                final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappropriation.getBugprojRevBeanList();
                for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCode = new LinkedHashMap<>();
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeadsFieldId(orgId,accountBudgetProjectedRevenueEntryBean.getFieldId());
                        model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP, budgetCode);
                    }
                }
                final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappropriation
                        .getBugprojExpBeanList1();
                for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
                        budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsFieldId(orgId,accountBudgetProjectedExpenditureBean.getFieldId());
                        accountBudgetProjectedExpenditureBean.setBudgetMapDynamicExp(budgetCodeExp);
                    }
                }
                final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappropriation.getBugprojExpBeanList();
                for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
                        budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeadsFieldId(orgId,accountBudgetProjectedExpenditureBean.getFieldId());
                        model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP,
                                budgetCodeExpStatic);
                    }
                }
            }
            
            final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +paAdjid;
    		final List<AttachDocs> attachDocsList = attachDocsService
    				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
    		tbAcBudgetReappropriation.setAttachDocsList(attachDocsList);
    		
    		
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappropriation);
            loadBudgetReappropriationData(tbAcBudgetReappropriation, model, redirectAttributes, httpServletRequest,
                    bindingResult);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetReappropriation 'update' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetReappropriation 'update' : view done - redirect");
            result = JSP_FORM;
        } else {
            log("tbAcBudgetReappropriation 'update' : binding errors");
            populateModel(model, tbAcBudgetReappropriation, FormMode.UPDATE);
            result = JSP_FORM;
        }
        return result;
    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public String formForView(AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
            @RequestParam("paAdjid") final Long paAdjid,
            @RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
            final BindingResult bindingResult,
            final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) {
    	fileUpload.sessionCleanUpForFileUpload();
        log("tbAcBudgetReappropriation-'gridData' : 'view'");
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
            final int LanguageId = userSession.getLanguageId();
            final Organisation Organisation = UserSession.getCurrent().getOrganisation();
            tbAcBudgetReappropriation.setPaAdjid(paAdjid);
            tbAcBudgetReappropriation = accountBudgetReappropriationMasterService
                    .getDetailsUsingBudgetReappropriationId(tbAcBudgetReappropriation, LanguageId, Organisation);
            if (tbAcBudgetReappropriation.getFaYearid() != null) {
                tbAcBudgetReappropriation.setFinancialYearDesc(
                        tbFinancialyearService.findFinancialYearDesc(tbAcBudgetReappropriation.getFaYearid()));
            }
            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
            if (tbAcBudgetReappropriation.getFaYearid() != null) {
                final Long cpdBugtypeid = tbAcBudgetReappropriation.getCpdBugtypeId();

                final List<AccountBudgetProjectedRevenueEntryBean> revBeanDynamic = tbAcBudgetReappropriation
                        .getBugprojRevBeanList1();
                Map<Long, String> budgetCode = new LinkedHashMap<>();
                for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBeanDynamic) {
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        budgetCode = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                    }
                }
                model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.REV_BUDGET_CODE_MAP, budgetCode);
                final List<AccountBudgetProjectedRevenueEntryBean> revBean = tbAcBudgetReappropriation.getBugprojRevBeanList();
                for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : revBean) {
                    Map<Long, String> budgetCodeStatic = new LinkedHashMap<>();
                    if (revenueLookup.getLookUpId() == cpdBugtypeid) {
                        budgetCodeStatic = accountBudgetProjectedRevenueEntryService.findByAllRevBudgetHeads(orgId);
                    }
                    model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_CODE_MAP, budgetCodeStatic);
                }
                Map<Long, String> budgetCodeExp = new LinkedHashMap<>();
                final List<AccountBudgetProjectedExpenditureBean> expBeanDynamic = tbAcBudgetReappropriation
                        .getBugprojExpBeanList1();
                for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBeanDynamic) {
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        budgetCodeExp = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                    }
                }
                model.addAttribute(MainetConstants.BUDGET_ALLOCATION_MASTER.EXP_BUDGET_CODE_MAP, budgetCodeExp);
                final List<AccountBudgetProjectedExpenditureBean> expBean = tbAcBudgetReappropriation.getBugprojExpBeanList();
                for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : expBean) {
                    Map<Long, String> budgetCodeExpStatic = new LinkedHashMap<>();
                    if (expLookup.getLookUpId() == cpdBugtypeid) {
                        budgetCodeExpStatic = accountBudgetProjectedExpenditureService.findByAllExpBudgetHeads(orgId);
                    }
                    model.addAttribute(MainetConstants.AccountBudgetReappropriationMaster.BUDGET_EXP_MAP, budgetCodeExpStatic);
                }
            }
            
            //file upload start
            final String accountIds = MainetConstants.RECEIPT_MASTER.Module + MainetConstants.operator.FORWARD_SLACE +paAdjid;
    		final List<AttachDocs> attachDocsList = attachDocsService
    				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), accountIds);
    		tbAcBudgetReappropriation.setAttachDocsList(attachDocsList);
    		//file upload end
            model.addAttribute(MAIN_ENTITY_NAME, tbAcBudgetReappropriation);
            loadBudgetReappropriationData(tbAcBudgetReappropriation, model, redirectAttributes, httpServletRequest,
                    bindingResult);
            messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
            log("tbAcBudgetReappropriation 'view' : view done - redirect");
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
            model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
            log("tbAcBudgetReappropriation 'view' : view done - redirect");
            result = JSP_VIEW;
        } else {
            log("tbAcBudgetReappropriation 'view' : binding errors");
            populateModel(model, tbAcBudgetReappropriation, FormMode.UPDATE);
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
    
    @RequestMapping(params = "crossReappropriation", method = RequestMethod.POST)
   public @ResponseBody Boolean  checkCrossReappropriation(final AccountBudgetReappropriationMasterBean tbAcBudgetReappropriation,
           final BindingResult bindingResult, final HttpServletRequest httpServletRequest) {
     try {
     LookUp lookupl = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.BUDGET_REAPPROPRIATION_MASTER.BCR,PrefixConstants.BCE, UserSession.getCurrent().getOrganisation());
     Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
     final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
             PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
     final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
             PrefixConstants.PREFIX, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getOrganisation());
     if(lookupl!=null) {
     if(lookupl.getOtherField().equals(MainetConstants.Y_FLAG)) {
    	  int count=0;
     if(expLookup.getLookUpId()==tbAcBudgetReappropriation.getCpdBugtypeId()) {
         List<AccountBudgetProjectedExpenditureBean> explist1 = tbAcBudgetReappropriation.getBugprojExpBeanList1();
         List<AccountBudgetProjectedExpenditureBean> explist = tbAcBudgetReappropriation.getBugprojExpBeanList();
  	     Long accType1 = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(explist1.get(0).getPrExpBudgetCode().replace(",","")));
  	     Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(explist.get(0).getPrExpBudgetCode().replace(",","")));
    	for(AccountBudgetProjectedExpenditureBean bean:explist1) {
          	 Long accTypeOthers = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(bean.getPrExpBudgetCode().replace(",","")));
              if(!accType1.equals(accTypeOthers) || !accType.equals(accTypeOthers)) {
            	  count++;
              }
    	}
    	 }else if(revenueLookup.getLookUpId()==tbAcBudgetReappropriation.getCpdBugtypeId()) {
    		  List<AccountBudgetProjectedRevenueEntryBean> revList1 = tbAcBudgetReappropriation.getBugprojRevBeanList1();
    		  List<AccountBudgetProjectedRevenueEntryBean> revList = tbAcBudgetReappropriation.getBugprojRevBeanList();
    	  	  Long accType1 = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(revList1.get(0).getPrRevBudgetCode().replace(",","")));
    	  	  Long accType = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(revList.get(0).getPrRevBudgetCode().replace(",","")));
    	  	  for(AccountBudgetProjectedRevenueEntryBean bean :revList1) {
        	  	  Long accTypeothers = accountBudgetCodeService.getBudgetAccTypeByBudgetCodeId(orgId,Long.valueOf(bean.getPrRevBudgetCode().replace(",","")));
        	  	 if(!accType1.equals(accTypeothers) || !accType.equals(accTypeothers)) {
               	  count++;
                 }
              }
    	 }
       if(count>0) {
    	   return true;
       }
    }
    }
     return false;
    }catch(Exception e) {
  	  return false; 
    }
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
