/**
 * 
 */
package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.additionalservices.constant.EChallanConstant;
import com.abm.mainet.additionalservices.dto.EChallanItemDetailsDto;
import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.additionalservices.service.EChallanEntryService;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import org.apache.commons.lang.StringUtils;
/**
 * @author divya.marshettiwar
 *
 */
@Component
@Scope("session")
public class EChallanEntryModel extends AbstractFormModel{

	private static final long serialVersionUID = -7735089394929549698L;
	
	@Autowired
	private EChallanEntryService eChallanService;
	
	@Resource
    private IFileUploadService fileUpload;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;
	
	@Autowired
    private IChallanService iChallanService;
	
	@Autowired
	private ServiceMasterService seviceMasterService;
	
	@Autowired
    private ServiceMasterService serviceMasterService;

	private EChallanMasterDto challanMasterDto = new EChallanMasterDto();
	private List<EChallanMasterDto> challanMasterDtoList = new ArrayList();
	private EChallanItemDetailsDto challanItemDetailsDto = new EChallanItemDetailsDto();
	private List<EChallanItemDetailsDto> challanItemDetailsDtoList = new ArrayList();
	
	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();
	private ServiceMaster serviceMaster = new ServiceMaster();
	private double amountToPay;
	private String formType;
	private String removeItemIds;
	
	private List<DocumentDetailsVO> checkList;
	private String saveMode;
	
	private List<CFCAttachment> documentList = new ArrayList<>();
	
	public EChallanMasterDto getChallanMasterDto() {
		return challanMasterDto;
	}
	public void setChallanMasterDto(EChallanMasterDto challanMasterDto) {
		this.challanMasterDto = challanMasterDto;
	}
	public List<EChallanMasterDto> getChallanMasterDtoList() {
		return challanMasterDtoList;
	}
	public void setChallanMasterDtoList(List<EChallanMasterDto> challanMasterDtoList) {
		this.challanMasterDtoList = challanMasterDtoList;
	}
	public EChallanItemDetailsDto getChallanItemDetailsDto() {
		return challanItemDetailsDto;
	}
	public void setChallanItemDetailsDto(EChallanItemDetailsDto challanItemDetailsDto) {
		this.challanItemDetailsDto = challanItemDetailsDto;
	}
	public List<EChallanItemDetailsDto> getChallanItemDetailsDtoList() {
		return challanItemDetailsDtoList;
	}
	public void setChallanItemDetailsDtoList(List<EChallanItemDetailsDto> challanItemDetailsDtoList) {
		this.challanItemDetailsDtoList = challanItemDetailsDtoList;
	}
	
	public EChallanEntryService geteChallanService() {
		return eChallanService;
	}
	public void seteChallanService(EChallanEntryService eChallanService) {
		this.eChallanService = eChallanService;
	}
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	public IFileUploadService getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}
	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}
	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}
	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}
	public double getAmountToPay() {
		return amountToPay;
	}
	public void setAmountToPay(double amountToPay) {
		this.amountToPay = amountToPay;
	}
	public TbTaxMasService getTbTaxMasService() {
		return tbTaxMasService;
	}
	public void setTbTaxMasService(TbTaxMasService tbTaxMasService) {
		this.tbTaxMasService = tbTaxMasService;
	}
	public IChallanService getiChallanService() {
		return iChallanService;
	}
	public void setiChallanService(IChallanService iChallanService) {
		this.iChallanService = iChallanService;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}
	public String getRemoveItemIds() {
		return removeItemIds;
	}
	public void setRemoveItemIds(String removeItemIds) {
		this.removeItemIds = removeItemIds;
	}
	
	@Override
	public boolean saveForm() {
		CommonChallanDTO offline = getOfflineDTO();
		boolean status = false;
		boolean challanStatus = false;
		String menuUrl = null;
		List<Long> removeItemIdsList = new ArrayList<>();
		
		EChallanMasterDto challanDto = getChallanMasterDto();
		final UserSession session = UserSession.getCurrent();
		
		ServiceMaster serviceMas = seviceMasterService.getServiceByShortName(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE,
				session.getOrganisation().getOrgid());
		setServiceMaster(serviceMas);
		
		// to save data and for receipt
		if (EChallanConstant.PAY.equalsIgnoreCase(formType)) {
			
			validateBean(offline, CommonOfflineMasterValidator.class);
			 if ((getOfflineDTO().getOnlineOfflineCheck() == null) || getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
		            addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.PAYMENT_MODE)));
		     }
			 if (hasValidationErrors()) {
		            return false;
		     }
			 
			 challanStatus = setAndSaveChallanDto(offline, challanDto);
			 challanMasterDto.setStatus("Y");
			 eChallanService.updatePaymentStatus(challanMasterDto.getChallanId(), challanMasterDto.getStatus(), challanMasterDto.getOrgid());
	
			 // for SMS and Email
			 menuUrl = MainetConstants.EncroachmentChallan.ECHALLAN_PAYMENT_SMS_URL;
			 sendSmsEmail(this, menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);
			 
			 setSuccessMessage(ApplicationSession.getInstance().getMessage("Echallan.raidNumSuccessPayment") + " "
						+ challanMasterDto.getRaidNo());
			 		 
			 return challanStatus;
		} else {

			if (challanMasterDto.getChallanId() == null) {
				challanMasterDto.setOrgid(session.getOrganisation().getOrgid());
				challanMasterDto.setLgIpMacUpd(getClientIpAddress());
				challanMasterDto.setCreatedDate(new Date());
				challanMasterDto.setCreatedBy(session.getEmployee().getEmpId());
				challanMasterDto.setUpdatedDate(new Date());
				challanMasterDto.setUpdatedBy(session.getEmployee().getEmpId());
				challanMasterDto.setLgIpMac(getClientIpAddress());
				challanMasterDto.setLangId((long) session.getLanguageId());
				challanMasterDto.setServiceId(serviceMas.getSmServiceId());
				challanMasterDto.setDeptId(serviceMas.getTbDepartment().getDpDeptid());

				if (challanMasterDto.getEchallanItemDetDto() != null) {
					for (EChallanItemDetailsDto itemDetails : challanItemDetailsDtoList) {
						itemDetails.setOrgid(session.getOrganisation().getOrgid());
						itemDetails.setLgIpMacUpd(getClientIpAddress());
						itemDetails.setCreatedDate(new Date());
						itemDetails.setCreatedBy(session.getEmployee().getEmpId());
						itemDetails.setUpdatedDate(new Date());
						itemDetails.setUpdatedBy(session.getEmployee().getEmpId());
						itemDetails.setLgIpMac(getClientIpAddress());
						itemDetails.setLangId((long) session.getLanguageId());
						itemDetails.setServiceId(serviceMas.getSmServiceId());
						itemDetails.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
					}
				}
				List<DocumentDetailsVO> docs =new ArrayList<>();
		        docs=fileUpload.setFileUploadMethod(docs);
		        challanMasterDto.setDocList(docs);
		        if(StringUtils.isBlank(challanMasterDto.getFromArea())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateChallanFromArea")));
		        }
		        
		        if(StringUtils.isBlank(challanMasterDto.getToArea())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateChallanToArea")));
		        }
		        
		        if(StringUtils.isBlank(challanMasterDto.getOfficerOnsite())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateChallanOfficerAvailableOnsite")));
		        }
		        
		        if(StringUtils.isBlank(challanMasterDto.getChallanDesc())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateChallanViolationPurpose")));
		        }
		        
		        if(StringUtils.isBlank(challanMasterDto.getLocality())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateLocality")));
		        }
		        
		        if(!challanMasterDto.getChallanType().equalsIgnoreCase("OS")) {
		        if(StringUtils.isBlank(getChallanItemDetailsDtoList().get(0).getStoreId())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.Valid.StoreLocation")));
		        }
		        }
		        
		        if(StringUtils.isBlank(challanMasterDto.getOffenderName())) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.validateChallanOffenderName")));
		        }
		        
		        if(challanMasterDto.getDocList().isEmpty()) {
		        	addValidationError(getAppSession()
		                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.uploadFile")));
		        	//return false;
		        }
		       // return false;
			}
			 if (hasValidationErrors()) {
		            return false;
		     }
			
			if (challanMasterDto.getChallanType().equals("OS")) {

				validateBean(offline, CommonOfflineMasterValidator.class);
				 if ((getOfflineDTO().getOnlineOfflineCheck() == null) || getOfflineDTO().getOnlineOfflineCheck().isEmpty()) {
			            addValidationError(getAppSession()
			                    .getMessage(ApplicationSession.getInstance().getMessage(MainetConstants.EstateBooking.PAYMENT_MODE)));
			     }
				 if (hasValidationErrors()) {
			            return false;
			     }
				
				/*
				 * List<DocumentDetailsVO> docs =new ArrayList<>();
				 * docs=fileUpload.setFileUploadMethod(docs); challanMasterDto.setDocList(docs);
				 */
			        
				String generateChallanNumber = eChallanService
						.generateChallanNumber(UserSession.getCurrent().getOrganisation(), challanItemDetailsDtoList);
				challanMasterDto.setChallanNo(generateChallanNumber);
				
				// to save the item table data
				//String generateRaidNumber = eChallanService
						//.generateRaidNumber(UserSession.getCurrent().getOrganisation(), challanItemDetailsDtoList);
				//challanMasterDto.setRaidNo(generateRaidNumber);
				
				setSuccessMessage(ApplicationSession.getInstance().getMessage("EChallan.challanNumber") + " "
						+ challanMasterDto.getChallanNo() + " "
						+ ApplicationSession.getInstance().getMessage("EChallan.savedSuccessfully"));
				
				challanStatus = setAndSaveChallanDto(offline, challanDto);
				challanMasterDto.setStatus("Y");
			} else {
				if(challanMasterDto.getRaidNo() == null) {
					
					/*
					 * List<DocumentDetailsVO> docs =new ArrayList<>();
					 * docs=fileUpload.setFileUploadMethod(docs); challanMasterDto.setDocList(docs);
					 */
			        
					String generateRaidNumber = eChallanService
							.generateRaidNumber(UserSession.getCurrent().getOrganisation(), challanItemDetailsDtoList);
					challanMasterDto.setRaidNo(generateRaidNumber);
					
					setSuccessMessage(ApplicationSession.getInstance()
			                .getMessage(ApplicationSession.getInstance().getMessage("EChallan.raidNumber") + " " + challanMasterDto.getRaidNo()
			                        + " " + ApplicationSession.getInstance().getMessage("EChallan.savedSuccessfully")));
					challanMasterDto.setStatus("N");
										 
				}else {
					if(getSaveMode().equals("E")) {
						
						for (EChallanItemDetailsDto itemDetails : challanMasterDto.getEchallanItemDetDto()) {
							itemDetails.setOrgid(session.getOrganisation().getOrgid());
							itemDetails.setLgIpMacUpd(getClientIpAddress());
							itemDetails.setUpdatedDate(new Date());
							itemDetails.setUpdatedBy(session.getEmployee().getEmpId());
							itemDetails.setLgIpMac(getClientIpAddress());
							itemDetails.setLangId((long) session.getLanguageId());
							itemDetails.setServiceId(serviceMas.getSmServiceId());
							itemDetails.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
						}
						
						final String prDetIds = getRemoveItemIds();
						if (null != prDetIds && !prDetIds.isEmpty()) {
							final String array[] = prDetIds.split(",");
							for (final String string : array) {
								removeItemIdsList.add(Long.valueOf(string));
							}
						}
						
						eChallanService.saveEChallanEntry(challanMasterDto, challanMasterDto.getEchallanItemDetDto(), removeItemIdsList);
						setSuccessMessage(ApplicationSession.getInstance()
				                .getMessage(ApplicationSession.getInstance().getMessage("EChallan.raidNumber") + MainetConstants.WHITE_SPACE + challanMasterDto.getRaidNo()
				                        + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("EChallan.savedSuccessfully")));
						return true;
					}
					boolean duplicateReceipt = false;
					duplicateReceipt = eChallanService.findFromReceiptMaster(challanMasterDto.getOrgid(), Long.parseLong(challanMasterDto.getRaidNo()));
					if(duplicateReceipt == true) {
						challanStatus = setAndSaveChallanDto(offline, challanDto);
						//challanMasterDto.setStatus("Y");
					}else {
						addValidationError(getAppSession()
			                    .getMessage(ApplicationSession.getInstance().getMessage("EChallan.paymentAlreadyDone")));
						return false;
					}
					
				}
				
			}
			
			challanMasterDto.setChallanDate(new Date());
			eChallanService.saveEChallanEntry(challanMasterDto, challanItemDetailsDtoList, null);
						
			//For SMS and Email
	        menuUrl = MainetConstants.EncroachmentChallan.ECHALLAN_ENTRY_SMS_URL;        
			sendSmsEmail(this, menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED);
			if(challanMasterDto.getChallanType().equals("OS")) {
				setSuccessMessage(ApplicationSession.getInstance()
		                .getMessage(ApplicationSession.getInstance().getMessage("EChallan.challanNo") + MainetConstants.WHITE_SPACE + challanMasterDto.getChallanNo()
		                        + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("EChallan.savedSuccessfully")));
			}else {
				
				setSuccessMessage(ApplicationSession.getInstance()
		                .getMessage(ApplicationSession.getInstance().getMessage("EChallan.raidNumber") + MainetConstants.WHITE_SPACE + challanMasterDto.getRaidNo()
		                        + MainetConstants.WHITE_SPACE + ApplicationSession.getInstance().getMessage("EChallan.savedSuccessfully")));
			}
		}
		      
		status = true;
		return status;
	}
	
	// for payment
    private boolean setAndSaveChallanDto(CommonChallanDTO offline, EChallanMasterDto challanDto) {
    	
    	 if(challanDto.getChallanType().equals("OS")) {
    		 offline.setUniquePrimaryId(challanDto.getChallanNo());
    		 offline.setApplNo(Long.parseLong(challanDto.getChallanNo()));
    	 }else {
    		 offline.setUniquePrimaryId(challanDto.getRaidNo());
    		 offline.setApplNo(Long.parseLong(challanDto.getRaidNo()));
    		 
    	 }
    	 offline.setDeptCode(MainetConstants.EncroachmentChallan.CHALLAN_DEPT_SHORT_CODE);
    	 offline.setAmountToPay(Double.toString(challanDto.getChallanAmt()));
         offline.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
         offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
         offline.setLangId(UserSession.getCurrent().getLanguageId());
         offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
         offline.setFaYearId(UserSession.getCurrent().getFinYearId());
         offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
         offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
         offline.setServiceId(getServiceMaster().getSmServiceId());
         offline.setApplicantName(challanDto.getOffenderName());
         offline.setMobileNumber(challanDto.getOffenderMobNo());
         offline.setEmailId(challanDto.getOffenderEmail());
         offline.setApplicantAddress(challanDto.getLocality());
         offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
         offline.setChallanValidDate(challanDto.getChallanDate());
         
         final LookUp taxAppAtBill = CommonMasterUtility.getValueFromPrefixLookUp(
                 "APL", PrefixConstants.NewWaterServiceConstants.CAA,
                 UserSession.getCurrent().getOrganisation());
         Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class).getDepartmentIdByDeptCode("ENC");
		/*
		 * List<TbTaxMas> indepenTaxList =
		 * tbTaxMasService.fetchAllIndependentTaxes(UserSession.getCurrent().
		 * getOrganisation().getOrgid(), deptId,taxAppAtBill.getLookUpId(), null,
		 * taxAppAtBill.getLookUpId());
		 */
        
         List<TbTaxMasEntity> indepenTaxList = ApplicationContextProvider.getApplicationContext()
                 .getBean(TbTaxMasService.class).fetchAllApplicableServiceCharge(getServiceMaster().getSmServiceId(),
                		 UserSession.getCurrent().getOrganisation().getOrgid(), taxAppAtBill.getLookUpId());
         
         if(CollectionUtils.isEmpty(indepenTaxList)) {
        	addValidationError(ApplicationSession.getInstance().getMessage("EChallan.taxMasterNotConfigured"));
 			return false;
         }
         
         offline.getFeeIds().put(indepenTaxList.get(0).getTaxId(), challanDto.getChallanAmt());
         offline.setWorkflowEnable(MainetConstants.FlagN); 
         if (CollectionUtils.isNotEmpty(getCheckList())) {
             offline.setDocumentUploaded(true);
         } else {
             offline.setDocumentUploaded(false);
         }
         offline.setLgIpMac(getClientIpAddress());
         offline.setLoggedLocId(UserSession.getCurrent().getLoggedLocId());
         offline.setDeptId(deptId);
         offline.setOfflinePaymentText(CommonMasterUtility
                 .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
                 .getLookUpCode());
         
         if ((offline.getOnlineOfflineCheck() != null)
                 && offline.getOnlineOfflineCheck().equals(MainetConstants.MENU.N)) {

             final ChallanMaster responseChallan = iChallanService.InvokeGenerateChallan(offline);

             offline.setChallanNo(responseChallan.getChallanNo());
             offline.setChallanValidDate(responseChallan.getChallanValiDate());

             setOfflineDTO(offline);
         } else if ((offline.getOnlineOfflineCheck() != null)
                 && offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {

             final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline,
                     getServiceMaster().getSmServiceName());
             setReceiptDTO(printDto);
			 
             setSuccessMessage(getAppSession().getMessage("chn.receipt"));
         }
         return true;
    }
    
    public void sendSmsEmail(EChallanEntryModel model, String menuUrl, String msgType) {
        SMSAndEmailDTO smsDto = new SMSAndEmailDTO();
        smsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        smsDto.setLangId(UserSession.getCurrent().getLanguageId());
        
        if(model.getChallanMasterDto().getChallanType().equals("OS")) {
        	smsDto.setAppNo(model.getChallanMasterDto().getChallanNo());
        }else {
        	smsDto.setAppNo(model.getChallanMasterDto().getRaidNo());
        }
        smsDto.setServName(getServiceMaster().getSmServiceName());
        smsDto.setMobnumber(model.getChallanMasterDto().getOffenderMobNo());
        smsDto.setAppName(model.getChallanMasterDto().getOffenderName());
        smsDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        smsDto.setDate(new Date());
        if (StringUtils.isNotBlank(model.getChallanMasterDto().getOffenderEmail())) {
            smsDto.setEmail(model.getChallanMasterDto().getOffenderEmail());
        }

        ApplicationContextProvider.getApplicationContext().getBean(ISMSAndEmailService.class).sendEmailSMS(
                MainetConstants.EncroachmentChallan.CHALLAN_DEPT_SHORT_CODE, menuUrl, msgType, smsDto,
                UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());

    }
  
}
