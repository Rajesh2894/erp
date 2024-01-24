package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * The persistent class for the tb_lgl_advocate_mas database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_ADVOCATE_MAS")
public class AdvocateMaster implements Serializable {

    private static final long serialVersionUID = 5126257983446018888L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADV_ID", unique = true, nullable = false)
    private Long advId;

    @Column(name = "ADV_ADDRESS", length = 200)
    private String advAddress;
    
    @Column(name = "OFFICE_ADDRESS", length = 200)
    private String advOfficeAddress;
    

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_APPFROMDATE", nullable = false)
    private Date advAppfromdate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_APPTODATE", nullable = false)
    private Date advApptodate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ADV_DOB", nullable = true)
    private Date advDob;

    @Column(name = "ADV_EMAIL", length = 100)
    private String advEmail;

    @Column(name = "ADV_EXPERIENCE", nullable = false)
    private BigDecimal advExperience;

    @Column(name = "ADV_FIRST_NM", nullable = false, length = 100)
    private String advFirstNm;

    @Column(name = "ADV_GEN", nullable = true)
    private Long advGen;

    @Column(name = "ADV_LAST_NM", nullable = false, length = 100)
    private String advLastNm;

    @Column(name = "ADV_MARITALSTATUS", length = 1)
    private String advMaritalstatus;

    @Column(name = "ADV_MIDDLE_NM", length = 100)
    private String advMiddleNm;

    @Column(name = "ADV_MOBILE", nullable = false, length = 30)
    private String advMobile;

    @Column(name = "ADV_PANNO", nullable = false, length = 10)
    private String advPanno;

    @Column(name = "ADV_STATUS", nullable = false, length = 1)
    private String advStatus;

    @Column(name = "ADV_UID", nullable = false, length = 28)
    private String advUid;

    @Column(name = "ADV_FEETYPE", nullable = false, length = 2)
    private Long advFeeType;

    @Column(name = "ADV_FEEAMT")
    private BigDecimal advFeeAmt;
    
    @Column(name = "CHAMBER_NO", nullable = false, length = 30)
    private String advChamberNo;
    
    @Column(name = "APM_APPLICATION_ID", nullable = true)
    private Long applicationId;
    

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
  
    @Column(name = "adv_barCouncilNo", length = 16)
    private String adv_barCouncilNo;
   
    @Column(name = "adv_courtNameId", nullable = true)
    private Long adv_courtNameId;
    
    @Column(name = "adv_advocateTypeId", nullable = true)
    private Long adv_advocateTypeId;
    
    @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tbLglAdvMaster", cascade = CascadeType.ALL)
	private List<AdvocateEducationDetails> advocateEducationDetails = new ArrayList<>();
    

    public AdvocateMaster() {
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

    public BigDecimal getAdvExperience() {
        return advExperience;
    }

    public void setAdvExperience(BigDecimal advExperience) {
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

    public Long getAdvFeeType() {
        return advFeeType;
    }

    public void setAdvFeeType(Long advFeeType) {
        this.advFeeType = advFeeType;
    }

    public BigDecimal getAdvFeeAmt() {
        return advFeeAmt;
    }

    public void setAdvFeeAmt(BigDecimal advFeeAmt) {
        this.advFeeAmt = advFeeAmt;
    }
    

    public String getAdv_barCouncilNo() {
		return adv_barCouncilNo;
	}

	public void setAdv_barCouncilNo(String adv_barCouncilNo) {
		this.adv_barCouncilNo = adv_barCouncilNo;
	}

	public Long getAdv_courtNameId() {
		return adv_courtNameId;
	}

	public void setAdv_courtNameId(Long adv_courtNameId) {
		this.adv_courtNameId = adv_courtNameId;
	}

	public Long getAdv_advocateTypeId() {
		return adv_advocateTypeId;
	}

	public void setAdv_advocateTypeId(Long adv_advocateTypeId) {
		this.adv_advocateTypeId = adv_advocateTypeId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}


	public List<AdvocateEducationDetails> getAdvocateEducationDetails() {
		return advocateEducationDetails;
	}

	public void setAdvocateEducationDetails(List<AdvocateEducationDetails> advocateEducationDetails) {
		this.advocateEducationDetails = advocateEducationDetails;
	}

	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_ADVOCATE_MAS", "ADV_ID" };
    }
}