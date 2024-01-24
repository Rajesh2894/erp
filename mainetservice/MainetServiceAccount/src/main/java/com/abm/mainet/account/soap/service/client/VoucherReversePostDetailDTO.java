
package com.abm.mainet.account.soap.service.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for voucherReversePostDetailDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voucherReversePostDetailDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="budgetCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="createdBy" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="drcrCpdId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="fieldCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="functionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fundCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lgIpMac" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lgIpMacUpd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="lmodDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="master" type="{http://service.soap.account.mainet.abm.com/}voucherReversePostDTO" minOccurs="0"/&gt;
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="primaryHeadCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="sacHeadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="secondaryHeadCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="updatedBy" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="updatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="voudetAmt" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="voudetId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucherReversePostDetailDTO", propOrder = {
        "budgetCode",
        "createdBy",
        "drcrCpdId",
        "fieldCode",
        "functionCode",
        "fundCode",
        "lgIpMac",
        "lgIpMacUpd",
        "lmodDate",
        "master",
        "orgId",
        "primaryHeadCode",
        "sacHeadId",
        "secondaryHeadCode",
        "updatedBy",
        "updatedDate",
        "voudetAmt",
        "voudetId"
})
public class VoucherReversePostDetailDTO {

    protected Long budgetCode;
    protected Long createdBy;
    protected Long drcrCpdId;
    protected String fieldCode;
    protected String functionCode;
    protected String fundCode;
    protected String lgIpMac;
    protected String lgIpMacUpd;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lmodDate;
    protected VoucherReversePostDTO master;
    protected Long orgId;
    protected String primaryHeadCode;
    protected Long sacHeadId;
    protected String secondaryHeadCode;
    protected Long updatedBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updatedDate;
    protected BigDecimal voudetAmt;
    protected long voudetId;

    /**
     * Gets the value of the budgetCode property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getBudgetCode() {
        return budgetCode;
    }

    /**
     * Sets the value of the budgetCode property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setBudgetCode(Long value) {
        this.budgetCode = value;
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
     * Gets the value of the drcrCpdId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getDrcrCpdId() {
        return drcrCpdId;
    }

    /**
     * Sets the value of the drcrCpdId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setDrcrCpdId(Long value) {
        this.drcrCpdId = value;
    }

    /**
     * Gets the value of the fieldCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * Sets the value of the fieldCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFieldCode(String value) {
        this.fieldCode = value;
    }

    /**
     * Gets the value of the functionCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Sets the value of the functionCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFunctionCode(String value) {
        this.functionCode = value;
    }

    /**
     * Gets the value of the fundCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * Sets the value of the fundCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setFundCode(String value) {
        this.fundCode = value;
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
     * Gets the value of the master property.
     * 
     * @return possible object is {@link VoucherReversePostDTO }
     * 
     */
    public VoucherReversePostDTO getMaster() {
        return master;
    }

    /**
     * Sets the value of the master property.
     * 
     * @param value allowed object is {@link VoucherReversePostDTO }
     * 
     */
    public void setMaster(VoucherReversePostDTO value) {
        this.master = value;
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
     * Gets the value of the primaryHeadCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPrimaryHeadCode() {
        return primaryHeadCode;
    }

    /**
     * Sets the value of the primaryHeadCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setPrimaryHeadCode(String value) {
        this.primaryHeadCode = value;
    }

    /**
     * Gets the value of the sacHeadId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * Sets the value of the sacHeadId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setSacHeadId(Long value) {
        this.sacHeadId = value;
    }

    /**
     * Gets the value of the secondaryHeadCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSecondaryHeadCode() {
        return secondaryHeadCode;
    }

    /**
     * Sets the value of the secondaryHeadCode property.
     * 
     * @param value allowed object is {@link String }
     * 
     */
    public void setSecondaryHeadCode(String value) {
        this.secondaryHeadCode = value;
    }

    /**
     * Gets the value of the updatedBy property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * Sets the value of the updatedBy property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setUpdatedBy(Long value) {
        this.updatedBy = value;
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
     * Gets the value of the voudetAmt property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getVoudetAmt() {
        return voudetAmt;
    }

    /**
     * Sets the value of the voudetAmt property.
     * 
     * @param value allowed object is {@link BigDecimal }
     * 
     */
    public void setVoudetAmt(BigDecimal value) {
        this.voudetAmt = value;
    }

    /**
     * Gets the value of the voudetId property.
     * 
     */
    public long getVoudetId() {
        return voudetId;
    }

    /**
     * Sets the value of the voudetId property.
     * 
     */
    public void setVoudetId(long value) {
        this.voudetId = value;
    }

}
