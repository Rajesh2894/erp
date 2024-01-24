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
 * @comment table use for storing details contracted Vendor & Witness Details
 */
@Entity
@Table(name = "TB_CONTRACT_PART2_DETAIL")
public class ContractPart2DetailEntity implements Serializable {
    private static final long serialVersionUID = -9074525311855729133L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONTP2_ID", precision = 12, scale = 0, nullable = false)
    private long contp2Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONT_ID", nullable = false)
    private ContractMastEntity contId;

    @Column(name = "CONTP2V_TYPE", precision = 12, scale = 0, nullable = false)
    private Long contp2vType;

    @Column(name = "VM_VENDORID", precision = 12, scale = 0, nullable = false)
    private Long vmVendorid;

    @Column(name = "CONTP2_NAME", length = 200, nullable = false)
    private String contp2Name;

    @Column(name = "CONTP2_ADDRESS", length = 500, nullable = false)
    private String contp2Address;

    @Column(name = "CONTP2_PROOF_ID_NO", length = 50, nullable = false)
    private String contp2ProofIdNo;

    @Column(name = "CONTV_ACTIVE", length = 1, nullable = false)
    private String contvActive;

    @Column(name = "CONTP2_PARENT_ID", precision = 12, scale = 0, nullable = false)
    private Long contp2ParentId;

    @Column(name = "CONTP2_TYPE", length = 1, nullable = false)
    private String contp2Type;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "CONTP2_PRIMARY", length = 1, nullable = false)
    private String contp2Primary;

    @Column(name = "CONTP2_PHOTO_FILE_NAME", length = 200, nullable = true)
    private String contp2PhotoFileName;

    @Column(name = "CONTP2_PHOTO_FILE_PATH_NAME", length = 500, nullable = true)
    private String contp2PhotoFilePathName;

    @Column(name = "CONTP2_THUMB_FILE_NAME", length = 200, nullable = true)
    private String contp2ThumbFileName;

    @Column(name = "CONTP2_THUMB_FILE_PATH_NAME", length = 500, nullable = true)
    private String contp2ThumbFilePathName;

    public String[] getPkValues() {
        return new String[] { MainetConstants.CommonConstants.COM, MainetConstants.RnLDetailEntity.TB_CONT_PART2,
                MainetConstants.RnLDetailEntity.CONTP2_ID };
    }

    /**
     * @return the contp2Id
     */
    public long getContp2Id() {
        return contp2Id;
    }

    /**
     * @param contp2Id the contp2Id to set
     */
    public void setContp2Id(final long contp2Id) {
        this.contp2Id = contp2Id;
    }

    /**
     * @return the contp2vType
     */
    public Long getContp2vType() {
        return contp2vType;
    }

    /**
     * @param contp2vType the contp2vType to set
     */
    public void setContp2vType(final Long contp2vType) {
        this.contp2vType = contp2vType;
    }

    /**
     * @return the vmVendorid
     */
    public Long getVmVendorid() {
        return vmVendorid;
    }

    /**
     * @param vmVendorid the vmVendorid to set
     */
    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    /**
     * @return the contp2Name
     */
    public String getContp2Name() {
        return contp2Name;
    }

    /**
     * @param contp2Name the contp2Name to set
     */
    public void setContp2Name(final String contp2Name) {
        this.contp2Name = contp2Name;
    }

    /**
     * @return the contp2Address
     */
    public String getContp2Address() {
        return contp2Address;
    }

    /**
     * @param contp2Address the contp2Address to set
     */
    public void setContp2Address(final String contp2Address) {
        this.contp2Address = contp2Address;
    }

    /**
     * @return the contp2ProofIdNo
     */
    public String getContp2ProofIdNo() {
        return contp2ProofIdNo;
    }

    /**
     * @param contp2ProofIdNo the contp2ProofIdNo to set
     */
    public void setContp2ProofIdNo(final String contp2ProofIdNo) {
        this.contp2ProofIdNo = contp2ProofIdNo;
    }

    /**
     * @return the contvActive
     */
    public String getContvActive() {
        return contvActive;
    }

    /**
     * @param contvActive the contvActive to set
     */
    public void setContvActive(final String contvActive) {
        this.contvActive = contvActive;
    }

    /**
     * @return the contp2ParentId
     */
    public Long getContp2ParentId() {
        return contp2ParentId;
    }

    /**
     * @param contp2ParentId the contp2ParentId to set
     */
    public void setContp2ParentId(final Long contp2ParentId) {
        this.contp2ParentId = contp2ParentId;
    }

    /**
     * @return the contp2Type
     */
    public String getContp2Type() {
        return contp2Type;
    }

    /**
     * @param contp2Type the contp2Type to set
     */
    public void setContp2Type(final String contp2Type) {
        this.contp2Type = contp2Type;
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

    public String getContp2Primary() {
        return contp2Primary;
    }

    public void setContp2Primary(final String contp2Primary) {
        this.contp2Primary = contp2Primary;
    }

    public String getContp2PhotoFileName() {
        return contp2PhotoFileName;
    }

    public void setContp2PhotoFileName(final String contp2PhotoFileName) {
        this.contp2PhotoFileName = contp2PhotoFileName;
    }

    public String getContp2PhotoFilePathName() {
        return contp2PhotoFilePathName;
    }

    public void setContp2PhotoFilePathName(final String contp2PhotoFilePathName) {
        this.contp2PhotoFilePathName = contp2PhotoFilePathName;
    }

    public String getContp2ThumbFileName() {
        return contp2ThumbFileName;
    }

    public void setContp2ThumbFileName(final String contp2ThumbFileName) {
        this.contp2ThumbFileName = contp2ThumbFileName;
    }

    public String getContp2ThumbFilePathName() {
        return contp2ThumbFilePathName;
    }

    public void setContp2ThumbFilePathName(final String contp2ThumbFilePathName) {
        this.contp2ThumbFilePathName = contp2ThumbFilePathName;
    }
}