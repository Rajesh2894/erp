package com.abm.mainet.property.ui.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.AttachDocs;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.property.dto.PropertyEditNameAddressDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentOwnerDtlDto;
import com.abm.mainet.property.service.SelfAssessmentService;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class EditNameAddressModel extends AbstractFormModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(EditNameAddressModel.class);

	@Autowired
	private SelfAssessmentService assessmentService;
	
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto;
    private ProvisionalAssesmentOwnerDtlDto provisionalAssesmentOwnerDtlDto;
    private String searchFlag;
    private String ownershipPrefix;
    private String ownershipTypeValue;
    private List<LookUp> location = new ArrayList<>(0);
    private Long deptId;
    private String modeType;
    private List<AttachDocs> attachDocsList = new ArrayList<>();
    
    private String billingMethod;
    private String flatNo;
    private List<String> flatNoList = new ArrayList<>();
    private Long billingMethodId;
    private String editType;
    private PropertyEditNameAddressDto editNameAddressDto = new PropertyEditNameAddressDto();
    private List<PropertyEditNameAddressDto> editNameAddressDtoList = new ArrayList<>();
    private List<DocumentDetailsVO> checkList = new ArrayList<>();
    private CommonChallanDTO offlineDTO = new CommonChallanDTO();
    private List<Long> finYearList = new ArrayList<>(0);
    private List<ProvisionalAssesmentMstDto> provAssMstDtoList = new ArrayList<>();
    private String date;
    private String dueDate;
	private String finYear;
	private String time;
	private ServiceMaster serviceMast = new ServiceMaster();
	private String deptName;// to print of acknowledgement
	private String applicantName;
	private String referenceNo;
	private String updateAllFlatAddress;
	private String orgNameMar;
	
	@Override
	public boolean saveForm() {
		LOGGER.info("Begin -->  " + this.getClass().getSimpleName() + " saveForm() method");
    	setCustomViewName("EditNameAddressForm");
        List<DocumentDetailsVO> docs = getCheckList();
        if(CollectionUtils.isNotEmpty(docs)) {
        	docs = setFileUploadMethod(docs);
        	editNameAddressDto.setDocs(docs);        	
        }
        if ((docs != null) && !docs.isEmpty()) {
			for (final DocumentDetailsVO doc : docs) {
				if (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y)) {
					if (doc.getDocumentByteCode() == null) {
						this.addValidationError(ApplicationSession.getInstance().getMessage("property.mandtory.docs"));
						break;
					}
				}
			}
		}
        if (hasValidationErrors()) {
            return false;
        }
        provisionalAssesmentMstDto.setDocs(docs);
        editNameAddressDto.setDocs(docs);
        
        provisionalAssesmentMstDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
        editNameAddressDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        editNameAddressDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        editNameAddressDto.setCreatedDate(new Date());
        editNameAddressDto.setDeptId(deptId);
        
        editNameAddressDto.setServiceId(getServiceId());
        editNameAddressDto.setLangId(UserSession.getCurrent().getLanguageId());
        editNameAddressDto.setProvisionalAssesmentMstDto(provisionalAssesmentMstDto);
        provisionalAssesmentMstDto.setSmServiceId(getServiceId());
        for(ProvisionalAssesmentDetailDto detailDto: provisionalAssesmentMstDto.getProvisionalAssesmentDetailDtoList()) {
        	detailDto.setFaYearId(provisionalAssesmentMstDto.getFaYearId());
        }
        StringJoiner applicantName = new StringJoiner(" , ");
        for(ProvisionalAssesmentOwnerDtlDto ownerDtlDto : provisionalAssesmentMstDto.getProvisionalAssesmentOwnerDtlDtoList()) {
        	ownerDtlDto.setSmServiceId(getServiceId());
        	applicantName.add(ownerDtlDto.getAssoOwnerName());
        }
        setApplicantName(applicantName.toString());
        editNameAddressDto.setFinYearList(getFinYearList());
        
        LOGGER.info("Before saveEditNameAndAddress() method call");
        PropertyEditNameAddressDto nameAddressDto = assessmentService.saveEditNameAndAddress(editNameAddressDto);
        LOGGER.info("After saveEditNameAndAddress() method call");
        
        if(nameAddressDto != null) {
        	setReferenceNo(String.valueOf(nameAddressDto.getApplicationId()));
        	setSuccessMessage(getAppSession().getMessage("edit.name.and.address.submit") + " " + nameAddressDto.getApplicationId());
        	LOGGER.info("End -->  " + this.getClass().getSimpleName() + " saveForm() method");
        	return true;
        }
		return false;
    
    		
    }
	
	private List<DocumentDetailsVO> setFileUploadMethod(
	           final List<DocumentDetailsVO> docs) {
	       final Map<Long, String> listOfString = new HashMap<>();
	       final Map<Long, String> fileName = new HashMap<>();

	       if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
	           Base64 base64 = null;
	           List<File> list = null;
	           for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
	               list = new ArrayList<>(entry.getValue());
	               for (final File file : list) {
	                   try {
	                       base64 = new Base64();
	                       final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
	                       fileName.put(entry.getKey(), file.getName());
	                       listOfString.put(entry.getKey(), bytestring);
	                   } catch (final IOException e) {
	                	   throw new FrameworkException("Exception has been occurred in file byte to string conversions", e);
	                   }
	               }
	           }
	       }
	       if (!docs.isEmpty() && !listOfString.isEmpty()) {
	           for (final DocumentDetailsVO d : docs) {
	               final long count = d.getDocumentSerialNo() - 1;
	               if (listOfString.containsKey(count) && fileName.containsKey(count)) {
	                   d.setDocumentByteCode(listOfString.get(count));
	                   d.setDocumentName(fileName.get(count));
	               }
	           }
	       }
	       return docs;
	   }
	
	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}
	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}
	public ProvisionalAssesmentOwnerDtlDto getProvisionalAssesmentOwnerDtlDto() {
		return provisionalAssesmentOwnerDtlDto;
	}
	public void setProvisionalAssesmentOwnerDtlDto(ProvisionalAssesmentOwnerDtlDto provisionalAssesmentOwnerDtlDto) {
		this.provisionalAssesmentOwnerDtlDto = provisionalAssesmentOwnerDtlDto;
	}
	public String getSearchFlag() {
		return searchFlag;
	}
	public void setSearchFlag(String searchFlag) {
		this.searchFlag = searchFlag;
	}
	public String getOwnershipPrefix() {
		return ownershipPrefix;
	}
	public void setOwnershipPrefix(String ownershipPrefix) {
		this.ownershipPrefix = ownershipPrefix;
	}
	public String getOwnershipTypeValue() {
		return ownershipTypeValue;
	}
	public void setOwnershipTypeValue(String ownershipTypeValue) {
		this.ownershipTypeValue = ownershipTypeValue;
	}
	public List<LookUp> getLocation() {
		return location;
	}
	public void setLocation(List<LookUp> location) {
		this.location = location;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public String getModeType() {
		return modeType;
	}
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	public String getBillingMethod() {
		return billingMethod;
	}
	public void setBillingMethod(String billingMethod) {
		this.billingMethod = billingMethod;
	}
	public String getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	public List<String> getFlatNoList() {
		return flatNoList;
	}
	public void setFlatNoList(List<String> flatNoList) {
		this.flatNoList = flatNoList;
	}
	public Long getBillingMethodId() {
		return billingMethodId;
	}
	public void setBillingMethodId(Long billingMethodId) {
		this.billingMethodId = billingMethodId;
	}
	public String getEditType() {
		return editType;
	}
	public void setEditType(String editType) {
		this.editType = editType;
	}
	public PropertyEditNameAddressDto getEditNameAddressDto() {
		return editNameAddressDto;
	}
	public void setEditNameAddressDto(PropertyEditNameAddressDto editNameAddressDto) {
		this.editNameAddressDto = editNameAddressDto;
	}
	public List<PropertyEditNameAddressDto> getEditNameAddressDtoList() {
		return editNameAddressDtoList;
	}
	public void setEditNameAddressDtoList(List<PropertyEditNameAddressDto> editNameAddressDtoList) {
		this.editNameAddressDtoList = editNameAddressDtoList;
	}
	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}
	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}
	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}
	public List<Long> getFinYearList() {
		return finYearList;
	}
	public void setFinYearList(List<Long> finYearList) {
		this.finYearList = finYearList;
	}
	public List<ProvisionalAssesmentMstDto> getProvAssMstDtoList() {
		return provAssMstDtoList;
	}
	public void setProvAssMstDtoList(List<ProvisionalAssesmentMstDto> provAssMstDtoList) {
		this.provAssMstDtoList = provAssMstDtoList;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getFinYear() {
		return finYear;
	}
	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public ServiceMaster getServiceMast() {
		return serviceMast;
	}
	public void setServiceMast(ServiceMaster serviceMast) {
		this.serviceMast = serviceMast;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getUpdateAllFlatAddress() {
		return updateAllFlatAddress;
	}
	public void setUpdateAllFlatAddress(String updateAllFlatAddress) {
		this.updateAllFlatAddress = updateAllFlatAddress;
	}

	public String getOrgNameMar() {
		return orgNameMar;
	}

	public void setOrgNameMar(String orgNameMar) {
		this.orgNameMar = orgNameMar;
	}
	
	
	
}
