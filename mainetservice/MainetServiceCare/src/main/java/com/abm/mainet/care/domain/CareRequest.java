package com.abm.mainet.care.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_CARE_REQUEST")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CARE_REQ_ID")
	private Long id;

	@Column(name = "REQUEST_TYPE")
	private String requestType;

	@Column(name = "COMPLAINT_DESC")
	private String description;

	@Column(name = "APM_APPLICATION_ID")
	private Long applicationId;

	@Column(name = "COMPLAINT_NO")
	private String complaintId;

	@Column(name = "EXT_REFERENCE_NO")
	private String extReferNumber;
	
	@Column(name = "RESIDENT_ID")
        private String residentId;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "REFERENCE_MODE")
	private String referenceMode;

	@Column(name = "REFERENCE_CATEGORY")
	private String referenceCategory;

	@Column(name = "REFERENCE_DATE")
	private Date referenceDate;

	@Column(name = "DATE_OF_REQUEST")
	private Date dateOfRequest;

	@Column(name = "LAST_DATE_OF_ACTION")
	private Date lastDateOfAction;

	@Column(name = "CREATED_DATE")
	private Date createdDate = new Date();

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "UPDATED_DATE")
	private Date modifiedDate;

	@Column(name = "UPDATED_BY")
	private Long modifiedBy;

	@Column(name = "LANDMARK")
	private String landmark;

	@Column(name = "LATITUDE")
	private String latitude;

	@Column(name = "LONGITUDE")
	private String longitude;

	@Column(name = "CARE_WARD_NO")
	private Long ward1;

	@Column(name = "CARE_WARD_NO1")
	private Long ward2;

	@Column(name = "CARE_WARD_NO2")
	private Long ward3;

	@Column(name = "CARE_WARD_NO3")
	private Long ward4;

	@Column(name = "CARE_WARD_NO4")
	private Long ward5;

	@Column(name = "Care_app_type")
	private String applnType;

	@Column(name = "DEPT_COMP_ID")
	private Long departmentComplaint;

	@Column(name = "COMP_SUBTYPE_ID")
	private Long complaintType;

	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;
	
	@Column(name = "DISTRICT")
	private Long district;
	
	@ManyToOne
	@JoinColumn(name = "LOC_ID", referencedColumnName = "LOC_ID")
	private LocationMasEntity location;

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

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	public String getExtReferNumber() {
		return extReferNumber;
	}

	public void setExtReferNumber(String extReferNumber) {
		this.extReferNumber = extReferNumber;
	}
	
	

	public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getReferenceMode() {
		return referenceMode;
	}

	public void setReferenceMode(String referenceMode) {
		this.referenceMode = referenceMode;
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

	public Date getDateOfRequest() {
		return dateOfRequest;
	}

	public void setDateOfRequest(Date dateOfRequest) {
		this.dateOfRequest = dateOfRequest;
	}

	public Date getLastDateOfAction() {
		return lastDateOfAction;
	}

	public void setLastDateOfAction(Date lastDateOfAction) {
		this.lastDateOfAction = lastDateOfAction;
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

	public LocationMasEntity getLocation() {
		return location;
	}

	public void setLocation(LocationMasEntity location) {
		this.location = location;
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

	public String[] getPkValues() {
		return new String[] { "COM", "TB_CARE_REQUEST", "CARE_REQ_ID" };
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

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}
}
