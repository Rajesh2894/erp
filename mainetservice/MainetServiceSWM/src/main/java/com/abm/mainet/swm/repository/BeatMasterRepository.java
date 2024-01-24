package com.abm.mainet.swm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.swm.domain.BeatMaster;

/**
 * The Interface VehicleMasterRepository.
 *
 * @author Lalit.Prusti Created Date : 07-May-2018
 * 
 */
@Repository
public interface BeatMasterRepository extends JpaRepository<BeatMaster, Long> {

    @Modifying
    @Query("update RouteDetails rd set rd.roCollActive = ?1, rd.updatedBy = ?2,rd.lgIpMacUpd = ?3, rd.updatedDate = CURRENT_DATE where rd.rodId = ?4")
    void deleteRouteDetails(String status, Long empId, String ipMacAdd, Long id);

    @Query("select r from BeatMaster r where  r.orgid =:orgid order by r.beatId asc")
    public List<BeatMaster> findAllRouteNoByOrgId(@Param("orgid") Long orgid);
    
    @Modifying
    @Query("DELETE from  BeatDetail a where a.beatDetId in (:areaId)")
    void deleteAllRecords(@Param("areaId") List<Long> areaId);
    

}
