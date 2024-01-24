package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountFunctionMasterEntity;

/**
 * Repository : TbAcFunctionMaster.
 */
public interface TbAcFunctionMasterJpaRepository extends PagingAndSortingRepository<AccountFunctionMasterEntity, Long> {

    @Query("select e.functionCompcode from AccountFunctionMasterEntity e  where e.functionId =:functionId")
    String getFunctionCode(@Param("functionId") Long functionId);

    @Query("select am.functionCompcode from AccountFunctionMasterEntity am  WHERE am.orgid= :orgid ")
    List<String> getAllComposteCode(@Param("orgid") Long orgId);

}
