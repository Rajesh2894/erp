package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.List;

public class WorkOrderGridResponse implements Serializable {

    private static final long serialVersionUID = -4616498055154889718L;
    private List<WorkOrderGridEntityList> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<WorkOrderGridEntityList> getRows() {
        return rows;
    }

    public void setRows(final List<WorkOrderGridEntityList> serviceList) {
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
