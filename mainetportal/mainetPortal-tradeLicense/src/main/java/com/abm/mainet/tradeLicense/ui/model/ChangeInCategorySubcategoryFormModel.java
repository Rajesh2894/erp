package com.abm.mainet.tradeLicense.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeCategorySubCategoryService;

@Component
@Scope("session")
public class ChangeInCategorySubcategoryFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
	private TradeMasterDetailDTO tradeDTO = null;
	private String licenseDetails;
	private String licFromDateDesc;
	private String licToDateDesc;
	private String checklistCheck;
	private String paymentCheck;
	private String propertyActiveStatus;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private PortalService serviceMaster = new PortalService();
	private String licenseBtnFlag;
	private String userOtp;
	private String envFlag;
	private Date appDate;
	private String appTime;
	private String departmentName;
	private Date dueDate;
	private String helpLine;
	private Long applicationId;
	private String applicantName;
	private String serviceName;
	private String appChargeFlag;
	private String checkListApplFlag;
	private String newEntry;
	private String viewMode;
	private String showHideFlag;
	private String editAppFlag;
    
	@Autowired
	private ICommonBRMSService brmsCommonService;
	
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;
	
	@Autowired
    private IChallanService challanService;
	
	@Override
	 public boolean saveForm() {
		 
		 setCommonFields(this);
		 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
			String lgIp = UserSession.getCurrent().getEmployee().getEmppiservername();
			Date newDate = new Date();
			TradeMasterDetailDTO tradeDto = getTradeDTO();
			LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS", UserSession.getCurrent().getOrganisation());
			TradeMasterDetailDTO masDto = getTradeMasterDetailDTO();
			if (masDto.getCreatedBy() == null) {
				masDto.setTrdEty(MainetConstants.FlagS);
				masDto.setTrdStatus(lookUp.getLookUpId());
				masDto.setCreatedBy(createdBy);
				masDto.setCreatedDate(newDate);
				masDto.setOrgid(orgId);
				masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
				masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
					ownDto.setCreatedBy(createdBy);
					ownDto.setCreatedDate(newDate);
					ownDto.setOrgid(orgId);
					ownDto.setLgIpMac(lgIp);
				});

				masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
					itemDto.setCreatedBy(createdBy);
					itemDto.setCreatedDate(newDate);
					itemDto.setOrgid(orgId);
					itemDto.setLgIpMac(lgIp);
				});

			} else {
				masDto.setTrdStatus(lookUp.getLookUpId());
				masDto.setTrdEty(MainetConstants.FlagS);
				masDto.setUpdatedBy(createdBy);
				masDto.setUpdatedDate(newDate);
				masDto.setOrgid(orgId);
				masDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
				masDto.setLangId((long) UserSession.getCurrent().getLanguageId());
				masDto.getTradeLicenseOwnerdetailDTO().forEach(ownDto -> {
					if (ownDto.getCreatedBy() == null) {
						ownDto.setCreatedBy(createdBy);
						ownDto.setCreatedDate(newDate);
						ownDto.setOrgid(orgId);
						ownDto.setLgIpMac(lgIp);
					} else {
						ownDto.setUpdatedBy(createdBy);
						ownDto.setUpdatedDate(newDate);
						ownDto.setOrgid(orgId);
						ownDto.setLgIpMac(lgIp);
					}
				});

				masDto.getTradeLicenseItemDetailDTO().forEach(itemDto -> {
					if (itemDto.getCreatedBy() == null) {
						itemDto.setCreatedBy(createdBy);
						itemDto.setCreatedDate(newDate);
						itemDto.setOrgid(orgId);
						itemDto.setLgIpMac(lgIp);
					} else {
						itemDto.setUpdatedBy(createdBy);
						itemDto.setUpdatedDate(newDate);
						itemDto.setOrgid(orgId);
						itemDto.setLgIpMac(lgIp);
					}
				});

			}
			
			TradeMasterDetailDTO tradeDetailDto = this.getTradeMasterDetailDTO();
	        TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();
	        List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
	        BeanUtils.copyProperties(tradeDetailDto, tradeDTO);
	        tradeDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
	        tradeDetailDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
	            TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
	            BeanUtils.copyProperties(itemdDetails, itemDto);
	            itemDto.setTriCod1(itemdDetails.getTriCategory1());
	            itemDto.setTriCod2(itemdDetails.getTriCategory2());
	            itemDto.setTriCod3(itemdDetails.getTriCategory3());
	            itemDto.setTriCod4(itemdDetails.getTriCategory4());
	            itemDto.setTriCod5(itemdDetails.getTriCategory5());
	            itemdDetailsList.add(itemDto);
	        });

	        tradeDTO.setTradeLicenseItemDetailDTO(itemdDetailsList);
	        tradeDetailDto.setTradeLicenseItemDetailDTO(itemdDetailsList);
	        setTradeMasterDetailDTO(tradeDTO);
	        BigDecimal paymentAmount = masDto.getTotalApplicationFee();
	        if (paymentAmount != null) {
	            tradeMasterDetailDTO.setFree(false);
	            masDto.setFree(false);
	        } else {
	            tradeMasterDetailDTO.setFree(true);
	            masDto.setFree(true);
	        }
	        
	        masDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        masDto.setDocumentList(getFileUploadList(getCheckList(), FileUploadUtility.getCurrent().getFileMap()));
			masDto = ApplicationContextProvider.getApplicationContext().getBean(IChangeCategorySubCategoryService.class).saveChangeCategoryService(masDto,tradeDto);
			this.setTradeMasterDetailDTO(masDto);
			
			final CommonChallanDTO offline = getOfflineDTO();
	        Map<Long, Double> details = new HashMap<>(0);
	        final Map<Long, Long> billDetails = new HashMap<>(0);
	        setChallanDToandSaveChallanData(offline, details, billDetails, getTradeMasterDetailDTO());
	        RequestDTO requestDTO1 = new RequestDTO();
	        requestDTO1.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	        requestDTO1.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	        requestDTO1.setStatus(MainetConstants.Common_Constant.ACTIVE_FLAG);
	        requestDTO1.setIdfId(getTradeMasterDetailDTO().getApmApplicationId().toString());
	        requestDTO1.setDepartmentName("ML");
	        requestDTO1.setApplicationId(getTradeMasterDetailDTO().getApmApplicationId());
	        requestDTO1.setDeptId(getServiceMaster().getPsmDpDeptid());
	        requestDTO1.setLangId(new Long(UserSession.getCurrent().getLanguageId()));
	        requestDTO1.setServiceId(getServiceMaster().getServiceId());
	        requestDTO1.setReferenceId(getTradeMasterDetailDTO().getApmApplicationId().toString());
	        this.setSuccessMessage(getAppSession().getMessage("trade.successMsg")
					+ masDto.getApmApplicationId());
			return true;

		}
	 
	 private void setCommonFields(ChangeInCategorySubcategoryFormModel model) {
			final Date sysDate = new Date();
			RequestDTO requestDTO = new RequestDTO();
			final TradeMasterDetailDTO dto = model.getTradeMasterDetailDTO();
			final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			dto.setOrgid(orgId);
			final Long serviceId = iPortalServiceMasterService.getServiceId("CCS", orgId);
			final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
			model.setServiceMaster(service);
			model.setServiceId(service.getServiceId());
			model.getTradeMasterDetailDTO().setServiceId(service.getServiceId());
			dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			dto.setPrimaryOwnerName(dto.getPrimaryOwnerName());
			dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			dto.setLgIpMac(dto.getLgIpMac());
			requestDTO.setUserId(dto.getCreatedBy());
			dto.setCreatedDate(sysDate);
			long deptId = service.getPsmDpDeptid();
			requestDTO.setDeptId(deptId);
	}
	 
	 private void setChallanDToandSaveChallanData(CommonChallanDTO offline, final Map<Long, Double> details,
	            final Map<Long, Long> billDetails, TradeMasterDetailDTO tradeMaster) {

	        final UserSession session = UserSession.getCurrent();
	        final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
	                UserSession.getCurrent().getOrganisation().getOrgid());
	       // setServiceMaster(portalServiceMaster);
	        if(CollectionUtils.isNotEmpty(tradeMaster.getTradeLicenseOwnerdetailDTO())) {
	        TradeLicenseOwnerDetailDTO ownDtlDto = tradeMaster.getTradeLicenseOwnerdetailDTO().get(0);
	        offline.setEmailId(ownDtlDto.getTroEmailid());
	        offline.setApplicantName(ownDtlDto.getTroName());
	        offline.setApplicantAddress(ownDtlDto.getTroAddress());
	        offline.setMobileNumber(ownDtlDto.getTroMobileno());
	        }
	        if(null != tradeMaster.getTotalApplicationFee())
	        offline.setAmountToPay(tradeMaster.getTotalApplicationFee().toString());
	        offline.setUserId(session.getEmployee().getEmpId());
	        offline.setOrgId(session.getOrganisation()
	                .getOrgid());
	        offline.setLangId(session.getLanguageId());
	        offline.setLgIpMac(session.getEmployee().getEmppiservername());
	        if ((details != null) && !details.isEmpty()) {
	            offline.setFeeIds(details);
	        }
	        if ((billDetails != null) && !billDetails.isEmpty()) {
	            offline.setBillDetIds(billDetails);
	        }
	       /* offline.setFaYearId(UserSession.getCurrent().getFinYearId());
	        offline.setPaymentCategory(MainetConstants.TradeLicense.PAYMENT_CATEGORY);
	        offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
	        offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());*/        
	        offline.setApplNo(tradeMaster.getApmApplicationId());
	        offline.setUniquePrimaryId(tradeMaster.getApmApplicationId().toString());
	        offline.setDeptId(portalServiceMaster.getPsmDpDeptid());
	        offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
	        offline.setServiceId(portalServiceMaster.getServiceId());
	        if ((FileUploadUtility.getCurrent().getFileMap() != null)
	                && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
	            offline.setDocumentUploaded(true);
	        }
	        offline.setFeeIds(tradeMaster.getFeeIds());
	        tradeMaster.setFeeIds(tradeMaster.getFeeIds());
	        offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
	        offline.setOfflinePaymentText(CommonMasterUtility
	                .getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
	                .getLookUpCode());
	        if ((offline.getOnlineOfflineCheck() != null)
                 && offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {
	        	 offline = challanService.generateChallanNumber(offline);
	           /* offline.setChallanValidDate(master
	                    .getChallanValiDate());
	            offline.setChallanNo(master.getChallanNo());*/
	        } 
	        setOfflineDTO(offline);
	    }
	
	public void getCheckListFromBrms() {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.TradeLicense.CHECKLIST_MODEL);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		List<DocumentDetailsVO> checkListList = new ArrayList<>();
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> checklist = JersyCall.castResponse(response, CheckListModel.class, 0);
			CheckListModel checkListModel = (CheckListModel) checklist.get(0);
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode("CCS");
			final WSRequestDTO checkRequestDto = new WSRequestDTO();
			checkRequestDto.setDataModel(checkListModel);
			checkListList = brmsCommonService.getChecklist(checkListModel);
			if (checkListList != null && !checkListList.isEmpty()) {
				Long fileSerialNo = 1L;
				for (final DocumentDetailsVO docSr : checkListList) {
					docSr.setDocumentSerialNo(fileSerialNo);
					fileSerialNo++;
				}
				setCheckList(checkListList);

			}
		}
	}
	
	 @Override
	 public void redirectToPayDetails(final HttpServletRequest httpServletRequest, final PaymentRequestDTO payURequestDTO) {
	 final TradeMasterDetailDTO tradeMasterDetailDTO = this.getTradeMasterDetailDTO();
	 final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
	                UserSession.getCurrent().getOrganisation().getOrgid());
	        payURequestDTO.setUdf3("CitizenHome.html");
	        payURequestDTO.setUdf5(portalServiceMaster.getShortName());
	        payURequestDTO.setUdf7(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
	        //String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(), reqDTO.getmName(), reqDTO.getlName()));
	        payURequestDTO.setApplicantName(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName());
	        payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
	        payURequestDTO.setUdf2(String.valueOf(tradeMasterDetailDTO.getApmApplicationId()));
	        payURequestDTO.setMobNo(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().toString());
	        payURequestDTO.setDueAmt(tradeMasterDetailDTO.getTotalApplicationFee());
	        payURequestDTO.setEmail(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
	        payURequestDTO.setApplicationId(tradeMasterDetailDTO.getApmApplicationId().toString());
	        payURequestDTO.setFeeIds(tradeMasterDetailDTO.getFeeIds().toString());
	        if (portalServiceMaster != null) {
	            payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
	            if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
	                payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
	            } else {
	                payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
	            }
	        }
	    }
	
	public boolean validateInputs() {
		
		FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}
	
	public String getUserOtp() {
		return userOtp;
	}

	public String getOtp() {
		return otp;
	}

	public void setUserOtp(String userOtp) {
		this.userOtp = userOtp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	private String otp;

	public String getLicenseBtnFlag() {
		return licenseBtnFlag;
	}

	public String getOtpBtnShowFlag() {
		return otpBtnShowFlag;
	}

	public void setLicenseBtnFlag(String licenseBtnFlag) {
		this.licenseBtnFlag = licenseBtnFlag;
	}

	public void setOtpBtnShowFlag(String otpBtnShowFlag) {
		this.otpBtnShowFlag = otpBtnShowFlag;
	}

	private String otpBtnShowFlag;

	public TradeMasterDetailDTO getTradeMasterDetailDTO() {
		return tradeMasterDetailDTO;
	}

	public void setTradeMasterDetailDTO(TradeMasterDetailDTO tradeMasterDetailDTO) {
		this.tradeMasterDetailDTO = tradeMasterDetailDTO;
	}

	public TradeMasterDetailDTO getTradeDTO() {
		return tradeDTO;
	}

	public String getLicenseDetails() {
		return licenseDetails;
	}

	public String getLicFromDateDesc() {
		return licFromDateDesc;
	}

	public String getLicToDateDesc() {
		return licToDateDesc;
	}

	public String getChecklistCheck() {
		return checklistCheck;
	}

	public String getPaymentCheck() {
		return paymentCheck;
	}

	public String getPropertyActiveStatus() {
		return propertyActiveStatus;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setTradeDTO(TradeMasterDetailDTO tradeDTO) {
		this.tradeDTO = tradeDTO;
	}

	public void setLicenseDetails(String licenseDetails) {
		this.licenseDetails = licenseDetails;
	}

	public void setLicFromDateDesc(String licFromDateDesc) {
		this.licFromDateDesc = licFromDateDesc;
	}

	public void setLicToDateDesc(String licToDateDesc) {
		this.licToDateDesc = licToDateDesc;
	}

	public void setChecklistCheck(String checklistCheck) {
		this.checklistCheck = checklistCheck;
	}

	public void setPaymentCheck(String paymentCheck) {
		this.paymentCheck = paymentCheck;
	}

	public void setPropertyActiveStatus(String propertyActiveStatus) {
		this.propertyActiveStatus = propertyActiveStatus;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getEnvFlag() {
		return envFlag;
	}

	public void setEnvFlag(String envFlag) {
		this.envFlag = envFlag;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public String getAppTime() {
		return appTime;
	}

	public void setAppTime(String appTime) {
		this.appTime = appTime;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getHelpLine() {
		return helpLine;
	}

	public void setHelpLine(String helpLine) {
		this.helpLine = helpLine;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAppChargeFlag() {
		return appChargeFlag;
	}

	public void setAppChargeFlag(String appChargeFlag) {
		this.appChargeFlag = appChargeFlag;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getNewEntry() {
		return newEntry;
	}

	public void setNewEntry(String newEntry) {
		this.newEntry = newEntry;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getShowHideFlag() {
		return showHideFlag;
	}

	public void setShowHideFlag(String showHideFlag) {
		this.showHideFlag = showHideFlag;
	}

	public String getEditAppFlag() {
		return editAppFlag;
	}

	public void setEditAppFlag(String editAppFlag) {
		this.editAppFlag = editAppFlag;
	}
	
	

	
}
