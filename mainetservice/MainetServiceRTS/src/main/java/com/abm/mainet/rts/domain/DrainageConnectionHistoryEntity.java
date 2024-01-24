package com.abm.mainet.rts.domain;

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
/**
 * @author rahul.chaubey
 *  
 */
@Entity
@Table(name = "TB_RTS_DRN_CON_HIST")
public class DrainageConnectionHistoryEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4870221477208958603L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CNN_ID_H", nullable = false)
	private Long cnnHistId;
	
	@Column(name = "CNN_ID",  nullable = false)
	private Long connectionId;

	@Column(name = "DRAINAGE_ADDRESS", length = 1000, nullable = false)
	private String drainageAddress;
	
	@Column(name = "PROPERTY_INDEX_NO", length = 100, nullable = false)
	private String propertyIndexNo;
	
	@Column(name = "SIZE_OF_CONNECTION", length = 12, nullable = false)
	private Long sizeOfConnection;
	
	@Column(name = "TYPE_OF_CONNECTION", length = 12, nullable = false)
	private Long typeOfConnection;
	
	@Column(name = "WARD", length = 12, nullable = false)
	private Long ward;
	
	@Column(name = "APPLICANT_TYPE", length = 12, nullable = true)
	private Long applicantType;
	
	@Column(name = "APM_APPLICATION_ID", length = 16, nullable = true)
	private Long apmApplicationId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "H_STATUS", nullable = true)
	private String hStatus;

	public Long getCnnHistId() {
		return cnnHistId;
	}

	public void setCnnHistId(Long cnnHistId) {
		this.cnnHistId = cnnHistId;
	}

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}

	public String getDrainageAddress() {
		return drainageAddress;
	}

	public void setDrainageAddress(String drainageAddress) {
		this.drainageAddress = drainageAddress;
	}

	public String getPropertyIndexNo() {
		return propertyIndexNo;
	}

	public void setPropertyIndexNo(String propertyIndexNo) {
		this.propertyIndexNo = propertyIndexNo;
	}

	public Long getSizeOfConnection() {
		return sizeOfConnection;
	}

	public void setSizeOfConnection(Long sizeOfConnection) {
		this.sizeOfConnection = sizeOfConnection;
	}

	public Long getTypeOfConnection() {
		return typeOfConnection;
	}

	public void setTypeOfConnection(Long typeOfConnection) {
		this.typeOfConnection = typeOfConnection;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}

	public Long getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(Long applicantType) {
		this.applicantType = applicantType;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	

	public static String[] getPkValues() {
		return new String[] { "RTS", "TB_RTS_DRN_CON_HIST", "CNN_ID_H" };
	}
	
}
