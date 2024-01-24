/**
 *
 */
package com.abm.mainet.cms.dto;

import java.io.Serializable;

/**
 * @author swapnil.shirke
 */
public class LinkMasterDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7196465651681766681L;
    private String exLink;
    private String linkRadioButton;
    private String temp;

    /**
     * @return the exLink
     */
    public String getExLink() {
        return exLink;
    }

    /**
     * @param exLink the exLink to set
     */
    public void setExLink(final String exLink) {
        this.exLink = exLink;
    }

    /**
     * @return the linkRadioButton
     */
    public String getLinkRadioButton() {
        return linkRadioButton;
    }

    /**
     * @param linkRadioButton the linkRadioButton to set
     */
    public void setLinkRadioButton(final String linkRadioButton) {
        this.linkRadioButton = linkRadioButton;
    }

    /**
     * @return the temp
     */
    public String getTemp() {
        return temp;
    }

    /**
     * @param temp the temp to set
     */
    public void setTemp(final String temp) {
        this.temp = temp;
    }

}
