package com.abm.mainet.common.service;

import java.io.Serializable;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;

public interface IDepartmentLocationService extends Serializable {
    public LocationMasEntity saveDepartmentLocation(Organisation organisation, Employee employee,
            LocationMasEntity departmentLocation);

    public LocationMasEntity getDepartmentLocationForRegistraion(Organisation organisation, String isDeleted);
}
