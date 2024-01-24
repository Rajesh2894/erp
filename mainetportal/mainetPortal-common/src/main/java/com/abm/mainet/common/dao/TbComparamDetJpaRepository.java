package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbComparamDetEntity;

/**
 * Repository : TbComparamDet.
 */
@Repository
public interface TbComparamDetJpaRepository extends PagingAndSortingRepository<TbComparamDetEntity, Long> {

    @Query("select tbComparamDetEntity from TbComparamDetEntity tbComparamDetEntity "
            + " where tbComparamDetEntity.tbComparamMas.cpmId =:cpmId and tbComparamDetEntity.orgid IN (select orgid from Organisation org where org.defaultStatus='Y')")
    List<TbComparamDetEntity> findCmprmDetByCpmId(@Param("cpmId") Long cpmId);
}
