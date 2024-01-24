package com.abm.mainet.legal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.legal.domain.TbLglComntRevwDtlEntity;
import com.abm.mainet.legal.dto.CaseHearingDTO;

@Repository
public interface TbLglComntRevwDtlRepository extends JpaRepository<TbLglComntRevwDtlEntity, Long>{

	@Query("SELECT entity FROM TbLglComntRevwDtlEntity entity WHERE entity.cseId= :cseId AND entity.activeStatus='A' and orgid= :orgid ")
    List<TbLglComntRevwDtlEntity> findByCseIdAndOrgid(@Param("cseId") Long cseId,@Param("orgid") Long orgid);

    @Query("SELECT entity FROM TbLglComntRevwDtlEntity entity WHERE entity.cseId= :cseId AND entity.activeStatus='A' ")
    List<TbLglComntRevwDtlEntity> findByCseId(@Param("cseId") Long cseId);
    
    @Modifying
    @Query("UPDATE TbLglComntRevwDtlEntity entity SET entity.activeStatus ='I', entity.updatedBy =:updatedBy, entity.updatedDate = CURRENT_DATE "
            + "WHERE entity.comntId in (:removeIds) ")
    void deleteHearingComntRevwByIds(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);
    
    @Query("SELECT entity FROM TbLglComntRevwDtlEntity entity WHERE entity.hrId = :hrId AND entity.activeStatus ='A'")
	List<TbLglComntRevwDtlEntity> findByhrId(@Param("hrId")Long hrId);
    
    @Query("SELECT entity FROM TbLglComntRevwDtlEntity entity WHERE entity.hrId = :hrId AND entity.activeStatus = 'A' and orgid= :orgid")
	List<TbLglComntRevwDtlEntity> findByhrIdAndOrgid(@Param("hrId")Long hrId, @Param("orgid")Long orgid);
}
