package com.abm.mainet.common.dto;

import java.io.Serializable;

public class PropertyFiles implements Serializable {

    private static final long serialVersionUID = 5699124166874669089L;

    public PropertyFiles() {
    }

    public PropertyFiles(final String name, final String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    private String name;
    private String value;
    private String propfileName;

    public StringBuilder getEditTemplate() {
        final StringBuilder datastring = new StringBuilder();
        datastring.append(
                "<a href='javascript:void(0);' onclick=\"_openChildForm('" + getName() + "','" + getPropfileName() + "')\">");
        datastring.append("<img src='css/images/edit.png' width='20px' alt='View Details' title='View Details' />");
        datastring.append("</a>");

        return datastring;
    }

    public String getPropfileName() {
        return propfileName;
    }

    public void setPropfileName(final String propfileName) {
        this.propfileName = propfileName;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
