package com.abm.mainet.common.dao;

import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;

public interface IDepartmentLocationDAO {

    public abstract LocationMasEntity saveOrUpdate(LocationMasEntity location);

    public abstract LocationMasEntity getDepartmentLocationForRegistraion(Organisation organisation, String isDeleted);
}