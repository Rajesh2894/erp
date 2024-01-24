package com.abm.mainet.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.IDepartmentLocationDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.Organisation;

@Service
public class DepartmentLocationService implements IDepartmentLocationService {
    
    private static final long serialVersionUID = 549308518272830616L;

    @Autowired
    private IDepartmentLocationDAO departmentLocationDAO;

    @Override
    @Transactional
    public LocationMasEntity saveDepartmentLocation(final Organisation organisation, final Employee employee,
            final LocationMasEntity departmentLocation) {
        departmentLocation.setOrganisation(organisation);
        return departmentLocationDAO.saveOrUpdate(departmentLocation);

    }

    @Override
    @Transactional(readOnly = true)
    public LocationMasEntity getDepartmentLocationForRegistraion(
            final Organisation organisation, final String isDeleted) {

        return departmentLocationDAO.getDepartmentLocationForRegistraion(organisation, isDeleted);
    }

}
