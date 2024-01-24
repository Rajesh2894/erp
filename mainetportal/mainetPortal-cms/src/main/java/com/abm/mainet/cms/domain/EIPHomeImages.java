package com.abm.mainet.cms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vinay.jangir
 * @since 13 May 2014
 */
@Entity
@Table(name = "TB_EIP_HOME_IMAGES")
public class EIPHomeImages extends BaseEntity {

    private static final long serialVersionUID = -259175550646062540L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HM_IMG_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long hmImgId;

    @Column(name = "HM_IMG_ORDER", precision = 2, scale = 0, nullable = true)
    // comments : order in which image should be displayed
    private Long hmImgOrder;

    @Column(name = "IMAGE_NAME", length = 200, nullable = true)
    // comments : Image file Name
    private String imageName;

    @Column(name = "IMAGE_PATH", length = 1000, nullable = true)
    // comments : Image file path
    private String imagePath;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    // comments : Record Deletion flag - value N non-deleted record and Y- deleted record
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    // comments : Organization Id.
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    // comments : User Id
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Created Date
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = true, updatable = true)
    // comments : Modified By
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Modification Date
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    // L for logo & S for Slider
    @Column(name = "MODULE_TYPE")
    private String moduleType;

    @Column(name = "DMS_DOC_ID", nullable = true)
    private String makkerchekerflage;

    @Column(name = "DMS_FOLDER_PATH", length = 100, nullable = true)
    private String folderPath;

    @Column(name = "DMS_DOC_VERSION", length = 10, nullable = true)
    private String docVersion;

    @Column(name = "DMS_DOC_NAME", length = 100, nullable = true)
    private String docName;

    @Column(name = "CAPTION", length = 50, nullable = true)
    // comments : Image file Name
    private String caption;

    @Column(name = "CAPTION_REG", length = 50, nullable = true)
    // comments : Image file Name
    private String captionReg;
    
    @Column(name = "REMARK", length = 1000, nullable = true)
    private String remark;
    
    @Column(name = "MOBILE_ENABLE", nullable = true, length = 1)
    private String mobileEnable;

	public String getMakkerchekerflage() {
        return makkerchekerflage;
    }

    public void setMakkerchekerflage(final String makkerchekerflage) {
        this.makkerchekerflage = makkerchekerflage;
    }

    @Override
    public long getId() {
        return hmImgId;
    }

    public long getHmImgId() {
        return hmImgId;
    }

    public void setHmImgId(long hmImgId) {
        this.hmImgId = hmImgId;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(final String moduleType) {
        this.moduleType = moduleType;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the hmImgOrder
     */
    public Long getHmImgOrder() {
        return hmImgOrder;
    }

    /**
     * @param hmImgOrder the hmImgOrder to set
     */
    public void setHmImgOrder(final Long hmImgOrder) {
        this.hmImgOrder = hmImgOrder;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return the imageNames
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageNames the imageNames to set
     */
    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_HOME_IMAGES", "HM_IMG_ID" };
    }

    @Transient
    private String chekkerflag1;

    public String getChekkerflag1() {
        return chekkerflag1;
    }

    public void setChekkerflag1(String chekkerflag1) {
        this.chekkerflag1 = chekkerflag1;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaptionReg() {
        return captionReg;
    }

    public void setCaptionReg(String captionReg) {
        this.captionReg = captionReg;
    }

    @Override
    public int getLangId() {
        return 0;
    }

    @Override
    public void setLangId(int langId) {

    }
    
    public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getMobileEnable() {
		return mobileEnable;
	}

	public void setMobileEnable(String mobileEnable) {
		this.mobileEnable = mobileEnable;
	}

}