package com.abm.mainet.cfc.checklist.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.master.dto.TbComparamDet;

/**
 * @author hiren.poriya
 *
 */
public class TbDocumentGroup {

    private Long dgId;

    private Long groupCpdId;

    private String docName;

    private String docType;

    private Long docSize;

    private Long ccmValueset;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private String docStatus;

    private Long hiddenGroupCpdId;

    private Long docSrNo;
    private Long orgid;
    
    private String docNameReg;
    
    private String docTypeReg;
    
    private String docPrefixRequired;

    private String prefixName;

    private List<TbComparamDet> valueSet = new ArrayList<>();

    public Long getDgId() {
        return dgId;
    }

    public void setDgId(final Long dgId) {
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

    public List<TbComparamDet> getValueSet() {
        return valueSet;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public void setValueSet(final List<TbComparamDet> valueSet) {
        this.valueSet = valueSet;
    }

    public Long getHiddenGroupCpdId() {
        return hiddenGroupCpdId;
    }

    public void setHiddenGroupCpdId(final Long hiddenGroupCpdId) {
        this.hiddenGroupCpdId = hiddenGroupCpdId;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(final String docStatus) {
        this.docStatus = docStatus;
    }

    public Long getDocSrNo() {
        return docSrNo;
    }

    public void setDocSrNo(final Long docSrNo) {
        this.docSrNo = docSrNo;
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
