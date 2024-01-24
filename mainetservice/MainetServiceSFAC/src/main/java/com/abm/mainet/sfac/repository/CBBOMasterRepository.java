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

import com.abm.mainet.sfac.domain.CBBOMastDetailEntity;
import com.abm.mainet.sfac.domain.CBBOMasterEntity;
import com.abm.mainet.sfac.domain.StateAreaZoneCategoryEntity;
import com.abm.mainet.sfac.dto.CBBOMasterDto;

/**
 * @author pooja.maske
 *
 */
@Repository
public interface CBBOMasterRepository extends JpaRepository<CBBOMasterEntity, Long> {

	/**
	 * @return
	 */
	
	@Query("Select a from StateAreaZoneCategoryEntity a ")
	List<StateAreaZoneCategoryEntity> fetchAllAreaAndZone();

	/**
	 * @param stateCode
	 * @return
	 */
	
	@Query("Select a from StateAreaZoneCategoryEntity a where a.stateCode=:stateCode ")
	StateAreaZoneCategoryEntity fetchAreaAndZoneByStateCode(@Param("stateCode") String stateCode);

	/**
	 * @param sdb3
	 * @return
	 */
	@Query("select case when count(tm)>0 THEN true ELSE false END from AspirationalDistrictDetailsEntity tm where tm.districtCode =:sdb3")
	Boolean checkIsAispirationalDist(@Param("sdb3") Long sdb3);
	
	/**
	 * @param sdb3
	 * @return
	 */
	@Query("Select case when count(tm)>0 THEN true ELSE false END from TribalDistrictDetailsEntity tm where tm.districtCode =:sdb3")
	Boolean checkIsTribalDist(@Param("sdb3") Long sdb3);

	/**
	 * @param sdb2
	 * @return
	 */
	@Query("Select o.odop from TbDistrictWiseODOPEntity o where o.districtCode=:sdb2")
	String getOdopByDist(@Param("sdb2") Long sdb2);

	/**
	 * @param orgId
	 * @return
	 */
	@Query("Select c from CBBOMasterEntity c where c.orgId=:orgId")
	List<CBBOMasterEntity> getCBBODetailsByorgId(@Param("orgId") Long orgId);

	/**
	 * @param iaId
	 * @return
	 */
	@Query("Select c from CBBOMasterEntity c, IAMasterEntity i  where c.iaId=i.IAId and c.iaId=:iaId")
	CBBOMasterEntity getCbboDetailsByIAId(@Param("iaId") Long iaId);

	/**
	 * @param removedContDetIdsList
	 * @param updatedBy
	 */
	@Modifying
	@Query("UPDATE CBBOMastDetailEntity y SET y.status ='I', y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.cbboDId in (:removedContDetIdsList)")
	void iactiveContactDetByIds(@Param("removedContDetIdsList") List<Long> removedContDetIdsList,@Param("updatedBy") Long updatedBy);

	/**
	 * @param cbboId
	 * @return
	 */
	@Query("Select c.typeofPromAgen from CBBOMasterEntity c where  c.cbboId=:cbboId")
	Long fetchPromotionAgnByCbboId(@Param("cbboId") Long cbboId);

	/**
	 * @return
	 */
	@Query("Select c from CBBOMasterEntity c")
	List<CBBOMasterEntity> findAllCBBO();

	/**
	 * @param orgTypeId
	 * @return
	 */
	@Query("Select c.cbboName from CBBOMasterEntity c where c.cbboId=:orgTypeId")
	String fetchNameById(@Param("orgTypeId") Long orgTypeId);

	/**
	 * @param panNo
	 * @return
	 */
	@Query("Select c from CBBOMasterEntity c where c.cbboId=(Select max(d.cbboId) from CBBOMasterEntity d where c.panNo=:panNo)")
	CBBOMasterEntity getDetailsByPanNo(@Param("panNo") String panNo);

	/**
	 * @param panNo
	 * @return
	 */
	@Query("Select case when count(tm)>0 THEN true ELSE false END from CBBOMasterEntity tm where tm.panNo=:panNo and tm.iaId=:iaId and tm.createdBy=:empId")
	Boolean checkPanNoExist(@Param("panNo") String panNo , @Param("iaId") Long iaId,@Param("empId") Long empId);
	
	
	@Query("Select c.cbboId,c.cbboName,c.iaId,c.IAName from CBBOMasterEntity c ,Employee e  where e.emploginname=c.cbboUniqueId  and c.cbboUniqueId=:loginName")
	List<Object[]> findAllIaAssociatedWithCbbo(@Param("loginName") String loginName);

	/**
	 * @param entity
	 * @return
	 */
	@Query("Select m from CBBOMastDetailEntity m where m.masterEntity=:entity")
	List<CBBOMastDetailEntity> getChildDetails(@Param("entity") CBBOMasterEntity entity);
	
	/**
	 * @param removedContDetIdsList
	 * @param updatedBy
	 */
	@Modifying
	@Query("UPDATE CBBOMastDetailHistory y SET y.status ='I', y.historyStatus ='D', y.updatedDate=CURRENT_DATE, y.updatedBy=:updatedBy where y.cbboDId in (:removedContDetIdsList)")
	void iactiveContactDetInHistByIds(@Param("removedContDetIdsList") List<Long> removedContDetIdsList,@Param("updatedBy") Long updatedBy);

	/**
	 * @param masId
	 * @return
	 */
	@Query("Select distinct m from CBBOMasterEntity m where m.iaId=:masId")
	List<CBBOMasterEntity> findCbboById(@Param("masId") Long masId);

	List<CBBOMasterEntity> findByIaId(Long masId);



	List<CBBOMasterEntity> findByCbboUniqueId(String cbboUniqueId);

	List<CBBOMasterEntity> findByOrgId(Long orgType);

	/**
	 * @param applicationId
	 * @return
	 */
	CBBOMasterEntity findByApplicationId(Long applicationId);

	/**
	 * @param cbboId
	 * @param appStatus
	 * @param remark
	 */
	@Modifying
	@Query("Update CBBOMasterEntity h set  h.appStatus=:appStatus , h.remark=:remark where h.cbboId=:cbboId")
	void updateApprovalStatusAndRemark(@Param("cbboId") Long cbboId,@Param("appStatus")  String appStatus,@Param("remark")  String remark);

	List<CBBOMasterEntity> findByOrgIdAndSdb1(Long orgType, Long sdb1);

}
