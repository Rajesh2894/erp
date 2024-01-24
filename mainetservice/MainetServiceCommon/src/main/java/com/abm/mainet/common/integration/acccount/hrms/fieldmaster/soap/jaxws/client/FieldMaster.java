
package com.abm.mainet.common.integration.acccount.hrms.fieldmaster.soap.jaxws.client;

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
 *         &lt;element name="FIELD_PARENT_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ORGID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CODCOFDET_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_STATUS" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FIELD_DESC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_CODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_COMPCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "fieldparentid",
    "orgid",
    "status",
    "codcofdetid",
    "modifiedIn",
    "createdBy",
    "fieldid",
    "sheetId",
    "createdAt",
    "metadata",
    "sheetName",
    "modifiedBy",
    "caption",
    "assignedTo",
    "fieldstatus",
    "modifiedAt",
    "fielddesc",
    "tenant",
    "fieldcode",
    "fieldcompcode",
    "ulbCode",
    "processInstance",
    "sheetMetadataName"
})
@XmlRootElement(name = "Field_Master")
public class FieldMaster {

    @XmlElement(name = "FIELD_PARENT_ID")
    protected double fieldparentid;
    @XmlElement(name = "ORGID")
    protected double orgid;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "CODCOFDET_ID")
    protected double codcofdetid;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "FIELD_ID")
    protected double fieldid;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "FIELD_STATUS")
    protected double fieldstatus;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "FIELD_DESC", required = true)
    protected String fielddesc;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "FIELD_CODE", required = true)
    protected String fieldcode;
    @XmlElement(name = "FIELD_COMPCODE", required = true)
    protected String fieldcompcode;
    @XmlElement(name = "ULB_Code", required = true)
    protected String ulbCode;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the fieldparentid property.
     * 
     */
    public double getFIELDPARENTID() {
        return fieldparentid;
    }

    /**
     * Sets the value of the fieldparentid property.
     * 
     */
    public void setFIELDPARENTID(double value) {
        this.fieldparentid = value;
    }

    /**
     * Gets the value of the orgid property.
     * 
     */
    public double getORGID() {
        return orgid;
    }

    /**
     * Sets the value of the orgid property.
     * 
     */
    public void setORGID(double value) {
        this.orgid = value;
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
     * Gets the value of the codcofdetid property.
     * 
     */
    public double getCODCOFDETID() {
        return codcofdetid;
    }

    /**
     * Sets the value of the codcofdetid property.
     * 
     */
    public void setCODCOFDETID(double value) {
        this.codcofdetid = value;
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
     * Gets the value of the fieldid property.
     * 
     */
    public double getFIELDID() {
        return fieldid;
    }

    /**
     * Sets the value of the fieldid property.
     * 
     */
    public void setFIELDID(double value) {
        this.fieldid = value;
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
     * Gets the value of the fieldstatus property.
     * 
     */
    public double getFIELDSTATUS() {
        return fieldstatus;
    }

    /**
     * Sets the value of the fieldstatus property.
     * 
     */
    public void setFIELDSTATUS(double value) {
        this.fieldstatus = value;
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
     * Gets the value of the fielddesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDDESC() {
        return fielddesc;
    }

    /**
     * Sets the value of the fielddesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDDESC(String value) {
        this.fielddesc = value;
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
     * Gets the value of the fieldcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDCODE() {
        return fieldcode;
    }

    /**
     * Sets the value of the fieldcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDCODE(String value) {
        this.fieldcode = value;
    }

    /**
     * Gets the value of the fieldcompcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIELDCOMPCODE() {
        return fieldcompcode;
    }

    /**
     * Sets the value of the fieldcompcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIELDCOMPCODE(String value) {
        this.fieldcompcode = value;
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
