package com.abm.mainet.audit.repository;

import java.util.Date;
import java.util.List;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.audit.domain.AuditParaEntryEntity;

@Repository
public interface IAuditParaEntryRepository extends JpaRepository<AuditParaEntryEntity, Long> {
	// Not needed, write in DAO  Write below only if internally needed by code

	// By auditType
	//List<AuditParaEntryEntity> findAllByauditType(long auditType);

	// By auditDeptId
	//List<AuditParaEntryEntity> findAllByauditDeptId(long auditDeptId);

	// By auditStatus
	//List<AuditParaEntryEntity> findAllByauditStatus(long auditStatus);

	// By auditTypeAndauditDeptId
	//List<AuditParaEntryEntity> findAllByauditTypeAndauditDeptId(long auditType, long auditDeptId);
	
	AuditParaEntryEntity findByAuditParaCodeAndOrgId(String auditParaCode,Long orgId);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditWfStatus =:flag where p.auditParaCode =:auditParaCode")
    void updateAuditWfStatus(@Param("auditParaCode") String auditParaCode, @Param("flag") String flag);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditWfStatus =:flag where p.auditParaId =:auditParaCode")
    void updateAuditWfStatusWithParaID(@Param("auditParaCode") Long auditParaID, @Param("flag") String flag);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditParaStatus =:flag where p.auditParaCode =:auditParaCode")
    void updateAuditParaStatus(@Param("auditParaCode") String auditParaCode, @Param("flag") Long flag);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditParaStatus =:flag where p.auditParaId =:auditParaID")
    void updateAuditParaStatusbyID(@Param("auditParaID") Long auditParaID, @Param("flag") Long flag);
	
	
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditParaChk =:flag where p.auditParaCode =:auditParaCode")
    void updateAuditParaChk(@Param("auditParaCode") String auditParaCode, @Param("flag") Long flag);

	List<AuditParaEntryEntity> findByOrgId(long orgId);
	
	List<AuditParaEntryEntity> findByOrgIdOrderByAuditParaIdDesc(long orgId);
	
	@Query(value="select count(*) from tb_workflow_task where reference_id=:refId and TASK_STATUS = 'PENDING' and orgid=:orgId and workflow_req_id = :wfId" ,nativeQuery=true)
	int findWorkflowTaskbyAuditParaCode(@Param("refId") String refId, @Param("orgId") Long orgId, @Param("wfId") Long wfId);
	
	@Query(value="select * from tb_workflow_task where reference_id=:refId and TASK_STATUS = 'PENDING' and orgid=:orgId ",nativeQuery = true)
	List<Object[]> findWorkFlowTaskByRefId(@Param("refId")String refId,@Param("orgId") Long orgId);

	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditParaStatus =:flag , p.auditDate=:auditDate where p.auditParaCode =:auditParaCode")
    void updateAuditParaStatusAndDate(@Param("auditParaCode") String auditParaCode, @Param("flag") Long flag,@Param("auditDate") Date auditDate);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.auditParaStatus =:flag , p.auditDate=:auditDate where p.auditParaId =:auditParaCode")
    void updateAuditParaStatusAndDateWithID(@Param("auditParaCode") Long auditParaCode, @Param("flag") Long flag,@Param("auditDate") Date auditDate);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.subUnitClosed =:subUnitClosed where p.auditParaId =:auditParaCode")
    void updateAuditParaSubUnitWithID(@Param("auditParaCode") Long auditParaCode, @Param("subUnitClosed") Integer subUnitClosed);
	
	@Modifying
    @Query("UPDATE AuditParaEntryEntity p SET p.subUnitCompDone =:subUnitCompDone , p.subUnitCompPending =:subUnitCompPending where p.auditParaId =:auditParaCode")
    void updateAuditParaSubDoneAndPendingWithID(@Param("auditParaCode") Long auditParaCode, @Param("subUnitCompDone") String subUnitCompDone, @Param("subUnitCompPending") String subUnitCompPending);
	
	@Query(value =
		    "SELECT DISTINCT\r\n" + 
		    "    AUDIT_PARA_CODE,\r\n" + 
		    "    AUDIT_PARA_SUB,\r\n" + 
		    "    auditAppendix,\r\n" + 
		    "    DEPT.DP_DEPTDESC AS department,\r\n" + 
		    "    ADT.AUDIT_PARA_YEAR,\r\n" + 
		    "    ADT.AUDIT_PARA_STATUS,\r\n" + 
		    "    fy.FA_FROMDATE,\r\n" + 
		    "    fy.FA_TODATE\r\n" + 
		    "FROM\r\n" + 
		    "    tb_adt_postadt_mas ADT\r\n" + 
		    "INNER JOIN\r\n" + 
		    "    tb_department DEPT ON DEPT.DP_DEPTID = ADT.AUDIT_DEPT_ID\r\n" + 
		    "INNER JOIN\r\n" + 
		    "    TB_FINANCIALYEAR fy ON fy.fa_yearid = ADT.AUDIT_PARA_YEAR\r\n" + 
		    "INNER JOIN\r\n" + 
		    "    tb_workflow_task WT ON ADT.AUDIT_PARA_CODE = WT.REFERENCE_ID\r\n" + 
		    "WHERE\r\n" + 
		    "    ADT.ORGID = :orgId\r\n" + 
		    "    AND ADT.AUDIT_DEPT_ID LIKE CASE WHEN :auditDeptId = 0 THEN '%' ELSE :auditDeptId END\r\n" + 
		    "    AND ADT.AUDIT_PARA_YEAR = :auditParaYr\r\n" + 
		    "    AND DATE(AUDIT_ENTRY_DATE) BETWEEN STR_TO_DATE(:fromDate, '%d/%m/%Y') AND STR_TO_DATE(:toDate, '%d/%m/%Y')\r\n" + 
		    "    AND WT.WFTASK_ID IN (\r\n" + 
		    "        SELECT MIN(WFTASK_ID)\r\n" + 
		    "        FROM tb_workflow_task\r\n" + 
		    "        WHERE TASK_ID != -1\r\n" + 
		    "        AND TASK_STATUS = 'PENDING'\r\n" + 
		    "        AND WFTASK_ID IN (\r\n" + 
		    "            SELECT MIN(WFTASK_ID)\r\n" + 
		    "            FROM tb_workflow_task WT\r\n" + 
		    "            WHERE TASK_ID != -1\r\n" + 
		    "            AND WT.WFTASK_ID NOT IN (\r\n" + 
		    "                SELECT MIN(WFTASK_ID)\r\n" + 
		    "                FROM tb_workflow_task\r\n" + 
		    "                WHERE TASK_ID != -1\r\n" + 
		    "                AND TASK_STATUS = 'COMPLETED'\r\n" + 
		    "                AND WFTASK_ID IN (\r\n" + 
		    "                    SELECT MIN(WFTASK_ID)\r\n" + 
		    "                    FROM tb_workflow_task\r\n" + 
		    "                    WHERE TASK_ID != -1\r\n" + 
		    "                    GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "                )\r\n" + 
		    "                GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "            )\r\n" + 
		    "            AND WT.WFTASK_ID NOT IN (\r\n" + 
		    "                SELECT MIN(WFTASK_ID)\r\n" + 
		    "                FROM tb_workflow_task\r\n" + 
		    "                WHERE TASK_ID != -1\r\n" + 
		    "                AND TASK_STATUS = 'PENDING'\r\n" + 
		    "                AND WFTASK_ID IN (\r\n" + 
		    "                    SELECT MIN(WFTASK_ID)\r\n" + 
		    "                    FROM tb_workflow_task\r\n" + 
		    "                    WHERE TASK_ID != -1\r\n" + 
		    "                    GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "                )\r\n" + 
		    "                GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "            )\r\n" + 
		    "            GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "        )\r\n" + 
		    "        GROUP BY WORKFLOW_REQ_ID\r\n" + 
		    "    )\r\n" + 
		    "ORDER BY DEPT.DP_DEPTDESC ASC",
		    	    nativeQuery = true)
	List<Object[]> getAuditReportData(@Param("auditDeptId")Long auditDeptId, @Param("auditParaYr")Long auditParaYr, @Param("fromDate")String fromDate, @Param("toDate")String toDate, @Param("orgId") Long orgId);
}
