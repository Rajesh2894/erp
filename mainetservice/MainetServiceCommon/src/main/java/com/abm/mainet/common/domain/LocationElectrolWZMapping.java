package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Jeetendra.Pal
 * @since 10 Aug 2016
 * @comment This table stores mapping Entry for Location and Electoral Ward Zone
 */
@Entity
@Table(name = "TB_LOCATION_ELECT_WARDZONE")
public class LocationElectrolWZMapping implements Serializable {

    private static final long serialVersionUID = -8406311573317183674L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOCEWZMP_ID", precision = 12, scale = 0, nullable = false)
    private Long locewzmpId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @ManyToOne(cascade = CascadeType.ALL , fetch= FetchType.LAZY)
    @JoinColumn(name = "LOC_ID")
    private LocationMasEntity locationMasEntity;

    @Column(name = "COD_ID_ELEC_LEVEL1", precision = 12, scale = 0, nullable = true)
    private Long codIdElecLevel1;

    @Column(name = "COD_ID_ELEC_LEVEL2", precision = 12, scale = 0, nullable = true)
    private Long codIdElecLevel2;

    @Column(name = "COD_ID_ELEC_LEVEL3", precision = 12, scale = 0, nullable = true)
    private Long codIdElecLevel3;

    @Column(name = "COD_ID_ELEC_LEVEL4", precision = 12, scale = 0, nullable = true)
    private Long codIdElecLevel4;

    @Column(name = "COD_ID_ELEC_LEVEL5", precision = 12, scale = 0, nullable = true)
    private Long codIdElecLevel5;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", precision = 15, scale = 0, nullable = true)
    private Long fi04N1;

    @Column(name = "FI04_V1", length = 100, nullable = true)
    private String fi04V1;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1, nullable = true)
    private String fi04Lo1;

    @Transient
    private boolean electoralChkBox;

    public Long getLocewzmpId() {
        return locewzmpId;
    }

    public void setLocewzmpId(final Long locewzmpId) {
        this.locewzmpId = locewzmpId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public LocationMasEntity getLocationMasEntity() {
        return locationMasEntity;
    }

    public void setLocationMasEntity(
            final LocationMasEntity locationMasEntity) {
        this.locationMasEntity = locationMasEntity;
    }

    public Long getCodIdElecLevel1() {
        return codIdElecLevel1;
    }

    public void setCodIdElecLevel1(final Long codIdElecLevel1) {
        this.codIdElecLevel1 = codIdElecLevel1;
    }

    public Long getCodIdElecLevel2() {
        return codIdElecLevel2;
    }

    public void setCodIdElecLevel2(final Long codIdElecLevel2) {
        this.codIdElecLevel2 = codIdElecLevel2;
    }

    public Long getCodIdElecLevel3() {
        return codIdElecLevel3;
    }

    public void setCodIdElecLevel3(final Long codIdElecLevel3) {
        this.codIdElecLevel3 = codIdElecLevel3;
    }

    public Long getCodIdElecLevel4() {
        return codIdElecLevel4;
    }

    public void setCodIdElecLevel4(final Long codIdElecLevel4) {
        this.codIdElecLevel4 = codIdElecLevel4;
    }

    public Long getCodIdElecLevel5() {
        return codIdElecLevel5;
    }

    public void setCodIdElecLevel5(final Long codIdElecLevel5) {
        this.codIdElecLevel5 = codIdElecLevel5;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public boolean isElectoralChkBox() {
        return electoralChkBox;
    }

    public void setElectoralChkBox(final boolean electoralChkBox) {
        this.electoralChkBox = electoralChkBox;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOCATION_ELECT_WARDZONE", "LOCEWZMP_ID" };
    }

}