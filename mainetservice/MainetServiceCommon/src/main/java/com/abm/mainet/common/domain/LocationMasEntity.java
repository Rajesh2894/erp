package com.abm.mainet.common.domain;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_LOCATION_MAS"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_LOCATION_MAS")
public class LocationMasEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOC_ID", nullable = false)
    private Long locId;
    
    @Column(name = "LOC_NAME_ENG")
    private String locNameEng;

    @Column(name = "LOC_NAME_REG")
    private String locNameReg;

    @Column(name = "LOC_ACTIVE", length = 1)
    private String locActive;

    @Column(name = "LOC_DWZ_ID")
    private Long locDwzId;

    @Column(name = "LOC_PARENTID")
    private Long locParentid;

    @Column(name = "LOC_SOURCE", length = 1)
    private String locSource;

    @Column(name = "LOC_AUT_STATUS", length = 1)
    private String locAutStatus;

    @Column(name = "LOC_AUT_BY")
    private Long locAutBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "LOC_AUT_DATE")
    private Date locAutDate;

    @Column(name = "LANG_ID")
    private Integer langId;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE")
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "CENTRALENO")
    private String centraleno;

    @Column(name = "LATTIUDE")
    private String latitude;

    @Column(name = "LONGITUDE")
    private String longitude;

    @Column(name = "AUT_V3")
    private String autV3;

    @Column(name = "AUT_V4")
    private String autV4;

    @Column(name = "AUT_V5")
    private String autV5;

    @Column(name = "LOC_CAT")
    private Long locCategory;

    @Column(name = "AUT_N2")
    private Long autN2;

    @Column(name = "AUT_N3")
    private Long autN3;

    @Column(name = "AUT_N4")
    private Long autN4;

    @Column(name = "AUT_N5")
    private Long autN5;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D1")
    private Date autD1;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D2")
    private Date autD2;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D3")
    private Date autD3;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D4")
    private Date autD4;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUT_D5")
    private Date autD5;

    @Transient
    private String parentLocationName;

    @Column(name = "GIS_NO", length = 30)
    private Long GISNo;

    @Column(name = "LOC_AREA", length = 200)
    private String locArea;

    @Column(name = "LOC_AREA_REG", length = 200)
    private String locAreaReg;

    @Column(name = "LOC_ADDRESS", length = 500)
    private String locAddress;

    @Column(name = "LOC_ADDRESS_REG", length = 500)
    private String locAddressReg;

    @Column(name = "PINCODE", length = 12)
    private Long pincode;

    @Column(name = "LOC_TYPE")
    private Character deptLoc;

    @Column(name = "LOC_LANDMARK")
    private String landmark;

    @ManyToOne
    @JoinColumn(name = "ORGID")
    private Organisation organisation;

    @JsonIgnore
    @OneToMany(mappedBy = "tbLocationMas", targetEntity = Designation.class)
    private List<Designation> listOfDesignation;

    @JsonIgnore
    @OneToMany(mappedBy = "locationMasEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationElectrolWZMapping> locationElectrolWZMapping = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "locationMasEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationRevenueWZMapping> locationRevenueWZMapping = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "locationMasEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationOperationWZMapping> locationOperationWZMapping = new ArrayList<>();

    @JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "locId", cascade = CascadeType.ALL)
	@Where(clause = "YE_ACTIVE='Y'")
	private List<LocationYearDetEntity> locYearDetEntity = new ArrayList<>();

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "LOC_CODE")
    private String locCode;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public LocationMasEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setLocId(final Long locId) {
        this.locId = locId;
    }

    public Long getLocId() {
        return locId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    // --- DATABASE MAPPING : LOC_NAME_ENG ( NVARCHAR2 )
    public void setLocNameEng(final String locNameEng) {
        this.locNameEng = locNameEng;
    }

    public String getLocNameEng() {
        return locNameEng;
    }

    // --- DATABASE MAPPING : LOC_NAME_REG ( NVARCHAR2 )
    public void setLocNameReg(final String locNameReg) {
        this.locNameReg = locNameReg;
    }

    public String getLocNameReg() {
        return locNameReg;
    }

    // --- DATABASE MAPPING : LOC_ACTIVE ( VARCHAR2 )
    public void setLocActive(final String locActive) {
        this.locActive = locActive;
    }

    public String getLocActive() {
        return locActive;
    }

    // --- DATABASE MAPPING : LOC_DWZ_ID ( NUMBER )
    public void setLocDwzId(final Long locDwzId) {
        this.locDwzId = locDwzId;
    }

    public Long getLocDwzId() {
        return locDwzId;
    }

    // --- DATABASE MAPPING : LOC_PARENTID ( NUMBER )
    public void setLocParentid(final Long locParentid) {
        this.locParentid = locParentid;
    }

    public Long getLocParentid() {
        return locParentid;
    }

    // --- DATABASE MAPPING : LOC_SOURCE ( CHAR )
    public void setLocSource(final String locSource) {
        this.locSource = locSource;
    }

    public String getLocSource() {
        return locSource;
    }

    // --- DATABASE MAPPING : LOC_AUT_STATUS ( CHAR )
    public void setLocAutStatus(final String locAutStatus) {
        this.locAutStatus = locAutStatus;
    }

    public String getLocAutStatus() {
        return locAutStatus;
    }

    // --- DATABASE MAPPING : LOC_AUT_BY ( NUMBER )
    public void setLocAutBy(final Long locAutBy) {
        this.locAutBy = locAutBy;
    }

    public Long getLocAutBy() {
        return locAutBy;
    }

    // --- DATABASE MAPPING : LOC_AUT_DATE ( DATE )
    public void setLocAutDate(final Date locAutDate) {
        this.locAutDate = locAutDate;
    }

    public Date getLocAutDate() {
        return locAutDate;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final Integer langId) {
        this.langId = langId;
    }

    public Integer getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : LMODDATE ( DATE )
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    // --- DATABASE MAPPING : CENTRALENO ( NVARCHAR2 )
    public void setCentraleno(final String centraleno) {
        this.centraleno = centraleno;
    }

    public String getCentraleno() {
        return centraleno;
    }

    // --- DATABASE MAPPING : AUT_V3 ( NVARCHAR2 )
    public void setAutV3(final String autV3) {
        this.autV3 = autV3;
    }

    public String getAutV3() {
        return autV3;
    }

    // --- DATABASE MAPPING : AUT_V4 ( NVARCHAR2 )
    public void setAutV4(final String autV4) {
        this.autV4 = autV4;
    }

    public String getAutV4() {
        return autV4;
    }

    // --- DATABASE MAPPING : AUT_V5 ( NVARCHAR2 )
    public void setAutV5(final String autV5) {
        this.autV5 = autV5;
    }

    public String getAutV5() {
        return autV5;
    }

    // --- DATABASE MAPPING : AUT_N2 ( NUMBER )
    public void setAutN2(final Long autN2) {
        this.autN2 = autN2;
    }

    public Long getAutN2() {
        return autN2;
    }

    // --- DATABASE MAPPING : AUT_N3 ( NUMBER )
    public void setAutN3(final Long autN3) {
        this.autN3 = autN3;
    }

    public Long getAutN3() {
        return autN3;
    }

    // --- DATABASE MAPPING : AUT_N4 ( NUMBER )
    public void setAutN4(final Long autN4) {
        this.autN4 = autN4;
    }

    public Long getAutN4() {
        return autN4;
    }

    // --- DATABASE MAPPING : AUT_N5 ( NUMBER )
    public void setAutN5(final Long autN5) {
        this.autN5 = autN5;
    }

    public Long getAutN5() {
        return autN5;
    }

    // --- DATABASE MAPPING : AUT_D1 ( DATE )
    public void setAutD1(final Date autD1) {
        this.autD1 = autD1;
    }

    public Date getAutD1() {
        return autD1;
    }

    // --- DATABASE MAPPING : AUT_D2 ( DATE )
    public void setAutD2(final Date autD2) {
        this.autD2 = autD2;
    }

    public Date getAutD2() {
        return autD2;
    }

    // --- DATABASE MAPPING : AUT_D3 ( DATE )
    public void setAutD3(final Date autD3) {
        this.autD3 = autD3;
    }

    public Date getAutD3() {
        return autD3;
    }

    // --- DATABASE MAPPING : AUT_D4 ( DATE )
    public void setAutD4(final Date autD4) {
        this.autD4 = autD4;
    }

    public Date getAutD4() {
        return autD4;
    }

    // --- DATABASE MAPPING : AUT_D5 ( DATE )
    public void setAutD5(final Date autD5) {
        this.autD5 = autD5;
    }

    public Date getAutD5() {
        return autD5;
    }

    public void setListOfDesignation(final List<Designation> listOfDesignation) {
        this.listOfDesignation = listOfDesignation;
    }

    public List<Designation> getListOfDesignation() {
        return listOfDesignation;
    }

    /**
     * @return the parentLocationName
     */
    public String getParentLocationName() {
        return parentLocationName;
    }

    /**
     * @param parentLocationName the parentLocationName to set
     */
    public void setParentLocationName(final String parentLocationName) {
        this.parentLocationName = parentLocationName;
    }

    /**
     * @return the gISNo
     */
    public Long getGISNo() {
        return GISNo;
    }

    /**
     * @param gISNo the gISNo to set
     */
    public void setGISNo(final Long gISNo) {
        GISNo = gISNo;
    }

    /**
     * @return the locArea
     */
    public String getLocArea() {
        return locArea;
    }

    /**
     * @param locArea the locArea to set
     */
    public void setLocArea(final String locArea) {
        this.locArea = locArea;
    }

    /**
     * @return the locAreaReg
     */
    public String getLocAreaReg() {
        return locAreaReg;
    }

    /**
     * @param locAreaReg the locAreaReg to set
     */
    public void setLocAreaReg(final String locAreaReg) {
        this.locAreaReg = locAreaReg;
    }

    /**
     * @return the locAddress
     */
    public String getLocAddress() {
        return locAddress;
    }

    /**
     * @param locAddress the locAddress to set
     */
    public void setLocAddress(final String locAddress) {
        this.locAddress = locAddress;
    }

    /**
     * @return the locAddressReg
     */
    public String getLocAddressReg() {
        return locAddressReg;
    }

    /**
     * @param locAddressReg the locAddressReg to set
     */
    public void setLocAddressReg(final String locAddressReg) {
        this.locAddressReg = locAddressReg;
    }

    /**
     * @return the pincode
     */
    public Long getPincode() {
        return pincode;
    }

    /**
     * @param pincode the pincode to set
     */
    public void setPincode(final Long pincode) {
        this.pincode = pincode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Character getDeptLoc() {
        return deptLoc;
    }

    public void setDeptLoc(final Character deptLoc) {
        this.deptLoc = deptLoc;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }

    public List<LocationElectrolWZMapping> getLocationElectrolWZMapping() {
        return locationElectrolWZMapping;
    }

    public void setLocationElectrolWZMapping(
            final List<LocationElectrolWZMapping> locationElectrolWZMapping) {
        this.locationElectrolWZMapping = locationElectrolWZMapping;
    }

    public List<LocationRevenueWZMapping> getLocationRevenueWZMapping() {
        return locationRevenueWZMapping;
    }

    public void setLocationRevenueWZMapping(
            final List<LocationRevenueWZMapping> locationRevenueWZMapping) {
        this.locationRevenueWZMapping = locationRevenueWZMapping;
    }

    public List<LocationOperationWZMapping> getLocationOperationWZMapping() {
        return locationOperationWZMapping;
    }

    public void setLocationOperationWZMapping(
            final List<LocationOperationWZMapping> locationOperationWZMapping) {
        this.locationOperationWZMapping = locationOperationWZMapping;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(final String landmark) {
        this.landmark = landmark;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getLocCategory() {
        return locCategory;
    }

    public void setLocCategory(Long locCategory) {
        this.locCategory = locCategory;
    }

    public String getLocCode() {
        return locCode;
    }

    public void setLocCode(String locCode) {
        this.locCode = locCode;
    }

    public List<LocationYearDetEntity> getLocYearDetEntity() {
		return locYearDetEntity;
	}

	public void setLocYearDetEntity(List<LocationYearDetEntity> locYearDetEntity) {
		this.locYearDetEntity = locYearDetEntity;
	}

	public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOCATION_MAS", "LOC_ID" };
    }

}
