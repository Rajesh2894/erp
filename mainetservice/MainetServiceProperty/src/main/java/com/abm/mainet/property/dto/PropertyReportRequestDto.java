package com.abm.mainet.property.dto;

import java.io.Serializable;

import java.util.Date;

public class PropertyReportRequestDto implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1246830820922435071L;

private Date mnFromdt;

private Date mnTodt;

private Long mnassward1;

private Long mnassward2;

private Long mnassward3;

private Long mnassward4;

private Long mnassward5;

private Long mnpropwise1;

private Long mnpropwise2;

private Long mnpropwise3;

private Long mnpropwise4;

private Long mnpropwise5;

private Long taxCollectorId;

private Long usageType1;

private Long usageType2;

private Long usageType3;

private String reportType;

private Long mnDefaulterCount;

private Long mnAmount;

private Long mntoAmount;

public Date getMnFromdt() {
	return mnFromdt;
}

public void setMnFromdt(Date mnFromdt) {
	this.mnFromdt = mnFromdt;
}

public Date getMnTodt() {
	return mnTodt;
}

public void setMnTodt(Date mnTodt) {
	this.mnTodt = mnTodt;
}

public Long getMnassward1() {
	return mnassward1;
}

public void setMnassward1(Long mnassward1) {
	this.mnassward1 = mnassward1;
}

public Long getMnassward2() {
	return mnassward2;
}

public void setMnassward2(Long mnassward2) {
	this.mnassward2 = mnassward2;
}

public Long getMnassward3() {
	return mnassward3;
}

public void setMnassward3(Long mnassward3) {
	this.mnassward3 = mnassward3;
}

public Long getMnassward4() {
	return mnassward4;
}

public void setMnassward4(Long mnassward4) {
	this.mnassward4 = mnassward4;
}

public Long getMnassward5() {
	return mnassward5;
}

public void setMnassward5(Long mnassward5) {
	this.mnassward5 = mnassward5;
}

public Long getMnpropwise1() {
	return mnpropwise1;
}

public void setMnpropwise1(Long mnpropwise1) {
	this.mnpropwise1 = mnpropwise1;
}

public Long getMnpropwise2() {
	return mnpropwise2;
}

public void setMnpropwise2(Long mnpropwise2) {
	this.mnpropwise2 = mnpropwise2;
}

public Long getMnpropwise3() {
	return mnpropwise3;
}

public void setMnpropwise3(Long mnpropwise3) {
	this.mnpropwise3 = mnpropwise3;
}

public Long getMnpropwise4() {
	return mnpropwise4;
}

public void setMnpropwise4(Long mnpropwise4) {
	this.mnpropwise4 = mnpropwise4;
}

public Long getMnpropwise5() {
	return mnpropwise5;
}

public void setMnpropwise5(Long mnpropwise5) {
	this.mnpropwise5 = mnpropwise5;
}

public Long getTaxCollectorId() {
	return taxCollectorId;
}

public void setTaxCollectorId(Long taxCollectorId) {
	this.taxCollectorId = taxCollectorId;
}

public Long getUsageType1() {
	return usageType1;
}

public void setUsageType1(Long usageType1) {
	this.usageType1 = usageType1;
}

public Long getUsageType2() {
	return usageType2;
}

public void setUsageType2(Long usageType2) {
	this.usageType2 = usageType2;
}

public Long getUsageType3() {
	return usageType3;
}

public void setUsageType3(Long usageType3) {
	this.usageType3 = usageType3;
}

public String getReportType() {
	return reportType;
}

public void setReportType(String reportType) {
	this.reportType = reportType;
}

public Long getMnDefaulterCount() {
	return mnDefaulterCount;
}

public void setMnDefaulterCount(Long mnDefaulterCount) {
	this.mnDefaulterCount = mnDefaulterCount;
}

public Long getMnAmount() {
	return mnAmount;
}

public void setMnAmount(Long mnAmount) {
	this.mnAmount = mnAmount;
}

public Long getMntoAmount() {
	return mntoAmount;
}

public void setMntoAmount(Long mntoAmount) {
	this.mntoAmount = mntoAmount;
}

}
