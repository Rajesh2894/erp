package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.utility.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author apurva.salgaonkar
 *
 */
public class ContractPart1DetailDTO implements Serializable {
    private static final long serialVersionUID = 5795985841167844079L;
    private long contp1Id;
    private ContractMastDTO contId;
    private Long dpDeptid;
    private Long dsgid;
    private Long empid;
    private String contp1Name;
    private String contp1Address;
    private String contp1ProofIdNo;
    private Long contp1ParentId;
    private String contp1Type;
    private String contp1Active;
    private Long orgId;
    private Long createdBy;
    private int langId;
    private Date lmodDate;
    private Long updatedBy;
    private Date updatedDate;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max=100)
    private String lgIpMacUpd;
    private String contp1PhotoFileName;
    private String contp1PhotoFilePathName;
    private String contp1ThumbFileName;
    private String contp1ThumbFilePathName;
    private String active;
    private String deptCode;

    private List<LookUp> contp1NameList = new ArrayList<>();
    private List<LookUp> desgList = new ArrayList<>();
    /**
     * @return the contp1Id
     */
    public long getContp1Id() {
        return contp1Id;
    }

    /**
     * @param contp1Id the contp1Id to set
     */
    public void setContp1Id(final long contp1Id) {
        this.contp1Id = contp1Id;
    }

    /**
     * @return the contId
     */
    public ContractMastDTO getContId() {
        return contId;
    }

    /**
     * @param contId the contId to set
     */
    public void setContId(final ContractMastDTO contId) {
        this.contId = contId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the dsgid
     */
    public Long getDsgid() {
        return dsgid;
    }

    /**
     * @param dsgid the dsgid to set
     */
    public void setDsgid(final Long dsgid) {
        this.dsgid = dsgid;
    }

    /**
     * @return the empid
     */
    public Long getEmpid() {
        return empid;
    }

    /**
     * @param empid the empid to set
     */
    public void setEmpid(final Long empid) {
        this.empid = empid;
    }

    /**
     * @return the contp1Name
     */
    public String getContp1Name() {
        return contp1Name;
    }

    /**
     * @param contp1Name the contp1Name to set
     */
    public void setContp1Name(final String contp1Name) {
        this.contp1Name = contp1Name;
    }

    /**
     * @return the contp1Address
     */
    public String getContp1Address() {
        return contp1Address;
    }

    /**
     * @param contp1Address the contp1Address to set
     */
    public void setContp1Address(final String contp1Address) {
        this.contp1Address = contp1Address;
    }

    /**
     * @return the contp1ProofIdNo
     */
    public String getContp1ProofIdNo() {
        return contp1ProofIdNo;
    }

    /**
     * @param contp1ProofIdNo the contp1ProofIdNo to set
     */
    public void setContp1ProofIdNo(final String contp1ProofIdNo) {
        this.contp1ProofIdNo = contp1ProofIdNo;
    }

    /**
     * @return the contp1ParentId
     */
    public Long getContp1ParentId() {
        return contp1ParentId;
    }

    /**
     * @param contp1ParentId the contp1ParentId to set
     */
    public void setContp1ParentId(final Long contp1ParentId) {
        this.contp1ParentId = contp1ParentId;
    }

    /**
     * @return the contp1Type
     */
    public String getContp1Type() {
        return contp1Type;
    }

    /**
     * @param contp1Type the contp1Type to set
     */
    public void setContp1Type(final String contp1Type) {
        this.contp1Type = contp1Type;
    }

    /**
     * @return the contp1Active
     */
    public String getContp1Active() {
        return contp1Active;
    }

    /**
     * @param contp1Active the contp1Active to set
     */
    public void setContp1Active(final String contp1Active) {
        this.contp1Active = contp1Active;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the langId
     */
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getContp1PhotoFilePathName() {
        return contp1PhotoFilePathName;
    }

    public void setContp1PhotoFilePathName(final String contp1PhotoFilePathName) {
        this.contp1PhotoFilePathName = contp1PhotoFilePathName;
    }

    public String getContp1ThumbFileName() {
        return contp1ThumbFileName;
    }

    public void setContp1ThumbFileName(final String contp1ThumbFileName) {
        this.contp1ThumbFileName = contp1ThumbFileName;
    }

    public String getContp1ThumbFilePathName() {
        return contp1ThumbFilePathName;
    }

    public void setContp1ThumbFilePathName(final String contp1ThumbFilePathName) {
        this.contp1ThumbFilePathName = contp1ThumbFilePathName;
    }

    public String getContp1PhotoFileName() {
        return contp1PhotoFileName;
    }

    public void setContp1PhotoFileName(final String contp1PhotoFileName) {
        this.contp1PhotoFileName = contp1PhotoFileName;
    }

    public List<LookUp> getContp1NameList() {
        return contp1NameList;
    }

    public void setContp1NameList(final List<LookUp> contp1NameList) {
        this.contp1NameList = contp1NameList;
    }

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public List<LookUp> getDesgList() {
		return desgList;
	}

	public void setDesgList(List<LookUp> desgList) {
		this.desgList = desgList;
	}
	
	
}
