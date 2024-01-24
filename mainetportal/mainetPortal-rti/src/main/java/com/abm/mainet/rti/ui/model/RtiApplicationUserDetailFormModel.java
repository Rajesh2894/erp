package com.abm.mainet.rti.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.dto.RequestDTO;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.payment.dto.PaymentRequestDTO;
import com.abm.mainet.payment.service.IChallanService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.validator.RtiApplicationUserDetailFormValidator;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class RtiApplicationUserDetailFormModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();
	private Long orgId;
	private Long DeptId;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String isFree;
	private Double charges = 0.0d;
	private Map<Long, Double> chargesMap = new HashMap<>();
	private Set<LookUp> departments = new HashSet<>();
	private Set<LookUp> locations = new HashSet<>();
	private String isValidationError;
	private PortalService serviceMaster = new PortalService();
	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();
	private List<Organisation> org = new ArrayList<>();
	private String backBtn;
	private String showBtn;
	List<LookUp> orgLookups = new ArrayList<>();

	List<LookUp> refrnceList = new ArrayList<>(); 

	private Set<LookUp> related_dept = new HashSet<>();

	private List<DocumentDetailsVO> fetchStampDoc = new ArrayList<DocumentDetailsVO>();

	@Autowired
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	IPortalServiceMasterService iPortalService;

	@Autowired
	private IChallanService challanService;

	@Autowired
	private ISMSAndEmailService ismsAndEmailService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

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

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
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

	public String getIsValidationError() {
		return isValidationError;
	}

	public void setIsValidationError(String isValidationError) {
		this.isValidationError = isValidationError;
	}

	@Override
	protected void initializeModel() {

		/* Getting Active Department with workflow configured */

		departments.addAll(rtiApplicationDetailService.getRtiWorkflowMasterDefinedDepartmentListByOrgId(
				UserSession.getCurrent().getOrganisation().getOrgid()));

		/* end */

	}

	/* Method for saving Rti Application */
	@Override
	public boolean saveForm() throws Exception {
		setCommonFields(this);
		CommonChallanDTO offline = getOfflineDTO();
		RtiApplicationFormDetailsReqDTO requestDTO = this.getReqDTO();
		final UserSession session = UserSession.getCurrent();
		Long orgId = requestDTO.getOrgId();

		final Date sysDate = new java.sql.Date(new Date().getTime());
		String refMode = CommonMasterUtility.findLookUpCode("RRM", orgId, reqDTO.getApplReferenceMode());
		String applicantCode = CommonMasterUtility.findLookUpCode("ATP",
				UserSession.getCurrent().getOrganisation().getOrgid(), reqDTO.getApplicationType());
		String bpl = CommonMasterUtility.findLookUpCode("YNC", orgId, Long.valueOf(reqDTO.getIsBPL()));
		Double paymentAmount = offline.getAmountToShow();
		if ((applicantCode.equals("O") && (refMode.equals("D")))
				|| (applicantCode.equals("I") && bpl.equals("N") && (refMode.equals("D")))) {
			if (paymentAmount != null && paymentAmount > 0) {
				requestDTO.setFree(false);
				// requestDTO.setRtiBplFlag(MainetConstants.FlagN);

			}
		} else {
			requestDTO.setFree(true);
			requestDTO.setPayStatus(MainetConstants.PAYMENT.FREE);
			// requestDTO.setRtiBplFlag(MainetConstants.FlagY);
		}

		String bplNo = requestDTO.getBplNo();
		if (bplNo != null && !bplNo.isEmpty()) {
			requestDTO.setRtiBplFlag("Y");
		} else {
			requestDTO.setRtiBplFlag("N");
		}

		/*
		 * Double paymentAmount = offline.getAmountToShow(); if (paymentAmount > 0) {
		 * requestDTO.setFree(false); } else { requestDTO.setFree(true);
		 * requestDTO.setPayStatus(MainetConstants.PAYMENT.FREE); }
		 */
		requestDTO.setUserId(session.getEmployee().getEmpId());
		requestDTO.setLangId((long) session.getLanguageId());
		requestDTO.setOrgId(orgId);
		requestDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		requestDTO.setlModDate(sysDate);
		requestDTO.setUpdatedBy(session.getEmployee().getEmpId());
		requestDTO.setUpdateDate(sysDate);
		requestDTO.setApmApplicationDate(sysDate);

		offline.setServiceId(requestDTO.getServiceId());
		offline.setLangId(session.getLanguageId());
		if (paymentAmount != null) {
			offline.setAmountToPay(paymentAmount.toString());
			requestDTO.getOfflineDTO().setAmountToShow(paymentAmount);
			requestDTO.getOfflineDTO().setAmountToPay(paymentAmount.toString());
		}
		requestDTO.getOfflineDTO().setOnlineOfflineCheck(getOfflineDTO().getOnlineOfflineCheck());

		/*
		 * RequestDTO dto = new RequestDTO();
		 * dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		 * dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		 * dto.setDepartmentName("RTI");
		 * requestDTO.setDocumentList(getFileUploadList(getCheckList(),
		 * FileUploadUtility.getCurrent() .getFileMap()));
		 */

		requestDTO = rtiApplicationDetailService.saveorUpdateRtiApplication(requestDTO);
		this.setReqDTO(requestDTO);
		this.setSuccessMessage(getAppSession().getMessage("rti.successMsg") + requestDTO.getApmApplicationId());
		// setOfflineDetailsDTO(offline, requestDTO);

		final String modeDesc = getNonHierarchicalLookUpObject(offline.getOflPaymentMode()).getLookUpCode();
		offline.setOfflinePaymentText(modeDesc);
		if (((offline.getOnlineOfflineCheck() != null)
				&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO))
				|| (getCharges() > 0d)) {
			offline.setApplNo(requestDTO.getApmApplicationId());
			offline.setAmountToPay(Double.toString(getCharges()));
			offline.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			offline.setOrgId(orgId);
			offline.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			offline.setLangId(UserSession.getCurrent().getLanguageId());
			offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
			if ((getCheckList() != null) && (getCheckList().size() > 0)) {
				offline.setDocumentUploaded(true);
			} else {
				offline.setDocumentUploaded(false);
			}

			String fullName = String.join(" ",
					Arrays.asList(requestDTO.getfName(), requestDTO.getmName(), requestDTO.getlName()));
			offline.setApplicantName(fullName);
			String applicantAddress = String.join(" ", Arrays.asList(requestDTO.getFlatBuildingNo(),
					requestDTO.getBlockName(), requestDTO.getRoadName(), requestDTO.getCityName()));
			offline.setApplicantAddress(applicantAddress);
			offline.setMobileNumber(requestDTO.getMobileNo());
			offline.setEmailId(requestDTO.getEmail());
			offline.setServiceId(requestDTO.getServiceId());
			offline.setDeptId(Long.valueOf(requestDTO.getRtiDeptId()));
			offline.setUserId(requestDTO.getUserId());
			offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			offline.setLangId(UserSession.getCurrent().getLanguageId());
			for (final Map.Entry<Long, Double> entry : getChargesMap().entrySet()) {
				offline.getFeeIds().put(entry.getKey(), entry.getValue());
			}
			offline.setOfflinePaymentText(CommonMasterUtility.getNonHierarchicalLookUpObject(
					offline.getOflPaymentMode(), UserSession.getCurrent().getOrganisation()).getLookUpCode());
			if ((offline.getOnlineOfflineCheck() != null)
					&& offline.getOnlineOfflineCheck().equals(MainetConstants.NewWaterServiceConstants.NO)) {

				offline = challanService.generateChallanNumber(offline);
				setOfflineDTO(offline);
			}
		}

		return true;
	}

	/* end */

	private void setCommonFields(RtiApplicationUserDetailFormModel model) {
		final Date sysDate = new Date();
		RequestDTO requestDTO = new RequestDTO();
		final RtiApplicationFormDetailsReqDTO dto = model.getReqDTO();
		final Long orgId = dto.getOrgId();
		// dto.setOrgid(orgId);
		final Long serviceId = iPortalServiceMasterService
				.getServiceId(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getServiceId());
		model.getReqDTO().setServiceId(service.getServiceId());
		model.getReqDTO().setSmServiceId(service.getServiceId());
		// dto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		dto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		dto.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		dto.setLgIpMac(dto.getLgIpMac());
		// requestDTO.setUserId(dto.getCreatedBy());
		// dto.setCreatedDate(sysDate);
		long deptId = service.getPsmDpDeptid();
		requestDTO.setDeptId(deptId);
	}

	private void setOfflineDetailsDTO(CommonChallanDTO offline, RtiApplicationFormDetailsReqDTO requestDTO) {
		/* Setting Offline DTO */
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.NON_REVENUE_BASED);
		offline.setOrgId(requestDTO.getOrgId());
		offline.setDeptId(Long.valueOf(requestDTO.getRtiDeptId()));
		offline.setApplNo(requestDTO.getApplicationId());

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
		offline.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
		offline.setLangId(UserSession.getCurrent().getLanguageId());
		for (final Map.Entry<Long, Double> entry : requestDTO.getChargesMap().entrySet()) {
			offline.getFeeIds().put(entry.getKey(), entry.getValue());
		}
		setOfflineDTO(offline);
		/* end of setting DTO */
	}

	public String getShowBtn() {
		return showBtn;
	}

	public void setShowBtn(String showBtn) {
		this.showBtn = showBtn;
	}

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {
		// FileUploadServiceValidator.getCurrent().validateUpload(getBindingResult());
		validateBean(this, RtiApplicationUserDetailFormValidator.class);

		if (hasValidationErrors()) {
			this.isValidationError = MainetConstants.Common_Constant.YES;
			return false;
		}
		return true;
	}
	/* end of validation method */

	@Override
	public void redirectToPayDetails(final HttpServletRequest httpServletRequest,
			final PaymentRequestDTO payURequestDTO) {
		final RtiApplicationFormDetailsReqDTO reqDTO = this.getReqDTO();
		final PortalService portalServiceMaster = iPortalServiceMasterService.getService(getServiceId(),
				reqDTO.getOrgId());
		payURequestDTO.setUdf3("CitizenHome.html");
		payURequestDTO.setUdf5(portalServiceMaster.getShortName());
		payURequestDTO.setUdf7(String.valueOf(reqDTO.getApplicationId()));
		String fullName = String.join(" ", Arrays.asList(reqDTO.getfName(), reqDTO.getmName(), reqDTO.getlName()));
		payURequestDTO.setApplicantName(fullName);
		payURequestDTO.setServiceId(portalServiceMaster.getServiceId());
		payURequestDTO.setUdf2(String.valueOf(reqDTO.getApplicationId()));
		payURequestDTO.setMobNo(reqDTO.getMobileNo().toString());
		if (getCharges() != null) {
			payURequestDTO.setDueAmt(new BigDecimal(getCharges()));
		}
		payURequestDTO.setOrgId(reqDTO.getOrgId());
		// payURequestDTO.setDueAmt(paymentAmount);
		payURequestDTO.setEmail(reqDTO.getEmail());
		payURequestDTO.setApplicationId(reqDTO.getApmApplicationId().toString());
		if (portalServiceMaster != null) {
			payURequestDTO.setUdf10(portalServiceMaster.getPsmDpDeptid().toString());
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceName());
			} else {
				payURequestDTO.setServiceName(portalServiceMaster.getServiceNameReg());
			}
		}
	}

	public PortalService getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(PortalService serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public List<DocumentDetailsVO> getUploadFileList() {
		return uploadFileList;
	}

	public void setUploadFileList(List<DocumentDetailsVO> uploadFileList) {
		this.uploadFileList = uploadFileList;
	}

	public String getBackBtn() {
		return backBtn;
	}

	public void setBackBtn(String backBtn) {
		this.backBtn = backBtn;
	}

	public List<Organisation> getOrg() {
		return org;
	}

	public void setOrg(List<Organisation> org) {
		this.org = org;
	}

	public List<LookUp> getOrgLookups() {
		return orgLookups;
	}

	public void setOrgLookups(List<LookUp> orgLookups) {
		this.orgLookups = orgLookups;
	}

	public List<LookUp> getRefrnceList() {
		return refrnceList;
	}

	public void setRefrnceList(List<LookUp> refrnceList) {
		this.refrnceList = refrnceList;
	}

	public Set<LookUp> getRelated_dept() {
		return related_dept;
	}

	public void setRelated_dept(Set<LookUp> related_dept) {
		this.related_dept = related_dept;
	}

	public List<DocumentDetailsVO> getFetchStampDoc() {
		return fetchStampDoc;
	}

	public void setFetchStampDoc(List<DocumentDetailsVO> fetchStampDoc) {
		this.fetchStampDoc = fetchStampDoc;
	}

}
