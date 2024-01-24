package com.abm.mainet.cms.ui.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = "session")
public class AccessibilityModel extends AbstractFormModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String contrastscheme;
    private String textsize;

    public String getContrastscheme() {
        return contrastscheme;
    }

    public void setContrastscheme(final String contrastscheme) {
        this.contrastscheme = contrastscheme;
    }

    public String getTextsize() {
        return textsize;
    }

    public void setTextsize(final String textsize) {
        this.textsize = textsize;
    }

}
