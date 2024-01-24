
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
public class ContractPart2DetailDTO implements Serializable {

    private static final long serialVersionUID = -1892349468268935757L;
    private long contp2Id;
    private ContractMastDTO contId;
    private Long contp2vType;
    private Long vmVendorid;
    private String contp2Name;
    private String contp2Address;
    private String contp2ProofIdNo;
    private String contvActive;
    private Long contp2ParentId;
    private String contp2Type;
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
    private String contp2Primary;
    private String venderName;
    private String contp2PhotoFileName;
    private String contp2PhotoFilePathName;
    private String contp2ThumbFileName;
    private String contp2ThumbFilePathName;
    private String active;
    private List<LookUp> vmVendoridList = new ArrayList<>();

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

    public String getContp2Primary() {
        return contp2Primary;
    }

    public void setContp2Primary(final String contp2Primary) {
        this.contp2Primary = contp2Primary;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(final String venderName) {
        this.venderName = venderName;
    }

    public List<LookUp> getVmVendoridList() {
        return vmVendoridList;
    }

    public void setVmVendoridList(final List<LookUp> vmVendoridList) {
        this.vmVendoridList = vmVendoridList;
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

    public String getActive() {
        return active;
    }

    public void setActive(final String active) {
        this.active = active;
    }

}
