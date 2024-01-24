package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.ContractMastEntity;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */
@Entity
@Table(name = "TB_ADH_CONTRACT_MAPPING")
public class ADHContractMappingEntity implements Serializable {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "cont_hrd_mapid")
    private Long contHrdMapId;

    @OneToOne
    @JoinColumn(name = "CONT_ID", referencedColumnName = "CONT_ID")
    private ContractMastEntity contractMastEntity;

    @ManyToOne
    @JoinColumn(name = "hrd_id", referencedColumnName = "hrd_id")
    private HoardingMasterEntity hoardingEntity;

    @Column(name = "cont_map_autby")
    private Long contMapAuthBy;

    @Column(name = "cont_map_autdate")
    private Date contAuthDate;

    @Column(name = "cont_map_active")
    private String contMapActive;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    public Long getContHrdMapId() {
	return contHrdMapId;
    }

    public void setContHrdMapId(Long contHrdMapId) {
	this.contHrdMapId = contHrdMapId;
    }

    public ContractMastEntity getContractMastEntity() {
	return contractMastEntity;
    }

    public void setContractMastEntity(ContractMastEntity contractMastEntity) {
	this.contractMastEntity = contractMastEntity;
    }

    public HoardingMasterEntity getHoardingEntity() {
	return hoardingEntity;
    }

    public void setHoardingEntity(HoardingMasterEntity hoardingEntity) {
	this.hoardingEntity = hoardingEntity;
    }

    public Long getContMapAuthBy() {
	return contMapAuthBy;
    }

    public void setContMapAuthBy(Long contMapAuthBy) {
	this.contMapAuthBy = contMapAuthBy;
    }

    public Date getContAuthDate() {
	return contAuthDate;
    }

    public void setContAuthDate(Date contAuthDate) {
	this.contAuthDate = contAuthDate;
    }

    public String getContMapActive() {
	return contMapActive;
    }

    public void setContMapActive(String contMapActive) {
	this.contMapActive = contMapActive;
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

    public String getLgIpMac() {
	return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
	this.lgIpMac = lgIpMac;
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

    public String getLgIpMacUpd() {
	return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
    }

    public String[] getPkValues() {
	return new String[] { "ADH", "TB_ADH_CONTRACT_MAPPING", "cont_hrd_mapid" };
    }
}
