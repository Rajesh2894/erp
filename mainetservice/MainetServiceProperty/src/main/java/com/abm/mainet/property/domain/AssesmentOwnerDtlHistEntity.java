package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author anwarul.hassan
 * @since 07-Dec-2020
 */
@Entity
@Table(name = "TB_AS_ASSESMENT_OWNER_DTL_HIST")
public class AssesmentOwnerDtlHistEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 541294082869811078L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MN_ASSO_HIS_ID", nullable = false)
    private long proAssoHisId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "MN_ass_id")
    private Long mnAssId;

    @Column(name = "MN_ASS_NO")
    private String assNo;

    @Column(name = "MN_asso_active")
    private String assoActive;

    @Column(name = "MN_asso_addharno")
    private Long assoAddharno;

    @Column(name = "MN_ASSO_END_DATE")
    private Date assoEndDate;

    @Column(name = "MN_asso_guardian_name")
    private String assoGuardianName;

    @Column(name = "MN_asso_id")
    private Long proAssoId;

    @Column(name = "MN_asso_mobileno")
    private String assoMobileno;

    @Column(name = "MN_asso_otype")
    private String assoOType;

    @Column(name = "MN_asso_owner_name")
    private String assoOwnerName;

    @Column(name = "MN_asso_panno")
    private String assoPanno;

    @Column(name = "MN_ASSO_START_DATE")
    private Date assoStartDate;

    @Column(name = "MN_asso_type")
    private String assoType;

    @Column(name = "MN_gender_id")
    private Long genderId;

    @Column(name = "MN_property_share")
    private Long propertyShare;

    @Column(name = "MN_relation_id")
    private Long relationId;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "SM_SERVICE_ID", nullable = false)
    private Long smServiceId;

    @Column(name = "APM_APPLICATION_ID", nullable = false)
    private Long apmApplicationId;

    @Column(name = "MN_ASS_EMAIL")
    private String eMail;
    
    @Column(name = "MN_asso_owner_name_reg")
    private String assoOwnerNameReg;
    
    @Column(name = "MN_ASS_HIS_id")
    private long proAssMastHisId;
    
    @Column(name = "H_STATUS")
    private String hStatus;

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_ASSESMENT_OWNER_DTL_HIST", "MN_ASSO_HIS_ID" };

    }

    public long getProAssoHisId() {
        return proAssoHisId;
    }

    public void setProAssoHisId(long proAssoHisId) {
        this.proAssoHisId = proAssoHisId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getMnAssId() {
        return mnAssId;
    }

    public void setMnAssId(Long mnAssId) {
        this.mnAssId = mnAssId;
    }

    public String getAssoActive() {
        return assoActive;
    }

    public void setAssoActive(String assoActive) {
        this.assoActive = assoActive;
    }

    public Date getAssoEndDate() {
        return assoEndDate;
    }

    public void setAssoEndDate(Date assoEndDate) {
        this.assoEndDate = assoEndDate;
    }

    public String getAssoGuardianName() {
        return assoGuardianName;
    }

    public void setAssoGuardianName(String assoGuardianName) {
        this.assoGuardianName = assoGuardianName;
    }

    public String getAssoMobileno() {
        return assoMobileno;
    }

    public void setAssoMobileno(String assoMobileno) {
        this.assoMobileno = assoMobileno;
    }

    public String getAssoOwnerName() {
        return assoOwnerName;
    }

    public void setAssoOwnerName(String assoOwnerName) {
        this.assoOwnerName = assoOwnerName;
    }

    public String getAssoPanno() {
        return assoPanno;
    }

    public void setAssoPanno(String assoPanno) {
        this.assoPanno = assoPanno;
    }

    public Date getAssoStartDate() {
        return assoStartDate;
    }

    public void setAssoStartDate(Date assoStartDate) {
        this.assoStartDate = assoStartDate;
    }

    public String getAssoType() {
        return assoType;
    }

    public void setAssoType(String assoType) {
        this.assoType = assoType;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
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

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public Long getProAssoId() {
        return proAssoId;
    }

    public void setProAssoId(Long proAssoId) {
        this.proAssoId = proAssoId;
    }

    public Long getAssoAddharno() {
        return assoAddharno;
    }

    public void setAssoAddharno(Long assoAddharno) {
        this.assoAddharno = assoAddharno;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getAssoOType() {
        return assoOType;
    }

    public void setAssoOType(String assoOType) {
        this.assoOType = assoOType;
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

	public long getProAssMastHisId() {
		return proAssMastHisId;
	}

	public void setProAssMastHisId(long proAssMastHisId) {
		this.proAssMastHisId = proAssMastHisId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
	

    
}
