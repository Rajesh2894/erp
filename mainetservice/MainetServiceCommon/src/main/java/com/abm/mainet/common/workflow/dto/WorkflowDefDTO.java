package com.abm.mainet.common.workflow.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author umashanker.kanaujiya
 *
 */
public class WorkflowDefDTO extends AbstractFormModel {

    
    private static final long serialVersionUID = -3350222606864788567L;

    public WorkflowDefDTO() {
        initializeLookupFields("WWZ");
    }

    private long wdId;

    private String wdName;

    private String wdNameRegional;

    private Organisation wdOrgid;

    private Long wdServiceId;

    private String wdMode;

    private Long wdLang;

    private String wdStatus;

    private String wdIsactive;

    private Date lmodDate;

    private Long createdBy;

    private Date updatedDate;

    private Employee updatedBy;

    private String isDeleted;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;

    private String wdDesc;

    private Department wdDeptId;

    private List<WorkflowEventDTO> workFlowEventDTO;

    private String weIdlist;

    private String wardZoneType;
    // for ward , zone, block
    private Long dwzId1;
    private Long dwzId2;
    private Long dwzId3;
    private Long dwzId4;
    private Long dwzId5;
    private String modeType;
    private Long complaintType;
    private Long complaintSubType;

    private String prefix;
    private boolean prefixFound;

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        
        return "dwzId";
    }

    /**
     * @return the weIdlist
     */
    public String getWeIdlist() {
        return weIdlist;
    }

    /**
     * @param weIdlist the weIdlist to set
     */
    public void setWeIdlist(final String weIdlist) {
        this.weIdlist = weIdlist;
    }

    /**
     * @return the wdId
     */
    public long getWdId() {
        return wdId;
    }

    /**
     * @param wdId the wdId to set
     */
    public void setWdId(final long wdId) {
        this.wdId = wdId;
    }

    /**
     * @return the wdName
     */
    public String getWdName() {
        return wdName;
    }

    /**
     * @param wdName the wdName to set
     */
    public void setWdName(final String wdName) {
        this.wdName = wdName;
    }

    /**
     * @return the wdNameRegional
     */
    public String getWdNameRegional() {
        return wdNameRegional;
    }

    /**
     * @param wdNameRegional the wdNameRegional to set
     */
    public void setWdNameRegional(final String wdNameRegional) {
        this.wdNameRegional = wdNameRegional;
    }

    /**
     * @return the wdServiceId
     */
    public Long getWdServiceId() {
        return wdServiceId;
    }

    /**
     * @param wdServiceId the wdServiceId to set
     */
    public void setWdServiceId(final Long wdServiceId) {
        this.wdServiceId = wdServiceId;
    }

    /**
     * @return the wdMode
     */
    public String getWdMode() {
        return wdMode;
    }

    /**
     * @param wdMode the wdMode to set
     */
    public void setWdMode(final String wdMode) {
        this.wdMode = wdMode;
    }

    /**
     * @return the wdLang
     */
    public Long getWdLang() {
        return wdLang;
    }

    /**
     * @param wdLang the wdLang to set
     */
    public void setWdLang(final Long wdLang) {
        this.wdLang = wdLang;
    }

    /**
     * @return the wdStatus
     */
    public String getWdStatus() {
        return wdStatus;
    }

    /**
     * @param wdStatus the wdStatus to set
     */
    public void setWdStatus(final String wdStatus) {
        this.wdStatus = wdStatus;
    }

    /**
     * @return the wdIsactive
     */
    public String getWdIsactive() {
        return wdIsactive;
    }

    /**
     * @param wdIsactive the wdIsactive to set
     */
    public void setWdIsactive(final String wdIsactive) {
        this.wdIsactive = wdIsactive;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the updatedBy
     */
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(final Long complaintType) {
        this.complaintType = complaintType;
    }

    public Long getComplaintSubType() {
        return complaintSubType;
    }

    public void setComplaintSubType(final Long complaintSubType) {
        this.complaintSubType = complaintSubType;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the wdDesc
     */
    public String getWdDesc() {
        return wdDesc;
    }

    /**
     * @param wdDesc the wdDesc to set
     */
    public void setWdDesc(final String wdDesc) {
        this.wdDesc = wdDesc;
    }

    /**
     * @return the wdOrgid
     */
    public Organisation getWdOrgid() {
        return wdOrgid;
    }

    /**
     * @param wdOrgid the wdOrgid to set
     */
    public void setWdOrgid(final Organisation wdOrgid) {
        this.wdOrgid = wdOrgid;
    }

    /**
     * @return the wdDeptId
     */
    public Department getWdDeptId() {
        return wdDeptId;
    }

    /**
     * @param wdDeptId the wdDeptId to set
     */
    public void setWdDeptId(final Department wdDeptId) {
        this.wdDeptId = wdDeptId;
    }

    /**
     * @return the workFlowEventDTO
     */
    public List<WorkflowEventDTO> getWorkFlowEventDTO() {
        return workFlowEventDTO;
    }

    /**
     * @param workFlowEventDTO the workFlowEventDTO to set
     */
    public void setWorkFlowEventDTO(final List<WorkflowEventDTO> workFlowEventDTO) {
        this.workFlowEventDTO = workFlowEventDTO;
    }

    public String getWardZoneType() {
        return wardZoneType;
    }

    public void setWardZoneType(final String wardZoneType) {
        this.wardZoneType = wardZoneType;
    }

    public Long getDwzId1() {
        return dwzId1;
    }

    public void setDwzId1(final Long dwzId1) {
        this.dwzId1 = dwzId1;
    }

    public Long getDwzId2() {
        return dwzId2;
    }

    public void setDwzId2(final Long dwzId2) {
        this.dwzId2 = dwzId2;
    }

    public Long getDwzId3() {
        return dwzId3;
    }

    public void setDwzId3(final Long dwzId3) {
        this.dwzId3 = dwzId3;
    }

    public Long getDwzId4() {
        return dwzId4;
    }

    public void setDwzId4(final Long dwzId4) {
        this.dwzId4 = dwzId4;
    }

    public Long getDwzId5() {
        return dwzId5;
    }

    public void setDwzId5(final Long dwzId5) {
        this.dwzId5 = dwzId5;
    }

    public String getModeType() {
        return modeType;
    }

    public void setModeType(final String modeType) {
        this.modeType = modeType;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    public boolean isPrefixFound() {
        return prefixFound;
    }

    public void setPrefixFound(final boolean prefixFound) {
        this.prefixFound = prefixFound;
    }

}
