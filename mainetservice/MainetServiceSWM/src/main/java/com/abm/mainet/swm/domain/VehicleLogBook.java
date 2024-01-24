package com.abm.mainet.swm.domain;

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
@Table(name = "TB_SW_VECHILELOGF")
public class VehicleLogBook implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;   
    
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEL_ID")
    private Long veId;    
   
    @Column(name = "VEL_EMPID3")
    private String empId3;
    
    @Column(name = "VEL_EMPID2")
    private String empId2;
    
    @Column(name = "VEL_EMPID1")
    private String empId1;
    
    @Column(name = "VEL_EMPNAME2")
    private String empName2;
    
    @Column(name = "VEL_EMPNAME3")
    private String empName3;
    
    @Column(name = "VEL_EMPNAME1")
    private String empName1;
    
    
    @Column(name = "VEL_MONTH")
    private String month;
    
    @Column(name = "VEL_NOBEAT")
    private Long noOfBeat;
    
    @Column(name = "VEL_NOESTABLI")
    private Long noOfEst;
    
    @Column(name = "VEL_NOCOMMA")
    private Long noOfComm;
    
    @Column(name = "VEL_NOHOUSE")
    private Long noOfHouse;
    
    @Column(name = "VEL_WARDNO")
    private String wardNo;
    
    @Column(name = "VEL_NOWARD")
    private Long noOfWard;
    
    @Column(name = "VE_NO")
    private String veNo;
    
    @Column(name = "VE_VETYPE")
    private String veType;
    
    @Column(name = "VEL_ADDRESS")
    private String address;
    
    @Column(name = "VEL_SLRMCNO")
    private String veSLRMNo;    
    
    @Column(name = "VEL_SLRMCNAME")
    private String veSLRMName;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public VehicleLogBook() {
        
    }
    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public String getEmpId3() {
        return empId3;
    }

    public void setEmpId3(String empId3) {
        this.empId3 = empId3;
    }

    public String getEmpId2() {
        return empId2;
    }

    public void setEmpId2(String empId2) {
        this.empId2 = empId2;
    }

    public String getEmpId1() {
        return empId1;
    }

    public void setEmpId1(String empId1) {
        this.empId1 = empId1;
    }

    public String getEmpName2() {
        return empName2;
    }

    public void setEmpName2(String empName2) {
        this.empName2 = empName2;
    }

    public String getEmpName3() {
        return empName3;
    }

    public void setEmpName3(String empName3) {
        this.empName3 = empName3;
    }

    public String getEmpName1() {
        return empName1;
    }

    public void setEmpName1(String empName1) {
        this.empName1 = empName1;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getNoOfBeat() {
        return noOfBeat;
    }

    public void setNoOfBeat(Long noOfBeat) {
        this.noOfBeat = noOfBeat;
    }

    public Long getNoOfEst() {
        return noOfEst;
    }

    public void setNoOfEst(Long noOfEst) {
        this.noOfEst = noOfEst;
    }

    public Long getNoOfComm() {
        return noOfComm;
    }

    public void setNoOfComm(Long noOfComm) {
        this.noOfComm = noOfComm;
    }

    public Long getNoOfHouse() {
        return noOfHouse;
    }

    public void setNoOfHouse(Long noOfHouse) {
        this.noOfHouse = noOfHouse;
    }

    public String getWardNo() {
        return wardNo;
    }

    public void setWardNo(String wardNo) {
        this.wardNo = wardNo;
    }

    public Long getNoOfWard() {
        return noOfWard;
    }

    public void setNoOfWard(Long noOfWard) {
        this.noOfWard = noOfWard;
    }

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public String getVeType() {
        return veType;
    }

    public void setVeType(String veType) {
        this.veType = veType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVeSLRMNo() {
        return veSLRMNo;
    }

    public void setVeSLRMNo(String veSLRMNo) {
        this.veSLRMNo = veSLRMNo;
    }

    public String getVeSLRMName() {
        return veSLRMName;
    }

    public void setVeSLRMName(String veSLRMName) {
        this.veSLRMName = veSLRMName;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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
    
    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_VECHILELOGF", "VEL_ID" };
    }
    

}
