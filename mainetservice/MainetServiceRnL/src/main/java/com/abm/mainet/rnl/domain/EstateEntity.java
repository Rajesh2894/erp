package com.abm.mainet.rnl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 * Estate Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_ESTATE_MAS")
public class EstateEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ES_ID", nullable = false)
    private Long esId;

    /**
     * estate no. for estate ( Auto generated)('EM'+'ORGID'+0000+Sequence number)
     */
    @Column(name = "ES_CODE", nullable = false)
    private String code;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "ES_NAME_ENG")
    private String nameEng;

    @Column(name = "ES_NAME_REG")
    private String nameReg;

    /**
     * Mapped to TB_LOCATION_MAS table with LocationMasEntity entity
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOC_ID")
    private LocationMasEntity locationMas;

    @Column(name = "ES_ADDRESS")
    private String address;

    @Column(name = "ASSET_ID")
    private String assetNo;

    /**
     * Category is taken as a Group or Single in property file
     *
     */
    @Column(name = "ES_TYPE")
    private Character category;

    /**
     * Getting type value from Prefix hierarchical -->ETY
     */
    @Column(name = "COD_ID1_ETY")
    private Integer type1;

    @Column(name = "COD_ID2_ETY")
    private Integer type2;

    @Column(name = "ES_REG_NUMBER")
    private String regNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "ES_REGN_DATE")
    private Date regDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ES_CONSTRUCTION_DATE")
    private Date constDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ES_COMPLETION_DATE")
    private Date compDate;

    @Column(name = "ES_NO_OF_FLOORS")
    private Integer floors;

    @Column(name = "ES_NO_OF_BASEMENT")
    private Integer basements;

    @Column(name = "USER_ID")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "LMODDATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    /**
     * flag to identify whether the record is deleted or not. 'y' for deleted (inactive) and 'n' for not deleted (active) record.
     */
    @Column(name = "ES_ACTIVE")
    private Character isActive;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "LANGID")
    private long langId;

    @Column(name = "ES_NATURE_LAND")
    private Long natureOfLand;

    @Column(name = "ES_SURVEY_NO")
    private String surveyNo;

    @Column(name = "ES_LATITUDE")
    private String latitude;

    @Column(name = "ES_LONGITUDE")
    private String longitude;

    @Column(name = "EAST", length = 200, nullable = true)
    private String east;

    @Column(name = "WEST", length = 200, nullable = true)
    private String west;

    @Column(name = "SOUTH", length = 200, nullable = true)
    private String south;

    @Column(name = "NORTH", length = 200, nullable = true)
    private String north;

    @Column(name = "PURPOSE")
    private Long purpose;// EPR

    @Column(name = "ACQ_TYPE")
    private Long acqType;// AQM

    @Column(name = "HOLDING_TYPE")
    private Long holdingType;// EHT
    
    @Column(name = "RNL_GST_NO")
    private String rnlGstNo;

    public Long getEsId() {
        return esId;
    }

    public void setEsId(final Long esId) {
        this.esId = esId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getNameEng() {
        return nameEng;
    }

    public void setNameEng(final String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameReg() {
        return nameReg;
    }

    public void setNameReg(final String nameReg) {
        this.nameReg = nameReg;
    }

    public LocationMasEntity getLocationMas() {
        return locationMas;
    }

    public void setLocationMas(final LocationMasEntity locationMas) {
        this.locationMas = locationMas;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(final String assetNo) {
        this.assetNo = assetNo;
    }

    public Character getCategory() {
        return category;
    }

    public void setCategory(final Character category) {
        this.category = category;
    }

    public Integer getType1() {
        return type1;
    }

    public void setType1(final Integer type1) {
        this.type1 = type1;
    }

    public Integer getType2() {
        return type2;
    }

    public void setType2(final Integer type2) {
        this.type2 = type2;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(final String regNo) {
        this.regNo = regNo;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(final Date regDate) {
        this.regDate = regDate;
    }

    public Date getConstDate() {
        return constDate;
    }

    public void setConstDate(final Date constDate) {
        this.constDate = constDate;
    }

    public Date getCompDate() {
        return compDate;
    }

    public void setCompDate(final Date compDate) {
        this.compDate = compDate;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(final Integer floors) {
        this.floors = floors;
    }

    public Integer getBasements() {
        return basements;
    }

    public void setBasements(final Integer basements) {
        this.basements = basements;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(final long langId) {
        this.langId = langId;
    }

    /**
     * @return the natureOfLand
     */
    public Long getNatureOfLand() {
        return natureOfLand;
    }

    /**
     * @param natureOfLand the natureOfLand to set
     */
    public void setNatureOfLand(final Long natureOfLand) {
        this.natureOfLand = natureOfLand;
    }

    /**
     * @return the surveyNo
     */
    public String getSurveyNo() {
        return surveyNo;
    }

    /**
     * @param surveyNo the surveyNo to set
     */
    public void setSurveyNo(final String surveyNo) {
        this.surveyNo = surveyNo;
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

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }

    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public String getSouth() {
        return south;
    }

    public void setSouth(String south) {
        this.south = south;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public Long getPurpose() {
        return purpose;
    }

    public void setPurpose(Long purpose) {
        this.purpose = purpose;
    }

    public Long getAcqType() {
        return acqType;
    }

    public void setAcqType(Long acqType) {
        this.acqType = acqType;
    }

    public Long getHoldingType() {
        return holdingType;
    }

    public void setHoldingType(Long holdingType) {
        this.holdingType = holdingType;
    }
    
    

    public String getRnlGstNo() {
		return rnlGstNo;
	}

	public void setRnlGstNo(String rnlGstNo) {
		this.rnlGstNo = rnlGstNo;
	}

	public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.EstateMaster.TB_RL_ESTATE_MAS,
                MainetConstants.RnLDetailEntity.ES_ID };
    }
}
