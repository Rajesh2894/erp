package com.abm.mainet.account.rest.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author vishwanath.s
 *
 */
public class VendorBillApprovalExtDTO  implements Serializable{

	
/** * 
	 */
private static final long serialVersionUID = -5582713107472046524L;

private String ulbCode;
private String billType;
private String billNo;
private String departmentName;
private String vendorName;
private String filed;
private String fundName;
private String narration;
private String invoiceAmount;
private String billEntryDate;
private Long createdBy;
private String createdDate;
private String financialYear;
private String expenditureExistsFlag;
private String taxableAmount;  
private String sanctionedAmount;
private String billAmount;
private String disallowedAmount;
private String totalSanctionedAmount;
private String netPayables;
private String lIpMac;
private String referenceId;
private String checkSum;
private List<VendorBillExpDetailExtDTO> expDetListDto;
private List<VendorBillDedDetailExtDTO> dedDetListDto;


public String getUlbCode() {
	return ulbCode;
}
public void setUlbCode(String ulbCode) {
	this.ulbCode = ulbCode;
}
public String getBillType() {
	return billType;
}
public void setBillType(String billType) {
	this.billType = billType;
}
public String getBillNo() {
	return billNo;
}
public void setBillNo(String billNo) {
	this.billNo = billNo;
}
public String getDepartmentName() {
	return departmentName;
}
public void setDepartmentName(String departmentName) {
	this.departmentName = departmentName;
}
public String getVendorName() {
	return vendorName;
}
public void setVendorName(String vendorName) {
	this.vendorName = vendorName;
}
public String getFiled() {
	return filed;
}
public void setFiled(String filed) {
	this.filed = filed;
}
public String getNarration() {
	return narration;
}
public void setNarration(String narration) {
	this.narration = narration;
}
public String getInvoiceAmount() {
	return invoiceAmount;
}
public void setInvoiceAmount(String invoiceAmount) {
	this.invoiceAmount = invoiceAmount;
}
public String getBillEntryDate() {
	return billEntryDate;
}
public void setBillEntryDate(String billEntryDate) {
	this.billEntryDate = billEntryDate;
}
public Long getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
}
public String getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}
public String getFinancialYear() {
	return financialYear;
}
public void setFinancialYear(String financialYear) {
	this.financialYear = financialYear;
}
public List<VendorBillExpDetailExtDTO> getExpDetListDto() {
	return expDetListDto;
}
public void setExpDetListDto(List<VendorBillExpDetailExtDTO> expDetListDto) {
	this.expDetListDto = expDetListDto;
}
public List<VendorBillDedDetailExtDTO> getDedDetListDto() {
	return dedDetListDto;
}
public void setDedDetListDto(List<VendorBillDedDetailExtDTO> dedDetListDto) {
	this.dedDetListDto = dedDetListDto;
}
public String getExpenditureExistsFlag() {
	return expenditureExistsFlag;
}
public void setExpenditureExistsFlag(String expenditureExistsFlag) {
	this.expenditureExistsFlag = expenditureExistsFlag;
}
public String getTaxableAmount() {
	return taxableAmount;
}
public String getSanctionedAmount() {
	return sanctionedAmount;
}
public String getBillAmount() {
	return billAmount;
}
public void setTaxableAmount(String taxableAmount) {
	this.taxableAmount = taxableAmount;
}
public void setSanctionedAmount(String sanctionedAmount) {
	this.sanctionedAmount = sanctionedAmount;
}
public void setBillAmount(String billAmount) {
	this.billAmount = billAmount;
}
public String getDisallowedAmount() {
	return disallowedAmount;
}
public String getTotalSanctionedAmount() {
	return totalSanctionedAmount;
}
public String getNetPayables() {
	return netPayables;
}
public void setDisallowedAmount(String disallowedAmount) {
	this.disallowedAmount = disallowedAmount;
}
public void setTotalSanctionedAmount(String totalSanctionedAmount) {
	this.totalSanctionedAmount = totalSanctionedAmount;
}
public void setNetPayables(String netPayables) {
	this.netPayables = netPayables;
}
public String getlIpMac() {
	return lIpMac;
}
public void setlIpMac(String lIpMac) {
	this.lIpMac = lIpMac;
}
public String getReferenceId() {
	return referenceId;
}
public void setReferenceId(String referenceId) {
	this.referenceId = referenceId;
}
public String getCheckSum() {
	return checkSum;
}
public void setCheckSum(String checkSum) {
	this.checkSum = checkSum;
}
public String getFundName() {
	return fundName;
}
public void setFundName(String fundName) {
	this.fundName = fundName;
}

}
