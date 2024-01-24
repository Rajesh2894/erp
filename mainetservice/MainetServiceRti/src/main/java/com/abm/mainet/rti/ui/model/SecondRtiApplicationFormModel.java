package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.validator.SecondRtiApplicationValidator;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class SecondRtiApplicationFormModel extends AbstractFormModel {

	private static final long serialVersionUID = 2248475580593264085L;

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

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();
	private List<DocumentDetailsVO> uploadFileList = new ArrayList<>();

	public List<DocumentDetailsVO> getSecondcheckList() {
		return secondcheckList;
	}

	public void setSecondcheckList(List<DocumentDetailsVO> secondcheckList) {
		this.secondcheckList = secondcheckList;
	}

	private List<DocumentDetailsVO> secondcheckList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private Long orgId;

	private Long DeptId;

	private String isFree;

	private String free = "O";

	private Set<LookUp> departments = new HashSet<>();

	private Set<LookUp> locations = new HashSet<>();

	private ServiceMaster serviceMaster = new ServiceMaster();

	private String isValidationError;

	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();

	private CFCApplicationAddressEntity cfcAddressEntity = new CFCApplicationAddressEntity();
	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	List<RtiApplicationFormDetailsReqDTO> rtiApplicationList = new ArrayList<>();

	public List<RtiApplicationFormDetailsReqDTO> getRtiApplicationList() {
		return rtiApplicationList;
	}

	public void setRtiApplicationList(List<RtiApplicationFormDetailsReqDTO> rtiApplicationList) {
		this.rtiApplicationList = rtiApplicationList;
	}

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

	/* for validating Inputs and other functional validation */
	public boolean validateInputs() {
		validateBean(this, SecondRtiApplicationValidator.class);

		if (hasValidationErrors()) {
			this.isValidationError = MainetConstants.Y_FLAG;
			return false;
		}
		return true;
	}

	// end of
	// validation method
	// adding for only document validation
	public boolean validateInputDocs(final List<DocumentDetailsVO> dto) {
		boolean flag = true;
		if ((dto != null) && !dto.isEmpty()) {
			for (final DocumentDetailsVO doc : dto) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.FlagY)) {
					if (doc.getDocumentByteCode() == null) {
						addValidationError(getAppSession().getMessage("water.fileuplaod.validtnMsg"));

						flag = false;
						break;
					}
				}
			}

		}

		return flag;
	}

	@Override
	protected void initializeModel() {

		departments.addAll(
				rtiApplicationDetailService.getActiveDepartment(UserSession.getCurrent().getOrganisation().getOrgid()));

	}

	/* Method for saving second Rti Application */

	@Override
	public boolean saveForm() {

		RtiApplicationFormDetailsReqDTO requestDTO = this.getReqDTO();
		final UserSession session = UserSession.getCurrent();
		final Date sysDate = UtilityService.getSQLDate(new Date());

		{
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

		requestDTO.setServiceId(getServiceId());

		requestDTO.setAppealType(MainetConstants.FlagS);

		/* creating second RTI Application */
		requestDTO = rtiApplicationDetailService.saveSecondApplication(requestDTO);
		setSuccessMessage(getAppSession().getMessage("rti.success"));

		// For filtering deprtment RTI from lookup
		List<LookUp> lookUp = CommonMasterUtility.getListLookup(MainetConstants.LookUpPrefix.PFR,
				UserSession.getCurrent().getOrganisation());
		if (lookUp.get(0).getLookUpDesc().trim().equalsIgnoreCase(MainetConstants.APP_NAME.DSCL)) {

			setSuccessMessage(getAppSession().getMessage("rti.success") + " Application Number : "
					+ requestDTO.getApmApplicationId() + ",RTI Number : " + requestDTO.getRtiNo());

		}
		return true;

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

}
