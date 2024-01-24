package com.abm.mainet.dashboard.citizen.dto;

import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class DashBoardResponse {

    private List<CitizenDashBoardResDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<CitizenDashBoardResDTO> getRows() {
        return rows;
    }

    public void setRows(final List<CitizenDashBoardResDTO> rows) {
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
