package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.WasteRateMaster;

/**
 * @author Ajay.Kumar
 *
 */
@Repository
public interface WasteRateMasterRepository extends JpaRepository<WasteRateMaster, Long> {

    /**
     * validate Waste Rate
     * @param codWast
     * @return
     */
    @Query("select wst from WasteRateMaster wst where wst.codWast =:codWast and orgid=:orgid")
    WasteRateMaster validateWasteRate(@Param("codWast") Long codWast, @Param("orgid") Long orgid);

    /**
     * get All Waste Rate
     * @param orgid
     * @return
     */
    @Query("select wst from WasteRateMaster wst where wst.orgid =:orgid")
    List<WasteRateMaster> getAllWasteRate(@Param("orgid") Long orgid);

    @Query(value = "select max(COM_LEVEL)  from tb_comparent_mas where CPM_ID=(select CPM_ID from tb_comparam_mas where CPM_PREFIX=:prefix) and ORGID=:orgId", nativeQuery = true)
    Long getPrefixLevelCount(@Param("prefix") String prefix, @Param("orgId") Long orgId);

}
