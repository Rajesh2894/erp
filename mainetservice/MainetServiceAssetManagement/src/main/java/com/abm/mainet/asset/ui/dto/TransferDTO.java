/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.abm.mainet.common.master.dto.EmployeeBean;

/**
 * @author sarojkumar.yadav
 *
 */
public class TransferDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8016088566445066705L;
    private Long transferId;
    private Long assetCode;
    private String assetSrNo;
    private String assetDesc;
    private Long department;
    private String currentCostCenter;
    private Long currentEmployee;
    private String custodian;
    private Long currentLocation;
    private String currentLocationDesc;
    private Date docDate;
    private Date postDate;
    @NotNull
    @NotEmpty
    private String transferType;
    private String remark;
    @NotNull
    private Long orgId;
    private String transferCostCenter;
    private Long transferEmployee;
    private Long transferLocation;
    private Long transferDepartment;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String empDesignation;
    private String deptCode;
    private Long assetClass2;
    private List<EmployeeBean> empList = new ArrayList<>();

    /**
     * @return the transferId
     */
    public Long getTransferId() {
        return transferId;
    }

    /**
     * @param transferId the transferId to set
     */
    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    /**
     * @return the assetCode
     */
    public Long getAssetCode() {
        return assetCode;
    }

    /**
     * @param assetCode the assetCode to set
     */
    public void setAssetCode(Long assetCode) {
        this.assetCode = assetCode;
    }

    /**
     * @return the assetDesc
     */
    public String getAssetDesc() {
        return assetDesc;
    }

    /**
     * @param assetDesc the assetDesc to set
     */
    public void setAssetDesc(String assetDesc) {
        this.assetDesc = assetDesc;
    }

    /**
     * @return the department
     */
    public Long getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Long department) {
        this.department = department;
    }

    public String getCurrentCostCenter() {
        return currentCostCenter;
    }

    public void setCurrentCostCenter(String currentCostCenter) {
        this.currentCostCenter = currentCostCenter;
    }

    public String getTransferCostCenter() {
        return transferCostCenter;
    }

    public void setTransferCostCenter(String transferCostCenter) {
        this.transferCostCenter = transferCostCenter;
    }

    /**
     * @return the currentEmployee
     */
    public Long getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * @param currentEmployee the currentEmployee to set
     */
    public void setCurrentEmployee(Long currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    /**
     * @return the custodian
     */
    public String getCustodian() {
        return custodian;
    }

    /**
     * @param custodian the custodian to set
     */
    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    /**
     * @return the currentLocation
     */
    public Long getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation the currentLocation to set
     */
    public void setCurrentLocation(Long currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * @return the docDate
     */
    public Date getDocDate() {
        return docDate;
    }

    /**
     * @param docDate the docDate to set
     */
    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    /**
     * @return the transferType
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * @param transferType the transferType to set
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the transferEmployee
     */
    public Long getTransferEmployee() {
        return transferEmployee;
    }

    /**
     * @param transferEmployee the transferEmployee to set
     */
    public void setTransferEmployee(Long transferEmployee) {
        this.transferEmployee = transferEmployee;
    }

    /**
     * @return the transferLocation
     */
    public Long getTransferLocation() {
        return transferLocation;
    }

    /**
     * @param transferLocation the transferLocation to set
     */
    public void setTransferLocation(Long transferLocation) {
        this.transferLocation = transferLocation;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the currentLocationDesc
     */
    public String getCurrentLocationDesc() {
        return currentLocationDesc;
    }

    /**
     * @param currentLocationDesc the currentLocationDesc to set
     */
    public void setCurrentLocationDesc(String currentLocationDesc) {
        this.currentLocationDesc = currentLocationDesc;
    }

    /**
     * @return the empDesignation
     */
    public String getEmpDesignation() {
        return empDesignation;
    }

    /**
     * @param empDesignation the empDesignation to set
     */
    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }

    public Long getTransferDepartment() {
        return transferDepartment;
    }

    public void setTransferDepartment(Long transferDepartment) {
        this.transferDepartment = transferDepartment;
    }

    public String getAssetSrNo() {
        return assetSrNo;
    }

    public void setAssetSrNo(String assetSrNo) {
        this.assetSrNo = assetSrNo;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    
    public Long getAssetClass2() {
		return assetClass2;
	}

	public void setAssetClass2(Long assetClass2) {
		this.assetClass2 = assetClass2;
	}

	public List<EmployeeBean> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmployeeBean> empList) {
		this.empList = empList;
	}

	/*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TransferDTO [transferId=" + transferId + ", assetCode=" + assetCode + ", assetSrNo=" + assetSrNo + ", assetDesc="
                + assetDesc + ", department=" + department + ", currentCostCenter=" + currentCostCenter + ", currentEmployee="
                + currentEmployee + ", custodian=" + custodian + ", currentLocation=" + currentLocation + ", currentLocationDesc="
                + currentLocationDesc + ", docDate=" + docDate + ", postDate=" + postDate + ", transferType=" + transferType
                + ", remark=" + remark + ", orgId=" + orgId + ", transferCostCenter=" + transferCostCenter + ", transferEmployee="
                + transferEmployee + ", transferLocation=" + transferLocation + ", transferDepartment=" + transferDepartment
                + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", empDesignation=" + empDesignation
                + ", deptCode=" + deptCode + "]";
    }

}
