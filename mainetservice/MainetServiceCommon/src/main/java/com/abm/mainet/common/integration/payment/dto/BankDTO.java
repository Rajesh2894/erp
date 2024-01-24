/**
 *
 */
package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;

/**
 * @author umashanker.kanaujiya 23-Feb-20152:34:56 pm TODO 2015
 */
public class BankDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long bankId;
    private String cbbankname;

    /**
     * @return the bankId
     */
    public long getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(final long bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the cbbankname
     */
    public String getCbbankname() {
        return cbbankname;
    }

    /**
     * @param cbbankname the cbbankname to set
     */
    public void setCbbankname(final String cbbankname) {
        this.cbbankname = cbbankname;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BankDTO [bankId=" + bankId + ", cbbankname=" + cbbankname + "]";
    }

}
