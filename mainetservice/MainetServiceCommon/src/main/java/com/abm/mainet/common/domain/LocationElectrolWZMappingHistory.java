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
@Table(name = "TB_LOCATION_ELECT_WARDZONE_HIS")
public class LocationElectrolWZMappingHistory implements Serializable {

    private static final long serialVersionUID = -8406311573317183674L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOCEWZMP_ID_H", precision = 12, scale = 0, nullable = false)
    private Long locewzmpHistId;
    

	@Column(name = "LOCEWZMP_ID", precision = 12, scale = 0, nullable = false)
    private Long locewzmpId;
    

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;
    
    @Column(name = "LOC_ID")
    private Long locId;

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

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;
    
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Transient
    private boolean electoralChkBox;
    
    @Column(name = "H_STATUS" , length =1 )
    private String status;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLocewzmpId() {
        return locewzmpId;
    }

    public void setLocewzmpId(final Long locewzmpId) {
        this.locewzmpId = locewzmpId;
    }

    public Long getLocewzmpHistId() {
		return locewzmpHistId;
	}

	public void setLocewzmpHistId(Long locewzmpHistId) {
		this.locewzmpHistId = locewzmpHistId;
	}
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
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


    public boolean isElectoralChkBox() {
        return electoralChkBox;
    }

    public void setElectoralChkBox(final boolean electoralChkBox) {
        this.electoralChkBox = electoralChkBox;
    }

    public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
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

	public String[] getPkValues() {
        return new String[] { "AUT", "TB_LOCATION_ELECT_WARDZONE_HIS", "LOCEWZMP_ID_H" };
    }

}