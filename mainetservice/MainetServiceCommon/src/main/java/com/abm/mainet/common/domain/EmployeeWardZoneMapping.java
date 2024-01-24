package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_EMP_WARD_ZONE_DET")
public class EmployeeWardZoneMapping implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 439461457000342098L;
	
	 	@Id
	    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	    @GeneratedValue(generator = "MyCustomGenerator")
	    @Column(name = "EWZ_ID", nullable = false)
	    private Long ewzId;
	 	
	 	@Column(name = "EMP_ID")
	 	private Long empId;
	 	
	 	@Column(name = "ZONES")
	 	private String zones;
	 	
	 	@Column(name = "WARDS")
	 	private String wards;
	 	
	 	@Column(name = "STATUS")
	 	private String status;
	 	
	 	@ManyToOne
	    @JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
	    private Organisation orgId;

	    @Column(name = "CREATED_BY")
	    private Long createdBy;

	    @Column(name = "CREATED_DATE")
	    private Date createDate;

	    @Column(name = "UPDATED_BY")
	    private Long updatedBy;

	    @Column(name = "UPDATED_DATE")
	    private Date updatedDate;

	    @Column(name = "LG_IP_MAC")
	    private String lgIpMac;

	    @Column(name = "LG_IP_MAC_UPD")
	    private String lgIpMacUpd;
	    
	    @Column(name = "LOC1")
	    private String loc1;
	    
	    @Column(name = "LOC2")
	    private String loc2;
	    
	    @Column(name = "LOC3")
	    private String loc3;
	    
	    @Column(name = "LOC4")
	    private String loc4;
	    
	    @Column(name = "LOC5")
	    private String loc5;

		public String getLoc1() {
			return loc1;
		}

		public void setLoc1(String loc1) {
			this.loc1 = loc1;
		}

		public String getLoc2() {
			return loc2;
		}

		public void setLoc2(String loc2) {
			this.loc2 = loc2;
		}

		public String getLoc3() {
			return loc3;
		}

		public void setLoc3(String loc3) {
			this.loc3 = loc3;
		}

		public String getLoc4() {
			return loc4;
		}

		public void setLoc4(String loc4) {
			this.loc4 = loc4;
		}

		public String getLoc5() {
			return loc5;
		}

		public void setLoc5(String loc5) {
			this.loc5 = loc5;
		}

		public Long getEwzId() {
			return ewzId;
		}

		public void setEwzId(Long ewzId) {
			this.ewzId = ewzId;
		}

		public Long getEmpId() {
			return empId;
		}

		public void setEmpId(Long empId) {
			this.empId = empId;
		}

		public String getZones() {
			return zones;
		}

		public void setZones(String zones) {
			this.zones = zones;
		}

		public String getWards() {
			return wards;
		}

		public void setWards(String wards) {
			this.wards = wards;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
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

		public Date getCreateDate() {
			return createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
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
	        return new String[] { "COM", "TB_EMP_WARD_ZONE_DET", "EWZ_ID" };
	    }

}
