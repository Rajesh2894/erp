package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 * @since 04 Jun 2016
 */
@Entity
@Table(name = "TB_WT_CHANGE_OF_USE")
public class ChangeOfUsage implements Serializable {

    private static final long serialVersionUID = -1294103433287014410L;

    @Id
    @GenericGenerator(name = "CustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "CustomGenerator")
    @Column(name = "CIS_ID", precision = 12, scale = 0, nullable = false)
    // comments : change in size identification number
    private long cisId;

    @Column(name = "CS_IDN", precision = 12, scale = 0, nullable = true)
    // comments : connection identification
    private Long csIdn;

    @Column(name = "STATUSOFWORK", length = 1, nullable = true)
    // comments : status of work
    private String statusofwork;

    @Column(name = "DATEOFCOMP", nullable = true)
    // comments : date of completion
    private Date dateofcomp;

    @Column(name = "PLUM_ID", precision = 12, scale = 0, nullable = true)
    // comments : plumber id
    private Long plumId;

    @Column(name = "REMARK", length = 2000, nullable = true)
    // comments : remark
    private String remark;

    @Column(name = "USE_TYPE", length = 2, nullable = true)
    // comments : Use-- CCN Size --CS / Tariff group -TG/ Source line -SL
    private String useType;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Org ID
    private Long orgId;

    @Column(name = "USER_ID", precision = 7, scale = 0, nullable = false)
    // comments : User ID
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    // comments : Lang ID
    private Long langId;

    @Column(name = "LMODDATE", nullable = false)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @Column(name = "COU_GRANTED", length = 1, nullable = true)
    // comments : Checking for granted
    private String couGranted;

    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    // comments : Application id
    private Long apmApplicationId;

    @Column(name = "APM_APPLICATION_DATE", nullable = true)
    // comments : Application date
    private Date apmApplicationDate;

    @Column(name = "TRD_PREMISE", precision = 12, scale = 0, nullable = true)
    // comments : Premise type
    private Long trdPremise;

    @Column(name = "COU_GRANTED_DT", nullable = true)
    // comments : Application Granted date
    private Date couGrantedDt;

    @Column(name = "OLD_TRD_PREMISE", precision = 12, scale = 0, nullable = true)
    // comments : Old premise
    private Long oldTrdPremise;

    @Column(name = "OLD_TRM_GROUP1", precision = 14, scale = 0, nullable = true)
    // comments : Old Tariff group hierarchy
    private Long oldTrmGroup1;

    @Column(name = "OLD_TRM_GROUP2", precision = 14, scale = 0, nullable = true)
    // comments : Old Tariff group hierarchy
    private Long oldTrmGroup2;

    @Column(name = "OLD_TRM_GROUP3", precision = 14, scale = 0, nullable = true)
    // comments : Old Tariff group hierarchy
    private Long oldTrmGroup3;

    @Column(name = "OLD_TRM_GROUP4", precision = 14, scale = 0, nullable = true)
    // comments : Old Tariff group hierarchy
    private Long oldTrmGroup4;

    @Column(name = "OLD_TRM_GROUP5", precision = 14, scale = 0, nullable = true)
    // comments : Old Tariff group hierarchy
    private Long oldTrmGroup5;

    @Column(name = "NEW_TRM_GROUP1", precision = 14, scale = 0, nullable = true)
    // comments : New tariff group hierarchy
    private Long newTrmGroup1;

    @Column(name = "NEW_TRM_GROUP2", precision = 14, scale = 0, nullable = true)
    // comments : New tariff group hierarchy
    private Long newTrmGroup2;

    @Column(name = "NEW_TRM_GROUP3", precision = 14, scale = 0, nullable = true)
    // comments : New tariff group hierarchy
    private Long newTrmGroup3;

    @Column(name = "NEW_TRM_GROUP4", precision = 14, scale = 0, nullable = true)
    // comments : New tariff group hierarchy
    private Long newTrmGroup4;

    @Column(name = "NEW_TRM_GROUP5", precision = 14, scale = 0, nullable = true)
    // comments : New tariff group hierarchy
    private Long newTrmGroup5;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine�s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine�s Login Name | IP Address | Physical
    // Address
    private String lgIpMacUpd;

    @Column(name = "CHAN_GRANT_FLAG", length = 1, nullable = true)
    private String chanGrantFlag;

    @Column(name = "CHAN_APRVDATE", nullable = true)
    private Date chanAprvdate;

    @Column(name = "CHAN_APPROVEDBY", precision = 7, scale = 0, nullable = true)
    private Long chanApprovedby;

    @Column(name = "CHAN_EXECDATE", nullable = true)
    private Date chanExecdate;
    
    @Column(name = "NEW_METER_TYPE", precision = 10, scale = 0, nullable = true)
    private Long newCsMeteredccn;

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
    
	public Long getNewCsMeteredccn() {
		return newCsMeteredccn;
	}

	public void setNewCsMeteredccn(Long newCsMeteredccn) {
		this.newCsMeteredccn = newCsMeteredccn;
	}

	@JsonIgnore
    public String[] getPkValues() {

        return new String[] { "WT", "TB_WT_CHANGE_OF_USE", "CIS_ID" };
    }

}