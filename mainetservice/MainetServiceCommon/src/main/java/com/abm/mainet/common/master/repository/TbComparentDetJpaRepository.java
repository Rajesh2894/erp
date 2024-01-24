package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbComparentDetEntity;

/**
 * Repository : TbComparentDet.
 */
public interface TbComparentDetJpaRepository extends PagingAndSortingRepository<TbComparentDetEntity, Long> {

    @Query("select tbComparentDetEntity from TbComparentDetEntity tbComparentDetEntity "
            + " where tbComparentDetEntity.tbComparentMas.comId =:comId and tbComparentDetEntity.orgid=:orgId order by tbComparentDetEntity.codDesc")
    List<TbComparentDetEntity> findComparentDetDataById(@Param("comId") Long comId, @Param("orgId") Long orgId);

    @Query("select tbComparentDetEntity from TbComparentDetEntity tbComparentDetEntity "
            + " where tbComparentDetEntity.tbComparentDet.codId = :parentId and tbComparentDetEntity.orgid=:orgId")
    List<TbComparentDetEntity> findComparentDetDataByParentId(@Param("parentId") Long parentId, @Param("orgId") Long orgId);
    
    @Query("select tbComparentDetEntity.codOthers from TbComparentDetEntity tbComparentDetEntity "
            + " where tbComparentDetEntity.tbComparentDet.codId = :parentId and tbComparentDetEntity.codId=:codId and tbComparentDetEntity.orgid=:orgId")
    String getOtherFieldVal(@Param("parentId") Long parentId,@Param("codId") Long codId ,@Param("orgId") Long orgId);

}
