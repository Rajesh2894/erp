package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbWorkOrderDetailEntity;

/**
 * Repository : TbWorkOrderDetail.
 */
@Repository
public interface TbWorkOrderDetailJpaRepository extends PagingAndSortingRepository<TbWorkOrderDetailEntity, Long> {

    @Query("select tbWorkOrderDetailEntity from TbWorkOrderDetailEntity tbWorkOrderDetailEntity where tbWorkOrderDetailEntity.wdApplicationId=:applicationId and tbWorkOrderDetailEntity.orgid=:orgid")
    List<TbWorkOrderDetailEntity> findByApplicationID(@Param("applicationId") Long applicationId, @Param("orgid") Long orgid);
    
    @Query("select tbWorkOrderDetailEntity from TbWorkOrderDetailEntity tbWorkOrderDetailEntity where tbWorkOrderDetailEntity.wdRemarkId=:remarkId and tbWorkOrderDetailEntity.wdApplicationId=:applicationId  and tbWorkOrderDetailEntity.orgid=:orgid")
    TbWorkOrderDetailEntity findByRemarkId(@Param("applicationId") Long applicationId,@Param("remarkId") Long remarkId, @Param("orgid") Long orgid);

}
