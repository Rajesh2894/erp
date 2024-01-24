package com.abm.mainet.common.dto;

import java.io.Serializable;

/**
 * @author Vikrant.Thakur
 *
 */
public class EmployeeSearchDTO implements Serializable {

    private static final long serialVersionUID = -7693178510863951634L;

    private Long id;

    private String employeeName;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    public long getRowId() {
        return getId();
    }

    public long getEditFlag() {
        return getRowId();
    }

    public long getViewMode() {
        return getRowId();
    }
}