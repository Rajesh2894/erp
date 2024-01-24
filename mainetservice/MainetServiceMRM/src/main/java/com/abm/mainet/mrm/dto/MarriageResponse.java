package com.abm.mainet.mrm.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ApplicantDetailDTO;

public class MarriageResponse implements Serializable {

    private static final long serialVersionUID = -711484839295523622L;

    private Long orgId;
    private Long serviceId;
    private Long deptId;
    private String smShortCode;
    private String serviceName;
    private String deptName;
    private Long smSLA;
    private Long smChklstVerify;
    private String smAppliChargeFlag;

    private MarriageDTO marriageDTO;
    private ApplicantDetailDTO applicantDetailDTO;
    
    private String serviceNameMar;
    private String deptNameMar;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getSmShortCode() {
        return smShortCode;
    }

    public void setSmShortCode(String smShortCode) {
        this.smShortCode = smShortCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getSmSLA() {
        return smSLA;
    }

    public void setSmSLA(Long smSLA) {
        this.smSLA = smSLA;
    }

    public Long getSmChklstVerify() {
        return smChklstVerify;
    }

    public void setSmChklstVerify(Long smChklstVerify) {
        this.smChklstVerify = smChklstVerify;
    }

    public String getSmAppliChargeFlag() {
        return smAppliChargeFlag;
    }

    public void setSmAppliChargeFlag(String smAppliChargeFlag) {
        this.smAppliChargeFlag = smAppliChargeFlag;
    }

    public MarriageDTO getMarriageDTO() {
        return marriageDTO;
    }

    public void setMarriageDTO(MarriageDTO marriageDTO) {
        this.marriageDTO = marriageDTO;
    }

    public ApplicantDetailDTO getApplicantDetailDTO() {
        return applicantDetailDTO;
    }

    public void setApplicantDetailDTO(ApplicantDetailDTO applicantDetailDTO) {
        this.applicantDetailDTO = applicantDetailDTO;
    }

    @Override
    public String toString() {
        return "MarriageResponse [orgId=" + orgId + ", serviceId=" + serviceId + ", deptId=" + deptId + ", smShortCode="
                + smShortCode + ", serviceName=" + serviceName + ", deptName=" + deptName + ", smSLA=" + smSLA
                + ", smChklstVerify=" + smChklstVerify + ", smAppliChargeFlag=" + smAppliChargeFlag + ", marriageDTO="
                + marriageDTO + ", applicantDetailDTO=" + applicantDetailDTO + "]";
    }

	public String getServiceNameMar() {
		return serviceNameMar;
	}

	public void setServiceNameMar(String serviceNameMar) {
		this.serviceNameMar = serviceNameMar;
	}

	public String getDeptNameMar() {
		return deptNameMar;
	}

	public void setDeptNameMar(String deptNameMar) {
		this.deptNameMar = deptNameMar;
	}

}
