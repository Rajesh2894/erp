package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AuditBalanceSheetKeyParameterEntity;
import com.abm.mainet.sfac.domain.AuditBalanceSheetMasterEntity;

@Repository
public interface AuditBalanceSheetKeyParameterRepository extends JpaRepository<AuditBalanceSheetKeyParameterEntity, Long>{

	List<AuditBalanceSheetKeyParameterEntity> findByMasterEntity(AuditBalanceSheetMasterEntity entity);

}
