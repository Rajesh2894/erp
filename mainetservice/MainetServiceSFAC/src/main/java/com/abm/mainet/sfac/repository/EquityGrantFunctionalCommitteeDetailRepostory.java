package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.EquityGrantFuctionalCommitteeDetailEntity;
import com.abm.mainet.sfac.domain.EquityGrantMasterEntity;

@Repository
public interface EquityGrantFunctionalCommitteeDetailRepostory extends JpaRepository<EquityGrantFuctionalCommitteeDetailEntity, Long>{

	List<EquityGrantFuctionalCommitteeDetailEntity> findByMasterEntity(EquityGrantMasterEntity equityGrantMasterEntity);

}
