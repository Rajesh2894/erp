package com.abm.mainet.common.integration.acccount.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.integration.acccount.domain.AccountFundMasterEntity;

/**
 * Repository : TbAcFundMaster.
 */
public interface AccountFundMasterJpaRepository extends PagingAndSortingRepository<AccountFundMasterEntity, Long> {

    @Query("select e.fundCompositecode from AccountFundMasterEntity e  where e.fundId =:fundId")
    String getFundCode(@Param("fundId") Long fundId);

    @Query("select e.fundCompositecode,e.fundDesc from AccountFundMasterEntity e  where e.fundId =:fundId")
    List<Object[]> getFundCodeDesc(@Param("fundId") Long fundId);
    
}
