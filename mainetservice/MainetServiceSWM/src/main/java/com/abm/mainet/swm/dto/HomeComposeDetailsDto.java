/**
 * 
 */
package com.abm.mainet.swm.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.swm.domain.SolidWasteConsumerMaster;

/**
 * @author cherupelli.srikanth
 *
 */
public class HomeComposeDetailsDto {

    private Long swHomeCompId;

    private String registrationId;

    private Long swHomeComItem;

    private Long swHomeCompCollection;

    private Long swHomeCompPepared;

    private Date swHomeCompDate;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;
    
    @JsonIgnore
    private SolidWasteConsumerMasterDTO solidWasteMasterDto;
    
    public SolidWasteConsumerMasterDTO getSolidWasteMasterDto() {
        return solidWasteMasterDto;
    }

    public void setSolidWasteMasterDto(SolidWasteConsumerMasterDTO solidWasteMasterDto) {
        this.solidWasteMasterDto = solidWasteMasterDto;
    }

    public Long getSwHomeCompId() {
        return swHomeCompId;
    }

    public void setSwHomeCompId(Long swHomeCompId) {
        this.swHomeCompId = swHomeCompId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public Long getSwHomeComItem() {
        return swHomeComItem;
    }

    public void setSwHomeComItem(Long swHomeComItem) {
        this.swHomeComItem = swHomeComItem;
    }

    public Long getSwHomeCompCollection() {
        return swHomeCompCollection;
    }

    public void setSwHomeCompCollection(Long swHomeCompCollection) {
        this.swHomeCompCollection = swHomeCompCollection;
    }

    public Long getSwHomeCompPepared() {
        return swHomeCompPepared;
    }

    public void setSwHomeCompPepared(Long swHomeCompPepared) {
        this.swHomeCompPepared = swHomeCompPepared;
    }

    public Date getSwHomeCompDate() {
        return swHomeCompDate;
    }

    public void setSwHomeCompDate(Date swHomeCompDate) {
        this.swHomeCompDate = swHomeCompDate;
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
    
    
    

}
