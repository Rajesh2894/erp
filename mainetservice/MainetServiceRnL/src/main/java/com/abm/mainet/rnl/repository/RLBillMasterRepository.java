package com.abm.mainet.rnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.ContractInstalmentDetailEntity;
import com.abm.mainet.rnl.domain.RLBillMaster;

public interface RLBillMasterRepository extends CrudRepository<RLBillMaster, Long> {

    List<RLBillMaster> findByContIdAndOrgIdAndPaidFlagAndBmTypeOrderByBillIdAsc(Long contId, Long orgId, String paidFlag,
            String type);

    @Query("select r from RLBillMaster r ,TbTaxMasEntity t where r.taxId =  t.taxId and r.contId = ?1 and r.orgId = ?2 and r.paidFlag = 'N' and r.bmType = 'B' order by t.collSeq,r.conitId asc")
    List<RLBillMaster> findByContIdAndOrgIdOrderByCollSeq(Long contId, Long orgId);

    @Query("select r from RLBillMaster r ,TbTaxMasEntity t where r.taxId =  t.taxId and r.contId = ?1 and r.orgId = ?2 and r.paidFlag = 'N'  order by r.bmType,t.collSeq,r.conitId asc")
    List<RLBillMaster> findByAllContIdAndOrgId(Long contId, Long orgId);

    @Query("select case when count(rl)>0 THEN true ELSE false END from RLBillMaster rl where  rl.conitId =:conitId AND rl.paidFlag='Y' AND rl.orgId=:orgId")
    Boolean checkInstallmentPaidOrNot(@Param("conitId") Long conitId, @Param("orgId") Long orgId);

    @Query("select rl.contId , sum(rl.balanceAmount) from RLBillMaster rl where rl.paidFlag = 'N' AND rl.dueDate <= :date AND contId =:contId AND rl.orgId = :orgId group by rl.contId")
    Object[] getDueAmountByCond(@Param("date") Date date, @Param("contId") Long contId, @Param("orgId") Long orgId);

    @Query("from RLBillMaster rl where rl.paidFlag = 'N' AND rl.dueDate <= :date AND contId =:contId AND rl.orgId = :orgId")
    List<RLBillMaster> fetchDueDetails(@Param("date") Date date, @Param("contId") Long contId, @Param("orgId") Long orgId);
    
    @Query("from ContractInstalmentDetailEntity i where i.contId.contId =?1 and i.conttActive =?2 and i.conitPrFlag =?3 order by i.conitId asc")
	List<ContractInstalmentDetailEntity> findAllAdjRecords(Long contId, String flag, String adjFlag);
    
    @Query("select rl from RLBillMaster rl where  rl.conitId =:conitId AND rl.orgId=:orgId")
    RLBillMaster getRLBillByConitId(@Param("conitId") Long conitId, @Param("orgId") Long orgId);
    
    @Modifying
	@Transactional
	@Query("delete from RLBillMaster rl where rl.conitId =?1")
	void deleteInstalmentRow(Long conitId);

    @Query("select sum(rl.balanceAmount) from RLBillMaster rl ,FinancialYear fin where rl.contId =:contId and rl.paidFlag =:flag and rl.orgId=:orgId and rl.dueDate<= fin.faToDate")
	Double getBalanceAmountByContractId(@Param("contId")Long contId, @Param("orgId")Long orgId, @Param("flag")String flag);

    @Query("select sum(rl.amount) from RLBillMaster rl ,FinancialYear fin where rl.contId =:contId and rl.paidFlag =:flag and rl.orgId=:orgId")
	Double getTotalAmountByContractId(@Param("contId")Long contId, @Param("orgId")Long orgId, @Param("flag")String flag);
    
	@Modifying
    @Query("update RLBillMaster e set e.active=:status, e.updatedBy=:empId,e.updatedDate = CURRENT_DATE where e.contId=:conitId and e.dueDate>=:suspendDate and e.orgId=:orgId")
    int updateSuspendDate(@Param("conitId") Long conitId, @Param("status") String status,@Param("suspendDate") Date suspendDate,@Param("empId") Long empId, @Param("orgId") Long orgId);
	
	@Query(value = "SELECT b.* " +
	        "FROM TB_RL_BILL_MAST b " +
	        "JOIN TB_CONTRACT_MAST c ON b.CONT_ID = c.CONT_ID " +
	        "WHERE b.CONT_ID = :contId " +
	        "AND b.ORGID = :orgId " +
	        "AND b.BM_PAID_FLAG =:paidFlag " +
	        "AND b.BM_TYPE = :type " +
	        "AND (CASE WHEN c.CONT_TERMINATION_DATE IS NULL THEN 1 ELSE b.BM_DUE_DATE < c.CONT_TERMINATION_DATE END) " +
	        "ORDER BY b.BM_BMNO ASC", nativeQuery = true)
	List<RLBillMaster> findUnpaidBills(@Param("contId") Long contId, 
	                                   @Param("orgId") Long orgId,
	                                   @Param("paidFlag") String paidFlag,
	                                   @Param("type") String type);

	
	@Query("SELECT SUM(rl.balanceAmount) " +
	        "FROM RLBillMaster rl, FinancialYear fin, ContractMastEntity c " +
	        "WHERE rl.contId = :contId " +
	        "AND rl.paidFlag = :flag " +
	        "AND rl.orgId = :orgId " +
	        "AND rl.dueDate <= fin.faToDate " +
	        "AND rl.contId = c.contId " +
	        "AND (c.contTerminationDate IS NULL OR rl.dueDate < c.contTerminationDate)")
	Double getBalanceAmountByContractIdDate(@Param("contId") Long contId, 
	                                         @Param("orgId") Long orgId, 
	                                         @Param("flag") String flag);

	
}
