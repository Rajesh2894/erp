/**
 * 
 */
package com.abm.mainet.sfac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.sfac.domain.FPOMasterEntity;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface FPOMasterRepository extends JpaRepository<FPOMasterEntity, Long> {

	/**
	 * @param lookUpId
	 * @param orgId
	 * @return
	 */

	@Query(value = "select COD_OTHERS from tb_comparent_det where COD_ID=:codId and ORGID=:orgId", nativeQuery = true)
	String getPrefixOtherValue(@Param("codId") Long codId, @Param("orgId") Long orgId);

	/**
	 * @param sdb3
	 * @param lookUpId
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from FPOMasterEntity f where f.sdb3=:sdb3 and f.allocationCategory=:lookUpId")
	Boolean getFpoDetailsBySdbId(@Param("sdb3") Long sdb3,@Param("lookUpId") Long lookUpId);

	/**
	 * @param orgid
	 * @return
	 */
	@Query("Select f from FPOMasterEntity f where f.orgId=:orgid and (f.appStatus = 'A' OR f.appStatus is null)")
	List<FPOMasterEntity> getFpoDetByOrgId(@Param("orgid") Long orgid);

	/**
	 * @param orgTypeId
	 * @return
	 */
	@Query("Select f.fpoName from FPOMasterEntity f where f.fpoId=:orgTypeId")
	String fetchNameById(@Param("orgTypeId") Long orgTypeId);

	/**
	 * @return
	 */
	@Query("Select f.fpoId,f.fpoName,f.fpoRegNo from FPOMasterEntity f ")
	List<Object[]> findAllFpo();

	/**
	 * @param fpoId
	 * @return
	 */
	@Query("Select f from FPOMasterEntity f  where f.fpoId=:fpoId")
	FPOMasterEntity getDetailById(@Param("fpoId") Long fpoId);

	/**
	 * @param masId
	 * @return
	 */
	@Query("Select f from FPOMasterEntity f, CBBOMasterEntity c  where c.cbboId=:masId and f.cbboId=c.cbboId")
	List<FPOMasterEntity> findFpoByMasId(@Param("masId") Long masId);

	/**
	 * @param frmFPORegNo
	 * @return
	 */
	@Query("Select f.fpoName from FPOMasterEntity f  where f.fpoRegNo=:frmFPORegNo")
	String getFpoNameBy(@Param("frmFPORegNo") String frmFPORegNo);

	/**
	 * @param masId
	 * @param emploginname
	 * @return
	 */
	@Query("Select f.fpoId,f.fpoName,f.fpoRegNo from FPOMasterEntity f,CBBOMasterEntity c where c.cbboId=:masId and f.cbboId=c.cbboId")
	List<Object[]> findFPOByIds(@Param("masId") Long masId);
	
	
	
	
	
	
	

	/**
	 * @param applicationId
	 * @return
	 */
	@Query("Select f from FPOMasterEntity f  where f.applicationId=:applicationId")
	FPOMasterEntity getDetailByAppId(@Param("applicationId") Long applicationId);

	/**
	 * @param cbboId
	 */
	@Query("Select count(f) from FPOMasterEntity f  where f.cbboId=:cbboId")
	Long getCountOfRegFpoWithCBBO(@Param("cbboId") Long cbboId);

	/**
	 * @param fpoId
	 * @param appStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update FPOMasterEntity f set f.appStatus=:appStatus, f.remark=:remark where f.fpoId=:fpoId")
	void updateApprovalStatusAndRemark(@Param("fpoId") Long fpoId,@Param("appStatus") String appStatus,@Param("remark") String remark);

	Long countByCbboId(Long cbboId);

	/**
	 * @param masId
	 * @return
	 */
	List<FPOMasterEntity> findByIaId(Long masId);

	List<FPOMasterEntity> findByOrgId(Long orgType);

	List<FPOMasterEntity> findByOrgIdAndSdb1AndSdb2(Long orgType, Long sdb1, Long sdb2);

	List<FPOMasterEntity> findByOrgIdAndSdb1(Long orgType, Long sdb1);

	List<FPOMasterEntity> findByOrgIdAndSdb2(Long orgType, Long sdb2);

	List<FPOMasterEntity> findByCbboId(Long cbboId);

	/**
	 * @param companyRegNo
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from FPOMasterEntity f where f.companyRegNo=:companyRegNo")
	Boolean findByCompanyRegNo(@Param("companyRegNo") String companyRegNo);

	/**
	 * @param fpoName
	 * @return
	 */
	@Query("Select case when count(f)>0 THEN true ELSE false END from FPOMasterEntity f where f.fpoName=:fpoName")
	Boolean checkFpoNameExist(@Param("fpoName") String fpoName);

}
