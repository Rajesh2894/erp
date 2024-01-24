package com.abm.mainet.cms.domain;

import java.text.SimpleDateFormat;
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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_opinion_poll_master")
public class OpinionPoll extends BaseEntity {

    private static final long serialVersionUID = 7992538874136137893L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "POLL_ID", precision = 12, scale = 0, nullable = false)
    private long pnId;

    @Column(name = "POLL_SUB_EN", length = 250, nullable = true)
    private String pollSubEn;

    @Column(name = "POLL_SUB_REG", length = 500, nullable = true)
    private String pollSubReg;

    @Column(name = "ISSUE_DATE", nullable = true)
    private Date issueDate;

    @Column(name = "VALIDITY_DATE", nullable = true)
    private Date validityDate;

    @Column(name = "Attachment1", length = 2000, nullable = true)
    private String imgPath;
    
    @Column(name = "Attachment2", length = 2000, nullable = true)
    private String docPath;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;
    
    @Column(name = "ISARCHIVE", length = 1, nullable = true)
    private String isArchive;

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
    
	
	 @Column(name = "REMARK", length = 1000, nullable = true) private String
	  remark;
	 

    
    @Transient
    private String attachmentName;
    
    
    @Transient
    private int optionSize;
    
    @Transient
    private boolean checkOpinionValidation=false;
    
    @Transient
    private String activePolls;
   
	public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
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

    
   

    public String getPollSubEn() {
		return pollSubEn;
	}

	public void setPollSubEn(String pollSubEn) {
		this.pollSubEn = pollSubEn;
	}

	public String getPollSubReg() {
		return pollSubReg;
	}

	public void setPollSubReg(String pollSubReg) {
		this.pollSubReg = pollSubReg;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getIsArchive() {
		return isArchive;
	}

	public void setIsArchive(String isArchive) {
		this.isArchive = isArchive;
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

   

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_OPINION_POLL_MASTER", "PN_ID" };
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
    

	  public String getRemark() { return remark; }
	 
	  public void setRemark(String remark) { 
		  this.remark = remark; 
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

	public int getOptionSize() {
		return optionSize;
	}

	public void setOptionSize(int optionSize) {
		this.optionSize = optionSize;
	}

	public boolean isCheckOpinionValidation() {
		return checkOpinionValidation;
	}

	public void setCheckOpinionValidation(boolean checkOpinionValidation) {
		this.checkOpinionValidation = checkOpinionValidation;
	}

	public String getActivePolls() {
		return activePolls;
	}

	public void setActivePolls(String activePolls) {
		this.activePolls = activePolls;
	}
	
	
}