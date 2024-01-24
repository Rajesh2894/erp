package com.abm.mainet.common.workflow.dto;

import java.util.List;

public class EventMasterResponse {

    private List<EventDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the rows
     */
    public List<EventDTO> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<EventDTO> rows) {
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
