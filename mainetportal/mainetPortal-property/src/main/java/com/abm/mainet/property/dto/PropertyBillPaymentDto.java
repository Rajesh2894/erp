package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

public class PropertyBillPaymentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String assNo;

    private String assOldpropno;

    private String assAddress;

    private Long orgId;

    private Long userId;

    private Date manualReeiptDate;
    
    private String flatNo;
    
	private String parentPropNo;

	private String specNotSearchType;
	
    private String billMethod;

    public String getAssNo() {
        return assNo;
    }

    public void setAssNo(String assNo) {
        this.assNo = assNo;
    }

    public String getAssOldpropno() {
        return assOldpropno;
    }

    public void setAssOldpropno(String assOldpropno) {
        this.assOldpropno = assOldpropno;
    }

    public String getAssAddress() {
        return assAddress;
    }

    public void setAssAddress(String assAddress) {
        this.assAddress = assAddress;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getManualReeiptDate() {
        return manualReeiptDate;
    }

    public void setManualReeiptDate(Date manualReeiptDate) {
        this.manualReeiptDate = manualReeiptDate;
    }

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
	}

	public String getSpecNotSearchType() {
		return specNotSearchType;
	}

	public void setSpecNotSearchType(String specNotSearchType) {
		this.specNotSearchType = specNotSearchType;
	}

	public String getBillMethod() {
		return billMethod;
	}

	public void setBillMethod(String billMethod) {
		this.billMethod = billMethod;
	}	
    
}
