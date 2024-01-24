package com.abm.mainet.common.dao;

import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Organisation;

public interface IDepartmentDAO {

    /**
     * To get {@link Designation} by 'dsgName' designation name.
     * @param organisation {@link Organisation}
     * @param dpDeptcode is Department CODE.
     * @param isDeleted is '0'(Zero) if not deleted otherwise '1'.
     * @return {@link Department}
     */
    public abstract Department getDepartment(String dpDeptcode, String status);

    /**
     * @param dpDeptcode
     * @param status
     * @return
     */
    public abstract Long getDepartmentIdByDeptCode(String dpDeptcode,
            String status);

    /**
     * To get all {@link Department} object for given status.
     * @param activeStatus the {@link String} containing status & dpCheck Flag.
     * @return {@link List} of {@link Department} objects.
     */
    public abstract List<Department> getAllDepartmentWithDpCheck(String activeStatus, String dpCheck);

	public abstract List<Department> getAllDepartment(String activeStatus);

}