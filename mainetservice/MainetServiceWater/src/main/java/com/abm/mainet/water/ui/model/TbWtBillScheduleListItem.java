/*
 * Created on 2 May 2016 ( Time 19:16:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.water.ui.model;

import com.abm.mainet.common.utility.ListItem;
import com.abm.mainet.water.dto.TbWtBillSchedule;

public class TbWtBillScheduleListItem implements ListItem {

    private final String value;
    private final String label;

    public TbWtBillScheduleListItem(final TbWtBillSchedule tbWtBillSchedule) {
        super();

        value = ""
                + tbWtBillSchedule.getCnsId()
                + "|" + tbWtBillSchedule.getOrgid();

        // TODO : Define here the attributes to be displayed as the label
        label = tbWtBillSchedule.toString();
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
