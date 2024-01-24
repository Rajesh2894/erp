package com.abm.mainet.mobile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ViewPrefixDetails;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public interface RegistrationServiceRepository extends CrudRepository<Employee, Long> {

    /**
     * @param string
     * @return
     */
    @Query("select bd from ViewPrefixDetails bd where bd.cpmPrefix =:preFix")
    List<ViewPrefixDetails> findthePrefix(@Param("preFix") String preFix);

    @Query("select gm from GroupMaster gm where gm.grStatus='A' and gm.orgId.orgid =:orgId and UPPER(gm.grCode) =:groupCode")
    GroupMaster findGpId(@Param("groupCode") String groupCode, @Param("orgId") Long orgId);

    @Query("Select d from Department d where d.dpDeptcode =:deptCode and d.status =:active ")
    Department getDepartment(@Param("deptCode") String deptCode, @Param("active") String active);

    @Query("Select dg from Designation dg where UPPER(dg.dsgname) =:citizen")
    List<Designation> getAllDesignationByDesgName(@Param("citizen") String citizen);

    @Query("Select d from Organisation d where d.orgid =:orgid and d.orgStatus =:active ")
    Organisation getOrganisation(@Param("orgid") Long orgid, @Param("active") String active);

}
