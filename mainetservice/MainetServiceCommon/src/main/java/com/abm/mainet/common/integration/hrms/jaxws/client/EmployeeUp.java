
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
 *         &lt;element name="LG_IP_MAC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="empmobno" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMPNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="emplname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DSGID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ORGID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMPLOGINNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="LOCID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="empemail" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="EMPID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
    "lgipmac",
    "modifiedIn",
    "dpdeptid",
    "createdAt",
    "metadata",
    "empmobno",
    "sheetName",
    "modifiedBy",
    "empname",
    "emplname",
    "modifiedAt",
    "tenant",
    "dsgid",
    "orgid",
    "status",
    "createdBy",
    "emploginname",
    "sheetId",
    "locid",
    "empemail",
    "caption",
    "assignedTo",
    "empid",
    "processInstance",
    "sheetMetadataName"
})
@XmlRootElement(name = "EmployeeUp")
public class EmployeeUp {

    @XmlElement(name = "LG_IP_MAC", required = true)
    protected String lgipmac;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "DP_DEPTID")
    protected double dpdeptid;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    protected double empmobno;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "EMPNAME", required = true)
    protected String empname;
    @XmlElement(required = true)
    protected String emplname;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "DSGID")
    protected double dsgid;
    @XmlElement(name = "ORGID")
    protected double orgid;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "EMPLOGINNAME", required = true)
    protected String emploginname;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "LOCID")
    protected double locid;
    @XmlElement(required = true)
    protected String empemail;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "EMPID")
    protected double empid;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the lgipmac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLGIPMAC() {
        return lgipmac;
    }

    /**
     * Sets the value of the lgipmac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLGIPMAC(String value) {
        this.lgipmac = value;
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
     * Gets the value of the dpdeptid property.
     * 
     */
    public double getDPDEPTID() {
        return dpdeptid;
    }

    /**
     * Sets the value of the dpdeptid property.
     * 
     */
    public void setDPDEPTID(double value) {
        this.dpdeptid = value;
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
     * Gets the value of the empmobno property.
     * 
     */
    public double getEmpmobno() {
        return empmobno;
    }

    /**
     * Sets the value of the empmobno property.
     * 
     */
    public void setEmpmobno(double value) {
        this.empmobno = value;
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
     * Gets the value of the empname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPNAME() {
        return empname;
    }

    /**
     * Sets the value of the empname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPNAME(String value) {
        this.empname = value;
    }

    /**
     * Gets the value of the emplname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmplname() {
        return emplname;
    }

    /**
     * Sets the value of the emplname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmplname(String value) {
        this.emplname = value;
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
     * Gets the value of the dsgid property.
     * 
     */
    public double getDSGID() {
        return dsgid;
    }

    /**
     * Sets the value of the dsgid property.
     * 
     */
    public void setDSGID(double value) {
        this.dsgid = value;
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
     * Gets the value of the emploginname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPLOGINNAME() {
        return emploginname;
    }

    /**
     * Sets the value of the emploginname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPLOGINNAME(String value) {
        this.emploginname = value;
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
     * Gets the value of the locid property.
     * 
     */
    public double getLOCID() {
        return locid;
    }

    /**
     * Sets the value of the locid property.
     * 
     */
    public void setLOCID(double value) {
        this.locid = value;
    }

    /**
     * Gets the value of the empemail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpemail() {
        return empemail;
    }

    /**
     * Sets the value of the empemail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpemail(String value) {
        this.empemail = value;
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
     * Gets the value of the empid property.
     * 
     */
    public double getEMPID() {
        return empid;
    }

    /**
     * Sets the value of the empid property.
     * 
     */
    public void setEMPID(double value) {
        this.empid = value;
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
