package com.abm.mainet.label.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;

@Component
@Scope("session")
public class DynamicLabelPropertiesModel extends AbstractEntryFormModel<Employee> {

    private static final long serialVersionUID = 7342867463588917379L;

    private String propertieFile;

    public String getPropertieFile() {
        return propertieFile;
    }

    public void setPropertieFile(final String propertieFile) {
        this.propertieFile = propertieFile;
    }

}
