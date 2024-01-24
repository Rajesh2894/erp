package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountHeadPrimaryAccountCodeMasterEntity;

/**
 * Repository : TbAcFunctionMaster.
 */
public interface AcPrimaryCodeMasterJpaRepository
        extends PagingAndSortingRepository<AccountHeadPrimaryAccountCodeMasterEntity, Long> {

    @Query("select p.primaryAcHeadCompcode,p.primaryAcHeadDesc from AccountHeadPrimaryAccountCodeMasterEntity p where p.primaryAcHeadId=:pacHeadId order by p.primaryAcHeadCompcode asc")
    List<Object[]> findByPrimaryHeadCodeDesc(@Param("pacHeadId") Long pacHeadId);

    @Query("select p.primaryAcHeadCompcode from AccountHeadPrimaryAccountCodeMasterEntity p where p.primaryAcHeadId=:pacHeadId order by p.primaryAcHeadCompcode asc")
    String findByPrimaryHeadCode(@Param("pacHeadId") Long pacHeadId);
    
    @Query("select p.primaryAcHeadDescReg from AccountHeadPrimaryAccountCodeMasterEntity p where p.primaryAcHeadId=:pacHeadId")
    String findPrimaryHeadCodeDescRegByHeadId(@Param("pacHeadId") Long headId);
}
