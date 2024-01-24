package com.abm.mainet.rts.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.rts.dto.FirstAppealDto;
import com.abm.mainet.rts.service.AppealService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class RtsFirstAppealModel extends AbstractFormModel {

	private static final long serialVersionUID = 7008151336896193611L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RtsFirstAppealModel.class);

	private String isValidationError;
	private String saveMode;
	private String errorMessage;
	private Long deptId;
	private String permanentAddress;
	private ObjectionDetailsDto objectionDetailsDto = new ObjectionDetailsDto();
	private FirstAppealDto firstAppealDto = new FirstAppealDto();
	

	@Autowired
	private AppealService appeal;

	public String getIsValidationError() {
		return isValidationError;
	}

	public void setIsValidationError(String isValidationError) {
		this.isValidationError = isValidationError;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public ObjectionDetailsDto getObjectionDetailsDto() {
		return objectionDetailsDto;
	}

	public void setObjectionDetailsDto(ObjectionDetailsDto objectionDetailsDto) {
		this.objectionDetailsDto = objectionDetailsDto;
	}

	public FirstAppealDto getFirstAppealDto() {
		return firstAppealDto;
	}

	public void setFirstAppealDto(FirstAppealDto firstAppealDto) {
		this.firstAppealDto = firstAppealDto;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	


	public boolean saveForm() {
		final UserSession session = UserSession.getCurrent();
		ObjectionDetailsDto objectionDto = this.getObjectionDetailsDto();
		// data set from firstAppealDto to objectionDto
		objectionDto.setApmApplicationId(firstAppealDto.getApplicationNo());
		objectionDto.setObjectionReferenceNumber(firstAppealDto.getApplicationNo().toString());
		String adharNo = firstAppealDto.getApplicantDetailDTO().getAadharNo();
		objectionDto.setUid(adharNo != null ? Long.valueOf(adharNo) : null);
		objectionDto.setObjectionDate(new Date());// TODO: doubt ask to sir
		objectionDto.setObjTime(Utility.getTimestamp());
		objectionDto.setServiceId(firstAppealDto.getServiceId());
		objectionDto.setObjectionDeptId(firstAppealDto.getDeptId());
		objectionDto.setObjectionDetails(firstAppealDto.getGroundForAppeal());
		objectionDto.setMobileNo(firstAppealDto.getApplicantDetailDTO().getMobileNo());
		// set objectionOn OBJ_TYPE using prefix OBJ
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp("FRTS", "OBJ");// 1st appeal RTS status
		objectionDto.setObjectionOn(lookup != null ? lookup.getLookUpId() : null);
		// TODO: doubt ask to where i set reason for appeal
		objectionDto.setTitle(firstAppealDto.getApplicantDetailDTO().getApplicantTitle());
		objectionDto.setfName(firstAppealDto.getApplicantDetailDTO().getApplicantFirstName());
		objectionDto.setmName(firstAppealDto.getApplicantDetailDTO().getApplicantMiddleName());
		objectionDto.setlName(firstAppealDto.getApplicantDetailDTO().getApplicantLastName());
		objectionDto.setGender(Long.valueOf(firstAppealDto.getApplicantDetailDTO().getGender()));
		objectionDto.seteMail(firstAppealDto.getApplicantDetailDTO().getEmailId());
		objectionDto.setAddress(firstAppealDto.getPermanentAddress());
		objectionDto.setLangId((long) session.getLanguageId());
		objectionDto.setUserId(session.getEmployee().getEmpId());
		objectionDto.setOrgId(session.getOrganisation().getOrgid());
		objectionDto.setObjectionReason(firstAppealDto.getReasonForAppeal());
		objectionDto.setObjectionStatus(MainetConstants.ApplicationMasterConstant.APPLICATION_STATUS_PENDING);
		objectionDto.setIpAddress(session.getEmployee().getEmppiservername());
		Employee emp = UserSession.getCurrent().getEmployee();
		objectionDto.setEname(emp.getEmpname());
		objectionDto.setEmpType(emp.getEmplType());
		objectionDto.setIsPaymentGenerated(false);
		objectionDto.setPaymentMode(MainetConstants.FlagN);
		List<DocumentDetailsVO> docs = prepareFileUpload();
		objectionDto.setDocs(docs);
		ObjectionDetailsDto objDto = appeal.saveFirstAppealInObjection(objectionDto);
		this.setObjectionDetailsDto(objDto);
		if (objDto.getObjectionNumber() != null) {
			setSuccessMessage("First Appeal raised Successfully Application Number : " + objDto.getApmApplicationId()
					+ ", Appeal Number : " + objDto.getObjectionNumber());
		} else {
			for(String error:objDto.getErrorList()){
	              addValidationError(error);
	              return false;
	      	}
			//addValidationError("workflow not initiate try again after some time");
		}
		return true;

	}


	public List<DocumentDetailsVO> prepareFileUpload() {
		long count = 0;
		List<DocumentDetailsVO> docs = null;
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			docs = new ArrayList<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						DocumentDetailsVO d = new DocumentDetailsVO();
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						d.setDocumentByteCode(bytestring);
						d.setDocumentName(file.getName());
						d.setDocumentSerialNo(count);
						count++;
						docs.add(d);
					} catch (final IOException e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		return docs;
	}

}
