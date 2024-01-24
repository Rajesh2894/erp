package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author vishwajeet.kumar
 * @since 5 june 2019
 */
public class EstatePropertyAmenityDTO implements Serializable {

    private static final long serialVersionUID = -8651951894149525693L;

    private Long propAmenityId;
    private Long propId;
    private Long propAmtFacility;
    private Long propQuantity;
    private String propType;
    private String amiFacStatus;
    private Long orgId;
    private long langId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String propAmtFacilityDesc;
    private String remark;

    public Long getPropAmenityId() {
        return propAmenityId;
    }

    public void setPropAmenityId(Long propAmenityId) {
        this.propAmenityId = propAmenityId;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public Long getPropAmtFacility() {
        return propAmtFacility;
    }

    public void setPropAmtFacility(Long propAmtFacility) {
        this.propAmtFacility = propAmtFacility;
    }

    public Long getPropQuantity() {
        return propQuantity;
    }

    public void setPropQuantity(Long propQuantity) {
        this.propQuantity = propQuantity;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getAmiFacStatus() {
        return amiFacStatus;
    }

    public void setAmiFacStatus(String amiFacStatus) {
        this.amiFacStatus = amiFacStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(long langId) {
        this.langId = langId;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String getPropAmtFacilityDesc() {
        return propAmtFacilityDesc;
    }

    public void setPropAmtFacilityDesc(String propAmtFacilityDesc) {
        this.propAmtFacilityDesc = propAmtFacilityDesc;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
    

}
