package com.abm.mainet.cfc.objection.dto;

import java.io.Serializable;
import java.util.Date;

public class NoticeMasterDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2139036909012519296L;

    private Long notId;

    private Long apmApplicationId;

    private Long createdBy;

    private Date creationDate;

    private Long dpDeptid;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Date notAcceptdt;

    private Date notDate;

    private Date notDuedt;

    private String notNo;

    private String refNo;

    private String notRemarks;

    private Long notSignedby;

    private Date notSigneddt;

    private Long notTyp;

    private Long orgId;

    private Long smServiceId;

    private Long updatedBy;

    private Date updatedDate;
    
    private String flatNo;

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
