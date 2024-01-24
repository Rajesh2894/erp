package com.abm.mainet.common.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbComparentMasEntity;

/**
 * Repository : TbComparentMas.
 */
public interface TbComparentMasJpaRepository extends PagingAndSortingRepository<TbComparentMasEntity, Long> {

    @Query("select tbComparentMasEntity from TbComparentMasEntity tbComparentMasEntity "
            + " where tbComparentMasEntity.tbComparamMas.cpmId =:cpmId and tbComparentMasEntity.orgid=:orgId "
            + " order by tbComparentMasEntity.comLevel asc") // and tbComparentMasEntity.comStatus='Y'
    List<TbComparentMasEntity> findComparentMasDataById(@Param("cpmId") Long cpmId, @Param("orgId") Long orgId);

    @Query("select tbComparentMasEntity from TbComparentMasEntity tbComparentMasEntity "
            + " where tbComparentMasEntity.tbComparamMas.cpmId =:cpmId and tbComparentMasEntity.orgid=:orgId "
            + " and tbComparentMasEntity.comStatus='Y' and tbComparentMasEntity.comLevel = 1"
            + " order by tbComparentMasEntity.comLevel asc")
    TbComparentMasEntity findDataByCpmIdLevel(@Param("cpmId") Long cpmId, @Param("orgId") Long orgid);

    @Query("select tbComparentMasEntity from TbComparentMasEntity tbComparentMasEntity "
            + " where tbComparentMasEntity.tbComparamMas.cpmId =:cpmId and tbComparentMasEntity.orgid IN (select orgid from Organisation org where org.defaultStatus='Y')"
            + "order by tbComparentMasEntity.comLevel asc")
    List<TbComparentMasEntity> findComparentMasDataByCpmId(@Param("cpmId") Long cpmId);

}
