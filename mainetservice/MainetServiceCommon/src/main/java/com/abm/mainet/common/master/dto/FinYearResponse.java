package com.abm.mainet.common.master.dto;

import java.util.List;

public class FinYearResponse {

    private List<TbFinancialyear> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbFinancialyear> getRows() {
        return rows;
    }

    public void setRows(final List<TbFinancialyear> rows) {
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
