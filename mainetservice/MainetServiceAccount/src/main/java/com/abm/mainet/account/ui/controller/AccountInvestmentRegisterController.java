/**
 * @author Rahul S Chaubey
 * @since December 2019
 */

package com.abm.mainet.account.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.service.AccountInvestmentService;
import com.abm.mainet.account.ui.model.AccountInvestmentMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;


@Controller
@RequestMapping("/investmentRegister.html")
public class AccountInvestmentRegisterController extends AbstractFormController<AccountInvestmentMasterModel>
{
		@Resource 
		AccountFinancialReportService accountFinancialReportService;
		
		@Resource
		AccountInvestmentService accountInvestmentService;
	
		@Resource
		private TbAcCodingstructureMasService tbAcCodingstructureMasService;
		@Resource
		private TbOrganisationService tbOrganisationService;
		
		@Resource
		private IEmployeeService employeeService;
		
	   	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	    public ModelAndView index(final Model model, final HttpServletRequest request) 
	    {
	        sessionCleanup(request);
	        loadFundMaster(request);
	        //loadInvestmentNumber(request);
	        return index();
	    }
	   	
	   	public void loadInvestmentNumber(HttpServletRequest httpServletRequest)
	   	{
	   		// get the list from the query 
	   		List<String> invstId = accountFinancialReportService.getInvestmentId(UserSession.getCurrent().getOrganisation().getOrgid());
	   		this.getModel().setInvstId(invstId);
	   	}
	   	
	   	@ResponseBody
	    @RequestMapping(params = "InvestNumber", method = { RequestMethod.POST })
	   	public List<String> loadInvestmentNumberFromFundId(HttpServletRequest httpServletRequest,@RequestParam("fundId")Long fundId)
	   	{
	   		// get the list from the query 
	   		List<String> invstId = accountFinancialReportService.getInvestmentId(UserSession.getCurrent().getOrganisation().getOrgid());
	   		this.getModel().setInvstId(invstId);
			return invstId;
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
	    @RequestMapping(params = "report", method = { RequestMethod.POST })
	    public ModelAndView report(final HttpServletRequest httpServletRequest, @RequestParam("invstNo")String invstNo,
	    		 @RequestParam("fundId")Long fundId,  @RequestParam("regFromDate")String fromDate,  @RequestParam("regToDate")String toDate, @RequestParam("fundName") String fundName) throws IllegalAccessException, InvocationTargetException
	    {
	   		String from = null, to = null;
	   		List<AccountInvestmentMasterDto> investMasterdtoList = accountInvestmentService.findByBankIdInvestmentData(
					invstNo ,null, null, null, fundId, Utility.stringToDate(fromDate), Utility.stringToDate(toDate),UserSession.getCurrent().getOrganisation().getOrgid());
	   		
	   		TbServiceReceiptMasBean dto = new TbServiceReceiptMasBean();
	   		
	   		int count = 0;
	   		BigDecimal totPurchasePrice = BigDecimal.ZERO;
			BigDecimal totAmtInteresDue = BigDecimal.ZERO;
			BigDecimal totAmtInteresRecovered =  BigDecimal.ZERO;
			BigDecimal totAmtReleased = BigDecimal.ZERO;
	   		List<AccountInvestmentMasterDto> reportList = new ArrayList<AccountInvestmentMasterDto>();
	   		while(count < investMasterdtoList.size())
	   		{
	   			AccountInvestmentMasterDto investmentMasterDto = investMasterdtoList.get(count);
	   			List<TbServiceReceiptMasEntity> receiptEntity= accountFinancialReportService.receiptsForRegister(investmentMasterDto.getOrgId(),
	   																						investmentMasterDto.getInvstId(),"INV");
	   			if(receiptEntity != null && !receiptEntity.isEmpty())
	   					BeanUtils.copyProperties(receiptEntity.get(0), investmentMasterDto);
	   			if(investmentMasterDto.getInvstAmount()!=null)
	   			totPurchasePrice=totPurchasePrice.add(investmentMasterDto.getInvstAmount());
	   			if(investmentMasterDto.getInstAmt()!=null)
	   			totAmtInteresDue=totAmtInteresDue.add(investmentMasterDto.getInstAmt());
			if (investmentMasterDto.getRmAmount() != null) {
				totAmtInteresRecovered = totAmtInteresRecovered.add(investmentMasterDto.getRmAmount());
				totAmtReleased = totAmtReleased.add(investmentMasterDto.getRmAmount());
			}
	   			reportList.add(investmentMasterDto);
	   			
	   			count++;
	   			
	   		}
	   		this.getModel().getAccountInvestmentMasterDto().setTotPurchasePrice(totPurchasePrice);
	   		this.getModel().getAccountInvestmentMasterDto().setTotAmtInteresDue(totAmtInteresDue);
	   		this.getModel().getAccountInvestmentMasterDto().setTotAmtInteresRecovered(totAmtInteresRecovered);
	   		this.getModel().getAccountInvestmentMasterDto().setTotAmtReleased(totAmtReleased);
	   		this.getModel().setAcInvstDtoList(reportList);
	   		
	   		if(!fundName.isEmpty() &&!fundName.contains("Select") )
	   		{
	   			this.getModel().getAccountInvestmentMasterDto().setFundName(ApplicationSession.getInstance().getMessage("account.viewBill.FundName")+":"+fundName);
	   		}
	   		if(fundName.contains("Select"))
	   		{
	   			this.getModel().getAccountInvestmentMasterDto().setFundName("");
	   		}
	   		if(!fromDate.isEmpty())
	   		{
	   			this.getModel().getAccountInvestmentMasterDto().setInvdate(ApplicationSession.getInstance().getMessage("account.fromDate")+":"+fromDate);
		   		this.getModel().getAccountInvestmentMasterDto().setInvDueDate(ApplicationSession.getInstance().getMessage("account.todate")+":"+toDate);
	   		}
	   		if(!invstNo.isEmpty())
	   		{
	   			this.getModel().getAccountInvestmentMasterDto().setInvstNo("Investment No.:"+invstNo);
	   		}
	   		this.getModel().getAccountInvestmentMasterDto().setFundId(fundId);
	   		if(CollectionUtils.isNotEmpty(investMasterdtoList)) {
	   		Employee emp  = employeeService.findEmployeeByIdAndOrgId(investMasterdtoList.get(0).getCreatedBy(), UserSession.getCurrent().getOrganisation().getOrgid());
	   		this.getModel().setEmpName(emp.getEmpname().toString()+" "+emp.getEmplname().toString());
	   		}
	   		return new ModelAndView("investmentRegister/Form",MainetConstants.FORM_NAME,this.getModel());
	    }
	    
}
