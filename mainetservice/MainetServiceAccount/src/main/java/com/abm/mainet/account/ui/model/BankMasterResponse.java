
package com.abm.mainet.account.ui.model;

import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;

/**
 * @author deepika.pimpale
 *
 */
public class BankMasterResponse {

    private List<BankAccountMasterDto> rows;
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
    public List<BankAccountMasterDto> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<BankAccountMasterDto> rows) {
        this.rows = rows;
    }

}
