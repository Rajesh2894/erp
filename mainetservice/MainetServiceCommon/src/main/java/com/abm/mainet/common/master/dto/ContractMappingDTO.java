package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

public class ContractMappingDTO implements Serializable {

    private static final long serialVersionUID = 7648238707618872038L;
    private Long contId;
    private String contractNo;
    private String deptName;
    private String representedBy;
    private String vendorName;
    private String fromDate;
    private String toDate;
    private String deptNameReg;
    private String contDate;
    private Long mappingId;
    private Date contractDate;
    private Long typeOfAffected;

    public Long getContId() {
        return contId;
    }

    public void setContId(final Long contId) {
        this.contId = contId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(final String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(final String deptName) {
        this.deptName = deptName;
    }

    public String getRepresentedBy() {
        return representedBy;
    }

    public void setRepresentedBy(final String representedBy) {
        this.representedBy = representedBy;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public String getDeptNameReg() {
        return deptNameReg;
    }

    public void setDeptNameReg(final String deptNameReg) {
        this.deptNameReg = deptNameReg;
    }

    public String getContDate() {
        return contDate;
    }

    public void setContDate(final String contDate) {
        this.contDate = contDate;
    }

    public Long getMappingId() {
        return mappingId;
    }

    public void setMappingId(final Long mappingId) {
        this.mappingId = mappingId;
    }

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Long getTypeOfAffected() {
		return typeOfAffected;
	}

	public void setTypeOfAffected(Long typeOfAffected) {
		this.typeOfAffected = typeOfAffected;
	}
    
}
