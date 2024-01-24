package com.abm.mainet.swm.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;

/**
 * @author Ajay.Kumar
 *
 */
@Component
@Scope("session")
public class VendorInspectionModel extends AbstractFormModel {
    private static final long serialVersionUID = 6584333722311736656L;
    private String saveMode;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

}
