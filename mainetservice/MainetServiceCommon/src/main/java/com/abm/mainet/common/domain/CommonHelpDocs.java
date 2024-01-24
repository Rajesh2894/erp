package com.abm.mainet.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vinay.jangir
 * @since 08 Jul 2014
 */
@Entity
@Table(name = "TB_COM_HELP_DOCS")
public class CommonHelpDocs extends BaseEntity {

    private static final long serialVersionUID = -3419406181688977828L;
    @Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")	
    @Column(name = "HELP_DOC_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long helpDocId;

    @Column(name = "MODULE_NAME", precision = 12, scale = 0, nullable = false)
    // comments : Name of the module for which the help doc is being uploaded
    private String moduleName;

    @Column(name = "FILE_NAME_ENG", length = 100, nullable = false)
    // comments : Name of the file being uploaded
    private String fileNameEng;

    @Column(name = "FILE_PATH_ENG", length = 2000, nullable = true)
    // comments : Path of the file being uploaded
    private String filePath;

    @Column(name = "FILE_TYPE_ENG", length = 1, nullable = false)
    // comments : Type of the file being uploaded
    private Character fileTypeEng;

    @Column(name = "FILE_NAME_REG", length = 100, nullable = false)
    // comments : Name of the file being uploaded
    private String fileNameReg;

    @Column(name = "FILE_PATH_REG", length = 2000, nullable = true)
    // comments : Path of the file being uploaded
    private String filePathReg;

    @Column(name = "FILE_TYPE_REG", length = 1, nullable = false)
    // comments : Type of the file being uploaded
    private Character fileTypeReg;

    @Column(name = "ATTACH_BY", precision = 12, scale = 0, nullable = true)
    // comments : Person by whom the file has been uploaded
    private Long attachBy;

    @Column(name = "ATTACH_ON", nullable = true)
    // comments : Date on which the file has been uploaded
    private Date attachOn;

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
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    // comments : User Id
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    // comments : Language Id
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Last Modification Date
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
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

    @Column(name = "DEPTNAME", nullable = true)
    private String deptCode;

    @Transient
    private List<CFCAttachment> cfcAttachments = new ArrayList<>(0);

    @Transient
    private Character checkHelpDocType;

    public List<CFCAttachment> getCfcAttachments() {
        return cfcAttachments;
    }

    public void setCfcAttachments(final List<CFCAttachment> cfcAttachments) {
        this.cfcAttachments = cfcAttachments;
    }

    @Transient
    private String imageName;

    @Override
    public long getId() {
        return helpDocId;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    public String getFileNameEng() {
        return fileNameEng;
    }

    public void setFileNameEng(final String fileNameEng) {
        this.fileNameEng = fileNameEng;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(final String filePath) {
        this.filePath = filePath;
    }

    public Character getFileTypeEng() {
        return fileTypeEng;
    }

    public void setFileTypeEng(final Character fileTypeEng) {
        this.fileTypeEng = fileTypeEng;
    }

    public String getFileNameReg() {
        return fileNameReg;
    }

    public void setFileNameReg(final String fileNameReg) {
        this.fileNameReg = fileNameReg;
    }

    public String getFilePathReg() {
        return filePathReg;
    }

    public void setFilePathReg(final String filePathReg) {
        this.filePathReg = filePathReg;
    }

    public Character getFileTypeReg() {
        return fileTypeReg;
    }

    public void setFileTypeReg(final Character fileTypeReg) {
        this.fileTypeReg = fileTypeReg;
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
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
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
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(final String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return the attachBy
     */
    public Long getAttachBy() {
        return attachBy;
    }

    /**
     * @param attachBy the attachBy to set
     */
    public void setAttachBy(final Long attachBy) {
        this.attachBy = attachBy;
    }

    /**
     * @return the attachOn
     */
    public Date getAttachOn() {
        return attachOn;
    }

    /**
     * @param attachOn the attachOn to set
     */
    public void setAttachOn(final Date attachOn) {
        this.attachOn = attachOn;
    }

    /**
     * @return the filePathInLocal
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param filePathInLocal the filePathInLocal to set
     */
    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    public long getHelpDocId() {
        return helpDocId;
    }

    public void setHelpDocId(final long helpDocId) {
        this.helpDocId = helpDocId;
    }

    public String getDocs() {
        if (getFileNameReg() != null) {
            return getFileNameEng() + "\n" + getFileNameReg();
        }
        return getFileNameEng();
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_COM_HELP_DOCS", "HELP_DOC_ID" };
    }

    public Character getCheckHelpDocType() {
        return checkHelpDocType;
    }

    public void setCheckHelpDocType(final Character checkHelpDocType) {
        this.checkHelpDocType = checkHelpDocType;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(final String deptCode) {
        this.deptCode = deptCode;
    }

}