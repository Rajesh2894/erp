/**
 * @author Lalit.Prusti
 */
package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.HelpDesk;

/**
 * The Interface HelpDeskRepository.
 */
@Repository
public interface HelpDeskRepository extends PagingAndSortingRepository<HelpDesk, Long> {

    /**
     * Find all individual and team activity.
     *
     * @param orgid the organisation id
     * @param empId the employee id
     * @return the list of CallLog
     */
    @Query(" SELECT m FROM HelpDesk m WHERE m.orgid =:orgid AND m.helpEmpid in (SELECT e.empId FROM Employee e WHERE e.empId=:empId OR e.reportingManager=:empId)   OR FIND_IN_SET(:empId,  m.helpWatcher) > 0 OR m.createdBy=:empId ")
    List<HelpDesk> findAllIndividualAndTeamCallLog(@Param(value = "orgid") Long orgid,
            @Param(value = "empId") Long empId);

    List<HelpDesk> findAllByOrgid(@Param(value = "orgid") Long orgid);
    
    @Query(" SELECT m FROM HelpDesk m")
	List<HelpDesk> findAllCallLog();

}
