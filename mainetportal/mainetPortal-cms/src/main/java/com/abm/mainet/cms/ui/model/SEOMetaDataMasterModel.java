package com.abm.mainet.cms.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.domain.SEOKeyWordMaster;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class SEOMetaDataMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = 5560728024318413907L;

    private SEOKeyWordMaster master = new SEOKeyWordMaster();

    public SEOKeyWordMaster getMaster() {
        return master;
    }

    public void setMaster(SEOKeyWordMaster master) {
        this.master = master;
    }

}
