package com.abm.mainet.rts.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author rahul.chaubey
 *
 */
@Entity
@Table(name = "TB_RTS_DRN_CON")
public class DrainageConnectionEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1265680477087298743L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CNN_ID",  nullable = false)
	private Long connectionId;

	@Column(name = "DRAINAGE_ADDRESS", length = 1000, nullable = false)
	private String drainageAddress;
	
	@Column(name = "PROPERTY_INDEX_NO", length = 100, nullable = false)
	private String propertyIndexNo;
	
	@Column(name = "SIZE_OF_CONNECTION", length = 12, nullable = false)
	private Long sizeOfConnection;
	
	@Column(name = "TYPE_OF_CONNECTION", length = 12, nullable = false)
	private Long typeOfConnection;
	
	@Column(name = "WARD", length = 12, nullable = false)
	private Long ward;
	
	@Column(name = "APPLICANT_TYPE", length = 12, nullable = true)
	private Long applicantType;
	
	@Column(name = "APM_APPLICATION_ID", length = 16, nullable = true)
	private Long apmApplicationId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "status", length = 5, nullable = true)
	private String status;
	
	@Column(name="NO_OF_FLAT")
	private Long noOfFlat;
	
	@Column(name = "APPL_TYPE", length = 12, nullable = true)
	private Long applType;
	
	@Column(name = "RESIDENTIALHOUSE", length = 12, nullable = true)
	private Long residentialHouse;
	
	@Column(name = "COMMERCIALHOUSE", length = 12, nullable = true)
	private Long commercialHouse;
	
	@Column(name = "ROAD_TYPE", length = 12, nullable = true)
	private Long roadType;
	
	@Column(name = "LEN_ROAD", length = 12, nullable = true)
	private Long lenRoad;
	
	@OneToMany(mappedBy = "drainageConnectionId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<DrainageConnectionRoadDetEntity> roadDetEntities = new ArrayList<>();
	
	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}

	public String getDrainageAddress() {
		return drainageAddress;
	}

	public void setDrainageAddress(String drainageAddress) {
		this.drainageAddress = drainageAddress;
	}

	public String getPropertyIndexNo() {
		return propertyIndexNo;
	}

	public void setPropertyIndexNo(String propertyIndexNo) {
		this.propertyIndexNo = propertyIndexNo;
	}

	public Long getSizeOfConnection() {
		return sizeOfConnection;
	}

	public void setSizeOfConnection(Long sizeOfConnection) {
		this.sizeOfConnection = sizeOfConnection;
	}

	public Long getTypeOfConnection() {
		return typeOfConnection;
	}

	public void setTypeOfConnection(Long typeOfConnection) {
		this.typeOfConnection = typeOfConnection;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}

	public Long getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(Long applicantType) {
		this.applicantType = applicantType;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getNoOfFlat() {
		return noOfFlat;
	}

	public void setNoOfFlat(Long noOfFlat) {
		this.noOfFlat = noOfFlat;
	}

	public Long getApplType() {
		return applType;
	}

	public void setApplType(Long applType) {
		this.applType = applType;
	}

	public Long getResidentialHouse() {
		return residentialHouse;
	}

	public void setResidentialHouse(Long residentialHouse) {
		this.residentialHouse = residentialHouse;
	}

	public Long getCommercialHouse() {
		return commercialHouse;
	}

	public void setCommercialHouse(Long commercialHouse) {
		this.commercialHouse = commercialHouse;
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
		return new String[] { "RTS", "TB_RTS_DRN_CON", "CNN_ID" };
	}

	public List<DrainageConnectionRoadDetEntity> getRoadDetEntities() {
		return roadDetEntities;
	}

	public void setRoadDetEntities(List<DrainageConnectionRoadDetEntity> roadDetEntities) {
		this.roadDetEntities = roadDetEntities;
	}
	
	
	
}
