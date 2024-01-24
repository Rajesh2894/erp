package com.abm.mainet.rti.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.rti.domain.TbRtiApplicationDetails;
import com.abm.mainet.rti.domain.TbRtiMediaDetails;

public interface RtiRepository extends JpaRepository<TbRtiApplicationDetails, Long> {

	// @Query("select a from TbRtiApplicationDetails a where a.orgId=:orgId and
	// a.appealType='F' and datediff(CURRENT_DATE,a.lModDate)<=90 and a.rtiNo not in
	// (select b.rtiNo from TbRtiApplicationDetails b where b.appealType='S'and
	// b.orgId=:orgId)")
	// @Query("select a from TbRtiApplicationDetails a where a.orgId=:orgId and
	// a.appealType='F' and datediff(CURRENT_DATE,a.lModDate)<=90 and a.rtiNo not in
	// (select b.rtiNo from TbRtiApplicationDetails b where b.appealType='S'and
	// b.orgId=:orgId) and COALESCE(a.finalDispatchMode,0)<>0 and a.dispatchDate is
	// not null" )
	// Query changes for showing second appeal task on forwarded organisation
	@Query("select a from  TbRtiApplicationDetails a ,TbObjectionEntity t where (a.frdOrgId=:frdOrgId or a.orgId=:orgId ) and t.apmApplicationId=a.apmApplicationId and t.objectionStatus like '%Completed%' and a.appealType='F' and datediff(CURRENT_DATE,a.lModDate)<=90 and a.rtiNo  in (select b.rtiNo from TbRtiApplicationDetails b where b.appealType='S' or b.appealType='F'  and (b.frdOrgId=:frdOrgId  or b.orgId=:orgId)) and COALESCE(a.finalDispatchMode,0)<>0 and a.dispatchDate is not null")
	List<TbRtiApplicationDetails> findByOrgid(@Param("orgId") Long orgId, @Param("frdOrgId") String frdOrgId);

	@Modifying
	@Query("update TbRtiApplicationDetails a set a.rtiDeciRecDate =:rtiDeciRecDate  where a.rtiNo =:rtiNo and a.orgId=:orgId")
	void saveInforamtionReceiveDate(@Param("rtiDeciRecDate") Date rtiDeciRecDate, @Param("rtiNo") String rtiNo,
			@Param("orgId") Long orgId);

	@Query("select l from TbLoiMasEntity l where l.loiNo=:loiNo and l.compositePrimaryKey.orgid=:orgid")
	TbRtiApplicationDetails getLoiIdByLoiNumber(@Param("loiNo") Long Loinumber, @Param("orgid") long orgId);

	@Query("select a from TbRtiApplicationDetails a where  a.rtiNo =:objectionReferenceNumber ")
	TbRtiApplicationDetails getRtiApplicationDetailsByRtiNo(
			@Param("objectionReferenceNumber") String objectionReferenceNumber);

	@Modifying
	@Query("update TbRtiMediaDetails l set l.mediastatus='I' where l.rtiMedId=:rtiId and l.orgId=:orgId")
	int inactiveMedia(@Param("rtiId") Long rtiId, @Param("orgId") Long orgId);
	
	@Query("select l from TbRtiMediaDetails l  where l.rtiMedId=:loiId and l.orgId=:orgId")
	List<TbRtiMediaDetails> getMediaListByLoiId(@Param("loiId") Long loiId, @Param("orgId") Long orgId);

	@Query("select time(now()) from TbRtiApplicationDetails")
	String getCurrentTime();

	@Modifying
	@Query("update TbLoiDetEntity ssa set ssa.loiAmount=:loiDetailsAmt,ssa.loiPrevAmt=:loiPrevAmt,ssa.loiRemarks=:loiRemarks where ssa.compositePrimaryKey.loiDetId =:loiId")
	int updateLoiDetAmount(@Param("loiDetailsAmt") BigDecimal loiDetailsAmt, @Param("loiPrevAmt") BigDecimal loiPrevAmt,
			@Param("loiRemarks") String loiRemarks, @Param("loiId") Long loiId);

	@Modifying
	@Query("update TbLoiMasEntity ssa set ssa.loiAmount=:loiAmount where ssa.loiApplicationId=:loiApplicationId and ssa.loiStatus='A'")
	int updateLoiMasAmount(@Param("loiAmount") BigDecimal loiAmount, @Param("loiApplicationId") Long loiApplicationId);

	@Query("select l from TbLoiMasEntity l where l.loiApplicationId=:applicationId and l.loiStatus='A'")
	TbLoiMasEntity getLoiIdByApplicationNo(@Param("applicationId") Long applicationId);

	// For getting latest Second appeal record Defect #81535
	@Query("select rti from TbRtiApplicationDetails rti where rti.rtiNo=:rtiNo\r\n"
			+ "and rti.apmApplicationId = (select max(cast(rti.apmApplicationId as long)) from \r\n"
			+ "TbRtiApplicationDetails rti  where rtiNo=:rtiNo and appealType='S' )")
	public TbRtiApplicationDetails getLatestDataOfSecondAppeal(@Param("rtiNo") String rtiNo);

}
