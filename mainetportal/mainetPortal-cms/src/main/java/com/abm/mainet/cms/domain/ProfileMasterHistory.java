package com.abm.mainet.cms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author swapnil.shirke
 */
@Entity
@Table(name = "TB_EIP_PROFILE_MASTER_HIST")
public class ProfileMasterHistory extends BaseEntity {

    private static final long serialVersionUID = 1075987789606785570L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROFILE_ID_H", nullable = false, precision = 22, scale = 0)
    private long profileHistId;

    @Column(name = "PROFILE_ID", nullable = false, precision = 22, scale = 0)
    private long profileId;

    @Column(name = "ADDRESS", nullable = true, length = 200)
    private String address;

    @Column(name = "IMAGE_NAME", nullable = true, length = 200)
    private String imageName;

    @Column(name = "IMAGE_PATH", nullable = true, length = 1000)
    private String imagePath;

    @Column(name = "IMAGE_SIZE", nullable = true, length = 150)
    private String imageSize;

    @Column(name = "DESIGNATION_EN", nullable = true, length = 100)
    private String designationEn;

    @Column(name = "DESIGNATION_REG", nullable = true, length = 1000)
    private String designationReg;

    @Column(name = "EMAIL_ID", nullable = true, length = 150)
    private String emailId;

    @Column(name = "LINK_TITLE_EN", nullable = true, length = 150)
    private String linkTitleEn;

    @Column(name = "LINK_TITLE_REG", nullable = true, length = 150)
    private String linkTitleReg;

    @Column(name = "P_NAME_EN", nullable = true, length = 150)
    private String pNameEn;

    @Column(name = "P_NAME_REG", nullable = true, length = 150)
    private String pNameReg;

    @Column(name = "PRABHAG_EN", nullable = true, length = 150)
    private String prabhagEn;

    @Column(name = "PRABHAG_REG", nullable = true, length = 150)
    private String prabhagReg;

    @Column(name = "PROFILE_EN", nullable = true, length = 150)
    private String profileEn;

    @Column(name = "PROFILE_REG", nullable = true)
    private String profileReg;

    @Column(name = "CPD_SECTION", nullable = true)
    private Long cpdSection;

    @Column(name = "SUMMARY_EN", nullable = true, length = 2000)
    private String summaryEng;

    @Column(name = "SUMMARY_REG", nullable = true, length = 2000)
    private String summaryReg;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    @ForeignKey(name = "FK_EIP_PM_ORG_ID")
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", nullable = false, precision = 12, scale = 0)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    @ForeignKey(name = "FK_CHG_UPD_EMPID")
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true, length = 100)
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

    @Column(name = "H_STATUS", length = 1)
    private String status;
    
    @Column(name = "REMARK", length = 1000, nullable= true)
    private String remark;

    @Column(name = "DT_OF_JOIN", nullable = true)
    private Date dtOfJoin;
    
   
	public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    /**
     * @return the profileId
     */
    public long getProfileId() {
        return profileId;
    }

    /**
     * @param profileId the profileId to set
     */
    public void setProfileId(final long profileId) {
        this.profileId = profileId;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the imageSize
     */
    public String getImageSize() {
        return imageSize;
    }

    /**
     * @param imageSize the imageSize to set
     */
    public void setImageSize(final String imageSize) {
        this.imageSize = imageSize;
    }

    /**
     * @return the designationEn
     */
    public String getDesignationEn() {
        return designationEn;
    }

    /**
     * @param designationEn the designationEn to set
     */
    public void setDesignationEn(final String designationEn) {
        this.designationEn = designationEn;
    }

    /**
     * @return the designationReg
     */
    public String getDesignationReg() {
        return designationReg;
    }

    /**
     * @param designationReg the designationReg to set
     */
    public void setDesignationReg(final String designationReg) {
        this.designationReg = designationReg;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the linkTitleEn
     */
    public String getLinkTitleEn() {
        return linkTitleEn;
    }

    /**
     * @param linkTitleEn the linkTitleEn to set
     */
    public void setLinkTitleEn(final String linkTitleEn) {
        this.linkTitleEn = linkTitleEn;
    }

    /**
     * @return the linkTitleReg
     */
    public String getLinkTitleReg() {
        return linkTitleReg;
    }

    /**
     * @param linkTitleReg the linkTitleReg to set
     */
    public void setLinkTitleReg(final String linkTitleReg) {
        this.linkTitleReg = linkTitleReg;
    }

    /**
     * @return the pNameEn
     */
    public String getpNameEn() {
        return pNameEn;
    }

    /**
     * @param pNameEn the pNameEn to set
     */
    public void setpNameEn(final String pNameEn) {
        this.pNameEn = pNameEn;
    }

    /**
     * @return the pNameReg
     */
    public String getpNameReg() {
        return pNameReg;
    }

    /**
     * @param pNameReg the pNameReg to set
     */
    public void setpNameReg(final String pNameReg) {
        this.pNameReg = pNameReg;
    }

    /**
     * @return the prabhagEn
     */
    public String getPrabhagEn() {
        return prabhagEn;
    }

    /**
     * @param prabhagEn the prabhagEn to set
     */
    public void setPrabhagEn(final String prabhagEn) {
        this.prabhagEn = prabhagEn;
    }

    /**
     * @return the prabhagReg
     */
    public String getPrabhagReg() {
        return prabhagReg;
    }

    /**
     * @param prabhagReg the prabhagReg to set
     */
    public void setPrabhagReg(final String prabhagReg) {
        this.prabhagReg = prabhagReg;
    }

    /**
     * @return the profileEn
     */
    public String getProfileEn() {
        return profileEn;
    }

    /**
     * @param profileEn the profileEn to set
     */
    public void setProfileEn(final String profileEn) {
        this.profileEn = profileEn;
    }

    /**
     * @return the profileReg
     */
    public String getProfileReg() {
        return profileReg;
    }

    /**
     * @param profileReg the profileReg to set
     */
    public void setProfileReg(final String profileReg) {
        this.profileReg = profileReg;
    }

    /**
     * @return the cpdSection
     */
    public Long getCpdSection() {
        return cpdSection;
    }

    /**
     * @param cpdSection the cpdSection to set
     */
    public void setCpdSection(final Long cpdSection) {
        this.cpdSection = cpdSection;
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
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
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

        return getProfileId();
    }

    /**
     * @return the summaryEng
     */
    public String getSummaryEng() {
        return summaryEng;
    }

    /**
     * @param summaryEng the summaryEng to set
     */
    public void setSummaryEng(final String summaryEng) {
        this.summaryEng = summaryEng;
    }

    /**
     * @return the summaryReg
     */
    public String getSummaryReg() {
        return summaryReg;
    }

    /**
     * @param summaryReg the summaryReg to set
     */
    public void setSummaryReg(final String summaryReg) {
        this.summaryReg = summaryReg;
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

    public long getProfileHistId() {
        return profileHistId;
    }

    public void setProfileHistId(long profileHistId) {
        this.profileHistId = profileHistId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_PROFILE_MASTER_HIST", "PROFILE_ID_H" };
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDtOfJoin() {
		return dtOfJoin;
	}

	public void setDtOfJoin(Date dtOfJoin) {
		this.dtOfJoin = dtOfJoin;
	}

}
