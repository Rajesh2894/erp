
package com.abm.mainet.mobile.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Employee;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Serializable> {

    @Query("select bd from Employee bd where bd.emploginname =:emploginname and bd.organisation.orgid  =:orgId and bd.emppassword =:emppassword and bd.isDeleted ='0'")
    Employee getAuthentication(@Param("emploginname") String emploginname, @Param("orgId") Long orgId,
            @Param("emppassword") String emppassword);

    @Query("select bd from Employee bd where bd.emploginname =:emploginname and bd.isDeleted ='0'")
    Employee getEmployee(@Param("emploginname") String emploginname);

    @Query("select bd from Employee bd where bd.empId =:empId and bd.isDeleted ='0'")
    Employee getEmployee(@Param("empId") Long empId);

    @Query("select bd from Employee bd where bd.empmobno =:empmobno and bd.organisation.orgid  =:orgId and bd.isDeleted ='0'")
    Employee getEmployeeMob(@Param("empmobno") String empmobno, @Param("orgId") Long orgId);

    @Query("select bd from Employee bd where bd.empemail =:empemail and bd.organisation.orgid  =:orgId and bd.isDeleted ='0'")
    Employee getEmployeeEmail(@Param("empemail") String empemail, @Param("orgId") Long orgId);

    @Query("select bd from Employee bd where bd.emploginname =:emploginname and bd.organisation.orgid  =:orgId and bd.isDeleted ='0'")
    Employee getEmployeeUserName(@Param("emploginname") String empemail, @Param("orgId") Long orgId);
}
