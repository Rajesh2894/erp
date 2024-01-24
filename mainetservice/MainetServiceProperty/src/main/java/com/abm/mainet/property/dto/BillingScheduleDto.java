
package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hiren.poriya
 * @since 21-Nov-2017
 */
public class BillingScheduleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long asBillScheid;
    private String tbFinancialyears;
    private Long asBillFrequency;
    private Long asFrequencyFrom;
    private Long asFrequencyTo;
    private String asAutStatus;
    private String asAutBy;
    private Date asAutDate;
    private Long orgId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Long tempToId;
    private Long calculateFrom;
    private Long noOfDay;
    private String asBillStatus;    
    private String finYearView;
    private Date billFromDate;
    private Date billToDate;
    private String billFromMonth;
    private String billToMonth;
    private Long oldBillFreq;



    public void setAsBillScheid(Long asBillScheid) {
        this.asBillScheid = asBillScheid;
    }

    public Long getAsBillScheid() {
        return this.asBillScheid;
    }

    public void setAsBillFrequency(Long asBillFrequency) {
        this.asBillFrequency = asBillFrequency;
    }

    public Long getAsBillFrequency() {
        return this.asBillFrequency;
    }

    public void setAsFrequencyFrom(Long asFrequencyFrom) {
        this.asFrequencyFrom = asFrequencyFrom;
    }

    public Long getAsFrequencyFrom() {
        return this.asFrequencyFrom;
    }

    public void setAsFrequencyTo(Long asFrequencyTo) {
        this.asFrequencyTo = asFrequencyTo;
    }

    public Long getAsFrequencyTo() {
        return this.asFrequencyTo;
    }

    public void setAsAutStatus(String asAutStatus) {
        this.asAutStatus = asAutStatus;
    }

    public String getAsAutStatus() {
        return this.asAutStatus;
    }

    public void setAsAutBy(String asAutBy) {
        this.asAutBy = asAutBy;
    }

    public String getAsAutBy() {
        return this.asAutBy;
    }

    public void setAsAutDate(Date asAutDate) {
        this.asAutDate = asAutDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getAsAutDate() {
        return this.asAutDate;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public String getTbFinancialyears() {
        return tbFinancialyears;
    }

    public void setTbFinancialyears(String tbFinancialyears) {
        this.tbFinancialyears = tbFinancialyears;
    }

    public Long getTempToId() {
        return tempToId;
    }

    public void setTempToId(Long tempToId) {
        this.tempToId = tempToId;
    }

	public String getAsBillStatus() {
		return asBillStatus;
	}

	public void setAsBillStatus(String asBillStatus) {
		this.asBillStatus = asBillStatus;
	}

	public String getFinYearView() {
		return finYearView;
	}

	public void setFinYearView(String finYearView) {
		this.finYearView = finYearView;
	}

	public Long getCalculateFrom() {
		return calculateFrom;
	}

	public void setCalculateFrom(Long calculateFrom) {
		this.calculateFrom = calculateFrom;
	}

	public Long getNoOfDay() {
		return noOfDay;
	}

	public void setNoOfDay(Long noOfDay) {
		this.noOfDay = noOfDay;
	}

	public Date getBillFromDate() {
		return billFromDate;
	}

	public void setBillFromDate(Date billFromDate) {
		this.billFromDate = billFromDate;
	}

	public Date getBillToDate() {
		return billToDate;
	}

	public void setBillToDate(Date billToDate) {
		this.billToDate = billToDate;
	}

	public String getBillFromMonth() {
		return billFromMonth;
	}

	public void setBillFromMonth(String billFromMonth) {
		this.billFromMonth = billFromMonth;
	}

	public String getBillToMonth() {
		return billToMonth;
	}

	public void setBillToMonth(String billToMonth) {
		this.billToMonth = billToMonth;
	}

	public Long getOldBillFreq() {
		return oldBillFreq;
	}

	public void setOldBillFreq(Long oldBillFreq) {
		this.oldBillFreq = oldBillFreq;
	}


}
