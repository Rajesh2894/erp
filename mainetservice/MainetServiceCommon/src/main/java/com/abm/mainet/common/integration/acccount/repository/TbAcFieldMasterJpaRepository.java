package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountFieldMasterEntity;

/**
 * Repository : TbAcFieldMaster.
 */
public interface TbAcFieldMasterJpaRepository extends PagingAndSortingRepository<AccountFieldMasterEntity, Long> {

    @Query("select e.fieldCompcode from AccountFieldMasterEntity e  where e.fieldId =:fieldId")
    String getFieldCode(@Param("fieldId") Long fieldId);

    @Query("select e from AccountFieldMasterEntity e where e.fieldParentId.fieldId is not null and e.orgid =:orgId order by e.fieldCompcode desc")
	List<AccountFieldMasterEntity> getLastLevelsCompositeCodeByOrgId(@Param("orgId") Long orgId);
    
    @Query("select e.fieldId from AccountFieldMasterEntity e  where e.fieldCompcode =:fieldCompcode and e.orgid=:orgId")
    Long getFieldId(@Param("fieldCompcode") String fieldId, @Param("orgId") Long orgId);
    
    
    @Query("select e from AccountFieldMasterEntity e where e.orgid =:orgId")
 	List<AccountFieldMasterEntity> getFieldMasterByOrgid(@Param("orgId") Long orgId);
    
    @Query("select e.fieldDesc from AccountFieldMasterEntity e  where e.fieldId =:fieldId")
    String getFieldDesc(@Param("fieldId") Long fieldId); 
}
