package com.abm.mainet.council.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.council.domain.CouncilProposalMasterEntity;

@Repository
public interface CouncilProposalMasterRepository extends JpaRepository<CouncilProposalMasterEntity, Long> {

    @Modifying
    @Query("UPDATE CouncilProposalMasterEntity p SET p.agendaId =:agendaId  where p.proposalId in (:proposalIds)")
    void updateTheAgendaIdByProposalIds(@Param("proposalIds") String proposalIds[], @Param("agendaId") Long agendaId);

    List<CouncilProposalMasterEntity> findAllByAgendaId(Long agendaId);

    @Modifying
    @Query("UPDATE CouncilProposalMasterEntity p SET p.proposalStatus =:flag where p.proposalId =:proposalId ")
    void updateProposalStatus(@Param("proposalId") Long proposalId, @Param("flag") String flag);

    @Query("SELECT p FROM CouncilProposalMasterEntity p WHERE p.orgId =:orgId and p.proposalNo = :proposalNo")
    CouncilProposalMasterEntity findProposalByProposalNo(@Param("proposalNo") String proposalNo, @Param("orgId") Long orgId);

    @Query("SELECT p FROM CouncilProposalMasterEntity p WHERE p.proposalStatus =:proposalStatus and p.agendaId = :agendaId")
    List<CouncilProposalMasterEntity> findProposalsByStatusAndAgendaId(@Param("proposalStatus") String proposalStatus,
            @Param("agendaId") Long agendaId);

    @Query("SELECT p FROM CouncilProposalMasterEntity p WHERE p.proposalDepId =:proposalDepId and p.orgId = :orgId")
    List<CouncilProposalMasterEntity> findProposalsByDeptIdAndOrgId(@Param("proposalDepId") Long proposalDepId,
            @Param("orgId") Long orgId);

    @Query("SELECT p FROM CouncilProposalMasterEntity p WHERE p.proposalDepId =:proposalDepId and p.orgId = :orgId and p.proposalId in (:proposalIds) ")
    List<CouncilProposalMasterEntity> findProposalsByProposalIdOfMOM(@Param("proposalDepId") Long proposalDepId,
            @Param("orgId") Long orgId, @Param("proposalIds") List<Long> proposalIds);
    
    @Modifying
    @Query("UPDATE CouncilBudgetDetEntity y SET y.yeActive ='N',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.cbId in (:removeYearIdList)")
    void iactiveYearsByIds(@Param("removeYearIdList") List<Long> removeYearIdList, @Param("updatedBy") Long updatedBy);
    
    @Query("SELECT c.committeeType FROM CouncilProposalMasterEntity c WHERE c.committeeType=:committeeType and c.orgId = :orgId")
    Long fetchCommiteeId(@Param("committeeType") Long committeeType, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("UPDATE CouncilProposalMasterEntity p SET p.subNo =:subNo where p.proposalId =:proposalId ")
    void updateSerialNo(@Param("proposalId") Long proposalId, @Param("subNo") String subNo);
    
    @Query("SELECT SUM(d.yeBugAmount) " +
    		"FROM CouncilBudgetDetEntity d, CouncilProposalMasterEntity c " +
    		"WHERE c.proposalId = d.councilMasEntity.proposalId " +
    		"AND c.proposalStatus='A' " +
    		"AND d.sacHeadId = :sacHeadId " +
    		"AND d.orgId = :orgId " +
    		"AND c.proposalDate >= :startDate ")
    BigDecimal fetchYeBugAmount(@Param("sacHeadId") Long sacHeadId, @Param("orgId") Long orgId, @Param("startDate") Date startDate);
 
    @Query(value = "select PE.PR_EXPENDITUREID,\r\n" + 
    		"       PE.ORGINAL_ESTAMT,\r\n" + 
    		"       PE.REVISED_ESTAMT,\r\n" + 
    		"       sum(BMS.BCH_CHARGES_AMT), PE.DP_DEPTID, PE.CUR_YR_SPAMT,PE.NXT_YR_SPAMT \r\n" + 
    		"  from TB_AC_PROJECTED_EXPENDITURE PE\r\n" + 
    		"  INNER JOIN   TB_AC_BUDGETCODE_MAS  BM ON PE.BUDGETCODE_ID = BM.BUDGETCODE_ID\r\n" + 
    		"  INNER JOIN   TB_AC_SECONDARYHEAD_MASTER  SM ON SM.SAC_HEAD_ID = BM.SAC_HEAD_ID\r\n" + 
    		"  INNER JOIN   TB_AC_FUNCTION_MASTER  FM ON SM.FUNCTION_ID = FM.FUNCTION_ID\r\n" + 
    		"  LEFT JOIN  (Select BMS.FIELD_ID,BE.BUDGETCODE_ID, BMS.DP_DEPTID,sum(BE.BCH_CHARGES_AMT) BCH_CHARGES_AMT\r\n" + 
    		"  From TB_AC_BILL_MAS BMS,TB_AC_BILL_EXP_DETAIL BE\r\n" + 
    		"  Where BE.BM_ID=BMS.BM_ID  AND BMS.CHECKER_AUTHO='Y' AND  BMS.BM_DEL_FLAG IS NULL  \r\n" + 
    		"  AND (BMS.BM_ENTRYDATE between :fromDate and :toDate)\r\n" + 
    		"  Group by BMS.FIELD_ID,BE.BUDGETCODE_ID, BMS.DP_DEPTID)BMS ON\r\n" + 
    		"  PE.BUDGETCODE_ID = BMS.BUDGETCODE_ID AND  BMS.DP_DEPTID=PE.DP_DEPTID AND  BMS.FIELD_ID=PE.FIELD_ID             \r\n" + 
    		" where PE.ORGID =:orgId\r\n" + 
    		"   AND PE.FA_YEARID =:finYearId\r\n" + 
    		"   AND PE.FIELD_ID =:fieldId\r\n" + 
    		"   AND PE.BUDGETCODE_ID =:budgetCodeId\r\n" + 
    		"    GROUP BY PE.PR_EXPENDITUREID,\r\n" + 
    		"          PE.ORGINAL_ESTAMT,\r\n" + 
    		"          PE.REVISED_ESTAMT, BMS.DP_DEPTID\r\n", nativeQuery = true)

    List<Object[]> getExpenditureDetailsForBillEntryFormViewWithFieldId(@Param("orgId") Long orgId,
            @Param("finYearId") Long finYearId, @Param("budgetCodeId") Long budgetCodeId,
            @Param("fromDate") Date fromDate, @Param("toDate") Date toDate,@Param("fieldId") Long fieldId);
    
    
    @Query(value = "select bm.BUDGETCODE_ID,bm.BUDGET_CODE from TB_AC_BUDGETCODE_MAS bm where bm.SAC_HEAD_ID=:sacHeadId", nativeQuery = true)
    Object[] getBudgetId(@Param("sacHeadId") Long sacHeadId);
    
    

}
