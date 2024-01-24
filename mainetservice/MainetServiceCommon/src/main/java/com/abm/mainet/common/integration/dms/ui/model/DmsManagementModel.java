package com.abm.mainet.common.integration.dms.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.DocManagementEntity;
import com.abm.mainet.common.integration.dms.dto.DmsManagementDetDto;
import com.abm.mainet.common.integration.dms.dto.DmsManagementDto;
import com.abm.mainet.common.integration.dms.service.IDmsManagementService;
import com.abm.mainet.common.integration.dms.ui.validator.DmsManagementValidator;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DmsManagementModel extends AbstractEntryFormModel<DocManagementEntity> {
	private static final Logger LOGGER = Logger.getLogger(DmsManagementModel.class);
	private static final long serialVersionUID = 1L;
	private List<LookUp> departmentList = new ArrayList<>();
	private List<LookUp> metadataList = new ArrayList<>();
	private List<LookUp> lookUpList = new ArrayList<>();
	private Long deptId;
	private List<String> attachNameList = new ArrayList<>(0);
	private DocManagementEntity entity = new DocManagementEntity();
	List<EmployeeBean> employeeList = new ArrayList<>();
	Map<Long, String> roleList = new HashMap<Long, String>();
	List<TbDepartment> assignedDeptList = new ArrayList<>();
	private String kms;
	private String employeeIds;
	private String roleIds;
	private String assignDeptIds;
	private String knowledgeSharing;
	private Long ward1;
	private Long ward2;
	private Long ward3;
	private String docRefNo;
	private String deptCode;
	private String seqNo;
	private Long dmsId;
	private String docAction;
	private Long noOfDays;
	private Long docRtrvlDays;
	private Date docActionDate;
	private String docActType;

	@Autowired
	private IDmsManagementService dmsService;

	public boolean saveForm() {
		Organisation org=UserSession.getCurrent().getOrganisation();
		boolean status = false;
		
		validateBean(this, DmsManagementValidator.class);

		if (hasValidationErrors()) {
			return status;
		}
					
		DmsManagementDto dmsDocsDto = new DmsManagementDto();
		List<DmsManagementDetDto> dmsDocsMetadataDetList = new ArrayList<>();
		dmsDocsDto.setDeptId(String.valueOf(getDeptId()));
		dmsDocsDto.setOrgId(UserSession.getCurrent().getOrganisation());
		dmsDocsDto.setIsActive(MainetConstants.FlagA);
		dmsDocsDto.setDmsId(getDmsId());
		dmsDocsDto.setStatus(MainetConstants.AssetManagement.STATUS.SUBMITTED);

		getLookUpList().forEach(data -> {
			if(StringUtils.isNotBlank(data.getOtherField())) {
				DmsManagementDetDto dmsDto = new DmsManagementDetDto();
			dmsDto.setMtKey(String.valueOf(data.getLookUpId()));
			dmsDto.setMtVal(data.getOtherField());	
			dmsDto.setOrgId(UserSession.getCurrent().getOrganisation());
			dmsDto.setUserId(UserSession.getCurrent().getEmployee());
			dmsDto.setLmodDate(new Date());
			dmsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
			dmsDocsMetadataDetList.add(dmsDto);
			}
		});
				
		dmsDocsDto.setZone(getWard1());
		dmsDocsDto.setWard(getWard2());
		dmsDocsDto.setMohalla(getWard3());
		dmsDocsDto.setDocRefNo(getDocRefNo());
		dmsDocsDto.setDmsDocsMetadataDetList(dmsDocsMetadataDetList);
		dmsDocsDto.setStorageType(MainetConstants.Dms.DMS);
		if(StringUtils.isNotBlank(docAction) && docAction.equals("D")) {
			dmsDocsDto.setDelDays(noOfDays);
			dmsDocsDto.setDelDocActionDate(docActionDate);
		}else if(docAction.equals("R")){
			dmsDocsDto.setRetDays(noOfDays);
			dmsDocsDto.setRetDocActionDate(docActionDate);
		}
		
		dmsDocsDto.setDocRtrvlDays(docRtrvlDays);
		dmsDocsDto.setUserId(UserSession.getCurrent().getEmployee());
		dmsDocsDto.setLmodDate(new Date());
		dmsDocsDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());

		dmsService.saveDms(dmsDocsDto);
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("dms.saveRecordSuccess"));
		status = true;
		return status;
	}


	public List<LookUp> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<LookUp> departmentList) {
		this.departmentList = departmentList;
	}

	public List<LookUp> getMetadataList() {
		return metadataList;
	}

	public void setMetadataList(List<LookUp> metadataList) {
		this.metadataList = metadataList;
	}

	public List<LookUp> getLookUpList() {
		return lookUpList;
	}

	public void setLookUpList(List<LookUp> lookUpList) {
		this.lookUpList = lookUpList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public List<String> getAttachNameList() {
		return attachNameList;
	}

	public void setAttachNameList(List<String> attachNameList) {
		this.attachNameList = attachNameList;
	}

	public DocManagementEntity getEntity() {
		return entity;
	}

	public void setEntity(DocManagementEntity entity) {
		this.entity = entity;
	}

	public String getKnowledgeSharing() {
		return knowledgeSharing;
	}

	public void setKnowledgeSharing(String knowledgeSharing) {
		this.knowledgeSharing = knowledgeSharing;
	}

	public List<EmployeeBean> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<EmployeeBean> employeeList) {
		this.employeeList = employeeList;
	}

	public Map<Long, String> getRoleList() {
		return roleList;
	}

	public void setRoleList(Map<Long, String> roleList) {
		this.roleList = roleList;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String employeeIds) {
		this.employeeIds = employeeIds;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public Long getWard1() {
		return ward1;
	}

	public void setWard1(Long ward1) {
		this.ward1 = ward1;
	}

	public Long getWard2() {
		return ward2;
	}

	public void setWard2(Long ward2) {
		this.ward2 = ward2;
	}

	public Long getWard3() {
		return ward3;
	}

	public void setWard3(Long ward3) {
		this.ward3 = ward3;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<TbDepartment> getAssignedDeptList() {
		return assignedDeptList;
	}

	public void setAssignedDeptList(List<TbDepartment> assignedDeptList) {
		this.assignedDeptList = assignedDeptList;
	}

	public String getAssignDeptIds() {
		return assignDeptIds;
	}

	public void setAssignDeptIds(String assignDeptIds) {
		this.assignDeptIds = assignDeptIds;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public Long getDmsId() {
		return dmsId;
	}

	public void setDmsId(Long dmsId) {
		this.dmsId = dmsId;
	}

	public String getDocAction() {
		return docAction;
	}

	public void setDocAction(String docAction) {
		this.docAction = docAction;
	}

	public Long getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Long noOfDays) {
		this.noOfDays = noOfDays;
	}


	public Long getDocRtrvlDays() {
		return docRtrvlDays;
	}


	public void setDocRtrvlDays(Long docRtrvlDays) {
		this.docRtrvlDays = docRtrvlDays;
	}


	public Date getDocActionDate() {
		return docActionDate;
	}


	public void setDocActionDate(Date docActionDate) {
		this.docActionDate = docActionDate;
	}


	public String getDocActType() {
		return docActType;
	}


	public void setDocActType(String docActType) {
		this.docActType = docActType;
	}

}
