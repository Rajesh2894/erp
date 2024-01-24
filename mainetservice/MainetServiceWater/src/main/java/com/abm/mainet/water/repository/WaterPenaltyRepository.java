/**
 * 
 */
package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.water.domain.WaterPenaltyEntity;
import com.abm.mainet.water.domain.WaterPenaltyHistoryEntity;

/**
 * @author cherupelli.srikanth
 *
 */
@Repository
public interface WaterPenaltyRepository extends CrudRepository<WaterPenaltyEntity, Long> {

    @Query("SELECT m from WaterPenaltyEntity m where m.connNo=:connNo and m.finYearId=:finYearId and m.orgId=:orgId")
    WaterPenaltyEntity getWaterPenaltyByCCnNoByFinYearId(@Param("connNo") String ccnNo,
	    @Param("finYearId") Long finYearId, @Param("orgId") Long orgId);
    
    @Query("SELECT m FROM WaterPenaltyEntity m WHERE m.connNo  IN (:conIds) AND m.orgId=:orgId")
    List<WaterPenaltyEntity> getWaterPenaltyByConIds(@Param("conIds") List<String> conIds, @Param("orgId") Long orgId);
    
    WaterPenaltyEntity findByBmIdNoAndOrgId(Long bmIdNo, Long orgId);
    
    @Query("SELECT m from WaterPenaltyEntity m where m.connNo=:connNo and m.orgId=:orgId")
    List<WaterPenaltyEntity> getWaterPenaltyByCCnNo(@Param("connNo") String ccnNo,@Param("orgId") Long orgId);

    @Query("SELECT m from WaterPenaltyHistoryEntity m where m.bmIdNo=:bmIdNo and m.orgId=:orgId")
    List<WaterPenaltyHistoryEntity> findHistoryByBmIdNoAndOrgId(@Param("bmIdNo") Long bmIdNo, @Param("orgId") Long orgId);
    
    @Query("SELECT m from WaterPenaltyHistoryEntity m where m.connNo=:csIdn and m.orgId=:orgId")
    List<WaterPenaltyHistoryEntity> findHistoryByCsIdnAndOrgId(@Param("csIdn") String csIdn, @Param("orgId") Long orgId);
    
    @Modifying
    @Query("update WaterPenaltyEntity m set m.activeFlag='I' where m.bmIdNo >=:bmIdno and m.orgId=:orgId ")
    void inactivePenaltyByBmIdno(@Param("orgId") Long orgId, @Param("bmIdno") Long bmIdno);
    
    @Modifying
    @Query("update WaterPenaltyHistoryEntity m set m.activeFlag='I' where m.bmIdNo >=:bmIdno and m.orgId=:orgId ")
    void inactivePenaltyHistoryByBmIdno(@Param("orgId") Long orgId, @Param("bmIdno") Long bmIdno);
    
    @Query("SELECT m from WaterPenaltyEntity m where m.connNo=:csIdn and m.orgId=:orgId and ((m.bmIdNo >=:bmIdno) or (m.currBmIdNo is not null and m.currBmIdNo >=:bmIdno))")
    List<WaterPenaltyEntity> findByBmIdnoAndOrgId(@Param("csIdn") String csIdn, @Param("orgId") Long orgId, @Param("bmIdno") Long bmIdno);
    
    @Query("SELECT m from WaterPenaltyEntity m where m.connNo=:csIdn and m.orgId=:orgId and m.billGenAmount is not null and m.billGenAmount > 0)")
    List<WaterPenaltyEntity> findByCurrGenAmount(@Param("csIdn") String csIdn, @Param("orgId") Long orgId);
    
    @Query("SELECT m FROM WaterPenaltyEntity m WHERE m.bmIdNo  IN (:bmIdNo) AND m.orgId=:orgId")
    List<WaterPenaltyEntity> getWaterPenaltyByBmIdNos(@Param("bmIdNo") List<Long> bmIdNo, @Param("orgId") Long orgId);

    @Query("SELECT m FROM WaterPenaltyEntity m where m.connNo=:csIdn and m.bmIdNo =:bmIdno and m.orgId=:orgId")
    WaterPenaltyEntity getWaterPenaltyByBmIdNo(@Param("csIdn") String csIdn, @Param("bmIdno") Long bmIdno, @Param("orgId") Long orgId);
 
    @Query("SELECT m from WaterPenaltyHistoryEntity m where m.connNo=:csIdn and m.bmIdNo=:bmIdNo and m.orgId=:orgId order by m.penaltyHisId desc")
    List<WaterPenaltyHistoryEntity> findHistoryByBmIdNoCsIdnAndOrgId(@Param("csIdn") String csIdn, @Param("bmIdNo") Long bmIdNo, 
		@Param("orgId") Long orgId);
    
}
