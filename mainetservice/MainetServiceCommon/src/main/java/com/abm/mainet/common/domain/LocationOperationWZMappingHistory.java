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
@Table(name = "TB_LOCATION_OPER_WARDZONE_HIS")
public class LocationOperationWZMappingHistory implements Serializable {

    private static final long serialVersionUID = -2420360280149206549L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LOCOWZMP_ID_H", precision = 12, scale = 0, nullable = false)
    private Long locowzmpHistId;
    
    @Column(name = "LOCOWZMP_ID", precision = 12, scale = 0, nullable = false)
    private Long locowzmpId;
    
   

	@Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "LOC_ID", nullable = false, updatable = false)
    private Long locId;

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
    
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;


    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;



    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptId;

    @Transient
    private boolean opertionalChkBox;
    
    @Column(name = "H_STATUS" , length =1 )
    private String status;

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLocowzmpId() {
        return locowzmpId;
    }

    public void setLocowzmpId(final Long locowzmpId) {
        this.locowzmpId = locowzmpId;
    }
    
    public Long getLocowzmpHistId() {
		return locowzmpHistId;
	}

	public void setLocowzmpHistId(Long locowzmpHistId) {
		this.locowzmpHistId = locowzmpHistId;
	}

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
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
        return new String[] { "AUT", "TB_LOCATION_OPER_WARDZONE_HIS", "LOCOWZMP_ID_H" };
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

}