package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_CUSTMAST")
public class CustomerMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CM_CUSTID", nullable = false)
    private Long custId;

    @Column(name = "CM_CUSTTYPE")
    private Long custType;

    @Column(name = "CM_CUSTNAME")
    private String custName;

    @Column(name = "CM_EMAIL_ID")
    private String custEmailId;

    @Column(name = "CM_CUSTADD")
    private String custAddress;

    @Column(name = "CM_MOBILE_NO")
    private String custMobNo;

    @Column(name = "CM_PAN_NUMBER")
    private String custPANNo;

    @Column(name = "CM_TAN_NUMBER")
    private String custTANNo;

    @Column(name = "CM_UID_NO")
    private Long custUIDNo;

    @Column(name = "CM_TIN_NUMBER")
    private String custTINNo;

    @Column(name = "CM_GSTNO")
    private String custGSTNo;

    @Column(name = "CM_STATUS")
    private Long custStatus;

    @Column(name = "CM_REFNO")
    private String custRefNo;

    @Column(name = "CM_LOC")
    private String custLocation;

    @Column(name = "CM_KHASARANO")
    private String custKhasaraNo;

    @Column(name = "CM_DISTRICT")
    private String custDistrict;

    @Column(name = "CM_PINCODE")
    private String custPinCode;

    @Column(name = "CM_REMARK")
    private String remark;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getCustType() {
        return custType;
    }

    public void setCustType(Long custType) {
        this.custType = custType;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustEmailId() {
        return custEmailId;
    }

    public void setCustEmailId(String custEmailId) {
        this.custEmailId = custEmailId;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustRefNo() {
        return custRefNo;
    }

    public void setCustRefNo(String custRefNo) {
        this.custRefNo = custRefNo;
    }

    public String getCustLocation() {
        return custLocation;
    }

    public void setCustLocation(String custLocation) {
        this.custLocation = custLocation;
    }

    public String getCustKhasaraNo() {
        return custKhasaraNo;
    }

    public void setCustKhasaraNo(String custKhasaraNo) {
        this.custKhasaraNo = custKhasaraNo;
    }

    public String getCustDistrict() {
        return custDistrict;
    }

    public void setCustDistrict(String custDistrict) {
        this.custDistrict = custDistrict;
    }

    public String getCustPinCode() {
        return custPinCode;
    }

    public void setCustPinCode(String custPinCode) {
        this.custPinCode = custPinCode;
    }

    public String getCustMobNo() {
        return custMobNo;
    }

    public void setCustMobNo(String custMobNo) {
        this.custMobNo = custMobNo;
    }

    public String getCustPANNo() {
        return custPANNo;
    }

    public void setCustPANNo(String custPANNo) {
        this.custPANNo = custPANNo;
    }

    public String getCustTANNo() {
        return custTANNo;
    }

    public void setCustTANNo(String custTANNo) {
        this.custTANNo = custTANNo;
    }

    public String getCustTINNo() {
        return custTINNo;
    }

    public void setCustTINNo(String custTINNo) {
        this.custTINNo = custTINNo;
    }

    public String getCustGSTNo() {
        return custGSTNo;
    }

    public void setCustGSTNo(String custGSTNo) {
        this.custGSTNo = custGSTNo;
    }

    public Long getCustUIDNo() {
        return custUIDNo;
    }

    public void setCustUIDNo(Long custUIDNo) {
        this.custUIDNo = custUIDNo;
    }

    public Long getCustStatus() {
        return custStatus;
    }

    public void setCustStatus(Long custStatus) {
        this.custStatus = custStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String[] getPkValues() {

        return new String[] { "AC", "TB_CUSTMAST", "CM_CUSTID" };
    }

}