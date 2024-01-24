package com.abm.mainet.additionalservices.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.service.NOCForBuildingPermissionService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class NOCForBuildingPermissionModel extends AbstractFormModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private NOCForBuildingPermissionService nocBuildPerService;
	
	 @Autowired
	    private IFileUploadService fileUpload;

	private List<DocumentDetailsVO> checkList = new ArrayList<>();

	private List<CFCAttachment> documentList = new ArrayList<>();

	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<AttachDocs> attachDocsList = new ArrayList<>();

	NOCForBuildingPermissionDTO nocBuildingPermissionDto = new NOCForBuildingPermissionDTO();

	private Map<Long, String> serviceList = null;

	List<TbServicesMst> serviceMstList = null;

	private List<TbDepartment> deptList = null;

	private String saveMode;
	
	private String gisValue;
	
    private String gISUri;

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

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	private TbCfcApplicationMstEntity cfcEntity = new TbCfcApplicationMstEntity();

	private Long applicationId;

	private String applicantName;

	public List<TbServicesMst> getServiceMstList() {
		return serviceMstList;
	}

	public void setServiceMstList(List<TbServicesMst> serviceMstList) {
		this.serviceMstList = serviceMstList;
	}

	public List<DocumentDetailsVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<DocumentDetailsVO> checkList) {
		this.checkList = checkList;
	}

	public List<CFCAttachment> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<CFCAttachment> documentList) {
		this.documentList = documentList;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public TbCfcApplicationMstEntity getCfcEntity() {
		return cfcEntity;
	}

	public void setCfcEntity(TbCfcApplicationMstEntity cfcEntity) {
		this.cfcEntity = cfcEntity;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public List<TbDepartment> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<TbDepartment> deptList) {
		this.deptList = deptList;
	}

	public Map<Long, String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(Map<Long, String> serviceList) {
		this.serviceList = serviceList;
	}

	public NOCForBuildingPermissionDTO getNocBuildingPermissionDto() {
		return nocBuildingPermissionDto;
	}

	public void setNocBuildingPermissionDto(NOCForBuildingPermissionDTO nocBuildingPermissionDto) {
		this.nocBuildingPermissionDto = nocBuildingPermissionDto;
	}

	
	
	
	public String getGisValue() {
		return gisValue;
	}

	public void setGisValue(String gisValue) {
		this.gisValue = gisValue;
	}

	public String getgISUri() {
		return gISUri;
	}

	public void setgISUri(String gISUri) {
		this.gISUri = gISUri;
	}

	@Override
	public boolean saveForm() {
        Employee employee = getUserSession().getEmployee();
		int langId = UserSession.getCurrent().getLanguageId();
		nocBuildingPermissionDto.setUpdatedBy(employee.getEmpId());
		nocBuildingPermissionDto.setUpdatedDate(new Date());
		nocBuildingPermissionDto.setLmoddate(new Date());
		nocBuildingPermissionDto.setLgIpMac(employee.getEmppiservername());
		nocBuildingPermissionDto.setLgIpMacUpd(employee.getEmppiservername());
		nocBuildingPermissionDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		nocBuildingPermissionDto.setLangId(langId);
		nocBuildingPermissionDto.setUserId(employee.getUserId());
		nocBuildingPermissionDto.setFinacialYear(Utility.getCurrentFinancialYear());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(NOCForBuildPermissionConstant.NBP,
				nocBuildingPermissionDto.getOrgId());
		nocBuildingPermissionDto.setSmServiceId(serviceMas.getSmServiceId());
		//ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).prepareFileUpload(checkList);
		//List<DocumentDetailsVO> docs =new ArrayList<>();
		List<DocumentDetailsVO> docs = this.getCheckList();
	        if (docs != null && !docs.isEmpty()) {
	            docs = fileUpload.prepareFileUpload(docs);
	        }else {
	        	setAttachments(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
						.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
	        	docs=getAttachments();
	        }
        nocBuildingPermissionDto.setDocList(docs);
		//File Upload  #40590				   		
        NOCForBuildingPermissionDTO dto=nocBuildPerService.saveData(nocBuildingPermissionDto);
        this.setNocBuildingPermissionDto(dto);
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
        	this.setSuccessMessage(getAppSession().getMessage("NOCBuildingPermission.succes.msg")
    				+ nocBuildingPermissionDto.getRefNo());
        }
        else {
        	this.setSuccessMessage(getAppSession().getMessage("NOCBuildingPermission.succes.msg")
    				+ nocBuildingPermissionDto.getApmApplicationId());
        }
		return true;
	}
}
