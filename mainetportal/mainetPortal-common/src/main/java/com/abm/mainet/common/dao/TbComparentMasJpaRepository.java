package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbComparentMasEntity;

@Repository
public interface TbComparentMasJpaRepository extends PagingAndSortingRepository<TbComparentMasEntity, Long> {

    @Query("select tbComparentMasEntity from TbComparentMasEntity tbComparentMasEntity "
            + " where tbComparentMasEntity.tbComparamMas.cpmId =:cpmId and tbComparentMasEntity.orgid IN (select orgid from Organisation org where org.defaultStatus='Y')"
            + "order by tbComparentMasEntity.comLevel asc")
    List<TbComparentMasEntity> findComparentMasDataByCpmId(@Param("cpmId") Long cpmId);

}
