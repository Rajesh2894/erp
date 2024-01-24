
package com.abm.mainet.account.soap.service.client;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for voucherPostDetailDTO complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="voucherPostDetailDTO"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="budgetCodeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="drCrId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="payModeId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="sacHeadId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="voucherAmount" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "voucherPostDetailDTO", propOrder = {
        "budgetCodeId",
        "drCrId",
        "payModeId",
        "sacHeadId",
        "voucherAmount"
})
public class VoucherPostDetailDTO {

    protected Long budgetCodeId;
    protected Long drCrId;
    protected Long payModeId;
    protected Long sacHeadId;
    protected BigDecimal voucherAmount;

    /**
     * Gets the value of the budgetCodeId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    /**
     * Sets the value of the budgetCodeId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setBudgetCodeId(Long value) {
        this.budgetCodeId = value;
    }

    /**
     * Gets the value of the drCrId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getDrCrId() {
        return drCrId;
    }

    /**
     * Sets the value of the drCrId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setDrCrId(Long value) {
        this.drCrId = value;
    }

    /**
     * Gets the value of the payModeId property.
     * 
     * @return possible object is {@link Long }
     * 
     */
    public Long getPayModeId() {
        return payModeId;
    }

    /**
     * Sets the value of the payModeId property.
     * 
     * @param value allowed object is {@link Long }
     * 
     */
    public void setPayModeId(Long value) {
        this.payModeId = value;
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
     * Gets the value of the voucherAmount property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getVoucherAmount() {
        return voucherAmount;
    }

    /**
     * Sets the value of the voucherAmount property.
     * 
     * @param value allowed object is {@link BigDecimal }
     * 
     */
    public void setVoucherAmount(BigDecimal value) {
        this.voucherAmount = value;
    }

}
