package com.abm.mainet.common.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.domain.TbComparamMasEntity;

@Repository
public interface TbComparamMasJpaRepository extends PagingAndSortingRepository<TbComparamMasEntity, Long> {

    @Query("select tbComparamMasEntity from TbComparamMasEntity tbComparamMasEntity "
            + " where tbComparamMasEntity.cpmReplicateFlag =:cpmRepFlag and"
            + " COALESCE(tbComparamMasEntity.cpmType,:cpmType)=:cpmType")
    List<TbComparamMasEntity> findAllByCpmReplicateFlag(@Param("cpmRepFlag") String cpmRepFlag, @Param("cpmType") String cpmType);

}
