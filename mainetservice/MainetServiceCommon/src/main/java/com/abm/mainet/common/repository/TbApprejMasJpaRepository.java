package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbApprejMasEntity;
import com.abm.mainet.common.domain.TbApprejMasEntityKey;

/**
 * Repository : TbApprejMas.
 */
public interface TbApprejMasJpaRepository extends PagingAndSortingRepository<TbApprejMasEntity, TbApprejMasEntityKey> {

    @Query("select tbApprejMasEntity from TbApprejMasEntity tbApprejMasEntity "
            + " where tbApprejMasEntity.artServiceId = :artServiceId and tbApprejMasEntity.artType = :artType and tbApprejMasEntity.compositePrimaryKey.orgid= :orgid ")
    List<TbApprejMasEntity> findByServiceId(@Param("artType") Long artType, @Param("artServiceId") Long artServiceId,
            @Param("orgid") Long orgid);

    @Query("select tbApprejMasEntity from TbApprejMasEntity tbApprejMasEntity "
            + " where tbApprejMasEntity.compositePrimaryKey.orgid= :orgid and tbApprejMasEntity.artType = :artType and tbApprejMasEntity.artServiceId = :artServiceId  and  tbApprejMasEntity.statusflag = :statusflag")
    List<TbApprejMasEntity> findByRemarkType(@Param("artType") Long artType, @Param("orgid") Long orgid,
            @Param("artServiceId") Long artServiceId, @Param("statusflag") String statusflag);

    @Query("select tbApprejMasEntity from TbApprejMasEntity tbApprejMasEntity where tbApprejMasEntity.compositePrimaryKey.artId in (:artId) and tbApprejMasEntity.compositePrimaryKey.orgid=:orgid ")
    List<TbApprejMasEntity> findByArtId(@Param("artId") List<Long> artId, @Param("orgid") Long orgid);
    
    @Query("select tbApprejMasEntity from TbApprejMasEntity tbApprejMasEntity "
            + " where tbApprejMasEntity.artType = :artType and tbApprejMasEntity.compositePrimaryKey.orgid= :orgid ")
    List<TbApprejMasEntity> findByArtTyped(@Param("artType") Long artType, @Param("orgid") Long orgid);
    
}
