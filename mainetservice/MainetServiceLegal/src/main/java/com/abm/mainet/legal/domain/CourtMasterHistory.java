package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_COURT_MAST_HIST")
public class CourtMasterHistory implements Serializable {

    private static final long serialVersionUID = 1678414071426407173L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CRT_ID_H", nullable = false)
    private Long histId;

    @Column(name = "CRT_ID", nullable = false)
    private Long id;

    @Column(name = "CRT_TYPE", nullable = false)
    private Long crtType;

    @Column(name = "CRT_NAME", nullable = false)
    private String crtName;

    @Column(name = "CRT_NAME_REG", nullable = false)
    private String crtNameReg;

    @Column(name = "CRT_START_TIME", nullable = false)
    private String crtStartTime;
 
    @Column(name = "CRT_END_TIME", nullable = false)
    private String crtEndTime;
    
    @Column(name = "CRT_PHONE_NO", nullable = false)
    private String crtPhoneNo;

    @Column(name = "CRT_EMAIL_ID", nullable = false)
    private String crtEmailId;

    @Column(name = "CRT_ADDRESS", nullable = false)
    private String crtAddress;

    @Column(name = "CRT_STATUS", nullable = false)
    private String crtStatus;

    @Column(name = "H_STATUS", nullable = false)
    private String status;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

  

    public Long getHistId() {
		return histId;
	}



	public void setHistId(Long histId) {
		this.histId = histId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getCrtType() {
		return crtType;
	}



	public void setCrtType(Long crtType) {
		this.crtType = crtType;
	}



	public String getCrtName() {
		return crtName;
	}



	public void setCrtName(String crtName) {
		this.crtName = crtName;
	}



	public String getCrtNameReg() {
		return crtNameReg;
	}



	public void setCrtNameReg(String crtNameReg) {
		this.crtNameReg = crtNameReg;
	}



	public String getCrtStartTime() {
		return crtStartTime;
	}



	public void setCrtStartTime(String crtStartTime) {
		this.crtStartTime = crtStartTime;
	}



	public String getCrtEndTime() {
		return crtEndTime;
	}



	public void setCrtEndTime(String crtEndTime) {
		this.crtEndTime = crtEndTime;
	}



	public String getCrtPhoneNo() {
		return crtPhoneNo;
	}



	public void setCrtPhoneNo(String crtPhoneNo) {
		this.crtPhoneNo = crtPhoneNo;
	}



	public String getCrtEmailId() {
		return crtEmailId;
	}



	public void setCrtEmailId(String crtEmailId) {
		this.crtEmailId = crtEmailId;
	}



	public String getCrtAddress() {
		return crtAddress;
	}



	public void setCrtAddress(String crtAddress) {
		this.crtAddress = crtAddress;
	}



	public String getCrtStatus() {
		return crtStatus;
	}



	public void setCrtStatus(String crtStatus) {
		this.crtStatus = crtStatus;
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



	public Long getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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



	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_COURT_MAST_HIST", "CRT_ID_H" };
    }
}
