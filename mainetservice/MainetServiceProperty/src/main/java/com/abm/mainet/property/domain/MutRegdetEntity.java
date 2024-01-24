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
@Table(name = "tb_as_mut_regdet")
public class MutRegdetEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5525635011665579430L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MUTD_ID")
    private long mutdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MUT_ID", nullable = false)
    private MutationRegistrationEntity mutId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MUTD_ADDRESS")
    private String address;

    @Column(name = "MUTD_FNAME")
    private String fatherName;

    @Column(name = "MUTD_GEN")
    private Long gender;

    @Column(name = "MUTD_MOBILENO")
    private String mobileno;

    @Column(name = "MUTD_NAME")
    private String personName;

    @Column(name = "MUTD_REGTYPE")
    private String regtype;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_mut_regdet", "MUTD_ID" };
    }

    public MutationRegistrationEntity getMutId() {
        return mutId;
    }

    public void setMutId(MutationRegistrationEntity mutId) {
        this.mutId = mutId;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public long getMutdId() {
        return mutdId;
    }

    public void setMutdId(long mutdId) {
        this.mutdId = mutdId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Long getGender() {
        return gender;
    }

    public void setGender(Long gender) {
        this.gender = gender;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRegtype() {
        return regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }
}
