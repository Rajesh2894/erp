package com.abm.mainet.mrm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_MRM_MARRIAGE_HIST")
public class MarriageHistory implements Serializable {

    private static final long serialVersionUID = 1185744632033866411L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MAR_ID_H")
    private Long marHistId;

    @Column(name = "MAR_ID")
    private Long marId;

    @Column(name = "APPLICATION_ID", length = 50, nullable = true)
    private Long applicationId;

    @Column(name = "MAR_DATE", nullable = false)
    private Date marDate;

    @Column(name = "APP_DATE", nullable = false)
    private Date appDate;// system date

    @Column(name = "PLACE_MAR_E", length = 100, nullable = false)
    private String placeMarE;

    @Column(name = "PLACE_MAR_R", length = 100, nullable = false)
    private String placeMarR;

    @Column(name = "PERSONAL_LAW", nullable = false)
    private Long personalLaw;// PREFIX RLG

    @Column(name = "PRIEST_NAME_E", length = 100, nullable = false)
    private String priestNameE;

    @Column(name = "PRIEST_NAME_R", length = 100, nullable = false)
    private String priestNameR;

    @Column(name = "PRIEST_ADDRESS", length = 500, nullable = false)
    private String priestAddress;

    @Column(name = "PRIEST_RELIGION", nullable = false)
    private Long priestReligion;// RLG PREFIX

    @Column(name = "PRIEST_AGE", nullable = false)
    private int priestAge;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "URL_PARAM", nullable = true)
    private String urlParam; // identify last save tab

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "SERIAL_NO", nullable = true, length = 50)
    private String serialNo;

    @Column(name = "VOLUME", nullable = true, length = 50)
    private String volume;

    @Column(name = "WARD1")
    private Long ward1;

    @Column(name = "WARD2")
    private Long ward2;

    @Column(name = "WARD3")
    private Long ward3;

    @Column(name = "WARD4")
    private Long ward4;

    @Column(name = "WARD5")
    private Long ward5;

    @Column(name = "REG_DATE")
    private Date regDate;

    @Column(name = "APPLICANT_TYPE")
    private String applicantType;// H,W,O

    @Column(name = "ACTION_STATUS")
    private String actionStatus;// workflow status maintain

    public Long getMarHistId() {
        return marHistId;
    }

    public void setMarHistId(Long marHistId) {
        this.marHistId = marHistId;
    }

    public Long getMarId() {
        return marId;
    }

    public void setMarId(Long marId) {
        this.marId = marId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getMarDate() {
        return marDate;
    }

    public void setMarDate(Date marDate) {
        this.marDate = marDate;
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public String getPlaceMarE() {
        return placeMarE;
    }

    public void setPlaceMarE(String placeMarE) {
        this.placeMarE = placeMarE;
    }

    public String getPlaceMarR() {
        return placeMarR;
    }

    public void setPlaceMarR(String placeMarR) {
        this.placeMarR = placeMarR;
    }

    public Long getPersonalLaw() {
        return personalLaw;
    }

    public void setPersonalLaw(Long personalLaw) {
        this.personalLaw = personalLaw;
    }

    public String getPriestNameE() {
        return priestNameE;
    }

    public void setPriestNameE(String priestNameE) {
        this.priestNameE = priestNameE;
    }

    public String getPriestNameR() {
        return priestNameR;
    }

    public void setPriestNameR(String priestNameR) {
        this.priestNameR = priestNameR;
    }

    public String getPriestAddress() {
        return priestAddress;
    }

    public void setPriestAddress(String priestAddress) {
        this.priestAddress = priestAddress;
    }

    public Long getPriestReligion() {
        return priestReligion;
    }

    public void setPriestReligion(Long priestReligion) {
        this.priestReligion = priestReligion;
    }

    public int getPriestAge() {
        return priestAge;
    }

    public void setPriestAge(int priestAge) {
        this.priestAge = priestAge;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Long getWard1() {
        return ward1;
    }

    public void setWard1(Long ward1) {
        this.ward1 = ward1;
    }

    public Long getWard2() {
        return ward2;
    }

    public void setWard2(Long ward2) {
        this.ward2 = ward2;
    }

    public Long getWard3() {
        return ward3;
    }

    public void setWard3(Long ward3) {
        this.ward3 = ward3;
    }

    public Long getWard4() {
        return ward4;
    }

    public void setWard4(Long ward4) {
        this.ward4 = ward4;
    }

    public Long getWard5() {
        return ward5;
    }

    public void setWard5(Long ward5) {
        this.ward5 = ward5;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus;
    }

    public static String[] getPkValues() {
        return new String[] { "MRM", "TB_MRM_MARRIAGE_HIST", "MAR_ID_H" };
    }
}
