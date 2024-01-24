package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.water.dto.MeterReadingMonthDTO;

public class WaterMeterReadingResponseDTO implements Serializable {

    private static final long serialVersionUID = -1308605935037635052L;

    private String status;

    private Long csIdn;

    private String csCcn;

    private String meterType;

    private Date mrdMrdate;

    private Long mrdMtrread;

    private Long mmMtnid;

    private List<MeterReadingMonthDTO> month = new ArrayList<>(0);

    private Long mrdCpdIdWtp;

    private Long cpdMtrstatus;

    private Long cpdGap;

    private Date pcDate;

    private Date meterInstallDate;

    private long maxMeterRead;

    private long installMeterRead;

    private String mtrNumber;

    private Long lastMtrRead;

    private String name;

    private Long userId;

    private int langId;

    private Long orgid;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public Date getMrdMrdate() {
        return mrdMrdate;
    }

    public void setMrdMrdate(final Date mrdMrdate) {
        this.mrdMrdate = mrdMrdate;
    }

    public Long getMrdMtrread() {
        return mrdMtrread;
    }

    public void setMrdMtrread(final Long mrdMtrread) {
        this.mrdMtrread = mrdMtrread;
    }

    public Long getMmMtnid() {
        return mmMtnid;
    }

    public void setMmMtnid(final Long mmMtnid) {
        this.mmMtnid = mmMtnid;
    }

    public List<MeterReadingMonthDTO> getMonth() {
        return month;
    }

    public void setMonth(final List<MeterReadingMonthDTO> month) {
        this.month = month;
    }

    public Long getMrdCpdIdWtp() {
        return mrdCpdIdWtp;
    }

    public void setMrdCpdIdWtp(final Long mrdCpdIdWtp) {
        this.mrdCpdIdWtp = mrdCpdIdWtp;
    }

    public Long getCpdMtrstatus() {
        return cpdMtrstatus;
    }

    public void setCpdMtrstatus(final Long cpdMtrstatus) {
        this.cpdMtrstatus = cpdMtrstatus;
    }

    public Long getCpdGap() {
        return cpdGap;
    }

    public void setCpdGap(final Long cpdGap) {
        this.cpdGap = cpdGap;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public String getCsCcn() {
        return csCcn;
    }

    public void setCsCcn(final String csCcn) {
        this.csCcn = csCcn;
    }

    public Date getPcDate() {
        return pcDate;
    }

    public void setPcDate(final Date pcDate) {
        this.pcDate = pcDate;
    }

    public Date getMeterInstallDate() {
        return meterInstallDate;
    }

    public void setMeterInstallDate(final Date meterInstallDate) {
        this.meterInstallDate = meterInstallDate;
    }

    public long getMaxMeterRead() {
        return maxMeterRead;
    }

    public void setMaxMeterRead(final long maxMeterRead) {
        this.maxMeterRead = maxMeterRead;
    }

    public long getInstallMeterRead() {
        return installMeterRead;
    }

    public void setInstallMeterRead(final long installMeterRead) {
        this.installMeterRead = installMeterRead;
    }

    public String getMtrNumber() {
        return mtrNumber;
    }

    public void setMtrNumber(final String mtrNumber) {
        this.mtrNumber = mtrNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getLastMtrRead() {
        return lastMtrRead;
    }

    public void setLastMtrRead(final Long lastMtrRead) {
        this.lastMtrRead = lastMtrRead;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(final String meterType) {
        this.meterType = meterType;
    }

}
