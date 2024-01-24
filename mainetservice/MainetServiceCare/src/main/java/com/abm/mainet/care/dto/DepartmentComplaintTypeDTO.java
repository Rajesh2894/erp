package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentComplaintTypeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long compId;
    private Long orgId;
    private String complaintDesc;
    private String complaintDescReg;
    private String residentId;
    private String amtDues;
    private String documentReq;
    private String otpValidReq;
    private Boolean isActive;
    private String status;
    private Long updatedBy;
    private Date updatedDate;
    private Date createDate;
    private Long createdBy;
    private Long serviceId;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(final Long compId) {
        this.compId = compId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public String getComplaintDesc() {
        return complaintDesc;
    }

    public void setComplaintDesc(final String complaintDesc) {
        this.complaintDesc = complaintDesc;
    }

    public String getComplaintDescReg() {
        return complaintDescReg;
    }

    public void setComplaintDescReg(String complaintDescReg) {
        this.complaintDescReg = complaintDescReg;
    }

    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public String getAmtDues() {
        return amtDues;
    }

    public void setAmtDues(String amtDues) {
        this.amtDues = amtDues;
    }

    public String getDocumentReq() {
        return documentReq;
    }

    public void setDocumentReq(String documentReq) {
        this.documentReq = documentReq;
    }

    public String getOtpValidReq() {
        return otpValidReq;
    }

    public void setOtpValidReq(String otpValidReq) {
        this.otpValidReq = otpValidReq;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(final Boolean isActive) {
        this.isActive = isActive;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((compId == null) ? 0 : compId.hashCode());
        result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DepartmentComplaintTypeDTO other = (DepartmentComplaintTypeDTO) obj;
        if (compId == null) {
            if (other.compId != null)
                return false;
        } else if (!compId.equals(other.compId))
            return false;
        if (serviceId == null) {
            if (other.serviceId != null)
                return false;
        } else if (!serviceId.equals(other.serviceId)) {
            return false;
        } else if (serviceId != null && (!serviceId.equals(other.serviceId)) && compId == null) {
            return false;
        }
        return true;
    }

    /*
     * @Override public int hashCode() { final int prime = 31; int result = 1; result = prime * result + ((compId == null) ? 0 :
     * compId.hashCode()); return result; }
     */

    /*
     * @Override public boolean equals(Object obj) { if (this == obj) return true; if (obj == null) return false; if (getClass()
     * != obj.getClass()) return false; DepartmentComplaintTypeDTO other = (DepartmentComplaintTypeDTO) obj; if (compId == null) {
     * if (other.compId != null) return false; } else if (!compId.equals(other.compId)) return false; return true; }
     */

}
