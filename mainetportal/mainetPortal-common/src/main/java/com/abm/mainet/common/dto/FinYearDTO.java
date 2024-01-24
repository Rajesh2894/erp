package com.abm.mainet.common.dto;

import java.io.Serializable;

public class FinYearDTO implements Serializable {

    private static final long serialVersionUID = 7039849090990366845L;
    private Long id;
    private String text;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(final String text) {
        this.text = text;
    }

}
