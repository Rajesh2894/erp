/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Data Transfer Object file for RTI Application.
 * @extends : RequestDTO.java
 */
package com.abm.mainet.rti.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class RtiApplicationFormDetailsReqDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = 4587771458335377655L;

	private int rtiId;
	private String rtiNo;
	private Long apmApplicationId;
	private Date apmApplicationDate;
	private Long smServiceId;
	private int applReferenceMode;
	private int rtiLocationId;
	private String rtiSubject;
	private String rtiDetails;
	private String loiApplicable;
	private String reasonForLoiNa;
	private int finalDispatchMode;
	private Date dispatchDate;
	private int deliveryReferenceNumber;
	private String stampNo;
	private Double stampAmt;
	private String stampDocPath;
	private String rtiAction;
	private int reasonId;
	private String rtiRemarks;
	private int partialInfoFlag;
	private int inwardType;
	private String inwReferenceNo;
	private Date inwReferenceDate;
	private String inwAuthorityDept;
	private String inwAuthorityName;
	private String inwAuthorityAddress;
	private String othForwardPioAddress;
	private String othForwardPioName;
	private String othForwardDeptName;
	private String othForwardPioEmail;
	private Integer othForwardPioModNo;
	private Date lModDate;
	private String lgIpMac;
	private Long updatedBy;
	private Date updateDate;
	private int lgIpMacUpd;
	private int isDeleted;
	private String isCorrespondingAddress;
	private String applicant;
	private String title;
	private Long remarksSerialNo;
	private String otherRemarks;
	private String gen;
	private String locationName;
	private String departmentName;
	private String inwardTypeName;
	private int rtiDeptId;
	private String referenceDate;
	private Long taskId;
	private String objType;
	private int objNo;
	private int srNo;
	private int objAppealno;
	private Date fromDate;
	private Date toDate;
	private String amountToPay;
	private String paymentMode;
	private Boolean isDocumentUpload;
	private String address;
	private boolean checkListFlag;
	private boolean paymentFlag;
	private String dateDesc;
	private String applicantName;
	private String status;
	private Long empId;
	private String empname;
	private String isFree;
	private Double charges = 0.0d;
	private String rtiBplFlag;
	private Long orgId;
	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();
	private Map<Long, Double> chargesMap = new HashMap<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private List<DocumentDetailsVO> fetchDocs = new ArrayList<>();
	private List<String> rtiRemarkList = new ArrayList<>();
	private String appealType;
	private String DispatchName;
	private String DispatchMobile;
	private String DispatchDocketNo;
	private Date rtiDeciRecDate;
	private String rtiDeciDet;
	private String rtiPioAction;
	private Date rtiPioActionDate;
	private Date rtiDeptidFdate;
	private String rtiDeciRecDateDesc;
	private String time;
	private String editAmount;
	private Long corrAddrsPincodeNo;
	private String corrAddrsAreaName;
	private String bplcode;
	private String dispatchNo;
	private Date dispatchDt;
	private List<Long> deptIdList;
	private List<Long> empList;
	private Long empSerNo;
	private String rtiDesc;
	private String locRegName;
	private String reportName;
	private Long uid;
	private String apmApplicationDateDesc;
	private boolean isWorkflowExist;
	private Long district;
	private Long rtiRelatedDeptId;
	private String relatedDeptName;
	private Long frdOrgId;
	private Long frdDeptId;
	private Long fdlDeptId;
	private Long trdWard1;
	private Long trdWard2;
	private Long trdWard3;
	private Long trdWard4;
	private Long trdWard5;
	private List<DocumentDetailsVO> stampDoc = new ArrayList<DocumentDetailsVO>();
	// added regarding US#111612
	private List<DocumentDetailsVO> postalDoc = new ArrayList<DocumentDetailsVO>();
	private List<DocumentDetailsVO> chlNonJudDoc = new ArrayList<DocumentDetailsVO>();
	private String postalCardNo;
	private Double postalAmt;
	// D#127764
	private String postalCardAmt;
	private List<String> remList;
	private String empRmk;
	private String rtiSecndAplStatus;
	private Long sliDays;
	private List<Long> sliDaysList;
	private String nonJudclNo;
	private String challanNo;
	private Date nonJudclDate;
	private Date challanDate;

	public Long getFrdOrgId() {
		return frdOrgId;
	}

	public void setFrdOrgId(Long frdOrgId) {
		this.frdOrgId = frdOrgId;
	}

	public String getRtiDesc() {
		return rtiDesc;
	}

	public void setRtiDesc(String rtiDesc) {
		this.rtiDesc = rtiDesc;
	}

	public Long getEmpSerNo() {
		return empSerNo;
	}

	public void setEmpSerNo(Long empSerNo) {
		this.empSerNo = empSerNo;
	}

	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	public List<Long> getEmpList() {
		return empList;
	}

	public void setEmpList(List<Long> empList) {
		this.empList = empList;
	}

	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getRtiSubject() {
		return rtiSubject;
	}

	public void setRtiSubject(String rtiSubject) {
		this.rtiSubject = rtiSubject;
	}

	public String getRtiRemarks() {
		return rtiRemarks;
	}

	public void setRtiRemarks(String rtiRemarks) {
		this.rtiRemarks = rtiRemarks;
	}

	public String getOthForwardPioAddress() {
		return othForwardPioAddress;
	}

	public void setOthForwardPioAddress(String othForwardPioAddress) {
		this.othForwardPioAddress = othForwardPioAddress;
	}

	public String getOthForwardPioName() {
		return othForwardPioName;
	}

	public void setOthForwardPioName(String othForwardPioName) {
		this.othForwardPioName = othForwardPioName;
	}

	public String getOthForwardDeptName() {
		return othForwardDeptName;
	}

	public void setOthForwardDeptName(String othForwardDeptName) {
		this.othForwardDeptName = othForwardDeptName;
	}

	public String getOthForwardPioEmail() {
		return othForwardPioEmail;
	}

	public void setOthForwardPioEmail(String othForwardPioEmail) {
		this.othForwardPioEmail = othForwardPioEmail;
	}

	public Integer getOthForwardPioModNo() {
		return othForwardPioModNo;
	}

	public void setOthForwardPioModNo(Integer othForwardPioModNo) {
		this.othForwardPioModNo = othForwardPioModNo;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getRtiId() {
		return rtiId;
	}

	public void setRtiId(int rtiId) {
		this.rtiId = rtiId;
	}

	public String getRtiNo() {
		return rtiNo;
	}

	public void setRtiNo(String rtiNo) {
		this.rtiNo = rtiNo;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Date getApmApplicationDate() {
		return apmApplicationDate;
	}

	public void setApmApplicationDate(Date apmApplicationDate) {
		this.apmApplicationDate = apmApplicationDate;
	}

	public int getApplReferenceMode() {
		return applReferenceMode;
	}

	public void setApplReferenceMode(int applReferenceMode) {
		this.applReferenceMode = applReferenceMode;
	}

	public int getRtiLocationId() {
		return rtiLocationId;
	}

	public void setRtiLocationId(int rtiLocationId) {
		this.rtiLocationId = rtiLocationId;
	}

	public String getSubject() {
		return rtiSubject;
	}

	public void setSubject(String subject) {
		this.rtiSubject = subject;
	}

	public String getRtiDetails() {
		return rtiDetails;
	}

	public void setRtiDetails(String rtiDetails) {
		this.rtiDetails = rtiDetails;
	}

	public String getLoiApplicable() {
		return loiApplicable;
	}

	public void setLoiApplicable(String loiApplicable) {
		this.loiApplicable = loiApplicable;
	}

	public String getReasonForLoiNa() {
		return reasonForLoiNa;
	}

	public void setReasonForLoiNa(String reasonForLoiNa) {
		this.reasonForLoiNa = reasonForLoiNa;
	}

	public int getFinalDispatchMode() {
		return finalDispatchMode;
	}

	public void setFinalDispatchMode(int finalDispatchMode) {
		this.finalDispatchMode = finalDispatchMode;
	}

	public Date getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(Date dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public String getStampNo() {
		return stampNo;
	}

	public void setStampNo(String stampNo) {
		this.stampNo = stampNo;
	}

	public String getStampDocPath() {
		return stampDocPath;
	}

	public void setStampDocPath(String stampDocPath) {
		this.stampDocPath = stampDocPath;
	}

	public String getRtiAction() {
		return rtiAction;
	}

	public void setRtiAction(String rtiAction) {
		this.rtiAction = rtiAction;
	}

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getAcceptRemarks() {
		return rtiRemarks;
	}

	public void setAcceptRemarks(String acceptRemarks) {
		this.rtiRemarks = acceptRemarks;
	}

	public int getInwardType() {
		return inwardType;
	}

	public void setInwardType(int inwardType) {
		this.inwardType = inwardType;
	}

	public String getInwReferenceNo() {
		return inwReferenceNo;
	}

	public void setInwReferenceNo(String inwReferenceNo) {
		this.inwReferenceNo = inwReferenceNo;
	}

	public Date getInwReferenceDate() {
		return inwReferenceDate;
	}

	public void setInwReferenceDate(Date inwReferenceDate) {
		this.inwReferenceDate = inwReferenceDate;
	}

	public String getInwAuthorityDept() {
		return inwAuthorityDept;
	}

	public void setInwAuthorityDept(String inwAuthorityDept) {
		this.inwAuthorityDept = inwAuthorityDept;
	}

	public String getInwAuthorityName() {
		return inwAuthorityName;
	}

	public void setInwAuthorityName(String inwAuthorityName) {
		this.inwAuthorityName = inwAuthorityName;
	}

	public String getInwAuthorityAddress() {
		return inwAuthorityAddress;
	}

	public void setInwAuthorityAddress(String inwAuthorityAddress) {
		this.inwAuthorityAddress = inwAuthorityAddress;
	}

	public Date getlModDate() {
		return lModDate;
	}

	public void setlModDate(Date lModDate) {
		this.lModDate = lModDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(int lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}

	public void setApplicantDTO(ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
	}

	public String getIsCorrespondingAddress() {
		return isCorrespondingAddress;
	}

	public void setIsCorrespondingAddress(String isCorrespondingAddress) {
		this.isCorrespondingAddress = isCorrespondingAddress;
	}

	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getRemarksSerialNo() {
		return remarksSerialNo;
	}

	public void setRemarksSerialNo(Long remarksSerialNo) {
		this.remarksSerialNo = remarksSerialNo;
	}

	public String getOtherRemarks() {
		return otherRemarks;
	}

	public void setOtherRemarks(String otherRemarks) {
		this.otherRemarks = otherRemarks;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGen() {
		return gen;
	}

	public void setGen(String gen) {
		this.gen = gen;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getInwardTypeName() {
		return inwardTypeName;
	}

	public void setInwardTypeName(String inwardTypeName) {
		this.inwardTypeName = inwardTypeName;
	}

	public int getRtiDeptId() {
		return rtiDeptId;
	}

	public void setRtiDeptId(int rtiDeptId) {
		this.rtiDeptId = rtiDeptId;
	}

	public String getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

	public int getPartialInfoFlag() {
		return partialInfoFlag;
	}

	public void setPartialInfoFlag(int partialInfoFlag) {
		this.partialInfoFlag = partialInfoFlag;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public int getObjNo() {
		return objNo;
	}

	public void setObjNo(int objNo) {
		this.objNo = objNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public int getObjAppealno() {
		return objAppealno;
	}

	public void setObjAppealno(int objAppealno) {
		this.objAppealno = objAppealno;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getAmountToPay() {
		return amountToPay;
	}

	public void setAmountToPay(String amountToPay) {
		this.amountToPay = amountToPay;
	}

	public Boolean getIsDocumentUpload() {
		return isDocumentUpload;
	}

	public void setIsDocumentUpload(Boolean isDocumentUpload) {
		this.isDocumentUpload = isDocumentUpload;
	}

	public int getDeliveryReferenceNumber() {
		return deliveryReferenceNumber;
	}

	public void setDeliveryReferenceNumber(int deliveryReferenceNumber) {
		this.deliveryReferenceNumber = deliveryReferenceNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isCheckListFlag() {
		return checkListFlag;
	}

	public void setCheckListFlag(boolean checkListFlag) {
		this.checkListFlag = checkListFlag;
	}

	public boolean isPaymentFlag() {
		return paymentFlag;
	}

	public void setPaymentFlag(boolean paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public String getDateDesc() {
		return dateDesc;
	}

	public void setDateDesc(String dateDesc) {
		this.dateDesc = dateDesc;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public Double getCharges() {
		return charges;
	}

	public void setCharges(Double charges) {
		this.charges = charges;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public String getRtiBplFlag() {
		return rtiBplFlag;
	}

	public void setRtiBplFlag(String rtiBplFlag) {
		this.rtiBplFlag = rtiBplFlag;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public List<DocumentDetailsVO> getFetchDocs() {
		return fetchDocs;
	}

	public void setFetchDocs(List<DocumentDetailsVO> fetchDocs) {
		this.fetchDocs = fetchDocs;
	}

	public List<String> getRtiRemarkList() {
		return rtiRemarkList;
	}

	public void setRtiRemarkList(List<String> rtiRemarkList) {
		this.rtiRemarkList = rtiRemarkList;
	}

	public String getAppealType() {
		return appealType;
	}

	public void setAppealType(String appealType) {
		this.appealType = appealType;
	}

	public String getDispatchName() {
		return DispatchName;
	}

	public void setDispatchName(String dispatchName) {
		DispatchName = dispatchName;
	}

	public String getDispatchMobile() {
		return DispatchMobile;
	}

	public void setDispatchMobile(String dispatchMobile) {
		DispatchMobile = dispatchMobile;
	}

	public String getDispatchDocketNo() {
		return DispatchDocketNo;
	}

	public void setDispatchDocketNo(String dispatchDocketNo) {
		DispatchDocketNo = dispatchDocketNo;
	}

	public Date getRtiDeciRecDate() {
		return rtiDeciRecDate;
	}

	public String getRtiDeciDet() {
		return rtiDeciDet;
	}

	public String getRtiPioAction() {
		return rtiPioAction;
	}

	public Date getRtiPioActionDate() {
		return rtiPioActionDate;
	}

	public Date getRtiDeptidFdate() {
		return rtiDeptidFdate;
	}

	public void setRtiDeciRecDate(Date rtiDeciRecDate) {
		this.rtiDeciRecDate = rtiDeciRecDate;
	}

	public void setRtiDeciDet(String rtiDeciDet) {
		this.rtiDeciDet = rtiDeciDet;
	}

	public void setRtiPioAction(String rtiPioAction) {
		this.rtiPioAction = rtiPioAction;
	}

	public void setRtiPioActionDate(Date rtiPioActionDate) {
		this.rtiPioActionDate = rtiPioActionDate;
	}

	public void setRtiDeptidFdate(Date rtiDeptidFdate) {
		this.rtiDeptidFdate = rtiDeptidFdate;
	}

	public String getRtiDeciRecDateDesc() {
		return rtiDeciRecDateDesc;
	}

	public void setRtiDeciRecDateDesc(String rtiDeciRecDateDesc) {
		this.rtiDeciRecDateDesc = rtiDeciRecDateDesc;
	}

	public Double getStampAmt() {
		return stampAmt;
	}

	public void setStampAmt(Double stampAmt) {
		this.stampAmt = stampAmt;
	}

	public String getBplcode() {
		return bplcode;
	}

	public void setBplcode(String bplcode) {
		this.bplcode = bplcode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getEditAmount() {
		return editAmount;
	}

	public void setEditAmount(String editAmount) {
		this.editAmount = editAmount;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Long getCorrAddrsPincodeNo() {
		return corrAddrsPincodeNo;
	}

	public void setCorrAddrsPincodeNo(Long corrAddrsPincodeNo) {
		this.corrAddrsPincodeNo = corrAddrsPincodeNo;
	}

	public String getCorrAddrsAreaName() {
		return corrAddrsAreaName;
	}

	public void setCorrAddrsAreaName(String corrAddrsAreaName) {
		this.corrAddrsAreaName = corrAddrsAreaName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getApmApplicationDateDesc() {
		return apmApplicationDateDesc;
	}

	public void setApmApplicationDateDesc(String apmApplicationDateDesc) {
		this.apmApplicationDateDesc = apmApplicationDateDesc;
	}

	public String getDispatchNo() {
		return dispatchNo;
	}

	public void setDispatchNo(String dispatchNo) {
		this.dispatchNo = dispatchNo;
	}

	public Date getDispatchDt() {
		return dispatchDt;
	}

	public void setDispatchDt(Date dispatchDt) {
		this.dispatchDt = dispatchDt;
	}

	public String getLocRegName() {
		return locRegName;
	}

	public void setLocRegName(String locRegName) {
		this.locRegName = locRegName;
	}

	public boolean isWorkflowExist() {
		return isWorkflowExist;
	}

	public void setWorkflowExist(boolean isWorkflowExist) {
		this.isWorkflowExist = isWorkflowExist;
	}

	public Long getDistrict() {
		return district;
	}

	public void setDistrict(Long district) {
		this.district = district;
	}

	public Long getRtiRelatedDeptId() {
		return rtiRelatedDeptId;
	}

	public void setRtiRelatedDeptId(Long rtiRelatedDeptId) {
		this.rtiRelatedDeptId = rtiRelatedDeptId;
	}

	public String getRelatedDeptName() {
		return relatedDeptName;
	}

	public void setRelatedDeptName(String relatedDeptName) {
		this.relatedDeptName = relatedDeptName;
	}

	public Long getFrdDeptId() {
		return frdDeptId;
	}

	public void setFrdDeptId(Long frdDeptId) {
		this.frdDeptId = frdDeptId;
	}

	public Long getFdlDeptId() {
		return fdlDeptId;
	}

	public void setFdlDeptId(Long fdlDeptId) {
		this.fdlDeptId = fdlDeptId;
	}

	public Long getTrdWard1() {
		return trdWard1;
	}

	public void setTrdWard1(Long trdWard1) {
		this.trdWard1 = trdWard1;
	}

	public Long getTrdWard2() {
		return trdWard2;
	}

	public void setTrdWard2(Long trdWard2) {
		this.trdWard2 = trdWard2;
	}

	public Long getTrdWard3() {
		return trdWard3;
	}

	public void setTrdWard3(Long trdWard3) {
		this.trdWard3 = trdWard3;
	}

	public Long getTrdWard4() {
		return trdWard4;
	}

	public void setTrdWard4(Long trdWard4) {
		this.trdWard4 = trdWard4;
	}

	public Long getTrdWard5() {
		return trdWard5;
	}

	public void setTrdWard5(Long trdWard5) {
		this.trdWard5 = trdWard5;
	}

	public List<DocumentDetailsVO> getStampDoc() {
		return stampDoc;
	}

	public void setStampDoc(List<DocumentDetailsVO> stampDoc) {
		this.stampDoc = stampDoc;
	}

	public List<DocumentDetailsVO> getPostalDoc() {
		return postalDoc;
	}

	public void setPostalDoc(List<DocumentDetailsVO> postalDoc) {
		this.postalDoc = postalDoc;
	}

	public String getPostalCardNo() {
		return postalCardNo;
	}

	public void setPostalCardNo(String postalCardNo) {
		this.postalCardNo = postalCardNo;
	}

	public Double getPostalAmt() {
		return postalAmt;
	}

	public void setPostalAmt(Double postalAmt) {
		this.postalAmt = postalAmt;
	}

	public String getPostalCardAmt() {
		return postalCardAmt;
	}

	public void setPostalCardAmt(String postalCardAmt) {
		this.postalCardAmt = postalCardAmt;
	}

	public List<String> getRemList() {
		return remList;
	}

	public void setRemList(List<String> remList) {
		this.remList = remList;
	}

	public String getEmpRmk() {
		return empRmk;
	}

	public void setEmpRmk(String empRmk) {
		this.empRmk = empRmk;
	}

	public String getRtiSecndAplStatus() {
		return rtiSecndAplStatus;
	}

	public void setRtiSecndAplStatus(String rtiSecndAplStatus) {
		this.rtiSecndAplStatus = rtiSecndAplStatus;
	}

	public Long getSliDays() {
		return sliDays;
	}

	public void setSliDays(Long sliDays) {
		this.sliDays = sliDays;
	}

	public List<Long> getSliDaysList() {
		return sliDaysList;
	}

	public void setSliDaysList(List<Long> sliDaysList) {
		this.sliDaysList = sliDaysList;
	}

	public String getNonJudclNo() {
		return nonJudclNo;
	}

	public void setNonJudclNo(String nonJudclNo) {
		this.nonJudclNo = nonJudclNo;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getNonJudclDate() {
		return nonJudclDate;
	}

	public void setNonJudclDate(Date nonJudclDate) {
		this.nonJudclDate = nonJudclDate;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public List<DocumentDetailsVO> getChlNonJudDoc() {
		return chlNonJudDoc;
	}

	public void setChlNonJudDoc(List<DocumentDetailsVO> chlNonJudDoc) {
		this.chlNonJudDoc = chlNonJudDoc;
	}

}
