package com.abm.mainet.account.ui.model.response;

import java.util.List;

import com.abm.mainet.account.dto.DeasMasterEntryDto;

/**
 * @author prasant.sahu
 *
 */
public class AccountAdvanceAndDepositeMapEntryMasterResponse {
    private List<DeasMasterEntryDto> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(final Integer total) {
        this.total = total;
    }

    /**
     * @return the records
     */
    public Integer getRecords() {
        return records;
    }

    /**
     * @param records the records to set
     */
    public void setRecords(final Integer records) {
        this.records = records;
    }

    /**
     * @return the page
     */
    public Integer getPage() {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(final Integer page) {
        this.page = page;
    }

    /**
     * @return the rows
     */
    public List<DeasMasterEntryDto> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<DeasMasterEntryDto> rows) {
        this.rows = rows;
    }
}
