package com.abm.mainet.common.integration.dms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Lalit.Prusti
 * @since 10 Mar 2016
 */
@Entity
@Table(name = "TB_ATTACH_CFC")
public class CFCAttachment implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1228360814089410771L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ATT_ID", precision = 12, scale = 0, nullable = false)
    // comments : Attachment no
    private Long attId;

    @Column(name = "ATT_DATE", nullable = false)
    // comments : Attachment Date
    private Date attDate;

    @Column(name = "ATT_PATH", length = 255, nullable = true)
    // comments : Attachment Path
    private String attPath;

    @Column(name = "ATT_FNAME", length = 500, nullable = true)
    // comments : Attach File Name
    private String attFname;

    @Column(name = "ATT_BY", length = 255, nullable = true)
    // comments : File Attach By
    private String attBy;

    @Column(name = "ATT_FROM_PATH", length = 255, nullable = true)
    // comments : The path of folder where file gets copied
    private String attFromPath;

    @Column(name = "DEPT", precision = 12, scale = 0, nullable = true)
    // comments : Attachment From Department
    private Long dept;

    @Column(name = "ORGID", precision = 12, scale = 0, nullable = false)
    // comments : Organisation id
    private Long orgid;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = true)
    // comments : User id
    private Long userId;

    @Column(name = "UPDATED_BY", precision = 12, scale = 0, nullable = true)
    // comments : Updated by
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Updated date
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine.s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine.s Login Name | IP Address | Physical
    // Address
    private String lgIpMacUpd;

    @Column(name = "CREATED_DATE", nullable = true)
    // comments : Last modification date
    private Date lmodate;

    @Column(name = "APPLICATION_ID", precision = 30, scale = 0, nullable = true)
    // comments : APPLICATION ID
    private Long applicationId;

    @Column(name = "REFERENCE_ID")
    private String referenceId;

    @Column(name = "SERVICE_ID", precision = 12, scale = 0, nullable = true)
    // comments : SERVICE ID
    private Long serviceId;

    @Column(name = "CLM_ID", precision = 12, scale = 0, nullable = true)
    // comments : TREE ID
    private Long clmId;

    @Column(name = "CLM_DESC", length = 2000, nullable = true)
    // comments : CHECKLIST DESCRIPTION
    private String clmDesc;

    @Column(name = "CLM_STATUS", length = 1, nullable = true)
    // comments : CHECKLIST STATUS
    private String clmStatus;

    @Column(name = "CLM_SR_NO", precision = 3, scale = 0, nullable = true)
    // comments : CHECKLIST Sr.No.
    private Long clmSrNo;

    @Column(name = "CHK_STATUS", precision = 12, scale = 0, nullable = true)
    // comments : Checklist Details
    private Long chkStatus;

    @Column(name = "CLM_APR_STATUS", length = 1, nullable = true)
    // comments : DOCUMENT APPROVE /REJECT FLAG(APPROVE=Y / REJECT=N)
    private String clmAprStatus;

    @Column(name = "CLM_REMARK", length = 300, nullable = true)
    // comments : DOCUMENT REJECT RAESON
    private String clmRemark;

    @Column(name = "MANDATORY", length = 1, nullable = true)
    private String mandatory;

    @Column(name = "CLM_DESC_ENGL", length = 2000, nullable = true)
    private String clmDescEngl;

    @Column(name = "DMS_DOC_ID", nullable = true)
    private String dmsDocId;

    @Column(name = "DMS_FOLDER_PATH", nullable = true)
    private String dmsFolderPath;

    @Column(name = "DMS_DOC_NAME", nullable = true)
    private String dmsDocName;

    @Column(name = "DMS_DOC_VERSION", nullable = true)
    private String dmsDocVersion;

    @Column(name = "WORKFLOW_ACT_ID", precision = 16, scale = 0, nullable = true)
    private Long workflowActId;

    @Column(name = "SM_SCRUTINY_ID", precision = 12, scale = 0, nullable = true)
    private Long smScrutinyId;

    @Column(name = "SM_SCRUTINY_LEVEL", precision = 2, scale = 0, nullable = true)
    private Long smScrutinyLevel;

    @Column(name = "GM_ID", nullable = true)
    private Long gmId;
    
    @Column(name = "DOC_DESCRIPTION",length = 50,  nullable = true)
    private String docDescription;

    /**
     * @return the attId
     */
    public Long getAttId() {
        return attId;
    }

    /**
     * @param attId the attId to set
     */
    public void setAttId(final Long attId) {
        this.attId = attId;
    }

    /**
     * @return the attDate
     */
    public Date getAttDate() {
        return attDate;
    }

    /**
     * @param attDate the attDate to set
     */
    public void setAttDate(final Date attDate) {
        this.attDate = attDate;
    }

    /**
     * @return the attPath
     */
    public String getAttPath() {
        return attPath;
    }

    /**
     * @param attPath the attPath to set
     */
    public void setAttPath(final String attPath) {
        this.attPath = attPath;
    }

    /**
     * @return the attFname
     */
    public String getAttFname() {
        return attFname;
    }

    /**
     * @param attFname the attFname to set
     */
    public void setAttFname(final String attFname) {
        this.attFname = attFname;
    }

    /**
     * @return the attBy
     */
    public String getAttBy() {
        return attBy;
    }

    /**
     * @param attBy the attBy to set
     */
    public void setAttBy(final String attBy) {
        this.attBy = attBy;
    }

    /**
     * @return the attFromPath
     */
    public String getAttFromPath() {
        return attFromPath;
    }

    /**
     * @param attFromPath the attFromPath to set
     */
    public void setAttFromPath(final String attFromPath) {
        this.attFromPath = attFromPath;
    }

    /**
     * @return the dept
     */
    public Long getDept() {
        return dept;
    }

    /**
     * @param dept the dept to set
     */
    public void setDept(final Long dept) {
        this.dept = dept;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the lmodate
     */
    public Date getLmodate() {
        return lmodate;
    }

    /**
     * @param lmodate the lmodate to set
     */
    public void setLmodate(final Date lmodate) {
        this.lmodate = lmodate;
    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the clmId
     */
    public Long getClmId() {
        return clmId;
    }

    /**
     * @param clmId the clmId to set
     */
    public void setClmId(final Long clmId) {
        this.clmId = clmId;
    }

    /**
     * @return the clmDesc
     */
    public String getClmDesc() {
        return clmDesc;
    }

    /**
     * @param clmDesc the clmDesc to set
     */
    public void setClmDesc(final String clmDesc) {
        this.clmDesc = clmDesc;
    }

    /**
     * @return the clmStatus
     */
    public String getClmStatus() {
        return clmStatus;
    }

    /**
     * @param clmStatus the clmStatus to set
     */
    public void setClmStatus(final String clmStatus) {
        this.clmStatus = clmStatus;
    }

    /**
     * @return the clmSrNo
     */
    public Long getClmSrNo() {
        return clmSrNo;
    }

    /**
     * @param clmSrNo the clmSrNo to set
     */
    public void setClmSrNo(final Long clmSrNo) {
        this.clmSrNo = clmSrNo;
    }

    /**
     * @return the chkStatus
     */
    public Long getChkStatus() {
        return chkStatus;
    }

    /**
     * @param chkStatus the chkStatus to set
     */
    public void setChkStatus(final Long chkStatus) {
        this.chkStatus = chkStatus;
    }

    /**
     * @return the clmAprStatus
     */
    public String getClmAprStatus() {
        return clmAprStatus;
    }

    /**
     * @param clmAprStatus the clmAprStatus to set
     */
    public void setClmAprStatus(final String clmAprStatus) {
        this.clmAprStatus = clmAprStatus;
    }

    /**
     * @return the clmRemark
     */
    public String getClmRemark() {
        return clmRemark;
    }

    /**
     * @param clmRemark the clmRemark to set
     */
    public void setClmRemark(final String clmRemark) {
        this.clmRemark = clmRemark;
    }

    /**
     * @return the mandatory
     */
    public String getMandatory() {
        return mandatory;
    }

    /**
     * @param mandatory the mandatory to set
     */
    public void setMandatory(final String mandatory) {
        this.mandatory = mandatory;
    }

    /**
     * @return the clmDescEngl
     */
    public String getClmDescEngl() {
        return clmDescEngl;
    }

    /**
     * @param clmDescEngl the clmDescEngl to set
     */
    public void setClmDescEngl(final String clmDescEngl) {
        this.clmDescEngl = clmDescEngl;
    }

    public String getApprovalStatus() {
        String status = null;
        switch (getClmAprStatus()) {
        case "Y":
            status = "APPROVRD";
            break;
        case "N":
            status = "REJECT";
            break;
        case "H":
            status = "HOLD";
            break;
        default:
            status = "";
            break;
        }
        return status;
    }

    /*
     * public String[] getPkValues() { return new String[] { "CFC", "TB_ATTACH_CFC", "ATT_ID", getOrgid().toString(),"C",null }; }
     */
    public String[] getPkValues() {

        return new String[] { "CFC", "TB_ATTACH_CFC", "ATT_ID" };
    }

    public Long getWorkflowActId() {
        return workflowActId;
    }

    public void setWorkflowActId(Long workflowActId) {
        this.workflowActId = workflowActId;
    }

    public Long getSmScrutinyId() {
        return smScrutinyId;
    }

    public void setSmScrutinyId(Long smScrutinyId) {
        this.smScrutinyId = smScrutinyId;
    }

    public Long getSmScrutinyLevel() {
        return smScrutinyLevel;
    }

    public void setSmScrutinyLevel(Long smScrutinyLevel) {
        this.smScrutinyLevel = smScrutinyLevel;
    }

    public Long getGmId() {
        return gmId;
    }

    public void setGmId(Long gmId) {
        this.gmId = gmId;
    }

    public String getDmsDocId() {
        return dmsDocId;
    }

    public void setDmsDocId(String dmsDocId) {
        this.dmsDocId = dmsDocId;
    }

    public String getDmsFolderPath() {
        return dmsFolderPath;
    }

    public void setDmsFolderPath(String dmsFolderPath) {
        this.dmsFolderPath = dmsFolderPath;
    }

    public String getDmsDocName() {
        return dmsDocName;
    }

    public void setDmsDocName(String dmsDocName) {
        this.dmsDocName = dmsDocName;
    }

    public String getDmsDocVersion() {
        return dmsDocVersion;
    }

    public void setDmsDocVersion(String dmsDocVersion) {
        this.dmsDocVersion = dmsDocVersion;
    }

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}
    
    
}