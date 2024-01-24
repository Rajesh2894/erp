
package com.abm.mainet.common.integration.hrms.jaxws.client;

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
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedIn" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTID" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetId" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="CreatedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Metadata" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="SheetName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_Status" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Caption" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="AssignedTo" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTDESC" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ModifiedAt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Tenant" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProcessInstance" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_NAME_MAR" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="DP_DEPTCODE" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
        "modifiedIn",
        "createdBy",
        "dpdeptid",
        "sheetId",
        "createdAt",
        "metadata",
        "sheetName",
        "dpStatus",
        "modifiedBy",
        "caption",
        "assignedTo",
        "dpdeptdesc",
        "modifiedAt",
        "tenant",
        "processInstance",
        "dpnamemar",
        "dpdeptcode",
        "sheetMetadataName",
        "mapId",
        "mapStatus",
        "functionRef",
        "functionCode",
        "ulbCode",
        "trainingRef",
        "trainingCtx"

})
@XmlRootElement(name = "Dept_Master")
public class DeptMaster {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "ModifiedIn", required = true)
    protected String modifiedIn;
    @XmlElement(name = "CreatedBy", required = true)
    protected String createdBy;
    @XmlElement(name = "DP_DeptID", required = true)
    protected String dpdeptid;
    @XmlElement(name = "SheetId", required = true)
    protected String sheetId;
    @XmlElement(name = "CreatedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdAt;
    @XmlElement(name = "Metadata", required = true)
    protected String metadata;
    @XmlElement(name = "SheetName", required = true)
    protected String sheetName;
    @XmlElement(name = "DP_Status", required = true)
    protected String dpStatus;
    @XmlElement(name = "ModifiedBy", required = true)
    protected String modifiedBy;
    @XmlElement(name = "Caption", required = true)
    protected String caption;
    @XmlElement(name = "AssignedTo", required = true)
    protected String assignedTo;
    @XmlElement(name = "DP_DeptDesc", required = true)
    protected String dpdeptdesc;
    @XmlElement(name = "ModifiedAt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedAt;
    @XmlElement(name = "Tenant", required = true)
    protected String tenant;
    @XmlElement(name = "ProcessInstance", required = true)
    protected String processInstance;
    @XmlElement(name = "DP_Name_MAR", required = true)
    protected String dpnamemar;
    @XmlElement(name = "DP_DeptCode", required = true)
    protected String dpdeptcode;
    @XmlElement(name = "SheetMetadataName", required = true)
    protected String sheetMetadataName;
    @XmlElement(name = "Map_Id", required = true)
    protected double mapId;
    @XmlElement(name = "Map_Status", required = true)
    protected String mapStatus;
    @XmlElement(name = "Function_Ref", required = true)
    protected String functionRef;
    @XmlElement(name = "Function_Code", required = true)
    protected String functionCode;
    @XmlElement(name = "ULB_Code", required = true)
    protected String ulbCode;
    @XmlElement(name = "Training_ref", required = true)
    protected String trainingRef;
    @XmlElement(name = "Training_ctx", required = true)
    protected String trainingCtx;

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
     * Gets the value of the dpdeptid property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getDPDEPTID() {
        return dpdeptid;
    }

    /**
     * Sets the value of the dpdeptid property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setDPDEPTID(String value) {
        this.dpdeptid = value;
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
     * Gets the value of the dpStatus property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getDPStatus() {
        return dpStatus;
    }

    /**
     * Sets the value of the dpStatus property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setDPStatus(String value) {
        this.dpStatus = value;
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
     * Gets the value of the dpdeptdesc property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getDPDEPTDESC() {
        return dpdeptdesc;
    }

    /**
     * Sets the value of the dpdeptdesc property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setDPDEPTDESC(String value) {
        this.dpdeptdesc = value;
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
     * Gets the value of the dpnamemar property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getDPNAMEMAR() {
        return dpnamemar;
    }

    /**
     * Sets the value of the dpnamemar property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setDPNAMEMAR(String value) {
        this.dpnamemar = value;
    }

    /**
     * Gets the value of the dpdeptcode property.
     * 
     * @return
     * possible object is
     * {@link String }
     * 
     */
    public String getDPDEPTCODE() {
        return dpdeptcode;
    }

    /**
     * Sets the value of the dpdeptcode property.
     * 
     * @param value
     * allowed object is
     * {@link String }
     * 
     */
    public void setDPDEPTCODE(String value) {
        this.dpdeptcode = value;
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

    public double getMapId() {
        return mapId;
    }

    public void setMapId(double mapId) {
        this.mapId = mapId;
    }

    public String getMapStatus() {
        return mapStatus;
    }

    public void setMapStatus(String mapStatus) {
        this.mapStatus = mapStatus;
    }

    public String getFunctionRef() {
        return functionRef;
    }

    public void setFunctionRef(String functionRef) {
        this.functionRef = functionRef;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getUlbCode() {
        return ulbCode;
    }

    public void setUlbCode(String ulbCode) {
        this.ulbCode = ulbCode;
    }

    public String getTrainingRef() {
        return trainingRef;
    }

    public void setTrainingRef(String trainingRef) {
        this.trainingRef = trainingRef;
    }

    public String getTrainingCtx() {
        return trainingCtx;
    }

    public void setTrainingCtx(String trainingCtx) {
        this.trainingCtx = trainingCtx;
    }

}
