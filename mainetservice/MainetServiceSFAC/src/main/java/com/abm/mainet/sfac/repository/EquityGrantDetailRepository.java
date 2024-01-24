package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquityGrantDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;

@Repository
public interface EquityGrantDetailRepository extends JpaRepository<EquityGrantDetailEntity, Long>{

	List<EquityGrantDetailEntity> findByMasterEntity(EquityGrantMasterEntity equityGrantMasterEntity);

}
