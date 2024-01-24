/**
 *
 */
package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author Pranit.Mhatre
 * @since 05 February, 2014
 */
public interface IDepartmentService {

    /**
     * To get list of {@link Department} for given status.
     * @param activeStatus the {@link String} containing status of the department.
     * @return {@link List} of {@link Department} if found otherwise <code>null</code>.
     */
    public List<Department> getDepartments(String activeStatus);

    /**
     * To get list containing mapping between {@link Department} and {@link Organisation} which stored in {@link DeptOrgMap} for
     * given status.
     * @param activeStatus the {@link String} containing status of the department.
     * @return {@link List} of {@link DeptOrgMap} if found otherwise <code>null</code>.
     */
    public List<DeptOrgMap> getMappingWithOragnisation(String activeStatus);

	/**
	 * @param organisation
	 * @param dpDeptcode
	 * @param status
	 * @return
	 */
	public Department getDepartment(String dpDeptcode, String status);
	
	public Department getDeptName(long deptId, String status);
	
	public Department create(Department department);

}
