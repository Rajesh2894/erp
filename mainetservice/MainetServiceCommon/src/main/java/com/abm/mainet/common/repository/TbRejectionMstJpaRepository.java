package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbRejectionMstEntity;
import com.abm.mainet.common.domain.TbRejectionMstEntityKey;

/**
 * Repository : TbRejectionMst.
 */
public interface TbRejectionMstJpaRepository extends PagingAndSortingRepository<TbRejectionMstEntity, TbRejectionMstEntityKey> {
    @Query("select tbRejectionMstEntity from TbRejectionMstEntity tbRejectionMstEntity "
            + " where tbRejectionMstEntity.compositePrimaryKey.orgid= :orgid and tbRejectionMstEntity.rejType = :rejType and tbRejectionMstEntity.rejServiceId = :rejServiceId  and tbRejectionMstEntity.rejApplicationId=:rejApplicationId")
    List<TbRejectionMstEntity> finbyApplicationId(@Param("rejType") Long rejType, @Param("orgid") Long orgid,
            @Param("rejServiceId") Long rejServiceId, @Param("rejApplicationId") Long rejApplicationId);

}
