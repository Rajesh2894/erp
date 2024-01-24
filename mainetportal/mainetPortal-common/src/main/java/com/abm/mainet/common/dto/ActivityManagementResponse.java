package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
@JsonAutoDetect
@JsonDeserialize
public class ActivityManagementResponse implements Serializable {

    private static final long serialVersionUID = 6452956627308381202L;
    private List<ActivityManagementDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    public List<ActivityManagementDTO> getRows() {
        return rows;
    }

    public void setRows(final List<ActivityManagementDTO> rows) {
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
