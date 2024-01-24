
package com.abm.mainet.common.integration.acccount.hrms.bankaccount.soap.jaxws.client;

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
 *         &lt;element name="BA_Accountid" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BA_Account_No" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AC_CPD_ID_STATUS" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CPD_ACCOUNTTYPE" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BA_Accountname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BANKID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OrgId" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "status",
    "baAccountid",
    "modifiedIn",
    "baAccountNo",
    "createdBy",
    "accpdidstatus",
    "sheetId",
    "cpdaccounttype",
    "createdAt",
    "metadata",
    "sheetName",
    "baAccountname",
    "modifiedBy",
    "caption",
    "bankid",
    "assignedTo",
    "modifiedAt",
    "orgId",
    "tenant",
    "processInstance",
    "sheetMetadataName"
})
@XmlRootElement(name = "Bank_Account")
public class BankAccount {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "BA_Accountid")
    protected double baAccountid;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "BA_Account_No", required = true)
    protected String baAccountNo;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "AC_CPD_ID_STATUS")
    protected double accpdidstatus;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "CPD_ACCOUNTTYPE")
    protected double cpdaccounttype;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "BA_Accountname", required = true)
    protected String baAccountname;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "BANKID")
    protected double bankid;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "OrgId")
    protected double orgId;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

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
     * Gets the value of the baAccountid property.
     * 
     */
    public double getBAAccountid() {
        return baAccountid;
    }

    /**
     * Sets the value of the baAccountid property.
     * 
     */
    public void setBAAccountid(double value) {
        this.baAccountid = value;
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
     * Gets the value of the baAccountNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBAAccountNo() {
        return baAccountNo;
    }

    /**
     * Sets the value of the baAccountNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBAAccountNo(String value) {
        this.baAccountNo = value;
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
     * Gets the value of the accpdidstatus property.
     * 
     */
    public double getACCPDIDSTATUS() {
        return accpdidstatus;
    }

    /**
     * Sets the value of the accpdidstatus property.
     * 
     */
    public void setACCPDIDSTATUS(double value) {
        this.accpdidstatus = value;
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
     * Gets the value of the cpdaccounttype property.
     * 
     */
    public double getCPDACCOUNTTYPE() {
        return cpdaccounttype;
    }

    /**
     * Sets the value of the cpdaccounttype property.
     * 
     */
    public void setCPDACCOUNTTYPE(double value) {
        this.cpdaccounttype = value;
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
     * Gets the value of the baAccountname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBAAccountname() {
        return baAccountname;
    }

    /**
     * Sets the value of the baAccountname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBAAccountname(String value) {
        this.baAccountname = value;
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
     * Gets the value of the bankid property.
     * 
     */
    public double getBANKID() {
        return bankid;
    }

    /**
     * Sets the value of the bankid property.
     * 
     */
    public void setBANKID(double value) {
        this.bankid = value;
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
     * Gets the value of the orgId property.
     * 
     */
    public double getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     */
    public void setOrgId(double value) {
        this.orgId = value;
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
