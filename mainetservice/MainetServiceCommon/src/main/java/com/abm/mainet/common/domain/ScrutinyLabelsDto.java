package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author nirmal.mahanta
 *
 */
public class ScrutinyLabelsDto implements Serializable {

    private static final long serialVersionUID = -8840697868781936391L;

    private TbScrutinyLabels scrutinyLabels;
    private List<TbScrutinyLabels> scrutinyLabelsList;

    /**
     * @return the scrutinyLabels
     */
    public TbScrutinyLabels getScrutinyLabels() {
        return scrutinyLabels;
    }

    /**
     * @param scrutinyLabels the scrutinyLabels to set
     */
    public void setScrutinyLabels(final TbScrutinyLabels scrutinyLabels) {
        this.scrutinyLabels = scrutinyLabels;
    }

    /**
     * @return the scrutinyLabelsList
     */
    public List<TbScrutinyLabels> getScrutinyLabelsList() {
        return scrutinyLabelsList;
    }

    /**
     * @param scrutinyLabelsList the scrutinyLabelsList to set
     */
    public void setScrutinyLabelsList(final List<TbScrutinyLabels> scrutinyLabelsList) {
        this.scrutinyLabelsList = scrutinyLabelsList;
    }

}
