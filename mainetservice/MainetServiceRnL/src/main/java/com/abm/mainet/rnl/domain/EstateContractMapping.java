package com.abm.mainet.rnl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.rnl.domain.EstateEntity;
import com.abm.mainet.rnl.domain.EstatePropertyEntity;


/**
 * @author ritesh.patil
 *
 * Estate Contract Mapping entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_EST_CONTRACT_MAPPING")
public class EstateContractMapping {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONT_EST_MAPID", nullable = false)
    private Long mappingId;

    @OneToOne
    @JoinColumn(name = "CONT_ID", referencedColumnName = "CONT_ID")
    private ContractMastEntity contractMastEntity;

    @ManyToOne
    @JoinColumn(name = "ES_ID", referencedColumnName = "ES_ID")
    private EstateEntity estateEntity;

    @ManyToOne
    @JoinColumn(name = "PROP_ID", referencedColumnName = "PROP_ID")
    private EstatePropertyEntity estatePropertyEntity;

    @Temporal(TemporalType.DATE)
    @Column(name = "CONT_MAP_AUT_DATE")
    private Date authDate;

    @Column(name = "CONT_MAP_AUT_BY")
    private Long authBy;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CONT_MAP_AUT_STATUS")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "CONT_MAP_ACTIVE")
    private String active;
    
    @Column(name = "WF_REFNO ", nullable = true)
    private String wfRefno;
    
    @Column(name = "TYPES_AFFECTED", nullable = true)
    private Long typesOfAffected;

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(final Long mappingId) {
        this.mappingId = mappingId;
    }

    public ContractMastEntity getContractMastEntity() {
        return contractMastEntity;
    }

    public void setContractMastEntity(final ContractMastEntity contractMastEntity) {
        this.contractMastEntity = contractMastEntity;
    }

    public EstateEntity getEstateEntity() {
        return estateEntity;
    }

    public void setEstateEntity(final EstateEntity estateEntity) {
        this.estateEntity = estateEntity;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(final Date authDate) {
        this.authDate = authDate;
    }

    public Long getAuthBy() {
        return authBy;
    }

    public void setAuthBy(final Long authBy) {
        this.authBy = authBy;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.RnLDetailEntity.TB_RL_EST,
                MainetConstants.RnLDetailEntity.CONT_EST };
    }

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

    public EstatePropertyEntity getEstatePropertyEntity() {
        return estatePropertyEntity;
    }

    public void setEstatePropertyEntity(final EstatePropertyEntity estatePropertyEntity) {
        this.estatePropertyEntity = estatePropertyEntity;
    }

	public String getWfRefno() {
		return wfRefno;
	}

	public void setWfRefno(String wfRefno) {
		this.wfRefno = wfRefno;
	}

	public Long getTypesOfAffected() {
		return typesOfAffected;
	}

	public void setTypesOfAffected(Long typesOfAffected) {
		this.typesOfAffected = typesOfAffected;
	}
   
}
