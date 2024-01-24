/**
 * 
 */
package com.abm.mainet.account.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;

/**
 * @author Anwarul.Hassan
 * @since 16-Dec-2019
 */
public class StopPaymemtReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private PaymentEntryDto paymentEntryDto = new PaymentEntryDto();
    private BankAccountMasterDto bankAccountMasterDto = new BankAccountMasterDto();
    private TbAcVendormaster vendorMaster = new TbAcVendormaster();
    private String checqueStatusCode;
    private String biilPaymentFlag;

    public BankAccountMasterDto getBankAccountMasterDto() {
        return bankAccountMasterDto;
    }

    public void setBankAccountMasterDto(BankAccountMasterDto bankAccountMasterDto) {
        this.bankAccountMasterDto = bankAccountMasterDto;
    }

    public PaymentEntryDto getPaymentEntryDto() {
        return paymentEntryDto;
    }

    public void setPaymentEntryDto(PaymentEntryDto paymentEntryDto) {
        this.paymentEntryDto = paymentEntryDto;
    }

    public TbAcVendormaster getTbAcVendormaster() {
        return vendorMaster;
    }

    public void setTbAcVendormaster(TbAcVendormaster tbAcVendormaster) {
        this.vendorMaster = tbAcVendormaster;
    }

	public String getChecqueStatusCode() {
		return checqueStatusCode;
	}

	public void setChecqueStatusCode(String checqueStatusCode) {
		this.checqueStatusCode = checqueStatusCode;
	}

	public String getBiilPaymentFlag() {
		return biilPaymentFlag;
	}

	public void setBiilPaymentFlag(String biilPaymentFlag) {
		this.biilPaymentFlag = biilPaymentFlag;
	}

    
}
