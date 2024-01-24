package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_INDENT_ISSUE")
public class IndentIssueEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "indissuemid")
	private Long indissuemid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indentid", nullable = false)
	private IndentProcessEntity indentid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inditemid", nullable = false)
	private IndentItemEntity indentItemEntity;

	@Column(name = "itemid")
	private Long itemid;

	@Column(name = "binlocation")
	private Long binlocation;

	@Column(name = "itemno")
	private String itemno;

	@Column(name = "issueqty")
	private Long issueqty;

	@Column(name = "isreturned")
	private String isreturned;

	@Column(name = "dreturnitem")
	private Long dreturnitem;

	@Column(name = "returnqty")
	private Long returnqty;

	@Column(name = "Status")
	private String Status;

	@Column(name = "ORGID")
	private Long ORGID;

	@Column(name = "USER_ID")
	private Long USER_ID;

	@Column(name = "LANGID")
	private Long LANGID;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
	private Date lmoddate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String LG_IP_MAC;

	@Column(name = "LG_IP_MAC_UPD")
	private String LG_IP_MAC_UPD;

	public Long getIndissuemid() {
		return indissuemid;
	}

	public void setIndissuemid(Long indissuemid) {
		this.indissuemid = indissuemid;
	}

	public IndentProcessEntity getIndentid() {
		return indentid;
	}

	public void setIndentid(IndentProcessEntity indentid) {
		this.indentid = indentid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getBinlocation() {
		return binlocation;
	}

	public void setBinlocation(Long binlocation) {
		this.binlocation = binlocation;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public Long getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(Long issueqty) {
		this.issueqty = issueqty;
	}

	public String getIsreturned() {
		return isreturned;
	}

	public void setIsreturned(String isreturned) {
		this.isreturned = isreturned;
	}

	public Long getDreturnitem() {
		return dreturnitem;
	}

	public void setDreturnitem(Long dreturnitem) {
		this.dreturnitem = dreturnitem;
	}

	public Long getReturnqty() {
		return returnqty;
	}

	public void setReturnqty(Long returnqty) {
		this.returnqty = returnqty;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getORGID() {
		return ORGID;
	}

	public void setORGID(Long oRGID) {
		ORGID = oRGID;
	}

	public Long getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(Long uSER_ID) {
		USER_ID = uSER_ID;
	}

	public Long getLANGID() {
		return LANGID;
	}

	public void setLANGID(Long lANGID) {
		LANGID = lANGID;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	public String getLG_IP_MAC() {
		return LG_IP_MAC;
	}

	public void setLG_IP_MAC(String lG_IP_MAC) {
		LG_IP_MAC = lG_IP_MAC;
	}

	public String getLG_IP_MAC_UPD() {
		return LG_IP_MAC_UPD;
	}

	public void setLG_IP_MAC_UPD(String lG_IP_MAC_UPD) {
		LG_IP_MAC_UPD = lG_IP_MAC_UPD;
	}

	public IndentItemEntity getIndentItemEntity() {
		return indentItemEntity;
	}

	public void setIndentItemEntity(IndentItemEntity indentItemEntity) {
		this.indentItemEntity = indentItemEntity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getPkValues() {

		return new String[] { "ITP", "MM_INDENT_ISSUE", "indentid" };
	}

}