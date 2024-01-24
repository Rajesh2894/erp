package com.abm.mainet.cms.dao;

import com.abm.mainet.cms.domain.EIPAboutUs;
import com.abm.mainet.cms.domain.EIPAboutUsHistory;
import com.abm.mainet.common.domain.Organisation;

public interface IEIPAboutUsDAO {

    public abstract EIPAboutUs getAboutUs(Organisation organisation, String isDeleted);

    public abstract EIPAboutUsHistory getGuestAboutUs(Organisation organisation, String isDeleted);

    public abstract void saveOrUpdateAboutUs(EIPAboutUs aboutUs, String chekkerflag);

}