
package com.abm.mainet.account.soap.service.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for doVoucherPosting complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="doVoucherPosting"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="voucherPostDTO" type="{http://service.soap.account.mainet.abm.com/}voucherPostDTO" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doVoucherPosting", propOrder = {
        "voucherPostDTO"
})
public class DoVoucherPosting {

    @XmlElement(namespace = "http://service.soap.account.mainet.abm.com/")
    protected VoucherPostDTO voucherPostDTO;

    /**
     * Gets the value of the voucherPostDTO property.
     * 
     * @return possible object is {@link VoucherPostDTO }
     * 
     */
    public VoucherPostDTO getVoucherPostDTO() {
        return voucherPostDTO;
    }

    /**
     * Sets the value of the voucherPostDTO property.
     * 
     * @param value allowed object is {@link VoucherPostDTO }
     * 
     */
    public void setVoucherPostDTO(VoucherPostDTO value) {
        this.voucherPostDTO = value;
    }

}
