/**
 * @author Lalit.Prusti
 */
package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.ActivityManagement;

/**
 * The Interface ActivityManagementRepository.
 */
@Repository
public interface ActivityManagementRepository extends PagingAndSortingRepository<ActivityManagement, Long> {

    /**
     * Find all individual and team activity.
     *
     * @param orgid the organisation id
     * @param empId the employee id
     * @return the list of Activity
     */
    @Query(" SELECT m FROM ActivityManagement m WHERE m.orgid =:orgid AND m.actEmpid in (SELECT e.empId FROM Employee e WHERE e.empId=:empId OR e.reportingManager=:empId)   OR FIND_IN_SET(:empId,  m.actWatcher) > 0 ")
    List<ActivityManagement> findAllIndividualAndTeamActivity(@Param(value = "orgid") Long orgid,
            @Param(value = "empId") Long empId);

    List<ActivityManagement> findAllByOrgid(@Param(value = "orgid") Long orgid);

}
