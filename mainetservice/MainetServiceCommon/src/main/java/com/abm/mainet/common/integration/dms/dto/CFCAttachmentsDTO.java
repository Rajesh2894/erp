/**
 *
 */
package com.abm.mainet.common.integration.dms.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Arun.Chavda
 *
 */
public class CFCAttachmentsDTO implements Serializable {

    private static final long serialVersionUID = -2474078090269161091L;

    private Long attId;

    // comments : Attachment Date
    private Date attDate;

    // comments : Attachment Path
    private String attPath;

    // comments : Attach File Name
    private String attFname;

    // comments : File Attach By
    private String attBy;

    // comments : The path of folder where file gets copied
    private String attFromPath;

    // comments : Attachment From Department
    private Long dept;

    // comments : Organisation id
    private Long orgid;

    // comments : User id
    private Long userId;

    // comments : Language id
    private Long langId;

    // comments : Updated by
    private Long updatedBy;

    // comments : Updated date
    private Date updatedDate;

    // comments : Client Machine.s Login Name | IP Address | Physical Address
    private String lgIpMac;

    // Address
    private String lgIpMacUpd;

    // comments : Last modification date
    private Date lmodate;

    // comments : APPLICATION ID
    private Long applicationId;

    // comments : SERVICE ID
    private Long serviceId;

    // comments : TREE ID
    private Long clmId;

    // comments : CHECKLIST DESCRIPTION
    private String clmDesc;

    // comments : CHECKLIST STATUS
    private String clmStatus;

    // comments : CHECKLIST Sr.No.
    private Long clmSrNo;

    // comments : Checklist Details
    private Long chkStatus;

    // comments : DOCUMENT APPROVE /REJECT FLAG(APPROVE=Y / REJECT=N)
    private String clmAprStatus;

    // comments : DOCUMENT REJECT RAESON
    private String clmRemark;

    private String mandatory;

    private String clmDescEngl;

    private String document;

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
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    @JsonIgnore
    public boolean isMendatory() {
        final ListIterator<LookUp> iterator = getLookUps("SET", UserSession.getCurrent().getOrganisation()).listIterator();

        if ((chkStatus != null) && (chkStatus > 0L)) {
            while (iterator.hasNext()) {
                final LookUp lookUp = iterator.next();
                if (chkStatus == lookUp.getLookUpId()) {
                    if (lookUp.getLookUpCode().equals("CP")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public String getCcmValueStr() {
        final ListIterator<LookUp> iterator = getLookUps("SET", UserSession.getCurrent().getOrganisation()).listIterator();

        while (iterator.hasNext()) {
            final LookUp lookUp = iterator.next();
            if (chkStatus == lookUp.getLookUpId()) {
                return lookUp.getLookUpDesc();
            }
        }
        return "";
    }

    @JsonIgnore
    private List<LookUp> getLookUps(final String lookUpCode, final Organisation organisation) {

        return getAppSession().getHierarchicalLookUp(organisation, lookUpCode).get(lookUpCode);
    }

    private ApplicationSession getAppSession() {

        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    @JsonIgnore
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

}
