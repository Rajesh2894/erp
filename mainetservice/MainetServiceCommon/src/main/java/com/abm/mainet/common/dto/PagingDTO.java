package com.abm.mainet.common.dto;

public class PagingDTO {

    private int page;
    private int rows;
    private String sidx;
    private String sord;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(final String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(final String sord) {
        this.sord = sord;
    }

}
