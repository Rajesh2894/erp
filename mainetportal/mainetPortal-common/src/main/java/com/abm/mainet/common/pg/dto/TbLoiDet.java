
package com.abm.mainet.common.pg.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TbLoiDet implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @NotNull
    private Long loiDetId;
    @NotNull
    private Long orgid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long loiMasId;

    private Long loiChrgid;

    private BigDecimal loiAmount;

    @Size(max = 1)
    private String loiCharge;

    private BigDecimal loiPrevAmt;

    private BigDecimal loiDiffAmt;

    private Long loiPaytype;

    @NotNull
    private Long userId;

    @NotNull
    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @Size(max = 100)
    private String lgIpMac;

    @Size(max = 100)
    private String lgIpMacUpd;

    @Size(max = 100)
    private String loiDetV1;

    @Size(max = 100)
    private String loiDetV2;

    @Size(max = 100)
    private String loiDetV3;

    @Size(max = 100)
    private String loiDetV4;

    @Size(max = 100)
    private String loiDetV5;

    private Long loiDetN1;

    private Long loiDetN2;

    private Long loiDetN3;

    private Long loiDetN4;

    private Long loiDetN5;

    @Size(max = 200)
    private String loiRemarks;

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setLoiDetId(final Long loiDetId) {
        this.loiDetId = loiDetId;
    }

    public Long getLoiDetId() {
        return loiDetId;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setLoiAmount(final BigDecimal loiAmount) {
        this.loiAmount = loiAmount;
    }

    public BigDecimal getLoiAmount() {
        return loiAmount;
    }

    public void setLoiCharge(final String loiCharge) {
        this.loiCharge = loiCharge;
    }

    public String getLoiCharge() {
        return loiCharge;
    }

    public void setLoiPrevAmt(final BigDecimal loiPrevAmt) {
        this.loiPrevAmt = loiPrevAmt;
    }

    public BigDecimal getLoiPrevAmt() {
        return loiPrevAmt;
    }

    public void setLoiDiffAmt(final BigDecimal loiDiffAmt) {
        this.loiDiffAmt = loiDiffAmt;
    }

    public BigDecimal getLoiDiffAmt() {
        return loiDiffAmt;
    }

    public void setLoiPaytype(final Long loiPaytype) {
        this.loiPaytype = loiPaytype;
    }

    public Long getLoiPaytype() {
        return loiPaytype;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLoiDetV1(final String loiDetV1) {
        this.loiDetV1 = loiDetV1;
    }

    public String getLoiDetV1() {
        return loiDetV1;
    }

    public void setLoiDetV2(final String loiDetV2) {
        this.loiDetV2 = loiDetV2;
    }

    public String getLoiDetV2() {
        return loiDetV2;
    }

    public void setLoiDetV3(final String loiDetV3) {
        this.loiDetV3 = loiDetV3;
    }

    public String getLoiDetV3() {
        return loiDetV3;
    }

    public void setLoiDetV4(final String loiDetV4) {
        this.loiDetV4 = loiDetV4;
    }

    public String getLoiDetV4() {
        return loiDetV4;
    }

    public void setLoiDetV5(final String loiDetV5) {
        this.loiDetV5 = loiDetV5;
    }

    public String getLoiDetV5() {
        return loiDetV5;
    }

    public void setLoiDetN1(final Long loiDetN1) {
        this.loiDetN1 = loiDetN1;
    }

    public Long getLoiDetN1() {
        return loiDetN1;
    }

    public void setLoiDetN2(final Long loiDetN2) {
        this.loiDetN2 = loiDetN2;
    }

    public Long getLoiDetN2() {
        return loiDetN2;
    }

    public void setLoiDetN3(final Long loiDetN3) {
        this.loiDetN3 = loiDetN3;
    }

    public Long getLoiDetN3() {
        return loiDetN3;
    }

    public void setLoiDetN4(final Long loiDetN4) {
        this.loiDetN4 = loiDetN4;
    }

    public Long getLoiDetN4() {
        return loiDetN4;
    }

    public void setLoiDetN5(final Long loiDetN5) {
        this.loiDetN5 = loiDetN5;
    }

    public Long getLoiDetN5() {
        return loiDetN5;
    }

    public void setLoiRemarks(final String loiRemarks) {
        this.loiRemarks = loiRemarks;
    }

    public String getLoiRemarks() {
        return loiRemarks;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(loiDetId);
        sb.append("|");
        sb.append(loiMasId);
        sb.append("|");
        sb.append(loiChrgid);
        sb.append("|");
        sb.append(loiAmount);
        sb.append("|");
        sb.append(loiCharge);
        sb.append("|");
        sb.append(loiPrevAmt);
        sb.append("|");
        sb.append(loiDiffAmt);
        sb.append("|");
        sb.append(loiPaytype);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(loiDetV1);
        sb.append("|");
        sb.append(loiDetV2);
        sb.append("|");
        sb.append(loiDetV3);
        sb.append("|");
        sb.append(loiDetV4);
        sb.append("|");
        sb.append(loiDetV5);
        sb.append("|");
        sb.append(loiDetN1);
        sb.append("|");
        sb.append(loiDetN2);
        sb.append("|");
        sb.append(loiDetN3);
        sb.append("|");
        sb.append(loiDetN4);
        sb.append("|");
        sb.append(loiDetN5);
        sb.append("|");
        sb.append(loiRemarks);
        return sb.toString();
    }

    public Long getLoiMasId() {
        return loiMasId;
    }

    public void setLoiMasId(final Long loiMasId) {
        this.loiMasId = loiMasId;
    }

    public Long getLoiChrgid() {
        return loiChrgid;
    }

    public void setLoiChrgid(final Long loiChrgid) {
        this.loiChrgid = loiChrgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

}
