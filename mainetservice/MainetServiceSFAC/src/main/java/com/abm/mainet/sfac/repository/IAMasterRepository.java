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

import com.abm.mainet.sfac.domain.IAMasterEntity;

/**
 * @author pooja.maske
 *
 */

@Repository
public interface IAMasterRepository extends JpaRepository<IAMasterEntity, Long> {

	/**
	 * @param orgId
	 * @return
	 */
	@Query("Select m from IAMasterEntity m where m.orgId=:orgId")
	List<IAMasterEntity> getIAListByOrgId(@Param("orgId") Long orgId);

	@Modifying
	@Query("UPDATE IAMasterDetailEntity y SET y.status ='I',y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.iadId in (:removedContDetIdsList)")
	void iactiveContactDetByIds(@Param("removedContDetIdsList") List<Long> removedContDetIdsList,
			@Param("updatedBy") Long updatedBy);

	/**
	 * @param iaName
	 * @return
	 */
	@Query("Select m.alcYear from IAMasterEntity m where m.IAName=:iaName")
	Long getIaALlocationYear(@Param("iaName") String iaName);

	/**
	 * @return
	 */
	@Query("Select m from IAMasterEntity m")
	List<IAMasterEntity> findAllIA();

	/**
	 * @param orgTypeId
	 * @return
	 */
	@Query("Select m.IAName from IAMasterEntity m where m.IAId=:orgTypeId")
	String fetchNameById(@Param("orgTypeId") Long orgTypeId);

	/**
	 * @param empId
	 * @return
	 */
	@Query("Select m from IAMasterEntity m, CBBOMasterEntity c   where c.cbboId=:empId and m.IAId=c.iaId")
	List<IAMasterEntity> findAllIaAssociatedWithCbbo(@Param("empId") Long empId);

	/**
	 * @param iaId
	 * @return
	 */
	@Query("Select m.alcYear from IAMasterEntity m where m.IAId=:iaId")
	Long findByIAId(@Param("iaId") Long iaId);

	List<IAMasterEntity> findByOrgId(Long orgType);

	/**
	 * @param iAName
	 * @return
	 */
	@Query("Select case when count(m)>0 THEN true ELSE false END from IAMasterEntity m where m.IAName=:iAName")
	Boolean checkIaNameExist(@Param("iAName")  String iAName);

	/**
	 * @param iaShortName
	 * @return
	 */
	@Query("Select case when count(i)>0 THEN true ELSE false END from IAMasterEntity i where i.iaShortName=:iaShortName")
	Boolean checkIaShortNmExist(@Param("iaShortName")  String iaShortName);


}
