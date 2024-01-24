package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author cherupelli.srikanth
 *@since 28 sep 2020
 */
public class PgBankParameterDetailDto implements Serializable{

	private static final long serialVersionUID = 201484297202840747L;

		private Long pgPramDetId;

	    private long pgId;

	    private String parName;

	    private String parValue;

	    private String parStatus;

	    private Organisation orgId;

	    private Long createdBy;

	    private Date lmodDate;

	    private int langId;

	    private Employee updatedBy;

	    private Date updatedDate;

	    private String lgIpMac;

	    private String lgIpMacUpd;

	    private Long commN1;

	    private String commV1;

	    private Date commD1;

	    private String commLo1;

		public Long getPgPramDetId() {
			return pgPramDetId;
		}

		public void setPgPramDetId(Long pgPramDetId) {
			this.pgPramDetId = pgPramDetId;
		}

		public long getPgId() {
			return pgId;
		}

		public void setPgId(long pgId) {
			this.pgId = pgId;
		}

		public String getParName() {
			return parName;
		}

		public void setParName(String parName) {
			this.parName = parName;
		}

		public String getParValue() {
			return parValue;
		}

		public void setParValue(String parValue) {
			this.parValue = parValue;
		}

		public String getParStatus() {
			return parStatus;
		}

		public void setParStatus(String parStatus) {
			this.parStatus = parStatus;
		}

		public Organisation getOrgId() {
			return orgId;
		}

		public void setOrgId(Organisation orgId) {
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

		public int getLangId() {
			return langId;
		}

		public void setLangId(int langId) {
			this.langId = langId;
		}

		public Employee getUpdatedBy() {
			return updatedBy;
		}

		public void setUpdatedBy(Employee updatedBy) {
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

		public Long getCommN1() {
			return commN1;
		}

		public void setCommN1(Long commN1) {
			this.commN1 = commN1;
		}

		public String getCommV1() {
			return commV1;
		}

		public void setCommV1(String commV1) {
			this.commV1 = commV1;
		}

		public Date getCommD1() {
			return commD1;
		}

		public void setCommD1(Date commD1) {
			this.commD1 = commD1;
		}

		public String getCommLo1() {
			return commLo1;
		}

		public void setCommLo1(String commLo1) {
			this.commLo1 = commLo1;
		}
	    
	    
}
