package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.AuditedBalanceSheetInfoDetailEntity;
import com.abm.mainet.sfac.domain.FPOProfileManagementMaster;

@Repository
public interface AuditedBalanceSheetInfoDetRepository extends JpaRepository<AuditedBalanceSheetInfoDetailEntity, Long>{

	List<AuditedBalanceSheetInfoDetailEntity> findByFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileManagemntMaster);

	List<AuditedBalanceSheetInfoDetailEntity> findByFpoProfileMgmtMasterAndDocumentName(
			FPOProfileManagementMaster fpoProfileManagemntMaster, String status);

	@Modifying
	@Query("UPDATE AuditedBalanceSheetInfoDetailEntity Abs SET Abs.documentName ='D', Abs.updatedBy =:updatedBy, Abs.updatedDate = CURRENT_DATE "
			+ "WHERE Abs.auditedBalanceSheetId in (:removeIds) ")
	void deActiveABSInfo(@Param("removeIds") List<Long> removeIds, @Param("updatedBy") Long updatedBy);

}
