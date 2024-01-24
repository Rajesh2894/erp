package com.abm.mainet.materialmgmt.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MM_BIN_LOCATION_MAS")
public class BinLocMasEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BINLOCID", nullable = false)
	private Long binLocId;

	@Column(name = "STORENAME", nullable = false)
	private Long storeId;

	@Column(name = "STORELOCATION", nullable = false)
	private String storeLocation;

	@Column(name = "STOREADDRESS", nullable = false)
	private String storeAdd;

	@Column(name = "BINLOCATION", nullable = false)
	private String binLocation;

	@Column(name = "LOCATIONNAME", nullable = false)
	private String locationName;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "BIN_LOC_ID")
	private List<BinLocDefMapping> mappingList;

	@Column(name = "LANGID", nullable = false)
	private Long langId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@JsonIgnore
	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	public Long getBinLocId() {
		return binLocId;
	}

	public void setBinLocId(Long binLocId) {
		this.binLocId = binLocId;
	}

	public Long getStoreId() {
		return storeId;
	}

	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}

	public String getStoreLocation() {
		return storeLocation;
	}

	public void setStoreLocation(String storeLocation) {
		this.storeLocation = storeLocation;
	}

	public String getStoreAdd() {
		return storeAdd;
	}

	public void setStoreAdd(String storeAdd) {
		this.storeAdd = storeAdd;
	}

	public String getBinLocation() {
		return binLocation;
	}

	public void setBinLocation(String binLocation) {
		this.binLocation = binLocation;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
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

	public List<BinLocDefMapping> getMappingList() {
		return mappingList;
	}

	public void setMappingList(List<BinLocDefMapping> mappingList) {
		this.mappingList = mappingList;
	}

	public static String[] getPkValues() {
		return new String[] { "MM", "MM_BIN_LOCATION_MAS", "BINLOCID" };
	}

}
