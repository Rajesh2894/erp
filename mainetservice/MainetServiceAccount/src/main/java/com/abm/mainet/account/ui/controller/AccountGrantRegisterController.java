/**
 * @author Rahul S Chaubey
 * @since December 2019
 */

package com.abm.mainet.account.ui.controller;

import java.text.DateFormat;
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
import org.apache.commons.lang.StringUtils;
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
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.service.AccountGrantMasterService;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.account.service.PaymentEntrySrevice;
import com.abm.mainet.account.ui.model.AccountGrantMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

import javaxt.utils.Array;

@Controller
@RequestMapping("/grantRegister.html")
public class AccountGrantRegisterController extends AbstractFormController<AccountGrantMasterModel>
{
	@Resource
	SecondaryheadMasterService secondaryHeadMasterService;
	
	@Resource
	AccountFinancialReportService accountFinancialReportService;
	
	@Resource
	AccountGrantMasterService accountGrantMasterService;
	
	@Resource
	SecondaryheadMasterService  secondaryheadMasterService;
	
	@Resource
	private TbFinancialyearService tbfinancialYearService;
	
	@Resource
	private  AccountBillEntryService accountBillEntryService; 
	
	@Resource
	private PaymentEntrySrevice paymentEntrySrevice;
	
	@Resource
	private AccountReceiptEntryService accountReceiptEntryService;

 

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
		loadGrantName(request);
		financialYear(request);
		return index();
	}

	private void financialYear(HttpServletRequest httpServletRequest) {
		this.getModel().setListOfinalcialyear(tbfinancialYearService.getAllFinincialYearStatusOpen( UserSession.getCurrent().getOrganisation().getOrgid()));
	}
	
	public void loadGrantName(HttpServletRequest httpServletRequest)
   	{
   		// get the list from the query 
		 Map<String, String> grtName = accountFinancialReportService.getGrantName(UserSession.getCurrent().getOrganisation().getOrgid());
   		this.getModel().setGrtName(grtName);
   	}
	
	
	public String getYear(Long faYearId)
	{
		Map<Long,String> financialYear = tbfinancialYearService.getAllFinincialYearByStatusWise(UserSession.getCurrent().getOrganisation().getOrgid());
		
		String fYear = financialYear.entrySet().stream().filter(map ->
		  map.getKey().equals(faYearId)) .map(map ->
		  map.getValue()).collect(Collectors.joining());
		
		return fYear;
	
	}
	
	
	
	@ResponseBody
    @RequestMapping(params = "report", method = { RequestMethod.POST })
    public ModelAndView report(final HttpServletRequest httpServletRequest, @RequestParam("grtName")String grtName,
    		 @RequestParam("faYearId")String faYearId, @RequestParam("faYearName")String faYearName ,
    		 @RequestParam("regFromDate")Date frmDate, @RequestParam("regToDate")Date toDate, @RequestParam("fullGrantName")String fullGrantName)
    {
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		  List<AccountGrantMasterDto> accountGrantMasterDto = null;
		accountGrantMasterDto = accountGrantMasterService.findByNameAndNature(null,grtName, null, null,faYearId,frmDate,toDate, orgid);
		 
		if(faYearId!=null)
		{
			for(AccountGrantMasterDto tempDto : accountGrantMasterDto )
			{
				tempDto.setFromYear(getYear(accountGrantMasterDto.get(0).getFromPerd()));
				tempDto.setToYear(getYear(accountGrantMasterDto.get(0).getToPerd()));
			}
		}
		 //#85070
		if(CollectionUtils.isNotEmpty(accountGrantMasterDto)) {
		  accountGrantMasterDto.forEach(granddto->{
			List<String[]> BillDetailAginstUtilize=new ArrayList<>();	 
			List<String[]> BillDetailAginstRefund=new ArrayList<>();	 
			
		    List<AccountBillEntryMasterBean> refundDetail = accountBillEntryService.getBillDetailsByIntRefIdAndOrgId(granddto.getGrntId(), orgid);
		    List<Object[]> receiptDetail = accountReceiptEntryService.findDataOfReceipt(MainetConstants.AccountFinancialReport.GRT, granddto.getGrntId(), orgid);
			if(CollectionUtils.isNotEmpty(refundDetail))  {
			      refundDetail.forEach(refundDto->{
			    	  String[] BillDetail=new String[2];
			    	  String[] BillDetailRefund=new String[2];
				 if(refundDto.getBillTypeCode().equals(MainetConstants.AccountFinancialReport.GFD)) {
					if(refundDto.getBillIntRefId().equals(granddto.getGrntId())) {
						 BillDetailRefund[0]=String.valueOf(refundDto.getBillBalanceAmt());
						 try { 
						 BillDetailRefund[1]=String.valueOf(Utility.dateToString(new SimpleDateFormat(MainetConstants.DATE_FORMATS).parse(refundDto.getBillEntryDate())));
						} catch (ParseException e) {
						}
						 BillDetailAginstRefund.add(BillDetailRefund);
				   }
				 }
				 if(refundDto.getBillTypeCode().equals(MainetConstants.AccountFinancialReport.GRT)) {
					 BillDetail[0]=String.valueOf(refundDto.getBillBalanceAmt());
					 BillDetailAginstUtilize.add(BillDetail);
					 List<PaymentEntryDto> paymentDetail = paymentEntrySrevice.getPaymentDetailByBillId(refundDto.getId(), orgid);
					 if(CollectionUtils.isNotEmpty(paymentDetail)) {
						granddto.setPaymentList(paymentDetail);
					 } 
				 }
			  });
			      granddto.setBillDetailAgainstUtilize(BillDetailAginstUtilize); 
			      granddto.setBillDetailAgainstRefund(BillDetailAginstRefund);
			}
		   if(CollectionUtils.isNotEmpty(receiptDetail)) {
			      List<String[]> advacnceGrant=new ArrayList<>();
					 for(Object[] reciptDto:receiptDetail) {
						 String[] array=new String[2];
						 if(reciptDto[2]!=null) {
							 array[0]=String.valueOf(new SimpleDateFormat(MainetConstants.DATE_FORMAT)
						                .format((Date)reciptDto[2]));
						 }
						 if(reciptDto[3]!=null) {
							 array[1]=String.valueOf(reciptDto[3]);
						 }
						 advacnceGrant.add(array);
					 }
					 granddto.setAdavancedGrandDetail(advacnceGrant);
				 }   
		 });
		}
		this.getModel().setAccountGrantMasterDtoList(accountGrantMasterDto);
		if(!faYearName.contains("Select"))
		{this.getModel().setFaYearName(ApplicationSession.getInstance().getMessage("account.budgetopenmaster.financialyear")+faYearName);}
		if(!grtName.isEmpty())
		{this.getModel().getAccountGrantMasterDto().setGrtName("Grant Name :"+grtName);}
		if(frmDate!= null )
		{this.getModel().getAccountGrantMasterDto().setGrtName(ApplicationSession.getInstance().getMessage("account.fromDate")+":"+Utility.dateToString(frmDate, "dd/MM/yyy")+" "+ApplicationSession.getInstance().getMessage("account.todate")+":"+Utility.dateToString(toDate, "dd/MM/yyy"));}
   		return new ModelAndView("grantRegister/Form",MainetConstants.FORM_NAME,this.getModel());
   		
    }
	
}
