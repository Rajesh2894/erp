package com.abm.mainet.property.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.CommonChallanPayModeDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.pg.dto.TbLoiDet;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.NoDuesPropertyDetailsDto;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;
import com.google.common.util.concurrent.AtomicDouble;

@Component
@Scope("session")
public class PropertyNoDuesCertificateModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;
	
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Autowired
	private IChallanService iChallanService;

	
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;

	private NoDuesCertificateDto noDuesCertificateDto = null;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<LookUp> documentsList = new ArrayList<>();

	private String appliChargeFlag;

	private List<BillDisplayDto> charges = new ArrayList<>();

	private List<LookUp> location = new ArrayList<>(0);

	private Long orgId;

	private Long deptId;

	private String serviceName;

	private String scrutinyAppliFlag;

	private String allowToGenerate;

	private String date;

	private String finYear;

	private String checkListApplFlag;

	private boolean isFree;
	
	private String path;

	private String authFlag;

	private ServiceMaster serviceMaster = new ServiceMaster();

	private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();

	private String successFlag;

	private String payableFlag;

	private Double totalLoiAmount;

	private String serviceShrtCode;

	private List<TbLoiDet> loiDetail = new ArrayList<>();

	private ProperySearchDto searchDto = new ProperySearchDto();

	private List<ProperySearchDto> searchDtoResult = new ArrayList<>();
	
	private List<CFCAttachment> attachmentList = new ArrayList<>(0);

	private Map<Long, String> financialYearMap = null;

	private boolean lastApproval;

	private boolean isNeedToCheckOutstanding;
	
	private String flatNumber;
	
	private String billMethod;

	@Override
	public boolean saveForm() {
		final CommonChallanDTO offline = getOfflineDTO();
		List<DocumentDetailsVO> docs = getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		validateData(offline, docs);
		if (hasValidationErrors()) {
			return false;
		}
		noDuesCertificateDto.setDocs(docs);
		noDuesCertificateDto.setDeptId(getDeptId());
		noDuesCertificateDto.setLangId(UserSession.getCurrent().getLanguageId());
		noDuesCertificateDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		noDuesCertificateDto.setOrgId(getOrgId());
		noDuesCertificateDto.setSmServiceId(getServiceId());
		noDuesCertificateDto.setMacAddress(UserSession.getCurrent().getEmployee().getEmppiservername());
		noDuesCertificateDto.setApplicantDto(getApplicantDetailDto());
		
		noDuesCertificateDto = propertyNoDuesCertificate.generateNoDuesCertificate(noDuesCertificateDto);
		if (!isFree) {
			setChallanDToandSaveChallanData(offline, charges, noDuesCertificateDto, provisionalAssesmentMstDto);
		}
		setSuccessMessage(getAppSession().getMessage("prop.no.dues.save1") + noDuesCertificateDto.getApplicationNo()
				+ getAppSession().getMessage("prop.no.dues.save2"));
		if (MainetConstants.FlagY.equals(getScrutinyAppliFlag()) && isFree) {
			callWorkFlow(noDuesCertificateDto);
		}
		return true;
	}

	private void validateData(final CommonChallanDTO offline, List<DocumentDetailsVO> docs) {
		if(CollectionUtils.isNotEmpty(docs)) {
			for (final DocumentDetailsVO doc : docs) {
				if(StringUtils.equals(MainetConstants.FlagY, doc.getCheckkMANDATORY()) && StringUtils.isBlank(doc.getDocumentByteCode())){
						this.addValidationError(getAppSession().getMessage("property.mandtory.docs"));
						break;
				}
			}
		}
		if (MainetConstants.FlagN.equals(appliChargeFlag)) {
			/*
			 * if (CollectionUtils.isNotEmpty(offline.getMultiModeList())) { if
			 * (offline.getOnlineOfflineCheck() == null ||
			 * offline.getOnlineOfflineCheck().isEmpty()) {
			 * addValidationError("Please select the Collection Type"); } AtomicDouble
			 * totalPaidAmt = new AtomicDouble(0);
			 * offline.getMultiModeList().forEach(modeList -> {
			 * totalPaidAmt.addAndGet(modeList.getAmount()); }); } else {
			 */
				validateBean(offline, CommonOfflineMasterValidator.class);
			/*}*/
		}
	}
	
	public boolean checkOutstandingStatus() {
		double outstanding = 0d;
		if(StringUtils.equals(MainetConstants.Property.NDT, serviceShrtCode) && CollectionUtils.isNotEmpty(noDuesCertificateDto.getPropertyDetails())) {
			Long currentYear = noDuesCertificateDto.getCurrentFinYearId();
			Date firstSemEndDate = noDuesCertificateDto.getFirstSemDate();
             List<TbBillMas> selectYearBillMas = noDuesCertificateDto.getBillMasList().stream()
						.filter(billMas -> billMas.getBmYear()
								.equals(noDuesCertificateDto.getPropertyDetails().get(0).getFinacialYearId()))
						.collect(Collectors.toList());
			if (noDuesCertificateDto.getPropertyDetails().get(0).getFinacialYearId().equals(currentYear)
					&& (Utility.compareDate(new Date(), firstSemEndDate)
							|| Utility.comapreDates(new Date(), firstSemEndDate)) && CollectionUtils.isNotEmpty(selectYearBillMas)) {
				noDuesCertificateDto.getPropertyDetails().get(0).setFlag(MainetConstants.FlagH);//Half
            	 if(selectYearBillMas.get(0).getBmToatlRebate() <= 0) {
            		 boolean paidFlag = checkHalfAMountPaid(selectYearBillMas);
            		 if(!paidFlag) {
            			 addValidationError(getAppSession().getMessage("prop.no.dues.outstanding"));
      					return false;
            		 }
            	 }
			} else if ((Utility.compareDate(new Date(), firstSemEndDate)
					|| Utility.comapreDates(new Date(), firstSemEndDate))
					&& CollectionUtils.isNotEmpty(selectYearBillMas)
					&& noDuesCertificateDto.getBillMasList().get(noDuesCertificateDto.getBillMasList().size() - 1)
							.getBmYear().equals(selectYearBillMas
									.get(selectYearBillMas.size() - 1).getBmYear())) {
				noDuesCertificateDto.getPropertyDetails().get(0).setFlag(MainetConstants.FlagH);
				boolean paidFlag = checkHalfAMountPaid(selectYearBillMas);
            	 if(!paidFlag) {
            		 addValidationError(getAppSession().getMessage("prop.no.dues.outstanding"));
            		 return false;
            	 }
             }
			else {
				if(CollectionUtils.isNotEmpty(selectYearBillMas) && StringUtils.equals(selectYearBillMas.get(0).getBmPaidFlag(), MainetConstants.FlagN)) {
					addValidationError(getAppSession().getMessage("prop.no.dues.outstanding"));
					return false;
				}
             }
		}else {
			if (noDuesCertificateDto.getPropertyDetails() != null && !noDuesCertificateDto.getPropertyDetails().isEmpty()) {
				for (NoDuesPropertyDetailsDto dto : noDuesCertificateDto.getPropertyDetails()) {
					outstanding = outstanding + dto.getTotalOutsatandingAmt();
				}
				if (outstanding > 0) {
					addValidationError(getAppSession().getMessage("prop.no.dues.outstanding"));
					return false;
				}
			}
		}
		

		return true;
	}
	
	private boolean checkHalfAMountPaid(List<TbBillMas> selectYearBillMas) {
		boolean paidFlag = true;
		 AtomicDouble totalCurBill = new AtomicDouble(0);
		 AtomicDouble totalCurBillBal = new AtomicDouble(0);
		 selectYearBillMas.get(selectYearBillMas.size() -1).getTbWtBillDet().forEach(det ->{
			 totalCurBill.addAndGet(det.getBdCurTaxamt());
			 totalCurBillBal.addAndGet(det.getBdCurBalTaxamt());
		 });
		 if(Math.round(totalCurBillBal.doubleValue()) > (totalCurBill.doubleValue()/2)) {
			 paidFlag = false;
		 }
		return paidFlag;
	}

	public boolean checkChequeClearanceStatus() {
		if (!propertyNoDuesCertificate.getChequeClearanceStatus(noDuesCertificateDto)) {
			addValidationError(getAppSession().getMessage("prop.no.dues.cheque.not.clear"));
			return false;
		}
		return true;
	}

	public List<LookUp> getDocumentsList() {
		final List<LookUp> documentDetailsList = new ArrayList<>(0);
		setPath(getAttachmentList().get(0).getAttPath());
		LookUp lookUp = null;
		for (final CFCAttachment temp : getAttachmentList()) {
			lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
			lookUp.setDescLangFirst(temp.getClmDesc());
			lookUp.setDescLangSecond(temp.getClmDesc());
			lookUp.setLookUpId(temp.getClmId());
			lookUp.setDefaultVal(temp.getAttPath());
			lookUp.setLookUpCode(temp.getAttFname());
			lookUp.setLookUpParentId(temp.getAttId());
			lookUp.setLookUpExtraLongOne(temp.getClmSrNo());
			lookUp.setOtherField(temp.getMandatory());
			documentDetailsList.add(lookUp);
		}
		return documentDetailsList;
	}

	void callWorkFlow(NoDuesCertificateDto dto) {		
		boolean status = propertyNoDuesCertificate.initiateWorkflowForPropertyfreeService(dto);
		if(!status) {
			throw new FrameworkException("Exception Occured While initiating workflow !! ");
		}
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final List<BillDisplayDto> charges,
			NoDuesCertificateDto noDuesCertificateDto, ProvisionalAssesmentMstDto prevAssessDetail) {
		final UserSession session = UserSession.getCurrent();
		offline.setAmountToPay(Double.toString(noDuesCertificateDto.getTotalPaybleAmt()));
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		/*noDuesCertificateDto.getCharges().forEach(charge -> {
			offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue());
		});*/
		// offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		// offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		// offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setEmailId(noDuesCertificateDto.getApplicantDto().getEmailId());
		offline.setApplicantName(noDuesCertificateDto.getApplicantDto().getApplicantFirstName());
		offline.setApplNo(noDuesCertificateDto.getApmApplicationId());
		offline.setApplicantAddress(noDuesCertificateDto.getApplicantDto().getBuildingName());
		offline.setMobileNumber(noDuesCertificateDto.getApplicantDto().getMobileNo());
		offline.setServiceId(getServiceId());
		offline.setDeptId(getDeptId());
		offline.setReferenceNo(noDuesCertificateDto.getApplicationNo());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		if (getCheckList() != null && !getCheckList().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		 Map<Long, Double> details = new HashMap<>(0);

	        if ((details != null) && !details.isEmpty()) {
	            offline.setFeeIds(details);
	        }
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		 if (offline.getOnlineOfflineCheck() != null && offline.getOnlineOfflineCheck().equals(MainetConstants.FlagY)) {
	            Long lookUpId = CommonMasterUtility
	                    .getValueFromPrefixLookUp(MainetConstants.PAY_PREFIX.ONLINE,
	                            MainetConstants.PAY_PREFIX.PREFIX_VALUE, session.getOrganisation()).getLookUpId();
	            CommonChallanPayModeDTO modeDto = new CommonChallanPayModeDTO();
	            modeDto.setPayModeIn(lookUpId);
	            modeDto.setAmount(noDuesCertificateDto.getTotalPaybleAmt());
			/*
			 * offline.getMultiModeList().clear(); offline.getMultiModeList().add(modeDto);
			 */
	        }
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			CommonChallanDTO dto = iChallanService.generateChallanNumber(offline);
			offline.setChallanValidDate(dto.getChallanValidDate());
			offline.setChallanNo(dto.getChallanNo());
			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + ":"
					+ noDuesCertificateDto.getApmApplicationId());
		}
		setOfflineDTO(offline);
	}

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final NoDuesCertificateDto reqDTO = this.getNoDuesCertificateDto();
		/*final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());*/
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(getServiceShrtCode());
		payURequestDTO.setUdf7(String.valueOf(reqDTO.getApmApplicationId()));
		payURequestDTO.setApplicantName(reqDTO.getApplicantDto().getApplicantFirstName());
		payURequestDTO.setServiceId(getServiceId());
		payURequestDTO.setUdf2(noDuesCertificateDto.getApplicationNo());
		payURequestDTO.setMobNo(reqDTO.getApplicantDto().getMobileNo());
		payURequestDTO.setServiceName(getServiceName());
		payURequestDTO.setDueAmt(new BigDecimal(reqDTO.getTotalPaybleAmt()));
		payURequestDTO.setEmail(reqDTO.getApplicantDto().getEmailId());
		payURequestDTO.setApplicationId(noDuesCertificateDto.getApplicationNo());
		payURequestDTO.setUdf10(getDeptId().toString());
		payURequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	}
	
	public boolean isNoBillExist(NoDuesPropertyDetailsDto detailsDto) {
		int count = 0;
		detailsDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		count = propertyNoDuesCertificate.getBillExistByPropNoFlatNoAndYearId(detailsDto);
			 
		if(count == 0) {
			return true;
		}
		return false;
	}
	public boolean isWorkflowDefined() {
		return propertyNoDuesCertificate.checkWorkflowDefined(noDuesCertificateDto);
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public NoDuesCertificateDto getNoDuesCertificateDto() {
		return noDuesCertificateDto;
	}

	public void setNoDuesCertificateDto(NoDuesCertificateDto noDuesCertificateDto) {
		this.noDuesCertificateDto = noDuesCertificateDto;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getAppliChargeFlag() {
		return appliChargeFlag;
	}

	public void setAppliChargeFlag(String appliChargeFlag) {
		this.appliChargeFlag = appliChargeFlag;
	}

	public PropertyNoDuesCertificateService getPropertyNoDuesCertificate() {
		return propertyNoDuesCertificate;
	}

	public void setPropertyNoDuesCertificate(PropertyNoDuesCertificateService propertyNoDuesCertificate) {
		this.propertyNoDuesCertificate = propertyNoDuesCertificate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getScrutinyAppliFlag() {
		return scrutinyAppliFlag;
	}

	public void setScrutinyAppliFlag(String scrutinyAppliFlag) {
		this.scrutinyAppliFlag = scrutinyAppliFlag;
	}

	public String getAllowToGenerate() {
		return allowToGenerate;
	}

	public void setAllowToGenerate(String allowToGenerate) {
		this.allowToGenerate = allowToGenerate;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public IPortalServiceMasterService getiPortalServiceMasterService() {
		return iPortalServiceMasterService;
	}

	public void setiPortalServiceMasterService(IPortalServiceMasterService iPortalServiceMasterService) {
		this.iPortalServiceMasterService = iPortalServiceMasterService;
	}

	public IChallanService getiChallanService() {
		return iChallanService;
	}

	public void setiChallanService(IChallanService iChallanService) {
		this.iChallanService = iChallanService;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public void setDocumentsList(List<LookUp> documentsList) {
		this.documentsList = documentsList;
	}

	public List<BillDisplayDto> getCharges() {
		return charges;
	}

	public void setCharges(List<BillDisplayDto> charges) {
		this.charges = charges;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<DocumentDetailsVO> getApprovalDocumentAttachment() {
		return approvalDocumentAttachment;
	}

	public void setApprovalDocumentAttachment(List<DocumentDetailsVO> approvalDocumentAttachment) {
		this.approvalDocumentAttachment = approvalDocumentAttachment;
	}

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	public String getPayableFlag() {
		return payableFlag;
	}

	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	public Double getTotalLoiAmount() {
		return totalLoiAmount;
	}

	public void setTotalLoiAmount(Double totalLoiAmount) {
		this.totalLoiAmount = totalLoiAmount;
	}

	public String getServiceShrtCode() {
		return serviceShrtCode;
	}

	public void setServiceShrtCode(String serviceShrtCode) {
		this.serviceShrtCode = serviceShrtCode;
	}

	public ProperySearchDto getSearchDto() {
		return searchDto;
	}

	public void setSearchDto(ProperySearchDto searchDto) {
		this.searchDto = searchDto;
	}

	public List<ProperySearchDto> getSearchDtoResult() {
		return searchDtoResult;
	}

	public void setSearchDtoResult(List<ProperySearchDto> searchDtoResult) {
		this.searchDtoResult = searchDtoResult;
	}

	public Map<Long, String> getFinancialYearMap() {
		return financialYearMap;
	}

	public void setFinancialYearMap(Map<Long, String> financialYearMap) {
		this.financialYearMap = financialYearMap;
	}

	public boolean isLastApproval() {
		return lastApproval;
	}

	public void setLastApproval(boolean lastApproval) {
		this.lastApproval = lastApproval;
	}

	public boolean isNeedToCheckOutstanding() {
		return isNeedToCheckOutstanding;
	}

	public void setNeedToCheckOutstanding(boolean isNeedToCheckOutstanding) {
		this.isNeedToCheckOutstanding = isNeedToCheckOutstanding;
	}

	public List<CFCAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<CFCAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(String flatNumber) {
		this.flatNumber = flatNumber;
	}

	public String getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(String billMethod) {
		this.billMethod = billMethod;
	}

}
