package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String requestType;
    private String description;
    private Long applicationId;
    private String complaintId;
    private Long orgId;
    private Date createdDate = new Date();
    private Long createdBy;
    private Date modifiedDate;
    private Long modifiedBy;
    private Long departmentComplaint;
    private Long complaintType;
    private Long location;
    private String departmentComplaintDesc;
    private String departmentComplaintDescReg;
    private String complaintTypeDesc;
    private String complaintTypeDescReg;
    private String locationEngName;
    private String locationRegName;
    private String pincode;
    private String status;
    private String lastDecision;
    private Long district;
    private String landmark;
    private String latitude;
    private String longitude;
    private String referenceMode;
    private Long ward1;
    private Long ward2;
    private Long ward3;
    private Long ward4;
    private Long ward5;
    private String ward1Desc;
    private String ward2Desc;
    private String ward3Desc;
    private String ward4Desc;
    private String ward5Desc;
    private String applnType;
    private String referenceCategory;
    private Date referenceDate;
    private String residentId;
    private String prefixName;
    private String extReferNumber;
    private List<DocumentDetailsVO> attachments;
    private String comment;
    private Long level;
    private String apaMobNo;
    private Long workflowReqId;
    private String propFlatNo;
    private String errorMsg;
    private String externalWorkFlowFlag;
    
  //#135509,#142867
    private String ward1DescReg;
    private String ward2DescReg;
    private String ward3DescReg;
    private String ward4DescReg;
    private String ward5DescReg;
    
    
    private String referenceModeDescReg;
    private String referenceCategoryDescReg;
    
  //#161770
    private String complaintDeptCode;
    
    //#164437
    private boolean isFlatListEmpty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getDepartmentComplaint() {
        return departmentComplaint;
    }

    public void setDepartmentComplaint(Long departmentComplaint) {
        this.departmentComplaint = departmentComplaint;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Long complaintType) {
        this.complaintType = complaintType;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long location) {
        this.location = location;
    }

    public String getDepartmentComplaintDesc() {
        return departmentComplaintDesc;
    }

    public void setDepartmentComplaintDesc(String departmentComplaintDesc) {
        this.departmentComplaintDesc = departmentComplaintDesc;
    }

    public String getDepartmentComplaintDescReg() {
        return departmentComplaintDescReg;
    }

    public void setDepartmentComplaintDescReg(String departmentComplaintDescReg) {
        this.departmentComplaintDescReg = departmentComplaintDescReg;
    }

    public String getComplaintTypeDesc() {
        return complaintTypeDesc;
    }

    public void setComplaintTypeDesc(String complaintTypeDesc) {
        this.complaintTypeDesc = complaintTypeDesc;
    }

    public String getComplaintTypeDescReg() {
        return complaintTypeDescReg;
    }

    public void setComplaintTypeDescReg(String complaintTypeDescReg) {
        this.complaintTypeDescReg = complaintTypeDescReg;
    }

    public String getLocationEngName() {
        return locationEngName;
    }

    public void setLocationEngName(String locationEngName) {
        this.locationEngName = locationEngName;
    }

    public String getLocationRegName() {
        return locationRegName;
    }

    public void setLocationRegName(String locationRegName) {
        this.locationRegName = locationRegName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReferenceMode() {
        return referenceMode;
    }

    public void setReferenceMode(String referenceMode) {
        this.referenceMode = referenceMode;
    }

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
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

	public Long getWard4() {
		return ward4;
	}

	public void setWard4(Long ward4) {
		this.ward4 = ward4;
	}

	public Long getWard5() {
		return ward5;
	}

	public void setWard5(Long ward5) {
		this.ward5 = ward5;
	}

	public String getApplnType() {
		return applnType;
	}

	public void setApplnType(String applnType) {
		this.applnType = applnType;
	}

	public String getWard1Desc() {
		return ward1Desc;
	}

	public void setWard1Desc(String ward1Desc) {
		this.ward1Desc = ward1Desc;
	}

	public String getWard2Desc() {
		return ward2Desc;
	}

	public void setWard2Desc(String ward2Desc) {
		this.ward2Desc = ward2Desc;
	}

	public String getWard3Desc() {
		return ward3Desc;
	}

	public void setWard3Desc(String ward3Desc) {
		this.ward3Desc = ward3Desc;
	}

	public String getWard4Desc() {
		return ward4Desc;
	}

	public void setWard4Desc(String ward4Desc) {
		this.ward4Desc = ward4Desc;
	}

	public String getWard5Desc() {
		return ward5Desc;
	}

	public void setWard5Desc(String ward5Desc) {
		this.ward5Desc = ward5Desc;
	}

	public String getReferenceCategory() {
		return referenceCategory;
	}

	public void setReferenceCategory(String referenceCategory) {
		this.referenceCategory = referenceCategory;
	}

	public Date getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(Date referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getResidentId() {
		return residentId;
	}

	public void setResidentId(String residentId) {
		this.residentId = residentId;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	public String getExtReferNumber() {
		return extReferNumber;
	}

	public void setExtReferNumber(String extReferNumber) {
		this.extReferNumber = extReferNumber;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getApaMobNo() {
		return apaMobNo;
	}

	public void setApaMobNo(String apaMobNo) {
		this.apaMobNo = apaMobNo;
	}

	public Long getWorkflowReqId() {
		return workflowReqId;
	}

	public void setWorkflowReqId(Long workflowReqId) {
		this.workflowReqId = workflowReqId;
	}

	public String getPropFlatNo() {
		return propFlatNo;
	}

	public void setPropFlatNo(String propFlatNo) {
		this.propFlatNo = propFlatNo;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getExternalWorkFlowFlag() {
		return externalWorkFlowFlag;
	}

	public void setExternalWorkFlowFlag(String externalWorkFlowFlag) {
		this.externalWorkFlowFlag = externalWorkFlowFlag;
	}

	public String getWard1DescReg() {
		return ward1DescReg;
	}

	public void setWard1DescReg(String ward1DescReg) {
		this.ward1DescReg = ward1DescReg;
	}

	public String getWard2DescReg() {
		return ward2DescReg;
	}

	public void setWard2DescReg(String ward2DescReg) {
		this.ward2DescReg = ward2DescReg;
	}

	public String getWard3DescReg() {
		return ward3DescReg;
	}

	public void setWard3DescReg(String ward3DescReg) {
		this.ward3DescReg = ward3DescReg;
	}

	public String getWard4DescReg() {
		return ward4DescReg;
	}

	public void setWard4DescReg(String ward4DescReg) {
		this.ward4DescReg = ward4DescReg;
	}

	public String getWard5DescReg() {
		return ward5DescReg;
	}

	public void setWard5DescReg(String ward5DescReg) {
		this.ward5DescReg = ward5DescReg;
	}

	public String getReferenceModeDescReg() {
		return referenceModeDescReg;
	}

	public void setReferenceModeDescReg(String referenceModeDescReg) {
		this.referenceModeDescReg = referenceModeDescReg;
	}

	public String getReferenceCategoryDescReg() {
		return referenceCategoryDescReg;
	}

	public void setReferenceCategoryDescReg(String referenceCategoryDescReg) {
		this.referenceCategoryDescReg = referenceCategoryDescReg;
	}

	public String getComplaintDeptCode() {
		return complaintDeptCode;
	}

	public void setComplaintDeptCode(String complaintDeptCode) {
		this.complaintDeptCode = complaintDeptCode;
	}

	public boolean isFlatListEmpty() {
		return isFlatListEmpty;
	}

	public void setFlatListEmpty(boolean isFlatListEmpty) {
		this.isFlatListEmpty = isFlatListEmpty;
	}
	
}
