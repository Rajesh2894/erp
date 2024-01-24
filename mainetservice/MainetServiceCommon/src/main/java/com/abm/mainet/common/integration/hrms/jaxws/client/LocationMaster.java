
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
 *         &lt;element name="Loc_Seq_No" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Acc_Unit_Mapping_ID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Attachment" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="La_Running_No" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Duplicate_Msg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="msg_content" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Taluk" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Lstatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Area" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ISAccUnit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Location_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Landmark" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Address" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Seq_no" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="mob_no" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="City" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Ward" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Last_Run_no" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Pincode" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="Len_Acc_Unit" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Location_Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Lower_Location" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Loc_Category" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Address_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="UserID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Name_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ULB_Code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Division" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Accounting_Unit" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="userid_Ref" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Loc_Area_Reg" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName_Lower" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Zone" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Len_AcUnit_Id" type="{http://www.w3.org/2001/XMLSchema}double"/&gt;
 *         &lt;element name="AccUnitMap_Context" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Duplicate_Check" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Active_Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Is_Acc_Unit_1" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "locSeqNo",
    "accUnitMappingID",
    "createdAt",
    "attachment",
    "sheetName",
    "modifiedBy",
    "laRunningNo",
    "duplicateMsg",
    "msgContent",
    "taluk",
    "lstatus",
    "locArea",
    "isAccUnit",
    "status",
    "locationName",
    "createdBy",
    "locLandmark",
    "locAddress",
    "seqNo",
    "mobNo",
    "city",
    "ward",
    "processInstance",
    "district",
    "lastRunNo",
    "pincode",
    "lenAccUnit",
    "modifiedIn",
    "ulbName",
    "locationCode",
    "metadata",
    "lowerLocation",
    "modifiedAt",
    "locCategory",
    "tenant",
    "locAddressReg",
    "userID",
    "locNameReg",
    "ulbCode",
    "division",
    "accountingUnit",
    "useridRef",
    "locAreaReg",
    "sheetNameLower",
    "sheetId",
    "zone",
    "caption",
    "assignedTo",
    "lenAcUnitId",
    "accUnitMapContext",
    "duplicateCheck",
    "activeStatus",
    "isAccUnit1",
    "sheetMetadataName"
})
@XmlRootElement(name = "LocationMaster")
public class LocationMaster {

    @XmlElement(name = "Loc_Seq_No")
    protected double locSeqNo;
    @XmlElement(name = "Acc_Unit_Mapping_ID", required = true)
    protected String accUnitMappingID;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Attachment", required = true)
    protected String attachment;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "La_Running_No")
    protected double laRunningNo;
    @XmlElement(name = "Duplicate_Msg", required = true)
    protected String duplicateMsg;
    @XmlElement(name = "msg_content", required = true)
    protected String msgContent;
    @XmlElement(name = "Taluk", required = true)
    protected String taluk;
    @XmlElement(name = "Lstatus", required = true)
    protected String lstatus;
    @XmlElement(name = "Loc_Area", required = true)
    protected String locArea;
    @XmlElement(name = "ISAccUnit", required = true)
    protected String isAccUnit;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Location_Name", required = true)
    protected String locationName;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "Loc_Landmark", required = true)
    protected String locLandmark;
    @XmlElement(name = "Loc_Address", required = true)
    protected String locAddress;
    @XmlElement(name = "Seq_no")
    protected double seqNo;
    @XmlElement(name = "mob_no", required = true)
    protected String mobNo;
    @XmlElement(name = "City", required = true)
    protected String city;
    @XmlElement(name = "Ward", required = true)
    protected String ward;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "District", required = true)
    protected String district;
    @XmlElement(name = "Last_Run_no", required = true)
    protected String lastRunNo;
    @XmlElement(name = "Pincode")
    protected double pincode;
    @XmlElement(name = "Len_Acc_Unit")
    protected double lenAccUnit;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "ULB_Name", required = true)
    protected String ulbName;
    @XmlElement(name = "Location_Code", required = true)
    protected String locationCode;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "Lower_Location", required = true)
    protected String lowerLocation;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Loc_Category", required = true)
    protected String locCategory;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "Loc_Address_Reg", required = true)
    protected String locAddressReg;
    @XmlElement(name = "UserID", required = true)
    protected String userID;
    @XmlElement(name = "Loc_Name_Reg", required = true)
    protected String locNameReg;
    @XmlElement(name = "ULB_Code", required = true)
    protected String ulbCode;
    @XmlElement(name = "Division", required = true)
    protected String division;
    @XmlElement(name = "Accounting_Unit", required = true)
    protected String accountingUnit;
    @XmlElement(name = "userid_Ref", required = true)
    protected String useridRef;
    @XmlElement(name = "Loc_Area_Reg", required = true)
    protected String locAreaReg;
    @XmlElement(name = "SheetName_Lower", required = true)
    protected String sheetNameLower;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "Zone", required = true)
    protected String zone;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "Len_AcUnit_Id")
    protected double lenAcUnitId;
    @XmlElement(name = "AccUnitMap_Context", required = true)
    protected String accUnitMapContext;
    @XmlElement(name = "Duplicate_Check", required = true)
    protected String duplicateCheck;
    @XmlElement(name = "Active_Status", required = true)
    protected String activeStatus;
    @XmlElement(name = "Is_Acc_Unit_1", required = true)
    protected String isAccUnit1;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;

    /**
     * Gets the value of the locSeqNo property.
     * 
     */
    public double getLocSeqNo() {
        return locSeqNo;
    }

    /**
     * Sets the value of the locSeqNo property.
     * 
     */
    public void setLocSeqNo(double value) {
        this.locSeqNo = value;
    }

    /**
     * Gets the value of the accUnitMappingID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccUnitMappingID() {
        return accUnitMappingID;
    }

    /**
     * Sets the value of the accUnitMappingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccUnitMappingID(String value) {
        this.accUnitMappingID = value;
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
     * Gets the value of the attachment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * Sets the value of the attachment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttachment(String value) {
        this.attachment = value;
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
     * Gets the value of the laRunningNo property.
     * 
     */
    public double getLaRunningNo() {
        return laRunningNo;
    }

    /**
     * Sets the value of the laRunningNo property.
     * 
     */
    public void setLaRunningNo(double value) {
        this.laRunningNo = value;
    }

    /**
     * Gets the value of the duplicateMsg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuplicateMsg() {
        return duplicateMsg;
    }

    /**
     * Sets the value of the duplicateMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuplicateMsg(String value) {
        this.duplicateMsg = value;
    }

    /**
     * Gets the value of the msgContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * Sets the value of the msgContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMsgContent(String value) {
        this.msgContent = value;
    }

    /**
     * Gets the value of the taluk property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaluk() {
        return taluk;
    }

    /**
     * Sets the value of the taluk property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaluk(String value) {
        this.taluk = value;
    }

    /**
     * Gets the value of the lstatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLstatus() {
        return lstatus;
    }

    /**
     * Sets the value of the lstatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLstatus(String value) {
        this.lstatus = value;
    }

    /**
     * Gets the value of the locArea property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocArea() {
        return locArea;
    }

    /**
     * Sets the value of the locArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocArea(String value) {
        this.locArea = value;
    }

    /**
     * Gets the value of the isAccUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISAccUnit() {
        return isAccUnit;
    }

    /**
     * Sets the value of the isAccUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISAccUnit(String value) {
        this.isAccUnit = value;
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
     * Gets the value of the locationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
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
     * Gets the value of the locLandmark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocLandmark() {
        return locLandmark;
    }

    /**
     * Sets the value of the locLandmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocLandmark(String value) {
        this.locLandmark = value;
    }

    /**
     * Gets the value of the locAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocAddress() {
        return locAddress;
    }

    /**
     * Sets the value of the locAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocAddress(String value) {
        this.locAddress = value;
    }

    /**
     * Gets the value of the seqNo property.
     * 
     */
    public double getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     * 
     */
    public void setSeqNo(double value) {
        this.seqNo = value;
    }

    /**
     * Gets the value of the mobNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobNo() {
        return mobNo;
    }

    /**
     * Sets the value of the mobNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobNo(String value) {
        this.mobNo = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the ward property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWard() {
        return ward;
    }

    /**
     * Sets the value of the ward property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWard(String value) {
        this.ward = value;
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
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the lastRunNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastRunNo() {
        return lastRunNo;
    }

    /**
     * Sets the value of the lastRunNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastRunNo(String value) {
        this.lastRunNo = value;
    }

    /**
     * Gets the value of the pincode property.
     * 
     */
    public double getPincode() {
        return pincode;
    }

    /**
     * Sets the value of the pincode property.
     * 
     */
    public void setPincode(double value) {
        this.pincode = value;
    }

    /**
     * Gets the value of the lenAccUnit property.
     * 
     */
    public double getLenAccUnit() {
        return lenAccUnit;
    }

    /**
     * Sets the value of the lenAccUnit property.
     * 
     */
    public void setLenAccUnit(double value) {
        this.lenAccUnit = value;
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
     * Gets the value of the locationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * Sets the value of the locationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationCode(String value) {
        this.locationCode = value;
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
     * Gets the value of the lowerLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLowerLocation() {
        return lowerLocation;
    }

    /**
     * Sets the value of the lowerLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLowerLocation(String value) {
        this.lowerLocation = value;
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
     * Gets the value of the locCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocCategory() {
        return locCategory;
    }

    /**
     * Sets the value of the locCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocCategory(String value) {
        this.locCategory = value;
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
     * Gets the value of the locAddressReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocAddressReg() {
        return locAddressReg;
    }

    /**
     * Sets the value of the locAddressReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocAddressReg(String value) {
        this.locAddressReg = value;
    }

    /**
     * Gets the value of the userID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the value of the userID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserID(String value) {
        this.userID = value;
    }

    /**
     * Gets the value of the locNameReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocNameReg() {
        return locNameReg;
    }

    /**
     * Sets the value of the locNameReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocNameReg(String value) {
        this.locNameReg = value;
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
     * Gets the value of the division property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivision() {
        return division;
    }

    /**
     * Sets the value of the division property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivision(String value) {
        this.division = value;
    }

    /**
     * Gets the value of the accountingUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingUnit() {
        return accountingUnit;
    }

    /**
     * Sets the value of the accountingUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingUnit(String value) {
        this.accountingUnit = value;
    }

    /**
     * Gets the value of the useridRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUseridRef() {
        return useridRef;
    }

    /**
     * Sets the value of the useridRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUseridRef(String value) {
        this.useridRef = value;
    }

    /**
     * Gets the value of the locAreaReg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocAreaReg() {
        return locAreaReg;
    }

    /**
     * Sets the value of the locAreaReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocAreaReg(String value) {
        this.locAreaReg = value;
    }

    /**
     * Gets the value of the sheetNameLower property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSheetNameLower() {
        return sheetNameLower;
    }

    /**
     * Sets the value of the sheetNameLower property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSheetNameLower(String value) {
        this.sheetNameLower = value;
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
     * Gets the value of the zone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets the value of the zone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZone(String value) {
        this.zone = value;
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
     * Gets the value of the lenAcUnitId property.
     * 
     */
    public double getLenAcUnitId() {
        return lenAcUnitId;
    }

    /**
     * Sets the value of the lenAcUnitId property.
     * 
     */
    public void setLenAcUnitId(double value) {
        this.lenAcUnitId = value;
    }

    /**
     * Gets the value of the accUnitMapContext property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccUnitMapContext() {
        return accUnitMapContext;
    }

    /**
     * Sets the value of the accUnitMapContext property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccUnitMapContext(String value) {
        this.accUnitMapContext = value;
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
     * Gets the value of the isAccUnit1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsAccUnit1() {
        return isAccUnit1;
    }

    /**
     * Sets the value of the isAccUnit1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsAccUnit1(String value) {
        this.isAccUnit1 = value;
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
