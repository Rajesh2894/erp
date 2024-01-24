package com.abm.mainet.cms.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 *
 */
@Entity
@Table(name = "TB_EIP_FEEDBACK_HIST")
public class FeedbackHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2865257214075474132L;

    @Id

    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FD_ID_H", nullable = false, precision = 12, scale = 0)
    private long feedHistId;

    @Column(name = "FD_ID", nullable = false, precision = 12, scale = 0)
    private long feedId;

    @Column(name = "FD_USER_NAME", nullable = true)
    private String fdUserName;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPID", nullable = false, referencedColumnName = "EMPID")
    private Employee empId;

    @Column(name = "MOBILE_NO", nullable = true)
    private String mobileNo;
    
    @Column(name = "MOBILE_EXTENSION ", length = 6, nullable = true)
    private String moblieExtension;

    @Column(name = "EMAIL_ID", nullable = true)
    private String emailId;

    @Column(name = "FD_QUESTIONS", nullable = true)
    private String feedBackDetails;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @Column(name = "FD_FLAG", nullable = true, length = 1)
    private String activeStatus;

    @Column(name = "FD_ANSWERS", nullable = true)
    private String feedBackAnswar;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    @ForeignKey(name = "FK_EIP_PM_ORG_ID")
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = true)
    private Employee userId;

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

    @Column(name = "H_STATUS", length = 1)
    private String status;

    @Column(name = "FD_SUBJECT", nullable = true)
    private String feedBackSubject;
    
    @Column(name = "ATT_PATH", length = 2000, nullable = true)
    private String attPath;
    
    @Column(name = "catagory_type", nullable = true)
    private Long catagoryType;
    

    @Column(name = "category_type_name", length = 100, nullable = true)
    private String categoryTypeName;

	public String getFeedBackSubject() {
		return feedBackSubject;
	}

	public void setFeedBackSubject(String feedBackSubject) {
		this.feedBackSubject = feedBackSubject;
	}

	/**
     * @return the feedId
     */
    public long getFeedId() {
        return feedId;
    }

    /**
     * @param feedId the feedId to set
     */
    public void setFeedId(final long feedId) {
        this.feedId = feedId;
    }

    /**
     * @return the fdUserName
     */
    public String getFdUserName() {
        return fdUserName;
    }

    /**
     * @param fdUserName the fdUserName to set
     */
    public void setFdUserName(final String fdUserName) {
        this.fdUserName = fdUserName;
    }

    /**
     * @return the empId
     */
    public Employee getEmpId() {
        return empId;
    }

    /**
     * @param empId the empId to set
     */
    public void setEmpId(final Employee empId) {
        this.empId = empId;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo the mobileNo to set
     */
    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMoblieExtension() {
		return moblieExtension;
	}

	public void setMoblieExtension(String moblieExtension) {
		this.moblieExtension = moblieExtension;
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

        return getFeedId();
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

    public long getFeedHistId() {
        return feedHistId;
    }

    public void setFeedHistId(long feedHistId) {
        this.feedHistId = feedHistId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_FEEDBACK_HIST", "FD_ID_H" };
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

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getFeedBackAnswar() {
        return feedBackAnswar;
    }

    public void setFeedBackAnswar(String feedBackAnswar) {
        this.feedBackAnswar = feedBackAnswar;
    }

    public String getFeedBackDetails() {
        return feedBackDetails;
    }

    public void setFeedBackDetails(String feedBackDetails) {
        this.feedBackDetails = feedBackDetails;
    }
    public String getAttPath() {
		return attPath;
	}

	public void setAttPath(String attPath) {
		this.attPath = attPath;
	}
	public Long getCatagoryType() {
		return catagoryType;
	}

	public void setCatagoryType(Long catagoryType) {
		this.catagoryType = catagoryType;
	}
	
	public String getCategoryTypeName() {
		return categoryTypeName;
	}

	public void setCategoryTypeName(String categoryTypeName) {
		this.categoryTypeName = categoryTypeName;
	}

}
