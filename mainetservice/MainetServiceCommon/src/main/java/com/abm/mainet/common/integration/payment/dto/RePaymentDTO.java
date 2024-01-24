/**
 * 
 */
package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */

public class RePaymentDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5980162208807487216L;

    private long id;

    private String refId;
    
    private String payeeName;

    private String phoneNo;

    private String email;

    private double amount;

    private Long serviceId;

    private String serviceName;
    
    private String serviceNameMar;
    
    private String feeIds;
    
    private String challanServiceType;
    private String flatNo;
    private Long orgId;
    private String documentUploaded;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

	public String getServiceNameMar() {
		return serviceNameMar;
	}

	public void setServiceNameMar(String serviceNameMar) {
		this.serviceNameMar = serviceNameMar;
	}

	public String getFeeIds() {
		return feeIds;
	}

	public void setFeeIds(String feeIds) {
		this.feeIds = feeIds;
	}

	public String getChallanServiceType() {
		return challanServiceType;
	}

	public void setChallanServiceType(String challanServiceType) {
		this.challanServiceType = challanServiceType;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDocumentUploaded() {
		return documentUploaded;
	}

	public void setDocumentUploaded(String documentUploaded) {
		this.documentUploaded = documentUploaded;
	}
    
    

}
