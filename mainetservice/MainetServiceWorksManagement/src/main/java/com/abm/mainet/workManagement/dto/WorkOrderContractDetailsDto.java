package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author vishwajeet.kumar
 * @since 09 July 2018
 */
public class WorkOrderContractDetailsDto implements Serializable {

    private static final long serialVersionUID = 5251648480755318960L;

    private Long contId;
    private String contNo;
    private String contDate;
    private String contDept;
    private Long contDeptId;
    private String contFromDate;
    private String contToDate;
    private String contp1Name;
    private String contp2Name;
    private String vmVendorAdd;
    private String vendorEmailId;
    private String vendorMobileNo;
    private BigDecimal contAmount;
    private String contTndNo;
    private String contLoaNo;
    private String venderName;
    private String workOrderStatus;
    private Long contToPeriod;
    private String tenderNo;
    private String tenderDate;
    private Long vmVendorid;
    private Long contToPeriodUnit;
 
    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getContDate() {
        return contDate;
    }

    public void setContDate(String contDate) {
        this.contDate = contDate;
    }

    public String getContDept() {
        return contDept;
    }

    public void setContDept(String contDept) {
        this.contDept = contDept;
    }

    public Long getContDeptId() {
        return contDeptId;
    }

    public void setContDeptId(Long contDeptId) {
        this.contDeptId = contDeptId;
    }

    public String getContFromDate() {
        return contFromDate;
    }

    public void setContFromDate(String contFromDate) {
        this.contFromDate = contFromDate;
    }

    public String getContToDate() {
        return contToDate;
    }

    public void setContToDate(String contToDate) {
        this.contToDate = contToDate;
    }

    public String getContp1Name() {
        return contp1Name;
    }

    public void setContp1Name(String contp1Name) {
        this.contp1Name = contp1Name;
    }

    public String getContp2Name() {
        return contp2Name;
    }

    public void setContp2Name(String contp2Name) {
        this.contp2Name = contp2Name;
    }

    public String getVmVendorAdd() {
        return vmVendorAdd;
    }

    public void setVmVendorAdd(String vmVendorAdd) {
        this.vmVendorAdd = vmVendorAdd;
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public String getVendorMobileNo() {
        return vendorMobileNo;
    }

    public void setVendorMobileNo(String vendorMobileNo) {
        this.vendorMobileNo = vendorMobileNo;
    }

    public BigDecimal getContAmount() {
        return contAmount;
    }

    public void setContAmount(BigDecimal contAmount) {
        this.contAmount = contAmount;
    }

    public String getContTndNo() {
        return contTndNo;
    }

    public void setContTndNo(String contTndNo) {
        this.contTndNo = contTndNo;
    }

    public String getContLoaNo() {
        return contLoaNo;
    }

    public void setContLoaNo(String contLoaNo) {
        this.contLoaNo = contLoaNo;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Long getContToPeriod() {
        return contToPeriod;
    }

    public void setContToPeriod(Long contToPeriod) {
        this.contToPeriod = contToPeriod;
    }

    public String getTenderNo() {
        return tenderNo;
    }

    public void setTenderNo(String tenderNo) {
        this.tenderNo = tenderNo;
    }

    public String getTenderDate() {
        return tenderDate;
    }

    public void setTenderDate(String tenderDate) {
        this.tenderDate = tenderDate;
    }

    public Long getVmVendorid() {
        return vmVendorid;
    }

    public void setVmVendorid(Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

	public Long getContToPeriodUnit() {
		return contToPeriodUnit;
	}

	public void setContToPeriodUnit(Long contToPeriodUnit) {
		this.contToPeriodUnit = contToPeriodUnit;
	}
      
}
