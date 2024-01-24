/**
 * 
 */
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
 * @author divya.marshettiwar
 *
 */
@Entity
@Table(name = "tb_contract_part2_detail_hist")
public class ContractPart2DetailHistEntity implements Serializable {

	private static final long serialVersionUID = 6544654790084967997L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CONTP2_ID_H", precision = 12, scale = 0, nullable = false)
    private long contp2IdH;
	
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
    
    @Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;
    
    public String[] getPkValues() {
        return new String[] { MainetConstants.CommonConstants.COM, "tb_contract_part2_detail_hist",
                "CONTP2_ID_H" };
    }

	public long getContp2IdH() {
		return contp2IdH;
	}

	public void setContp2IdH(long contp2IdH) {
		this.contp2IdH = contp2IdH;
	}

	public long getContp2Id() {
		return contp2Id;
	}

	public void setContp2Id(long contp2Id) {
		this.contp2Id = contp2Id;
	}

	public ContractMastEntity getContId() {
		return contId;
	}

	public void setContId(ContractMastEntity contId) {
		this.contId = contId;
	}

	public Long getContp2vType() {
		return contp2vType;
	}

	public void setContp2vType(Long contp2vType) {
		this.contp2vType = contp2vType;
	}

	public Long getVmVendorid() {
		return vmVendorid;
	}

	public void setVmVendorid(Long vmVendorid) {
		this.vmVendorid = vmVendorid;
	}

	public String getContp2Name() {
		return contp2Name;
	}

	public void setContp2Name(String contp2Name) {
		this.contp2Name = contp2Name;
	}

	public String getContp2Address() {
		return contp2Address;
	}

	public void setContp2Address(String contp2Address) {
		this.contp2Address = contp2Address;
	}

	public String getContp2ProofIdNo() {
		return contp2ProofIdNo;
	}

	public void setContp2ProofIdNo(String contp2ProofIdNo) {
		this.contp2ProofIdNo = contp2ProofIdNo;
	}

	public String getContvActive() {
		return contvActive;
	}

	public void setContvActive(String contvActive) {
		this.contvActive = contvActive;
	}

	public Long getContp2ParentId() {
		return contp2ParentId;
	}

	public void setContp2ParentId(Long contp2ParentId) {
		this.contp2ParentId = contp2ParentId;
	}

	public String getContp2Type() {
		return contp2Type;
	}

	public void setContp2Type(String contp2Type) {
		this.contp2Type = contp2Type;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String getContp2Primary() {
		return contp2Primary;
	}

	public void setContp2Primary(String contp2Primary) {
		this.contp2Primary = contp2Primary;
	}

	public String getContp2PhotoFileName() {
		return contp2PhotoFileName;
	}

	public void setContp2PhotoFileName(String contp2PhotoFileName) {
		this.contp2PhotoFileName = contp2PhotoFileName;
	}

	public String getContp2PhotoFilePathName() {
		return contp2PhotoFilePathName;
	}

	public void setContp2PhotoFilePathName(String contp2PhotoFilePathName) {
		this.contp2PhotoFilePathName = contp2PhotoFilePathName;
	}

	public String getContp2ThumbFileName() {
		return contp2ThumbFileName;
	}

	public void setContp2ThumbFileName(String contp2ThumbFileName) {
		this.contp2ThumbFileName = contp2ThumbFileName;
	}

	public String getContp2ThumbFilePathName() {
		return contp2ThumbFilePathName;
	}

	public void setContp2ThumbFilePathName(String contp2ThumbFilePathName) {
		this.contp2ThumbFilePathName = contp2ThumbFilePathName;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
    
}
