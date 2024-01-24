package com.abm.mainet.bnd.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bnd.domain.BirthCertificateEntity;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;

@Repository("bndBirthCertificate")
public interface BirthCertificateRepository extends JpaRepository<BirthCertificateEntity, Long>{
	
	
	@Query("select b from BirthCertificateEntity b where b.apmApplicationId=:applicationId and b.orgId=:orgId")
	public BirthCertificateEntity findData(@Param("applicationId") Long applicationId , @Param("orgId")Long orgId );
	
	@Modifying
	   @Query("update BirthCertificateEntity d set d.birthWfStatus=:wfStatus,d.brStatus=:brStatus where d.brRId=:brId and d.orgId=:orgId")
	   void updateWorkFlowStatus(@Param("brId")Long brId,@Param("orgId")Long orgId,@Param("wfStatus")String wfStatus,@Param("brStatus")String brStatus);
	
	@Query("FROM TbCfcApplicationMstEntity where orgId =:orgId and tbServicesMst.smServiceId =:serviceId")
    List<TbCfcApplicationMstEntity> loadSummaryData(@Param("orgId") Long orgId, @Param("serviceId") Long serviceId);

    @Query("FROM  CFCApplicationAddressEntity where orgId.orgid=:orgId and apmApplicationId=:apmApplicationNo")
    CFCApplicationAddressEntity getApplicationAddressData(@Param("orgId") Long orgId,
            @Param("apmApplicationNo") Long apmApplicationNo);

	@Modifying
	@Query("update BirthCertificateEntity d set d.birthWfStatus=:wfStatus,d.brStatus=:brStatus where d.brRId=:brRId and d.orgId=:orgId")
	public void updateWorkFlowBirthReg(@Param("brRId") Long brRId, @Param("orgId") Long orgId,
			@Param("wfStatus") String wfStatus, @Param("brStatus") String brStatus);	
	
	@Query("select b from BirthCertificateEntity b where b.brRId=:brRId")
	public List<BirthCertificateEntity>  findCertData(@Param("brRId") Long brRId);
	
	@Query(value ="select a.RM_RCPTNO,a.RM_DATE,a.RM_RECEIVEDFROM,a.RM_AMOUNT from tb_receipt_mas a\r\n" + 
			"LEFT JOIN TB_RTS_BIRTHREGCERT BR ON BR.APM_APPLICATION_ID=a.APM_APPLICATION_ID\r\n" + 
			"LEFT JOIN tb_rts_deathregcert  DR ON DR.APPLICATION_NO=a.APM_APPLICATION_ID\r\n" + 
			"Where (\r\n" + 
			"COALESCE(a.RM_RCPTNO,0)=(case when COALESCE(:rmRcptNO,0)=0 then COALESCE(a.RM_RCPTNO,0) else COALESCE(:rmRcptNO,0) end) And\r\n" + 
			"COALESCE(date(a.RM_DATE),'X')=(case when COALESCE(:rmDate,'X')='X' then COALESCE(date(a.RM_DATE),'X') else COALESCE(:rmDate,'X') end) And\r\n" + 
			"COALESCE(date(BR.BR_DOB),'X')=(case when COALESCE(:birthDate,'X')='X' then COALESCE(date(BR.BR_DOB),'X') else COALESCE(:birthDate,'X') end) And\r\n" + 
			"COALESCE(date(DR.DR_DOD),'X')=(case when COALESCE(:deathDate,'X')='X' then COALESCE(date(DR.DR_DOD),'X') else COALESCE(:deathDate,'X') end) And\r\n" +
			"COALESCE(BR.BR_CHILDNAME,'X')=(case when COALESCE(:brChildName,'X')='X' then COALESCE(BR.BR_CHILDNAME,'X') else COALESCE(:brChildName,'X') end) And\r\n" +
			"COALESCE(DR.DR_DECEASEDNAME,'X')=(case when COALESCE(:deathName,'X')='X' then COALESCE(DR.DR_DECEASEDNAME,'X') else COALESCE(:deathName,'X') end)  )",nativeQuery = true)
	public List<Object[]> getBirthReceiptData(@Param("birthDate") String birthDate, @Param("rmDate") String rmDate,
			@Param("brChildName") String brChildName, @Param("rmRcptNO") Long rmRcptNO,@Param("deathDate") String deathDate,@Param("deathName") String deathName);

	public List<BirthCertificateEntity> findByBrDobAndOrgIdAndBrStatus(Date brDob,Long orgId, String brStatus);
	
	public List<BirthCertificateEntity> findByApmApplicationIdAndBrDobAndOrgIdAndBrStatus(Long applicationId, Date brDob, Long orgId, String brStatus);
	
	public BirthCertificateEntity findByApmApplicationIdAndOrgIdAndBrStatus(Long applicationId, Long orgId, String brStatus);
}
