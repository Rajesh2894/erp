/**
 * @author  : Harshit kumar
 * @since   : 20 Feb 2018
 * @comment : Entity class for RTI Application
 */
package com.abm.mainet.rti.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_RTI_APPLICATION_HIST")
public class TbRtiApplicationDetailsHistory implements Serializable {

	private static final long serialVersionUID = 6305845804554510493L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "RTI_H_ID", nullable = false)
	private Long rtiHistId;

	@Column(name = "RTI_ID", nullable = false)
	private Long rtiId;

	@Column(name = "RTI_NO", length = 20)
	private String rtiNo;

	/*
	 * @JsonIgnore
	 * 
	 * @OneToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "APM_APPLICATION_ID", referencedColumnName =
	 * "APM_APPLICATION_ID")
	 */
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;

	@Column(name = "ORGID", nullable = false)
	private long orgId;

	@Column(name = "APPL_REFERENCE_MODE")
	private int applReferenceMode;

	@Column(name = "APM_APPLICATION_DATE")
	private Date apmApplicationDate;
	@Column(name = "RTI_DEPTID")
	private int rtiDeptId;

	@Column(name = "RTI_LOCATION_ID")
	private int rtiLocationId;

	@Column(name = "RTI_SUBJECT", length = 500)
	private String rtiSubject;

	@Column(name = "RTI_DETAILS", length = 2000)
	private String rtiDetails;

	@Column(name = "LOIAPPLICABLE", length = 5)
	private String loiApplicable;

	@Column(name = "REASON_FOR_LOI_NA", length = 1000)
	private String reasonForLoiNa;

	@Column(name = "FINALDISPATCHMODE")
	private int finalDispatchMode;

	@Temporal(TemporalType.DATE)
	@Column(name = "DISPATCHDATE")
	private Date dispatchDate;

	@Column(name = "STAMP_NO", length = 50)
	private String stampNo;

	@Column(name = "STAMP_AMT")
	private Double stampAmt;

	@Column(name = "STAMP_DOC_PATH", length = 500)
	private String stampDocPath;

	@Column(name = "RTI_ACTION")
	private int rtiAction;

	@Column(name = "REASON_ID")
	private int reasonId;

	@Column(name = "RTI_REMARK", length = 2000)
	private String rtiRemarks;

	@Column(name = "PARTIAL_INFO_FLAG", length = 16)
	private int partialInfoFlag;

	@Column(name = "INWARD_TYPE")
	private int inwardType;

	@Column(name = "INW_REFERENCE_NO", length = 100)
	private String inwReferenceNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "INW_REFERENCE_DATE")
	private Date inwReferenceDate;

	@Column(name = "INW_AUTHORITY_DEPT", length = 100)
	private String inwAuthorityDept;

	@Column(name = "INW_AUTHORITY_NAME", length = 100)
	private String inwAuthorityName;

	@Column(name = "INW_AUTHORITY_ADDRESS", length = 1000)
	private String inwAuthorityAddress;

	@Column(name = "OTH_FORWARD_PIO_ADDRESS", length = 1000)
	private String othForwardPioAddress;

	@Column(name = "OTH_FORWARD_PIO_NAME", length = 100)
	private String othForwardPioName;

	@Column(name = "OTH_FORWARD_DEPT_NAME", length = 100)
	private String othForwardDeptName;

	@Column(name = "OTH_FORWARD_PIO_EMAIL", length = 100)
	private String othForwardPioEmail;

	@Column(name = "OTH_FORWARD_PIO_MOB_NO")
	private int othForwardPioModNo;

	@Column(name = "LANG_ID", nullable = false)
	private Long langId;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = false)
	private Date lModDate;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updateDate;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private int lgIpMacUpd;

	@Column(name = "ISDELETED")
	private int isDeleted;

	@Column(name = "DELIVERYREFERENCENUMBER")
	private int deliveryReferenceNumber;

	@Column(name = "RTI_BPLFLAG", length = 1)
	private String rtiBplFlag;
	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "rtiMedId", cascade =
	 * CascadeType.MERGE) private List<TbRtiMediaDetails> rtiMediaIdList = new
	 * ArrayList<>(0);
	 */

	@Column(name = "RTI_APPL_TYPE")
	private String appealType;

	@Column(name = "RTI_DISPATCH_NAME")
	private String DispatchName;

	@Column(name = "RTI_DISPATCH_MOBNO")
	private String DispatchMobile;

	@Column(name = "RTI_DISPATCH_DOCNO")
	private String DispatchDocketNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "RTI_DECI_REC_DATE")
	private Date rtiDeciRecDate;

	@Column(name = "RTI_DECI_DET")
	private String rtiDeciDet;

	@Column(name = "RTI_PIO_ACTION")
	private String rtiPioAction;

	@Temporal(TemporalType.DATE)
	@Column(name = "RTI_PIO_ACTION_DATE")
	private Date rtiPioActionDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "RTI_DEPTID_FDATE")
	private Date rtiDeptidFdate;

	@Column(name = "RTI_DISPATCH_NO")
	private String dispatchNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "RTI_DISPATCH_DATE")
	private Date dispatchDt;

	// adding correspondance address related field by rajesh.das

	@Column(name = "CORR_PINCODE")
	private Long corrAddrsPincodeNo;

	@Column(name = "CORR_ADDRESS")
	private String corrAddrsAreaName;
	@Column(name = "RTI_DESC")
	private String rtiDesc;

	@Column(name = "RTI_RELATED_DEPT")
	private String rtiRelatedDeptId;

	@Column(name = "RTI_FWD_ORGID")
	private String frdOrgId;
	// Added for Word zone wise work flow
	@Column(name = "TRD_WARD1", nullable = false)
	private Long trdWard1;

	@Column(name = "TRD_WARD2")
	private Long trdWard2;

	@Column(name = "TRD_WARD3")
	private Long trdWard3;

	@Column(name = "TRD_WARD4")
	private Long trdWard4;

	@Column(name = "TRD_WARD5")
	private Long trdWard5;
	@Column(name = "NON_JUDCL_NO")
	private String nonJudclNo;
	@Column(name = "CHALLAN_NO")
	private String challanNo;
	@Temporal(TemporalType.DATE)
	@Column(name = "NON_JUDCL_DATE")
	private Date nonJudclDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "CHALLAN_DATE")
	private Date challanDate;

	public Long getRtiId() {
		return rtiId;
	}

	public void setRtiId(Long rtiId) {
		this.rtiId = rtiId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Double getStampAmt() {
		return stampAmt;
	}

	public void setStampAmt(Double stampAmt) {
		this.stampAmt = stampAmt;
	}

	public int getRtiAction() {
		return rtiAction;
	}

	public void setRtiAction(int rtiAction) {
		this.rtiAction = rtiAction;
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

	public int getOthForwardPioModNo() {
		return othForwardPioModNo;
	}

	public void setOthForwardPioModNo(int othForwardPioModNo) {
		this.othForwardPioModNo = othForwardPioModNo;
	}

	public String getRtiNo() {
		return rtiNo;
	}

	public void setRtiNo(String rtiNo) {
		this.rtiNo = rtiNo;
	}

	public int getApplReferenceMode() {
		return applReferenceMode;
	}

	public void setApplReferenceMode(int applReferenceMode) {
		this.applReferenceMode = applReferenceMode;
	}

	public int getRtiDeptId() {
		return rtiDeptId;
	}

	public void setRtiDeptId(int rtiDeptId) {
		this.rtiDeptId = rtiDeptId;
	}

	public int getRtiLocationId() {
		return rtiLocationId;
	}

	public void setRtiLocationId(int rtiLocationId) {
		this.rtiLocationId = rtiLocationId;
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

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public String getRtiRemarks() {
		return rtiRemarks;
	}

	public void setRtiRemarks(String rtiRemarks) {
		this.rtiRemarks = rtiRemarks;
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

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public long getOrgId() {
		return orgId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	/*
	 * public List<TbRtiMediaDetails> getRtiMediaId() { return rtiMediaIdList; }
	 * public void setRtiMediaId(List<TbRtiMediaDetails> rtiMediaId) {
	 * this.rtiMediaIdList = rtiMediaId; }
	 */
	public String getRtiSubject() {
		return rtiSubject;
	}

	public void setRtiSubject(String rtiSubject) {
		this.rtiSubject = rtiSubject;
	}

	public Date getApmApplicationDate() {
		return apmApplicationDate;
	}

	public void setApmApplicationDate(Date apmApplicationDate) {
		this.apmApplicationDate = apmApplicationDate;
	}

	public int getPartialInfoFlag() {
		return partialInfoFlag;
	}

	public void setPartialInfoFlag(int partialInfoFlag) {
		this.partialInfoFlag = partialInfoFlag;
	}

	public int getDeliveryReferenceNumber() {
		return deliveryReferenceNumber;
	}

	public void setDeliveryReferenceNumber(int deliveryReferenceNumber) {
		this.deliveryReferenceNumber = deliveryReferenceNumber;
	}

	public String[] getPkValues() {
		return new String[] { "RTI", "TB_RTI_APPLICATION", "RTI_ID" };
	}

	public String getRtiBplFlag() {
		return rtiBplFlag;
	}

	public void setRtiBplFlag(String rtiBplFlag) {
		this.rtiBplFlag = rtiBplFlag;
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

	public String getRtiDesc() {
		return rtiDesc;
	}

	public void setRtiDesc(String rtiDesc) {
		this.rtiDesc = rtiDesc;
	}

	public String getRtiRelatedDeptId() {
		return rtiRelatedDeptId;
	}

	public void setRtiRelatedDeptId(String rtiRelatedDeptId) {
		this.rtiRelatedDeptId = rtiRelatedDeptId;
	}

	public String getFrdOrgId() {
		return frdOrgId;
	}

	public void setFrdOrgId(String frdOrgId) {
		this.frdOrgId = frdOrgId;
	}

	public Long getRtiHistId() {
		return rtiHistId;
	}

	public void setRtiHistId(Long rtiHistId) {
		this.rtiHistId = rtiHistId;
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

}
