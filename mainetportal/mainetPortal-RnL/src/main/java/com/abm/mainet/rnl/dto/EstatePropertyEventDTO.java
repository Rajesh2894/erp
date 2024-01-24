package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 18 june 2019
 */
public class EstatePropertyEventDTO implements Serializable {

    private static final long serialVersionUID = -1723458737967811379L;

    private Long propEventId;
    private Long propId;
    private Long propEvent;
    private String propAllowFlag;
    private String eventStatus;
    private Long orgId;
    private long langId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String propEventDesc;
    private String cpdDesc;
    private Integer categoryId;
    
    private String cpdDescMar;

    public Long getPropEventId() {
        return propEventId;
    }

    public void setPropEventId(Long propEventId) {
        this.propEventId = propEventId;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getPropEvent() {
        return propEvent;
    }

    public void setPropEvent(Long propEvent) {
        this.propEvent = propEvent;
    }

    public String getPropAllowFlag() {
        return propAllowFlag;
    }

    public void setPropAllowFlag(String propAllowFlag) {
        this.propAllowFlag = propAllowFlag;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(long langId) {
        this.langId = langId;
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

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String getPropEventDesc() {
        return propEventDesc;
    }

    public void setPropEventDesc(String propEventDesc) {
        this.propEventDesc = propEventDesc;
    }

    public String getCpdDesc() {
        return cpdDesc;
    }

    public void setCpdDesc(String cpdDesc) {
        this.cpdDesc = cpdDesc;
    }

    public String getCpdDescMar() {
        return cpdDescMar;
    }

    public void setCpdDescMar(String cpdDescMar) {
        this.cpdDescMar = cpdDescMar;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }





}
