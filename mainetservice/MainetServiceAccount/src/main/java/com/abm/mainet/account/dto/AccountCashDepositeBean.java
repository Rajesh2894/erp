
package com.abm.mainet.account.dto;

/**
 * @author prasant.sahu
 *
 */
public class AccountCashDepositeBean {

    private Long tbComparamDet;

    private Long denomination;

    private String denomDesc;
    private String denCode;
    /**
     * @return the tbComparamDet
     */
    public Long getTbComparamDet() {
        return tbComparamDet;
    }

    /**
     * @param tbComparamDet the tbComparamDet to set
     */
    public void setTbComparamDet(final Long tbComparamDet) {
        this.tbComparamDet = tbComparamDet;
    }

    /**
     * @return the denomination
     */
    public Long getDenomination() {
        return denomination;
    }

    /**
     * @param denomination the denomination to set
     */
    public void setDenomination(final Long denomination) {
        this.denomination = denomination;
    }

    /**
     * @return the denomDesc
     */
    public String getDenomDesc() {
        return denomDesc;
    }

    /**
     * @param denomDesc the denomDesc to set
     */
    public void setDenomDesc(final String denomDesc) {
        this.denomDesc = denomDesc;
    }

	public String getDenCode() {
		return denCode;
	}

	public void setDenCode(String denCode) {
		this.denCode = denCode;
	}

}
