package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * The persistent class for the tb_lgl_advocate_mas_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_ADVOCATE_MAS_HIST")
public class AdvocateMasterHistory implements Serializable {

    private static final long serialVersionUID = -7079532688975648377L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADV_ID_H", unique = true, nullable = false)
    private Long advIdHist;

    @Column(name = "ADV_ID")
    private Long advId;

    @Column(name = "ADV_ADDRESS", length = 200)
    private String advAddress;
    
    @Column(name = "OFFICE_ADDRESS", length = 200)
    private String advOfficeAddress;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_APPFROMDATE")
    private Date advAppfromdate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_APPTODATE")
    private Date advApptodate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_DOB")
    private Date advDob;

    @Column(name = "ADV_EMAIL", length = 100)
    private String advEmail;

    @Column(name = "ADV_EXPERIENCE")
    private Long advExperience;

    @Column(name = "ADV_FIRST_NM", length = 100)
    private String advFirstNm;

    @Column(name = "ADV_GEN")
    private Long advGen;

    @Column(name = "ADV_LAST_NM", length = 100)
    private String advLastNm;

    @Column(name = "ADV_MARITALSTATUS", length = 1)
    private String advMaritalstatus;

    @Column(name = "ADV_MIDDLE_NM", length = 100)
    private String advMiddleNm;

    @Column(name = "ADV_MOBILE", length = 30)
    private String advMobile;

    @Column(name = "ADV_PANNO", length = 10)
    private String advPanno;

    @Column(name = "ADV_STATUS", length = 1)
    private String advStatus;

    @Column(name = "ADV_UID", length = 28)
    private String advUid;

    @Column(name = "ADV_FEETYPE")
    private String advFeeType;

    @Column(name = "ADV_FEEAMT")
    private BigDecimal advFeeAmt;
    
    @Column(name = "CHAMBER_NO", nullable = false, length = 30)
    private String advChamberNo;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;
    
    @Column(name = "APM_APPLICATION_ID", nullable = true)
    private Long applicationId;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public AdvocateMasterHistory() {
    }

    public Long getAdvId() {
        return this.advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public String getAdvAddress() {
        return this.advAddress;
    }

    public void setAdvAddress(String advAddress) {
        this.advAddress = advAddress;
    }

    public Date getAdvAppfromdate() {
        return this.advAppfromdate;
    }

    public String getAdvOfficeAddress() {
		return advOfficeAddress;
	}

	public void setAdvOfficeAddress(String advOfficeAddress) {
		this.advOfficeAddress = advOfficeAddress;
	}

	public String getAdvChamberNo() {
		return advChamberNo;
	}

	public void setAdvChamberNo(String advChamberNo) {
		this.advChamberNo = advChamberNo;
	}

	public void setAdvAppfromdate(Date advAppfromdate) {
        this.advAppfromdate = advAppfromdate;
    }

    public Date getAdvApptodate() {
        return this.advApptodate;
    }

    public void setAdvApptodate(Date advApptodate) {
        this.advApptodate = advApptodate;
    }

    public Date getAdvDob() {
        return this.advDob;
    }

    public void setAdvDob(Date advDob) {
        this.advDob = advDob;
    }

    public String getAdvEmail() {
        return this.advEmail;
    }

    public void setAdvEmail(String advEmail) {
        this.advEmail = advEmail;
    }

    public Long getAdvExperience() {
        return this.advExperience;
    }

    public void setAdvExperience(Long advExperience) {
        this.advExperience = advExperience;
    }

    public String getAdvFirstNm() {
        return this.advFirstNm;
    }

    public void setAdvFirstNm(String advFirstNm) {
        this.advFirstNm = advFirstNm;
    }

    public Long getAdvGen() {
        return this.advGen;
    }

    public void setAdvGen(Long advGen) {
        this.advGen = advGen;
    }

    public String getAdvLastNm() {
        return this.advLastNm;
    }

    public void setAdvLastNm(String advLastNm) {
        this.advLastNm = advLastNm;
    }

    public String getAdvMaritalstatus() {
        return this.advMaritalstatus;
    }

    public void setAdvMaritalstatus(String advMaritalstatus) {
        this.advMaritalstatus = advMaritalstatus;
    }

    public String getAdvMiddleNm() {
        return this.advMiddleNm;
    }

    public void setAdvMiddleNm(String advMiddleNm) {
        this.advMiddleNm = advMiddleNm;
    }

    public String getAdvMobile() {
        return this.advMobile;
    }

    public void setAdvMobile(String advMobile) {
        this.advMobile = advMobile;
    }

    public String getAdvPanno() {
        return this.advPanno;
    }

    public void setAdvPanno(String advPanno) {
        this.advPanno = advPanno;
    }

    public String getAdvStatus() {
        return this.advStatus;
    }

    public void setAdvStatus(String advStatus) {
        this.advStatus = advStatus;
    }

    public String getAdvUid() {
        return this.advUid;
    }

    public void setAdvUid(String advUid) {
        this.advUid = advUid;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getAdvIdHist() {
        return advIdHist;
    }

    public void setAdvIdHist(Long advIdHist) {
        this.advIdHist = advIdHist;
    }

    public String getAdvFeeType() {
        return advFeeType;
    }

    public void setAdvFeeType(String advFeeType) {
        this.advFeeType = advFeeType;
    }

    public BigDecimal getAdvFeeAmt() {
        return advFeeAmt;
    }

    public void setAdvFeeAmt(BigDecimal advFeeAmt) {
        this.advFeeAmt = advFeeAmt;
    }

    public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_ADVOCATE_MAS_HIST", "ADV_ID_H" };
    }
}