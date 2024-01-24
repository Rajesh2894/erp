package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Jeetendra.Pal
 * @since 10 Mar 2016
 * @comment This table is used to store Disconnections(Temporary/Permanent) entries.
 */
public class TBWaterDisconnectionDTO implements Serializable {
    private static final long serialVersionUID = -4731415059319901900L;

    private long discId;

    private Long csIdn;

    private Long apmApplicationId;

    private Date discAppdate;

    private String discReason;

    private Long discType;

    private Long discMethod;

    private String discGrantFlag;

    private Date discAprvdate;

    private Long discApprovedby;

    private Date discExecdate;

    private Long orgId;

    private Long userId;

    private int langId;

    private Date lmodDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String wlbWrPrflg;

    private String wtV2;

    private String wtV3;

    private String wtV4;

    private String wtV5;

    private Long wlbWkno;

    private Long wtN2;

    private Long wtN3;

    private Long wtN4;

    private Long wtN5;

    private Date wlbWkdt;

    private Date wtD2;

    private Date wtD3;

    private String wtLo1;

    private String wtLo2;

    private String wtLo3;

    private Long plumId;

    private Date disconnectFromDate;

    private Date disconnectToDate;

    private List<String> fileList;
    private String name;
    private String address;
    private Date paymentDate;
    private Long receiptNo;
    private String loiNo;
    private Date loiDate;
    private String connectionInch;
    private Date startDate;
    private String connectionNo;
    private String city;
    private String workName;
    private String serviceName;
    private String applicationDate;
    private String executionDate;
    private String customString;
    private String serviceCode;
    private String deptName;
    
    public long getDiscId() {
        return discId;
    }

    public void setDiscId(final long discId) {
        this.discId = discId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getDiscAppdate() {
        return discAppdate;
    }

    public void setDiscAppdate(final Date discAppdate) {
        this.discAppdate = discAppdate;
    }

    public String getDiscReason() {
        return discReason;
    }

    public void setDiscReason(final String discReason) {
        this.discReason = discReason;
    }

    public Long getDiscType() {
        return discType;
    }

    public void setDiscType(final Long discType) {
        this.discType = discType;
    }

    public Long getDiscMethod() {
        return discMethod;
    }

    public void setDiscMethod(final Long discMethod) {
        this.discMethod = discMethod;
    }

    public String getDiscGrantFlag() {
        return discGrantFlag;
    }

    public void setDiscGrantFlag(final String discGrantFlag) {
        this.discGrantFlag = discGrantFlag;
    }

    public Date getDiscAprvdate() {
        return discAprvdate;
    }

    public void setDiscAprvdate(final Date discAprvdate) {
        this.discAprvdate = discAprvdate;
    }

    public Long getDiscApprovedby() {
        return discApprovedby;
    }

    public void setDiscApprovedby(final Long discApprovedby) {
        this.discApprovedby = discApprovedby;
    }

    public Date getDiscExecdate() {
        return discExecdate;
    }

    public void setDiscExecdate(final Date discExecdate) {
        this.discExecdate = discExecdate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getWlbWrPrflg() {
        return wlbWrPrflg;
    }

    public void setWlbWrPrflg(final String wlbWrPrflg) {
        this.wlbWrPrflg = wlbWrPrflg;
    }

    public String getWtV2() {
        return wtV2;
    }

    public void setWtV2(final String wtV2) {
        this.wtV2 = wtV2;
    }

    public String getWtV3() {
        return wtV3;
    }

    public void setWtV3(final String wtV3) {
        this.wtV3 = wtV3;
    }

    public String getWtV4() {
        return wtV4;
    }

    public void setWtV4(final String wtV4) {
        this.wtV4 = wtV4;
    }

    public String getWtV5() {
        return wtV5;
    }

    public void setWtV5(final String wtV5) {
        this.wtV5 = wtV5;
    }

    public Long getWlbWkno() {
        return wlbWkno;
    }

    public void setWlbWkno(final Long wlbWkno) {
        this.wlbWkno = wlbWkno;
    }

    public Long getWtN2() {
        return wtN2;
    }

    public void setWtN2(final Long wtN2) {
        this.wtN2 = wtN2;
    }

    public Long getWtN3() {
        return wtN3;
    }

    public void setWtN3(final Long wtN3) {
        this.wtN3 = wtN3;
    }

    public Long getWtN4() {
        return wtN4;
    }

    public void setWtN4(final Long wtN4) {
        this.wtN4 = wtN4;
    }

    public Long getWtN5() {
        return wtN5;
    }

    public void setWtN5(final Long wtN5) {
        this.wtN5 = wtN5;
    }

    public Date getWlbWkdt() {
        return wlbWkdt;
    }

    public void setWlbWkdt(final Date wlbWkdt) {
        this.wlbWkdt = wlbWkdt;
    }

    public Date getWtD2() {
        return wtD2;
    }

    public void setWtD2(final Date wtD2) {
        this.wtD2 = wtD2;
    }

    public Date getWtD3() {
        return wtD3;
    }

    public void setWtD3(final Date wtD3) {
        this.wtD3 = wtD3;
    }

    public String getWtLo1() {
        return wtLo1;
    }

    public void setWtLo1(final String wtLo1) {
        this.wtLo1 = wtLo1;
    }

    public String getWtLo2() {
        return wtLo2;
    }

    public void setWtLo2(final String wtLo2) {
        this.wtLo2 = wtLo2;
    }

    public String getWtLo3() {
        return wtLo3;
    }

    public void setWtLo3(final String wtLo3) {
        this.wtLo3 = wtLo3;
    }

    public Long getPlumId() {
        return plumId;
    }

    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    /**
     * @return the fileList
     */
    public List<String> getFileList() {
        return fileList;
    }

    /**
     * @param fileList the fileList to set
     */
    public void setFileList(final List<String> fileList) {
        this.fileList = fileList;
    }

    public Date getDisconnectFromDate() {
        return disconnectFromDate;
    }

    public void setDisconnectFromDate(final Date disconnectFromDate) {
        this.disconnectFromDate = disconnectFromDate;
    }

    public Date getDisconnectToDate() {
        return disconnectToDate;
    }

    public void setDisconnectToDate(final Date disconnectToDate) {
        this.disconnectToDate = disconnectToDate;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getLoiNo() {
		return loiNo;
	}

	public void setLoiNo(String loiNo) {
		this.loiNo = loiNo;
	}

	public Date getLoiDate() {
		return loiDate;
	}

	public void setLoiDate(Date loiDate) {
		this.loiDate = loiDate;
	}

	public String getConnectionInch() {
		return connectionInch;
	}

	public void setConnectionInch(String connectionInch) {
		this.connectionInch = connectionInch;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public String getCustomString() {
		return customString;
	}

	public void setCustomString(String customString) {
		this.customString = customString;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return "TBWaterDisconnectionDTO [discId=" + discId + ", csIdn=" + csIdn + ", apmApplicationId="
				+ apmApplicationId + ", discAppdate=" + discAppdate + ", discReason=" + discReason + ", discType="
				+ discType + ", discMethod=" + discMethod + ", discGrantFlag=" + discGrantFlag + ", discAprvdate="
				+ discAprvdate + ", discApprovedby=" + discApprovedby + ", discExecdate=" + discExecdate + ", orgId="
				+ orgId + ", userId=" + userId + ", langId=" + langId + ", lmodDate=" + lmodDate + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ ", wlbWrPrflg=" + wlbWrPrflg + ", wtV2=" + wtV2 + ", wtV3=" + wtV3 + ", wtV4=" + wtV4 + ", wtV5="
				+ wtV5 + ", wlbWkno=" + wlbWkno + ", wtN2=" + wtN2 + ", wtN3=" + wtN3 + ", wtN4=" + wtN4 + ", wtN5="
				+ wtN5 + ", wlbWkdt=" + wlbWkdt + ", wtD2=" + wtD2 + ", wtD3=" + wtD3 + ", wtLo1=" + wtLo1 + ", wtLo2="
				+ wtLo2 + ", wtLo3=" + wtLo3 + ", plumId=" + plumId + ", disconnectFromDate=" + disconnectFromDate
				+ ", disconnectToDate=" + disconnectToDate + ", fileList=" + fileList + ", name=" + name + ", address="
				+ address + ", paymentDate=" + paymentDate + ", receiptNo=" + receiptNo + ", loiNo=" + loiNo
				+ ", loiDate=" + loiDate + ", connectionInch=" + connectionInch + ", startDate=" + startDate
				+ ", connectionNo=" + connectionNo + ", city=" + city + ", workName=" + workName + ", serviceName="
				+ serviceName + ", applicationDate=" + applicationDate + ", executionDate=" + executionDate
				+ ", customString=" + customString + "]";
	}


}