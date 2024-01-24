package com.abm.mainet.adh.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 * @since 24 Sept 2019
 */
@Entity
@Table(name = "TB_ADH_MAS")
public class NewAdvertisementApplication implements Serializable {

    private static final long serialVersionUID = 7849429078077387688L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ADH_ID")
    private Long adhId;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "APLCNT_CATID")
    private Long appCategoryId;

    @Column(name = "AGN_ID")
    private Long agencyId;

    @Column(name = "LOC_CATID")
    private String locCatType;

    @Column(name = "ADH_LICTYPE")
    private Long licenseType;

    @Column(name = "ADH_LICNO")
    private String licenseNo;

    @Column(name = "ADH_OLDLICNO")
    private String oldLicenseNo;

    @Column(name = "ADH_LICFROM_DATE")
    private Date licenseFromDate;

    @Column(name = "ADH_LICTO_DATE")
    private Date licenseToDate;

    @Column(name = "ADH_LICISDATE")
    private Date licenseIssueDate;

    @Column(name = "ADH_LOCID")
    private Long locId;

    @Column(name = "ADH_ZONE1")
    private Long adhZone1;

    @Column(name = "ADH_ZONE2")
    private Long adhZone2;

    @Column(name = "ADH_ZONE3")
    private Long adhZone3;

    @Column(name = "ADH_ZONE4")
    private Long adhZone4;

    @Column(name = "ADH_ZONE5")
    private Long adhZone5;

    @Column(name = "PROPERTY_TYPID")
    private Long propTypeId;

    @Column(name = "PROPERTY_ID")
    private String propNumber;

    @Column(name = "TRD_LIC_NO")
    private String tradeLicNo;

    @Column(name = "PT_OWNER_NAME")
    private String propOwnerName;

    @Column(name = "ADH_STATUS")
    private String adhStatus;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "property_address")
    private String propAddress;
    
    @Column(name = "ULB_STATUS")
    private String ulbOwned;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newAdvertisement", cascade = CascadeType.ALL)
    private List<NewAdvertisementApplicationDet> newAdvertisetDetails = new ArrayList<>();

    public Long getAdhId() {
        return adhId;
    }

    public void setAdhId(Long adhId) {
        this.adhId = adhId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getAppCategoryId() {
        return appCategoryId;
    }

    public void setAppCategoryId(Long appCategoryId) {
        this.appCategoryId = appCategoryId;
    }

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getLocCatType() {
        return locCatType;
    }

    public void setLocCatType(String locCatType) {
        this.locCatType = locCatType;
    }

    public Long getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(Long licenseType) {
        this.licenseType = licenseType;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public String getOldLicenseNo() {
        return oldLicenseNo;
    }

    public void setOldLicenseNo(String oldLicenseNo) {
        this.oldLicenseNo = oldLicenseNo;
    }

    public Date getLicenseFromDate() {
        return licenseFromDate;
    }

    public void setLicenseFromDate(Date licenseFromDate) {
        this.licenseFromDate = licenseFromDate;
    }

    public Date getLicenseToDate() {
        return licenseToDate;
    }

    public void setLicenseToDate(Date licenseToDate) {
        this.licenseToDate = licenseToDate;
    }

    public Date getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(Date licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public Long getAdhZone1() {
        return adhZone1;
    }

    public void setAdhZone1(Long adhZone1) {
        this.adhZone1 = adhZone1;
    }

    public Long getAdhZone2() {
        return adhZone2;
    }

    public void setAdhZone2(Long adhZone2) {
        this.adhZone2 = adhZone2;
    }

    public Long getAdhZone3() {
        return adhZone3;
    }

    public void setAdhZone3(Long adhZone3) {
        this.adhZone3 = adhZone3;
    }

    public Long getAdhZone4() {
        return adhZone4;
    }

    public void setAdhZone4(Long adhZone4) {
        this.adhZone4 = adhZone4;
    }

    public Long getAdhZone5() {
        return adhZone5;
    }

    public void setAdhZone5(Long adhZone5) {
        this.adhZone5 = adhZone5;
    }

    public Long getPropTypeId() {
        return propTypeId;
    }

    public void setPropTypeId(Long propTypeId) {
        this.propTypeId = propTypeId;
    }

    public String getPropNumber() {
        return propNumber;
    }

    public void setPropNumber(String propNumber) {
        this.propNumber = propNumber;
    }

    public String getTradeLicNo() {
        return tradeLicNo;
    }

    public void setTradeLicNo(String tradeLicNo) {
        this.tradeLicNo = tradeLicNo;
    }

    public String getPropOwnerName() {
        return propOwnerName;
    }

    public void setPropOwnerName(String propOwnerName) {
        this.propOwnerName = propOwnerName;
    }

    public String getAdhStatus() {
        return adhStatus;
    }

    public void setAdhStatus(String adhStatus) {
        this.adhStatus = adhStatus;
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

    public List<NewAdvertisementApplicationDet> getNewAdvertisetDetails() {
        return newAdvertisetDetails;
    }

    public void setNewAdvertisetDetails(List<NewAdvertisementApplicationDet> newAdvertisetDetails) {
        this.newAdvertisetDetails = newAdvertisetDetails;
    }

    public String getPropAddress() {
        return propAddress;
    }

    public void setPropAddress(String propAddress) {
        this.propAddress = propAddress;
    }
    
    public String getUlbOwned() {
		return ulbOwned;
	}

	public void setUlbOwned(String ulbOwned) {
		this.ulbOwned = ulbOwned;
	}

	public String[] getPkValues() {
        return new String[] { "ADH", "TB_ADH_MAS", "ADH_ID" };
    }

}
