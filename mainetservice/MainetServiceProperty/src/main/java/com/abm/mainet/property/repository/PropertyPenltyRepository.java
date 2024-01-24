package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.PropertyPenltyEntity;

public interface PropertyPenltyRepository extends JpaRepository<PropertyPenltyEntity, Long> {

    @Query("select m from PropertyPenltyEntity m where m.propNo=:propNo and m.finYearId=:finYearId and m.orgId=:orgId and m.activeFlag is null")
    PropertyPenltyEntity getPropertyPenltyByPropNoAndFinYearId(@Param("propNo") String propNo, @Param("finYearId") Long finYearId,
            @Param("orgId") Long orgId);
     
    @Modifying
    @Query("UPDATE PropertyPenltyEntity a SET a.activeFlag =:activeFlag where a.finYearId=:finYearId and a.orgId=:orgId and a.propNo=:propNo")
    void inActiveSurchargeByPropNoAndFinYear(@Param("activeFlag") String activeFlag,  @Param("finYearId") Long finYearId, @Param("orgId") Long orgId,@Param("propNo") String propNo);

	@Query("select m from PropertyPenltyEntity m where m.propNo=:propNo and m.orgId=:orgId and m.activeFlag is null")
    List<PropertyPenltyEntity> getPropertyPenltyByPropNo(@Param("propNo") String propNo,@Param("orgId") Long orgId);
	
	@Query("select sum(m.pendingAmount) from PropertyPenltyEntity m where m.propNo in(:propNoList) and m.finYearId=:finYearId and m.orgId=:orgId ")
	Double getPropertyPenltyByGroupPropNos(@Param("propNoList") List<String> propNoList,
			@Param("finYearId") Long finYearId, @Param("orgId") Long orgId);
	
	@Query("select m from PropertyPenltyEntity m where m.parentPropNo=:parentPropNo and m.finYearId=:finYearId and m.orgId=:orgId and m.activeFlag is null")
	List<PropertyPenltyEntity> getPropertyPenltyByParentPropNoAndFinYearId(@Param("parentPropNo") String propNo, @Param("finYearId") Long finYearId,
            @Param("orgId") Long orgId);
	
}
