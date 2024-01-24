package com.abm.mainet.water.ui.model;

import java.util.List;

import com.abm.mainet.water.dto.MeterDetailsEntryDTO;

/**
 * @author prasant.sahu
 *
 */
public class MeterDetailsEntryResponse {

    private List<MeterDetailsEntryDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the rows
     */

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

    /**
     * @return the rows
     */
    public List<MeterDetailsEntryDTO> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<MeterDetailsEntryDTO> rows) {
        this.rows = rows;
    }

    /**
     * @return the rows
     */

}
