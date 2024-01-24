package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerMasterDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long custId;

    private Long custType;

    private String custName;

    private String custEmailId;

    private String custAddress;

    private String custMobNo;

    private String custPANNo;

    private String custTANNo;

    private Long custUIDNo;

    private String custTINNo;

    private String custGSTNo;

    private Long custStatus;

    private String custRefNo;

    private String custLocation;

    private String custKhasaraNo;

    private String custDistrict;

    private String custPinCode;

    private String remark;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String custTypeStr;

    private String custUIDNoStr;

    private String custTINNoStr;

    private String custMobNoStr;

    private List<Object> custParameter = new ArrayList<>();

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

    public String getCustTypeStr() {
        return custTypeStr;
    }

    public void setCustTypeStr(String custTypeStr) {
        this.custTypeStr = custTypeStr;
    }

    public String getCustUIDNoStr() {
        return custUIDNoStr;
    }

    public void setCustUIDNoStr(String custUIDNoStr) {
        this.custUIDNoStr = custUIDNoStr;
    }

    public String getCustTINNoStr() {
        return custTINNoStr;
    }

    public void setCustTINNoStr(String custTINNoStr) {
        this.custTINNoStr = custTINNoStr;
    }

    public String getCustMobNoStr() {
        return custMobNoStr;
    }

    public void setCustMobNoStr(String custMobNoStr) {
        this.custMobNoStr = custMobNoStr;
    }

    public List<Object> getCustParameter() {
        return custParameter;
    }

    public void setCustParameter(List<Object> custParameter) {
        this.custParameter = custParameter;
    }

}
