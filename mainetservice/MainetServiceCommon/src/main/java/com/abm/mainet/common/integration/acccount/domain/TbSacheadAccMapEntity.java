package com.abm.mainet.common.integration.acccount.domain;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TB_AC_RCPTHD_LINK")
public class TbSacheadAccMapEntity implements Serializable {

    private static final long serialVersionUID = 1L;
	
	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MAP_ID")
    private Long mapId;

    @Column(name = "SAC_HEAD_ID", nullable = false)
    private Long sacHeadId;

    @Column(name = "BA_ACCOUNT_ID", nullable = false)
    private Long baAccountId;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = false, length = 100)
    private String lgIpMacUpd;
    
	public Long getBaAccountId() {
		return baAccountId;
	}

	public void setBaAccountId(Long baAccountId) {
		this.baAccountId = baAccountId;
	}

	public Long getMapId() {
		return mapId;
	}

	public void setMapId(Long mapId) {
		this.mapId = mapId;
	}

	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(Long sacHeadId) {
		this.sacHeadId = sacHeadId;
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
	
	
	public String[] getPkValues() {
		return new String[] { "AC", "TB_AC_RCPTHD_LINK", "MAP_ID" };
	}

    
}
