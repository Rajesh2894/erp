package com.abm.mainet.smsemail.domain;

import java.util.List;

public class SmsAndMailRespose {

    private List<SMSAndEmailInterfaceDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<SMSAndEmailInterfaceDTO> getRows() {
        return rows;
    }

    public void setRows(final List<SMSAndEmailInterfaceDTO> rows) {
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