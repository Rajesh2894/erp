package com.abm.mainet.vehiclemanagement.dto;

import java.util.Date;

import com.abm.mainet.common.constant.MainetConstants;


public class SLRMEmployeeMasterDTO {

    private Long empId;

    private Long desgId;

    private Long mrfId;

    private Long ttlId;

    private String empName;

    private String empMName;

    private String empLName;

    private String gender;

    private String empMobNo;

    private String empEmailId;

    private String empAddress;

    private String empAddress1;

    private String empPincode;

    private String empUId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;
    
    private String mrfName;
    
    private String desigName;
    
    private String deptName;

   
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getDesgId() {
        return desgId;
    }

    public void setDesgId(Long desgId) {
        this.desgId = desgId;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public Long getTtlId() {
        return ttlId;
    }

    public void setTtlId(Long ttlId) {
        this.ttlId = ttlId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpMName() {
        return empMName;
    }

    public void setEmpMName(String empMName) {
        this.empMName = empMName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmpMobNo() {
        return empMobNo;
    }

    public void setEmpMobNo(String empMobNo) {
        this.empMobNo = empMobNo;
    }

    public String getEmpEmailId() {
        return empEmailId;
    }

    public void setEmpEmailId(String empEmailId) {
        this.empEmailId = empEmailId;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAddress1() {
        return empAddress1;
    }

    public void setEmpAddress1(String empAddress1) {
        this.empAddress1 = empAddress1;
    }

    public String getEmpPincode() {
        return empPincode;
    }

    public void setEmpPincode(String empPincode) {
        this.empPincode = empPincode;
    }
    


	public String getEmpUId() {
		return empUId;
	}

	public void setEmpUId(String empUId) {
		this.empUId = empUId;
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

    public String getMrfName() {
        return mrfName;
    }

    public void setMrfName(String mrfName) {
        this.mrfName = mrfName;
    }

    public String getDesigName() {
        return desigName;
    }

    public void setDesigName(String desigName) {
        this.desigName = desigName;
    }
    
    public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getFullName() {
        return((getEmpName() != null ? getEmpName() : "") + MainetConstants.WHITE_SPACE + (getEmpLName() != null ? getEmpLName() : ""));
    }

    

}
