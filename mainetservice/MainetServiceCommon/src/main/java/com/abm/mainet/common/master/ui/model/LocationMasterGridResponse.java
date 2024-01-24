package com.abm.mainet.common.master.ui.model;

import java.io.Serializable;

import java.util.List;

import com.abm.mainet.common.master.dto.TbLocationMas;

public class LocationMasterGridResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<TbLocationMas> rows;

    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbLocationMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbLocationMas> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(final Integer total) {
        this.total = total;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(final Integer records) {
        this.records = records;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(final Integer page) {
        this.page = page;
    }

}
