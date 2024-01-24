package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "MM_INDENT_ITEM")
public class IndentItemEntity implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "inditemid")
    private Long inditemid;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "indentid", nullable = false)
    private IndentProcessEntity indentid;
	    
    @OneToMany(mappedBy ="indentItemEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<IndentIssueEntity> indentIssueEntities = new ArrayList<>();
	    
	@Column(name = "storeid")
    private Long storeid;
	
	@Column(name = "itemid")
	private Long itemid;
	
	@Column(name = "quantity")
	private BigDecimal quantity;
	
	@Column(name = "issuedqty")
	private BigDecimal issuedqty;
	
	@Column(name = "Remarks")
	private String Remarks;
	
	@Column(name = "Status")
	private String Status;
	
	@Column(name = "ORGID")
    private Long orgid;
	
	@Column(name = "USER_ID")
    private Long user_id;
	
	@Column(name = "LANGID")
    private Long langId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LMODDATE")
    private Date lmoddate;
    
    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    
	public Long getInditemid() {
		return inditemid;
	}

	public void setInditemid(Long inditemid) {
		this.inditemid = inditemid;
	}

	public IndentProcessEntity getIndentid() {
		return indentid;
	}

	public void setIndentid(IndentProcessEntity indentid) {
		this.indentid = indentid;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getIssuedqty() {
		return issuedqty;
	}

	public void setIssuedqty(BigDecimal issuedqty) {
		this.issuedqty = issuedqty;
	}
	
	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}
	
	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public List<IndentIssueEntity> getIndentIssueEntities() {
		return indentIssueEntities;
	}

	public void setIndentIssueEntities(List<IndentIssueEntity> indentIssueEntities) {
		this.indentIssueEntities = indentIssueEntities;
	}

	public String[] getPkValues() {
        return new String[] { "ITP", "MM_INDENT_ITEM", "inditemid" };
    }
}
