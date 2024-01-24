package com.abm.mainet.vehiclemanagement.domain;

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
@Table(name = "TB_VM_EMPLOYEE_HIST")
public class VehicleEmployeeMasterHistory {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VM_EMPID_H")
    private Long empIdH;
    
    @Column(name = "VM_EMPID")
    private Long empId;

    @Column(name = "DSGID")
    private Long desgId;
    
    @Column(name = "MRF_ID")
    private Long mrfId;

    @Column(name = "CPD_TTL_ID")
    private Long ttlId;
    
    @Column(name = "VM_EMPNAME")
    private String empName;
    
    @Column(name = "VM_EMPMNAME")
    private String empMName;
    
    @Column(name = "VM_EMPLNAME")
    private String empLName;
    
    @Column(name = "VM_EMPGENDER")
    private String gender;
    
    @Column(name = "VM_EMPMOBNO")
    private String empMobNo;
    
    @Column(name = "VM_EMPEMAIL")
    private String empEmailId;
    
    @Column(name = "VM_EMP_ADDRESS")
    private String empAddress;
    
    @Column(name = "VM_EMP_ADDRESS1")
    private String empAddress1;
    
    @Column(name = "VM_EMPPINCODE")
    private String empPincode;
    
    @Column(name = "VM_EMPUID")  
    private String empUId;
    
    @Column(name = "H_STATUS")
    private String hStatus;
    
    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;   

    public VehicleEmployeeMasterHistory() {
        
    }       

    public Long getEmpIdH() {
        return empIdH;
    }


    public void setEmpIdH(Long empIdH) {
        this.empIdH = empIdH;
    }


    public String gethStatus() {
        return hStatus;
    }


    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }


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
    
    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_EMPLOYEE_HIST", "VM_EMPID_H" };
    }
 



}
