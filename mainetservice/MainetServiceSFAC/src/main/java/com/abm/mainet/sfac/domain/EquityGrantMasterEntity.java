/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "Tb_Sfac_Equity_Grant_Mast")
public class EquityGrantMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -601661698259166013L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EG_ID", nullable = false)
	private Long egId;

	@Column(name = "FPO_NAME")
	private Long fpoName;

	@Column(name = "STATE")
	private Long state;

	@Column(name = "DISTRICT")
	private Long district;

	@Column(name = "CORRES_ADD")
	private String CorrespondenceAdd;

	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "REG_NO")
	private String registrationNo;

	@Column(name = "REG_DATE")
	private Date registrationDate;

	@Column(name = "BUSINESS_OF_FPC")
	private String businessofFPC;

	@Column(name = "NO_OF_SHARE_HOLDER_MEM")
	private Long noofShareholderMem;

	@Column(name = "NO_OF_SML_SHARE_HOLDER_MEM")
	private Long noOfSMLShareholderMemb;

	@Column(name = "AUTHORISED_CAPITAL")
	private BigDecimal authorisedCapital;

	@Column(name = "PAID_UP_CAPITAL")
	private BigDecimal paidUpCapital;

	@Column(name = "AMT_OF_EQUITY_GRANT")
	private BigDecimal amountofEquityGrant;

	@Column(name = "MAX_IND_SHARE_HOLD_MEM")
	private BigDecimal maxIndShareholdMem;

	@Column(name = "MAX_SHARE_HOLD_INS_MEM")
	private BigDecimal MaxShareholdInsMem;

	@Column(name = "RI_NAME")
	private String riName;

	@Column(name = "RI_CONTACT_NO")
	private String riContactNo;

	@Column(name = "RI_ADDRESS")
	private String riAddress;

	@Column(name = "RI_EMAIL_ID")
	private String riEmailId;

	@Column(name = "APP_STATUS")
	private String appStatus;
	
	@Column(name = "APP_NO")
	private Long appNumber;
	
	@Column(name = "AUTH_REMARK")
	private String authRemark;

	@Column(name = "NO_OF_DIRECTORS")
	private Long noOfDirectors;

	@Column(name = "NO_OF_WOMEN_DIRECTORS")
	private Long womenDirectors;

	@Column(name = "LAST_YR_BOARD_MEET_DT")
	private Date lastYrBoardMeetDt;

	@Column(name = "NO_OF_FUNC_COMMITTEES")
	private String NoofFuncCommittees;

	@Column(name = "ROLES_RESPOF_BOARD_DIREC")
	private String RolesRespofBoardDirec;
	
	@Column(name = "BANK_ID", nullable = true)
	private Long bankId;
	
	@Column(name = "BRANCH_EMAIL", nullable = true)
	private String branchEmail;
	
	@Column(name="MODE_OF_BOARD_FORMATN")
	private Long modeOfBoardFormation;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<EquityGrantDetailEntity> equityGrantDetailEntities = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<EquityGrantFuctionalCommitteeDetailEntity>  equityGrantFuctionalCommitteeDetailEntities = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterEntity", cascade = CascadeType.ALL)
	private List<EquityGrantShareHoldingDetailEntity> equityGrantShareHoldingDetailEntities = new ArrayList<>();

	/**
	 * @return the egId
	 */
	public Long getEgId() {
		return egId;
	}

	/**
	 * @param egId the egId to set
	 */
	public void setEgId(Long egId) {
		this.egId = egId;
	}

	/**
	 * @return the fpoName
	 */
	public Long getFpoName() {
		return fpoName;
	}

	/**
	 * @param fpoName the fpoName to set
	 */
	public void setFpoName(Long fpoName) {
		this.fpoName = fpoName;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * @return the district
	 */
	public Long getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(Long district) {
		this.district = district;
	}

	/**
	 * @return the correspondenceAdd
	 */
	public String getCorrespondenceAdd() {
		return CorrespondenceAdd;
	}

	/**
	 * @param correspondenceAdd the correspondenceAdd to set
	 */
	public void setCorrespondenceAdd(String correspondenceAdd) {
		CorrespondenceAdd = correspondenceAdd;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the registrationNo
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}

	/**
	 * @param registrationNo the registrationNo to set
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	/**
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	/**
	 * @return the businessofFPC
	 */
	public String getBusinessofFPC() {
		return businessofFPC;
	}

	/**
	 * @param businessofFPC the businessofFPC to set
	 */
	public void setBusinessofFPC(String businessofFPC) {
		this.businessofFPC = businessofFPC;
	}

	/**
	 * @return the noofShareholderMem
	 */
	public Long getNoofShareholderMem() {
		return noofShareholderMem;
	}

	/**
	 * @param noofShareholderMem the noofShareholderMem to set
	 */
	public void setNoofShareholderMem(Long noofShareholderMem) {
		this.noofShareholderMem = noofShareholderMem;
	}

	/**
	 * @return the noOfSMLShareholderMemb
	 */
	public Long getNoOfSMLShareholderMemb() {
		return noOfSMLShareholderMemb;
	}

	/**
	 * @param noOfSMLShareholderMemb the noOfSMLShareholderMemb to set
	 */
	public void setNoOfSMLShareholderMemb(Long noOfSMLShareholderMemb) {
		this.noOfSMLShareholderMemb = noOfSMLShareholderMemb;
	}

	/**
	 * @return the authorisedCapital
	 */
	public BigDecimal getAuthorisedCapital() {
		return authorisedCapital;
	}

	/**
	 * @param authorisedCapital the authorisedCapital to set
	 */
	public void setAuthorisedCapital(BigDecimal authorisedCapital) {
		this.authorisedCapital = authorisedCapital;
	}

	/**
	 * @return the paidUpCapital
	 */
	public BigDecimal getPaidUpCapital() {
		return paidUpCapital;
	}

	/**
	 * @param paidUpCapital the paidUpCapital to set
	 */
	public void setPaidUpCapital(BigDecimal paidUpCapital) {
		this.paidUpCapital = paidUpCapital;
	}

	/**
	 * @return the amountofEquityGrant
	 */
	public BigDecimal getAmountofEquityGrant() {
		return amountofEquityGrant;
	}

	/**
	 * @param amountofEquityGrant the amountofEquityGrant to set
	 */
	public void setAmountofEquityGrant(BigDecimal amountofEquityGrant) {
		this.amountofEquityGrant = amountofEquityGrant;
	}

	/**
	 * @return the maxIndShareholdMem
	 */
	public BigDecimal getMaxIndShareholdMem() {
		return maxIndShareholdMem;
	}

	/**
	 * @param maxIndShareholdMem the maxIndShareholdMem to set
	 */
	public void setMaxIndShareholdMem(BigDecimal maxIndShareholdMem) {
		this.maxIndShareholdMem = maxIndShareholdMem;
	}

	/**
	 * @return the maxShareholdInsMem
	 */
	public BigDecimal getMaxShareholdInsMem() {
		return MaxShareholdInsMem;
	}

	/**
	 * @param maxShareholdInsMem the maxShareholdInsMem to set
	 */
	public void setMaxShareholdInsMem(BigDecimal maxShareholdInsMem) {
		MaxShareholdInsMem = maxShareholdInsMem;
	}

	/**
	 * @return the riName
	 */
	public String getRiName() {
		return riName;
	}

	/**
	 * @param riName the riName to set
	 */
	public void setRiName(String riName) {
		this.riName = riName;
	}

	/**
	 * @return the riContactNo
	 */
	public String getRiContactNo() {
		return riContactNo;
	}

	/**
	 * @param riContactNo the riContactNo to set
	 */
	public void setRiContactNo(String riContactNo) {
		this.riContactNo = riContactNo;
	}

	/**
	 * @return the riAddress
	 */
	public String getRiAddress() {
		return riAddress;
	}

	/**
	 * @param riAddress the riAddress to set
	 */
	public void setRiAddress(String riAddress) {
		this.riAddress = riAddress;
	}

	/**
	 * @return the riEmailId
	 */
	public String getRiEmailId() {
		return riEmailId;
	}

	/**
	 * @param riEmailId the riEmailId to set
	 */
	public void setRiEmailId(String riEmailId) {
		this.riEmailId = riEmailId;
	}

	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the noOfDirectors
	 */
	public Long getNoOfDirectors() {
		return noOfDirectors;
	}

	/**
	 * @param noOfDirectors the noOfDirectors to set
	 */
	public void setNoOfDirectors(Long noOfDirectors) {
		this.noOfDirectors = noOfDirectors;
	}

	/**
	 * @return the womenDirectors
	 */
	public Long getWomenDirectors() {
		return womenDirectors;
	}

	/**
	 * @param womenDirectors the womenDirectors to set
	 */
	public void setWomenDirectors(Long womenDirectors) {
		this.womenDirectors = womenDirectors;
	}

	/**
	 * @return the lastYrBoardMeetDt
	 */
	public Date getLastYrBoardMeetDt() {
		return lastYrBoardMeetDt;
	}

	/**
	 * @param lastYrBoardMeetDt the lastYrBoardMeetDt to set
	 */
	public void setLastYrBoardMeetDt(Date lastYrBoardMeetDt) {
		this.lastYrBoardMeetDt = lastYrBoardMeetDt;
	}

	/**
	 * @return the noofFuncCommittees
	 */
	public String getNoofFuncCommittees() {
		return NoofFuncCommittees;
	}

	/**
	 * @param noofFuncCommittees the noofFuncCommittees to set
	 */
	public void setNoofFuncCommittees(String noofFuncCommittees) {
		NoofFuncCommittees = noofFuncCommittees;
	}

	/**
	 * @return the rolesRespofBoardDirec
	 */
	public String getRolesRespofBoardDirec() {
		return RolesRespofBoardDirec;
	}

	/**
	 * @param rolesRespofBoardDirec the rolesRespofBoardDirec to set
	 */
	public void setRolesRespofBoardDirec(String rolesRespofBoardDirec) {
		RolesRespofBoardDirec = rolesRespofBoardDirec;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_EQUITY_GRANT_MAST", "EG_ID" };
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getAppNumber() {
		return appNumber;
	}

	public void setAppNumber(Long appNumber) {
		this.appNumber = appNumber;
	}

	public String getBranchEmail() {
		return branchEmail;
	}

	public void setBranchEmail(String branchEmail) {
		this.branchEmail = branchEmail;
	}

	public List<EquityGrantDetailEntity> getEquityGrantDetailEntities() {
		return equityGrantDetailEntities;
	}

	public void setEquityGrantDetailEntities(List<EquityGrantDetailEntity> equityGrantDetailEntities) {
		this.equityGrantDetailEntities = equityGrantDetailEntities;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public List<EquityGrantFuctionalCommitteeDetailEntity> getEquityGrantFuctionalCommitteeDetailEntities() {
		return equityGrantFuctionalCommitteeDetailEntities;
	}

	public void setEquityGrantFuctionalCommitteeDetailEntities(
			List<EquityGrantFuctionalCommitteeDetailEntity> equityGrantFuctionalCommitteeDetailEntities) {
		this.equityGrantFuctionalCommitteeDetailEntities = equityGrantFuctionalCommitteeDetailEntities;
	}

	public List<EquityGrantShareHoldingDetailEntity> getEquityGrantShareHoldingDetailEntities() {
		return equityGrantShareHoldingDetailEntities;
	}

	public void setEquityGrantShareHoldingDetailEntities(
			List<EquityGrantShareHoldingDetailEntity> equityGrantShareHoldingDetailEntities) {
		this.equityGrantShareHoldingDetailEntities = equityGrantShareHoldingDetailEntities;
	}

	public Long getModeOfBoardFormation() {
		return modeOfBoardFormation;
	}

	public void setModeOfBoardFormation(Long modeOfBoardFormation) {
		this.modeOfBoardFormation = modeOfBoardFormation;
	}
	
	

}
