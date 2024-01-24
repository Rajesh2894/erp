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

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_EIP_USER_CONTACT_US_HIST")
public class EipUserContactUsHistory extends BaseEntity {
    private static final long serialVersionUID = 8386577270888820450L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ATT_ID_H", precision = 12, scale = 0, nullable = false)
    private long attHistId;

    @Column(name = "ATT_ID", precision = 12, scale = 0, nullable = false)
    private long attId;

    @Column(name = "ATT_PATH", length = 2000, nullable = true)
    private String attPath;

    @Column(name = "PHONE_NO", length = 20, nullable = true)
    private String phoneNo;
    @Column(name = "MOBILE_EXTENSION ", length = 6, nullable = true)
    private String moblieExtension;
    
    @Column(name = "DESC_QUERY", length = 500, nullable = true)
    private String descQuery;

    @Column(name = "FIRST_NAME", length = 100, nullable = true)
    private String firstName;

    @Column(name = "LAST_NAME", length = 100, nullable = true)
    private String lastName;

    @Column(name = "EMAIL_ID", length = 100, nullable = true)
    private String emailId;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

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

    @Column(name = "DMS_FOLDER_PATH", length = 100, nullable = true)
    private String folderPath;

    @Column(name = "DMS_DOC_VERSION", length = 10, nullable = true)
    private String docVersion;

    @Column(name = "DMS_DOC_NAME", length = 100, nullable = true)
    private String docName;
    
    @Column(name = "H_STATUS", length = 1)
    private String status;

    public long getAttId() {
        return attId;
    }

    public void setAttId(final long attId) {
        this.attId = attId;
    }

    public String getAttPath() {
        return attPath;
    }

    public void setAttPath(final String attPath) {
        this.attPath = attPath;
    }

    public String getDescQuery() {
        return descQuery;
    }

    public void setDescQuery(final String descQuery) {
        this.descQuery = descQuery;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
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
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
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
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
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
    public long getId() {

        return getAttId();

    }

    public long getAttHistId() {
        return attHistId;
    }

    public void setAttHistId(long attHistId) {
        this.attHistId = attHistId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "ONL", "TB_EIP_USER_CONTACT_US_HIST", "ATT_ID_H" };
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(final String phoneNo) {
        this.phoneNo = phoneNo;
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

	public String getMoblieExtension() {
		return moblieExtension;
	}

	public void setMoblieExtension(String moblieExtension) {
		this.moblieExtension = moblieExtension;
	}

}