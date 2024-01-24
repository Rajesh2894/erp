package com.abm.mainet.common.master.ui.model;

import java.util.List;

import com.abm.mainet.common.master.dto.TbCustbanksMas;

public class TbCustbanksMasResponse {

    private List<TbCustbanksMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbCustbanksMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbCustbanksMas> rows) {
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
