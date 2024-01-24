package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_transfer_mst")
public class PropertyTransferMasterEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2318681542562026735L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRANSFER_MST_ID", nullable = false)
    private long transferMstId;

    @Column(name = "ACTUAL_TRANSFER_DATE")
    private Date actualTransferDate;

    @Column(name = "APM_APPLICATION_ID")
    private Long apmApplicationId;

    @Column(name = "SM_SERVICE_ID")
    private Long smServiceId;

    @Column(name = "AUT_BY")
    private Long autBy;

    @Column(name = "AUT_DATE")
    private Date autDate;

    @Column(name = "AUT_STATUS")
    private String autStatus;

    @Column(name = "BASE_VALUE")
    private Double baseValue;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MARKET_VALUE")
    private BigDecimal marketValue;

    @Column(name = "orgid")
    private long orgId;

    @Column(name = "OWNER_TYPE")
    private Long ownerType;

    @Column(name = "PRO_ASS_NO")
    private String proAssNo;

    @Column(name = "SALES_DEED_VALUE")
    private BigDecimal salesDeedValue;

    @Column(name = "status")
    private String status;

    @Column(name = "TRANSFER_TYPE")
    private Long transferType;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "MUT_INTI_FLAG")
    private String mutIntiFlag;
    
    @Column(name = "CERTIFICATE_NO")
    private String certificateNo;
    
    @Column(name = "REGISTRATION_NO")
    private String regNo;
    
    @Column(name = "FLAT_NO")
    private String flatNo;
    
    @Column(name = "AUTHO_STATUS")
    private String authoStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tbAsTransferrMast", cascade = CascadeType.ALL)
    private List<PropertyTransferOwnerEntity> propTransferOwnerList;

    public PropertyTransferMasterEntity() {
    }

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_transfer_mst", "TRANSFER_MST_ID" };
    }

    public long getTransferMstId() {
        return transferMstId;
    }

    public void setTransferMstId(long transferMstId) {
        this.transferMstId = transferMstId;
    }

    public Date getActualTransferDate() {
        return actualTransferDate;
    }

    public void setActualTransferDate(Date actualTransferDate) {
        this.actualTransferDate = actualTransferDate;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Long getAutBy() {
        return autBy;
    }

    public void setAutBy(Long autBy) {
        this.autBy = autBy;
    }

    public Date getAutDate() {
        return autDate;
    }

    public void setAutDate(Date autDate) {
        this.autDate = autDate;
    }

    public String getAutStatus() {
        return autStatus;
    }

    public void setAutStatus(String autStatus) {
        this.autStatus = autStatus;
    }

    public Double getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(Double baseValue) {
        this.baseValue = baseValue;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public Long getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Long ownerType) {
        this.ownerType = ownerType;
    }

    public String getProAssNo() {
        return proAssNo;
    }

    public void setProAssNo(String proAssNo) {
        this.proAssNo = proAssNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<PropertyTransferOwnerEntity> getPropTransferOwnerList() {
        return propTransferOwnerList;
    }

    public void setPropTransferOwnerList(List<PropertyTransferOwnerEntity> propTransferOwnerList) {
        this.propTransferOwnerList = propTransferOwnerList;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Long getTransferType() {
        return transferType;
    }

    public void setTransferType(Long transferType) {
        this.transferType = transferType;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getSalesDeedValue() {
        return salesDeedValue;
    }

    public void setSalesDeedValue(BigDecimal salesDeedValue) {
        this.salesDeedValue = salesDeedValue;
    }

    public String getMutIntiFlag() {
        return mutIntiFlag;
    }

    public void setMutIntiFlag(String mutIntiFlag) {
        this.mutIntiFlag = mutIntiFlag;
    }

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getAuthoStatus() {
		return authoStatus;
	}

	public void setAuthoStatus(String authoStatus) {
		this.authoStatus = authoStatus;
	}	
	
    
}
