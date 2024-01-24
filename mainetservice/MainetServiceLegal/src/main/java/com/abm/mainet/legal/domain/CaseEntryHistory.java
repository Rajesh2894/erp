package com.abm.mainet.legal.domain;

import java.io.Serializable;
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
 * The persistent class for the tb_lgl_case_mas_hist database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASE_MAS_HIST")
public class CaseEntryHistory implements Serializable {

    private static final long serialVersionUID = 2453114276535935253L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CSE_ID_H", unique = true, nullable = false)
    private Long cseIdH;

    @Column(name = "CRT_ID")
    private Long crtId;

    @Column(name = "CSE_CASE_STATUS_ID")
    private Long cseCaseStatusId;

    @Column(name = "CSE_CAT_ID")
    private Long cseCatId;

    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_DATE")
    private Date cseDate;

    @Column(name = "CSE_DEPTID")
    private Long cseDeptid;

    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_ENTRY_DT")
    private Date cseEntryDt;

    @Column(name = "CSE_ID")
    private Long cseId;

    @Column(name = "CSE_MATDET_1", length = 1000)
    private String cseMatdet1;

    @Column(name = "CSE_NAME", length = 500)
    private String cseName;

    @Column(name = "CSE_PEIC_DROA")
    private Long csePeicDroa;

    @Column(name = "CSE_REFERENCENO", length = 30)
    private String cseReferenceno;

    @Column(name = "CSE_REFSUIT_NO", length = 20)
    private String cseRefsuitNo;

    @Column(name = "CSE_REMARKS", length = 1000)
    private String cseRemarks;

    @Column(name = "CSE_SECT_APPL", length = 1000)
    private String cseSectAppl;

    @Column(name = "CSE_SUBCAT_ID")
    private Long cseSubcatId;

    @Column(name = "CSE_SUIT_NO", length = 20)
    private String cseSuitNo;

    @Column(name = "CSE_TYP_ID", nullable = false)
    private Long cseTypId1;
    
    @Column(name = "CSE_SUBTYP_ID", nullable = false)
    private Long cseTypId2;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;
    
    @Column(name = "CSE_FILING_NO", nullable = true, length = 16)
    private String cseFilingNumber;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_FILING_DATE", nullable = true)
    private Date cseFilingDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "CSE_OFFICE_INCHARGE")
    private Long officeIncharge;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "CSE_OIC_APPOINT_DATE")
    private Date appointmentDate;
    
    @Column(name = "CSE_NO", nullable = false, length = 20)
    private String caseNo;

    public Long getCseIdH() {
        return cseIdH;
    }

    public void setCseIdH(Long cseIdH) {
        this.cseIdH = cseIdH;
    }

    public Long getCrtId() {
        return crtId;
    }

    public void setCrtId(Long crtId) {
        this.crtId = crtId;
    }

    public Long getCseCaseStatusId() {
        return cseCaseStatusId;
    }

    public void setCseCaseStatusId(Long cseCaseStatusId) {
        this.cseCaseStatusId = cseCaseStatusId;
    }

    public Long getCseCatId() {
        return cseCatId;
    }

    public void setCseCatId(Long cseCatId) {
        this.cseCatId = cseCatId;
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

    public Long getCseId() {
        return cseId;
    }

    public void setCseId(Long cseId) {
        this.cseId = cseId;
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

    public Long getCseSubcatId() {
        return cseSubcatId;
    }

    public void setCseSubcatId(Long cseSubcatId) {
        this.cseSubcatId = cseSubcatId;
    }

    public String getCseSuitNo() {
        return cseSuitNo;
    }

    public void setCseSuitNo(String cseSuitNo) {
        this.cseSuitNo = cseSuitNo;
    }


    public Long getCseTypId1() {
		return cseTypId1;
	}

	public void setCseTypId1(Long cseTypId1) {
		this.cseTypId1 = cseTypId1;
	}

	public Long getCseTypId2() {
		return cseTypId2;
	}

	public void setCseTypId2(Long cseTypId2) {
		this.cseTypId2 = cseTypId2;
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

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASE_MAS_HIST", "CSE_ID_H" };
    }

	
}