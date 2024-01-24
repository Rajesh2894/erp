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
public class EmployeeVerificationModel extends AbstractFormModel {
    private static final long serialVersionUID = -3529956184143919329L;

    @Override
    public boolean saveForm() {
        boolean status = false;
        return status;
    }

}
