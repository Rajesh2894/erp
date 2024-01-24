/*
 * Created on 9 Dec 2015 ( Time 11:08:12 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.master.ui.model;

import com.abm.mainet.common.master.dto.DepartmentDTO;
import com.abm.mainet.common.utility.ListItem;

public class DepartmentListItem implements ListItem {

    private final String value;
    private final String label;

    public DepartmentListItem(final DepartmentDTO department) {
        super();

        value = ""
                + department.getDepid();

        // TODO : Define here the attributes to be displayed as the label
        label = department.toString();
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

}
