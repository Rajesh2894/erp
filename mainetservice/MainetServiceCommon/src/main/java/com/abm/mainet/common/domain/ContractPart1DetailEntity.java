package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author apurva.salgaonkar
 * @since 19 Jan 2017
 * @comment table used to store contract ulb & witness details
 */
@Entity
@Table(name = "TB_CONTRACT_PART1_DETAIL")
public class ContractPart1DetailEntity implements Serializable {

    private static final long serialVersionUID = -2244180471282347519L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONTP1_ID", precision = 12, scale = 0, nullable = false)
    private long contp1Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONT_ID", nullable = false)
    private ContractMastEntity contId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = false)
    private Long dpDeptid;

    @Column(name = "DSGID", precision = 12, scale = 0, nullable = false)
    private Long dsgid;

    @Column(name = "EMPID", precision = 12, scale = 0, nullable = false)
    private Long empid;

    @Column(name = "CONTP1_NAME", length = 200, nullable = true)
    private String contp1Name;

    @Column(name = "CONTP1_ADDRESS", length = 500, nullable = true)
    private String contp1Address;

    @Column(name = "CONTP1_PROOF_ID_NO", length = 50, nullable = true)
    private String contp1ProofIdNo;

    @Column(name = "CONTP1_PARENT_ID", precision = 12, scale = 0, nullable = false)
    private Long contp1ParentId;

    @Column(name = "CONTP1_TYPE", length = 1, nullable = false)
    private String contp1Type;

    @Column(name = "CONTP1_ACTIVE", length = 1, nullable = false)
    private String contp1Active;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CONTP1_PHOTO_FILE_NAME", length = 200, nullable = true)
    private String contp1PhotoFileName;

    @Column(name = "CONTP1_PHOTO_FILE_PATH_NAME", length = 500, nullable = true)
    private String contp1PhotoFilePathName;

    @Column(name = "CONTP1_THUMB_FILE_NAME", length = 200, nullable = true)
    private String contp1ThumbFileName;

    @Column(name = "CONTP1_THUMB_FILE_PATH_NAME", length = 500, nullable = true)
    private String contp1ThumbFilePathName;

    public String[] getPkValues() {
        return new String[] { MainetConstants.CommonConstants.COM, MainetConstants.RnLDetailEntity.TB_CONTRACT_PART1,
                MainetConstants.RnLDetailEntity.CONTP1_ID };
    }

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

    /**
     * @return the contId
     */
    public ContractMastEntity getContId() {
        return contId;
    }

    /**
     * @param contId the contId to set
     */
    public void setContId(final ContractMastEntity contId) {
        this.contId = contId;
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

}