package com.abm.mainet.common.master.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.integration.acccount.domain.AdvanceRequisition;

@Repository
public interface CommonAdvanceEntryRepository
		extends org.springframework.data.repository.PagingAndSortingRepository<AdvanceRequisition, Long> {

	@Query(" select sm.sacHeadId,sm.acHeadCode from " + " AccountHeadSecondaryAccountCodeMasterEntity sm "
			+ " where sm.tbAcPrimaryheadMaster.cpdIdAccountType =:advanceTypeId and sm.tbAcPrimaryheadMaster.cpdIdPayMode =:acountSubType and sm.sacStatusCpdId =:statusId and sm.orgid =:orgId ")
	List<Object[]> getAdvBudgetHeadIds(@Param("statusId") Long statusId, @Param("advanceTypeId") Long advanceTypeId,
			@Param("acountSubType") Long acountSubType, @Param("orgId") Long orgId);

	@Query("select pm.cpdIdPayMode from  AccountHeadPrimaryAccountCodeMasterEntity pm,AccountHeadSecondaryAccountCodeMasterEntity sm \r\n"
			+ "        where pm.primaryAcHeadId = sm.tbAcPrimaryheadMaster.primaryAcHeadId\r\n"
			+ "        and sm.orgid=:orgId and sm.sacHeadId=:id")
	Long getAdvAccountTypeIdByOrgIdAndId(@Param("id") Long id, @Param("orgId") Long orgId);

	@Query("select Sum(a.advAmount) from AdvanceRequisition a where  a.referenceNo =:referenceNo and a.orgid =:orgId")
	BigDecimal getUsedContractAmountByReferenceNumber(@Param("referenceNo") String referenceNumber,
			@Param("orgId") Long orgId);

	@Modifying
	@Query("update AdvanceRequisition a set a.advStatus=:satusFlag where a.advId=:advId")
	void updateAdvanceRequisitionMode(@Param("advId") Long advId, @Param("satusFlag") String satusFlag);

	@Query("select a from AdvanceRequisition a where a.advNo=:arnNumber and a.orgid =:orgId")
	AdvanceRequisition getAdvanceRequisitionByArn(@Param("arnNumber") String arnNumber,@Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE AdvanceRequisition a SET a.billNumber =:billNumber ,a.billDate = CURRENT_DATE where a.advId =:advId")
	void updateBillNumberByAdvId(@Param("billNumber") String billNumber, @Param("advId") Long advId);

}
