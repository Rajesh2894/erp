package com.abm.mainet.cfc.objection.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class ObjectionDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long objectionId;
	private String objectionNumber;
	private Date objectionDate;
	private Long objectionDeptId;
	private Long apmApplicationId;
	private String address;
	private String eMail;
	private String fName;
	private String lName;
	private String mName;
	private String mobileNo;
	private Long gender;
	private Long title;
	private Long uid;
	private String objectionReferenceNumber;
	private String objectionAddReferenceNumber;
	private String objectionDetails;
	private String objectionStatus;
	private Long serviceId;
	private Long createdBy;
	private Date createdDate;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Integer objectionSerialNumber;
	private Long codIdOperLevel1;
	private Long codIdOperLevel2;
	private Long codIdOperLevel3;
	private Long codIdOperLevel4;
	private Long codIdOperLevel5;
	private Long locId;
	private String deptCode;
	private Date lModDate;
	private Date updateDate;
	private Long billNo;
	private Date billDueDate;
	private String noticeNo;
	private String schedulingSelection;
	private String inspectionType;
	private String objectionIssuerName;
	private Long objectionOn;
	private Date hearingDate;
	private String name; // owner Name in case of Property
	private String nameOfInspector;
	private String deptName;
	private String serviceName;
	private String locName;
	private Long taskId;
	private String ipAddress;
	private String ename;
	private Long empType;
	private List<String> errorList;
	private Double charges;
	private String isFree;
	private boolean free;
	private Boolean isPaymentGenerated;
	private String paymentMode;
	private Long objTime;
	private Date applicationDate;
	private String validDate;
	private String flatNo;

	List<DocumentDetailsVO> docs = new ArrayList<>();
	private Map<Long, Double> chargesMap = new HashMap<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String inspectionStatus;

	private String applicantName;

	private String objectionReason;
	private String objectionAppealType;
	private String dispatchNo;
	private Date dispachDate;
	private Date deliveryDate;
	private String selectType;

	public Long getObjectionId() {
		return objectionId;
	}

	public void setObjectionId(Long objectionId) {
		this.objectionId = objectionId;
	}

	public String getObjectionStatus() {
		return objectionStatus;
	}

	public void setObjectionStatus(String objectionStatus) {
		this.objectionStatus = objectionStatus;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getObjectionDetails() {
		return objectionDetails;
	}

	public void setObjectionDetails(String objectionDetails) {
		this.objectionDetails = objectionDetails;
	}

	public Long getObjectionDeptId() {
		return objectionDeptId;
	}

	public void setObjectionDeptId(Long objectionDeptId) {
		this.objectionDeptId = objectionDeptId;
	}

	public Integer getObjectionSerialNumber() {
		return objectionSerialNumber;
	}

	public void setObjectionSerialNumber(Integer objectionSerialNumber) {
		this.objectionSerialNumber = objectionSerialNumber;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String getObjectionNumber() {
		return objectionNumber;
	}

	public void setObjectionNumber(String objectionNumber) {
		this.objectionNumber = objectionNumber;
	}

	public String getObjectionReferenceNumber() {
		return objectionReferenceNumber;
	}

	public void setObjectionReferenceNumber(String objectionReferenceNumber) {
		this.objectionReferenceNumber = objectionReferenceNumber;
	}

	public String getObjectionAddReferenceNumber() {
		return objectionAddReferenceNumber;
	}

	public void setObjectionAddReferenceNumber(String objectionAddReferenceNumber) {
		this.objectionAddReferenceNumber = objectionAddReferenceNumber;
	}

	public Date getObjectionDate() {
		return objectionDate;
	}

	public void setObjectionDate(Date objectionDate) {
		this.objectionDate = objectionDate;
	}

	public Long getCodIdOperLevel1() {
		return codIdOperLevel1;
	}

	public void setCodIdOperLevel1(Long codIdOperLevel1) {
		this.codIdOperLevel1 = codIdOperLevel1;
	}

	public Long getCodIdOperLevel2() {
		return codIdOperLevel2;
	}

	public void setCodIdOperLevel2(Long codIdOperLevel2) {
		this.codIdOperLevel2 = codIdOperLevel2;
	}

	public Long getCodIdOperLevel3() {
		return codIdOperLevel3;
	}

	public void setCodIdOperLevel3(Long codIdOperLevel3) {
		this.codIdOperLevel3 = codIdOperLevel3;
	}

	public Long getCodIdOperLevel4() {
		return codIdOperLevel4;
	}

	public void setCodIdOperLevel4(Long codIdOperLevel4) {
		this.codIdOperLevel4 = codIdOperLevel4;
	}

	public Long getCodIdOperLevel5() {
		return codIdOperLevel5;
	}

	public void setCodIdOperLevel5(Long codIdOperLevel5) {
		this.codIdOperLevel5 = codIdOperLevel5;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Date getlModDate() {
		return lModDate;
	}

	public void setlModDate(Date lModDate) {
		this.lModDate = lModDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(String noticeNo) {
		this.noticeNo = noticeNo;
	}

	public String getSchedulingSelection() {
		return schedulingSelection;
	}

	public void setSchedulingSelection(String schedulingSelection) {
		this.schedulingSelection = schedulingSelection;
	}

	public String getInspectionType() {
		return inspectionType;
	}

	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}

	public String getObjectionIssuerName() {
		return objectionIssuerName;
	}

	public void setObjectionIssuerName(String objectionIssuerName) {
		this.objectionIssuerName = objectionIssuerName;
	}

	public Long getObjectionOn() {
		return objectionOn;
	}

	public void setObjectionOn(Long objectionOn) {
		this.objectionOn = objectionOn;
	}

	public Date getBillDueDate() {
		return billDueDate;
	}

	public void setBillDueDate(Date billDueDate) {
		this.billDueDate = billDueDate;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<DocumentDetailsVO> getDocs() {
		return docs;
	}

	public void setDocs(List<DocumentDetailsVO> docs) {
		this.docs = docs;
	}

	public Date getHearingDate() {
		return hearingDate;
	}

	public void setHearingDate(Date hearingDate) {
		this.hearingDate = hearingDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameOfInspector() {
		return nameOfInspector;
	}

	public void setNameOfInspector(String nameOfInspector) {
		this.nameOfInspector = nameOfInspector;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getGender() {
		return gender;
	}

	public void setGender(Long gender) {
		this.gender = gender;
	}

	public Long getTitle() {
		return title;
	}

	public void setTitle(Long title) {
		this.title = title;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getBillNo() {
		return billNo;
	}

	public void setBillNo(Long billNo) {
		this.billNo = billNo;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Long getEmpType() {
		return empType;
	}

	public void setEmpType(Long empType) {
		this.empType = empType;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public Boolean getIsPaymentGenerated() {
		return isPaymentGenerated;
	}

	public void setIsPaymentGenerated(Boolean isPaymentGenerated) {
		this.isPaymentGenerated = isPaymentGenerated;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Long getObjTime() {
		return objTime;
	}

	public void setObjTime(Long objTime) {
		this.objTime = objTime;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getInspectionStatus() {
		return inspectionStatus;
	}

	public void setInspectionStatus(String inspectionStatus) {
		this.inspectionStatus = inspectionStatus;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getObjectionReason() {
		return objectionReason;
	}

	public void setObjectionReason(String objectionReason) {
		this.objectionReason = objectionReason;
	}

	public String getObjectionAppealType() {
		return objectionAppealType;
	}

	public void setObjectionAppealType(String objectionAppealType) {
		this.objectionAppealType = objectionAppealType;
	}

	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public Date getDispachDate() {
		return dispachDate;
	}

	public void setDispachDate(Date dispachDate) {
		this.dispachDate = dispachDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getSelectType() {
		return selectType;
	}

	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}

	
}
