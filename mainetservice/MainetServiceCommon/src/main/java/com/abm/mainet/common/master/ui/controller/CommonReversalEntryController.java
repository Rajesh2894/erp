package com.abm.mainet.common.master.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bill.service.BillPaymentService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.TbServiceReceiptMasBean;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TrasactionReversalDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.ICommonReversalEntry;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.ui.model.CommonReversalEntryModel;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;

@Controller
@RequestMapping({"/CommonReversalEntry.html"})
public class CommonReversalEntryController extends  AbstractFormController<CommonReversalEntryModel>
{

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonReversalEntryController.class);
	
	@Autowired
	ICommonReversalEntry iCommonReversalEntry;

    @Resource
    private DepartmentService departmentService;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    IReceiptEntryService iReceiptEntryService;
    
    @Autowired
    private IEmployeeWardZoneMappingService employeeWardZoneMappingService;

	@RequestMapping(method = {RequestMethod.POST})
	public ModelAndView index(HttpServletRequest request) 
	{
		this.sessionCleanup(request);
		
		List<TbDepartment> deparmentList = ApplicationContextProvider.getApplicationContext()
				.getBean(TbDepartmentService.class)
				.findAllActive(UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(deparmentList)) {
			this.getModel().setDeparatmentList(deparmentList);
		}
		
		CommonReversalEntryModel model = this.getModel();
		/*
		 * DepartmentLookUp departmentLookUp = model.getDepartmentLookUp(10L);
		 * 
		 * model.getDept().setLookUpId(departmentLookUp.getLookUpId());
		 * model.getDept().setDescLangFirst(departmentLookUp.getDescLangFirst());
		 * model.getDept().setDescLangSecond(departmentLookUp.getDescLangSecond());
		 * model.getDept().getLookUpDesc();
		 */
		
		model.setTransactionType(CommonMasterUtility.getListLookup(PrefixConstants.AccountPrefix.TOS.toString(), UserSession.getCurrent().getOrganisation()));
		
		return defaultResult();
	}
	
	
	@RequestMapping(method = {RequestMethod.POST}, params = {"search"})
	public ModelAndView search(HttpServletRequest request) 
	{
		this.getModel().bind(request);
		CommonReversalEntryModel model = this.getModel();
	
		long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		Long dpDeptid = getModel().getDepId();
		String deptCode = departmentService.getDeptCode(dpDeptid);
		TrasactionReversalDTO trasactionReversalDTO = model.getTrasactionReversalDTO();
		
		
	
		if (StringUtils.isBlank(deptCode)) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("water.select.receipt.dept"));
		} else if (trasactionReversalDTO.getTransactionType() == null
				|| trasactionReversalDTO.getTransactionType().isEmpty()) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("water.select.receipt.type"));
		} else if (trasactionReversalDTO.getTransactionNo() == null) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("water.select.receipt.bill"));
		} else if (trasactionReversalDTO.getTransactionDate() == null) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("water.select.date"));

		}
		else
		{

			List<TbServiceReceiptMasBean> receiptList =iCommonReversalEntry.getReceiptByDeptAndDate(trasactionReversalDTO, orgid,dpDeptid);

			if (receiptList != null && !receiptList.isEmpty()) 
			{
				TbServiceReceiptMasBean receiptMasBean = receiptList.get(0);
				
				if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "EWZ") && MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(deptCode) && !(employeeWardZoneMappingService.checkWardZoneMappingFlag(
						UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), receiptMasBean.getCoddwzId1(),
						receiptMasBean.getCoddwzId2(), receiptMasBean.getCoddwzId3(), receiptMasBean.getCoddwzId4(),
						receiptMasBean.getCoddwzId5()))) {
					
					model.addValidationError("ward zone not mapped");
				}else {
					TbServiceReceiptMasEntity maxReceiptIdByAdditinalRefNo = null;
					if(StringUtils.isNotBlank(receiptMasBean.getFlatNo())) {
						maxReceiptIdByAdditinalRefNo = iReceiptEntryService.getLatestReceiptDetailByAddRefNoAndFlatNo(orgid, receiptMasBean.getAdditionalRefNo(), receiptMasBean.getFlatNo());
					}else {
						if(null != receiptMasBean.getAdditionalRefNo())
						maxReceiptIdByAdditinalRefNo = iReceiptEntryService.getMaxReceiptIdByAdditinalRefNo(receiptMasBean.getAdditionalRefNo());
					}
					if(null != maxReceiptIdByAdditinalRefNo && !(maxReceiptIdByAdditinalRefNo.getRmRcptid() == receiptMasBean.getRmRcptid())){
						model.addValidationError("First reverse latest receipt i.e., Receipt No = " + " "+maxReceiptIdByAdditinalRefNo.getRmRcptno());
					}else {
						if(checkLatestBillExist(deptCode, receiptMasBean)) {
							model.addValidationError("Current Year Bill Exists. Please delete bill first");
							if(getModel().hasValidationErrors()) {
								return defaultResult();
							}
						}
						if(!isChequeClearDishonour(deptCode, receiptMasBean)) {
							model.addValidationError("Cheque cleaance is pending. You cannot do receipt reversal");
							if(getModel().hasValidationErrors()) {
								return defaultResult();
							}
						}
						trasactionReversalDTO.setReferenceNo(receiptMasBean.getAdditionalRefNo());
						
						model.setTbServiceReceiptMasBean(receiptMasBean);
						
						int[] count= iCommonReversalEntry.validateReceipt(trasactionReversalDTO,orgid,dpDeptid,receiptMasBean.getRmRcptid());
						
						if (count[0] !=0 )
						{
							
							model.addValidationError("next receipt exist");
							
							
						}
						else
						{	
					
				    	      getModel().setReceiptMasList(receiptList);
						}
					}
				}
			
			   } else 
			    {
				model.addValidationError(ApplicationSession.getInstance().getMessage("water.reverse.record"));
			    }
			
			
		}
	
		return defaultResult();
	}
	
	
	@RequestMapping(params = {"back"}, method = {RequestMethod.POST})
	public ModelAndView backToSearch(HttpServletRequest request) 
	{
		 CommonReversalEntryModel model = this.getModel();
	     model.bind(request);
		 return defaultResult();
	}
	
	
	@RequestMapping(params = {"reverse"}, method = {RequestMethod.POST})
	public ModelAndView reverse(HttpServletRequest request) 
	{
		  CommonReversalEntryModel model = this.getModel();
	      model.bind(request);
	     
	     
	      List<TbServiceReceiptMasBean> receiptMasList = model.getReceiptMasList();
	      
	      receiptMasList.get(0).setReceiptDelRemark(model.getTbServiceReceiptMasBean().getReceiptDelRemark());
	      model.setTbServiceReceiptMasBean(receiptMasList.get(0));
	      
	      
	      TbServiceReceiptMasBean ReceiptMasBean = model.getTbServiceReceiptMasBean();
	     
	      Long deptId=null;
	     
	    try {
	            BillPaymentService dynamicServiceInstance = null;
	            String serviceClassName = null;
	            
	            Long empId = UserSession.getCurrent().getEmployee().getEmpId();
	            String ipAddress = UserSession.getCurrent().getEmployee().getEmppiservername();
	            
	            long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	        
	            deptId = ReceiptMasBean.getDpDeptId();
	            final String deptCode = departmentService.getDeptCode(deptId);
	            serviceClassName =messageSource.getMessage(
	                    MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
	                    StringUtils.EMPTY, Locale.ENGLISH);
	            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
	                    BillPaymentService.class);
	         
	            
	         VoucherPostDTO receiptReversalDTO = dynamicServiceInstance.reverseBill(ReceiptMasBean,orgid,empId,ipAddress);
	         
	         
	         List<VoucherPostDetailDTO> voucherDetails = receiptReversalDTO.getVoucherDetails();
	        
	         String sliActiveFlag = "N";
             final LookUp accountActiveLookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL,
                     MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, UserSession.getCurrent().getOrganisation());
             if (accountActiveLookUp != null) {
            	 sliActiveFlag = accountActiveLookUp.getDefaultVal();
             }
             if(StringUtils.equals(sliActiveFlag, "N")) {
            	 List<TbServiceReceiptMasBean> ReceiptMasBeandtoList = new ArrayList<TbServiceReceiptMasBean>();
    	         List<TbServiceReceiptMasBean> rebateReceiptList = iReceiptEntryService.findReceiptByReceiptDateType(ReceiptMasBean.getRmRcptid(), orgid, ReceiptMasBean.getRmDate(), ReceiptMasBean.getDpDeptId(), "RB");
    	         ReceiptMasBeandtoList.add(ReceiptMasBean);
    	         ReceiptMasBeandtoList.addAll(rebateReceiptList);
    	         ReceiptMasBeandtoList.forEach(ReceiptMasBeandto ->{
    	        	 iCommonReversalEntry.updateReceipt(ReceiptMasBeandto, orgid,UserSession.getCurrent().getEmployee().getEmpId());
    	         });
            		 return jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("Receipt.Reversal.Done.Successfully")));
             }
             
	            
	            if(voucherDetails != null)
	            {
	            	
	            	
	            	
	            	
	              VoucherPostDTO voucherPostDTO =new VoucherPostDTO();
	            	
	              Organisation org = UserSession.getCurrent().getOrganisation();
	            	
	              LookUp PrefixLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
	                        MainetConstants.BILL_MASTER_COMMON.TAX_BILL_RECEIPT_REVERSAL,
	                        MainetConstants.BILL_MASTER_COMMON.ACCOUNT_TEMPLATE_FOR_PREFIX, org);
	            	
	            	
	            	voucherPostDTO.setVoucherDate(new Date());
	            	
	            	voucherPostDTO.setVoucherType(null);
	            	
	            	voucherPostDTO.setVoucherSubTypeId(PrefixLookUp.getLookUpId());
	            	
	            	voucherPostDTO.setDepartmentId(deptId);
	            	
	            	voucherPostDTO.setVoucherReferenceNo(String.valueOf(ReceiptMasBean.getRmRcptno()));
	            	
	            	
	            	voucherPostDTO.setVoucherReferenceDate(ReceiptMasBean.getRmDate());
	            	
	            	voucherPostDTO.setNarration(ReceiptMasBean.getReceiptDelRemark());
	            	
	            	
	            	voucherPostDTO.setPayerOrPayee(ReceiptMasBean.getRmReceivedfrom());
	            	
	            	voucherPostDTO.setFieldId(ReceiptMasBean.getTbAcFieldMaster().getFieldId());
	            	
	            	voucherPostDTO.setOrgId(orgid);
	            	
	            	voucherPostDTO.setCreatedBy(ReceiptMasBean.getCreatedBy());
	            	
	            	voucherPostDTO.setCreatedDate(new Date());
	            	
	            	
	            	voucherPostDTO.setLangId(ReceiptMasBean.getLangId());
	            	
	            	voucherPostDTO.setLgIpMac(ReceiptMasBean.getLgIpMac());
	            	
	            	voucherPostDTO.setEntryType(MainetConstants.BILL_MASTER_COMMON.ENTRY_TYPE);
	            	
	            	voucherPostDTO.setBillVouPostingDate(null);
	            	
	            	voucherPostDTO.setPayEntryMakerFlag(null);
	            	
	            	voucherPostDTO.setAuthFlag(null);
	            	
	            	voucherPostDTO.setTemplateType(null);
	            	
	            	voucherPostDTO.setFinancialYearId(null);
	            	
	            	voucherPostDTO.setPayModeId(ReceiptMasBean.getModeId());
	            	
	            	voucherPostDTO.setEntryFlag(null);
	            	
	            	List<VoucherPostDetailDTO> voucherPostDetails = new ArrayList<>();
	            	
	            	
	            	for (VoucherPostDetailDTO voucherPostDetail : voucherDetails) 
	            	{
						
	            		
	            		VoucherPostDetailDTO VoucherPostDetaildto=new VoucherPostDetailDTO();
	            		
	            		VoucherPostDetaildto.setVoucherAmount(voucherPostDetail.getVoucherAmount());
	            		
	            		VoucherPostDetaildto.setSacHeadId(voucherPostDetail.getSacHeadId());
	            		
	            		VoucherPostDetaildto.setYearId(voucherPostDetail.getYearId());
	            		
	            		VoucherPostDetaildto.setDemandTypeId(voucherPostDetail.getDemandTypeId());
	            		
	            		VoucherPostDetaildto.setDrCrId(null);
	            		
	            		VoucherPostDetaildto.setPayModeId(null);
	            		
	            		VoucherPostDetaildto.setAccountHeadFlag(null);
	            		
	            		voucherPostDetails.add(VoucherPostDetaildto);
	            		
					}
	            	
	            	
	            	 voucherPostDTO.setVoucherDetails(voucherPostDetails);
	            	
	            	 List<VoucherPostDTO> accountPostingList = new ArrayList<>();
	            	 
	            	 
	            	 String activeFlag = null;
	                 final LookUp accountActive = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagL,
	                         MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE, org);
	                 if (accountActive != null) {
	                     activeFlag = accountActive.getDefaultVal();
	                 }
	            	 
	            	try {
	            		iReceiptEntryService.doAccountVoucherPosting(ReceiptMasBean.getReceiptDelRemark(), accountPostingList, voucherPostDTO, activeFlag);
	            	}catch (Exception e) {
	            		LOGGER.error("Exception occured while receipt reversal account posting" + e);
					}
	            	
	            	Long userId = UserSession.getCurrent().getEmployee().getEmpId();
	            	
	            	ReceiptMasBean.setLgIpMacUpd(ipAddress);
	            	
	            	 List<TbServiceReceiptMasBean> ReceiptMasBeandtoList = new ArrayList<TbServiceReceiptMasBean>();
	    	         List<TbServiceReceiptMasBean> rebateReceiptList = iReceiptEntryService.findReceiptByReceiptDateType(ReceiptMasBean.getRmRcptid(), orgid, ReceiptMasBean.getRmDate(), ReceiptMasBean.getDpDeptId(), "RB");
	    	         ReceiptMasBeandtoList.add(ReceiptMasBean);
	    	         ReceiptMasBeandtoList.addAll(rebateReceiptList);
	    	         ReceiptMasBeandtoList.forEach(ReceiptMasBeandto ->{
	    	        	 ReceiptMasBeandto.setReceiptDelRemark(ReceiptMasBean.getReceiptDelRemark());
	    	        	 iCommonReversalEntry.updateReceipt(ReceiptMasBeandto, orgid,UserSession.getCurrent().getEmployee().getEmpId());
	    	         });
	            	
	            	return jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("Receipt.Reversal.Done.Successfully")));
	            }
	            else
	            {
	            	model.addValidationError("Not a valid Receipt");
	            	
	            }
	             
	        } 
	       catch (LinkageError | Exception e) 
	         {
	            throw new FrameworkException("Exception in UpdateBill for department Id :" + deptId, e);
	         }
	    
		    return defaultResult();
	
	}
	
	private boolean checkLatestBillExist(String deptCode, TbServiceReceiptMasBean receiptMasBean) {
		boolean flag = false;
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")) {
			BillPaymentService dynamicServiceInstance = null;
            String serviceClassName = null;
            serviceClassName =messageSource.getMessage(
                    MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillPaymentService.class);
            
			TbBillMas latestYearBill = dynamicServiceInstance.getLatestYearBill(receiptMasBean.getAdditionalRefNo(), UserSession.getCurrent().getOrganisation().getOrgid());
			if(latestYearBill != null) {
				// receiptMasBean.getRmDate().after(latestYearBill.getBmBilldt())
				if(!Utility.compareDate(latestYearBill.getBmBilldt(), receiptMasBean.getRmDate()) ) {
					flag = true;
				}
			}
		}
		
		return flag;
	}
	
	private boolean isChequeClearDishonour(String deptCode, TbServiceReceiptMasBean receiptMasBean) {
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), "SKDCL")
				&& MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(deptCode)) {
			BillPaymentService dynamicServiceInstance = null;
            String serviceClassName = null;
            serviceClassName =messageSource.getMessage(
                    MainetConstants.CHALLAN_BILL + deptCode, new Object[] {},
                    StringUtils.EMPTY, Locale.ENGLISH);
            dynamicServiceInstance = ApplicationContextProvider.getApplicationContext().getBean(serviceClassName,
                    BillPaymentService.class);
            
			return dynamicServiceInstance.isChequeClearDishonour(receiptMasBean, UserSession.getCurrent().getOrganisation().getOrgid());
			
		}
		
		return true;
	}
	
}
