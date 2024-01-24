package com.abm.mainet.cms.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EIP_PUBLIC_NOTICES")
public class PublicNotices extends BaseEntity {

    private static final long serialVersionUID = 7992538874136137893L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PN_ID", precision = 12, scale = 0, nullable = false)
    private long pnId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_ID", nullable = false)
    private Department department;

    @Column(name = "NOTICE_SUB_EN", length = 250, nullable = true)
    private String noticeSubEn;

    @Column(name = "NOTICE_SUB_REG", length = 500, nullable = true)
    private String noticeSubReg;

    @Column(name = "DETAIL_EN", length = 500, nullable = true)
    private String detailEn;

    @Column(name = "DETAIL_REG", length = 1000, nullable = true)
    private String detailReg;

    @Column(name = "ISSUE_DATE", nullable = true)
    private Date issueDate;

    @Column(name = "VALIDITY_DATE", nullable = true)
    private Date validityDate;

    @Column(name = "PUBLISH_FLAG", length = 1, nullable = true)
    private String publishFlag;

    @Column(name = "PROFILE_IMG_PATH", length = 2000, nullable = true)
    private String profileImgPath;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "DMS_FOLDER_PATH", length = 100, nullable = true)
    private String folderPath;

    @Column(name = "DMS_DOC_ID", length = 100, nullable = true)
    private String docId;

    @Column(name = "DMS_DOC_VERSION", length = 10, nullable = true)
    private String docVersion;

    @Column(name = "DMS_DOC_NAME", length = 100, nullable = true)
    private String docName;
    
    @Column(name = "LINK_NO", length = 10, nullable = true)
    private Long seqNo;
    
    @Column(name = "REMARK", length = 1000, nullable = true)
    private String remark;

    private String linkType;

    private String link;

    private String imagePath;

    private String isHighlighted;
    private String isUsefullLink;

    @Column(name = "NOTICE_TITLE")
    private String noticeTitle;

    @Transient
    private String attachmentName;

    @Transient
    private String newDate;
    
    @Transient
    private String newOrImpLink;
    
    @Transient
    private boolean categoryFlag;
    
    public String getNewOrImpLink() {
		return newOrImpLink;
	}

	public void setNewOrImpLink(String newOrImpLink) {
		this.newOrImpLink = newOrImpLink;
	}

	public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(final String newDate) {
        this.newDate = newDate;
    }

    /**
     * @return the pnId
     */
    public long getPnId() {
        return pnId;
    }

    /**
     * @param pnId the pnId to set
     */
    public void setPnId(final long pnId) {
        this.pnId = pnId;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final Department department) {
        this.department = department;
    }

    /**
     * @return the noticeSubEn
     */
    public String getNoticeSubEn() {
        return noticeSubEn;
    }

    /**
     * @param noticeSubEn the noticeSubEn to set
     */
    public void setNoticeSubEn(final String noticeSubEn) {
        this.noticeSubEn = noticeSubEn;
    }

    /**
     * @return the noticeSubReg
     */
    public String getNoticeSubReg() {
        return noticeSubReg;
    }

    /**
     * @param noticeSubReg the noticeSubReg to set
     */
    public void setNoticeSubReg(final String noticeSubReg) {
        this.noticeSubReg = noticeSubReg;
    }

    /**
     * @return the detailEn
     */
    public String getDetailEn() {
        return detailEn;
    }

    /**
     * @param detailEn the detailEn to set
     */
    public void setDetailEn(final String detailEn) {
        this.detailEn = detailEn;
    }

    /**
     * @return the detailReg
     */
    public String getDetailReg() {
        return detailReg;
    }

    /**
     * @param detailReg the detailReg to set
     */
    public void setDetailReg(final String detailReg) {
        this.detailReg = detailReg;
    }

    /**
     * @return the issueDate
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * @param issueDate the issueDate to set
     */
    public void setIssueDate(final Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * @return the validityDate
     */
    public Date getValidityDate() {
        return validityDate;
    }

    /**
     * @param validityDate the validityDate to set
     */
    public void setValidityDate(final Date validityDate) {
        this.validityDate = validityDate;
    }

    /**
     * @return the publishFlag
     */
    public String getPublishFlag() {
        return publishFlag;
    }

    /**
     * @param publishFlag the publishFlag to set
     */
    public void setPublishFlag(final String publishFlag) {
        this.publishFlag = publishFlag;
    }

    /**
     * @return the profileImgPath
     */
    public String getProfileImgPath() {
        return profileImgPath;
    }

    /**
     * @param profileImgPath the profileImgPath to set
     */
    public void setProfileImgPath(final String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    /**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {

        return getPnId();
    }

    /**
     * @return the attachmentName
     */
    public String getAttachmentName() {
        return attachmentName;
    }

    /**
     * @param attachmentName the attachmentName to set
     */
    public void setAttachmentName(final String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(final String folderPath) {
        this.folderPath = folderPath;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(final String docId) {
        this.docId = docId;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(final String docVersion) {
        this.docVersion = docVersion;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(final String docName) {
        this.docName = docName;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_PUBLIC_NOTICES", "PN_ID" };
    }

    @Transient
    private String chekkerflag1;

    public String getChekkerflag1() {
        return chekkerflag1;
    }

    public void setChekkerflag1(String chekkerflag1) {
        this.chekkerflag1 = chekkerflag1;
    }

    @Transient
    private String issueDateNew = null;

    public String getIssueDateNew() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
        if (issueDate != null) {
            issueDateNew = simpleDateFormat.format(issueDate);
        }
        return issueDateNew;
    }

    public void setIssueDateNew(String issueDateNew) {
        this.issueDateNew = issueDateNew;
    }

    @Override
    public int getLangId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLangId(int langId) {
        // TODO Auto-generated method stub

    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getIsHighlighted() {
        return isHighlighted;
    }

    public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public void setIsHighlighted(String isHighlighted) {
        this.isHighlighted = isHighlighted;
    }

    public String getIsUsefullLink() {
        return isUsefullLink;
    }

    public void setIsUsefullLink(String isUsefullLink) {
        this.isUsefullLink = isUsefullLink;
    }
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isCategoryFlag() {
		return categoryFlag;
	}

	public void setCategoryFlag(boolean categoryFlag) {
		this.categoryFlag = categoryFlag;
	}

	
}