package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;

public class StoreGroupMappingDto implements Serializable {
    
	
	private static final long serialVersionUID = 1L;

	    private Long groupMapId;
		private Long itemGroupId;
		private char status;
		private Long orgId;
	    private Long userId;
	    private Long langId;
	    private Date lmoDate;
	    private Long updatedBy;
	    private Date updatedDate;
	    private String lgIpMac;
	    private String lgIpMacUpd;
	    
	    @JsonIgnore
	    private StoreMasterDTO storeMasterDTO;

	    
		public StoreGroupMappingDto() {
			
		}

		public Long getGroupMapId() {
			return groupMapId;
		}

		public void setGroupMapId(Long groupMapId) {
			this.groupMapId = groupMapId;
		}

		public Long getItemGroupId() {
			return itemGroupId;
		}

		public void setItemGroupId(Long itemGroupId) {
			this.itemGroupId = itemGroupId;
		}

		public char getStatus() {
			return status;
		}

		public void setStatus(char status) {
			this.status = status;
		}

		public Long getOrgId() {
			return orgId;
		}

		public void setOrgId(Long orgId) {
			this.orgId = orgId;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Long getLangId() {
			return langId;
		}

		public void setLangId(Long langId) {
			this.langId = langId;
		}

		public Date getLmoDate() {
			return lmoDate;
		}

		public void setLmoDate(Date lmoDate) {
			this.lmoDate = lmoDate;
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

		

		public StoreMasterDTO getStoreMasterDTO() {
			return storeMasterDTO;
		}

		public void setStoreMasterDTO(StoreMasterDTO storeMasterDTO) {
			this.storeMasterDTO = storeMasterDTO;
		}

		@Override
		public String toString() {
			return "StoreGroupMappingDto [groupMapId=" + groupMapId + ", itemGroupId=" + itemGroupId + ", status="
					+ status + ", orgId=" + orgId + ", userId=" + userId + ", langId=" + langId + ", lmoDate=" + lmoDate
					+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
					+ ", lgIpMacUpd=" + lgIpMacUpd + ", storeMasterEntity=" + storeMasterDTO + "]";
		}
	    
   
	    
}
