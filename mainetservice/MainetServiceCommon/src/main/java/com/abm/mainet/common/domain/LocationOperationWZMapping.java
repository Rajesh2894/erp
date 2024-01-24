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
 * @comment This table stores mapping Entry for Location and Operational Ward Zone
 */
@Entity
@Table(name = "TB_LOCATION_OPER_WARDZONE")
public class LocationOperationWZMapping implements Serializable {

    private static final long serialVersionUID = -2420360280149206549L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOCOWZMP_ID", precision = 12, scale = 0, nullable = false)
    private Long locowzmpId;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @ManyToOne(cascade = CascadeType.ALL , fetch=  FetchType.LAZY)
    @JoinColumn(name = "LOC_ID")
    private LocationMasEntity locationMasEntity;

    @Column(name = "COD_ID_OPER_LEVEL1", precision = 12, scale = 0, nullable = true)
    private Long codIdOperLevel1;

    @Column(name = "COD_ID_OPER_LEVEL2", precision = 12, scale = 0, nullable = true)
    private Long codIdOperLevel2;

    @Column(name = "COD_ID_OPER_LEVEL3", precision = 12, scale = 0, nullable = true)
    private Long codIdOperLevel3;

    @Column(name = "COD_ID_OPER_LEVEL4", precision = 12, scale = 0, nullable = true)
    private Long codIdOperLevel4;

    @Column(name = "COD_ID_OPER_LEVEL5", precision = 12, scale = 0, nullable = true)
    private Long codIdOperLevel5;

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

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptId;

    @Transient
    private boolean opertionalChkBox;

    public Long getLocowzmpId() {
        return locowzmpId;
    }

    public void setLocowzmpId(final Long locowzmpId) {
        this.locowzmpId = locowzmpId;
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

    public Long getCodIdOperLevel1() {
        return codIdOperLevel1;
    }

    public void setCodIdOperLevel1(final Long codIdOperLevel1) {
        this.codIdOperLevel1 = codIdOperLevel1;
    }

    public Long getCodIdOperLevel2() {
        return codIdOperLevel2;
    }

    public void setCodIdOperLevel2(final Long codIdOperLevel2) {
        this.codIdOperLevel2 = codIdOperLevel2;
    }

    public Long getCodIdOperLevel3() {
        return codIdOperLevel3;
    }

    public void setCodIdOperLevel3(final Long codIdOperLevel3) {
        this.codIdOperLevel3 = codIdOperLevel3;
    }

    public Long getCodIdOperLevel4() {
        return codIdOperLevel4;
    }

    public void setCodIdOperLevel4(final Long codIdOperLevel4) {
        this.codIdOperLevel4 = codIdOperLevel4;
    }

    public Long getCodIdOperLevel5() {
        return codIdOperLevel5;
    }

    public void setCodIdOperLevel5(final Long codIdOperLevel5) {
        this.codIdOperLevel5 = codIdOperLevel5;
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

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public boolean isOpertionalChkBox() {
        return opertionalChkBox;
    }

    public void setOpertionalChkBox(final boolean opertionalChkBox) {
        this.opertionalChkBox = opertionalChkBox;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOCATION_OPER_WARDZONE", "LOCOWZMP_ID" };
    }

}