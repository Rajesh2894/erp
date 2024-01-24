package com.abm.mainet.bnd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthDeathCFCInterface;

@Repository
public interface CfcInterfaceJpaRepository extends JpaRepository<BirthDeathCFCInterface, Long> {
	
	@Query("select f.smServiceId from  BirthDeathCFCInterface f where f.apmApplicationId=:ApplicationId and f.orgId=:orgId")
    Long  findServiceBirthorDeath(@Param("ApplicationId")Long ApplicationId,@Param("orgId")Long orgId);	
	
	@Query("select f from  BirthDeathCFCInterface f where f.bdRequestId=:bdRequestId and f.orgId=:orgId  order by f.bdCfcIntfId desc")
	List<BirthDeathCFCInterface> findAppBirthorDeath(@Param("bdRequestId")Long bdRequestId,@Param("orgId")Long orgId);	
	
	@Query(value = "SELECT wr.STATUS from tb_workflow_request wr where wr.APM_APPLICATION_ID =:apmApplicationId and  wr.ORGID =:orgId", nativeQuery = true)
    String getWorkflowRequestByAppId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);

}
