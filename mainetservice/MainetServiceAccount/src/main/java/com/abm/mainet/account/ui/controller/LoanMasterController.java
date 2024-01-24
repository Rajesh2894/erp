package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.service.AccountLoanMasterService;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.ui.model.LoanMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/loanmaster.html")
public class LoanMasterController extends AbstractFormController<LoanMasterModel> {
	@Autowired
	private AccountLoanMasterService accountLoanMasterService;

	@Resource
	private DepartmentService departmentService;

	@Resource
	private TbAcVendormasterService tbAcVendormasterService;

	@Resource
	private AccountReceiptEntryService accountReceiptEntryService;
	
	@Resource
	private AccountFinancialReportService accountFinancialReportService;
	
	@Autowired
	private AccountBillEntryService accountBillEntryService;
	
	@Resource
	private PaymentEntrySrevice paymentEntrySrevice;
	
	// index method is present in every controller of SUDA framework

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		
		sessionCleanup(httpServletRequest);
		//loanId, deptId, lnPurpose
		loadSummaryData(null, null, null,null);
		loadDepartment(httpServletRequest);
		loadCategorytypeId(httpServletRequest);
		loanCode(httpServletRequest);
		return index();
	}

 

	private void loadCategorytypeId(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUps : recieptVouType) {
			if (lookUps.getLookUpCode().equals("LNR")) {
				this.getModel().getAccountLoanMasterDto().setCategoryTypeId(lookUps.getLookUpId());
			
			}
		}
	}

	private void loadReceiptNo(HttpServletRequest httpServletRequest, Long loanId) {
		List<Object[]> grantList = accountReceiptEntryService.findDataOfReceipt("LNR", loanId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbServiceReceiptMasBean> receiptMasBeanList = new ArrayList<TbServiceReceiptMasBean>();
		if (grantList != null && !grantList.isEmpty()) {
			for (final Object[] grantBean : grantList) {
				TbServiceReceiptMasBean receiptMasBean = new TbServiceReceiptMasBean();
				if (grantBean[1] != null) {
					receiptMasBean.setRmRcptid(Long.valueOf(grantBean[1].toString()));
				}
				if (grantBean[2] != null) {
					receiptMasBean.setRmDate((Date) grantBean[2]);
				}
				if (grantBean[3] != null) {
					receiptMasBean.setRmAmount(grantBean[3].toString());
				}
				if (grantBean[4] != null) {
					receiptMasBean.setRmReceivedfrom(grantBean[4].toString());
				}
				if (grantBean[5] != null) {
					receiptMasBean.setRmNarration(grantBean[5].toString());
				}
				receiptMasBeanList.add(receiptMasBean);
			}
		}
		this.getModel().setReceiptMasBeanList(receiptMasBeanList);
	}

	private void loadSummaryData(Long loanId, String deptId, String lnPurpose,String loanCode) {
		// TODO Auto-generated method stub
		List<AccountLoanMasterDto> listLoanData = new ArrayList<AccountLoanMasterDto>();
		listLoanData = accountLoanMasterService.findLoanMasterData(loanId, deptId, lnPurpose,
				UserSession.getCurrent().getOrganisation().getOrgid(),loanCode);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
	    this.getModel().getAccountLoanMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		this.getModel().setAccountLoanMasterDtoList(listLoanData);
	}

	
	//MainetConstants.AccountConstants.AC.getValue()
	private void vendorName(HttpServletRequest httpServletRequest) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final Integer languageId = UserSession.getCurrent().getLanguageId();
		final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
				"AC", PrefixConstants.VSS, languageId, org);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final LookUp lookUpSacStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.MASTER.A,
				PrefixConstants.ACN, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		final Long activeStatusId = lookUpSacStatus.getLookUpId();
		final List<TbAcVendormaster> vendorList = tbAcVendormasterService
				.getActiveStatusVendorsAndSacAcHead(org.getOrgid(), vendorStatus, activeStatusId);
		this.getModel().setVendorList(vendorList);
		// this method gets the name of all the vendors and sets it to the
		// setVendorList. This is then called on to the jsp page
	}

	private void loadDepartment(HttpServletRequest httpServletRequest) {
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		this.getModel().setDepList(deptMap);
	}
	
	
	private void loanCode(final HttpServletRequest request)
	{
		List<String> id = accountFinancialReportService.getLoanCode(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setId(id);
	}
	
	// CRUD operations
	// Displays the first form for data entry. This method loads the vendor list and
	// department list into the drop down box
	@ResponseBody
	@RequestMapping(params = "craeteForm", method = RequestMethod.POST)
	public ModelAndView loanMasterAdd(final HttpServletRequest request) {
		sessionCleanup(request);
		// here MODE_CREATE is set to C ; create mode which is sent to the jsp page
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		loadDepartment(request);
		vendorName(request);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
	    this.getModel().getAccountLoanMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return new ModelAndView("loanMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView loanMasterSave(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView("loanMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "editForm", method = RequestMethod.POST)
	public ModelAndView loanMasterEdit(final HttpServletRequest request, @RequestParam Long loanId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
		
		AccountLoanMasterDto listLoanData = new AccountLoanMasterDto();
		listLoanData = accountLoanMasterService
				.findLoanMasterData(loanId, null, null, UserSession.getCurrent().getOrganisation().getOrgid(),null).get(0);
		this.getModel().setAccountLoanMasterDto(listLoanData);
		loadDepartment(request);
		vendorName(request);
		loadReceiptNo(request, loanId);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		
	    this.getModel().getAccountLoanMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return new ModelAndView("loanMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewForm", method = RequestMethod.POST)
	public ModelAndView loanMasterView(final HttpServletRequest request, @RequestParam Long loanId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
		loadDepartment(request);
		vendorName(request);
		loadReceiptNo(request, loanId);
		AccountLoanMasterDto listLoanData = new AccountLoanMasterDto();
		listLoanData = accountLoanMasterService
				.findLoanMasterData(loanId, null, null, UserSession.getCurrent().getOrganisation().getOrgid(),null).get(0);
		this.getModel().setAccountLoanMasterDto(listLoanData);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		List<PaymentEntryDto> PaymentList=new ArrayList<>();
		List<AccountBillEntryMasterBean> BillDetailList =accountBillEntryService.getBillDetailsByIntRefIdAndOrgId(loanId, UserSession.getCurrent().getOrganisation().getOrgid());
		 if(CollectionUtils.isNotEmpty(BillDetailList)) {
			 BillDetailList.forEach(dto->{
			      List<PaymentEntryDto> peyment = paymentEntrySrevice.getPaymentDetailByBillId(dto.getId(), UserSession.getCurrent().getOrganisation().getOrgid());
			     PaymentList.addAll(peyment);
			  });
			 }
		 this.getModel().setPaymentList(PaymentList);

	    this.getModel().getAccountLoanMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return new ModelAndView("loanMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView loanMasterSearch(final HttpServletRequest request, @RequestParam String deptId,
			@RequestParam String lnPurpose,@RequestParam String loanCode) {
		sessionCleanup(request);
		if (deptId.isEmpty()) {
			deptId = null;
		}
		if (lnPurpose.isEmpty()) {
			lnPurpose = null;
		}
		if(loanCode.isEmpty())
		{
			loanCode = null;
		}
		loadSummaryData(null, deptId, lnPurpose,loanCode);
		loadDepartment(request);
		loadCategorytypeId(request);
		this.getModel().getAccountLoanMasterDto().setLnDeptname(deptId);
		this.getModel().getAccountLoanMasterDto().setLnPurpose(lnPurpose);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		this.getModel().getAccountLoanMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));

		return new ModelAndView("loanMasterSummary/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@ResponseBody
	@RequestMapping(params = "checkLoanBillDone", method = RequestMethod.POST)
	public Map<String, Object> checkLoanBillDone(HttpServletRequest httpServletRequest,
			@RequestParam("loanRepaymentId") Long loanRepaymentId) {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		List<AccountBillEntryMasterBean> billList = ApplicationContextProvider.getApplicationContext().getBean(AccountBillEntryService.class).getBillDetailsByIntRefIdAndOrgId(loanRepaymentId, UserSession.getCurrent().getOrganisation().getOrgid());
		
		if(CollectionUtils.isNotEmpty(billList)) {
			getModel().addValidationError(getApplicationSession()
					.getMessage("Already bill have done for the respective repayment schedule"));
		}
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		return object;
	}
}
