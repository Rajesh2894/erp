package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Anwarul.Hassan
 * @since 25-Oct-2019
 */
@Entity
@Table(name = "TB_ADH_INESPMAS")
public class InspectionEntryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INES_ID")
    private Long inesId;

    @Column(name = "ADH_ID")
    private Long adhId;

    @Column(name = "INES_DT")
    private Date inesDate;

    @Column(name = "INES_EMPID")
    private Long inesEmpId;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    // @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inspectionEntryEntity", cascade = CascadeType.ALL)
    private List<InspectionEntryDetEntity> inspectionEntryDetEntity = new ArrayList<InspectionEntryDetEntity>();

    @Column(name = "NO_OF_DAYS")
    private Long noOfDays;

    @Column(name = "NOTICE_GEN_FLAG")
    private String noticeGenFlag;

    public Long getInesId() {
        return inesId;
    }

    public void setInesId(Long inesId) {
        this.inesId = inesId;
    }

    public Long getAdhId() {
        return adhId;
    }

    public void setAdhId(Long adhId) {
        this.adhId = adhId;
    }

    public Date getInesDate() {
        return inesDate;
    }

    public void setInesDate(Date inesDate) {
        this.inesDate = inesDate;
    }

    public Long getInesEmpId() {
        return inesEmpId;
    }

    public void setInesEmpId(Long inesEmpId) {
        this.inesEmpId = inesEmpId;
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

    public List<InspectionEntryDetEntity> getInspectionEntryDetEntity() {
        return inspectionEntryDetEntity;
    }

    public void setInspectionEntryDetEntity(List<InspectionEntryDetEntity> inspectionEntryDetEntity) {
        this.inspectionEntryDetEntity = inspectionEntryDetEntity;
    }

    public Long getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(Long noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getNoticeGenFlag() {
        return noticeGenFlag;
    }

    public void setNoticeGenFlag(String noticeGenFlag) {
        this.noticeGenFlag = noticeGenFlag;
    }

    public String[] getPkValues() {
        return new String[] { "ADH", "TB_ADH_INESPMAS", "INES_ID" };
    }
}
