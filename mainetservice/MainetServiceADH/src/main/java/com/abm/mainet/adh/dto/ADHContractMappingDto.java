package com.abm.mainet.adh.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cherupelli.srikanth
 * @since 11 November 2019
 */
public class ADHContractMappingDto implements Serializable {

    private static final long serialVersionUID = -4514655843820608251L;

    private Long contHrdMapId;

    private Long contractId;

    private Long HoardingId;

    private Long contMapAuthBy;

    private Date contAuthDate;

    private String contMapActive;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMacUpd;

    private List<HoardingMasterDto> hoardingMasterList = new ArrayList<>();

    public Long getContHrdMapId() {
	return contHrdMapId;
    }

    public void setContHrdMapId(Long contHrdMapId) {
	this.contHrdMapId = contHrdMapId;
    }

    public Long getContractId() {
	return contractId;
    }

    public void setContractId(Long contractId) {
	this.contractId = contractId;
    }

    public Long getHoardingId() {
	return HoardingId;
    }

    public void setHoardingId(Long hoardingId) {
	HoardingId = hoardingId;
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

    public List<HoardingMasterDto> getHoardingMasterList() {
	return hoardingMasterList;
    }

    public void setHoardingMasterList(List<HoardingMasterDto> hoardingMasterList) {
	this.hoardingMasterList = hoardingMasterList;
    }

}
