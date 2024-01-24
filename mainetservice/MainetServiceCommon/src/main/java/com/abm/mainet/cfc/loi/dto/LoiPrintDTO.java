package com.abm.mainet.cfc.loi.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author umashanker.kanaujiya
 *
 */
public class LoiPrintDTO implements Serializable {

    private static final long serialVersionUID = -1006572869711759820L;
    private Long loiId;
    private String loiNo;
    private Date loiDate;
    private Long loiServiceId;
    private Long loiApplicationId;
    private Long loiRefId;
    private Double loiAmount;
    private String serviceName;
    private String loiPaid;
    private String applicantName;
    private String applDate;
    private BigDecimal loiAmt;
    private String serviceNameMar;

    /**
     * @return the loiPaid
     */
    public String getLoiPaid() {
        return loiPaid;
    }

    /**
     * @param loiPaid the loiPaid to set
     */
    public void setLoiPaid(final String loiPaid) {
        this.loiPaid = loiPaid;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @return the loiId
     */
    public Long getLoiId() {
        return loiId;
    }

    /**
     * @param loiId the loiId to set
     */
    public void setLoiId(final Long loiId) {
        this.loiId = loiId;
    }

    /**
     * @return the loiNo
     */
    public String getLoiNo() {
        return loiNo;
    }

    /**
     * @param loiNo the loiNo to set
     */
    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    /**
     * @return the loiDate
     */
    public Date getLoiDate() {
        return loiDate;
    }

    /**
     * @param loiDate the loiDate to set
     */
    public void setLoiDate(final Date loiDate) {
        this.loiDate = loiDate;
    }

    /**
     * @return the loiServiceId
     */
    public Long getLoiServiceId() {
        return loiServiceId;
    }

    /**
     * @param loiServiceId the loiServiceId to set
     */
    public void setLoiServiceId(final Long loiServiceId) {
        this.loiServiceId = loiServiceId;
    }

    /**
     * @return the loiApplicationId
     */
    public Long getLoiApplicationId() {
        return loiApplicationId;
    }

    /**
     * @param loiApplicationId the loiApplicationId to set
     */
    public void setLoiApplicationId(final Long loiApplicationId) {
        this.loiApplicationId = loiApplicationId;
    }

    /**
     * @return the loiRefId
     */
    public Long getLoiRefId() {
        return loiRefId;
    }

    /**
     * @param loiRefId the loiRefId to set
     */
    public void setLoiRefId(final Long loiRefId) {
        this.loiRefId = loiRefId;
    }

    /**
     * @return the loiAmount
     */
    public Double getLoiAmount() {
        return loiAmount;
    }

    /**
     * @param loiAmount the loiAmount to set
     */
    public void setLoiAmount(final Double loiAmount) {
        this.loiAmount = loiAmount;
    }

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplDate() {
		return applDate;
	}

	public void setApplDate(String applDate) {
		this.applDate = applDate;
	}

	public BigDecimal getLoiAmt() {
		return loiAmt;
	}

	public void setLoiAmt(BigDecimal loiAmt) {
		this.loiAmt = loiAmt;
	}

	public String getServiceNameMar() {
		return serviceNameMar;
	}

	public void setServiceNameMar(String serviceNameMar) {
		this.serviceNameMar = serviceNameMar;
	}
	
	
    
    

}
