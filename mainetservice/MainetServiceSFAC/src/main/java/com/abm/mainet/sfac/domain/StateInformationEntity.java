/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_STATE_INFO_MAST")
public class StateInformationEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3117650606971931370L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ST_ID", nullable = false)
	private Long stId;

	@Column(name = "STATE")
	private Long state;

	@Column(name = "STATE_CODE")
	private Long stateCode;

	@Column(name = "ST_SHORT_CODE")
	private String stShortCode;

	@Column(name = "DISTRICT")
	private Long district;

	@Column(name = "DIST_CODE")
	private Long distCode;

	@Column(name = "AREA_TYPE")
	private Long areaType;

	@Column(name = "ZONE")
	private Long zone;

	@Column(name = "ODOP")
	private Long odop;

	@Column(name = "ASPIRATIONAL_DIST")
	private Long aspirationalDist;

	@Column(name = "TRIBAL_DIST")
	private Long tribalDist;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	/**
	 * @return the stId
	 */
	public Long getStId() {
		return stId;
	}

	/**
	 * @param stId the stId to set
	 */
	public void setStId(Long stId) {
		this.stId = stId;
	}

	/**
	 * @return the state
	 */
	public Long getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Long state) {
		this.state = state;
	}

	/**
	 * @return the stateCode
	 */
	public Long getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the stShortCode
	 */
	public String getStShortCode() {
		return stShortCode;
	}

	/**
	 * @param stShortCode the stShortCode to set
	 */
	public void setStShortCode(String stShortCode) {
		this.stShortCode = stShortCode;
	}

	/**
	 * @return the district
	 */
	public Long getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(Long district) {
		this.district = district;
	}

	/**
	 * @return the distCode
	 */
	public Long getDistCode() {
		return distCode;
	}

	/**
	 * @param distCode the distCode to set
	 */
	public void setDistCode(Long distCode) {
		this.distCode = distCode;
	}

	/**
	 * @return the areaType
	 */
	public Long getAreaType() {
		return areaType;
	}

	/**
	 * @param areaType the areaType to set
	 */
	public void setAreaType(Long areaType) {
		this.areaType = areaType;
	}

	/**
	 * @return the zone
	 */
	public Long getZone() {
		return zone;
	}

	/**
	 * @param zone the zone to set
	 */
	public void setZone(Long zone) {
		this.zone = zone;
	}

	/**
	 * @return the odop
	 */
	public Long getOdop() {
		return odop;
	}

	/**
	 * @param odop the odop to set
	 */
	public void setOdop(Long odop) {
		this.odop = odop;
	}

	/**
	 * @return the aspirationalDist
	 */
	public Long getAspirationalDist() {
		return aspirationalDist;
	}

	/**
	 * @param aspirationalDist the aspirationalDist to set
	 */
	public void setAspirationalDist(Long aspirationalDist) {
		this.aspirationalDist = aspirationalDist;
	}

	/**
	 * @return the tribalDist
	 */
	public Long getTribalDist() {
		return tribalDist;
	}

	/**
	 * @param tribalDist the tribalDist to set
	 */
	public void setTribalDist(Long tribalDist) {
		this.tribalDist = tribalDist;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_STATE_INFO_MAST", "ST_ID" };
	}
}
