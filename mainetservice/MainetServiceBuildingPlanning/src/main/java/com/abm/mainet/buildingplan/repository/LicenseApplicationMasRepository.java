package com.abm.mainet.buildingplan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.buildingplan.domain.LicenseApplicationFeesMasterEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetHistEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandSurroundingsEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;

public interface LicenseApplicationMasRepository extends JpaRepository<LicenseApplicationMaster, Long> {

	@Query("Select mas from LicenseApplicationMaster mas where mas.orgId=:orgId and mas.createdBy=:userId order by mas.tcpLicMstrId desc)")
	List<LicenseApplicationMaster> getNewLicenseSummaryData(@Param("orgId") Long orgId, @Param("userId") Long userId);
	
	LicenseApplicationMaster findByTcpLicMstrId(Long tcpLicMstrId);
	
	@Query("SELECT lam FROM LicenseApplicationMaster lam WHERE lam.applicationNo = :applicationNo AND lam.orgId = :orgId")
    LicenseApplicationMaster findByApplicationNoAndOrgId(@Param("applicationNo") Long applicationNo, @Param("orgId") Long orgId);
	
	@Query("SELECT r FROM LicenseApplicationLandAcquisitionDetHistEntity r WHERE  r.licenseApplicationMaster.tcpLicMstrId in (select l.tcpLicMstrId from LicenseApplicationMaster l where l.applicationNo=:applicationNo) and r.taskId=:taskId and r.scrnFlag=:scrnFlag")
	List<LicenseApplicationLandAcquisitionDetHistEntity> getApplicationNotingDetail(@Param("applicationNo") Long applicationNo,@Param("taskId") Long taskId,@Param("scrnFlag") String scrnFlag);
	
	@Modifying
    @Query("UPDATE LicenseApplicationMaster m SET m.loaDcRemark =:loaDcRemark where m.applicationNo =:applicationNo")
    void updateLoaRemark(@Param("applicationNo") Long applicationNo, @Param("loaDcRemark") String loaDcRemark);
	
	@Query("SELECT lam.loaDcRemark FROM LicenseApplicationMaster lam WHERE lam.applicationNo = :applicationNo AND lam.orgId = :orgId")
    String getLaoRemark(@Param("applicationNo") Long applicationNo, @Param("orgId") Long orgId);

	List<LicenseApplicationMaster> findByKhrsDistAndKhrsDevPlanAndKhrsZoneAndKhrsSecAndKhrsThesilAndKhrsRevEstAndKhrsHadbastAndKhrsMustilAndKhrsKillaAndApplicationNoEServiceIsNotNull(
			Long khrsDist, Long khrsDevPlan, Long khrsZone, Long khrsSec, String khrsThesil, String khrsRevEst,
			String khrsHadbast, String khrsMustil, String khrsKilla);
	
	
	@Query("SELECT r FROM LicenseApplicationFeesMasterEntity r WHERE  r.licenseApplicationMaster.tcpLicMstrId in (select l.tcpLicMstrId from LicenseApplicationMaster l where l.applicationNo=:applicationNo)")
    List<LicenseApplicationFeesMasterEntity> getFeeAndCharges(@Param("applicationNo") Long applicationNo);
	
	@Query("SELECT s FROM LicenseApplicationLandSurroundingsEntity s WHERE s.landSchId.landSchId in (select l.landSchId from LicenseApplicationLandSchedule l where l.licenseApplicationMaster.tcpLicMstrId in (select m.tcpLicMstrId from LicenseApplicationMaster m where m.applicationNo=:applicationNo))")
    List<LicenseApplicationLandSurroundingsEntity> getSurroundingDetail(@Param("applicationNo") Long applicationNo);
	

}
