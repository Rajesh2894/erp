package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lalit.Prusti
 * @since 04 Jun 2016
 */

public class ChangeOfUsageDTO implements Serializable {
    private static final long serialVersionUID = -1648536797748240567L;

    private long cisId;

    private Long csIdn;

    private String statusofwork;

    private Date dateofcomp;

    private Long plumId;

    private String remark;

    private String useType;

    private Long orgId;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    private String couGranted;

    private Long apmApplicationId;

    private Date apmApplicationDate;

    private Long trdPremise;

    private Date couGrantedDt;

    private Long oldTrdPremise;

    private Long oldTrmGroup1;

    private Long oldTrmGroup2;

    private Long oldTrmGroup3;

    private Long oldTrmGroup4;

    private Long oldTrmGroup5;

    private Long newTrmGroup1;

    private Long newTrmGroup2;

    private Long newTrmGroup3;

    private Long newTrmGroup4;

    private Long newTrmGroup5;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String chanGrantFlag;

    private Date chanAprvdate;

    private Long chanApprovedby;

    private Date chanExecdate;
    
    private Long newCsMeteredccn;
    private String name;
    private String address;
    private Date paymentDate;
    private Long receiptNo;
    private Long connSize;
    private String meterType;
    private String ccnTypeFrom;
    private String ccnTypeTo;
    private String loiNo;
    private Date loiDate;
    private Long workId;
    private Date startDate;
    private String workOrderNo;
    private String connectionInch;
    private Date orderDate;
    private String connectionNo;
    private String city;
    private String workName;
    private String serviceName;
    private String applicationDate;
    private String executionDate;
    private String customString;
    private String serviceCode;
    private String deptName;

    public String getChanGrantFlag() {
        return chanGrantFlag;
    }

    public void setChanGrantFlag(final String chanGrantFlag) {
        this.chanGrantFlag = chanGrantFlag;
    }

    public Date getChanAprvdate() {
        return chanAprvdate;
    }

    public void setChanAprvdate(final Date chanAprvdate) {
        this.chanAprvdate = chanAprvdate;
    }

    public Long getChanApprovedby() {
        return chanApprovedby;
    }

    public void setChanApprovedby(final Long chanApprovedby) {
        this.chanApprovedby = chanApprovedby;
    }

    public Date getChanExecdate() {
        return chanExecdate;
    }

    public void setChanExecdate(final Date chanExecdate) {
        this.chanExecdate = chanExecdate;
    }

    public long getCisId() {
        return cisId;
    }

    public void setCisId(final long cisId) {
        this.cisId = cisId;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public String getStatusofwork() {
        return statusofwork;
    }

    public void setStatusofwork(final String statusofwork) {
        this.statusofwork = statusofwork;
    }

    public Date getDateofcomp() {
        return dateofcomp;
    }

    public void setDateofcomp(final Date dateofcomp) {
        this.dateofcomp = dateofcomp;
    }

    public Long getPlumId() {
        return plumId;
    }

    public void setPlumId(final Long plumId) {
        this.plumId = plumId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(final String useType) {
        this.useType = useType;
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

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public String getCouGranted() {
        return couGranted;
    }

    public void setCouGranted(final String couGranted) {
        this.couGranted = couGranted;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    public void setApmApplicationDate(final Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    public Long getTrdPremise() {
        return trdPremise;
    }

    public void setTrdPremise(final Long trdPremise) {
        this.trdPremise = trdPremise;
    }

    public Date getCouGrantedDt() {
        return couGrantedDt;
    }

    public void setCouGrantedDt(final Date couGrantedDt) {
        this.couGrantedDt = couGrantedDt;
    }

    public Long getOldTrdPremise() {
        return oldTrdPremise;
    }

    public void setOldTrdPremise(final Long oldTrdPremise) {
        this.oldTrdPremise = oldTrdPremise;
    }

    public Long getOldTrmGroup1() {
        return oldTrmGroup1;
    }

    public void setOldTrmGroup1(final Long oldTrmGroup1) {
        this.oldTrmGroup1 = oldTrmGroup1;
    }

    public Long getOldTrmGroup2() {
        return oldTrmGroup2;
    }

    public void setOldTrmGroup2(final Long oldTrmGroup2) {
        this.oldTrmGroup2 = oldTrmGroup2;
    }

    public Long getOldTrmGroup3() {
        return oldTrmGroup3;
    }

    public void setOldTrmGroup3(final Long oldTrmGroup3) {
        this.oldTrmGroup3 = oldTrmGroup3;
    }

    public Long getOldTrmGroup4() {
        return oldTrmGroup4;
    }

    public void setOldTrmGroup4(final Long oldTrmGroup4) {
        this.oldTrmGroup4 = oldTrmGroup4;
    }

    public Long getOldTrmGroup5() {
        return oldTrmGroup5;
    }

    public void setOldTrmGroup5(final Long oldTrmGroup5) {
        this.oldTrmGroup5 = oldTrmGroup5;
    }

    public Long getNewTrmGroup1() {
        return newTrmGroup1;
    }

    public void setNewTrmGroup1(final Long newTrmGroup1) {
        this.newTrmGroup1 = newTrmGroup1;
    }

    public Long getNewTrmGroup2() {
        return newTrmGroup2;
    }

    public void setNewTrmGroup2(final Long newTrmGroup2) {
        this.newTrmGroup2 = newTrmGroup2;
    }

    public Long getNewTrmGroup3() {
        return newTrmGroup3;
    }

    public void setNewTrmGroup3(final Long newTrmGroup3) {
        this.newTrmGroup3 = newTrmGroup3;
    }

    public Long getNewTrmGroup4() {
        return newTrmGroup4;
    }

    public void setNewTrmGroup4(final Long newTrmGroup4) {
        this.newTrmGroup4 = newTrmGroup4;
    }

    public Long getNewTrmGroup5() {
        return newTrmGroup5;
    }

    public void setNewTrmGroup5(final Long newTrmGroup5) {
        this.newTrmGroup5 = newTrmGroup5;
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

	public Long getNewCsMeteredccn() {
		return newCsMeteredccn;
	}

	public void setNewCsMeteredccn(Long newCsMeteredccn) {
		this.newCsMeteredccn = newCsMeteredccn;
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

	public Long getConnSize() {
		return connSize;
	}

	public void setConnSize(Long connSize) {
		this.connSize = connSize;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

	public String getCcnTypeFrom() {
		return ccnTypeFrom;
	}

	public void setCcnTypeFrom(String ccnTypeFrom) {
		this.ccnTypeFrom = ccnTypeFrom;
	}

	public String getCcnTypeTo() {
		return ccnTypeTo;
	}

	public void setCcnTypeTo(String ccnTypeTo) {
		this.ccnTypeTo = ccnTypeTo;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getWorkOrderNo() {
		return workOrderNo;
	}

	public void setWorkOrderNo(String workOrderNo) {
		this.workOrderNo = workOrderNo;
	}

	public String getConnectionInch() {
		return connectionInch;
	}

	public void setConnectionInch(String connectionInch) {
		this.connectionInch = connectionInch;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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
		return "ChangeOfUsageDTO [cisId=" + cisId + ", csIdn=" + csIdn + ", statusofwork=" + statusofwork
				+ ", dateofcomp=" + dateofcomp + ", plumId=" + plumId + ", remark=" + remark + ", useType=" + useType
				+ ", orgId=" + orgId + ", userId=" + userId + ", langId=" + langId + ", lmoddate=" + lmoddate
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", couGranted=" + couGranted
				+ ", apmApplicationId=" + apmApplicationId + ", apmApplicationDate=" + apmApplicationDate
				+ ", trdPremise=" + trdPremise + ", couGrantedDt=" + couGrantedDt + ", oldTrdPremise=" + oldTrdPremise
				+ ", oldTrmGroup1=" + oldTrmGroup1 + ", oldTrmGroup2=" + oldTrmGroup2 + ", oldTrmGroup3=" + oldTrmGroup3
				+ ", oldTrmGroup4=" + oldTrmGroup4 + ", oldTrmGroup5=" + oldTrmGroup5 + ", newTrmGroup1=" + newTrmGroup1
				+ ", newTrmGroup2=" + newTrmGroup2 + ", newTrmGroup3=" + newTrmGroup3 + ", newTrmGroup4=" + newTrmGroup4
				+ ", newTrmGroup5=" + newTrmGroup5 + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
				+ ", chanGrantFlag=" + chanGrantFlag + ", chanAprvdate=" + chanAprvdate + ", chanApprovedby="
				+ chanApprovedby + ", chanExecdate=" + chanExecdate + ", newCsMeteredccn=" + newCsMeteredccn + ", name="
				+ name + ", address=" + address + ", paymentDate=" + paymentDate + ", receiptNo=" + receiptNo
				+ ", connSize=" + connSize + ", meterType=" + meterType + ", ccnTypeFrom=" + ccnTypeFrom
				+ ", ccnTypeTo=" + ccnTypeTo + ", loiNo=" + loiNo + ", loiDate=" + loiDate + ", workId=" + workId
				+ ", startDate=" + startDate + ", workOrderNo=" + workOrderNo + ", connectionInch=" + connectionInch
				+ ", orderDate=" + orderDate + ", connectionNo=" + connectionNo + ", city=" + city + ", workName="
				+ workName + ", serviceName=" + serviceName + ", applicationDate=" + applicationDate
				+ ", executionDate=" + executionDate + ", customString=" + customString + "]";
	}


}