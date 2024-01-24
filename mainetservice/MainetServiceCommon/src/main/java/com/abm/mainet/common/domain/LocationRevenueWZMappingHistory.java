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
 * @comment This table stores mapping Entry for Location and Revenue Ward Zone
 */
@Entity
@Table(name = "TB_LOC_REVENUE_WARDZONE_HIS")
public class LocationRevenueWZMappingHistory implements Serializable {

    private static final long serialVersionUID = -7718513111372529237L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOCRWZMP_ID_H", precision = 12, scale = 0, nullable = false)
    private Long locrwzmpHistId;
    
    @Column(name = "LOCRWZMP_ID", precision = 12, scale = 0, nullable = false)
    private Long locrwzmpId;

	@Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "COD_ID_REV_LEVEL1", precision = 12, scale = 0, nullable = true)
    private Long codIdRevLevel1;

    @Column(name = "COD_ID_REV_LEVEL2", precision = 12, scale = 0, nullable = true)
    private Long codIdRevLevel2;

    @Column(name = "COD_ID_REV_LEVEL3", precision = 12, scale = 0, nullable = true)
    private Long codIdRevLevel3;

    @Column(name = "COD_ID_REV_LEVEL4", precision = 12, scale = 0, nullable = true)
    private Long codIdRevLevel4;

    @Column(name = "COD_ID_REV_LEVEL5", precision = 12, scale = 0, nullable = true)
    private Long codIdRevLevel5;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "H_STATUS" , length =1 )
    private String status;

	@Transient
    private boolean revenueChkBox;

    public Long getLocrwzmpId() {
        return locrwzmpId;
    }

    public void setLocrwzmpId(final Long locrwzmpId) {
        this.locrwzmpId = locrwzmpId;
    }
    
    public Long getLocrwzmpHistId() {
		return locrwzmpHistId;
	}

	public void setLocrwzmpHistId(Long locrwzmpHistId) {
		this.locrwzmpHistId = locrwzmpHistId;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCodIdRevLevel1() {
        return codIdRevLevel1;
    }

    public void setCodIdRevLevel1(final Long codIdRevLevel1) {
        this.codIdRevLevel1 = codIdRevLevel1;
    }

    public Long getCodIdRevLevel2() {
        return codIdRevLevel2;
    }

    public void setCodIdRevLevel2(final Long codIdRevLevel2) {
        this.codIdRevLevel2 = codIdRevLevel2;
    }

    public Long getCodIdRevLevel3() {
        return codIdRevLevel3;
    }

    public void setCodIdRevLevel3(final Long codIdRevLevel3) {
        this.codIdRevLevel3 = codIdRevLevel3;
    }

    public Long getCodIdRevLevel4() {
        return codIdRevLevel4;
    }

    public void setCodIdRevLevel4(final Long codIdRevLevel4) {
        this.codIdRevLevel4 = codIdRevLevel4;
    }

    public Long getCodIdRevLevel5() {
        return codIdRevLevel5;
    }

    public void setCodIdRevLevel5(final Long codIdRevLevel5) {
        this.codIdRevLevel5 = codIdRevLevel5;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
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
    
    public boolean isRevenueChkBox() {
        return revenueChkBox;
    }

    public void setRevenueChkBox(final boolean revenueChkBox) {
        this.revenueChkBox = revenueChkBox;
    }

    public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOC_REVENUE_WARDZONE_HIS", "LOCRWZMP_ID_H" };
    }

}