
package com.abm.mainet.validitymaster.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author cherupelli.srikanth
 * @since 29 Nov 2021
 */

@Entity
@Table(name="tb_emp_loc_map_det")
public class EmployeeWardZoneMappingDetailEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "emp_loc_id")
	private Long empLocDetId;

	@Column(name = "emp_id")
	private Long empLocId;
		
	@Column(name = "ward1")
	private Long ward1;
	
	@Column(name = "ward2")
	private Long ward2;
	
	@Column(name = "ward3")
	private Long ward3;
	
	@Column(name = "ward4")
	private Long ward4;
	
	@Column(name = "ward5")
	private Long ward5;
	
	@Column(name = "orgid")
	private Long orgId;
	
	@Column(name = "created_date")
	private Date  createdDate;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "updated_by")		
	private Long updatedBy;
	
	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getEmpLocDetId() {
		return empLocDetId;
	}

	public void setEmpLocDetId(Long empLocDetId) {
		this.empLocDetId = empLocDetId;
	}
	
	public Long getEmpLocId() {
		return empLocId;
	}

	public void setEmpLocId(Long empLocId) {
		this.empLocId = empLocId;
	}

	public Long getWard1() {
		return ward1;
	}

	public void setWard1(Long ward1) {
		this.ward1 = ward1;
	}

	public Long getWard2() {
		return ward2;
	}

	public void setWard2(Long ward2) {
		this.ward2 = ward2;
	}

	public Long getWard3() {
		return ward3;
	}

	public void setWard3(Long ward3) {
		this.ward3 = ward3;
	}

	public Long getWard4() {
		return ward4;
	}

	public void setWard4(Long ward4) {
		this.ward4 = ward4;
	}

	public Long getWard5() {
		return ward5;
	}

	public void setWard5(Long ward5) {
		this.ward5 = ward5;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public Date getUpdatedDate() {
		return updatedDate;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
	
	public String[] getPkValues() {
        return new String[] { "COM", "tb_emp_loc_map_det ", "emp_loc_id" };
    }
	
}
