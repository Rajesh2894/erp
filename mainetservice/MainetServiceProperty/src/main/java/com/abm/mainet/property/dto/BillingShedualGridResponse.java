/**
 * 
 */
package com.abm.mainet.property.dto;

import java.io.Serializable;

/**
 * @author hiren.poriya
 *
 */
public class BillingShedualGridResponse implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String financialYear;
    private String billingFreq;
    private String fromMonth;
    private String toMonth;
    private Long id;

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    public String getBillingFreq() {
        return billingFreq;
    }

    public void setBillingFreq(String billingFreq) {
        this.billingFreq = billingFreq;
    }

    public String getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(String fromMonth) {
        this.fromMonth = fromMonth;
    }

    public String getToMonth() {
        return toMonth;
    }

    public void setToMonth(String toMonth) {
        this.toMonth = toMonth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
