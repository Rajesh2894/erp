package com.abm.mainet.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dao.IDepartmentDAO;
import com.abm.mainet.common.dao.IDepartmentOrganisationMappingDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;

/**
 * @author Pranit.Mhatre
 * @since 05 February, 2014
 */
@Service
public class DepartmentService implements IDepartmentService {
    @Autowired
    private IDepartmentDAO departmentDAO;

    @Autowired
    private IDepartmentOrganisationMappingDAO mappingDAO;

    @Override
    @Transactional
    public List<Department> getDepartments(final String activeStatus) {
        return departmentDAO.getAllDepartment(activeStatus);
    }

    @Override
    @Transactional
    public List<DeptOrgMap> getMappingWithOragnisation(final String activeStatus) {
        return mappingDAO.getaMapping(activeStatus);
    }

    @Override
    @Transactional
    public Department create(Department department) {
        return departmentDAO.createDepartment(department);
    }

    @Override
    @Transactional
    public Department getDepartment(final String dpDeptcode, final String status) {
        return departmentDAO.getDepartment(dpDeptcode, status);
    }

    @Override
    @Transactional
    public Department getDeptName(final long deptId, final String status) {
        return departmentDAO.getDepartment(deptId, status);
    }

}
