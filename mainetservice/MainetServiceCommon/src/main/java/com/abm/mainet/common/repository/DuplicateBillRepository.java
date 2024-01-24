/**
 * 
 */
package com.abm.mainet.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.domain.DuplicateBillEntity;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public interface DuplicateBillRepository extends CrudRepository<DuplicateBillEntity, Long> {

	/**
	 * To get Duplicate Bill By Bill Id
	 * 
	 * @param bmId
	 * @param orgId
	 * @param deptCode
	 * @param serviceCode
	 * @return DuplicateBillEntity
	 */
	@Query("select a from DuplicateBillEntity a where a.bmId=:bmId and a.deptCode=:deptCode and a.orgId=:orgId and a.serviceCode=:serviceCode")
	List<DuplicateBillEntity> findByBillId(@Param("bmId") Long bmId, @Param("orgId") Long orgId,
			@Param("deptCode") String deptCode, @Param("serviceCode") String serviceCode);

	/**
	 * To get Duplicate Bills By Reference No
	 * 
	 * @param referenceId
	 * @param orgId
	 * @param deptCode
	 * @return List<DuplicateBillEntity>
	 */
	@Query("select a from DuplicateBillEntity a where a.referenceId=:referenceId and a.deptCode=:deptCode and a.orgId=:orgId")
	List<DuplicateBillEntity> findByRefNo(@Param("referenceId") String referenceId, @Param("orgId") Long orgId,
			@Param("deptCode") String deptCode);
	
	@Query("select a from DuplicateBillEntity a where a.referenceId=:referenceId and a.bmYear=:yearId and a.orgId=:orgId")
	List<DuplicateBillEntity> findByRefNoAndYearId(@Param("referenceId") String referenceId, @Param("orgId") Long orgId,
			@Param("yearId") Long yearId);
	
	@Modifying(clearAutomatically = true)
	@Query("update DuplicateBillEntity d set d.dupBillData=:writeValueAsString where d.dupBillId=:id")
	void updateOwnerNameById(@Param("id") Long dupId, @Param("writeValueAsString") String writeValueAsString);
}
