
package com.abm.mainet.account.soap.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.1.14 Thu Mar 15 19:41:49 IST 2018 Generated source version: 3.1.14
 */

@XmlRootElement(name = "saveReceiptResponse", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saveReceiptResponse", namespace = "http://service.soap.account.mainet.abm.com/")

public class SaveReceiptResponse {

    @XmlElement(name = "return")
    private java.lang.String _return;

    public java.lang.String getReturn() {
        return this._return;
    }

    public void setReturn(java.lang.String new_return) {
        this._return = new_return;
    }

}
