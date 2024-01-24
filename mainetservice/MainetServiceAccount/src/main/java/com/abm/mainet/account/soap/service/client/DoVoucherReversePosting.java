
package com.abm.mainet.account.soap.service.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for doVoucherReversePosting complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="doVoucherReversePosting"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="voucherPostDTO" type="{http://service.soap.account.mainet.abm.com/}voucherReversePostDTO" minOccurs="0" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "doVoucherReversePosting", propOrder = {
        "voucherPostDTO"
})
public class DoVoucherReversePosting {

    @XmlElement(namespace = "http://service.soap.account.mainet.abm.com/")
    protected VoucherReversePostDTO voucherPostDTO;

    /**
     * Gets the value of the voucherPostDTO property.
     * 
     * @return possible object is {@link VoucherReversePostDTO }
     * 
     */
    public VoucherReversePostDTO getVoucherPostDTO() {
        return voucherPostDTO;
    }

    /**
     * Sets the value of the voucherPostDTO property.
     * 
     * @param value allowed object is {@link VoucherReversePostDTO }
     * 
     */
    public void setVoucherPostDTO(VoucherReversePostDTO value) {
        this.voucherPostDTO = value;
    }

}
