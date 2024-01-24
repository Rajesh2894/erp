package com.abm.mainet.account.ui.model.response;

import java.util.List;

import com.abm.mainet.account.dto.TbAcPayToBank;

public class TbAcPayToBankResponse {
    private List<TbAcPayToBank> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<TbAcPayToBank> getRows() {
        return rows;
    }

    public void setRows(final List<TbAcPayToBank> chList) {
        rows = chList;
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
