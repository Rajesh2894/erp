package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.dto.TbApprejMas;

public class TbApprejMstResponse implements Serializable {

    
    private static final long serialVersionUID = -4616498055154889718L;
    private List<TbApprejMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbApprejMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbApprejMas> serviceList) {
        rows = serviceList;
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
