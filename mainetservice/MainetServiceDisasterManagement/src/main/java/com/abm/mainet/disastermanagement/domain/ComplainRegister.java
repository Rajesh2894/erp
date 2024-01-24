package com.abm.mainet.disastermanagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



/**
 * A ComplainRegister.
 */
@Entity
@Table(name = "tb_dm_complain_register")
public class ComplainRegister implements Serializable {

	private static final long serialVersionUID = 4487850785958071747L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "complain_id", nullable = false)
	private Long complainId;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "complain_id")
	private List<ComplainScrutiny> complainScrutiny;

	@Column(name = "complain_no")
	private String complainNo;
	
	@Column(name = "manual_complain_no")
	private String manualComplainNo;
	
	@Column(name = "designation")
	private String designation;

	@Column(name = "department")
	private String department;
	
	@Column(name = "cod_complaint_type1")
	private Long complaintType1;

	@Column(name = "cod_complaint_type2")
	private Long complaintType2;

	@Column(name = "cod_complaint_type3")
	private Long complaintType3;

	@Column(name = "cod_complaint_type4")
	private Long complaintType4;

	@Column(name = "cod_complaint_type5")
	private Long complaintType5;

	@Column(name = "apm_application_id")
	private Long applicationId;

	@Column(name = "complainer_name")
	private String complainerName;

	@Column(name = "complainer_mobile")
	private String complainerMobile;
	
	@Column(name = "complainer_mobile1")
	private String complainerMobile1;

	@Column(name = "complainer_address")
	private String complainerAddress;

	@Column(name = "complaint_description")
	private String complaintDescription;

	@Column(name = "loc_id")
	private Long location;

	@Column(name = "complain_status")
	private String complainStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name="complain_close_date")
	private Date complainCloseDate;
	
	@Column(name="call_close_remark")
	private String remark;
	
	@Column(name="no_of_death_child")
	private Long noOfDeathChild;

	@Column(name="no_of_death_female")
	private Long noOfDeathFemale;

	@Column(name="no_of_death_male")
	private Long noOfDeathMale;

	@Column(name="no_of_injury_child")
	private Long noOfInjuryChild;

	@Column(name="no_of_injury_female")
	private Long noOfInjuryFemale;

	@Column(name="no_of_injury_male")
	private Long noOfInjuryMale;

	@Column(name = "orgid")
	private Long orgid;

	@Column(name = "created_by")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac")
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd")
	private String lgIpMacUpd;
	
	@Column(name = "caller_area")
	private String callerArea;
	
	@Column(name="no_of_veh_Damaged")
	private String noOfVehDamaged;
	
//	@Column(name = "USER_ID", nullable = false, updatable = false)
//	private Long userId;
	
//	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
//	private int langId;

	public List<ComplainScrutiny> getComplainScrutiny() {
		return complainScrutiny;
	}

	public void setComplainScrutiny(List<ComplainScrutiny> complainScrutiny) {
		this.complainScrutiny = complainScrutiny;
	}
	
	
	
	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the complainId
	 */
	public Long getComplainId() {
		return complainId;
	}

	/**
	 * @param complainId the complainId to set
	 */
	public void setComplainId(Long complainId) {
		this.complainId = complainId;
	}

	/**
	 * @return the complainNo
	 */
	public String getComplainNo() {
		return complainNo;
	}

	/**
	 * @param complainNo the complainNo to set
	 */
	public void setComplainNo(String complainNo) {
		this.complainNo = complainNo;
	}
	
	

	public String getManualComplainNo() {
		return manualComplainNo;
	}

	public void setManualComplainNo(String manualComplainNo) {
		this.manualComplainNo = manualComplainNo;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the complaintType1
	 */
	public Long getComplaintType1() {
		return complaintType1;
	}

	/**
	 * @param complaintType1 the complaintType1 to set
	 */
	public void setComplaintType1(Long complaintType1) {
		this.complaintType1 = complaintType1;
	}

	/**
	 * @return the complaintType2
	 */
	public Long getComplaintType2() {
		return complaintType2;
	}

	/**
	 * @param complaintType2 the complaintType2 to set
	 */
	public void setComplaintType2(Long complaintType2) {
		this.complaintType2 = complaintType2;
	}

	/**
	 * @return the complaintType3
	 */
	public Long getComplaintType3() {
		return complaintType3;
	}

	/**
	 * @param complaintType3 the complaintType3 to set
	 */
	public void setComplaintType3(Long complaintType3) {
		this.complaintType3 = complaintType3;
	}

	/**
	 * @return the complaintType4
	 */
	public Long getComplaintType4() {
		return complaintType4;
	}

	/**
	 * @param complaintType4 the complaintType4 to set
	 */
	public void setComplaintType4(Long complaintType4) {
		this.complaintType4 = complaintType4;
	}

	/**
	 * @return the complaintType5
	 */
	public Long getComplaintType5() {
		return complaintType5;
	}

	/**
	 * @param complaintType5 the complaintType5 to set
	 */
	public void setComplaintType5(Long complaintType5) {
		this.complaintType5 = complaintType5;
	}

	/**
	 * @return the complainerName
	 */
	public String getComplainerName() {
		return complainerName;
	}

	/**
	 * @param complainerName the complainerName to set
	 */
	public void setComplainerName(String complainerName) {
		this.complainerName = complainerName;
	}

	/**
	 * @return the complainerMobile
	 */
	public String getComplainerMobile() {
		return complainerMobile;
	}

	/**
	 * @param complainerMobile the complainerMobile to set
	 */
	public void setComplainerMobile(String complainerMobile) {
		this.complainerMobile = complainerMobile;
	}

	/**
	 * @return the complainerAddress
	 */
	public String getComplainerAddress() {
		return complainerAddress;
	}

	/**
	 * @param complainerAddress the complainerAddress to set
	 */
	public void setComplainerAddress(String complainerAddress) {
		this.complainerAddress = complainerAddress;
	}

	/**
	 * @return the complaintDescription
	 */
	public String getComplaintDescription() {
		return complaintDescription;
	}

	/**
	 * @param complaintDescription the complaintDescription to set
	 */
	public void setComplaintDescription(String complaintDescription) {
		this.complaintDescription = complaintDescription;
	}

	/**
	 * @return the location
	 */
	public Long getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Long location) {
		this.location = location;
	}

	/**
	 * @return the orgid
	 */
	public Long getOrgid() {
		return orgid;
	}

	/**
	 * @param orgid the orgid to set
	 */
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}


	/**
	 * @return the complainStatus
	 */
	public String getcomplainStatus() {
		return complainStatus;
	}

	/**
	 * @param complainStatus the complainStatus to set
	 */
	public void setcomplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	
	

	/**
	 * @return the complainCloseDate
	 */
	public Date getComplainCloseDate() {
		return complainCloseDate;
	}

	/**
	 * @param complainCloseDate the complainCloseDate to set
	 */
	public void setComplainCloseDate(Date complainCloseDate) {
		this.complainCloseDate = complainCloseDate;
	}

	/**
	 * @return the callCloseRemark
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the noOfDeathChild
	 */
	public Long getNoOfDeathChild() {
		return noOfDeathChild;
	}


	/**
	 * @param noOfDeathChild the noOfDeathChild to set
	 */
	public void setNoOfDeathChild(Long noOfDeathChild) {
		this.noOfDeathChild = noOfDeathChild;
	}

	/**
	 * @return the noOfDeathFemale
	 */
	public Long getNoOfDeathFemale() {
		return noOfDeathFemale;
	}

	/**
	 * @param noOfDeathFemale the noOfDeathFemale to set
	 */
	public void setNoOfDeathFemale(Long noOfDeathFemale) {
		this.noOfDeathFemale = noOfDeathFemale;
	}

	/**
	 * @return the noOfDeathMale
	 */
	public Long getNoOfDeathMale() {
		return noOfDeathMale;
	}

	/**
	 * @param noOfDeathMale the noOfDeathMale to set
	 */
	public void setNoOfDeathMale(Long noOfDeathMale) {
		this.noOfDeathMale = noOfDeathMale;
	}

	/**
	 * @return the noOfInjuryChild
	 */
	public Long getNoOfInjuryChild() {
		return noOfInjuryChild;
	}

	/**
	 * @param noOfInjuryChild the noOfInjuryChild to set
	 */
	public void setNoOfInjuryChild(Long noOfInjuryChild) {
		this.noOfInjuryChild = noOfInjuryChild;
	}

	/**
	 * @return the noOfInjuryFemale
	 */
	public Long getNoOfInjuryFemale() {
		return noOfInjuryFemale;
	}

	/**
	 * @param noOfInjuryFemale the noOfInjuryFemale to set
	 */
	public void setNoOfInjuryFemale(Long noOfInjuryFemale) {
		this.noOfInjuryFemale = noOfInjuryFemale;
	}

	/**
	 * @return the noOfInjuryMale
	 */
	public Long getNoOfInjuryMale() {
		return noOfInjuryMale;
	}

	/**
	 * @param noOfInjuryMale the noOfInjuryMale to set
	 */
	public void setNoOfInjuryMale(Long noOfInjuryMale) {
		this.noOfInjuryMale = noOfInjuryMale;
	}

	public String getCallerArea() {
		return callerArea;
	}

	public void setCallerArea(String callerArea) {
		this.callerArea = callerArea;
	}
	
	public String getNoOfVehDamaged() {
		return noOfVehDamaged;
	}

	public void setNoOfVehDamaged(String noOfVehDamaged) {
		this.noOfVehDamaged = noOfVehDamaged;
	}

	public String getComplainerMobile1() {
		return complainerMobile1;
	}

	public void setComplainerMobile1(String complainerMobile1) {
		this.complainerMobile1 = complainerMobile1;
	}

	public String[] getPkValues() {
		return new String[] { "DM", "TB_DM_COMPLAIN_REGISTER", "COMPLAIN_ID" };
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicationId, remark, complainCloseDate, complainId, complainNo,
				complainerAddress, complainerMobile, complainerName, complaintDescription, complainStatus,
				complaintType1, complaintType2, complaintType3, complaintType4, complaintType5, createdBy, createdDate,
				department, lgIpMac, lgIpMacUpd, location, noOfDeathChild, noOfDeathFemale, noOfDeathMale,
				noOfInjuryChild, noOfInjuryFemale, noOfInjuryMale, orgid, updatedBy, updatedDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ComplainRegister)) {
			return false;
		}
		ComplainRegister other = (ComplainRegister) obj;
		return Objects.equals(applicationId, other.applicationId)
				&& Objects.equals(remark, other.remark)
				&& Objects.equals(complainCloseDate, other.complainCloseDate)
				&& Objects.equals(complainId, other.complainId) && Objects.equals(complainNo, other.complainNo)
				&& Objects.equals(complainerAddress, other.complainerAddress)
				&& Objects.equals(complainerMobile, other.complainerMobile)
				&& Objects.equals(complainerName, other.complainerName)
				&& Objects.equals(complaintDescription, other.complaintDescription)
				&& Objects.equals(complainStatus, other.complainStatus)
				&& Objects.equals(complaintType1, other.complaintType1)
				&& Objects.equals(complaintType2, other.complaintType2)
				&& Objects.equals(complaintType3, other.complaintType3)
				&& Objects.equals(complaintType4, other.complaintType4)
				&& Objects.equals(complaintType5, other.complaintType5) && Objects.equals(createdBy, other.createdBy)
				&& Objects.equals(createdDate, other.createdDate) && Objects.equals(department, other.department)
				&& Objects.equals(lgIpMac, other.lgIpMac) && Objects.equals(lgIpMacUpd, other.lgIpMacUpd)
				&& Objects.equals(location, other.location) && Objects.equals(noOfDeathChild, other.noOfDeathChild)
				&& Objects.equals(noOfDeathFemale, other.noOfDeathFemale)
				&& Objects.equals(noOfDeathMale, other.noOfDeathMale)
				&& Objects.equals(noOfInjuryChild, other.noOfInjuryChild)
				&& Objects.equals(noOfInjuryFemale, other.noOfInjuryFemale)
				&& Objects.equals(noOfInjuryMale, other.noOfInjuryMale) && Objects.equals(orgid, other.orgid)
				&& Objects.equals(updatedBy, other.updatedBy) && Objects.equals(updatedDate, other.updatedDate);
	}

	@Override
	public String toString() {
		return "ComplainRegister [complainId=" + complainId + ", complainNo=" + complainNo + ", deprtment=" + department
				+ ", complaintType=" + complaintType1 + ", applicationId=" + applicationId + ", complainerName="
				+ complainerName + ", complainerMobile=" + complainerMobile + ", complainerAddress=" + complainerAddress
				+ ", complaintDescription=" + complaintDescription + ", location=" + location + ", orgid=" + orgid
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
	}

}
