package com.abm.mainet.tradeLicense.domain;

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
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "TB_MTL_NOTICE_MAS")
public class TbMtlNoticeMas {
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	
	@Column(name = "NT_ID", nullable = false)
	private Long ntId;
	
	
	@Column(name = "NT_NO", nullable = false)
	private Long noticeNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "NT_DATE")
	private Date noticeDate;
	

	@Column(name = "NT_TYPE_ID", nullable = false)
	private Long noticeTypeId;
	
	
	@Column(name = "TRD_ID", nullable = false)
	private Long trdId;
	
	
	@Column(name = "NR_REASON", nullable = false, length = 1000)
	private String reason;
	
	@Column(name = "NT_REMARKS", nullable = false, length = 2000)
	private String remark;
	
	
	@Column(name = "NT_CLEARED", nullable = false)
	private String cleared;
	
	@Column(name = "NT_NOTICEBY", nullable = false)
	private Long noticeBy;
	
	
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
	
	public Long getNtId() {
		return ntId;
	}

	public void setNtId(Long ntId) {
		this.ntId = ntId;
	}

	public Long getNoticeNo() {
		return noticeNo;
	}

	public void setNoticeNo(Long noticeNo) {
		this.noticeNo = noticeNo;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}

	public Long getNoticeTypeId() {
		return noticeTypeId;
	}

	public void setNoticeTypeId(Long noticeTypeId) {
		this.noticeTypeId = noticeTypeId;
	}

	public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCleared() {
		return cleared;
	}

	public void setCleared(String cleared) {
		this.cleared = cleared;
	}

	public Long getNoticeBy() {
		return noticeBy;
	}

	public void setNoticeBy(Long noticeBy) {
		this.noticeBy = noticeBy;
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

	public String[] getPkValues() {
		return new String[] { "ML", "TB_MTL_NOTICE_MAS", "NT_ID" };
	}


}
