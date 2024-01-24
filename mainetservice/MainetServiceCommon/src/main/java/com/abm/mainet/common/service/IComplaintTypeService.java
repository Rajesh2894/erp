package com.abm.mainet.common.service;

import java.util.List;

import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.dto.ComplaintTypeBean;
import com.abm.mainet.common.dto.DepartmentComplaintBean;

/**
 * @author ritesh.patil
 *
 */
public interface IComplaintTypeService {

    boolean save(DepartmentComplaintBean departmentComplaintBean);

    List<ComplaintTypeBean> findAllComplaintsByOrg(Long orgId);

    List<Object[]> getNotcomplainedDepartment(Long orgId, String status);

    List<DepartmentComplaint> getcomplainedDepartment(Long orgId);

    DepartmentComplaint findComplainedDepartmentByDeptId(Long depId,
            Long orgId);

    ComplaintType findComplaintTypeById(Long compId);

    DepartmentComplaintBean findById(Long compId, int langId);

    List<ComplaintGrid> findAllComplaintRecords(Long orgId, String status);

    boolean saveEdit(DepartmentComplaintBean departmentComplaintBean,
            List<Long> removeChildIds);

    boolean isApplicaitonPendingForComplaintType(Long comId);

    /**
     * this service is used to check if any complaints is active or not.
     * @param deptCompId
     * @return it complaints is active than return true else return false.
     */
    boolean isComplaintsActiveForDepartment(Long deptCompId);

    /**
     * this service is used to inactive complaints department.
     * @param deptCompId
     */
    void inactiveComplaintDepartment(Long deptCompId);

}
