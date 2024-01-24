
package com.abm.mainet.account.dto;

/**
 * @author prasant.sahu
 *
 */
public class DraweeBankDetailsBean {
    private Long bankId;
    private Long bankCode;
    private String bankName;
    private String checkBox;

    /**
     * @return the bankId
     */
    public Long getBankId() {
        return bankId;
    }

    /**
     * @param bankId the bankId to set
     */
    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the bankCode
     */
    public Long getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(final Long bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the checkBox
     */
    public String getCheckBox() {
        return checkBox;
    }

    /**
     * @param checkBox the checkBox to set
     */
    public void setCheckBox(final String checkBox) {
        this.checkBox = checkBox;
    }

}
