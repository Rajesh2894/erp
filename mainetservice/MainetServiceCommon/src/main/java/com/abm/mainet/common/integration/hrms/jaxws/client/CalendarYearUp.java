
package com.abm.mainet.common.integration.hrms.jaxws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Fa_tomonth" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserID_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Cal_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Calendar_Year" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="From_Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fa_monstatus" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="fa_yearstatus" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Search_Year" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Calendar_Year_End" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Duplicate_Value" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="fa_fromyear" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Cal_Ctx" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FY" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Chk_Year_len" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Financial_Year" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Map_Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="child_cnt" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="To_Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Fa_toyear" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Fa_YearId" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Map_Id" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Active_Flag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Duplicate_Check" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Chk_Calendar_Year" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Active_Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Configuration_Master_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Fa_frommonth" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="User_Id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Holiday_days" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetMetadataName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "faTomonth",
    "modifiedIn",
    "ulbName",
    "userIDRef",
    "calRef",
    "createdAt",
    "calendarYear",
    "fromDate",
    "metadata",
    "sheetName",
    "faMonstatus",
    "modifiedBy",
    "faYearstatus",
    "searchYear",
    "calendarYearEnd",
    "modifiedAt",
    "duplicateValue",
    "faFromyear",
    "calCtx",
    "fy",
    "tenant",
    "chkYearLen",
    "ulbCode",
    "financialYear",
    "mapStatus",
    "childCnt",
    "status",
    "toDate",
    "createdBy",
    "faToyear",
    "sheetId",
    "faYearId",
    "caption",
    "mapId",
    "activeFlag",
    "assignedTo",
    "duplicateCheck",
    "chkCalendarYear",
    "activeStatus",
    "configurationMasterRef",
    "processInstance",
    "faFrommonth",
    "userId",
    "holidayDays",
    "sheetMetadataName"
})
@XmlRootElement(name = "CalendarYearUp")
public class CalendarYearUp {

    @XmlElement(name = "Fa_tomonth")
    protected double faTomonth;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "ULB_Name", required = true)
    protected String ulbName;
    @XmlElement(name = "UserID_Ref", required = true)
    protected String userIDRef;
    @XmlElement(name = "Cal_Ref", required = true)
    protected String calRef;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Calendar_Year")
    protected double calendarYear;
    @XmlElement(name = "From_Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fromDate;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "fa_monstatus")
    protected double faMonstatus;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "fa_yearstatus")
    protected double faYearstatus;
    @XmlElement(name = "Search_Year", required = true)
    protected String searchYear;
    @XmlElement(name = "Calendar_Year_End")
    protected double calendarYearEnd;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Duplicate_Value")
    protected double duplicateValue;
    @XmlElement(name = "fa_fromyear")
    protected double faFromyear;
    @XmlElement(name = "Cal_Ctx", required = true)
    protected String calCtx;
    @XmlElement(name = "FY", required = true)
    protected String fy;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "Chk_Year_len", required = true)
    protected String chkYearLen;
    @XmlElement(name = "ULB_Code", required = true)
    protected String ulbCode;
    @XmlElement(name = "Financial_Year", required = true)
    protected String financialYear;
    @XmlElement(name = "Map_Status", required = true)
    protected String mapStatus;
    @XmlElement(name = "child_cnt")
    protected double childCnt;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "To_Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toDate;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "Fa_toyear")
    protected double faToyear;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "Fa_YearId")
    protected double faYearId;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "Map_Id")
    protected double mapId;
    @XmlElement(name = "Active_Flag", required = true)
    protected String activeFlag;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "Duplicate_Check", required = true)
    protected String duplicateCheck;
    @XmlElement(name = "Chk_Calendar_Year", required = true)
    protected String chkCalendarYear;
    @XmlElement(name = "Active_Status", required = true)
    protected String activeStatus;
    @XmlElement(name = "Configuration_Master_Ref", required = true)
    protected String configurationMasterRef;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "Fa_frommonth")
    protected double faFrommonth;
    @XmlElement(name = "User_Id", required = true)
    protected String userId;
    @XmlElement(name = "Holiday_days")
    protected double holidayDays;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the faTomonth property.
     * 
     */
    public double getFaTomonth() {
        return faTomonth;
    }

    /**
     * Sets the value of the faTomonth property.
     * 
     */
    public void setFaTomonth(double value) {
        this.faTomonth = value;
    }

    /**
     * Gets the value of the modifiedIn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedIn() {
        return modifiedIn;
    }

    /**
     * Sets the value of the modifiedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedIn(String value) {
        this.modifiedIn = value;
    }

    /**
     * Gets the value of the ulbName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULBName() {
        return ulbName;
    }

    /**
     * Sets the value of the ulbName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULBName(String value) {
        this.ulbName = value;
    }

    /**
     * Gets the value of the userIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserIDRef() {
        return userIDRef;
    }

    /**
     * Sets the value of the userIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserIDRef(String value) {
        this.userIDRef = value;
    }

    /**
     * Gets the value of the calRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalRef() {
        return calRef;
    }

    /**
     * Sets the value of the calRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalRef(String value) {
        this.calRef = value;
    }

    /**
     * Gets the value of the createdAt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the createdAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Gets the value of the calendarYear property.
     * 
     */
    public double getCalendarYear() {
        return calendarYear;
    }

    /**
     * Sets the value of the calendarYear property.
     * 
     */
    public void setCalendarYear(double value) {
        this.calendarYear = value;
    }

    /**
     * Gets the value of the fromDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFromDate() {
        return fromDate;
    }

    /**
     * Sets the value of the fromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFromDate(XMLGregorianCalendar value) {
        this.fromDate = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetadata(String value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the sheetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Sets the value of the sheetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSheetName(String value) {
        this.sheetName = value;
    }

    /**
     * Gets the value of the faMonstatus property.
     * 
     */
    public double getFaMonstatus() {
        return faMonstatus;
    }

    /**
     * Sets the value of the faMonstatus property.
     * 
     */
    public void setFaMonstatus(double value) {
        this.faMonstatus = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the faYearstatus property.
     * 
     */
    public double getFaYearstatus() {
        return faYearstatus;
    }

    /**
     * Sets the value of the faYearstatus property.
     * 
     */
    public void setFaYearstatus(double value) {
        this.faYearstatus = value;
    }

    /**
     * Gets the value of the searchYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchYear() {
        return searchYear;
    }

    /**
     * Sets the value of the searchYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchYear(String value) {
        this.searchYear = value;
    }

    /**
     * Gets the value of the calendarYearEnd property.
     * 
     */
    public double getCalendarYearEnd() {
        return calendarYearEnd;
    }

    /**
     * Sets the value of the calendarYearEnd property.
     * 
     */
    public void setCalendarYearEnd(double value) {
        this.calendarYearEnd = value;
    }

    /**
     * Gets the value of the modifiedAt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Sets the value of the modifiedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedAt(XMLGregorianCalendar value) {
        this.modifiedAt = value;
    }

    /**
     * Gets the value of the duplicateValue property.
     * 
     */
    public double getDuplicateValue() {
        return duplicateValue;
    }

    /**
     * Sets the value of the duplicateValue property.
     * 
     */
    public void setDuplicateValue(double value) {
        this.duplicateValue = value;
    }

    /**
     * Gets the value of the faFromyear property.
     * 
     */
    public double getFaFromyear() {
        return faFromyear;
    }

    /**
     * Sets the value of the faFromyear property.
     * 
     */
    public void setFaFromyear(double value) {
        this.faFromyear = value;
    }

    /**
     * Gets the value of the calCtx property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalCtx() {
        return calCtx;
    }

    /**
     * Sets the value of the calCtx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalCtx(String value) {
        this.calCtx = value;
    }

    /**
     * Gets the value of the fy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFY() {
        return fy;
    }

    /**
     * Sets the value of the fy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFY(String value) {
        this.fy = value;
    }

    /**
     * Gets the value of the tenant property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Sets the value of the tenant property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTenant(String value) {
        this.tenant = value;
    }

    /**
     * Gets the value of the chkYearLen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChkYearLen() {
        return chkYearLen;
    }

    /**
     * Sets the value of the chkYearLen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChkYearLen(String value) {
        this.chkYearLen = value;
    }

    /**
     * Gets the value of the ulbCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getULBCode() {
        return ulbCode;
    }

    /**
     * Sets the value of the ulbCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setULBCode(String value) {
        this.ulbCode = value;
    }

    /**
     * Gets the value of the financialYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFinancialYear() {
        return financialYear;
    }

    /**
     * Sets the value of the financialYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFinancialYear(String value) {
        this.financialYear = value;
    }

    /**
     * Gets the value of the mapStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapStatus() {
        return mapStatus;
    }

    /**
     * Sets the value of the mapStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapStatus(String value) {
        this.mapStatus = value;
    }

    /**
     * Gets the value of the childCnt property.
     * 
     */
    public double getChildCnt() {
        return childCnt;
    }

    /**
     * Sets the value of the childCnt property.
     * 
     */
    public void setChildCnt(double value) {
        this.childCnt = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the toDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getToDate() {
        return toDate;
    }

    /**
     * Sets the value of the toDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setToDate(XMLGregorianCalendar value) {
        this.toDate = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the faToyear property.
     * 
     */
    public double getFaToyear() {
        return faToyear;
    }

    /**
     * Sets the value of the faToyear property.
     * 
     */
    public void setFaToyear(double value) {
        this.faToyear = value;
    }

    /**
     * Gets the value of the sheetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Sets the value of the sheetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSheetId(String value) {
        this.sheetId = value;
    }

    /**
     * Gets the value of the faYearId property.
     * 
     */
    public double getFaYearId() {
        return faYearId;
    }

    /**
     * Sets the value of the faYearId property.
     * 
     */
    public void setFaYearId(double value) {
        this.faYearId = value;
    }

    /**
     * Gets the value of the caption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaption(String value) {
        this.caption = value;
    }

    /**
     * Gets the value of the mapId property.
     * 
     */
    public double getMapId() {
        return mapId;
    }

    /**
     * Sets the value of the mapId property.
     * 
     */
    public void setMapId(double value) {
        this.mapId = value;
    }

    /**
     * Gets the value of the activeFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveFlag() {
        return activeFlag;
    }

    /**
     * Sets the value of the activeFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveFlag(String value) {
        this.activeFlag = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the duplicateCheck property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplicateCheck() {
        return duplicateCheck;
    }

    /**
     * Sets the value of the duplicateCheck property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplicateCheck(String value) {
        this.duplicateCheck = value;
    }

    /**
     * Gets the value of the chkCalendarYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChkCalendarYear() {
        return chkCalendarYear;
    }

    /**
     * Sets the value of the chkCalendarYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChkCalendarYear(String value) {
        this.chkCalendarYear = value;
    }

    /**
     * Gets the value of the activeStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActiveStatus() {
        return activeStatus;
    }

    /**
     * Sets the value of the activeStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActiveStatus(String value) {
        this.activeStatus = value;
    }

    /**
     * Gets the value of the configurationMasterRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationMasterRef() {
        return configurationMasterRef;
    }

    /**
     * Sets the value of the configurationMasterRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationMasterRef(String value) {
        this.configurationMasterRef = value;
    }

    /**
     * Gets the value of the processInstance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProcessInstance() {
        return processInstance;
    }

    /**
     * Sets the value of the processInstance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProcessInstance(String value) {
        this.processInstance = value;
    }

    /**
     * Gets the value of the faFrommonth property.
     * 
     */
    public double getFaFrommonth() {
        return faFrommonth;
    }

    /**
     * Sets the value of the faFrommonth property.
     * 
     */
    public void setFaFrommonth(double value) {
        this.faFrommonth = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the holidayDays property.
     * 
     */
    public double getHolidayDays() {
        return holidayDays;
    }

    /**
     * Sets the value of the holidayDays property.
     * 
     */
    public void setHolidayDays(double value) {
        this.holidayDays = value;
    }

    /**
     * Gets the value of the sheetMetadataName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSheetMetadataName() {
        return sheetMetadataName;
    }

    /**
     * Sets the value of the sheetMetadataName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSheetMetadataName(String value) {
        this.sheetMetadataName = value;
    }

}
