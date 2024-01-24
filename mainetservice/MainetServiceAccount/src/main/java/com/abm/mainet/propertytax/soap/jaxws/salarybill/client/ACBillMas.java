
package com.abm.mainet.propertytax.soap.jaxws.salarybill.client;

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
 *         &lt;element name="LG_IP_MAC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Bill_Reverse_Flag" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_NARRATION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BillEntryDet" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_INVOICEVALUE" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="INT_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="INT_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_BILLTYPE_CPD_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Pay_Bill_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ORGID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="FIELD_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_DEL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_BILLNO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VM_VENDORID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="BM_INVOICENUMBER" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_ENTRYDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CREATED_DEt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="INT_REF_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="VM_VENDORNAME" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="BM_INVOICEDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
        "billReverseFlag",
        "createddate",
        "createdAt",
        "metadata",
        "bmid",
        "sheetName",
        "bmnarration",
        "billEntryDet",
        "modifiedBy",
        "bminvoicevalue",
        "modifiedAt",
        "intflag",
        "intdate",
        "tenant",
        "bmbilltypecpdid",
        "payBillRef",
        "createdby",
        "orgid",
        "status",
        "createdBy",
        "fieldid",
        "sheetId",
        "bmdelflag",
        "bmbillno",
        "vmvendorid",
        "bminvoicenumber",
        "caption",
        "bmentrydate",
        "createddEt",
        "intrefid",
        "assignedTo",
        "vmvendorname",
        "processInstance",
        "bminvoicedate",
        "sheetMetadataName"
})
@XmlRootElement(name = "AC_Bill_Mas")
public class ACBillMas {

    @XmlElement(name = "LG_IP_MAC", required = true)
    protected String lgipmac;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "DP_DEPTID")
    protected double dpdeptid;
    @XmlElement(name = "Bill_Reverse_Flag", required = true)
    protected String billReverseFlag;
    @XmlElement(name = "CREATED_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createddate;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "BM_ID")
    protected double bmid;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "BM_NARRATION", required = true)
    protected String bmnarration;
    @XmlElement(name = "BillEntryDet", required = true)
    protected String billEntryDet;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "BM_INVOICEVALUE")
    protected double bminvoicevalue;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "INT_FLAG", required = true)
    protected String intflag;
    @XmlElement(name = "INT_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar intdate;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "BM_BILLTYPE_CPD_ID")
    protected double bmbilltypecpdid;
    @XmlElement(name = "Pay_Bill_Ref", required = true)
    protected String payBillRef;
    @XmlElement(name = "CREATED_BY")
    protected double createdby;
    @XmlElement(name = "ORGID")
    protected double orgid;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "FIELD_ID")
    protected double fieldid;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "BM_DEL_FLAG", required = true)
    protected String bmdelflag;
    @XmlElement(name = "BM_BILLNO", required = true)
    protected String bmbillno;
    @XmlElement(name = "VM_VENDORID")
    protected double vmvendorid;
    @XmlElement(name = "BM_INVOICENUMBER", required = true)
    protected String bminvoicenumber;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "BM_ENTRYDATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bmentrydate;
    @XmlElement(name = "CREATED_DEt", required = true)
    protected String createddEt;
    @XmlElement(name = "INT_REF_ID")
    protected double intrefid;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "VM_VENDORNAME", required = true)
    protected String vmvendorname;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "BM_INVOICEDATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar bminvoicedate;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the lgipmac property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLGIPMAC() {
        return lgipmac;
    }

    /**
     * Sets the value of the lgipmac property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLGIPMAC(String value) {
        this.lgipmac = value;
    }

    /**
     * Gets the value of the modifiedIn property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getModifiedIn() {
        return modifiedIn;
    }

    /**
     * Sets the value of the modifiedIn property.
     * 
     * @param value allowed object is {@link String }
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
     * Gets the value of the billReverseFlag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBillReverseFlag() {
        return billReverseFlag;
    }

    /**
     * Sets the value of the billReverseFlag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBillReverseFlag(String value) {
        this.billReverseFlag = value;
    }

    /**
     * Gets the value of the createddate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getCREATEDDATE() {
        return createddate;
    }

    /**
     * Sets the value of the createddate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setCREATEDDATE(XMLGregorianCalendar value) {
        this.createddate = value;
    }

    /**
     * Gets the value of the createdAt property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the value of the createdAt property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    /**
     * Gets the value of the metadata property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the value of the metadata property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setMetadata(String value) {
        this.metadata = value;
    }

    /**
     * Gets the value of the bmid property.
     * 
     */
    public double getBMID() {
        return bmid;
    }

    /**
     * Sets the value of the bmid property.
     * 
     */
    public void setBMID(double value) {
        this.bmid = value;
    }

    /**
     * Gets the value of the sheetName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Sets the value of the sheetName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSheetName(String value) {
        this.sheetName = value;
    }

    /**
     * Gets the value of the bmnarration property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBMNARRATION() {
        return bmnarration;
    }

    /**
     * Sets the value of the bmnarration property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBMNARRATION(String value) {
        this.bmnarration = value;
    }

    /**
     * Gets the value of the billEntryDet property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBillEntryDet() {
        return billEntryDet;
    }

    /**
     * Sets the value of the billEntryDet property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBillEntryDet(String value) {
        this.billEntryDet = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the bminvoicevalue property.
     * 
     */
    public double getBMINVOICEVALUE() {
        return bminvoicevalue;
    }

    /**
     * Sets the value of the bminvoicevalue property.
     * 
     */
    public void setBMINVOICEVALUE(double value) {
        this.bminvoicevalue = value;
    }

    /**
     * Gets the value of the modifiedAt property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Sets the value of the modifiedAt property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setModifiedAt(XMLGregorianCalendar value) {
        this.modifiedAt = value;
    }

    /**
     * Gets the value of the intflag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getINTFLAG() {
        return intflag;
    }

    /**
     * Sets the value of the intflag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setINTFLAG(String value) {
        this.intflag = value;
    }

    /**
     * Gets the value of the intdate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getINTDATE() {
        return intdate;
    }

    /**
     * Sets the value of the intdate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setINTDATE(XMLGregorianCalendar value) {
        this.intdate = value;
    }

    /**
     * Gets the value of the tenant property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTenant() {
        return tenant;
    }

    /**
     * Sets the value of the tenant property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTenant(String value) {
        this.tenant = value;
    }

    /**
     * Gets the value of the bmbilltypecpdid property.
     * 
     */
    public double getBMBILLTYPECPDID() {
        return bmbilltypecpdid;
    }

    /**
     * Sets the value of the bmbilltypecpdid property.
     * 
     */
    public void setBMBILLTYPECPDID(double value) {
        this.bmbilltypecpdid = value;
    }

    /**
     * Gets the value of the payBillRef property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPayBillRef() {
        return payBillRef;
    }

    /**
     * Sets the value of the payBillRef property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPayBillRef(String value) {
        this.payBillRef = value;
    }

    /**
     * Gets the value of the createdby property.
     * 
     */
    public double getCREATEDBY() {
        return createdby;
    }

    /**
     * Sets the value of the createdby property.
     * 
     */
    public void setCREATEDBY(double value) {
        this.createdby = value;
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
     * @return possible object is {@link String }
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value allowed object is {@link String }
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
     * @return possible object is {@link String }
     * 
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Sets the value of the sheetId property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSheetId(String value) {
        this.sheetId = value;
    }

    /**
     * Gets the value of the bmdelflag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBMDELFLAG() {
        return bmdelflag;
    }

    /**
     * Sets the value of the bmdelflag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBMDELFLAG(String value) {
        this.bmdelflag = value;
    }

    /**
     * Gets the value of the bmbillno property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBMBILLNO() {
        return bmbillno;
    }

    /**
     * Sets the value of the bmbillno property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBMBILLNO(String value) {
        this.bmbillno = value;
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
     * Gets the value of the bminvoicenumber property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBMINVOICENUMBER() {
        return bminvoicenumber;
    }

    /**
     * Sets the value of the bminvoicenumber property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setBMINVOICENUMBER(String value) {
        this.bminvoicenumber = value;
    }

    /**
     * Gets the value of the caption property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCaption(String value) {
        this.caption = value;
    }

    /**
     * Gets the value of the bmentrydate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getBMENTRYDATE() {
        return bmentrydate;
    }

    /**
     * Sets the value of the bmentrydate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setBMENTRYDATE(XMLGregorianCalendar value) {
        this.bmentrydate = value;
    }

    /**
     * Gets the value of the createddEt property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCREATEDDEt() {
        return createddEt;
    }

    /**
     * Sets the value of the createddEt property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setCREATEDDEt(String value) {
        this.createddEt = value;
    }

    /**
     * Gets the value of the intrefid property.
     * 
     */
    public double getINTREFID() {
        return intrefid;
    }

    /**
     * Sets the value of the intrefid property.
     * 
     */
    public void setINTREFID(double value) {
        this.intrefid = value;
    }

    /**
     * Gets the value of the assignedTo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAssignedTo() {
        return assignedTo;
    }

    /**
     * Sets the value of the assignedTo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    /**
     * Gets the value of the vmvendorname property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVMVENDORNAME() {
        return vmvendorname;
    }

    /**
     * Sets the value of the vmvendorname property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVMVENDORNAME(String value) {
        this.vmvendorname = value;
    }

    /**
     * Gets the value of the processInstance property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getProcessInstance() {
        return processInstance;
    }

    /**
     * Sets the value of the processInstance property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setProcessInstance(String value) {
        this.processInstance = value;
    }

    /**
     * Gets the value of the bminvoicedate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getBMINVOICEDATE() {
        return bminvoicedate;
    }

    /**
     * Sets the value of the bminvoicedate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setBMINVOICEDATE(XMLGregorianCalendar value) {
        this.bminvoicedate = value;
    }

    /**
     * Gets the value of the sheetMetadataName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSheetMetadataName() {
        return sheetMetadataName;
    }

    /**
     * Sets the value of the sheetMetadataName property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSheetMetadataName(String value) {
        this.sheetMetadataName = value;
    }

}
