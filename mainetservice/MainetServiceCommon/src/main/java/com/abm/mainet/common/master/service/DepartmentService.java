package com.abm.mainet.common.master.service;

import java.util.List;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DeptOrgMap;
import com.abm.mainet.common.master.dto.DepartmentDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.utility.LookUp;

/**
 * Business Service Interface for entity Department.
 */
public interface DepartmentService {

    /**
     * Loads an entity from the database using its Primary Key
     * @param depid
     * @return entity
     */
    DepartmentDTO findById(Long depid);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<DepartmentDTO> findAll();

    /**
     * Saves the given entity in the database (create or update)
     * @param entity
     * @return entity
     */
    DepartmentDTO save(DepartmentDTO entity);

    /**
     * Updates the given entity in the database
     * @param entity
     * @return
     */
    DepartmentDTO update(DepartmentDTO entity);

    /**
     * Creates the given entity in the database
     * @param entity
     * @return
     */
    DepartmentDTO create(DepartmentDTO entity);

    /**
     * Deletes an entity using its Primary Key
     * @param depid
     */
    void delete(Long depid);

    List<Object[]> getAllDeptTypeNames();

    public List<Department> getDepartments(long orgId, String activeStatus);
    public List<Department> getDepartments(String activeStatus);
    public List<DeptOrgMap> getMappingWithOragnisation(String activeStatus);

    public Department getDepartment(String dpDeptcode, String status);

    public Long getDepartmentIdByDeptCode(String dpDeptcode, String status);

    public TbDepartment findDeptByCode(final Long orgId, final String status, final String code);

    /**
     * use this method in case you require Department Lookups in ascending order of Department Names
     * @param status : pass status either A or I
     * @return list of LookUps for available Department
     * @throws IllegalArgumentException : if no record found for provided parameter
     */
    List<LookUp> getDepartmentLookUpsByAsc(String status);

    public String getDeptCode(Long dpDeptid);

    public Long getDepartmentIdByDeptCode(String deptCode);

    String fetchDepartmentDescById(Long deptId);
    
    public String getDepartmentDescByDeptCode(String deptCode);
    
    String fetchDepartmentDescEngById(final Long deptId,int langId);

	List<LookUp> getAllDesgBasedOnDept(Long deptId, Long orgId);

	List<LookUp> finDeptListForLoc(Long locId);

}
