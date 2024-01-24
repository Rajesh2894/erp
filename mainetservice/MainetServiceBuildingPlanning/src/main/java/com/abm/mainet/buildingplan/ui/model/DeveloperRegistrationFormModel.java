package com.abm.mainet.buildingplan.ui.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.buildingplan.dto.DeveloperRegistrationDTO;
import com.abm.mainet.buildingplan.service.IDeveloperRegistrationService;
import com.abm.mainet.buildingplan.ui.validator.developerRegFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class DeveloperRegistrationFormModel extends AbstractFormModel implements Serializable{

	private static final long serialVersionUID = -4581406305431125531L;

	private DeveloperRegistrationDTO developerRegistrationDTO = new DeveloperRegistrationDTO();

	private String removeStorageInfoIds;

	private List<Long> removedIds;

	@Resource
	private IFileUploadService fileUploadService;

	@Resource
	private IDeveloperRegistrationService devRegService;
	
	@Autowired
	ServiceMasterService serviceMaster;
	
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	
	private Long authId;
	
	private Long directId;
	
	private Long stkId;
	
	private String perLetter;
	
	private String panVerifyFlg;

	private String panVerifySuccessMsg;
	
	private String isMobileValidation;
	
	private Long orgId;
	
	private Long serviceId;
	
	private String deptCode;
	
	private List<DocumentDetailsVO> checkListDoc = new ArrayList<>();
		
	private List<CFCAttachment> checkListDocument;
	
	public DeveloperRegistrationDTO getDeveloperRegistrationDTO() {
		return developerRegistrationDTO;
	}

	public void setDeveloperRegistrationDTO(DeveloperRegistrationDTO developerRegistrationDTO) {
		this.developerRegistrationDTO = developerRegistrationDTO;
	}

	public String getRemoveStorageInfoIds() {
		return removeStorageInfoIds;
	}

	public void setRemoveStorageInfoIds(String removeStorageInfoIds) {
		this.removeStorageInfoIds = removeStorageInfoIds;
	}

	public List<Long> getRemovedIds() {
		return removedIds;
	}

	public void setRemovedIds(List<Long> removedIds) {
		this.removedIds = removedIds;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	@Override
	public boolean saveForm(){
		if(getDeveloperRegistrationDTO().getDirectorInfoFlag()!=null){
    		if(getDeveloperRegistrationDTO().getDirectorInfoFlag().equals(MainetConstants.FlagN)){
        		getDeveloperRegistrationDTO().getDeveloperDirectorInfoDTOList().clear();
        	}
    	}
    	if(getDeveloperRegistrationDTO().getLicenseHDRUFlag()!=null){
    		if(getDeveloperRegistrationDTO().getLicenseHDRUFlag().equals(MainetConstants.FlagN)){
        		getDeveloperRegistrationDTO().getDevLicenseHDRUDTOList().clear();
        	}
    	}
		Employee loggedInUser = UserSession.getCurrent().getEmployee();
		String lgIpMacUpd = UserSession.getCurrent().getEmployee().getLgIpMacUpd();
			ServiceMaster serviceMst = serviceMaster.getServiceByShortName("DRN",
					UserSession.getCurrent().getOrganisation().getOrgid());
			
			developerRegistrationDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			developerRegistrationDTO.setLangId(UserSession.getCurrent().getLanguageId());
			
			if (developerRegistrationDTO.getDevId() != null && developerRegistrationDTO.getDevId() > 0) {
				developerRegistrationDTO.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				developerRegistrationDTO.setUpdatedDate(new Date());
				developerRegistrationDTO.setLgIpMacUp(UserSession.getCurrent().getEmployee().getEmppiservername());
			} else {
				developerRegistrationDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				developerRegistrationDTO.setCreatedDate(new Date());
				developerRegistrationDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			}

			developerRegistrationDTO = devRegService.saveDeveloperRegForm(getDeveloperRegistrationDTO(),
					loggedInUser, lgIpMacUpd, UserSession.getCurrent().getOrganisation().getOrgid(),
					serviceMst.getSmServiceId(), serviceMst.getTbDepartment().getDpDeptcode());
			/*if (saveDeveloperRegForm) {*/			
				setSuccessMessage(getAppSession().getMessage("success.msg.key"));
			/*} else {
				setSuccessMessage(getAppSession().getMessage("FAIL"));
			}*/
			return true;			
	}

	public boolean validateInputs() {
		//validateBean(this, developerRegFormValidator.class);
		final List<DocumentDetailsVO> docList = fileUploadService.prepareFileUpload(getCheckList());
		setCheckListDoc(docList);
		if ((docList != null) && !docList.isEmpty()) {
			for (final DocumentDetailsVO doc : docList) {
				if(doc.getCheckkMANDATORY()!=null){
					if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) && doc.getDocumentByteCode() == null) {
						addValidationError(ApplicationSession.getInstance().getMessage("please upload ")+doc.getDoc_DESC_ENGL());
					}
				}					
			}
		}
		
		for (final Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			if (entry.getKey().longValue() == 401) {
				final Set<File> set = entry.getValue();
				final File file = set.iterator().next();
				String bytestring = MainetConstants.BLANK;
				final Base64 base64 = new Base64();
				try {
					bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
				} catch (final IOException e) {
					logger.error("Exception has been occurred in file byte to string conversions", e);
				}
				final String perLetter = file.getName();
				setPerLetter(perLetter);
				break;
			}
		}
		if(getDeveloperRegistrationDTO().getProjectsFlag()!=null){
			if(getDeveloperRegistrationDTO().getProjectsFlag().equals(MainetConstants.FlagY)){
				if ((getPerLetter() == null) || getPerLetter().isEmpty()) {
					addValidationError(
							ApplicationSession.getInstance().getMessage("Please upload Permission letter"));
				}
			}
		}
			
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public Long getDirectId() {
		return directId;
	}

	public void setDirectId(Long directId) {
		this.directId = directId;
	}

	public Long getStkId() {
		return stkId;
	}

	public void setStkId(Long stkId) {
		this.stkId = stkId;
	}

	public String getPerLetter() {
		return perLetter;
	}

	public void setPerLetter(String perLetter) {
		this.perLetter = perLetter;
	}

	public String getPanVerifyFlg() {
		return panVerifyFlg;
	}

	public void setPanVerifyFlg(String panVerifyFlg) {
		this.panVerifyFlg = panVerifyFlg;
	}

	public String getPanVerifySuccessMsg() {
		return panVerifySuccessMsg;
	}

	public void setPanVerifySuccessMsg(String panVerifySuccessMsg) {
		this.panVerifySuccessMsg = panVerifySuccessMsg;
	}

	public String getIsMobileValidation() {
		return isMobileValidation;
	}

	public void setIsMobileValidation(String isMobileValidation) {
		this.isMobileValidation = isMobileValidation;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<DocumentDetailsVO> getCheckListDoc() {
		return checkListDoc;
	}

	public void setCheckListDoc(List<DocumentDetailsVO> checkListDoc) {
		this.checkListDoc = checkListDoc;
	}

	public List<CFCAttachment> getCheckListDocument() {
		return checkListDocument;
	}

	public void setCheckListDocument(List<CFCAttachment> checkListDocument) {
		this.checkListDocument = checkListDocument;
	}
}
