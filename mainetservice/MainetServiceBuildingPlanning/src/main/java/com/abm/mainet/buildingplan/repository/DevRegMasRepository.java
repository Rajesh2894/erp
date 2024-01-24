package com.abm.mainet.buildingplan.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.buildingplan.domain.TbDeveloperRegistrationEntity;

public interface DevRegMasRepository extends JpaRepository<TbDeveloperRegistrationEntity, Long>  {
	
	@Query("select mas from TbDeveloperRegistrationEntity mas where mas.createdBy=:createdBy order by mas.id desc")
	List<TbDeveloperRegistrationEntity> getDeveloperRegistrationByCreatedById(@Param("createdBy") Long createdBy);
	
	@Modifying
	@Transactional
	@Query("delete from CFCAttachment cfc where cfc.applicationId =:applicationId and cfc.serviceId=:serviceId and cfc.orgid=:orgId and cfc.clmId=:clmId")
	void deleteDocument(@Param("applicationId")Long applicationId,@Param("serviceId") Long serviceId ,@Param("orgId") Long orgId, @Param("clmId") Long clmId);
}
