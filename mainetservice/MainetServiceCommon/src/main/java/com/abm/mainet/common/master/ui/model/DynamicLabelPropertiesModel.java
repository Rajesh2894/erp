package com.abm.mainet.common.master.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.smsemail.domain.SMSAndEmailInterface;

@Component
@Scope("session")
public class DynamicLabelPropertiesModel extends AbstractEntryFormModel {


	private static final long serialVersionUID = -4856600464005721629L;
	private String propertieFile;

    public String getPropertieFile() {
        return propertieFile;
    }

    public void setPropertieFile(final String propertieFile) {
        this.propertieFile = propertieFile;
    }

}
