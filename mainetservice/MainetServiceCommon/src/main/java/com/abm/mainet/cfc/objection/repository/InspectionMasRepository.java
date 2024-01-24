package com.abm.mainet.cfc.objection.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.cfc.objection.domain.InspectionMasterEntity;

public interface InspectionMasRepository extends JpaRepository<InspectionMasterEntity, Long> {

    @Query("SELECT i from InspectionMasterEntity i WHERE i.objId in(select o.objectionId from TbObjectionEntity o "
            + "where o.objectionNumber=:objNo) and i.orgId=:orgId and i.inspStatus='C'")
    InspectionMasterEntity getInspectionByObjNo(@Param("orgId") Long orgId, @Param("objNo") String objNo);

    @Query("SELECT h from  InspectionMasterEntity h WHERE h.orgId=:orgId"
            + " AND h.objId=:objId")
    InspectionMasterEntity getInspectionByObjId(@Param("orgId") Long orgId, @Param("objId") Long objId);

}
