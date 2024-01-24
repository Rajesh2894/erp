package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.util.List;

/**
 * This class provides the facility to convert given {@link Object} into {@link JSON} format.
 * @author Pranit.Mhatre
 * @param <T> refers to which {@link Object} to be converted into {@link JSON} format.
 */
public class JQGridResponse<T extends Serializable> {
    /**
     * Current page
     */
    private int page;

    /**
     * Total pages
     */
    private int total;

    /**
     * Total number of records
     */
    private int records;

    /**
     * Contains the actual data
     */
    private List<T> rows;

    public JQGridResponse() {
    }

    public JQGridResponse(final int page, final int total, final int records, final List<T> rows) {
        super();
        this.page = page;
        this.total = total;
        this.records = records;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;

        if ((rows == null) || (rows.size() == 0)) {
            this.page = 0;
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(final int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(final int records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(final List<T> rows) {
        this.rows = rows;
    }

}
