package com.abm.mainet.common.dto;

import java.util.List;
import java.util.Set;

public class TbOrganisationResponse {
    private Set<TbOrganisationRest> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the rows
     */
    public Set<TbOrganisationRest> getRows() {
        return rows;
    }

    /**
     * @param list the rows to set
     */
    public void setRows(Set<TbOrganisationRest> list) {
        this.rows = list;
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
