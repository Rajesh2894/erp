
package com.abm.mainet.common.integration.acccount.hrms.vendormaster.soap.jaxws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ORGID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="PAC_HEAD_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="VM_VENDORADD" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VM_VENDORID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Vendor_Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="VM_VENDORNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vendor_Mail_Id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vendor_Mobile_No" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Vendor_Type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VM_VENDORCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CPD_VENDORSUBTYPE" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
        "orgid",
        "status",
        "modifiedIn",
        "vmclass",
        "createdBy",
        "pacheadid",
        "vmvendoradd",
        "sheetId",
        "createdAt",
        "metadata",
        "sheetName",
        "vmvendorid",
        "vendorStatus",
        "modifiedBy",
        "caption",
        "assignedTo",
        "modifiedAt",
        "vmvendorname",
        "vendorMailId",
        "vendorMobileNo",
        "vendorType",
        "tenant",
        "vmvendorcode",
        "cpdvendorsubtype",
        "processInstance",
        "sheetMetadataName"
})
@XmlRootElement(name = "VendorMaster")
public class VendorMaster {

    @XmlElement(name = "ORGID", required = true)
    protected String orgid;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "VM_CLASS", required = true)
    protected String vmclass;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "PAC_HEAD_ID")
    protected double pacheadid;
    @XmlElement(name = "VM_VENDORADD", required = true)
    protected String vmvendoradd;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "VM_VENDORID")
    protected double vmvendorid;
    @XmlElement(name = "Vendor_Status", required = true)
    protected String vendorStatus;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "VM_VENDORNAME", required = true)
    protected String vmvendorname;
    @XmlElement(name = "Vendor_Mail_Id", required = true)
    protected String vendorMailId;
    @XmlElement(name = "Vendor_Mobile_No", required = true)
    protected String vendorMobileNo;
    @XmlElement(name = "Vendor_Type", required = true)
    protected String vendorType;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "VM_VENDORCODE", required = true)
    protected String vmvendorcode;
    @XmlElement(name = "CPD_VENDORSUBTYPE")
    protected double cpdvendorsubtype;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    public String getVmclass() {
        return vmclass;
    }

    public void setVmclass(String vmclass) {
        this.vmclass = vmclass;
    }

    /**
     * Gets the value of the orgid property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getORGID() {
        return orgid;
    }

    /**
     * Sets the value of the orgid property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setORGID(String value) {
        this.orgid = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the modifiedIn property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getModifiedIn() {
        return modifiedIn;
    }

    /**
     * Sets the value of the modifiedIn property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setModifiedIn(String value) {
        this.modifiedIn = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the pacheadid property.
     * 
     */
    public double getPACHEADID() {
        return pacheadid;
    }

    /**
     * Sets the value of the pacheadid property.
     * 
     */
    public void setPACHEADID(double value) {
        this.pacheadid = value;
    }

    /**
     * Gets the value of the vmvendoradd property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVMVENDORADD() {
        return vmvendoradd;
    }

    /**
     * Sets the value of the vmvendoradd property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVMVENDORADD(String value) {
        this.vmvendoradd = value;
    }

    /**
     * Gets the value of the sheetId property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Sets the value of the sheetId property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setSheetId(String value) {
        this.sheetId = value;
    }

    /**
     * Gets the value of the createdAt property.
     * 
     * @return
     * possible object is
     * {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the createdAt property.
     * 
     * @param value
     * allowed object is
     * {@link XMLGregorianCalendar }
     * 
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setMetadata(String value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the sheetName property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Sets the value of the sheetName property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setSheetName(String value) {
        this.sheetName = value;
    }

    /**
     * Gets the value of the vmvendorid property.
     * 
     */
    public double getVMVENDORID() {
        return vmvendorid;
    }

    /**
     * Sets the value of the vmvendorid property.
     * 
     */
    public void setVMVENDORID(double value) {
        this.vmvendorid = value;
    }

    /**
     * Gets the value of the vendorStatus property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVendorStatus() {
        return vendorStatus;
    }

    /**
     * Sets the value of the vendorStatus property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVendorStatus(String value) {
        this.vendorStatus = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the caption property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setCaption(String value) {
        this.caption = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the modifiedAt property.
     * 
     * @return
     * possible object is
     * {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Sets the value of the modifiedAt property.
     * 
     * @param value
     * allowed object is
     * {@link XMLGregorianCalendar }
     * 
     */
    public void setModifiedAt(XMLGregorianCalendar value) {
        this.modifiedAt = value;
    }

    /**
     * Gets the value of the vmvendorname property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVMVENDORNAME() {
        return vmvendorname;
    }

    /**
     * Sets the value of the vmvendorname property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVMVENDORNAME(String value) {
        this.vmvendorname = value;
    }

    /**
     * Gets the value of the vendorMailId property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVendorMailId() {
        return vendorMailId;
    }

    /**
     * Sets the value of the vendorMailId property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVendorMailId(String value) {
        this.vendorMailId = value;
    }

    /**
     * Gets the value of the vendorMobileNo property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVendorMobileNo() {
        return vendorMobileNo;
    }

    /**
     * Sets the value of the vendorMobileNo property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVendorMobileNo(String value) {
        this.vendorMobileNo = value;
    }

    /**
     * Gets the value of the vendorType property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVendorType() {
        return vendorType;
    }

    /**
     * Sets the value of the vendorType property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVendorType(String value) {
        this.vendorType = value;
    }

    /**
     * Gets the value of the tenant property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Sets the value of the tenant property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setTenant(String value) {
        this.tenant = value;
    }

    /**
     * Gets the value of the vmvendorcode property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getVMVENDORCODE() {
        return vmvendorcode;
    }

    /**
     * Sets the value of the vmvendorcode property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setVMVENDORCODE(String value) {
        this.vmvendorcode = value;
    }

    /**
     * Gets the value of the cpdvendorsubtype property.
     * 
     */
    public double getCPDVENDORSUBTYPE() {
        return cpdvendorsubtype;
    }

    /**
     * Sets the value of the cpdvendorsubtype property.
     * 
     */
    public void setCPDVENDORSUBTYPE(double value) {
        this.cpdvendorsubtype = value;
    }

    /**
     * Gets the value of the processInstance property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getProcessInstance() {
        return processInstance;
    }

    /**
     * Sets the value of the processInstance property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setProcessInstance(String value) {
        this.processInstance = value;
    }

    /**
     * Gets the value of the sheetMetadataName property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getSheetMetadataName() {
        return sheetMetadataName;
    }

    /**
     * Sets the value of the sheetMetadataName property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setSheetMetadataName(String value) {
        this.sheetMetadataName = value;
    }

}
