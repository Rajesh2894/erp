package com.abm.mainet.common.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;

/**
 * Repository : TbCfcApplicationMst.
 */
public interface TbCfcApplicationMstJpaRepository extends PagingAndSortingRepository<TbCfcApplicationMstEntity, Long> {

	@Query("select count(cfcApplicationMst.tbServicesMst.smServiceId) from TbCfcApplicationMstEntity cfcApplicationMst "
			+ " where cfcApplicationMst.tbServicesMst.smServiceId = :serviceId and cfcApplicationMst.tbOrganisation.orgid = :orgId")
	Long checkForTransactionExist(@Param("serviceId") Long serviceId, @Param("orgId") Long orgId);

	@Query("select appMst from TbCfcApplicationMstEntity appMst where appMst.tbOrganisation.orgid=:orgid")
	List<TbCfcApplicationMstEntity> getExistApplicationMstForDelete(@Param("orgid") Long orgid);

	@Query("select p.apmFname,p.apmMname,p.apmLname from  TbCfcApplicationMstEntity p where p.apmApplicationId=?1 and p.tbOrganisation.orgid=?2")
	List<Object[]> getApplicantInfo(Long applicationNo, Long orgId);

	@Query("select p.apmFname,p.apmMname,p.apmLname from  TbCfcApplicationMstEntity p where p.apmApplicationId=?1 and p.tbOrganisation.orgid=?2")
	List<TbCfcApplicationMstEntity> getApplicantAllInfo(Long applicationNo, Long orgId);

	@Query("select a from TbCfcApplicationMstEntity a join fetch a.tbServicesMst s where a.apmApplicationId=:apmapplicationid")
	Optional<TbCfcApplicationMstEntity> findByApmApplicationId(@Param("apmapplicationid") Long apmapplicationid);

	@Query("select a from TbCfcApplicationMstEntity a join fetch a.tbServicesMst s where a.tbOrganisation.orgid =:orgId AND s.smServiceId in (:serviceIds) and a.apmApplSuccessFlag !='C' "
			+ "AND a.apmApplicationId in (select ca.apmApplicationId from CFCApplicationAddressEntity ca where ca.apaMobilno =:mobileNo)")
	List<TbCfcApplicationMstEntity> fetchCfcApplicationsByIds(@Param("serviceIds") List<Long> serviceIds,
			@Param("orgId") Long orgId, @Param("mobileNo") String mobileNo);

	@Query("select a from TbCfcApplicationMstEntity a join fetch a.tbServicesMst s where a.tbOrganisation.orgid =:orgId AND s.smServiceId in (:serviceIds)")
	List<TbCfcApplicationMstEntity> fetchCfcApplicationsByServiceIds(@Param("serviceIds") List<Long> serviceIds,
			@Param("orgId") Long orgId);

	@Query("select p.apmPayStatFlag from  TbCfcApplicationMstEntity p where p.apmApplicationId=?1 and p.tbOrganisation.orgid=?2")
	String getApplicantPymentStatusByApplNo(Long applicationNo, Long orgId);

	@Query("select p.refNo from  TbCfcApplicationMstEntity p where p.apmApplicationId=:applicationId and p.tbOrganisation.orgid=:orgId ")
	String getLicenseNoByAppIdAndOrgId(@Param("applicationId") Long applicationId, @Param("orgId") Long orgId);

	@Query("select a from TbCfcApplicationMstEntity a where a.tbServicesMst.smServiceId =:serviceId and a.refNo=:refNo")
	TbCfcApplicationMstEntity fetchCfcApplicationsByServiceId(@Param("serviceId") Long serviceId,
			@Param("refNo") String refNo);
	
	@Query("select a.apmApplicationId from TbCfcApplicationMstEntity a where a.tbServicesMst.smServiceId =:serviceId")
	List<Long> getApplicationIdsByServiceId(@Param("serviceId") Long serviceId);
	
	@Query("select a.refNo from TbCfcApplicationMstEntity a where a.tbServicesMst.smServiceId =:serviceId")
	List<String> getrefNosByServiceId(@Param("serviceId") Long serviceId);
	
	@Query("select a.refNo from TbCfcApplicationMstEntity a where a.apmApplicationId =:apmApplicationId")
	String getrefNosByAppId(@Param("apmApplicationId") Long apmApplicationId);
	//D#125111
	@Query("select count(a) from TbCfcApplicationMstEntity a where (a.apmApplicationId =:apmApplicationId and a.rejctionNo is not null) or (a.apmApplicationId =:apmApplicationId and a.tbServicesMst.smServiceId=:serviceId))")
	int checkApplicationIsRejectedOrNot(@Param("apmApplicationId") Long apmApplicationId,@Param("serviceId") Long serviceId);
	
	@Query("select a.rejctionNo from TbCfcApplicationMstEntity a where a.apmApplicationId =:apmApplicationId and a.tbOrganisation.orgid=:orgId")
	long getRejctionNoByAppId(@Param("apmApplicationId") Long apmApplicationId, @Param("orgId") Long orgId);

}
