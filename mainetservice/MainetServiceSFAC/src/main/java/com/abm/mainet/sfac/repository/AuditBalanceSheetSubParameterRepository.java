package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AuditBalanceSheetKeyParameterEntity;
import com.abm.mainet.sfac.domain.AuditBalanceSheetSubParameterEntity;

@Repository
public interface AuditBalanceSheetSubParameterRepository extends JpaRepository<AuditBalanceSheetSubParameterEntity, Long>{

	List<AuditBalanceSheetSubParameterEntity> findByKeyMasterEntity(AuditBalanceSheetKeyParameterEntity key);

}
