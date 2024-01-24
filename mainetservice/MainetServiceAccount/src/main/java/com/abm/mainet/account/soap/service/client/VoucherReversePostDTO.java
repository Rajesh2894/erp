
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
 * Java class for voucherReversePostDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voucherReversePostDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="authRemark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="authoDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="authoFlg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="authoId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="details" type="{http://service.soap.account.mainet.abm.com/}voucherReversePostDetailDTO" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="dpDeptid" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="entryType" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="fi04Lo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fi04N1" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="fieldId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="lgIpMac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lgIpMacUpd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lmodDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="narration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="org" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="payerPayee" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="updatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="updatedby" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="vouDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="vouId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="vouNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vouPostingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="vouReferenceNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="vouReferenceNoDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="vouSubtypeCpdId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="vouTypeCpdId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucherReversePostDTO", propOrder = {
        "authRemark",
        "authoDate",
        "authoFlg",
        "authoId",
        "createdBy",
        "details",
        "dpDeptid",
        "entryType",
        "fi04Lo1",
        "fi04N1",
        "fieldId",
        "lgIpMac",
        "lgIpMacUpd",
        "lmodDate",
        "narration",
        "org",
        "payerPayee",
        "updatedDate",
        "updatedby",
        "vouDate",
        "vouId",
        "vouNo",
        "vouPostingDate",
        "vouReferenceNo",
        "vouReferenceNoDate",
        "vouSubtypeCpdId",
        "vouTypeCpdId"
})
public class VoucherReversePostDTO {

    protected String authRemark;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar authoDate;
    protected String authoFlg;
    protected Long authoId;
    protected Long createdBy;
    @XmlElement(nillable = true)
    protected List<VoucherReversePostDetailDTO> details;
    protected Long dpDeptid;
    protected Long entryType;
    protected String fi04Lo1;
    protected Long fi04N1;
    protected Long fieldId;
    protected String lgIpMac;
    protected String lgIpMacUpd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lmodDate;
    protected String narration;
    protected Long org;
    protected String payerPayee;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updatedDate;
    protected Long updatedby;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar vouDate;
    protected long vouId;
    protected String vouNo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar vouPostingDate;
    protected String vouReferenceNo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar vouReferenceNoDate;
    protected Long vouSubtypeCpdId;
    protected Long vouTypeCpdId;

    /**
     * Gets the value of the authRemark property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAuthRemark() {
        return authRemark;
    }

    /**
     * Sets the value of the authRemark property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAuthRemark(String value) {
        this.authRemark = value;
    }

    /**
     * Gets the value of the authoDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getAuthoDate() {
        return authoDate;
    }

    /**
     * Sets the value of the authoDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setAuthoDate(XMLGregorianCalendar value) {
        this.authoDate = value;
    }

    /**
     * Gets the value of the authoFlg property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAuthoFlg() {
        return authoFlg;
    }

    /**
     * Sets the value of the authoFlg property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setAuthoFlg(String value) {
        this.authoFlg = value;
    }

    /**
     * Gets the value of the authoId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getAuthoId() {
        return authoId;
    }

    /**
     * Sets the value of the authoId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setAuthoId(Long value) {
        this.authoId = value;
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
     * Gets the value of the details property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the details
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link VoucherReversePostDetailDTO }
     * 
     * 
     */
    public List<VoucherReversePostDetailDTO> getDetails() {
        if (details == null) {
            details = new ArrayList<VoucherReversePostDetailDTO>();
        }
        return this.details;
    }

    /**
     * Gets the value of the dpDeptid property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * Sets the value of the dpDeptid property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setDpDeptid(Long value) {
        this.dpDeptid = value;
    }

    /**
     * Gets the value of the entryType property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getEntryType() {
        return entryType;
    }

    /**
     * Sets the value of the entryType property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setEntryType(Long value) {
        this.entryType = value;
    }

    /**
     * Gets the value of the fi04Lo1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * Sets the value of the fi04Lo1 property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFi04Lo1(String value) {
        this.fi04Lo1 = value;
    }

    /**
     * Gets the value of the fi04N1 property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * Sets the value of the fi04N1 property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setFi04N1(Long value) {
        this.fi04N1 = value;
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
     * Gets the value of the lgIpMacUpd property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * Sets the value of the lgIpMacUpd property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setLgIpMacUpd(String value) {
        this.lgIpMacUpd = value;
    }

    /**
     * Gets the value of the lmodDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getLmodDate() {
        return lmodDate;
    }

    /**
     * Sets the value of the lmodDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setLmodDate(XMLGregorianCalendar value) {
        this.lmodDate = value;
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
     * Gets the value of the org property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getOrg() {
        return org;
    }

    /**
     * Sets the value of the org property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setOrg(Long value) {
        this.org = value;
    }

    /**
     * Gets the value of the payerPayee property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPayerPayee() {
        return payerPayee;
    }

    /**
     * Sets the value of the payerPayee property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPayerPayee(String value) {
        this.payerPayee = value;
    }

    /**
     * Gets the value of the updatedDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Sets the value of the updatedDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setUpdatedDate(XMLGregorianCalendar value) {
        this.updatedDate = value;
    }

    /**
     * Gets the value of the updatedby property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getUpdatedby() {
        return updatedby;
    }

    /**
     * Sets the value of the updatedby property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setUpdatedby(Long value) {
        this.updatedby = value;
    }

    /**
     * Gets the value of the vouDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getVouDate() {
        return vouDate;
    }

    /**
     * Sets the value of the vouDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setVouDate(XMLGregorianCalendar value) {
        this.vouDate = value;
    }

    /**
     * Gets the value of the vouId property.
     * 
     */
    public long getVouId() {
        return vouId;
    }

    /**
     * Sets the value of the vouId property.
     * 
     */
    public void setVouId(long value) {
        this.vouId = value;
    }

    /**
     * Gets the value of the vouNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVouNo() {
        return vouNo;
    }

    /**
     * Sets the value of the vouNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVouNo(String value) {
        this.vouNo = value;
    }

    /**
     * Gets the value of the vouPostingDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getVouPostingDate() {
        return vouPostingDate;
    }

    /**
     * Sets the value of the vouPostingDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setVouPostingDate(XMLGregorianCalendar value) {
        this.vouPostingDate = value;
    }

    /**
     * Gets the value of the vouReferenceNo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getVouReferenceNo() {
        return vouReferenceNo;
    }

    /**
     * Sets the value of the vouReferenceNo property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setVouReferenceNo(String value) {
        this.vouReferenceNo = value;
    }

    /**
     * Gets the value of the vouReferenceNoDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getVouReferenceNoDate() {
        return vouReferenceNoDate;
    }

    /**
     * Sets the value of the vouReferenceNoDate property.
     * 
     * @param value allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setVouReferenceNoDate(XMLGregorianCalendar value) {
        this.vouReferenceNoDate = value;
    }

    /**
     * Gets the value of the vouSubtypeCpdId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getVouSubtypeCpdId() {
        return vouSubtypeCpdId;
    }

    /**
     * Sets the value of the vouSubtypeCpdId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setVouSubtypeCpdId(Long value) {
        this.vouSubtypeCpdId = value;
    }

    /**
     * Gets the value of the vouTypeCpdId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getVouTypeCpdId() {
        return vouTypeCpdId;
    }

    /**
     * Sets the value of the vouTypeCpdId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setVouTypeCpdId(Long value) {
        this.vouTypeCpdId = value;
    }

}
