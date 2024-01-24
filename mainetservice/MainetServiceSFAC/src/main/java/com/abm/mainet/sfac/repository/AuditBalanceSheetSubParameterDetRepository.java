package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AuditBalanceSheetSubParameterDetail;
import com.abm.mainet.sfac.domain.AuditBalanceSheetSubParameterEntity;

@Repository
public interface AuditBalanceSheetSubParameterDetRepository extends JpaRepository<AuditBalanceSheetSubParameterDetail, Long> {

	List<AuditBalanceSheetSubParameterDetail> findByAbsSubParamEntity(AuditBalanceSheetSubParameterEntity sub);

}
