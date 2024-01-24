package com.abm.mainet.common.master.ui.model;

import java.util.List;

import com.abm.mainet.common.master.dto.TbComparentMas;

public class TbComparentMasResponse {

    private List<TbComparentMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbComparentMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbComparentMas> rows) {
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
