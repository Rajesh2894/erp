package com.abm.mainet.intranet.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Table(name = "tb_poll_details")
public class PollDetails {
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "poll_det_id", nullable = false, precision = 10)
	private Long id;
	
	/*@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="id")
	@JoinColumn(name = "poll_det_id")
	private Poll pollEntity = new Poll();*/
	
	///ashish
	/*@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "poll_id",  nullable = true)
	private Poll pollid;
	
	public Poll getPollid() {
		return pollid;
	}
	public void setPollid(Poll pollid) {
		this.pollid = pollid;
	}*/
	///ashish
	
	@Column(name = "POLL_NAME", length = 400)
	private String pollName;
	
	@Column(name = "POLL_DEPT_ID", precision = 10)
	private Long pollDeptId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "POLL_FROM_DATE")
	private Date pollFromDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "POLL_TO_DATE")
	private Date pollToDate;
    
	@Column(name="ORG_ID", nullable = false, precision = 10)
	private Long orgid;
    
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "USER_ID", nullable = false, precision = 10)
	private Long userId;
	
	@Column(name = "LANG_ID", nullable = false, precision = 10)
	private int langId;
	
	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date lmoddate;

    @CreatedBy
    @Column(name="created_by")
    private Long createdBy;
    
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	
	public String getPollName() {
		return pollName;
	}

	public void setPollName(String pollName) {
		this.pollName = pollName;
	}

	public Long getPollDeptId() {
		return pollDeptId;
	}

	public void setPollDeptId(Long pollDeptId) {
		this.pollDeptId = pollDeptId;
	}

	public Date getPollFromDate() {
		return pollFromDate;
	}

	public void setPollFromDate(Date pollFromDate) {
		this.pollFromDate = pollFromDate;
	}

	public Date getPollToDate() {
		return pollToDate;
	}

	public void setPollToDate(Date pollToDate) {
		this.pollToDate = pollToDate;
	}
	
/*	public Poll getPollEntity() {
		return pollEntity;
	}

	public void setPollEntity(Poll pollEntity) {
		this.pollEntity = pollEntity;
	}*/

	public static String[] getPkValues() {
		return new String[] { "HD", "tb_poll_details", "id" };
	}
	
    
}
