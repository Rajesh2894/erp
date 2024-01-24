package com.abm.mainet.common.master.ui.controller;

import java.util.List;

import com.abm.mainet.common.master.dto.TbCustbanks;

public class TbCustBankResponse {

    private List<TbCustbanks> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbCustbanks> getRows() {
        return rows;
    }

    public void setRows(final List<TbCustbanks> rows) {
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
