package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.MilestoneCBBODetEntity;
import com.abm.mainet.sfac.domain.MilestoneMasterEntity;

@Repository
public interface MilestoneCBBODetRepository extends JpaRepository<MilestoneCBBODetEntity, Long>{

	List<MilestoneCBBODetEntity> findByMilestoneMasterEntity(MilestoneMasterEntity milestoneMasterEntity);

	List<MilestoneCBBODetEntity> findByCbboID(Long cbboId);

	MilestoneCBBODetEntity findByMilestoneMasterEntityAndCbboID(MilestoneMasterEntity milestoneMasterEntity, Long cbboId);


}
