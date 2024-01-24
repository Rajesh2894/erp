package com.abm.mainet.account.ui.model.response;

import java.util.List;

import com.abm.mainet.account.dto.TbAcChequebookleafMas;

public class TbAcChequebookleafMasResponse {
    private List<TbAcChequebookleafMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbAcChequebookleafMas> getRows() {
        return rows;
    }

    public void setRows(final List<TbAcChequebookleafMas> rows) {
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
