package com.abm.mainet.cfc.checklist.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 * @since 15 Oct 2016
 */
@Entity
@Table(name = "TB_DOCUMENT_GROUP")
// Define named queries here
@NamedQueries({
        @NamedQuery(name = "TbDocumentGroupEntity.countAll", query = "SELECT COUNT(x) FROM TbDocumentGroupEntity x")
})
public class TbDocumentGroupEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DG_ID", precision = 12, scale = 0, nullable = false)
    private long dgId;

    @Column(name = "GROUP_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long groupCpdId;

    @Column(name = "DOC_NAME", length = 100, nullable = true)
    private String docName;

    @Column(name = "DOC_TYPE", length = 20, nullable = true)
    private String docType;

    @Column(name = "DOC_SIZE", precision = 5, scale = 0, nullable = true)
    private Long docSize;

    @Column(name = "CCM_DOCUMENT_FLAG")
    private Long ccmValueset;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "DOC_SR_NO", nullable = false)
    private Long docSrNo;

    @Column(name = "DOC_STATUS")
    private String docStatus;
    
    @Column(name = "DOC_NAME_REG", length = 100, nullable = true)
    private String docNameReg;
    
    @Column(name = "DOC_TYPE_REG", length = 20, nullable = true)
    private String docTypeReg;
    
    @Column(name = "DOC_PREFIX_REQ", length = 3, nullable = true)
    private String docPrefixRequired;
    
    @Column(name = "PREFIX_NAME", length = 5, nullable = true)
    private String prefixName;
    

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
    private Organisation tbOrganisation;

    public long getDgId() {
        return dgId;
    }

    public void setDgId(final long dgId) {
        this.dgId = dgId;
    }

    public Long getGroupCpdId() {
        return groupCpdId;
    }

    public void setGroupCpdId(final Long groupCpdId) {
        this.groupCpdId = groupCpdId;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(final String docName) {
        this.docName = docName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(final String docType) {
        this.docType = docType;
    }

    public Long getDocSize() {
        return docSize;
    }

    public void setDocSize(final Long docSize) {
        this.docSize = docSize;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Organisation getTbOrganisation() {
        return tbOrganisation;
    }

    public void setTbOrganisation(final Organisation tbOrganisation) {
        this.tbOrganisation = tbOrganisation;
    }

    public Long getDocSrNo() {
        return docSrNo;
    }

    public void setDocSrNo(final Long docSrNo) {
        this.docSrNo = docSrNo;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(final String docStatus) {
        this.docStatus = docStatus;
    }

    public Long getCcmValueset() {
        return ccmValueset;
    }

    public void setCcmValueset(final Long ccmValueset) {
        this.ccmValueset = ccmValueset;
    }
   

    public String getDocNameReg() {
		return docNameReg;
	}

	public void setDocNameReg(String docNameReg) {
		this.docNameReg = docNameReg;
	}

	public String getDocTypeReg() {
		return docTypeReg;
	}

	public void setDocTypeReg(String docTypeReg) {
		this.docTypeReg = docTypeReg;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_DOCUMENT_GROUP", "DG_ID" };
    }

	public String getDocPrefixRequired() {
		return docPrefixRequired;
	}

	public void setDocPrefixRequired(String docPrefixRequired) {
		this.docPrefixRequired = docPrefixRequired;
	}

	public String getPrefixName() {
		return prefixName;
	}

	public void setPrefixName(String prefixName) {
		this.prefixName = prefixName;
	}

	
}