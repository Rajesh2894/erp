/**
 * 
 */
package com.abm.mainet.common.integration.dto;

/**
 * @author akshata.bhat
 *
 */
public class BillTaxDetailsResponse {

    private Long bdBilldetid;
    private String taxName;
    private Long taxId;
    private Double bdCurTaxamt;
    private Double bdCurBalTaxamt;
    private Double bdPrvBalArramt;
	public Long getBdBilldetid() {
		return bdBilldetid;
	}
	public void setBdBilldetid(Long bdBilldetid) {
		this.bdBilldetid = bdBilldetid;
	}
	public String getTaxName() {
		return taxName;
	}
	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}
	public Long getTaxId() {
		return taxId;
	}
	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}
	public Double getBdCurTaxamt() {
		return bdCurTaxamt;
	}
	public void setBdCurTaxamt(Double bdCurTaxamt) {
		this.bdCurTaxamt = bdCurTaxamt;
	}
	public Double getBdCurBalTaxamt() {
		return bdCurBalTaxamt;
	}
	public void setBdCurBalTaxamt(Double bdCurBalTaxamt) {
		this.bdCurBalTaxamt = bdCurBalTaxamt;
	}
	public Double getBdPrvBalArramt() {
		return bdPrvBalArramt;
	}
	public void setBdPrvBalArramt(Double bdPrvBalArramt) {
		this.bdPrvBalArramt = bdPrvBalArramt;
	}
}
