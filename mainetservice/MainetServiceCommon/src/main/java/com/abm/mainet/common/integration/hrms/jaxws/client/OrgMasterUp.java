
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
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="O_NLS_ORGNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ORGANISATION_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ORG_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DEFAULT_STATUS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="O_NLS_ORGNAME_MAR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetMetadataName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ORG_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "status",
    "modifiedIn",
    "createdBy",
    "sheetId",
    "createdAt",
    "metadata",
    "onlsorgname",
    "organisationid",
    "sheetName",
    "modifiedBy",
    "caption",
    "assignedTo",
    "modifiedAt",
    "tenant",
    "orgstatus",
    "defaultstatus",
    "processInstance",
    "onlsorgnamemar",
    "sheetMetadataName",
    "orgaddress"
})
@XmlRootElement(name = "Org_MasterUp")
public class OrgMasterUp {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "O_NLS_ORGNAME", required = true)
    protected String onlsorgname;
    @XmlElement(name = "ORGANISATION_ID", required = true)
    protected String organisationid;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "ORG_STATUS", required = true)
    protected String orgstatus;
    @XmlElement(name = "DEFAULT_STATUS", required = true)
    protected String defaultstatus;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "O_NLS_ORGNAME_MAR", required = true)
    protected String onlsorgnamemar;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;
    @XmlElement(name = "ORG_ADDRESS", required = true)
    protected String orgaddress;

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
     * Gets the value of the onlsorgname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONLSORGNAME() {
        return onlsorgname;
    }

    /**
     * Sets the value of the onlsorgname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONLSORGNAME(String value) {
        this.onlsorgname = value;
    }

    /**
     * Gets the value of the organisationid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGANISATIONID() {
        return organisationid;
    }

    /**
     * Sets the value of the organisationid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGANISATIONID(String value) {
        this.organisationid = value;
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
     * Gets the value of the orgstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGSTATUS() {
        return orgstatus;
    }

    /**
     * Sets the value of the orgstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGSTATUS(String value) {
        this.orgstatus = value;
    }

    /**
     * Gets the value of the defaultstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDEFAULTSTATUS() {
        return defaultstatus;
    }

    /**
     * Sets the value of the defaultstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDEFAULTSTATUS(String value) {
        this.defaultstatus = value;
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
     * Gets the value of the onlsorgnamemar property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getONLSORGNAMEMAR() {
        return onlsorgnamemar;
    }

    /**
     * Sets the value of the onlsorgnamemar property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setONLSORGNAMEMAR(String value) {
        this.onlsorgnamemar = value;
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

    /**
     * Gets the value of the orgaddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getORGADDRESS() {
        return orgaddress;
    }

    /**
     * Sets the value of the orgaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setORGADDRESS(String value) {
        this.orgaddress = value;
    }

}
