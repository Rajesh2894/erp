package com.abm.mainet.common.utility;

import java.text.DateFormat;

import org.springframework.beans.propertyeditors.CustomDateEditor;

import com.abm.mainet.common.ui.validator.DateValidator;

public class DateEditor extends CustomDateEditor {
    public DateEditor(final DateFormat dateFormat, final boolean allowEmpty) {
        super(dateFormat, allowEmpty);
    }

    public DateEditor(final DateFormat dateFormat, final boolean allowEmpty, final int exactDateLength) {
        super(dateFormat, allowEmpty, exactDateLength);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.beans.propertyeditors.CustomDateEditor#setAsText(java .lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.trim().isEmpty()) {
            setValue(null);
        } else {
            if (text.trim().length() <= 10) {
                text = text + " 00:00 AM";
            }

            final boolean valid = new DateValidator().isValidDate(text);

            if (valid) {

                super.setAsText(text);
            } else {
                setValue(null);
            }
        }
    }

}
