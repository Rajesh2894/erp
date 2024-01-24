package com.abm.mainet.property.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.property.domain.TbAsTryEntity;

public interface TbAsTryRepository extends JpaRepository<TbAsTryEntity, Long> {

    @Query("select distinct t.tryDisName,t.tryDisCode from TbAsTryEntity t  where t.tryPropType=:landType order by t.tryDisName")
    List<Object[]> findDistrictByLandType(@Param("landType") String landType);

    @Query("select distinct t.tryTehsilName,t.tryTehsilCode from TbAsTryEntity t  where t.tryDisCode=:districtId and t.tryPropType=:landType order by t.tryTehsilName")
    List<Object[]> findTehsilListByDistrict(@Param("districtId") String districtId, @Param("landType") String landType);

    @Query("select distinct t.tryVillName,t.tryVillCode,t.tryHalkaCode from TbAsTryEntity t  where t.tryTehsilCode=:tehsilId and t.tryDisCode=:districtId and t.tryPropType=:landType order by t.tryVillName")
    List<Object[]> findVillageListByTehsil(@Param("tehsilId") String tehsilId, @Param("districtId") String districtId,
            @Param("landType") String landType);

    @Query("select distinct t.tryWardName,t.tryWardCode from TbAsTryEntity t  where t.tryVillCode=:villageId and t.tryTehsilCode=:tehsilId and t.tryDisCode=:districtId and t.tryPropType=:landType order by t.tryWardName")
    List<Object[]> findMohallaListByVillageId(@Param("villageId") String villageId, @Param("tehsilId") String tehsilId,
            @Param("districtId") String districtId, @Param("landType") String landType);

    @Query("select distinct t.trySheetNo,t.trySheetId from TbAsTryEntity t  where t.tryWardCode=:mohallaId and t.tryVillCode=:villageId and t.tryTehsilCode=:tehsilId and t.tryDisCode=:districtId and t.tryPropType=:landType order by t.trySheetNo")
    List<Object[]> findStreetListByMohallaId(@Param("mohallaId") String mohallaId, @Param("villageId") String villageId,
            @Param("tehsilId") String tehsilId, @Param("districtId") String districtId, @Param("landType") String landType);

    @Query("select distinct t.tryRiCode from TbAsTryEntity t where t.tryPropType=:landType and t.tryDisCode=:districtId and t.tryTehsilCode=:tehsilId and t.tryVillCode=:villageId ")
    String findRiDetailsByDistrictTehsilVillageID(@Param("landType") String landType, @Param("districtId") String districtId,
            @Param("tehsilId") String tehsilId, @Param("villageId") String villageId);

    @Query("select distinct t.tryVsrNo from TbAsTryEntity t where t.tryDisCode=:districtId and t.tryTehsilCode=:tehsilId and t.tryVillCode=:villageId and t.tryRiCode=:RiCode")
    String findVsrNoByDistrictTehsilVillageRiCode(@Param("districtId") String districtId,@Param("tehsilId") String tehsilId,@Param("RiCode") String RiCode,@Param("villageId") String villageId);
    
    @Query("select distinct t.tryRecordcode from TbAsTryEntity t where  t.tryDisCode=:districtId and t.tryTehsilCode=:tehsilId and t.tryVillCode=:villageId and t.tryWardCode=:mohallaId and t.trySheetId=:streetNo")
    String findRecordDetailsByDistrictTehsilVillageMohallaStreetID(@Param("districtId") String districtId,
            @Param("tehsilId") String tehsilId, @Param("villageId") String villageId,
            @Param("mohallaId") String mohallaId, @Param("streetNo") String streetNo);

    @Query("select t from TbAsTryEntity t where  t.tryRecordcode=:recordcode and t.tryPropType=:landTypeId")
    TbAsTryEntity findTryDataByRecordNoAndLandType(@Param("recordcode") String recordcode,
            @Param("landTypeId") String landTypeId);

    @Query("from TbAsTryEntity "
            + "  am  WHERE  am.tryVsrNo=:vsrNo")
    TbAsTryEntity findTryDataByVsrNo(@Param("vsrNo") String vsrNo);
    
    @Query("select distinct t.tryVillCode,t.tryVillName,t.tryVsrNo from TbAsTryEntity t")
    List<Object[]> findVillageList();
}
