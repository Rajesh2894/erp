/**
 * 
 */
package com.abm.mainet.property.ui.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.BillDisplayDto;
import com.abm.mainet.property.dto.NoDuesCertificateDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.service.PropertyNoDuesCertificateService;

/**
 * @author cherupelli.srikanth
 * @since 25 January 2021
 */

@Component
@Scope("session")
public class ExtractPropertyModel extends AbstractFormModel {

	private static final long serialVersionUID = 5805185350047241238L;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private PropertyNoDuesCertificateService propertyNoDuesCertificate;

	@Autowired
	private IChallanService iChallanService;

	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto = null;

	private NoDuesCertificateDto noDuesCertificateDto = null;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private String appliChargeFlag;

	private List<BillDisplayDto> charges = new ArrayList<>();

	private Long orgId;

	private Long deptId;

	private String serviceName;

	private String scrutinyAppliFlag;

	private String allowToGenerate;

	private String date;

	private String finYear;

	private String checkListApplFlag;

	private boolean isFree;

	private String authFlag;

	private ServiceMaster serviceMaster = new ServiceMaster();

	private List<DocumentDetailsVO> approvalDocumentAttachment = new ArrayList<>();

	private String successFlag;

	private String payableFlag;

	private Double totalLoiAmount;

	private String serviceShrtCode;

	private List<TbLoiDet> loiDetail = new ArrayList<>();

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private List<LookUp> location = new ArrayList<>(0);

	@Override
	public boolean saveForm() {
		// setCustomViewName("PropertyNoDuesCertificate");
		List<DocumentDetailsVO> docs = getCheckList();
		docs = fileUpload.prepareFileUpload(docs);
		noDuesCertificateDto.setDocs(docs);

		noDuesCertificateDto.setDeptId(getDeptId());
		noDuesCertificateDto.setLangId(UserSession.getCurrent().getLanguageId());
		noDuesCertificateDto.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		noDuesCertificateDto.setOrgId(getOrgId());
		noDuesCertificateDto.setSmServiceId(getServiceId());
		final CommonChallanDTO offline = getOfflineDTO();
		if (appliChargeFlag.equals(MainetConstants.Y_FLAG)) {
			validateBean(offline, CommonOfflineMasterValidator.class);
		}
		if (hasValidationErrors()) {
			return false;
		}
		propertyNoDuesCertificate.generateNoDuesCertificate(noDuesCertificateDto);
		if (appliChargeFlag.equals(MainetConstants.Y_FLAG)) {
			setChallanDToandSaveChallanData(offline, charges, noDuesCertificateDto, provisionalAssesmentMstDto);
		} else {
			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + ":"
					+ noDuesCertificateDto.getApmApplicationId());
		}
		return true;
	}

	private void setChallanDToandSaveChallanData(final CommonChallanDTO offline, final List<BillDisplayDto> charges,
			NoDuesCertificateDto noDuesCertificateDto, ProvisionalAssesmentMstDto prevAssessDetail) {
		final UserSession session = UserSession.getCurrent();
		offline.setAmountToPay(Double.toString(noDuesCertificateDto.getTotalPaybleAmt()));
		offline.setUserId(session.getEmployee().getEmpId());
		offline.setOrgId(session.getOrganisation().getOrgid());
		offline.setLangId(session.getLanguageId());
		offline.setLgIpMac(session.getEmployee().getEmppiservername());
		noDuesCertificateDto.getCharges().forEach(charge -> {
			offline.getFeeIds().put(charge.getTaxId(), charge.getTotalTaxAmt().doubleValue());
		});
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setEmailId(noDuesCertificateDto.getAppliEmail());
		offline.setApplicantName(noDuesCertificateDto.getApplicantName());
		offline.setApplNo(noDuesCertificateDto.getApmApplicationId());
		offline.setApplicantAddress(noDuesCertificateDto.getAppliAddress());
		offline.setMobileNumber(noDuesCertificateDto.getAppliMobileno());
		offline.setServiceId(getServiceId());
		offline.setDeptId(getDeptId());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		if (getCheckList() != null && !getCheckList().isEmpty()) {
			offline.setDocumentUploaded(true);
		}
		offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		offline.setOfflinePaymentText(CommonMasterUtility
				.getNonHierarchicalLookUpObject(offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation())
				.getLookUpCode());
		if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.OFFLINE)) {
			final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
			offline.setChallanValidDate(master.getChallanValiDate());
			offline.setChallanNo(master.getChallanNo());
			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + " "
					+ noDuesCertificateDto.getApmApplicationId());
		} else if ((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
			final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, serviceName);
			setReceiptDTO(printDto);
			setSuccessMessage(getAppSession().getMessage("property.noDues.sccessMsg") + " "
					+ noDuesCertificateDto.getApmApplicationId());
		}
		setOfflineDTO(offline);
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	public PropertyNoDuesCertificateService getPropertyNoDuesCertificate() {
		return propertyNoDuesCertificate;
	}

	public void setPropertyNoDuesCertificate(PropertyNoDuesCertificateService propertyNoDuesCertificate) {
		this.propertyNoDuesCertificate = propertyNoDuesCertificate;
	}

	public IChallanService getiChallanService() {
		return iChallanService;
	}

	public void setiChallanService(IChallanService iChallanService) {
		this.iChallanService = iChallanService;
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public NoDuesCertificateDto getNoDuesCertificateDto() {
		return noDuesCertificateDto;
	}

	public void setNoDuesCertificateDto(NoDuesCertificateDto noDuesCertificateDto) {
		this.noDuesCertificateDto = noDuesCertificateDto;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getAppliChargeFlag() {
		return appliChargeFlag;
	}

	public void setAppliChargeFlag(String appliChargeFlag) {
		this.appliChargeFlag = appliChargeFlag;
	}

	public List<BillDisplayDto> getCharges() {
		return charges;
	}

	public void setCharges(List<BillDisplayDto> charges) {
		this.charges = charges;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
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

	public List<TbLoiDet> getLoiDetail() {
		return loiDetail;
	}

	public void setLoiDetail(List<TbLoiDet> loiDetail) {
		this.loiDetail = loiDetail;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	public List<LookUp> getLocation() {
		return location;
	}

	public void setLocation(List<LookUp> location) {
		this.location = location;
	}

}
