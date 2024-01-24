package com.abm.mainet.cms.ui.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.cms.service.IAdminHomeService;
import com.abm.mainet.cms.ui.validator.AdminEIPHomeValidator;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;

/**
 * @author pranit.mhatre
 */
@Component
@Scope("session")
public class AdminHomeModel extends AbstractEntryFormModel<EIPHome> {
    private static final long serialVersionUID = 8187574498874230308L;
    @Autowired
    private IAdminHomeService service;

    public IAdminHomeService getService() {
        return service;
    }

    @Override
    public boolean saveOrUpdateForm() {
        this.validateBean(getEntity(), AdminEIPHomeValidator.class);

        if (hasValidationErrors()) {
            return false;
        }

        service.saveObject(getEntity());

        return true;
    }

}
