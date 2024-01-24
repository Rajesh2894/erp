package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;
import com.abm.mainet.sfac.domain.EquityGrantShareHoldingDetailEntity;

@Repository
public interface EquityGrantShareholdingDetailRepository extends JpaRepository<EquityGrantShareHoldingDetailEntity, Long>{

	List<EquityGrantShareHoldingDetailEntity> findByMasterEntity(EquityGrantMasterEntity equityGrantMasterEntity);

}
