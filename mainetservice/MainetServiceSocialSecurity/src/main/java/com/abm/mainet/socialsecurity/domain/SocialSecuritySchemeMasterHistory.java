/**
 * 
 */
package com.abm.mainet.socialsecurity.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_SWD_SCHEME_MAST_HIST")
public class SocialSecuritySchemeMasterHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3211697449647754375L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SDSCH_HIST_ID", nullable = true)
    private Long schemeMstHisId;

    @Column(name = "SDSCH_ID", nullable = true)
    private Long schemeMstId;

    @Column(name = "SDSCH_SER_ID", nullable = true)
    private Long schemeNameId;

    @Column(name = "SDSCH_OBJ", nullable = true)
    private String objOfScheme;

    @Column(name = "SDSCH_ACTIVE", nullable = false)
    private String isSchmeActive;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;
    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;
    
    @Column(name = "RESOLUTION_NO", nullable = true)
	private String resolutionNo;
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESOLUTION_DATE", nullable = true)
	private Date resolutionDate;
	
	@Column(name = "FAMILY_DET_REQ", nullable = true)
	private String familyDetReq;
	
	@Column(name = "LIFE_CERTIFICATE_REQ", nullable = true)
	private String lifeCertificateReq;
	
	
    public String getLifeCertificateReq() {
		return lifeCertificateReq;
	}

	public void setLifeCertificateReq(String lifeCertificateReq) {
		this.lifeCertificateReq = lifeCertificateReq;
	}

	public static String[] getPkValues() {
        return new String[] { "SWD", "TB_SWD_SCHEME_MAST_HIST", "SDSCH_HIST_ID" };
    }

    public Long getSchemeMstHisId() {
        return schemeMstHisId;
    }

    public void setSchemeMstHisId(Long schemeMstHisId) {
        this.schemeMstHisId = schemeMstHisId;
    }

    public Long getSchemeMstId() {
        return schemeMstId;
    }

    public void setSchemeMstId(Long schemeMstId) {
        this.schemeMstId = schemeMstId;
    }

    public Long getSchemeNameId() {
        return schemeNameId;
    }

    public void setSchemeNameId(Long schemeNameId) {
        this.schemeNameId = schemeNameId;
    }

    public String getObjOfScheme() {
        return objOfScheme;
    }

    public void setObjOfScheme(String objOfScheme) {
        this.objOfScheme = objOfScheme;
    }

    public String getIsSchmeActive() {
        return isSchmeActive;
    }

    public void setIsSchmeActive(String isSchmeActive) {
        this.isSchmeActive = isSchmeActive;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

	public String getResolutionNo() {
		return resolutionNo;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public String getFamilyDetReq() {
		return familyDetReq;
	}

	public void setResolutionNo(String resolutionNo) {
		this.resolutionNo = resolutionNo;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public void setFamilyDetReq(String familyDetReq) {
		this.familyDetReq = familyDetReq;
	}

}
