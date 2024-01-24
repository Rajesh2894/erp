package com.abm.mainet.property.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_as_try")
public class TbAsTryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "TRY_ID")
    private Long tryId;

    @Column(name = "TRY_DIS_CODE")
    private String tryDisCode;

    @Column(name = "TRY_DIS_NAME")
    private String tryDisName;

    @Column(name = "TRY_HALKA_CODE")
    private String tryHalkaCode;

    @Column(name = "TRY_HALKA_NAME")
    private String tryHalkaName;

    @Column(name = "TRY_PROP_TYPE")
    private String tryPropType;

    @Column(name = "TRY_RECORDCODE")
    private String tryRecordcode;

    @Column(name = "TRY_RI_CODE")
    private String tryRiCode;

    @Column(name = "TRY_RI_NAME")
    private String tryRiName;

    @Column(name = "TRY_SHEET_ID")
    private String trySheetId;

    @Column(name = "TRY_SHEET_NO")
    private String trySheetNo;

    @Column(name = "TRY_TEHSIL_CODE")
    private String tryTehsilCode;

    @Column(name = "TRY_TEHSIL_NAME")
    private String tryTehsilName;

    @Column(name = "TRY_VILL_CODE")
    private String tryVillCode;

    @Column(name = "TRY_VILL_NAME")
    private String tryVillName;

    @Column(name = "TRY_WARD_CODE")
    private String tryWardCode;

    @Column(name = "TRY_WARD_NAME")
    private String tryWardName;
    
    @Column(name = "TRY_VSRNO")
    private String tryVsrNo;

    public TbAsTryEntity() {
    }

    public Long getTryId() {
        return this.tryId;
    }

    public void setTryId(Long tryId) {
        this.tryId = tryId;
    }

    public String getTryDisCode() {
        return this.tryDisCode;
    }

    public void setTryDisCode(String tryDisCode) {
        this.tryDisCode = tryDisCode;
    }

    public String getTryDisName() {
        return this.tryDisName;
    }

    public void setTryDisName(String tryDisName) {
        this.tryDisName = tryDisName;
    }

    public String getTryHalkaCode() {
        return this.tryHalkaCode;
    }

    public void setTryHalkaCode(String tryHalkaCode) {
        this.tryHalkaCode = tryHalkaCode;
    }

    public String getTryHalkaName() {
        return this.tryHalkaName;
    }

    public void setTryHalkaName(String tryHalkaName) {
        this.tryHalkaName = tryHalkaName;
    }

    public String getTryPropType() {
        return this.tryPropType;
    }

    public void setTryPropType(String tryPropType) {
        this.tryPropType = tryPropType;
    }

    public String getTryRecordcode() {
        return this.tryRecordcode;
    }

    public void setTryRecordcode(String tryRecordcode) {
        this.tryRecordcode = tryRecordcode;
    }

    public String getTryRiCode() {
        return this.tryRiCode;
    }

    public void setTryRiCode(String tryRiCode) {
        this.tryRiCode = tryRiCode;
    }

    public String getTryRiName() {
        return this.tryRiName;
    }

    public void setTryRiName(String tryRiName) {
        this.tryRiName = tryRiName;
    }

    public String getTrySheetId() {
        return this.trySheetId;
    }

    public void setTrySheetId(String trySheetId) {
        this.trySheetId = trySheetId;
    }

    public String getTrySheetNo() {
        return this.trySheetNo;
    }

    public void setTrySheetNo(String trySheetNo) {
        this.trySheetNo = trySheetNo;
    }

    public String getTryTehsilCode() {
        return this.tryTehsilCode;
    }

    public void setTryTehsilCode(String tryTehsilCode) {
        this.tryTehsilCode = tryTehsilCode;
    }

    public String getTryTehsilName() {
        return this.tryTehsilName;
    }

    public void setTryTehsilName(String tryTehsilName) {
        this.tryTehsilName = tryTehsilName;
    }

    public String getTryVillCode() {
        return this.tryVillCode;
    }

    public void setTryVillCode(String tryVillCode) {
        this.tryVillCode = tryVillCode;
    }

    public String getTryVillName() {
        return this.tryVillName;
    }

    public void setTryVillName(String tryVillName) {
        this.tryVillName = tryVillName;
    }

    public String getTryWardCode() {
        return this.tryWardCode;
    }

    public void setTryWardCode(String tryWardCode) {
        this.tryWardCode = tryWardCode;
    }

    public String getTryWardName() {
        return this.tryWardName;
    }

    public void setTryWardName(String tryWardName) {
        this.tryWardName = tryWardName;
    }

	public String getTryVsrNo() {
		return tryVsrNo;
	}

	public void setTryVsrNo(String tryVsrNo) {
		this.tryVsrNo = tryVsrNo;
	}

}