package com.abm.mainet.sfac.domain;

import java.io.Serializable;
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

@Entity
@Table(name = "Tb_SFAC_Circular_Notify_Master")
public class CircularNotificationMasterEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -753049474551554887L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CN_ID", unique = true, nullable = false)
	private Long cnId;
	
	@Column(name = "CIRCULAR_TITLE")
	private String circularTitle;
	
	@Column(name = "CIRCULAR_NO")
	private String circularNo;
	
	@Column(name = "CONVENER_OF_CIRCULAR")
	private Long convenerOfCircular;
	
	@Column(name = "CONVENER_NAME")
	private String convenerName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_CIRCULAR")
	private Date dateOfCircular;
	
	@Column(name = "EMAIL_BODY")
	private String emailBody;
	
	@Column(name = "SMS_BODY")
	private String smsBody;

	@Column(name = "DOC_DESC")
	private String docDesc;
	
	@Column(name = "STATUS")
	private String status;
	
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
	private List<CircularNotiicationDetEntity> circularNotiicationDetEntities = new ArrayList<>();

	public Long getCnId() {
		return cnId;
	}

	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}

	public String getCircularTitle() {
		return circularTitle;
	}

	public void setCircularTitle(String circularTitle) {
		this.circularTitle = circularTitle;
	}

	public String getCircularNo() {
		return circularNo;
	}

	public void setCircularNo(String circularNo) {
		this.circularNo = circularNo;
	}

	public Long getConvenerOfCircular() {
		return convenerOfCircular;
	}

	public void setConvenerOfCircular(Long convenerOfCircular) {
		this.convenerOfCircular = convenerOfCircular;
	}

	public String getConvenerName() {
		return convenerName;
	}

	public void setConvenerName(String convenerName) {
		this.convenerName = convenerName;
	}

	public Date getDateOfCircular() {
		return dateOfCircular;
	}

	public void setDateOfCircular(Date dateOfCircular) {
		this.dateOfCircular = dateOfCircular;
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getSmsBody() {
		return smsBody;
	}

	public void setSmsBody(String smsBody) {
		this.smsBody = smsBody;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<CircularNotiicationDetEntity> getCircularNotiicationDetEntities() {
		return circularNotiicationDetEntities;
	}

	public void setCircularNotiicationDetEntities(List<CircularNotiicationDetEntity> circularNotiicationDetEntities) {
		this.circularNotiicationDetEntities = circularNotiicationDetEntities;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Circular_Notify_Master", "CN_ID" };
	}
	
	

}
