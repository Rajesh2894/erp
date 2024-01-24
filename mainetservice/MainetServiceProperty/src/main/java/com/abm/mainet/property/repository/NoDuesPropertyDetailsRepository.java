package com.abm.mainet.property.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.NoDuesPropertyDetailsEntity;

public interface NoDuesPropertyDetailsRepository extends JpaRepository<NoDuesPropertyDetailsEntity, Long> {

	@Query("select m from NoDuesPropertyDetailsEntity m where m.applicationId=:applicationId and m.orgId=:orgId and m.isDeleted='N'")
	List<NoDuesPropertyDetailsEntity> getByApplicationId(@Param("applicationId") Long applicationId,
			@Param("orgId") Long orgId);

	@Query(value = "select count(RD_MODESID) from tb_receipt_mode where RM_RCPTID in ( select RM_RCPTID from tb_receipt_mas a where a.ADDITIONAL_REF_NO=:propNo and a.ORGID=:orgId and a.RM_DATE >=:finFromDate and a.RM_DATE <=:finToDate) and RD_SR_CHK_DATE is null and CPD_FEEMODE in (:modeIds)", nativeQuery = true)
	int getCountPendingChequeClearance(@Param("modeIds") List<Long> modeIds, @Param("propNo") String propNo,
			@Param("orgId") Long orgId, @Param("finFromDate") Date fromDate, @Param("finToDate") Date toDate);

	@Query(value = "select count(RD_MODESID) from tb_receipt_mode where RM_RCPTID in ( select RM_RCPTID from tb_receipt_mas a where a.ADDITIONAL_REF_NO=:propNo and a.ORGID=:orgId and a.flat_no=:flatNo  and a.RM_DATE >=:finFromDate and a.RM_DATE <=:finToDate) and RD_SR_CHK_DATE is null and CPD_FEEMODE in (:modeIds)", nativeQuery = true)
	int getCountPendingChequeClearanceWithFlatNo(@Param("modeIds") List<Long> modeIds, @Param("propNo") String propNo,
			@Param("flatNo") String flatNo, @Param("orgId") Long orgId, @Param("finFromDate") Date fromDate, @Param("finToDate") Date toDate);

}
