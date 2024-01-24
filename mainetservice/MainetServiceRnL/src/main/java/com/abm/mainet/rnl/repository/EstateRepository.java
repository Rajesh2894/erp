package com.abm.mainet.rnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.rnl.domain.EstateEntity;

/**
 * @author ritesh.patil
 *
 */
public interface EstateRepository extends CrudRepository<EstateEntity, Long> {

    @Query("select e.esId,e.code,e.nameEng,e.nameReg,e.locationMas.locNameEng,e.locationMas.locNameReg,e.category,e.type1,e.type2,e.purpose,e.acqType from EstateEntity e where e.orgId =?1 and e.isActive = 'Y'")
    List<Object[]> findEstateRecords(Long orgId);

    @Query("select e.esId,e.code,e.nameEng,e.nameReg from EstateEntity e where e.orgId =?1 and e.locationMas.locId =?2 and e.isActive = 'Y' ")
    List<Object[]> findEstates(Long orgId, Long locId);

    @Query("select e.esId,e.code,e.nameEng,e.nameReg,e.locationMas.locNameEng,e.locationMas.locNameReg,e.category,e.type1,e.type2,e.purpose,e.acqType from EstateEntity e where e.orgId =?1 and e.isActive = 'Y' and  e.locationMas.locId = ?2 and e.esId = ?3")
    List<Object[]> findEstateFilterRecordsLocAndEstate(Long orgId, Long locId, Long estateId);

    @Query("select e.esId,e.code,e.nameEng,e.nameReg,e.locationMas.locNameEng,e.locationMas.locNameReg,e.category,e.type1,e.type2,e.purpose,e.acqType from EstateEntity e where e.orgId =?1 and e.isActive = 'Y' and  e.locationMas.locId = ?2")
    List<Object[]> findEstateFilterRecordsLoc(Long orgId, Long locId);

    @Modifying
    @Query("update EstateEntity e set e.isActive = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.esId = ?3")
    void deleteRecord(Character flag, Long empId, Long id);

    @Query("select e.esId,e.code,e.nameEng,e.nameReg from EstateEntity e where e.orgId =?1 and e.isActive = 'Y' order by e.nameEng asc")
    List<Object[]> findEstateRecordsForProperty(Long orgId);

    // writing native query because below table is from asset module entity
    @Query(value = "select ASSET_CODE from TB_AST_INFO_MST  where ASSET_CLASS in (:lookupIds) AND ORGID =:orgId", nativeQuery = true)
    List<String> fetchAssetCodesByAssetClassIds(@Param("lookupIds") List<Long> lookupIds,
            @Param("orgId") Long orgId);

    @Query("select case when count(e)>0 THEN true ELSE false END from EstateEntity e where e.assetNo =:assetNo AND e.orgId=:orgId")
    Boolean checkAssetCodePresent(@Param("assetNo") String assetNo, @Param("orgId") Long orgId);
    
    @Query(value = "select ASSET_CODE,ASSET_NAME from TB_AST_INFO_MST  where ASSET_CLASS in (:lookupIds) AND ORGID =:orgId", nativeQuery = true)
    List<Object[]> fetchAssetCodesAndAssetNameByAssetClassIds(@Param("lookupIds") List<Long> lookupIds,
            @Param("orgId") Long orgId);
    
    @Query("select e.esId,e.code,e.nameEng,e.nameReg,e.locationMas.locNameEng,e.locationMas.locNameReg,e.category,e.type1,e.type2,e.purpose,e.acqType from EstateEntity e where e.orgId =?1 and e.isActive = 'Y' and e.purpose = ?2")
    List<Object[]> findEstateFilterRecordsPurpose(@Param("orgId")Long orgId,@Param("purpose") Long purpose);
}
