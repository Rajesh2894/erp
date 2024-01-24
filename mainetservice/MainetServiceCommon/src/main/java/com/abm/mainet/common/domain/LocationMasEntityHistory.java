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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Persistent class for entity stored in table "TB_LOCATION_MAS"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name = "TB_LOCATION_MAS_HIST")
public class LocationMasEntityHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
     
    @Column(name = "H_LOCID", nullable = false)
    private Long locHistId;
    
    
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

    @Transient
    private String parentLocationName;


    @ManyToOne
    @JoinColumn(name = "ORGID")
    private Organisation organisation;

    @JsonIgnore
    @OneToMany(mappedBy = "tbLocationMas", targetEntity = Designation.class)
    private List<Designation> listOfDesignation;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "H_STATUS" , length =1 )
    private String status;

	// ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public LocationMasEntityHistory() {
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
    
    public Long getLocHistId() {
		return locHistId;
	}

	public void setLocHistId(Long locHistId) {
		this.locHistId = locHistId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

	public void setStatus(String status) {
		this.status = status;
	}

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(locId);
        sb.append("]:");
        sb.append(locNameEng);
        sb.append("|");
        sb.append(locNameReg);
        sb.append("|");
        sb.append("|");
        sb.append(locActive);
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append(locDwzId);
        sb.append("|");
        sb.append(locParentid);
        sb.append("|");
        sb.append(locSource);
        sb.append("|");
        sb.append(locAutStatus);
        sb.append("|");
        sb.append(locAutBy);
        sb.append("|");
        sb.append(locAutDate);
        sb.append("|");
//        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(centraleno);
        sb.append("|");
//        sb.append(autV1);
        sb.append("|");
//        sb.append(autV2);
        sb.append("|");
//        sb.append(autV3);
        sb.append("|");
//        sb.append(autV4);
        sb.append("|");
//        sb.append(autV5);
        sb.append("|");
//        sb.append(autN1);
        sb.append("|");
//        sb.append(autN2);
        sb.append("|");
//        sb.append(autN3);
        sb.append("|");
//        sb.append(autN4);
        sb.append("|");
//        sb.append(autN5);
        sb.append("|");
//        sb.append(autD1);
        sb.append("|");
//        sb.append(autD2);
        sb.append("|");
//        sb.append(autD3);
        sb.append("|");
//        sb.append(autD4);
        sb.append("|");
//        sb.append(autD5);
        return sb.toString();
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOCATION_MAS_HIST", "H_LOCID"};
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

	public String getStatus() {
		return status;
	}


}
