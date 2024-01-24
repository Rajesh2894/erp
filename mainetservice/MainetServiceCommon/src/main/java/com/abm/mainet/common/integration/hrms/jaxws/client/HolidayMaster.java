
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
 *         &lt;element name="Holiday_Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="cal_year" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Dup_Holiday" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="From_Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="HO_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CalendarYear_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Holiday_Name_lower" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Starting_Year" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Cal_Holiday_year" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dup_Holiday_Value" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Ho_active" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Dup_HolidayDate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="To_Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CalendarYear_Value" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Holiday_Dat_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Day_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Holiday_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="End_year" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Holiday_Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "holidayDate",
    "calYear",
    "modifiedIn",
    "ulbName",
    "flag",
    "dupHoliday",
    "createdAt",
    "fromDate",
    "hoid",
    "metadata",
    "sheetName",
    "modifiedBy",
    "calendarYearRef",
    "holidayNameLower",
    "modifiedAt",
    "tenant",
    "startingYear",
    "calHolidayYear",
    "ulbCode",
    "dupHolidayValue",
    "hoActive",
    "status",
    "dupHolidayDate",
    "toDate",
    "calendarYearValue",
    "createdBy",
    "holidayDatName",
    "sheetId",
    "dayName",
    "holidayName",
    "caption",
    "assignedTo",
    "endYear",
    "processInstance",
    "holidayType",
    "sheetMetadataName"
})
@XmlRootElement(name = "HolidayMaster")
public class HolidayMaster {

    @XmlElement(name = "Holiday_Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar holidayDate;
    @XmlElement(name = "cal_year")
    protected double calYear;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "ULB_Name", required = true)
    protected String ulbName;
    protected double flag;
    @XmlElement(name = "Dup_Holiday", required = true)
    protected String dupHoliday;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "From_Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fromDate;
    @XmlElement(name = "HO_ID")
    protected double hoid;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "CalendarYear_Ref", required = true)
    protected String calendarYearRef;
    @XmlElement(name = "Holiday_Name_lower", required = true)
    protected String holidayNameLower;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "Starting_Year")
    protected double startingYear;
    @XmlElement(name = "Cal_Holiday_year", required = true)
    protected String calHolidayYear;
    @XmlElement(name = "ULB_Code", required = true)
    protected String ulbCode;
    @XmlElement(name = "Dup_Holiday_Value", required = true)
    protected String dupHolidayValue;
    @XmlElement(name = "Ho_active", required = true)
    protected String hoActive;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Dup_HolidayDate", required = true)
    protected String dupHolidayDate;
    @XmlElement(name = "To_Date", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toDate;
    @XmlElement(name = "CalendarYear_Value")
    protected double calendarYearValue;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "Holiday_Dat_Name", required = true)
    protected String holidayDatName;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "Day_Name", required = true)
    protected String dayName;
    @XmlElement(name = "Holiday_Name", required = true)
    protected String holidayName;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "End_year")
    protected double endYear;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "Holiday_Type", required = true)
    protected String holidayType;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the holidayDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getHolidayDate() {
        return holidayDate;
    }

    /**
     * Sets the value of the holidayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setHolidayDate(XMLGregorianCalendar value) {
        this.holidayDate = value;
    }

    /**
     * Gets the value of the calYear property.
     * 
     */
    public double getCalYear() {
        return calYear;
    }

    /**
     * Sets the value of the calYear property.
     * 
     */
    public void setCalYear(double value) {
        this.calYear = value;
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
     * Gets the value of the flag property.
     * 
     */
    public double getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     */
    public void setFlag(double value) {
        this.flag = value;
    }

    /**
     * Gets the value of the dupHoliday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDupHoliday() {
        return dupHoliday;
    }

    /**
     * Sets the value of the dupHoliday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDupHoliday(String value) {
        this.dupHoliday = value;
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
     * Gets the value of the hoid property.
     * 
     */
    public double getHOID() {
        return hoid;
    }

    /**
     * Sets the value of the hoid property.
     * 
     */
    public void setHOID(double value) {
        this.hoid = value;
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
     * Gets the value of the calendarYearRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalendarYearRef() {
        return calendarYearRef;
    }

    /**
     * Sets the value of the calendarYearRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalendarYearRef(String value) {
        this.calendarYearRef = value;
    }

    /**
     * Gets the value of the holidayNameLower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolidayNameLower() {
        return holidayNameLower;
    }

    /**
     * Sets the value of the holidayNameLower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolidayNameLower(String value) {
        this.holidayNameLower = value;
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
     * Gets the value of the startingYear property.
     * 
     */
    public double getStartingYear() {
        return startingYear;
    }

    /**
     * Sets the value of the startingYear property.
     * 
     */
    public void setStartingYear(double value) {
        this.startingYear = value;
    }

    /**
     * Gets the value of the calHolidayYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCalHolidayYear() {
        return calHolidayYear;
    }

    /**
     * Sets the value of the calHolidayYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCalHolidayYear(String value) {
        this.calHolidayYear = value;
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
     * Gets the value of the dupHolidayValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDupHolidayValue() {
        return dupHolidayValue;
    }

    /**
     * Sets the value of the dupHolidayValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDupHolidayValue(String value) {
        this.dupHolidayValue = value;
    }

    /**
     * Gets the value of the hoActive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoActive() {
        return hoActive;
    }

    /**
     * Sets the value of the hoActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoActive(String value) {
        this.hoActive = value;
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
     * Gets the value of the dupHolidayDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDupHolidayDate() {
        return dupHolidayDate;
    }

    /**
     * Sets the value of the dupHolidayDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDupHolidayDate(String value) {
        this.dupHolidayDate = value;
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
     * Gets the value of the calendarYearValue property.
     * 
     */
    public double getCalendarYearValue() {
        return calendarYearValue;
    }

    /**
     * Sets the value of the calendarYearValue property.
     * 
     */
    public void setCalendarYearValue(double value) {
        this.calendarYearValue = value;
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
     * Gets the value of the holidayDatName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolidayDatName() {
        return holidayDatName;
    }

    /**
     * Sets the value of the holidayDatName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolidayDatName(String value) {
        this.holidayDatName = value;
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
     * Gets the value of the dayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayName() {
        return dayName;
    }

    /**
     * Sets the value of the dayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayName(String value) {
        this.dayName = value;
    }

    /**
     * Gets the value of the holidayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolidayName() {
        return holidayName;
    }

    /**
     * Sets the value of the holidayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolidayName(String value) {
        this.holidayName = value;
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
     * Gets the value of the endYear property.
     * 
     */
    public double getEndYear() {
        return endYear;
    }

    /**
     * Sets the value of the endYear property.
     * 
     */
    public void setEndYear(double value) {
        this.endYear = value;
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
     * Gets the value of the holidayType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHolidayType() {
        return holidayType;
    }

    /**
     * Sets the value of the holidayType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHolidayType(String value) {
        this.holidayType = value;
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
