/**
 * 
 */
package com.abm.mainet.workManagement.roadcutting.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_WMS_ROAD_CUTTING_DET")
public class RoadRouteDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4098786241519235887L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "RCD_ID", nullable = true)
	private Long rcdId;

	@ManyToOne
	@JoinColumn(name = "RC_ID", nullable = false)
	private RoadCuttingEntity roadCuttingEntity;
	@Column(name = "RCD_TECTYPE", nullable = true)
	private BigInteger typeOfTechnology;
	@Column(name = "RCD_STARTPOINT", nullable = true)
	private String roadRouteDesc;
	@Column(name = "RCD_ROADTYPE", nullable = true)
	private BigInteger roadType;
	@Column(name = "RCD_NO", nullable = true)
	private BigInteger numbers;
	@Column(name = "RCD_LENGTH", nullable = true)
	private BigDecimal length;
	@Column(name = "RCD_BREADTH", nullable = true)
	private BigDecimal breadth;
	@Column(name = "RCD_HEIGHT", nullable = true)
	private BigDecimal height;
	@Column(name = "RCD_DIAMETER", nullable = true)
	private BigDecimal daimeter;
	@Column(name = "LOC_ID", nullable = true)
	private BigInteger location;

	@Column(name = "RCD_ENDPOINT", nullable = true)
	private String rcdEndpoint;
	@Column(name = "RCD_COMPLETIONDT", nullable = true)
	private Date rcdCompletionDate;
	@Column(name = "RCD_STARTLOGITUDE", nullable = true)
	private String rcdStartlogitude;
	@Column(name = "RCD_STARTLATITUDE", nullable = true)
	private String rcdStartlatitude;
	@Column(name = "RCD_ENDLOGITUDE", nullable = true)
	private String rcdEndlogitude;
	@Column(name = "RCD_ENDLATITUDE", nullable = true)
	private String rcdEndlatitude;

	@Column(name = "RCD_QTY", nullable = true)
	private BigDecimal rcdQuantiy;

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
	
	@Column(name = "COD_ZONE1")
	private Long codZoneward1;
	@Column(name = "COD_ZONE2")
	private Long codZoneward2;
	@Column(name = "COD_ZONE3")
	private Long codZoneward3;
	@Column(name = "COD_ZONE4")
	private Long codZoneward4;
	@Column(name = "COD_ZONE5")
	private Long codZoneward5;
	
	@Column(name = "APM_APPLICATION_STATUS")
    private String apmAppStatus;
	
	@Column(name = "REF_ID")
	private String refId;
	
	@Column(name = "RCD_AREANAME", nullable = true)
	private String roadAreaName;
	
	/**
	 * @return the rcdId
	 */
	public Long getRcdId() {
		return rcdId;
	}

	/**
	 * @param rcdId the rcdId to set
	 */
	public void setRcdId(Long rcdId) {
		this.rcdId = rcdId;
	}

	/**
	 * @return the typeOfTechnology
	 */
	public BigInteger getTypeOfTechnology() {
		return typeOfTechnology;
	}

	/**
	 * @param typeOfTechnology the typeOfTechnology to set
	 */
	public void setTypeOfTechnology(BigInteger typeOfTechnology) {
		this.typeOfTechnology = typeOfTechnology;
	}

	/**
	 * @return the roadRouteDesc
	 */
	public String getRoadRouteDesc() {
		return roadRouteDesc;
	}

	/**
	 * @param roadRouteDesc the roadRouteDesc to set
	 */
	public void setRoadRouteDesc(String roadRouteDesc) {
		this.roadRouteDesc = roadRouteDesc;
	}

	/**
	 * @return the roadType
	 */
	public BigInteger getRoadType() {
		return roadType;
	}

	/**
	 * @param roadType the roadType to set
	 */
	public void setRoadType(BigInteger roadType) {
		this.roadType = roadType;
	}

	/**
	 * @return the numbers
	 */
	public BigInteger getNumbers() {
		return numbers;
	}

	/**
	 * @param numbers the numbers to set
	 */
	public void setNumbers(BigInteger numbers) {
		this.numbers = numbers;
	}

	/**
	 * @return the length
	 */
	public BigDecimal getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(BigDecimal length) {
		this.length = length;
	}

	/**
	 * @return the breadth
	 */
	public BigDecimal getBreadth() {
		return breadth;
	}

	/**
	 * @param breadth the breadth to set
	 */
	public void setBreadth(BigDecimal breadth) {
		this.breadth = breadth;
	}

	/**
	 * @return the height
	 */
	public BigDecimal getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	/**
	 * @return the daimeter
	 */
	public BigDecimal getDaimeter() {
		return daimeter;
	}

	/**
	 * @param daimeter the daimeter to set
	 */
	public void setDaimeter(BigDecimal daimeter) {
		this.daimeter = daimeter;
	}

	/**
	 * @return the location
	 */
	public BigInteger getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(BigInteger location) {
		this.location = location;
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

	/**
	 * @return the roadCuttingEntity
	 */
	public RoadCuttingEntity getRoadCuttingEntity() {
		return roadCuttingEntity;
	}

	/**
	 * @param roadCuttingEntity the roadCuttingEntity to set
	 */
	public void setRoadCuttingEntity(RoadCuttingEntity roadCuttingEntity) {
		this.roadCuttingEntity = roadCuttingEntity;
	}

	/**
	 * @return the rcdEndpoint
	 */
	public String getRcdEndpoint() {
		return rcdEndpoint;
	}

	/**
	 * @param rcdEndpoint the rcdEndpoint to set
	 */
	public void setRcdEndpoint(String rcdEndpoint) {
		this.rcdEndpoint = rcdEndpoint;
	}

	/**
	 * @return the rcdCompletionDate
	 */
	public Date getRcdCompletionDate() {
		return rcdCompletionDate;
	}

	/**
	 * @param rcdCompletionDate the rcdCompletionDate to set
	 */
	public void setRcdCompletionDate(Date rcdCompletionDate) {
		this.rcdCompletionDate = rcdCompletionDate;
	}

	/**
	 * @return the rcdStartlogitude
	 */
	public String getRcdStartlogitude() {
		return rcdStartlogitude;
	}

	/**
	 * @param rcdStartlogitude the rcdStartlogitude to set
	 */
	public void setRcdStartlogitude(String rcdStartlogitude) {
		this.rcdStartlogitude = rcdStartlogitude;
	}

	/**
	 * @return the rcdStartlatitude
	 */
	public String getRcdStartlatitude() {
		return rcdStartlatitude;
	}

	/**
	 * @param rcdStartlatitude the rcdStartlatitude to set
	 */
	public void setRcdStartlatitude(String rcdStartlatitude) {
		this.rcdStartlatitude = rcdStartlatitude;
	}

	/**
	 * @return the rcdEndlogitude
	 */
	public String getRcdEndlogitude() {
		return rcdEndlogitude;
	}

	/**
	 * @param rcdEndlogitude the rcdEndlogitude to set
	 */
	public void setRcdEndlogitude(String rcdEndlogitude) {
		this.rcdEndlogitude = rcdEndlogitude;
	}

	/**
	 * @return the rcdQuantiy
	 */
	public BigDecimal getRcdQuantiy() {
		return rcdQuantiy;
	}

	/**
	 * @param rcdQuantiy the rcdQuantiy to set
	 */
	public void setRcdQuantiy(BigDecimal rcdQuantiy) {
		this.rcdQuantiy = rcdQuantiy;
	}

	/**
	 * @return the rcdEndlatitude
	 */
	public String getRcdEndlatitude() {
		return rcdEndlatitude;
	}

	/**
	 * @param rcdEndlatitude the rcdEndlatitude to set
	 */
	public void setRcdEndlatitude(String rcdEndlatitude) {
		this.rcdEndlatitude = rcdEndlatitude;
	}

	public static String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_ROAD_CUTTING_DET", "RCD_ID" };
	}

	public Long getCodZoneward1() {
		return codZoneward1;
	}

	public void setCodZoneward1(Long codZoneward1) {
		this.codZoneward1 = codZoneward1;
	}

	public Long getCodZoneward2() {
		return codZoneward2;
	}

	public void setCodZoneward2(Long codZoneward2) {
		this.codZoneward2 = codZoneward2;
	}

	public Long getCodZoneward3() {
		return codZoneward3;
	}

	public void setCodZoneward3(Long codZoneward3) {
		this.codZoneward3 = codZoneward3;
	}

	public Long getCodZoneward4() {
		return codZoneward4;
	}

	public void setCodZoneward4(Long codZoneward4) {
		this.codZoneward4 = codZoneward4;
	}

	public Long getCodZoneward5() {
		return codZoneward5;
	}

	public void setCodZoneward5(Long codZoneward5) {
		this.codZoneward5 = codZoneward5;
	}

	public String getApmAppStatus() {
		return apmAppStatus;
	}

	public void setApmAppStatus(String apmAppStatus) {
		this.apmAppStatus = apmAppStatus;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getRoadAreaName() {
		return roadAreaName;
	}

	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}

	
	

}
