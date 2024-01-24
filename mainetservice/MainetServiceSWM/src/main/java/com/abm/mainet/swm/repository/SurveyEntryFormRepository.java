package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.SurveyFormMaster;

@Repository
public interface SurveyEntryFormRepository extends JpaRepository<SurveyFormMaster, Long>  {
    
    @Query("select wst from SurveyFormMaster wst where wst.orgid =:orgid and wst.locId =:locId")
    SurveyFormMaster getSurveyDetails(@Param("orgid") Long orgid , @Param("locId") Long locId);
    
    @Query(value ="SELECT a.COD_ID_OPER_LEVEL1,a.COD_ID_OPER_LEVEL2,a.COD_ID_OPER_LEVEL3,a.COD_ID_OPER_LEVEL4,a.COD_ID_OPER_LEVEL5 from TB_LOCATION_OPER_WARDZONE as a where a.ORGID=:orgid and a.LOC_ID=:locId",nativeQuery = true)
    List<Long[]> getLocationMappingDetails(@Param("orgid") Long orgid , @Param("locId") Long locId);

}
