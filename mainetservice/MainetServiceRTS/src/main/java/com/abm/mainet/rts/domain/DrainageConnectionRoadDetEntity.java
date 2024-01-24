package com.abm.mainet.rts.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_RTS_DRN_CON_RDET")
public class DrainageConnectionRoadDetEntity implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CNN_RDID", nullable = false)
	private Long cnnRdId;
	
	@ManyToOne
	@JoinColumn(name = "CNN_ID", referencedColumnName = "CNN_ID")
	private DrainageConnectionEntity drainageConnectionId;
	
	@Column(name = "ORGID")
	private Long orgId;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	
	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "status")
	private String status;

	@Column(name = "ROAD_TYPE")
	private Long roadType;
	
	@Column(name = "LEN_ROAD")
	private Long lenRoad;
	
	
	
	public Long getCnnRdId() {
		return cnnRdId;
	}



	public void setCnnRdId(Long cnnRdId) {
		this.cnnRdId = cnnRdId;
	}



	public DrainageConnectionEntity getDrainageConnectionId() {
		return drainageConnectionId;
	}



	public void setDrainageConnectionId(DrainageConnectionEntity drainageConnectionId) {
		this.drainageConnectionId = drainageConnectionId;
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Long getRoadType() {
		return roadType;
	}



	public void setRoadType(Long roadType) {
		this.roadType = roadType;
	}



	public Long getLenRoad() {
		return lenRoad;
	}



	public void setLenRoad(Long lenRoad) {
		this.lenRoad = lenRoad;
	}



	public static String[] getPkValues() {
		return new String[] { "RTS", "TB_RTS_DRN_CON_RDET", "CNN_RDID" };
	}
	

}
