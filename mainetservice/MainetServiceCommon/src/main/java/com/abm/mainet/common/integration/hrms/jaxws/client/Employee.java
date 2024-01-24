
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
        "sheetMetadataName",
        "trgFlag",
        "empisecuritykey",
        "userId",
        "orgName",
        "locName",
        "deptName",
        "dsgName",
        "grpCode"
})
@XmlRootElement(name = "Employee")
public class Employee {

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
    @XmlElement(name = "Trg_Flag", required = false)
    protected String trgFlag;
    @XmlElement(name = "empisecuritykey", required = false)
    protected String empisecuritykey;
    @XmlElement(name = "UserId", required = false)
    protected String userId;
    @XmlElement(name = "ORGNAME")
    protected String orgName;
    @XmlElement(name = "LOCNAME")
    protected String locName;
    @XmlElement(name = "DEPTNAME")
    protected String deptName;
    @XmlElement(name = "DSGNAME")
    protected String dsgName;
    @XmlElement(name = "Group_Code")
    protected String grpCode;

    public String getModifiedIn() {
        return modifiedIn;
    }

    public void setModifiedIn(String value) {
        this.modifiedIn = value;
    }

    public XMLGregorianCalendar getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(XMLGregorianCalendar value) {
        this.createdAt = value;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String value) {
        this.metadata = value;
    }

    public double getEmpmobno() {
        return empmobno;
    }

    public void setEmpmobno(double value) {
        this.empmobno = value;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String value) {
        this.sheetName = value;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    public String getEmplname() {
        return emplname;
    }

    public void setEmplname(String value) {
        this.emplname = value;
    }

    public XMLGregorianCalendar getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(XMLGregorianCalendar value) {
        this.modifiedAt = value;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String value) {
        this.tenant = value;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String value) {
        this.status = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String value) {
        this.sheetId = value;
    }

    public String getEmpemail() {
        return empemail;
    }

    public void setEmpemail(String value) {
        this.empemail = value;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String value) {
        this.caption = value;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String value) {
        this.assignedTo = value;
    }

    public String getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(String value) {
        this.processInstance = value;
    }

    public String getSheetMetadataName() {
        return sheetMetadataName;
    }

    public void setSheetMetadataName(String value) {
        this.sheetMetadataName = value;
    }

    public String getTrgFlag() {
        return trgFlag;
    }

    public void setTrgFlag(String trgFlag) {
        this.trgFlag = trgFlag;
    }

    public String getEmpisecuritykey() {
        return empisecuritykey;
    }

    public void setEmpisecuritykey(String empisecuritykey) {
        this.empisecuritykey = empisecuritykey;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLgipmac() {
        return lgipmac;
    }

    public void setLgipmac(String lgipmac) {
        this.lgipmac = lgipmac;
    }

    public double getDpdeptid() {
        return dpdeptid;
    }

    public void setDpdeptid(double dpdeptid) {
        this.dpdeptid = dpdeptid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public double getDsgid() {
        return dsgid;
    }

    public void setDsgid(double dsgid) {
        this.dsgid = dsgid;
    }

    public double getOrgid() {
        return orgid;
    }

    public void setOrgid(double orgid) {
        this.orgid = orgid;
    }

    public String getEmploginname() {
        return emploginname;
    }

    public void setEmploginname(String emploginname) {
        this.emploginname = emploginname;
    }

    public double getLocid() {
        return locid;
    }

    public void setLocid(double locid) {
        this.locid = locid;
    }

    public double getEmpid() {
        return empid;
    }

    public void setEmpid(double empid) {
        this.empid = empid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDsgName() {
        return dsgName;
    }

    public void setDsgName(String dsgName) {
        this.dsgName = dsgName;
    }

    public String getGrpCode() {
        return grpCode;
    }

    public void setGrpCode(String grpCode) {
        this.grpCode = grpCode;
    }

}
