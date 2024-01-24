package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lalit.Prusti
 * @since 04 Jun 2016
 */

public class ChangeOfUsageDTO implements Serializable {

    private static final long serialVersionUID = -1648536797748240567L;

    private long cisId;

    private Long csIdn;

    private String statusofwork;

    private Date dateofcomp;

    private Long plumId;

    private String remark;

    private String useType;

    private Long orgId;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    private String couGranted;

    private Long apmApplicationId;

    private Date apmApplicationDate;

    private Long trdPremise;

    private Date couGrantedDt;

    private Long oldTrdPremise;

    private Long oldTrmGroup1;

    private Long oldTrmGroup2;

    private Long oldTrmGroup3;

    private Long oldTrmGroup4;

    private Long oldTrmGroup5;

    private Long newTrmGroup1;

    private Long newTrmGroup2;

    private Long newTrmGroup3;

    private Long newTrmGroup4;

    private Long newTrmGroup5;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String chanGrantFlag;

    private Date chanAprvdate;

    private Long chanApprovedby;

    private Date chanExecdate;
    
    private String deptName;

    public String getChanGrantFlag() {
        return chanGrantFlag;
    }

    public void setChanGrantFlag(final String chanGrantFlag) {
        this.chanGrantFlag = chanGrantFlag;
    }

    public Date getChanAprvdate() {
        return chanAprvdate;
    }

    public void setChanAprvdate(final Date chanAprvdate) {
        this.chanAprvdate = chanAprvdate;
    }

    public Long getChanApprovedby() {
        return chanApprovedby;
    }

    public void setChanApprovedby(final Long chanApprovedby) {
        this.chanApprovedby = chanApprovedby;
    }

    public Date getChanExecdate() {
        return chanExecdate;
    }

    public void setChanExecdate(final Date chanExecdate) {
        this.chanExecdate = chanExecdate;
    }

    public long getCisId() {
        return cisId;
    }

    public void setCisId(final long cisId) {
        this.cisId = cisId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public String getStatusofwork() {
        return statusofwork;
    }

    public void setStatusofwork(final String statusofwork) {
        this.statusofwork = statusofwork;
    }

    public Date getDateofcomp() {
        return dateofcomp;
    }

    public void setDateofcomp(final Date dateofcomp) {
        this.dateofcomp = dateofcomp;
    }

    public Long getPlumId() {
        return plumId;
    }

    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(final String useType) {
        this.useType = useType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCouGranted() {
        return couGranted;
    }

    public void setCouGranted(final String couGranted) {
        this.couGranted = couGranted;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    public void setApmApplicationDate(final Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    public Long getTrdPremise() {
        return trdPremise;
    }

    public void setTrdPremise(final Long trdPremise) {
        this.trdPremise = trdPremise;
    }

    public Date getCouGrantedDt() {
        return couGrantedDt;
    }

    public void setCouGrantedDt(final Date couGrantedDt) {
        this.couGrantedDt = couGrantedDt;
    }

    public Long getOldTrdPremise() {
        return oldTrdPremise;
    }

    public void setOldTrdPremise(final Long oldTrdPremise) {
        this.oldTrdPremise = oldTrdPremise;
    }

    public Long getOldTrmGroup1() {
        return oldTrmGroup1;
    }

    public void setOldTrmGroup1(final Long oldTrmGroup1) {
        this.oldTrmGroup1 = oldTrmGroup1;
    }

    public Long getOldTrmGroup2() {
        return oldTrmGroup2;
    }

    public void setOldTrmGroup2(final Long oldTrmGroup2) {
        this.oldTrmGroup2 = oldTrmGroup2;
    }

    public Long getOldTrmGroup3() {
        return oldTrmGroup3;
    }

    public void setOldTrmGroup3(final Long oldTrmGroup3) {
        this.oldTrmGroup3 = oldTrmGroup3;
    }

    public Long getOldTrmGroup4() {
        return oldTrmGroup4;
    }

    public void setOldTrmGroup4(final Long oldTrmGroup4) {
        this.oldTrmGroup4 = oldTrmGroup4;
    }

    public Long getOldTrmGroup5() {
        return oldTrmGroup5;
    }

    public void setOldTrmGroup5(final Long oldTrmGroup5) {
        this.oldTrmGroup5 = oldTrmGroup5;
    }

    public Long getNewTrmGroup1() {
        return newTrmGroup1;
    }

    public void setNewTrmGroup1(final Long newTrmGroup1) {
        this.newTrmGroup1 = newTrmGroup1;
    }

    public Long getNewTrmGroup2() {
        return newTrmGroup2;
    }

    public void setNewTrmGroup2(final Long newTrmGroup2) {
        this.newTrmGroup2 = newTrmGroup2;
    }

    public Long getNewTrmGroup3() {
        return newTrmGroup3;
    }

    public void setNewTrmGroup3(final Long newTrmGroup3) {
        this.newTrmGroup3 = newTrmGroup3;
    }

    public Long getNewTrmGroup4() {
        return newTrmGroup4;
    }

    public void setNewTrmGroup4(final Long newTrmGroup4) {
        this.newTrmGroup4 = newTrmGroup4;
    }

    public Long getNewTrmGroup5() {
        return newTrmGroup5;
    }

    public void setNewTrmGroup5(final Long newTrmGroup5) {
        this.newTrmGroup5 = newTrmGroup5;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}