package com.abm.mainet.common.integration.payment.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "TB_ONL_EGRASS_PAY_DETAILS")
public class EgrassPaymentENtity {
    /**
    *
    */
   private static final long serialVersionUID = 5380776844395809110L;

   @Id
   @GenericGenerator(name = "generator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
   @GeneratedValue(generator = "generator")
   @Column(name = "PAY_ID", precision = 12, scale = 0, nullable = false)
   private long payId;

   @Column(name = "REFERENCE_ID", precision = 16, scale = 0, nullable = true)
   private String referenceId;

   @Column(name = "CHEQUE_DDDATE", nullable = true)
   private Date chequeDDDate;

   @Column(name = "CHEQUE_DDNO", precision = 12, scale = 0, nullable = true)
   private Long chequeDDNo;

   @Column(name = "PAY_MODE", length = 20, nullable = true)
   private Long paymode;

   @Column(name = "PAY_AGGR", length = 20, nullable = true)
   private Long aggrigator;

   @Column(name = "REMARKS", length = 100, nullable = true)
   private String remarks;

   @Column(name = "PAY_BANK", length = 100, nullable = true)
   private String payBank;

   @Column(name = "SEND_AMOUNT", precision = 2, scale = 0, nullable = true)
   private BigDecimal sendAmount;
   @Column(name = "TRNAN_CM_ID", nullable = true)
   private Long trnsCmId;

   @Column(name = "SEND_FIRSTNAME", length = 500, nullable = true)
   private String payeeName;

   @JsonIgnore
   @Column(name = "ORGID", nullable = false)
   private Long orgId;

   @JsonIgnore
   @Column(name = "CREATED_BY", nullable = false, updatable = false)
   private Long userId;

   @Column(name = "CREATED_DATE", nullable = false)
   private Date lmodDate;

   @JsonIgnore
   @Column(name = "UPDATED_BY", nullable = true)
   private Long updatedBy;

   @Column(name = "UPDATED_DATE", nullable = true)
   private Date updatedDate;

   @Column(name = "LG_IP_MAC", length = 100, nullable = true)
   private String lgIpMac;

   @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
   private String lgIpMacUpd;
   
   @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
   private Integer langId;
 

   
	public long getPayId() {
	return payId;
}



public void setPayId(long payId) {
	this.payId = payId;
}



public String getReferenceId() {
	return referenceId;
}



public void setReferenceId(String referenceId) {
	this.referenceId = referenceId;
}



public Date getChequeDDDate() {
	return chequeDDDate;
}



public void setChequeDDDate(Date chequeDDDate) {
	this.chequeDDDate = chequeDDDate;
}



public Long getChequeDDNo() {
	return chequeDDNo;
}



public void setChequeDDNo(Long chequeDDNo) {
	this.chequeDDNo = chequeDDNo;
}



public Long getPaymode() {
	return paymode;
}



public void setPaymode(Long paymode) {
	this.paymode = paymode;
}



public Long getAggrigator() {
	return aggrigator;
}



public void setAggrigator(Long aggrigator) {
	this.aggrigator = aggrigator;
}



public String getRemarks() {
	return remarks;
}



public void setRemarks(String remarks) {
	this.remarks = remarks;
}



public String getPayBank() {
	return payBank;
}



public void setPayBank(String payBank) {
	this.payBank = payBank;
}



public BigDecimal getSendAmount() {
	return sendAmount;
}



public void setSendAmount(BigDecimal sendAmount) {
	this.sendAmount = sendAmount;
}



public Long getTrnsCmId() {
	return trnsCmId;
}



public void setTrnsCmId(Long trnsCmId) {
	this.trnsCmId = trnsCmId;
}



public String getPayeeName() {
	return payeeName;
}



public void setPayeeName(String payeeName) {
	this.payeeName = payeeName;
}



public Long getOrgId() {
	return orgId;
}



public void setOrgId(Long orgId) {
	this.orgId = orgId;
}



public Long getUserId() {
	return userId;
}



public void setUserId(Long userId) {
	this.userId = userId;
}



public Date getLmodDate() {
	return lmodDate;
}



public void setLmodDate(Date lmodDate) {
	this.lmodDate = lmodDate;
}



public Long getUpdatedBy() {
	return updatedBy;
}



public void setUpdatedBy(Long updatedBy) {
	this.updatedBy = updatedBy;
}



public Date getUpdatedDate() {
	return updatedDate;
}



public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
}



public String getLgIpMac() {
	return lgIpMac;
}



public void setLgIpMac(String lgIpMac) {
	this.lgIpMac = lgIpMac;
}



public String getLgIpMacUpd() {
	return lgIpMacUpd;
}



public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
}



public Integer getLangId() {
	return langId;
}



public void setLangId(Integer langId) {
	this.langId = langId;
}



	public String[] getPkValues() {
       return new String[] { "COM", "TB_ONL_EGRASS_PAY_DETAILS", "PAY_ID" };
   }

}
