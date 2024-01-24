package com.abm.mainet.council.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pooja.maske
 * @since 06 December 2019
 */
public class CouncilActionTakenDto implements Serializable {

    private static final long serialVersionUID = 6406956666566114480L;

    private Long patId;

    private Date patDate;
    
    private String propDate;

    private Long patDepId;

    private Long patEmpId;

    private String patDetails;

    private Long proposalId;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String propDisableFlag;

    public Long getPatId() {
        return patId;
    }

    public void setPatId(Long patId) {
        this.patId = patId;
    }

    public Long getPatDepId() {
        return patDepId;
    }

    public Date getPatDate() {
        return patDate;
    }

    public void setPatDate(Date patDate) {
        this.patDate = patDate;
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

    public Long getProposalId() {
        return proposalId;
    }

    public void setProposalId(Long proposalId) {
        this.proposalId = proposalId;
    }

    public String getPropDisableFlag() {
        return propDisableFlag;
    }

    public void setPropDisableFlag(String propDisableFlag) {
        this.propDisableFlag = propDisableFlag;
    }

	public String getPropDate() {
		return propDate;
	}

	public void setPropDate(String propDate) {
		this.propDate = propDate;
	}
    

}
