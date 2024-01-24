package com.abm.mainet.bnd.dto;
import java.io.Serializable;
import java.util.Date;


/*	@JsonAutoDetect
	@JsonSerialize
	@JsonDeserialize
	@NamedQuery(name="TbCemetery.findAll", query="SELECT t FROM CemeteryMaster t")*/
	public class CemeteryMasterDTO implements Serializable {
		
		
		private static final long serialVersionUID = -2498307488575123462L;
		
	
	
		
		private Long ceId;

		private Long agencyId;
	
		private Long cpdTypeId;
		
		private String ceAddr;
		
		private String ceAddrMar;
		
		private String ceName;
	
		private String ceNameMar;

		private String ceStatus;

		private Long langId;

		private String lgIpMac;
	
		private String lgIpMacUpd;
		
		private Date lmoddate;
		
		private Long orgid;

		private Long userId;
		
		private Long updatedBy;

		private Date updatedDate;
		
		private String cpdDesc;
		private String cpdDescMar;
		
		private Long wardid;

		public Long getCeId() {
			return ceId;
		}

		public void setCeId(Long ceId) {
			this.ceId = ceId;
		}

		public Long getAgencyId() {
			return agencyId;
		}

		public void setAgencyId(Long agencyId) {
			this.agencyId = agencyId;
		}

		public Long getCpdTypeId() {
			return cpdTypeId;
		}

		public void setCpdTypeId(Long cpdTypeId) {
			this.cpdTypeId = cpdTypeId;
		}

		public String getCeAddr() {
			return ceAddr;
		}

		public void setCeAddr(String ceAddr) {
			this.ceAddr = ceAddr;
		}

		public String getCeAddrMar() {
			return ceAddrMar;
		}

		public void setCeAddrMar(String ceAddrMar) {
			this.ceAddrMar = ceAddrMar;
		}

		public String getCeName() {
			return ceName;
		}

		public void setCeName(String ceName) {
			this.ceName = ceName;
		}

		public String getCeNameMar() {
			return ceNameMar;
		}

		public void setCeNameMar(String ceNameMar) {
			this.ceNameMar = ceNameMar;
		}

		public String getCeStatus() {
			return ceStatus;
		}

		public void setCeStatus(String ceStatus) {
			this.ceStatus = ceStatus;
		}

		public Long getLangId() {
			return langId;
		}

		public void setLangId(Long langId) {
			this.langId = langId;
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

		public Date getLmoddate() {
			return lmoddate;
		}

		public void setLmoddate(Date lmoddate) {
			this.lmoddate = lmoddate;
		}

		public Long getOrgid() {
			return orgid;
		}

		public void setOrgid(Long orgid) {
			this.orgid = orgid;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
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

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getCpdDescMar() {
			return cpdDescMar;
		}

		public void setCpdDescMar(String cpdDescMar) {
			this.cpdDescMar = cpdDescMar;
		}

		public String getCpdDesc() {
			return cpdDesc;
		}

		public void setCpdDesc(String cpdDesc) {
			this.cpdDesc = cpdDesc;
		}

		public Long getWardid() {
			return wardid;
		}

		public void setWardid(Long wardid) {
			this.wardid = wardid;
		}
		
	
	}
	
	
	
	

