package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_transfer_owner_dtl")
public class PropertyTransferOwnerEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2318681542562026735L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OWNER_DTL_ID")
    private long ownerDtlId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_MST_ID")
    private PropertyTransferMasterEntity tbAsTransferrMast;

    @Column(name = "ACTIVE")
    private String active;

    @Column(name = "ADDHARNO")
    private Long addharno;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "ASS_NO")
    private String assNo;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "GENDER_ID")
    private Long genderId;

    @Column(name = "GUARDIAN_NAME")
    private String guardianName;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MOBILENO")
    private String mobileno;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "OTYPE")
    private String otype;

    @Column(name = "OWNER_NAME")
    private String ownerName;

    @Column(name = "PANNO")
    private String panno;

    @Column(name = "PROPERTY_SHARE")
    private Long propertyShare;

    @Column(name = "RELATION_ID")
    private Long relationId;

    @Column(name = "SM_SERVICE_ID")
    private Long smServiceId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "EMAIL")
    private String eMail;
    
    @Column(name = "owner_name_reg")
    private String assoOwnerNameReg;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_transfer_owner_dtl", "OWNER_DTL_ID" };
    }

    public PropertyTransferOwnerEntity() {
    }

    public long getOwnerDtlId() {
        return ownerDtlId;
    }

    public void setOwnerDtlId(long ownerDtlId) {
        this.ownerDtlId = ownerDtlId;
    }

    public PropertyTransferMasterEntity getTbAsTransferrMast() {
        return tbAsTransferrMast;
    }

    public void setTbAsTransferrMast(PropertyTransferMasterEntity tbAsTransferrMast) {
        this.tbAsTransferrMast = tbAsTransferrMast;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getAddharno() {
        return addharno;
    }

    public void setAddharno(Long addharno) {
        this.addharno = addharno;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
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

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public Long getPropertyShare() {
        return propertyShare;
    }

    public void setPropertyShare(Long propertyShare) {
        this.propertyShare = propertyShare;
    }

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

	public String getAssoOwnerNameReg() {
		return assoOwnerNameReg;
	}

	public void setAssoOwnerNameReg(String assoOwnerNameReg) {
		this.assoOwnerNameReg = assoOwnerNameReg;
	}

    
}
