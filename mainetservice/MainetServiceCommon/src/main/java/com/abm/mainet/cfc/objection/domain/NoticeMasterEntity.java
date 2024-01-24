package com.abm.mainet.cfc.objection.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_notice_mas")
public class NoticeMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "NOT_ID")
    private Long notId;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Column(name = "DP_DEPTID")
    private Long dpDeptid;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "NOT_ACCEPTDT")
    private Date notAcceptdt;

    @Column(name = "NOT_DATE")
    private Date notDate;

    @Column(name = "NOT_DUEDT")
    private Date notDuedt;

    @Column(name = "NOT_NO")
    private String notNo;

    @Column(name = "NOT_REF_NO")
    private String refNo;

    @Column(name = "NOT_REMARKS")
    private String notRemarks;

    @Column(name = "NOT_SIGNEDBY")
    private Long notSignedby;

    @Column(name = "NOT_SIGNEDDT")
    private Date notSigneddt;

    @Column(name = "NOT_TYP")
    private Long notTyp;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "SM_SERVICE_ID")
    private Long smServiceId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "FLAT_NO")
    private String flatNo;

    public NoticeMasterEntity() {
    }

    public String[] getPkValues() {
        return new String[] { "COM", "tb_notice_mas", "NOT_ID" };
    }

    public Long getNotId() {
        return notId;
    }

    public void setNotId(Long notId) {
        this.notId = notId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(Long dpDeptid) {
        this.dpDeptid = dpDeptid;
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

    public Date getNotAcceptdt() {
        return notAcceptdt;
    }

    public void setNotAcceptdt(Date notAcceptdt) {
        this.notAcceptdt = notAcceptdt;
    }

    public Date getNotDate() {
        return notDate;
    }

    public void setNotDate(Date notDate) {
        this.notDate = notDate;
    }

    public Date getNotDuedt() {
        return notDuedt;
    }

    public void setNotDuedt(Date notDuedt) {
        this.notDuedt = notDuedt;
    }

    public String getNotRemarks() {
        return notRemarks;
    }

    public void setNotRemarks(String notRemarks) {
        this.notRemarks = notRemarks;
    }

    public Long getNotSignedby() {
        return notSignedby;
    }

    public void setNotSignedby(Long notSignedby) {
        this.notSignedby = notSignedby;
    }

    public Date getNotSigneddt() {
        return notSigneddt;
    }

    public void setNotSigneddt(Date notSigneddt) {
        this.notSigneddt = notSigneddt;
    }

    public Long getNotTyp() {
        return notTyp;
    }

    public void setNotTyp(Long notTyp) {
        this.notTyp = notTyp;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
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

    public String getNotNo() {
        return notNo;
    }

    public void setNotNo(String notNo) {
        this.notNo = notNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

    
}
