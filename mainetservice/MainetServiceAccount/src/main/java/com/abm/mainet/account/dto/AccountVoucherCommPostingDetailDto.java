
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author dharmendra.chouhan
 *
 */
public class AccountVoucherCommPostingDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto;

    private Long fundId;

    private Long functionId;

    private Long pacHeadId;

    private Long sacHeadId;

    private BigDecimal voudetAmt;

    private String drcrValue;

    /**
     * @return the accountVoucherCommPostingMasterDto
     */
    public AccountVoucherCommPostingMasterDto getAccountVoucherCommPostingMasterDto() {
        return accountVoucherCommPostingMasterDto;
    }

    /**
     * @param accountVoucherCommPostingMasterDto the accountVoucherCommPostingMasterDto to set
     */
    public void setAccountVoucherCommPostingMasterDto(
            final AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto) {
        this.accountVoucherCommPostingMasterDto = accountVoucherCommPostingMasterDto;
    }

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the functionId
     */
    public Long getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId the functionId to set
     */
    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    /**
     * @return the pacHeadId
     */
    public Long getPacHeadId() {
        return pacHeadId;
    }

    /**
     * @param pacHeadId the pacHeadId to set
     */
    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    /**
     * @return the sacHeadId
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * @param sacHeadId the sacHeadId to set
     */
    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the voudetAmt
     */
    public BigDecimal getVoudetAmt() {
        return voudetAmt;
    }

    /**
     * @param voudetAmt the voudetAmt to set
     */
    public void setVoudetAmt(final BigDecimal voudetAmt) {
        this.voudetAmt = voudetAmt;
    }

    /**
     * @return the drcrValue
     */
    public String getDrcrValue() {
        return drcrValue;
    }

    /**
     * @param drcrValue the drcrValue to set
     */
    public void setDrcrValue(final String drcrValue) {
        this.drcrValue = drcrValue;
    }

}
