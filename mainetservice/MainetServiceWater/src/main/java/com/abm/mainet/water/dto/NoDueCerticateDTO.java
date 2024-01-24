package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dto.ResponseDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class NoDueCerticateDTO extends ResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String serviceName;
    private String applicantName;
    private String serviceNameMar;
    private Long applicationId;
    private String applicantDate;
    private String noDuesCertiDate;
    private Date approveDate;
    private Long orgId;
    private String approveBy;
    private String fromDate;
    private String toDate;
    private String noDuesExecFlag;
    private String orgName;
    private String certificateNo;
    private String connectionNo;
    private String applicantAdd;
    private String zone;
    private String ward;
    private String block;

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the applicantAdd
     */
    public String getApplicantAdd() {
        return applicantAdd;
    }

    /**
     * @return the applicantDate
     */
    public String getApplicantDate() {
        return applicantDate;
    }

    /**
     * @return the applicantName
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @return the approveBy
     */
    public String getApproveBy() {
        return approveBy;
    }

    /**
     * @return the approveDate
     */
    public Date getApproveDate() {
        return approveDate;
    }

    /**
     * @return the certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }

    /**
     * @return the connectionNo
     */
    public String getConnectionNo() {
        return connectionNo;
    }

    /**
     * @return the fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * @return the noDuesCertiDate
     */
    public String getNoDuesCertiDate() {
        return noDuesCertiDate;
    }

    /**
     * @return the noDuesExecFlag
     */
    public String getNoDuesExecFlag() {
        return noDuesExecFlag;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * @param applicantAdd the applicantAdd to set
     */
    public void setApplicantAdd(final String applicantAdd) {
        this.applicantAdd = applicantAdd;
    }

    /**
     * @param applicantDate the applicantDate to set
     */
    public void setApplicantDate(final String applicantDate) {
        this.applicantDate = applicantDate;
    }

    /**
     * @param applicantName the applicantName to set
     */
    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @param approveBy the approveBy to set
     */
    public void setApproveBy(final String approveBy) {
        this.approveBy = approveBy;
    }

    /**
     * @param approveDate the approveDate to set
     */
    public void setApproveDate(final Date approveDate) {
        this.approveDate = approveDate;
    }

    /**
     * @param certificateNo the certificateNo to set
     */
    public void setCertificateNo(final String certificateNo) {
        this.certificateNo = certificateNo;
    }

    /**
     * @param connectionNo the connectionNo to set
     */
    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @param noDuesCertiDate the noDuesCertiDate to set
     */
    public void setNoDuesCertiDate(final String noDuesCertiDate) {
        this.noDuesCertiDate = noDuesCertiDate;
    }

    /**
     * @param noDuesExecFlag the noDuesExecFlag to set
     */
    public void setNoDuesExecFlag(final String noDuesExecFlag) {
        this.noDuesExecFlag = noDuesExecFlag;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the serviceNameMar
     */
    public String getServiceNameMar() {
        return serviceNameMar;
    }

    /**
     * @param serviceNameMar the serviceNameMar to set
     */
    public void setServiceNameMar(final String serviceNameMar) {
        this.serviceNameMar = serviceNameMar;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

}
