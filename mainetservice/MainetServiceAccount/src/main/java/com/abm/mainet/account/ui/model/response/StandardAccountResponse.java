package com.abm.mainet.account.ui.model.response;

import java.util.List;

import com.abm.mainet.account.dto.StandardAccountHeadDto;

/**
 * @author deepika.pimpale
 *
 */
public class StandardAccountResponse {

    private List<StandardAccountHeadDto> rows;
    private Integer total;
    private Integer records;
    private Integer page;

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
    public List<StandardAccountHeadDto> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<StandardAccountHeadDto> rows) {
        this.rows = rows;
    }

}
