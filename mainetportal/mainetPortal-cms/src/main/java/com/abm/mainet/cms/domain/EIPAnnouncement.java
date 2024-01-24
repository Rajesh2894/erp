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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rajdeep.Sinha
 * @since 20 Jun 2014
 */
@Entity
@Table(name = "TB_EIP_ANNOUNCEMENT")
public class EIPAnnouncement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ANNOUNCE_ID", precision = 12, scale = 0, nullable = false)
    // comments : EIP ANNOUNCEMENT Primary Key Id
    private long announceId;

    @Column(name = "ANNOUNCE_DESC_ENG", length = 2000, nullable = false)
    // comments : EIP DESC ENG
    private String announceDescEng;

    @Column(name = "ANNOUNCE_DESC_REG", length = 2000, nullable = false)
    // comments : EIP DESC REG
    private String announceDescReg;

    @Column(name = "ATTACH", length = 2000, nullable = true)
    // comments : ATTACHMENT
    private String attach;

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

    @Column(name = "IMAGE", length = 100, nullable = true)
    // comments : ATTACHIMAGE
    private String attachImage;

    @Column(name = "DMS_IMG_FOLDER_PATH", length = 100, nullable = true)
    private String imgFolderPath;

    @Column(name = "DMS_IMG_ID", length = 100, nullable = true)
    private String imgDocId;

    @Column(name = "DMS_IMG_VERSION", length = 10, nullable = true)
    private String imgDocVersion;

    @Column(name = "DMS_IMG_NAME", length = 100, nullable = true)
    private String imgDocName;

    @Temporal(TemporalType.DATE)
    @Column(name = "ANNOUNCE_DATE")
    private Date newsDate;

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
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    // comments : Modified By
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Modification Date
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "VALIDITY_DATE", nullable = true)
    private Date validityDate;
    
    @Column(name = "REMARK", length = 1000, nullable = true)
    private String remark;
    
    @Column(name="HIGHLIGHTED_DATE" , nullable=true)
    private Date highlightedDate;
    
    public Date getHighlightedDate() {
		return highlightedDate;
	}

	public void setHighlightedDate(Date highlightedDate) {
		this.highlightedDate = highlightedDate;
	}
	
    @Column(name = "LINKTYPE", length = 1, nullable = true)
    private String linkType;

    @Column(name = "LINK", length = 500, nullable = true)
    private String link;
    
    
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

	//comment: flag true-latest news
	@Transient
    private String isHighlighted;
	
	@Column(name = "ISHIGHLIGHTEDFLAG", length = 1)
	private String isHighlightedFlag;
    
	public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    public long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(final long announceId) {
        this.announceId = announceId;
    }

    public String getAnnounceDescEng() {
        return announceDescEng;
    }

    public void setAnnounceDescEng(final String announceDescEng) {
        this.announceDescEng = announceDescEng;
    }

    public String getAnnounceDescReg() {
        return announceDescReg;
    }

    public void setAnnounceDescReg(final String announceDescReg) {
        this.announceDescReg = announceDescReg;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(final String attach) {
        this.attach = attach;
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

        return getAnnounceId();
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

    public String getAttachImage() {
        return attachImage;
    }

    public void setAttachImage(String attachImage) {
        this.attachImage = attachImage;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_ANNOUNCEMENT", "ANNOUNCE_ID" };
    }

    @Transient
    private String chekkerflag1;

    public String getChekkerflag1() {
        return chekkerflag1;
    }

    public void setChekkerflag1(String chekkerflag1) {
        this.chekkerflag1 = chekkerflag1;
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

    public String getImgFolderPath() {
        return imgFolderPath;
    }

    public void setImgFolderPath(String imgFolderPath) {
        this.imgFolderPath = imgFolderPath;
    }

    public String getImgDocId() {
        return imgDocId;
    }

    public void setImgDocId(String imgDocId) {
        this.imgDocId = imgDocId;
    }

    public String getImgDocVersion() {
        return imgDocVersion;
    }

    public void setImgDocVersion(String imgDocVersion) {
        this.imgDocVersion = imgDocVersion;
    }

    public String getImgDocName() {
        return imgDocName;
    }

    public void setImgDocName(String imgDocName) {
        this.imgDocName = imgDocName;
    }

    public Employee getUserId() {
        return userId;
    }

    public void setUserId(Employee userId) {
        this.userId = userId;
    }

    @Transient
    private String attachName;

    @Transient
    private String attachImageName;

    public String getAttachName() {
        if (this.attach != null && this.attach != "") {
			if (attach.contains("\\")) {
				attachName = this.attach.substring(this.attach.lastIndexOf("\\") + 1);
				if (attach.contains("/")) {
					attachName = this.attach.substring(this.attach.lastIndexOf("/") + 1);	
				}
			} 	
			else {
				attachName = this.attach.substring(this.attach.lastIndexOf("/") + 1);
        		 if(attachName.contains("\\")) {
        			 attachName = this.attach.substring(this.attach.lastIndexOf("\\") + 1);
                 }
			}
        }
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public String getAttachImageName() {
        if (this.attachImage != null && this.attachImage != "") {
        	if (attachImage.contains("\\")) {
            attachImageName = this.attachImage.substring(this.attachImage.lastIndexOf("\\") + 1);
            if(attachImageName.contains("/")) {
            	attachImageName=this.attachImage.substring(this.attachImage.lastIndexOf("/") + 1);
            }
        	 } else {
        		 attachImageName = this.attachImage.substring(this.attachImage.lastIndexOf("/") + 1);
        		 if(attachImageName.contains("\\")) {
        			 attachImageName = this.attachImage.substring(this.attachImage.lastIndexOf("\\") + 1);
                 }
        	 }
        	
        }
        return attachImageName;
    }
    

    public void setAttachImageName(String attachImageName) {
        this.attachImageName = attachImageName;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public Date getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Date validityDate) {
        this.validityDate = validityDate;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	 public String getIsHighlighted() {
			return isHighlighted;
	}

	 public void setIsHighlighted(String isHighlighted) {
			this.isHighlighted = isHighlighted;
	 }

	public String getIsHighlightedFlag() {
		return isHighlightedFlag;
	}

	public void setIsHighlightedFlag(String isHighlightedFlag) {
		this.isHighlightedFlag = isHighlightedFlag;
	}

	 
	 
}