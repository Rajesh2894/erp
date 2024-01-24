
package com.abm.mainet.account.ui.model;

import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.TbAcCodingstructureMas;

/**
 * @author deepika.pimpale
 *
 */
public class ConfigurationMasterResponse {

    private List<TbAcCodingstructureMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbAcCodingstructureMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbAcCodingstructureMas> rows) {
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
