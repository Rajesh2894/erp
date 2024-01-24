package com.abm.mainet.cfc.loi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntityKey;

/**
 * Repository : TbLoiDet.
 */
public interface TbLoiDetJpaRepository extends PagingAndSortingRepository<TbLoiDetEntity, TbLoiDetEntityKey> {

    /**
     * @param loiApplicationId
     * @param loiId
     * @param orgId
     * @return
     */
    @Query("select l from TbLoiDetEntity l where l.loiMasId=:loiMasId and l.compositePrimaryKey.orgid=:orgid")
    List<TbLoiDetEntity> findLoiDetailsByLoiMasAndOrgId(@Param("loiMasId") Long loiId, @Param("orgid") Long orgId);

}
