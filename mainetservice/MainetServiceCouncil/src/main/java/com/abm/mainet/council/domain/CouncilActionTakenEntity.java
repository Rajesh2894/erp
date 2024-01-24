package com.abm.mainet.council.domain;

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
 * @author pooja.maske
 * @since 06 December 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_PROPOSAL_ACTIONTAKEN")
public class CouncilActionTakenEntity implements Serializable {

    private static final long serialVersionUID = -1024276685618339043L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAT_ID")
    private Long patId;

    @Temporal(TemporalType.DATE)
    @Column(name = "PAT_DATE", nullable = true)
    private Date patDate;

    @Column(name = "DP_DEPTID", nullable = true)
    private Long patDepId;

    @Column(name = "EMPID", nullable = true)
    private Long patEmpId;

    @Column(name = "PAT_DETAIL", length = 1000, nullable = true)
    private String patDetails;

    @Column(name = "PROPOSAL_ID", nullable = false)
    private Long proposalId;

    @Column(name = "ORGID", nullable = false, updatable = true)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public Date getPatDate() {
        return patDate;
    }

    public void setPatDate(Date patDate) {
        this.patDate = patDate;
    }

    public Long getPatDepId() {
        return patDepId;
    }

    public void setPatDepId(Long patDepId) {
        this.patDepId = patDepId;
    }

    public Long getPatEmpId() {
        return patEmpId;
    }

    public void setPatEmpId(Long patEmpId) {
        this.patEmpId = patEmpId;
    }

    public String getPatDetails() {
        return patDetails;
    }

    public void setPatDetails(String patDetails) {
        this.patDetails = patDetails;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_PROPOSAL_ACTIONTAKEN", "PAT_ID" };
    }
}
