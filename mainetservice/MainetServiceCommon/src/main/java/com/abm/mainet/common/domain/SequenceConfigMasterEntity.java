package com.abm.mainet.common.domain;

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

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "TB_SEQ_CONFIGMASTER")
public class SequenceConfigMasterEntity implements Serializable {

	private static final long serialVersionUID = 5476172742804405035L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SEQ_CONFID", nullable = false)
	private Long seqConfigId;

	@OneToMany(mappedBy = "seqConfigMasterEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SequenceConfigDetEntity> configDetEntities = new ArrayList<>();

	@Column(name = "SEQ_NAME", nullable = false)
	private Long seqName;

	@Column(name = "CAT_ID", nullable = false)
	private Long catId;

	@Column(name = "DEPT_ID", nullable = false)
	private Long deptId;

	@Column(name = "SEQ_TYP", nullable = false)
	private Long seqType;

	@Column(name = "SEQ_SEP", nullable = true)
	private Long seqSep;

	@Column(name = "SEQ_LENTH", nullable = false, length = 2)
	private Long seqLength;

	@Column(name = "CUST_SEQ", nullable = true, length = 1)
	private String custSeq;

	@Column(name = "SEQ_FRMNO", nullable = false)
	private Long seqFrmNo;

	@Column(name = "SEQ_STATUS", nullable = false, length = 1)
	private String seqStatus;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "FROM_DATE", nullable = false)
	private Date fromDate;

	@Column(name = "DUE_DATE", nullable = true)
	private Date endDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getSeqConfigId() {
		return seqConfigId;
	}

	public void setSeqConfigId(Long seqConfigId) {
		this.seqConfigId = seqConfigId;
	}

	public List<SequenceConfigDetEntity> getConfigDetEntities() {
		return configDetEntities;
	}

	public void setConfigDetEntities(List<SequenceConfigDetEntity> configDetEntities) {
		this.configDetEntities = configDetEntities;
	}

	public Long getSeqName() {
		return seqName;
	}

	public void setSeqName(Long seqName) {
		this.seqName = seqName;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getSeqType() {
		return seqType;
	}

	public void setSeqType(Long seqType) {
		this.seqType = seqType;
	}

	public Long getSeqSep() {
		return seqSep;
	}

	public void setSeqSep(Long seqSep) {
		this.seqSep = seqSep;
	}

	public Long getSeqLength() {
		return seqLength;
	}

	public void setSeqLength(Long seqLength) {
		this.seqLength = seqLength;
	}

	public String getCustSeq() {
		return custSeq;
	}

	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}

	public Long getSeqFrmNo() {
		return seqFrmNo;
	}

	public void setSeqFrmNo(Long seqFrmNo) {
		this.seqFrmNo = seqFrmNo;
	}

	public String getSeqStatus() {
		return seqStatus;
	}

	public void setSeqStatus(String seqStatus) {
		this.seqStatus = seqStatus;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public static String[] getPkValues() {
		return new String[] { "COM", "TB_SEQ_CONFIGMASTER", "SEQ_CONFID" };
	}

	@Override
	public String toString() {
		return "SequenceConfigMasterEntity [seqConfigId=" + seqConfigId + ", configDetEntities=" + configDetEntities
				+ ", seqName=" + seqName + ", catId=" + catId + ", deptId=" + deptId + ", seqType=" + seqType
				+ ", seqSep=" + seqSep + ", seqLength=" + seqLength + ", custSeq=" + custSeq + ", seqFrmNo=" + seqFrmNo
				+ ", seqStatus=" + seqStatus + ", orgId=" + orgId + ", creationDate=" + creationDate + ", createdBy="
				+ createdBy + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fromDate=" + fromDate
				+ ", endDate=" + endDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

}
