package com.abm.mainet.common.pg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rahul.Yadav
 *
 */
public class LoiPaymentSearchDTO implements Serializable {

    private static final long serialVersionUID = 3503057770870961436L;

    private Long serviceId;

    private String applicantName;

    private Long applicationId;

    private String loiNo;

    private Date loiDate;

    private BigDecimal loiAmount;

    private Long orgId;

    private String serviceName;

    private String applicationDate;

    private String email;

    private String mobileNo;

    private TbLoiMas loiMasData = null;

    private Map<Long, Double> loiCharges = null;

    private Map<String, Double> chargeDesc = new HashMap<>(0);

    private Double total = new Double(0d);

    private String referenceNo;
    
    private String address;
    
    private Long empId;
    
    private Long deptId;
	
	private String serviceNameMar;
	private double applicationFee;

	private double totalAmntInclApplFee;

	private Long applicationFeeTaxId;
	private String isTaxApplicable;
   

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public Date getLoiDate() {
        return loiDate;
    }

    public void setLoiDate(final Date loiDate) {
        this.loiDate = loiDate;
    }

    public BigDecimal getLoiAmount() {
        return loiAmount;
    }

    public void setLoiAmount(final BigDecimal loiAmount) {
        this.loiAmount = loiAmount;
    }

    public TbLoiMas getLoiMasData() {
        return loiMasData;
    }

    public void setLoiMasData(final TbLoiMas loiMasData) {
        this.loiMasData = loiMasData;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Map<String, Double> getChargeDesc() {
        return chargeDesc;
    }

    public void setChargeDesc(final Map<String, Double> chargeDesc) {
        this.chargeDesc = chargeDesc;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(final Double total) {
        this.total = total;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(final String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Map<Long, Double> getLoiCharges() {
        return loiCharges;
    }

    public void setLoiCharges(final Map<Long, Double> loiCharges) {
        this.loiCharges = loiCharges;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }
    
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getServiceNameMar() {
		return serviceNameMar;
	}

	public void setServiceNameMar(String serviceNameMar) {
		this.serviceNameMar = serviceNameMar;
	}

	public double getApplicationFee() {
		return applicationFee;
	}

	public void setApplicationFee(double applicationFee) {
		this.applicationFee = applicationFee;
	}

	public double getTotalAmntInclApplFee() {
		return totalAmntInclApplFee;
	}

	public void setTotalAmntInclApplFee(double totalAmntInclApplFee) {
		this.totalAmntInclApplFee = totalAmntInclApplFee;
	}

	public Long getApplicationFeeTaxId() {
		return applicationFeeTaxId;
	}

	public void setApplicationFeeTaxId(Long applicationFeeTaxId) {
		this.applicationFeeTaxId = applicationFeeTaxId;
	}

	public String getIsTaxApplicable() {
		return isTaxApplicable;
	}

	public void setIsTaxApplicable(String isTaxApplicable) {
		this.isTaxApplicable = isTaxApplicable;
	}
	
}
