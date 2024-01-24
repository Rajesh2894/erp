package com.abm.mainet.common.master.ui.model;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.master.dto.TbServicesMst;

public class TbServicesMstResponse implements Serializable {

    private static final long serialVersionUID = -4616498055154889718L;
    private List<TbServicesMst> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbServicesMst> getRows() {
        return rows;
    }

    public void setRows(final List<TbServicesMst> rows) {
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
