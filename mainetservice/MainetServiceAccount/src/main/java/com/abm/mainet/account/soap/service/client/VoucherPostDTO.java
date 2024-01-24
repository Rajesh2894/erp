
package com.abm.mainet.account.soap.service.client;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for voucherPostDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voucherPostDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="authFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="billVouPostingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="createdDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="departmentId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="entryType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fieldId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="financialYearId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="langId" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="lgIpMac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="narration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="payEntryMakerFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="payerOrPayee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="propertyIntFlag" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="templateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="voucherDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="voucherDetails" type="{http://service.soap.account.mainet.abm.com/}voucherPostDetailDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="voucherReferenceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="voucherReferenceNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="voucherSubTypeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="voucherType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucherPostDTO", namespace = "", propOrder = {
        "authFlag",
        "billVouPostingDate",
        "createdBy",
        "createdDate",
        "departmentId",
        "entryType",
        "fieldId",
        "financialYearId",
        "langId",
        "lgIpMac",
        "narration",
        "orgId",
        "payEntryMakerFlag",
        "payerOrPayee",
        "propertyIntFlag",
        "templateType",
        "voucherDate",
        "voucherDetails",
        "voucherReferenceDate",
        "voucherReferenceNo",
        "voucherSubTypeId",
        "voucherType"
})
public class VoucherPostDTO {

    protected String authFlag;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar billVouPostingDate;
    protected Long createdBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    protected Long departmentId;
    protected String entryType;
    protected Long fieldId;
    protected Long financialYearId;
    protected int langId;
    protected String lgIpMac;
    protected String narration;
    protected Long orgId;
    protected String payEntryMakerFlag;
    protected String payerOrPayee;
    protected String propertyIntFlag;
    protected String templateType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar voucherDate;
    @XmlElement(nillable = true)
    protected List<VoucherPostDetailDTO> voucherDetails;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar voucherReferenceDate;
    protected String voucherReferenceNo;
    protected Long voucherSubTypeId;
    protected String voucherType;

    /**
     * Gets the value of the authFlag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAuthFlag() {
        return authFlag;
    }

    /**
     * Sets the value of the authFlag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAuthFlag(String value) {
        this.authFlag = value;
    }

    /**
     * Gets the value of the billVouPostingDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getBillVouPostingDate() {
        return billVouPostingDate;
    }

    /**
     * Sets the value of the billVouPostingDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setBillVouPostingDate(XMLGregorianCalendar value) {
        this.billVouPostingDate = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setCreatedBy(Long value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the departmentId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the value of the departmentId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setDepartmentId(Long value) {
        this.departmentId = value;
    }

    /**
     * Gets the value of the entryType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEntryType() {
        return entryType;
    }

    /**
     * Sets the value of the entryType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setEntryType(String value) {
        this.entryType = value;
    }

    /**
     * Gets the value of the fieldId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * Sets the value of the fieldId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setFieldId(Long value) {
        this.fieldId = value;
    }

    /**
     * Gets the value of the financialYearId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getFinancialYearId() {
        return financialYearId;
    }

    /**
     * Sets the value of the financialYearId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setFinancialYearId(Long value) {
        this.financialYearId = value;
    }

    /**
     * Gets the value of the langId property.
     * 
     */
    public int getLangId() {
        return langId;
    }

    /**
     * Sets the value of the langId property.
     * 
     */
    public void setLangId(int value) {
        this.langId = value;
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
     * Gets the value of the narration property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNarration() {
        return narration;
    }

    /**
     * Sets the value of the narration property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setNarration(String value) {
        this.narration = value;
    }

    /**
     * Gets the value of the orgId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setOrgId(Long value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the payEntryMakerFlag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPayEntryMakerFlag() {
        return payEntryMakerFlag;
    }

    /**
     * Sets the value of the payEntryMakerFlag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPayEntryMakerFlag(String value) {
        this.payEntryMakerFlag = value;
    }

    /**
     * Gets the value of the payerOrPayee property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPayerOrPayee() {
        return payerOrPayee;
    }

    /**
     * Sets the value of the payerOrPayee property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPayerOrPayee(String value) {
        this.payerOrPayee = value;
    }

    /**
     * Gets the value of the propertyIntFlag property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPropertyIntFlag() {
        return propertyIntFlag;
    }

    /**
     * Sets the value of the propertyIntFlag property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPropertyIntFlag(String value) {
        this.propertyIntFlag = value;
    }

    /**
     * Gets the value of the templateType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * Sets the value of the templateType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setTemplateType(String value) {
        this.templateType = value;
    }

    /**
     * Gets the value of the voucherDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getVoucherDate() {
        return voucherDate;
    }

    /**
     * Sets the value of the voucherDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setVoucherDate(XMLGregorianCalendar value) {
        this.voucherDate = value;
    }

    /**
     * Gets the value of the voucherDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the
     * voucherDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getVoucherDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link VoucherPostDetailDTO }
     * 
     * 
     */
    public List<VoucherPostDetailDTO> getVoucherDetails() {
        if (voucherDetails == null) {
            voucherDetails = new ArrayList<VoucherPostDetailDTO>();
        }
        return this.voucherDetails;
    }

    /**
     * Gets the value of the voucherReferenceDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getVoucherReferenceDate() {
        return voucherReferenceDate;
    }

    /**
     * Sets the value of the voucherReferenceDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setVoucherReferenceDate(XMLGregorianCalendar value) {
        this.voucherReferenceDate = value;
    }

    /**
     * Gets the value of the voucherReferenceNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVoucherReferenceNo() {
        return voucherReferenceNo;
    }

    /**
     * Sets the value of the voucherReferenceNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVoucherReferenceNo(String value) {
        this.voucherReferenceNo = value;
    }

    /**
     * Gets the value of the voucherSubTypeId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getVoucherSubTypeId() {
        return voucherSubTypeId;
    }

    /**
     * Sets the value of the voucherSubTypeId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setVoucherSubTypeId(Long value) {
        this.voucherSubTypeId = value;
    }

    /**
     * Gets the value of the voucherType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVoucherType() {
        return voucherType;
    }

    /**
     * Sets the value of the voucherType property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVoucherType(String value) {
        this.voucherType = value;
    }

}
