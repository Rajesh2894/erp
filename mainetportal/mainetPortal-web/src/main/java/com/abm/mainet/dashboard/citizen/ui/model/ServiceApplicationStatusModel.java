package com.abm.mainet.dashboard.citizen.ui.model;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.cms.dto.ServiceApplicationStatusDTO;
import com.abm.mainet.common.domain.ApplicationPortalMaster;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class ServiceApplicationStatusModel extends AbstractEntryFormModel<ApplicationPortalMaster> implements Serializable {

    private static final long serialVersionUID = 1L;

    ApplicationPortalMaster details = new ApplicationPortalMaster();

    ServiceApplicationStatusDTO dto = new ServiceApplicationStatusDTO();

    public ApplicationPortalMaster getDetails() {
        return details;
    }

    public void setDetails(final ApplicationPortalMaster details) {
        this.details = details;
    }

    public ServiceApplicationStatusDTO getDto() {
        return dto;
    }

    public void setDto(final ServiceApplicationStatusDTO dto) {
        this.dto = dto;
    }

}
