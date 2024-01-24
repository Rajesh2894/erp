package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;

public interface IDepartmentDAO {

    /**
     * To get all {@link Department} object for given status.
     * @param activeStatus the {@link String} containing status.
     * @return {@link List} of {@link Department} objects.
     */
    public abstract List<Department> getAllDepartment(String activeStatus);

    /**
     * To get {@link Designation} by 'dsgName' designation name.
     * @param organisation {@link Organisation}
     * @param dpDeptcode is Department CODE.
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Department}
     */
    public abstract Department getDepartment(String dpDeptcode, String status);

    public abstract Department getDepartment(Long dpDeptId, String status);
    
    public abstract Department createDepartment(Department department);

}