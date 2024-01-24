package com.abm.mainet.account.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.service.AccountInvestmentService;
import com.abm.mainet.account.service.BankAccountService;
import com.abm.mainet.account.ui.model.AccountInvestmentMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/investmentMaster.html")
public class AccountInvestmentMasterController extends AbstractFormController<AccountInvestmentMasterModel> {

	@Autowired
	private AccountInvestmentService accountInvestmentService;
	@Resource
	private BankAccountService bankAccountService;
	@Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;
	@Resource
	private TbOrganisationService tbOrganisationService;
	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		loadSummaryData(httpServletRequest);
		loadBankName(httpServletRequest);
		loadFundMaster(httpServletRequest);
		loadCategorytypeId(httpServletRequest);
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
	    this.getModel().getAccountInvestmentMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return index();
	}

	private void loadCategorytypeId(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
	/*	String date= null;
		Date SLI_Date;*/
		final List<LookUp> recieptVouType = CommonMasterUtility.getListLookup(
				MainetConstants.BILL_MASTER_COMMON.PREFIX_CODE_RV, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUps : recieptVouType) {
			if (lookUps.getLookUpCode().equals("INV")) {
				this.getModel().getAccountInvestmentMasterDto().setCategoryTypeId(lookUps.getLookUpId());
			}
		}
	
		
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
	
	private void loadBankName(HttpServletRequest httpServletRequest) {
		final List<Object[]> bankListOfObj = bankAccountService
				.findBankDeatilsInBankAccount(UserSession.getCurrent().getOrganisation().getOrgid());
		AccountInvestmentMasterModel invstModel = this.getModel();
		Map<Long, String> bankMap = new HashMap<Long, String>();
		if (bankListOfObj != null && !bankListOfObj.isEmpty()) {
			for (Object[] objects : bankListOfObj) {
				if (objects[0] != null && objects[1] != null && objects[2] != null && objects[3] != null) {
					bankMap.put((Long) objects[0], objects[1].toString() + MainetConstants.SEPARATOR
							+ objects[2].toString() + MainetConstants.SEPARATOR + objects[3].toString());
				}
			}
		}
		invstModel.setBankMap(bankMap);
	}

	private void loadSummaryData(HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		AccountInvestmentMasterModel invstModel = this.getModel();
		List<AccountInvestmentMasterDto> listofInvestMasterData = accountInvestmentService.findByBankIdInvestmentData(
				null, null,null, null, null,null, null,UserSession.getCurrent().getOrganisation().getOrgid());
		
		for(AccountInvestmentMasterDto list : listofInvestMasterData)
		{
			Long bid = list.getBankId(); 
			list.setBankName(bankName(bid));
		}
		invstModel.setAcInvstDtoList(listofInvestMasterData);
		
		
		
	}

	private String bankName(Long bankid) {
		String bank=null;
		final List<Object[]> bankListOfObj = bankAccountService
				.findBankDeatilsInBankAccount(UserSession.getCurrent().getOrganisation().getOrgid());
		AccountInvestmentMasterModel invstModel = this.getModel();
		Map<Long, String> bankMap = new HashMap<Long, String>();
		if (bankListOfObj != null && !bankListOfObj.isEmpty()) {
			for (Object[] objects : bankListOfObj) {
					if (objects[0] != null && objects[1] != null) {
					bankMap.put((Long) objects[0], objects[1].toString() + MainetConstants.SEPARATOR + objects[3].toString());
				}
			if(bankMap.containsKey(bankid))
			{
				bank = bankMap.get(bankid);
			}
				
			}
		}
		return bank;
	}
	
	@ResponseBody
	@RequestMapping(params = "createForm", method = RequestMethod.POST)
	public ModelAndView investmentMasterAdd(final HttpServletRequest request) {
		sessionCleanup(request);
		  this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		  loadBankName(request);
		  loadFundMaster(request);
		  final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
					UserSession.getCurrent().getOrganisation());
			lookUp.getOtherField();
		    this.getModel().getAccountInvestmentMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return new ModelAndView("viewInvestmentMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView investmentMasterSave(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		return new ModelAndView("viewInvestmentMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "editForm", method = RequestMethod.POST)
	public ModelAndView investmentMasterEdit(final HttpServletRequest request, @RequestParam Long invstId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);
		loadFundMaster(request);
		loadBankName(request);
		AccountInvestmentMasterDto investMasterdto = accountInvestmentService.findByBankIdInvestmentData(null, invstId,
				null,null, null,null,null, UserSession.getCurrent().getOrganisation().getOrgid() ).get(0);
		this.getModel().setAccountInvestmentMasterDto(investMasterdto);
		 final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
					UserSession.getCurrent().getOrganisation());
		    this.getModel().getAccountInvestmentMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		return new ModelAndView("viewInvestmentMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "viewInvestmentMaster", method = RequestMethod.POST)
	public ModelAndView investmentMasterView(final HttpServletRequest request, @RequestParam Long invstId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
		loadFundMaster(request);
		loadBankName(request);
	
		AccountInvestmentMasterDto investMasterdto = accountInvestmentService.findByBankIdInvestmentData(null, invstId,
				null, null,null ,null,null,UserSession.getCurrent().getOrganisation().getOrgid()).get(0);
		this.getModel().setAccountInvestmentMasterDto(investMasterdto);
		  final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
					UserSession.getCurrent().getOrganisation());
			lookUp.getOtherField();
		    this.getModel().getAccountInvestmentMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		
		return new ModelAndView("viewInvestmentMasterForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView investmentMasterSearch(final HttpServletRequest request, @RequestParam Long bankId,
			@RequestParam BigDecimal invstAmount, @RequestParam String invstNo, @RequestParam Long fundId ) {
		sessionCleanup(request);
		List<AccountInvestmentMasterDto> investMasterdtoList = accountInvestmentService.findByBankIdInvestmentData(
				invstNo ,null, bankId, invstAmount, fundId,null, null,UserSession.getCurrent().getOrganisation().getOrgid());
		for(AccountInvestmentMasterDto list : investMasterdtoList)
		{
			Long bid = list.getBankId(); 
			list.setBankName(bankName(bid));
		}
		loadFundMaster(request);
		loadBankName(request);
		// here we are setting the values in the dto so that the input fields hold the "searched" text after hitting the search button
		String bankName = bankName(bankId);
		this.getModel().getAccountInvestmentMasterDto().setBankId(bankId);
		
		this.getModel().getAccountInvestmentMasterDto().setInvstAmount(invstAmount);
		this.getModel().getAccountInvestmentMasterDto().setInvstNo(invstNo);
		this.getModel().getAccountInvestmentMasterDto().setFundId(fundId);
		this.getModel().setAcInvstDtoList(investMasterdtoList);
		
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.NEC.ADVOCATE, MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, 
				UserSession.getCurrent().getOrganisation());
		lookUp.getOtherField();
	    this.getModel().getAccountInvestmentMasterDto().setSliDate(Utility.stringToDate(lookUp.getOtherField(),"dd/MM/yyyy"));
		
		return new ModelAndView("investmentMasterSummary/Form", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "updateStatus" , method = RequestMethod.POST)
	public ModelAndView updateStatus(final HttpServletRequest request,@RequestParam String invstId)
	{
		sessionCleanup(request);
		List<AccountInvestmentMasterDto> investMasterdtoList = accountInvestmentService.findByBankIdInvestmentData(
				invstId ,null, null, null, null, null,null,UserSession.getCurrent().getOrganisation().getOrgid());
		
		for(AccountInvestmentMasterDto list : investMasterdtoList)
		{
			list.setStatus(MainetConstants.WorksManagement.CLOSED);
		}
		this.getModel().setAcInvstDtoList(investMasterdtoList);
		
		
		return new ModelAndView("investmentMasterSummary/Form", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	

}
