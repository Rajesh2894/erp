package com.abm.mainet.rts.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ApplicantDetailDTO;

public class FirstAppealDto implements Serializable {

    private static final long serialVersionUID = -8150418907881104948L;

    private Long applicationNo;
    private String applicationDate;
    private String status;
    private Long serviceId;
    private String serviceName;
    private Long deptId;
    private ApplicantDetailDTO applicantDetailDTO;
    private String reasonForAppeal;
    private String groundForAppeal;
    private String correspondingAddress;
    private String permanentAddress;
    private String name;
    private String pincodeNo;

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public ApplicantDetailDTO getApplicantDetailDTO() {
        return applicantDetailDTO;
    }

    public void setApplicantDetailDTO(ApplicantDetailDTO applicantDetailDTO) {
        this.applicantDetailDTO = applicantDetailDTO;
    }

    public String getReasonForAppeal() {
        return reasonForAppeal;
    }

    public void setReasonForAppeal(String reasonForAppeal) {
        this.reasonForAppeal = reasonForAppeal;
    }

    public String getGroundForAppeal() {
        return groundForAppeal;
    }

    public void setGroundForAppeal(String groundForAppeal) {
        this.groundForAppeal = groundForAppeal;
    }

    public String getCorrespondingAddress() {
        return correspondingAddress;
    }

    public void setCorrespondingAddress(String correspondingAddress) {
        this.correspondingAddress = correspondingAddress;
    }

    public String getPermanentAddress() {
        return permanentAddress;
    }

    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincodeNo() {
        return pincodeNo;
    }

    public void setPincodeNo(String pincodeNo) {
        this.pincodeNo = pincodeNo;
    }

    @Override
    public String toString() {
        return "FirstAppealDto [applicationNo=" + applicationNo + ", applicationDate=" + applicationDate + ", status=" + status
                + ", serviceId=" + serviceId + ", serviceName=" + serviceName + ", deptId=" + deptId + ", applicantDetailDTO="
                + applicantDetailDTO + ", reasonForAppeal=" + reasonForAppeal + ", groundForAppeal=" + groundForAppeal
                + ", correspondingAddress=" + correspondingAddress + ", permanentAddress=" + permanentAddress + ", name=" + name
                + ", pincodeNo=" + pincodeNo + "]";
    }

}
