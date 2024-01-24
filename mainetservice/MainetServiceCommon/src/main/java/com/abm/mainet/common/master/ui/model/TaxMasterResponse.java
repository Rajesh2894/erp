package com.abm.mainet.common.master.ui.model;

import java.io.Serializable;
import java.util.List;

import com.abm.mainet.common.master.dto.TbTaxMas;

/**
 * @author nirmal.mahanta
 *
 */
public class TaxMasterResponse implements Serializable {

    private static final long serialVersionUID = 4260409636402181797L;
    private List<TbTaxMas> rows;
    private Integer total;
    private Integer records;
    private Integer page;

    /**
     * @return the rows
     */
    public List<TbTaxMas> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(final List<TbTaxMas> rows) {
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
