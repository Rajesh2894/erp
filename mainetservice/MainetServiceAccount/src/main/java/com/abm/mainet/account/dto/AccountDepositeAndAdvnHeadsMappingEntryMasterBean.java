package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDepositeAndAdvnHeadsMappingEntryMasterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long mappingType;
    private Long advancedType;
    private Long depositeType;
    private Long deptId;

    private List<DeasMasterEntryDto> listOfDto;

    private Long orgid;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @return the mappingType
     */
    public Long getMappingType() {
        return mappingType;
    }

    /**
     * @param mappingType the mappingType to set
     */
    public void setMappingType(final Long mappingType) {
        this.mappingType = mappingType;
    }

    /**
     * @return the listOfDto
     */
    public List<DeasMasterEntryDto> getListOfDto() {
        return listOfDto;
    }

    /**
     * @param listOfDto the listOfDto to set
     */
    public void setListOfDto(final List<DeasMasterEntryDto> listOfDto) {
        this.listOfDto = listOfDto;
    }

    /**
     * @return the depositeType
     */
    public Long getDepositeType() {
        return depositeType;
    }

    /**
     * @param depositeType the depositeType to set
     */
    public void setDepositeType(final Long depositeType) {
        this.depositeType = depositeType;
    }

    /**
     * @return the advancedType
     */
    public Long getAdvancedType() {
        return advancedType;
    }

    /**
     * @param advancedType the advancedType to set
     */
    public void setAdvancedType(final Long advancedType) {
        this.advancedType = advancedType;
    }

    /**
     * @return the deptId
     */
    public Long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }
}
