package com.abm.mainet.cms.ui.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.PublicNotices;
import com.abm.mainet.cms.service.IAdminPublicNoticesService;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;

/**
 * @author swapnil.shirke
 *
 */
@Component
@Scope("session")
public class CitizenPublicNoticesFormModel extends AbstractEntryFormModel<PublicNotices> {

    private static final long serialVersionUID = -1523223864151844893L;
    @Autowired
    private IAdminPublicNoticesService iAdminPublicNoticesService;

    @Override
    public void editForm(final long rowId) {
        setEntity(iAdminPublicNoticesService.getPublicNotices(rowId));
    }
}
