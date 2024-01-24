package com.abm.mainet.account.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.account.service.AccountGrantMasterService;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.ui.model.AccountGrantMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/grantMaster.html")
public class AccountGrantMasterController extends AbstractFormController<AccountGrantMasterModel> {
	@Autowired
	private AccountGrantMasterService accountGrantMasterService;
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	@Resource
	private TbOrganisationService tbOrganisationService;
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	@Resource
	private AccountReceiptEntryService accountReceiptEntryService;

	@Resource
	private TbFinancialyearService tbfinancialYearService;
	
	@Autowired
	private AccountBillEntryService accountBillEntryService;
	
	@Resource
	private PaymentEntrySrevice paymentEntrySrevice;


	@RequestMapping(method = { RequestMethod.POST , RequestMethod.GET})
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		loadSummaryData(httpServletRequest);
		loadFundMaster(httpServletRequest);
		loadCategorytypeId(httpServletRequest);

		return index();
	}

	private void loadCategorytypeId(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUps : recieptVouType) {
			if (lookUps.getLookUpCode().equals("GRT")) {
				this.getModel().getAccountGrantMasterDto().setCategoryTypeId(lookUps.getLookUpId());
				this.getModel().getAccountGrantMasterDto().setCategoryType(lookUps.getLookUpCode());
			}
		}
	}

	private void loadReceiptNo(HttpServletRequest httpServletRequest, Long grantId) {
		List<Object[]> grantList = accountReceiptEntryService.findDataOfReceipt("GRT", grantId,
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

	private void loadSummaryData(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub

		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		this.getModel().getAccountGrantMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));

		List<AccountGrantMasterDto> accountGrantMasterDto = accountGrantMasterService.findByNameAndNature(null, null,
				null, null, null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAccountGrantMasterDtoList(accountGrantMasterDto);
	}

	private void financialYear(HttpServletRequest httpServletRequest) {

		/*
		 * Map<Long , String> years = secondaryheadMasterService.getAllFinincialYear(
		 * UserSession.getCurrent().getOrganisation().getOrgid(),
		 * UserSession.getCurrent().getLanguageId());
		 */

		this.getModel().setListOfinalcialyear(tbfinancialYearService.getAllFinincialYear());

	}

	private void loadFundMaster(HttpServletRequest httpServletRequest) {
		@SuppressWarnings("unused")
		boolean fieldDefaultFlag = false;
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		boolean fundDefaultFlag = false;
		if (isDafaultOrgExist) {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
		} else {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
		}
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
		final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
				PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		this.getModel().setFundList(tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
				fundLookup.getLookUpId(), UserSession.getCurrent().getLanguageId()));
	}

	@ResponseBody
	@RequestMapping(params = "createForm", method = RequestMethod.POST)
	public ModelAndView grantMasterAdd(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		loadFundMaster(request);
		financialYear(request);

		/*
		 * final LookUp lookUp =
		 * CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
		 * MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
		 * UserSession.getCurrent().getLanguageId()));
		 */
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		this.getModel().getAccountGrantMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));
		return new ModelAndView("grantMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView grantMasterSave(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView("grantMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "editForm", method = RequestMethod.POST)
	public ModelAndView grantMasterEdit(final HttpServletRequest request, @RequestParam Long grantId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
		loadFundMaster(request);
		financialYear(request);
		AccountGrantMasterDto accountGrantMasterDto = accountGrantMasterService
				.findByGrntIdAndOrgId(grantId, UserSession.getCurrent().getOrganisation().getOrgid()).get(0);
		this.getModel().setAccountGrantMasterDto(accountGrantMasterDto);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		this.getModel().getAccountGrantMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));
		return new ModelAndView("grantMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewForm", method = RequestMethod.POST)
	public ModelAndView grantMasterView(final HttpServletRequest request, @RequestParam Long grantId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
		loadFundMaster(request);
		financialYear(request);
		loadReceiptNo(request, grantId);
		List<AccountBillEntryMasterBean> billDeatilGrandRefund=new ArrayList<AccountBillEntryMasterBean>();
		List<PaymentEntryDto> PaymentList=new ArrayList<>();
		List<AccountBillEntryMasterBean> BillDetailList =accountBillEntryService.getBillDetailsByIntRefIdAndOrgId(grantId, UserSession.getCurrent().getOrganisation().getOrgid());
	  if(CollectionUtils.isNotEmpty(BillDetailList)) {
		 BillDetailList.forEach(dto->{
			if(dto.getBillTypeCode().equals("GFD")) {//in refund detail grand refund only allowed  #85062
			  billDeatilGrandRefund.add(dto);
			 }
			if(dto.getBillTypeCode().equals("GRT")) {
		      List<PaymentEntryDto> peyment = paymentEntrySrevice.getPaymentDetailByBillId(dto.getId(), UserSession.getCurrent().getOrganisation().getOrgid());
		     PaymentList.addAll(peyment);
			}
		  });
		 }
		this.getModel().setBillDtoList(billDeatilGrandRefund);
		this.getModel().setPaymentList(PaymentList);
		AccountGrantMasterDto accountGrantMasterDto = accountGrantMasterService
				.findByGrntIdAndOrgId(grantId, UserSession.getCurrent().getOrganisation().getOrgid()).get(0);
		this.getModel().setAccountGrantMasterDto(accountGrantMasterDto);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		this.getModel().getAccountGrantMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));

		return new ModelAndView("grantMaster/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView grantMasterSearch(final HttpServletRequest request, @RequestParam String grtType,
			@RequestParam String grtName, @RequestParam String grtNo, @RequestParam Long fundId) {
		sessionCleanup(request);
		loadFundMaster(request);
        //D#146998
		loadCategorytypeId(request);
		if (grtName.isEmpty()) {
			grtName = null;
		}
		List<AccountGrantMasterDto> accountGrantMasterDto = accountGrantMasterService.findByNameAndNature(grtType,
				grtName, grtNo, fundId, null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAccountGrantMasterDtoList(accountGrantMasterDto);
		this.getModel().getAccountGrantMasterDto().setGrtType(grtType);
		this.getModel().getAccountGrantMasterDto().setGrtName(grtName);
		this.getModel().getAccountGrantMasterDto().setGrtNo(grtNo);
		this.getModel().getAccountGrantMasterDto().setFundId(fundId);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE,
				MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE,
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
		this.getModel().getAccountGrantMasterDto()
				.setSliDate(Utility.stringToDate(lookUp.getOtherField(), "dd/MM/yyyy"));

		return new ModelAndView("grantMasterSummary/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "checkingGrantRefundsApplicable", method = RequestMethod.POST)
	public Map<String, Object> checkingGrantRefundsApplicable(HttpServletRequest httpServletRequest,
			@RequestParam("grantType") String grantType, @RequestParam("grantId") Long grantId) {
		getModel().bind(httpServletRequest);
		LookUp valueFromPrefixLookUp = null;
		if (StringUtils.equals(grantType, "GRT")) {
			try {
				valueFromPrefixLookUp = CommonMasterUtility.getValueFromPrefixLookUp("GRT", "ABT",
						UserSession.getCurrent().getOrganisation());
			} catch (Exception e) {
			}
			if (valueFromPrefixLookUp == null) {
				getModel().addValidationError(
						getApplicationSession().getMessage("account.grant.master.controller.define.prefix.GRT"));
			}

		}
		if (StringUtils.equals(grantType, "GFD")) {

			try {
				valueFromPrefixLookUp = CommonMasterUtility.getValueFromPrefixLookUp("GFD", "ABT",
						UserSession.getCurrent().getOrganisation());
			} catch (Exception e) {
			}
			if (valueFromPrefixLookUp == null) {
				getModel().addValidationError(
						getApplicationSession().getMessage("account.grant.master.controller.define.prefix.GFD"));
			}
		}

		Map<String, Object> object = new LinkedHashMap<String, Object>();
		
		Long payableAmount = 0L;
		if (valueFromPrefixLookUp != null) {
			Long totalReceiptAmount = ApplicationContextProvider.getApplicationContext()
					.getBean(IReceiptEntryService.class).getTotalReceiptAmountByRefIdAndReceiptType(grantId, "GRT",
							UserSession.getCurrent().getOrganisation().getOrgid());
			if (totalReceiptAmount != null && totalReceiptAmount > 0) {
				Long totalBillAmount =accountBillEntryService.getTotalBillAmountByIntRefIdAndBillTypeId(grantId,
						CommonMasterUtility.getValueFromPrefixLookUp("GRT", "ABT",
								UserSession.getCurrent().getOrganisation()).getLookUpId(),
								UserSession.getCurrent().getOrganisation().getOrgid());
				payableAmount = totalReceiptAmount;
				if (totalBillAmount != null && totalBillAmount > 0) {
					payableAmount = totalReceiptAmount - totalBillAmount;
				}
				if (payableAmount <= 0) {
					getModel().addValidationError(getApplicationSession().getMessage(
							"account.grant.master.controller.bill.refund.done"));
				}
			} else {
				getModel().addValidationError(getApplicationSession()
						.getMessage("account.grant.master.controller.noreceipt"));
			}

			/*
			 * Long sanctionAmount =
			 * accountGrantMasterService.getSanctionAmountByGrantIdAndOrgId(grantId,
			 * UserSession.getCurrent().getOrganisation().getOrgid());
			 */

		}
		object.put("payableAmount", payableAmount);
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		if (this.getModel().getBindingResult() == null && !this.getModel().getBindingResult().hasErrors()) {
			object.put("successFlag", MainetConstants.FlagY);
		}
		return object;
	}
	

}
