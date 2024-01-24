package com.abm.mainet.landEstate.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class AccountIntegrationModel extends AbstractFormModel {

    private static final long serialVersionUID = -8636288563398274460L;

    @Override
    public boolean saveForm() {

        boolean status = false;
        // Ask to SAMADHAN sir
        // refer ExecuteChangeOfOwner.html
        return status;
    }

}
