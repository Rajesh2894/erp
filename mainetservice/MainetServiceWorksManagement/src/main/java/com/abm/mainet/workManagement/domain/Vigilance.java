package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */

@Entity
@Table(name = "TB_WMS_VIGILANCE")
public class Vigilance implements Serializable {

	private static final long serialVersionUID = -1150477235813206187L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "VI_ID", nullable = false)
	private Long vigilanceId;
	
	 @Column(name = "PROJ_ID", nullable = false)
	 private Long projId;
	 
	  @Column(name = "WORK_ID", nullable = false)
	 private Long workId;

	@Column(name = "VI_STATUS", nullable = false)
	private String status;

	@Column(name = "VI_REFTYPE", nullable = false)
	private String referenceType;

	@Column(name = "VI_MEMOTYPE", nullable = false)
	private String memoType;

	@Column(name = "VI_MEMODESC", nullable = false)
	private String memoDescription;

	@Column(name = "VI_REFNO", nullable = true)
	private String referenceNumber;

	@Column(name = "VI_MEMODATE", nullable = false)
	private Date memoDate;

	@Column(name = "VI_INSPECTIONDATE", nullable = false)
	private Date inspectionDate;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	public Long getVigilanceId() {
		return vigilanceId;
	}

	public void setVigilanceId(Long vigilanceId) {
		this.vigilanceId = vigilanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getMemoType() {
		return memoType;
	}

	public void setMemoType(String memoType) {
		this.memoType = memoType;
	}

	public String getMemoDescription() {
		return memoDescription;
	}

	public void setMemoDescription(String memoDescription) {
		this.memoDescription = memoDescription;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getMemoDate() {
		return memoDate;
	}

	public void setMemoDate(Date memoDate) {
		this.memoDate = memoDate;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
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

	public Long getProjId() {
        return projId;
    }

      public void setProjId(Long projId) {
        this.projId = projId;
      }
 
       public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_VIGILANCE", "VI_ID" };
	}

}
