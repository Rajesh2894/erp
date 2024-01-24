
package com.abm.mainet.common.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.workflow.domain.ServicesEventEntity;

/**
 * @author ritesh.patil
 *
 */
public interface IServicesEventEntityRepository extends CrudRepository<ServicesEventEntity, Long> {

    @Modifying
    @Query("update ServicesEventEntity s set s.isDeleted = ?1 where s.serviceEventId = ?2")
    void deleteRecord(String flag, Long id);

    @Query("select count(s.serviceEventId) from ServicesEventEntity s where s.smServiceId=?1 AND s.orgId=?2 "
            + "AND s.deptId=?3 AND s.systemModuleFunction.smfid in (?4) and s.isDeleted = ?5")
    Long checkEventsForServiceIdExist(long smServiceId, Organisation orgId, Department deptId, List<Long> eventIds,
            String isDeleted);

    @Query("SELECT e.smfname,e.smfname_mar,e.smfdescription,s.serviceEventId "
            + "FROM ServicesEventEntity s join s.systemModuleFunction e WHERE s.orgId.orgid=?1 and  s.deptId.dpDeptid =?2 and s.smServiceId =?3 and s.isDeleted = 'N' order by s.serviceEventId asc")
    List<Object[]> findAllEventsByDeptAndServiceId(Long orgId, Long deptId, Long serviceId);

    @Query("SELECT e.smfid, e.smfname,e.smfname_mar,e.smfdescription,e.smfaction,s.serviceEventId,s.smServiceId,s.isDeleted "
            + "FROM ServicesEventEntity s join s.systemModuleFunction e "
            + "WHERE s.orgId=:org AND s.smServiceId=:serviceId AND s.deptId.dpDeptid=:deptId")
    List<Object[]> findEventsByDeptOrgService(@Param("deptId") Long deptId, @Param("org") Organisation org,
            @Param("serviceId") Long serviceId);

    @Query("SELECT e.smfid, e.smfname,e.smfname_mar,e.smfdescription,e.smfaction,s.serviceEventId,s.smServiceId,s.isDeleted "
            + "FROM ServicesEventEntity s join s.systemModuleFunction e "
            + "WHERE s.orgId=:org AND s.deptId.dpDeptid=:deptId")
    List<Object[]> findEventsByDeptOrg(@Param("deptId") Long deptId, @Param("org") Organisation org);

    @Query("SELECT s FROM ServicesEventEntity s "
            + "WHERE s.orgId=:org AND s.smServiceId=:serviceId AND s.deptId.dpDeptid=:deptId "
            + "AND s.systemModuleFunction.smfid=:eventId")
    ServicesEventEntity findEventByEventId(@Param("eventId") Long eventId, @Param("deptId") Long deptId,
            @Param("org") Organisation org, @Param("serviceId") Long serviceId);

    @Query("SELECT s.smfid, s.smfname_mar,s.smfdescription,s.smfaction,s.smfname, "
	        + "(select s1.smfname from SystemModuleFunction s1 where s1.smfid=s.moduleFunction.smfid) as parentid2, "
		+ "(select s2.smfname from SystemModuleFunction s2 where s2.smfid=(select s3.moduleFunction from SystemModuleFunction s3 where s3.smfid=s.moduleFunction.smfid)) as parentid3, "
		+ "(select s4.smfname from SystemModuleFunction s4 where s4.smfid=(select s5.moduleFunction from SystemModuleFunction s5 where s5.smfid=(select s6.moduleFunction from SystemModuleFunction s6 where s6.smfid=s.moduleFunction.smfid))) as parentid4, "
		+ "(select s7.smfname from SystemModuleFunction s7 where s7.smfid=(select s8.moduleFunction from SystemModuleFunction s8 where s8.smfid=(select s9.moduleFunction from SystemModuleFunction s9 where s9.smfid=(select s10.moduleFunction from SystemModuleFunction s10 where s10.smfid=s.moduleFunction.smfid)))) as parentid5 "
    	+ "FROM SystemModuleFunction s WHERE LOWER(s.smfaction) like concat('%',LOWER( ?1 )) and s.isdeleted = ?2 order by s.smfname")
    List<Object[]> findAllEvents(String smfCode, String activeStatus);

    @Query("SELECT s.smfid, s.smfname_mar,e.serviceEventId,s.smfaction,s.smfname, "
    		+ "(select s1.smfname from SystemModuleFunction s1 where s1.smfid=s.moduleFunction.smfid) as parentid2, "
    		+ "(select s2.smfname from SystemModuleFunction s2 where s2.smfid=(select s3.moduleFunction from SystemModuleFunction s3 where s3.smfid=s.moduleFunction.smfid)) as parentid3, "
    		+ "(select s4.smfname from SystemModuleFunction s4 where s4.smfid=(select s5.moduleFunction from SystemModuleFunction s5 where s5.smfid=(select s6.moduleFunction from SystemModuleFunction s6 where s6.smfid=s.moduleFunction.smfid))) as parentid4, "
    		+ "(select s7.smfname from SystemModuleFunction s7 where s7.smfid=(select s8.moduleFunction from SystemModuleFunction s8 where s8.smfid=(select s9.moduleFunction from SystemModuleFunction s9 where s9.smfid=(select s10.moduleFunction from SystemModuleFunction s10 where s10.smfid=s.moduleFunction.smfid)))) as parentid5 "
    		+ "FROM ServicesEventEntity e join e.systemModuleFunction s "
            + "WHERE LOWER(s.smfaction) like concat('%',LOWER(:flag)) "
            + "and e.deptId.dpDeptid=:deptId and e.smServiceId=:serviceId "
            + "and s.isdeleted =:activeStatus and e.orgId.orgid=:orgId "
            + "order by s.smfname")
    List<Object[]> findMappedEventList(@Param("flag") String flag, @Param("activeStatus") String activeStatus,
            @Param("deptId") Long deptId, @Param("serviceId") Long serviceId, @Param("orgId") Long orgId);

    @Query("SELECT t.smfid, t.smfname_mar,t.smfaction,t.smfname, "
    		+ "(select s1.smfname from SystemModuleFunction s1 where s1.smfid=t.moduleFunction.smfid) as parentid2, "
    		+ "(select s2.smfname from SystemModuleFunction s2 where s2.smfid=(select s3.moduleFunction from SystemModuleFunction s3 where s3.smfid=t.moduleFunction.smfid)) as parentid3, "
    		+ "(select s4.smfname from SystemModuleFunction s4 where s4.smfid=(select s5.moduleFunction from SystemModuleFunction s5 where s5.smfid=(select s6.moduleFunction from SystemModuleFunction s6 where s6.smfid=t.moduleFunction.smfid))) as parentid4, "
    		+ "(select s7.smfname from SystemModuleFunction s7 where s7.smfid=(select s8.moduleFunction from SystemModuleFunction s8 where s8.smfid=(select s9.moduleFunction from SystemModuleFunction s9 where s9.smfid=(select s10.moduleFunction from SystemModuleFunction s10 where s10.smfid=t.moduleFunction.smfid)))) as parentid5 "
    		+ "FROM SystemModuleFunction t "
            + "WHERE LOWER(t.smfaction) LIKE CONCAT('%',LOWER(:flag)) "
            + "AND t.isdeleted =:activeStatus "
            + "AND t.smfid NOT IN "
            + "(SELECT e.systemModuleFunction.smfid FROM ServicesEventEntity e "
            + "WHERE e.deptId.dpDeptid=:deptId and e.smServiceId=:serviceId and e.orgId.orgid=:orgId) "
            + "order by t.smfname")
    List<Object[]> findNonMappedEventList(@Param("flag") String flag, @Param("activeStatus") String activeStatus,
            @Param("deptId") Long deptId, @Param("serviceId") Long serviceId, @Param("orgId") Long orgId);

    
    
}
