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
 * The persistent class for the tb_lgl_caseparawise_remark database table.
 * 
 */
@Entity
@Table(name = "TB_LGL_CASEPARAWISE_REMARK")
public class ParawiseRemark implements Serializable {

    private static final long serialVersionUID = -7170601694909069408L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAR_ID")
    private Long parId;

    @Column(name = "PAR_PAGNO")
    private String parPagno;

    @Column(name = "PAR_SECTIONNO")
    private String parSectionno;

    @Column(name = "PAR_COMMENT")
    private String parComment;

    @Column(name = "par_uad_remark")
    private String parUadRemark;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CSE_ID")
    private Long caseId;

    @Column(name = "ATD_ID")
    private Long atdId;
    
    @Column(name = "REF_CASE_NO")
    private String refCaseNo;
    
    @Column(name = "STATUS")
    private String status;

  

	public ParawiseRemark() {
    }

    public Long getParId() {
        return this.parId;
    }

    public void setParId(Long parId) {
        this.parId = parId;
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

    public String getParComment() {
        return this.parComment;
    }

    public void setParComment(String parComment) {
        this.parComment = parComment;
    }

    public String getParPagno() {
        return this.parPagno;
    }

    public void setParPagno(String parPagno) {
        this.parPagno = parPagno;
    }

    public String getParSectionno() {
        return this.parSectionno;
    }

    public void setParSectionno(String parSectionno) {
        this.parSectionno = parSectionno;
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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public Long getAtdId() {
        return atdId;
    }

    public void setAtdId(Long atdId) {
        this.atdId = atdId;
    }

    public String getParUadRemark() {
        return parUadRemark;
    }

    public void setParUadRemark(String parUadRemark) {
        this.parUadRemark = parUadRemark;
    }
    

    public String getRefCaseNo() {
		return refCaseNo;
	}

	public void setRefCaseNo(String refCaseNo) {
		this.refCaseNo = refCaseNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getPkValues() {
        return new String[] { "LGL", "TB_LGL_CASEPARAWISE_REMARK", "PAR_ID" };
    }

}