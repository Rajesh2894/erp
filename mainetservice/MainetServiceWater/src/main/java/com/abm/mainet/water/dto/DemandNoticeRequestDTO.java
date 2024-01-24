package com.abm.mainet.water.dto;

/**
 * @author Lalit.Prusti
 * @since 28 Jun 2016
 */
public class DemandNoticeRequestDTO {
    private long orgid;
    private long trf1;
    private long trf2;
    private long trf3;
    private long trf4;
    private long trf5;
    private long wwz1;
    private long wwz2;
    private long wwz3;
    private long wwz4;
    private long wwz5;
    private long csz;
    private long wdn;
    private long wmn;
    private String connFrom;
    private String connTo;
    private long finalNoticeType;
    private String amountFrom;
    private String amountTo;

    public long getOrgid() {
        return orgid;
    }

    public void setOrgid(final long orgid) {
        this.orgid = orgid;
    }

    public long getTrf1() {
        return trf1;
    }

    public void setTrf1(final long trf1) {
        this.trf1 = trf1;
    }

    public long getTrf2() {
        return trf2;
    }

    public void setTrf2(final long trf2) {
        this.trf2 = trf2;
    }

    public long getTrf3() {
        return trf3;
    }

    public void setTrf3(final long trf3) {
        this.trf3 = trf3;
    }

    public long getTrf4() {
        return trf4;
    }

    public void setTrf4(final long trf4) {
        this.trf4 = trf4;
    }

    public long getTrf5() {
        return trf5;
    }

    public void setTrf5(final long trf5) {
        this.trf5 = trf5;
    }

    public long getWwz1() {
        return wwz1;
    }

    public void setWwz1(final long wwz1) {
        this.wwz1 = wwz1;
    }

    public long getWwz2() {
        return wwz2;
    }

    public void setWwz2(final long wwz2) {
        this.wwz2 = wwz2;
    }

    public long getWwz3() {
        return wwz3;
    }

    public void setWwz3(final long wwz3) {
        this.wwz3 = wwz3;
    }

    public long getWwz4() {
        return wwz4;
    }

    public void setWwz4(final long wwz4) {
        this.wwz4 = wwz4;
    }

    public long getWwz5() {
        return wwz5;
    }

    public void setWwz5(final long wwz5) {
        this.wwz5 = wwz5;
    }

    public long getCsz() {
        return csz;
    }

    public void setCsz(final long csz) {
        this.csz = csz;
    }

    public long getWdn() {
        return wdn;
    }

    public void setWdn(final long wdn) {
        this.wdn = wdn;
    }

    public long getWmn() {
        return wmn;
    }

    public void setWmn(final long wmn) {
        this.wmn = wmn;
    }

    public String getConnFrom() {
        return connFrom;
    }

    public void setConnFrom(final String connFrom) {
        this.connFrom = connFrom;
    }

    public String getConnTo() {
        return connTo;
    }

    public void setConnTo(final String connTo) {
        this.connTo = connTo;
    }

    public long getFinalNoticeType() {
        return finalNoticeType;
    }

    public void setFinalNoticeType(final long finalNoticeType) {
        this.finalNoticeType = finalNoticeType;
    }

	public String getAmountFrom() {
		return amountFrom;
	}

	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}

	public String getAmountTo() {
		return amountTo;
	}

	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}

}