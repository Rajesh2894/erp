package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MILESTONE_GEOTAG")
public class MilestoneGeoTag implements Serializable{
	
	 private static final long serialVersionUID = 1L;

	    @Id
	    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	    @GeneratedValue(generator = "MyCustomGenerator")
	    @Column(name = "MILEG_ID", nullable = false)
	    private Long milegId;
        
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "MILED_ID", nullable = false)
	    private MilestoneDetail milestoneDetEntity;
	    
	    @Column(name = "ATD_ID", precision = 12, scale = 0, nullable = false)
		private Long atdId;
	    
	    @Column(name = "MILEG_STATUS")
	    private String status;
	    
	    @Column(name = "LATITUDE", nullable = false)
		private BigDecimal latitude;
	    
	    @Column(name = "LONGITUDE", nullable = false)
		private BigDecimal longitude;
	    
	    @Column(name = "ORGID", nullable = false)
		private Long orgId;

		@Column(name = "CREATED_BY", nullable = false)
		private Long createdBy;

		@Column(name = "CREATED_DATE", nullable = false)
		private Date createdDate;

		@Column(name = "UPDATED_BY", nullable = true)
		private Long updatedBy;

		@Column(name = "UPDATED_DATE", nullable = true)
		private Date updatedDate;

		@Column(name = "LG_IP_MAC", nullable = false)
		private String lgIpMac;

		@Column(name = "LG_IP_MAC_UPD", nullable = true)
		private String lgIpMacUpd;

		public Long getMilegId() {
			return milegId;
		}

		public void setMilegId(Long milegId) {
			this.milegId = milegId;
		}

		public MilestoneDetail getMilestoneDetEntity() {
			return milestoneDetEntity;
		}

		public void setMilestoneDetEntity(MilestoneDetail milestoneDetEntity) {
			this.milestoneDetEntity = milestoneDetEntity;
		}

		public Long getAtdId() {
			return atdId;
		}

		public void setAtdId(Long atdId) {
			this.atdId = atdId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public BigDecimal getLatitude() {
			return latitude;
		}

		public void setLatitude(BigDecimal latitude) {
			this.latitude = latitude;
		}

		public BigDecimal getLongitude() {
			return longitude;
		}

		public void setLongitude(BigDecimal longitude) {
			this.longitude = longitude;
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

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
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
		public String[] getPkValues() {
			return new String[] { "WMS", "TB_WMS_MILESTONE_GEOTAG", "MILEG_ID" };
		}
}
