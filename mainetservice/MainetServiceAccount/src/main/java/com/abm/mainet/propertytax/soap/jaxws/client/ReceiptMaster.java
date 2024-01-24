
package com.abm.mainet.propertytax.soap.jaxws.client;

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
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="CREATED_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RECEIPT_DEL_ORDER_NO" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="RECEIPT_DEL_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RECEIPT_TYPE_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_MANUAL_RCPTNO" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_RCPTID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="INT_FLAG" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="INT_DATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_RECEIPTID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="FinYearId" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="fieldId" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="CREATED_BY" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ORGID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_NARRATION" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Receipt_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_RECEIPTAMOUNT" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="RM_RECEIPTNO" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_RECEIVEDFROM" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="lgIpMac" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="receiptate" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="RM_RECEIPTDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="rmReceiptAmt" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="langId" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="REF_ID" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
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
        "modifiedIn",
        "dpdeptid",
        "createddate",
        "receiptdelorderno",
        "receiptdelflag",
        "createdAt",
        "metadata",
        "receipttypeflag",
        "sheetName",
        "rmmanualrcptno",
        "modifiedBy",
        "rmrcptid",
        "modifiedAt",
        "intflag",
        "intdate",
        "tenant",
        "rmreceiptid",
        "finYearId",
        "fieldId",
        "createdby",
        "orgid",
        "status",
        "rmnarration",
        "createdBy",
        "sheetId",
        "receiptRef",
        "rmreceiptamount",
        "rmreceiptno",
        "caption",
        "assignedTo",
        "rmreceivedfrom",
        "lgIpMac",
        "receiptate",
        "rmreceiptdate",
        "rmReceiptAmt",
        "processInstance",
        "langId",
        "refid",
        "sheetMetadataName"
})
@XmlRootElement(name = "ReceiptMaster")
public class ReceiptMaster {

    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "DP_DEPTID")
    protected double dpdeptid;
    @XmlElement(name = "CREATED_DATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createddate;
    @XmlElement(name = "RECEIPT_DEL_ORDER_NO")
    protected double receiptdelorderno;
    @XmlElement(name = "RECEIPT_DEL_FLAG", required = true)
    protected String receiptdelflag;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "RECEIPT_TYPE_FLAG", required = true)
    protected String receipttypeflag;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "RM_MANUAL_RCPTNO", required = true)
    protected String rmmanualrcptno;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "RM_RCPTID")
    protected double rmrcptid;
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
    @XmlElement(name = "RM_RECEIPTID")
    protected double rmreceiptid;
    @XmlElement(name = "FinYearId")
    protected double finYearId;
    protected double fieldId;
    @XmlElement(name = "CREATED_BY")
    protected double createdby;
    @XmlElement(name = "ORGID")
    protected double orgid;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "RM_NARRATION", required = true)
    protected String rmnarration;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "Receipt_Ref", required = true)
    protected String receiptRef;
    @XmlElement(name = "RM_RECEIPTAMOUNT")
    protected double rmreceiptamount;
    @XmlElement(name = "RM_RECEIPTNO")
    protected double rmreceiptno;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "RM_RECEIVEDFROM", required = true)
    protected String rmreceivedfrom;
    @XmlElement(required = true)
    protected String lgIpMac;
    @XmlElement(required = true)
    protected String receiptate;
    @XmlElement(name = "RM_RECEIPTDATE", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar rmreceiptdate;
    @XmlElement(required = true)
    protected String rmReceiptAmt;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    protected double langId;
    @XmlElement(name = "REF_ID")
    protected double refid;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

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
     * Gets the value of the receiptdelorderno property.
     * 
     */
    public double getRECEIPTDELORDERNO() {
        return receiptdelorderno;
    }

    /**
     * Sets the value of the receiptdelorderno property.
     * 
     */
    public void setRECEIPTDELORDERNO(double value) {
        this.receiptdelorderno = value;
    }

    /**
     * Gets the value of the receiptdelflag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRECEIPTDELFLAG() {
        return receiptdelflag;
    }

    /**
     * Sets the value of the receiptdelflag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRECEIPTDELFLAG(String value) {
        this.receiptdelflag = value;
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
     * Gets the value of the receipttypeflag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRECEIPTTYPEFLAG() {
        return receipttypeflag;
    }

    /**
     * Sets the value of the receipttypeflag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRECEIPTTYPEFLAG(String value) {
        this.receipttypeflag = value;
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
     * Gets the value of the rmmanualrcptno property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRMMANUALRCPTNO() {
        return rmmanualrcptno;
    }

    /**
     * Sets the value of the rmmanualrcptno property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRMMANUALRCPTNO(String value) {
        this.rmmanualrcptno = value;
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
     * Gets the value of the rmrcptid property.
     * 
     */
    public double getRMRCPTID() {
        return rmrcptid;
    }

    /**
     * Sets the value of the rmrcptid property.
     * 
     */
    public void setRMRCPTID(double value) {
        this.rmrcptid = value;
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
     * Gets the value of the rmreceiptid property.
     * 
     */
    public double getRMRECEIPTID() {
        return rmreceiptid;
    }

    /**
     * Sets the value of the rmreceiptid property.
     * 
     */
    public void setRMRECEIPTID(double value) {
        this.rmreceiptid = value;
    }

    /**
     * Gets the value of the finYearId property.
     * 
     */
    public double getFinYearId() {
        return finYearId;
    }

    /**
     * Sets the value of the finYearId property.
     * 
     */
    public void setFinYearId(double value) {
        this.finYearId = value;
    }

    /**
     * Gets the value of the fieldId property.
     * 
     */
    public double getFieldId() {
        return fieldId;
    }

    /**
     * Sets the value of the fieldId property.
     * 
     */
    public void setFieldId(double value) {
        this.fieldId = value;
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
     * Gets the value of the rmnarration property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRMNARRATION() {
        return rmnarration;
    }

    /**
     * Sets the value of the rmnarration property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRMNARRATION(String value) {
        this.rmnarration = value;
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
     * Gets the value of the receiptRef property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReceiptRef() {
        return receiptRef;
    }

    /**
     * Sets the value of the receiptRef property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setReceiptRef(String value) {
        this.receiptRef = value;
    }

    /**
     * Gets the value of the rmreceiptamount property.
     * 
     */
    public double getRMRECEIPTAMOUNT() {
        return rmreceiptamount;
    }

    /**
     * Sets the value of the rmreceiptamount property.
     * 
     */
    public void setRMRECEIPTAMOUNT(double value) {
        this.rmreceiptamount = value;
    }

    /**
     * Gets the value of the rmreceiptno property.
     * 
     */
    public double getRMRECEIPTNO() {
        return rmreceiptno;
    }

    /**
     * Sets the value of the rmreceiptno property.
     * 
     */
    public void setRMRECEIPTNO(double value) {
        this.rmreceiptno = value;
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
     * Gets the value of the rmreceivedfrom property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRMRECEIVEDFROM() {
        return rmreceivedfrom;
    }

    /**
     * Sets the value of the rmreceivedfrom property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRMRECEIVEDFROM(String value) {
        this.rmreceivedfrom = value;
    }

    /**
     * Gets the value of the lgIpMac property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * Sets the value of the lgIpMac property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLgIpMac(String value) {
        this.lgIpMac = value;
    }

    /**
     * Gets the value of the receiptate property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getReceiptate() {
        return receiptate;
    }

    /**
     * Sets the value of the receiptate property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setReceiptate(String value) {
        this.receiptate = value;
    }

    /**
     * Gets the value of the rmreceiptdate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getRMRECEIPTDATE() {
        return rmreceiptdate;
    }

    /**
     * Sets the value of the rmreceiptdate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setRMRECEIPTDATE(XMLGregorianCalendar value) {
        this.rmreceiptdate = value;
    }

    /**
     * Gets the value of the rmReceiptAmt property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRmReceiptAmt() {
        return rmReceiptAmt;
    }

    /**
     * Sets the value of the rmReceiptAmt property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setRmReceiptAmt(String value) {
        this.rmReceiptAmt = value;
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
     * Gets the value of the langId property.
     * 
     */
    public double getLangId() {
        return langId;
    }

    /**
     * Sets the value of the langId property.
     * 
     */
    public void setLangId(double value) {
        this.langId = value;
    }

    /**
     * Gets the value of the refid property.
     * 
     */
    public double getREFID() {
        return refid;
    }

    /**
     * Sets the value of the refid property.
     * 
     */
    public void setREFID(double value) {
        this.refid = value;
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
