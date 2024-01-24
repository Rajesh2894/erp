
package com.abm.mainet.common.formbuilder.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author nirmal.mahanta
 *
 */
public class FormBuilderValueResponse implements Serializable {

    private static final long serialVersionUID = -7347295690502701910L;

    private List<FormBuilderValueDTO> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the rows
     */
    public List<FormBuilderValueDTO> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<FormBuilderValueDTO> rows) {
        this.rows = rows;
    }

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
}
