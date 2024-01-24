
package com.abm.mainet.account.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryBean;
import com.abm.mainet.account.dto.AccountJournalVoucherEntryDetailsBean;
import com.abm.mainet.account.dto.AccountVoucherDetailsUploadDTO;
import com.abm.mainet.account.dto.AccountVoucherMasterUploadDTO;
import com.abm.mainet.account.dto.WriteExcelData;
import com.abm.mainet.account.service.AccountJournalVoucherService;
import com.abm.mainet.account.service.BudgetCodeService;
import com.abm.mainet.account.ui.model.AccountJournalVoucherEntryModel;
import com.abm.mainet.account.ui.validator.VoucherEntryValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author deepika.pimpale
 *
 */
@Controller
@RequestMapping(value = { "/AccountVoucherEntry.html", "/AccountVoucherAuthorisation.html" })
public class AccountJournalVoucherEntryController extends AbstractEntryFormController<AccountJournalVoucherEntryModel>
        implements Serializable {

    private static final String ACCOUNT_JOURNAL_VOUCHER_ENTRY_FORM = "AccountJournalVoucherEntryForm";
    private static final String TRANSACTION_TRACKING_VOUCHER = "TransactionTracking/voucherForm";
    private static final String JSP_EXCELUPLOAD = "AccountVocherEntry/ExcelUpload";
    private static final long serialVersionUID = -990552995766059163L;

    private static final String ENTITY = "accountJournalVoucherEntryModel";
    private static final Logger LOGGER = Logger.getLogger(AccountJournalVoucherEntryController.class);

    private static final String ReceiptReportJsp_Form = "VoucherReport";
    private static final String ReceiptReportGrid_Form = "VoucherGridReport";
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private AccountJournalVoucherService journalVoucherService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private ILocationMasService locMasService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;
    @Autowired
    private IFileUploadService fileUpload;
    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
         List<AccountJournalVoucherEntryBean> voucherBean = getModel()
                .getGridVoucherData(UserSession.getCurrent().getOrganisation().getOrgid());
         List<Department> depiList = departmentService.getDepartments(MainetConstants.STATUS.ACTIVE);
        if(CollectionUtils.isNotEmpty(voucherBean)) {
      	  if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
      		voucherBean=voucherBean.stream().map(m->{
      			for(Department d:depiList) {
      				if(m.getDpDeptid()==d.getDpDeptid()) {
      				  LookUp lookup = CommonMasterUtility
                      .getNonHierarchicalLookUpObject(m.getVouTypeCpdId(), UserSession.getCurrent().getOrganisation());
                     if("RV".equalsIgnoreCase(lookup.getLookUpCode())) 
      					m.setVouReferenceNo(d.getDpDeptcode().concat(m.getVouReferenceNo()));
      				}
      				      			}
      			return m;
      		}).collect(Collectors.toList());
            }
        }
        getModel().setSearchData(voucherBean);
        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        }
        return super.index();
    }

    @RequestMapping(params = "getGridData", method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<AccountJournalVoucherEntryBean> loadGridData(
            final HttpServletRequest httpServletRequest, @RequestParam final String page,
            @RequestParam final String rows) {
        getModel().bind(httpServletRequest);
        final List<AccountJournalVoucherEntryBean> voucherBean = getModel().getSearchData();
        for (final AccountJournalVoucherEntryBean bean : voucherBean) {
            if (bean.getVouTypeCpdId() != null) {
                bean.setVoucherDesc(
                        CommonMasterUtility.getCPDDescription(bean.getVouTypeCpdId(), MainetConstants.MODE_EDIT));
            }
        }
        return getModel().paginate(httpServletRequest, page, rows, voucherBean);
    }

    @RequestMapping(params = "searchVoucher", method = RequestMethod.POST)
    public @ResponseBody List searchVoucherData(@RequestParam("voucherType") final Long voucherType,
            @RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
            @RequestParam("dateType") final String dateType, @RequestParam("authStatus") final String authStatus,
            @RequestParam("amount") final BigDecimal amount, @RequestParam("refNo") final String refNo,
            @RequestParam("urlIdentifyFlag") final String urlIdentifyFlag,
            final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        final Date frmdate = Utility.stringToDate(fromDate);
        final Date todate = Utility.stringToDate(toDate);
        List<AccountJournalVoucherEntryBean> voucherBean = getModel().getSearchVoucherData(voucherType, frmdate,
                todate, dateType, authStatus, amount, refNo, UserSession.getCurrent().getOrganisation().getOrgid(),
                urlIdentifyFlag);
        List<Department> depiList = departmentService.getDepartments(MainetConstants.STATUS.ACTIVE);
        if(CollectionUtils.isNotEmpty(voucherBean)) {
      	  if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SKDCL)){
      		voucherBean=voucherBean.stream().map(m->{
      			for(Department d:depiList) {
      				if(m.getDpDeptid()==d.getDpDeptid()) {
      				  LookUp lookup = CommonMasterUtility
                      .getNonHierarchicalLookUpObject(m.getVouTypeCpdId(), UserSession.getCurrent().getOrganisation());
                     if("RV".equalsIgnoreCase(lookup.getLookUpCode())) 
      					m.setVouReferenceNo(d.getDpDeptcode().concat(m.getVouReferenceNo()));
      				}
      			}
      			return m;
      		}).collect(Collectors.toList());
            }
        }
        getModel().setSearchData(voucherBean);

        return voucherBean;
    }

    @RequestMapping(params = "createformFromDeposit", method = RequestMethod.POST)
    public ModelAndView formForCreateFromDeposit(@RequestParam("depId") final Long depId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().depCreateForm(depId);
        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        }
        getModel().setMode(MainetConstants.APPLICABLE);
        getModel().setServiceFlag(MainetConstants.MENU.Y);
        UserSession.getCurrent().getOrganisation().getOrgid();
        final List<LookUp> voucherSubTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                UserSession.getCurrent().getOrganisation());
        getModel().setVoucherSubTypeList(voucherSubTypeList);
        final ModelAndView mv = new ModelAndView(ACCOUNT_JOURNAL_VOUCHER_ENTRY_FORM, MainetConstants.FORM_NAME,
                getModel());
        return mv;
    }

    @Override
    @RequestMapping(params = "addForm", method = RequestMethod.POST)
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        getModel().addForm(UserSession.getCurrent().getOrganisation().getOrgid());
        getModel().setMode(MainetConstants.APPLICABLE);
        UserSession.getCurrent().getOrganisation().getOrgid();
        final List<LookUp> voucherSubTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                UserSession.getCurrent().getOrganisation());
        getModel().setVoucherSubTypeList(voucherSubTypeList);
        final ModelAndView mv = new ModelAndView(ACCOUNT_JOURNAL_VOUCHER_ENTRY_FORM, MainetConstants.FORM_NAME,
                getModel());
        return mv;
    }

    @RequestMapping(params = "saveJournalForm", method = RequestMethod.POST)
    public ModelAndView saveJournalVoucherForm(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
        getModel().getEntity().setLgIpMac(ipMacAddress);
        if (getModel().saveForm()) {
            if ((getModel().getEntity().getAuthoFlg() != null)
                    && MainetConstants.MENU.Y.equals(getModel().getEntity().getAuthoFlg())) {
                httpServletRequest.getSession().setAttribute(MainetConstants.AccountJournalVoucherEntry.VOUCHER_LIST,
                        getModel().getoAccountJournalVoucherEntryBean());
                return jsonResult(JsonViewObject.successResult(getApplicationSession()
                        .getMessage("day.book.report.voucherno") + getModel().getEntity().getVouNo() + getApplicationSession()
                        .getMessage("account.journal.voucher.record.voucher.success")));
            } else {
                httpServletRequest.getSession().setAttribute(MainetConstants.AccountJournalVoucherEntry.VOUCHER_LIST,
                        getModel().getoAccountJournalVoucherEntryBean());
                if (MainetConstants.APPLICABLE.equals(getModel().getMode())) {
                    return jsonResult(JsonViewObject.successResult(
                            getApplicationSession().getMessage("account.journal.voucher.record.success")));
                } else if(MainetConstants.FlagD.equals(getModel().getEntity().getAuthoFlg())){
                    return jsonResult(JsonViewObject.successResult(
                            getApplicationSession().getMessage("account.journal.voucher.record.reject")));
                }else {
                	return jsonResult(JsonViewObject.successResult(
                            getApplicationSession().getMessage("account.journal.voucher.record.update")));
                }

            }
        }
        return defaultMyResult();
    }

    @RequestMapping(params = "update", method = RequestMethod.POST)
    public ModelAndView editVoucherForm(@RequestParam("vouId") final Long vouId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().editForm(vouId);
        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        } else {
            getModel().setModeView(MainetConstants.MODE_EDIT);
        }
        UserSession.getCurrent().getOrganisation().getOrgid();
        final List<LookUp> voucherSubTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                UserSession.getCurrent().getOrganisation());
        getModel().setVoucherSubTypeList(voucherSubTypeList);

        final ModelAndView mv = new ModelAndView(ACCOUNT_JOURNAL_VOUCHER_ENTRY_FORM, MainetConstants.FORM_NAME,
                getModel());
        return mv;

    }

    @RequestMapping(params = "formForView", method = RequestMethod.POST)
    public ModelAndView viewVoucherForm(@RequestParam("vouId") final Long vouId,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().viewForm(vouId);

        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        } else {
            getModel().setModeView(MainetConstants.MODE_VIEW);
        }
        UserSession.getCurrent().getOrganisation().getOrgid();

        final List<LookUp> voucherSubTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                UserSession.getCurrent().getOrganisation());

        getModel().setVoucherSubTypeList(voucherSubTypeList);

        final ModelAndView mv = new ModelAndView(ACCOUNT_JOURNAL_VOUCHER_ENTRY_FORM, MainetConstants.FORM_NAME,
                getModel());
        return mv;

    }

    @RequestMapping(params = "removeObject", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody boolean removeObject1(@RequestParam("voutId") final Long voutId,
            final HttpServletRequest httpServletRequest) {
        for (final AccountJournalVoucherEntryDetailsBean detail : getModel().getEntity().getDetails()) {
            if (detail.getVoudetId() == voutId) {
                detail.setDeleted(MainetConstants.MENU.D);
            }
        }
        return true;
    }

    @RequestMapping(params = "getBudgetCode", produces = "application/json", method = RequestMethod.POST)
    public @ResponseBody Map<Long, String> getBudgetCode(@RequestParam("voucherType") final String voucherType,
            @RequestParam("tranType") final String transType, final HttpServletRequest httpServletRequest) {
        Map<Long, String> budgetCode = new HashMap<>();
        budgetCode = getModel().populateAccountHead(voucherType, transType);
        return budgetCode;
    }

    @RequestMapping(params = "VoucherReportForm", method = RequestMethod.POST)
    public String ReceiptPrintForm(final Model model, final HttpServletRequest request) {

        final AccountJournalVoucherEntryBean dto = (AccountJournalVoucherEntryBean) request.getSession()
                .getAttribute(MainetConstants.AccountJournalVoucherEntry.VOUCHER_LIST);

        final List<AccountJournalVoucherEntryBean> listdto = new ArrayList<>();

        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        // final int langId = UserSession.getCurrent().getLanguageId();
        if ((dto.getNarration() != null) && !dto.getNarration().isEmpty()) {
            dto.setNarration(dto.getNarration());
        }
        CommonMasterUtility
                .getNonHierarchicalLookUpObject(dto.getVouTypeCpdId(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode();

        dto.setVoucherDesc(CommonMasterUtility.getCPDDescription(dto.getVouTypeCpdId(), MainetConstants.MODE_EDIT));
        dto.setVouchersubType(CommonMasterUtility.findLookUpDesc(
                MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgid, dto.getVoucherSubType()));
        if ((dto.getDetails() != null) && !dto.getDetails().isEmpty()) {
            Collections.sort(dto.getDetails(), AccountJournalVoucherEntryDetailsBean.compareOnvoutDetId);
        }

        Long budgetid = null;
        String budgetCode = null;
        String drCrCode = null;
        BigDecimal sumOfDr = new BigDecimal(0.00);
        BigDecimal sumOfCr = new BigDecimal(0.00);
        for (final AccountJournalVoucherEntryDetailsBean det : dto.getDetails()) {
            final AccountJournalVoucherEntryBean bean = new AccountJournalVoucherEntryBean();
            budgetid = det.getSacHeadId();
            final List<Object[]> accountcode = budgetCodeService.findAccountHeadCode(budgetid, orgid);
            for (final Object[] occountcode : accountcode) {
                if ((occountcode[0] != null) && (occountcode[1] != null)) {
                    bean.setAccountHeadDesc(occountcode[0].toString().replace(MainetConstants.HYPHEN,
                            MainetConstants.CommonConstant.BLANK) + MainetConstants.SEPARATOR
                            + occountcode[1].toString());
                }
            }

            budgetCode = budgetCodeService.findAccountHeadCodeBySacHeadId(budgetid, orgid);
            bean.setAccountHead(budgetCode);
            final Long dcCrIds = det.getDrcrCpdId();
            drCrCode = CommonMasterUtility.findLookUpCode(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE,
                    orgid, dcCrIds);
            if (drCrCode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY)) {
                sumOfDr = sumOfDr.add(det.getVoudetAmt());
                dto.setDrtotalAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfDr));
                bean.setAmountDebit(CommonMasterUtility.getAmountInIndianCurrency(det.getVoudetAmt()));
                bean.setAmountCredit(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

            }
            if (drCrCode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
                sumOfCr = sumOfCr.add(det.getVoudetAmt());
                dto.setDrtotalAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfCr));
                bean.setAmountCredit(CommonMasterUtility.getAmountInIndianCurrency(det.getVoudetAmt()));
                bean.setAmountDebit(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

            }

            listdto.add(bean);
        }

        dto.setAccountHeadList(listdto);

        if (dto.getVouNo() != null) {
            dto.setVouNo(dto.getVouNo());
        } else {
            dto.setVouNo(MainetConstants.CommonConstant.BLANK);
        }

        dto.setVocherDate(dto.getTransactionDate());
        dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

        final String DeptMasterCode = departmentService.getDeptCode(dto.getDpDeptid());

        if (!MainetConstants.MENU.Y.equalsIgnoreCase(dto.getAuthoFlg())) {
            dto.setAuthoFlg(MainetConstants.MASTER.N);
        } else {
            dto.setAuthoFlg(MainetConstants.MENU.Y);

        }

        dto.setDepartmentDesc(DeptMasterCode);
        if (dto.getDepositFlag() != null && !dto.getDepositFlag().isEmpty()) {
            dto.setDepositFlag(dto.getDepositFlag());
        }

        String empName = getEmpNameById(dto.getCreatedBy());
        dto.setPreparedBy(empName);
        dto.setPreparedDate(Utility.dateToString(dto.getLmodDate()));
        if (dto.getAuthoId() != null) {
            String empname = getEmpNameById(dto.getAuthoId());
            dto.setVerifiedby(empname);
            dto.setApprovedBy(empname);
            dto.setPostedby(empname);
        }
        if (dto.getAuthoDate() != null) {
            String date = Utility.dateToString(dto.getAuthoDate());
            dto.setVerifiedDate(date);
            dto.setApprovedDate(date);
            dto.setPostedDate(date);
        }

        model.addAttribute(MainetConstants.AccountJournalVoucherEntry.REPORT_TO, listdto);
        model.addAttribute(MainetConstants.AccountJournalVoucherEntry.REPORT_TO, dto);
        return ReceiptReportJsp_Form;
    }

    @RequestMapping(params = "formPrint", method = RequestMethod.POST)
    public String PrintForm(@RequestParam("vouId") final Long vouId,
            @RequestParam("urlIdentifyFlag") final String urlIdentifyFlag, HttpServletRequest httpServletRequest,
            final Model model) {
        bindModel(httpServletRequest);
        final long rowId = vouId;

        AccountJournalVoucherEntryBean dto = journalVoucherService.getAccountVoucherDataBeanById(rowId);

        final List<AccountJournalVoucherEntryBean> listdto = new ArrayList<>();

        final Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
        final Organisation org = new Organisation();
        org.setOrgid(orgid);
        // final int langId = UserSession.getCurrent().getLanguageId();
        BigDecimal sumOfDr = new BigDecimal(0.00);
        BigDecimal sumOfCr = new BigDecimal(0.00);

        if ((dto.getNarration() != null) && !dto.getNarration().isEmpty()) {
            dto.setNarration(dto.getNarration());
        }
        CommonMasterUtility
                .getNonHierarchicalLookUpObject(dto.getVouTypeCpdId(), UserSession.getCurrent().getOrganisation())
                .getLookUpCode();

        dto.setVoucherDesc(CommonMasterUtility.getCPDDescription(dto.getVouTypeCpdId(), MainetConstants.MODE_EDIT));

        dto.setVouchersubType(CommonMasterUtility.findLookUpDesc(
                MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, orgid, dto.getVoucherSubType()));

        if ((dto.getDetails() != null) && !dto.getDetails().isEmpty()) {
            Collections.sort(dto.getDetails(), AccountJournalVoucherEntryDetailsBean.compareOnvoutDetId);
        }

        Long budgetid = null;
        String budgetCode = null;
        String drCrCode = null;

        for (final AccountJournalVoucherEntryDetailsBean det : dto.getDetails()) {
            final AccountJournalVoucherEntryBean bean = new AccountJournalVoucherEntryBean();
            budgetid = det.getSacHeadId();
            final List<Object[]> accountcode = budgetCodeService.findAccountHeadCode(budgetid, orgid);
            for (final Object[] occountcode : accountcode) {
                if ((occountcode[0] != null) && (occountcode[1] != null)) {
                    bean.setAccountHeadDesc(occountcode[0].toString().replace(MainetConstants.HYPHEN,
                            MainetConstants.CommonConstant.BLANK) + MainetConstants.SEPARATOR
                            + occountcode[1].toString());
                }
            }

            budgetCode = budgetCodeService.findAccountHeadCodeBySacHeadId(budgetid, orgid);
            bean.setAccountHead(budgetCode);
            final Long dcCrIds = det.getDrcrCpdId();
            drCrCode = CommonMasterUtility.findLookUpCode(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE,
                    orgid, dcCrIds);
            if (drCrCode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY)) {
                sumOfDr = sumOfDr.add(det.getVoudetAmt());
                dto.setDrtotalAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfDr));
                bean.setAmountDebit(CommonMasterUtility.getAmountInIndianCurrency(det.getVoudetAmt()));
                bean.setAmountCredit(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);

            }
            if (drCrCode.equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS)) {
                sumOfCr = sumOfCr.add(det.getVoudetAmt());
                dto.setDrtotalAmount(CommonMasterUtility.getAmountInIndianCurrency(sumOfCr));
                bean.setAmountCredit(CommonMasterUtility.getAmountInIndianCurrency(det.getVoudetAmt()));
                bean.setAmountDebit(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
            }

            listdto.add(bean);
        }

        dto.setAccountHeadList(listdto);

        if (dto.getVouNo() != null) {
            dto.setVouNo(dto.getVouNo());
        } else {
            dto.setVouNo(MainetConstants.CommonConstant.BLANK);
        }

        dto.setVocherDate(dto.getTransactionDate());
        dto.setOrganizationName(UserSession.getCurrent().getOrganisation().getONlsOrgname());

        final String DeptMasterCode = departmentService.getDeptCode(dto.getDpDeptid());

        if (urlIdentifyFlag.equals(MainetConstants.AccountJournalVoucherEntry.AUTH)) {
            dto.setAuthoFlg(MainetConstants.MASTER.Y);
        } else {
            dto.setAuthoFlg(MainetConstants.MASTER.N);
        }

        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        } else {
            getModel().setModeView(MainetConstants.MODE_VIEW);
        }

        dto.setDepartmentDesc(DeptMasterCode);
        if (dto.getDepositFlag() != null && !dto.getDepositFlag().isEmpty()) {
            dto.setDepositFlag(dto.getDepositFlag());
        }

        String empName = getEmpNameById(dto.getCreatedBy());
        dto.setPreparedBy(empName);
        dto.setPreparedDate(Utility.dateToString(dto.getLmodDate()));
        if (dto.getAuthoId() != null) {
            String empname = getEmpNameById(dto.getAuthoId());
            dto.setVerifiedby(empname);
            dto.setApprovedBy(empname);
            dto.setPostedby(empname);
        }
        if (dto.getAuthoDate() != null) {
            String date = Utility.dateToString(dto.getAuthoDate());
            dto.setVerifiedDate(date);
            dto.setApprovedDate(date);
            dto.setPostedDate(date);
        }

        model.addAttribute(MainetConstants.AccountJournalVoucherEntry.REPORT_TO, listdto);
        model.addAttribute(MainetConstants.AccountJournalVoucherEntry.REPORT_TO, dto);
        return ReceiptReportGrid_Form;
    }

    private String getEmpNameById(Long empId) {
        String empName = "";
        final EmployeeBean bean = employeeService.findById(empId);
        if(bean != null) {
        	 if ((bean.getEmpmname() != null) && !bean.getEmpmname().isEmpty()) {
                 empName = bean.getEmpname() + " " + bean.getEmpmname() + " " + bean.getEmplname();
             } else {
                 if (bean.getEmplname() != null && !bean.getEmplname().isEmpty()) {
                     empName = bean.getEmpname() + " " + bean.getEmplname();
                 } else {
                     empName = bean.getEmpname();
                 }
             }
        }
        return empName;
    }

    @RequestMapping(params = "onAuthorizationDate", method = RequestMethod.GET)
    public @ResponseBody String validateAuthorizationDate(
            @RequestParam("authorizationDate") final String authorizationDate,
            @RequestParam("transactionDate") final String transactionDate) {
        String response;
        try {
            Objects.requireNonNull(authorizationDate,
                    ApplicationSession.getInstance().getMessage("account.journal.voucher.authorizationdate"));
            Objects.requireNonNull(transactionDate,
                    ApplicationSession.getInstance().getMessage("account.journal.voucher.transactiondate"));
            final Date entrDate = Utility.stringToDate(transactionDate);
            final Date authDate = Utility.stringToDate(authorizationDate);
            if ((authDate.getTime() >= entrDate.getTime()) && (authDate.getTime() <= new Date().getTime())) {
                response = ApplicationSession.getInstance().getMessage("accounts.depositslip.response.ok");
            } else {
                response = ApplicationSession.getInstance().getMessage("account.journal.voucher.currentdate");
            }
        } catch (final Exception ex) {
            response = ApplicationSession.getInstance().getMessage("account.depositslip.response.servererror");
            LOGGER.error("Error while validating Authorization date with entry date", ex);
        }
        return response;
    }

    @RequestMapping(params = "checkTransactions", method = RequestMethod.POST)
    public @ResponseBody boolean checkTransactionsCombination(@RequestParam("sacHeadId") final Long sacHeadId,
            final HttpServletRequest request) {

        boolean isValidationError = false;
        final Long sacHeadid = sacHeadId;
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (journalVoucherService.isCombinationCheckTransactions(sacHeadid, orgId)) {
            isValidationError = true;
        }
        return isValidationError;
    }

    @RequestMapping(params = "transactionFormForView", method = RequestMethod.POST)
    public ModelAndView transactionviewVoucherForm(@RequestParam("vouId") final Long vouId,
            @RequestParam("accountHead") final String accountHead,
            @RequestParam("voucherDate") final String voucherDate, @RequestParam("fromDate") final String fromDate,
            @RequestParam("toDate") final String toDate, @RequestParam("faYearid") final Long faYearid,
            @RequestParam("openDr") final BigDecimal openDr, @RequestParam("openCr") final BigDecimal openCr,
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        getModel().viewForm(vouId);

        if (!httpServletRequest.getRequestURI().contains(MainetConstants.AccountBillEntry.ENTRY)) {
            getModel().setMode(MainetConstants.AccountJournalVoucherEntry.AUTH);
        } else {
            getModel().setModeView(MainetConstants.MODE_VIEW);
        }
        UserSession.getCurrent().getOrganisation().getOrgid();

        final List<LookUp> voucherSubTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                UserSession.getCurrent().getOrganisation());

        getModel().setVoucherSubTypeList(voucherSubTypeList);
        getModel().setAccountHead(accountHead);
        getModel().setVoucherDates(voucherDate);
        getModel().setToDate(toDate);
        getModel().setFromDate(fromDate);
        getModel().setFaYearId(faYearid);
        getModel().setOpenCr(openCr);
        getModel().setOpenDr(openDr);
        final ModelAndView mv = new ModelAndView(TRANSACTION_TRACKING_VOUCHER, MainetConstants.FORM_NAME, getModel());
        return mv;

    }

    @RequestMapping(params = "ExcelTemplateData")
    public void exportAccountVoucherEntryExcelData(final HttpServletResponse response,
            final HttpServletRequest request) {
        LOGGER.info("in download template method");
        try {
            WriteExcelData data = new WriteExcelData(MainetConstants.VOUCHERENTRYUPLOADDTO + MainetConstants.XLSX_EXT,
                    request, response);

            data.getExpotedExcelSheet(new ArrayList<AccountVoucherMasterUploadDTO>(),
                    AccountVoucherMasterUploadDTO.class);
            data.getExpotedExcelSheet(new ArrayList<AccountVoucherDetailsUploadDTO>(),
                    AccountVoucherDetailsUploadDTO.class);
            data.responseBody();
        } catch (Exception ex) {
            throw new FrameworkException(ex.getMessage());
        }
    }

    @RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
    public ModelAndView exportImportExcelTemplate(final HttpServletRequest request) throws Exception {
        AccountJournalVoucherEntryModel model = this.getModel();
        model.bind(request);
        model.setErrorMap(null);
        String result = MainetConstants.CommonConstant.BLANK;
        result = JSP_EXCELUPLOAD;
        fileUpload.sessionCleanUpForFileUpload();
        return new ModelAndView(result, MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public ModelAndView loadValidateAndLoadExcelData(AccountJournalVoucherEntryModel voucherModel,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {

        voucherModel = this.getModel();
        voucherModel.bind(httpServletRequest);
        final ModelAndView mv = new ModelAndView(JSP_EXCELUPLOAD, MainetConstants.FORM_NAME, voucherModel);
        final ApplicationSession session = ApplicationSession.getInstance();

        int count = 0;
        final UserSession userSession = UserSession.getCurrent();
        final Long orgId = userSession.getOrganisation().getOrgid();
        final int langId = userSession.getLanguageId();
        final Long userId = userSession.getEmployee().getEmpId();
        final Employee emp = UserSession.getCurrent().getEmployee();
        final String filePath = getUploadedFinePath();
        ReadExcelData<AccountVoucherMasterUploadDTO> data = new ReadExcelData<>(filePath,
                AccountVoucherMasterUploadDTO.class, 0);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.empty.excel.sheet.first")));
        }
        ReadExcelData<AccountVoucherDetailsUploadDTO> secSheetData = new ReadExcelData<>(filePath,
                AccountVoucherDetailsUploadDTO.class, 1);
        secSheetData.parseExcelList();
        List<String> secErrorList = secSheetData.getErrorList();
        if (!secErrorList.isEmpty()) {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.empty.excel.sheet.second")));
        }

        final List<AccountVoucherMasterUploadDTO> voucherMasterUploadList = data.getParseData();
        final List<AccountVoucherDetailsUploadDTO> voucherDetailsList = secSheetData.getParseData();
        Set<AccountVoucherMasterUploadDTO> voucherEntryExport = voucherMasterUploadList.stream()
                .filter(dto -> Collections.frequency(voucherMasterUploadList, dto) > 1).collect(Collectors.toSet());
        if (!voucherEntryExport.isEmpty()) {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.VOUCHER_TEMPLATE_ENTRY.VOUCHER_TEMPLATE, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("account.journal.voucher.empty.excel.duplicate")));
        }
        if (count == 0) {
            Map<String, AccountVoucherMasterUploadDTO> masterVoucherDTOMap = new LinkedHashMap<>();
            for (AccountVoucherMasterUploadDTO accountVoucherMasterUploadDTO : voucherMasterUploadList) {
                // List<AccountVoucherMasterUploadDTO> listDTO = new ArrayList<>();
                StringBuilder newDTO = new StringBuilder();
                newDTO.append(accountVoucherMasterUploadDTO.getTranDate());
                newDTO.append(accountVoucherMasterUploadDTO.getTranRefNo());
                newDTO.append(accountVoucherMasterUploadDTO.getVoucherType());
                AccountVoucherMasterUploadDTO newDetailsDTO = new AccountVoucherMasterUploadDTO();
                newDetailsDTO.setTranDate(accountVoucherMasterUploadDTO.getTranDate());
                newDetailsDTO.setTranRefNo(accountVoucherMasterUploadDTO.getTranRefNo());
                newDetailsDTO.setVoucherType(accountVoucherMasterUploadDTO.getVoucherType());
                newDetailsDTO.setVoucherSubType(accountVoucherMasterUploadDTO.getVoucherSubType());
                newDetailsDTO.setNarration(accountVoucherMasterUploadDTO.getNarration());
                // listDTO.add(newDetailsDTO);
                masterVoucherDTOMap.put(newDTO.toString(), newDetailsDTO);
            }
            Map<String, List<AccountVoucherDetailsUploadDTO>> detailVoucherDTOMap = new LinkedHashMap<>();
            for (AccountVoucherDetailsUploadDTO accountVoucherDetailUploadDTO : voucherDetailsList) {
                StringBuilder newDetailDTO = new StringBuilder();
                newDetailDTO.append(accountVoucherDetailUploadDTO.getTranDate());
                newDetailDTO.append(accountVoucherDetailUploadDTO.getTranRefNo());
                newDetailDTO.append(accountVoucherDetailUploadDTO.getVoucherType());
                List<AccountVoucherDetailsUploadDTO> listDTO = detailVoucherDTOMap.get(newDetailDTO.toString());
                if (listDTO == null || listDTO.isEmpty()) {
                    listDTO = new ArrayList<>();
                }
                AccountVoucherDetailsUploadDTO newDetailsDTO = new AccountVoucherDetailsUploadDTO();
                newDetailsDTO.setDrOrCr(accountVoucherDetailUploadDTO.getDrOrCr());
                newDetailsDTO.setAccHead(accountVoucherDetailUploadDTO.getAccHead());
                newDetailsDTO.setAmount(accountVoucherDetailUploadDTO.getAmount());
                listDTO.add(newDetailsDTO);
                detailVoucherDTOMap.put(newDetailDTO.toString(), listDTO);
            }
            List<AccountVoucherMasterUploadDTO> masterLatestList = new ArrayList<>();
            for (Map.Entry<String, AccountVoucherMasterUploadDTO> entry1 : masterVoucherDTOMap.entrySet()) {
                List<AccountVoucherDetailsUploadDTO> det = detailVoucherDTOMap.get(entry1.getKey());
                AccountVoucherMasterUploadDTO entryMaster = entry1.getValue();
                entryMaster.setDetails(det);
                masterLatestList.add(entryMaster);
            }

            final List<LookUp> voucherTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.VOT,
                    UserSession.getCurrent().getOrganisation());
            Map<Long, String> voucherTypeMap = new HashMap<>();
            for (LookUp list : voucherTypeList) {
                String otherFiled = list.getOtherField();
                String otherValue = MainetConstants.Y_FLAG;
                if (otherFiled != null && !otherFiled.isEmpty()) {
                    if (otherFiled.equalsIgnoreCase(otherValue)) {
                        voucherTypeMap.put(list.getLookUpId(), list.getLookUpDesc());
                    }
                }
            }
            Map<String, String> voucherCodeMap = new HashMap<>();
            for (LookUp list : voucherTypeList) {
                String otherFiled = list.getOtherField();
                String otherValue = MainetConstants.Y_FLAG;
                if (otherFiled != null && !otherFiled.isEmpty()) {
                    if (otherFiled.equalsIgnoreCase(otherValue)) {
                        voucherCodeMap.put(list.getLookUpCode(), list.getLookUpDesc());
                    }
                }
            }
            final List<LookUp> voucherSubTypeList = CommonMasterUtility
                    .getListLookup(MainetConstants.AccountBillEntry.TDP, UserSession.getCurrent().getOrganisation());

            Map<Long, String> voucherSubTypeMap = new HashMap<>();
            for (LookUp list : voucherSubTypeList) {
                String otherField = list.getOtherField();
                String otherValue = MainetConstants.AccountConstants.JV.toString();
                if (otherField != null && !otherField.isEmpty()) {
                    if (otherField.equalsIgnoreCase(otherValue)) {
                        voucherSubTypeMap.put(list.getLookUpId(), list.getLookUpDesc());
                    }
                }

            }
            final List<LookUp> drCrTypes = CommonMasterUtility.getListLookup(
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE,
                    UserSession.getCurrent().getOrganisation());
           // Map<Long, String> secondaryHeadDescMap = tbAcSecondaryheadMasterService.getSecondaryHeadDesc(orgId);
            Map<Long, String> secondaryHeadDescMap = tbAcSecondaryheadMasterService.getAcHeadCode(orgId);
            Map<Long, String> oldSecondaryHeadDescMap = tbAcSecondaryheadMasterService.oldSecondaryHeadDescMap(orgId);
            VoucherEntryValidator validator = new VoucherEntryValidator();
            Map<Long, String>  accHeadErr=  checkExistanceOfAccountHead(secondaryHeadDescMap,oldSecondaryHeadDescMap,voucherDetailsList);
            voucherModel.setErrorMap(accHeadErr);
            if(MapUtils.isEmpty(accHeadErr)) {
            List<AccountVoucherEntryEntity> voucherMasterDetails = journalVoucherService.getVoucherMasterDetails(orgId);
            List<AccountVoucherMasterUploadDTO> voucherEntryUploadDtosUploadList = validator.excelValidation(
                    masterLatestList, bindingResult, voucherTypeMap, voucherSubTypeList, drCrTypes, voucherCodeMap,
                    voucherModel, secondaryHeadDescMap,oldSecondaryHeadDescMap);
            validator.checkDuprecords(voucherEntryUploadDtosUploadList, bindingResult, voucherMasterDetails);
            boolean entryExcelData = false;
            if (!bindingResult.hasErrors()) {
                for (AccountVoucherMasterUploadDTO voucherEntryUploadDto : voucherEntryUploadDtosUploadList) {

                    voucherEntryUploadDto.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
                    voucherEntryUploadDto.setLangId(Long.valueOf(langId));
                    voucherEntryUploadDto.setUserId(userId);
                    voucherEntryUploadDto.setLmoddate(new Date());
                    voucherEntryUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
                    entryExcelData = journalVoucherService.saveVoucherEntryExcelData(voucherEntryUploadDto, orgId,
                            langId, emp);
                    if (entryExcelData == false) {
                        bindingResult.addError(new org.springframework.validation.FieldError(
                                MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME,
                                MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                session.getMessage("account.journal.voucher.fin.YearClose")));
                        break;
                    }
                }
                if (entryExcelData) {
                    mv.addObject(MainetConstants.AccountBudgetAdditionalSupplemental.KEY_TEST,
                            session.getMessage("accounts.success.excel"));
                }
            }
        }}
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
        return mv;
    }

    private Map<Long, String> checkExistanceOfAccountHead(Map<Long, String> secondaryHeadDescMap,
			Map<Long, String> oldSecondaryHeadDescMap, List<AccountVoucherDetailsUploadDTO> masterList) {
		Map<Long, String> mapData = new HashMap<>();
		Long key = 1L;
		for (AccountVoucherDetailsUploadDTO voucherEntryUploadDto : masterList) {
			int accountHeadExist = 0;
			for (Map.Entry<Long, String> entry : secondaryHeadDescMap.entrySet()) {
				String value = entry.getValue().replaceAll("(\\r|\\n)", "");
				String value1 = value.replaceAll("\\s", "");
				if(voucherEntryUploadDto.getAccHead()!=null) {
				String accountHead = voucherEntryUploadDto.getAccHead().trim();
				String acHeadCode = accountHead.replaceAll("\\s", "");
				if (acHeadCode.trim().equalsIgnoreCase(value1.trim())) {
					accountHeadExist++;
				}}
			}
			if (accountHeadExist == 0) {
				for (Map.Entry<Long, String> entry : oldSecondaryHeadDescMap.entrySet()) {
					if (entry != null && entry.getValue() != null) {
						String value = entry.getValue().replaceAll("(\\r|\\n)", "");
						String value1 = value.replaceAll("\\s", "");
						if(voucherEntryUploadDto.getAccHead()!=null) {
						String accountHead = voucherEntryUploadDto.getAccHead().trim();
						String acHeadCode = accountHead.replaceAll("\\s", "");
						if (acHeadCode.trim().equalsIgnoreCase(value1.trim())) {
							accountHeadExist++;
						}}
					}
				}
			}
			if (accountHeadExist == 0) {
				if(!mapData.containsValue(voucherEntryUploadDto.getAccHead())) {
				mapData.put(key, voucherEntryUploadDto.getAccHead());
				key++;
			}
		}}
		return mapData;}

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
    @RequestMapping(params = "getVoucherSubType", method = RequestMethod.POST)
	public @ResponseBody List<Object[]> getActiveEmpNameByDeptId(@RequestParam("voucherType") final String voucherType) {
		 final List<LookUp> voucherTypeList = CommonMasterUtility.getListLookup(MainetConstants.AccountBillEntry.TDP,
                 UserSession.getCurrent().getOrganisation());
		 List<Object[]> obj=new ArrayList<>();
		 for(LookUp lookup:voucherTypeList) {
			 if(StringUtils.isNotBlank(lookup.getOtherField())&& lookup.getOtherField().equals(voucherType)) {
				 Object[] objArr=new Object [5];
				 objArr[0]=lookup.getLookUpId();
				 objArr[1]=lookup.getLookUpCode();
				 objArr[2]=lookup.getLookUpDesc();
				 obj.add(objArr);
			 }
		 }
		return obj;
	}
	}
