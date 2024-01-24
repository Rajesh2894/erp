package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author rajesh.chavan
 * @since 22 Jan 2016
 * @comment Applicant Address Details
 */
@Entity
@Table(name = "TB_CFC_APPLICATION_ADDRESS")
public class CFCApplicationAddressEntity implements Serializable {

    private static final long serialVersionUID = 3931628684521667307L;

    @Id
    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
    private long apmApplicationId;

    @Column(name = "APA_BLOCKNO", length = 10, nullable = true)
    private String apaBlockno;

    @Column(name = "APA_FLOOR", length = 10, nullable = true)
    private String apaFloor;

    @Column(name = "APA_WING", length = 10, nullable = true)
    private String apaWing;

    @Column(name = "APA_BLDGNM", length = 150, nullable = true)
    private String apaBldgnm;

    @Column(name = "APA_HSG_CMPLXNM", length = 150, nullable = true)
    private String apaHsgCmplxnm;

    @Column(name = "APA_ROADNM", length = 150, nullable = true)
    private String apaRoadnm;

    @Column(name = "APA_AREANM", length = 150, nullable = true)
    private String apaAreanm;

    @Column(name = "APA_PINCODE", precision = 6, scale = 0, nullable = true)
    private Long apaPincode;

    @Column(name = "APA_PHONE1", length = 25, nullable = true)
    private String apaPhone1;

    @Column(name = "APA_PHONE2", length = 25, nullable = true)
    private String apaPhone2;

    @Column(name = "APA_CONTACT_PERSONNM", length = 100, nullable = true)
    private String apaContactPersonnm;

    @Column(name = "APA_EMAIL", length = 100, nullable = true)
    private String apaEmail;

    @Column(name = "APA_PAGERNO", length = 20, nullable = true)
    private String apaPagerno;

    @Column(name = "APA_MOBILNO", length = 20, nullable = true)
    private String apaMobilno;

    @Column(name = "APA_WARD_NO", precision = 12, scale = 0, nullable = true)
    private Long apaWardNo;

    @Column(name = "APA_ELECTORAL_WARD", length = 5, nullable = true)
    private String apaElectoralWard;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private Long langId;

    @Column(name = "LMODDATE", nullable = false)
    private Date lmodDate;

    @Column(name = "APA_CITY_ID", precision = 12, scale = 0, nullable = true)
    private Long apaCityId;

    @Column(name = "APA_DISTRICT_ID", precision = 12, scale = 0, nullable = true)
    private Long apaDistrictId;

    @Column(name = "APA_STATE_ID", precision = 12, scale = 0, nullable = true)
    private Long apaStateId;

    @JsonIgnore
    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "APA_TALUKA_ID", precision = 12, scale = 0, nullable = true)
    private Long apaTalukaId;

    @Column(name = "APA_COUNTRY_ID", precision = 12, scale = 0, nullable = true)
    private Long apaCountryId;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "APA_LANDMARK", length = 150, nullable = true)
    private String apaLandmark;

    @Column(name = "APA_ZONE_NO", length = 12, nullable = true)
    private Long apaZoneNo;

    @Column(name = "APA_BLOCK_NAME", length = 50, nullable = true)
    private String apaBlockName;

    @Column(name = "APA_FLAT_BUILDING_NO", length = 20, nullable = true)
    private String apaFlatBuildingNo;

    @Column(name = "APA_CITY_NAME", length = 200, nullable = true)
    private String apaCityName;

    public long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getApaBlockno() {
        return apaBlockno;
    }

    public void setApaBlockno(final String apaBlockno) {
        this.apaBlockno = apaBlockno;
    }

    public String getApaFloor() {
        return apaFloor;
    }

    public void setApaFloor(final String apaFloor) {
        this.apaFloor = apaFloor;
    }

    public String getApaWing() {
        return apaWing;
    }

    public void setApaWing(final String apaWing) {
        this.apaWing = apaWing;
    }

    public String getApaBldgnm() {
        return apaBldgnm;
    }

    public void setApaBldgnm(final String apaBldgnm) {
        this.apaBldgnm = apaBldgnm;
    }

    public String getApaHsgCmplxnm() {
        return apaHsgCmplxnm;
    }

    public void setApaHsgCmplxnm(final String apaHsgCmplxnm) {
        this.apaHsgCmplxnm = apaHsgCmplxnm;
    }

    public String getApaRoadnm() {
        return apaRoadnm;
    }

    public void setApaRoadnm(final String apaRoadnm) {
        this.apaRoadnm = apaRoadnm;
    }

    public String getApaAreanm() {
        return apaAreanm;
    }

    public void setApaAreanm(final String apaAreanm) {
        this.apaAreanm = apaAreanm;
    }

    public Long getApaPincode() {
        return apaPincode;
    }

    public void setApaPincode(final Long apaPincode) {
        this.apaPincode = apaPincode;
    }

    public String getApaPhone1() {
        return apaPhone1;
    }

    public void setApaPhone1(final String apaPhone1) {
        this.apaPhone1 = apaPhone1;
    }

    public String getApaPhone2() {
        return apaPhone2;
    }

    public void setApaPhone2(final String apaPhone2) {
        this.apaPhone2 = apaPhone2;
    }

    public String getApaContactPersonnm() {
        return apaContactPersonnm;
    }

    public void setApaContactPersonnm(final String apaContactPersonnm) {
        this.apaContactPersonnm = apaContactPersonnm;
    }

    public String getApaEmail() {
        return apaEmail;
    }

    public void setApaEmail(final String apaEmail) {
        this.apaEmail = apaEmail;
    }

    public String getApaPagerno() {
        return apaPagerno;
    }

    public void setApaPagerno(final String apaPagerno) {
        this.apaPagerno = apaPagerno;
    }

    public String getApaMobilno() {
        return apaMobilno;
    }

    public void setApaMobilno(final String apaMobilno) {
        this.apaMobilno = apaMobilno;
    }

    public Long getApaWardNo() {
        return apaWardNo;
    }

    public void setApaWardNo(final Long apaWardNo) {
        this.apaWardNo = apaWardNo;
    }

    public String getApaElectoralWard() {
        return apaElectoralWard;
    }

    public void setApaElectoralWard(final String apaElectoralWard) {
        this.apaElectoralWard = apaElectoralWard;
    }

    public Long getApaCityId() {
        return apaCityId;
    }

    public void setApaCityId(final Long apaCityId) {
        this.apaCityId = apaCityId;
    }

    public Long getApaDistrictId() {
        return apaDistrictId;
    }

    public void setApaDistrictId(final Long apaDistrictId) {
        this.apaDistrictId = apaDistrictId;
    }

    public Long getApaStateId() {
        return apaStateId;
    }

    public void setApaStateId(final Long apaStateId) {
        this.apaStateId = apaStateId;
    }

    public Long getApaTalukaId() {
        return apaTalukaId;
    }

    public void setApaTalukaId(final Long apaTalukaId) {
        this.apaTalukaId = apaTalukaId;
    }

    public Long getApaCountryId() {
        return apaCountryId;
    }

    public void setApaCountryId(final Long apaCountryId) {
        this.apaCountryId = apaCountryId;
    }

    public String getApaLandmark() {
        return apaLandmark;
    }

    public void setApaLandmark(final String apaLandmark) {
        this.apaLandmark = apaLandmark;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getApaZoneNo() {
        return apaZoneNo;
    }

    public void setApaZoneNo(final Long apaZoneNo) {
        this.apaZoneNo = apaZoneNo;
    }

    public String getApaBlockName() {
        return apaBlockName;
    }

    public void setApaBlockName(final String apaBlockName) {
        this.apaBlockName = apaBlockName;
    }

    public String getApaFlatBuildingNo() {
        return apaFlatBuildingNo;
    }

    public void setApaFlatBuildingNo(final String apaFlatBuildingNo) {
        this.apaFlatBuildingNo = apaFlatBuildingNo;
    }

    public String getApaCityName() {
        return apaCityName;
    }

    public void setApaCityName(final String apaCityName) {
        this.apaCityName = apaCityName;
    }

    public String[] getPkValues() {
        return new String[] { "CFC", "TB_CFC_APPLICATION_ADDRESS", "APM_APPLICATION_ID" };
    }

}