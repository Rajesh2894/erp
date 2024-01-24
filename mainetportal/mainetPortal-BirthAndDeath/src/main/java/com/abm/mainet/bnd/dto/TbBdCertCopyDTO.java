package com.abm.mainet.bnd.dto;
import java.io.Serializable;
import java.util.Date;
/**
 * The persistent class for the tb_bd_cert_copy database table.
 * 
 */
public class TbBdCertCopyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long bdId;
	private Long brId;
	private String certNo;
	private Long copyNo;
	private Long drId;
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private Long orgid;
	private Long rtIdOrApplicationId;
	private String status;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private String regNo;
	private Long APMApplicationId;
	private Long nacBrId;
	private Long nacDrId;
	private Long serviceId;
	private String filePath;
	private String serviceCode;

	public TbBdCertCopyDTO() {
	}
	public String getCertNo() {
		return this.certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public int getLangId() {
		return this.langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public String getLgIpMac() {
		return this.lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}
	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	public Date getLmoddate() {
		return this.lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdatedDate() {
		return this.updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getBdId() {
		return bdId;
	}
	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}
	public Long getBrId() {
		return brId;
	}
	public void setBrId(Long brId) {
		this.brId = brId;
	}
	public Long getCopyNo() {
		return copyNo;
	}
	public void setCopyNo(Long copyNo) {
		this.copyNo = copyNo;
	}
	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getRtIdOrApplicationId() {
		return rtIdOrApplicationId;
	}

	public void setRtIdOrApplicationId(Long rtIdOrApplicationId) {
		this.rtIdOrApplicationId = rtIdOrApplicationId;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public Long getAPMApplicationId() {
		return APMApplicationId;
	}
	public void setAPMApplicationId(Long aPMApplicationId) {
		APMApplicationId = aPMApplicationId;
	}
	public Long getNacBrId() {
		return nacBrId;
	}
	public void setNacBrId(Long nacBrId) {
		this.nacBrId = nacBrId;
	}
	public Long getNacDrId() {
		return nacDrId;
	}
	public void setNacDrId(Long nacDrId) {
		this.nacDrId = nacDrId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	
	
}