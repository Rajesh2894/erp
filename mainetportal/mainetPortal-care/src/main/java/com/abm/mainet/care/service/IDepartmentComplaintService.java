package com.abm.mainet.care.service;

import java.util.List;

import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.dto.DepartmentComplaintTypeDTO;

public interface IDepartmentComplaintService {

    /**
     * REST call to MaintService to get all departments complaint by organization id.
     * 
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    List<DepartmentComplaintDTO> getDepartmentComplaintByOrgId(Long orgId) throws Exception;

    /**
     * REST call to MaintService to get all departments complaint for which care work-flow is defined by organization id.
     * 
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    List<DepartmentComplaintDTO> getCareWorkflowMasterDefinedDepartmentsListByOrgId(Long orgId) throws Exception;

    /**
     * REST call to MaintService to get department complaint by department id and organization id.
     * 
     * @param depId Department ID
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    DepartmentComplaintDTO getDepartmentComplaintByDepartmentId(Long depId, Long orgId) throws Exception;

    /**
     * REST call to MaintService to get all departments complaint types for which care work-flow is defined by department id and
     * organization id.
     * 
     * @param depId Department ID
     * @param orgId Organization ID
     * @return
     * @throws Exception
     */
    List<DepartmentComplaintTypeDTO> getCareWorkflowMasterDefinedDepartmentComplaintTypeByDepartmentId(Long depId,
            Long orgId) throws Exception;

    /**
     * REST call to MaintService to get department complaint type by id.
     * 
     * @param id
     * @return
     * @throws Exception
     */
    DepartmentComplaintTypeDTO getDepartmentComplaintTypeById(Long id) throws Exception;

}