package com.abm.mainet.legal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_lgl_case_mas database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASE_MAS")
public class CaseEntry implements Serializable {

    private static final long serialVersionUID = -2046799059123895165L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CSE_ID", unique = true, nullable = false)
    private Long cseId;

    @Column(name = "CRT_ID", nullable = false)
    private Long crtId;

    @Column(name = "ADV_ID")
    private Long advId;

    @Column(name = "CSE_CASE_STATUS_ID", nullable = false)
    private Long cseCaseStatusId;

    @Column(name = "CSE_CAT_ID", nullable = false)
    private Long cseCatId1;

    @Column(name = "CSE_SUBCAT_ID", nullable = false)
    private Long cseCatId2;

    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_DATE", nullable = false)
    private Date cseDate;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "ORDER_DATE")
    private Date orderDate;

    @Column(name = "CSE_DEPTID", nullable = false)
    private Long cseDeptid;

    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_ENTRY_DT", nullable = false)
    private Date cseEntryDt;

    @Column(name = "CSE_MATDET_1", nullable = false, length = 1000)
    private String cseMatdet1;

    @Column(name = "CSE_NAME", nullable = false, length = 500)
    private String cseName;

    @Column(name = "CSE_STAT", nullable = true)
    private Long cseState;

    @Column(name = "CSE_CITY", nullable = true)
    private String cseCity;

    @Column(name = "CSE_PEIC_DROA", nullable = false)
    private Long csePeicDroa;

    @Column(name = "CSE_REFERENCENO", length = 30)
    private String cseReferenceno;

    @Column(name = "CSE_REFSUIT_NO", length = 20)
    private String cseRefsuitNo;

    @Column(name = "CSE_REMARKS", nullable = false, length = 1000)
    private String cseRemarks;

    @Column(name = "CSE_SECT_APPL", nullable = true, length = 1000)
    private String cseSectAppl;

    @Column(name = "CSE_SUIT_NO", nullable = false, length = 20)
    private String cseSuitNo;

    @Column(name = "CSE_TYP_ID", nullable = false)
    private Long cseTypId;

    @Column(name = "LOC_ID", nullable = true)
    private Long locId;

    @Column(name = "ORGID")
    private Long orgid;
    
    @Column(name = "CONCERNED_ULB", nullable = true)
    private Long concernedUlb;
  

    @Column(name = "CSE_FILING_NO", nullable = true, length = 16)
    private String cseFilingNumber;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_FILING_DATE", nullable = true)
    private Date cseFilingDate;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    // bi-directional many-to-one association to TbLglCasePddetail
    @JsonIgnore
    @OneToMany(mappedBy = "tbLglCaseMa", cascade = CascadeType.ALL)
    private List<CaseEntryDetail> tbLglCasePddetails;

    @JsonIgnore
    @OneToMany(mappedBy = "tbLglCaseMa", cascade = CascadeType.ALL)
    private List<OfficerInchargeDetails> tbLglCaseOICdetails;

    public List<OfficerInchargeDetails> getTbLglCaseOICdetails() {
        return tbLglCaseOICdetails;
    }

    public void setTbLglCaseOICdetails(List<OfficerInchargeDetails> tbLglCaseOICdetails) {
        this.tbLglCaseOICdetails = tbLglCaseOICdetails;
    }

    @Column(name = "CSE_OFFICE_INCHARGE", nullable = true)
    private Long officeIncharge;

    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_OIC_APPOINT_DATE", nullable = true)
    private Date appointmentDate;

    @Column(name = "ORDER_NO", nullable = true, length = 200)
    private String orderNo;

    @Column(name = "ADDRESS", nullable = true, length = 200)
    private String address;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tbCaseEntry", cascade = CascadeType.ALL)
    private Set<CaseEntryArbitoryFee> tbArbitoryFee;
    
    @Column(name = "CSE_NO", nullable = false, length = 20)
    private String caseNo;
    
    @Column(name = "CSE_PHYSICAL_NO", nullable = false, length = 50)
    private String physicalNo;

    public Set<CaseEntryArbitoryFee> getTbArbitoryFee() {
        return tbArbitoryFee;
    }

    public void setTbArbitoryFee(Set<CaseEntryArbitoryFee> tbArbitoryFee) {
        this.tbArbitoryFee = tbArbitoryFee;
    }

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }

    public Long getCseCaseStatusId() {
        return cseCaseStatusId;
    }

    public void setCseCaseStatusId(Long cseCaseStatusId) {
        this.cseCaseStatusId = cseCaseStatusId;
    }

    public Date getCseDate() {
        return cseDate;
    }

    public void setCseDate(Date cseDate) {
        this.cseDate = cseDate;
    }

    public Long getCseDeptid() {
        return cseDeptid;
    }

    public void setCseDeptid(Long cseDeptid) {
        this.cseDeptid = cseDeptid;
    }

    public Date getCseEntryDt() {
        return cseEntryDt;
    }

    public void setCseEntryDt(Date cseEntryDt) {
        this.cseEntryDt = cseEntryDt;
    }

    public String getCseMatdet1() {
        return cseMatdet1;
    }

    public void setCseMatdet1(String cseMatdet1) {
        this.cseMatdet1 = cseMatdet1;
    }

    public String getCseName() {
        return cseName;
    }

    public void setCseName(String cseName) {
        this.cseName = cseName;
    }

    public Long getCseState() {
        return cseState;
    }

    public void setCseState(Long cseState) {
        this.cseState = cseState;
    }

    public String getCseCity() {
        return cseCity;
    }

    public void setCseCity(String cseCity) {
        this.cseCity = cseCity;
    }

    public Long getCsePeicDroa() {
        return csePeicDroa;
    }

    public void setCsePeicDroa(Long csePeicDroa) {
        this.csePeicDroa = csePeicDroa;
    }

    public String getCseReferenceno() {
        return cseReferenceno;
    }

    public void setCseReferenceno(String cseReferenceno) {
        this.cseReferenceno = cseReferenceno;
    }

    public String getCseRefsuitNo() {
        return cseRefsuitNo;
    }

    public void setCseRefsuitNo(String cseRefsuitNo) {
        this.cseRefsuitNo = cseRefsuitNo;
    }

    public String getCseRemarks() {
        return cseRemarks;
    }

    public void setCseRemarks(String cseRemarks) {
        this.cseRemarks = cseRemarks;
    }

    public String getCseSectAppl() {
        return cseSectAppl;
    }

    public void setCseSectAppl(String cseSectAppl) {
        this.cseSectAppl = cseSectAppl;
    }

    public String getCseSuitNo() {
        return cseSuitNo;
    }

    public void setCseSuitNo(String cseSuitNo) {
        this.cseSuitNo = cseSuitNo;
    }

    public Long getCseTypId() {
        return cseTypId;
    }

    public void setCseTypId(Long cseTypId) {
        this.cseTypId = cseTypId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public Long getCseCatId1() {
        return cseCatId1;
    }

    public void setCseCatId1(Long cseCatId1) {
        this.cseCatId1 = cseCatId1;
    }

    public Long getCseCatId2() {
        return cseCatId2;
    }

    public void setCseCatId2(Long cseCatId2) {
        this.cseCatId2 = cseCatId2;
    }

    public List<CaseEntryDetail> getTbLglCasePddetails() {
        return tbLglCasePddetails;
    }

    public void setTbLglCasePddetails(List<CaseEntryDetail> tbLglCasePddetails) {
        this.tbLglCasePddetails = tbLglCasePddetails;
    }

    /**
     * @return the officeIncharge
     */
    public Long getOfficeIncharge() {
        return officeIncharge;
    }

    /**
     * @param officeIncharge the officeIncharge to set
     */
    public void setOfficeIncharge(Long officeIncharge) {
        this.officeIncharge = officeIncharge;
    }

    /**
     * @return the appointmentDate
     */
    public Date getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @param appointmentDate the appointmentDate to set
     */
    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    

    /**
	 * @return the concernedUlb
	 */
	public Long getConcernedUlb() {
		return concernedUlb;
	}

	/**
	 * @param concernedUlb the concernedUlb to set
	 */
	public void setConcernedUlb(Long concernedUlb) {
		this.concernedUlb = concernedUlb;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getCaseNo() {
		return caseNo;
	}

	public void setCaseNo(String caseNo) {
		this.caseNo = caseNo;
	}


	public String getCseFilingNumber() {
		return cseFilingNumber;
	}

	public void setCseFilingNumber(String cseFilingNumber) {
		this.cseFilingNumber = cseFilingNumber;
	}

	public Date getCseFilingDate() {
		return cseFilingDate;
	}

	public void setCseFilingDate(Date cseFilingDate) {
		this.cseFilingDate = cseFilingDate;
	}

	public String getPhysicalNo() {
		return physicalNo;
	}

	public void setPhysicalNo(String physicalNo) {
		this.physicalNo = physicalNo;
	}

	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASE_MAS", "CSE_ID" };
    }

	

}