package com.abm.mainet.workManagement.roadcutting.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author satish.rathore
 *
 */
public class RoadRouteDetailsDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1250098074883459451L;
	/* road/route details */
	private Long rcdId;
	private BigInteger typeOfTechnology;
	private String roadRouteDesc;
	private BigInteger roadType;
	private BigDecimal length;
	private BigDecimal breadth;
	private BigDecimal height;
	private BigInteger numbers = BigInteger.ONE;
	private BigDecimal diameter;
	private BigDecimal quantity = BigDecimal.ONE;
	private String roadAreaName;

	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	private String rcdEndpoint;
	private Date rcdCompletionDate;
	private String rcdStartlogitude;
	private String rcdStartlatitude;
	private String rcdEndlogitude;
	private String rcdEndlatitude;
	private Long codZoneward1;
    private Long codZoneward2;
    private Long codZoneward3;
    private Long codZoneward4;
    private Long codZoneward5;
    private String apmAppStatus;
    private String refId;
    private String workFlowAppStatus;

	public String getRoadRouteDesc() {
		return roadRouteDesc;
	}
	/**
	 * @param roadRouteDesc the roadRouteDesc to set
	 */
	public void setRoadRouteDesc(String roadRouteDesc) {
		this.roadRouteDesc = roadRouteDesc;
	}

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
	 * @return the diameter
	 */
	public BigDecimal getDiameter() {
		return diameter;
	}
	/**
	 * @param diameter the diameter to set
	 */
	public void setDiameter(BigDecimal diameter) {
		this.diameter = diameter;
	}
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
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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

	public String getRcdEndpoint() {
		return rcdEndpoint;
	}

	public void setRcdEndpoint(String rcdEndpoint) {
		this.rcdEndpoint = rcdEndpoint;
	}

	public Date getRcdCompletionDate() {
		return rcdCompletionDate;
	}

	public void setRcdCompletionDate(Date rcdCompletionDate) {
		this.rcdCompletionDate = rcdCompletionDate;
	}

	public String getRcdStartlogitude() {
		return rcdStartlogitude;
	}

	public void setRcdStartlogitude(String rcdStartlogitude) {
		this.rcdStartlogitude = rcdStartlogitude;
	}

	public String getRcdStartlatitude() {
		return rcdStartlatitude;
	}

	public void setRcdStartlatitude(String rcdStartlatitude) {
		this.rcdStartlatitude = rcdStartlatitude;
	}

	public String getRcdEndlogitude() {
		return rcdEndlogitude;
	}

	public void setRcdEndlogitude(String rcdEndlogitude) {
		this.rcdEndlogitude = rcdEndlogitude;
	}

	public String getRcdEndlatitude() {
		return rcdEndlatitude;
	}

	public void setRcdEndlatitude(String rcdEndlatitude) {
		this.rcdEndlatitude = rcdEndlatitude;
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
	public String getWorkFlowAppStatus() {
		return workFlowAppStatus;
	}
	public void setWorkFlowAppStatus(String workFlowAppStatus) {
		this.workFlowAppStatus = workFlowAppStatus;
	}
	public String getRoadAreaName() {
		return roadAreaName;
	}
	public void setRoadAreaName(String roadAreaName) {
		this.roadAreaName = roadAreaName;
	}
    
	
}
