package com.abm.mainet.workManagement.ui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbOrgDesignation;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.BidMasterDto;
import com.abm.mainet.workManagement.dto.TenderMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.repository.WmsProjectMasterRepository;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.ui.validator.TenderInitiationValidatior;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class TenderInitiationModel extends AbstractFormModel {

	private static final long serialVersionUID = 1L;

	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private WmsProjectMasterRepository wmsProjectMasterRepository;
	
	@Autowired
	private TbServicesMstService servicesMstService;

	@Resource
	TbDepartmentService departmentService;
	
	private List<WorkDefinitionDto> workList;
	private List<TbDepartment> departmentList;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	// Unit lookup List fetch by using 'VEC' prefix
	private List<LookUp> venderCategoryList = new ArrayList<>();
	// Unit lookup List fetch by using 'WKC' prefix
	private List<LookUp> workDurationUnit = new ArrayList<>();
	private TenderMasterDto initiationDto;
	private String mode;
	private String removeWorkIdArray;
	private Long deleteFileId;
	private TenderWorkDto loaTenderDetails = new TenderWorkDto();
	private TenderWorkDto printNoticeInvintingTender = new TenderWorkDto();
	private TenderWorkDto preQualDocument = new TenderWorkDto();
	private String loaMode;
	private String modeCpd;
	private String awardMode;

	private List<LookUp> tenderTpyes;
	private List<LookUp> tenderPercentageAmount;
	private BigDecimal amountToCheckValidation;
	private TenderWorkDto tenderWorksForms = new TenderWorkDto();
	private String serviceFlag;
	private ServiceMaster serviceMaster;
	private String initiationNo;
	private List<DocumentDetailsVO> checkList = new ArrayList<>();
	private String enableSubmit;
	List<TbOrgDesignation> designationList;

	private String tenderUpdated;
	private List<TbAcVendormaster> vendorList;
	private BidMasterDto bidMasterDto;
	private List<BidMasterDto> bidMasterDtos;
	private String bidMode;

	private List<String> techParamList;
	private List<String> commParamList;
	
	private List<Map<String,Long>> techParamMarks;
	private List<Map<String,Long>> commParamRate;
	
	public List<Map<String, Long>> getTechParamMarks() {
		return techParamMarks;
	}

	public void setTechParamMarks(List<Map<String, Long>> techParamMarks) {
		this.techParamMarks = techParamMarks;
	}

	public List<Map<String, Long>> getCommParamRate() {
		return commParamRate;
	}

	public void setCommParamRate(List<Map<String, Long>> commParamRate) {
		this.commParamRate = commParamRate;
	}

	public List<String> getTechParamList() {
		return techParamList;
	}

	public void setTechParamList(List<String> techParamList) {
		this.techParamList = techParamList;
	}

	public List<String> getCommParamList() {
		return commParamList;
	}

	public void setCommParamList(List<String> commParamList) {
		this.commParamList = commParamList;
	}

	public IFileUploadService getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(IFileUploadService fileUpload) {
		this.fileUpload = fileUpload;
	}

	/*
	 * public List<String> getTechParamList() { return techParamList; }
	 * 
	 * public void setTechParamList(List<String> techParamList) { this.techParamList
	 * = techParamList; }
	 * 
	 * public List<String> getCommParamList() { return commParamList; }
	 * 
	 * public void setCommParamList(List<String> commParamList) { this.commParamList
	 * = commParamList; }
	 */

	public String getBidMode() {
		return bidMode;
	}

	public void setBidMode(String bidMode) {
		this.bidMode = bidMode;
	}

	public List<BidMasterDto> getBidMasterDtos() {
		return bidMasterDtos;
	}

	public void setBidMasterDtos(List<BidMasterDto> bidMasterDtos) {
		this.bidMasterDtos = bidMasterDtos;
	}

	private Long tndId;

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {

		this.tndId = tndId;
	}

	public BidMasterDto getBidMasterDto() {
		return bidMasterDto;
	}

	public void setBidMasterDto(BidMasterDto bidMasterDto) {
		this.bidMasterDto = bidMasterDto;
	}

	public List<TbAcVendormaster> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<TbAcVendormaster> vendorList) {
		this.vendorList = vendorList;
	}

	@Override
	public boolean saveForm() {

		initiationDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		validateBean(initiationDto, TenderInitiationValidatior.class);
		if (hasValidationErrors()) {
			return false;
		}

		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setStatus(MainetConstants.FlagA);	
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)
				&& (MainetConstants.DEPT_SHORT_NAME.STORE).equalsIgnoreCase(departmentService.findDepartmentShortCodeByDeptId(initiationDto.getDeptId(), 
						initiationDto.getOrgId()))) {
			requestDTO.setDepartmentName(MainetConstants.DEPT_SHORT_NAME.STORE);
			this.setServiceMaster(servicesMstService.findShortCodeByOrgId(MainetConstants.ServiceShortCode.STORE_TND_SHORT_CODE,
					initiationDto.getOrgId()));			
		}
		else
			requestDTO.setDepartmentName(MainetConstants.WorksManagement.WORKS_MANAGEMENT);
		
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
		if (this.getServiceFlag() != null) {
			fileUpload.validateUpload(getBindingResult());
			List<DocumentDetailsVO> docs = getCheckList();
			if (!docs.isEmpty() && docs != null) {
				docs = fileUpload.prepareFileUpload(docs);
				requestDTO.setDocumentList(docs);
			}
		} else {
			setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		}
		if (mode.equals(MainetConstants.MODE_CREATE)) {
			initiationDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			initiationDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			if (this.getServiceFlag() != null && this.getServiceFlag().equals(MainetConstants.FlagA)) {
				initiationDto.setStatus(MainetConstants.FlagP);
				initiationDto.setServiceFlag(this.getServiceFlag());
				requestDTO.setServiceId(this.getServiceMaster().getSmServiceId());
				requestDTO.setDeptId(this.getServiceMaster().getTbDepartment().getDpDeptid());
			} else {
				initiationDto.setStatus(MainetConstants.Y_FLAG);
			}
			/*if(MainetConstants.DEPT_SHORT_NAME.STORE.equalsIgnoreCase(initiationDto.getDeptCode())) {
				initiationDto.setProjId(wmsProjectMasterRepository.findByProjCode("SD").getProjId());//TODO need to configure project for SD
			}*/
			TenderMasterDto tenderMasterDto = ApplicationContextProvider.getApplicationContext()
					.getBean(TenderInitiationService.class)
					.createTenderInitiation(initiationDto, getAttachments(), requestDTO);
			if (this.getServiceFlag() != null) {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("work.tender.approval.initiated") + " "
						+ ApplicationSession.getInstance().getMessage("works.tender.initiation.no") + " "
						+ tenderMasterDto.getInitiationNo());
			} else {
				setSuccessMessage(ApplicationSession.getInstance().getMessage("tender.init.success"));
			}
		} else {
			List<Long> removedWorkIds = getDeletedWorkIdAsList();
			initiationDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			initiationDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
			ApplicationContextProvider.getApplicationContext().getBean(TenderInitiationService.class)
					.updateTenderInitiation(initiationDto, getAttachments(), requestDTO, removedWorkIds, deleteFileId);
			setSuccessMessage(ApplicationSession.getInstance().getMessage("tender.update.sucess"));

		}
		return true;

	}

	// deleted documents asset id as list
	private List<Long> getDeletedWorkIdAsList() {
		List<Long> removeWorkIdList = null;
		String workIds = getRemoveWorkIdArray();
		if (workIds != null && !workIds.isEmpty()) {
			removeWorkIdList = new ArrayList<>();
			String workArray[] = workIds.split(MainetConstants.operator.COMMA);
			for (String wdid : workArray) {
				removeWorkIdList.add(Long.valueOf(wdid));
			}
		}
		return removeWorkIdList;
	}

	// validate tender update form details
	public boolean validateUpdateForm(TenderMasterDto tenderMasterDto) {
		boolean result = false;
		if (tenderMasterDto.getTenderNo() == null || tenderMasterDto.getTenderNo().isEmpty()) {
			addValidationError(ApplicationSession.getInstance().getMessage("tender.enter.tenderNo"));
		}
		if (tenderMasterDto.getTenderDate() == null) {
			addValidationError(ApplicationSession.getInstance().getMessage("tender.select.tenderdate"));
		}

		if (!hasValidationErrors()) {
			result = true;
		}
		return result;

	}

	public boolean validateInputs() {
		validateBean(initiationDto, TenderInitiationValidatior.class);
		if (hasValidationErrors()) {
			return false;
		}
		return true;
	}

	public TenderMasterDto getInitiationDto() {
		return initiationDto;
	}

	public void setInitiationDto(TenderMasterDto initiationDto) {
		this.initiationDto = initiationDto;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<WorkDefinitionDto> getWorkList() {
		return workList;
	}

	public void setWorkList(List<WorkDefinitionDto> workList) {
		this.workList = workList;
	}

	public List<TbDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<TbDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}

	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}

	public String getRemoveWorkIdArray() {
		return removeWorkIdArray;
	}

	public void setRemoveWorkIdArray(String removeWorkIdArray) {
		this.removeWorkIdArray = removeWorkIdArray;
	}

	public Long getDeleteFileId() {
		return deleteFileId;
	}

	public void setDeleteFileId(Long deleteFileId) {
		this.deleteFileId = deleteFileId;
	}

	public List<LookUp> getVenderCategoryList() {
		return venderCategoryList;
	}

	public void setVenderCategoryList(List<LookUp> venderCategoryList) {
		this.venderCategoryList = venderCategoryList;
	}

	public List<LookUp> getWorkDurationUnit() {
		return workDurationUnit;
	}

	public void setWorkDurationUnit(List<LookUp> workDurationUnit) {
		this.workDurationUnit = workDurationUnit;
	}

	public TenderWorkDto getLoaTenderDetails() {
		return loaTenderDetails;
	}

	public void setLoaTenderDetails(TenderWorkDto loaTenderDetails) {
		this.loaTenderDetails = loaTenderDetails;
	}

	public String getLoaMode() {
		return loaMode;
	}

	public void setLoaMode(String loaMode) {
		this.loaMode = loaMode;
	}

	public TenderWorkDto getPrintNoticeInvintingTender() {
		return printNoticeInvintingTender;
	}

	public void setPrintNoticeInvintingTender(TenderWorkDto printNoticeInvintingTender) {
		this.printNoticeInvintingTender = printNoticeInvintingTender;
	}

	public TenderWorkDto getPreQualDocument() {
		return preQualDocument;
	}

	public void setPreQualDocument(TenderWorkDto preQualDocument) {
		this.preQualDocument = preQualDocument;
	}

	public List<LookUp> getTenderTpyes() {
		return tenderTpyes;
	}

	public void setTenderTpyes(List<LookUp> tenderTpyes) {
		this.tenderTpyes = tenderTpyes;
	}

	public BigDecimal getAmountToCheckValidation() {
		return amountToCheckValidation;
	}

	public void setAmountToCheckValidation(BigDecimal amountToCheckValidation) {
		this.amountToCheckValidation = amountToCheckValidation;
	}

	public TenderWorkDto getTenderWorksForms() {
		return tenderWorksForms;
	}

	public void setTenderWorksForms(TenderWorkDto tenderWorksForms) {
		this.tenderWorksForms = tenderWorksForms;
	}

	public String getModeCpd() {
		return modeCpd;
	}

	public void setModeCpd(String modeCpd) {
		this.modeCpd = modeCpd;
	}

	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}

	public ServiceMaster getServiceMaster() {
		return serviceMaster;
	}

	public void setServiceMaster(ServiceMaster serviceMaster) {
		this.serviceMaster = serviceMaster;
	}

	public String getInitiationNo() {
		return initiationNo;
	}

	public void setInitiationNo(String initiationNo) {
		this.initiationNo = initiationNo;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public String getEnableSubmit() {
		return enableSubmit;
	}

	public void setEnableSubmit(String enableSubmit) {
		this.enableSubmit = enableSubmit;
	}

	public List<TbOrgDesignation> getDesignationList() {
		return designationList;
	}

	public void setDesignationList(List<TbOrgDesignation> designationList) {
		this.designationList = designationList;
	}

	public List<LookUp> getTenderPercentageAmount() {
		return tenderPercentageAmount;
	}

	public void setTenderPercentageAmount(List<LookUp> tenderPercentageAmount) {
		this.tenderPercentageAmount = tenderPercentageAmount;
	}

	public String getTenderUpdated() {
		return tenderUpdated;
	}

	public void setTenderUpdated(String tenderUpdated) {
		this.tenderUpdated = tenderUpdated;
	}

	public String getAwardMode() {
		return awardMode;
	}

	public void setAwardMode(String awardMode) {
		this.awardMode = awardMode;
	}

}
