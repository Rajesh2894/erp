package com.abm.mainet.socialsecurity.repository;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.socialsecurity.domain.ConfigurationMasterEntity;


/**
 * @author rahul.chaubey
 * @since 11 Jan 2020
 */

@Repository
public interface ConfigurationMasterRepository  extends JpaRepository<ConfigurationMasterEntity, Long>
{
	//for checking the if the scheme is already configured or not
	@Query(" FROM ConfigurationMasterEntity where  configurationId=:configurationId AND orgId=:orgId")
	public ConfigurationMasterEntity findSchemeById(@Param("configurationId") Long configurationId ,@Param("orgId") Long orgId );
	
	
	@Query("FROM ConfigurationMasterEntity where  orgId=:orgId")
	public List<ConfigurationMasterEntity>loadData(@Param("orgId") Long orgId );
	
	@Query("FROM ConfigurationMasterEntity where schemeMstId=:schemeMstId AND orgId=:orgId")
	public List<ConfigurationMasterEntity>searchData(@Param("schemeMstId") Long schemeMstId ,@Param("orgId") Long orgId );
	
	
	@Query("SELECT DISTINCT (sm.smShortdesc) ,sm.smServiceId, sm.smServiceName,  sm.smServiceNameMar FROM ServiceMaster sm "
            + "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeStatusId and sm.comV1=:notActualFlag AND sm.smServiceName IS NOT NULL"
            + " AND sm.smServiceId NOT IN (SELECT cm.schemeMstId FROM ConfigurationMasterEntity cm)")
	public List<Object[]> unconfiguredList(@Param("orgId") Long orgId,
            @Param("depId") Long depId, @Param("activeStatusId") Long activeStatusId,
            @Param("notActualFlag") String notActualFlag);
	
	
	@Query("FROM ConfigurationMasterEntity where schemeMstId=:schemeMstId AND orgId=:orgId")
	public ConfigurationMasterEntity getConfigMstDataBySchemeId(@Param("schemeMstId") Long schemeMstId ,@Param("orgId") Long orgId );
}
