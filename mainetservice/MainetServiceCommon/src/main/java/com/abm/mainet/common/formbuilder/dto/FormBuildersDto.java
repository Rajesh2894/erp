package com.abm.mainet.common.formbuilder.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author nirmal.mahanta
 *
 */
public class FormBuildersDto implements Serializable {

    private static final long serialVersionUID = -8840697868781936391L;

    private FormBuilder scrutinyLabels;
    private List<FormBuilder> scrutinyLabelsList;

    /**
     * @return the scrutinyLabels
     */
    public FormBuilder getScrutinyLabels() {
        return scrutinyLabels;
    }

    /**
     * @param scrutinyLabels the scrutinyLabels to set
     */
    public void setScrutinyLabels(final FormBuilder scrutinyLabels) {
        this.scrutinyLabels = scrutinyLabels;
    }

    /**
     * @return the scrutinyLabelsList
     */
    public List<FormBuilder> getScrutinyLabelsList() {
        return scrutinyLabelsList;
    }

    /**
     * @param scrutinyLabelsList the scrutinyLabelsList to set
     */
    public void setScrutinyLabelsList(final List<FormBuilder> scrutinyLabelsList) {
        this.scrutinyLabelsList = scrutinyLabelsList;
    }

}
