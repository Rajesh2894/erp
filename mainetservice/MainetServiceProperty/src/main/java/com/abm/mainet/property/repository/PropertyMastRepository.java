package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.PropertyDetEntity;
import com.abm.mainet.property.domain.PropertyMastEntity;

public interface PropertyMastRepository extends JpaRepository<PropertyMastEntity, Long> {

    @Query("SELECT m FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propertyNo and m.assActive='A'")
    PropertyMastEntity getPropertyDetailsByPropNo(@Param("propertyNo") String propertyNo, @Param("orgId") Long orgId);
    
	@Query("SELECT m FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propertyNo and m.flatNo=:flatNo and m.assActive='A'")
	PropertyMastEntity getPropertyDetailsByPropNoNFlatNo(@Param("propertyNo") String propertyNo,
			@Param("flatNo") String flatNo, @Param("orgId") Long orgId);

    @Query("SELECT m FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assOldpropno=:oldPropNo and m.assActive='A'")
	PropertyMastEntity getPropertyDetailsByOldPropNo(@Param("oldPropNo") String oldPropNo,@Param("orgId") Long orgId);
    
    @Query("SELECT m.billMethod FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propNo")
   	Long getBillMethodIdByPropNo(@Param("propNo") String propNo,@Param("orgId") Long orgId);
    
    @Query("SELECT m.flatNo FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propNo")
   	List<String> getFlatNoIdByPropNo(@Param("propNo") String propNo,@Param("orgId") Long orgId);
    
	@Query("SELECT m FROM  PropertyMastEntity m  WHERE m.orgId=:orgId and m.assNo=:propertyNo and m.flatNo=:flatNo and m.assActive='A'")
	PropertyMastEntity getPropertyDetailsByPropNoFlatNo(@Param("propertyNo") String propertyNo,
			@Param("flatNo") String flatNo, @Param("orgId") Long orgId);

	@Modifying
	@Query("update PropertyMastEntity a set a.assActive=:assActive  where  a.assNo =:assNo and a.orgId=:orgId ")
	void updatePropertyMasterStatus(@Param("assNo") String assNo, @Param("assActive") String assActive,
			@Param("orgId") Long orgId);

	@Query("SELECT d FROM  PropertyMastEntity m ,PropertyDetEntity d WHERE m.orgId=:orgId and m.assNo=:propertyNo and m.flatNo=:flatNo and m.assActive='A' and m.pmPropid=d.tbAsPrimaryMast.pmPropid")
	List<PropertyDetEntity> getPropertyDetailsByPropNoFlatNoAndOrgId(@Param("propertyNo") String propertyNo,
			@Param("flatNo") String flatNo, @Param("orgId") Long orgId);
	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("update PropertyMastEntity a set a.groupPropNo=:groupPropNo,a.parentPropNo=:parentPropNo where a.assNo in :PropNoList"
	 * ) void updateGroupPropNo(@Param("groupPropNo") String
	 * groupPropNo, @Param("parentPropNo") String parentPropNo,
	 * 
	 * @Param("PropNoList") List<String> propNoList);
	 * 
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("update PropertyMastEntity a set a.groupPropNo=null,a.parentPropNo=null where a.assNo in :PropNoList"
	 * ) void inactiveGroupPropNo(@Param("PropNoList") List<String> propNoList);
	 */
}
