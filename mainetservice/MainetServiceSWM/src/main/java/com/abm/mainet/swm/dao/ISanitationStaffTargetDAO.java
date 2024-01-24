package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.SanitationStaffTarget;

/**
 * @author Ajay.Kumar
 *
 */
public interface ISanitationStaffTargetDAO {

    /**
     * search Sanitation StaffTarget
     * @param empId
     * @param orgId
     * @return
     */
    List<SanitationStaffTarget> searchSanitationStaffTarget(Long sanType, Date fromDate, Date toDate, Long orgId);

}
