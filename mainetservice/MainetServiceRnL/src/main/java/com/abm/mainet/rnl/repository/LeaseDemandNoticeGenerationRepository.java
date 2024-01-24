/**
 * 
 */
package com.abm.mainet.rnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.cfc.objection.domain.NoticeMasterEntity;

/**
 * @author divya.marshettiwar
 *
 */
@Repository
public interface LeaseDemandNoticeGenerationRepository extends JpaRepository<NoticeMasterEntity, Long>{

	@Query(value = "select PM_PROPNO,\r\n" + 
			"(select min(bm_due_date) from tb_rl_bill_mast where cont_id = CM.CONT_ID and bm_paymnet_date is null) from_date,\r\n" + 
			"(select max(bm_due_date) from tb_rl_bill_mast where cont_id = CM.CONT_ID and bm_paymnet_date is null) to_date,\r\n" + 
			"(SELECT sum(bm_balance_amt) FROM tb_rl_bill_mast WHERE CONT_ID = CM.CONT_ID) pending_amount\r\n" + 
			"from tb_rl_estate_mas rem,tb_rl_property_mas rpm,tb_rl_est_contract_mapping recm,tb_contract_mast cm,\r\n" + 
			"tb_contract_part2_detail cpd,tb_vendormaster vm\r\n" + 
			"where rem.ORGID =:orgId\r\n" + 
			"and rem.ES_ACTIVE = 'Y'\r\n" + 
			"and rem.ES_ID = rpm.ES_ID\r\n" + 
			"and rem.ES_ID = recm.ES_ID\r\n" + 
			"and rpm.PROP_ID = recm.PROP_ID\r\n" + 
			"and rpm.PM_PROPNO =:refNo\r\n"+
			"and recm.cont_id = cm.cont_id\r\n" + 
			"and cm.cont_id = cpd.cont_id\r\n" + 
			"and vm.VM_VENDORID = cpd.VM_VENDORID\r\n" + 
			"and (SELECT sum(bm_balance_amt) FROM tb_rl_bill_mast WHERE CONT_ID = CM.CONT_ID) > 0", nativeQuery = true)
    List<Object[]> findByPropertyNo(@Param("orgId") Long orgId,@Param("refNo") String refNo);
    
    @Query(value = "select PM_PROPNO,\r\n" + 
			"(select min(bm_due_date) from tb_rl_bill_mast where cont_id = CM.CONT_ID and bm_paymnet_date is null) from_date,\r\n" + 
			"(select max(bm_due_date) from tb_rl_bill_mast where cont_id = CM.CONT_ID and bm_paymnet_date is null) to_date,\r\n" + 
			"(SELECT sum(bm_balance_amt) FROM tb_rl_bill_mast WHERE CONT_ID = CM.CONT_ID) pending_amount\r\n" + 
			"from tb_rl_estate_mas rem,tb_rl_property_mas rpm,tb_rl_est_contract_mapping recm,tb_contract_mast cm,\r\n" + 
			"tb_contract_part2_detail cpd,tb_vendormaster vm\r\n" + 
			"where rem.ORGID =:orgId\r\n" + 
			"and rem.ES_ACTIVE = 'Y'\r\n" + 
			"and rem.ES_ID = rpm.ES_ID\r\n" + 
			"and rem.ES_ID = recm.ES_ID\r\n" + 
			"and rpm.PROP_ID = recm.PROP_ID\r\n" + 
			"and recm.cont_id = cm.cont_id\r\n" + 
			"and cm.cont_id = cpd.cont_id\r\n" + 
			"and rem.LOC_ID =:locationId\r\n" +
			"and vm.VM_VENDORID = cpd.VM_VENDORID\r\n" + 
			"and (SELECT sum(bm_balance_amt) FROM tb_rl_bill_mast WHERE CONT_ID = CM.CONT_ID) > 0", nativeQuery = true)
    List<Object[]> findByLocationId(@Param("orgId") Long orgId,@Param("locationId") Long locationId);
    
    @Query("select s from NoticeMasterEntity s where s.notTyp=:notTyp and s.dpDeptid=:dpDeptId and s.orgId=:orgId")
    List<NoticeMasterEntity> getSecondReminderNotice(@Param("notTyp") Long notTyp,@Param("dpDeptId") Long dpDeptId, @Param("orgId") Long orgId);
    
    @Query("select s from NoticeMasterEntity s where s.notTyp=:notTyp and s.refNo=:refNo and s.dpDeptid=:dpDeptId and s.orgId=:orgId")
    NoticeMasterEntity getfirstReminderNotice(@Param("notTyp") Long notTyp, @Param("refNo") String refNo, 
    		@Param("dpDeptId") Long dpDeptId, @Param("orgId") Long orgId);
    
    @Query("select s from NoticeMasterEntity s where s.notTyp=:notTyp and s.dpDeptid=:dpDeptId and s.orgId=:orgId")
    List<NoticeMasterEntity> getfirstReminderNoticeByNoticeType(@Param("notTyp") Long notTyp, @Param("dpDeptId") Long dpDeptId, @Param("orgId") Long orgId);
}
