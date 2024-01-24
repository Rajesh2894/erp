package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountChequeDishonourDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long id;

    @NotNull
    private Long chequeDishonourId;

    private Long receiptModeId;

    private int index;

    private String hasError;

    private String alreadyExists;

    private String serchType;

    private String number;

    private String date;

    private String amount;

    private String bankAccount;

    private Long chequeddno;

    private String chequedddate;

    private String remarks;

    private String flag;

    private String dishonourAmount;

    private String dishonourDate;

    private String dishonourIds;

    private Long orgid;

    private Long userId;

    private int langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    private List<AccountChequeDishonourDTO> chequeDishonourDtoList = new ArrayList<>();

    public Long getChequeDishonourId() {
        return chequeDishonourId;
    }

    public void setChequeDishonourId(final Long chequeDishonourId) {
        this.chequeDishonourId = chequeDishonourId;
    }

    public String getSerchType() {
        return serchType;
    }

    public void setSerchType(final String serchType) {
        this.serchType = serchType;
    }

    public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(final String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Long getChequeddno() {
        return chequeddno;
    }

    public void setChequeddno(final Long chequeddno) {
        this.chequeddno = chequeddno;
    }

    public String getChequedddate() {
        return chequedddate;
    }

    public void setChequedddate(final String chequedddate) {
        this.chequedddate = chequedddate;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public List<AccountChequeDishonourDTO> getChequeDishonourDtoList() {
        return chequeDishonourDtoList;
    }

    public void setChequeDishonourDtoList(final List<AccountChequeDishonourDTO> chequeDishonourDtoList) {
        this.chequeDishonourDtoList = chequeDishonourDtoList;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public Long getReceiptModeId() {
        return receiptModeId;
    }

    public void setReceiptModeId(final Long receiptModeId) {
        this.receiptModeId = receiptModeId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(final String flag) {
        this.flag = flag;
    }

    public String getDishonourAmount() {
        return dishonourAmount;
    }

    public void setDishonourAmount(final String dishonourAmount) {
        this.dishonourAmount = dishonourAmount;
    }

    public String getDishonourDate() {
        return dishonourDate;
    }

    public void setDishonourDate(final String dishonourDate) {
        this.dishonourDate = dishonourDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getDishonourIds() {
        return dishonourIds;
    }

    public void setDishonourIds(final String dishonourIds) {
        this.dishonourIds = dishonourIds;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

}
