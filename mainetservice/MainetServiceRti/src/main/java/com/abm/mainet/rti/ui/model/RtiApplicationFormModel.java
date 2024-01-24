/**
save * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Model file for RTI Application Form.
 * @method  : initializeModel - for getting departments and RTI location
 *            saveForm - saving RTI application  
 */

package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.validator.CommonOfflineMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.validator.RtiApplicationDetailValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtiApplicationFormModel extends AbstractFormModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(RtiApplicationFormModel.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 4049201181713405081L;

	@Autowired
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Resource
	IFileUploadService fileUploadService;

	@Resource
	private CommonService commonService;

	@Resource
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private IChallanService iChallanService;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();
	

	private Long orgId;

	private Long DeptId;

	private String isFree;

	private String free = "O";

	private Double charges = 0.0d;

	private Map<Long, Double> chargesMap = new HashMap<>();

	private Set<LookUp> departments = new HashSet<>();

	private Set<LookUp> locations = new HashSet<>();

	private Set<LookUp> payModeIn = new HashSet<>();

	private ServiceMaster serviceMaster = new ServiceMaster();

	private String isValidationError;

	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();

	private CommonChallanDTO offlineDTO = new CommonChallanDTO();

	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	private List<CFCAttachment> fetchDocumentList = new ArrayList<>();
	private List<CFCAttachment> fetchApplnUpload = new ArrayList<>();
	private List<LookUp> refList=new ArrayList<LookUp>();

	List<LookUp> orgLookups = new ArrayList<>();
	private List<Organisation> org = new ArrayList<>();
	private Set<LookUp> related_dept = new HashSet<>();
	private List<DocumentDetailsVO> uploadStamoDoc = new ArrayList<>(); 

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;

	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public RtiApplicationFormDetailsReqDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(RtiApplicationFormDetailsReqDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeptId() {
		return DeptId;
	}

	public void setDeptId(Long deptId) {
		DeptId = deptId;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public Set<LookUp> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<LookUp> departments) {
		this.departments = departments;
	}

	public Set<LookUp> getLocations() {
		return locations;
	}

	public void setLocations(Set<LookUp> locations) {
		this.locations = locations;
	}

	public Set<LookUp> getPayModeIn() {
		return payModeIn;
	}

	public void setPayModeIn(Set<LookUp> payModeIn) {
		this.payModeIn = payModeIn;
	}

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {
		validateBean(this, RtiApplicationDetailValidator.class);
		 validateBean(getOfflineDTO(), CommonOfflineMasterValidator.class);

		if (hasValidationErrors()) {
			// this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}
	/* end of validation method */

	@Override
	protected void initializeModel() {

		departments.addAll(
				rtiApplicationDetailService.getActiveDepartment(UserSession.getCurrent().getOrganisation().getOrgid()));

		List<LookUp> paymentMode = UserSession.getCurrent().getPaymentMode();
		paymentMode.forEach(d -> {
			LookUp detData = new LookUp();
			if (!("Adjustment".equalsIgnoreCase(d.getDescLangFirst()))) {

				detData.setLookUpId(d.getLookUpId());
				detData.setLookUpCode(d.getLookUpCode());
				detData.setLookUpType(d.getLookUpType());
				detData.setDescLangFirst(d.getDescLangFirst());
				detData.setDescLangSecond(d.getDescLangSecond());
				detData.setDefaultVal(d.getDefaultVal());
				detData.setOtherField(d.getOtherField());
				payModeIn.add(detData);
			}
		});
	}

	/* Method for saving Rti Application */

	@Override
	public boolean saveForm() {
		CommonChallanDTO offline = getOfflineDTO();
		RtiApplicationFormDetailsReqDTO requestDTO = this.getReqDTO();
		final UserSession session = UserSession.getCurrent();
		final Date sysDate = UtilityService.getSQLDate(new Date());

		String refMode = CommonMasterUtility.findLookUpCode(PrefixConstants.RTI_PREFIX.REFERENCE_MODE,
				UserSession.getCurrent().getOrganisation().getOrgid(), reqDTO.getApplReferenceMode());
		String applicantCode = CommonMasterUtility.findLookUpCode(PrefixConstants.RTI_PREFIX.APPLICANT,
				UserSession.getCurrent().getOrganisation().getOrgid(), reqDTO.getApplicationType());
		String bpl = CommonMasterUtility.findLookUpCode(PrefixConstants.RTI_PREFIX.BPL,
				UserSession.getCurrent().getOrganisation().getOrgid(), Long.valueOf(reqDTO.getIsBPL()));
		Double paymentAmount = offline.getAmountToShow();
		if ((applicantCode.equals(PrefixConstants.RTI_PREFIX.APPLICANT_TYPE.O)
				&& (refMode.equals(PrefixConstants.RTI_PREFIX.REFERENCE_MODE_TYPE.D)))
				|| (applicantCode.equals(PrefixConstants.RTI_PREFIX.APPLICANT_TYPE.I)
						&& bpl.equals(PrefixConstants.RTI_PREFIX.BPL_TYPE.N)
						&& (refMode.equals(PrefixConstants.RTI_PREFIX.REFERENCE_MODE_TYPE.D)))) {
			if (paymentAmount != null && paymentAmount > 0) {
				requestDTO.setFree(false);

			}
		} else {
			requestDTO.setFree(true);
			requestDTO.setPayStatus(MainetConstants.PAYMENT.FREE);
		}

		String bplNo = requestDTO.getBplNo();
		if (bplNo != null && !bplNo.isEmpty()) {
			requestDTO.setRtiBplFlag("Y");
		} else {
			requestDTO.setRtiBplFlag("N");
		}
		requestDTO.setUserId(session.getEmployee().getEmpId());
		requestDTO.setLangId((long) session.getLanguageId());
		requestDTO.setOrgId(session.getOrganisation().getOrgid());
		requestDTO.setLgIpMac(Utility.getMacAddress());
		requestDTO.setlModDate(sysDate);
		requestDTO.setUpdatedBy(session.getEmployee().getEmpId());
		requestDTO.setUpdateDate(sysDate);
		requestDTO.setApmApplicationDate(sysDate);

		requestDTO.setAppealType(MainetConstants.FlagF);

		/* creating RTI Application */
		requestDTO = rtiApplicationDetailService.saveServiceApplication(requestDTO);

		/* setting offline DTO */
		requestDTO.setChargesMap(this.getChargesMap());
		setOfflineDetailsDTO(offline, requestDTO);
		offline.setApplicantAddress(requestDTO.getAreaName());
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("RAF", session.getOrganisation().getOrgid());
		setServiceMaster(sm);
		requestDTO.setServiceId(sm.getSmServiceId());

		if (!requestDTO.isFree()) { // offline or pay@ULB
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			offline.setAmountToPay(paymentAmount.toString());

			if (offline.getOnlineOfflineCheck() != null
					&& MainetConstants.N_FLAG.equalsIgnoreCase(offline.getOnlineOfflineCheck())) {
				final ChallanMaster master = iChallanService.InvokeGenerateChallan(offline);
				offline.setChallanValidDate(master.getChallanValiDate());
				offline.setChallanNo(master.getChallanNo());
				setOfflineDTO(offline);

			} else if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT_TYPE.PAY_AT_COUNTER)) {
				final ChallanReceiptPrintDTO printDto = iChallanService.savePayAtUlbCounter(offline, "RAF");
				if (printDto.getDeptShortCode().equalsIgnoreCase(MainetConstants.DEPT_SHORT_NAME.RTI)) {
					printDto.setDeptName(ApplicationSession.getInstance().getMessage("rti.RePrint.deptName"));
				}
				setReceiptDTO(printDto);

			}
			setSuccessMessage(getAppSession().getMessage("rti.success") +"  "+ getAppSession().getMessage("rti.appno")
					+"  "+ requestDTO.getApmApplicationId() +" "+ getAppSession().getMessage("rti.rtino")+" "+ requestDTO.getRtiNo());
		}

		// Send Email and SMS 

		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setAppName(offline.getApplicantName());
		dto.setMobnumber(offline.getMobileNumber());
		dto.setAppNo(requestDTO.getApmApplicationId().toString());
		dto.setServName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICENAME);
		dto.setEmail(offline.getEmailId());
		String paymentUrl = MainetConstants.RTISERVICE.RTI_SMS_EMAIL;
		Organisation org = new Organisation();
		org.setOrgid(offline.getOrgId());
		int langId = Utility.getDefaultLanguageId(org);
		dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);

		return true;

	}

	private void setOfflineDetailsDTO(CommonChallanDTO offline, RtiApplicationFormDetailsReqDTO requestDTO) {
		/* Setting Offline DTO */
		offline.setFaYearId(UserSession.getCurrent().getFinYearId());
		offline.setFinYearStartDate(UserSession.getCurrent().getFinStartDate());
		offline.setFinYearEndDate(UserSession.getCurrent().getFinEndDate());
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(Long.valueOf(requestDTO.getRtiDeptId()));
		offline.setApplNo(requestDTO.getApmApplicationId());

		String fullName = String.join(" ",
				Arrays.asList(requestDTO.getfName(), requestDTO.getmName(), requestDTO.getlName()));
		offline.setApplicantName(fullName);
		String applicantAddress = String.join(" ", Arrays.asList(requestDTO.getFlatBuildingNo(),
				requestDTO.getBlockName(), requestDTO.getRoadName(), requestDTO.getCityName()));
		offline.setApplicantAddress(applicantAddress);
		offline.setMobileNumber(requestDTO.getMobileNo());
		offline.setEmailId(requestDTO.getEmail());
		offline.setServiceId(requestDTO.getServiceId());
		offline.setUserId(requestDTO.getUserId());
		offline.setLgIpMac(Utility.getMacAddress());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		for (final Map.Entry<Long, Double> entry : requestDTO.getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		setOfflineDTO(offline);

	}

	public String getIsValidationError() {
		return isValidationError;
	}

	public void setIsValidationError(String isValidationError) {
		this.isValidationError = isValidationError;
	}

	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}

	public void setApplicantDTO(ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
	}

	public String getFree() {
		return free;
	}

	public void setFree(String free) {
		this.free = free;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public CFCApplicationAddressEntity getCfcAddressEntity() {
		return cfcAddressEntity;
	}

	public void setCfcAddressEntity(CFCApplicationAddressEntity cfcAddressEntity) {
		this.cfcAddressEntity = cfcAddressEntity;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public List<DocumentDetailsVO> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	public List<CFCAttachment> getFetchDocumentList() {
		return fetchDocumentList;
	}

	public void setFetchDocumentList(List<CFCAttachment> fetchDocumentList) {
		this.fetchDocumentList = fetchDocumentList;
	}

	public List<CFCAttachment> getFetchApplnUpload() {
		return fetchApplnUpload;
	}

	public void setFetchApplnUpload(List<CFCAttachment> fetchApplnUpload) {
		this.fetchApplnUpload = fetchApplnUpload;
	}

	public List<LookUp> getOrgLookups() {
		return orgLookups;
	}

	public void setOrgLookups(List<LookUp> orgLookups) {
		this.orgLookups = orgLookups;
	}

	public List<Organisation> getOrg() {
		return org;
	}

	public void setOrg(List<Organisation> org) {
		this.org = org;
	}


	public List<LookUp> getRefList() {
		return refList;
	}

	public void setRefList(List<LookUp> refList) {
		this.refList = refList;
	}

	public Set<LookUp> getRelated_dept() {
		return related_dept;
	}

	public void setRelated_dept(Set<LookUp> related_dept) {
		this.related_dept = related_dept;
	}

	public List<DocumentDetailsVO> getUploadStamoDoc() {
		return uploadStamoDoc;
	}

	public void setUploadStamoDoc(List<DocumentDetailsVO> uploadStamoDoc) {
		this.uploadStamoDoc = uploadStamoDoc;
	}
	

}
